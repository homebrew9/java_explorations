public class MidInitials
{
public static void main(String[] args)
{
System.out.println("Enter your name:");
String name = IO.readString();
int spaceindex1 = name.indexOf(" ");
int lastspaceindex = name.lastIndexOf(" ");
String firstname = name.substring(0, spaceindex1);
String firstletter = firstname.substring(0,1);
firstletter = firstletter.toUpperCase();
firstname = firstname.substring(1,spaceindex1);
firstname = firstletter + firstname;
System.out.println(firstname);
String lastname = name.substring(lastspaceindex + 1);
System.out.println(lastname);
String middlename = name.substring(spaceindex1 + 1,lastspaceindex);
System.out.println(middlename);
//String midinitial = middlename.substring(0,1) + ".";
// System.out.println(midinitial);
String fullname = firstname;
if ( middlename.contains(" "))
{
while (spaceindex1 != -1)
{
System.out.println(midname1);
String midinitial = middlename.substring(0,1) + ".";
System.out.println(midinitial);
fullname = fullname + " " + midinitial + " ";
System.out.println(fullname);
middlename = middlename.substring(spaceindex1);
middlename = middlename.trim();
System.out.println(middlename);
}
fullname = fullname + " " + lastname;
System.out.println(fullname);
}
else
{
String midinitial = middlename.substring(0,1) + ".";
fullname = fullname+ " " + midinitial + " " + lastname;
System.out.println(fullname);
IO.outputStringAnswer(fullname);
}
}
}
