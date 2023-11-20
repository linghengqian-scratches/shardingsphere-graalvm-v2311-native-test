package com.lingh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileTestUtils {
    
    /**
     * read file From file URL string.
     * @param fileUrl fileUrl
     * @return byte array
     */
    public static byte[] readFromFileURLString(final String fileUrl) {
        return readInputStream(ClassLoader.getSystemResourceAsStream(fileUrl)).getBytes(StandardCharsets.UTF_8);
    }
    
    private static String readInputStream(final InputStream is) {
        StringBuilder out = new StringBuilder();
        try (
                InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(streamReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
                // ShardingSphere does not actively handle line separators when parsing YAML and needs to be actively added.
                out.append(System.lineSeparator());
            }
        } catch (IOException e) {
            Logger.getLogger(FileTestUtils.class.getName()).log(Level.SEVERE, null, e);
        }
        return out.toString();
    }
}
