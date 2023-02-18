package com.test.apply.aop.aspectj.util;

import java.util.function.Function;

class DefaultTransferClass implements Function<String, Void> {
    @Override
    public Void apply(String s) {
        throw new UnsupportedOperationException("Can not transfer default arg \"%s\" to target type!".formatted(s));
    }
}
