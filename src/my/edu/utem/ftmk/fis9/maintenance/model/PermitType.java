package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 */
public class PermitType extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int permitTypeID;
	private String code;
	private String name;
	private String status;

	public int getPermitTypeID() 
	{
		return permitTypeID;
	}

	public void setPermitTypeID(int permitTypeID) 
	{
		this.permitTypeID = permitTypeID;
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

	public String getStatus() 
	{
		return status;
	}

	public void setStatus(String status) 
	{
		this.status = status;
	}

	@Override
	public String toString()
	{
		return name;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof PermitType)
		{
			PermitType that = (PermitType) obj;
			equal = permitTypeID == that.permitTypeID;
		}

		return equal;
	}
}