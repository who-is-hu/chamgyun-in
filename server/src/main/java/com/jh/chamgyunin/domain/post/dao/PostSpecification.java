package com.jh.chamgyunin.domain.post.dao;

import com.jh.chamgyunin.domain.post.model.Post;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class PostSpecification {

    public static Specification<Post> containTags(final List<String> tagNames) {
        return (Specification<Post>) ((root, query, builder) -> {
            List<Predicate> predicates = getPredicateWithKeys(tagNames, root, builder);
            return builder.or(predicates.toArray(new Predicate[0]));
        });
    }

    private static List<Predicate> getPredicateWithKeys(final List<String> tagNames, Root<Post> root, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        tagNames.stream().forEach(
                tagName -> predicates.add(
                        builder.like(root.get("tags"), "%" + tagName + "%")
                )
        );
        return predicates;
    }
}
