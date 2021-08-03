package uniqchars;

import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UniqueChar {

    public long solution(String value) {
        if (Objects.isNull(value)) return 0;

        final IntStream chars = value.chars();

        final HashMap<Character, Long> collect = chars.mapToObj(i -> (char) i)
                .collect(Collectors.groupingBy(c -> c, HashMap::new, Collectors.counting()));

        return collect.values()
                .stream()
                .filter(v -> v.equals(1L))
                .count();
    }
}
