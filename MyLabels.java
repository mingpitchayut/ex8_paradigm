/*  Member :
 *       Pitchayut Boonporn 6680741
 */

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
    public void updateLocation()  {
        // This method will be called after curX and curY are updated
        // This set the new bounds for the label
        setBounds(curX, curY, width, height);
        parentFrame.repaint();  // repaint the frame to show updated location

    }

    public void moveUp()          {
        // Move only if not caught and vertical movement is allowed
        if (!caught && verticalMove){
            // decrease curY
            curY -= 10;
            // Check for upperBound(Top of the sea)
            if (curY < upperBound){
                curY = upperBound;
            }
            // Call updateLocation()
            updateLocation();
        }
    }
    public void moveDown()        {
        if (!caught && verticalMove){
            // increase curY
            curY += 10;

            // Check for lowerBound (Bottom of the sea)
            if (curY + height > lowerBound){
                curY = lowerBound;
            }

            // Call updateLocation()
            updateLocation();
        }
    }
    public void moveLeft()        {
        if (!caught && horizontalMove){
            // decrease curX
            curX -= 10;

            // Handle wrapping around the left and of the frame
            if (curX + width < 0){
                curX = MyConstants.FRAME_WIDTH; // Appear on the right side
            }

            // Change icon to fish_left.png
            setIcon(iconMain);  // iconMain is set to fish_left.png btw

            // Call updateLocation()
            updateLocation();
        }
    }
    public void moveRight()       {
        if (!caught && horizontalMove){
            // increase curX
            curX += 10;

            // Handle wrapping around the right end of the frame
            if (curX > MyConstants.FRAME_WIDTH){
                curX = -width;  // Appear on the left side
            }

            // Change icon to fish_right.png
            setIcon(iconAlt);   // iconAlt = fish_right.png

            // Call updateLocation()
            updateLocation();
        }
    }

    // (2) Complete the method to make fishLabel jump away from netLabel to a
    //     random location in the sea
    //     - Also reset frame's title to indicate that fish is swimming
    public void escape()          {
        // Only escape if caught
        if (caught){
            // set caught to false
            // Generate random curX within frame width
            // Generate random curY within sea bounds (upperBound to lowerBound)
            // Set frame title to "Fish is swimming"
        }
    }
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


