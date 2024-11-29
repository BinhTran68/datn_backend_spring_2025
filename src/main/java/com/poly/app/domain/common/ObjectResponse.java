package com.poly.app.domain.common;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ObjectResponse {
    private boolean isSuccess = false;
    private String mess;
    private Object data;

    public <T> ObjectResponse(T obj) {
        processResponseObject(obj);
    }

    public void processResponseObject(Object obj) {
        if (obj != null) {
            this.isSuccess = true;
            this.data = obj;
        }
    }
}
