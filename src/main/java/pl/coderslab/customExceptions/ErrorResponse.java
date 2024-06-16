package pl.coderslab.customExceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {
    private final int responseStatusCode;
    private final String message;
}
