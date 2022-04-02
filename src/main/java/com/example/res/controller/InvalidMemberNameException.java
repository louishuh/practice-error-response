package com.example.res.controller;

import com.gmail.imlouishuh.web.BusinessException;
import com.gmail.imlouishuh.web.ResponseCode;

@ResponseCode("name.Invalid")
public class InvalidMemberNameException extends BusinessException {
}
