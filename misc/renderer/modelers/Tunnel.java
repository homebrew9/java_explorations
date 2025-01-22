/*
 *
 * Created: Jun  7 2006
 *
 * Copyright (C) 1999-2000 Fabien Sanglard
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package renderer.modelers;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.PixelGrabber;
import renderer.Modeler;
import renderer.Renderer;

public class Tunnel implements Modeler
{
  private  int scrWidth ;
  private  int scrHeight;
  
  private static  final int TEXTUREWIDTH = 256;
  private static  final int TEXTUREHEIGHT = 256;
   
  private static int texture[] ;
  private static int distances[][] ;
  private static int angles[][]    ;
  
  private Renderer renderer;

  public Tunnel(Renderer r)
  {
    this.renderer = r ;
    texture = new int[TEXTUREHEIGHT * TEXTUREHEIGHT];
    this.scrWidth = renderer.getRenderedWidth();
    this.scrHeight = renderer.getRenderedHeight();
    
    //Image textureImage = renderer.getToolkit().getImage(this.getClass().getResource("/tex99.jpg"));  
    java.awt.Image textureImage = renderer.getImage(renderer.getCodeBase(), "tex99.jpg");
    
    PixelGrabber pixelgrabber = new PixelGrabber(textureImage, 0, 0, TEXTUREWIDTH, TEXTUREHEIGHT, texture, 0, TEXTUREWIDTH);
        try
        {
            pixelgrabber.grabPixels();
        }
        catch(InterruptedException interruptedexception) { 
          interruptedexception.printStackTrace(System.out);
          
        }
      
    distances = new int[scrWidth][scrHeight];
    angles   = new int[scrWidth][scrHeight];
  
    
    // Pregenerating the mapping pixel/coordinate.
        for(int x = 0; x < scrWidth; x++)
          for(int y = 0; y < scrHeight; y++)
          {
              distances[x][y] = (int)(  (30.0 * TEXTUREHEIGHT / Math.sqrt( 
                                  (x - scrWidth /2.0) * (x - scrWidth /2.0) + (y - scrHeight /2.0) * (y - scrHeight /2.0)
                              )
                                   ) % TEXTUREHEIGHT);
              
              angles[x][y] = (int)(0.5* TEXTUREWIDTH * Math.atan2(y - scrHeight /2.0, x - scrWidth /2.0) / Math.PI);
          }
  }

  
  static int shiftX=0;
  static int shiftY=0;
  
  static double movement =  0.1;
  static double animation = 0;
  
  public void drawOffScreen(){
    // Java timer sucks ! Don't try to make the animation platform independant (Renderer.tick) :( !
        animation += 3;        
        movement += 1;//movement + (int)Renderer.tick * 100  / 10000.0;
        
        //calculate the shift values out of the animation value
        shiftX =  (int)(TEXTUREWIDTH  + animation);
        shiftY =  (int)(TEXTUREHEIGHT +movement);// / rotation);        
        
        for(int y = 0,cursor=0; y < scrHeight; y++)
          for(int x = 0; x < scrWidth; x++,cursor++)
        { 
          renderer.offScreenRaster[cursor] =// texture[23543];
          
            texture[(distances[x][y] + shiftX)  % TEXTUREWIDTH + 
                    (((angles[x][y] + shiftY) % TEXTUREHEIGHT) * TEXTUREWIDTH)];
        }
  }

}


