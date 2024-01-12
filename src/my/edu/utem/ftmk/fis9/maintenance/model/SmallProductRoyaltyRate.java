package my.edu.utem.ftmk.fis9.maintenance.model;

import java.math.BigDecimal;
import java.util.Date;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 */
public class SmallProductRoyaltyRate extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private long smallProductRoyaltyRateID;
	private int smallProductID;
	private String smallProductCode;
	private String smallProductName;
	private int unitID;
	private String unitCode;
	private String unitName;
	private BigDecimal royaltyRate;
	private BigDecimal cessRate;
	private Date startDate;
	private Date endDate;
	private String status;

	public long getSmallProductRoyaltyRateID() {
		return smallProductRoyaltyRateID;
	}

	public void setSmallProductRoyaltyRateID(long smallProductRoyaltyRateID) {
		this.smallProductRoyaltyRateID = smallProductRoyaltyRateID;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

		if (obj instanceof SmallProductRoyaltyRate)
		{
			SmallProductRoyaltyRate that = (SmallProductRoyaltyRate) obj;
			equal = smallProductRoyaltyRateID == that.smallProductRoyaltyRateID;
		}

		return equal;
	}
}