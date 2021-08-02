package fileprocessing;

import fileprocessing.delegate.FilterType;
import fileprocessing.utils.PathUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class FileProcessorTest {

    private final String VALID_CSV_FILE = "VALID_CSV.csv";
    private final String INVALID_CSV_FILE = "invalid.csv";
    private final String EMPTY_FILE = "empty.csv";
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void testShouldFilterOutAllRecord() throws IOException, URISyntaxException {
        final Path sourcePath = PathUtils.createPath(VALID_CSV_FILE);
        final Path excludedFile = PathUtils.createPath(INVALID_CSV_FILE);
        FileProcessor fileProcessor = new FileProcessor(sourcePath, excludedFile, FilterType.BASIC);
        fileProcessor.process();
        assertEquals("", outputStream.toString());
    }

    @Test
    public void testShouldPrintAllRecords() throws URISyntaxException, IOException {
        Path sourcePath = PathUtils.createPath(VALID_CSV_FILE);
        Path fileToComparePath = PathUtils.createPath(EMPTY_FILE);
        FileProcessor fileProcessor = new FileProcessor(sourcePath, fileToComparePath, FilterType.BASIC);
        fileProcessor.process();

        assertEquals("9004A;Lorem ipsum dolor;A85;1000.125547;8524;-1411.1;Lorem ipsum\r\n" +
                "9004A;Lorem ipsum dolor;A85;1000.125548;8524;-1411.1;Lorem ipsum\r\n" +
                "9004B;Lorem ipsum dolor;A87;1000.125549;8524;-1411.1;Lorem ipsum\r\n", outputStream.toString());
    }

    @Test
    public void testShouldPrintAllRecordsWithMultipleFilters() throws URISyntaxException, IOException {
        Path sourcePath = PathUtils.createPath(VALID_CSV_FILE);
        Path fileToComparePath = PathUtils.createPath(INVALID_CSV_FILE);
        FileProcessor fileProcessor = new FileProcessor(sourcePath, fileToComparePath, FilterType.BASIC, FilterType.MULTIPLE_FIELDS);
        fileProcessor.process();
        assertEquals("", outputStream.toString());

    }

    @Test
    public void testShouldNotAcceptNull() throws URISyntaxException  {
        Path fileToComparePath = PathUtils.createPath(INVALID_CSV_FILE);
        assertThrows(IllegalArgumentException.class, () -> new FileProcessor(null, fileToComparePath, FilterType.BASIC, FilterType.MULTIPLE_FIELDS));
    }




}
