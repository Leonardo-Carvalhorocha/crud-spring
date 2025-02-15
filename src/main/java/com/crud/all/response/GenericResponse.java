package com.crud.all.response;

public class GenericResponse<T, data> implements ResponseDTO<T>{

    private final String message;
    private final T data;

    public GenericResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public T getData() {
        return data;
    }

}
