package com.hhjang.spring.study.gof.structure.adapter;

public class RequestAdapter implements RequestInterface {

    private ThirdPartyRequest thirdPartyRequest;

    public RequestAdapter(ThirdPartyRequest thirdPartyRequest) {
        this.thirdPartyRequest = thirdPartyRequest;
    }

    @Override
    public void sendRequest() {
        thirdPartyRequest.sendThirdPartyRequest();
    }
}
