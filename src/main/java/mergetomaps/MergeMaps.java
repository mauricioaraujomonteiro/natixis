package mergetomaps;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MergeMaps {


    public void solution(Map<String, Long> m1, Map<String, Long> m2) {


        final Map<String, Long> merged = Stream.of(m1, m2)
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1 + v2));

        System.out.println(merged);
    }
}
