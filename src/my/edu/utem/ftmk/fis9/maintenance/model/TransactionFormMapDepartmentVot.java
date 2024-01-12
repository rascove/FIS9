package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 */
public class TransactionFormMapDepartmentVot extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int transactionFormMapDepartmentVotID;
	private int transactionFormID;
	private String transactionFormCode;
	private String transactionFormName;
	private int departmentVotID;
	private String departmentVotCode;
	private String departmentVotName;
	private int bursaryVotID;
	private String bursaryVotCode;
	private String bursaryVotName;	
	private String status;

	
	public int getTransactionFormMapDepartmentVotID() 
	{
		return transactionFormMapDepartmentVotID;
	}

	public void setTransactionFormMapDepartmentVotID(int transactionFormMapDepartmentVotID) 
	{
		this.transactionFormMapDepartmentVotID = transactionFormMapDepartmentVotID;
	}

	public int getTransactionFormID() 
	{
		return transactionFormID;
	}

	public void setTransactionFormID(int transactionFormID) 
	{
		this.transactionFormID = transactionFormID;
	}

	public String getTransactionFormCode() 
	{
		return transactionFormCode;
	}

	public void setTransactionFormCode(String transactionFormCode) 
	{
		this.transactionFormCode = transactionFormCode;
	}

	public String getTransactionFormName() 
	{
		return transactionFormName;
	}

	public void setTransactionFormName(String transactionFormName) 
	{
		this.transactionFormName = transactionFormName;
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
		return departmentVotCode+"-"+departmentVotName;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof TransactionFormMapDepartmentVot)
		{
			TransactionFormMapDepartmentVot that = (TransactionFormMapDepartmentVot) obj;
			equal = transactionFormMapDepartmentVotID == that.transactionFormMapDepartmentVotID;
		}

		return equal;
	}
}