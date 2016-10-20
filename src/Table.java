import java.util.concurrent.Semaphore;

/**
 * Created by Hustler on 15.10.2016.
 */
public class Table {
    public static Fork[] forks;//вилки
    public static Philosoph[] philosophs;//философы
    // не пускаем больше двух философов за стол, таким образом дедлок невозможен
    //а также обеспечено равномерный доступ к ресурсам
    public static Semaphore semaphore = new Semaphore(2, true);

    public static void main(String[] args) {
        forks = new Fork[5];
        philosophs = new Philosoph[5];

        for (int i = 0; i < forks.length; i++) {
            forks[i] = new Fork();
        }
        for (int i = 0; i < philosophs.length; i++) {
            if (i < philosophs.length - 1) {
                philosophs[i] = new Philosoph(forks[i], forks[i + 1], i + 1);
            } else {
                philosophs[i] = new Philosoph(forks[i], forks[0], i + 1);
            }
        }
        for (Philosoph philosoph : philosophs) {
            philosoph.start();
        }
    }
}
