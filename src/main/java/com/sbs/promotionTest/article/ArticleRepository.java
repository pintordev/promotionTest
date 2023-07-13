package com.sbs.promotionTest.article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query(value = "select * from article a " +
            "where a.title like %:kw% " +
            "or a.content like %:kw% "
            , nativeQuery = true)
    List<Article> findAllByKeyWord(@Param("kw") String kw);
}
