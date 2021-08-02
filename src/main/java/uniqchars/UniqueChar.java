package uniqchars;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class UniqueChar {

    public long solution(String value) {
        if (Objects.isNull(value)) return 0;

        AtomicReference<Map<Character, Integer>> map = new AtomicReference<>();
        map.set(new HashMap<>());

        final HashMap<Character, Integer> characterIntegerHashMap = new HashMap<>();
        for (int x =0; x < value.length(); x++ ) {
            final Character character = Character.valueOf(value.charAt(x));

            final Integer count = characterIntegerHashMap.get(character);
            if (null == count) {
                characterIntegerHashMap.put(character, 1);
                continue;
            }

            characterIntegerHashMap.put(character, count+1);
         }

        return characterIntegerHashMap.values()
                .stream()
                .filter(v -> v.equals(1))
                .count();
//        value.chars()
//                .parallel()
//                .mapToObj(c -> (char) c)
//                .forEach( v -> {
//                    final Map<Character, Integer> characterIntegerMap = map.get();
//                    if (null == characterIntegerMap.get(v)) characterIntegerMap.put(v, 0);
//
//                    final Integer integer = characterIntegerMap.get(v) + 1;
//                    characterIntegerMap.put(v, integer);
//                });
//
//
//        final Map<Character, Integer> characterIntegerMap = map.get();
//
//        return characterIntegerMap.values().stream().filter(v -> v.equals(1))
//                .count();

//        value.chars()
//
//                .flatMap(Arrays.stream())
//                .reduce(0, (a, b) -> a - b)



        //return count;
    }
}
