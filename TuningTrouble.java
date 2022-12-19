// arg[0] == the input message to decode 
// example use:
// > java TuningTrouble mjqjpqmgbljsphdztnvjfqwrcgsmlb


public class TuningTrouble {

    public static void main(String args[]){
        
        // assign user input to a variable 
        String input_str = args[0]; 

        // looping vars
        int substr_start = 0;
        int packet_length = 4;
        int msg_length = 14;
        int max_length = input_str.length();

        for(int i=0; i<=(max_length-packet_length); i++){

            // get the substring and unique characters in it 
            String start_of_packet_candidate = input_str.substring(substr_start+i,packet_length+i);
            Long num_unq_chars = start_of_packet_candidate.chars().distinct().count();

            // conditionally print the solution and break the loop if all characters are unique 
            if(num_unq_chars==packet_length){
                System.out.println("The start-of-packet marker is complete after: "+(i+packet_length)+" characters have been processed.");
                break;
            }
        
        }

        for(int i=0; i<=(max_length-msg_length); i++){

            // get the substring and unique characters in it 
            String start_of_msg_candidate = input_str.substring(substr_start+i,msg_length+i);
            Long num_unq_chars = start_of_msg_candidate.chars().distinct().count();

            // conditionally print the solution and break the loop if all characters are unique 
            if(num_unq_chars==msg_length){
                System.out.println("The start-of-message marker is complete after: "+(i+msg_length)+" characters have been processed.");
                break;
            }
        
        }


    }
    
}
