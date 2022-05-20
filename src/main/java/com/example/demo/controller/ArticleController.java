package com.example.demo.controller;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.Article;
import com.example.demo.domain.Comment;
import com.example.demo.form.ArticleForm;
import com.example.demo.form.CommentForm;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.CommentRepository;

@Controller
@RequestMapping("Article")
public class ArticleController {

	@Autowired
	private ServletContext application;

	@Autowired
	private ArticleRepository repository;

	@Autowired
	private CommentRepository commentRepository;

	@ModelAttribute
	public ArticleForm setUpArticleForm() {
		return new ArticleForm();
	}

	@ModelAttribute
	public CommentForm setUpCommentForm() {
		return new CommentForm();
	}

//	初期表示メソッド
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
		return "redirect:/Article/";
	}

//	コメントを投稿、表示するメソッド
	@RequestMapping("insertcomment")
	public String insertComment(Comment comment, Integer id) {
		commentRepository.insert(comment);
		return "redirect:/Article/";
	}

//	記事とコメントを削除するメソッド
	@RequestMapping("delete")
	public String deleteArticle(Integer articleId, Integer id) {
		commentRepository.deleteByArticleId(articleId);
		repository.deleteById(id);
		return "redirect:/Article/";
	}


}
