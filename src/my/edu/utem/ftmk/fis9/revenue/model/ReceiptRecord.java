package my.edu.utem.ftmk.fis9.revenue.model;

import java.math.BigDecimal;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 */
public class ReceiptRecord extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private long receiptRecordID;
	private long receiptID;
	private int departmentVotID;
	private String departmentVotCode;
	private String departmentVotName;
	private int bursaryVotID;
	private String bursaryVotCode;
	private String bursaryVotName;
	private String description;
	private BigDecimal rate;
	private BigDecimal quantity;
	
	public long getReceiptRecordID() {
		return receiptRecordID;
	}

	public void setReceiptRecordID(long receiptRecordID) {
		this.receiptRecordID = receiptRecordID;
	}

	public long getReceiptID() 
	{
		return receiptID;
	}

	public void setReceiptID(long receiptID) 
	{
		this.receiptID = receiptID;
	}

	public int getDepartmentVotID() 
	{
		return departmentVotID;
	}

	public void setDepartmentVotID(int departmentVotID) 
	{
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
	
	public int getBursaryVotID() 
	{
		return bursaryVotID;
	}

	public void setBursaryVotID(int bursaryVotID) 
	{
		this.bursaryVotID = bursaryVotID;
	}

	public String getBursaryVotCode() {
		return bursaryVotCode;
	}

	public void setBursaryVotCode(String bursaryVotCode) {
		this.bursaryVotCode = bursaryVotCode;
	}

	public String getBursaryVotName() {
		return bursaryVotName;
	}

	public void setBursaryVotName(String bursaryVotName) {
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

	public BigDecimal getRate() 
	{
		return rate;
	}

	public void setRate(BigDecimal rate) 
	{
		this.rate = rate;
	}

	public BigDecimal getQuantity() 
	{
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) 
	{
		this.quantity = quantity;
	}

	public BigDecimal getTotal() 
	{
		return rate.multiply(quantity);
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

		if (obj instanceof ReceiptRecord)
		{
			ReceiptRecord that = (ReceiptRecord) obj;
			equal = receiptID == that.receiptID;
		}

		return equal;
	}
}