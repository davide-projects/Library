package com.apulia.library.controller;

import com.apulia.library.dto.LoanRequest;
import com.apulia.library.model.Loan;
import com.apulia.library.service.LoanService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/loan")
@SecurityRequirement(name = "basicAuth")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        List<Loan> loans = loanService.getAllLoans();
        return ResponseEntity.ok(loans);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable int id) {
        Loan loan = loanService.getLoanById(id);
        return ResponseEntity.ok(loan);
    }

    // GET BY MEMBER
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Loan>> getLoansByMember(@PathVariable int memberId) {
        List<Loan> loans = loanService.getLoansByMemberId(memberId);
        return ResponseEntity.ok(loans);
    }

    // GET BY BOOK
    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<Loan>> getLoansByBook(@PathVariable int bookId) {
        List<Loan> loans = loanService.getLoansByBookId(bookId);
        return ResponseEntity.ok(loans);
    }

    // GET ACTIVE LOANS BY MEMBER
    @GetMapping("/member/{memberId}/active")
    public ResponseEntity<List<Loan>> getActiveLoansByMember(@PathVariable int memberId) {
        List<Loan> loans = loanService.getActiveLoansByMemberId(memberId);
        return ResponseEntity.ok(loans);
    }

    // POST - crea prestito passando bookId e memberId come query params
    @PostMapping
    public ResponseEntity<Loan> addLoan(@Valid @RequestBody LoanRequest request) {
        Loan saved = loanService.addLoan(request.getBookId(), request.getMemberId());
        URI location = URI.create("/loan/" + saved.getId());
        return ResponseEntity.created(location).body(saved);
    }

    // PATCH - restituzione libro
    @PatchMapping("/{id}/return")
    public ResponseEntity<Loan> returnBook(@PathVariable int id) {
        Loan returned = loanService.returnBook(id);
        return ResponseEntity.ok(returned);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable int id) {
        loanService.deleteLoan(id);
        return ResponseEntity.noContent().build(); // 204
    }
}