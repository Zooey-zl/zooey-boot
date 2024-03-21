package com.cn.zooey.util;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Fengzl
 * @date 2024/3/6 09:57
 * @desc
 **/
@Slf4j
public class TestAuto {
    private static AtomicInteger total = new AtomicInteger();
    private static boolean inShutdown = false;

    private static final RateLimiter inputLimiter = RateLimiter.create(50);
    private static final int maxSize = 1000;

    public static void main(String[] args) {

        loadData("test");

    }

    public static void loadData(String key) {
        int startIndex = new Random().nextInt(100);
        do {
            try {
                log.info("startIndex == {}, total == {}", startIndex, total);

                int foundSize = 0;
                for (int i = 0; i < 100; i++) {
                    inputLimiter.acquire();
                    int index = (startIndex + i) % 100;
                    boolean haveData = false;

                    // haveData = statCountInput(index) || haveData;
                    // haveData = callBackStatInput(index) || haveData;
                    total.incrementAndGet();
                    log.info("i == {}, index == {}, total == {}", i, index, total.get());
                    if (new Random().nextInt(10) > 5) {
                        foundSize++;
                    }
                }
                if (foundSize <= startIndex / 2) {
                    log.info("no more data for {} {} {}", key, foundSize, startIndex);
                    return;
                }
                if (total.get() > maxSize) {
                    log.info("enough data for {} {}", key, total.get());
                    return;
                }
                if (inShutdown) {
                    log.info("in shutdown for {}", key);
                    break;
                }
            } catch (Exception e) {
                log.error("{}", e.getMessage(), e);
                break;
            }
        } while (true);
        log.info("load {} data finish.", key);
    }
}
