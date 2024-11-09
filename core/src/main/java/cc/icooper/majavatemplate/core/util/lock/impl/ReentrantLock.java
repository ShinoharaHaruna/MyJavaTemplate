package cc.icooper.majavatemplate.core.util.lock.impl;

import cc.icooper.majavatemplate.core.util.lock.Lock;

public class ReentrantLock implements Lock {
    private final java.util.concurrent.locks.ReentrantLock lock;

    public ReentrantLock() {
        this.lock = new java.util.concurrent.locks.ReentrantLock();
    }

    @Override
    public void lock() {
        lock.lock();
    }

    @Override
    public void unlock() {
        lock.unlock();
    }

    @Override
    public boolean tryLock() {
        return lock.tryLock();
    }

    public boolean isHeldByCurrentThread() {
        return lock.isHeldByCurrentThread();
    }
}