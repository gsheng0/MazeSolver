import util.Util;

public class Tester {
    public static void f1(String str){
        str.concat("other string");
    }
    public static void main(String[] args) {

        String str = "hello world";
        f1(str);
        System.out.println(str);
    }
}