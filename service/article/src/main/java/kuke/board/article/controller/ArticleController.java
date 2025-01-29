package kuke.board.article.controller;

import kuke.board.article.service.ArticleService;
import kuke.board.article.service.request.ArticleCreateReequest;
import kuke.board.article.service.request.ArticleUpdateReequest;
import kuke.board.article.service.response.ArticlePageResponse;
import kuke.board.article.service.response.ArticleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/v1/articles/{articleId}")
    public ArticleResponse read(@PathVariable long articleId){
        return articleService.read(articleId);
    }

    @GetMapping("/v1/articles")
    public ArticlePageResponse readAll(
            @RequestParam("boardId") Long boardId,
            @RequestParam("page") Long page,
            @RequestParam("pageSize") Long pageSize
    ) {
        return articleService.readAll(boardId, page, pageSize);
    }

    @GetMapping("/v1/articles/infinite-scroll")
    public List<ArticleResponse> readAllInfiniteScroll(
            @RequestParam("boardId") Long boardId,
            @RequestParam("pageSize") Long pageSize,
            @RequestParam(value="lastArticleId", required = false) Long lastArticleId
    ){
        return articleService.readAllInfiniteScroll(boardId, pageSize, lastArticleId);
    }

    @PostMapping("/v1/articles")
    public ArticleResponse create(@RequestBody ArticleCreateReequest request){
        return articleService.create(request);
    }

    @PutMapping("/v1/articles/{articleId}")
    public ArticleResponse update(@PathVariable Long articleId, @RequestBody ArticleUpdateReequest reequest){
        return articleService.update(articleId, reequest);
    }

    @DeleteMapping("/v1/articles/{articleId}")
    public void delete(@PathVariable long articleId){
        articleService.delete(articleId);
    }




}
