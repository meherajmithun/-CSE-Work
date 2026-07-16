import java.util.*;

public class test{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        // madaam
        // int[] arr = new int[26];

        // for(int i=0; i<s.length(); i++){
        //     int a = s.charAt(i)-'a';
        //     // System.out.println(a);
        //     arr[a]++;
        // }
        // for(int i=0; i<s.length(); i++){
        //     int a = s.charAt(i)-'a';
        //     // System.out.println(a);
        //     char c = (char)(a+'a');
            
        //     if(arr[a]>0) {
        //         System.out.println(c+" "+arr[a]);
        //         arr[a]=0;    
        //     }
            
        // }


        // int r=s.length()-1;
        // int l=0;
        // int flag=0;
        // while(l<r){
        //     if(s.charAt(l)!=s.charAt(r)){
        //         flag=1; break;
        //     }
        //     l++; r--;
        // }
        // if(flag==1){
        //     System.out.println("Not palindrome");
        // }
        // else {
        //     System.out.println("Palindrome");
        // }

        HashSet<Character>st = new HashSet<>();
        for(int i=0; i<s.length(); i++){
            char c = s.charAt(i);
            st.add(c);
        }
        System.out.println(st);
    }
}