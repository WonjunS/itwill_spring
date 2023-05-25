package com.itwill.post.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.post.datasource.HikariDataSourceUtil;
import com.itwill.post.model.Post;
import com.zaxxer.hikari.HikariDataSource;

// Repository(Persistence) Layer(저장소/영속성 계층)
// DB CRUD(Create, Read, Update, Delete) 작업을 수행하는 계층
public class PostDao {
    
    // Slf4j 로깅 기능 사용:
    private static final Logger log = LoggerFactory.getLogger(PostDao.class);
    
    private static PostDao instance = null;
    
    private HikariDataSource ds;
    
    private PostDao() {
        ds = HikariDataSourceUtil.getInstance().getDataSource();
    }
    
    public static PostDao getInstance() {
        if(instance == null) {
            instance = new PostDao();
        }
        
        return instance;
    }
    
    // POSTS 테이블에서 전체 레코드를 id 내림차순으로 정렬(최근 작성 포스트 먼저)해서 검색
    private static final String SQL_SELECT_ALL = 
            "select * from POSTS order by ID desc";
    
    public List<Post> select() {
        List<Post> list = new ArrayList<>();
        
        log.info(SQL_SELECT_ALL);
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ds.getConnection(); // 풀에서 Connection 객체를 빌려옴
            stmt = conn.prepareStatement(SQL_SELECT_ALL);
            rs = stmt.executeQuery();
            while(rs.next()) {
                // 테이블 컬럼 내용을 Post 타입 객체로 변환하고 리스트에 추가
                Post post = recordToPost(rs);
                list.add(post);
            }
            log.info("# of rows = {}", list.size());
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close(); // 물리적인 접속 해제가 아니라, 풀에 반환
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return list;
    }
    
    private static final String SQL_SELECT_BY_ID = 
            "select * from POSTS "
            + "where ID = ?";
    
    public Post select(Long id) {
        log.info("read({})", id);
        log.info(SQL_SELECT_BY_ID);
        
        Post post = null;
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = ds.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_BY_ID);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            while(rs.next()) {
                post = recordToPost(rs);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return post;
    }
    
    private static final String SQL_SELECT_BY_TITLE = 
            "select * from POSTS where TITLE like ?";
    
    private static final String SQL_SELECT_BY_CONTENT = 
            "select * from POSTS where CONTENT like ?";
    
    private static final String SQL_SELECT_BY_TITLE_AND_CONTENT = 
            "select * from POSTS where TITLE like ? or CONTENT like ?";
    
    private static final String SQL_SELECT_BY_AUTHOR = 
            "select * from POSTS where AUTHOR like ?";
    
    public List<Post> select(String category, String keyword) {
        log.info("select({}, {})", category, keyword);
        log.info(SQL_SELECT_BY_TITLE);
        
        List<Post> list = new ArrayList<>();
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = ds.getConnection();
            if(category.equals("t")) {
                stmt = conn.prepareStatement(SQL_SELECT_BY_TITLE);
                stmt.setString(1, "%" + keyword + "%");
            }
            if(category.equals("c")) {
                stmt = conn.prepareStatement(SQL_SELECT_BY_CONTENT);
                stmt.setString(1, "%" + keyword + "%");
            }
            if(category.equals("tc")) {
                stmt = conn.prepareStatement(SQL_SELECT_BY_TITLE_AND_CONTENT);
                stmt.setString(1, "%" + keyword + "%");
                stmt.setString(2, "%" + keyword + "%");
            }
            if(category.equals("a")) {
                stmt = conn.prepareStatement(SQL_SELECT_BY_AUTHOR);
                stmt.setString(1, "%" + keyword + "%");
            }
            rs = stmt.executeQuery();
            while(rs.next()) {
                Post post = recordToPost(rs);
                list.add(post);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return list;
    }
    
    private static final String SQL_INSERT = 
            "insert into POSTS (TITLE, CONTENT, AUTHOR) "
            + "values(?, ?, ?)";
    
    public int insert(Post post) {
        log.info("insert({})", post);
        log.info(SQL_INSERT);
        
        int result = 0; // executeUpdate() 결과(insert 결과)를 저장할 변수
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = ds.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, post.getTitle());
            stmt.setString(2, post.getContent());
            stmt.setString(3, post.getAuthor());
            
            result = stmt.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return result;
    }
    
    // 해당 아이디의 포스트의 제목과 내용, 수정 시간을 업데이트
    private static final String SQL_UPDATE = 
            "update POSTS set TITLE = ?, CONTENT = ?, MODIFIED_TIME = sysdate where ID = ?";
    
    public int update(Post post) {
        log.info("update({})", post);
        log.info(SQL_UPDATE);
        
        int result = 0;
        
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = ds.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, post.getTitle());
            stmt.setString(2, post.getContent());
            stmt.setLong(3, post.getId());
            result = stmt.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return result;
    }
    
    // 포스트 아이디(PK)로 삭제하기:
    private static final String SQL_DELETE_BY_ID = 
            "delete from POSTS where ID = ?";
    
    public int delete(long id) {
        log.info("delete(id={})", id);
        log.info(SQL_DELETE_BY_ID);
        
        int result = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = ds.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE_BY_ID);
            stmt.setLong(1, id);
            result = stmt.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return result;
    }
    
    private Post recordToPost(ResultSet rs) throws SQLException {
        long id = rs.getLong("ID");
        String title = rs.getString("TITLE");
        String content = rs.getString("CONTENT");
        String author = rs.getString("AUTHOR");
        LocalDateTime created = rs.getTimestamp("CREATED_TIME").toLocalDateTime();
        LocalDateTime modified = rs.getTimestamp("MODIFIED_TIME").toLocalDateTime();
        
        return new Post(id, title, content, author, created, modified);
    }

}
