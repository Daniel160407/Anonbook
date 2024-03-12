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
import org.anonbook.anonbook.manager.JsonArrayManager;
import org.anonbook.anonbook.request.AddPostRequest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.anonbook.anonbook.manager.ImageManager.getFileName;
import static org.anonbook.anonbook.manager.ImageManager.imageDecoder;

@WebServlet("/post")
@MultipartConfig
public class PostServlet extends HttpServlet {
    private final MySQLController mySQLController = new MySQLController();

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/json");
        String postTitle = request.getParameter("title");
        String fileName="";
        try {
            System.err.println("entered");
            Part filePart = request.getPart("image");
            fileName = getFileName(filePart);

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
        } catch (ServletException ignored) {

        }
        System.err.println("finished");

        Instant instant = Instant.now();
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        int year = zonedDateTime.getYear();
        int month = zonedDateTime.getMonthValue();
        int day = zonedDateTime.getDayOfMonth();
        int hour = zonedDateTime.getHour();
        int minute = zonedDateTime.getMinute();

        System.err.println("Works");
        mySQLController.addPost(new AddPostRequest(postTitle, fileName, year + "." + month + "." + day + "   " + hour + ":" + minute));

        List<String> base64Images = imageDecoder(getServletContext().getRealPath("/images"));

        PrintWriter printWriter = response.getWriter();
        printWriter.println(new ObjectMapper().writeValueAsString(JsonArrayManager.getMergedJsonNode(mySQLController, base64Images)));
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/json");

        List<String> base64Images = imageDecoder(getServletContext().getRealPath("/images"));

        PrintWriter printWriter = response.getWriter();
        printWriter.println(new ObjectMapper().writeValueAsString(JsonArrayManager.getMergedJsonNode(mySQLController, base64Images)));
    }
}