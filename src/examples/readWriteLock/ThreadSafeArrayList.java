package examples.readWriteLock;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class ThreadSafeArrayList<E> {
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private final Lock readLock = readWriteLock.readLock();

    private final Lock writeLock = readWriteLock.writeLock();

    private final List<E> list = new ArrayList<>();
    //Множество потоков могут получить лок на чтение, но только единственный поток может получить
    //эксклюзивный лок на запись. Пока поток, взязвший лок на запись, не завершит работу, читающие потоки
    //будут ждать, пока лок на запись будет освбожден.
    public static void main(String[] args) {
        ThreadSafeArrayList<String> threadSafeArrayList = new ThreadSafeArrayList<>();
        threadSafeArrayList.set("1");
        threadSafeArrayList.set("2");
        threadSafeArrayList.set("3");

        System.out.println("Printing the First Element : " + threadSafeArrayList.get(1));
    }

    public void set(E o) {
        writeLock.lock();
        try {
            list.add(o);
            System.out.println("Adding element by thread " + Thread.currentThread().getName());
        } finally {
            writeLock.unlock();
        }
    }

    public E get(int i) {
        readLock.lock();
        try {
            System.out.println("Printing elements by thread " + Thread.currentThread().getName());
            return list.get(i);
        } finally {
            readLock.unlock();
        }
    }


}
