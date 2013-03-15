import java.util.*;
public class SstfScheduler implements DiskScheduler
{
	public ArrayList <Request> requests= new ArrayList<Request>();
	public int start = 0; 
	public int totalTime = 0;
	public DiskSchedule sched;
	public int head;

	public ArrayList <SeekTime> seekTimeList = new ArrayList<SeekTime>();
	
	public SstfScheduler(int head)
	{
		this.head = head;
		sched = new DiskSchedule();
	}
	
	public void addRequest(int arrivalTime, int cylinderAddress)
	{
		requests.add(new Request(start,arrivalTime,cylinderAddress));
		seekTimeList.add(new SeekTime(start, Math.abs( cylinderAddress - head ), arrivalTime ));
		//System.out.println("address="+ cylinderAddress + " head=" + head );
		start++;
	}

	public DiskSchedule computeSchedule()
	{/*
		Collections.sort(seekTimeList, new ArrivalTimeComparator());
		for(int i = 0; i < start; ++i){
			
			int index = seekTimeList.get(i).id;
			//System.out.println("R"+index+"::"+seekTimeList.get(i).st);
			totalTime += seekTimeList.get(i).st;
			sched.addServed(requests.get(index),totalTime);
		}

		return sched;
		*/
		int siz = requests.size();
		while(requests.size() > 0)
		{
			boolean go = false;
			int closest = 2048;
			int getIndex = 2048;
			for( int j = 0; j< requests.size(); j++)
			{
				//System.out.println(head + " add " + requests.get(j).address); 
				if((Math.abs(requests.get(j).address - head) <= closest))
				{
					if(requests.get(j).arrival <= totalTime)
					{
						getIndex = j;
						closest =  Math.abs(requests.get(j).address - head);
						go = true;
					}
				}
			}
			//System.out.println("closest " + closest + " " + go);
			if(go == true)
			{
				totalTime += closest;			
				sched.addServed(requests.get(getIndex),totalTime);
				if(requests.get(getIndex).address > head)
				{
					head +=closest;
				}
				else
				{
					head -= closest;
				}
				requests.remove(getIndex);
			}
			else
			{
				totalTime++;
			}
		}
		return sched;
	
	}

	class ArrivalTimeComparator implements Comparator<SeekTime>{
		public int compare(SeekTime one, SeekTime two){
			return one.at - two.at;
		}
	}

	class SeekTime {
		int ct;
		int id;
		int at;
		public SeekTime(int idThing, int cylinderAddress, int arrivalTime){
			id = idThing;
			ct = cylinderAddress;
			at = arrivalTime;
		}
	}
}