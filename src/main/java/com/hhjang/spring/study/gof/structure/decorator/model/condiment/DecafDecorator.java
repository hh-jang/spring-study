package com.hhjang.spring.study.gof.structure.decorator.model.condiment;

import com.hhjang.spring.study.gof.structure.decorator.model.beverage.Beverage;

public class DecafDecorator extends CondimentDecorator {

    Beverage beverage;

    public DecafDecorator(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.3;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + " + " + "디카페인";
    }
}
