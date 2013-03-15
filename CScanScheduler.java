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
		ArrayList<Integer> arrived = new ArrayList<Integer>();
		boolean ascend = true;
		int siz = requests.size();
		while(requests.size() > 0)
		{
			boolean go = false;
			int closest = 2048;
			int getIndex = 2048;
			for( int j = 0; j< requests.size(); j++)
			{
				//System.out.println(head + " add " + requests.get(j).address); 
				if((requests.get(j).address >= head) && (requests.get(j).address - head <= closest))
				{
					if(requests.get(j).arrival <= totalTime)
					{
						getIndex = j;
						closest =  requests.get(j).address - head;
						go = true;
					}
				}
			}
			//System.out.println("closest " + closest + " " + go);
			if(go == true)
			{
				totalTime += closest;			
				sched.addServed(requests.get(getIndex),totalTime);
				requests.remove(getIndex);
				head += closest;
			}
			else
			{
				head++;
				if(head == 2048)
				{
					head = 0;
				}
					totalTime++;
			}
		}
		return sched;
	}
}