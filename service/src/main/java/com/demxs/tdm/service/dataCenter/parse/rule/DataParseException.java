package com.demxs.tdm.service.dataCenter.parse.rule;

import com.demxs.tdm.common.service.ServiceException;

public class DataParseException extends ServiceException {
    public DataParseException() {
        super();
    }

    public DataParseException(String message) {
        super(message);
    }
}
