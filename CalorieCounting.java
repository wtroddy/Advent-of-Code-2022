import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// examples used to help: 
// https://stackoverflow.com/questions/2788080/java-how-to-read-a-text-file
// https://github.com/zebalu/advent-of-code-2022/blob/master/aoc2022/src/main/java/io/github/zebalu/aoc2022/Day01.java

public class CalorieCounting {
    public static void main(String[] args) throws NumberFormatException, IOException {
        List<Integer> elves = new ArrayList<>();
        int[] current = new int[] { 0 };

        for (String line : Files.readAllLines(Paths.get("./day01/input.txt"))) {
            if (line.isEmpty()) {
                elves.add(current[0]);
                current[0] = 0;
            } else {
                for (String part : line.split("\\n")) {
                    Integer i = Integer.valueOf(part);
                    current[0] += i;
                }
            }
        }

        Integer most_calories = Collections.max(elves);
        System.out.println("The elf carrying the most calories is carrying: "+most_calories+" total calories.");

        // sort the list
        elves.sort((a, b) -> b - a);

        // sum the top three
        Integer top_three_sum = elves.get(0) + elves.get(1) + elves.get(2);
        System.out.println("The total calories carried by the top three elves is: "+top_three_sum+".");

    }
}


