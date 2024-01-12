package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 */
public class TransactionFormMapTrustFund extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int transactionFormMapTrustFundID;
	private int transactionFormID;
	private String transactionFormCode;
	private String transactionFormName;
	private int trustFundID;
	private String trustFundCode;
	private String trustFundName;
	private String status;

	
	public int getTransactionFormMapTrustFundID() 
	{
		return transactionFormMapTrustFundID;
	}

	public void setTransactionFormMapTrustFundID(int transactionFormMapTrustFundID) 
	{
		this.transactionFormMapTrustFundID = transactionFormMapTrustFundID;
	}

	public int getTransactionFormID() 
	{
		return transactionFormID;
	}

	public void setTransactionFormID(int transactionFormID) 
	{
		this.transactionFormID = transactionFormID;
	}

	public String getTransactionFormCode() 
	{
		return transactionFormCode;
	}

	public void setTransactionFormCode(String transactionFormCode) 
	{
		this.transactionFormCode = transactionFormCode;
	}

	public String getTransactionFormName() 
	{
		return transactionFormName;
	}

	public void setTransactionFormName(String transactionFormName) 
	{
		this.transactionFormName = transactionFormName;
	}

	public int getTrustFundID() 
	{
		return trustFundID;
	}

	public void setTrustFundID(int trustFundID) 
	{
		this.trustFundID = trustFundID;
	}

	public String getTrustFundCode() 
	{
		return trustFundCode;
	}

	public void setTrustFundCode(String trustFundCode) 
	{
		this.trustFundCode = trustFundCode;
	}

	public String getTrustFundName() 
	{
		return trustFundName;
	}

	public void setTrustFundName(String trustFundName) 
	{
		this.trustFundName = trustFundName;
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
		return trustFundCode+"-"+trustFundName;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof TransactionFormMapTrustFund)
		{
			TransactionFormMapTrustFund that = (TransactionFormMapTrustFund) obj;
			equal = transactionFormMapTrustFundID == that.transactionFormMapTrustFundID;
		}

		return equal;
	}
}