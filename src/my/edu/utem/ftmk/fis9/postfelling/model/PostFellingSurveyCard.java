package my.edu.utem.ftmk.fis9.postfelling.model;

import java.util.ArrayList;
import java.util.Date;

import my.edu.utem.ftmk.fis9.global.model.FieldWorksheet;

/**
 * @author Satrya Fajri Pratama, Zurina
 */
public class PostFellingSurveyCard extends FieldWorksheet<PostFellingSurveyRecord>
{
	private static final long serialVersionUID = VERSION;
	private String lineNo;
	private String plotNo;
	private double latitude;
	private double longitude;
	private int forestTypeID;
	private int soilStatusID;
	private int aspectID;
	private int slope;
	private int slopeLocationID;
	private int elevationID;
	private int bertam;
	private int bamboo;
	private int palm;
	private int resamID;
	private int gingerID;
	private int bananaID;
	private String forestType;
	private String soilStatus;
	private String aspect;
	private String slopeLocation;
	private String elevation;
	private String resam;
	private String ginger;
	private String banana;
	private int inspectionForestTypeID;
	private int inspectionSoilStatusID;
	private int inspectionAspectID;
	private int inspectionSlope;
	private int inspectionSlopeLocationID;
	private int inspectionElevationID;
	private int inspectionBertam;
	private int inspectionBamboo;
	private int inspectionPalm;
	private int inspectionResamID;
	private int inspectionGingerID;
	private int inspectionBananaID;
	private String inspectionForestType;
	private String inspectionSoilStatus;
	private String inspectionAspect;
	private String inspectionSlopeLocation;
	private String inspectionElevation;
	private String inspectionResam;
	private String inspectionGinger;
	private String inspectionBanana;
	private boolean inspection;
	
	public long getPostFellingSurveyCardID()
	{
		return getFieldWorksheetID();
	}

