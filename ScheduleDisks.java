import java.io.*;
import java.util.*;
public class ScheduleDisks
{
	public static void main(String args[]) throws IOException
	{
		SchedulerFactory factory = new SchedulerFactory();
		DiskScheduler sched;
		Scanner sc = new Scanner(new FileReader("io-requests.txt"));
		PrintStream out = new PrintStream("disk-schedules.txt");
		while(true)
		{
			String input = sc.nextLine();
			if(input.equals("END 0"))
			{
				break;
			}
			else
			{
				List<Pair> pairs = new ArrayList<Pair>();
				DiskSchedule order;
				List<ServicedRequest> printable;
				String [] request = input.split(" ");
				//out.print(request[0]);
				int head = Integer.parseInt(request[1]);
				sched = factory.createScheduler(request[0],head);
				int numRequest = Integer.parseInt(sc.nextLine());
				for(int i = 0; i < numRequest; i++)
				{
					String temp = sc.nextLine();
					String temp2 [] = temp.split(" ");
					int arrive = Integer.parseInt(temp2[0]);
					int address = Integer.parseInt(temp2[1]);
					Pair temp3 = new Pair(arrive,address);
					pairs.add(temp3);
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
					out.print("R" + printable.get(i).getRequestId() + " " + printable.get(i).timeServiced() + " ");
				}
				out.print("\n" + order.averageResponseTime() + "\n");
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
		return one.arrival - two.arrival;
		// if(comp == 0)
		// {
		// 	return -1; 
		// }
		// else
		// {
		// 	return comp;
		// }
	}
}


















