package ca.mcit.bigdata.course4.project;

import ca.mcit.bigdata.course4.project.model.EnrichedStopTime;
import ca.mcit.bigdata.course4.project.model.EnrichedTrip;
import ca.mcit.bigdata.course4.project.model.StopTime;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author iraj
 * @since 9/16/18
 */
public class Runner {

    public static void main(String[] args) throws IOException, URISyntaxException {
        ProjectConfiguration conf = ProjectConfiguration.getInstance();
        HiveClient hiveClient = new HiveClient(conf);
        HBaseClient hBaseClient = new HBaseClient();
        List<EnrichedTrip> enrichedTrips = hiveClient.getEnrichedTrips();
        hBaseClient.put(enrichedTrips);
        List<StopTime> stopTimes = readStopTimes(conf.getStopTimesLocation());
        List<EnrichedStopTime> enrichedStopTimes = null;
        // Add a piece of code to get enriched trip information from HBase and instantiate an EnrichedStopTime
        // using StopTime and EnrichedTrip information
        hiveClient.load(enrichedStopTimes);
    }



    private static List<StopTime> readStopTimes(String fileName) throws URISyntaxException, IOException {
        List<String> inRecords =
                Files.readAllLines(
                        Paths.get(Objects.requireNonNull(Thread
                                .currentThread()
                                .getContextClassLoader()
                                .getResource(fileName)).toURI()), Charset.defaultCharset());
        return inRecords.stream()
                .map(StopTime::parseStopTime).collect(Collectors.toList());

    }

}
