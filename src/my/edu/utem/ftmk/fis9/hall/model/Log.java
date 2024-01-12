package my.edu.utem.ftmk.fis9.hall.model;

import java.math.BigDecimal;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 */
public class Log extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private long logID;
	private int logNo;
	private String logSerialNo;
	private BigDecimal length;
	private BigDecimal diameter;
	private BigDecimal grossVolume;
	private BigDecimal holeDiameter;
	private BigDecimal netVolume;
	private long taggingRecordID;
	private String summaryTaggingRecord;
	private int speciesID;
	private String speciesCode;
	private String speciesName;
	private int speciesTypeID;
	private String status;

	public long getLogID() {
		return logID;
	}

	public void setLogID(long logID) {
		this.logID = logID;
	}

	public int getLogNo() {
		return logNo;
	}

	public void setLogNo(int logNo) {
		this.logNo = logNo;
	}

	public String getLogSerialNo() {
		return logSerialNo;
	}

	public void setLogSerialNo(String logSerialNo) {
		this.logSerialNo = logSerialNo;
	}

	public BigDecimal getLength() {
		return length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
	}

	public BigDecimal getDiameter() {
		return diameter;
	}

	public void setDiameter(BigDecimal diameter) {
		this.diameter = diameter;
	}

	public BigDecimal getGrossVolume() 
	{
		grossVolume = ((new BigDecimal(Math.PI)).multiply(((diameter.divide(new BigDecimal(2))).divide(new BigDecimal(100))).pow(2))).multiply(length);
		return grossVolume.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setGrossVolume(BigDecimal grossVolume) 
	{
		this.grossVolume = grossVolume;
	}

	public BigDecimal getHoleDiameter() {
		return holeDiameter;
	}

	public void setHoleDiameter(BigDecimal holeDiameter) {
		this.holeDiameter = holeDiameter;
	}

	public BigDecimal getNetVolume() 
	{
		BigDecimal holeVolume = ((new BigDecimal(Math.PI)).multiply(((holeDiameter.divide(new BigDecimal(2))).divide(new BigDecimal(100))).pow(2))).multiply(length);
		netVolume = getGrossVolume().subtract(holeVolume);
		return netVolume.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setNetVolume(BigDecimal netVolume) 
	{
		this.netVolume = netVolume;
	}

	public long getTaggingRecordID() {
		return taggingRecordID;
	}

	public void setTaggingRecordID(long taggingRecordID) {
		this.taggingRecordID = taggingRecordID;
	}

	public String getSummaryTaggingRecord() {
		return summaryTaggingRecord;
	}

	public void setSummaryTaggingRecord(String summaryTaggingRecord) {
		this.summaryTaggingRecord = summaryTaggingRecord;
	}

	public int getSpeciesID() {
		return speciesID;
	}

	public void setSpeciesID(int speciesID) {
		this.speciesID = speciesID;
	}

	public String getSpeciesCode() {
		return speciesCode;
	}

	public void setSpeciesCode(String speciesCode) {
		this.speciesCode = speciesCode;
	}

	public String getSpeciesName() {
		return speciesName;
	}

	public void setSpeciesName(String speciesName) {
		this.speciesName = speciesName;
	}

	public int getSpeciesTypeID() {
		return speciesTypeID;
	}

	public void setSpeciesTypeID(int speciesTypeID) {
		this.speciesTypeID = speciesTypeID;
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
		return logSerialNo;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof Log)
		{
			Log that = (Log) obj;
			equal = logID == that.logID;
		}

		return equal;
	}
}