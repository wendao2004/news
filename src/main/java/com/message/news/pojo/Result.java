package com.message.news.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;
    private String msg;

    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.code = 200; // 把0改成200即可
        result.msg = "操作成功";
        return result;
    }

    public static <E> Result<E> error(String message) {
        return new Result<>(1, message, null,"操作失败");
    }

    public static <E> Result<E> fail(String string) {
        return new Result<>(1, string, null,"操作失败");
    }
}
