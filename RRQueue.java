import java.util.*;

public class RRQueue extends SchedQueue {
/*
	Simulates a Round Robin ready queue of processes waiting to be executed by the CPU scheduler.
*/
	
	//int quantum;
	int currIndex = 0;
	int peekCount = 0;

	RRQueue(int quantum) {
	//Requires a burst quantum value be provided.
		super.quantum = quantum;
		super.type = "Round Robin (" + quantum + ")";
	}

	public PCB peek() {
	//Returns the nest process to be executed without removing it from the RRQueue.
		while (this.get(currIndex % this.size()).getBurstTime() == 0) {
			currIndex = ((currIndex + 1) % this.size());
			peekCount = 0;
		}
		PCB currProc = this.get(currIndex);
		peekCount++;
		if ((peekCount % quantum) == 0) {
			currIndex = ((currIndex + 1) % this.size());
		}
		
		//currProc.quantaRecd++;
		return currProc;
	}
}