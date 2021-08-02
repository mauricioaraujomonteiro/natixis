package fileprocessing.service.filter;

import fileprocessing.utils.PathUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class CSVFilterTest {

    public static final String INVALID_CSV = "invalid.csv";

    @Test
    public void testFilterWithOneColumn() throws URISyntaxException {
        final fileprocessing.service.filter.impl.CSVFilterService csvFilter = new fileprocessing.service.filter.impl.CSVFilterService(0);
        final Path path = PathUtils.createPath(INVALID_CSV);
        final boolean filter = csvFilter.filter("A87", path);
        assertTrue(filter);
    }

    @Test
    public void testFilterWithTwoColumn() throws URISyntaxException {
        final fileprocessing.service.filter.impl.CSVFilterService csvFilter = new fileprocessing.service.filter.impl.CSVFilterService(1,2);
        final Path path = PathUtils.createPath(INVALID_CSV);
        assertTrue(csvFilter.filter("CODE1;A87;CODE2", path));
        assertTrue(csvFilter.filter("CODE1;CODE2;A87", path));
    }

    @Test
    public void testThrowErrorForInvalidColumn() throws URISyntaxException {
        final fileprocessing.service.filter.impl.CSVFilterService csvFilter = new fileprocessing.service.filter.impl.CSVFilterService(3);
        final Path path = PathUtils.createPath(INVALID_CSV);
        assertThrows(IllegalArgumentException.class, () ->csvFilter.filter("CODE1;A87;CODE2", path));
    }

    @Test
    public void testUsingCache() throws URISyntaxException {

        final fileprocessing.service.filter.impl.CSVFilterService csvFilter = new fileprocessing.service.filter.impl.CSVFilterService(3);
        final URL resource = CSVFilterTest.class.getClassLoader().getResource(INVALID_CSV);
        Path path = Paths.get(resource.toURI());
        assertThrows(IllegalArgumentException.class, () ->csvFilter.filter("CODE1;A87;CODE2", path));
        assertThrows(IllegalArgumentException.class, () ->csvFilter.filter("CODE1;A87;CODE2", path));

    }


}
