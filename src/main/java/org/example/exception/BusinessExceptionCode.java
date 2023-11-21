package org.example.exception;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum BusinessExceptionCode {
    INVALID_USER("INVALID_USER", "The user has invalid fields!"),
    INVALID_COURSE("INVALID_COURSE","The course has invalid fields");

    private String errorId;
    private String message;
}
