package com.hhjang.spring.study.gof.structure.adapter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AdapterPatternTest {

    @Test
    @DisplayName("Adapter 패턴을 적용하여 Adaptee(변경 불가능한 ThirdPartyLibrary)를 Target Interface에 맞게 Adapter를 사용한다.")
    public void call_third_party_using_adapter() {
        ThirdPartyRequest thirdPartyRequest = new ThirdPartyRequest();
        RequestAdapter adapter = new RequestAdapter(thirdPartyRequest);
        RequestClient client = new RequestClient(adapter);

        client.execute();
    }

}