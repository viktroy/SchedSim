import java.util.*;

public class MLFQueue extends ArrayList<SchedQueue> {
/*
	Simulates a Multi-level Feedback Queue of processes waiting to be executed.
*/

	private static final int MAX_QUEUES = 5;
	private ArrayList<Double> cpuPortions = new ArrayList<Double>(MAX_QUEUES);
	SchedQueue currQueue;
	int peekCount = 0;
	//int currInsertIndex = 0;
	double totalWaitingTime = 0;
	double totalTurnaroundTime = 0;

	public void add(PCB proc) {
		this.get(0).add(proc);
		//currInsertIndex = (currInsertIndex + 1) % this.size();
	}

	public void addQueue(SchedQueue queue, double cpuPortion) {
		this.add(queue);
		cpuPortions.add(cpuPortion);
	}

	public void updateCurrQueue() {
		double randNum = Math.random();
		double accum = 0;
		for (int i=0; i<this.size(); i++) {
			accum += cpuPortions.get(i);
			if (randNum <= accum) {
				currQueue = this.get(i);
				peekCount = 0;
				System.out.println("Queue " + this.indexOf(currQueue) + " selected");
				break;
			}
		}
	}

	public PCB peek() {
		
		updateCurrQueue();
		while (currQueue.isDone()) {
			updateCurrQueue();
		}
		PCB currProc = currQueue.peek();
		if (currProc.burstsRecd == currQueue.quantum - 1) {
			demote(currProc);
		}
		return currProc;
	}
		/*
		//Check to see if the maximum quantum of the queues has already been given (
		if ((peekCount == this.getMaxQuantum())) {
			System.out.println(this.getMaxQuantum() + " bursts given to queue " + this.indexOf(currQueue));
			this.updateCurrQueue();
			//peakCount = 0;
		}
		peekCount++;
		
		while (currQueue.isEmpty()) {
			System.out.println("Queue " + this.indexOf(currQueue) + " is empty");
			this.updateCurrQueue();
			//peekCount = 0;
		}
		//System.out.println("12 CPU Bursts allocated to queue " + this.indexOf(currQueue));
		System.out.println("CPU bursts allocated to queue " + this.indexOf(currQueue));
		PCB currProc = currQueue.peek();
		while (currProc.burstsRecd == currQueue.quantum) {
			this.demote(currProc);
			if (currQueue.isEmpty()) {
				this.updateCurrQueue();
			}
			currProc = currQueue.peek();
		}
		currProc.burstsRecd++;
		return currProc;
		*/
	//}

	public void demote(PCB proc) {
		boolean removed = false;
		boolean added = false;
		for (int i=0; i<this.size(); i++) {
			removed = this.get(i).remove(proc);
			if (removed) {
				proc.burstsRecd = 0;
				int j = i+1;
				added = this.get(j).add(proc);
				while (! added && j<(this.size()-1)) {
					j++;
					added = this.get(j).add(proc);
				}
				break;
			}
		}
	}

	public boolean isDone() {
		for (int i=0; i<this.size(); i++) {
			if (! this.get(i).isDone()) {
				return false;
			}
		}
		return true;
	}

/*
	public int getMaxQuantum() {
		int maxQuantum = 1;
		for (int i=0; i<this.size(); i++) {
			if (this.get(i) instanceof RRQueue) {
				if (this.get(i).quantum > maxQuantum) {
					maxQuantum = this.get(i).quantum;
				}
			}
		}
		return maxQuantum;
	}
*/

	public void execute() {
	
		while (! this.isDone()) {
			PCB proc = peek();
			try {
				proc.giveBurst();
				for (int i=0; i<size(); i++) {
					for (int j=0; j<get(i).size(); j++) {
						PCB nextPCB = get(i).get(j);
						if (nextPCB.getBurstTime() != 0 && !nextPCB.equals(proc)) {
							nextPCB.giveWait();
						}
					}
				}
			} catch (InterruptedException e) {}
			if (proc.getBurstTime() == 0) {
				totalWaitingTime += proc.waitTime;
				totalTurnaroundTime += proc.turnTime;
				System.out.println("Process " + proc.getID() + " terminated.");
				System.out.println(this);
			} else {
				System.out.println("Process " + proc.getID() + " receives a CPU burst.");
			}
		}
		double avgWaitingTime = totalWaitingTime / (double)this.size();
		double avgTurnaroundTime = totalTurnaroundTime / (double)this.size();
		System.out.println("Avg Waiting Time: " + avgWaitingTime);
		System.out.println("Avg Turnaround Time: " + avgTurnaroundTime);
	}

	public String toString() {
		String output = "";
		for (int i=0; i<this.size(); i++) {
			output += this.get(i).type + ":\n" + this.get(i);
		}
		return output;
	}
}