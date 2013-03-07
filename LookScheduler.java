public class LookScheduler
{
	public List <Request> requests;
	public int start = 0; 
	public int totalTime = 0;
	public DiskSchedule sched;
	public int head;
	
	public FcfsScheduler(int head)
	{
		this.head = head;
	}
	
	public void addRequest(int arrivalTime, int cylinderAddress)
	{
		requests.add(new Request(start,arrivalTime,cylinderAddress));
		start++;
	}

	public DiskSchedule computeSchedule()
	{
	
	}
}