package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMemberRepository implements MemberRepository{

    private final DataSource dataSource;

    public JdbcMemberRepository(DataSource dataSource) throws SQLException {
        this.dataSource = dataSource; //spring을 통해 dataSource 주입받기
        dataSource.getConnection();
        /**
         * dataSource.getConnection();을 통해 진짜 db랑 연결하는 소켓을 얻음
         * sql문 날려서 db에 저장하면 됨
         */
    }

    @Override
    public Member save(Member member) {
        String sql = "insert into member(name) values(?)";
/**
 * Pseudocode
        Connection conn = dataSource.getConnection(); // dataSource Connection을 가져옴

        conn.prepareStatement(sql); //sql문 작성

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt, setString(1, member.getName());

        pstmt.executeUpdate(); //db에 qurry가 날라감
 */
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null; //결과 받기
        try {
            conn = getConnection(); //connection 가져옴
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // prepareStatement을 통해 sql문 넣음
            //RETURN_GENERATED_KEYS: DB상에 AUTO_INCREMENT로 인해 자동으로 생성되어진 key(=id)를 가져오는 쿼리

            pstmt.setString(1, member.getName()); // 1: ? 내용을 member.getName()으로 값 set

            pstmt.executeUpdate(); //db에 실제 쿼리가 날라감
            rs = pstmt.getGeneratedKeys();
            //RETURN_GENERATED_KEYS에 들어간 key값에 따라 값을 꺼내서 rs에 저장시킴

            if (rs.next()) { //rs에 값이 있으면
                member.setId(rs.getLong(1)); //rs.getLong: 값 꺼내서 setId
            } else { // 실패하면 Exception
                throw new SQLException("id 조회 실패");
            }
            return member;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs); // resource 절약: 끊어주기
        }
    }

    /**
     * 조회
     */
    @Override
    public Optional<Member> findById(Long id) {
        String sql = "select * from member where id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            rs = pstmt.executeQuery(); //조회할때는 executeQuery();

            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    /**
     * 모두 조회
     */
    @Override
    public List<Member> findAll() {
        String sql = "select * from member";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            List<Member> members = new ArrayList<>();
            while(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);
            }

            return members;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findByName(String name) {
        String sql = "select * from member where name = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);

            rs = pstmt.executeQuery();

            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        //parameter 역순으로 close 시킴
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
