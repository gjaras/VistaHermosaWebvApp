/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.grupo2.vhwebapp.servlets.solicitudes;

import cl.grupo2.vhwebapp.servlets.users.DeleteUserServlet;
import cl.grupo2.vhwebapp.util.Config;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author gabriel.jara
 */
@WebServlet(name = "ChangeSolState", urlPatterns = {"/changeSolState"})
public class ChangeSolStateServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(ChangeSolStateServlet.class);
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String opType = request.getParameter("opType");
        String id = opType.equalsIgnoreCase("accept") ? request.getParameter("solIdAccept") : request.getParameter("solIdReject");
        LOG.info("Change Solicitud State Servlet called. User ID: " + id);
        HttpSession session = request.getSession();

        String type = ((HashMap) session.getAttribute("userParams")).get("userType").toString();
        String runSinDv = ((HashMap) session.getAttribute("userParams")).get("userRut").toString().split("-")[0];
        LOG.info("user type: " + type);
        if (type.equalsIgnoreCase("Administrador")) {
            String url = Config.get("BD_BASE_URL") + "/IntegracionVistaHermosa/restWs/permiso/changeState?id=" +id+"&opType="+opType+"&runSinDv="+runSinDv;
            LOG.info("URL: "+url);
            String result = "";
            try {
                LOG.info("Changin Solicitud State");
                result = Request.Get(url).addHeader("accessToken", Config.get("ACCESS_TOKEN"))
                        .execute().returnContent().asString();
                JsonObject resultJson = new JsonParser().parse(result).getAsJsonObject();
                if (resultJson.get("response").getAsString().equalsIgnoreCase("failed")) {
                    LOG.error("Server Problem");
                } else if (resultJson.get("response").getAsString().equalsIgnoreCase("success")) {
                    if (resultJson.get("result").getAsString().equalsIgnoreCase("failed")) {
//               
                    } else {

                    }
                } else {
                    throw new Exception("Strange Exception");
                }

            } catch (Exception ex) {
                LOG.error("Exception Creating User");
//            request.setAttribute("styleClass", "alert alert-danger");
//            request.setAttribute("message", "problem Requesting Auth: " + ex.getLocalizedMessage());
//            request.getRequestDispatcher("/WEB-INF/pages/users/create.jsp").forward(request, response);
            }
            response.sendRedirect("listSolicitudes?page=1");
        } else {
            response.sendRedirect("dashboard");
        }
    }

}
