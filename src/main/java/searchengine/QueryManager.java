package searchengine;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;

/**
 * This class manages search queries and provides a method for searching for
 * pages matching a given query.
 */
public class QueryManager {

  /**
   * Searches for pages matching the given query and sends a response to the 
   * front-end through the ResponseManager class. The response
   * is a list of JSON objects, each containing the URL and title of a matching
   * page.
   *
   * @param io the HttpExchange object used to send the response.
   */
  public static void search(HttpExchange io) {
    Charset CHARSET = StandardCharsets.UTF_8;
    String query = io.getRequestURI().getQuery().split("=")[1];

    List<String> response = new ArrayList<String>();

    HashSet<Integer> query_index_matches = SearchEngine.lookUp(query);

    for (Integer page_index : query_index_matches) {
      String URL = PageManager.getPages().get(page_index).getURL();
      String Title = PageManager.getPages().get(page_index).getTitle();

      response.add(String.format("{\"url\": \"%s\", \"title\": \"%s\"}", URL, Title));
    }

    byte[] bytes = response.toString().getBytes(CHARSET);
    ResponseManager.respond(io, 200, "application/json", bytes);
  }
}
