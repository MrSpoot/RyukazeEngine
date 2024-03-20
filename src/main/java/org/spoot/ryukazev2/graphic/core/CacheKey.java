package org.spoot.ryukazev2.graphic.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CacheKey {

    private final Map<String, Object> attributes = new HashMap<>();

    public void putAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CacheKey cacheKey = (CacheKey) o;
        if (attributes.size() != cacheKey.attributes.size()) {
            return false;
        }
        for (String key : attributes.keySet()) {
            Object value1 = attributes.get(key);
            Object value2 = cacheKey.attributes.get(key);
            if (value1 instanceof Object[] && value2 instanceof Object[]) {
                if (!Arrays.equals((Object[]) value1, (Object[]) value2)) {
                    return false;
                }
            } else if (value1 instanceof int[] && value2 instanceof int[]) {
                if (!Arrays.equals((int[]) value1, (int[]) value2)) {
                    return false;
                }
            } else if (value1 instanceof float[] && value2 instanceof float[]) {
                if (!Arrays.equals((float[]) value1, (float[]) value2)) {
                    return false;
                }
            }
            else if (value1 instanceof double[] && value2 instanceof double[]) {
                if (!Arrays.equals((double[]) value1, (double[]) value2)) {
                    return false;
                }
            }
            else if (value1 instanceof byte[] && value2 instanceof byte[]) {
                if (!Arrays.equals((byte[]) value1, (byte[]) value2)) {
                    return false;
                }
            }
            else if (!Objects.equals(value1, value2)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return attributes.entrySet().stream()
                .mapToInt(e -> {
                    if (e.getValue() instanceof Object[]) {
                        return Arrays.hashCode((Object[]) e.getValue());
                    } else if (e.getValue() instanceof int[]) {
                        return Arrays.hashCode((int[]) e.getValue());
                    } else if (e.getValue() instanceof float[]) {
                        return Arrays.hashCode((float[]) e.getValue());
                    } else if (e.getValue() instanceof double[]) {
                        return Arrays.hashCode((double[]) e.getValue());
                    } else if (e.getValue() instanceof byte[]) {
                        return Arrays.hashCode((byte[]) e.getValue());
                    }
                    return Objects.hashCode(e.getValue());
                })
                .reduce(0, (a, b) -> 31 * a + b);
    }
}
