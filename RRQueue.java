import java.util.*;

public class RRQueue extends SchedQueue {
/*
	Simulates a Round Robin ready queue of processes waiting to be executed by the CPU scheduler.
*/
	
	int quantum;
	int currIndex = -1;

	RRQueue(int quantum) {
	//Requires a burst quantum value be provided.
		quantum = quantum;
	}

	public PCB peek() {
	//Returns the nest process to be executed without removing it from the RRQueue.
		currIndex = ((currIndex + 1) % this.size());
		while (this.get(currIndex).getBurstTime() == 0) {
			currIndex = ((currIndex + 1) % this.size());
		}
		return this.get(currIndex);
	}
}