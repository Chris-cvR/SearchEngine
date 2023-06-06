package searchengine;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * This class provides a method to search for a given query in the
 * inverted index.
 */
public class SearchEngine {

  /**
   * Searches the inverted index for the given query.
   *
   * @param query the search query provided as input on the frontend
   * @return a set of page IDs that match the query
   */

  public static HashSet<Integer> lookUp(String query) {

    Boolean operator_check;
    LinkedHashSet<Integer> final_response = new LinkedHashSet<Integer>();
    HashSet<Integer> full_query_response;
    List<String> query_words;

    try {
      query = query.toLowerCase();

      if (query.contains(" or ")) {
        operator_check = true;
        query_words = Arrays.asList(query.split(" or "));
      } else {
        operator_check = false;
        query_words = Arrays.asList(query.split(" "));
      }

      if (operator_check == true) {
        for (String entry : query_words) {
          List<String> word_holder = Arrays.asList(entry.split(" "));
          full_query_response = new LinkedHashSet<Integer>(
              IndexManager.getIndex().get(word_holder.get(0).toLowerCase()));
          for (String word : word_holder) {
            full_query_response.retainAll(IndexManager.getIndex().get(word));
          }
          final_response.addAll(full_query_response);
        }
      } else {
        full_query_response = new LinkedHashSet<Integer>(IndexManager.getIndex().get(query_words.get(0).toLowerCase()));

        if (query_words.size() > 1) {
          for (String entry : query_words) {
            full_query_response.retainAll(IndexManager.getIndex().get(entry));
          }
          final_response.addAll(Ranker.sort_query_by_relevance(query_words, full_query_response));
        } else {
          for (String entry : query_words) {
            full_query_response.retainAll(IndexManager.getIndex().get(entry));
          }
          final_response.addAll(full_query_response);
        }
      }
    } catch (NullPointerException e) {
      return final_response;
    }
    return final_response;
  }
}