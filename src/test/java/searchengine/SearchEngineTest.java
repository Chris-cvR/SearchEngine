package searchengine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashSet;

/**
 * This class tests the search engine class by checking queries with the "or" operator
 * no "or" operator and an empty query. 
 */
class SearchEngineTest {
  
     /**
     * Sets up the test by initializing the page manager and index manager with
     * the test data file.
     * 
     * @throws IOException if an I/O error occurs while reading the test data file
     */
    @BeforeEach
    void setUp() throws IOException {
        String filename = "data/test-file.txt";
        PageManager.construct_pages(filename);
        IndexManager.indexBuilder(filename);
    }

     /**
     * Tests the SearchEngine lookUp(String) method with three different instances.
     */
    @Test
    void testLookUp() {
      // Test with query containing the "or" operator
      HashSet<Integer> response = SearchEngine.lookUp("word1 or word2");
      assertEquals(response.contains(0),true);
      assertEquals(response.contains(1),true);
      assertEquals(response.contains(3),false);
      
      // Test with query containing no "or" operator
      response = SearchEngine.lookUp("word1 word2");
      assertEquals(response.contains(0),true);
      assertEquals(response.contains(1),false);
      assertEquals(response.contains(2),false);
      
      // Test with empty query
      response = SearchEngine.lookUp("");
      assertEquals(response.isEmpty(),true);
    }
  }