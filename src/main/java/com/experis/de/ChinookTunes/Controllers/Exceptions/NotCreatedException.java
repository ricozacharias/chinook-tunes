package com.experis.de.ChinookTunes.Controllers.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class NotCreatedException extends RuntimeException
{
    public NotCreatedException(String exception) {
        super(exception);
    }
}
