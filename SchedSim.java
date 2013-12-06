public class SchedSim {
	
	public static final int NUM_PROCESSES = 7;
	
	public static void main(String[] args) {
		
		FCFSQueue fcfs = new FCFSQueue();
		SJFQueue sjf = new SJFQueue();
		PriorityQueue priority = new PriorityQueue();
		RRQueue roundRobin = new RRQueue(6);

		for (int i=0; i<NUM_PROCESSES; i++) {
			PCB newProc = new PCB(i);
			PCB newProcSJFCpy = new PCB(newProc);
			PCB newProcPryCpy = new PCB(newProc);
			PCB newProcRRCpy = new PCB(newProc);
			PCB newProcMFCpy = new PCB(newProc);
			fcfs.add(i, newProc);
			sjf.add(i, newProcSJFCpy);
			priority.add(i, newProcPryCpy);
			roundRobin.add(i, newProcRRCpy);
		}
		/*
		System.out.println("FCFS:");
		System.out.println(fcfs);
		execute(fcfs);

		System.out.println("SJF:");
		System.out.println(sjf);
		execute(sjf);
		
		System.out.println("Priority:");
		System.out.println(priority);
		execute(priority);
		*/

		System.out.println("Round Robin:");
		System.out.println(roundRobin);
		execute(roundRobin);
	}

	public static void execute(SchedQueue queue) {
		double totalWaitingTime = 0;
		double avgWaitingTime = 0;
		double totalTurnaroundTime = 0;
		double avgTurnaroundTime = 0;

		while (! queue.isDone()) {
			PCB currProc = queue.peek();
			int numBurst;
			if((queue instanceof RRQueue) && queue.quantum < currProc.getBurstTime()) {
				numBurst = queue.quantum;
			} else {
				numBurst = currProc.getBurstTime();
			}
				for (int j=0; j<numBurst; j++) {
					try {
						currProc.giveBurst();
						for (int k=0; k<queue.size(); k++) {
							if (queue.get(k).getBurstTime() != 0 && !queue.get(k).equals(currProc)) {
								queue.get(k).giveWait();
							}
						}		
					} catch (InterruptedException e) {
						j--;
					}
				}
			totalWaitingTime += currProc.waitTime;
			totalTurnaroundTime += currProc.turnTime;
			System.out.println("Process " + currProc.getID() + " terminated.");
			System.out.println(queue);
		}
		avgWaitingTime = totalWaitingTime / (double)queue.size();
		avgTurnaroundTime = totalTurnaroundTime / (double)queue.size();
		System.out.println("Avg Waiting Time: " + avgWaitingTime);
		System.out.println("Avg Turnaround Time: " + avgTurnaroundTime);
	}
/*	
	public static void fcfs(PCB[] processes) {
	//Takes an array of PCB objects and runs them under FCFS scheduling, displaying output to console.
	//Assumes the number of processes (NUM_PROCESSES) <= the capacity of the queue.
		
		//Load the given PCB objects into a new FCFSQueue
		int numProcs = processes.length;
		FCFSQueue fcfs = new FCFSQueue();
		boolean pushed;
		for (int i=0; i<numProcs; i++) {
			pushed = fcfs.add(processes[i]);
			if (!pushed) {
				System.out.println("Process " + i + " not added. Queue is full.");
			}
		}
		System.out.println(fcfs);

		//Get the next PCB to be executed and execute it.
		for (int k=0; k<numProcs; k++) {
			PCB currProc = fcfs.peek();
			int currProcBurst = currProc.getBurstTime();
			for (int i=0; i<currProcBurst; i++) {
				try {
					currProc.giveBurst();
					for (int j=0; j<numProcs; j++) {
						if (fcfs.get(j).getBurstTime() != 0 && !fcfs.get(j).equals(currProc)) {
							fcfs.get(j).giveWait();
						}
					}		
				} catch (InterruptedException e) {
					i--;
				}
			}
			System.out.println("Process " + currProc.getID() + " terminated.");
			System.out.println(fcfs);
		}
	}

	public static void sjf(PCB[] processes) {
	//Takes an array of PCB objects and runs them under SJF scheduling, displaying output to console.
	//Assumes the number of processes (NUM_PROCESSES) <= the capacity of the queue.

		int numProcs = processes.length;
		SJFQueue sjf = new SJFQueue();
		boolean pushed;
		for (int i=0; i<numProcs; i++) {
			pushed = sjf.push(processes[i]);
			if (!pushed) {
				System.out.println("Process " + i + " not added. Queue is full.");
			}
		}
		System.out.println(sjf);

		//Get the next PCB to be executed and execute it.
		int totWaitingTime = 0;
		for (int k=0; k<numProcs; k++) {
			PCB nextProc = sjf.peek();
			int nextProcBurst = nextProc.getBurstTime();
			for (int i=0; i<nextProcBurst; i++) {
				try {
					nextProc.giveBurst();
					for (int j=0; j<numProcs; j++) {
						if (sjf.peek(j).getBurstTime() != 0 && !sjf.peek(j).equals(nextProc)) {
							sjf.peek(j).giveWait();
						}
					}
					System.out.println(sjf);
					totWaitingTime += (sjf.size() - 1);
					//System.out.println("Total Waiting Time: " + totWaitingTime);
				} catch (InterruptedException e) {
					i--;
				}
			}
			//sjf.pop();
		}
		int avgWaitingTime = totWaitingTime / numProcs;
		System.out.println("Average Waiting Time: " + avgWaitingTime);	
	}

	public static void priority(PCB[] processes) {
	//Takes an array of PCB objects and runs them under Priority scheduling, displaying output to console.
	//Assumes the number of processes (NUM_PROCESSES) <= the capacity of the queue.

		int numProcs = processes.length;
		PriorityQueue priority = new PriorityQueue();
		boolean pushed;
		for (int i=0; i<numProcs; i++) {
			pushed = priority.push(processes[i]);
			if (!pushed) {
				System.out.println("Process " + i + " not added. Queue is full.");
			}
		}
		System.out.println(priority);

		//Get the next PCB to be executed and execute it.
		int totWaitingTime = 0;
		for (int k=0; k<numProcs; k++) {
			PCB nextProc = priority.peek();
			int nextProcBurst = nextProc.getBurstTime();
			for (int i=0; i<nextProcBurst; i++) {
				try {
					nextProc.giveBurst();
					for (int j=0; j<numProcs; j++) {
						if (priority.peek(j).getBurstTime() != 0 && !priority.peek(j).equals(nextProc)) {
							priority.peek(j).giveWait();
						}
					}
					//System.out.println(priority);
					totWaitingTime += (priority.size() - 1);
					//System.out.println("Total Waiting Time: " + totWaitingTime);
				} catch (InterruptedException e) {
					i--;
				}
			}
			//nextProc.setPriority(-1 * nextProc.getPriority());
			System.out.println(priority);
			//priority.pop();
		}
		int avgWaitingTime = totWaitingTime / numProcs;
		System.out.println("Average Waiting Time: " + avgWaitingTime);

	}
*/
}