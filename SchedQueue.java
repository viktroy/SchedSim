import java.util.*;

public class SchedQueue extends ArrayList<PCB> {
/*
	A superclass representing a generic queue of PCB objects.
*/

	int quantum = -1;
	double totalWaitingTime = 0;
	double totalTurnaroundTime = 0;
	String type = "Queue";

	public PCB peek() {
		return this.get(0);
	}

	public boolean isDone() {
		for (int i=0; i<this.size(); i++) {
			if (this.get(i).getBurstTime() != 0) {
				return false;
			}
		}
		return true;
	}

	
	public int getQuantum() {
		if (this instanceof RRQueue){
			return this.quantum;
		}	else {
			return 0;
		}
	}

	public void execute() {
		while (! this.isDone()) {
			PCB proc = this.peek();
			try {
				proc.giveBurst();
				for (int i=0; i<this.size(); i++) {
					PCB nextPCB = this.get(i);
					if (nextPCB.getBurstTime() != 0 && !nextPCB.equals(proc)) {
						nextPCB.giveWait();
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
		String output = "\tProcess ID\tBurst Time\tPriority\tWait Time\tT. Time\n";
		for (int i=0; i<this.size(); i++) {
			output += (this.get(i).toString() + "\n");
		}
		return output;
	}
}