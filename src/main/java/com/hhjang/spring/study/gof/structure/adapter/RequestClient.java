package com.hhjang.spring.study.gof.structure.adapter;

public class RequestClient {

    private RequestInterface requestInterface;

    public RequestClient(RequestInterface requestInterface) {
        this.requestInterface = requestInterface;
    }

    public void execute() {
        requestInterface.sendRequest();
    }
}
