package com.sparta.commons.domain.response;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;

@Getter
@JsonTypeName("true")
public final class SuccessResponseBody<T> extends ResponseBody<T> {
    private final T data;

    public SuccessResponseBody() {
        data = null;
    }

    public SuccessResponseBody(String code, T result) {
        super();
        this.data = result;
        this.setCode(code);
    }
}
