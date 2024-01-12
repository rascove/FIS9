package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class Geology extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int geologyID;
	private String code;
	private String name;

	public int getGeologyID()
	{
		return geologyID;
	}

	public void setGeologyID(int geologyID)
	{
		this.geologyID = geologyID;
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

		if (obj instanceof Geology)
		{
			Geology that = (Geology) obj;
			equal = geologyID == that.geologyID;
		}

		return equal;
	}
}