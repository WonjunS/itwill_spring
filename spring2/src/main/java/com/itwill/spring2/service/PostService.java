package com.itwill.spring2.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itwill.spring2.domain.Post;
import com.itwill.spring2.dto.PostCreateDto;
import com.itwill.spring2.dto.PostDetailDto;
import com.itwill.spring2.dto.PostListDto;
import com.itwill.spring2.dto.PostUpdateDto;
import com.itwill.spring2.repository.PostRepository;
import com.itwill.spring2.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// application-context.xml에서 <context:component-scan> 설정에서
// com.itwill.spring2.service 패키지와 그 하위 패키지를 스캔(검색)하고 있음
@Service //-> 스프링 컨테이너에서 서비스 컴포넌트 객체를 생성하고 관리(필요한 곳에 주입)
@RequiredArgsConstructor // final로 선언된 필드를 초기화하는 생성자
@Slf4j
public class PostService {
    
    // 의존성 주입(DI: Dependency Injection):
    // 1. 필드에 의한 의존성 주입 - @Autowired 애너테이션 사용
    // 2. 생성자에 의한 의존성 주입:
    //    (1) 필드를 final로 선언
    //    (2) final 변수를 초기화할 수 있는 생성자를 작성
    
    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;
    
    // 포스트 목록 페이지
    public List<PostListDto> read() {
        log.info("read()");
        
        return postRepository.selectWithReplyCount();
    }
    
    // 포스트 상세보기 페이지
    public PostDetailDto read(long id) {
        log.info("read(id={})", id);
        
        // DB POSTS 테이블에서 검색
        Post entity = postRepository.selectById(id);
        
        // 검색한 내용을 DTO로 변환
        PostDetailDto dto = PostDetailDto.fromEntity(entity);
        
        // DB REPLIES 테이블에서 댓글 개수를 검색
        long count = replyRepository.selectReplyCountWithPostId(id);
        dto.setReplyCount(count);
        
        return dto;
    }
    
    // 새 포스트 작성 페이지
    public int create(PostCreateDto dto) {
        log.info("create({})", dto);
        
        return postRepository.insert(dto.toEntity());
    }
    
    // 포스트 업데이트
    public int update(PostUpdateDto dto) {
        log.info("update({})", dto);
        
        return postRepository.updateTitleAndContent(dto.toEntity());
    }
    
    // 포스트 삭제
    public int delete(long id) {
        log.info("delete({})", id);
        
        return postRepository.deleteById(id);
    }
    
}
