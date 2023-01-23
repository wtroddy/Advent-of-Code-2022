import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class TreeTopHouse {

    public static int[][] read_tree_grid(Path input_data_path) throws IOException {
        // get the number of lines in the data 
        // the grids we're given are all square so we can use lines as the n row and n col
        int input_data_line_count = (int) Files.lines(input_data_path).count();

        // set an object to store the grid 
        int[][] tree_grid = new int[input_data_line_count][input_data_line_count];
        int tree_grid_idx = 0;


        // Read the input and loop through each line 
        for (String line : Files.readAllLines(input_data_path)) {

            // System.out.println(line.length());
            // convert to an int array 
            int[] row_array = new int [input_data_line_count];

            for(int i=0; i<line.length(); i++){
                row_array[i] = Character.getNumericValue(line.charAt(i));    
            }
            
            // add to the tree grid 
            tree_grid[tree_grid_idx] = row_array;

            // increment index 
            tree_grid_idx = tree_grid_idx+1;

        }

        return(tree_grid);

    }

    public static Boolean isTreeVisible(int current_tree_height, int[] comparison_array){
        for(int i=0; i<comparison_array.length; i++){
            if(comparison_array[i]>=current_tree_height){
                return false; 
            } 
        }
        return true;
    }

    public static int calculateViewingDistance(int current_tree_height, int[] comparison_array, String direction){
        int viewing_distance = 0;


        if(direction.equals("forward")) {
            for(int i=0; i<comparison_array.length; i++){
                if(comparison_array[i]>=current_tree_height){
                    viewing_distance = viewing_distance+1;
                    return viewing_distance;
                } else if(comparison_array[i]<current_tree_height) {
                    viewing_distance = viewing_distance+1;
                }
            }    
        } else if(direction.equals("reverse")){
            for(int i=comparison_array.length-1; i>=0; i--){
                if(comparison_array[i]>=current_tree_height){
                    viewing_distance = viewing_distance+1;
                    return viewing_distance;
                } else if(comparison_array[i]<current_tree_height) {
                    viewing_distance = viewing_distance+1;
                }
            }    
        }
        
        return viewing_distance;

    }

    public static int calculateScenicScore(int[][] tree_grid, int row_idx, int col_idx){
        int current_tree_height = tree_grid[row_idx][col_idx];
        
        // calculate the left viewing distance 
        int[] left_comparison_arr = Arrays.copyOfRange(tree_grid[row_idx], 0, col_idx); 
        int left_viewing_distance = calculateViewingDistance(current_tree_height, left_comparison_arr, "reverse");

        // calculate the right viewing distance 
        int[] right_comparison_arr = Arrays.copyOfRange(tree_grid[row_idx], col_idx+1, tree_grid[row_idx].length);
        int right_viewing_distance = calculateViewingDistance(current_tree_height, right_comparison_arr, "forward");

        // calculate the top viewing distances 
        int top_length = row_idx;
        int[] top_comparison_arr = new int[top_length];
        for(int i=0; i<row_idx; i++){
            top_comparison_arr[i] = tree_grid[i][col_idx];
        } 
        int top_viewing_distance = calculateViewingDistance(current_tree_height, top_comparison_arr, "reverse");

        // check the bottom visibility 
        int bottom_length = tree_grid[0].length - row_idx - 1;
        int[] bottom_comparison_arr = new int[bottom_length];
        for(int i=0; i<bottom_length; i++){
            bottom_comparison_arr[i] = tree_grid[row_idx+i+1][col_idx];
        }
        int bottom_viewing_distance = calculateViewingDistance(current_tree_height, bottom_comparison_arr, "forward");
        
        int scenic_score = left_viewing_distance*right_viewing_distance*top_viewing_distance*bottom_viewing_distance;

        return scenic_score;

    }

    public static Boolean check_visibility(int[][] tree_grid, int row_idx, int col_idx){

        int current_tree_height = tree_grid[row_idx][col_idx];

        // check the left visibility 
        int[] left_comparison_arr = Arrays.copyOfRange(tree_grid[row_idx], 0, col_idx); //[1:col_idx];
        Boolean left_visibility = isTreeVisible(current_tree_height, left_comparison_arr);

        // check the right visibility 
        int[] right_comparison_arr = Arrays.copyOfRange(tree_grid[row_idx], col_idx+1, tree_grid[row_idx].length);
        Boolean right_visibility = isTreeVisible(current_tree_height, right_comparison_arr);

        // check the top visibility 
        int top_length = row_idx;
        int[] top_comparison_arr = new int[top_length];
        for(int i=0; i<row_idx; i++){
            top_comparison_arr[i] = tree_grid[i][col_idx];
        } 
        Boolean top_visibility = isTreeVisible(current_tree_height, top_comparison_arr);

        // check the bottom visibility 
        int bottom_length = tree_grid[0].length - row_idx - 1;
        int[] bottom_comparison_arr = new int[bottom_length];
        for(int i=0; i<bottom_length; i++){
            bottom_comparison_arr[i] = tree_grid[row_idx+i+1][col_idx];
        }
        Boolean bottom_visibility = isTreeVisible(current_tree_height, bottom_comparison_arr);

        if (left_visibility || right_visibility || top_visibility || bottom_visibility) {
           return true; 
        }

        return false;
    }


    public static void main(String[] args) throws IOException {

        String input_data_arg = args[0];
        Path input_data_path = Paths.get(input_data_arg);

        // read the input data 
        int[][] tree_grid = read_tree_grid(input_data_path);

        // tree grid size 
        int tree_grid_size = tree_grid[0].length;

        // create a count of visible trees 
        int visible_tree_count = 0;

        // create an array of scenic scores
        ArrayList<Integer> scenic_score_list = new ArrayList<Integer>(); 

        for(int i=0; i<(tree_grid_size-1); i++){

            if(i==0) {
                // add the perimeter count
                visible_tree_count = visible_tree_count+((tree_grid_size*4)-4);
            } else {
                for(int j=1; j<(tree_grid_size-1); j++){
                    Boolean visibility_status = check_visibility(tree_grid, i, j);
                    if(visibility_status){
                        visible_tree_count=visible_tree_count+1;
                    }

                    // scenic score 
                    int scenic_score = calculateScenicScore(tree_grid, i, j);
                    scenic_score_list.add(scenic_score);
                }    
            }
        }    

         System.out.println("There are "+visible_tree_count+" visible trees.");

         Integer max_scenic_score = Collections.max(scenic_score_list);
         System.out.println("The maximum scenic score is: "+max_scenic_score);

    }
}