package com.apulia.library.service;

import com.apulia.library.exception.MemberNotFoundException;
import com.apulia.library.exception.PhoneAlreadyExistsException;
import com.apulia.library.exception.SearchException;
import com.apulia.library.model.Member;
import com.apulia.library.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MemberService {

    private final MemberRepository membersRepository;

    public MemberService(MemberRepository membersRepository) {
        this.membersRepository = membersRepository;
    }

    // GET ALL
    public List<Member> getAllMembers() {
        return membersRepository.findAll();
    }

    // GET BY ID
    public Member getMemberById(Integer id) {
        return membersRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));
    }

    // CREATE
    public Member addMember(Member member) {
        if (membersRepository.existsByPhone(member.getPhone())) {
            throw new PhoneAlreadyExistsException(member.getPhone());
        }
        return membersRepository.save(member);
    }

    // UPDATE (PUT)
    public Member updateMember(int id, Member updated) {
        Member existing = getMemberById(id);

        if (!existing.getPhone().equals(updated.getPhone()) &&
                membersRepository.existsByPhone(updated.getPhone())) {
            throw new PhoneAlreadyExistsException(updated.getPhone());
        }

        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setCity(updated.getCity());
        existing.setPhone(updated.getPhone());

        return membersRepository.save(existing);
    }

    // PATCH
    public Member patchMember(int id, Map<String, Object> updates) {
        Member existing = getMemberById(id);

        updates.forEach((key, value) -> {
            if (value == null) {
                throw new SearchException("Il campo '" + key + "' non può essere null");
            }

            String stringValue = value.toString().trim();

            switch (key) {
                case "firstName" -> {
                    if (stringValue.isBlank()) {
                        throw new SearchException("Il nome non può essere vuoto");
                    }
                    existing.setFirstName(stringValue);
                }
                case "lastName" -> {
                    if (stringValue.isBlank()) {
                        throw new SearchException("Il cognome non può essere vuoto");
                    }
                    existing.setLastName(stringValue);
                }
                case "city" -> {
                    if (stringValue.isBlank()) {
                        throw new SearchException("La città non può essere vuota");
                    }
                    existing.setCity(stringValue);
                }
                case "phone" -> {
                    if (stringValue.isBlank()) {
                        throw new SearchException("Il numero di telefono non può essere vuoto");
                    }

                    if (!existing.getPhone().equals(stringValue) &&
                            membersRepository.existsByPhone(stringValue)) {
                        throw new PhoneAlreadyExistsException(stringValue);
                    }
                    existing.setPhone(stringValue);
                }
                default -> throw new SearchException("Campo '" + key + "' non supportato");
            }
        });

        return membersRepository.save(existing);
    }

    // DELETE
    public void deleteMember(int id) {
        Member existing = getMemberById(id);
        membersRepository.delete(existing);
    }

    // SEARCH SMART
    public List<Member> search(String firstName, String lastName, String city, String phone) {

        boolean hasFirst = firstName != null && !firstName.isBlank();
        boolean hasLast  = lastName != null && !lastName.isBlank();
        boolean hasCity  = city != null && !city.isBlank();
        boolean hasPhone = phone != null && !phone.isBlank();

        if (hasPhone) {
            return searchByPhone(phone);
        }

        if (hasCity) {
            return searchByCity(city);
        }

        if (hasFirst || hasLast) {
            return searchByFirstNameAndLastName(firstName, lastName);
        }

        throw new SearchException("Devi specificare almeno un parametro di ricerca");
    }

    // SEARCH BY CITY
    public List<Member> searchByCity(String city) {
        List<Member> results = membersRepository.findByCityContainingIgnoreCase(city);

        if (results.isEmpty()) {
            throw new SearchException("Nessun membro trovato nella città: " + city);
        }

        return results;
    }

    // SEARCH BY PHONE (mancava!)
    public List<Member> searchByPhone(String phone) {
        List<Member> results = membersRepository.findByPhone(phone);

        if (results.isEmpty()) {
            throw new SearchException("Nessun membro trovato con il numero: " + phone);
        }

        return results;
    }

    // SEARCH BY FIRSTNAME + LASTNAME
    public List<Member> searchByFirstNameAndLastName(String firstName, String lastName) {

        boolean hasFirst = firstName != null && !firstName.isBlank();
        boolean hasLast  = lastName != null && !lastName.isBlank();

        if (!hasFirst && !hasLast) {
            throw new SearchException("Devi specificare almeno 'firstName' oppure 'lastName'");
        }

        List<Member> results;

        if (hasFirst && !hasLast) {
            results = membersRepository.findByFirstNameContainingIgnoreCase(firstName);
        }
        else if (!hasFirst) {
            results = membersRepository.findByLastNameContainingIgnoreCase(lastName);
        }
        else {
            results = membersRepository
                    .findByFirstNameContainingIgnoreCase(firstName)
                    .stream()
                    .filter(m -> m.getLastName().toLowerCase().contains(lastName.toLowerCase()))
                    .toList();
        }

        if (results.isEmpty()) {
            throw new SearchException("Nessun membro trovato con i criteri forniti");
        }

        return results;
    }
}
