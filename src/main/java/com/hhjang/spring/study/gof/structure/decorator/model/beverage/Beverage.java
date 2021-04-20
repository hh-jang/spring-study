package com.hhjang.spring.study.gof.structure.decorator.model.beverage;

// 기본 음료수
public abstract class Beverage {

    String description = "기본 음료수";

    public String getDescription() {
        return description;
    }

    public abstract double cost();
}
