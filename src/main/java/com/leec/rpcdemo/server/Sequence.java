package com.leec.rpcdemo.server;

import java.util.concurrent.atomic.AtomicLong;

public class Sequence {
    public static volatile AtomicLong atomicLong = new AtomicLong(1L);
}
