package reflection;

public class Consumer {
    private int a;
    private int c;
    private int d;

    public Consumer(int a, int c, int d) {
        this.a = a;
        this.c = c;
        this.d = d;
    }

    public int getA() {
        return a;
    }

    public int getC() {
        return c;
    }

    public int getD() {
        return d;
    }

    public void setA(int a) {
        this.a = a;
    }

    public void setC(int c) {
        this.c = c;
    }

    public void setD(int d) {
        this.d = d;
    }

    @Override
    public String toString() {
        return "Consumer{" +
                "a=" + a +
                ", c=" + c +
                ", d=" + d +
                '}';
    }
}
