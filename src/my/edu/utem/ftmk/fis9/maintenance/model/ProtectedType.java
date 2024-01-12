package my.edu.utem.ftmk.fis9.maintenance.model;

import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class ProtectedType extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int protectedTypeID;
	private String name;
	private int stateID;
	private String stateName;
	private ArrayList<ProtectedSpecies> protectedSpeciesList;

	public int getProtectedTypeID()
	{
		return protectedTypeID;
	}

	public void setProtectedTypeID(int protectedTypeID)
	{
		this.protectedTypeID = protectedTypeID;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
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

	public ArrayList<ProtectedSpecies> getProtectedSpeciesList()
	{
		return protectedSpeciesList;
	}

	public void setProtectedSpeciesList(
			ArrayList<ProtectedSpecies> protectedSpeciesList)
	{
		this.protectedSpeciesList = protectedSpeciesList;
	}

	@Override
	public String toString()
	{
		return name;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof ProtectedType)
		{
			ProtectedType that = (ProtectedType) obj;
			equal = protectedTypeID == that.protectedTypeID;
		}

		return equal;
	}
}