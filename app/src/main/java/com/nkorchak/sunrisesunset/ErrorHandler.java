package com.nkorchak.sunrisesunset;

/**
 * Created by nazarkorchak on 24.01.18.
 */

public class ErrorHandler {

    public String handleError(String errorMessage) {
        String error;
        switch (errorMessage) {
            case "INVALID_REQUEST":
                error = "INVALID_REQUEST";
                break;
            case "INVALID_DATE":
                error = "INVALID_DATE";
                break;
            default:
                error = "UNKNOWN_ERROR";
        }
        return error;
    }
}
