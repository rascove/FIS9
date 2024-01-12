package my.edu.utem.ftmk.fis9.global.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public abstract class FieldWorkRecord extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private long fieldWorkRecordID;
	private long fieldWorksheetID;
	private Double diameter;
	private double nett;
	private double basalArea;
	private double volume;
	private double netVolume;
	private int speciesID;
	private int speciesTypeID;
	private int logQualityID;
	private String speciesCode;
	private String speciesName;
	private String logQuality;
	private String logQualityName;

	protected final long getFieldWorkRecordID()
	{
		return fieldWorkRecordID;
	}

	protected final void setFieldWorkRecordID(long fieldWorkRecordID)
	{
		this.fieldWorkRecordID = fieldWorkRecordID;
	}

	protected final long getFieldWorksheetID()
	{
		return fieldWorksheetID;
	}

	protected final void setFieldWorksheetID(long fieldWorksheetID)
	{
		this.fieldWorksheetID = fieldWorksheetID;
	}

	public final int getSpeciesID()
	{
		return speciesID;
	}

	public final void setSpeciesID(int speciesID)
	{
		this.speciesID = speciesID;
	}

	public final int getSpeciesTypeID()
	{
		return speciesTypeID;
	}

	public final void setSpeciesTypeID(int speciesTypeID)
	{
		this.speciesTypeID = speciesTypeID;
	}

	public final int getLogQualityID()
	{
		return logQualityID;
	}

	public final void setLogQualityID(int logQualityID)
	{
		this.logQualityID = logQualityID;
	}

	public final Double getDiameter()
	{
		return diameter;
	}

	public void setDiameter(Double diameter)
	{
		this.diameter = diameter;
		this.basalArea = (Math.PI * Math.pow(diameter, 2)) / 40000;
	}

	public final double getNett()
	{
		return nett;
	}

	public final void setNett(double nett)
	{
		this.nett = nett;
	}

	public final double getBasalArea()
	{
		return basalArea;
	}

	public final double getVolume()
	{
		return volume;
	}

	public final void setVolume(double volume)
	{
		this.volume = volume;
		this.netVolume = (diameter < 60 ? 0.6 : 0.7) * volume;
	}

	public final double getNetVolume()
	{
		return netVolume;
	}

	public final void setNetVolume(double netVolume)
	{
		this.netVolume = netVolume;
	}

	public final String getSpeciesCode()
	{
		return speciesCode;
	}

	public final void setSpeciesCode(String speciesCode)
	{
		this.speciesCode = speciesCode;
	}

	public final String getSpeciesName()
	{
		return speciesName;
	}

	public final void setSpeciesName(String speciesName)
	{
		this.speciesName = speciesName;
	}

	public final String getLogQuality()
	{
		return logQuality;
	}

	public final void setLogQuality(String logQuality)
	{
		this.logQuality = logQuality;
	}

	public final String getLogQualityName()
	{
		return logQualityName;
	}

	public final void setLogQualityName(String logQualityName)
	{
		this.logQualityName = logQualityName;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof FieldWorkRecord)
		{
			FieldWorkRecord that = (FieldWorkRecord) obj;
			equal = fieldWorkRecordID == that.fieldWorkRecordID;
		}

		return equal;
	}
}