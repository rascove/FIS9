package my.edu.utem.ftmk.fis9.revenue.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 */
public class LoggingContractor extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private long loggingContractorID;
	private long receiptID;
	private String receiptNo;
	private String receiptStatus;
	private String registrationSerialNo;
	private String type;
	private String name;
	private String icNo;
	private String companyName;
	private String address;
	private String registeredAddress;
	private String businessRegistrationNo;
	private String telNo;	
	private int licenseStatusID;
	private String licenseStatusCode;
	private String licenseStatusName;
	private Date startDate;
	private Date endDate;
	private Date registrationDate;
	private int managementStaffNo;
	private int clericalStaffNo;
	private int othersStaffNo;
	private int jcbNo;
	private int penyanggaNo;
	private int penggeredNo;
	private int lorryNo;
	private String previousLicensePermitNo;
	private BigDecimal area;
	private int stateID;
	private String stateName;
	private String recorderID;
	private String recorderName;	
	private Timestamp recordTime;
	private ArrayList<Renew> renews;
	private String status;	

	public long getLoggingContractorID() {
		return loggingContractorID;
	}

	public void setLoggingContractorID(long loggingContractorID) {
		this.loggingContractorID = loggingContractorID;
	}

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

	public String getRegistrationSerialNo() {
		return registrationSerialNo;
	}

	public void setRegistrationSerialNo(String registrationSerialNo) {
		this.registrationSerialNo = registrationSerialNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcNo() {
		return icNo;
	}

	public void setIcNo(String icNo) {
		this.icNo = icNo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRegisteredAddress() {
		return registeredAddress;
	}

	public void setRegisteredAddress(String registeredAddress) {
		this.registeredAddress = registeredAddress;
	}

	public String getBusinessRegistrationNo() {
		return businessRegistrationNo;
	}

	public void setBusinessRegistrationNo(String businessRegistrationNo) {
		this.businessRegistrationNo = businessRegistrationNo;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public int getLicenseStatusID() {
		return licenseStatusID;
	}

	public void setLicenseStatusID(int licenseStatusID) {
		this.licenseStatusID = licenseStatusID;
	}

	public String getLicenseStatusCode() {
		return licenseStatusCode;
	}

	public void setLicenseStatusCode(String licenseStatusCode) {
		this.licenseStatusCode = licenseStatusCode;
	}

	public String getLicenseStatusName() {
		return licenseStatusName;
	}

	public void setLicenseStatusName(String licenseStatusName) {
		this.licenseStatusName = licenseStatusName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public int getManagementStaffNo() {
		return managementStaffNo;
	}

	public void setManagementStaffNo(int managementStaffNo) {
		this.managementStaffNo = managementStaffNo;
	}

	public int getClericalStaffNo() {
		return clericalStaffNo;
	}

	public void setClericalStaffNo(int clericalStaffNo) {
		this.clericalStaffNo = clericalStaffNo;
	}

	public int getOthersStaffNo() {
		return othersStaffNo;
	}

	public void setOthersStaffNo(int othersStaffNo) {
		this.othersStaffNo = othersStaffNo;
	}

	public int getJcbNo() {
		return jcbNo;
	}

	public void setJcbNo(int jcbNo) {
		this.jcbNo = jcbNo;
	}

	public int getPenyanggaNo() {
		return penyanggaNo;
	}

	public void setPenyanggaNo(int penyanggaNo) {
		this.penyanggaNo = penyanggaNo;
	}

	public int getPenggeredNo() {
		return penggeredNo;
	}

	public void setPenggeredNo(int penggeredNo) {
		this.penggeredNo = penggeredNo;
	}

	public int getLorryNo() {
		return lorryNo;
	}

	public void setLorryNo(int lorryNo) {
		this.lorryNo = lorryNo;
	}

	public String getPreviousLicensePermitNo() {
		return previousLicensePermitNo;
	}

	public void setPreviousLicensePermitNo(String previousLicensePermitNo) {
		this.previousLicensePermitNo = previousLicensePermitNo;
	}

	public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public int getStateID() {
		return stateID;
	}

	public void setStateID(int stateID) {
		this.stateID = stateID;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<Renew> getRenews() {
		return renews;
	}

	public void setRenews(ArrayList<Renew> renews) {
		this.renews = renews;
	}

	@Override
	public String toString()
	{
		return registrationSerialNo+" - "+companyName;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof LoggingContractor)
		{
			LoggingContractor that = (LoggingContractor) obj;
			equal = loggingContractorID == that.loggingContractorID;
		}

		return equal;
	}
}