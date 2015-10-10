package aa;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Problem2Main {

    private static int DOCUMENT_URL = 0;
    private static int POST_TIMESTAMP = 1;

    // Note some documents have zero phrases or zero links.
    private static int PHRASE_1 = 2;
    private static int PHRASE_2 = 3;
    private static int PHRASE_3 = 4;
    private static int PHRASE_4 = 5;

    // note some documents have 0 phrases or 0 links
    private static int HYPERLINK_1 = 6;
    private static int HYPER_LINK_2 = 7;
    private static int HYPERLINK_3 = 8;

    public static void main (String[] args) throws IOException {

        /*

         https://snap.stanford.edu/data/memetracker9.html

         Data Format:

         P       http://blogs.abcnews.com/politicalpunch/2008/09/obama-says-mc-1.html
         T       2008-09-09 22:35:24
         Q       that's not change
         Q       you know you can put lipstick on a pig
         Q       what's the difference between a hockey mom and a pit bull lipstick
         Q       you can wrap an old fish in a piece of paper called change
         L       http://reuters.com/article/politicsnews/idusn2944356420080901?pagenumber=1&virtualbrandchannel=10112
         L       http://cbn.com/cbnnews/436448.aspx
         L       http://voices.washingtonpost.com/thefix/2008/09/bristol_palin_is_pregnant.html?hpid=topnews
         */

        // first let's analyse the data print first 250 lines maybe to get a sense of the format?
//        getLines("quotes_2009-04.txt")
//                .limit(250)
//                .forEach(System.out::println);

        /*
         a. How many occurrences are there of each of the following words in the quote lines (those that start with “Q”)?
         - “lipstick”
         - “steppe ” // Notice the space after steppe. Stepped would not count.
         - “boesch”
         - “antithesis”
         */
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
        Stream<String> quoteLines = getLines("quotes_2009-04.txt");

        Map<String, Long> wordFrequencyMap = quoteLines
                .filter(line -> line.startsWith("Q") && line.matches(".*\\b(lipstick|steppe |boesch|antithesis)\\b.*"))
                .map(line -> line.split("\\s+"))
                .collect(
                        Collectors.groupingBy(
                                line -> {

                                    for (String word : line) {

                                        switch (word) {
                                            case "lipstick":
                                                return "lipstick";
                                            case "steppe ":
                                                return "steppe ";
                                            case "boesch":
                                                return "boesch";
                                            case "antithesis":
                                                return "antithesis";
                                        }
                                    }
                                    return "default";

                                },
                            Collectors.counting()
                        )
                );

        System.out.println();

//        long timeTakenForPartA = stopWatch.stop();

        // question didn't ask for time measurement, this is purely for curosity
        // no parallel: 214791
        // parallel is  64334
//        System.out.println("Time taken for part a: (ms)" + timeTakenForPartA);

        /*
         b. In eLearn, there is a file called y.txt. Download this file and write a stream-based program that can
            determine what words starting with “y” appear in the quote lines of the 10GB unzipped file which are not
            already listed in the y.txt file.
         */

        quoteLines = getLines("quotes_2009-04.txt");

        // y.txt is pretty small only 13841 entries, might as well get it in memory for frequent re-use.

        Set<String> yWordsSet = getLines("y.txt").collect(Collectors.toSet());

        Set<String> wordsNotInYTxt = quoteLines
                .filter(line -> line.startsWith("Q"))
                .map(line -> line.split("\\s+"))
                .flatMap(Arrays::stream)
                .filter(word -> word.startsWith("y") && !yWordsSet.contains(word))
                .collect(Collectors.toSet());

        System.out.println("Words starting with “y” appear in the quote lines of the 10GB unzipped file which are not already listed in the y.txt file. are");
        wordsNotInYTxt.forEach(System.out::println);

        System.out.println();


        /*
         c. How many unique words are in the quote lines of the 10GB unzipped file?
         */

        quoteLines = getLines("quotes_2009-04.txt");

        long uniqueWordsCount = quoteLines
                .filter(line -> line.startsWith("Q"))
                .map(line -> line.split("\\s+"))
                .flatMap(Arrays::stream)
                .distinct()
                .count();

        System.out.println("Total number of unique words are: " + uniqueWordsCount);



    }

    private static Stream<String> getLines(String fileName) throws IOException {
        return Files.lines(Paths.get(fileName));
    }

}
