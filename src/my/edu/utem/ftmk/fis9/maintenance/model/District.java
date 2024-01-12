package my.edu.utem.ftmk.fis9.maintenance.model;

import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class District extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int districtID;
	private String code;
	private String name;
	private String address;
	private int stateID;
	private String officerID;
	private String asstOfficerID;
	private String clerk1ID;
	private String clerk2ID;
	private String clerk3ID;
	private String stateCode;
	private String stateName;
	private String officerName;
	private String asstOfficerName;
	private String clerk1Name;
	private String clerk2Name;
	private String clerk3Name;
	private ArrayList<Range> ranges;
	private ArrayList<Hall> halls;

	public int getDistrictID()
	{
		return districtID;
	}

	public void setDistrictID(int districtID)
	{
		this.districtID = districtID;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public int getStateID()
	{
		return stateID;
	}

	public void setStateID(int stateID)
	{
		this.stateID = stateID;
	}

	public String getOfficerID()
	{
		return officerID;
	}

	public void setOfficerID(String officerID)
	{
		this.officerID = officerID;
	}

	public String getAsstOfficerID()
	{
		return asstOfficerID;
	}

	public void setAsstOfficerID(String asstOfficerID)
	{
		this.asstOfficerID = asstOfficerID;
	}

	public String getClerk1ID()
	{
		return clerk1ID;
	}

	public void setClerk1ID(String clerk1ID)
	{
		this.clerk1ID = clerk1ID;
	}

	public String getClerk2ID()
	{
		return clerk2ID;
	}

	public void setClerk2ID(String clerk2ID)
	{
		this.clerk2ID = clerk2ID;
	}

	public String getClerk3ID()
	{
		return clerk3ID;
	}

	public void setClerk3ID(String clerk3ID)
	{
		this.clerk3ID = clerk3ID;
	}

	public String getStateCode()
	{
		return stateCode;
	}

	public void setStateCode(String stateCode)
	{
		this.stateCode = stateCode;
	}

	public String getStateName()
	{
		return stateName;
	}

	public void setStateName(String stateName)
	{
		this.stateName = stateName;
	}

	public String getOfficerName()
	{
		return officerName;
	}

	public void setOfficerName(String officerName)
	{
		this.officerName = officerName;
	}

	public String getAsstOfficerName()
	{
		return asstOfficerName;
	}

	public void setAsstOfficerName(String asstOfficerName)
	{
		this.asstOfficerName = asstOfficerName;
	}

	public String getClerk1Name()
	{
		return clerk1Name;
	}

	public void setClerk1Name(String clerk1Name)
	{
		this.clerk1Name = clerk1Name;
	}

	public String getClerk2Name()
	{
		return clerk2Name;
	}

	public void setClerk2Name(String clerk2Name)
	{
		this.clerk2Name = clerk2Name;
	}

	public String getClerk3Name()
	{
		return clerk3Name;
	}

	public void setClerk3Name(String clerk3Name)
	{
		this.clerk3Name = clerk3Name;
	}

	public ArrayList<Range> getRanges()
	{
		return ranges;
	}

	public void setRanges(ArrayList<Range> ranges)
	{
		this.ranges = ranges;
	}

	public ArrayList<Hall> getHalls()
	{
		return halls;
	}

	public void setHalls(ArrayList<Hall> halls)
	{
		this.halls = halls;
	}

	@Override
	public String toString()
	{
		return name;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof District)
		{
			District that = (District) obj;
			equal = districtID == that.districtID;
		}

		return equal;
	}
}