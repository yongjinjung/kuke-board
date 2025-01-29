package kuke.board.comment.service.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kuke.board.comment.entity.Comment;
import kuke.board.common.snowflake.Snowflake;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class DataInitializer {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    TransactionTemplate transactionTemplate;
    Snowflake snowflake = new Snowflake();
    CountDownLatch countDownLatch = new CountDownLatch(EXECUTE_COUNT);

    static final int BLUK_INSERT_SIZE = 2000;
    static final int EXECUTE_COUNT = 6000;

    @Test
    void initialize() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < EXECUTE_COUNT; i++) {
            executorService.submit(() -> {
                insert();
                countDownLatch.countDown();
                System.out.println("countDownLatch.countDown() = " + countDownLatch.getCount());
            });
        }
        countDownLatch.await();
        executorService.shutdown();
    }

    void insert(){
        transactionTemplate.executeWithoutResult(status -> {
            Comment prev = null;
           for (int i = 0; i < BLUK_INSERT_SIZE; i++) {
               Comment comment = Comment.create(snowflake.nextId(), "content", i % 2 == 0? null : prev.getCommentId(), 1L, 1L);
               prev = comment;
               entityManager.persist(comment);
           }
        });
    }
}
