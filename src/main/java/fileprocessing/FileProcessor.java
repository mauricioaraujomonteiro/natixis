package fileprocessing;

import com.sun.istack.internal.NotNull;
import fileprocessing.delegate.FilterDelegate;
import fileprocessing.delegate.FilterType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;


public class FileProcessor {

    private final Path source;
    private final FilterType[] filterTypes;
    private final Path fileToCompare;

    public FileProcessor(Path source, Path fileToCompare, FilterType... filterTypes) {
        if (Objects.isNull(source)) {
            throw new IllegalArgumentException("Source can not be null");
        }
        this.source = source;
        this.fileToCompare = fileToCompare;
        this.filterTypes = filterTypes;
    }


    public void process() throws IOException {

        final FileConsumerFactory fileConsumerFactory = new FileConsumerFactory();
        final Set<FilterDelegate> filters = FilterDelegate.getFilters(filterTypes);

        try (Stream<String> stream = Files.lines(source)) {

            stream.forEach(line -> {


                final boolean shouldIgnore = filters
                        .stream()
                        .anyMatch(filter -> filter.getProcessingFilter().filter(line, fileToCompare));

                if (!shouldIgnore) {
                    FileConsumer fileConsumer = fileConsumerFactory.createFileConsumer(line);

                    fileConsumer.consume(line);
                }
            });

        }

    }

    static class FileConsumerFactory {

        FileConsumer createFileConsumer(String line) {

            //Some logic here to create a valid file consumer.

            //For this example it only return a Default File Consumer

            //You don’t need to care about this method.

            return new DefaultFileConsumer();

        }

    }


    interface FileConsumer {

        void consume(String line);

    }


    static class DefaultFileConsumer implements FileConsumer {

        @Override

        public void consume(String line) {
            System.out.println(line);

            //Some code is done here, but it is not important for this exercise

        }

    }

}