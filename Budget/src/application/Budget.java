package application;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class Budget {

	private LocalDate pd;
	private String usage;
	private Double budget;
	private Double mp;
	private Double mr;
	
	
	
	public  Budget()
	{
		this(null,null);
	}
	public Budget(Double date, Object object )
	{
		this.budget=0.0;
		this.mr=0.0;
		this.mp=0.0;
		this.usage="";
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
	
	public Double getMr() {
		return mr;
	}
	public void setMr(Double mr) {
		this.mr = mr;
	}
	
	//Date examen
		@XmlJavaTypeAdapter(LocalDateAdapter.class)
		public LocalDate getDate() {
			return pd;
		}

		public void setDate(LocalDate pd) {
			this.pd = pd;
		}
		
		
			
	
	
	
}
