// Constructs an ID number based on rules
import javax.swing.*;
public class ConstructID
{
  public static void main(String[] args)
  {
    String name;
    String address;
//    String stringLength;
    int stringLength;
    int x;
    name = JOptionPane.showInputDialog(null,"Enter your full name - first, middle, and last");
    address = JOptionPane.showInputDialog(null,"Enter your complete street address");
    stringLength = name.length();
//    x = Integer.parseInt(stringLength);

    StringBuffer id = new StringBuffer();
    id.append(name.charAt(0));
    for(x = 1; x < stringLength - 1; ++x)
      if(name.charAt(x) == ' ')
        id.append(name.charAt(x + 1));

    stringLength = address.length();
//    x = Integer.parseInt(stringLength);
    id.append(address.charAt(0));
    for(x = 0; x < stringLength; ++x)
      if(address.charAt(x) == ' ')
        x = stringLength;
      else
        if(Character.isDigit(address.charAt(x)))
          id.append(address.charAt(x));
System.out.println(id);

    System.exit(0);
  }
}
