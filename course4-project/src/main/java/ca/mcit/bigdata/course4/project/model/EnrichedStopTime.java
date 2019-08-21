package ca.mcit.bigdata.course4.project.model;

/**
 * @author iraj
 * @since 9/16/18
 */
public class EnrichedStopTime {
    private StopTime stopTime;
    private EnrichedTrip enrichedTrip;

    public EnrichedStopTime(StopTime stopTime, EnrichedTrip enrichedTrip) {
        this.stopTime = stopTime;
        this.enrichedTrip = enrichedTrip;
    }

    public StopTime getStopTime() {
        return stopTime;
    }

    public void setStopTime(StopTime stopTime) {
        this.stopTime = stopTime;
    }

    public EnrichedTrip getEnrichedTrip() {
        return enrichedTrip;
    }

    public void setEnrichedTrip(EnrichedTrip enrichedTrip) {
        this.enrichedTrip = enrichedTrip;
    }
}
