import java.util.*;
import java.io.*;

public class SchedSim {
	
	public static final int NUM_PROCESSES = 10;
	
	public static void main(String[] args) {
		
		try {
			demo();
		} catch (IOException e) {}
	}

	public static void demo() throws IOException {

		//Create instances of queue classes to demo.
		FCFSQueue fcfs = new FCFSQueue();
		SJFQueue sjf = new SJFQueue();
		PriorityQueue priority = new PriorityQueue();
		RRQueue roundRobin = new RRQueue(6);
		MLFQueue mlf = new MLFQueue();
		mlf.addQueue(new RRQueue(6), 0.5);				//Add 3 given queues to MLFQueue for demo.
		mlf.addQueue(new RRQueue(12), 0.3);
		mlf.addQueue(new FCFSQueue(), 0.2);

		for (int i=0; i<NUM_PROCESSES; i++) {
			//Create a new PCB object and add a copy of it to each queue.
			PCB newProc = new PCB(i);
			PCB newProcSJFCpy = new PCB(newProc);
			PCB newProcPryCpy = new PCB(newProc);
			PCB newProcRRCpy = new PCB(newProc);
			PCB newProcMLFCpy = new PCB(newProc);
			fcfs.add(i, newProc);
			sjf.add(i, newProcSJFCpy);
			priority.add(i, newProcPryCpy);
			roundRobin.add(i, newProcRRCpy);
			mlf.add(newProcMLFCpy);						//See add(PCB) method in MLFQueue class.
		}
		
		System.out.println(fcfs);						//Prints the processes just created.

		fcfs.execute();
		sjf.execute();
		priority.execute();
		roundRobin.execute();
		mlf.execute();
		

		System.out.println("\tAlgorithm\tAvg Waiting Time\tAvg Turnaround Time");
		System.out.printf("\tFCFS\t\t\t%.2f\t\t\t%.2f\n", (fcfs.totalWaitingTime / (double) NUM_PROCESSES), (fcfs.totalTurnaroundTime / (double) NUM_PROCESSES));
		System.out.printf("\tSJF\t\t\t%.2f\t\t\t%.2f\n", (sjf.totalWaitingTime / (double) NUM_PROCESSES), (sjf.totalTurnaroundTime / (double) NUM_PROCESSES));
		System.out.printf("\tPriority\t\t%.2f\t\t\t%.2f\n", (priority.totalWaitingTime / (double) NUM_PROCESSES), (priority.totalTurnaroundTime / (double) NUM_PROCESSES));
		System.out.printf("\tRound Robin\t\t%.2f\t\t\t%.2f\n", (roundRobin.totalWaitingTime / (double) NUM_PROCESSES), (roundRobin.totalTurnaroundTime / (double) NUM_PROCESSES));
		System.out.printf("\tMulti-Level\t\t%.2f\t\t\t%.2f\n", (mlf.totalWaitingTime / (double) NUM_PROCESSES), (mlf.totalTurnaroundTime / (double) NUM_PROCESSES));
	}
}