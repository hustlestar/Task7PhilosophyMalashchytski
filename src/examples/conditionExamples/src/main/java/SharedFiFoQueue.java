package examples.conditionExamples;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedFiFoQueue {

	private Object[] elems = null;
	private int current = 0;
	private int placeIndex = 0;
	private int removeIndex = 0;
	
	private final Lock lock = new ReentrantLock();
	private final Condition isEmpty = lock.newCondition();
	private final Condition isFull = lock.newCondition();
	
	public SharedFiFoQueue(int capacity) {
		this.elems = new Object[capacity];
	}
	
	public void add(Object elem) throws InterruptedException {
		lock.lock();
		while(current >= elems.length)
			isFull.await();
	
		elems[placeIndex] = elem;
		
		//чтобы не выйти за границы
		placeIndex = (placeIndex + 1) % elems.length;
		
		++current;
		
		//Сообщить потребителю, что данные доступны
		isEmpty.signal();
		
		lock.unlock();
	}

	public Object remove() throws InterruptedException {
		Object elem = null;
		
		lock.lock();
		while(current <= 0)
			isEmpty.await();
	
		elem = elems[removeIndex];

		//чтобы не выйти за границы
		removeIndex = (removeIndex + 1) % elems.length;
		
		--current;
		
		//сообщить продюсеру, что есть свободное место
		isFull.signal();
		
		lock.unlock();
		
		return elem;
	}
}
