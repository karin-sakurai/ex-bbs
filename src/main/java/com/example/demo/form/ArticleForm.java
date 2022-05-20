package com.example.demo.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ArticleForm {

	@NotEmpty(message = "名前を入力してください")
	@Size(min = 0, max = 50, message = "名前は５０文字以内で入力してください")
	private String name;

	@NotEmpty(message = "投稿内容を入力してください")
	private String content;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "ArticleForm [name=" + name + ", content=" + content + "]";
	}

}
