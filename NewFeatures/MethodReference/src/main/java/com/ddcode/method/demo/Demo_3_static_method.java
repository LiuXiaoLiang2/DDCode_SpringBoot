package com.ddcode.method.demo;

import java.util.function.Supplier;

public class Demo_3_static_method {

    public static void main(String[] args) {
        Supplier<Long> supp = () -> {
            return System.currentTimeMillis();
        };
        System.out.println(supp.get());
        Supplier<Long> supp2 = System::currentTimeMillis;
        System.out.println(supp2.get());
    }
}
