package examples.cyclicBarrier;

import java.util.concurrent.CyclicBarrier;

//Стороны, которые будут достигать барьера
public class Car implements Runnable {
    private int carNumber;
    private CyclicBarrier BARRIER= Ferry.BARRIER;

    public Car(int carNumber) {
        this.carNumber = carNumber;
    }

    @Override
    public void run() {
        try {
            System.out.printf("Автомобиль №%d подъехал к паромной переправе.\n", carNumber);
            //Для указания потоку о том что он достиг барьера, нужно вызвать метод await()
            //После этого данный поток блокируется, и ждет пока остальные стороны достигнут барьера
            BARRIER.await();
            System.out.printf("Автомобиль №%d продолжил движение.\n", carNumber);
        } catch (Exception e) {
        }
    }
}