package cc.icooper.majavatemplate.core.util.lock;

public interface Lock {
    void lock();

    void unlock();

    boolean tryLock();
}