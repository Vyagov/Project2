package backend.exception;

import org.springframework.validation.ObjectError;

import java.util.List;

public class ValidationResponse {
    private String status;
    private String generalErrorText;
    private List<ObjectError> errorMessageList;

    public ValidationResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGeneralErrorText() {
        return generalErrorText;
    }

    public void setGeneralErrorText(String generalErrorText) {
        this.generalErrorText = generalErrorText;
    }

    public List<ObjectError> getErrorMessageList() {
        return errorMessageList;
    }

    public void setErrorMessageList(List<ObjectError> errorMessageList) {
        this.errorMessageList = errorMessageList;
    }
}
