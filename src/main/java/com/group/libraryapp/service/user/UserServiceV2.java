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

    private final UserRepository userRepository;

    public UserServiceV2(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveUser(UserCreateRequest request){
        User u = userRepository.save(new User(request.getName(), request.getAge()));
        //throw new IllegalArgumentException(); //테스트 용도 문제가 생겨 롤백된다.
    }

    @Transactional(readOnly = true) // 데이터 저장등 커밋이 필요없는 곳에 readOnly해주면 약간의 성능 향상이 있다.
    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll();
        /*return users.stream()
                .map(user->new UserResponse(user.getId(), user.getName(), user.getAge()))
                .collect(Collectors.toList());*/
        return userRepository.findAll().stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateUser(UserUpdateRequest request) {
        User user =  userRepository.findById(request.getId())
                .orElseThrow(IllegalArgumentException::new);

        user.updateName(request.getName());
        // 아래 save는 @Transactional 아래에서 영속성 엔티티가 변경되면 자동으로 저장된다. 따라서 save가 없어도 가능
        //userRepository.save(user);  //바로 윗줄에서 user객체의 name 값이 바뀐 것을 확인하고 자동으로 업데이트 한다.
    }

    @Transactional
    public void deleteUser(String name){
        // SELETE * FROM user name = ?

        /*User user = userRepository.findByname(name);
        if(user == null){
            throw new IllegalArgumentException("삭제오류");
        }*/

        //findByName을 optional로 선언하면 오류체크문을 간결화 할 수 있다.
        User user =  userRepository.findByname(name).orElseThrow(IllegalArgumentException::new);
        userRepository.delete(user);
    }

}
