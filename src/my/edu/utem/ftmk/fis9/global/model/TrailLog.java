package my.edu.utem.ftmk.fis9.global.model;

import java.util.Date;

public class TrailLog extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private Date logTime;
	private String operation;
	private String staffID;
	private String staffName;

	public Date getLogTime()
	{
		return logTime;
	}

	public void setLogTime(Date logTime)
	{
		this.logTime = logTime;
	}

	public String getOperation()
	{
		return operation;
	}

	public void setOperation(String operation)
	{
		this.operation = operation;
	}

	public String getStaffID()
	{
		return staffID;
	}

	public void setStaffID(String staffID)
	{
		this.staffID = staffID;
	}

	public String getStaffName()
	{
		return staffName;
	}

	public void setStaffName(String staffName)
	{
		this.staffName = staffName;
	}

	@Override
	public String toString()
	{
		return staffID + ": " + operation + " on " + logTime;
	}

	@Override
	public boolean equals(Object obj)
	{
		return toString().equals(obj.toString());
	}
}