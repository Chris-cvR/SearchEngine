package searchengine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * This class represents a manager for the inverted index for a set of pages. The index maps
 * keywords to the pages that contain them. The index can be built and ranked
 * using the methods in this class.
 */
public class IndexManager {

  /**
   * This field stores the inverted index as a map from keywords to lists of
   * page indices.
   */
  private static Map<String, List<Integer>> index;

  /**
   * This field stores the list of pages that the index is built from.
   */
  private static List<Page> pages = PageManager.getPages();



  /**
   * This method builds the inverted index from the list of pages.
   *
   * @param filename The path to the data that you want to build an index with
   * @throws IOException when there is an error reading the file.
   */
  public static void indexBuilder(String filename) throws IOException {
    index = new HashMap<String, List<Integer>>();
    String keyword;

    Map<String, Integer> pageMap = new HashMap<>();

    if (pages.size() > 0) {

      for (Page page : pages) {
        pageMap.put(page.getTitle(), pages.indexOf(page));
      }

      System.out.println("Building inverted index..");
      for (Page page : pages) {
        List<String> keywords = page.getKeywords();
        Iterator<String> it = keywords.iterator();
        while (it.hasNext()) {
          keyword = it.next();
          index.computeIfAbsent(keyword, k -> new ArrayList<Integer>()).add(pageMap.get(page.getTitle()));
        }
      }
      System.out.println("Inverted index built! ");
    }

  }

  /**
   * This method ranks the inverted index according to the relevance of the
   * pages to the keywords, using the Ranker class.
   *
   * @param index The inverted index to rank.
   */

  public static void indexRanker(Map<String, List<Integer>> index) {
    System.out.println("Ranking index..");
    for (String keyword : index.keySet()){
      List<Integer> ranked_indices = Ranker.sort_index_by_relevance(index.get(keyword).stream());
      index.put(keyword, ranked_indices);
    }
    System.out.println("Index ranked!");
  }

  /**
 * This method returns the inverted index.
 *
 * @return The inverted index.
 */
  public static Map<String, List<Integer>> getIndex() {
    return index;
  }
}