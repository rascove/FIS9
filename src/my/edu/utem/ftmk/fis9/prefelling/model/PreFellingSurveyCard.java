package my.edu.utem.ftmk.fis9.prefelling.model;

import java.util.ArrayList;
import java.util.Date;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;
import my.edu.utem.ftmk.fis9.global.model.FieldWorksheet;

/**
 * @author Satrya Fajri Pratama
 */
public class PreFellingSurveyCard extends FieldWorksheet<PreFellingSurveyRecord>
{
	private static final long serialVersionUID = VERSION;
	private String lineNo;
	private String plotNo;
	private String subBaseNo;
	private double latitude;
	private double longitude;
	private int forestTypeID;
	private int soilTypeID;
	private int geologyID;
	private int areaStatusID;
	private int soilStatusID;
	private int aspectID;
	private Integer slope;
	private int slopeLocationID;
	private int elevationID;
	private Integer bertam;
	private Integer palm;
	private int resamID;
	private Integer rattanA;
	private Integer rattanB;
	private Integer rattanC;
	private Integer rattanD;
	private Integer rattanE;
	private Integer rattanF;
	private Integer rattanG;
	private Integer bambooA;
	private Integer bambooB;
	private Integer bambooC;
	private String forestType;
	private String soilType;
	private String geology;
	private String areaStatus;
	private String soilStatus;
	private String aspect;
	private String slopeLocation;
	private String elevation;
	private String resam;

	public long getPreFellingSurveyCardID()
	{
		return getFieldWorksheetID();
	}

	public void setPreFellingSurveyCardID(long preFellingSurveyCardID)
	{
		setFieldWorksheetID(preFellingSurveyCardID);
	}

	public String getLineNo()
	{
		return lineNo;
	}

	public void setLineNo(String lineNo)
	{
		this.lineNo = lineNo;
	}

	public String getPlotNo()
	{
		return plotNo;
	}

	public void setPlotNo(String plotNo)
	{
		this.plotNo = plotNo;
	}

	public String getSubBaseNo()
	{
		return subBaseNo;
	}

	public void setSubBaseNo(String subBaseNo)
	{
		this.subBaseNo = subBaseNo;
	}

	public double getLatitude()
	{
		return latitude;
	}

	public void setLatitude(double latitude)
	{
		this.latitude = latitude;
	}

	public double getLongitude()
	{
		return longitude;
	}

	public void setLongitude(double longitude)
	{
		this.longitude = longitude;
	}

	public int getForestTypeID()
	{
		return forestTypeID;
	}

	public void setForestTypeID(int forestTypeID)
	{
		this.forestTypeID = forestTypeID;
	}

	public int getSoilTypeID()
	{
		return soilTypeID;
	}

	public void setSoilTypeID(int soilTypeID)
	{
		this.soilTypeID = soilTypeID;
	}

	public int getGeologyID()
	{
		return geologyID;
	}

	public void setGeologyID(int geologyID)
	{
		this.geologyID = geologyID;
	}

	public int getAreaStatusID()
	{
		return areaStatusID;
	}

	public void setAreaStatusID(int areaStatusID)
	{
		this.areaStatusID = areaStatusID;
	}

	public int getSoilStatusID()
	{
		return soilStatusID;
	}

	public void setSoilStatusID(int soilStatusID)
	{
		this.soilStatusID = soilStatusID;
	}

	public int getAspectID()
	{
		return aspectID;
	}

	public void setAspectID(int aspectID)
	{
		this.aspectID = aspectID;
	}

	public Integer getSlope()
	{
		return slope;
	}

	public void setSlope(Integer slope)
	{
		this.slope = slope;
	}

	public int getSlopeLocationID()
	{
		return slopeLocationID;
	}

	public void setSlopeLocationID(int slopeLocationID)
	{
		this.slopeLocationID = slopeLocationID;
	}

	public int getElevationID()
	{
		return elevationID;
	}

	public void setElevationID(int elevationID)
	{
		this.elevationID = elevationID;
	}

	public Integer getBertam()
	{
		return bertam;
	}

	public void setBertam(Integer bertam)
	{
		this.bertam = bertam;
	}

	public Integer getPalm()
	{
		return palm;
	}

	public void setPalm(Integer palm)
	{
		this.palm = palm;
	}

	public int getResamID()
	{
		return resamID;
	}

	public void setResamID(int resamID)
	{
		this.resamID = resamID;
	}

	public Integer getRattanA()
	{
		return rattanA;
	}

	public void setRattanA(Integer rattanA)
	{
		this.rattanA = rattanA;
	}

	public Integer getRattanB()
	{
		return rattanB;
	}

	public void setRattanB(Integer rattanB)
	{
		this.rattanB = rattanB;
	}

