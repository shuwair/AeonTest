package org.aeon.test;

import org.aeon.test.entity.Book;
import org.aeon.test.entity.Borrower;
import org.aeon.test.repository.BookRepository;
import org.aeon.test.repository.BorrowerRepository;
import org.aeon.test.service.LibraryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LibraryServiceTest {

    @Mock
    private BorrowerRepository borrowerRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private LibraryService libraryService;

    private Borrower borrower;
    private Book book;

    @BeforeEach
    void setUp() {
        borrower = new Borrower("John Doe", "john.doe@example.com");
        borrower.setId(1L);

        book = new Book("978-3-16-148410-0", "Effective Java", "Joshua Bloch");
        book.setId(1L);
    }

    @Test
    void testBorrowBook_Success() {
        when(borrowerRepository.findById(1L)).thenReturn(Optional.of(borrower));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        String result = libraryService.borrowBook(1L, 1L);

        assertEquals("Book borrowed successfully", result);
        assertTrue(book.isBorrowed());
    }

    @Test
    void testReturnBook_Success() {
        book.setBorrowed(true);

        when(borrowerRepository.findById(1L)).thenReturn(Optional.of(borrower));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        String result = libraryService.returnBook(1L, 1L);

        assertEquals("Book returned successfully", result);
        assertFalse(book.isBorrowed());
    }
}

