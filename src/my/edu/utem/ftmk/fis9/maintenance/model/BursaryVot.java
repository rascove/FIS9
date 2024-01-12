package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 */
public class BursaryVot extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int bursaryVotID;
	private String code;
	private String name;
	private String type;
	private String typeName;
	private String status;

	public int getBursaryVotID() 
	{
		return bursaryVotID;
	}

	public void setBursaryVotID(int bursaryVotID) 
	{
		this.bursaryVotID = bursaryVotID;
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

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getTypeName()
	{
		return typeName;
	}

	public void setTypeName(String typeName)
	{
		this.typeName = typeName;
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
		return code+"-"+name;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof BursaryVot)
		{
			BursaryVot that = (BursaryVot) obj;
			equal = bursaryVotID == that.bursaryVotID;
		}

		return equal;
	}
}