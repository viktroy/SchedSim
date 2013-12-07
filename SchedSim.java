public class SchedSim {
	
	public static final int NUM_PROCESSES = 7;
	
	public static void main(String[] args) {

		demo();
	}

	public static void demo() {

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
		/*
		System.out.println("FCFS:");
		System.out.println(fcfs);
		fcfs.execute();

		System.out.println("SJF:");
		System.out.println(sjf);
		sjf.execute();

		System.out.println("Priority:");
		System.out.println(priority);
		priority.execute();
		
		
		System.out.println("Round Robin:");
		System.out.println(roundRobin);
		roundRobin.execute();
		*/

		System.out.println("Multi-Level Feeback Queue:");
		System.out.println(mlf);
		mlf.execute();
	}
}