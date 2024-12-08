package com.example.myfolderapp.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer code;
    private String msg;
    private String data;

    public static Result success(String msg){
        return new Result(1, msg, null);
    }

    public static Result success(String msg, String data){
        return new Result(1, msg, data);
    }

    public static Result err(String msg){
        return new Result(2, msg, null);
    }
}
