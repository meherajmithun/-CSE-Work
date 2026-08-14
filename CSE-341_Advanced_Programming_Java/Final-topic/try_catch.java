public class try_catch{
    public static void main(String[] args){
        // int i=2,j=0;
        // String s = null;
        // int arr[] = new int[5];
        // try{
        //     j = 10/i;
        //     System.out.println(arr[2]);
        //     System.out.println(s.charAt(10));
        //     System.out.println("Hello");
        // }
        // catch(ArrayIndexOutOfBoundsException e){
        //     System.out.println("Array limit exceeded");
        // }
        // catch(StringIndexOutOfBoundsException e){
        //     System.out.println("String limit Exceeded");
        // }
        // catch(NullPointerException e){
        //     System.out.println("Null");
        // }
        // catch(Exception e){
        //     System.out.println("Invalid denominator");
        // }
        
        // System.out.println(i+" -> "+j);


        int i=11,j=0;
        try{
            j = 10/i;
            if(j==0){
                throw new ArithmeticException("set Default value");
            }
        }
        catch (ArithmeticException e){
            System.out.println(e+"\nDefault value is " + i);
        }
        
        System.out.println(i+" -> "+j);
    }
}