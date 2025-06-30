/*  Member :
 *       Pitchayut Boonporn 6680741
 */


// Don't forget to rename the package
package Ex8;

import java.awt.Image;
import javax.swing.ImageIcon;


// Interface for keeping constant values
interface MyConstants
{
    //----- Resource files
    static final String PATH             = "resources/";
    static final String FILE_BG          = PATH + "background.jpg"; 
    static final String FILE_BOWL_MAIN   = PATH + "bowl_rice.png";
    static final String FILE_BOWL_ALT    = PATH + "bowl_fish.png";    
    static final String FILE_FISH_LEFT   = PATH + "fish_left.png";
    static final String FILE_FISH_RIGHT  = PATH + "fish_right.png";
    static final String FILE_NET_MAIN    = PATH + "net.png";     
    
    //----- Sizes and locations
    static final int FRAME_WIDTH   = 750;
    static final int FRAME_HEIGHT  = 600;
    static final int SEPARATOR_Y   = 200;
    static final int LABEL_WIDTH   = 100;
    static final int LABEL_HEIGHT  = 80;


}


// Auxiliary class to resize image
class MyImageIcon extends ImageIcon
{
    public MyImageIcon(String fname)  { super(fname); }
    public MyImageIcon(Image image)   { super(image); }

    public MyImageIcon resize(int width, int height)
    {
	Image oldimg = this.getImage();
	Image newimg = oldimg.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new MyImageIcon(newimg);
    }
}