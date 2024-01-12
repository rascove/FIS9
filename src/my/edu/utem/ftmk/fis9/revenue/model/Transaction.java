package my.edu.utem.ftmk.fis9.revenue.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 */
public class Transaction extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private long transactionID;
	private String transactionType;
	private long transactionFormID;
	private int departmentVotID;
	private String departmentVotName;
	private String departmentVotCode;
	private BigDecimal value;
	private Timestamp recordTime;

	public long getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(long transactionID) {
		this.transactionID = transactionID;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public long getTransactionFormID() {
		return transactionFormID;
	}

	public void setTransactionFormID(long transactionFormID) {
		this.transactionFormID = transactionFormID;
	}

	public int getDepartmentVotID() {
		return departmentVotID;
	}

	public void setDepartmentVotID(int departmentVotID) {
		this.departmentVotID = departmentVotID;
	}

	public String getDepartmentVotName() {
		return departmentVotName;
	}

	public void setDepartmentVotName(String departmentVotName) {
		this.departmentVotName = departmentVotName;
	}

	public String getDepartmentVotCode() {
		return departmentVotCode;
	}

	public void setDepartmentVotCode(String departmentVotCode) {
		this.departmentVotCode = departmentVotCode;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Timestamp getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Timestamp recordTime) {
		this.recordTime = recordTime;
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

		if (obj instanceof Transaction)
		{
			Transaction that = (Transaction) obj;
			equal = transactionID == that.transactionID;
		}

		return equal;
	}
}