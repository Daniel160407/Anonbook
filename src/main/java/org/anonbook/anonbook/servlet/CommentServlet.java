package org.anonbook.anonbook.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.anonbook.anonbook.dao.MySQLController;
import org.anonbook.anonbook.request.GetCommentsRequest;
import org.anonbook.anonbook.request.GetPostRequest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static org.anonbook.anonbook.manager.ImageManager.imageEncoder;
import static org.anonbook.anonbook.manager.JsonArrayManager.getSinglePost;

@WebServlet("/comment")
public class CommentServlet extends HttpServlet {
    private final MySQLController mySQLController = new MySQLController();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/json");
        Integer postId = Integer.valueOf(request.getParameter("postId"));

        List<String> base64Images = imageEncoder(getServletContext().getRealPath("/images"));

        PrintWriter printWriter = response.getWriter();
        printWriter.println(new ObjectMapper().writeValueAsString(getSinglePost(mySQLController, base64Images, new GetPostRequest(postId))));
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/json");
        int postId = Integer.parseInt(request.getParameter("postId"));
        String comment = request.getParameter("comment");

        mySQLController.addComment(postId, comment);

        PrintWriter printWriter = response.getWriter();
        printWriter.println(new ObjectMapper().writeValueAsString(mySQLController.getComments(new GetCommentsRequest(postId))));
    }
}
