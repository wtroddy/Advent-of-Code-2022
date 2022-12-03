import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class RockPaperScissors {

    public static void main(String[] args) throws IOException {

        // Input and score mapping 
        Map<String, String> move_map = new HashMap<>();
        
        // opponent moves 
        move_map.put("A", "Rock");
        move_map.put("B", "Paper");
        move_map.put("C", "Scissors");

        // player moves 
        move_map.put("X", "Rock");
        move_map.put("Y", "Paper");
        move_map.put("Z", "Scissors");

        // players score 
        int player_score = 0;

        // Read the input and print each move pair
        for (String line : Files.readAllLines(Paths.get("./day02/input.txt"))) {            
            // split the moves 
            String[] move_array = line.split("\s");

            // System.out.println("Player score before match: "+player_score);
            // System.out.println("Match: "+line);

            // print the chosen moves 
            // System.out.println("The opponent chose: "+move_array[0]+". This maps to a move of: "+move_map.get(move_array[0]));
            // System.out.println("The player chose: "+move_array[1]+". This maps to a move of: "+move_map.get(move_array[1]));

            // calculate the outcome of the game 
            // the same move results in a draw
            if (move_map.get(move_array[0]) == move_map.get(move_array[1])) {
                // update the players score
                player_score = player_score+3;
            } 

            // second loop to handle wins & each the score for each choice
            // player selects rock; opponent selects scissors
            if (
                move_map.get(move_array[1]).equals("Rock")
            ) {
                // update the score for this choice 
                player_score = player_score+1;
                // check if player wins
                if (move_map.get(move_array[0]).equals("Scissors")) {
                    // System.out.println("Player wins!\r\n");
                    // update the players score
                    player_score = player_score+6;
                }
            } 
            // player selects Scissors; opponent selects Paper
            else if (
                move_map.get(move_array[1]).equals("Scissors")
            ) {
                // update the score for this choice 
                player_score = player_score+3;
                // check if player wins
                if (move_map.get(move_array[0]).equals("Paper")) {
                    // System.out.println("Player wins!\r\n");
                    // update the players score
                    player_score = player_score+6;
                }
            }
            // player selects Scissors; opponent selects Paper
            else if (
                move_map.get(move_array[1]).equals("Paper")
            ) {
                // update the score for this choice 
                player_score = player_score+2;
                // check if player wins                
                if (move_map.get(move_array[0]).equals("Rock")) {
                    // System.out.println("Player wins!\r\n");
                    // update the players score
                    player_score = player_score+6;
                }
            }

            // System.out.println("Player score after match: "+player_score);
        }

        System.out.println("The players final score is: "+player_score);

    }
}
