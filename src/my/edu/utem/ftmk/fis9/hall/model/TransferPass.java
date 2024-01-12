package my.edu.utem.ftmk.fis9.hall.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 */
public class TransferPass extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private long transferPassID;
	private String transferPassNo;
	private String batchNo;
	private Date date;	
	private long hallOfficerID;
	private String hallOfficerHammerNo;
	private String hallOfficerName;
	private String hallName;
	private int code;
	private int royaltyRate;
	private int cessRate;
	private int premiumRate;
	private long licenseID;
	private String licenseNo;	
	private String companyName;
	private String licenseeNo;
	private String address;
	private String telNo;
	private String destinationAddress;
	private int destinationStateID;
	private String destinationStateName;
	private String driverName;
	private String driverICNo;
	private String driverAddress;
	private String vehicleNo;
	private BigDecimal grossVehicleWeight;
	private long taggingID;
	private String summaryTagging;
	private int logSizeID;
	private BigDecimal logMinBigSize;
	private BigDecimal logMinSmallSize;
	private BigDecimal royaltyAmount;
	private BigDecimal cessAmount;
	private BigDecimal premiumAmount;
	private String recorderID;
	private String recorderName;
	private Timestamp recordTime;
	private long journalID;
	private String remarks;
	private ArrayList<MainRevenueTransferPassRecord> mainRevenueTransferPassRecords;
	private ArrayList<SmallProductTransferPassRecord> smallProductTransferPassRecords;
	private ArrayList<SpecialTransferPassRecord> specialTransferPassRecords;
	private String status;	
	
	public long getTransferPassID() {
		return transferPassID;
	}

	public void setTransferPassID(long transferPassID) {
		this.transferPassID = transferPassID;
	}

	public String getTransferPassNo() {
		return transferPassNo;
	}

	public void setTransferPassNo(String transferPassNo) {
		this.transferPassNo = transferPassNo;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public String getHallName() {
		return hallName;
	}

	public void setHallName(String hallName) {
		this.hallName = hallName;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getRoyaltyRate() {
		return royaltyRate;
	}

	public void setRoyaltyRate(int royaltyRate) {
		this.royaltyRate = royaltyRate;
	}

	public int getCessRate() {
		return cessRate;
	}

	public void setCessRate(int cessRate) {
		this.cessRate = cessRate;
	}

	public int getPremiumRate() {
		return premiumRate;
	}

	public void setPremiumRate(int premiumRate) {
		this.premiumRate = premiumRate;
	}

	public long getLicenseID() {
		return licenseID;
	}

	public void setLicenseID(long licenseID) {
		this.licenseID = licenseID;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getLicenseeNo() {
		return licenseeNo;
	}

	public void setLicenseeNo(String licenseeNo) {
		this.licenseeNo = licenseeNo;
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

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public int getDestinationStateID() {
		return destinationStateID;
	}

	public void setDestinationStateID(int destinationStateID) {
		this.destinationStateID = destinationStateID;
	}

	public String getDestinationStateName() {
		return destinationStateName;
	}

	public void setDestinationStateName(String destinationStateName) {
		this.destinationStateName = destinationStateName;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverICNo() {
		return driverICNo;
	}

	public void setDriverICNo(String driverICNo) {
		this.driverICNo = driverICNo;
	}

	public String getDriverAddress() {
		return driverAddress;
	}

	public void setDriverAddress(String driverAddress) {
		this.driverAddress = driverAddress;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public BigDecimal getGrossVehicleWeight() {
		return grossVehicleWeight;
	}

	public void setGrossVehicleWeight(BigDecimal grossVehicleWeight) {
		this.grossVehicleWeight = grossVehicleWeight;
	}

	public long getTaggingID() {
		return taggingID;
	}

	public void setTaggingID(long taggingID) {
		this.taggingID = taggingID;
	}

	public String getSummaryTagging() {
		return summaryTagging;
	}

	public void setSummaryTagging(String summaryTagging) {
		this.summaryTagging = summaryTagging;
	}

	public int getLogSizeID() {
		return logSizeID;
	}

	public void setLogSizeID(int logSizeID) {
		this.logSizeID = logSizeID;
	}

	public BigDecimal getLogMinBigSize() {
		return logMinBigSize;
	}

	public void setLogMinBigSize(BigDecimal logMinBigSize) {
		this.logMinBigSize = logMinBigSize;
	}

	public BigDecimal getLogMinSmallSize() {
		return logMinSmallSize;
	}

	public void setLogMinSmallSize(BigDecimal logMinSmallSize) {
		this.logMinSmallSize = logMinSmallSize;
	}

	public BigDecimal getRoyaltyAmount() {
		return royaltyAmount;
	}

	public void setRoyaltyAmount(BigDecimal royaltyAmount) {
		this.royaltyAmount = royaltyAmount;
	}

	public BigDecimal getCessAmount() {
		return cessAmount;
	}

	public void setCessAmount(BigDecimal cessAmount) {
		this.cessAmount = cessAmount;
	}

	public BigDecimal getPremiumAmount() {
		return premiumAmount;
	}

	public void setPremiumAmount(BigDecimal premiumAmount) {
		this.premiumAmount = premiumAmount;
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

	public long getJournalID() {
		return journalID;
	}

	public void setJournalID(long journalID) {
		this.journalID = journalID;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public ArrayList<MainRevenueTransferPassRecord> getMainRevenueTransferPassRecords() {
		return mainRevenueTransferPassRecords;
	}

	public void setMainRevenueTransferPassRecords(ArrayList<MainRevenueTransferPassRecord> mainRevenueTransferPassRecords) {
		this.mainRevenueTransferPassRecords = mainRevenueTransferPassRecords;
	}

	public ArrayList<SmallProductTransferPassRecord> getSmallProductTransferPassRecords() {
		return smallProductTransferPassRecords;
	}

	public void setSmallProductTransferPassRecords(
			ArrayList<SmallProductTransferPassRecord> smallProductTransferPassRecords) {
		this.smallProductTransferPassRecords = smallProductTransferPassRecords;
	}

	public ArrayList<SpecialTransferPassRecord> getSpecialTransferPassRecords() {
		return specialTransferPassRecords;
	}

	public void setSpecialTransferPassRecords(ArrayList<SpecialTransferPassRecord> specialTransferPassRecords) {
		this.specialTransferPassRecords = specialTransferPassRecords;
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
		return transferPassNo;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof TransferPass)
		{
			TransferPass that = (TransferPass) obj;
			equal = transferPassID == that.transferPassID;
		}

		return equal;
	}
}