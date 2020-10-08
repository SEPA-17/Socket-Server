package socketserver;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The Data Queue is a wrapper class for a concurrency-safe underlying Queue.
 * This queue is used as the buffer between the SmartMeter worker threads, and the SQLDatabase worker threads.
 * @author Steerpike
 *
 */
public class DataQueue {
	private BlockingQueue<ArrayList<SmartMeterDataMap>> fQueue;
	
	/**
	 * Creates a new SmartMeterDataQueue
	 * @param aSize. The maximum size of the Queue. If you want an unbounded queue, use 0 as the input.
	 */
	public DataQueue(Integer aSize) {
		//create the queue with correct size
	
		if(aSize <= 0) {
		fQueue = new LinkedBlockingQueue <ArrayList<SmartMeterDataMap>>();
		
	} else {
		fQueue = new LinkedBlockingQueue <ArrayList<SmartMeterDataMap>>(aSize);
	}
	
	}
	
	/**
	 * Put an element into the tail of underlying Blocking Queue.
	 * TODO how to handle exceptions correctnly
	 * @param aList The list to add to the queue.
	 * @return true If the operation was successful
	 * @throws InterruptedException 
	 */
	public void put(ArrayList<SmartMeterDataMap> aList) throws InterruptedException {
		fQueue.put(aList);
	}
	
	/**
	 * Retrieves, but does not remove, the head of this queue, or returns null if this queue is empty.
	 * @return The element at the head of the queue, or empty if empty.
	 */
	public ArrayList<SmartMeterDataMap> peek(){
		return fQueue.peek();
	}
	
	/**
	 * Retrieves and removes the head of this queue, or returns null if this queue is empty.
	 * @return The element at the head of the queue, or empty if empty.
	 */
	public ArrayList<SmartMeterDataMap> poll(){
		return fQueue.poll();
	}
	
	/**
	 * @return Retrieves and removes the head of this queue, waiting if necessary until an element becomes available.
	 * @throws InterruptedException 
	 */
	public ArrayList<SmartMeterDataMap>  take() throws InterruptedException{
		return fQueue.take();
	}
	
	
}
