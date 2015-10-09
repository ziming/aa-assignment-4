package aa;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Problem2Main {

    public static void main (String[] args) throws IOException {

        /*
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

        /*
         b. In eLearn, there is a file called y.txt. Download this file and write a stream-based program that can
            determine what words starting with “y” appear in the quote lines of the 10GB unzipped file which are not
            already listed in the y.txt file.
         */

        /*
         c. How many unique words are in the quote lines of the 10GB unzipped file?
         */


        Stream<String> lines = getLines();




    }

    public static Stream<String> getLines() throws IOException {
        return Files.lines(Paths.get("quotes_2009-04.txt"));
    }

}
