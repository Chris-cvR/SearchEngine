package searchengine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class tests the functionality of the Page class.
 */
public class PageTest {
    /**
     * Tests the constructor of the Page class.
     */
    @Test
    public void testConstructor() {
        String url = "https://www.example.com";
        String title = "Example Page";
        List<String> keywords = Arrays.asList("example", "page", "test");

        Page page = new Page(url, title, keywords);
        assertEquals(url, page.getURL());
        assertEquals(title, page.getTitle());
        assertEquals(keywords, page.getKeywords());
    }

    /**
     * Tests the Page.toString() method of the Page class.
     */
    @Test
    public void testToString() {
        String url = "https://www.example.com";
        String title = "Example Page";
        List<String> keywords = Arrays.asList("example", "page", "test");

        Page page = new Page(url, title, keywords);
        assertEquals("Page has url: https://www.example.com, title: Example Page, keywords: [example, page, test]", page.toString());
    }

   /**
     * Tests the Page.equals(Page) method of the Page class
     * Tests if two pages have the same URL, title, and keywords.
     */
    @Test
    public void testEqualPages() {
        String url = "https://www.example.com";
        String title = "Example Domain";
        List<String> keywords = new ArrayList<>();
        keywords.add("example");
        keywords.add("domain");

        Page page1 = new Page(url, title, keywords);
        Page page2 = new Page(url, title, keywords);

        assertEquals(page1.equals(page2), true);
    }

  /**
     * Tests the Page.equals(Page) method of the Page class
     * Tests if two pages have different URLs.
     */
    @Test
    public void testPagesWithDifferentURLs() {
        String url1 = "https://www.example.com";
        String url2 = "https://www.example.net";
        String title = "Example Domain";
        List<String> keywords = new ArrayList<>();
        keywords.add("example");
        keywords.add("domain");

        Page page1 = new Page(url1, title, keywords);
        Page page2 = new Page(url2, title, keywords);

        assertEquals(page1.equals(page2), false);
    }

  /**
     * Tests the Page.equals(Page) method of the Page class
     * Test if two pages have different titles.
     */
    @Test
    public void testPagesWithDifferentTitles() {
        String url = "https://www.example.com";
        String title1 = "Example Domain";
        String title2 = "Example Website";
        List<String> keywords = new ArrayList<>();
        keywords.add("example");
        keywords.add("domain");

        Page page1 = new Page(url, title1, keywords);
        Page page2 = new Page(url, title2, keywords);

        assertEquals(page1.equals(page2), false);
    }

    /**
     * Tests the Page.equals(Page) method of the Page class
     * Tests if two pages have different keywords.
     */

    @Test
    public void testPagesWithDifferentKeywords() {
        String url = "https://www.example.com";
        String title = "Example Domain";
        List<String> keywords1 = new ArrayList<>();
        keywords1.add("example");
        keywords1.add("domain");
        List<String> keywords2 = new ArrayList<>();
        keywords2.add("example");
        keywords2.add("website");

        Page page1 = new Page(url, title, keywords1);
        Page page2 = new Page(url, title, keywords2);

        assertEquals(page1.equals(page2), false);
    }

    /**
     * Tests the Page.equals(Page) method of the Page class.
     * Tests if one of the objects being compared is null.
     */
    @Test
    public void testPageWithNullObject() {
        String url = "https://www.example.com";
        String title = "Example Domain";
        List<String> keywords = new ArrayList<>();
        keywords.add("example");
        keywords.add("domain");

        Page page = new Page(url, title, keywords);

        assertEquals(page.equals(null), false);
        }

    /**
     * Tests the Page.equals(Page) method of the Page class.
     * Tests if the object being compared is of a different type.
     */
        @Test
        public void testPageWithDifferentObjectType() {
            String url = "https://www.example.com";
            String title = "Example Domain";
            List<String> keywords = new ArrayList<>();
            keywords.add("example");
            keywords.add("domain");

            Page page = new Page(url, title, keywords);

             assertEquals(page.equals("https://www.example.com"), false);
        }

    /**
     * Tests the Page.hashCode() method of the Page class.
     * Tests whether two Page ojects have the same hashcode based on changing some variables.
     */
    @Test
    public void testHashCode() {

        // Different URL
        List<String> keywords = Arrays.asList("keyword1", "keyword2", "keyword3");
        Page page1 = new Page("https://www.test1.com", "Page 1", keywords);
        Page page2 = new Page("https://www.test2.com", "Page 1", keywords);

        assertNotEquals(page1.hashCode(), page2.hashCode());

        // Different Title
        Page page3 = new Page("https://www.test1.com", "Page 1", keywords);
        Page page4 = new Page("https://www.test1.com", "Page 2", keywords);

        assertNotEquals(page3.hashCode(), page4.hashCode());

        // Different keywords
        Page page5 = new Page("https://www.test1.com", "Page 1", keywords);
        List<String> keywords_t2 = Arrays.asList("keyword4", "keyword5", "keyword6");
        Page page6 = new Page("https://www.test1.com", "Page 1", keywords_t2);

        assertNotEquals(page5.hashCode(), page6.hashCode());

        // Same page
        Page page7 = new Page("https://www.test1.com", "Page 1", keywords);
        Page page8 = new Page("https://www.test1.com", "Page 1", keywords);

        assertEquals(page7.hashCode(), page8.hashCode());

    }

}