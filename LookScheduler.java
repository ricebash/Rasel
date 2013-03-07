import java.util.*;
public class LookScheduler implements DiskScheduler
{
	public List <Request> requests;
	public int start = 0; 
	public int totalTime = 0;
	public DiskSchedule sched;
	public int head;
	
	public LookScheduler(int head)
	{
		this.head = head;
		
		sched = new DiskSchedule();
	}
	
	public void addRequest(int arrivalTime, int cylinderAddress)
	{
		requests.add(new Request(start,arrivalTime,cylinderAddress));
		start++;
	}

	public DiskSchedule computeSchedule()
	{
		return sched;
	}
}