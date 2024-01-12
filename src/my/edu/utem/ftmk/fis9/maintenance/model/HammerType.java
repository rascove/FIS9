package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class HammerType extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int hammerTypeID;
	private String name;

	public int getHammerTypeID()
	{
		return hammerTypeID;
	}

	public void setHammerTypeID(int hammerTypeID)
	{
		this.hammerTypeID = hammerTypeID;
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
		return name;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof HammerType)
		{
			HammerType that = (HammerType) obj;
			equal = hammerTypeID == that.hammerTypeID;
		}

		return equal;
	}
}