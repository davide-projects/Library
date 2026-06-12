package com.apulia.library.exception;

public class LoanExpiredYetException extends RuntimeException{

    public LoanExpiredYetException(Integer id) {
        super("Libro con id " + id + "è stato già restituito");
    }

}
