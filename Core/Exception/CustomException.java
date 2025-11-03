class KBException extends Exception{
    public KBException(String msg){
        super(msg);
    }
}

public class CustomException {
    public static void main(String[] args) {
        int i = 20;

        int j = 0;

         try{

            j = j / i;

            if(j == 0){
                throw new KBException("Cannot divide by zero!");
            }

            System.out.println("Result = " + j);

            // System.out.println(nums[0]);

            // System.out.println(str.length());
        }catch(KBException e){
            j = 18/i;
            System.out.println("That's the default value = " + j + " " + e);
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Index out of bounds!");
        }catch(Exception e){
            System.out.println("Something went wrong! " + e);
        }
    }
}
