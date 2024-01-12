package my.edu.utem.ftmk.fis9.revenue.model;

import java.math.BigDecimal;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 */
public class Revenue extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int departmentVotID;
	private String departmentVotCode;
	private String departmentVotName;
	private BigDecimal value;

	public int getDepartmentVotID() {
		return departmentVotID;
	}

	public void setDepartmentVotID(int departmentVotID) {
		this.departmentVotID = departmentVotID;
	}

	public String getDepartmentVotCode() {
		return departmentVotCode;
	}

	public void setDepartmentVotCode(String departmentVotCode) {
		this.departmentVotCode = departmentVotCode;
	}

	public String getDepartmentVotName() {
		return departmentVotName;
	}

	public void setDepartmentVotName(String departmentVotName) {
		this.departmentVotName = departmentVotName;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@Override
	public String toString()
	{
		return departmentVotCode + " - " + departmentVotName;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof Revenue)
		{
			Revenue that = (Revenue) obj;
			equal = departmentVotID == that.departmentVotID;
		}

		return equal;
	}
}