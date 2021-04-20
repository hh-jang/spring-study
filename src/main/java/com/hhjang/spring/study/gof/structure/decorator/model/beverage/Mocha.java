package com.hhjang.spring.study.gof.structure.decorator.model.beverage;

public class Mocha extends Beverage {

    public Mocha() {
        description = "모카";
    }

    @Override
    public double cost() {
        return 3.5;
    }
}