	public void setPostFellingSurveyCardID(long postFellingSurveyCardID)
	{
		setFieldWorksheetID(postFellingSurveyCardID);
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

	public int getSlope()
	{
		return slope;
	}

	public void setSlope(int slope)
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

	public int getBertam()
	{
		return bertam;
	}

	public void setBertam(int bertam)
	{
		this.bertam = bertam;
	}

	public int getPalm()
	{
		return palm;
	}

	public void setPalm(int palm)
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

	public int getBamboo() 
	{
		return bamboo;
	}

	public void setBamboo(int bamboo) 
	{
		this.bamboo = bamboo;
	}

	public int getGingerID() 
	{
		return gingerID;
	}

	public void setGingerID(int gingerID) 
	{
		this.gingerID = gingerID;
	}

	public int getBananaID() {
		return bananaID;
	}

	public void setBananaID(int bananaID) 
	{
		this.bananaID = bananaID;
	}

	public Date getSurveyDate()
	{
		return getFieldWorkDate();
	}

	public void setSurveyDate(Date postFellingSurveyDate)
	{
		setFieldWorkDate(postFellingSurveyDate);
	}

	public long getPostFellingSurveyID()
	{
		return getFieldWorkID();
	}

	public void setPostFellingSurveyID(long postFellingSurveyID)
	{
		setFieldWorkID(postFellingSurveyID);
	}

	public String getForestType()
	{
		return forestType;
	}

	public void setForestType(String forestType)
	{
		this.forestType = forestType;
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

	public String getGinger() 
	{
		return ginger;
	}

	public void setGinger(String ginger) 
	{
		this.ginger = ginger;
	}

	public String getBanana() 
	{
		return banana;
	}

	public void setBanana(String banana) 
	{
		this.banana = banana;
	}
	

	public int getInspectionForestTypeID()
	{
		return inspectionForestTypeID;
	}

	public void setInspectionForestTypeID(int inspectionForestTypeID)
	{
		this.inspectionForestTypeID = inspectionForestTypeID;
	}

	public int getInspectionSoilStatusID()
	{
		return inspectionSoilStatusID;
	}

	public void setInspectionSoilStatusID(int inspectionSoilStatusID)
	{
		this.inspectionSoilStatusID = inspectionSoilStatusID;
	}

	public int getInspectionAspectID()
	{
		return inspectionAspectID;
	}

	public void setInspectionAspectID(int inspectionAspectID)
	{
		this.inspectionAspectID = inspectionAspectID;
	}

	public int getInspectionSlope()
	{
		return inspectionSlope;
	}

	public void setInspectionSlope(int inspectionSlope)
	{
		this.inspectionSlope = inspectionSlope;
	}

	public int getInspectionSlopeLocationID()
	{
		return inspectionSlopeLocationID;
	}

	public void setInspectionSlopeLocationID(int inspectionSlopeLocationID)
	{
		this.inspectionSlopeLocationID = inspectionSlopeLocationID;
	}

	public int getInspectionElevationID()
	{
		return inspectionElevationID;
	}

	public void setInspectionElevationID(int inspectionElevationID)
	{
		this.inspectionElevationID = inspectionElevationID;
	}

	public int getInspectionBertam()
	{
		return inspectionBertam;
	}

	public void setInspectionBertam(int inspectionBertam)
	{
		this.inspectionBertam = inspectionBertam;
	}

	public int getInspectionBamboo()
	{
		return inspectionBamboo;
	}

	public void setInspectionBamboo(int inspectionBamboo)
	{
		this.inspectionBamboo = inspectionBamboo;
	}

	public int getInspectionPalm()
	{
		return inspectionPalm;
	}

	public void setInspectionPalm(int inspectionPalm)
	{
		this.inspectionPalm = inspectionPalm;
	}

	public int getInspectionResamID()
	{
		return inspectionResamID;
	}

	public void setInspectionResamID(int inspectionResamID)
	{
		this.inspectionResamID = inspectionResamID;
	}

	public int getInspectionGingerID()
	{
		return inspectionGingerID;
	}

	public void setInspectionGingerID(int inspectionGingerID)
	{
		this.inspectionGingerID = inspectionGingerID;
	}

	public int getInspectionBananaID()
	{
		return inspectionBananaID;
	}

	public void setInspectionBananaID(int inspectionBananaID)
	{
		this.inspectionBananaID = inspectionBananaID;
	}

	public String getInspectionForestType()
	{
		return inspectionForestType;
	}

	public void setInspectionForestType(String inspectionForestType)
	{
		this.inspectionForestType = inspectionForestType;
	}

	public String getInspectionSoilStatus()
	{
		return inspectionSoilStatus;
	}

	public void setInspectionSoilStatus(String inspectionSoilStatus)
	{
		this.inspectionSoilStatus = inspectionSoilStatus;
	}

	public String getInspectionAspect()
	{
		return inspectionAspect;
	}

	public void setInspectionAspect(String inspectionAspect)
	{
		this.inspectionAspect = inspectionAspect;
	}

	public String getInspectionSlopeLocation()
	{
		return inspectionSlopeLocation;
	}

	public void setInspectionSlopeLocation(String inspectionSlopeLocation)
	{
		this.inspectionSlopeLocation = inspectionSlopeLocation;
	}

	public String getInspectionElevation()
	{
		return inspectionElevation;
	}

	public void setInspectionElevation(String inspectionElevation)
	{
		this.inspectionElevation = inspectionElevation;
	}

	public String getInspectionResam()
	{
		return inspectionResam;
	}

	public void setInspectionResam(String inspectionResam)
	{
		this.inspectionResam = inspectionResam;
	}

	public String getInspectionGinger()
	{
		return inspectionGinger;
	}

	public void setInspectionGinger(String inspectionGinger)
	{
		this.inspectionGinger = inspectionGinger;
	}

	public String getInspectionBanana()
	{
		return inspectionBanana;
	}

	public void setInspectionBanana(String inspectionBanana)
	{
		this.inspectionBanana = inspectionBanana;
	}

	public ArrayList<PostFellingSurveyRecord> getPostFellingSurveyRecords()
	{
		return getFieldWorkRecords();
	}

	public void setPostFellingSurveyRecords(ArrayList<PostFellingSurveyRecord> postFellingSurveyRecords)
	{
		setFieldWorkRecords(postFellingSurveyRecords);
	}
	
	public ArrayList<PostFellingSurveyRecord> getPostFellingSurveyRecordsForInspection()
	{
		return getFieldWorkRecords();
	}

	public void setPostFellingSurveyRecordsForInspection(ArrayList<PostFellingSurveyRecord> postFellingSurveyRecords)
	{
		setFieldWorkRecords(postFellingSurveyRecords);
	}

	public int getRecordCount(String plotTypeName)
	{
		int count = 0;

		for (PostFellingSurveyRecord postFellingSurveyRecord : getPostFellingSurveyRecords())
			if (postFellingSurveyRecord.getPlotTypeName().equals(plotTypeName))
				count++;

		return count;
	}
	
	

	public boolean isInspection()
	{
		return inspection;
	}

	public void setInspection(boolean inspection)
	{
		this.inspection = inspection;
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

		if (obj instanceof PostFellingSurveyCard)
		{
			PostFellingSurveyCard that = (PostFellingSurveyCard) obj;
			equal = getPostFellingSurveyCardID() == that.getPostFellingSurveyCardID() || toString().equals(that.toString());
		}

		return equal;
	}
}