package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class Dominance extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int dominanceID;
	private String code;
	private String name;

	public int getDominanceID()
	{
		return dominanceID;
	}

	public void setDominanceID(int dominanceID)
	{
		this.dominanceID = dominanceID;
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

		if (obj instanceof Dominance)
		{
			Dominance that = (Dominance) obj;
			equal = dominanceID == that.dominanceID;
		}

		return equal;
	}
}