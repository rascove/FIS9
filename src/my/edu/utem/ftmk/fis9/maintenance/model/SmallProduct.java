package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 */
public class SmallProduct extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int smallProductID;
	private int productGroupID;
	private String productGroupCode;
	private String productGroupName;
	private String code;
	private String name;
	private String status;

	public int getSmallProductID() 
	{
		return smallProductID;
	}

	public void setSmallProductID(int smallProductID) 
	{
		this.smallProductID = smallProductID;
	}

	public int getProductGroupID() 
	{
		return productGroupID;
	}

	public void setProductGroupID(int productGroupID) 
	{
		this.productGroupID = productGroupID;
	}

	public String getProductGroupCode() 
	{
		return productGroupCode;
	}

	public void setProductGroupCode(String productGroupCode) 
	{
		this.productGroupCode = productGroupCode;
	}

	public String getProductGroupName() 
	{
		return productGroupName;
	}

	public void setProductGroupName(String productGroupName) 
	{
		this.productGroupName = productGroupName;
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

	public String getStatus() 
	{
		return status;
	}

	public void setStatus(String status) 
	{
		this.status = status;
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

		if (obj instanceof SmallProduct)
		{
			SmallProduct that = (SmallProduct) obj;
			equal = smallProductID == that.smallProductID;
		}

		return equal;
	}
}