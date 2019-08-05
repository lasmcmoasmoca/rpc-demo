package com.leec.rpcdemo.sequence;

import java.util.concurrent.atomic.AtomicLong;

public class Sequence {
    public static volatile AtomicLong atomicLong = new AtomicLong(1L);
}
