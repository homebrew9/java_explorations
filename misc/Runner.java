import java.io.*;
public class Runner {
    public static void main(String[] args) {
        String username = "Eric";

        try {
            ReadMyPassword r = new ReadMyPassword();
            char[] password = r.readPassword(
              "Hey %s, enter password to arm the nuclear vessels>", username);

            System.out.println("Exposing the password now: '" + 
                new String(password) + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class ReadMyPassword{
    public char[] readPassword(String format, Object... args)
            throws IOException {
        if (System.console() != null)
            return System.console().readPassword(format, args);
        return this.readLine(format, args).toCharArray();
    }
    private String readLine(String format, Object... args) throws IOException {
        if (System.console() != null) {
            return System.console().readLine(format, args);
        }
        System.out.print(String.format(format, args));
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                System.in));
        return reader.readLine();
    }
}

