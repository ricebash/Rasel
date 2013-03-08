import java.util.*;
public class CScanScheduler implements DiskScheduler
{
	public List <Request> requests= new ArrayList<Request>();
	public int start = 0; 
	public int totalTime = 0;
	public DiskSchedule sched;
	public int head;
	
	public CScanScheduler(int head)
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