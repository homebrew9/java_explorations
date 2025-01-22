package renderer.modelers;

public class Vector {

  public float x;
  public float y;
  public float z;
  
  public Vector(float x, float y, float z)
  {
    this.x = x;
    this.y = y;
    this.z = z;
  }
  
  public String toString()
  {
    StringBuffer out = new StringBuffer();
    out.append("x=");
    out.append(x);
    out.append("\n");
    out.append("y=");
    out.append(y);
    out.append("\n");
    out.append("z=");
    out.append(z);
    out.append("\n");
    return out.toString();
  }
  
  public void display()
  {
    System.out.println(this);
  }
}

