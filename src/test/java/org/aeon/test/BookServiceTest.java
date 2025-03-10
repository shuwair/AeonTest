package org.aeon.test;

import org.aeon.test.entity.Book;
import org.aeon.test.repository.BookRepository;
import org.aeon.test.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book("978-3-16-148410-0", "Effective Java", "Joshua Bloch");
        book.setId(1L);
    }

    @Test
    void testRegisterBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        Book savedBook = bookService.registerBook(book);
        assertNotNull(savedBook);
        assertEquals("Effective Java", savedBook.getTitle());
    }

    @Test
    void testGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(List.of(book));
        List<Book> books = bookService.getAllBooks();
        assertEquals(1, books.size());
    }

    @Test
    void testGetBookById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Optional<Book> foundBook = bookService.getBookById(1L);
        assertTrue(foundBook.isPresent());
        assertEquals("Effective Java", foundBook.get().getTitle());
    }
}

