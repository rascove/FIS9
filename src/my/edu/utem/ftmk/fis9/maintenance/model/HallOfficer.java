package my.edu.utem.ftmk.fis9.maintenance.model;

import java.util.Date;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 */
public class HallOfficer extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private long hallOfficerID;
	private String staffID;
	private String hallOfficerName;
	private String hammerNo;
	private int districtID;
	private Date startDate;
	private Date endDate;
	private long hallID;
	private String hallName;
	private String status;

	public long getHallOfficerID() {
		return hallOfficerID;
	}

	public void setHallOfficerID(long hallOfficerID) {
		this.hallOfficerID = hallOfficerID;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getHallOfficerName() {
		return hallOfficerName;
	}

	public void setHallOfficerName(String hallOfficerName) {
		this.hallOfficerName = hallOfficerName;
	}

	public String getHammerNo() {
		return hammerNo;
	}

	public void setHammerNo(String hammerNo) {
		this.hammerNo = hammerNo;
	}

	public int getDistrictID() {
		return districtID;
	}

	public void setDistrictID(int districtID) {
		this.districtID = districtID;
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

	public long getHallID() {
		return hallID;
	}

	public void setHallID(long hallID) {
		this.hallID = hallID;
	}

	public String getHallName() {
		return hallName;
	}

	public void setHallName(String hallName) {
		this.hallName = hallName;
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
		return hallOfficerName;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof HallOfficer)
		{
			HallOfficer that = (HallOfficer) obj;
			equal = staffID == that.staffID;
		}

		return equal;
	}
}