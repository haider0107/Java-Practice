public class MyString {
    public static void main(String[] args) {
        // String s = "Hello world";
        // System.out.println(s);

        StringBuffer sb = new StringBuffer("Hello world");
        System.out.println(sb.capacity());

        sb.append(" nice to meet you");
        System.out.println(sb.capacity());
    }  
}
