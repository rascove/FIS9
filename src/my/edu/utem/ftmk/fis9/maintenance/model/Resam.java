package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class Resam extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int resamID;
	private String code;
	private String name;

	public int getResamID()
	{
		return resamID;
	}

	public void setResamID(int resamID)
	{
		this.resamID = resamID;
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

		if (obj instanceof Resam)
		{
			Resam that = (Resam) obj;
			equal = resamID == that.resamID;
		}

		return equal;
	}
}