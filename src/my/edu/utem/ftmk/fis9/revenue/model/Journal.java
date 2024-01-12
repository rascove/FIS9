package my.edu.utem.ftmk.fis9.revenue.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;
import my.edu.utem.ftmk.fis9.hall.model.TransferPass;

/**
 * @author Nor Azman Mat Ariff
 */
public class Journal extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private long journalID;
	private String journalNo;
	private String name;
	private String remarks;
	private Date date;
	private int category;
	private long licenseID;
	private String licenseNo;
	private BigDecimal woodWorkFund;
	private BigDecimal licenseFund;
	private int licenseTypeID;
	private long permitID;
	private String permitNo;
	private BigDecimal permitFund;
	private int permitTypeID;
	private String licenseeCompanyName;
	private String licenseeNo;
	private String address;
	private String telNo;
	private int districtID;
	private String districtName;
	private int stateID;
	private String stateName;
	private String recorderID;
	private String recorderName;
	private Timestamp recordTime;
	private BigDecimal total;
	private String status;
	private ArrayList<JournalRecord> journalRecords;
	private ArrayList<TransferPass> transferPasses;

	public long getJournalID()
	{
		return journalID;
	}

	public void setJournalID(long journalID)
	{
		this.journalID = journalID;
	}

	public String getJournalNo()
	{
		return journalNo;
	}

	public void setJournalNo(String journalNo)
	{
		this.journalNo = journalNo;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getRemarks()
	{
		return remarks;
	}

	public void setRemarks(String remarks)
	{
		this.remarks = remarks;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public int getCategory()
	{
		return category;
	}

	public void setCategory(int category)
	{
		this.category = category;
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

	public BigDecimal getWoodWorkFund()
	{
		return woodWorkFund;
	}

	public void setWoodWorkFund(BigDecimal woodWorkFund)
	{
		this.woodWorkFund = woodWorkFund;
	}

	public BigDecimal getLicenseFund()
	{
		return licenseFund;
	}

	public void setLicenseFund(BigDecimal licenseFund)
	{
		this.licenseFund = licenseFund;
	}

	public int getLicenseTypeID()
	{
		return licenseTypeID;
	}

	public void setLicenseTypeID(int licenseTypeID)
	{
		this.licenseTypeID = licenseTypeID;
	}

	public String getLicenseeCompanyName()
	{
		return licenseeCompanyName;
	}

	public void setLicenseeCompanyName(String licenseeCompanyName)
	{
		this.licenseeCompanyName = licenseeCompanyName;
	}

	public String getLicenseeNo()
	{
		return licenseeNo;
	}

	public void setLicenseeNo(String licenseeNo)
	{
		this.licenseeNo = licenseeNo;
	}

	public long getPermitID()
	{
		return permitID;
	}

	public void setPermitID(long permitID)
	{
		this.permitID = permitID;
	}

	public String getPermitNo()
	{
		return permitNo;
	}

	public void setPermitNo(String permitNo)
	{
		this.permitNo = permitNo;
	}

	public BigDecimal getPermitFund()
	{
		return permitFund;
	}

	public void setPermitFund(BigDecimal permitFund)
	{
		this.permitFund = permitFund;
	}

	public int getPermitTypeID()
	{
		return permitTypeID;
	}

	public void setPermitTypeID(int permitTypeID)
	{
		this.permitTypeID = permitTypeID;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getTelNo()
	{
		return telNo;
	}

	public void setTelNo(String telNo)
	{
		this.telNo = telNo;
	}

	public int getDistrictID()
	{
		return districtID;
	}

	public void setDistrictID(int districtID)
	{
		this.districtID = districtID;
	}

	public String getDistrictName()
	{
		return districtName;
	}

	public void setDistrictName(String districtName)
	{
		this.districtName = districtName;
	}

	public int getStateID()
	{
		return stateID;
	}

	public void setStateID(int stateID)
	{
		this.stateID = stateID;
	}

	public String getStateName()
	{
		return stateName;
	}

	public void setStateName(String stateName)
	{
		this.stateName = stateName;
	}

	public String getRecorderID()
	{
		return recorderID;
	}

	public void setRecorderID(String recorderID)
	{
		this.recorderID = recorderID;
	}

	public String getRecorderName()
	{
		return recorderName;
	}

	public void setRecorderName(String recorderName)
	{
		this.recorderName = recorderName;
	}

	public Timestamp getRecordTime()
	{
		return recordTime;
	}

	public void setRecordTime(Timestamp recordTime)
	{
		this.recordTime = recordTime;
	}

	public BigDecimal getTotal()
	{
		return total;
	}

	public void setTotal(BigDecimal total)
	{
		this.total = total;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public ArrayList<JournalRecord> getJournalRecords()
	{
		return journalRecords;
	}

	public void setJournalRecords(ArrayList<JournalRecord> journalRecords)
	{
		this.journalRecords = journalRecords;
	}

	public ArrayList<TransferPass> getTransferPasses()
	{
		return transferPasses;
	}

	public void setTransferPasses(ArrayList<TransferPass> transferPasses)
	{
		this.transferPasses = transferPasses;
	}

	@Override
	public String toString()
	{
		return journalNo;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof Journal)
		{
			Journal that = (Journal) obj;
			equal = journalID == that.journalID;
		}

		return equal;
	}
}