/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.grupo2.vhwebapp.servlets.users;

import cl.grupo2.vhwebapp.util.Config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import org.apache.http.HttpHeaders;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author gabriel.jara
 */
@WebServlet(name = "CreateUserServlet", urlPatterns = {"/createUser"})
public class CreateUserServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(CreateUserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOG.info("Entered Create User");
        HttpSession session = request.getSession();

        String type = ((HashMap) session.getAttribute("userParams")).get("userType").toString();
        LOG.info("user type: " + type);
        if (type.equalsIgnoreCase("Administrador")) {
            request.getRequestDispatcher("/WEB-INF/pages/users/create.jsp").forward(request, response);
        } else {
            response.sendRedirect("dashboard");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOG.info("Attempting to Insert User");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject requestJsonObject = new JsonObject();
        requestJsonObject.addProperty("rut", request.getParameter("rut"));
        requestJsonObject.addProperty("nombre", request.getParameter("nombre"));
        requestJsonObject.addProperty("password", request.getParameter("pwd"));
        requestJsonObject.addProperty("type", request.getParameter("type"));
        String requestJsonString = gson.toJson(requestJsonObject);
        EntityBuilder eb = EntityBuilder.create().setText(requestJsonString);
        String url = Config.get("BD_BASE_URL") + "/IntegracionVistaHermosa/restWs/usuario/create";
        //String url = "http://localhost:8081/IntegracionVistaHermosa/WebServiceAppWeb/requestauth";
        String result = "";
        try {
            LOG.info("Request Object Built. Requesting Insert User");
            result = Request.Post(url).addHeader(HttpHeaders.CONTENT_TYPE, "application/json").addHeader("accessToken", Config.get("ACCESS_TOKEN"))
                    .body(eb.build()).execute().returnContent().asString();
            JsonObject resultJson = new JsonParser().parse(result).getAsJsonObject();
            if (resultJson.get("response").getAsString().equalsIgnoreCase("failed")) {
                LOG.error("Server Problem");
                request.setAttribute("styleClass", "alert alert-danger");
                request.setAttribute("message", "There is a problem witth the server: " + resultJson.get("message").getAsString());
                request.getRequestDispatcher("/WEB-INF/pages/users/create.jsp").forward(request, response);
            } else if (resultJson.get("response").getAsString().equalsIgnoreCase("success")) {
                if (resultJson.get("result").getAsString().equalsIgnoreCase("failed")) {
                    request.setAttribute("styleClass", "alert alert-danger");
                    request.setAttribute("message", "User Could not be created. "+resultJson.get("message").getAsString());
                    request.getRequestDispatcher("/WEB-INF/pages/users/create.jsp").forward(request, response);
                } else {
                    response.sendRedirect("listUsers?page=1");
                }
            } else {
                throw new Exception("Strange Exception");
            }

        } catch (Exception ex) {
            LOG.error("Exception Creating User");
            request.setAttribute("styleClass", "alert alert-danger");
            request.setAttribute("message", "problem Requesting Auth: " + ex.getLocalizedMessage());
            request.getRequestDispatcher("/WEB-INF/pages/users/create.jsp").forward(request, response);
        }
    }
}
