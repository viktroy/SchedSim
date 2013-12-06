import java.util.*;

public class SJFQueue {
	
//Simulates a Shortest Job First ready queue of processes waiting to be executed by the CPU scheduler.
	
	private static final int CAPACITY = 10;
	ArrayList<PCB> queue;
	
	SJFQueue() {
	//If no paramaters are given, initialize the queue with a size of CAPACITY.
		queue = new ArrayList<PCB>(CAPACITY);
	}
	
	SJFQueue(int capacity) {
	//If a capacity is given, intialize the queue with the given length.
		queue = new ArrayList<PCB>(capacity);
	}
	
	public boolean push(PCB newProc) {
	//Returns true if PCB object is successfully added to the queue. Returns false if not (queue is full).
		boolean pushed = queue.add(newProc);
		//Collections.sort(Database.ArrayList, new BurstComparator());
		return pushed;
	}
	
	public void pop() {
	//Removes the top element of the SJFQueue.
		queue.remove(peek());
	}

	public PCB peek() {
	//Returns the nest process to be executed without removing it from the SJFQueue.
		int j=0;
		PCB minBurstProc = queue.get(j);
		while (minBurstProc.getBurstTime() == 0) {
			j++;
			minBurstProc = queue.get(j);
		}
		for (int i=1; i<queue.size(); i++) {
			if (queue.get(i).getBurstTime() > 0 && queue.get(i).getBurstTime() < minBurstProc.getBurstTime()) {
				minBurstProc = queue.get(i);
			}
		}
		return minBurstProc;
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