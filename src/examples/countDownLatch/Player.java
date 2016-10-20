package examples.countDownLatch;

import java.util.concurrent.CountDownLatch;

public class Player implements Runnable {
    private int playerNumber;
    private int playerIntel;//считаем, что IQ игрока примерно равный и постоянный
    private CountDownLatch START = IntelBattle.START;
    private int gameLength = IntelBattle.gameLength;

    public Player(int playerNumber, int playerIntel) {
        this.playerNumber = playerNumber;
        this.playerIntel = playerIntel;
    }

    @Override
    public void run() {
        try {
            System.out.printf("Участник №%d готов к игре.\n", playerNumber);
            //Участник готов к игре - условие выполнено
            //уменьшаем счетчик на 1
            START.countDown();
            //метод await() блокирует поток, вызвавший его, до тех пор, пока
            //счетчик countDownLatch не станет равен 0
            START.await();
            Thread.sleep(gameLength / playerIntel);//ждем пока ошибется
            System.out.printf("Участник №%d выбывает!\n", playerNumber);
        } catch (InterruptedException e) {
        }
    }
}