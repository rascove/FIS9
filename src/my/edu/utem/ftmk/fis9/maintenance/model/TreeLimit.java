package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class TreeLimit extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int treeLimitID;
	private double motherLimit;
	private double chengalLimit;
	private double protectedLimit;
	private int maximumPerPlot;
	private int stateID;
	private String stateName;

	public int getTreeLimitID()
	{
		return treeLimitID;
	}

	public void setTreeLimitID(int treeLimitID)
	{
		this.treeLimitID = treeLimitID;
	}

	public double getMotherLimit()
	{
		return motherLimit;
	}

	public void setMotherLimit(double motherLimit)
	{
		this.motherLimit = motherLimit;
	}

	public double getChengalLimit()
	{
		return chengalLimit;
	}

	public void setChengalLimit(double chengalLimit)
	{
		this.chengalLimit = chengalLimit;
	}

	public double getProtectedLimit()
	{
		return protectedLimit;
	}

	public void setProtectedLimit(double protectedLimit)
	{
		this.protectedLimit = protectedLimit;
	}

	public int getMaximumPerPlot()
	{
		return maximumPerPlot;
	}

	public void setMaximumPerPlot(int maximumPerPlot)
	{
		this.maximumPerPlot = maximumPerPlot;
	}

	public int getStateID()
	{
		return stateID;
	}

	public void setStateID(int stateID)
	{
		this.stateID = stateID;
	}

	public String getStateName()
	{
		return stateName;
	}

	public void setStateName(String stateName)
	{
		this.stateName = stateName;
	}

	@Override
	public String toString()
	{
		return "Had tebangan untuk " + stateName;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof TreeLimit)
		{
			TreeLimit that = (TreeLimit) obj;
			equal = treeLimitID == that.treeLimitID;
		}

		return equal;
	}
}