package searchengine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class for the search engine.
 */
public class Main {
/**
 * Read the data file that config.txt is pointing to.
 * PageManage class construcs pages based on the data file provided.
 * IndexManager class creates the index and ranks pages based on word frequency and score.
 * WebServer sets port to 8080 to boot up the server.
 */
    public static void main(String[] args) throws IOException {
        String filename = Files.readString(Paths.get("config.txt")).strip();
        PageManager.construct_pages(filename);
        IndexManager.indexBuilder(filename);
        IndexManager.indexRanker(IndexManager.getIndex());
        new WebServer(8080);


    }
}
