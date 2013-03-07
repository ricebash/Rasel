pubclic class SchedulerFactory {
	public static DiskScheduler createScheduler(String code,int headAddress){
		if( code.equals("FCFS") ){
			return new FcfsScheduler(int head);//you can create your object however you want, of course.
		}else if ( code.equals("SSTF")) {
			return new SstfScheduler(int head);
		}else if ( code.equals("SCAN")) {
			return new ScanScheduler(int head);
		}else if ( code.equals("LOOK")) {
			return new LookScheduler(int head);
		} 
		/* BONUS
		else if (code.equals("CSCAN")){
			return new CScanScheduler();
		}else if (code.equals("CLOOK")){
			return new CLookScheduler();
		} 
		*/
		else {
			throw new IllegalArgumentException("Invalid code for disk scheduler: " + code);//don't need to do this, but it is a good idea.
		}

	}
}