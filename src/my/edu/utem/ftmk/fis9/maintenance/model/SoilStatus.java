package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class SoilStatus extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int soilStatusID;
	private String code;
	private String name;

	public int getSoilStatusID()
	{
		return soilStatusID;
	}

	public void setSoilStatusID(int soilStatusID)
	{
		this.soilStatusID = soilStatusID;
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

	@Override
	public String toString()
	{
		return code + " - " + name;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof SoilStatus)
		{
			SoilStatus that = (SoilStatus) obj;
			equal = soilStatusID == that.soilStatusID;
		}

		return equal;
	}
}