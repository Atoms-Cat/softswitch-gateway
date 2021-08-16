package link.thingscloud.spring.boot.common.util;

import cn.hutool.core.thread.NamedThreadFactory;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorHelper {
    private static final ScheduledExecutorService EXECUTOR_SERVICE = new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors() * 2, new NamedThreadFactory("pool-helper-exec-", true));

    private ExecutorHelper() {
    }

    public static void execute(Runnable runnable) {
        EXECUTOR_SERVICE.execute(runnable);
    }

    public static <T> Future<T> submit(Runnable runnable, T result) {
        return EXECUTOR_SERVICE.submit(runnable, result);
    }

    public static <T> Future<T> submit(Callable<T> callable) {
        return EXECUTOR_SERVICE.submit(callable);
    }

    public static ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        return EXECUTOR_SERVICE.schedule(command, delay, unit);
    }

    public static ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        return EXECUTOR_SERVICE.scheduleWithFixedDelay(command, initialDelay, delay, unit);
    }

    public static ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        return EXECUTOR_SERVICE.scheduleAtFixedRate(command, initialDelay, period, unit);
    }
}
