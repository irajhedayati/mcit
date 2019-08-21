package ca.mcit.bigdata.course4.project;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * @author iraj
 * @since 9/16/18
 */
public class ProjectConfiguration {

    private static ProjectConfiguration instance = null;

    private String connectionString;
    private String driverName;
    private String stopTimeFileLocation;

    public static ProjectConfiguration getInstance() {
        if (instance == null) {
            instance = new ProjectConfiguration();
        }
        return instance;
    }

    private ProjectConfiguration() {
        Config config = ConfigFactory.load();
        this.connectionString = config.getString("hive.jdbc.connection.string");
        this.driverName = config.getString("hive.jdbc.driver");
        this.stopTimeFileLocation = config.getString("stop_time.location");
    }

    public String getConnectionString() {
        return connectionString;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getStopTimesLocation() {
        return null;
    }
}
