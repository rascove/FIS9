package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class Hammer extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private String hammerNo;
	private String registrationNo;
	private int hammerTypeID;
	private int districtID;
	private String contractorID;
	private int stateID;
	private String hammerTypeName;
	private String districtName;
	private String stateName;
	private String contractorName;
	
	public String getHammerNo()
	{
		return hammerNo;
	}

	public void setHammerNo(String hammerNo)
	{
		this.hammerNo = hammerNo;
	}

	public String getRegistrationNo()
	{
		return registrationNo;
	}

	public void setRegistrationNo(String registrationNo)
	{
		this.registrationNo = registrationNo;
	}

	public int getHammerTypeID()
	{
		return hammerTypeID;
	}

	public void setHammerTypeID(int hammerTypeID)
	{
		this.hammerTypeID = hammerTypeID;
	}

	public int getDistrictID()
	{
		return districtID;
	}

	public void setDistrictID(int districtID)
	{
		this.districtID = districtID;
	}

	public String getContractorID()
	{
		return contractorID;
	}

	public void setContractorID(String contractorID)
	{
		this.contractorID = contractorID;
	}

	public int getStateID()
	{
		return stateID;
	}

	public void setStateID(int stateID)
	{
		this.stateID = stateID;
	}

	public String getHammerTypeName()
	{
		return hammerTypeName;
	}

	public void setHammerTypeName(String hammerTypeName)
	{
		this.hammerTypeName = hammerTypeName;
	}

	public String getDistrictName()
	{
		return districtName;
	}

	public void setDistrictName(String districtName)
	{
		this.districtName = districtName;
	}

	public String getStateName()
	{
		return stateName;
	}

	public void setStateName(String stateName)
	{
		this.stateName = stateName;
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
		return hammerNo;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof Hammer)
		{
			Hammer that = (Hammer) obj;
			equal = hammerNo.equals(that.hammerNo);
		}

		return equal;
	}
}