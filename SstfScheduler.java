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
	{
		Collections.sort(seekTimeList, new SeekTimeComparator());
		Collections.sort(seekTimeList, new ArrivalTimeComparator());

		for(int i = 0; i < start; ++i){
			int index = seekTimeList.get(i).id;
			//System.out.println("R"+index+"::"+seekTimeList.get(i).st);
			totalTime += seekTimeList.get(i).st;
			sched.addServed(requests.get(index),totalTime);
		}

		return sched;
	}

	class ArrivalTimeComparator implements Comparator<SeekTime>{
		public int compare(SeekTime one, SeekTime two){
			return one.at - two.at;
		}
	}

	class SeekTimeComparator implements Comparator<SeekTime>{
		public int compare(SeekTime one, SeekTime two){
			return one.st - two.st;
		}
	}

	class SeekTime {
		int st;
		int id;
		int at;
		public SeekTime(int idThing, int seekTime, int arrivalTime){
			id = idThing;
			st = seekTime;
			at = arrivalTime;
		}
	}
}