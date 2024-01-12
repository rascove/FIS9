package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 * @author Satrya Fajri Pratama
 */
public class Contractor extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private String contractorID;
	private String type;
	private String name;
	private String companyName;
	private String registrationNo;
	private String address;
	private String postcode;
	private int regionID;
	private String telNo;
	private String faxNo;
	private String status;
	private String regionName;

	public String getContractorID()
	{
		return contractorID;
	}

	public void setContractorID(String contractorID)
	{
		this.contractorID = contractorID;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getCompanyName()
	{
		return companyName;
	}

	public void setCompanyName(String companyName)
	{
		this.companyName = companyName;
	}
	
	public String getRegistrationNo() 
	{
		return registrationNo;
	}

	public void setRegistrationNo(String registrationNo) 
	{
		this.registrationNo = registrationNo;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getPostcode()
	{
		return postcode;
	}

	public void setPostcode(String postcode)
	{
		this.postcode = postcode;
	}

	public int getRegionID()
	{
		return regionID;
	}

	public void setRegionID(int regionID)
	{
		this.regionID = regionID;
	}

	public String getTelNo()
	{
		return telNo;
	}

	public void setTelNo(String telNo)
	{
		this.telNo = telNo;
	}

	public String getFaxNo()
	{
		return faxNo;
	}

	public void setFaxNo(String faxNo)
	{
		this.faxNo = faxNo;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getRegionName()
	{
		return regionName;
	}

	public void setRegionName(String regionName)
	{
		this.regionName = regionName;
	}
	
	@Override
	public String toString()
	{
		return contractorID;
	}

	@Override
	public boolean equals(Object obj)
	{
		return toString().equals(obj.toString());
	}
}