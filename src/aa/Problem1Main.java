package aa;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Problem1Main {

    private static final int TRADE_ID = 0;
    private static final int BUYER_ID = 1;
    private static final int SELLER_ID = 2;
    private static final int FISH_TYPE = 3;
    private static final int FISH_PRICE = 4;
    private static final int NUMBER_OF_FISH_TRADED = 5;

    public static void main (String[] args) throws IOException {

        // CSV Format according to assignment 4 is: TradeID, BuyerID, SellerID, Fish Type, Price, Number of Fish traded

        /*
         * a. The number of purchases by each buyer.
         * b. The average price per fish.
         * c. The average quantity of fish.
         */

        /*
            TradeID, BuyerID, SellerID, Fish Type, Price, Number of Fish traded
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

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // === a. The number of purchases (trades) by each buyer. ===
        partANumOfTradesGroupByBuyer();

        // "=== b. The average price per fish ==="
        partBAvgPriceGroupByFishPerTransaction();

        // c. The average quantity per fish.
        partCAvgQUantityGroupByFishPerTransaction();

        long totalTimeTaken = stopWatch.stop();

        // non parallel stream is about 19102 ms
        // parallel stream is about 9657
        System.out.println("Total Time Taken (ms): " + totalTimeTaken);

    }

    private static void partANumOfTradesGroupByBuyer() throws IOException {

        // Get combined stream from fish0.dat to fish9.dat
        Stream<String> lines = getCombinedStream();

        // a. The number of purchases by each buyer.
        // assumption: purchases is number of trades (transactions) not number of fish traded.
        Map<String, Long> buyerNoOfPurchasesMap = lines
                .map(line -> line.split(","))
                .collect(
                        Collectors.groupingBy(
                                line -> line[BUYER_ID],
                                Collectors.counting()
                        ));

        lines.close();

        System.out.println("=== a. The number of purchases (trades) by each buyer. ===");
        buyerNoOfPurchasesMap.forEach(
                (key, value) -> System.out.println(key + " has made " + value + " trades (purchases)")
        );


        // just to leave a blank line
        System.out.println();
    }

    private static void partBAvgPriceGroupByFishPerTransaction() throws IOException {
        Stream<String> lines = getCombinedStream();

        Map<String, Double> fishAvgPriceMap = lines
                .map(line -> line.split(","))
                .collect(
                        Collectors.groupingBy(
                                line -> line[FISH_TYPE],
                                Collectors.averagingDouble(line -> Double.parseDouble(line[FISH_PRICE]))
                        )
                );

        lines.close();

        System.out.println("=== b. The average price by fish per transaction ===");
        fishAvgPriceMap.forEach(
                (key, value) -> System.out.println("Fish: " + key + " and its Average Price per transaction is " + value)
        );

        System.out.println();

    }

    private static void partCAvgQUantityGroupByFishPerTransaction() throws IOException {

        Stream<String> lines = getCombinedStream();
        Map<String, Double> avgQtyPerFishMap = lines
                .map(line -> line.split(","))
                .collect(
                        Collectors.groupingBy(
                                line -> line[FISH_TYPE],
                                Collectors.averagingDouble(line -> Double.parseDouble(line[NUMBER_OF_FISH_TRADED]))
                        )
                );

        lines.close();

        System.out.println("=== c. The average quantity by fish per transaction ===");
        avgQtyPerFishMap.forEach(
                (key, value) -> System.out.println("Fish: " + key + " and its Average Quantity per transaction is " + value)
        );

        System.out.println();

    }

    private static Stream<String> getCombinedStream() throws IOException {

        Stream<String> lines = null;

        for (int i = 0; i < 10; i++) {

            Stream<String> currentFileLines = Files.lines(Paths.get(String.format("fish%d.dat", i)));

            lines = (lines != null) ? Stream.concat(lines, currentFileLines) : currentFileLines;

        }

        /*
         * In the doc of Files.lines it says
         * The returned stream encapsulates a Reader. If timely disposal of file system resources is required,
         * the try-with-resources construct should be used to ensure that the stream's close method is invoked after the
         * stream operations are completed.
         *
         * So that means we must remember to call close() if we didn't use try-with resources
         */

        return lines.parallel();
    }

}
