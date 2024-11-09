package cc.icooper.myjavatemplate.simpletest.controller;

import cc.icooper.majavatemplate.core.data.result.Result;
import cc.icooper.myjavatemplate.simpletest.service.SimpleTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class SimpleTestController {
    @Autowired
    SimpleTestService simpleTestService;

    @GetMapping("/hello")
    public Result helloWorld() {
        return Result.success(simpleTestService.helloWorld());
    }

    @PostMapping("/echo/{msg}")
    public Result echo(@PathVariable String msg) {
        return Result.success(msg);
    }

    @PostMapping("/cache")
    public Result cacheData(@RequestBody Map<String, String> data) {
        simpleTestService.cacheData(data.get("username"), data.get("message"));
        return Result.success();
    }

    @PostMapping("/lock")
    public Result testLock() {
        return simpleTestService.testLock();
    }
}
