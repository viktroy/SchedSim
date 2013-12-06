import java.util.*;

public class PriorityQueue {
	
//Simulates a Priority ready queue of processes waiting to be executed by the CPU scheduler.
	
	private static final int CAPACITY = 10;
	ArrayList<PCB> queue;
	
	PriorityQueue() {
	//If no paramaters are given, initialize the queue with a size of CAPACITY.
		queue = new ArrayList<PCB>(CAPACITY);
	}
	
	PriorityQueue(int capacity) {
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
	//Removes the top element of the PriorityQueue.
		queue.remove(peek());
	}

	public PCB peek() {
	//Returns the nest process to be executed without removing it from the PriorityQueue.
		int j=0;
		PCB maxPriorityProc = queue.get(j);
		while (maxPriorityProc.getBurstTime() == 0) {
			j++;
			maxPriorityProc = queue.get(j);
		}
		for (int i=1; i<queue.size(); i++) {
			if (queue.get(i).getBurstTime() > 0 && queue.get(i).getPriority() > maxPriorityProc.getPriority()) {
				maxPriorityProc = queue.get(i);
			}
		}
		return maxPriorityProc;
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

	/*
	public int[] getActiveProcs() {
		
		int[queue.size()] active;
		for (int i=0; i<queue.size(); i++) {
			if (queue.get(i).getBurstTime == 0) {
				active[i] = 0;
			} else {
				active[i] = 1;
			}
		}
	}
	*/
	
	public String toString() {
		String output = "\tProcess ID\tBurst Time\tPriority\tWait Time\tT. Time\n";
		for (int i=0; i<queue.size(); i++) {
			output += (queue.get(i).toString() + "\n");
		}
		return output;
	}
}