package my.edu.utem.ftmk.fis9.revenue.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 */
public class ForestDevelopmentContractorSubWorkTypeRecord extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int forestDevelopmentContractorSubWorkTypeRecordID;
	private long forestDevelopmentContractorID;
	private int forestDevelopmentSubWorkTypeID;
	private String status;

	public int getForestDevelopmentContractorSubWorkTypeRecordID() {
		return forestDevelopmentContractorSubWorkTypeRecordID;
	}

	public void setForestDevelopmentContractorSubWorkTypeRecordID(int forestDevelopmentContractorSubWorkTypeRecordID) {
		this.forestDevelopmentContractorSubWorkTypeRecordID = forestDevelopmentContractorSubWorkTypeRecordID;
	}

	public long getForestDevelopmentContractorID() {
		return forestDevelopmentContractorID;
	}

	public void setForestDevelopmentContractorID(long forestDevelopmentContractorID) {
		this.forestDevelopmentContractorID = forestDevelopmentContractorID;
	}

	public int getForestDevelopmentSubWorkTypeID() {
		return forestDevelopmentSubWorkTypeID;
	}

	public void setForestDevelopmentSubWorkTypeID(int forestDevelopmentSubWorkTypeID) {
		this.forestDevelopmentSubWorkTypeID = forestDevelopmentSubWorkTypeID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString()
	{
		return "";
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof ForestDevelopmentContractorSubWorkTypeRecord)
		{
			ForestDevelopmentContractorSubWorkTypeRecord that = (ForestDevelopmentContractorSubWorkTypeRecord) obj;
			equal = forestDevelopmentContractorSubWorkTypeRecordID == that.forestDevelopmentContractorSubWorkTypeRecordID;
		}

		return equal;
	}
}