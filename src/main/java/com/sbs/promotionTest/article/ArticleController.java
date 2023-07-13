package com.sbs.promotionTest.article;

import com.sbs.promotionTest.user.UserService;
import com.sbs.promotionTest.user._User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RequestMapping("/article")
@RequiredArgsConstructor
@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "kw", defaultValue = "") String kw) {
        List<Article> articleList = this.articleService.getList(kw);
        model.addAttribute("articleList", articleList);
        model.addAttribute("kw", kw);
        return "article_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id) {
        Article article = this.articleService.getArticle(id);
        model.addAttribute("article", article);
        return "article_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String createArticle(ArticleForm articleForm) {
        return "article_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String createArticle(Principal principal,
                                @Valid ArticleForm articleForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "article_form";
        }

        _User author = this.userService.getUserByNickName(principal.getName());
        this.articleService.create(articleForm.getTitle(), articleForm.getContent(), author);

        return "redirect:/article/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modifyArticle(@PathVariable("id") Long id, Principal principal,
                                ArticleForm articleForm) {

        Article article = this.articleService.getArticle(id);

        if (!article.getAuthor().getNickname().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        articleForm.setTitle(article.getTitle());
        articleForm.setContent(article.getContent());

        return "article_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modifyArticle(@PathVariable("id") Long id, Principal principal,
                                @Valid ArticleForm articleForm, BindingResult bindingResult) {

        Article article = this.articleService.getArticle(id);

        if (!article.getAuthor().getNickname().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        if (bindingResult.hasErrors()) {
            return "article_form";
        }

        this.articleService.modify(article, articleForm.getTitle(), articleForm.getContent());

        return String.format("redirect:/article/detail/%s", article.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String deleteArticle(@PathVariable("id") Long id, Principal principal) {

        Article article = this.articleService.getArticle(id);

        if (!article.getAuthor().getNickname().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }

        this.articleService.delete(article);

        return "redirect:/article/list";
    }
}
