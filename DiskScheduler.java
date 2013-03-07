public interface DiskScheduler {
	public void addRequest(int arrivalTime, int cylinderAddress );
	public DiskSchedule computeSchedule();
}