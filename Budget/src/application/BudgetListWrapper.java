package application;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="budget")
public class BudgetListWrapper {

	private List<Budget> budget;
	
	@XmlElement(name="budget")
	public List<Budget> getBudget()
	{
		return budget;
		
	}
	public void setBudget(List<Budget> budget)
	{
		this.budget=budget;
	}
	
	
	
}
