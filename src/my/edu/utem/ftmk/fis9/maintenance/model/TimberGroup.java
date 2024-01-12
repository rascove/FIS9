package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class TimberGroup extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int timberGroupID;
	private String name;
	private String description;

	public int getTimberGroupID()
	{
		return timberGroupID;
	}

	public void setTimberGroupID(int timberGroupID)
	{
		this.timberGroupID = timberGroupID;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
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

		if (obj instanceof TimberGroup)
		{
			TimberGroup that = (TimberGroup) obj;
			equal = timberGroupID == that.timberGroupID;
		}

		return equal;
	}
}