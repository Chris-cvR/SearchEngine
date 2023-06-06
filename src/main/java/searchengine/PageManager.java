package searchengine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class constructs and manages a list of Page objects. The class
 * has methods to constructing the list of pages and
 * to access the list.
 */
public class PageManager {

    private static List<Page> pages;



    /**
     * Constructs a list of Page objects from the specified file.
     *
     * @param filename the name of the file to read from
     * @throws IOException if an I/O error occurs while reading the file
     */
    public static void construct_pages(String filename) throws IOException {
        System.out.println("Constructing pages..");
        Map<String, List<String>> data_map = new TreeMap<>();
        pages = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            Iterator<String> it = lines.iterator();
            String last_url = "";

            while (it.hasNext()) {
                String next_line = it.next();
                if (next_line.startsWith("*PAGE")) {
                    last_url = next_line.split("\\*PAGE:")[1];
                    List<String> keyword_holder = new ArrayList<>();
                    data_map.put(last_url, keyword_holder);
                } else {
                    if (data_map.size() > 0) {
                        data_map.get(last_url).add(next_line);
                    }
                    // There is no URL yet, so ignore these words.
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        try {
            for (String key : data_map.keySet()) {
                if (Character.isUpperCase(data_map.get(key).get(0).charAt(0))) {
                    String title = data_map.get(key).get(0);
                    data_map.get(key).remove(0);
                    if (data_map.get(key).size() > 0) {
                        pages.add(new Page(key, title, data_map.get(key)));
                    }
                }
            }
            if (pages.size() > 0) {
                System.out.println("Page construction finished!");

            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No data in data_map - can not construct pages.");
        }
    }

    /**
     * Returns the list of Page objects.
     *
     * @return the list of pages
     */
    public static List<Page> getPages() {
        return pages;
    }

    public static void setPages(List<Page> new_pages) {
        pages = new_pages;
    }

}
