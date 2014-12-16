import java.util.Scanner;
/**
 * The model for radar scan and accumulator
 * 
 * @author @gcschmit
 * @version 19 July 2014
 */
public class Radar
{
    
    // stores whether each cell triggered detection for the current scan of the radar
    private boolean[][] currentScan;
    private boolean [][] previousScan;
    // value of each cell is incremented for each scan in which that cell triggers detection 
    private int[][] accumulator;
    // location of the monster
    private int monsterLocationRow;
    private int monsterLocationCol;
    private int dx;
    private int dy;
    // probability that a cell will trigger a false detection (must be >= 0 and < 1)
    private double noiseFraction;
    
    // number of scans of the radar since construction
    private int numScans;

    /**
     * Constructor for objects of class Radar
     * 
     * @param   rows    the number of rows in the radar grid
     * @param   cols    the number of columns in the radar grid
     */
    public Radar(int ROWS, int COLS, int DX, int DY, int MONSTERR, int MONSTERC, double NOIZE)
    {
        // initialize instance variables
        dx = DX;
        dy = DY;
        monsterLocationRow = MONSTERR; 
        monsterLocationCol = MONSTERC;
        
        currentScan = new boolean[ROWS][COLS];
        previousScan = new boolean[ROWS][COLS];// elements will be set to false
        accumulator = new int[11][11]; // elements will be set to 0
       
        // randomly set the location of the monster (can be explicity set through the
        //  setMonsterLocation method
        
        noiseFraction = NOIZE;
        numScans= 0;
    }
    
    /**
     * Performs a scan of the radar. Noise is injected into the grid and the accumulator is updated.
     * 
     */
    public void scan()
    { 
        for(int rrr = 0; rrr < currentScan.length; rrr++)
        {
            for(int ccc = 0; ccc < currentScan[0].length; ccc++)
            {
                currentScan[rrr][ccc] = false;
            }        
        }
        
        
        // detect the monster
        
        
        if(monsterLocationRow < currentScan[0].length)
        {
            currentScan[monsterLocationRow][monsterLocationCol] = true;
            monsterLocationRow += dy;
            monsterLocationCol += dx;
            if(monsterLocationCol == currentScan[0].length)
            {
                monsterLocationRow = 0;
                monsterLocationCol = 0;
            }
            
        }
        // inject noise into the grid
        injectNoise();
           
        
        //udpate the accumulator
        if (numScans > 0)
        {
            for(int row = 0; row < previousScan.length; row++)
            {
                for(int col = 0; col < previousScan[0].length; col++)
                {
                    if(previousScan[row][col] == true)
                    {
                       for (int parallel = 0; parallel < currentScan.length; parallel++)
                       {
                           for (int perpindicular = 0; perpindicular < currentScan[0].length; perpindicular++)
                           {
                               if(currentScan[parallel][perpindicular] == true)
                               {
                                   int yDifference = ((parallel - row)+ 5);
                                   int xDifference = ((perpindicular - col)+5);
                                   if((yDifference > 0 && yDifference <= 10) && (xDifference > 0 && xDifference <= 10))
                                       {
                                           //less than 10
                                           accumulator[yDifference][xDifference]++;
                                        }
                                } 
                           }                           
                        }
                    }                    
                }                
            }
        }
        
        for(int i=0; i<currentScan.length; i++)
            {
                for(int j=0; j<currentScan[i].length; j++)
                {
                    previousScan[i][j] = currentScan[i][j];
                }
            }
        
        //keep track of the total number of scans
        numScans++;
    }

    /**
     * Sets the location of the monster
     * 
     * @param   row     the row in which the monster is located
     * @param   col     the column in which the monster is located
     * @pre row and col must be within the bounds of the radar grid
     */
    public void setMonsterLocation(int row, int col)
    {
        // remember the row and col of the monster's location
        monsterLocationRow = row;
        monsterLocationCol = col;
        
        // update the radar grid to show that something was detected at the specified location
        currentScan[row][col] = true;
    }
    
     /**
     * Sets the probability that a given cell will generate a false detection
     * 
     * @param   fraction    the probability that a given cell will generate a flase detection expressed
     *                      as a fraction (must be >= 0 and < 1)
     */
    public void setNoiseFraction(double fraction)
    {
        noiseFraction = fraction;
    }
    
    /**
     * Returns true if the specified location in the radar grid triggered a detection.
     * 
     * @param   row     the row of the location to query for detection
     * @param   col     the column of the location to query for detection
     * @return true if the specified location in the radar grid triggered a detection
     */
    public boolean isDetected(int row, int col)
    {
        return currentScan[row][col];
    }
    
    /**
     * Returns the number of times that the specified location in the radar grid has triggered a
     *  detection since the constructor of the radar object.
     * 
     * @param   row     the row of the location to query for accumulated detections
     * @param   col     the column of the location to query for accumulated detections
     * @return the number of times that the specified location in the radar grid has
     *          triggered a detection since the constructor of the radar object
     */
    public int getAccumulatedDetection(int row, int col)
    {
        return accumulator[row][col];
    }
    
    /**
     * Returns the number of rows in the radar grid
     * 
     * @return the number of rows in the radar grid
     */
    public int getNumRows()
    {
        return currentScan.length;
    }
    
    /**
     * Returns the number of columns in the radar grid
     * 
     * @return the number of columns in the radar grid
     */
    public int getNumCols()
    {
        return currentScan[0].length;
    }
    
    /**
     * Returns the number of scans that have been performed since the radar object was constructed
     * 
     * @return the number of scans that have been performed since the radar object was constructed
     */
    public int getNumScans()
    {
        return numScans;
    }
    
    /**
     * Returns the velocity of the monster.
     * Velocity is found by comparing all the values in the accumulator, the one with the highest value (most reoccuring dx, dy) is kept,
     * to find velocity take change in y and divide it by the change in x
     */
    public String getVelocity()
    {
        int current_max = accumulator[0][0];
        String vString = "";
        for (int row = 0; row< accumulator.length; row++)
        {
            for (int col = 0; col< accumulator[0].length; col++)
            {
                if((accumulator[row][col]> current_max) && ((row != 0)&&(col != 0)))
                {
                    current_max = accumulator[row][col];
                    int xmax = row - 5;
                    int ymax = col - 5;
                    vString = "Dy is " + xmax + ", Dx is "  + ymax;

                }
            }
        }
        return vString; 
    }
    
    /**
     * Sets cells as falsely triggering detection based on the specified probability
     * 
     */
    private void injectNoise()
    {
        for(int row = 0; row < currentScan.length; row++)
        {
            for(int col = 0; col < currentScan[0].length; col++)
            {
                // each cell has the specified probablily of being a false positive
                if(Math.random() < noiseFraction)
                {
                    currentScan[row][col] = true;
                }
            }
        }
    }

}
