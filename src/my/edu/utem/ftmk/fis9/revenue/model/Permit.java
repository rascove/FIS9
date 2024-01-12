package my.edu.utem.ftmk.fis9.revenue.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 */
public class Permit extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private long permitID;
	private long receiptID;
	private String receiptNo;
	private int permitTypeID;
	private String permitTypeCode;
	private String permitTypeName;
	private String permitNo;
	private String fileReferencePHN;
	private String fileReferencePHD;
	private long licenseID;
	private String licenseNo;
	private long licenseeID;
	private String licenseeNo;
	private String licenseeName;
	private String licenseeCompanyName;
	private String licenseeAddress;
	private String licenseeTelNo;
	private long contractorID;
	private String contractorNo;
	private String contractorName;
	private String contractorCompanyName;	
	private String contractorAddress;
	private String contractorTelNo;
	private int forestID;
	private String forestCode;
	private String forestName;
	private int districtID;
	private String districtCode;
	private String districtName;
	private int stateID;
	private String stateCode;
	private String stateName;
	private String compartmentNo;
	private Date startDate;
	private Date endDate;
	private Date registrationDate;
	private String referenceNo;	
	private String recorderID;
	private String recorderName;
	private String recorderMyKad;	
	private Timestamp recordTime;
	private ArrayList<Renew> renews;
	private BigDecimal permitFund;
	private String status;	

	public long getPermitID() {
		return permitID;
	}

	public void setPermitID(long permitID) {
		this.permitID = permitID;
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

	public String getFileReferencePHN() {
		return fileReferencePHN;
	}

	public void setFileReferencePHN(String fileReferencePHN) {
		this.fileReferencePHN = fileReferencePHN;
	}

	public String getFileReferencePHD() {
		return fileReferencePHD;
	}

	public void setFileReferencePHD(String fileReferencePHD) {
		this.fileReferencePHD = fileReferencePHD;
	}

	public long getLicenseID()
	{
		return licenseID;
	}

	public void setLicenseID(long licenseID)
	{
		this.licenseID = licenseID;
	}

	public String getLicenseNo()
	{
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo)
	{
		this.licenseNo = licenseNo;
	}

	public long getLicenseeID() {
		return licenseeID;
	}

	public void setLicenseeID(long licenseeID) {
		this.licenseeID = licenseeID;
	}

	public String getLicenseeNo() {
		return licenseeNo;
	}

	public void setLicenseeNo(String licenseeNo) {
		this.licenseeNo = licenseeNo;
	}

	public String getLicenseeName() {
		return licenseeName;
	}

	public void setLicenseeName(String licenseeName) {
		this.licenseeName = licenseeName;
	}

	public String getLicenseeCompanyName() {
		return licenseeCompanyName;
	}

	public void setLicenseeCompanyName(String licenseeCompanyName) {
		this.licenseeCompanyName = licenseeCompanyName;
	}

	public String getLicenseeAddress() {
		return licenseeAddress;
	}

	public void setLicenseeAddress(String licenseeAddress) {
		this.licenseeAddress = licenseeAddress;
	}

	public String getLicenseeTelNo() {
		return licenseeTelNo;
	}

	public void setLicenseeTelNo(String licenseeTelNo) {
		this.licenseeTelNo = licenseeTelNo;
	}

	public long getContractorID() {
		return contractorID;
	}

	public void setContractorID(long contractorID) {
		this.contractorID = contractorID;
	}

	public String getContractorNo() {
		return contractorNo;
	}

	public void setContractorNo(String contractorNo) {
		this.contractorNo = contractorNo;
	}

	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public String getContractorCompanyName() {
		return contractorCompanyName;
	}

	public void setContractorCompanyName(String contractorCompanyName) {
		this.contractorCompanyName = contractorCompanyName;
	}

	public String getContractorAddress() {
		return contractorAddress;
	}

	public void setContractorAddress(String contractorAddress) {
		this.contractorAddress = contractorAddress;
	}

	public String getContractorTelNo() {
		return contractorTelNo;
	}

	public void setContractorTelNo(String contractorTelNo) {
		this.contractorTelNo = contractorTelNo;
	}

	public int getForestID() {
		return forestID;
	}

	public void setForestID(int forestID) {
		this.forestID = forestID;
	}

	public String getForestCode() {
		return forestCode;
	}

	public void setForestCode(String forestCode) {
		this.forestCode = forestCode;
	}

	public String getForestName() {
		return forestName;
	}

	public void setForestName(String forestName) {
		this.forestName = forestName;
	}

	public int getDistrictID() {
		return districtID;
	}

	public void setDistrictID(int districtID) {
		this.districtID = districtID;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public int getStateID() {
		return stateID;
	}

	public void setStateID(int stateID) {
		this.stateID = stateID;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCompartmentNo() {
		return compartmentNo;
	}

	public void setCompartmentNo(String compartmentNo) {
		this.compartmentNo = compartmentNo;
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

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
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

	public String getRecorderMyKad() {
		return recorderMyKad;
	}

	public void setRecorderMyKad(String recorderMyKad) {
		this.recorderMyKad = recorderMyKad;
	}

	public Timestamp getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Timestamp recordTime) {
		this.recordTime = recordTime;
	}

	public ArrayList<Renew> getRenews() {
		return renews;
	}

	public void setRenews(ArrayList<Renew> renews) {
		this.renews = renews;
	}

	public BigDecimal getPermitFund()
	{
		return permitFund;
	}

	public void setPermitFund(BigDecimal permitFund)
	{
		this.permitFund = permitFund;
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
		return permitNo+"-"+licenseeCompanyName;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof Permit)
		{
			Permit that = (Permit) obj;
			equal = permitID == that.permitID;
		}

		return equal;
	}
}