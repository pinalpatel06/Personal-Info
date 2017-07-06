package com.knoxpo.personinfo.model;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Tejas Sherdiwala on 4/11/2017.
 * &copy; Knoxpo
 */

public class CountCheck {
    private final AtomicInteger count = new AtomicInteger();

    public void init(int no) {
        count.set(no);
    }

    public void addOne(){
        count.incrementAndGet();
    }

    public int getCount(){
        return count.get();
    }
}
