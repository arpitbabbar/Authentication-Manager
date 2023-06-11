package com.babbarop.authenticationmanager.response;

import lombok.Data;

/**
 * @author Arpit Babbar
 * @since 2023-06-12
 */
@Data
public class AMGenericResponse {

    private int statusCode;
    private String status;
    private String errorMessage;

    public void setStatusCode(AMErrorCodes amErrorCodes) {
        this.statusCode = amErrorCodes.getStatusCode();
        this.status = amErrorCodes.getStatus();
        this.errorMessage = amErrorCodes.getErrorMessage();
    }

}
