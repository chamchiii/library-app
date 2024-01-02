package com.group.libraryapp.service.user;

import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import com.group.libraryapp.repository.user.UserJdbcRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceV1 {

    //의존성 주입방법 1 - 생성자 사용으로 가장 권장되는 방법
    private final UserJdbcRepository userJdbcRepository;
    //private final FruitService fruitService;

    public UserServiceV1(UserJdbcRepository userJdbcRepository/*, @Qualifier("main") FruitService fruitService*/) {
        this.userJdbcRepository = userJdbcRepository;
        //this.fruitService = fruitService;
    }

    //스프링 빈 주입방법 2 - setter 사용
    /*private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }*/

    //스프링 빈 주입방법 3 - 필드에 직접 사용
    /*@Autowired
    private final UserRepository userRepository;*/

    public void saveUser(UserCreateRequest request){
        userJdbcRepository.saveUser(request.getName(), request.getAge());
    }

    public List<UserResponse> getUsers(){
        return userJdbcRepository.getUsers();
    }

    public void updateUser(UserUpdateRequest request) {
        if (userJdbcRepository.isUserNotExist(request.getId())) {
            throw new IllegalArgumentException();
        }

        userJdbcRepository.updateUserName(request.getName(), request.getId());
    }

    public void deleteUser(String name){
        if(userJdbcRepository.isUserNotExist(name)){
            throw new IllegalArgumentException();
        }
        userJdbcRepository.deleteUser(name);
    }

}
