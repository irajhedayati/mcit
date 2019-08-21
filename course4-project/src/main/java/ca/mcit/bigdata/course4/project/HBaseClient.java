package ca.mcit.bigdata.course4.project;

import ca.mcit.bigdata.course4.project.model.EnrichedTrip;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author iraj
 * @since 9/16/18
 */
public class HBaseClient {

    private Table enrichedTripTable;

    /**
     * Complete this code to instantiate {@code enrichedTripTable} and connect to C4P:TRIP table in HBase.
     */
    public HBaseClient() {

    }

    /**
     * Complete this method to convert an {@link EnrichedTrip} object to an HBase {@link Put} object
     */
    private Put convertEnrichedTripToPut(EnrichedTrip enrichedTrip) {
        return null;
    }

    /**
     * Complete this method to get list of enriched trip records, convert them to list of Put objects and put all
     * in an HBase table
     * @param records
     */
    public void put(List<EnrichedTrip> records) throws IOException {
        List<Put> putList = records.stream().map(this::convertEnrichedTripToPut).collect(Collectors.toList());
        this.enrichedTripTable.put(putList);
    }

    /**
     * Complete this method that gets a trip ID and returns the latest version of the row corresponding to that
     * ID from Hbase. This method will be used in stop time enrichment.
     */
    public EnrichedTrip get(String tripId) {
        return null;
    }

}
