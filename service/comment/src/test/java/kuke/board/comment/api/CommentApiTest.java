package kuke.board.comment.api;

import kuke.board.comment.service.request.CommentCreateRequest;
import kuke.board.comment.service.response.CommentPageResponse;
import kuke.board.comment.service.response.CommentResponse;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Objects;

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

    @Test
    void readAll(){
        CommentPageResponse response = restClient.get()
                .uri("/v1/comments?articleId=1&page=1&pageSize=10")
                .retrieve()
                .body(CommentPageResponse.class);

        System.out.println("response.getCommentCount() = " + response.getCommentCount());

        for(CommentResponse comment : Objects.requireNonNull(response).getComments()){
            if(!comment.getCommentId().equals(comment.getParentCommentId())){
                System.out.print("\t");
            }
            System.out.printf("commentId=%s%n", comment.getCommentId());
        }

        /**
         * 1번 페이지 수행 결과
         * commentId=142945468260786176
         * 	commentId=142945468302729219
         * commentId=142945468260786177
         * 	commentId=142945468302729216
         * commentId=142945468260786178
         * 	commentId=142945468302729253
         * commentId=142945468260786179
         * 	commentId=142945468302729221
         * commentId=142945468260786180
         * 	commentId=142945468302729237
         */
    }

    @Test
    void readAllInfiniteScroll(){
        List<CommentResponse> responses1 = restClient.get()
                .uri("/v1/comments/infinite-scroll?articleId=1&pageSize=5")
                .retrieve()
                .body(new ParameterizedTypeReference<List<CommentResponse>>() {
                });
        System.out.println("firstPage");
        for(CommentResponse comment : responses1){
            if(!comment.getCommentId().equals(comment.getParentCommentId())){
                System.out.print("\t");
            }
            System.out.printf("commentId=%s%n", comment.getCommentId());
        }

        Long lastParentCommentId = responses1.getLast().getParentCommentId();
        Long lastCommentId = responses1.getLast().getCommentId();

        List<CommentResponse> responses2 = restClient.get()
                .uri("/v1/comments/infinite-scroll?articleId=1&lastParentCommentId=%s&lastCommentId=%s&pageSize=5".formatted(lastParentCommentId, lastCommentId))
                .retrieve()
                .body(new ParameterizedTypeReference<List<CommentResponse>>() {
                });
        System.out.println("secondPage");
        for(CommentResponse comment : responses2){
            if(!comment.getCommentId().equals(comment.getParentCommentId())){
                System.out.print("\t");
            }
            System.out.printf("commentId=%s%n", comment.getCommentId());
        }




    }
}
