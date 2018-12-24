package reflection;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Producer producer = new Producer(1, 2, 3);
        Consumer consumer = new Consumer(4,5,6);
        System.out.println(producer.toString());
        System.out.println(consumer.toString());
        BeanUtils.assign(consumer, producer);
        System.out.println(consumer.toString());
    }
}
