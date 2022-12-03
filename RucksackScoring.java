// arg[0] == path to the input data 
// example use:
// > java RucksackScoring ./day03/input.txt
// > java RucksackScoring ./day03/sample_input.txt 

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

    static String sharedCharacter(String compartment_one_string , String  compartment_two_string){
        // https://stackoverflow.com/a/30662665
        Set<Character> set1 = new HashSet<Character>();
        Set<Character> set2 = new HashSet<Character>();

        for(char c : compartment_one_string.toCharArray()){
            set1.add(c);
        }

        for(char c : compartment_two_string.toCharArray()){
            set2.add(c);
        }

        // get the intersection of the two sets 
        set1.retainAll(set2);

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

        // Read the input and loop through each line 
        for (String line : Files.readAllLines(Paths.get(input_data_path))) {

            // split the string into compartments
            String rucksack = line ; 
            int rucksack_length = rucksack.length();
            String compartment_one_string = rucksack.substring(0, rucksack_length/2);
            String compartment_two_string = rucksack.substring(rucksack_length/2);

            // System.out.println("Split string: "+compartment_one_string+"    "+compartment_two_string);
            
            // Find the Letter shared in both compartments
            String shared_character = sharedCharacter(compartment_one_string, compartment_two_string);
            char my_letter = shared_character.charAt(0);

            // Convert to the score 
            int score = convertLetterToScore(my_letter);
            // System.out.println("Character "+my_letter+" has score of: "+score);

            // add to the priority sum 
            priority_sum = priority_sum+score;

        }

        System.out.println("Priority Sum: "+priority_sum);

    }
    
}
