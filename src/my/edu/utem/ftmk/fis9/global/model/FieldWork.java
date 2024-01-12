package my.edu.utem.ftmk.fis9.global.model;

import java.util.ArrayList;
import java.util.Date;

import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.prefelling.model.CuttingLimit;

/**
 * @author Satrya Fajri Pratama
 */
public abstract class FieldWork<X extends FieldWorksheet<?>> extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private long fieldWorkID;
	private String comptBlockNo;
	private double area;
	private int year;
	private Date startDate;
	private Date endDate;
	private boolean open;
	private double dipterocarpLimit;
	private double nonDipterocarpLimit;
	private int forestID;
	private int rangeID;
	private int districtID;
	private int stateID;
	private String teamLeaderID;
	private String tenderNo;
	private String creatorID;
	private String forestCode;
	private String forestName;
	private String rangeName;
	private String districtCode;
	private String districtName;
	private String stateCode;
	private String stateName;
	private String teamLeaderName;
	private String creatorName;
	private ArrayList<Staff> recorders;
	private ArrayList<CuttingLimit> cuttingLimits;
	private ArrayList<X> fieldWorksheets;
	private boolean planUploaded;
	private boolean stockUploaded;

	protected final long getFieldWorkID()
	{
		return fieldWorkID;
	}

	protected final void setFieldWorkID(long fieldWorkID)
	{
		this.fieldWorkID = fieldWorkID;
	}

	public final String getComptBlockNo()
	{
		return comptBlockNo;
	}

	public final void setComptBlockNo(String comptBlockNo)
	{
		this.comptBlockNo = comptBlockNo;
	}

	public final double getArea()
	{
		return area;
	}

	public final void setArea(double area)
	{
		this.area = area;
	}

	public final int getYear()
	{
		return year;
	}

	public final void setYear(int year)
	{
		this.year = year;
	}

	public final Date getStartDate()
	{
		return startDate;
	}

	public final void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	public final Date getEndDate()
	{
		return endDate;
	}

	public final void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	public final boolean isOpen()
	{
		return open;
	}

	public final void setOpen(boolean open)
	{
		this.open = open;
	}

	public final double getDipterocarpLimit()
	{
		return dipterocarpLimit;
	}

	public final void setDipterocarpLimit(double dipterocarpLimit)
	{
		this.dipterocarpLimit = dipterocarpLimit;
	}

	public final double getNonDipterocarpLimit()
	{
		return nonDipterocarpLimit;
	}

	public final void setNonDipterocarpLimit(double nonDipterocarpLimit)
	{
		this.nonDipterocarpLimit = nonDipterocarpLimit;
	}

	public final int getForestID()
	{
		return forestID;
	}

	public final void setForestID(int forestID)
	{
		this.forestID = forestID;
	}

	public final int getRangeID()
	{
		return rangeID;
	}

	public final void setRangeID(int rangeID)
	{
		this.rangeID = rangeID;
	}

	public final String getTeamLeaderID()
	{
		return teamLeaderID;
	}

	public final void setTeamLeaderID(String teamLeaderID)
	{
		this.teamLeaderID = teamLeaderID;
	}

	public final String getTenderNo()
	{
		return tenderNo;
	}

	public final void setTenderNo(String tenderNo)
	{
		this.tenderNo = tenderNo;
	}

	public final String getCreatorID()
	{
		return creatorID;
	}

	public final void setCreatorID(String creatorID)
	{
		this.creatorID = creatorID;
	}

	public final int getDistrictID()
	{
		return districtID;
	}

	public final void setDistrictID(int districtID)
	{
		this.districtID = districtID;
	}

	public final int getStateID()
	{
		return stateID;
	}

	public final void setStateID(int stateID)
	{
		this.stateID = stateID;
	}

	public final String getForestCode()
	{
		return forestCode;
	}

	public final void setForestCode(String forestCode)
	{
		this.forestCode = forestCode;
	}

	public final String getForestName()
	{
		return forestName;
	}

	public final void setForestName(String forestName)
	{
		this.forestName = forestName;
	}

	public final String getRangeName()
	{
		return rangeName;
	}

	public final void setRangeName(String rangeName)
	{
		this.rangeName = rangeName;
	}

	public final String getDistrictCode()
	{
		return districtCode;
	}

	public final void setDistrictCode(String districtCode)
	{
		this.districtCode = districtCode;
	}

	public final String getDistrictName()
	{
		return districtName;
	}

	public final void setDistrictName(String districtName)
	{
		this.districtName = districtName;
	}

	public final String getStateCode()
	{
		return stateCode;
	}

	public final void setStateCode(String stateCode)
	{
		this.stateCode = stateCode;
	}

	public final String getStateName()
	{
		return stateName;
	}

	public final void setStateName(String stateName)
	{
		this.stateName = stateName;
	}

	public final String getTeamLeaderName()
	{
		return teamLeaderName;
	}

	public final void setTeamLeaderName(String teamLeaderName)
	{
		this.teamLeaderName = teamLeaderName;
	}

	public final String getCreatorName()
	{
		return creatorName;
	}

	public final void setCreatorName(String creatorName)
	{
		this.creatorName = creatorName;
	}

	public final ArrayList<Staff> getRecorders()
	{
		return recorders;
	}

	public final void setRecorders(ArrayList<Staff> recorders)
	{
		this.recorders = recorders;
	}

	public final ArrayList<CuttingLimit> getCuttingLimits()
	{
		return cuttingLimits;
	}

	public final void setCuttingLimits(ArrayList<CuttingLimit> cuttingLimits)
	{
		this.cuttingLimits = cuttingLimits;
	}

	protected final ArrayList<X> getFieldWorksheets()
	{
		return fieldWorksheets;
	}

	protected final void setFieldWorksheets(ArrayList<X> fieldWorksheets)
	{
		this.fieldWorksheets = fieldWorksheets;
	}

	public boolean isPlanUploaded()
	{
		return planUploaded;
	}

	public void setPlanUploaded(boolean planUploaded)
	{
		this.planUploaded = planUploaded;
	}

	public boolean isStockUploaded()
	{
		return stockUploaded;
	}

	public void setStockUploaded(boolean stockUploaded)
	{
		this.stockUploaded = stockUploaded;
	}

	@Override
	public String toString()
	{
		return forestName + " no. kompatmen/blok " + comptBlockNo + " tahun " + year;
	}
}