package mergetomaps;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        MergeMaps mergeMaps = new MergeMaps();
        Map<String, Long> m1 = new HashMap<>();
        m1.put("A", 1L);
        m1.put("B", 1L);
        Map<String, Long> m2 = new HashMap<>();
        m2.put("A", 1L);
        m2.put("B", 1000L);
        m2.put("C", 1000L);
        mergeMaps.solution(m1, m2);
    }
}
