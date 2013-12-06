public class SchedSim {
	
	public static final int NUM_PROCESSES = 7;
	
	public static void main(String[] args) {
		
		PCB[] processes = new PCB[NUM_PROCESSES];
		//FCFSQueue fcfs = new FCFSQueue();
		for (int i=0; i<NUM_PROCESSES; i++) {
			PCB newProc = new PCB(i);
			processes[i] = newProc;
			//boolean pushed = fcfs.push(newProc);
		}
		//System.out.println(fcfs);
		fcfs(processes);
		//sjf(processes);
		//priority(processes);
	}
	
	public static void fcfs(PCB[] processes) {
	//Takes an array of PCB objects and runs them under FCFS scheduling, displaying output to console.
	//Assumes the number of processes (NUM_PROCESSES) <= the capacity of the queue.
		
		//Load the given PCB objects into a new FCFSQueue
		int numProcs = processes.length;
		FCFSQueue fcfs = new FCFSQueue();
		boolean pushed;
		for (int i=0; i<numProcs; i++) {
			pushed = fcfs.push(processes[i]);
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
						if (fcfs.peek(j).getBurstTime() != 0 && !fcfs.peek(j).equals(currProc)) {
							fcfs.peek(j).giveWait();
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
}