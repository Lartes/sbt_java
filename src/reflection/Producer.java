package reflection;

public class Producer {
    private int a;
    private int b;
    private int c;

    public Producer(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public int getC() {
        return c;
    }

    public void setA(int a) {
        this.a = a;
    }

    public void setB(int b) {
        this.b = b;
    }

    public void setC(int c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return "Producer{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                '}';
    }
}
