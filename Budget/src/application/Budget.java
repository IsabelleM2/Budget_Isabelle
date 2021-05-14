package application;

public class Budget {

	private Double date;
	private String usage;
	private Double budget;
	private Double mp;
	
	
	public  Budget()
	{
		this(null,null);
	}
	public Budget(Double date, Object object )
	{
		this.date=0.0;
		this.budget=0.0;
		this.mp=0.0;
		this.usage="";
	}
	
	

	public Double getDate() {
		return date;
	}
	public void setDate(Double date) {
		this.date = date;
	}
	public String getUsage() {
		return usage;
	}
	public void setUsage(String usage) {
		this.usage = usage;
	}
	public Double getBudget() {
		return budget;
	}
	public void setBudget(Double budget) {
		this.budget = budget;
	}
	public Double getMp() {
		return mp;
	}
	public void setMp(Double mp) {
		this.mp = mp;
	}
}
