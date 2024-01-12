package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 */
public class ForestDevelopmentSubWorkType extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int forestDevelopmentSubWorkTypeID;
	private int forestDevelopmentWorkTypeID;
	private int forestDevelopmentWorkTypeHeaderNo;
	private String forestDevelopmentWorkTypeDescription;
	private String headerNo;
	private String description;
	private String status;

	public int getForestDevelopmentSubWorkTypeID() {
		return forestDevelopmentSubWorkTypeID;
	}

	public void setForestDevelopmentSubWorkTypeID(int forestDevelopmentSubWorkTypeID) {
		this.forestDevelopmentSubWorkTypeID = forestDevelopmentSubWorkTypeID;
	}

	public int getForestDevelopmentWorkTypeID() {
		return forestDevelopmentWorkTypeID;
	}

	public void setForestDevelopmentWorkTypeID(int forestDevelopmentWorkTypeID) {
		this.forestDevelopmentWorkTypeID = forestDevelopmentWorkTypeID;
	}

	public int getForestDevelopmentWorkTypeHeaderNo() {
		return forestDevelopmentWorkTypeHeaderNo;
	}

	public void setForestDevelopmentWorkTypeHeaderNo(int forestDevelopmentWorkTypeHeaderNo) {
		this.forestDevelopmentWorkTypeHeaderNo = forestDevelopmentWorkTypeHeaderNo;
	}

	public String getForestDevelopmentWorkTypeDescription() {
		return forestDevelopmentWorkTypeDescription;
	}

	public void setForestDevelopmentWorkTypeDescription(String forestDevelopmentWorkTypeDescription) {
		this.forestDevelopmentWorkTypeDescription = forestDevelopmentWorkTypeDescription;
	}

	public String getHeaderNo() {
		return headerNo;
	}

	public void setHeaderNo(String headerNo) {
		this.headerNo = headerNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		return description;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof ForestDevelopmentSubWorkType)
		{
			ForestDevelopmentSubWorkType that = (ForestDevelopmentSubWorkType) obj;
			equal = forestDevelopmentSubWorkTypeID == that.forestDevelopmentSubWorkTypeID;
		}

		return equal;
	}
}