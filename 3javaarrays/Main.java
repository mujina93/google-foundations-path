
public class Main {
    public static void main(String[] args){
        // array: box syntax: type[]
        int[] ages = {17,21,18,19};
                    // braces initialization
        // initialize reference with new object
        boolean[] student = new boolean[4];
                            // pre-allocate fixed size
        // random access - initialize by hand
        student[0] = true;
        student[1] = false;
        student[2] = false;
        student[3] = true;
        // access/initialize with for
        for(int i = 0; i < ages.length; i++){
            System.out.println(ages[i]);
        }
    }
}