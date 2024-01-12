package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class Hall extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private long hallID;
	private String name;
	private boolean status;
	private int districtID;

	public long getHallID()
	{
		return hallID;
	}

	public void setHallID(long hallID)
	{
		this.hallID = hallID;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public boolean isStatus()
	{
		return status;
	}

	public void setStatus(boolean status)
	{
		this.status = status;
	}

	public int getDistrictID()
	{
		return districtID;
	}

	public void setDistrictID(int districtID)
	{
		this.districtID = districtID;
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

		if (obj instanceof Hall)
		{
			Hall that = (Hall) obj;
			equal = hallID == that.hallID;
		}

		return equal;
	}
}