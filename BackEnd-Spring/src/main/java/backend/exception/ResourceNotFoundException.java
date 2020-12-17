package backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Resource is not found!")
public class ResourceNotFoundException extends CustomBaseException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
