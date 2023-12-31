package com.itwill.spring2.dto;

import com.itwill.spring2.domain.Post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class PostUpdateDto {
    
    private long id;
    private String title;
    private String content;
    
    public Post toEntity() {
        return Post.builder()
                .id(id)
                .title(title)
                .content(content)
                .build();
    }
    
}
