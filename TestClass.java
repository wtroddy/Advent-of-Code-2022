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


public class TestClass {

    public static void main(String[] args){

        DirectionComponents lead_knot_direction_components = new DirectionComponents();

        lead_knot_direction_components.parseDirection("U");

        System.out.println(lead_knot_direction_components.coord_element);

    }
    
}
