package my.edu.utem.ftmk.fis9.revenue.model;

import java.math.BigDecimal;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 */
public class JournalRecord extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private long journalRecordID;
	private long journalID;
	private int departmentVotID;
	private String departmentVotCode;
	private String departmentVotName;
	private int bursaryVotID;
	private String bursaryVotCode;
	private String bursaryVotName;
	private int trustFundID;
	private int trustFundDepartmentVotID;
	private String trustFundDepartmentVotCode;
	private String trustFundDepartmentVotName;
	private int trustFundBursaryVotID;
	private String trustFundBursaryVotCode;
	private String trustFundBursaryVotName;
	private String description;
	private BigDecimal total;

	public long getJournalRecordID() {
		return journalRecordID;
	}

	public void setJournalRecordID(long journalRecordID) {
		this.journalRecordID = journalRecordID;
	}

	public long getJournalID() {
		return journalID;
	}

	public void setJournalID(long journalID) {
		this.journalID = journalID;
	}

	public int getDepartmentVotID() {
		return departmentVotID;
	}

	public void setDepartmentVotID(int departmentVotID) {
		this.departmentVotID = departmentVotID;
	}

	public String getDepartmentVotCode() {
		return departmentVotCode;
	}

	public void setDepartmentVotCode(String departmentVotCode) {
		this.departmentVotCode = departmentVotCode;
	}

	public String getDepartmentVotName() {
		return departmentVotName;
	}

	public void setDepartmentVotName(String departmentVotName) {
		this.departmentVotName = departmentVotName;
	}

	public int getBursaryVotID()
	{
		return bursaryVotID;
	}

	public void setBursaryVotID(int bursaryVotID)
	{
		this.bursaryVotID = bursaryVotID;
	}

	public String getBursaryVotCode()
	{
		return bursaryVotCode;
	}

	public void setBursaryVotCode(String bursaryVotCode)
	{
		this.bursaryVotCode = bursaryVotCode;
	}

	public String getBursaryVotName()
	{
		return bursaryVotName;
	}

	public void setBursaryVotName(String bursaryVotName)
	{
		this.bursaryVotName = bursaryVotName;
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

	public String getTrustFundDepartmentVotCode() {
		return trustFundDepartmentVotCode;
	}

	public void setTrustFundDepartmentVotCode(String trustFundDepartmentVotCode) {
		this.trustFundDepartmentVotCode = trustFundDepartmentVotCode;
	}

	public String getTrustFundDepartmentVotName() {
		return trustFundDepartmentVotName;
	}

	public void setTrustFundDepartmentVotName(String trustFundDepartmentVotName) {
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
		return "("+departmentVotCode+" - "+departmentVotName+") - ("+trustFundDepartmentVotCode+" - "+trustFundDepartmentVotName+")";
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof JournalRecord)
		{
			JournalRecord that = (JournalRecord) obj;
			equal = journalRecordID == that.journalRecordID;
		}

		return equal;
	}
}