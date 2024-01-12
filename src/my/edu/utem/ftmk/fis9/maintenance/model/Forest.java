package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class Forest extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int forestID;
	private String code;
	private String name;
	private double area;
	private double volumeRate;
	private double powerRate;
	private int districtID;
	private int stateID;
	private String districtName;
	private String stateName;

	public int getForestID()
	{
		return forestID;
	}

	public void setForestID(int forestID)
	{
		this.forestID = forestID;
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

	public double getArea()
	{
		return area;
	}

	public void setArea(double area)
	{
		this.area = area;
	}

	public double getVolumeRate()
	{
		return volumeRate;
	}

	public void setVolumeRate(double volumeRate)
	{
		this.volumeRate = volumeRate;
	}

	public double getPowerRate()
	{
		return powerRate;
	}

	public void setPowerRate(double powerRate)
	{
		this.powerRate = powerRate;
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

	@Override
	public String toString()
	{
		return name;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof Forest)
		{
			Forest that = (Forest) obj;
			equal = forestID == that.forestID;
		}

		return equal;
	}
}