package backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Passwords not matches!")
public class PasswordsNotMatchException extends CustomBaseException {

    public PasswordsNotMatchException(String message) {
        super(message);
    }

}
