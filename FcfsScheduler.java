public class FcfsScheduler
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
		for(int i = 0; i <= start; i++);
		{
			if(request.get(i).address > head)
			{
				totalTime += request.get(i).address - head;
				head = request.get(i).address;
			}
			else
			{
				totalTime += head - request.get(i).address;
				head = request.get(i).address;
			}
			sched.addServed(request.get(i),totalTime);
		}
	}
}