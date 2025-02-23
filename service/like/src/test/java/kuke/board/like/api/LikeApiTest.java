package kuke.board.like.api;


import kuke.board.like.service.response.ArticleLikeResponse;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LikeApiTest {
    RestClient restClient = RestClient.create("http://localhost:9003");


    @Test
    void likeAndUnlikeTest(){
        Long articleId = 9999L;

        //like(articleId, 1L);
        //like(articleId, 2L);
        //like(articleId, 3L);

//        ArticleLikeResponse read = read(articleId, 1L);
//        ArticleLikeResponse read1 = read(articleId, 2L);
//        ArticleLikeResponse read2 = read(articleId, 3L);
//        System.out.println("read = " + read);
//        System.out.println("read1 = " + read1);
//        System.out.println("read2 = " + read2);

        //unlike(articleId, 1L);



    }

    void like(Long articleId, Long userId, String lockType) {
        restClient.post()
                .uri("/v1/article-likes/articles/{articleId}/users/{userId}/%s".formatted(lockType), articleId, userId)
                .retrieve();
    }

    void unlike(Long articleId, Long userId, String lockType) {
        restClient.delete()
                .uri("/v1/article-likes/articles/{articleId}/users/{userId}/%s".formatted(lockType), articleId, userId)
                .retrieve();
    }

    ArticleLikeResponse read(Long articleId, Long userId) {
        return restClient.get()
                .uri("/v1/article-likes/articles/{articleId}/users/{userId}", articleId, userId)
                .retrieve()
                .body(ArticleLikeResponse.class);
    }


    @Test
    void likePerformanceTest() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(100);
       // likePerformanceTest(executor, 1111L, "pessimistic-lock-1");
      //  likePerformanceTest(executor, 2222L, "pessimistic-lock-2");
        likePerformanceTest(executor, 3333L, "optimistic-lock");
    }

    private void likePerformanceTest(ExecutorService executor, long articleId, String lockType) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3000);
        System.out.println(lockType +  " start");

        long start = System.nanoTime();
        for(int i=0; i < 3000; i++) {
            long userId = i + 2;
            executor.execute(() -> {
                like(articleId, userId, lockType);
                latch.countDown();
            });
        }
        latch.await();
        long end = System.nanoTime();
        System.out.println("lockType = " + lockType + ", time = " + (end - start) / 1000000 + "ms");

        Long count = restClient.get()
                .uri("/v1/article-likes/articles/{articleId}/count", articleId)
                .retrieve()
                .body(Long.class);

        System.out.println("count = " + count);

    }


}
