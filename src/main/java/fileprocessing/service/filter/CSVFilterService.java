package fileprocessing.service.filter.impl;

import fileprocessing.service.filter.ProcessingFilter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVFilterService implements ProcessingFilter {

    private static final String DELIMITER = ";";
    private final Integer[] columnsToFilter;
    private final Map<String, Set<String>> cache = new HashMap<>();

    public CSVFilterService(Integer... columnsToFilter) {
            this.columnsToFilter = columnsToFilter;
    }

    @Override
    public boolean filter(String value, Path excludedFile) {
        final Set<String> values = getValues(excludedFile);

        return Arrays.stream(columnsToFilter)
                .parallel()
                .map(column -> getFieldValue(value, column))
                .anyMatch(toFilter -> values.contains(toFilter));
    }

    private String getFieldValue(String value, Integer column) {
        final String[] split = value.split(DELIMITER);
        if (column >= split.length){
            throw new IllegalArgumentException(String.format("Line does not have %s columns", column));
        }
        return split[column];
    }

    private Set<String> getValues(Path fileToCompare) {
        try(Stream<String> stream = Files.lines(fileToCompare)) {

            String filename = fileToCompare.getFileName().toString();

            if (this.cache.containsKey(filename)) return cache.get(filename);
                final Set<String> collect = stream
                        .collect(Collectors.toSet());
                cache.put(filename, collect);
                return collect;
        } catch (IOException e) {
            return Collections.emptySet();
        }
    }
}
