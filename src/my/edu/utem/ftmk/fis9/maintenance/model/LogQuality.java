package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class LogQuality extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int logQualityID;
	private String code;
	private String name;

	public int getLogQualityID()
	{
		return logQualityID;
	}

	public void setLogQualityID(int logQualityID)
	{
		this.logQualityID = logQualityID;
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

		if (obj instanceof LogQuality)
		{
			LogQuality that = (LogQuality) obj;
			equal = logQualityID == that.logQualityID;
		}

		return equal;
	}
}