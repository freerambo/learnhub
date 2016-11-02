
package com.erian.restexample.springmvc.exception;

/**
 *
 * @author Rambo Zhu<asybzhu@gmail.com>
 */
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final Long id;

    public ResourceNotFoundException(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
