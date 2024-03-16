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
import org.anonbook.anonbook.request.AddPostRequest;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.anonbook.anonbook.manager.ImageManager.*;
import static org.anonbook.anonbook.manager.JsonArrayManager.getMergedJsonPosts;

@WebServlet("/post")
@MultipartConfig
public class PostServlet extends HttpServlet {
    private final MySQLController mySQLController = new MySQLController();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/json");

        List<String> base64Images = imageEncoder(getServletContext().getRealPath("/images"));

        PrintWriter printWriter = response.getWriter();
        printWriter.println(new ObjectMapper().writeValueAsString(getMergedJsonPosts(mySQLController, base64Images)));
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/json");
        String postTitle = request.getParameter("title");
        String fileName = "";
        try {
            Part filePart = request.getPart("image");
            fileName = getFileName(filePart);

            imageDecoder(getServletContext().getRealPath("/images") + "\\", filePart, fileName);
        } catch (ServletException ignored) {

        }

        Instant instant = Instant.now();
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        int year = zonedDateTime.getYear();
        int month = zonedDateTime.getMonthValue();
        int day = zonedDateTime.getDayOfMonth();
        int hour = zonedDateTime.getHour();
        int minute = zonedDateTime.getMinute();

        mySQLController.addPost(new AddPostRequest(postTitle, fileName, year + "." + month + "." + day + "   " + hour + ":" + minute));

        List<String> base64Images = imageEncoder(getServletContext().getRealPath("/images"));

        PrintWriter printWriter = response.getWriter();
        printWriter.println(new ObjectMapper().writeValueAsString(getMergedJsonPosts(mySQLController, base64Images)));
    }
}