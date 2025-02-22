package com.mycompany.bookservice.rest;

import com.mycompany.bookservice.mapper.BookMapper;
import com.mycompany.bookservice.model.Book;
import com.mycompany.bookservice.rest.dto.BookResponse;
import com.mycompany.bookservice.rest.dto.CreateBookRequest;
import com.mycompany.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private final Environment environment;
    private final BookMapper bookMapper;

    @GetMapping("/dbcredentials")
    public String getDBCredentials() {
        return String.format("%s/%s",
                environment.getProperty("spring.datasource.username"),
                environment.getProperty("spring.datasource.password"));
    }

    @GetMapping("/secretMessage")
    public String getSecretMessage() {
        return environment.getProperty("message");
    }

    @GetMapping
    public List<BookResponse> getBooks() {
        return bookService.getBooks()
                .stream()
                .map(bookMapper::toBookResponse)
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BookResponse createBook(@Valid @RequestBody CreateBookRequest createBookRequest) {
        Book book = bookMapper.toBook(createBookRequest);
        book = bookService.saveBook(book);
        return bookMapper.toBookResponse(book);
    }
}
