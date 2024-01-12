package my.edu.utem.ftmk.fis9.hall.model;

import java.math.BigDecimal;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 */
public class SpecialTransferPassRecord extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private long specialTransferPassRecordID;
	private long transferPassID;
	private int speciesID;
	private String speciesCode;
	private String speciesName;
	private String speciesSymbol;
	private int speciesTypeID;
	private BigDecimal length;
	private BigDecimal diameter;
	private BigDecimal volume;
	private long mainRevenueRoyaltyRateID;	
	private BigDecimal bigSizeRoyaltyRate;
	private BigDecimal smallSizeRoyaltyRate;
	private BigDecimal cessRate;
	private BigDecimal jarasRoyaltyRate;
	private BigDecimal jarasCessRate;
	private BigDecimal royalty;
	private BigDecimal cess;	

	public long getSpecialTransferPassRecordID() {
		return specialTransferPassRecordID;
	}

	public void setSpecialTransferPassRecordID(long specialTransferPassRecordID) {
		this.specialTransferPassRecordID = specialTransferPassRecordID;
	}

	public long getTransferPassID() {
		return transferPassID;
	}

	public void setTransferPassID(long transferPassID) {
		this.transferPassID = transferPassID;
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

	public String getSpeciesSymbol()
	{
		return speciesSymbol;
	}

	public void setSpeciesSymbol(String speciesSymbol)
	{
		this.speciesSymbol = speciesSymbol;
	}

	public int getSpeciesTypeID() {
		return speciesTypeID;
	}

	public void setSpeciesTypeID(int speciesTypeID) {
		this.speciesTypeID = speciesTypeID;
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
	
	public BigDecimal getVolume() {
		volume = ((new BigDecimal(Math.PI)).multiply(((diameter.divide(new BigDecimal(2))).divide(new BigDecimal(100))).pow(2))).multiply(length);
		return volume.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
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
		return speciesCode+" - "+speciesName;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof SpecialTransferPassRecord)
		{
			SpecialTransferPassRecord that = (SpecialTransferPassRecord) obj;
			equal = specialTransferPassRecordID == that.specialTransferPassRecordID;
		}

		return equal;
	}
}