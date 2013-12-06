import java.util.*;

public class SJFQueue extends SchedQueue {
/*
	Simulates a Shortest Job First ready queue of processes waiting to be executed by the CPU scheduler.
*/

	public PCB peek() {
	//Returns the nest process to be executed without removing it from the SJFQueue.
		int j=0;
		PCB minBurstProc = this.get(j);
		while (j < (this.size() - 1) && minBurstProc.getBurstTime() == 0) {
			j++;
			minBurstProc = this.get(j);
		}
		for (int i=1; i<this.size(); i++) {
			if (this.get(i).getBurstTime() > 0 && this.get(i).getBurstTime() < minBurstProc.getBurstTime()) {
				minBurstProc = this.get(i);
			}
		}
		return minBurstProc;
	}
}
