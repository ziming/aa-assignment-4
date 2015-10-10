package aa;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
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

        /*
         a. How many occurrences are there of each of the following words in the quote lines (those that start with “Q”)?
         - “lipstick”
         - “steppe ” // Notice the space after steppe. Stepped would not count.
         - “boesch”
         - “antithesis”
         */
        Stream<String> lines = getLines();

        List<String> phraseList = lines
                .filter(line -> line.startsWith("Q") && line.matches(".*\\b(lipstick|steppe |boesch|antithesis)\\b.*"))
                .collect(Collectors.toList());

        phraseList.forEach(System.out::println);

        System.out.println();

        /*
         b. In eLearn, there is a file called y.txt. Download this file and write a stream-based program that can
            determine what words starting with “y” appear in the quote lines of the 10GB unzipped file which are not
            already listed in the y.txt file.
         */



        /*
         c. How many unique words are in the quote lines of the 10GB unzipped file?
         */

        lines = getLines();

        long uniqueWordsCount = lines
                .filter(line -> line.startsWith("Q"))
                .map(String::toLowerCase)
                .map(line -> line.split("\\s+"))
                .flatMap(Arrays::stream)
                .distinct()
                .count();

        System.out.println("Total number of unique words are: " + uniqueWordsCount);



    }

    private static Stream<String> getLines() throws IOException {
        return Files.lines(Paths.get("quotes_2009-04.txt"));
    }

}
