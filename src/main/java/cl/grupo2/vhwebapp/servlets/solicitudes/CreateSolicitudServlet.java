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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.ws.rs.core.MediaType;
import org.apache.http.HttpHeaders;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author gabriel.jara
 */
@WebServlet(name = "CreateSolicitudServlet", urlPatterns = {"/createSolicitud"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class CreateSolicitudServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(CreateSolicitudServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOG.info("Entered Create Solicitud");
        HttpSession session = request.getSession();

        request.getRequestDispatcher("/WEB-INF/pages/permisos/create.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOG.info("Attempting Create Solicitud");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Part filePart = request.getPart("doc");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString().replace(" ","_").replace(".", "-");

        HttpSession session = request.getSession();

        String rut = ((HashMap) session.getAttribute("userParams")).get("userRut").toString();

        JsonObject requestJsonObject = new JsonObject();
        requestJsonObject.addProperty("tipo", request.getParameter("tipoPer"));
        requestJsonObject.addProperty("fecInit", request.getParameter("fecInit"));
        requestJsonObject.addProperty("fecFin", request.getParameter("fecFin"));
        requestJsonObject.addProperty("desc", request.getParameter("desc"));
        requestJsonObject.addProperty("userRutSinDv", rut.split("-")[0]);
        String requestJsonString = gson.toJson(requestJsonObject);
        EntityBuilder eb = EntityBuilder.create().setText(requestJsonString);
        String url = Config.get("BD_BASE_URL") + "/IntegracionVistaHermosa/restWs/permiso/create";
        String result = "";
        try {
            LOG.info("Request Object Built. Requesting Insert Solicitud");
            result = Request.Post(url).addHeader(HttpHeaders.CONTENT_TYPE, "application/json").addHeader("accessToken", Config.get("ACCESS_TOKEN"))
                    .body(eb.build()).execute().returnContent().asString();
            JsonObject resultJson = new JsonParser().parse(result).getAsJsonObject();
            if (resultJson.get("response").getAsString().equalsIgnoreCase("failed")) {
                LOG.error("Server Problem");
                request.setAttribute("styleClass", "alert alert-danger");
                request.setAttribute("message", "There is a problem witth the server: " + resultJson.get("message").getAsString());
                request.getRequestDispatcher("/WEB-INF/pages/permisos/create.jsp").forward(request, response);
            } else if (resultJson.get("response").getAsString().equalsIgnoreCase("success")) {
                if (resultJson.get("result").getAsString().equalsIgnoreCase("failed")) {
                    LOG.error("Params Problem");
                    request.setAttribute("styleClass", "alert alert-danger");
                    request.setAttribute("message", "Permiso Could not be created. " + resultJson.get("message").getAsString());
                    request.getRequestDispatcher("/WEB-INF/pages/permisos/create.jsp").forward(request, response);
                } else {
                    LOG.info("Correctly Created Permiso");
                    LOG.info("Attempting to create Documento");
                    int permisoId = resultJson.get("permisoId").getAsInt();
                    InputStream fileContent = filePart.getInputStream();
                    EntityBuilder eb2 = EntityBuilder.create()
                            .setStream(fileContent)
                            .setContentType(ContentType.APPLICATION_OCTET_STREAM);
                    String url2 = Config.get("BD_BASE_URL") + "/IntegracionVistaHermosa/restWs/documento/create?fileName="+fileName+"&solId="+permisoId;
                    String result2 = "";
                    result2 = Request.Post(url2).addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM).addHeader("accessToken", Config.get("ACCESS_TOKEN"))
                            .body(eb2.build()).execute().returnContent().asString();
                    LOG.info("Result: "+result2);
                    JsonObject resultJson2 = new JsonParser().parse(result2).getAsJsonObject();
                    if (resultJson2.get("response").getAsString().equalsIgnoreCase("failed")) {
                        LOG.error("Server Problem");
                        request.setAttribute("styleClass", "alert alert-danger");
                        request.setAttribute("message", "There is a problem witth the server: " + resultJson.get("message").getAsString());
                        request.getRequestDispatcher("/WEB-INF/pages/permisos/create.jsp").forward(request, response);
                    } else if (resultJson2.get("response").getAsString().equalsIgnoreCase("success")) {
                        if (resultJson2.get("result").getAsString().equalsIgnoreCase("failed")) {
                            LOG.error("Params Problem");
                            request.setAttribute("styleClass", "alert alert-danger");
                            request.setAttribute("message", "Permiso Could not be created. " + resultJson.get("message").getAsString());
                            request.getRequestDispatcher("/WEB-INF/pages/permisos/create.jsp").forward(request, response);
                        } else {
                            LOG.info("Document Creation Successful");
                            response.sendRedirect("listPermisos");
                        }
                    }
                }
            } else {
                throw new Exception("Strange Exception");
            }

        } catch (Exception ex) {
            LOG.error("Exception Creating Solicitud");
            request.setAttribute("styleClass", "alert alert-danger");
            request.setAttribute("message", "problem Creating Solicitud: " + ex.getLocalizedMessage());
            request.getRequestDispatcher("/WEB-INF/pages/permisos/create.jsp").forward(request, response);
        }
    }
}
