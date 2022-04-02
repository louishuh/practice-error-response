package com.example.res.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
public class RequestBodyController {

    private static final List<String> invalidMemberNameList;

    static {
        invalidMemberNameList = List.of("a", "b");
    }

    @PostMapping("/members")
    public void create(@Valid @RequestBody MemberPayload memberPayload) {

        if (invalidMemberNameList.contains(memberPayload.getName())) {
            throw new InvalidMemberNameException();
        }

        log.debug("member: {}", memberPayload);
    }
}
