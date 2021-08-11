package com.demxs.tdm.common.file.exception;

/**
 * 文档下载异常.
 * User: wuliepeng
 * Date: 2017-08-04
 * Time: 下午2:16
 */
public class DownloadException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DownloadException() {
        super();
    }

    public DownloadException(String message) {
        super(message);
    }

    public DownloadException(Throwable cause) {
        super(cause);
    }

    public DownloadException(String message, Throwable cause) {
        super(message, cause);
    }
}