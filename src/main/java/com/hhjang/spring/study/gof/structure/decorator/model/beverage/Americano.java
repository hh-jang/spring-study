package com.hhjang.spring.study.gof.structure.decorator.model.beverage;

public class Americano extends Beverage {

    public Americano() {
        description = "아메리카노";
    }

    @Override
    public double cost() {
        return 3.0;
    }
}
