package com.artenesnogueira.bakingapp.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Utility to get a response from a HTTP server.
 */
public class HttpClient {

    private static final int TIMEOUT = 5000;

    /**
     * Get the raw response from a server as a String.
     *
     * @param url the url to request.
     * @return the response.
     * @throws IOException in case the connection failed or there is no response.
     */
    public String get(String url) throws IOException {

        HttpURLConnection connection = (HttpURLConnection) (new URL(url)).openConnection();
        connection.setConnectTimeout(TIMEOUT);

        try (InputStream inputStream = connection.getInputStream()) {

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            if (!scanner.hasNext()) {
                throw new IOException("No response returned from server");
            }

            return scanner.next();

        }

    }

}