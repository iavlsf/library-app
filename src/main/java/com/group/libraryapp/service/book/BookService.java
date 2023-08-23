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
    private final UserRepository userRepository;

    public BookService(BookRepository bookRepository,UserLoanHistoryRepository userLoanHistoryRepository,UserRepository userRepository)
    {
        this.bookRepository = bookRepository;
        this.userLoanHistoryRepository = userLoanHistoryRepository;
        this.userRepository = userRepository;
    }
    @Transactional
    public void saveBook(BookCreateRequest request) {
        Book book = bookRepository.save(new Book(request.getName()));
    }


    @Transactional
    public void loanBook(BookLoanRequest request){
        //         * 1. 책 정보를 가져온다.
        Book book = bookRepository.findByName(request.getBookName()).orElseThrow();//Optional 활용한 null체크

        //         * 2. 대출기록 정보를 확인해서 대출 중인지 확인한다.
        if(userLoanHistoryRepository.existsByBookNameAndIsReturn(book.getName(), false)){
        //         * 3. 만약 대출중이라면 예외를 발생시킨다.
            throw new IllegalArgumentException("이미 대출 중인 책입니다.");
        }

        //4. 유저 정보를 가져온다.
        User user = userRepository.findByName(request.getUserName()).orElseThrow(IllegalArgumentException::new);
        //5. 가져온 유저정보와 책 이름으로 UserLoanHistory를 생성하고 테이블에 저장한다.
        user.loanBook(book.getName());//cascade옵션을 사용해서 User를 통해 userLoanHistories에 매핑하는 간접적인 동작으로 테이블에 자동 생성되게함,추가로 user를 UserRepository에서 세이브 안시켜도 트랜잭션에 의해 변경점이 자동 저장됨
        //userLoanHistoryRepository.save(new UserLoanHistory(userRepository.findByName(request.getUserName()).orElseThrow(IllegalArgumentException::new), request.getBookName())); //cascade옵션을 사용하지 않으면 userLoanHistoryRepository를 직접사용해서 테이블에 데이터를 생성


    }

    @Transactional
    public void returnBook(BookReturnRequest request) {
        //         * 1. 책 정보를 가져온다.
        Book book = bookRepository.findByName(request.getBookName())
                .orElseThrow(IllegalArgumentException::new);//Optional 활용한 null체크

        //         * 2. 대출기록 정보를 확인해서 대출 중인지 확인한다.
        if(userLoanHistoryRepository.existsByBookNameAndIsReturn(book.getName(), false)){
            //4. 유저 정보를 가져온다.
            User user = userRepository.findByName(request.getUserName()).orElseThrow(IllegalArgumentException::new);
            System.out.println("hello");
            //5. 가져온 유저정보와 책 이름으로 UserLoanHistory를 조회하고 isReturn을 변경한다.
            user.returnBook(request.getBookName());
            //UserLoanHistory userLoanHistory = userLoanHistoryRepository.findByUserIdAndBookName(userRepository.findByName(request.getUserName()).orElseThrow(IllegalArgumentException::new).getId(), book.getName());
            //userLoanHistory.doIsReturn();
            //userLoanHistoryRepository.save(userLoanHistory); 영속성 컨텍스트 안에서 이뤄진 객체의 변화는 자동으로 save(여기선 update)를 해주기 때문에 생략가능함

        }else{
            //         * 3. 만약 대출중이 아니라면 예외를 발생시킨다.
            throw new IllegalArgumentException("현재 대출 중 상태가 아닙니다.");
        }



    }

}
