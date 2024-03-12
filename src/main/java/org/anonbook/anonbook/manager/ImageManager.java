package org.anonbook.anonbook.manager;

import jakarta.servlet.http.Part;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ImageManager {
    public static String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");

        for (String content : partHeader.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    public static List<String> imageDecoder(String path) throws IOException {
        List<String> base64Images = new ArrayList<>();
        File[] files = new File(path).listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isFile()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] data = new byte[(int) file.length()];
                fileInputStream.read(data);
                fileInputStream.close();
                String base64Image = Base64.getEncoder().encodeToString(data);
                base64Images.add(base64Image);
            }
        }

        return base64Images;
    }
}