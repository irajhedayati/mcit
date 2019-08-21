package ca.mcit.bigdata.course4.project;

import ca.mcit.bigdata.course4.project.model.EnrichedStopTime;
import ca.mcit.bigdata.course4.project.model.EnrichedTrip;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author iraj
 * @since 9/16/18
 */
public class HiveClient {

    private String connectionString;
    private String driverName;

    public HiveClient(ProjectConfiguration conf) {
        this.connectionString = conf.getConnectionString();
        this.driverName = conf.getDriverName();
    }

    /**
     * Complete this method in a way that queries Hive and gets enriched trips information.
     * You need to put your query in enriched_trips.sql file under resources.
     */
    public List<EnrichedTrip> getEnrichedTrips() throws IOException {
        List<EnrichedTrip> records = new ArrayList<>();
        String joinQuery =
                IOUtils.toString(
                        this.getClass().getClassLoader().getResourceAsStream("enriched_trips.sql"),
                        "UTF-8");
        // Add code to run joinQuery and get the records into a ResultSet object
        // Follow tutorials to convert each record in ResultSet to an EnrichedTrip object and add it to the list
        return records;
    }

    /**
     * Complete this method to insert a list of enriched stop times to the appropriate partitioned table
     */
    public void load(List<EnrichedStopTime> enrichedStopTimes) {

    }
}
