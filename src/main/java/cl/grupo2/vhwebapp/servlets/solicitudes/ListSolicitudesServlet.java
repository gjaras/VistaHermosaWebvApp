/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.grupo2.vhwebapp.servlets.solicitudes;

import cl.grupo2.vhwebapp.servlets.users.*;
import cl.grupo2.vhwebapp.util.Config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
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
@WebServlet(name = "ListSolicitudesServlet", urlPatterns = {"/listSolicitudes"})
public class ListSolicitudesServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(ListSolicitudesServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOG.info("Entered Peticiones List");
        HttpSession session = request.getSession();

        String type = ((HashMap) session.getAttribute("userParams")).get("userType").toString();
        LOG.info("user type: " + type);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject requestJsonObject = new JsonObject();
        LOG.info("comparing types");
        if (type.equalsIgnoreCase("Administrador") || type.equalsIgnoreCase("Encargado")) {
            LOG.info("User is Admin or Encargado");
            String requestJsonString = gson.toJson(requestJsonObject);
            EntityBuilder eb = EntityBuilder.create().setText(requestJsonString);
            String url = Config.get("BD_BASE_URL") + "/IntegracionVistaHermosa/restWs/permiso/requestList";
            //String url = "http://localhost:8081/IntegracionVistaHermosa/WebServiceAppWeb/requestpeticioneslist";
            String result = "";
            try {
                LOG.info("Request Object Built. Requesting List");
                result = Request.Post(url).addHeader(HttpHeaders.CONTENT_TYPE, "application/json").addHeader("accessToken", Config.get("ACCESS_TOKEN"))
                        .body(eb.build()).execute().returnContent().asString();
                JsonObject resultJson = new JsonParser().parse(result).getAsJsonObject();

                if (resultJson.get("response").getAsString().equalsIgnoreCase("failed")) {
                    LOG.error("Server Problem");
                    request.setAttribute("styleClass", "alert alert-danger");
                    request.setAttribute("message", "There is a problem with the remote server: " + resultJson.get("message").getAsString());
                } else if (resultJson.get("response").getAsString().equalsIgnoreCase("success")) {
                    LOG.info("List successfuly retrieved");
                    JsonArray listArray = resultJson.getAsJsonArray("permisoList");
                    ArrayList solicitudesList = new ArrayList();
                    for (JsonElement element : listArray) {
                        LOG.info("there is element");
                        JsonObject member = element.getAsJsonObject();
                        HashMap hm = new HashMap();
                        hm.put("id", member.get("permisoId").getAsString());
                        hm.put("rutSol", member.get("permisoType").getAsString());
                        hm.put("type", member.get("permisoFunc").getAsString());
                        hm.put("fecSol", member.get("permisoFechaSol").getAsString());
                        hm.put("fecIni", member.get("permisoFechaIni").getAsString());
                        hm.put("fecFin", member.get("permisoFechaFin").getAsString());
                        switch(Integer.parseInt(member.get("permisoStatus").getAsString())){
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
                    request.setAttribute("members", solicitudesList);
                    request.setAttribute("servletName", "listSolicitudes");
                    if(solicitudesList.size() > 0){
                        request.setAttribute("pages", Math.ceil(new Double(solicitudesList.size())/10.0));
                    }
                }

            } catch (Exception ex) {
                request.setAttribute("styleClass", "alert alert-danger");
                request.setAttribute("message", "There is a problem with the remote server: " + ex.getMessage());
            }

            request.getRequestDispatcher("/WEB-INF/pages/permisos/listAll.jsp").forward(request, response);
        } else {
            LOG.info("User is Normal User");
            requestJsonObject.addProperty("rut", ((HashMap) session.getAttribute("userParams")).get("userRut").toString());
            String requestJsonString = gson.toJson(requestJsonObject);
            EntityBuilder eb = EntityBuilder.create().setText(requestJsonString);
            String url = Config.get("BD_BASE_URL") + "/IntegracionVistaHermosa/WebServiceAppWeb/requestPeticionesList";
            //String url = "http://localhost:8081/IntegracionVistaHermosa/WebServiceAppWeb/requestpeticioneslist";
            String result = "";
            try {
                LOG.info("Request Object Built. Requesting List");
                result = Request.Post(url).addHeader(HttpHeaders.CONTENT_TYPE, "application/json").addHeader("accessToken", Config.get("ACCESS_TOKEN"))
                        .body(eb.build()).execute().returnContent().asString();
                JsonObject resultJson = new JsonParser().parse(result).getAsJsonObject();

                if (resultJson.get("response").getAsString().equalsIgnoreCase("failed")) {
                    LOG.error("Server Problem");
                    request.setAttribute("styleClass", "alert alert-danger");
                    request.setAttribute("message", "There is a problem with the remote server: " + resultJson.get("message").getAsString());
                } else if (resultJson.get("response").getAsString().equalsIgnoreCase("success")) {
                    LOG.info("List successfuly retrieved");
                    JsonArray listArray = resultJson.getAsJsonArray("permisoList");
                    ArrayList solicitudesList = new ArrayList();
                    for (JsonElement element : listArray) {
                        LOG.info("there is element");
                        JsonObject member = element.getAsJsonObject();
                        HashMap hm = new HashMap();
                        hm.put("id", member.get("permisoId").getAsString());
                        hm.put("type", member.get("permisoType").getAsString());
                        hm.put("fecSol", member.get("permisoFechaSol").getAsString());
                        hm.put("fecIni", member.get("permisoFechaIni").getAsString());
                        hm.put("fecFin", member.get("permisoFechaFin").getAsString());
                        switch(Integer.parseInt(member.get("permisoStatus").getAsString())){
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
                        solicitudesList.add(hm);
                    }
                    request.setAttribute("members", solicitudesList);
                    request.setAttribute("servletName", "listSolicitudes");
                    if(solicitudesList.size() > 0){
                        request.setAttribute("pages", Math.ceil(new Double(solicitudesList.size())/10.0));
                    }
                }

            } catch (Exception ex) {
                request.setAttribute("styleClass", "alert alert-danger");
                request.setAttribute("message", "There is a problem with the remote server: " + ex.getMessage());
            }

            request.getRequestDispatcher("/WEB-INF/pages/permisos/listIndividual.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
