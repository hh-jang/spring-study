package com.hhjang.spring.study.gof.structure.decorator.model.condiment;

import com.hhjang.spring.study.gof.structure.decorator.model.beverage.Beverage;

// 음료에 넣는 기본 첨가물
public abstract class CondimentDecorator extends Beverage {

    public abstract String getDescription();
}
