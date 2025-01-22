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

package renderer;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.MemoryImageSource;

import renderer.modelers.*;


public class Renderer extends Applet implements  Runnable
{

  private static final long serialVersionUID = 1L;

  private Modeler modeler = null;
    
  Image vramBundle;
  public MemoryImageSource source ;
  public int[] offScreenRaster ;
  
  public final static int renderedWidth  = 640;
  public final static int renderedHeight = 480;
  
  public int drawWidth  = 640;
    public int drawHeight = 480;
  
    public void start()
    {
      new Thread(this).start();
    }
    
    
    
    
    public void init()
    {
      this.setSize(drawWidth,drawHeight);
      
        setBackground(Color.black);
        
    
     ColorModel colorModel = new DirectColorModel(32, 0xff0000, 0x00ff00, 0x0000ff, 0);
    
        offScreenRaster = new int[renderedWidth*renderedHeight];
        
        source = new MemoryImageSource(renderedWidth, renderedHeight, colorModel , offScreenRaster, 0, renderedWidth);
    source.setAnimated(true);
    source.setFullBufferUpdates(true);
    vramBundle  = createImage(source);
        
    this.modeler = new Tunnel(this);
    }
  
  
  
  long  now = 0;
  long  before = 0;
  //float framespd = 0;
  int fps = 0 ;
  public static long tick;
  
   public void run()
      {
     before = (int)System.currentTimeMillis();
        
          while(true) 
          {
              repaint();
              now = (int)System.currentTimeMillis();
              tick = now - before;
              if(now - before > 1000)
              {
                  fps = frameCounter;
                  before = now;
                  frameCounter = 0;
              }
              
              try
              {
                  Thread.sleep(20L);
              }
              catch(InterruptedException interruptedexception) { }
              
          }
      }
   
    public void update(Graphics g)
      {
          paint(g);
      }
    
    int frameCounter;
    boolean showFps = true;
    public void paint(Graphics g)
      {
        
        frameCounter++;
              this.modeler.drawOffScreen();
              source.newPixels();
              g.drawImage(vramBundle, 0, 0, getWidth(),getHeight(), this);
            
              if(showFps)
              {
                  g.setColor(Color.white);
                  g.drawString("fps : " + fps, 5, 15);
              }
          
      }

  public static int  getRenderedHeight() {
    return renderedHeight;
  }



  public static int getRenderedWidth() {
    return renderedWidth;
  }

}

