package my.edu.utem.ftmk.fis9.revenue.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 */
public class ForestDevelopmentContractor extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private long forestDevelopmentContractorID;
	private int stateID;
	private String stateName;
	private long receiptID;
	private String receiptNo;
	private String registrationNo;
	private String name;
	private String icNo;
	private String companyName;
	private String address;
	private String registeredAddress;
	private String telNo;	
	private String hpNo;	
	private String registeredBusinessNo;
	private int licenseStatusID;
	private String licenseStatusCode;
	private String licenseStatusName;
	private Date startDate;
	private Date endDate;
	private Date registrationDate;
	private String contractorServiceCenterTitle;
	private String pkkRegistrationCertificateNo;
	private String cidbRegistrationNo;
	private int managementStaffNo;
	private int supervisionStaffNo;
	private int skilledStaffNo;
	private int unSkilledStaffNo;
	private int othersStaffNo;
	private String machineryDescription;
	private String previousExperience;
	private String recorderID;
	private String recorderName;	
	private Timestamp recordTime;
	private ArrayList<ForestDevelopmentContractorSubWorkTypeRecord> forestDevelopmentContractorSubWorkTypeRecords;
	private ArrayList<Renew> renews;
	private String status;	

	public long getForestDevelopmentContractorID() {
		return forestDevelopmentContractorID;
	}

	public void setForestDevelopmentContractorID(long forestDevelopmentContractorID) {
		this.forestDevelopmentContractorID = forestDevelopmentContractorID;
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
	
	public String getRegistrationNo() {
		return registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
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

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getHpNo() {
		return hpNo;
	}

	public void setHpNo(String hpNo) {
		this.hpNo = hpNo;
	}

	public String getRegisteredBusinessNo() {
		return registeredBusinessNo;
	}

	public void setRegisteredBusinessNo(String registeredBusinessNo) {
		this.registeredBusinessNo = registeredBusinessNo;
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

	public String getContractorServiceCenterTitle() {
		return contractorServiceCenterTitle;
	}

	public void setContractorServiceCenterTitle(String contractorServiceCenterTitle) {
		this.contractorServiceCenterTitle = contractorServiceCenterTitle;
	}

	public String getPkkRegistrationCertificateNo() {
		return pkkRegistrationCertificateNo;
	}

	public void setPkkRegistrationCertificateNo(String pkkRegistrationCertificateNo) {
		this.pkkRegistrationCertificateNo = pkkRegistrationCertificateNo;
	}

	public String getCidbRegistrationNo() {
		return cidbRegistrationNo;
	}

	public void setCidbRegistrationNo(String cidbRegistrationNo) {
		this.cidbRegistrationNo = cidbRegistrationNo;
	}

	public int getManagementStaffNo() {
		return managementStaffNo;
	}

	public void setManagementStaffNo(int managementStaffNo) {
		this.managementStaffNo = managementStaffNo;
	}

	public int getSupervisionStaffNo() {
		return supervisionStaffNo;
	}

	public void setSupervisionStaffNo(int supervisionStaffNo) {
		this.supervisionStaffNo = supervisionStaffNo;
	}

	public int getSkilledStaffNo() {
		return skilledStaffNo;
	}

	public void setSkilledStaffNo(int skilledStaffNo) {
		this.skilledStaffNo = skilledStaffNo;
	}

	public int getUnSkilledStaffNo() {
		return unSkilledStaffNo;
	}

	public void setUnSkilledStaffNo(int unSkilledStaffNo) {
		this.unSkilledStaffNo = unSkilledStaffNo;
	}

	public int getOthersStaffNo() {
		return othersStaffNo;
	}

	public void setOthersStaffNo(int othersStaffNo) {
		this.othersStaffNo = othersStaffNo;
	}

	public String getMachineryDescription() {
		return machineryDescription;
	}

	public void setMachineryDescription(String machineryDescription) {
		this.machineryDescription = machineryDescription;
	}

	public String getPreviousExperience() {
		return previousExperience;
	}

	public void setPreviousExperience(String previousExperience) {
		this.previousExperience = previousExperience;
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

	public ArrayList<ForestDevelopmentContractorSubWorkTypeRecord> getForestDevelopmentContractorSubWorkTypeRecords() {
		return forestDevelopmentContractorSubWorkTypeRecords;
	}

	public void setForestDevelopmentContractorSubWorkTypeRecords(ArrayList<ForestDevelopmentContractorSubWorkTypeRecord> forestDevelopmentContractorSubWorkTypeRecords) {
		this.forestDevelopmentContractorSubWorkTypeRecords = forestDevelopmentContractorSubWorkTypeRecords;
	}

	public ArrayList<Renew> getRenews() {
		return renews;
	}

	public void setRenews(ArrayList<Renew> renews) {
		this.renews = renews;
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
		return registrationNo+" - "+companyName;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof ForestDevelopmentContractor)
		{
			ForestDevelopmentContractor that = (ForestDevelopmentContractor) obj;
			equal = forestDevelopmentContractorID == that.forestDevelopmentContractorID;
		}

		return equal;
	}
}