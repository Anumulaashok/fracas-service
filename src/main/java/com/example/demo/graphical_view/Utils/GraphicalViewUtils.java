package com.example.demo.graphical_view.Utils;


import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The type Graphical view utils.
 */
@Component
public class GraphicalViewUtils {
    /**
     * Generate unique hash id string.
     *
     * @param values the values
     * @return the string
     */
    public static String generateUniqueHashId(String... values) {
        try {
            // Concatenate all the values into a single string
            StringBuilder concatenatedString = new StringBuilder();
            for (String value : values) {
                concatenatedString.append(value);
            }

            // Hash the concatenated string using SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(concatenatedString.toString().getBytes(StandardCharsets.UTF_8));

            // Convert the hash bytes to a hexadecimal string representation
            StringBuilder hashIdBuilder = new StringBuilder();
            for (byte b : hashBytes) {
                hashIdBuilder.append(String.format("%02x", b));
            }

            // Return the unique hash ID
            return hashIdBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle any exceptions related to hashing algorithm
            e.printStackTrace();
            return null;
        }
    }
}
