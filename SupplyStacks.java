// arg[0] == path to the input data 
// arg[1] == crate mover model (CrateMover9000, CrateMover9001)
// example use:
// > java SupplyStacks ./day05/input.txt CrateMover9000
// > java SupplyStacks ./day05/sample_input.txt CrateMover9001

import java.util.Collections;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SupplyStacks {

    public static int getStackCount(String stacks_string) {

        // empty variables to return 
        int max_stack_count = 0;
        
        // split the string into an array 
        String[] stack_lines = stacks_string.split(System.lineSeparator());

        // loop through the string 
        for(String l : stack_lines){
            // System.out.println("l: "+l);
            // check if the line starts with a space followed by a number at the end of the str 
            if(l.matches("\\s\\d.+[\\s\\S]")) {
                String[] cols = l.split("\\s");
                for(String c : cols) {
                    if(!c.isBlank()){
                        // System.out.println("col: "+c);
                        // update the max stack count variable 
                        max_stack_count = Integer.parseInt(c);
                    }
                }
            }
        }

        return(max_stack_count);
    }

    static <T> List<List<T>> transpose(List<List<T>> table) {
        // from https://stackoverflow.com/a/2942044
        List<List<T>> ret = new ArrayList<List<T>>();
        final int N = table.get(0).size();
        for (int i = 0; i < N; i++) {
            List<T> col = new ArrayList<T>();
            for (List<T> row : table) {
                col.add(row.get(i));
            }
            ret.add(col);
        }
        return ret;
    }

    public static List<List<String>> parseStacks(String stacks_string, int max_stack_count) {

        // create output variables 
        List<List<String>> input_stacks = new ArrayList<>();

        // split the string into an array 
        String[] stack_lines = stacks_string.split(System.lineSeparator());

        for(String l : stack_lines) {
            // skip lines that are just the column numbers 
            if(!l.matches("\\s\\d.+[\\s\\S]")) {
                // create nested "column" list 
                List<String> stack_list = new ArrayList<String>();

                // process input list 
                String[] l_split = l.split("\\s{4}|\\s");

                // System.out.println(Arrays.toString(l_split));
                // System.out.println(l_split.length);

                // this loop iterates through each possible "column" or stack of boxes
                // if the iteration loop has a split string index in the array from above then:
                    // we remove the brackets and check if the string is empty
                    // if the string is bettern A-Z then this is the letter of that box
                    // if the string IS NOT bettern A-Z we know this column/stack has no current box in it
                // else:
                    // e.g.; if there is no string index in this array BUT i is < max_stack_count then 
                    // the column/stack has no current letter in it
                for(int i=0; i < max_stack_count; i++){
                    if(i < l_split.length){
                        String stack_value_split = l_split[i].replaceAll("\\[|\\]", "");
                        // System.out.println("split: "+stack_value_split);
                        //if(stack_value_split.isEmpty()) {
                        if(stack_value_split.matches("[A-Z]")){
                            // System.out.println("stack column: "+i+" is: "+stack_value_split);
                            // add box to list 
                            stack_list.add(stack_value_split);
                        } else {
                            // System.out.println("stack column: "+i+" is: "+"NULL");
                            stack_list.add(null);
                        }
                    }
                    else {
                        // System.out.println("stack column: "+i+" is: "+"NULL");
                        stack_list.add(null); 
                    }
                }

                // add sub list to main list 
                input_stacks.add(stack_list);
            }

        }

        // transpose the stack representation 
        List<List<String>> input_stacks_transposed = transpose(input_stacks);

        // reverse the order of each list in the stack 
        List<List<String>> input_stacks_reversed = new ArrayList<>();

        for(List<String> stack_list : input_stacks_transposed){
            // reverse the list 
            Collections.reverse(stack_list);
            // remove empty elements 
            stack_list.removeAll(Collections.singleton(null));
            // add to the main list 
            input_stacks_reversed.add(stack_list);
        }

        return(input_stacks_reversed);

    }

    public static List<List<Integer>> parseMoves(String input_string) {
        
        // regex to match the moves 
        String regex_str = "move (\\d+) from (\\d+) to (\\d+)";

        // split the input string 
        String[] input_string_sep = input_string.split(System.lineSeparator());

        // vars to hold output
        List<List<Integer>> parsed_move_list = new ArrayList<>();
        List<Integer> num_to_move = new ArrayList<>();
        List<Integer> from_column = new ArrayList<>();
        List<Integer> to_column   = new ArrayList<>();

        for(String line : input_string_sep){

            // convert the regex string to a pattern object 
            Pattern pattern = Pattern.compile(regex_str, Pattern.MULTILINE);
            
            // run the pattern matcher 
            Matcher matcher = pattern.matcher(line);

            // process the matches
            while (matcher.find()) {
                // System.out.println("Full match: " + matcher.group(0));
                
                // add the regex matches to the list
                num_to_move.add(Integer.parseInt(matcher.group(1)));
                from_column.add(Integer.parseInt(matcher.group(2)));
                to_column.add(Integer.parseInt(matcher.group(3)));

            }
        }

        parsed_move_list.add(num_to_move);
        parsed_move_list.add(from_column);
        parsed_move_list.add(to_column);
        
        return(parsed_move_list);
        
    }

    public static List<List<String>> CrateMover9000(List<List<String>> stack_list, List<List<Integer>> parsed_move_list) {
        // get the number of moves
        int num_of_moves = parsed_move_list.get(0).size();

        // set vars to populate in loop 
        int num_to_move, from_col, to_col;

        // move the boxes! 
        for(int i=0; i<num_of_moves; i++){
            // get the current moveset 
            num_to_move = parsed_move_list.get(0).get(i);
            from_col = parsed_move_list.get(1).get(i)-1;
            to_col = parsed_move_list.get(2).get(i)-1;

            // System.out.println("MOVE :"+i);
            // System.out.println("Num to move: "+num_to_move);
            // System.out.println("From col: "+from_col);
            // System.out.println("To Col: "+to_col);

            // loop through the number of boxes to move 
            for(int j=0; j<num_to_move; j++){
                int box_to_move_idx = stack_list.get(from_col).size();

                if(box_to_move_idx!=0){
                    box_to_move_idx=box_to_move_idx-1;
                }
                                
                String box_to_move_str = stack_list.get(from_col).get(box_to_move_idx) ;
                stack_list.get(to_col).add(box_to_move_str) ;
                stack_list.get(from_col).remove(box_to_move_idx);
            }
        }

        return(stack_list);
    }

    public static List<List<String>> CrateMover9001(List<List<String>> stack_list, List<List<Integer>> parsed_move_list) {
        // get the number of moves
        int num_of_moves = parsed_move_list.get(0).size();

        // set vars to populate in loop 
        int num_to_move, from_col, to_col;

        // move the boxes! 
        for(int i=0; i<num_of_moves; i++){
            // get the current moveset 
            num_to_move = parsed_move_list.get(0).get(i);
            from_col = parsed_move_list.get(1).get(i)-1;
            to_col = parsed_move_list.get(2).get(i)-1;

            // box to move positions 
            int top_box_idx = stack_list.get(from_col).size();
            int bottom_box_idx = top_box_idx-num_to_move;

            // update box stacks 
            List<String> box_to_move_list = stack_list.get(from_col).subList(bottom_box_idx, top_box_idx) ;
            stack_list.get(to_col).addAll(box_to_move_list) ;
            stack_list.get(from_col).subList(bottom_box_idx, top_box_idx).clear();
        }

        return(stack_list);
    }


    public static void main(String args[]) throws IOException {

        // read the input file 
        String input_file = args[0];
        Path path = Paths.get(input_file);
        String input_string = Files.readString(path); //   readAllLines(path);
        
        // Set the Crate Mover Model
        String crate_mover_model = args[1];

        // split the input string by the empty line 
        String[] input_string_split = input_string.split("\\n\\r\\n");
        
        // set the component string variables 
        String stacks_string = input_string_split[0];
        String moves_string = input_string_split[1];
        
        // get the maximum number of stacks 
        int max_stack_count = getStackCount(stacks_string);

        // parse the input stacks to a list 
        List<List<String>> stack_list = parseStacks(stacks_string, max_stack_count);

        // parse the input moves to a list 
        List<List<Integer>> parsed_move_list = parseMoves(moves_string);

        // create empty moved stack list 
        List<List<String>> moved_stack_list = new ArrayList<>();

        // move the boxes!
        if(crate_mover_model.equals("CrateMover9000")){
            moved_stack_list = CrateMover9000(stack_list, parsed_move_list);
        } else if (crate_mover_model.equals("CrateMover9001")){
            moved_stack_list = CrateMover9001(stack_list, parsed_move_list);
        }
        

        // print the final stack configuration 
        System.out.println("FINAL STACK CONFIGURATION");
        for(List<String> l : moved_stack_list){
            System.out.println(l);
        }

        // compile final answer 
        String top_row = new String();

        for(List<String> l : moved_stack_list){
            String top_row_value = new String();
            if(!l.isEmpty()){
                top_row_value = l.get(l.size()-1);
            } else {
                top_row_value = " ";
            }

            top_row = top_row+top_row_value;
        }

        System.out.println("\nThe top row of all stacks is: "+top_row);

    }
    
}
