class A{
    public void show(){
        System.out.println("In A Class");
    }
}

class B extends A{

    @Override                  // Annotation - Telling that this method is present in A and we are overrinding it
    public void show() {
        // TODO Auto-generated method stub
        // super.show();
        System.out.println("In B Class");
    }
    
}


public class Demo {
    public static void main(String[] args){
        B obj = new B();
        obj.show();
    }
}
