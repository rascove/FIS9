package my.edu.utem.ftmk.fis9.hall.model;

import java.math.BigDecimal;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 */
public class MainRevenueTransferPassRecord extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private long mainRevenueTransferPassRecordID;
	private long transferPassID;
	private long logID;
	private long taggingRecordID;
	private int speciesID;
	private String speciesName;
	private String speciesCode;
	private int speciesTypeID;
	private String speciesTypeName;
	private String speciesSymbol;
	private int logNo;
	private String logSerialNo;
	private BigDecimal length;
	private BigDecimal diameter;
	private BigDecimal holeDiameter;
	private BigDecimal grossVolume;
	private BigDecimal netVolume;
	private long mainRevenueRoyaltyRateID;	
	private BigDecimal bigSizeRoyaltyRate;
	private BigDecimal smallSizeRoyaltyRate;
	private BigDecimal cessRate;
	private BigDecimal jarasRoyaltyRate;
	private BigDecimal jarasCessRate;
	private BigDecimal royalty;
	private BigDecimal cess;

	public long getMainRevenueTransferPassRecordID() {
		return mainRevenueTransferPassRecordID;
	}

	public void setMainRevenueTransferPassRecordID(long mainRevenueTransferPassRecordID) {
		this.mainRevenueTransferPassRecordID = mainRevenueTransferPassRecordID;
	}

	public long getTransferPassID() {
		return transferPassID;
	}

	public void setTransferPassID(long transferPassID) {
		this.transferPassID = transferPassID;
	}

	public long getLogID() {
		return logID;
	}

	public void setLogID(long logID) {
		this.logID = logID;
	}

	public long getTaggingRecordID()
	{
		return taggingRecordID;
	}

	public void setTaggingRecordID(long taggingRecordID)
	{
		this.taggingRecordID = taggingRecordID;
	}

	public int getSpeciesID()
	{
		return speciesID;
	}

	public void setSpeciesID(int speciesID)
	{
		this.speciesID = speciesID;
	}

	public String getSpeciesName() {
		return speciesName;
	}

	public void setSpeciesName(String speciesName) {
		this.speciesName = speciesName;
	}

	public String getSpeciesCode() {
		return speciesCode;
	}

	public void setSpeciesCode(String speciesCode) {
		this.speciesCode = speciesCode;
	}

	public int getSpeciesTypeID()
	{
		return speciesTypeID;
	}

	public void setSpeciesTypeID(int speciesTypeID)
	{
		this.speciesTypeID = speciesTypeID;
	}

	public String getSpeciesSymbol()
	{
		return speciesSymbol;
	}

	public void setSpeciesSymbol(String speciesSymbol)
	{
		this.speciesSymbol = speciesSymbol;
	}

	public String getSpeciesTypeName()
	{
		return speciesTypeName;
	}

	public void setSpeciesTypeName(String speciesTypeName)
	{
		this.speciesTypeName = speciesTypeName;
	}

	public int getLogNo() {
		return logNo;
	}

	public void setLogNo(int logNo) {
		this.logNo = logNo;
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

	public BigDecimal getHoleDiameter() {
		return holeDiameter;
	}

	public void setHoleDiameter(BigDecimal holeDiameter) {
		this.holeDiameter = holeDiameter;
	}

	public BigDecimal getGrossVolume() {
		grossVolume = ((new BigDecimal(Math.PI)).multiply(((diameter.divide(new BigDecimal(2))).divide(new BigDecimal(100))).pow(2))).multiply(length);
		return grossVolume.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setGrossVolume(BigDecimal grossVolume) {
		this.grossVolume = grossVolume;
	}

	public BigDecimal getNetVolume() {
		BigDecimal holeVolume = ((new BigDecimal(Math.PI)).multiply(((holeDiameter.divide(new BigDecimal(2))).divide(new BigDecimal(100))).pow(2))).multiply(length);
		netVolume = grossVolume.subtract(holeVolume);			
		return netVolume.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setNetVolume(BigDecimal netVolume) {
		this.netVolume = netVolume;
	}

	public String getLogSerialNo() {
		return logSerialNo;
	}

	public void setLogSerialNo(String logSerialNo) {
		this.logSerialNo = logSerialNo;
	}

	public long getMainRevenueRoyaltyRateID() {
		return mainRevenueRoyaltyRateID;
	}

	public void setMainRevenueRoyaltyRateID(long mainRevenueRoyaltyRateID) {
		this.mainRevenueRoyaltyRateID = mainRevenueRoyaltyRateID;
	}

	public BigDecimal getBigSizeRoyaltyRate() {
		return bigSizeRoyaltyRate;
	}

	public void setBigSizeRoyaltyRate(BigDecimal bigSizeRoyaltyRate) {
		this.bigSizeRoyaltyRate = bigSizeRoyaltyRate;
	}

	public BigDecimal getSmallSizeRoyaltyRate() {
		return smallSizeRoyaltyRate;
	}

	public void setSmallSizeRoyaltyRate(BigDecimal smallSizeRoyaltyRate) {
		this.smallSizeRoyaltyRate = smallSizeRoyaltyRate;
	}

	public BigDecimal getCessRate() {
		return cessRate;
	}

	public void setCessRate(BigDecimal cessRate) {
		this.cessRate = cessRate;
	}

	public BigDecimal getJarasRoyaltyRate() {
		return jarasRoyaltyRate;
	}

	public void setJarasRoyaltyRate(BigDecimal jarasRoyaltyRate) {
		this.jarasRoyaltyRate = jarasRoyaltyRate;
	}

	public BigDecimal getJarasCessRate() {
		return jarasCessRate;
	}

	public void setJarasCessRate(BigDecimal jarasCessRate) {
		this.jarasCessRate = jarasCessRate;
	}

	public BigDecimal getRoyalty() {
		return royalty;
	}

	public void setRoyalty(BigDecimal royalty) {
		this.royalty = royalty;
	}

	public BigDecimal getCess() {
		return cess;
	}

	public void setCess(BigDecimal cess) {
		this.cess = cess;
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

		if (obj instanceof MainRevenueTransferPassRecord)
		{
			MainRevenueTransferPassRecord that = (MainRevenueTransferPassRecord) obj;
			equal = mainRevenueTransferPassRecordID == that.mainRevenueTransferPassRecordID;
		}

		return equal;
	}
}