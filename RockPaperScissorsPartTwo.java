import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class RockPaperScissorsPartTwo {

    public static void main(String[] args) throws IOException {

        // Input and score mapping 
        Map<String, String> move_map = new HashMap<>();
        
        // opponent moves 
        move_map.put("A", "Rock");
        move_map.put("B", "Paper");
        move_map.put("C", "Scissors");

        // player moves 
        move_map.put("X", "lose");
        move_map.put("Y", "draw");
        move_map.put("Z", "win");

        // players score 
        int player_score = 0;

        // Read the input and print each move pair
        for (String line : Files.readAllLines(Paths.get("./day02/input.txt"))) {            
            // split the moves 
            String[] move_array = line.split("\s");

            // System.out.println("Player score before match: "+player_score);
            // System.out.println("Match: "+line);

            // players choice 
            String player_choice = new String();

            // calculate the score from the outcome of the game & what the player picked 
            if (move_map.get(move_array[1]).equals("lose")){
                player_score = player_score+0;
                
                // set the move required to lose 
                if (move_map.get(move_array[0]).equals("Rock")){
                    player_choice = "Scissors";
                } else if (move_map.get(move_array[0]).equals("Scissors")){
                    player_choice = "Paper";
                } else if (move_map.get(move_array[0]).equals("Paper")){
                    player_choice = "Rock";
                }

            } else if (move_map.get(move_array[1]).equals("draw")){
                player_score = player_score+3;
                // draws are the same move
                player_choice = move_map.get(move_array[0]);
            } else if (move_map.get(move_array[1]).equals("win")){
                player_score = player_score+6; 

                // set the move required to win 
                if (move_map.get(move_array[0]).equals("Scissors")){
                    player_choice = "Rock";
                } else if (move_map.get(move_array[0]).equals("Paper")){
                    player_choice = "Scissors";
                } else if (move_map.get(move_array[0]).equals("Rock")){
                    player_choice = "Paper";
                }
            }

            // second loop the score for each choice
            if (player_choice.equals("Rock")) {
                player_score = player_score+1;
            } 
            else if (player_choice.equals("Paper")) {
                player_score = player_score+2;
            }
            else if (player_choice.equals("Scissors")) {
                player_score = player_score+3;
            }
            
            // System.out.println("Player score after match: "+player_score);


        }

        System.out.println("The players final score is: "+player_score);

    }
}
