package kuke.board.article.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PageLimitCalculatorTest {

    @Test
    void calculatePageLimitTest(){
        calculatePageLimitTest(1L, 30L, 10L, 301L);
        calculatePageLimitTest(7L, 30L, 10L, 301L);
        calculatePageLimitTest(10L, 30L, 10L, 301L);
    }

    void calculatePageLimitTest(Long page, Long PageSize, Long movablePageCount, Long expected){
        Long result = PageLimitCalculator.calculatePageLimit(page, PageSize, movablePageCount);
        assertThat(result).isEqualTo(expected);
    }
}