	public Integer getRattanC()
	{
		return rattanC;
	}

	public void setRattanC(Integer rattanC)
	{
		this.rattanC = rattanC;
	}

	public Integer getRattanD()
	{
		return rattanD;
	}

	public void setRattanD(Integer rattanD)
	{
		this.rattanD = rattanD;
	}

	public Integer getRattanE()
	{
		return rattanE;
	}

	public void setRattanE(Integer rattanE)
	{
		this.rattanE = rattanE;
	}

	public Integer getRattanF()
	{
		return rattanF;
	}

	public void setRattanF(Integer rattanF)
	{
		this.rattanF = rattanF;
	}

	public Integer getRattanG()
	{
		return rattanG;
	}

	public void setRattanG(Integer rattanG)
	{
		this.rattanG = rattanG;
	}

	public Integer getBambooA()
	{
		return bambooA;
	}

	public void setBambooA(Integer bambooA)
	{
		this.bambooA = bambooA;
	}

	public Integer getBambooB()
	{
		return bambooB;
	}

	public void setBambooB(Integer bambooB)
	{
		this.bambooB = bambooB;
	}

	public Integer getBambooC()
	{
		return bambooC;
	}

	public void setBambooC(Integer bambooC)
	{
		this.bambooC = bambooC;
	}

	public Date getSurveyDate()
	{
		return getFieldWorkDate();
	}

	public void setSurveyDate(Date surveyDate)
	{
		setFieldWorkDate(surveyDate);
	}

	public long getPreFellingSurveyID()
	{
		return getFieldWorkID();
	}

	public void setPreFellingSurveyID(long preFellingSurveyID)
	{
		setFieldWorkID(preFellingSurveyID);
	}

	public String getForestType()
	{
		return forestType;
	}

	public void setForestType(String forestType)
	{
		this.forestType = forestType;
	}

	public String getSoilType()
	{
		return soilType;
	}

	public void setSoilType(String soilType)
	{
		this.soilType = soilType;
	}

	public String getGeology()
	{
		return geology;
	}

	public void setGeology(String geology)
	{
		this.geology = geology;
	}

	public String getAreaStatus()
	{
		return areaStatus;
	}

	public void setAreaStatus(String areaStatus)
	{
		this.areaStatus = areaStatus;
	}

	public String getSoilStatus()
	{
		return soilStatus;
	}

	public void setSoilStatus(String soilStatus)
	{
		this.soilStatus = soilStatus;
	}

	public String getAspect()
	{
		return aspect;
	}

	public void setAspect(String aspect)
	{
		this.aspect = aspect;
	}

	public String getSlopeLocation()
	{
		return slopeLocation;
	}

	public void setSlopeLocation(String slopeLocation)
	{
		this.slopeLocation = slopeLocation;
	}

	public String getElevation()
	{
		return elevation;
	}

	public void setElevation(String elevation)
	{
		this.elevation = elevation;
	}

	public String getResam()
	{
		return resam;
	}

	public void setResam(String resam)
	{
		this.resam = resam;
	}

	public ArrayList<PreFellingSurveyRecord> getPreFellingSurveyRecords()
	{
		return getFieldWorkRecords();
	}

	public void setPreFellingSurveyRecords(ArrayList<PreFellingSurveyRecord> preFellingSurveyRecords)
	{
		setFieldWorkRecords(preFellingSurveyRecords);
	}

	public int getRecordCount(String plotTypeName)
	{
		int count = 0;

		for (PreFellingSurveyRecord preFellingSurveyRecord : getPreFellingSurveyRecords())
			if (preFellingSurveyRecord.getPlotTypeName().equals(plotTypeName))
				count++;

		return count;
	}

	@Override
	public String toString()
	{
		return "Kad bancian no. garisan " + lineNo + " dan no. petak " + plotNo;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof PreFellingSurveyCard)
		{
			PreFellingSurveyCard that = (PreFellingSurveyCard) obj;
			equal = getPreFellingSurveyCardID() == that.getPreFellingSurveyCardID() || toString().equals(that.toString());
		}

		return equal;
	}

	@Override
	public int compareTo(AbstractModel model)
	{
		PreFellingSurveyCard that = (PreFellingSurveyCard) model;
		Integer current = new Integer(lineNo.replaceAll("\\D", ""));
		Integer other = new Integer(that.lineNo.replaceAll("\\D", ""));
		int status = current.compareTo(other);

		if (status == 0)
		{
			current = new Integer(plotNo.replaceAll("\\D", ""));
			other = new Integer(that.plotNo.replaceAll("\\D", ""));
			status = current.compareTo(other);

			if (status == 0)
				status = plotNo.compareTo(that.plotNo);
		}

		return status;
	}
}