package com.mytaxi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The driver has not been assigned any car.")
public class DriverHasNoCarAssignedException extends Exception
{
	private static final long serialVersionUID = 4466116509246888915L;

	public DriverHasNoCarAssignedException(String message)
    {
        super(message);
    }
}
