package com.itwill.spring2.dto;

import com.itwill.spring2.domain.Reply;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReplyCreateDto {
    
    private long postId;
    private String replyText;
    private String writer;
    
    public Reply toEntity() {
        return Reply.builder()
                .post_id(postId)
                .reply_text(replyText)
                .writer(writer)
                .build();
    }
    
}
