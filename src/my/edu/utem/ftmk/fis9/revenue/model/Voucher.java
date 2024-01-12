package my.edu.utem.ftmk.fis9.revenue.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 */
public class Voucher extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private long voucherID;
	private String voucherNo;
	private String remarks;
	private Date date;
	private int category;
	private long licenseID;
	private int licenseTypeID;
	private String licenseNo;
	private long permitID;
	private int permitTypeID;
	private String permitNo;
	private String licenseeNo;
	private String licenseeCompanyName;
	private int districtID;
	private String districtName;
	private int stateID;
	private String stateName;
	private String recorderID;
	private String recorderName;	
	private Timestamp recordTime;
	private BigDecimal total;
	private String status;
	private ArrayList<VoucherRecord> voucherRecords;

	public long getVoucherID() {
		return voucherID;
	}

	public void setVoucherID(long voucherID) {
		this.voucherID = voucherID;
	}

	public String getVoucherNo() {
		return voucherNo;
	}

	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
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

	public long getPermitID()
	{
		return permitID;
	}

	public void setPermitID(long permitID)
	{
		this.permitID = permitID;
	}

	public int getPermitTypeID()
	{
		return permitTypeID;
	}

	public void setPermitTypeID(int permitTypeID)
	{
		this.permitTypeID = permitTypeID;
	}

	public String getPermitNo()
	{
		return permitNo;
	}

	public void setPermitNo(String permitNo)
	{
		this.permitNo = permitNo;
	}

	public String getLicenseeNo() {
		return licenseeNo;
	}

	public void setLicenseeNo(String licenseeNo) {
		this.licenseeNo = licenseeNo;
	}

	public String getLicenseeCompanyName() {
		return licenseeCompanyName;
	}

	public void setLicenseeCompanyName(String licenseeCompanyName) {
		this.licenseeCompanyName = licenseeCompanyName;
	}

	public int getDistrictID() {
		return districtID;
	}

	public void setDistrictID(int districtID) {
		this.districtID = districtID;
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

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<VoucherRecord> getVoucherRecords() {
		return voucherRecords;
	}

	public void setVoucherRecords(ArrayList<VoucherRecord> voucherRecords) {
		this.voucherRecords = voucherRecords;
	}

	@Override
	public String toString()
	{
		return voucherNo;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof Voucher)
		{
			Voucher that = (Voucher) obj;
			equal = voucherID == that.voucherID;
		}

		return equal;
	}
}