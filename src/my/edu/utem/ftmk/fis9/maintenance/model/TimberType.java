package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class TimberType extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int timberTypeID;
	private String code;
	private String name;

	public int getTimberTypeID()
	{
		return timberTypeID;
	}

	public void setTimberTypeID(int timberTypeID)
	{
		this.timberTypeID = timberTypeID;
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

		if (obj instanceof TimberType)
		{
			TimberType that = (TimberType) obj;
			equal = timberTypeID == that.timberTypeID;
		}

		return equal;
	}
}