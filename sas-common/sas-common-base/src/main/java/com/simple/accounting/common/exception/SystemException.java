package com.simple.accounting.common.exception;

/**
 * 系统异常类
 * 用于处理系统级别的异常情况
 */
public class SystemException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 构造函数
     * @param message 错误信息
     */
    public SystemException(String message) {
        super(message);
        this.code = 500;
        this.message = message;
    }

    /**
     * 构造函数
     * @param code 错误码
     * @param message 错误信息
     */
    public SystemException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 构造函数
     * @param message 错误信息
     * @param cause 原始异常
     */
    public SystemException(String message, Throwable cause) {
        super(message, cause);
        this.code = 500;
        this.message = message;
    }

    /**
     * 构造函数
     * @param code 错误码
     * @param message 错误信息
     * @param cause 原始异常
     */
    public SystemException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
