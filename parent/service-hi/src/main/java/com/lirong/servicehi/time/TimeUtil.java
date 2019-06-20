package com.lirong.servicehi.time;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Title: TimeUtil <br>
 * Description: TimeUtil <br>
 * Date: 2019年05月30日
 * <p>
 * 稳定定时任务
 *
 * @author lirong
 * @version 1.0.0
 * @since jdk8
 */
public class TimeUtil {

    public static void task(String name, List<Task> tasks) {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(tasks.size(),
                new BasicThreadFactory.Builder().namingPattern(name + "-pool-%d").daemon(false).build());

        tasks.forEach(task -> executor.scheduleAtFixedRate(task.getRunnable(), task.getInitialDelay(), task.getPeriod(),
                task.getTimeUnit()));
    }

    public static TaskBuilder builder() {
        return new TaskBuilder();
    }

    public static class TaskBuilder {
        /**
         * 刚开始执行任务是延时时间
         */
        private long initialDelay = 0;

        private List<Task> tasks = new ArrayList<>();

        public TaskBuilder() {
        }

        public TaskBuilder add(long initialDelay, long period, TimeUnit timeUnit, Runnable runnable) {
            tasks.add(new Task(initialDelay, period, timeUnit, runnable));
            return this;
        }

        public TaskBuilder add(long period, Runnable runnable) {
            tasks.add(new Task(initialDelay, period, runnable));
            return this;
        }

        public void build(String poolName) {
            TimeUtil.task(poolName, tasks);
        }

    }

    public static class Task {

        private long initialDelay;

        private long period;

        private TimeUnit timeUnit = TimeUnit.SECONDS;

        private Runnable runnable;

        public Task(long initialDelay, long period, TimeUnit timeUnit, Runnable runnable) {
            this.initialDelay = initialDelay;
            this.period = period;
            this.timeUnit = timeUnit;
            this.runnable = runnable;
        }

        public Task(long initialDelay, long period, Runnable runnable) {
            this.initialDelay = initialDelay;
            this.period = period;
            this.runnable = runnable;
        }

        public long getInitialDelay() {
            return initialDelay;
        }

        public long getPeriod() {
            return period;
        }

        public TimeUnit getTimeUnit() {
            return timeUnit;
        }

        public Runnable getRunnable() {
            return runnable;
        }
    }

}
