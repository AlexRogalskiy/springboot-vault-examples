package com.mycompany.bookservice.service;

import com.mycompany.bookservice.model.Book;

import java.util.List;

public interface BookService {

    List<Book> getBooks();

    Book saveBook(Book book);
}
