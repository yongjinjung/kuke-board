package kuke.board.like.api;


import kuke.board.like.service.response.ArticleLikeResponse;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

public class LikeApiTest {
    RestClient restClient = RestClient.create("http://localhost:9003");


    @Test
    void likeAndUnlikeTest(){
        Long articleId = 9999L;

        //like(articleId, 1L);
        //like(articleId, 2L);
        //like(articleId, 3L);

        ArticleLikeResponse read = read(articleId, 1L);
        ArticleLikeResponse read1 = read(articleId, 2L);
        ArticleLikeResponse read2 = read(articleId, 3L);
        System.out.println("read = " + read);
        System.out.println("read1 = " + read1);
        System.out.println("read2 = " + read2);




    }

    void like(Long articleId, Long userId) {
        restClient.post()
                .uri("/v1/article-likes/articles/{articleId}/users/{userId}", articleId, userId)
                .retrieve();
    }

    void unlike(Long articleId, Long userId) {
        restClient.delete()
                .uri("/v1/article-likes/articles/{articleId}/users/{userId}", articleId, userId)
                .retrieve();
    }

    ArticleLikeResponse read(Long articleId, Long userId) {
        return restClient.get()
                .uri("/v1/article-likes/articles/{articleId}/users/{userId}", articleId, userId)
                .retrieve()
                .body(ArticleLikeResponse.class);
    }



}
