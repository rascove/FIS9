package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class Elevation extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int elevationID;
	private String code;
	private String name;

	public int getElevationID()
	{
		return elevationID;
	}

	public void setElevationID(int elevationID)
	{
		this.elevationID = elevationID;
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

		if (obj instanceof Elevation)
		{
			Elevation that = (Elevation) obj;
			equal = elevationID == that.elevationID;
		}

		return equal;
	}
}