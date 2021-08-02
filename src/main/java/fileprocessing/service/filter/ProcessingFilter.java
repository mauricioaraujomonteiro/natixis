package fileprocessing.service.filter;

import java.nio.file.Path;

public interface ProcessingFilter {

    boolean filter(String value, Path excludedFile);
}
