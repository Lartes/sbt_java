package multithreading.taskTask;

public class Main {
    static int count;
    static Task<Integer> task = new Task<> (() -> {
        return ++count;
    });
    public static void main(String[] args) {
        for(int i = 0; i < 3; i++) {
            Thread threadTmp = new Thread(() -> doRun(), "myThread_" + i);
            threadTmp.start();
        } }
    private static void doRun() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + " " + task.get());
        }
    }
}
