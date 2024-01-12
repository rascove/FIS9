package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 */
public class AbstractsSpeciesGroupRecord extends AbstractModel
{
	private static final long serialVersionUID = 1L;
	private int abstractsSpeciesGroupRecordID;
	private int abstractsSpeciesGroupID;
	private String abstractsSpeciesGroupName;
	private int speciesID;
	private String speciesCode;
	private String speciesName;
	private String status;

	public int getAbstractsSpeciesGroupRecordID() {
		return abstractsSpeciesGroupRecordID;
	}

	public void setAbstractsSpeciesGroupRecordID(int abstractsSpeciesGroupRecordID) {
		this.abstractsSpeciesGroupRecordID = abstractsSpeciesGroupRecordID;
	}

	public int getAbstractsSpeciesGroupID() {
		return abstractsSpeciesGroupID;
	}

	public void setAbstractsSpeciesGroupID(int abstractsSpeciesGroupID) {
		this.abstractsSpeciesGroupID = abstractsSpeciesGroupID;
	}

	public String getAbstractsSpeciesGroupName() {
		return abstractsSpeciesGroupName;
	}

	public void setAbstractsSpeciesGroupName(String abstractsSpeciesGroupName) {
		this.abstractsSpeciesGroupName = abstractsSpeciesGroupName;
	}

	public int getSpeciesID() {
		return speciesID;
	}

	public void setSpeciesID(int speciesID) {
		this.speciesID = speciesID;
	}

	public String getSpeciesCode() {
		return speciesCode;
	}

	public void setSpeciesCode(String speciesCode) {
		this.speciesCode = speciesCode;
	}

	public String getSpeciesName() {
		return speciesName;
	}

	public void setSpeciesName(String speciesName) {
		this.speciesName = speciesName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString()
	{
		return speciesCode + "-" + speciesName;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof AbstractsSpeciesGroupRecord)
		{
			AbstractsSpeciesGroupRecord that = (AbstractsSpeciesGroupRecord) obj;
			equal = abstractsSpeciesGroupRecordID == that.abstractsSpeciesGroupRecordID;
		}

		return equal;
	}
}