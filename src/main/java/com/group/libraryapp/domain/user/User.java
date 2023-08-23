package com.group.libraryapp.domain.user;

import com.group.libraryapp.domain.user.loanHistory.UserLoanHistory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @Column(nullable = false, length = 20, name="name")
    private String name;
    private Integer age;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserLoanHistory> userLoanHistories = new ArrayList<>(); //인터페이스라 구현체 새 인스턴스 넣어두는거(초기화하는거)

    protected User() {

    }

    public User(String name, Integer age) {
        if (name == null || name.isBlank()){
            throw new IllegalArgumentException(
                    String.format("잘못된 name(%s)이 들어왔습니다.", name)
            );
        }
            this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Long getId() {
        return id;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void loanBook(String bookName) {
        this.userLoanHistories.add(new UserLoanHistory(this, bookName));
    }

    public void returnBook(String bookName) {
        UserLoanHistory targetHistory = this.userLoanHistories.stream().   //User도메인 안에서 bookName만 받고 userLoanHIstories에서 bookName으로 return대상인 책을 찾는다.(stream의 filter사용)
                filter(history -> history.getBookName().equals(bookName) && history.getReturn()==false)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        targetHistory.doIsReturn();  //찾은 userLoanHistory의 doIsReturn을 사용해서 책반납완료
    }
}
