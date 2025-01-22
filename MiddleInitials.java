/*
Ask the user for a person's full name (one string). Output a version of their
name where all but their first and last name are replaced by initials (one
string). Your output should properly capitalize the first name, last name,
and initials.
Examples:
java MiddleInitials
franklin delano roosevelt
RESULT: "Franklin D. Roosevelt"
java MiddleInitials
Christian Dennis Richard Jack Harold Leonard Hobbs
RESULT: "Christian D. R. J. H. L. Hobbs"
*/

import java.util.Scanner;
public class MiddleInitials {
  public static void main (String[] args) {
    StringBuffer newName = new StringBuffer();
    System.out.print("Enter your full name: ");
    Scanner scnr = new Scanner(System.in);
    String username = scnr.nextLine();
    String[] result = username.split("\\s");
    for (int x=0; x<result.length; x++)
      if (x==0)
        newName.append(Character.toUpperCase(result[x].charAt(0))+result[x].substring(1));
      else if (x==result.length-1)
        newName.append(" "+Character.toUpperCase(result[x].charAt(0))+result[x].substring(1));
      else
        newName.append(" "+Character.toUpperCase(result[x].charAt(0))+".");
    System.out.println(newName);
  }
}
