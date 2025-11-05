import java.util.HashSet;
import java.util.Set;

public class UniqueSet {
    public static void main(String[] args) {
        Set<Integer> nums = new HashSet<Integer>();
    
        nums.add(5);
        nums.add(6);
        nums.add(10);
        nums.add(1);
        nums.add(3);
        nums.add(3);

        System.out.println(nums);

        System.out.println("  ");

        for(int n : nums){
            System.out.println(n);
        }
    } 
}
