package multithreading.taskManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;

public class ThreadPool implements Executor {
    private final Queue<Runnable> workQueue = new ConcurrentLinkedQueue<>();
    private final List<Runnable> waitingTasks;
    private final Runnable callback;
    private final Object lock = new Object();
    private final int numberOfTasks;
    private int numberOfFailedTasks = 0;
    private int numberOfCompletedTasks = 0;
    private int numberOfInterruptedTasks = 0;
    private volatile boolean isRunning = true;

    public ThreadPool(int nThreads, Runnable callback, Runnable... tasks) {
        numberOfTasks = tasks.length;
        waitingTasks = new ArrayList<>(Arrays.asList(tasks));
        this.callback = callback;
        for (int i = 0; i < nThreads; i++) {
            new Thread(new TaskWorker()).start();
        }
    }
    public int getFailedTask() {
        synchronized (lock) {
            return numberOfFailedTasks;
        }
    }
    public int getCompletedTask() {
        synchronized (lock) {
            return numberOfCompletedTasks;
        }
    }
    public int getInterruptedTask() {
        synchronized (lock) {
            return numberOfInterruptedTasks;
        }
    }
    public void interrupt() {
        synchronized (lock) {
            numberOfInterruptedTasks = waitingTasks.size();
            waitingTasks.clear();
            runCallback();
        }
    }
    public boolean isFinished() {
        synchronized (lock) {
            return numberOfFailedTasks + numberOfCompletedTasks + numberOfInterruptedTasks == numberOfTasks;
        }
    }
    protected void beforeExecute(Runnable task) {
        if (task != callback) {
            synchronized (lock) {
                waitingTasks.remove(task);
            }
        }
    }
    protected void afterExecute(Runnable task, Throwable exception) {
        if (task != callback) {
            synchronized (lock) {
                if (exception != null) {
                    numberOfFailedTasks++;
                }
                else {
                    numberOfCompletedTasks++;
                }
                runCallback();
            }
        }
        if(task == callback) {
            ThreadPool.this.shutdown();
        }
    }
    private void runCallback() {
        if (numberOfFailedTasks + numberOfCompletedTasks + numberOfInterruptedTasks == numberOfTasks) {
            execute(callback);
        }
    }
    @Override
    public void execute(Runnable command) {
        if (isRunning) {
            workQueue.offer(command);
        }
    }
    public void shutdown() {
        isRunning = false;
    }

    private final class TaskWorker implements Runnable {
        @Override
        public void run() {
            while (isRunning) {
                Runnable nextTask = workQueue.poll();
                if (nextTask != null) {
                    beforeExecute(nextTask);
                    Throwable exception = null;
                    try {
                        nextTask.run();
                    }
                    catch (Throwable e) {
                        exception = e;
                    }
                    finally {
                        afterExecute(nextTask, exception);
                    }
                }
            }
        }
    }
}

