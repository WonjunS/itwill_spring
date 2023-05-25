package com.itwill.spring2.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 댓글 테이블(REPLIES)에 저장되는 레코드(행 1개)를 표현하는 객체
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reply {
    
    private long id;
    private long post_id;
    private String reply_text;
    private String writer;
    private LocalDateTime created_time;
    private LocalDateTime modified_time;
    
}
