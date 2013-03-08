public class SchedulerFactory {
	public static DiskScheduler createScheduler(String code,int headAddress){
		if( code.equals("FCFS") ){
			return new FcfsScheduler(headAddress);
		}else if ( code.equals("SSTF")) {
			return new SstfScheduler(headAddress);
		}else if ( code.equals("SCAN")) {
			return new ScanScheduler(headAddress);
		}else if ( code.equals("LOOK")) {
			return new LookScheduler(headAddress);
		} 
		// BONUS
		else if (code.equals("CSCAN")){
			return new CScanScheduler(headAddress);
		}else if (code.equals("CLOOK")){
			return new CLookScheduler(headAddress);
		} 
		else {
			throw new IllegalArgumentException("Invalid code for disk scheduler: " + code);//don't need to do this, but it is a good idea.
		}

	}
}