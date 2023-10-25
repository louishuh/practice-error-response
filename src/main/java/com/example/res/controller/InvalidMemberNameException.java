package com.example.res.controller;

import com.gmail.imlouishuh.web.BusinessException;

public class InvalidMemberNameException extends BusinessException {

    public InvalidMemberNameException(String invalidName) {
        super("name.Invalid");
        addArgument("invalidName", invalidName);
    }

}
