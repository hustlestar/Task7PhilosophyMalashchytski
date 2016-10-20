package examples.semaphore;

import java.util.concurrent.Semaphore;

public class Restraunt {
    //Ресторан. В нем 15 столиков. Приходят гости и если есть свободный столик - садятся
    //Если нет, то ждут, пока освободится какой-либо столик и занимают его в порядке честной очереди

    //Столики в ресторане: занято - true, свободно - false
    public static final boolean[] TABLES = new boolean[15];
    //Устанавливаем флаг "справедливый", в таком случае метод
    //aсquire() будет раздавать разрешения в порядке очереди
    public static final Semaphore SEMAPHORE = new Semaphore(15, true);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i <= 150; i++) {
            new Guest(i, (long)Math.random()*10).start();
            Thread.sleep((long) (Math.random() * 1));
        }
    }
}