package searchengine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * JUnit test class for the IndexManager class.
 */
public class IndexManagerTest {
    
    /**
     * Tests the IndexManager indexBuilder(String) method by providing it with
     * a file containing a non existent input file.
     * 
     * @throws IOException if an I/O error occurs while reading the test data file
     */
    @Test
    public void indexFailTest() throws IOException {
        String filename = "data/test-file-errors.txt";
        PageManager.construct_pages(filename);
        IndexManager.indexBuilder(filename);

    }
}
