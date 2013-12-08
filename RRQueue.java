import java.util.*;

public class RRQueue extends SchedQueue {
/*
	Simulates a Round Robin ready queue of processes waiting to be executed by the CPU scheduler.
*/
	
	PCB currProc;
	int peekCount = 0;

	RRQueue(int quantum) {
	//Requires a burst quantum value be provided.
		super.quantum = quantum;
		super.type = "Round Robin (" + quantum + ")";
	}

	public PCB peek() {
	//Returns the nest process to be executed without removing it from the RRQueue.

		currIndex = currIndex % size();							//Added to handle demotion in MLFQueue reducing queue size.

		while (this.get(currIndex).getBurstTime() == 0) {
			currIndex = ((currIndex + 1) % size());
			peekCount = 0;
		}
		PCB currProc = this.get(currIndex);
		peekCount++;
		if ((peekCount % quantum) == 0) {
			currIndex = ((currIndex + 1) % size());
		}
		
		return currProc;
	}
}