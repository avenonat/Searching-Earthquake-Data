import java.util.*;
/**
 * Write a description of class LargestQuakes here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class LargestQuakes
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class LargestQuakes
     */
    public LargestQuakes()
    {
        // initialise instance variables
        x = 0;
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public void findLargestQuakes()
    {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size());
        ArrayList<QuakeEntry> res = getLargest(list, 50);
        for (QuakeEntry qe : res) {
            System.out.println(qe);
        }
    }
    
    public int indexOfLargest(ArrayList<QuakeEntry> data) {
        int num = 0;
        double mad = 0.0;
        int i = 0;
        for (QuakeEntry qe : data) {
            if (qe.getMagnitude() > mad) {
                mad = qe.getMagnitude();
                num = i;
            }
            i++;
        }
        return num;
    }
    
    public ArrayList<QuakeEntry> getLargest(ArrayList<QuakeEntry> quakeData, int howMany) {
        ArrayList<QuakeEntry> large = new ArrayList<QuakeEntry>(quakeData);
        ArrayList<QuakeEntry> res = new ArrayList<QuakeEntry>();
        for (int i = 0; i < howMany; i++) {
            int indexMax  = indexOfLargest(large);
            res.add(large.get(indexMax));
            large.remove(indexMax);
        }
        return res;
    }
}
