package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class CuttingOption extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int cuttingOptionID;
	private double dipterocarpLimit;
	private double nonDipterocarpLimit;
	private int stateID;
	private String stateName;

	public int getCuttingOptionID()
	{
		return cuttingOptionID;
	}

	public void setCuttingOptionID(int cuttingOptionID)
	{
		this.cuttingOptionID = cuttingOptionID;
	}

	public double getDipterocarpLimit()
	{
		return dipterocarpLimit;
	}

	public void setDipterocarpLimit(double dipterocarpLimit)
	{
		this.dipterocarpLimit = dipterocarpLimit;
	}

	public double getNonDipterocarpLimit()
	{
		return nonDipterocarpLimit;
	}

	public void setNonDipterocarpLimit(double nonDipterocarpLimit)
	{
		this.nonDipterocarpLimit = nonDipterocarpLimit;
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
		return "Had tebangan dipterokarp: " + dipterocarpLimit + " cm, bukan dipterokarp: " + nonDipterocarpLimit + " cm";
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof CuttingOption)
		{
			CuttingOption that = (CuttingOption) obj;
			equal = cuttingOptionID == that.cuttingOptionID;
		}

		return equal;
	}
}