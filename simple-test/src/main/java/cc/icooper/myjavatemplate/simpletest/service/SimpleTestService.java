package cc.icooper.myjavatemplate.simpletest.service;

import cc.icooper.majavatemplate.core.component.RedisUtil;
import cc.icooper.majavatemplate.core.data.result.Result;
import cc.icooper.majavatemplate.core.util.lock.Lock;
import cc.icooper.majavatemplate.core.util.lock.impl.ReentrantLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class SimpleTestService {
    private final Lock lock = new ReentrantLock();
    @Autowired
    private RedisUtil redisUtil;

    public String helloWorld() {
        return "Hello, world!";
    }

    public void cacheData(String username, String message) {
        // 使用用户名作为键，信息作为值，设置1分钟的过期时间
        System.out.println("username: " + username + ", message: " + message);
        redisUtil.set(username, message, 1, TimeUnit.MINUTES);
    }

    public Result testLock() {
        // 一种 fast-fail 的锁测试
        boolean status = lock.tryLock();
        if (status) {
            try {
                Thread.sleep(5000);
                return Result.success("Lock acquired and processed successfully!");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupt status
                return Result.fail("Operation was interrupted!");
            } finally {
                lock.unlock();
            }
        } else {
            return Result.fail("Failed to get lock!");
        }
    }

}
