package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 */
public class DepartmentVot extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int departmentVotID;
	private int bursaryVotID;
	private String bursaryVotCode;
	private String bursaryVotName;
	private String code;
	private String name;
	private String status;

	public int getDepartmentVotID() 
	{
		return departmentVotID;
	}

	public void setDepartmentVotID(int departmentVotID) 
	{
		this.departmentVotID = departmentVotID;
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

		if (obj instanceof DepartmentVot)
		{
			DepartmentVot that = (DepartmentVot) obj;
			equal = departmentVotID == that.departmentVotID;
		}

		return equal;
	}
}