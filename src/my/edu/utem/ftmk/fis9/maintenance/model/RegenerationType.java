package my.edu.utem.ftmk.fis9.maintenance.model;

import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class RegenerationType extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int regenerationTypeID;
	private String name;
	private int stateID;
	private String stateName;
	private ArrayList<RegenerationSpecies> regenerationSpeciesList;

	public int getRegenerationTypeID()
	{
		return regenerationTypeID;
	}

	public void setRegenerationTypeID(int regenerationTypeID)
	{
		this.regenerationTypeID = regenerationTypeID;
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

	public ArrayList<RegenerationSpecies> getRegenerationSpeciesList()
	{
		return regenerationSpeciesList;
	}

	public void setRegenerationSpeciesList(
			ArrayList<RegenerationSpecies> regenerationSpeciesList)
	{
		this.regenerationSpeciesList = regenerationSpeciesList;
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

		if (obj instanceof RegenerationType)
		{
			RegenerationType that = (RegenerationType) obj;
			equal = regenerationTypeID == that.regenerationTypeID;
		}

		return equal;
	}
}