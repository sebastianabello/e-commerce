package com.abello.ecommerce.ecommercereplica.exceptions;

public class CreditCardException extends RuntimeException{
    public CreditCardException(String message) {
        super("CREDIT_CARD_EXCEPTION: "+message);
    }

    public CreditCardException(String message, Throwable cause) {
        super(message, cause);
    }
}
