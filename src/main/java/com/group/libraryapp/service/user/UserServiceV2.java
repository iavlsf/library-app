package com.group.libraryapp.service.user;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceV2 {
    private UserRepository userRepository;

    public UserServiceV2(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveUser(UserCreateRequest request) {
        User u = userRepository.save(new User(request.getName(), request.getAge()));
    }//저장은 Repository에 JPA로 매핑했던 객체를 생성해서 넣고 save메소드 사용하면됨

    @Transactional(readOnly = true)
    public List<UserResponse> getUsers() {
//        return userRepository.findAll().stream().map(user -> new UserResponse(user.getId(),user)).collect(Collectors.toList());
        return userRepository.findAll().stream().map(UserResponse::new).collect(Collectors.toList()); //findAll을 사용하면 @Entity매핑했던 모든 객체를 List로 반환함, DTO 변환이 필요함 UserResponse에서 User를 받는 생성자를 만들면 이런식으로 더 짧게 만듦
    }

    @Transactional
    public void updateUser(UserUpdateRequest request) {
        User user = userRepository.findById(request.getId()).orElseThrow(IllegalArgumentException::new); //id를 기준으로 1개의 데이터를 가져온다. 반환시 Optional로 감싸져서 나옴, Optional의 orElseThrow를 사용해서 데이터가 없으면 예외를 던지게만듬
        user.updateName(request.getName());  //DTO에 담긴 내용으로 검색했던id의 user를 업데이트함
        //userRepository.save(user); 영속성 컨텍스트 범위안에서 변경이 일어났기 때문에 명시적으로 저장을 날리지 않아도 된다. //알아서 user를 비교하고 수정해서 저장함(save메소드를 사용했음에도 업데이트 SQL로 날라감)
    }

    @Transactional
    public void deleteUser(String name) {
//        if(!userRepository.existByName(name)){ //existByName사용했을때, 이때 findByName은 Optional로 받지마셈
//            throw new IllegalArgumentException();
//        }else{
//            User user = userRepository.findByName(name).;
//        }
        User user = userRepository.findByName(name).orElseThrow(IllegalArgumentException::new);
        userRepository.delete(user);
//        if(user==null){ Optional의 orElseThrow쓰니까 이렇게 안해도됨
//            throw new IllegalArgumentException();
//        }
    }
}
