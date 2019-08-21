package ca.mcit.bigdata.course4.project;

import ca.mcit.bigdata.course4.project.model.StopTime;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class StopTimes {

    private String filename;
    private List<StopTime> subscriberMap;

    public StopTimes(String filename) throws URISyntaxException, IOException {
        List<String> inRecords =
                Files.readAllLines(
                        Paths.get(Thread
                                .currentThread()
                                .getContextClassLoader()
                                .getResource(filename).toURI()), Charset.defaultCharset());
        // read map
        subscriberMap =
                inRecords.stream()
                        .map(StopTime::parseStopTime).collect(Collectors.toList());
    }

}
