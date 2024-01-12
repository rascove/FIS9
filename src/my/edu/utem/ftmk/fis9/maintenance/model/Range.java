package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class Range extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int rangeID;
	private String name;
	private String address;
	private int districtID;
	private int stateID;
	private String asstOfficerID;
	private String districtName;
	private String stateName;
	private String asstOfficerName;
	
	public int getRangeID()
	{
		return rangeID;
	}

	public void setRangeID(int rangeID)
	{
		this.rangeID = rangeID;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public int getDistrictID()
	{
		return districtID;
	}

	public void setDistrictID(int districtID)
	{
		this.districtID = districtID;
	}
	
	public int getStateID()
	{
		return stateID;
	}

	public void setStateID(int stateID)
	{
		this.stateID = stateID;
	}

	public String getAsstOfficerID()
	{
		return asstOfficerID;
	}

	public void setAsstOfficerID(String asstOfficerID)
	{
		this.asstOfficerID = asstOfficerID;
	}
	
	public String getDistrictName()
	{
		return districtName;
	}

	public void setDistrictName(String districtName)
	{
		this.districtName = districtName;
	}

	public String getStateName()
	{
		return stateName;
	}

	public void setStateName(String stateName)
	{
		this.stateName = stateName;
	}

	public String getAsstOfficerName()
	{
		return asstOfficerName;
	}

	public void setAsstOfficerName(String asstOfficerName)
	{
		this.asstOfficerName = asstOfficerName;
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
		
		if (obj instanceof Range)
		{
			Range that = (Range) obj;
			equal = rangeID == that.rangeID;
		}
		
		return equal;
	}
}