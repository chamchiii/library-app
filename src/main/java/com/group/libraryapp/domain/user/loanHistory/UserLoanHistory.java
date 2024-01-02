package com.group.libraryapp.domain.user.loanHistory;

import com.group.libraryapp.domain.user.User;

import javax.persistence.*;

@Entity
public class UserLoanHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @JoinColumn(nullable = false)
    @ManyToOne
    private User user;

    //private Long userId;

    private String bookName;

    private boolean isReturn;

    protected UserLoanHistory() {
    }

    public UserLoanHistory(User user, String bookName) {
        //this.userId = userId;
        this.user = user;
        this.bookName = bookName;
        this.isReturn = false;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getBookName() {
        return bookName;
    }

    public boolean isReturn() {
        return isReturn;
    }

    public void doReturn(){
        this.isReturn = true;
    }
}