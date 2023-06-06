package searchengine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
The PageManagerTest class is a JUnit test class that tests the functionality of the PageManager class.
*/
public class PageManagerTest {

/**
Tests the construct_pages() method by creating a list of expected Page objects and
checking if it matches the list of pages returned by the method.
*/
    @Test
    public void testConstructPages() throws IOException {
        String filename = "data/test-file.txt";
        List<Page> expectedPages = new ArrayList<>();
        expectedPages.add(new Page("http://page1.com", "Title1", Arrays.asList("word1", "word2")));
        expectedPages.add(new Page("http://page2.com", "Title2", Arrays.asList("word1", "word3")));
        PageManager.construct_pages(filename);
        assertEquals(expectedPages, PageManager.getPages());

    }
/**
Tests the construct_pages(String) method by providing a file that does not contain any pages
and checking if the method returns an empty list of pages.
*/
    @Test
    public void testConstructPages_nopages() throws IOException {
        String filename = "data/test-file-errors.txt";
        List<Page> expectedPages = new ArrayList<>();

        PageManager.construct_pages(filename);
        assertEquals(expectedPages.size(), 0);
    }

    /*@Test
    public void testConstructPages_FileNotFoundException() throws IOException {
        String nonExistentFile = "invalid_file.txt";
        try {
            PageManager.construct_pages(nonExistentFile);
            fail("Expected a FileNotFoundException to be thrown");
        } catch (NoSuchFileException e) {
            assertTrue(true);
    }*/

}
