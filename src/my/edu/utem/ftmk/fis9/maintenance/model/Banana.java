package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Zurina
 */
public class Banana extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int bananaID;
	private String code;
	private String name;

	public int getBananaID()
	{
		return bananaID;
	}

	public void setBananaID(int bananaID)
	{
		this.bananaID = bananaID;
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

		if (obj instanceof Banana)
		{
			Banana that = (Banana) obj;
			equal = bananaID == that.bananaID;
		}

		return equal;
	}
}