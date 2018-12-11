/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.grupo2.vhwebapp.servlets.funcionarios;

import cl.grupo2.vhwebapp.util.Config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.http.HttpHeaders;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author gabriel.jara
 */
@WebServlet(name = "ViewFuncionarioServlet", urlPatterns = {"/viewFuncionario"})
public class ViewFuncionarioServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(ViewFuncionarioServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOG.info("Entered Funcionario View");
        HttpSession session = request.getSession();

        String type = ((HashMap) session.getAttribute("userParams")).get("userType").toString();
        String funcRut = request.getParameter("rut").split("-")[0];
        LOG.info("user type: " + type);
        if (type.equalsIgnoreCase("Administrador") || type.equalsIgnoreCase("Encargado")) {
            String url = Config.get("BD_BASE_URL") + "/IntegracionVistaHermosa/restWs/funcionario/requestView?rut=" + funcRut;
            String result = "";
            try {
                LOG.info("Request Object Built. Requesting List");
                result = Request.Get(url).addHeader("accessToken", Config.get("ACCESS_TOKEN"))
                        .execute().returnContent().asString();
                JsonObject resultJson = new JsonParser().parse(result).getAsJsonObject();
                if (resultJson.get("response").getAsString().equalsIgnoreCase("failed")) {
                    LOG.error("Server Problem");
                    request.setAttribute("styleClass", "alert alert-danger");
                    request.setAttribute("message", "There is a problem with the remote server: " + resultJson.get("message").getAsString());
                } else if (resultJson.get("response").getAsString().equalsIgnoreCase("success")) {
                    LOG.info("Funcionario successfuly retrieved");
                    JsonObject funcionario = resultJson.getAsJsonObject("funcionario");
                    request.setAttribute("rut", funcionario.get("rut").getAsString());
                    request.setAttribute("nombre", funcionario.get("nombre").getAsString());
                    request.setAttribute("fecNac", funcionario.get("fecNac").getAsString());
                    request.setAttribute("correo", funcionario.get("correo").getAsString());
                    request.setAttribute("direccion", funcionario.get("direccion").getAsString());
                    request.setAttribute("cargo", funcionario.get("cargo").getAsString());
                    request.setAttribute("unidad", funcionario.get("unidad").getAsString());
                    JsonArray listArray = resultJson.getAsJsonArray("permisoList");
                    ArrayList solicitudesList = new ArrayList();
                    for (JsonElement element : listArray) {
                        JsonObject member = element.getAsJsonObject();
                        HashMap hm = new HashMap();
                        hm.put("id", member.get("permisoId").getAsString());
                        hm.put("rutSol", member.get("permisoFunc").getAsString());
                        hm.put("type", member.get("permisoType").getAsString());
                        hm.put("fecSol", member.get("permisoFechaSol").getAsString());
                        hm.put("fecIni", member.get("permisoFechaIni").getAsString());
                        hm.put("fecFin", member.get("permisoFechaFin").getAsString());
                        switch (Integer.parseInt(member.get("permisoStatus").getAsString())) {
                            case 0:
                                hm.put("status", "Rechazada");
                                break;
                            case 1:
                                hm.put("status", "Aceptada");
                                break;
                            case 2:
                                hm.put("status", "Pendiente");
                                break;
                        }
                        hm.put("aut", member.get("permisoAut").getAsString());
                        solicitudesList.add(hm);
                    }
                    request.setAttribute("solicitudes", solicitudesList);
                    request.setAttribute("servletName", "viewFuncionario");
                    if (solicitudesList.size() > 0) {
                        request.setAttribute("pages", Math.ceil(new Double(solicitudesList.size()) / 10.0));
                    }
                }

            } catch (Exception ex) {
                request.setAttribute("styleClass", "alert alert-danger");
                request.setAttribute("message", "There is a problem with the remote server: " + ex.getMessage());
            }

            request.getRequestDispatcher("/WEB-INF/pages/funcionarios/view.jsp?page=1").forward(request, response);
        } else {
            response.sendRedirect("dashboard");
        }
    }

}
