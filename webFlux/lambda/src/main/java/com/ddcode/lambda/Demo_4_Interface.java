package com.ddcode.lambda;

@FunctionalInterface
interface Demo_4_Interface {
    public int doubleNum(int i);

    default int add(int x, int y){
        return x + y;
    }
}


@FunctionalInterface
interface Demo_4_Interface_1 {
    public int doubleNum(int i);

    default int add(int x, int y){
        return x + y;
    }
}


@FunctionalInterface
interface Demo_4_Interface_2 extends Demo_4_Interface, Demo_4_Interface_1{

    @Override
    default int add(int x, int y) {
        return Demo_4_Interface.super.add(x,y);
    }
}
