package com.example.res.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@ToString
public class MemberPayload {

    @NotBlank
    private String name;
    @NotNull
    private Integer age;

    @NotNull @Size(min=2)
    private List<@Valid Phone> phones;

    @Getter @Setter
    public static class Phone {
        @NotBlank
        private String number;
    }
}
