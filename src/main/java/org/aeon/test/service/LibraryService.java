package org.aeon.test.service;

import org.aeon.test.entity.Book;
import org.aeon.test.entity.Borrower;
import org.aeon.test.repository.BookRepository;
import org.aeon.test.repository.BorrowerRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

@Service
public class LibraryService {

    private final BorrowerRepository borrowerRepository;
    private final BookRepository bookRepository;

    @Autowired
    public LibraryService(BorrowerRepository borrowerRepository, BookRepository bookRepository) {
        this.borrowerRepository = borrowerRepository;
        this.bookRepository = bookRepository;
    }

    public String borrowBook(Long borrowerId, Long bookId) {
        Optional<Borrower> borrowerOpt = borrowerRepository.findById(borrowerId);
        Optional<Book> bookOpt = bookRepository.findById(bookId);

        if (borrowerOpt.isEmpty() || bookOpt.isEmpty()) {
            throw new IllegalArgumentException("Borrower or Book not found.");
        }

        Book book = bookOpt.get();
        if (book.isBorrowed()) {
            throw new IllegalStateException("Book is already borrowed.");
        }

        book.setBorrowed(true);
        bookRepository.save(book);
        return "Book borrowed successfully";
    }

    public String returnBook(Long borrowerId, Long bookId) {
        Optional<Borrower> borrowerOpt = borrowerRepository.findById(borrowerId);
        Optional<Book> bookOpt = bookRepository.findById(bookId);

        if (borrowerOpt.isEmpty() || bookOpt.isEmpty()) {
            throw new IllegalArgumentException("Borrower or Book not found.");
        }

        Book book = bookOpt.get();
        if (!book.isBorrowed()) {
            throw new IllegalStateException("Book is not borrowed.");
        }

        book.setBorrowed(false);
        bookRepository.save(book);
        return "Book returned successfully";
    }
}

