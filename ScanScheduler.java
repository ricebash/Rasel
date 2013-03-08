import java.util.*;
public class ScanScheduler implements DiskScheduler
{
	public List <Request> requests= new ArrayList<Request>();
	public int start = 0; 
	public int totalTime = 0;
	public DiskSchedule sched;
	public int head;
	
	public ScanScheduler(int head)
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
		while(requests.size() > 0 || arrived.size() >0)
		{
			boolean go = false;
			int i = 0;
			int closest = 2048;
			int getIndex = 0;
			for( int j = 0; j< requests.size(); j++)
			{
				//System.out.println(head + " add " + requests.get(j).address); 
				if(ascend)
				{
					if((requests.get(j).address >= head) && (requests.get(j).address - head <= closest))
					{
						getIndex = j;
						closest =  requests.get(j).address - head;
						go = true;
					}
				}
				else
				{
					if((requests.get(j).address < head) && (head - requests.get(j).address <= closest))
					{
						getIndex = j;
						closest = head - requests.get(j).address;
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
				if(ascend)
				{
					head += closest;
				}
				else
				{
					head -=closest;
				}
			}
			else
			{
				if(ascend)
				{
					head++;
				}
				else
				{
					head--;
				}
				if(head == -1 || head == 2048)
				{
					ascend = !ascend;
					if(ascend)
					{
						head = 0;
					}
					else
					{
						head = 2047;
					}
				}
				else
				{
				totalTime++;
				}
			}
		}
		return sched;
	}
}