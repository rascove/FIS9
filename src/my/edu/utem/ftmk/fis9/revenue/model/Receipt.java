package my.edu.utem.ftmk.fis9.revenue.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 */
public class Receipt extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private long receiptID;
	private String receiptNo;
	private String receiptStatus;
	private int category;
	private long forestDevelopmentContractorID;
	private long loggingContractorID;
	private String loggingContractorRegistrationSerialNo;
	private String loggingContractorType;
	private long licenseID;
	private int licenseTypeID;
	private String licenseTypeCode;
	private String licenseTypeName;	
	private String licenseNo;
	private long permitID;
	private int permitTypeID;
	private String permitTypeCode;
	private String permitTypeName;
	private String permitNo;
	private String contractorName;
	private String companyName;
	private String companyRegistrationNo;
	private String address;
	private String telNo;
	private String faxNo;
	private String name;
	private Date date;
	private String notes;
	private String collectorStatement;
	private int paymentTypeID;
	private String paymentTypeCode;
	private String paymentTypeName;
	private int bankID;
	private String bankCode;
	private String bankName;
	private int chequeTypeID;
	private String chequeTypeCode;
	private String chequeTypeName;	
	private String chequeNo;
	private Date chequeDate;
	private BigDecimal grandTotal;
	private String recorderID;
	private String recorderName;
	private Timestamp recordTime;
	private ArrayList<ReceiptRecord> receiptRecords;
	private String status;

	public long getReceiptID() {
		return receiptID;
	}

	public void setReceiptID(long receiptID) {
		this.receiptID = receiptID;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getReceiptStatus() {
		return receiptStatus;
	}

	public void setReceiptStatus(String receiptStatus) {
		this.receiptStatus = receiptStatus;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
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

	public String getLoggingContractorRegistrationSerialNo() {
		return loggingContractorRegistrationSerialNo;
	}

	public void setLoggingContractorRegistrationSerialNo(String loggingContractorRegistrationSerialNo) {
		this.loggingContractorRegistrationSerialNo = loggingContractorRegistrationSerialNo;
	}

	public String getLoggingContractorType() {
		return loggingContractorType;
	}

	public void setLoggingContractorType(String loggingContractorType) {
		this.loggingContractorType = loggingContractorType;
	}

	public long getLicenseID() {
		return licenseID;
	}

	public void setLicenseID(long licenseID) {
		this.licenseID = licenseID;
	}

	public int getLicenseTypeID() {
		return licenseTypeID;
	}

	public void setLicenseTypeID(int licenseTypeID) {
		this.licenseTypeID = licenseTypeID;
	}

	public String getLicenseTypeCode() {
		return licenseTypeCode;
	}

	public void setLicenseTypeCode(String licenseTypeCode) {
		this.licenseTypeCode = licenseTypeCode;
	}

	public String getLicenseTypeName() {
		return licenseTypeName;
	}

	public void setLicenseTypeName(String licenseTypeName) {
		this.licenseTypeName = licenseTypeName;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public long getPermitID() {
		return permitID;
	}

	public void setPermitID(long permitID) {
		this.permitID = permitID;
	}

	public int getPermitTypeID() {
		return permitTypeID;
	}

	public void setPermitTypeID(int permitTypeID) {
		this.permitTypeID = permitTypeID;
	}

	public String getPermitTypeCode() {
		return permitTypeCode;
	}

	public void setPermitTypeCode(String permitTypeCode) {
		this.permitTypeCode = permitTypeCode;
	}

	public String getPermitTypeName() {
		return permitTypeName;
	}

	public void setPermitTypeName(String permitTypeName) {
		this.permitTypeName = permitTypeName;
	}

	public String getPermitNo() {
		return permitNo;
	}

	public void setPermitNo(String permitNo) {
		this.permitNo = permitNo;
	}

	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyRegistrationNo() {
		return companyRegistrationNo;
	}

	public void setCompanyRegistrationNo(String companyRegistrationNo) {
		this.companyRegistrationNo = companyRegistrationNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getFaxNo() {
		return faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public String getCollectorStatement() {
		return collectorStatement;
	}

	public void setCollectorStatement(String collectorStatement) {
		this.collectorStatement = collectorStatement;
	}

	public int getPaymentTypeID() {
		return paymentTypeID;
	}

	public void setPaymentTypeID(int paymentTypeID) {
		this.paymentTypeID = paymentTypeID;
	}

	public String getPaymentTypeCode() {
		return paymentTypeCode;
	}

	public void setPaymentTypeCode(String paymentTypeCode) {
		this.paymentTypeCode = paymentTypeCode;
	}

	public String getPaymentTypeName() {
		return paymentTypeName;
	}

	public void setPaymentTypeName(String paymentTypeName) {
		this.paymentTypeName = paymentTypeName;
	}

	public int getBankID() {
		return bankID;
	}

	public void setBankID(int bankID) {
		this.bankID = bankID;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public int getChequeTypeID() {
		return chequeTypeID;
	}

	public void setChequeTypeID(int chequeTypeID) {
		this.chequeTypeID = chequeTypeID;
	}

	public String getChequeTypeCode() {
		return chequeTypeCode;
	}

	public void setChequeTypeCode(String chequeTypeCode) {
		this.chequeTypeCode = chequeTypeCode;
	}

	public String getChequeTypeName() {
		return chequeTypeName;
	}

	public void setChequeTypeName(String chequeTypeName) {
		this.chequeTypeName = chequeTypeName;
	}

	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	public Date getChequeDate()
	{
		return chequeDate;
	}

	public void setChequeDate(Date chequeDate)
	{
		this.chequeDate = chequeDate;
	}

	public BigDecimal getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(BigDecimal grandTotal) {
		this.grandTotal = grandTotal;
	}

	public String getRecorderID() {
		return recorderID;
	}

	public void setRecorderID(String recorderID) {
		this.recorderID = recorderID;
	}

	public String getRecorderName() {
		return recorderName;
	}

	public void setRecorderName(String recorderName) {
		this.recorderName = recorderName;
	}

	public Timestamp getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Timestamp recordTime) {
		this.recordTime = recordTime;
	}

	public ArrayList<ReceiptRecord> getReceiptRecords() {
		return receiptRecords;
	}

	public void setReceiptRecords(ArrayList<ReceiptRecord> receiptRecords) {
		this.receiptRecords = receiptRecords;
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
		return receiptNo;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof Receipt)
		{
			Receipt that = (Receipt) obj;
			equal = receiptID == that.receiptID;
		}

		return equal;
	}
}