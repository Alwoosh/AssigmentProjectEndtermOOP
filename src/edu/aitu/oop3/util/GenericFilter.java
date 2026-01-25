package edu.aitu.oop3.util;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GenericFilter<T> {
    public List<T> filter(List<T> items, Predicate<T> criteria) {
        return items.stream().filter(criteria).collect(Collectors.toList());
    }
}
