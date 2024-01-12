package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class AreaStatus extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int areaStatusID;
	private String code;
	private String name;

	public int getAreaStatusID()
	{
		return areaStatusID;
	}

	public void setAreaStatusID(int areaStatusID)
	{
		this.areaStatusID = areaStatusID;
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

		if (obj instanceof AreaStatus)
		{
			AreaStatus that = (AreaStatus) obj;
			equal = areaStatusID == that.areaStatusID;
		}

		return equal;
	}
}