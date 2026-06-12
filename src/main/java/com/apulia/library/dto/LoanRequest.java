package com.apulia.library.dto;

import java.util.List;

public class LoanRequest {

    private List<Integer> bookIds;
    private Integer memberId;

    public LoanRequest() {}

    public LoanRequest(List<Integer> bookIds, Integer memberId) {
        this.bookIds = bookIds;
        this.memberId = memberId;
    }

    public List<Integer> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<Integer> bookIds) {
        this.bookIds = bookIds;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }
}
