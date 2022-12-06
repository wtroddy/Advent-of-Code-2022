import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CampCleanup {

    public static int[] cleanString(String str) {
        // split the string 
        String[] split_string = str.split(",|-");

        // convert to an int 
        int[] split_string_as_int = new int[split_string.length];
        for(int i = 0; i < split_string.length; i++) {
            split_string_as_int[i] = Integer.parseInt(split_string[i]) ;
        }

        return(split_string_as_int);
    }

    public static boolean compareAssignments(int section_a_start, int section_a_end, int section_b_start, int section_b_end) {
        
        boolean assignment_overlap = false;
        
        // check if section a is entirely within section b
        if((section_a_start >= section_b_start && section_a_end <= section_b_end)) {
            assignment_overlap = true;
        } else if ((section_b_start >= section_a_start && section_b_end <= section_a_end)){
            assignment_overlap = true;
        }

        return(assignment_overlap);

    }

    public static void main(String args[]) throws IOException {

        // input vars
        String input_data_path = args[0];

        // assignment overlap counter
        int counter = 0;

        // read the input file 
        for (String line : Files.readAllLines(Paths.get(input_data_path))) {
            int[] section_assignments = cleanString(line);

            boolean assignment_overlap = compareAssignments(section_assignments[0], section_assignments[1], section_assignments[2], section_assignments[3]);
            
            // conditionally update the counter 
            if(assignment_overlap){
                counter = counter+1;
            }

        }

        // print the result
        System.out.println("in the assignments there are "+counter+" pairs that are fully contained in another.");
    }

}
