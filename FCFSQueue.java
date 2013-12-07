import java.util.*;

public class FCFSQueue extends SchedQueue {
/*
	Simulates a First-Come, First-Served ready queue of processes waiting to be executed by the CPU scheduler.
*/
	FCFSQueue() {
		super.type = "FCFS";
	}
	
	public PCB peek() {
	//Returns the nest process to be executed without removing it from the FCFSQueue.
		int i=0;
		PCB nextProc = this.get(i);
		while (nextProc.getBurstTime() == 0) {
			i++;
			nextProc = this.get(i);
		}
		return nextProc;
	}
}