package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class Region extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int regionID;
	private String code;
	private String name;
	private int stateID;
	
	public int getRegionID()
	{
		return regionID;
	}

	public void setRegionID(int regionID)
	{
		this.regionID = regionID;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
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

	@Override
	public String toString()
	{
		return name;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;
		
		if (obj instanceof Region)
		{
			Region that = (Region) obj;
			equal = regionID == that.regionID;
		}
		
		return equal;
	}
}