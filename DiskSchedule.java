import java.util.*;
public class DiskSchedule
{
	public List<ServicedRequest> served;
	public int totalResponseTime;
	
	public DiskSchedule()
	{}
	
	public void addServed(Request req,int time)
	{
		served.add(new ServicedRequest(req.id,time));
		totalResponseTime += time - req.arrival;
	}
	
	public List<ServicedRequest> getRequestOrder()
	{
		return served;
	}
	
	public double averageResponseTime()
	{
		return (totalResponseTime/served.size());
	}
}