// arg[0] == the input message to decode 
// example use:
// > java TuningTrouble mjqjpqmgbljsphdztnvjfqwrcgsmlb


public class TuningTrouble {

    public static void main(String args[]){
        
        // assign user input to a variable 
        String input_str = args[0]; 

        // looping vars
        int substr_start = 0;
        int substr_end = 4;
        int max_length = input_str.length();

        for(int i=0; i<=(max_length-substr_end); i++){

            // get the substring and unique characters in it 
            String input_sub_str = input_str.substring(substr_start+i,substr_end+i);
            Long num_unq_chars = input_sub_str.chars().distinct().count();

            // conditionally print the solution and break the loop if all characters are unique 
            if(num_unq_chars==4){
                System.out.println("The start-of-packet marker is complete after: "+(i+substr_end)+" characters have been processed.");
                break;
            }
        
        }

    }
    
}
