package com.thur.btc.Object;

public class Booster {
    private int work_time;
    private int multiply;
    private String name;

    public Booster(int work_time, int multiply, String name){
        setMultiply(multiply);
        setWork_time(work_time);
        setName(name);
    }

    public int getWork_time() {
        return work_time;
    }

    public void setWork_time(int work_time) {
        this.work_time = work_time;
    }

    public int getMultiply() {
        return multiply;
    }

    public void setMultiply(int multiply) {
        this.multiply = multiply;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
