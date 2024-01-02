package com.group.libraryapp.service.fruit;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("main")  //@Primary보다 우선순위를 갖는다. 스프링은 주로 사용자가 따로 지정한게 우선순위를 갖음
public class BananaService implements FruitService {
}
