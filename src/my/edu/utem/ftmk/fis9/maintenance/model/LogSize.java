package my.edu.utem.ftmk.fis9.maintenance.model;

import java.sql.Timestamp;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 */
public class LogSize extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int logSizeID;
	private int stateID;
	private String stateName;
	private double minBigSize;
	private double minSmallSize;
	private Timestamp startDate;
	private Timestamp endDate;
	private String status;

	public int getLogSizeID() 
	{
		return logSizeID;
	}

	public void setLogSizeID(int logSizeID) 
	{
		this.logSizeID = logSizeID;
	}

	public int getStateID() 
	{
		return stateID;
	}

	public void setStateID(int stateID) 
	{
		this.stateID = stateID;
	}

	public String getStateName() 
	{
		return stateName;
	}

	public void setStateName(String stateName) 
	{
		this.stateName = stateName;
	}

	public double getMinBigSize() {
		return minBigSize;
	}

	public void setMinBigSize(double minBigSize) {
		this.minBigSize = minBigSize;
	}

	public double getMinSmallSize() {
		return minSmallSize;
	}

	public void setMinSmallSize(double minSmallSize) {
		this.minSmallSize = minSmallSize;
	}

	public Timestamp getStartDate() 
	{
		return startDate;
	}

	public void setStartDate(Timestamp startDate) 
	{
		this.startDate = startDate;
	}

	public Timestamp getEndDate() 
	{
		return endDate;
	}

	public void setEndDate(Timestamp endDate) 
	{
		this.endDate = endDate;
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
		return stateName == null ? "Semua negeri" : stateName;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof LogSize)
		{
			LogSize that = (LogSize) obj;
			equal = logSizeID == that.logSizeID;
		}

		return equal;
	}
}