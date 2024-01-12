package my.edu.utem.ftmk.fis9.hall.model;

import java.math.BigDecimal;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 */
public class SmallProductTransferPassRecord extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private long smallProductTransferPassRecordID;
	private long transferPassID;
	private int smallProductID;
	private String smallProductCode;
	private String smallProductName;
	private String smallProductSymbol;
	private int unitID;
	private String unitCode;
	private String unitName;
	private long smallProductRoyaltyRateID;	
	private BigDecimal royaltyRate;
	private BigDecimal cessRate;
	private BigDecimal quantity;
	private BigDecimal royalty;
	private BigDecimal cess;

	public long getSmallProductTransferPassRecordID() {
		return smallProductTransferPassRecordID;
	}

	public void setSmallProductTransferPassRecordID(long smallProductTransferPassRecordID) {
		this.smallProductTransferPassRecordID = smallProductTransferPassRecordID;
	}

	public long getTransferPassID() {
		return transferPassID;
	}

	public void setTransferPassID(long transferPassID) {
		this.transferPassID = transferPassID;
	}

	public int getSmallProductID() {
		return smallProductID;
	}

	public void setSmallProductID(int smallProductID) {
		this.smallProductID = smallProductID;
	}

	public String getSmallProductCode() {
		return smallProductCode;
	}

	public void setSmallProductCode(String smallProductCode) {
		this.smallProductCode = smallProductCode;
	}

	public String getSmallProductName() {
		return smallProductName;
	}

	public void setSmallProductName(String smallProductName) {
		this.smallProductName = smallProductName;
	}

	public String getSmallProductSymbol()
	{
		return smallProductSymbol;
	}

	public void setSmallProductSymbol(String smallProductSymbol)
	{
		this.smallProductSymbol = smallProductSymbol;
	}

	public int getUnitID() {
		return unitID;
	}

	public void setUnitID(int unitID) {
		this.unitID = unitID;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public long getSmallProductRoyaltyRateID() {
		return smallProductRoyaltyRateID;
	}

	public void setSmallProductRoyaltyRateID(long smallProductRoyaltyRateID) {
		this.smallProductRoyaltyRateID = smallProductRoyaltyRateID;
	}

	public BigDecimal getRoyaltyRate() {
		return royaltyRate;
	}

	public void setRoyaltyRate(BigDecimal royaltyRate) {
		this.royaltyRate = royaltyRate;
	}

	public BigDecimal getCessRate() {
		return cessRate;
	}

	public void setCessRate(BigDecimal cessRate) {
		this.cessRate = cessRate;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
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
		return smallProductName+" - "+unitName;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof SmallProductTransferPassRecord)
		{
			SmallProductTransferPassRecord that = (SmallProductTransferPassRecord) obj;
			equal = smallProductTransferPassRecordID == that.smallProductTransferPassRecordID;
		}

		return equal;
	}
}