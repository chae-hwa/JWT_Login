package com.ll.com.jwt_login_exam.app.article.controller;

import com.ll.com.jwt_login_exam.app.article.entity.Article;
import com.ll.com.jwt_login_exam.app.article.service.ArticleService;
import com.ll.com.jwt_login_exam.app.base.dto.RsData;
import com.ll.com.jwt_login_exam.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticlesController {
    private final ArticleService articleService;

    @GetMapping("")
    public ResponseEntity<RsData> list(){
        List<Article> articles = articleService.findAll();

        return Util.spring.responseEntityOf(
                RsData.successOf(
                        Util.mapOf(
                                "articles", articles
                        )
                )
        );
    }
}
