package com.group.libraryapp.repository.book;


import com.group.libraryapp.domain.book.Book;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Primary
@Repository
public class BookMemoryRepository implements BookRepository{
    List<Book> books = new ArrayList<>();
    @Override
    public void saveBook() {
        System.out.println("memoryRepository");
//        books.add(new Book());
    }
}
