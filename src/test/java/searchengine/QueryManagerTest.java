package searchengine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.BindException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Random;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.TestInstance.Lifecycle;


@TestInstance(Lifecycle.PER_CLASS)
/**
 * Test class for the QueryManager class.
 */

public class QueryManagerTest {

    /**
     * This field holds the instance of the WebServer used for testing.
     */
    WebServer server = null;

    @BeforeEach
    /**
     * This method is run before each test in the class.
     * It initializes the server and constructs the pages, index, and ranking.
     */
    void setUp() {
        try {
            var rnd = new Random();
            while (server == null) {
                try {
                    String filename = "data/test-file.txt";
                    PageManager.construct_pages(filename);
                    IndexManager.indexBuilder(filename);
                    IndexManager.indexRanker(IndexManager.getIndex());            
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
     * This method is run after all the tests are finished, then it stops the server.
     */
    @AfterAll
    void tearDown() {
        server.server.stop(0);
        server = null;
    }

    /**
     * This test checks the response of the web server when it receives a query.
     * It sends HTTP GET requests to the server with various query parameters and checks if the response matches the expected result.
     */
    @Test
    void lookupWebServer() {
        String baseURL = String.format("http://localhost:%d/search?q=", server.server.getAddress().getPort());
        assertEquals("[{\"url\": \"http://page1.com\", \"title\": \"Title1\"}, {\"url\": \"http://page2.com\", \"title\": \"Title2\"}]", 
            httpGet(baseURL + "word1"));
        assertEquals("[{\"url\": \"http://page1.com\", \"title\": \"Title1\"}]",
            httpGet(baseURL + "word2"));
        assertEquals("[{\"url\": \"http://page2.com\", \"title\": \"Title2\"}]", 
            httpGet(baseURL + "word3"));
        assertEquals("[]", 
            httpGet(baseURL + "word4"));
    }


    /**
     * Helper method that sends an HTTP GET request to the specified URL and returns the response as a string.
     * @param url The URL to which the GET request will be sent.
     * @return The response of the GET request as a string.
     */
    private String httpGet(String url) {
        var uri = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder().uri(uri).GET().build();
        try {
            return client.send(request, BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
