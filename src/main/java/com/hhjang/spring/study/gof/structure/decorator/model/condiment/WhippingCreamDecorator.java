package com.hhjang.spring.study.gof.structure.decorator.model.condiment;

import com.hhjang.spring.study.gof.structure.decorator.model.beverage.Beverage;

public class WhippingCreamDecorator extends CondimentDecorator {

    Beverage beverage;

    public WhippingCreamDecorator(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.5;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + " + " + "휘핑크림";
    }
}
