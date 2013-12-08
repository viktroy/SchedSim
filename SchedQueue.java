import java.util.*;
import java.io.*;

public class SchedQueue extends ArrayList<PCB> {
/*
	A superclass representing a generic queue of PCB objects.
*/


	int quantum = -1;
	int peekCount;
	int currIndex;						//Added for RRQueue handling demotion in MLFQueue
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

	public void execute() throws IOException {
		
		String outFileName = type + "Out.txt"; 
		File outFile = new File(outFileName);
		FileWriter outWriter = new FileWriter(outFile);
		BufferedWriter outBuff = new BufferedWriter(outWriter);

		outBuff.write(type);
		outBuff.newLine();

		while (! this.isDone()) {
			
			PCB proc = this.peek();
			try {
				proc.giveBurst();
				for (int i=0; i<this.size(); i++) {
					PCB nextPCB = this.get(i);
					if (nextPCB.getBurstTime() != 0 && !nextPCB.equals(proc)) {
						nextPCB.giveWait();
					}
				//System.out.println(this);										//Uncomment to print the queue after each CPU burst.	
				}		
			} catch (InterruptedException e) {}
			outBuff.write("Process " + proc.getID() + " receives a CPU burst. " + proc.getBurstTime() + " remaining.");
			outBuff.newLine();
			if (proc.getBurstTime() == 0) {
				totalWaitingTime += proc.waitTime;
				totalTurnaroundTime += proc.turnTime;
				outBuff.write("Process " + proc.getID() + " terminated.");
				outBuff.newLine();
				//System.out.println(this);										//Uncomment to print the queue when a process is terminated.
			}
		}

		outBuff.close();
		System.out.println(type + ":");
		System.out.println(this);
		
	}

	public String toString() {
		String output = "\tProcess ID\tBurst Time\tPriority\tWait Time\tT. Time\n";
		for (int i=0; i<this.size(); i++) {
			output += (this.get(i).toString() + "\n");
		}
		return output;
	}
}