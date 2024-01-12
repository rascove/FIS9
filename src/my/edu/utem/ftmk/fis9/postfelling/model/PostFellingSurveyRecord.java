package my.edu.utem.ftmk.fis9.postfelling.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;
import my.edu.utem.ftmk.fis9.global.model.FieldWorkRecord;

/**
 * @author Satrya Fajri Pratama, Zurina 
 */
public class PostFellingSurveyRecord extends FieldWorkRecord
{
	private static final long serialVersionUID = VERSION;
	private int logQuantity;
	private int treeStatusID;
	private int silaraID;
	private int dominanceID;
	private int vine;
	private int fertilityID;
	private int saplingQuantity;
	private int seedlingQuantity;
	private int plotTypeID;
	private String treeStatus;
	private String silara;
	private String dominance;
	private String fertility;
	private String fertilityName;
	private String plotTypeName;
	private int timberGroupID;
	private String treeStatusName;
	private String silaraName;
	private String dominanceName;
	private Double inspectionDiameter;
	private int inspectionLogQuantity;
	private int inspectionLogQualityID;
	private int inspectionTreeStatusID;
	private int inspectionSilaraID;
	private int inspectionDominanceID;
	private int inspectionVine;
	private int inspectionFertilityID;
	private int inspectionSaplingQuantity;
	private int inspectionSeedlingQuantity;
	private String inspectionLogQuality;
	private String inspectionLogQualityName;
	private String inspectionTreeStatus;
	private String inspectionSilara;
	private String inspectionDominance;
	private String inspectionFertility;
	private String inspectionFertilityName;
	private String inspectionTreeStatusName;
	private String inspectionSilaraName;
	private String inspectionDominanceName;
	private int inspectionSpeciesID;
	private String inspectionSpeciesCode;
	private String inspectionSpeciesName;
	private boolean inspection;
	
	
	public String getTreeStatusName() {
		return treeStatusName;
	}

	public void setTreeStatusName(String treeStatusName) {
		this.treeStatusName = treeStatusName;
	}

	public String getSilaraName() {
		return silaraName;
	}

	public void setSilaraName(String silaraName) {
		this.silaraName = silaraName;
	}

	public String getDominanceName() {
		return dominanceName;
	}

	public void setDominanceName(String dominanceName) {
		this.dominanceName = dominanceName;
	}

	public String getFertilityName() {
		return fertilityName;
	}

	public void setFertilityName(String fertilityName) {
		this.fertilityName = fertilityName;
	}

	public int getTimberGroupID() {
		return timberGroupID;
	}

	public void setTimberGroupID(int timberGroupID) {
		this.timberGroupID = timberGroupID;
	}

	public long getPostFellingSurveyRecordID()
	{
		return getFieldWorkRecordID();
	}

	public void setPostFellingSurveyRecordID(long postFellingSurveyRecordID)
	{
		setFieldWorkRecordID(postFellingSurveyRecordID);
	}

	public int getLogQuantity()
	{
		return logQuantity;
	}

	public void setLogQuantity(int logQuantity)
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
	

	public int getSaplingQuantity()
	{
		return saplingQuantity;
	}

	public void setSaplingQuantity(int saplingQuantity)
	{
		this.saplingQuantity = saplingQuantity;
	}

	public int getSeedlingQuantity()
	{
		return seedlingQuantity;
	}

	public void setSeedlingQuantity(int seedlingQuantity)
	{
		this.seedlingQuantity = seedlingQuantity;
	}

	public long getPostFellingSurveyCardID()
	{
		return getFieldWorksheetID();
	}

