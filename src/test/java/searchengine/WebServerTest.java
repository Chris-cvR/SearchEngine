
package searchengine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.TestInstance.Lifecycle;

import java.net.HttpURLConnection;
import java.net.URL;


import java.io.IOException;
import java.net.BindException;
import java.util.Random;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(Lifecycle.PER_CLASS)
/**
 * This class tests the WebServer by starting the server,
 * making requests to it, and checking the responses.
 */
class WebServerTest {
    /**
     * This field holds the instance of the WebServer used for testing.
     */
    WebServer server = null;

    /**
     * This method starts the server by creating a new instance of
     * the WebServer class and binding it to a random port.
     */
    @BeforeAll
    void setUp() {
        try {
            var rnd = new Random();
            while (server == null) {
                try {
                    server = new WebServer(rnd.nextInt(60000) + 1024);
                } catch (BindException e) {
                    // port in use. Try again
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method stops the server.
     */
    @AfterAll
    void tearDown() {
        server.server.stop(0);
        server = null;
    }

    /**
     * This method tests whether the server is running by making
     * a GET request to the server's URL and checking the
     * response code.
     */
    @Test
    void lookupWebServer() throws IOException {
        URL url = new URL("http://localhost:" + server.server.getAddress().getPort());

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        assertEquals(connection.getResponseCode(), 200);
    }


    /**
     * This method tests whether the server can serve CSS files
     * by making a GET request to the server's /style.css URL
     * and checking the response code.
     */
    @Test
    void lookupCSS() throws IOException {
        URL url = new URL("http://localhost:" + server.server.getAddress().getPort() + "/style.css");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        assertEquals(connection.getResponseCode(), 200);
    }


    /**
     * This method tests whether the server can serve JavaScript
     * files by making a GET request to the server's /code.js
     * URL and checking the response code.
     */
    @Test
    void lookupJS() throws IOException {
        URL url = new URL("http://localhost:" + server.server.getAddress().getPort() + "/code.js");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        assertEquals(connection.getResponseCode(), 200);
    }


    /**
     * This method tests whether the server can serve the
     * favicon file by making a GET request to the server's /favicon.ico
     * URL and checking the response code.
     */
    @Test
    void lookupFavIcon() throws IOException {
        URL url = new URL("http://localhost:" + server.server.getAddress().getPort() + "/favicon.ico");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        assertEquals(connection.getResponseCode(), 200);
    }
}