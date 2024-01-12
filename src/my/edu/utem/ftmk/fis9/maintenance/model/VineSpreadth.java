package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class VineSpreadth extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int vineSpreadthID;
	private String code;
	private String name;

	public int getVineSpreadthID()
	{
		return vineSpreadthID;
	}

	public void setVineSpreadthID(int vineSpreadthID)
	{
		this.vineSpreadthID = vineSpreadthID;
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

		if (obj instanceof VineSpreadth)
		{
			VineSpreadth that = (VineSpreadth) obj;
			equal = vineSpreadthID == that.vineSpreadthID;
		}

		return equal;
	}
}