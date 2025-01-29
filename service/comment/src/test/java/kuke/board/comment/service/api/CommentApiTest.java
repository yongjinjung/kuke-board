package kuke.board.comment.service.api;

import kuke.board.comment.service.request.CommentCreateRequest;
import kuke.board.comment.service.response.CommentResponse;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

public class CommentApiTest {

    RestClient restClient = RestClient.create("http://localhost:9001");

    @Test
    void create(){
        CommentResponse response1 = createComment(new CommentCreateRequest(1L, "my comment1", null, 1L));
        CommentResponse response2 = createComment(new CommentCreateRequest(1L, "my comment2", response1.getCommentId(), 1L));
        CommentResponse response3 = createComment(new CommentCreateRequest(1L, "my comment3", response1.getCommentId(), 1L));


        System.out.println("commentId=%s".formatted(response1.getCommentId()));
        System.out.println("\tcommentId=%s".formatted(response2.getCommentId()));
        System.out.println("\tcommentId=%s".formatted(response3.getCommentId()));

//        commentId=142941890001838080
//        commentId=142941890366742528
//        commentId=142941890421268480
    }

    CommentResponse createComment(CommentCreateRequest request){
        return restClient.post()
                .uri("/v1/comments")
                .body(request)
                .retrieve()
                .body(CommentResponse.class);
    }

    @Test
    void readTest(){
        CommentResponse response = restClient.get()
                .uri("/v1/comments/{commentId}", 142941890001838080L)
                .retrieve()
                .body(CommentResponse.class);

        System.out.println("commentId=%s".formatted(response.getCommentId()));
    }

    @Test
    void deleteTest(){
        restClient.delete()
                .uri("/v1/comments/{commentId}", 142941890421268480L)
                .retrieve();

    }
}
