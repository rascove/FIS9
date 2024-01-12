package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Zurina
 */
public class Silara extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int silaraID;
	private String code;
	private String name;

	public int getSilaraID()
	{
		return silaraID;
	}

	public void setSilaraID(int silaraID)
	{
		this.silaraID = silaraID;
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

		if (obj instanceof Silara)
		{
			Silara that = (Silara) obj;
			equal = silaraID == that.silaraID;
		}

		return equal;
	}
}