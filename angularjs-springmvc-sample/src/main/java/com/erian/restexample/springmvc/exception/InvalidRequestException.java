package com.erian.restexample.springmvc.exception;

import org.springframework.validation.BindingResult;

/**
 *
 * @author Rambo Zhu<asybzhu@gmail.com>
 */
public class InvalidRequestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final BindingResult errors;

    public InvalidRequestException(BindingResult errors) {
        this.errors = errors;
    }

    public BindingResult getErrors() {
        return this.errors;
    }
}
