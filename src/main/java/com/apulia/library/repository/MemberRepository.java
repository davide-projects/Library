package com.apulia.library.repository;

import com.apulia.library.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    // Ricerca per nome (case insensitive)
    List<Member> findByFirstNameContainingIgnoreCase(String firstName);

    // Ricerca per cognome (case insensitive)
    List<Member> findByLastNameContainingIgnoreCase(String lastName);

    // Ricerca per città (case insensitive)
    List<Member> findByCityContainingIgnoreCase(String city);

    // Ricerca per telefono (match esatto)
    List<Member> findByPhone(String phone);

    // Controlla se il numero esiste già
    boolean existsByPhone(String phone);

}
