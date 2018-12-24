package multithreading.taskTask;

import java.util.concurrent.Callable;

public class Task<T> {
    private final Callable<? extends T> callable;
    private volatile T result = null;
    private volatile TaskException exception = null;

    public Task(Callable<? extends T> callable) {
        this.callable = callable;
    }
    public T get() {
        T checkBeforeSynch = checkPreviousResult();
        if (checkBeforeSynch != null) {
            return checkBeforeSynch;
        }
        synchronized (this) {
            T checkAfterSynch = checkPreviousResult();
            if (checkAfterSynch != null) {
                return checkAfterSynch;
            }
            try {
                result = callable.call();
                return result;
            } catch (Exception e) {
                exception = new TaskException("Exception in callabale");
                throw exception;
            }
        }
    }

    private T checkPreviousResult() {
        if (result != null) {
            return result;
        }
        else if (exception != null) {
            throw exception;
        }
        return null;
    }
}
class TaskException extends RuntimeException{
    TaskException(String message) {
        super(message);
    }

}