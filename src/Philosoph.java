import java.util.concurrent.Semaphore;

public class Philosoph extends Thread {

    Fork left;
    Fork right;
    static Semaphore semaphore = Table.semaphore;
    public int count;
    boolean hungry = true;
    int id;

    public Philosoph(Fork left, Fork right, int i) {
        this.left = left;
        this.right = right;
        this.id = i;
    }

    @Override
    public void run() {

        while (hungry) {
            semaphore.tryAcquire();
            left.lock();
            try {
                if (right.tryLock()) {
                    try {
                        eat();
                    } finally {
                        right.unlock();
                    }
                }
            } finally {
                left.unlock();
            }
            semaphore.release();
            think();
        }
    }

    private void think() {
        try {
            Thread.sleep(5);
            hungry = true;
            System.out.println(id + " Подумал и снова хочет есть");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void eat() {
        try {
            Thread.sleep(1);
            System.out.println(id + " Поел " + ++count);
            hungry = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

