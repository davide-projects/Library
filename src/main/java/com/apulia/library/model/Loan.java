package com.apulia.library.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@JsonPropertyOrder({"id", "book", "member", "loanDate", "returnDate"})
@JsonIgnoreProperties(ignoreUnknown = false)

@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Il campo 'book' è obbligatorio")
    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @NotNull(message = "Il campo 'member' è obbligatorio")
    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @NotNull(message = "Il campo 'loanDate' è obbligatorio")
    @Column(name = "loan_date", nullable = false)
    private LocalDate loanDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    public Loan() {}

    public Loan(Book book, Member member, LocalDate loanDate, LocalDate returnDate) {
        this.book = book;
        this.member = member;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    public Loan(Book book, Member member, LocalDate loanDate) {
        this.book = book;
        this.member = member;
        this.loanDate = loanDate;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }

    public LocalDate getLoanDate() { return loanDate; }
    public void setLoanDate(LocalDate loanDate) { this.loanDate = loanDate; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", book=" + book +
                ", member=" + member +
                ", loanDate=" + loanDate +
                ", returnDate=" + returnDate +
                '}';
    }
}