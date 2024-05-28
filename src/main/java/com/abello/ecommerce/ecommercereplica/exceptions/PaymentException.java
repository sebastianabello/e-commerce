package com.abello.ecommerce.ecommercereplica.exceptions;

public class PaymentException extends RuntimeException{
    public PaymentException(String message) {
        super("PAYMENT_EXCEPTION: "+message);
    }

    public PaymentException(String message, Throwable cause) {
        super(message, cause);
    }
}
