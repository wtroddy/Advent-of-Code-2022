import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;


public class FilesystemSpace {  
    

    public static void main(String[] args) throws IOException{
        String input_path = args[0];

        List<String> input_lines = Files.readAllLines(Paths.get(input_path));

        // output structure 
        List<List<Integer>> directory_sizes = new ArrayList<>();

        String cmd_cd = "$ cd";
        String cmd_ls = "$ ls";
        String dir_start = "dir";

        Boolean ls_flag = false;

        int fsize_sum = 0;

        int tree_depth = 0;


        for(String l : input_lines){

            if (l.startsWith(dir_start)) {
                System.out.println("       dir: "+l);
            } else if(l.equals(cmd_ls)) {
                System.out.println("OLD DIRECTORY SIZE IS: "+fsize_sum);
                System.out.println("beginning to list directory");

                // set ls flag to true - the next lines of the file are file sizes
                ls_flag = true;
                // reset the file size counter for this upcoming directory
                fsize_sum = 0;
                
            } else if(l.startsWith(cmd_cd)){
                String[] cd_cmd = l.split("\\$\\scd\\s");
                System.out.println("changing directory to: "+Arrays.toString(cd_cmd));



                // this loop needs to be updated to handle nested directories correctly 
                // it's getting close to adding new nested lists but isn't quite right yet 
                if (ls_flag == true){
                    // create a new current directory size list
                    List<List<Integer>> nested_parent_directory = new ArrayList<>();
                    List<Integer> current_directory_size = new ArrayList<>();
                    current_directory_size.add(fsize_sum);
                    nested_parent_directory.add(current_directory_size);

                    // update the directory sizes object
                    System.out.println(directory_sizes);
                    if(tree_depth == 0){
                        directory_sizes//.get(tree_depth).addAll()
                        .add(current_directory_size);
                    } else {
                        directory_sizes.addAll(nested_parent_directory);
                    }

                }

                // reset the list flag to false - the next lines are not this directory 
                ls_flag = false;

                // update the tree depth for after the cd commands
                if (cd_cmd[1].equals("/")) {
                    tree_depth = 0;
                } else if(cd_cmd[1].equals("..")){
                    tree_depth = tree_depth-1;
                } else {
                    tree_depth = tree_depth+1;
                }
                
                System.out.println("New tree depth is: "+tree_depth);
                
            } else if(l.matches("\\d.+")) {
                int fsize = Integer.parseInt(l.split("\\s")[0]);
                // System.out.print("filesize is: "+fsize+"\n");
                if(ls_flag == true){
                    fsize_sum=fsize_sum+fsize;
                }
            } 
            else {
                System.out.println("! UNCAUGHT LINE"+l);                
            }
            
        }

        System.out.println(fsize_sum);

        System.out.println(directory_sizes);

    }
}
