import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RopeBridge {

    public static void main(String[] args) throws IOException {

        String input_data_arg = args[0];
        Path input_data_path = Paths.get(input_data_arg);

        // initial positions
        int head_coord_x = 0;
        int head_coord_y = 0; 
        int tail_coord_x = 0;
        int tail_coord_y = 0;

        // object to record movements 
        ArrayList<String> positions = new ArrayList<String>(); 

        // Read the input and loop through each line 
        for (String line : Files.readAllLines(input_data_path)) {
            String direction = line.split("\\s")[0];
            int distance = Integer.parseInt(line.split("\\s")[1]);

            // update position
            for(int i=0; i<distance; i++){
                
                if(direction.equals("R")){
                    head_coord_x = head_coord_x+1;
                    if(Math.abs(head_coord_x-tail_coord_x) > 1){
                        tail_coord_x = tail_coord_x+1;
                        // handle diagonal offset
                        if(head_coord_y!=tail_coord_y){
                            tail_coord_y = head_coord_y;
                        }
                    };
                }
                if(direction.equals("L")){
                    head_coord_x = head_coord_x-1;
                    if(Math.abs(head_coord_x-tail_coord_x) > 1){
                        tail_coord_x = tail_coord_x-1;
                        // handle diagonal offset
                        if(head_coord_y!=tail_coord_y){
                            tail_coord_y = head_coord_y;
                        }
                    };
                }
                if(direction.equals("U")){
                    head_coord_y = head_coord_y+1;
                    if(Math.abs(head_coord_y-tail_coord_y) > 1){
                        tail_coord_y = tail_coord_y+1;

                        // handle diagonal offset
                        if(head_coord_x!=tail_coord_x){
                            tail_coord_x = head_coord_x;
                        }

                    };
                }
                if(direction.equals("D")){
                    head_coord_y = head_coord_y-1;
                    if(Math.abs(head_coord_y-tail_coord_y) > 1){
                        tail_coord_y = tail_coord_y-1;
                        // handle diagonal offset
                        if(head_coord_x!=tail_coord_x){
                            tail_coord_x = head_coord_x;
                        }

                    };
                }

                // update the position tracker 
                positions.add(tail_coord_x+","+tail_coord_y);

            }

        }

        // get the set (unique) string of positions 
        Set<String> distinct_positions = new HashSet<String>(positions);
        System.out.println("the total positions occupied by the tail were: "+distinct_positions.size());
        
    }

}
