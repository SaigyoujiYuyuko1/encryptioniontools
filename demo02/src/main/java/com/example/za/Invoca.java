package com.example.za;

abstract class Invoca implements Invocation {
    public String name="bob";
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAge() {
        return "10";
    }

    @Override
    public String getSex() {
        return "男";
    }
}
