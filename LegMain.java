import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LegMain {

    public static void main(String[] args) {

        String File = "./distances.csv";
        String line = "";
        String SplitBy = ",";
        List<Leg> legs = new ArrayList<>();
        // This is where the code opens the file and reads it
        try (BufferedReader br = new BufferedReader(new FileReader(File))) {

            while ((line = br.readLine()) != null) {
                // split the line into tokens
                String[] tokens = line.split(SplitBy);

                // parse the from and to tokens
                String from = tokens[0];
                String to = tokens[1];

                // parse the distance token as a double
                Double distance = Double.parseDouble(tokens[2]);

                // create a Leg object with the parsed values
                Leg leg = new Leg(from, to, distance);

                // add the Leg object to the list
                legs.add(leg);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // write the average distance for each leg to the output file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("results.csv"))) {
            for (Leg leg : legs) {
                // find the leg with the opposite direction
                String from = leg.getFrom();
                String to = leg.getTo();
                String key = to + "-" + from;
                Leg oppositeLeg = findLegByKey(legs, key);

                // if the opposite leg was found, calculate the average distance
                if (oppositeLeg != null) {
                    Double averageDistance = (leg.getDistance() + oppositeLeg.getDistance()) / 2;
                    String outputLine = from + "-" + to + "," + averageDistance;
                    bw.write(outputLine);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // helper method to find a Leg object in the list by its key
    private static Leg findLegByKey(List<Leg> legs, String key) {
        for (Leg leg : legs) {
            if (leg.getFrom() + "-" + leg.getTo().equals(key) != null) {
                return leg;
            }
        }
        return null;
    }

}
