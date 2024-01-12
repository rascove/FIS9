package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class ForestType extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int forestTypeID;
	private String code;
	private String name;

	public int getForestTypeID()
	{
		return forestTypeID;
	}

	public void setForestTypeID(int forestTypeID)
	{
		this.forestTypeID = forestTypeID;
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

		if (obj instanceof ForestType)
		{
			ForestType that = (ForestType) obj;
			equal = forestTypeID == that.forestTypeID;
		}

		return equal;
	}
}