package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class Aspect extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int aspectID;
	private String code;
	private String name;

	public int getAspectID()
	{
		return aspectID;
	}

	public void setAspectID(int aspectID)
	{
		this.aspectID = aspectID;
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

		if (obj instanceof Aspect)
		{
			Aspect that = (Aspect) obj;
			equal = aspectID == that.aspectID;
		}

		return equal;
	}
}