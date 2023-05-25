package com.itwill.spring2.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itwill.spring2.domain.Reply;
import com.itwill.spring2.dto.ReplyCreateDto;
import com.itwill.spring2.dto.ReplyReadDto;
import com.itwill.spring2.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReplyService {
    
    private final ReplyRepository replyRepository;

    public int create(ReplyCreateDto dto) {
        log.info("create(dto={})", dto);
        
        return replyRepository.insert(dto.toEntity());
    }

    public List<ReplyReadDto> read(long postId) {
        log.info("read(postId={})", postId);
        
        List<Reply> list = replyRepository.selectByPostId(postId);
        
        return list.stream().map(ReplyReadDto::fromEntity).toList();
    }
    
    public int delete(long id) {
        log.info("delete(id={})", id);
        
        return replyRepository.delete(id);
    }
    
}