package com.apulia.library.dto;

public class LoanRequest {

    private Integer bookId;
    private Integer memberId;

    public LoanRequest() {}

    public LoanRequest(Integer bookId, Integer memberId) {
        this.bookId = bookId;
        this.memberId = memberId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }
}
