package my.edu.utem.ftmk.fis9.prefelling.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;
import my.edu.utem.ftmk.fis9.global.model.FieldWorkRecord;

/**
 * @author Satrya Fajri Pratama
 */
public class PreFellingSurveyRecord extends FieldWorkRecord
{
	private static final long serialVersionUID = VERSION;
	private Integer logQuantity;
	private int fertilityID;
	private Integer vineDiameter;
	private int vineSpreadthID;
	private Integer saplingQuantity;
	private Integer seedlingQuantity;
	private int timberGroupID;
	private int plotTypeID;
	private String fertility;
	private String fertilityName;
	private String vineSpreadth;
	private String vineSpreadthName;
	private String plotTypeName;

	public long getPreFellingSurveyRecordID()
	{
		return getFieldWorkRecordID();
	}

	public void setPreFellingSurveyRecordID(long preFellingSurveyRecordID)
	{
		setFieldWorkRecordID(preFellingSurveyRecordID);
	}
	
	public final void setDiameter(Double diameter)
	{
		super.setDiameter(diameter);
		int heightLimit = 0;
		
		if (diameter > 15 && diameter <= 30)
		{
			setNett(0.5);
			heightLimit = 5;
		}
		else if (diameter > 30 && diameter <= 60)
		{
			setNett(diameter <= 45 ? 0.6 : 0.7);
			heightLimit = 10;
		}
		else if (diameter > 60)
		{
			setNett(0.8);
			heightLimit = diameter <= 75 ? 15 : 20;
		}
		
		setVolume(getBasalArea() * 0.65 * heightLimit);
	}

	public Integer getLogQuantity()
	{
		return logQuantity;
	}

	public void setLogQuantity(Integer logQuantity)
	{
		this.logQuantity = logQuantity;
	}
	
	public int getFertilityID()
	{
		return fertilityID;
	}

	public void setFertilityID(int fertilityID)
	{
		this.fertilityID = fertilityID;
	}
	
	public Integer getVineDiameter()
	{
		return vineDiameter;
	}

	public void setVineDiameter(Integer vineDiameter)
	{
		this.vineDiameter = vineDiameter;
	}
	
	public int getVineSpreadthID()
	{
		return vineSpreadthID;
	}

	public void setVineSpreadthID(int vineSpreadthID)
	{
		this.vineSpreadthID = vineSpreadthID;
	}

	public Integer getSaplingQuantity()
	{
		return saplingQuantity;
	}

	public void setSaplingQuantity(Integer saplingQuantity)
	{
		this.saplingQuantity = saplingQuantity;
	}

	public Integer getSeedlingQuantity()
	{
		return seedlingQuantity;
	}

	public void setSeedlingQuantity(Integer seedlingQuantity)
	{
		this.seedlingQuantity = seedlingQuantity;
	}

	public long getPreFellingSurveyCardID()
	{
		return getFieldWorksheetID();
	}

	public void setPreFellingSurveyCardID(long preFellingSurveyCardID)
	{
		setFieldWorksheetID(preFellingSurveyCardID);
	}

	public int getTimberGroupID()
	{
		return timberGroupID;
	}

	public void setTimberGroupID(int timberGroupID)
	{
		this.timberGroupID = timberGroupID;
	}
	
	public int getPlotTypeID()
	{
		return plotTypeID;
	}

	public void setPlotTypeID(int plotTypeID)
	{
		this.plotTypeID = plotTypeID;
	}
	
	public String getFertility()
	{
		return fertility;
	}

	public void setFertility(String fertility)
	{
		this.fertility = fertility;
	}
	
	public String getFertilityName()
	{
		return fertilityName;
	}

	public void setFertilityName(String fertilityName)
	{
		this.fertilityName = fertilityName;
	}
	
	public String getVineSpreadth()
	{
		return vineSpreadth;
	}

	public void setVineSpreadth(String vineSpreadth)
	{
		this.vineSpreadth = vineSpreadth;
	}
	
	public String getVineSpreadthName()
	{
		return vineSpreadthName;
	}

	public void setVineSpreadthName(String vineSpreadthName)
	{
		this.vineSpreadthName = vineSpreadthName;
	}

	public String getPlotTypeName()
	{
		return plotTypeName;
	}

	public void setPlotTypeName(String plotTypeName)
	{
		this.plotTypeName = plotTypeName;
	}

	@Override
	public String toString()
	{
		return "Maklumat pokok " + getSpeciesName().toLowerCase() + " dan pepanjat di " + plotTypeName.toLowerCase();
	}
	
	@Override
	public int compareTo(AbstractModel model)
	{
		PreFellingSurveyRecord that = (PreFellingSurveyRecord) model;
		int status = 0;
		
		if (plotTypeID < that.plotTypeID)
			status = -1;
		else if (plotTypeID > that.plotTypeID)
			status = 1;
		else
		{
			if (getSpeciesID() < that.getSpeciesID())
				status = -1;
			else if (getSpeciesID() > that.getSpeciesID())
				status = 1;
		}
		
		return status;
	}
}