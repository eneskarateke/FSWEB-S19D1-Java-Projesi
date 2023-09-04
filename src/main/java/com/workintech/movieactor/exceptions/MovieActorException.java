package com.workintech.movieactor.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class MovieActorException extends RuntimeException{
    private HttpStatus code;

    public MovieActorException(String message, HttpStatus code) {
        super(message);
        this.code = code;
    }
}
