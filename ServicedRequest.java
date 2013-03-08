public class ServicedRequest
{
	private int id;
	private int time;
	
	public ServicedRequest(int id, int time)
	{
		this.id = id;
		this.time = time;
	}
	
	public int getRequestId()
	{
		return id;
	}
	
	public void setRequestId(int id)
	{
		this.id = id;
	}
	
	public int timeServiced()
	{
		return time;
	}
	
	public void setTime(int time)
	{
		this.time = time;
	}
}