import java.util.ArrayList;
import java.util.List;

public class Array {
    public static void main(String[] args) {
        List<Integer> nums = new ArrayList<Integer>();
    
        nums.add(5);
        nums.add(6);
        nums.add(10);
        nums.add(1);
        nums.add(3);

        System.out.println(nums);

        System.out.println("  ");

        for(int n : nums){
            System.out.println(n);
        }
    }    
}
