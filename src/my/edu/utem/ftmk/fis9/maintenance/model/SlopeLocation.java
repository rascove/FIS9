package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class SlopeLocation extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int slopeLocationID;
	private String code;
	private String name;

	public int getSlopeLocationID()
	{
		return slopeLocationID;
	}

	public void setSlopeLocationID(int slopeLocationID)
	{
		this.slopeLocationID = slopeLocationID;
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

		if (obj instanceof SlopeLocation)
		{
			SlopeLocation that = (SlopeLocation) obj;
			equal = slopeLocationID == that.slopeLocationID;
		}

		return equal;
	}
}