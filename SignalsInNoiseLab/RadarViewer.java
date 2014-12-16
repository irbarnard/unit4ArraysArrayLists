import javax.swing.JFrame;
import java.util.Scanner;
/**
 * Class that contains the main method for the program and creates the frame containing the component.
 * 
 * @author @gcschmit
 * @version 19 July 2014
 */
public class RadarViewer
{
    /**
     * main method for the program which creates and configures the frame for the program
     *
     */
    public static void main(String[] args) throws InterruptedException
    {
       
        //create the radar, set the monster location, and perform the initial scan
        Scanner in = new Scanner(System.in);
        
        System.out.println("Please enter the amount of rows. ");
        final int ROWS = in.nextInt();
        System.out.println("Please enter the amount of columns. ");
        final int COLS = in.nextInt();
        System.out.println("Please enter the change in x. ");
        final int DX = in.nextInt();
        System.out.println("Please enter the change in y. ");
        final int DY = in.nextInt();
        System.out.println("Please enter the starting rows for the monster. ");
        final int MONSTERR = in.nextInt();
        System.out.println("Please enter the starting columns for the monster. ");
        final int MONSTERC = in.nextInt();
        System.out.println("Please enter the noise fraction. ");
        double NOIZE = in.nextDouble();
        
        Radar radar = new Radar(ROWS, COLS,DX, DY, MONSTERR, MONSTERC, NOIZE );

        radar.setNoiseFraction(NOIZE);
        
        JFrame frame = new JFrame();
        
        frame.setTitle("Signals in Noise Lab");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // a frame contains a single component; create the radar component and add it to the frame
        RadarComponent component = new RadarComponent(radar);
        frame.add(component);
        
        // set the size of the frame to encompass the contained component
        frame.pack();
        
        // make the frame visible which will result in the paintComponent method being invoked on the
        //  component.
        frame.setVisible(true);
        
        // perform 100 scans of the radar wiht a slight pause between each
        // after each scan, instruct the Java Run-Time to redraw the window
        for(int i = 0; i < 100; i++)
        {
            Thread.sleep(1); // sleep 100 milliseconds (1/10 second)
            
            radar.scan();
            
            frame.repaint();
        }
        String veloc = radar.getVelocity();
        System.out.println(veloc);
    }

}
