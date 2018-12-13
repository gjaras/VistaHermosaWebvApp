/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.grupo2.vhwebapp.servlets.solicitudes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author gabriel.jara
 */
@WebServlet(name = "PdfViewServlet", urlPatterns = {"/verPdf"})
public class PdfViewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        File file = new File("SolicitudN64.pdf");
//        System.out.println(file.getAbsolutePath());
//        response.setHeader("Content-Type", getServletContext().getMimeType(file.getName()));
//        response.setHeader("Content-Length", String.valueOf(file.length()));
//        response.setHeader("Content-Disposition", "inline; filename=\"SolicitudN64.pdf\"");
//        Files.copy(file.toPath(), response.getOutputStream());

        String pdfFileName = "SolicitudN64.pdf";
        File pdfFile = new File(pdfFileName);
        System.out.println(pdfFile.getAbsolutePath());
        response.setContentType("application/pdf");
        response.addHeader("Content-Disposition", "inline; filename=" + pdfFileName);
        response.setContentLength((int) pdfFile.length());

        FileInputStream fileInputStream = new FileInputStream(pdfFile);
        OutputStream responseOutputStream = response.getOutputStream();
        int bytes;
        while ((bytes = fileInputStream.read()) != -1) {
            responseOutputStream.write(bytes);
        }
    }

}
