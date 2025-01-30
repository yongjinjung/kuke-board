package kuke.board.comment.repository;

import kuke.board.comment.entity.Comment;
import kuke.board.comment.service.response.CommentResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Test
    void findAll() {

        List<CommentResponse> responses = commentRepository.findAll(1L, 1L, 30L).stream().map(CommentResponse::from).toList();
        for (CommentResponse response : responses) {
            log.info(response.toString());
        }
    }

    @Test
    void count(){
        Long count = commentRepository.count(1L, 30L);
        log.info("count: {}", count);
    }

    @Test
    void findAllInfiniteScrollTest(){
        List<CommentResponse> responses = commentRepository.findAllInfiniteScroll(1L, 30L).stream()
                .map(CommentResponse::from)
                .toList();

        for (CommentResponse response : responses) {
            log.info(response.toString());
        }

        CommentResponse comment = responses.getLast();

        commentRepository.findAllInfiniteScroll(comment.getArticleId(), comment.getParentCommentId(), comment.getCommentId(), 30L).stream()
                .map(CommentResponse::from)
                .forEach(System.out::println);


    }




}