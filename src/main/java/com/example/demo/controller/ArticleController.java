package com.example.demo.controller;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.Article;
import com.example.demo.form.ArticleForm;
import com.example.demo.repository.ArticleRepository;

@Controller
@RequestMapping("Article")
public class ArticleController {

	@Autowired
	private ServletContext application;

	@Autowired
	private ArticleRepository repository;

	@ModelAttribute
	public ArticleForm setUpArticleForm() {
		return new ArticleForm();
	}

//	全件検索を行うメソッド
	@RequestMapping("")
	public String index() {
		List<Article> articleList = repository.findAll();
		application.setAttribute("articleList", articleList);
		return "Bulletinboard"; // HTML
	}

//	記事投稿を行うメソッド
	@RequestMapping("insert")
	public String insertArticle(Article article) {
		repository.insert(article);
		return "Bulletinboard"; // HTMLz
	}

}
