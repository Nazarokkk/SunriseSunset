package com.nkorchak.sunrisesunset;

/**
 * Copyright (c) 2004-2017, SpamDrain AB. All Rights Reserved.
 * <p>
 * Redistribution and use in source and binary forms, with or
 * without modification, are prohibited without specific prior
 * written permission from SpamDrain AB.
 * <p>
 * This notice and attribution to SpamDrain AB may not be removed.
 * Created by nkorchak on 24.01.18.
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
