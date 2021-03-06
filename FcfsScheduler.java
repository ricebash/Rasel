import java.util.*;
public class FcfsScheduler implements DiskScheduler
{
	public ArrayList <Request> requests = new ArrayList<Request>();
	public int start = 0; 
	public int totalTime = 0;
	public DiskSchedule sched;
	public int head;
	
	public FcfsScheduler(int head)
	{
		this.head = head;
		sched = new DiskSchedule();
	}
	
	public void addRequest(int arrivalTime, int cylinderAddress)
	{
		Request temp = new Request(start,arrivalTime,cylinderAddress);
		requests.add(temp);
		start++;
	}

	public DiskSchedule computeSchedule()
	{
		for(int i = 0; i < start; i++)
		{
			if(requests.get(i).address > head)
			{
				totalTime += requests.get(i).address - head;
				head = requests.get(i).address;
			}
			else
			{
				totalTime += head - requests.get(i).address;
				head = requests.get(i).address;
			}
			sched.addServed(requests.get(i),totalTime);
		}
		return sched;
	}
}