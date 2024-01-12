package my.edu.utem.ftmk.fis9.tagging.model;

import java.util.ArrayList;
import java.util.Date;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;
import my.edu.utem.ftmk.fis9.global.model.FieldWorksheet;

/**
 * @author Satrya Fajri Pratama
 */
public class TaggingForm extends FieldWorksheet<TaggingRecord>
{
	private static final long serialVersionUID = VERSION;
	private String formNo;
	private Double length;
	private Double width;
	private int taggingTypeID;
	private String taggingTypeName;

	public long getTaggingFormID()
	{
		return getFieldWorksheetID();
	}

	public void setTaggingFormID(long taggingFormID)
	{
		setFieldWorksheetID(taggingFormID);
	}

	public String getFormNo()
	{
		return formNo;
	}

	public void setFormNo(String formNo)
	{
		this.formNo = formNo;
	}

	public Double getLength()
	{
		return length;
	}

	public void setLength(Double length)
	{
		this.length = length;
	}

	public Double getWidth()
	{
		return width;
	}

	public void setWidth(Double width)
	{
		this.width = width;
	}

	public Date getTaggingDate()
	{
		return getFieldWorkDate();
	}

	public void setTaggingDate(Date taggingDate)
	{
		setFieldWorkDate(taggingDate);
	}

	public long getTaggingID()
	{
		return getFieldWorkID();
	}

	public void setTaggingID(long taggingID)
	{
		setFieldWorkID(taggingID);
	}

	public int getTaggingTypeID()
	{
		return taggingTypeID;
	}

	public void setTaggingTypeID(int taggingTypeID)
	{
		this.taggingTypeID = taggingTypeID;
	}
	
	public String getTaggingTypeName()
	{
		return taggingTypeName;
	}

	public void setTaggingTypeName(String taggingTypeName)
	{
		this.taggingTypeName = taggingTypeName;
	}

	public ArrayList<TaggingRecord> getTaggingRecords()
	{
		return getFieldWorkRecords();
	}

	public void setTaggingRecords(ArrayList<TaggingRecord> taggingRecords)
	{
		setFieldWorkRecords(taggingRecords);
	}

	@Override
	public String toString()
	{
		return "Borang penandaan " + taggingTypeName + " no. " + formNo;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof TaggingForm)
		{
			TaggingForm that = (TaggingForm) obj;
			equal = getTaggingFormID() == that.getTaggingFormID() || toString().equals(that.toString());
		}

		return equal;
	}
	
	@Override
	public int compareTo(AbstractModel model)
	{
		TaggingForm that = (TaggingForm) model;
		int status = 0;
		
		if (taggingTypeID < that.taggingTypeID)
			status = -1;
		else if (taggingTypeID > that.taggingTypeID)
			status = 1;
		else
		{
			Integer current = new Integer(formNo.replaceAll("\\D", ""));
			Integer other = new Integer(that.formNo.replaceAll("\\D", ""));
			status = current.compareTo(other);
			
			if (status == 0)
				status = formNo.compareTo(that.formNo);
		}
		
		return status;
	}
}