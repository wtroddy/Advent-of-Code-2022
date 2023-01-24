import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TestIntList {
    
    public static void main(String[] args){
        List<List<Integer>> coordinate_list = new ArrayList<>();
        int n_knots=2;

        for(int i=0; i<n_knots; i++){
            coordinate_list.add(Arrays.asList(0,0));
        }

        System.out.println(coordinate_list.toString());

        // update head
        coordinate_list.get(0).set(0, 10);
        System.out.println(coordinate_list.get(0).toString()); //.set(0, 10);
        System.out.println(coordinate_list.toString());

    }

}
