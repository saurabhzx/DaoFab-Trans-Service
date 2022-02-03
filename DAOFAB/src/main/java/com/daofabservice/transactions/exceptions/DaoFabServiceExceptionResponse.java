package com.daofabservice.transactions.exceptions;

import lombok.Data;

@Data
public class DaoFabServiceExceptionResponse {
    protected final String errorCode;
    protected final String errorMessage;
}
