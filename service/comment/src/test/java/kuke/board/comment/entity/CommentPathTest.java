package kuke.board.comment.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommentPathTest {

    @Test
    void createChildCommentTest(){
        createChildCommentTest(CommentPath.create(null), null);

    }


    void createChildCommentTest(CommentPath commentPath, String descendantsTopPath){
        CommentPath childCommentPath = commentPath.createChildCommentPath(descendantsTopPath);
        System.out.println("commentPath = " + childCommentPath.getPath());
    }

}