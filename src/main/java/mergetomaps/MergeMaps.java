package mergetomaps;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MergeMaps {


    public void solution(Map<String, Long> m1, Map<String, Long> m2) {


        final Set<String> m1Keys = m1.keySet();
        final Set<String> m2Keys = m2.keySet();
        final Map<String, Long> merged = new HashMap<>();
        for (String key : m1Keys ){
            if (m2Keys.contains(key)) {
                merged.put(key, m2.get(key) + m1.get(key));
                continue;
            }
            merged.put(key, m1.get(key));
        }

        for (String key : m2Keys) {
            if (!m1Keys.contains(key)) {
                merged.put(key, m2.get(key));
            }
        }

        System.out.println(merged);
    }
}
