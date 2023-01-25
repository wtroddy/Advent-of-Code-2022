import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

class DirectionComponents {
    String input_direction;
    String coord_direction;
    int coord_element;
    int diag_coord_element;

    void parseDirection(String direction){
        input_direction = direction;
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
    }
}

public class RopeBridge {

    public static List<List<Integer>> initCoordinateList(int n_knots){
        List<List<Integer>> coordinate_list = new ArrayList<>();

        for(int i=0; i<n_knots; i++){
            coordinate_list.add(Arrays.asList(0,0));
        }

        return coordinate_list;
    }

    public static List<List<Integer>> moveKnot(List<List<Integer>> coordinate_list, int current_knot, String coord_direction, int coord_element){
        System.out.println("CURRENT KNOT:  "+current_knot);
        System.out.println("      BEFORE: "+coordinate_list.get(current_knot).toString());
        System.out.println("      moving the next knot: "+coord_direction+" "+coord_element);

        // update coords
        if(coord_direction=="Positive"){
            int current_pos = coordinate_list.get(current_knot).get(coord_element);
            coordinate_list.get(current_knot).set(coord_element, current_pos+1);
        } else if(coord_direction=="Negative"){
            int current_pos = coordinate_list.get(current_knot).get(coord_element);
            coordinate_list.get(current_knot).set(coord_element, current_pos-1);
        }
        System.out.println("      AFTER:   "+coordinate_list.get(current_knot).toString());

        return coordinate_list;
    }

    public static void main(String[] args) throws IOException {

        String input_data_arg = args[0];
        Path input_data_path = Paths.get(input_data_arg);

        int n_knots = Integer.parseInt(args[1]);

        // initial positions
        List<List<Integer>> coordinate_list = initCoordinateList(n_knots);
        int tail_idx = coordinate_list.size()-1;

        // object to record movements 
        ArrayList<String> positions = new ArrayList<String>(); 

        // Read the input and loop through each line 
        for (String line : Files.readAllLines(input_data_path)) {
            String direction = line.split("\\s")[0];
            int distance = Integer.parseInt(line.split("\\s")[1]);

            System.out.println(line);

            // for(int j=0; j<n_knots-1; j++){
            //     System.out.println("KNOT: "+j);
            //     System.out.println("          FULL SET: ");
            // for(List<Integer> l : coordinate_list){
            //     System.out.println("                    "+l.toString());
            // }

            // set coordinate handlers 
            DirectionComponents lead_knot = new DirectionComponents();
            lead_knot.parseDirection(direction);
            DirectionComponents follow_knot = new DirectionComponents();
            follow_knot.parseDirection(direction);
            
            // loop through distance first knot moves
            for(int i=0; i<distance; i++){

                // move the knot 
                coordinate_list = moveKnot(coordinate_list, 0, lead_knot.coord_direction, lead_knot.coord_element);

                // // print state 
                // System.out.println("          MOVE: "+i+"   COODINATES: "+coordinate_list.get(0));

                 
                for(int j=0; j<n_knots-1; j++){
                    int lead_pos; 
                    int follow_pos;
                    
                    if(j==0){
                        lead_pos = coordinate_list.get(j).get(lead_knot.coord_element);
                    } else {
                        lead_pos = coordinate_list.get(j).get(follow_knot.coord_element);
                    }
                    follow_pos = coordinate_list.get(j+1).get(follow_knot.coord_element);

                    // move the next knot if distance > 1
                    if(Math.abs(lead_pos-follow_pos) > 1){
                    // if(follow_pos-lead_pos > 1){
                        // coordinate_list = moveKnot(coordinate_list, j+1, follow_knot.coord_direction, follow_knot.coord_element);

                        // update diagonal offsets 
                        int lead_diag_pos; 
                        int follow_diag_pos;
                        
                        if(j==0){
                            lead_diag_pos = coordinate_list.get(j).get(lead_knot.diag_coord_element);
                        } else {
                            lead_diag_pos = coordinate_list.get(j).get(follow_knot.diag_coord_element);
                        }
                        follow_diag_pos = coordinate_list.get(j+1).get(follow_knot.diag_coord_element);
                        
                        if(lead_diag_pos!=follow_diag_pos){
                            // coordinate_list.get(j+1).set(follow_knot.diag_coord_element, lead_diag_pos);
                            // a diagonal move changes the direction the next lead knot is moving
                            // update the coordinate components
                            if(lead_diag_pos>follow_diag_pos){
                                // Positive moves 
                                if(follow_knot.input_direction.equals("R")){
                                    follow_knot.parseDirection("U");
                                } else if(follow_knot.input_direction.equals("U")){
                                    follow_knot.parseDirection("R");
                                }
                            } else if(lead_diag_pos<follow_diag_pos){
                                // Negative moves 
                                if(follow_knot.input_direction.equals("L")){
                                    follow_knot.parseDirection("D");
                                } else if(follow_knot.input_direction.equals("D")){
                                    follow_knot.parseDirection("L");
                                }
                            } 
                            System.out.println("CHANGING DIAG -- FROM: "+lead_knot.input_direction+"  TO:  "+follow_knot.input_direction);
                            // System.out.println();
                        }

                        // finally update the movement 
                        coordinate_list = moveKnot(coordinate_list, j+1, follow_knot.coord_direction, follow_knot.coord_element);

                    }    
                }

                // update the position tracker 
                // System.out.println(coordinate_list.get(tail_idx));
                positions.add(coordinate_list.get(tail_idx).get(0)+","+coordinate_list.get(tail_idx).get(1));

                System.out.println(" MOVE STEP: "+i);
                for(List<Integer> l : coordinate_list){
                    System.out.println("          "+l.toString());
                }
    
            }

            // // output final list
            // System.out.println("   OUTPUT FROM ONE MOVE   ");
            // for(List<Integer> l : coordinate_list){
            //     System.out.println(l.toString());
            // }

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
