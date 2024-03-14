package org.anonbook.anonbook.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.anonbook.anonbook.dao.MySQLController;
import org.anonbook.anonbook.request.GetCommentsRequest;
import org.anonbook.anonbook.request.GetPostRequest;

import java.util.List;

public class JsonArrayManager {
    public static ObjectNode getMergedJsonPosts(MySQLController mySQLController, List<String> base64Images) throws JsonProcessingException {
        JsonNode data = new ObjectMapper().readTree(new ObjectMapper().writeValueAsString(mySQLController.getPosts()));
        JsonNode imageBase64 = new ObjectMapper().readTree(new ObjectMapper().writeValueAsString(base64Images));

        ObjectNode mergedJsonNode = new ObjectMapper().createObjectNode();

        mergedJsonNode.set("data", data);
        mergedJsonNode.set("imageBase64", imageBase64);

        return mergedJsonNode;
    }

    public static ObjectNode getSinglePost(MySQLController mySQLController, List<String> base64Image, GetPostRequest getPostRequest) throws JsonProcessingException {
        JsonNode data = new ObjectMapper().readTree(new ObjectMapper().writeValueAsString(mySQLController.getPost(getPostRequest)));
        JsonNode comments = new ObjectMapper().readTree(new ObjectMapper().writeValueAsString(mySQLController.getComments(new GetCommentsRequest(getPostRequest.postId()))));

        ObjectNode mergedJsonNode = new ObjectMapper().createObjectNode();

        mergedJsonNode.set("data", data);
        mergedJsonNode.set("comments", comments);

        try {
            JsonNode imageBase64 = new ObjectMapper().readTree(new ObjectMapper().writeValueAsString(base64Image.get(getPostRequest.postId() - 1)));
            mergedJsonNode.set("imageBase64", imageBase64);
        } catch (IndexOutOfBoundsException ignored) {

        }

        return mergedJsonNode;
    }
}
