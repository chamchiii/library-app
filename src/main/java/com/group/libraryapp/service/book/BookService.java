package com.group.libraryapp.service.book;

import com.group.libraryapp.domain.book.Book;
import com.group.libraryapp.domain.book.BookRepository;
import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistory;
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistoryRepository;
import com.group.libraryapp.dto.book.request.BookCreateRequest;
import com.group.libraryapp.dto.book.request.BookLoanRequest;
import com.group.libraryapp.dto.book.request.BookReturnRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserLoanHistoryRepository userLoanHistoryRepository;
    private  final UserRepository userRepository;

    public BookService(BookRepository bookRepository,
                       UserLoanHistoryRepository userLoanHistoryRepository,
                       UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userLoanHistoryRepository = userLoanHistoryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveBook(BookCreateRequest request){
        bookRepository.save(new Book(request.getName()));
    }

    @Transactional
    public void loanBook(BookLoanRequest request){
        System.out.println("1. 책정보 가져옴");
        // 1. 책정보 가져옴
        Book book = bookRepository.findByName(request.getBookName()).
                orElseThrow(IllegalArgumentException::new);

        System.out.println("2. 정보 확인 후 대출 중인지 확인");
        // 2. 정보 확인 후 대출 중인지 확인
        if(userLoanHistoryRepository.existsByBookNameAndIsReturn(book.getName(), false)){
            // 3. 대출중이면 예외처리
            throw new IllegalArgumentException(String.format("(%s)은 현재 대출중인 책 입니다.", book.getName()));
        }

        System.out.println("4. 유저 정보를 가져온다.");
        // 4. 유저 정보를 가져온다.
        User user = userRepository.findByname(request.getUserName()).
                orElseThrow(IllegalArgumentException::new);
        
        // 5. 유저 정도와 책 정보를 기반으로 UserLoanHistory를 저장한다.
        userLoanHistoryRepository.save(new UserLoanHistory(user, book.getName()));
    }

    @Transactional
    public void bookReturn(BookReturnRequest request){
        //1. 유저정보 가져온다
        User user = userRepository.findByname(request.getUserName()).orElseThrow(IllegalArgumentException::new);
        
        //2. 유저정보로 대여히스토리 조회
        /*UserLoanHistory history = userLoanHistoryRepository
                .findByUserIdAndBookName(user.getId(), request.getBookName())
                .orElseThrow(IllegalArgumentException::new);*/
        //3. 반납처리
        /*history.doReturn();*/
        //트랜잭션 안에 있기 때문에 영속성 변경이 이루어지면 따로 업데이트 안해도 됨
        //userLoanHistoryRepository.save(history);

        user.returnBook(request.getBookName());
    }
}
