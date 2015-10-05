package aa;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Problem2Main {

    public static void main (String[] args) {
        
        try (Stream<String> lines = Files.lines(Paths.get("quotes_2009-04.txt"))) {


        } catch (IOException e) {

        }
    }

}
