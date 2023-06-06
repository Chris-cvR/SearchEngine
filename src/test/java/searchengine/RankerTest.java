package searchengine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;


/**
 * This class is a test for the Ranker class.
 */
public class RankerTest {

    @BeforeEach
    /**
     * Sets up the test by creating a new instance of the PageManager} and IndexManager classes using a specified test file.
     * This method is called before each test is run.
     *
     * @throws IOException if there is a error reading the test file
     */
    void setUp() throws IOException {
    String filename = "data/test-file-ranker.txt";
    PageManager.construct_pages(filename);
    IndexManager.indexBuilder(filename);
    }

    /**
     * Tests the Ranker sort_index_by_relevance() method to ensure it correctly sorts a stream of indices by their relevance.
     * It checks the method's output against expected results for two test cases.
     */
    @Test
    public void testSortIndexByRelevance() {
        List<Integer> test_list = new ArrayList<>();
        test_list.add(0);
        test_list.add(1);
        test_list.add(2);
        test_list.add(0);
        test_list.add(1);
        test_list.add(3);
        test_list.add(3);

        var stream = test_list.stream();

        LinkedList<Integer> expected1 = new LinkedList<>();
        expected1.add(0);
        expected1.add(1);
        expected1.add(3);
        expected1.add(2);
        

        assertEquals(Ranker.sort_index_by_relevance(stream), expected1);

        Stream<Integer> elements2 = Stream.of(1, 1, 1, 1, 1);
        LinkedList<Integer> expected2 = new LinkedList<>();
        expected2.add(1);
        assertEquals(expected2, Ranker.sort_index_by_relevance(elements2));

    }

}
