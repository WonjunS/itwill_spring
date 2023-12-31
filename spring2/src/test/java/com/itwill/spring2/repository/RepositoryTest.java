package com.itwill.spring2.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.itwill.spring2.domain.Post;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        locations = { "file:src/main/webapp/WEB-INF/application-context.xml" }
)
public class RepositoryTest {
    
    @Autowired
    private PostRepository postRepository;
    
    @Test
    public void testDelete() {
        int result = postRepository.deleteById(7);
        assertEquals(1, result);
    }
    
    // @Test
    public void testUpdate() {
        Post post = Post.builder()
                .id(2)
                .title("업데이트 TEST")
                .content("MyBatis 프레임워크를 사용한 DB 업데이트")
                .build();
        
        int result = postRepository.updateTitleAndContent(post);
        assertEquals(1, result);
    }
    
    // @Test
    public void testSelectById() {
        Post result = postRepository.selectById(2);
        assertNotNull(result);
        log.info(result.toString());
        
        result = postRepository.selectById(-1);
        assertNull(result);
    }
    
    // @Test
    public void testSelectOrderByIdDesc() {
        List<Post> list = postRepository.selectOrderByIdDesc();
        for(Post p : list) {
            log.info(p.toString());
        }
    }
    
    // @Test
    public void testPostRepository() {
        assertNotNull(postRepository);
        log.info("postRepository = {}", postRepository);
        
        Post post = Post.builder()
                .title("MyBatis 테스트")
                .content("MyBatis를 이용한 insert")
                .author("admin")
                .build();
        
        log.info(post.toString());
        
        int result = postRepository.insert(post);
        assertEquals(1, result);
        log.info("result = {}", result);
    }
    
}
