package kuke.board.article.api;

import kuke.board.article.entity.Article;
import kuke.board.article.service.request.ArticleCreateReequest;
import kuke.board.article.service.response.ArticlePageResponse;
import kuke.board.article.service.response.ArticleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

public class ArticleApiTest {

    RestClient restClient = RestClient.create("http://localhost:9000");

    @Test
    void createTest(){
        ArticleResponse response = create(new ArticleCreateReequest("hi", "my content", 1L, 1L));
        System.out.println("response: " + response);
    }

    ArticleResponse create(ArticleCreateReequest request){
        return restClient.post()
                .uri("/v1/articles")
                .body(request)
                .retrieve()
                .body(ArticleResponse.class);
    }

    @Test
    void readTest(){
        ArticleResponse response = read(142505489119563776L);
        System.out.println("response: " + response);
    }

    ArticleResponse read(Long articleId){
        return restClient.get()
                .uri("/v1/articles/{articleId}", articleId)
                .retrieve()
                .body(ArticleResponse.class);
    }

    @Test
    void updateTest(){
        update(142505489119563776L, new ArticleUpdateReequest("hi 3", "my content 33"));
        ArticleResponse response = read(142505489119563776L);
        System.out.println("response: " + response);
    }

    void update(Long articleId, ArticleUpdateReequest request){
         restClient.put()
            .uri("/v1/articles/{articleId}", articleId)
            .body(request)
            .retrieve();
    }

    @Test
    void deleteTest(){
        delete(142505489119563776L);
    }

    void delete(Long articleId){
        restClient.delete()
            .uri("/v1/articles/{articleId}", articleId)
            .retrieve();
    }

    @Test
    void readAllTest(){
        ArticlePageResponse response = restClient.get()
                .uri("/v1/articles?boardId=1&pageSize=30&page=19")
                .retrieve()
                .body(ArticlePageResponse.class);

        System.out.println("response = " + response.getArticleCount());
        for(ArticleResponse article : response.getArticles()) {
            System.out.println("article = " + article);
        }
    }


    @Getter
    @AllArgsConstructor
    static class ArticleCreateReequest {
        private String title;
        private String content;
        private Long writerId;
        private Long boardId;
    }

    @Getter
    @AllArgsConstructor
    static class ArticleUpdateReequest {
        private String title;
        private String content;
    }

}
