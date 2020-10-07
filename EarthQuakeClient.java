import java.util.*;

public class EarthQuakeClient {
    public EarthQuakeClient() {
        // TODO Auto-generated constructor stub
    }

    public ArrayList<QuakeEntry> filterByMagnitude(ArrayList<QuakeEntry> quakeData,
    double magMin) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for (QuakeEntry qe : quakeData) {
            if (qe.getMagnitude() > magMin) {
                answer.add(qe);
            }
        }
        // TODO
        return answer;
    }

    public ArrayList<QuakeEntry> filterByDistanceFrom(ArrayList<QuakeEntry> quakeData,
    double distMax,
    Location from) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        // TODO
        for (QuakeEntry qe : quakeData) {
            Location loc = qe.getLocation();
            //System.out.println(loc.distanceTo(from));
            if (loc.distanceTo(from) < distMax) {
                answer.add(qe);
            }
        }
        return answer;
    }

    public void dumpCSV(ArrayList<QuakeEntry> list){
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                qe.getLocation().getLatitude(),
                qe.getLocation().getLongitude(),
                qe.getMagnitude(),
                qe.getInfo());
        }

    }

    public void bigQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");
        ArrayList<QuakeEntry> qe = filterByMagnitude(list, 5.0);
        System.out.println("IS " + qe.size() + "greater");
        for(QuakeEntry temp : qe) {
            System.out.println(temp);
        }
    }

    public void closeToMe(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        //"http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");

        // This location is Durham, NC
        Location city = new Location(35.988, -78.907);
        ArrayList<QuakeEntry> qe = filterByDistanceFrom(list, 1000000.0, city);
        System.out.println("In a distance is: " + qe.size());
        // This location is Bridgeport, CA
        Location city1 =  new Location(38.17, -118.82);
        ArrayList<QuakeEntry> qe1 = filterByDistanceFrom(list, 1000000.0, city1);
        System.out.println("In a distance is: " + qe1.size());
        // TODO
    }

    public void createCSV(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
        for (QuakeEntry qe : list) {
            System.out.println(qe);
        }
    }
    
    public ArrayList<QuakeEntry> filterByDepth(ArrayList<QuakeEntry> quakeData,
    double minDepth, double maxDepth) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for (QuakeEntry qe : quakeData) {
            if (qe.getDepth() > minDepth && qe.getDepth() < maxDepth) {
                answer.add(qe);
            }
        }
        return(answer);
    }
    
    public void quakesOfDepth() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
        ArrayList<QuakeEntry> qe = filterByDepth(list, -4000.0, -2000.0);
        for (QuakeEntry temp : qe) {
            System.out.println(temp);
        }
        System.out.println("Between min and max: " + qe.size());
    }
    
    public ArrayList filterByPhrase(ArrayList<QuakeEntry> quakeData, String where,
    String phrase) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for (QuakeEntry qe : quakeData) {
            String s = qe.getInfo();
            if (where.equals("start")) {
                int i = s.indexOf(' ');
                if (s.substring(0, i).equals(phrase)) {
                    answer.add(qe);
                }
            }
            else if (where.equals("end")) {
                if (s.substring(s.lastIndexOf(" ") + 1).equals(phrase)) {
                    answer.add(qe);
                }
            }
            else if (where.equals("any")) {
                if (s.contains(phrase)) {
                    answer.add(qe);
                }
            }
        }
        return answer;
    }
    
    public void quakeByPhrase() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
        ArrayList<QuakeEntry> qe = filterByPhrase(list, "any", "Can");
        for (QuakeEntry temp : qe) {
            System.out.println(temp);
        }
        System.out.println("Found " + qe.size());
    }
}
