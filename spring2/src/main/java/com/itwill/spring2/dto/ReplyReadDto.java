package com.itwill.spring2.dto;

import java.sql.Timestamp;

import com.itwill.spring2.domain.Reply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ReplyReadDto {
    
    private long id;
    private long postId;
    private String replyText;
    private String writer;
    private Timestamp modifiedTime;
    
    public static ReplyReadDto fromEntity(Reply entity) {
        return ReplyReadDto.builder()
                .id(entity.getId())
                .postId(entity.getPost_id())
                .replyText(entity.getReply_text())
                .writer(entity.getWriter())
                .modifiedTime(Timestamp.valueOf(entity.getModified_time()))
                .build();
    }
    
}
