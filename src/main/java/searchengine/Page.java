package searchengine;

import java.util.List;
import java.util.Objects;

/**
 * This class represents a single web page. A page has a URL, a title, and a
 * list of keywords.
 */
public class Page {

    private String URL;
    private String title;
    private List<String> keywords;

    /**
     * Constructs a new Page object with the given URL, title, and keywords.
     *
     * @param URL      The URL of the page.
     * @param title    The title of the page.
     * @param keywords The keywords associated with the page.
     */

    public Page(String URL, String title, List<String> keywords) {
        this.URL = URL;
        this.title = title;
        this.keywords = keywords;
    }

    /**
     * Returns a string representation of the page.
     *
     * @return A string representation of the page.
     */
    public String toString() {
        return "Page has url: " + this.URL + ", title: " + this.title + ", keywords: " + this.keywords;
    }

    /**
     * Returns the URL of the page.
     *
     * @return The URL of the page.
     */
    public String getURL() {
        return this.URL;

    }

    /**
     * Returns the title of the page.
     *
     * @return The title of the page.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Returns the keywords associated with the page.
     *
     * @return The keywords associated with the page.
     */
    public List<String> getKeywords() {
        return this.keywords;
    }

    /**
     * Indicates whether some other object is equal to this page.
     *
     * @param object is the reference object with which to compare.
     * @return true if this object is the same as the page argument; false
     *         otherwise.
     */
    @Override
    public boolean equals(Object target) {
        if (target == null) {
            return false;
        }

        if (target.getClass() != this.getClass()) {
            return false;
        }

        final Page test = (Page) target;

        // URL comparison
        if (!this.URL.equals(test.URL)) {
            return false;
        }

        // Title comparison
        if (!this.title.equals(test.title)) {
            return false;
        }

        // Keyword comparison
        if (!this.keywords.equals(test.keywords)) {
            return false;
        }

        return true;

    }

    /**
     * Returns the hash code value for this page
     *
     * @return the hash code value for this page
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.URL);
        hash = 11 * hash + Objects.hashCode(this.title);
        hash = 11 * hash + Objects.hashCode(this.keywords);
        return hash;
    }

}
