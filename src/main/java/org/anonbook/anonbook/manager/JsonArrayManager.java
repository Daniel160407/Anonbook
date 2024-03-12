package org.anonbook.anonbook.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.anonbook.anonbook.dao.MySQLController;

import java.util.List;

public class JsonArrayManager {
    public static ObjectNode getMergedJsonNode(MySQLController mySQLController, List<String> base64Images) throws JsonProcessingException {
        JsonNode data = new ObjectMapper().readTree(new ObjectMapper().writeValueAsString(mySQLController.getPosts()));
        JsonNode imageBase64 = new ObjectMapper().readTree(new ObjectMapper().writeValueAsString(base64Images));


        ObjectNode mergedJsonNode = new ObjectMapper().createObjectNode();

        mergedJsonNode.set("data", data);
        mergedJsonNode.set("imageBase64", imageBase64);

        return mergedJsonNode;
    }
}
