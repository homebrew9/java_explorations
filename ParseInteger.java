public class ParseInteger {
    public static void main (String[] args) {
        if (args.length == 1) {
            String strParam = args[0];
            System.out.println("Input string parameter                  = [" + strParam + "]");
            System.out.print  ("Integer value of input string parameter = ");
            System.out.println(Integer.parseInt(strParam));
        }
    }
}
