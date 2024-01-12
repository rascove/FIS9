package my.edu.utem.ftmk.fis9.revenue.model;

import java.util.Date;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 */
public class Renew extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private long renewID;
	private String type;
	private long forestDevelopmentContractorID;
	private long loggingContractorID;
	private long licenseID;
	private long permitID;
	private long receiptID;
	private Date startDate;	
	private Date endDate;	
	private String status;

	public long getRenewID() {
		return renewID;
	}

	public void setRenewID(long renewID) {
		this.renewID = renewID;
	}

	public long getForestDevelopmentContractorID() {
		return forestDevelopmentContractorID;
	}

	public void setForestDevelopmentContractorID(long forestDevelopmentContractorID) {
		this.forestDevelopmentContractorID = forestDevelopmentContractorID;
	}

	public long getLoggingContractorID() {
		return loggingContractorID;
	}

	public void setLoggingContractorID(long loggingContractorID) {
		this.loggingContractorID = loggingContractorID;
	}

	public long getLicenseID() {
		return licenseID;
	}

	public void setLicenseID(long licenseID) {
		this.licenseID = licenseID;
	}

	public long getPermitID() {
		return permitID;
	}

	public void setPermitID(long permitID) {
		this.permitID = permitID;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public long getReceiptID() {
		return receiptID;
	}

	public void setReceiptID(long receiptID) {
		this.receiptID = receiptID;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

		if (obj instanceof Renew)
		{
			Renew that = (Renew) obj;
			equal = renewID == that.renewID;
		}

		return equal;
	}
}