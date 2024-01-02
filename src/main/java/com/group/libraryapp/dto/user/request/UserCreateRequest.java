package com.group.libraryapp.dto.user.request;

public class UserCreateRequest {

    private String name;
    private Integer age;    //int와 다르게 null을 받을 수 있다.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
