/* Given a non-empty string like "Code" return a string like "CCoCodCode".

stringSplosion("Code") → "CCoCodCode"
stringSplosion("abc") → "aababc"
stringSplosion("ab") → "aab" */

public class Main {
    public static void main(String[] args){
        // take string from command line
        String s = args[0];
        s = stringSplosion(s);
        System.out.println(s);

    }

    public static String stringSplosion(String s){
        String r = new String();
        for(int i=0; i<s.length(); i++){
            r = r.concat(s.substring(0,i+1));
        }
        return r;
    }
}