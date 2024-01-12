package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 */
public class TrustFund extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int trustFundID;
	private int departmentVotID;
	private String departmentVotCode;
	private String departmentVotName;
	private int bursaryVotID;
	private String bursaryVotCode;
	private String bursaryVotName;	
	private String description;	
	private String symbol;
	private String status;

	public int getTrustFundID() 
	{
		return trustFundID;
	}

	public void setTrustFundID(int trustFundID) 
	{
		this.trustFundID = trustFundID;
	}

	public int getDepartmentVotID() 
	{
		return departmentVotID;
	}

	public void setDepartmentVotID(int departmentVotID) 
	{
		this.departmentVotID = departmentVotID;
	}
	
	public String getDepartmentVotCode() 
	{
		return departmentVotCode;
	}

	public void setDepartmentVotCode(String departmentVotCode) 
	{
		this.departmentVotCode = departmentVotCode;
	}

	public String getDepartmentVotName() 
	{
		return departmentVotName;
	}

	public void setDepartmentVotName(String departmentVotName) 
	{
		this.departmentVotName = departmentVotName;
	}

	public int getBursaryVotID()
	{
		return bursaryVotID;
	}

	public void setBursaryVotID(int bursaryVotID)
	{
		this.bursaryVotID = bursaryVotID;
	}

	public String getBursaryVotCode()
	{
		return bursaryVotCode;
	}

	public void setBursaryVotCode(String bursaryVotCode)
	{
		this.bursaryVotCode = bursaryVotCode;
	}

	public String getBursaryVotName()
	{
		return bursaryVotName;
	}

	public void setBursaryVotName(String bursaryVotName)
	{
		this.bursaryVotName = bursaryVotName;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getSymbol()
	{
		return symbol;
	}

	public void setSymbol(String symbol)
	{
		this.symbol = symbol;
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
		return departmentVotName;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof TrustFund)
		{
			TrustFund that = (TrustFund) obj;
			equal = trustFundID == that.trustFundID;
		}

		return equal;
	}
}