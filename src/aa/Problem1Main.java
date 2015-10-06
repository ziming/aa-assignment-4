package aa;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Problem1Main {

    private static final int TRADE_ID = 0;
    private static final int BUYER_ID = 1;
    private static final int SELLER_ID = 2;
    private static final int FISH_TYPE = 3;
    private static final int PRICE = 4;
    private static final int NUMBER_OF_FISH_TRADED = 5;

    public static void main (String[] args) throws IOException {

        // TradeID, BuyerID, SellerID, Fish Type, Price, Number of Fish traded

        /*
         * a. The number of purchases by each buyer.
         * b. The average price per fish.
         * c. The average quantity of fish.
         */

        /*
            0,20,16,sting ray,206,23
            1,49,46,mackerel,9242,99
            2,8,26,shrimp,476,71
            3,6,27,pomfret,2166,87
            4,31,45,mackerel,7860,31
            5,31,25,grouper,5460,82
            6,27,38,grouper,859,87
            7,19,1,snapper,3742,8
            8,48,45,snapper,1073,53
            9,1,33,sting ray,4991,20
         */

        // API docs: https://docs.oracle.com/javase/8/docs/api/

        Map<String, AtomicInteger> buyerPurchases = new HashMap<>();

        // v1 of number of purchases by each buyer.
        for (int i = 0; i < 10; i++) {

            Stream<String> lines = Files.lines(Paths.get(String.format("fish%d.dat", i)));

            lines.forEach(line -> {
                String[] row = line.split(",");
                int rowfishesTraded = Integer.parseInt(row[NUMBER_OF_FISH_TRADED]);

                AtomicInteger buyerFishesTradedSum = buyerPurchases.get(row[BUYER_ID]);

                if (buyerFishesTradedSum == null) {
                    buyerPurchases.put(row[BUYER_ID], new AtomicInteger(rowfishesTraded));
                } else {
                    buyerFishesTradedSum.getAndAdd(rowfishesTraded);
                }

            });
        }

        buyerPurchases.forEach((key, value) -> System.out.println(key + " bought " + value.get() + " fishes"));

        // average prices per fish. v1
        Map<String, double[]> fishPricesMap = new HashMap<>();

        for (int i = 0; i < 10; i++) {

            Stream<String> lines = Files.lines(Paths.get(String.format("fish%d.dat", i)));

            lines.forEach(line -> {
                String[] row = line.split(",");

                double rowFishPrice = Double.parseDouble(row[PRICE]);
                double rowfishesTraded= Double.parseDouble(row[NUMBER_OF_FISH_TRADED]);

                double[] fishPriceStat = fishPricesMap.get(row[FISH_TYPE]);

                if (fishPriceStat == null) {
                    fishPricesMap.put(row[BUYER_ID], new double[] {rowFishPrice, rowfishesTraded});
                } else {
                    fishPriceStat[0] += rowFishPrice;
                    fishPriceStat[1] += rowfishesTraded;
                }

            });
        }

        // to calculate and print average

    }

}
