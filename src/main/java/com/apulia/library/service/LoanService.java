package com.apulia.library.service;

import com.apulia.library.exception.BookNotFoundException;
import com.apulia.library.exception.LoanExpiredYetException;
import com.apulia.library.exception.MemberNotFoundException;
import com.apulia.library.exception.SearchException;
import com.apulia.library.model.Book;
import com.apulia.library.model.Loan;
import com.apulia.library.model.Member;
import com.apulia.library.repository.BookRepository;
import com.apulia.library.repository.LoanRepository;
import com.apulia.library.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public LoanService(LoanRepository loanRepository,
                       BookRepository bookRepository,
                       MemberRepository memberRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }


    // GET ALL
    @Transactional(readOnly = true)
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    // GET BY ID
    @Transactional(readOnly = true)
    public Loan getLoanById(Integer id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new LoanExpiredYetException(id));
    }

    // GET BY
    @Transactional(readOnly = true)
    public List<Loan> getLoansByMemberId(Integer memberId) {
        // Verifica che il membro esista
        if (!memberRepository.existsById(memberId)) {
            throw new MemberNotFoundException(memberId);
        }
        List<Loan> results = loanRepository.findByMemberId(memberId);
        if (results.isEmpty()) {
            throw new SearchException("Nessun prestito trovato per il membro con id: " + memberId);
        }
        return results;
    }

    // GET BY BOOK
    @Transactional(readOnly = true)
    public List<Loan> getLoansByBookId(Integer bookId) {
        // Verifica che il libro esista
        if (!bookRepository.existsById(bookId)) {
            throw new BookNotFoundException(bookId);
        }
        List<Loan> results = loanRepository.findByBookId(bookId);
        if (results.isEmpty()) {
            throw new SearchException("Nessun prestito trovato per il libro con id: " + bookId);
        }
        return results;
    }

    // CREATE
    @Transactional
    public Loan addLoan(Integer bookId, Integer memberId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));

        // Controlla se il libro è già in prestito
        if (loanRepository.existsByBookIdAndReturnDateIsNull(bookId)) {
            throw new SearchException("Il libro con id " + bookId + " è già in prestito");
        }

        Loan loan = new Loan(book, member, LocalDate.now());
        return loanRepository.save(loan);
    }

    // RETURN BOOK (patch returnDate)
    @Transactional
    public Loan returnBook(Integer id) {
        Loan loan = getLoanById(id);

        if (loan.getReturnDate() != null) {
            throw new SearchException("Il prestito con id " + id + " è già stato restituito");
        }
        loan.setReturnDate(LocalDate.now());
        return loan;
    }

    // DELETE
    @Transactional
    public void deleteLoan(Integer id) {
        Loan loan = getLoanById(id);
        loanRepository.delete(loan);
    }

    // GET ACTIVE LOANS BY MEMBER
    @Transactional(readOnly = true)
    public List<Loan> getActiveLoansByMemberId(Integer memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberNotFoundException(memberId);
        }
        List<Loan> results = loanRepository.findByMemberIdAndReturnDateIsNull(memberId);
        if (results.isEmpty()) {
            throw new SearchException("Nessun prestito attivo per il membro con id: " + memberId);
        }
        return results;
    }
}