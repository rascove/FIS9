package my.edu.utem.ftmk.fis9.tagging.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

public class TaggingLimitException extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private String blockNo;
	private String plotNo;
	private int quantity;
	private long taggingID;

	public String getBlockNo()
	{
		return blockNo;
	}

	public void setBlockNo(String blockNo)
	{
		this.blockNo = blockNo;
	}

	public String getPlotNo()
	{
		return plotNo;
	}

	public void setPlotNo(String plotNo)
	{
		this.plotNo = plotNo;
	}

	public int getQuantity()
	{
		return quantity;
	}

	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}

	public long getTaggingID()
	{
		return taggingID;
	}

	public void setTaggingID(long taggingID)
	{
		this.taggingID = taggingID;
	}

	@Override
	public String toString()
	{
		return "Pengecualian had pokok tebangan di no. blok " + blockNo + " dan no. petak " + plotNo + ": " + quantity + " pokok";
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof TaggingLimitException)
		{
			TaggingLimitException that = (TaggingLimitException) obj;
			equal = taggingID == that.taggingID && blockNo.equals(that.blockNo) && plotNo.equals(that.plotNo);
		}

		return equal;
	}
}