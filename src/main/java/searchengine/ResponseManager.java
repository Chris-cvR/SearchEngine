package searchengine;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.sun.net.httpserver.HttpExchange;

/**
 * This class provides a static method which returns nothing, in order to send a
 * response to a given HTTP request.
 */
public class ResponseManager {

    /**
     * This method is repsponsible for sending a response to the given HTTP request.
     *
     * @param io       the HTTP request/response object
     * @param code     the HTTP response code
     * @param mime     the MIME type of the response
     * @param response the response body, as an array of bytes
     */
    public static void respond(HttpExchange io, int code, String mime, byte[] response) {
        try {
            Charset CHARSET = StandardCharsets.UTF_8;
            io.getResponseHeaders()
                    .set("Content-Type", String.format("%s; charset=%s", mime, CHARSET));
            io.sendResponseHeaders(200, response.length);
            io.getResponseBody().write(response);
        } catch (Exception exception) {
        } finally {
            io.close();
        }
    }
}