package com.hhjang.spring.study.gof.structure.adapter;

import com.hhjang.spring.study.gof.structure.decorator.model.beverage.Americano;
import com.hhjang.spring.study.gof.structure.decorator.model.beverage.Beverage;
import com.hhjang.spring.study.gof.structure.decorator.model.beverage.Mocha;
import com.hhjang.spring.study.gof.structure.decorator.model.condiment.DecafDecorator;
import com.hhjang.spring.study.gof.structure.decorator.model.condiment.WhippingCreamDecorator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DecoratorPatternTest {

    /*
        가격표
        * 음료
        1. 아메리카노 3.0
        2. 모카 3.5

        * 첨가물
        1. 휘핑크림 0.5
        2. 디카페인 0.3
     */
    @Test
    @DisplayName("Decorator 패턴을 이용하여 음료에 여러 옵션을 추가할 수 있다.")
    public void decorate_beverage_using_decorator() {
        Beverage decaffeinatedAmericano = new DecafDecorator(new Americano());
        Beverage whippedMocha = new WhippingCreamDecorator(new Mocha());
        Beverage decaffeinatedAndWhippedMocha = new DecafDecorator(new WhippingCreamDecorator(new Mocha()));

        // 아메리카노 + 디카페인
        System.out.println(decaffeinatedAmericano.getDescription());

        // 모카 + 휘핑크림
        System.out.println(whippedMocha.getDescription());

        // 모카 + 휘핑크림 + 디카페인
        System.out.println(decaffeinatedAndWhippedMocha.getDescription());

        assertThat(decaffeinatedAmericano.cost()).isEqualTo(3.3);
        assertThat(whippedMocha.cost()).isEqualTo(4.0);
        assertThat(decaffeinatedAndWhippedMocha.cost()).isEqualTo(4.3);
    }
}
