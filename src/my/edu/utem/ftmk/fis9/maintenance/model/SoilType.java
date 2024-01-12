package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class SoilType extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int soilTypeID;
	private String code;
	private String name;

	public int getSoilTypeID()
	{
		return soilTypeID;
	}

	public void setSoilTypeID(int soilTypeID)
	{
		this.soilTypeID = soilTypeID;
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

		if (obj instanceof SoilType)
		{
			SoilType that = (SoilType) obj;
			equal = soilTypeID == that.soilTypeID;
		}

		return equal;
	}
}