package my.edu.utem.ftmk.fis9.revenue.model;

import java.math.BigDecimal;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 */
public class VoucherRecord extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private long voucherRecordID;
	private long voucherID;
	private int trustFundID;
	private int trustFundDepartmentVotID;
	private String trustFundDepartmentVotCode;
	private String trustFundDepartmentVotName;
	private int trustFundBursaryVotID;
	private String trustFundBursaryVotCode;
	private String trustFundBursaryVotName;	
	private String trustFundDescription;
	private String description;
	private BigDecimal total;

	public long getVoucherRecordID() {
		return voucherRecordID;
	}

	public void setVoucherRecordID(long voucherRecordID) {
		this.voucherRecordID = voucherRecordID;
	}

	public long getVoucherID() {
		return voucherID;
	}

	public void setVoucherID(long voucherID) {
		this.voucherID = voucherID;
	}

	public int getTrustFundID() {
		return trustFundID;
	}

	public void setTrustFundID(int trustFundID) {
		this.trustFundID = trustFundID;
	}

	public int getTrustFundDepartmentVotID()
	{
		return trustFundDepartmentVotID;
	}

	public void setTrustFundDepartmentVotID(int trustFundDepartmentVotID)
	{
		this.trustFundDepartmentVotID = trustFundDepartmentVotID;
	}

	public String getTrustFundDepartmentVotCode()
	{
		return trustFundDepartmentVotCode;
	}

	public void setTrustFundDepartmentVotCode(String trustFundDepartmentVotCode)
	{
		this.trustFundDepartmentVotCode = trustFundDepartmentVotCode;
	}

	public String getTrustFundDepartmentVotName()
	{
		return trustFundDepartmentVotName;
	}

	public void setTrustFundDepartmentVotName(String trustFundDepartmentVotName)
	{
		this.trustFundDepartmentVotName = trustFundDepartmentVotName;
	}

	public int getTrustFundBursaryVotID()
	{
		return trustFundBursaryVotID;
	}

	public void setTrustFundBursaryVotID(int trustFundBursaryVotID)
	{
		this.trustFundBursaryVotID = trustFundBursaryVotID;
	}

	public String getTrustFundBursaryVotCode()
	{
		return trustFundBursaryVotCode;
	}

	public void setTrustFundBursaryVotCode(String trustFundBursaryVotCode)
	{
		this.trustFundBursaryVotCode = trustFundBursaryVotCode;
	}

	public String getTrustFundBursaryVotName()
	{
		return trustFundBursaryVotName;
	}

	public void setTrustFundBursaryVotName(String trustFundBursaryVotName)
	{
		this.trustFundBursaryVotName = trustFundBursaryVotName;
	}

	public String getTrustFundDescription()
	{
		return trustFundDescription;
	}

	public void setTrustFundDescription(String trustFundDescription)
	{
		this.trustFundDescription = trustFundDescription;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	@Override
	public String toString()
	{
		return trustFundDepartmentVotCode+" - "+trustFundDepartmentVotName;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof VoucherRecord)
		{
			VoucherRecord that = (VoucherRecord) obj;
			equal = voucherRecordID == that.voucherRecordID;
		}

		return equal;
	}
}