/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.grupo2.vhwebapp.servlets.users;

import cl.grupo2.vhwebapp.util.Config;
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
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author gabriel.jara
 */
@WebServlet(name = "ListUsersServlet", urlPatterns = {"/listUsers"})
public class ListUsersServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(ListUsersServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOG.info("Entered User List");
        HttpSession session = request.getSession();

        String type = ((HashMap) session.getAttribute("userParams")).get("userType").toString();
        LOG.info("user type: "+type);
        if (type.equalsIgnoreCase("admin")) {
            String url = Config.get("BD_BASE_URL") + "/IntegracionVistaHermosa/WebServiceAppWeb/requestuserlist";
            //String url = "http://localhost:8081/IntegracionVistaHermosa/WebServiceAppWeb/requestdashboardinfo";
            String result = "";
            try {
                LOG.info("Requesting List");
                result = Request.Get(url).addHeader("accessToken", Config.get("ACCESS_TOKEN"))
                        .execute().returnContent().asString();
                JsonObject resultJson = new JsonParser().parse(result).getAsJsonObject();

                if (resultJson.get("response").getAsString().equalsIgnoreCase("failed")) {
                    LOG.error("Server Problem");
                    request.setAttribute("styleClass", "alert alert-danger");
                    request.setAttribute("message", "There is a problem with the remote server: " + resultJson.get("message").getAsString());
                } else if (resultJson.get("response").getAsString().equalsIgnoreCase("success")) {
                    LOG.info("List successfuly retrieved");
                    JsonArray listArray = resultJson.getAsJsonArray("users");
                    ArrayList userList = new ArrayList();
                    for (JsonElement element : listArray) {
                        LOG.info("there is element");
                        JsonObject member = element.getAsJsonObject();
                        HashMap hm = new HashMap();
                        hm.put("id", member.get("id").getAsString());
                        hm.put("nombre", member.get("nombre").getAsString());
                        hm.put("tipo", member.get("tipo").getAsString());
                        userList.add(hm);
                    }
                    request.setAttribute("members", userList);
                }

            } catch (Exception ex) {
                request.setAttribute("styleClass", "alert alert-danger");
                request.setAttribute("message", "There is a problem with the remote server: " + ex.getMessage());
            }

            request.getRequestDispatcher("/WEB-INF/pages/users/list.jsp").forward(request, response);
        }else{
            response.sendRedirect("dashboard");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
