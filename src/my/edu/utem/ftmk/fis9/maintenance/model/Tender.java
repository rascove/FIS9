package my.edu.utem.ftmk.fis9.maintenance.model;

import java.util.Date;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 * @author Satrya Fajri Pratama
 */
public class Tender extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private String tenderNo;
	private String contractorID;
	private String workType;
	private Date appointDate;
	private Date startDate;
	private Date endDate;
	private String voucher;
	private String status;
	private int stateID;
	private String contractorName;

	public String getTenderNo()
	{
		return tenderNo;
	}

	public void setTenderNo(String tenderNo)
	{
		this.tenderNo = tenderNo;
	}

	public String getContractorID()
	{
		return contractorID;
	}

	public void setContractorID(String contractorID)
	{
		this.contractorID = contractorID;
	}

	public String getWorkType()
	{
		return workType;
	}

	public void setWorkType(String workType)
	{
		this.workType = workType;
	}

	public Date getAppointDate()
	{
		return appointDate;
	}

	public void setAppointDate(Date appointDate)
	{
		this.appointDate = appointDate;
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	public String getVoucher()
	{
		return voucher;
	}

	public void setVoucher(String voucher)
	{
		this.voucher = voucher;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public int getStateID()
	{
		return stateID;
	}

	public void setStateID(int stateID)
	{
		this.stateID = stateID;
	}

	public String getContractorName()
	{
		return contractorName;
	}

	public void setContractorName(String contractorName)
	{
		this.contractorName = contractorName;
	}

	@Override
	public String toString()
	{
		return tenderNo;
	}

	@Override
	public boolean equals(Object obj)
	{
		return toString().equals(obj.toString());
	}
}