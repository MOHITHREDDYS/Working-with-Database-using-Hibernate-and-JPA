package com.example.goodreads.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import com.example.goodreads.repository.BookRepository;
import com.example.goodreads.repository.BookJpaRepository;
import com.example.goodreads.model.Book;

@Service
public class BookJpaService implements BookRepository {
    @Autowired
    private BookJpaRepository bookJpaRepository;

    @Override
    public ArrayList<Book> getBooks() {
        List<Book> bookList = bookJpaRepository.findAll();
        ArrayList<Book> books = new ArrayList<>(bookList);
        return books;
    }

    @Override
    public Book getBookById(int bookId) {
        try {
            Book book = bookJpaRepository.findById(bookId).get();
            return book;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Book addBook(Book book) {
        bookJpaRepository.save(book);
        return book;
    }

    @Override
    public Book updateBook(int bookId, Book book) {
        Book savedBook = getBookById(bookId);
        if (book.getName() != null)
            savedBook.setName(book.getName());
        if (book.getImageUrl() != null)
            savedBook.setImageUrl(book.getImageUrl());

        return addBook(savedBook);
    }

    @Override
    public void deleteBook(int bookId) {
        try {
            bookJpaRepository.deleteById(bookId);
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}