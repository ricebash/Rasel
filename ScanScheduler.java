public class ScanScheduler
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
		ArrayList<Integer> arrived = new ArrayList<Integer>();
		boolean ascend = true;
		while(requests.size() > 0 || arrived.size() >0)
		{
			boolean go = false;
			int i = 0;
			int closest = 2048;
			while(i < requests.size())
			{
				if(requests.get(i).arrival <= totalTime)
				{
					arrived.add(i);
					i++;
				}
			}
			if(arrived.size() == 0)
			{
				totalTime++;
			}
			else
			{
				int getIndex;
				for( int j = 0; j< arrived.size; j++)
				{
					int index = arrived.get(j);
					if(ascend)
					{
						if((requests.get(index).address >= head) && (requests.get(index).address - head <= closest))
						{
							getIndex = index;
							closest =  requests.get(index).address - head;
							go = true;
						}
					}
					else
					{
						if((requests.get(index).address < head) && (requests.get(index).address - head <= closest))
						{
							getIndex = index;
							closest = head - requests.get(index).address;
							go = true;
						}
					}
				}
				if(go = true)
				{
					totalTime += closest;			
					sched.addServed(request.get(getIndex),totalTime);
					requests.remove(getIndex);
				}
				else
				{
					totalTime++;
				}
			}
		}
	}
}