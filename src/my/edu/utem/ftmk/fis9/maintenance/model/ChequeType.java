package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 */
public class ChequeType extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int chequeTypeID;
	private String code;
	private String name;
	private String status;

	public int getChequeTypeID() 
	{
		return chequeTypeID;
	}

	public void setChequeTypeID(int chequeTypeID) 
	{
		this.chequeTypeID = chequeTypeID;
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
		return code+"-"+name;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof ChequeType)
		{
			ChequeType that = (ChequeType) obj;
			equal = chequeTypeID == that.chequeTypeID;
		}

		return equal;
	}
}