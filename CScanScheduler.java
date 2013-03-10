import java.util.*;
public class CScanScheduler implements DiskScheduler
{
	public List <Request> requests= new ArrayList<Request>();
	public int start = 0; 
	public int totalTime = 0;
	public DiskSchedule sched;
	public int head;

	public List<SeekTime> higherSet = new ArrayList<SeekTime>();
	public List<SeekTime> lowerSet = new ArrayList<SeekTime>();
	
	public CScanScheduler(int head)
	{
		this.head = head;
		sched = new DiskSchedule();
	}
	
	public void addRequest(int arrivalTime, int cylinderAddress)
	{
		requests.add(new Request(start,arrivalTime,cylinderAddress));

		if( cylinderAddress > head )
			higherSet.add(new SeekTime(start, Math.abs(cylinderAddress - head), arrivalTime ));
		else lowerSet.add(new SeekTime(start, Math.abs(cylinderAddress - head), arrivalTime));

		start++;
	}

	public DiskSchedule computeSchedule()
	{
		Collections.sort(higherSet, new SeekTimeComparator());
		Collections.sort(lowerSet, new RebersSeekTimeComparator());
		
		for(int i = 0; i < higherSet.size(); ++i){
			int index = higherSet.get(i).id;
			totalTime += higherSet.get(i).st;
			sched.addServed(requests.get(index), totalTime);
		}

		totalTime += 2047;

		for(int i = 0; i < lowerSet.size(); ++i){
			int index = lowerSet.get(i).id;
			totalTime += higherSet.get(i).st;
			sched.addServed(requests.get(index), totalTime);
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

	class RebersSeekTimeComparator implements Comparator<SeekTime>{
		public int compare(SeekTime one, SeekTime two){
			return two.st - one.st;
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