package com.group.libraryapp.config;

import org.springframework.context.annotation.Configuration;

/*
    @RestController, @Service, @Repository 등등
    정해진 어노테이션 말고 직접 사용자가 스프링 컨테이너에 빈으로 등록

    @Configuration, @Bean 은 외부 라이브러리 등 유저가 만들지 않을때 사용한다.
*/

@Configuration
public class UserConfiguration {

/*    @Bean
    public UserRepository userRepository(JdbcTemplate jdbcTemplate){
        return new UserRepository(jdbcTemplate);
    }*/
}
