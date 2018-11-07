/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.grupo2.vhwebapp.servlets;

import cl.grupo2.vhwebapp.util.Config;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.HashMap;
import org.apache.http.HttpHeaders;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.fluent.Request;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.http.client.entity.EntityBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author gabriel.jara
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(LoginServlet.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
        //processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOG.info("Posting to Login Servlet..");
        if (request.getParameter("usr") != null && !request.getParameter("usr").isEmpty() && request.getParameter("pwd") != null && !request.getParameter("pwd").isEmpty()) {
            LOG.info("Correct Parameters Detected. Building Request Object..");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject requestJsonObject = new JsonObject();
            requestJsonObject.addProperty("name", request.getParameter("usr"));
            requestJsonObject.addProperty("pass", request.getParameter("pwd"));
            String requestJsonString = gson.toJson(requestJsonObject);
            EntityBuilder eb = EntityBuilder.create().setText(requestJsonString);
            String url = Config.get("BD_BASE_URL")+"/IntegracionVistaHermosa/WebServiceAppWeb/requestauth";
            //String url = "http://localhost:8081/IntegracionVistaHermosa/WebServiceAppWeb/requestauth";
            String result = "";
            try {
                LOG.info("Request Object Built. Requesting Auth");
                result = Request.Post(url).addHeader(HttpHeaders.CONTENT_TYPE, "application/json").addHeader("accessToken", Config.get("ACCESS_TOKEN"))
                        .body(eb.build()).execute().returnContent().asString();
                JsonObject resultJson = new JsonParser().parse(result).getAsJsonObject();
                if (resultJson.get("response").getAsString().equalsIgnoreCase("failed")) {
                    LOG.error("Server Problem");
                    request.setAttribute("styleClass", "alert alert-danger");
                    request.setAttribute("message", "There is a problem witth the server: " + resultJson.get("message").getAsString());
                    request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
                } else if (resultJson.get("response").getAsString().equalsIgnoreCase("success")) {
                    if (resultJson.get("tipo") != null) {
                        //request.setAttribute("styleClass", "alert alert-success");
                        //request.setAttribute("message", "User Correctly Authenticated"+resultJson.get("tipo").getAsString());
                        //request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
                        HashMap user = new HashMap();
                        user.put("userRut", resultJson.get("rut"));
                        user.put("userName", resultJson.get("nombre"));
                        user.put("userType", resultJson.get("tipo"));
                        HttpSession session = request.getSession();
			session.setAttribute("userParams", user);
                        response.sendRedirect("dashboard");
                    }else{
                        request.setAttribute("styleClass", "alert alert-danger");
                        request.setAttribute("message", resultJson.get("message").getAsString());
                        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
                     
                    }
                }else{
                    throw new Exception("Strange Exception");
                }

            } catch (Exception ex) {
                LOG.error("Exception Requesting Auth");
                request.setAttribute("styleClass", "alert alert-danger");
                request.setAttribute("message", "problem Requesting Auth: " + ex.getLocalizedMessage());
                request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
            }
        } else {
            LOG.warn("Incorrect Parameters Detected.");
            request.setAttribute("styleClass", "alert alert-danger");
            request.setAttribute("message", "incorrect parameters");
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);

        }
    }
}
