package my.edu.utem.ftmk.fis9.maintenance.model;

import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 */
public class AbstractsSpeciesGroup extends AbstractModel
{
	private static final long serialVersionUID = 1L;
	private int abstractsSpeciesGroupID;
	private String name;
	private ArrayList<AbstractsSpeciesGroupRecord> abstractsSpeciesGroupRecords;
	private String status;

	public int getAbstractsSpeciesGroupID() {
		return abstractsSpeciesGroupID;
	}

	public void setAbstractsSpeciesGroupID(int abstractsSpeciesGroupID) {
		this.abstractsSpeciesGroupID = abstractsSpeciesGroupID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<AbstractsSpeciesGroupRecord> getAbstractsSpeciesGroupRecords() {
		return abstractsSpeciesGroupRecords;
	}

	public void setAbstractsSpeciesGroupRecords(ArrayList<AbstractsSpeciesGroupRecord> abstractsSpeciesGroupRecords) {
		this.abstractsSpeciesGroupRecords = abstractsSpeciesGroupRecords;
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
		return name;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof AbstractsSpeciesGroup)
		{
			AbstractsSpeciesGroup that = (AbstractsSpeciesGroup) obj;
			equal = abstractsSpeciesGroupID == that.abstractsSpeciesGroupID;
		}

		return equal;
	}
}