package com.group.libraryapp.repository.user;

import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class UserJdbcRepository { //Mysql 내 User테이블에 sql 날리는 내용들
    private final JdbcTemplate jdbcTemplate;

    public UserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveUser(String name, Integer age) {
        String sql = "INSERT INTO user (name, age) VALUES (?,?)";
        jdbcTemplate.update(sql, name, age);
        //users.add(new User(request.getName(), request.getAge()));  메모리에 직접 저장
    }

    public List<UserResponse> getUsers() {
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            int age = rs.getInt("age");
            return new UserResponse(id, name, age);
        });
    }

    public void updateUserName(String name, long id) {
        String sql = "UPDATE user SET name=? WHERE id=?";
        this.jdbcTemplate.update(sql, name, id);
    }

    public void deleteUserName(String name) {
        String sql = "DELETE FROM user WHERE name=?";
        jdbcTemplate.update(sql, name);
    }

    public boolean isUserNotExist (long id) {
        String readSql = "SELECT * FROM user WHERE id=?";  //수정할 id가 존재하는지 확인할 sql
        return this.jdbcTemplate.query(readSql, (rs, rowNum) -> 0,id).isEmpty();  //jdbc로 sql 날리고 결과가 있으면 0이담긴 list 생성, 결과가 있는지 없는지 isEmpty로 확인
    }

    public boolean isUserNotExist(String name) {
        String readSql = "SELECT * FROM user WHERE name=?";  //수정될 name이 존재하는지 확인할 sql
        return jdbcTemplate.query(readSql, (rs, rowNum) -> 0, name).isEmpty();  //jdbc로 sql 날리고 결과가 있으면 0이담긴 list 생성, 결과가 있는지 없는지 isEmpty로 확인
    }
}


