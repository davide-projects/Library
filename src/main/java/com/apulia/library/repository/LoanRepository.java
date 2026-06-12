package com.apulia.library.repository;

import com.apulia.library.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {

    // Tutti i prestiti di un membro
    List<Loan> findByMemberId(Integer memberId);

    // Tutti i prestiti di un libro
    List<Loan> findByBookId(Integer bookId);

    // Prestiti attivi (non ancora restituiti) di un membro
    List<Loan> findByMemberIdAndReturnDateIsNull(Integer memberId);

    // Prestiti attivi di un libro (per controllare disponibilità)
    boolean existsByBookIdAndReturnDateIsNull(Integer bookId);
}