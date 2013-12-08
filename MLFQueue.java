import java.util.*;
import java.io.*;

public class MLFQueue extends ArrayList<SchedQueue> {
/*
	Simulates a Multi-level Feedback Queue of processes waiting to be executed.
*/

	private static final int MAX_QUEUES = 5;
	private ArrayList<Double> cpuPortions = new ArrayList<Double>(MAX_QUEUES);
	SchedQueue currQueue;
	int peekCount = 0;
	double totalWaitingTime = 0;
	double totalTurnaroundTime = 0;

	public void add(PCB proc) {
		get(0).add(proc);
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
		//System.out.println("currIndex = " + currQueue.currIndex);
		
		return currProc;
	}

	public void demote(PCB proc) {
		boolean removed = false;
		boolean added = false;
		for (int i=0; i<this.size(); i++) {
			removed = this.get(i).remove(proc);
			if (removed) {
				proc.burstsRecd = 0;
				int j = Math.min(i+1, (size() - 1));
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

	public void execute() throws IOException {
	
		File outFile = new File("MLFOut.txt");
		FileWriter outWriter = new FileWriter(outFile);
		BufferedWriter outBuff = new BufferedWriter(outWriter);

		outBuff.write("MLF");
		outBuff.newLine();

		while (! this.isDone()) {
			PCB proc = peek();
			outBuff.write("Queue " + indexOf(currQueue) + " selected.");
			outBuff.newLine();
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
				//System.out.println(this);											//Uncomment to print the queue after each CPU burst.
			} catch (InterruptedException e) {}
			outBuff.write("Process " + proc.getID() + " receives a CPU burst. " + proc.getBurstTime() + " remaining.");
			outBuff.newLine();
			if (proc.getBurstTime() == 0) {
				totalWaitingTime += proc.waitTime;
				totalTurnaroundTime += proc.turnTime;
				outBuff.write("Process " + proc.getID() + " terminated.");
				outBuff.newLine();
				//System.out.println(this);											//Uncomment to print the queue when a process terminates.
			} else {
				if (proc.burstsRecd == currQueue.quantum) {
					currQueue.currIndex = currQueue.indexOf(proc);					//Added to prevent skipping a process after a demotion.
					demote(proc);
					outBuff.write("Process " + proc.getID() + " demoted.");
					outBuff.newLine();
					//System.out.println(this);										//Uncomment to print the queue when a process is demoted.
				}
			}
		}

		outBuff.close();
		System.out.println("MLF:");
		System.out.println(this);
	}

	public String toString() {
		String output = "";
		for (int i=0; i<this.size(); i++) {
			output += this.get(i).type + ":\n" + this.get(i);
		}
		return output;
	}
}