	public void setPostFellingSurveyCardID(long postFellingSurveyCardID)
	{
		setFieldWorksheetID(postFellingSurveyCardID);
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
	
	

	public int getTreeStatusID() {
		return treeStatusID;
	}

	public void setTreeStatusID(int treeStatusID) {
		this.treeStatusID = treeStatusID;
	}

	public int getSilaraID() {
		return silaraID;
	}

	public void setSilaraID(int silaraID) {
		this.silaraID = silaraID;
	}

	public int getDominanceID() 
	{
		return dominanceID;
	}

	public void setDominanceID(int dominanceID) 
	{
		this.dominanceID = dominanceID;
	}

	public int getVine() 
	{
		return vine;
	}

	public void setVine(int vine) 
	{
		this.vine = vine;
	}

	public String getTreeStatus() 
	{
		return treeStatus;
	}

	public void setTreeStatus(String treeStatus) 
	{
		this.treeStatus = treeStatus;
	}

	public String getSilara() 
	{
		return silara;
	}

	public void setSilara(String silara) 
	{
		this.silara = silara;
	}

	public String getDominance() 
	{
		return dominance;
	}

	public void setDominance(String dominance)
	{
		this.dominance = dominance;
	}

	public String getPlotTypeName()
	{
		return plotTypeName;
	}

	public void setPlotTypeName(String plotTypeName)
	{
		this.plotTypeName = plotTypeName;
	}

	
	public Double getInspectionDiameter()
	{
		return inspectionDiameter;
	}

	public void setInspectionDiameter(Double inspectionDiameter)
	{
		this.inspectionDiameter = inspectionDiameter;
	}

	public int getInspectionLogQuantity()
	{
		return inspectionLogQuantity;
	}

	public void setInspectionLogQuantity(int inspectionLogQuantity)
	{
		this.inspectionLogQuantity = inspectionLogQuantity;
	}

	public int getInspectionLogQualityID()
	{
		return inspectionLogQualityID;
	}

	public void setInspectionLogQualityID(int inspectionLogQualityID)
	{
		this.inspectionLogQualityID = inspectionLogQualityID;
	}

	public int getInspectionTreeStatusID()
	{
		return inspectionTreeStatusID;
	}

	public void setInspectionTreeStatusID(int inspectionTreeStatusID)
	{
		this.inspectionTreeStatusID = inspectionTreeStatusID;
	}

	public int getInspectionSilaraID()
	{
		return inspectionSilaraID;
	}

	public void setInspectionSilaraID(int inspectionSilaraID)
	{
		this.inspectionSilaraID = inspectionSilaraID;
	}

	public int getInspectionDominanceID()
	{
		return inspectionDominanceID;
	}

	public void setInspectionDominanceID(int inspectionDominanceID)
	{
		this.inspectionDominanceID = inspectionDominanceID;
	}

	public int getInspectionVine()
	{
		return inspectionVine;
	}

	public void setInspectionVine(int inspectionVine)
	{
		this.inspectionVine = inspectionVine;
	}

	public int getInspectionFertilityID()
	{
		return inspectionFertilityID;
	}

	public void setInspectionFertilityID(int inspectionFertilityID)
	{
		this.inspectionFertilityID = inspectionFertilityID;
	}

	public int getInspectionSaplingQuantity()
	{
		return inspectionSaplingQuantity;
	}

	public void setInspectionSaplingQuantity(int inspectionSaplingQuantity)
	{
		this.inspectionSaplingQuantity = inspectionSaplingQuantity;
	}

	public int getInspectionSeedlingQuantity()
	{
		return inspectionSeedlingQuantity;
	}

	public void setInspectionSeedlingQuantity(int inspectionSeedlingQuantity)
	{
		this.inspectionSeedlingQuantity = inspectionSeedlingQuantity;
	}
	

	public String getInspectionTreeStatus()
	{
		return inspectionTreeStatus;
	}

	public void setInspectionTreeStatus(String inspectionTreeStatus)
	{
		this.inspectionTreeStatus = inspectionTreeStatus;
	}

	public String getInspectionSilara()
	{
		return inspectionSilara;
	}

	public void setInspectionSilara(String inspectionSilara)
	{
		this.inspectionSilara = inspectionSilara;
	}

	public String getInspectionDominance()
	{
		return inspectionDominance;
	}

	public void setInspectionDominance(String inspectionDominance)
	{
		this.inspectionDominance = inspectionDominance;
	}

	public String getInspectionFertility()
	{
		return inspectionFertility;
	}

	public void setInspectionFertility(String inspectionFertility)
	{
		this.inspectionFertility = inspectionFertility;
	}

	public String getInspectionFertilityName()
	{
		return inspectionFertilityName;
	}

	public void setInspectionFertilityName(String inspectionFertilityName)
	{
		this.inspectionFertilityName = inspectionFertilityName;
	}

	public String getInspectionTreeStatusName()
	{
		return inspectionTreeStatusName;
	}

	public void setInspectionTreeStatusName(String inspectionTreeStatusName)
	{
		this.inspectionTreeStatusName = inspectionTreeStatusName;
	}

	public String getInspectionSilaraName()
	{
		return inspectionSilaraName;
	}

	public void setInspectionSilaraName(String inspectionSilaraName)
	{
		this.inspectionSilaraName = inspectionSilaraName;
	}

	public String getInspectionDominanceName()
	{
		return inspectionDominanceName;
	}

	public void setInspectionDominanceName(String inspectionDominanceName)
	{
		this.inspectionDominanceName = inspectionDominanceName;
	}

	public String getInspectionLogQuality()
	{
		return inspectionLogQuality;
	}

	public void setInspectionLogQuality(String inspectionLogQuality)
	{
		this.inspectionLogQuality = inspectionLogQuality;
	}
	

	public String getInspectionLogQualityName()
	{
		return inspectionLogQualityName;
	}

	public void setInspectionLogQualityName(String inspectionLogQualityName)
	{
		this.inspectionLogQualityName = inspectionLogQualityName;
	}

	
	public int getInspectionSpeciesID()
	{
		return inspectionSpeciesID;
	}

	public void setInspectionSpeciesID(int inspectionSpeciesID)
	{
		this.inspectionSpeciesID = inspectionSpeciesID;
	}
	
	

	public String getInspectionSpeciesCode()
	{
		return inspectionSpeciesCode;
	}

	public void setInspectionSpeciesCode(String inspectionSpeciesCode)
	{
		this.inspectionSpeciesCode = inspectionSpeciesCode;
	}

	public String getInspectionSpeciesName()
	{
		return inspectionSpeciesName;
	}

	public void setInspectionSpeciesName(String inspectionSpeciesName)
	{
		this.inspectionSpeciesName = inspectionSpeciesName;
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
			return "Maklumat pokok dan pepanjat di " + plotTypeName.toLowerCase();
		
	}
	
	@Override
	public int compareTo(AbstractModel model)
	{
		PostFellingSurveyRecord that = (PostFellingSurveyRecord) model;
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