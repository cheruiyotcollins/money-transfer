package com.pezesha.moneytransfer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
    @JsonInclude(JsonInclude.Include. NON_NULL)
    private Object payload;
    private HttpStatus status;
    private String Description;
}
