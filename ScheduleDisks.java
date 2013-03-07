import java.io.*;

public class ScheduleDisks
{
	public static void main(String args[])
	{
		public SchedulerFactory factory = new SchedulerFactory();
		public DiskScheduler sched;
		Scanner sc = new Scanner(new FileReader("io-requests.txt"));
		while(true)
		{
			String input = sc.nextLine();
			if(input.equals("END 0")
			{
				break;
			}
			else
			{
				List<Pair> pairs = new List<Pair>();
				public DiskSchedule order;
				List<ServicedRequest> printable;
				String [] request = input.split(" ");
				int head = Integer.parseInt(request[1]);
				sched = factory.createScheduler(request[0],head);
				int numRequest = Integer.parseInt(sc.nextLine());
				for(int i = 0; i < numRequest; i++)
				{
					String temp = sc.nextLine();
					input = temp.split(" ");
					int arrive = Integer.parseInt(input[0]);
					int address = Integer.parseInt(input[1]);
					pairs.add(arrive,address);
				}
				Collections.sort(pairs,new ArrivalComparator());
				for(int i = 0; i < numRequest; i++)
				{
					sched.addRequest(pairs.get(i).arrival,pairs.get(i).address);
				}
				order = sched.computeSchedule();
				printable = order.getRequestOrder();
				for(int i = 0; i < printable.size(); i++)
				{
					System.out.print("R" + printable.get(i).getRequestId() + " " + printable.get(i).timeServiced() + " ");
				}
				System.out.print("\n" + order.averageResponseTime + "\n");
			}
		}
	}
}

class Pair
{
	int arrival;
	int address;
	public Pair(int arrival, int address)
	{
		this.arrival = arrival;
		this.address = address;
	}
}

class ArrivalComparator implements Comparator<Pair>
{
	public int compare(Pair one, Pair two)
	{
		int comp = one.arrival - two.arrival;
		if(comp == 0)
		{
			return -1; 
		}
		else
		{
			return comp;
		}
	}
}


















