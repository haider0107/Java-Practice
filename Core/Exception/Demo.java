public class Demo {
    public static void main(String[] args) {
        
        int i = 20;

        int j = 0;

        int nums[] = new int[5];

        // String str = null;

        try{

            int result = j / i;

            if(j == 0){
                throw new ArithmeticException("Cannot divide by zero!");
            }

            System.out.println("Result = " + result);

            System.out.println(nums[0]);

            // System.out.println(str.length());
        }catch(ArithmeticException e){
            System.out.println("Cannot divide by zero!");
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Index out of bounds!");
        }catch(Exception e){
            System.out.println("Something went wrong! " + e);
        }

        System.out.println("Done!");
    }    
}
