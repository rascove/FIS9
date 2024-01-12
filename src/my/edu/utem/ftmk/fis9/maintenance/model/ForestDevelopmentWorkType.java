package my.edu.utem.ftmk.fis9.maintenance.model;

import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 */
public class ForestDevelopmentWorkType extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int forestDevelopmentWorkTypeID;
	private int headerNo;
	private String description;
	private ArrayList<ForestDevelopmentSubWorkType> forestDevelopmentSubWorkTypes;
	private String status;

	public int getForestDevelopmentWorkTypeID() {
		return forestDevelopmentWorkTypeID;
	}

	public void setForestDevelopmentWorkTypeID(int forestDevelopmentWorkTypeID) {
		this.forestDevelopmentWorkTypeID = forestDevelopmentWorkTypeID;
	}

	public int getHeaderNo() {
		return headerNo;
	}

	public void setHeaderNo(int headerNo) {
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

	public ArrayList<ForestDevelopmentSubWorkType> getForestDevelopmentSubWorkTypes() {
		return forestDevelopmentSubWorkTypes;
	}

	public void setForestDevelopmentSubWorkTypes(ArrayList<ForestDevelopmentSubWorkType> forestDevelopmentSubWorkTypes) {
		this.forestDevelopmentSubWorkTypes = forestDevelopmentSubWorkTypes;
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

		if (obj instanceof ForestDevelopmentWorkType)
		{
			ForestDevelopmentWorkType that = (ForestDevelopmentWorkType) obj;
			equal = forestDevelopmentWorkTypeID == that.forestDevelopmentWorkTypeID;
		}

		return equal;
	}
}