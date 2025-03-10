package org.aeon.test;

import org.aeon.test.entity.Borrower;
import org.aeon.test.repository.BorrowerRepository;
import org.aeon.test.service.BorrowerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BorrowerServiceTest {

    @Mock
    private BorrowerRepository borrowerRepository;

    @InjectMocks
    private BorrowerService borrowerService;

    private Borrower borrower;

    @BeforeEach
    void setUp() {
        borrower = new Borrower("John Doe", "john.doe@example.com");
        borrower.setId(1L);
    }

    @Test
    void testRegisterBorrower() {
        when(borrowerRepository.save(any(Borrower.class))).thenReturn(borrower);
        Borrower savedBorrower = borrowerService.registerBorrower(borrower);
        assertNotNull(savedBorrower);
        assertEquals("John Doe", savedBorrower.getName());
    }

    @Test
    void testGetAllBorrowers() {
        when(borrowerRepository.findAll()).thenReturn(List.of(borrower));
        List<Borrower> borrowers = borrowerService.getAllBorrowers();
        assertEquals(1, borrowers.size());
    }

    @Test
    void testGetBorrowerById() {
        when(borrowerRepository.findById(1L)).thenReturn(Optional.of(borrower));
        Optional<Borrower> foundBorrower = borrowerService.getBorrowerById(1L);
        assertTrue(foundBorrower.isPresent());
        assertEquals("John Doe", foundBorrower.get().getName());
    }
}

