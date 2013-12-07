import java.util.*;

public class PriorityQueue extends SchedQueue {
/*
	Simulates a Priority ready queue of processes waiting to be executed by the CPU scheduler.
*/
	PriorityQueue() {
		super.type = "Priority";
	}
	
	public PCB peek() {
	//Returns the nest process to be executed without removing it from the PriorityQueue.
		int j=0;
		PCB maxPriorityProc = this.get(j);
		while (j < (this.size() - 1) && maxPriorityProc.getBurstTime() == 0) {
			j++;
			maxPriorityProc = this.get(j);
		}
		for (int i=1; i<this.size(); i++) {
			if (this.get(i).getBurstTime() > 0 && this.get(i).getPriority() > maxPriorityProc.getPriority()) {
				maxPriorityProc = this.get(i);
			}
		}
		return maxPriorityProc;
	}
}