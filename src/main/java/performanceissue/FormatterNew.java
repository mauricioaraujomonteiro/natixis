package performanceissue;

import java.io.IOException;

import java.io.PrintWriter;

import java.io.Writer;

import java.util.ArrayList;
import java.util.Collection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import java.util.Random;

import java.util.Set;
import java.util.stream.Collectors;

import java.util.stream.IntStream;



/**

 * Class responsible for formatting Row objects and write the output to a given output

 */

public class FormatterNew {

    private final Set<Element> entries;

    private final Set<Row> rows;
    private Map<Integer, List<Element>> cache = new HashMap<>();


    public static void main(String[] args) throws IOException {



        //Sample Data preparation (Random data just for the sake of simplicity for this exercise)

        Random r = new Random();

        final Set<Row> rows = IntStream.range(1, 235_000)

                .mapToObj(value -> new Row(r.nextInt(235_000)))
                .collect(Collectors.toSet());



        final Set<Element> elementList = IntStream.range(1, 1_000_000)
                .mapToObj(value -> new Element(r.nextInt(235_000)))
                .collect(Collectors.toSet());

        List<Element> t = new ArrayList<Element>(elementList);
        final boolean equals = t.get(0).equals(t.get(1));

        //Sample use case. For the sake of simplicity in this exercise, it is initialized in the main method

        final FormatterNew formatter = new FormatterNew(elementList, rows);



        try (Writer writer = new PrintWriter(System.out, false)) {

            formatter.execute(writer);

        }

    }





    public FormatterNew(Set<Element> entries, Set<Row> rows) {

        this.entries = entries;

        this.rows = rows;

    }



    public void execute(Writer writer) throws IOException {

        this.rows.stream()
                .forEach(row -> {
                    try {
                        makeFlow(row, writer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });



//        for (Row row : this.rows) {
//
//            makeFlow(row, writer);
//
//        }

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


        if (!this.cache.containsKey(row.getId())) {
            final List<Element> matchingEntries = this.entries.stream()
                    .parallel()
                    .filter(entry -> entry.getId().equals(row.getId())).collect(Collectors.toList());
            this.cache.put(row.getId(), matchingEntries);
        }

        final List<Element> elementList = cache.get(row.getId());

        elementList.stream()
                .parallel()
                .forEach(element -> {
                    final StringBuilder builder = new StringBuilder().append(row.getId())
                            .append(";")
                            .append(element.getId())
                            .append(";")
                            .append(row.getContent())
                            .append(";")
                            .append(element.getContent())
                            .append(System.lineSeparator());
                    try {
                        output.write(builder.toString());
                        output.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });




        //This call must remains here as it is

        ExternalClass.execute(this.entries, row);

    }



    static class Row {

        private final Integer id;

        private final String content;



        public Row(Integer value) {

            this.id = value;

            this.content = "Row " + value;

        }



        public Integer getId() {

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

            return Objects.equals(id, element.id) && Objects.equals(content, element.content);

        }



        @Override

        public int hashCode() {

            return Objects.hash(id, content);

        }

    }



    static class Element {

        private final Integer id;

        private final String content;



        public Element(Integer value) {

            this.id = value;

            this.content = "Data " + value;

        }



        public Integer getId() {

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

            return  Objects.equals(id, element.id) && Objects.equals(content, element.content);

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