import java.util.*;

public class FCFSQueue {
	
//Simulates a First-Come, First-Served ready queue of processes waiting to be executed by the CPU scheduler.
	
	private static final int CAPACITY = 10;
	ArrayList<PCB> queue;
	
	FCFSQueue() {
	//If no paramaters are given, initialize the queue with a size of CAPACITY.
		queue = new ArrayList<PCB>(CAPACITY);
	}
	
	FCFSQueue(int capacity) {
	//If a capacity is given, intialize the queue with the given length.
		queue = new ArrayList<PCB>(capacity);
	}
	
	public boolean push(PCB newProc) {
	//Returns true if PCB object is successfully added to the queue. Returns false if not (queue is full).
		return queue.add(newProc);
	}
	
	public void pop() {
	//Removes the top element of the FCFSQueue.
		queue.remove(peek());
	}

	public PCB peek() {
	//Returns the nest process to be executed without removing it from the FCFSQueue.
		int i=0;
		PCB nextProc = queue.get(i);
		while (nextProc.getBurstTime() == 0) {
			i++;
			nextProc = queue.get(i);
		}
		return nextProc;
	}

	public PCB peek(int index) {
		return queue.get(index);
	}

	public int size() {
		return queue.size();
	}

	public boolean isEmpty() {
		return queue.isEmpty();
	}
	
	public String toString() {
		String output = "\tProcess ID\tBurst Time\tPriority\tWait Time\tT. Time\n";
		for (int i=0; i<queue.size(); i++) {
			output += (queue.get(i).toString() + "\n");
		}
		return output;
	}
}