package com.sbs.promotionTest.article;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleForm {
    @NotEmpty(message = "제목은 필수 항목입니다.")
    private String title;

    @NotEmpty(message = "내용은 필수 항목입니다.")
    private String content;
}
