import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

public class RopeBridge {

    public static List<List<Integer>> initCoordinateList(int n_knots){
        List<List<Integer>> coordinate_list = new ArrayList<>();

        for(int i=0; i<n_knots; i++){
            coordinate_list.add(Arrays.asList(0,0));
        }

        return coordinate_list;
    }

    public static List<List<Integer>> moveKnot(List<List<Integer>> coordinate_list, int current_knot, String coord_direction, int coord_element){

        // update coords
        if(coord_direction=="Positive"){
            int current_pos = coordinate_list.get(current_knot).get(coord_element);
            coordinate_list.get(current_knot).set(coord_element, current_pos+1);
        } else if(coord_direction=="Negative"){
            int current_pos = coordinate_list.get(current_knot).get(coord_element);
            coordinate_list.get(current_knot).set(coord_element, current_pos-1);
        }

        return coordinate_list;
    }

    public static void main(String[] args) throws IOException {

        String input_data_arg = args[0];
        Path input_data_path = Paths.get(input_data_arg);

        int n_knots = Integer.parseInt(args[1]);

        // initial positions
        List<List<Integer>> coordinate_list = initCoordinateList(n_knots);

        // object to record movements 
        ArrayList<String> positions = new ArrayList<String>(); 

        // Read the input and loop through each line 
        for (String line : Files.readAllLines(input_data_path)) {
            String direction = line.split("\\s")[0];
            int distance = Integer.parseInt(line.split("\\s")[1]);

            System.out.println(line);

            // update position for each move in the distance 
            // for(int i=0; i<distance; i++){
            //     // update each knot in the rope 
            //     for(int j=0; j<n_knots-1; j++){

            // set coordinate handlers 
            String coord_direction = "";
            int coord_element = -1;
            int diag_coord_element = -1;
            if(direction.equals("R")){
                coord_direction = "Positive";
                coord_element = 0;
                diag_coord_element = 1;
            } else if (direction.equals("L")){
                coord_direction = "Negative";
                coord_element = 0;
                diag_coord_element = 1;
            } else if(direction.equals("U")){
                coord_direction = "Positive";
                coord_element = 1;
                diag_coord_element = 0;
            } else if (direction.equals("D")){
                coord_direction = "Negative";
                coord_element = 1;
                diag_coord_element = 0;
            }


            for(int j=0; j<n_knots-1; j++){
                // update each knot in the rope 
                for(int i=0; i<distance; i++){    
    
                    // move the knot 
                    coordinate_list = moveKnot(coordinate_list, j, coord_direction, coord_element);
    
                    // move the next knot if distance > 1 
                    int lead_pos = coordinate_list.get(j).get(coord_element);
                    int follow_pos = coordinate_list.get(j+1).get(coord_element);
                    if(Math.abs(lead_pos-follow_pos) > 1){
                        coordinate_list = moveKnot(coordinate_list, j+1, coord_direction, coord_element);
                        // update diagonal offsets 
                        int lead_diag_pos = coordinate_list.get(j).get(diag_coord_element);
                        int follow_diag_pos = coordinate_list.get(j+1).get(diag_coord_element);
                        if(lead_diag_pos!=follow_diag_pos){
                            coordinate_list.get(j+1).set(diag_coord_element, lead_diag_pos);
                        }
                    }

                    // update the position tracker 
                    int tail_idx = coordinate_list.size()-1;
                    // System.out.println(coordinate_list.get(tail_idx));
                    positions.add(coordinate_list.get(tail_idx).get(0)+","+coordinate_list.get(tail_idx).get(1));
                }
            }
            // output final list
            System.out.println("   OUTPUT FROM ONE MOVE   ");
            for(List<Integer> l : coordinate_list){
                System.out.println(l.toString());
            }

        }

        // output final list
        System.out.println("");
        System.out.println("FINAL");
        for(List<Integer> l : coordinate_list){
            System.out.println(l.toString());
        }

        // get the set (unique) string of positions 
        System.out.println(positions.toString());

        Set<String> distinct_positions = new HashSet<String>(positions);
        System.out.println(distinct_positions.toString());

        System.out.println("the total positions occupied by the tail were: "+distinct_positions.size());
        
    }

}
