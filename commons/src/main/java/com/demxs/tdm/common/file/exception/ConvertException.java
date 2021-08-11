package com.demxs.tdm.common.file.exception;

/**
 * 文档转换异常.
 * User: wuliepeng
 * Date: 2017-08-04
 * Time: 下午2:16
 */
public class ConvertException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ConvertException() {
        super();
    }

    public ConvertException(String message) {
        super(message);
    }

    public ConvertException(Throwable cause) {
        super(cause);
    }

    public ConvertException(String message, Throwable cause) {
        super(message, cause);
    }
}