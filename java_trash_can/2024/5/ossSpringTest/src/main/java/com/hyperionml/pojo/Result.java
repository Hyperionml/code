package com.hyperionml.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer code;
    private String message;
    private Object data;

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Result success(Object data){
        return new Result(1, "success", data);
    }

    public static Result success(){
        return new Result(1, "success");
    }
}
