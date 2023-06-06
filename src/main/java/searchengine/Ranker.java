package searchengine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class provides methods for ranking pages, one
 * for the inverted index, and one for a query 
 */

public class Ranker {
    private static Map<Integer, Double> score_map;
    /**
     * Sorts the given stream of page indices based on their relevance 
     * The page indices are sorted in descending order, with the most relevant pages appearing first.
     *
     * @param elements The stream of page indices to sort.
     * @return A linked list of the sorted page indices.
     */
    public static LinkedList<Integer> sort_index_by_relevance(Stream<Integer> elements) {
        LinkedHashMap<Integer, Long> occurrence_map = elements.collect(Collectors.groupingBy(
                Function.identity(),
                Collectors.counting())).entrySet().stream()

                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (index, occurrence) -> index, LinkedHashMap::new));

        List<Page> pages = PageManager.getPages();

        Map<Integer, Double> ScoreMap = new TreeMap<>();


        for (Integer index : occurrence_map.keySet()) {

            Integer keyword_size = pages.get(index).getKeywords().size();
            Double keyword_size_double = keyword_size.doubleValue();
            Double count = occurrence_map.get(index).doubleValue();

            double tf = count / keyword_size_double;
            double idf = Math.log10((pages.size() / occurrence_map.size())) + 0.01;
            double tfidf = tf * idf;
            ScoreMap.put(index, tfidf);
            score_map = ScoreMap;

            //double tf = (occurrence_map.get(index).doubleValue() / pages.get(index).getKeywords().size());
            //double idf = Math.log10((pages.size()/occurrence_map.keySet().size()));
            //double score = tf*idf; 
            //ScoreMap.put(index, score); 
        }

        LinkedHashMap<Integer, Double> sorted_scores = ScoreMap.entrySet() // This is where the scores are ordered from
                                                                           // high to low
                .stream()
                .sorted(Map.Entry.<Integer, Double>comparingByValue())
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (index, score) -> index, LinkedHashMap::new));

        LinkedList<Integer> sorted_indices = new LinkedList<>(sorted_scores.keySet());

        return sorted_indices; 
    }


    /**
     * Sorts the resulting pages by their relevance, based on the given query.
     * The page indices are sorted in descending order, with the most relevant pages appearing first.
     * 
     * 
     * @param query_words a list of words that the pages should be sorted by relevance to
     * @param results_indices a set of indices that specify which pages to include in the sorting
     * @return a linked list of indices for the pages, sorted in descending order by relevance
     */
    public static LinkedList<Integer> sort_query_by_relevance(List<String> query_words, Set<Integer> results_indices) {
        List<Page> all_pages = new ArrayList<>(PageManager.getPages());
        List<Page> pages = new ArrayList<>();
        Integer count = 0;
        Integer total_words = 0;
        Map<Integer, Double> score_map = new HashMap<Integer, Double>();

        for (Integer index : results_indices) {
            pages.add(all_pages.get(index));
        }

        for (String word : query_words) {
            for (Page page : pages) {
                count = 0;
                total_words = 0;
                count = count + Collections.frequency(page.getKeywords(), word);
                total_words = page.getKeywords().size();

                Integer keyword_size = total_words;
                Double keyword_size_double = keyword_size.doubleValue();
                Double count_double = count.doubleValue();

                double tf = count_double / keyword_size_double;
                double idf = Math.log10((pages.size() / results_indices.size())) + 0.01;
                double tfidf = tf * idf;

                if (score_map.containsKey(pages.indexOf(page))) {
                    score_map.put(all_pages.indexOf(page), score_map.get(pages.indexOf(page)) + tfidf);
                } else {
                    score_map.put(all_pages.indexOf(page), tfidf);
                }
            }
        }

        LinkedHashMap<Integer, Double> sorted_scores = score_map.entrySet() // This is where the scores are ordered from
                                                                            // high to low
                .stream()
                .sorted(Map.Entry.<Integer, Double>comparingByValue())
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (index, score) -> index, LinkedHashMap::new));

        LinkedList<Integer> sorted_indices = new LinkedList<>(sorted_scores.keySet());
        return sorted_indices;

    }

    /**
    * Returns a map of page indices and their corresponding TF-IDF scores.
    * @return a map of page indices and their TF-IDF scores
    */
    public static Map<Integer, Double> getScoreMap() {
        return score_map;
    }

}
