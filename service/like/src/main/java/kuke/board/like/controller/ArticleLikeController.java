package kuke.board.like.controller;

import kuke.board.like.service.ArticleLikeService;
import kuke.board.like.service.response.ArticleLikeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ArticleLikeController {
    private final ArticleLikeService articleLikeService;


    @GetMapping("/v1/article-likes/articles/{articleId}/users/{userId}")
    public ArticleLikeResponse read(@PathVariable("articleId") Long articleId, @PathVariable("userId") Long userId) {
        ArticleLikeResponse res = articleLikeService.read(9999L    , 1L);
        log.info("read article like response : {}", res.toString());
        return res;
    }

    @PostMapping("/v1/article-likes/articles/{articleId}/users/{userId}")
    public void like(@PathVariable("articleId") Long articleId, @PathVariable("userId") Long userId) {
        articleLikeService.like(articleId, userId);
    }

    @DeleteMapping("/v1/article-likes/articles/{articleId}/users/{userId}")
    public void unlike(@PathVariable("articleId") Long articleId, @PathVariable("userId") Long userId) {
        articleLikeService.unLike(articleId, userId);
    }

}
