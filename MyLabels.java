// Don't forget to rename the package
package Ex8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


abstract class BaseLabel extends JLabel
{
    protected MyImageIcon      iconMain, iconAlt;
    protected int              curX, curY, width, height;
    protected boolean          horizontalMove, verticalMove;
    protected MainApplication  parentFrame;   
    
    public BaseLabel() { }
    public BaseLabel(String file1, String file2, int w, int h, MainApplication pf)				
    { 
        width = w; height = h;
        iconMain = new MyImageIcon(file1).resize(width, height);  
	setIcon(iconMain);        
	setHorizontalAlignment(JLabel.CENTER);
        parentFrame = pf;  
        
        if (file2 != null)
            iconAlt = new MyImageIcon(file2).resize(width, height);
        else
            iconAlt = null;
    }

    public void setInitialLocation(int x, int y)
    {
        curX = x; curY = y; 
        setBounds(curX, curY, width, height);        
    }
    
    public void setMoveConditions(boolean hm, boolean vm)
    {
        horizontalMove = hm; verticalMove = vm;
    }
    
    abstract public void updateLocation(); 
    
    public void setMainIcon()      { setIcon(iconMain); }    
    public void setAltIcon()       { setIcon(iconAlt);  }
}

////////////////////////////////////////////////////////////////////////////////
class FishLabel extends BaseLabel 
{  
    protected int       upperBound, lowerBound;
    protected boolean   caught;
  
    public FishLabel(String file1, String file2, int w, int h, MainApplication pf)				
    { 
        super(file1, file2, w, h, pf);
    }
      
    public boolean isCaught()                  { return caught; }
    public void    setCaught(boolean c)        { caught = c; }
    
    public void setVerticalBounds(int up, int lo)
    {
        upperBound = up; lowerBound = lo;
    }
    
    // (1) Complete the following methods to update fishLabel's location when
    //     pressing arrow keys
    public void updateLocation()  { }    
    public void moveUp()          { }
    public void moveDown()        { }
    public void moveLeft()        { }
    public void moveRight()       { }   
    
    // (2) Complete the method to make fishLabel jump away from netLabel to a
    //     random location in the sea
    //     - Also reset frame's title to indicate that fish is swimming
    public void escape()          { }
}

////////////////////////////////////////////////////////////////////////////////
class NetLabel extends BaseLabel  //implements MouseMotionListener
{
    public NetLabel(String file1, int w, int h, MainApplication pf)				
    { 
        super(file1, null, w, h, pf);      
    }   

    // (3) Complete the method to drag NetLabel
    //     - Update its location to follow mouse cursor
    //
    //     - If overlapping with fishLabel (1st time) --> set fishLabel's caught 
    //       and change frame's title to indicate that fish is caught
    //
    //     - If fishLabel's caught is already set, update fishLabel location to 
    //       also follow mouse cursor
    //
    //     - And if fishLabel overlaps with bowlLabel --> change bowlLabel's icon
    //       (this condition + code can be put in either this class or FishLabel)

    public void updateLocation()  { }    
    
    
    // (4) Implement mouseDragged to handle event & add MouseMotionListener 
    //     to this object
}

////////////////////////////////////////////////////////////////////////////////
class BowlLabel extends BaseLabel 
{
    public BowlLabel(String file1, String file2, int w, int h, MainApplication pf)				
    { 
        super(file1, file2, w, h, pf);
    } 
 
    // (5) Complete the methods to update bowlLabel's location when pressing
    //     alphabet keys A/D
    public void updateLocation()  { }    
    public void moveLeft()        { }
    public void moveRight()       { }   
}


