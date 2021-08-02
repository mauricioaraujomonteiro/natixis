package performanceissue;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;



/**

 * Class responsible for formatting Row objects and write the output to a given output

 */

public class Formatter {

    public static final String DELIMITER = ";";
    private final Collection<Element> entries;

    private final Collection<Row> rows;
    private Map<Integer, Set<Element>> cache = new HashMap<>();


    public static void main(String[] args) throws IOException {



        //Sample Data preparation (Random data just for the sake of simplicity for this exercise)

        Random r = new Random();

        final Collection<Row> rows = IntStream.range(1, 235_000)

                .mapToObj(value -> new Row(r.nextInt(235_000))).collect(Collectors.toSet());



        final Collection<Element> elementList = IntStream.range(1, 1_000_000)

                .mapToObj(value -> new Element(r.nextInt(235_000))).collect(Collectors.toSet());





        //Sample use case. For the sake of simplicity in this exercise, it is initialized in the main method

        final Formatter formatter = new Formatter(elementList, rows);



        try (Writer writer = new PrintWriter(System.out, false)) {

            formatter.execute(writer);

        }

    }





    public Formatter(Collection<Element> entries, Collection<Row> rows) {

        this.entries = entries;

        this.rows = rows;

    }



    public void execute(Writer writer) throws IOException {

        for (Row row : this.rows) {

            makeFlow(row, writer);

        }

    }



    /**

     * <p>

     * Method that formats lines based on the content of the given {@code Row} and based on the content of the given

     * list of {@code Element}. The line is formatted as follow:

     * </p>

     * <p>

     * For each {@code Element id} that matches a {@code Row id}, a new line is written to the output. Each line is

     * separated by a ';'

     * </p>

     */

    void makeFlow(Row row, Writer output) throws IOException {

        //Extracts only the elements which id matches the row.getId()
        if (cache.isEmpty()) {
            final HashMap<Integer, Set<Element>> collect = this.entries.stream()
                    .collect(Collectors.groupingBy(Element::getId, HashMap::new, Collectors.toSet()));
            this.cache.putAll(collect);
        }

        final Collection<Element> matchingEntries = cache.get(row.getId());


        if (Objects.isNull(matchingEntries)) return;

        for (Element element : matchingEntries) {

            StringBuilder builder = new StringBuilder()
                    .append(row.getId())
                    .append(DELIMITER)
                    .append(element.getId())
                    .append(DELIMITER)
                    .append(row.getContent())
                    .append(DELIMITER)
                    .append(element.getContent())
                    .append(System.lineSeparator());
            output.write(builder.toString());
            output.flush();

        }



        //This call must remains here as it is

        ExternalClass.execute(this.entries, row);

    }



    static class Row {

        private final int id;

        private final String content;



        public Row(int value) {

            this.id = value;

            this.content = "Row " + value;

        }



        public int getId() {

            return id;

        }



        public String getContent() {

            return content;

        }



        @Override

        public boolean equals(Object o) {

            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            Row element = (Row) o;

            return id == element.id && Objects.equals(content, element.content);

        }



        @Override

        public int hashCode() {

            return Objects.hash(id, content);

        }

    }



    static class Element {

        private final int id;

        private final String content;



        public Element(int value) {

            this.id = value;

            this.content = "Data " + value;

        }



        public int getId() {

            return id;

        }



        public String getContent() {

            return content;

        }



        @Override

        public boolean equals(Object o) {

            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            Element element = (Element) o;

            return id == element.id && Objects.equals(content, element.content);

        }



        @Override

        public int hashCode() {

            return Objects.hash(id, content);

        }

    }



    static class ExternalClass {

        public static void execute(Collection<Element> elementList, Row row) {

            //do something that does not matter to the context of this exercise

        }

    }

}