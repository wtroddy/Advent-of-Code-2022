// arg[0] == path to the input data 
// example use:
// > java RucksackScoring ./day03/input.txt
// > java RucksackScoring ./day03/sample_input.txt 

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RucksackScoring {

    static int convertLetterToScore(char letter) {
        
        int score;

        if (Character.isUpperCase(letter)){
            score = letter-'A'+1+26;
        } else {
            score = letter-'a'+1;
        }       

        return score;
    }

    static String sharedCharacter(String set_one_string , String  set_two_string, String set_three_string){
        // https://stackoverflow.com/a/30662665
        Set<Character> set1 = new HashSet<Character>();
        Set<Character> set2 = new HashSet<Character>();

        for(char c : set_one_string.toCharArray()){
            set1.add(c);
        }

        for(char c : set_two_string.toCharArray()){
            set2.add(c);
        }

        // get the intersection of the two sets 
        set1.retainAll(set2);

        // conditionally process a third string set 
        if (!set_three_string.isEmpty()) {
            Set<Character> set3 = new HashSet<Character>();
            for(char c : set_three_string.toCharArray()){
                set3.add(c);
            }

            // get the intersection
            set1.retainAll(set3);
    
        }

        // convert to a string
        String shared_string = new String();
        for(char c : set1) {
            shared_string =  shared_string+c;
        }

        return shared_string;
        
    }

    public static void main(String[] args) throws IOException {

        String input_data_path = args[0];
        int priority_sum = 0;

        ArrayList<String> group_rucksack = new ArrayList<String>();
        int group_counter = 0;
        int group_size = 3;
        int group_priority_sum = 0;

        // Read the input and loop through each line 
        for (String line : Files.readAllLines(Paths.get(input_data_path))) {

            String rucksack = line ; 

            // group rucksack calculations
            // add the rucksacks if the group isn't complete
            if (group_counter<group_size) {
                group_rucksack.add(rucksack);
                group_counter=group_counter+1;
            } 

            // if the group is complete, print the groups rucksacks and reset 
            if(group_counter==group_size){
                
                // get the shared character for the group
                String shared_group_character = sharedCharacter(group_rucksack.get(0).toString(), group_rucksack.get(1).toString(), group_rucksack.get(2).toString());
                char group_letter = shared_group_character.charAt(0);
                int group_score = convertLetterToScore(group_letter);
                group_priority_sum = group_priority_sum+group_score;

                // update count and reset array 
                group_counter=0;
                group_rucksack.clear();
            }

            // split the string into compartments
            int rucksack_length = rucksack.length();
            String set_one_string = rucksack.substring(0, rucksack_length/2);
            String set_two_string = rucksack.substring(rucksack_length/2);            
            // Find the Letter shared in both compartments
            String shared_character = sharedCharacter(set_one_string, set_two_string, "");
            char my_letter = shared_character.charAt(0);

            // Convert to the score and add to priority score 
            int score = convertLetterToScore(my_letter);
            priority_sum = priority_sum+score;


        }

        // print the results 
        System.out.println("(Part 1) Individaul Priority Sum: "+priority_sum);
        System.out.println("(Part 2) Group Priority Sum: "+group_priority_sum);

    }
    
}
