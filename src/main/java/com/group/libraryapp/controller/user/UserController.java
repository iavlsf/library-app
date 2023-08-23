package com.group.libraryapp.controller.user;

import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import com.group.libraryapp.service.user.UserServiceV2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    //private final List<User> users = new ArrayList<>();  User를 메모리에 저장
    private final UserServiceV2 userService;

    public UserController(UserServiceV2 userService) { //UserService를 Bean으로 등록하고 스프링컨테이너에서 넘겨받음
        this.userService = userService;
    }

    //    public UserController(JdbcTemplate jdbcTemplate) {
//        userService = new UserService(jdbcTemplate);
//    }

    /**
     * 사용자 등록 API spec
     * HTTP Method - POST
     * HTTP Path - /user
     * 데이터 전달방법 - HTTP Body(JSON) - {
     * "name":String(null 불가능),
     * "age":Integer
     * }
     * API의 반환결과 - 없음(HTTP 상태 200 OK만)
     */
    @PostMapping("/user")
    public void saveUser(@RequestBody UserCreateRequest request) {
        userService.saveUser(request);
    }

    /**
     * 사용자 전체 조회 API spec
     * HTTP Method - GET
     * HTTP Path - /user
     * 데이터 전달방법 - 쿼리(없음)
     * API의 반환결과 - JSON
     * [{
     * "id": Long,
     * "name" : String(null불가능)
     * "age" : Integer
     * },...]
     */
    @GetMapping("/user")
    public List<UserResponse> getUsers() {
        return userService.getUsers();

        /*
        데이터 메모리에서 조회
        List<UserResponse> responses = new ArrayList<>();
        for(int i =0;i<users.size();i++){
            responses.add(new UserResponse(i + 1,users.get(i)));
        }
        return responses;
        */

    }

    /**
     * 사용자 이름 수정 API spec
     * HTTP Method - PUT
     * HTTP Path - /user
     * 데이터 전달방법 - HTTP Body(Json)
     * {
     * "id": Long,
     * "name" : String(변경되어야하는 이름이 들어옴)
     * }
     * API의 반환결과 - 없음(HTTP 상태 200 OK만)
     * <p>
     * 만약 요청된 id가 존재하지 않을 경우 에러발생되어야함
     */
    @PutMapping("/user")
    public void updateUser(@RequestBody UserUpdateRequest request) {
        userService.updateUser(request);

    }

    /**
     * 사용자 삭제 API spec
     * HTTP Method - DELETE
     * HTTP Path - /user
     * 데이터 전달방법 - 쿼리
     * 쿼리 Key와 Value - String name(삭제되어야할 이름)
     * API의 반환결과 - 없음(HTTP 상태 200 OK만)
     * <p>
     * 만약 요청된 name이 존재하지 않을 경우 에러발생되어야함
     */
    @DeleteMapping("/user")
    public void deleteUser(@RequestParam String name) {
        userService.deleteUser(name);
    }

//    @GetMapping("/user/error-test")  api내부에서 예외처리하면 어떻게 결과가 나가는지 확인: 결과로는 500에러가 나감
//    public void errorTest() {
//        throw new IllegalArgumentException();
//    }


//    @GetMapping("/fruit")  response body에 json으로 나가는거 확인용
//    public Fruit getFruit() {
//        return new Fruit("바나나", 2000);
//    }
}
