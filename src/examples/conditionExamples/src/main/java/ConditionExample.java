package examples.conditionExamples;

public class ConditionExample {
	public static void main(String[] args) throws InterruptedException {
		//условия используются, когда необходимо, чтобы оповещать поток, когда условие правдиво
        //продюсер читает файл и добавляет значения в общую очередь
        //а потребитель достает эти значения из очереди
		SharedFiFoQueue sharedQueue = new SharedFiFoQueue(10);
		
		//создаем производителя и потребителя
		Thread producer = new Producer(sharedQueue);
		Thread consumer = new Consumer(sharedQueue);
		
		producer.start();
		consumer.start();
		
		producer.join();
		consumer.join();
	}
}
