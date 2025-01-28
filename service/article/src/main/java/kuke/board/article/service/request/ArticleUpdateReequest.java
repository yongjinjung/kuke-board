package kuke.board.article.service.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ArticleUpdateReequest {
    private String title;
    private String content;
}
