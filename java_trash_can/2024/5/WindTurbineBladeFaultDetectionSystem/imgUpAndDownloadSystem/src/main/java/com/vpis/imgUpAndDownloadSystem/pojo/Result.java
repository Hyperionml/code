package com.vpis.imgUpAndDownloadSystem.pojo;

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

    public static Result success(String msg, Object data){
        return new Result(1, msg, data);
    }

    public static Result success(String msg){
        return new Result(1, msg, null);
    }

    public static Result error(String msg){
        return new Result(2, msg, null);
    }
}
