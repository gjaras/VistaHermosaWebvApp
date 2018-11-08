/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.grupo2.vhwebapp.servlets;

import cl.grupo2.vhwebapp.util.Config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
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
@WebServlet(name = "DashboardServlet", urlPatterns = {"/dashboard"})
public class DashboardServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(DashboardServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        LOG.info("Entered Dashboard. Setting view attributes");
        HttpSession session = request.getSession();
        HashMap user = (HashMap) session.getAttribute("userParams");
        request.setAttribute("userName", user.get("userName").toString());
        request.setAttribute("userRut", user.get("userRut").toString());
        request.setAttribute("userType", user.get("userType").toString());

        LOG.info("Initiating info request. Creating request object");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject requestJsonObject = new JsonObject();
        requestJsonObject.addProperty("rut", user.get("userRut").toString());
        String requestJsonString = gson.toJson(requestJsonObject);
        EntityBuilder eb = EntityBuilder.create().setText(requestJsonString);
        String url = Config.get("BD_BASE_URL") + "/IntegracionVistaHermosa/WebServiceAppWeb/requestdashboardinfo";
        //String url = "http://localhost:8081/IntegracionVistaHermosa/WebServiceAppWeb/requestdashboardinfo";
        String result = "";
        try {
                LOG.info("Request Object Built. Requesting Dashboard Info");
            result = Request.Post(url).addHeader(HttpHeaders.CONTENT_TYPE, "application/json").addHeader("accessToken", Config.get("ACCESS_TOKEN"))
                    .body(eb.build()).execute().returnContent().asString();
            JsonObject resultJson = new JsonParser().parse(result).getAsJsonObject();
            
            if (resultJson.get("response").getAsString().equalsIgnoreCase("failed")) {
                    LOG.error("Server Problem");
                    request.setAttribute("styleClass", "alert alert-danger");
                    request.setAttribute("message", "There is a problem with the remote server: " + resultJson.get("message").getAsString());
            } else if (resultJson.get("response").getAsString().equalsIgnoreCase("success")) {
                LOG.info("Successful server call");
                if (resultJson.get("lastPermiso") != null) {
                    LOG.info("There is a Last Permiso");
                    JsonObject lastPermisoJson = resultJson.get("lastPermiso").getAsJsonObject();
                    HashMap lastPermiso = new HashMap();

                    lastPermiso.put("permisoId", lastPermisoJson.get("permisoId").getAsString());
                    lastPermiso.put("permisoFecha", lastPermisoJson.get("permisoFecha").getAsString());
                    lastPermiso.put("permisoStatus", lastPermisoJson.get("permisoStatus").getAsString());
                    request.setAttribute("lastPermiso", lastPermiso);
                }
                request.setAttribute("solAcept", resultJson.get("solicitudesAceptadas") != null ? resultJson.get("solicitudesAceptadas") : 0);
                request.setAttribute("solRech", resultJson.get("solicitudesRechazadas") != null ? resultJson.get("solicitudesRechazadas") : 0);
                request.setAttribute("solPend", resultJson.get("solicitudesPendientes") != null ? resultJson.get("solicitudesPendientes") : 0);
                
            }
            
        } catch (Exception ex) {
            request.setAttribute("styleClass", "alert alert-danger");
            request.setAttribute("message", "There is a problem with the remote server: " + ex.getMessage());
        }
        
        request.getRequestDispatcher("/WEB-INF/pages/dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
