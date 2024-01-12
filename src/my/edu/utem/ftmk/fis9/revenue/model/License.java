package my.edu.utem.ftmk.fis9.revenue.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 */
public class License extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private long licenseID;
	private long receiptID;
	private String receiptNo;
	private String licenseNo;
	private String fileReferencePHN;
	private String fileReferencePHD;
	private long licenseeID;
	private String licenseeNo;
	private String licenseeName;
	private String licenseeCompanyName;
	private long contractorID;
	private String contractorNo;
	private String contractorName;
	private String contractorCompanyName;	
	private String address;
	private int districtID;
	private String districtCode;
	private String districtName;
	private int licenseTypeID;
	private String licenseTypeCode;
	private String licenseTypeName;
	private Date startDate;
	private Date endDate;
	private Date registrationDate;			
	private String fileNo;
	private int forestCategoryID;
	private String forestCategoryCode;
	private String forestCategoryName;
	private int forestID;
	private String forestCode;
	private String forestName;
	private int stateID;
	private String stateCode;
	private String stateName;
	private String compartmentNo;
	private BigDecimal compartmentArea;
	private long hallID;
	private String hallCode;
	private String hallName;
	private long hallOfficerID;
	private String hallOfficerHammerNo;
	private String hallOfficerName;
	private String hallOfficerMyKad;	
	private BigDecimal woodWorkFund;
	private BigDecimal licenseFund;
	private BigDecimal resinLimit;
	private BigDecimal nonResinLimit;
	private BigDecimal chengalLimit;
	private BigDecimal logLimit;
	private BigDecimal jarasLimit;
	private BigDecimal wasteWoodLimit;
	private String recorderID;
	private String recorderName;
	private String recorderMyKad;	
	private Timestamp recordTime;
	private ArrayList<Renew> renews;
	private String status;	

	public long getLicenseID() {
		return licenseID;
	}

	public void setLicenseID(long licenseID) {
		this.licenseID = licenseID;
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

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

	public int getForestCategoryID() {
		return forestCategoryID;
	}

	public void setForestCategoryID(int forestCategoryID) {
		this.forestCategoryID = forestCategoryID;
	}

	public String getForestCategoryCode() {
		return forestCategoryCode;
	}

	public void setForestCategoryCode(String forestCategoryCode) {
		this.forestCategoryCode = forestCategoryCode;
	}

	public String getForestCategoryName() {
		return forestCategoryName;
	}

	public void setForestCategoryName(String forestCategoryName) {
		this.forestCategoryName = forestCategoryName;
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

	public BigDecimal getCompartmentArea() {
		return compartmentArea;
	}

	public void setCompartmentArea(BigDecimal compartmentArea) {
		this.compartmentArea = compartmentArea;
	}

	public long getHallID() {
		return hallID;
	}

	public void setHallID(long hallID) {
		this.hallID = hallID;
	}

	public String getHallCode() {
		return hallCode;
	}

	public void setHallCode(String hallCode) {
		this.hallCode = hallCode;
	}

	public String getHallName() {
		return hallName;
	}

	public void setHallName(String hallName) {
		this.hallName = hallName;
	}

	public long getHallOfficerID() {
		return hallOfficerID;
	}

	public void setHallOfficerID(long hallOfficerID) {
		this.hallOfficerID = hallOfficerID;
	}

	public String getHallOfficerHammerNo() {
		return hallOfficerHammerNo;
	}

	public void setHallOfficerHammerNo(String hallOfficerHammerNo) {
		this.hallOfficerHammerNo = hallOfficerHammerNo;
	}

	public String getHallOfficerName() {
		return hallOfficerName;
	}

	public void setHallOfficerName(String hallOfficerName) {
		this.hallOfficerName = hallOfficerName;
	}

	public String getHallOfficerMyKad() {
		return hallOfficerMyKad;
	}

	public void setHallOfficerMyKad(String hallOfficerMyKad) {
		this.hallOfficerMyKad = hallOfficerMyKad;
	}

	public BigDecimal getWoodWorkFund() {
		return woodWorkFund;
	}

	public void setWoodWorkFund(BigDecimal woodWorkFund) {
		this.woodWorkFund = woodWorkFund;
	}

	public BigDecimal getLicenseFund() {
		return licenseFund;
	}

	public void setLicenseFund(BigDecimal licenseFund) {
		this.licenseFund = licenseFund;
	}

	public BigDecimal getResinLimit() {
		return resinLimit;
	}

	public void setResinLimit(BigDecimal resinLimit) {
		this.resinLimit = resinLimit;
	}

	public BigDecimal getNonResinLimit() {
		return nonResinLimit;
	}

	public void setNonResinLimit(BigDecimal nonResinLimit) {
		this.nonResinLimit = nonResinLimit;
	}

	public BigDecimal getChengalLimit() {
		return chengalLimit;
	}

	public void setChengalLimit(BigDecimal chengalLimit) {
		this.chengalLimit = chengalLimit;
	}

	public BigDecimal getLogLimit() {
		return logLimit;
	}

	public void setLogLimit(BigDecimal logLimit) {
		this.logLimit = logLimit;
	}

	public BigDecimal getJarasLimit() {
		return jarasLimit;
	}

	public void setJarasLimit(BigDecimal jarasLimit) {
		this.jarasLimit = jarasLimit;
	}

	public BigDecimal getWasteWoodLimit() {
		return wasteWoodLimit;
	}

	public void setWasteWoodLimit(BigDecimal wasteWoodLimit) {
		this.wasteWoodLimit = wasteWoodLimit;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString()
	{
		if(licenseeCompanyName == null)
		{
			return licenseNo;
		}
		else
		{
			return licenseNo+"-"+licenseeCompanyName;
		}
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof License)
		{
			License that = (License) obj;
			equal = licenseID == that.licenseID;
		}

		return equal;
	}
}