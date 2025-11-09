package org.example.backend.utils;

import jakarta.persistence.criteria.Join;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.backend.authors.models.Author;
import org.example.backend.books.models.Book;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpecificationUtils {

    public static <S> Specification<S> iLike(String field, String value) {
        if (value == null || value.isBlank()) {
            return (root, query, cb) -> null;
        }
        return (root, query, cb) ->
                cb.like(cb.lower(root.get(field)), "%" + value.toLowerCase() + "%");
    }

    public static <S, T extends Comparable<? super T>> Specification<S> gte(String field, T value) {
        if (value == null) {
            return (root, query, cb) -> null;
        }
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(field), value);
    }

    public static <S, T extends Comparable<? super T>> Specification<S> lte(String field, T value) {
        if (value == null) {
            return (root, query, cb) -> null;
        }
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(field), value);
    }

    public static <S, T> Specification<S> in(String field, Collection<? extends T> values) {
        if (values == null || values.isEmpty()) {
            return (root, query, cb) -> null;
        }
        return (root, query, cb) -> root.get(field).in(values);
    }

    public static <S> Specification<S> joinLike(String joinField, String field, String value) {
        if (value == null || value.isBlank()) {
            return (root, query, cb) -> null;
        }
        return (root, query, cb) -> {
            assert query != null;
            query.distinct(true);
            Join<S, Object> join = root.join(joinField);
            return cb.like(cb.lower(join.get(field)), "%" + value.toLowerCase() + "%");
        };
    }

    public static <S> Specification<S> joinEqual(String joinField, String field, Object value) {
        if (value == null) {
            return (root, query, cb) -> null;
        }
        return (root, query, cb) -> {
            assert query != null;
            query.distinct(true);
            Join<S, Object> join = root.join(joinField);
            return cb.equal(join.get(field), value);
        };
    }
}

