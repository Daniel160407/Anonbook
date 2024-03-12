package org.anonbook.anonbook.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.anonbook.anonbook.dao.MySQLController;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static org.anonbook.anonbook.manager.ImageManager.getFileName;
import static org.anonbook.anonbook.manager.ImageManager.imageDecoder;

@WebServlet("/post")
@MultipartConfig
public class PostServlet extends HttpServlet {
    private final MySQLController mySQLController = new MySQLController();

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json");
        String postTitle = request.getParameter("title");

        Part filePart = request.getPart("image");
        String fileName = getFileName(filePart);

        InputStream fileContent = filePart.getInputStream();
        String imagesPackagePath = getServletContext().getRealPath("/images") + "\\";

        FileOutputStream outputStream = new FileOutputStream(imagesPackagePath + fileName);
        int read;
        final byte[] bytes = new byte[1024];
        while ((read = fileContent.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }
        outputStream.close();
        fileContent.close();

        List<String> base64Images = imageDecoder(getServletContext().getRealPath("/images"));

        PrintWriter printWriter = response.getWriter();
        printWriter.println(new ObjectMapper().writeValueAsString(base64Images));
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/json");

        List<String> base64Images = imageDecoder(getServletContext().getRealPath("/images"));

        PrintWriter printWriter = response.getWriter();
        printWriter.println(new ObjectMapper().writeValueAsString(base64Images));
    }
}