package com.group.libraryapp.domain.user;

import com.group.libraryapp.domain.user.loanHistory.UserLoanHistory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //pk를 오토 인크리먼트 생성한다.
    private Long id = null;
    @Column(nullable = false, length = 20/*, name = "name"*/) //name 컬럼 정보, 멤버변수와 컬럼명이 같으면 생략가능
    private String name;
    private Integer age; //@Column을 아예 생략도 가능하다.

    //orphanRemoval = true 옵션을 사용하면 리스트에서 삭제를 한다면 디비에도 적용되게 된다.
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserLoanHistory> userLoanHistories = new ArrayList<>();

    //jpa를 사용하기 위해선 기본 생성자가 필요하다.
    protected User() {
    }

    public User(String name, Integer age) {
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException(String.format("잘못된 name(%s)가 들어왔습니다.", name));
        }
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public void updateName(String name){
        this.name = name;
    }

    public void loanBook(String bookName){
        this.userLoanHistories.add(new UserLoanHistory(this, bookName));
    }

    public void returnBook(String bookName){
        UserLoanHistory tartgetHistory = this.userLoanHistories.stream()
                .filter(history->history.getBookName().equals(bookName))
                .findFirst().orElseThrow(IllegalArgumentException::new);
        tartgetHistory.doReturn();
    }
}
