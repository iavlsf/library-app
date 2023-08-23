package com.group.libraryapp.controller.book;

import com.group.libraryapp.dto.book.request.BookCreateRequest;
import com.group.libraryapp.dto.book.request.BookLoanRequest;
import com.group.libraryapp.dto.book.request.BookReturnRequest;
import com.group.libraryapp.service.book.BookService;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * 책 등록 API
     * HTTP Method: POST
     * HTTP Path: /book
     * 데이터 전달방법 - HTTP Request Body (JSON):
     * {
     * 	"name": String //책이름
     * }
     * 결과 반환 X(200 OK만)
     */
    @PostMapping("/book")
    public void saveBook(@RequestBody BookCreateRequest request) {

        bookService.saveBook(request);
    }

    @GetMapping("/book")
    public void getBook() {

    }

    @PutMapping("/book")
    public void updateBook() {

    }

    @DeleteMapping("/book")
    public void deleteBook() {

    }

    /**
     * HTTP Method: POST
     * HTTP Path: /book/loan
     * HTTP Request Body (JSON):
     * {
     * "userName": String
     * "bookName": String
     * }
     * 결과 반환 X(200 OK만)
     */
    @PostMapping("/book/loan")
    public void loanBook(@RequestBody BookLoanRequest request) {
        bookService.loanBook(request);

    }

    /**
     * HTTP Method: PUT    //PUT이면 테이블,도메인(@Entity),Repository 새로 만들진 않음(데이터를 수정하라는 의미니까)
     * HTTP Path: /book/return
     * HTTP Request Body (JSON):
     * {
     * "userName": String
     * "bookName": String
     * }
     * 결과 반환 X(200 OK만)
     */
    @PutMapping("/book/return")
    public void returnBook(@RequestBody BookReturnRequest request) {
        bookService.returnBook(request);
    }
}
