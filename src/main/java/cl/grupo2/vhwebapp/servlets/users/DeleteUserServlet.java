/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.grupo2.vhwebapp.servlets.users;

import cl.grupo2.vhwebapp.util.Config;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.HttpHeaders;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author gabriel.jara
 */
@WebServlet(name = "DeleteUserServlet", urlPatterns = {"/deleteUser"})
public class DeleteUserServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(DeleteUserServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("usrId");
        LOG.info("Delete user servlet called. User ID: "+id);
        String url = Config.get("BD_BASE_URL") + "/IntegracionVistaHermosa/restWs/usuario/delete?id="+id;
        String result = "";
        try {
            LOG.info("Deleting User");
                result = Request.Get(url).addHeader("accessToken", Config.get("ACCESS_TOKEN"))
                        .execute().returnContent().asString();
                JsonObject resultJson = new JsonParser().parse(result).getAsJsonObject();
            if (resultJson.get("response").getAsString().equalsIgnoreCase("failed")) {
                LOG.error("Server Problem");
//                request.setAttribute("styleClass", "alert alert-danger");
//                request.setAttribute("message", "There is a problem witth the server: " + resultJson.get("message").getAsString());
//                request.getRequestDispatcher("/WEB-INF/pages/users/create.jsp").forward(request, response);
            } else if (resultJson.get("response").getAsString().equalsIgnoreCase("success")) {
                if (resultJson.get("result").getAsString().equalsIgnoreCase("failed")) {
//                    request.setAttribute("styleClass", "alert alert-danger");
//                    request.setAttribute("message", "User Could not be created. "+resultJson.get("message").getAsString());
//                    request.getRequestDispatcher("/WEB-INF/pages/users/create.jsp").forward(request, response);
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
        response.sendRedirect("listUsers?page=1");
    }

}
