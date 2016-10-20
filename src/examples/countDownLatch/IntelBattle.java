package examples.countDownLatch;

import java.util.concurrent.CountDownLatch;

public class IntelBattle {
    //Игра на андроид Интеллект-баттл. Многопользовательский "Кто хочет стать миллионером".
    //Игра начинается когда собирается 5 игроков

    //Создаем countDownLatch на 8 "условий"
    public static final CountDownLatch START = new CountDownLatch(8);
    //Условная длина гоночной трассы
    public static final int gameLength = 500000;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i <= 5; i++) {
            new Thread(new Player(i, (int) (Math.random() * 100 + 100))).start();
            Thread.sleep(100);
        }

        while (START.getCount() > 3) //Проверяем, собрались ли все 5 игроков
            Thread.sleep(100);       //Если нет, ждем 100ms

        Thread.sleep(500);
        System.out.println("Игра готовится!");
        START.countDown();//Команда дана, уменьшаем счетчик на 1
        Thread.sleep(500);
        System.out.println("Сейчас начнется игра!");
        START.countDown();//Команда дана, уменьшаем счетчик на 1
        Thread.sleep(500);
        System.out.println("Поехали!");
        START.countDown();//Команда дана, уменьшаем счетчик на 1
        //счетчик становится равным нулю, и все ожидающие потоки
        //одновременно разблокируются
    }


}