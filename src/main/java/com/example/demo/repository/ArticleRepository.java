package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Article;
import com.example.demo.domain.Comment;

@Repository
public class ArticleRepository {

	private static final ResultSetExtractor<List<Article>> JOIN_TABLE_RESULTSET = (rs) -> {
		    List<Article> articleList = new ArrayList<>();
		    List<Comment> commentList = null;

		    int beforeIdNum = 0;

		    while(rs.next()) {
		      int nowIdNum = rs.getInt("id");

		      if (nowIdNum != beforeIdNum) {
		        Article article =new Article();
		        article.setId(nowIdNum);
		        article.setName(rs.getString("name"));
				article.setContent(rs.getString("content"));

		        commentList = new ArrayList<>();
		        article.setCommentList(commentList);
		        articleList.add(article);
		      }

		      if (rs.getInt("com_id") != 0) {
		        Comment comment = new Comment();
		        comment.setId(rs.getInt("com_id"));
		        comment.setName(rs.getString("com_name"));
				comment.setContent(rs.getString("com_content"));
		        commentList.add(comment);
		      }
		      beforeIdNum = nowIdNum;
		    }
		    return articleList;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

//    記事,コメントをまとめて全件検索する処理
	public List<Article> findAll() {
		String sql = "SELECT a.id,a.name,a.content,c.id AS com_id, "
				+ "c.name AS com_name,c.content AS com_content,c.article_id FROM articles AS a "
				+ "FULL OUTER JOIN comments AS c on a.id=c.article_id ORDER BY a.id desc;";
		List<Article> articleList = template.query(sql, JOIN_TABLE_RESULTSET);
		return articleList;
	}

//	記事を投稿する処理
	public void insert(Article article) {
		String sql = "INSERT INTO articles (name,content) VALUES (:name,:content);";
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);
		template.update(sql, param);
	}

//	記事を削除する
	public void deleteById(Integer id) {
		String sql = "DELETE FROM articles WHERE id=:id ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}


}
