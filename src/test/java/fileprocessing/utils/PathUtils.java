package fileprocessing.utils;

import fileprocessing.service.filter.CSVFilterTest;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathUtils {

    public static Path createPath(String filename) throws URISyntaxException {
        final URL resource = CSVFilterTest.class.getClassLoader().getResource(filename);
        return Paths.get(resource.toURI());
    }
}
