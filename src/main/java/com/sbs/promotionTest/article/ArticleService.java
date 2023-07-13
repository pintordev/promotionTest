package com.sbs.promotionTest.article;

import com.sbs.promotionTest.user._User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public List<Article> getList(String kw) {
        return this.articleRepository.findAllByKeyWord(kw);
    }

    public Article getArticle(Long id) {
        Optional<Article> optionalArticle = this.articleRepository.findById(id);
        if (optionalArticle.isPresent()) {
            return optionalArticle.get();
        } else {
            throw new RuntimeException("article not found");
        }
    }

    public void create(String title, String content, _User author) {
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setAuthor(author);
        article.setCreateDate(LocalDateTime.now());
        this.articleRepository.save(article);
    }

    public void modify(Article article, String title, String content) {
        article.setTitle(title);
        article.setContent(content);
        this.articleRepository.save(article);
    }

    public void delete(Article article) {
        this.articleRepository.delete(article);
    }
}
