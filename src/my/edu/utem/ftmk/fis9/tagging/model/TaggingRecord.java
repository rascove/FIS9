package my.edu.utem.ftmk.fis9.tagging.model;

import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;
import my.edu.utem.ftmk.fis9.global.model.FieldWorkRecord;
import my.edu.utem.ftmk.fis9.hall.model.Log;

/**
 * @author Satrya Fajri Pratama
 */
public class TaggingRecord extends FieldWorkRecord
{
	private static final long serialVersionUID = VERSION;
	private String plotNo;
	private String serialNo;
	private int treeTypeID;
	private int correctedSpeciesID;
	private String correctedSpeciesCode;
	private String correctedSpeciesName;
	private Integer estimation;
	private Integer correctedEstimation;
	private String note;
	private double latitude;
	private double longitude;
	private String treeTypeName;
	private String status;
	private ArrayList<Log> logs;

	public long getTaggingRecordID()
	{
		return getFieldWorkRecordID();
	}

	public void setTaggingRecordID(long taggingRecordID)
	{
		setFieldWorkRecordID(taggingRecordID);
	}

	public String getPlotNo()
	{
		return plotNo;
	}

	public void setPlotNo(String plotNo)
	{
		this.plotNo = plotNo;
	}

	public String getSerialNo()
	{
		return serialNo;
	}

	public void setSerialNo(String serialNo)
	{
		this.serialNo = serialNo;
	}

	public int getTreeTypeID()
	{
		return treeTypeID;
	}

	public void setTreeTypeID(int treeTypeID)
	{
		this.treeTypeID = treeTypeID;
	}

	public int getCorrectedSpeciesID() {
		return correctedSpeciesID;
	}

	public void setCorrectedSpeciesID(int correctedSpeciesID) {
		this.correctedSpeciesID = correctedSpeciesID;
	}

	public String getCorrectedSpeciesCode() {
		return correctedSpeciesCode;
	}

	public void setCorrectedSpeciesCode(String correctedSpeciesCode) {
		this.correctedSpeciesCode = correctedSpeciesCode;
	}

	public String getCorrectedSpeciesName() {
		return correctedSpeciesName;
	}

	public void setCorrectedSpeciesName(String correctedSpeciesName) {
		this.correctedSpeciesName = correctedSpeciesName;
	}

	public Integer getEstimation()
	{
		return estimation;
	}

	public void setEstimation(Integer estimation)
	{
		this.estimation = estimation;
	}

	public Integer getCorrectedEstimation() {
		return correctedEstimation;
	}

	public void setCorrectedEstimation(Integer correctedEstimation) {
		this.correctedEstimation = correctedEstimation;
	}

	public String getNote()
	{
		return note;
	}

	public void setNote(String note)
	{
		this.note = note;
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

	public long getTaggingFormID()
	{
		return getFieldWorksheetID();
	}

	public void setTaggingFormID(long taggingFormID)
	{
		setFieldWorksheetID(taggingFormID);
	}

	public String getTreeTypeName()
	{
		return treeTypeName;
	}

	public void setTreeTypeName(String treeTypeName)
	{
		this.treeTypeName = treeTypeName;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public ArrayList<Log> getLogs()
	{
		return logs;
	}

	public void setLogs(ArrayList<Log> logs)
	{
		this.logs = logs;
	}

	@Override
	public String toString()
	{
		return "Maklumat penandaan" + (plotNo != null ? " no. petak " + plotNo : "") + " no. siri pokok " + serialNo;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = super.equals(obj);

		if (!equal && obj instanceof TaggingRecord)
		{
			TaggingRecord that = (TaggingRecord) obj;

			if (that.serialNo != null)
			{
				String current = serialNo.toUpperCase().replaceAll("\\s", "");
				String other = that.serialNo.toUpperCase().replaceAll("\\s", "");
				equal = current.equals(other);
			}
		}

		return equal;
	}

	@Override
	public int compareTo(AbstractModel model)
	{
		TaggingRecord that = (TaggingRecord) model;

		return serialNo.compareTo(that.serialNo);
	}
}