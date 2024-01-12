package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Zurina
 */
public class Ginger extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int gingerID;
	private String code;
	private String name;

	public int getGingerID()
	{
		return gingerID;
	}

	public void setGingerID(int gingerID)
	{
		this.gingerID = gingerID;
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

		if (obj instanceof Ginger)
		{
			Ginger that = (Ginger) obj;
			equal = gingerID == that.gingerID;
		}

		return equal;
	}
}