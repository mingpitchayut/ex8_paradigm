/*  Member :
 *       Pitchayut Boonporn 6680741
 */

// Don't forget to rename the package
package Ex8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;


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
            caught = false;

            // Generate random curX within frame width
            Random random = new Random();
            curX = random.nextInt(MyConstants.FRAME_WIDTH - width);

            // Generate random curY within sea bounds (upperBound to lowerBound)
            curY = random.nextInt(lowerBound - upperBound - height) + upperBound;

            // Update Location
            updateLocation();

            // Set frame title to "Fish is swimming"
            parentFrame.setTitle("Fish is swimming");
        }
    }
}

////////////////////////////////////////////////////////////////////////////////
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

class NetLabel extends BaseLabel  implements MouseMotionListener
{
    private int offsetX, offsetY;     // We will use this in the MouseListener method

    public NetLabel(String file1, int w, int h, MainApplication pf)				
    { 
        super(file1, null, w, h, pf);
        // Make NetLabel listen to mouse motion events
        addMouseMotionListener(this);
        addMouseListener(this);
    }   


    // (4) Implement mouseDragged to handle event & add MouseMotionListener
    //     to this object

    @Override
    public void mouseDragged(MouseEvent e){
        // This method will call when the mouse is dragged over this component

        // Update Net's position - Get the current mouse coordinates relative to the NetLabel
        // Make the center of the label follow the mouse by halving its width and height
        curX = e.getX() - (width/2);
        curY = e.getY() - (height/2);

        // Make the Net stays within the boundary
        if (curX < 0) curX = 0;
        if (curX + width > MyConstants.FRAME_WIDTH) curX = MyConstants.FRAME_WIDTH - width;
        if (curY < 0) curY = 0;
        if (curY + height > MyConstants.FRAME_HEIGHT) curY = MyConstants.FRAME_HEIGHT - height;

        // Update net's location
        updateLocation();

        FishLabel fishLabel = parentFrame.getFishLabel();
        BowlLabel bowlLabel = parentFrame.getBowlLabel();

        // Check for Fish Overlap(Catching)
        if (getBounds().intersects(fishLabel.getBounds())){         // If NetBounds intersect with fishBound
            if (!fishLabel.isCaught()){                 // If the fish is not caught yet,
                fishLabel.setCaught(true);                  // set fish as caught
                parentFrame.setTitle("Fish is caught");     // Change frame title
            }
        }

        // Move caught fish with Net
        if (fishLabel.isCaught()){  // If fish get caught already,
            // Make the fish follows the net, and plus some offsets
            fishLabel.curX = this.curX + (this.width / 2) - (fishLabel.width / 2);
            fishLabel.curY = this.curY + (this.height / 2) - (fishLabel.height / 2);

            // Make the fish stays within the frame bounds if moved by the net
            if (fishLabel.curX < 0) fishLabel.curX = 0;
            if (fishLabel.curX + fishLabel.width > MyConstants.FRAME_WIDTH) fishLabel.curX = MyConstants.FRAME_WIDTH - fishLabel.width;
            if (fishLabel.curY < 0) fishLabel.curY = 0;
            if (fishLabel.curY + fishLabel.height > MyConstants.FRAME_HEIGHT) fishLabel.curY = MyConstants.FRAME_HEIGHT - fishLabel.height;

            // Update fish's location
            fishLabel.updateLocation();

            // Check for fish-bowl overlap when the fish gets caught
            if (fishLabel.getBounds().intersects(bowlLabel.getBounds())){   // If the fish and the bowl are intersect,
                bowlLabel.setAltIcon(); // Change bowl to sashimi icon
            } else {
                bowlLabel.setMainIcon();    // Change it back to the rice icon
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e){
        // This method will call when the mouse is moved over this component without clicking
        // We don't need to config this
    }

    public void updateLocation()  {
        // Update Net's visual location
        setBounds(curX, curY, width, height);

        // Repaint the frame to show updated location
        parentFrame.repaint();
    }

    // MouseListener Methods (Overridden)
    @Override
    public void mousePressed(MouseEvent e){
        // Store the offset from NetLabel's top-left corner to the mouse click point
        offsetX = e.getX();
        offsetY = e.getY();
    }




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
    public void updateLocation()  {
        // Set the new bounds for the label
        setBounds(curX, curY, width, height);

        // Repaint the frame to show the updated location
        parentFrame.repaint();
    }
    public void moveLeft()        {
        if (horizontalMove){
            // Move to the left
            curX -= 10;

            // Handle wrapping around the left end of the frame
            if (curX + width < 0){
                curX = MyConstants.FRAME_WIDTH;     // Appear it to the right side
            }

            // Update Location
            updateLocation();
        }
    }
    public void moveRight()       {
        if (horizontalMove){
            // Move to the Right
            curX += 10;

            // Handle wrapping around the right end of the frame
            if(curX > MyConstants.FRAME_WIDTH){
                curX = -width;                      // Appear on the left side
            }

            // Update Location
            updateLocation();
        }
    }
}


