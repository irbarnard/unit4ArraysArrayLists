

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class Test1.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class Test1
{
    /**
     * Default constructor for test class Test1
     */
    public Test1()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
    @Test
    public void testCase1()
    {
        /** 
         * 
         * not sure how to correctly create a test.. i think im close, but i cant seem to get rid of a few errors
         * 
         * 
         */
        final int ROWS = 100;
        final int COLS = 100;
        final int DX = 1;
        final int DY = 1;
        int MONSTERR = 0;
        int MONSTERC = 0;
        double NOIZE = 0.01;
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
    public void testCase2()
    {
        
        final int ROWS = 10;
        final int COLS = 10;
        final int DX = 2;
        final int DY = 3;
        int MONSTERR = 3;
        int MONSTERC = 4;
        double NOIZE = 0.01;
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
        for(int i = 0; i < 10; i++)
        {
            Thread.sleep(1); // sleep 100 milliseconds (1/10 second)
            
            radar.scan();
            
            frame.repaint();
        }
        String veloc = radar.getVelocity();
        System.out.println(veloc);
    }
}
