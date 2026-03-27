package com.retail.rewards.exception;

/**
 * Exception thrown when a requested resource is not found.
 *
 * <p>This exception is typically used when a requested entity
 * (e.g., customer transactions) does not exist in the database.</p>
 *
 * <p>Handled globally using {@code GlobalExceptionHandler} to return
 * appropriate HTTP status (404 NOT FOUND).</p>
 */
public class ResourceNotFoundException extends RuntimeException{

	public ResourceNotFoundException(String message) {
        super(message);
    }
}
