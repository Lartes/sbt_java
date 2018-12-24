package generics;

import java.util.HashMap;
import java.util.Map;

public class CountMapImpl<T> implements CountMap<T> {
    private Map<T, Integer> map;

    public CountMapImpl() {
        map = new HashMap<>();
    }

    @Override
    public void add(T element) {
        map.put(element, map.containsKey(element) ? map.get(element) + 1 : 1);
    }

    @Override
    public int getCount(T element) {
        Integer count = map.get(element);
        return count != null ? count : 0;
    }

    @Override
    public int remove(T element) {
        Integer count = map.remove(element);
        return count != null ? count : 0;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public void addAll(CountMap<? extends T> source) {
        for (Map.Entry<? extends T, Integer> entry: source.toMap().entrySet()) {
            Integer count = entry.getValue();
            map.put(entry.getKey(), map.containsKey(entry.getKey()) ? map.get(entry.getKey()) + count : count);
        }
    }

    @Override
    public Map<T, Integer> toMap() {
        return map;
    }

    @Override
    public void toMap(Map<? super T, Integer> destination) {
        destination.putAll(map);
    }
}
