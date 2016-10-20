package examples.semaphore;

import java.util.concurrent.Semaphore;

/**
 * Created by Hustler on 15.10.2016.
 */
public class Guest extends Thread {
    private int tableNumber;
    private Semaphore sema = Restraunt.SEMAPHORE;
    private long time;

    public Guest(int guestNumber, long l) {
        this.tableNumber = guestNumber;
        this.time = l;
    }

    @Override
    public void run() {
        System.out.printf("Гость №%d пришел к нам в ресторан\n", tableNumber);
        try {
            //acquire() запрашивает доступ к следующему за вызовом этого метода блоку кода,
            //если доступ не разрешен, поток вызвавший этот метод блокируется до тех пор,
            //пока семафор не разрешит доступ
            sema.acquire();

            int tableNumber = -1;

            //Ищем свободное место и садимся за столик
            synchronized (Restraunt.TABLES) {
                for (int i = 0; i < 15; i++)
                    if (!Restraunt.TABLES[i]) {      //Если место свободно
                        Restraunt.TABLES[i] = true;  //занимаем его
                        tableNumber = i;         //Наличие свободного места, гарантирует семафор
                        System.out.printf("Гость №%d сел за столик %d.\n", this.tableNumber, i);
                        break;
                    }
            }

            Thread.sleep(time);       //Будем сидеть тут, сколько захотим

            synchronized (Restraunt.TABLES) {
                Restraunt.TABLES[tableNumber] = false;//Освобождаем место
            }

            //release(), напротив, освобождает семафор
            sema.release();
            System.out.printf("Гость №%d поел и  расплатился.\n", this.tableNumber);
        } catch (InterruptedException e) {
            System.out.println("Execption in" + tableNumber);
        }
    }
}
