import java.util.Random.*;

public class PCB {
/*
	Object that represents a Process Control Block for a process in the simulated CPU Scheduler. Stores process id, burst time, and priority. Maintains waiting
	and turnaround times for each process.
*/
	
	//Class variables
	int id;
	int burst;
	int priority;
	int waitTime = 0;
	int turnTime = 0;	
	
	//Constructors
	PCB(int procID) {
	//If only ID is given, set burst time and priority to random values. 
		id = procID;
		burst = (int)(Math.random() * (10-1)) + 1;
		priority = (int)(Math.random() * (5-1)) + 1;
	}
	
	PCB(int procID, int burstTime) {
	//If ID and bustTime are given, use the process burst time as its priority.
		id = procID;
		burst = burstTime;
		priority = burst;
	}
	
	PCB(int procID, int burstTime, int priorityLevel) {
	//If all three values are given, set variables accordingly.
		id = procID;
		burst = burstTime;
		priority = priorityLevel;
	}

	PCB(PCB proc) {
		id = proc.id;
		burst = proc.burst;
		priority = proc.priority;
	}
	
	public int getID() {
		return id;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public int getBurstTime() {
		return burst;
	}
	
	public void setBurstTime(int burstTime) {
		burst = burstTime;
	}
	
	public void setPriority(int priorityLevel) {
		priority = priorityLevel;
	}
	
	public void giveBurst() throws InterruptedException {
		Thread.sleep(300);
		burst--;
		turnTime++;
	}

	public void giveWait() {
		waitTime++;
		turnTime++;
	}


	public boolean equals(PCB proc) {
		if (proc.id == this.id) {
			return true;
		} else {
			return false;
		}
	}
	
	public String toString() {
		String status = "\t" + id + "\t\t" + burst + "\t\t" + priority + "\t\t" + waitTime + "\t\t" + turnTime;
		return status;
	}
}