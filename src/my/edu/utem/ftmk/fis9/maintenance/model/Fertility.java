package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class Fertility extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int fertilityID;
	private String code;
	private String name;

	public int getFertilityID()
	{
		return fertilityID;
	}

	public void setFertilityID(int fertilityID)
	{
		this.fertilityID = fertilityID;
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

		if (obj instanceof Fertility)
		{
			Fertility that = (Fertility) obj;
			equal = fertilityID == that.fertilityID;
		}

		return equal;
	}
}