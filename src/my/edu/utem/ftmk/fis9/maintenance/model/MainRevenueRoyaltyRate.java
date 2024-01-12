package my.edu.utem.ftmk.fis9.maintenance.model;

import java.math.BigDecimal;
import java.util.Date;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 */
public class MainRevenueRoyaltyRate extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private long mainRevenueRoyaltyRateID;
	private int stateID;
	private String stateCode;
	private String stateName;
	private int speciesID;
	private String speciesCode;
	private String speciesName;
	private BigDecimal bigSizeRoyaltyRate;
	private BigDecimal smallSizeRoyaltyRate;
	private BigDecimal cessRate;
	private BigDecimal jarasRoyaltyRate;
	private BigDecimal jarasCessRate;
	private Date startDate;
	private Date endDate;
	private String status;

	public long getMainRevenueRoyaltyRateID() {
		return mainRevenueRoyaltyRateID;
	}

	public void setMainRevenueRoyaltyRateID(long mainRevenueRoyaltyRateID) {
		this.mainRevenueRoyaltyRateID = mainRevenueRoyaltyRateID;
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
		return speciesName+" - "+stateName;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof MainRevenueRoyaltyRate)
		{
			MainRevenueRoyaltyRate that = (MainRevenueRoyaltyRate) obj;
			equal = mainRevenueRoyaltyRateID == that.mainRevenueRoyaltyRateID;
		}

		return equal;
	}
}