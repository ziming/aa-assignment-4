package aa;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Problem1Main {

    public static void main (String[] args) {

        // TradeID, BuyerID, SellerID, Fish Type, Price, Number of Fish traded

        /*
         * a. The number of purchases by each buyer.
         * b. The average price per fish.
         * c. The average quantity of fish.
         */

        // start with fish0.dat first
        try (Stream<String> lines = Files.lines(Paths.get("fish0.dat"))) {

            /*
             * Strategy for (a)
             * For each line,
             * get the buyerID and Number of fish traded
             * add to the number of purchases for that buyer in a hashmap
             */

        } catch (IOException e) {

        }
    }

}
