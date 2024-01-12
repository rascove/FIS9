package my.edu.utem.ftmk.fis9.maintenance.model;

import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class Staff extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private String staffID;
	private String name;
	private String password;
	private int designationID;
	private boolean status;
	private boolean administrative;
	private int stateID;
	private String contractorID;
	private String designationName;
	private String stateName;
	private String contractorName;
	private ArrayList<Form> forms;

	public String getStaffID()
	{
		return staffID;
	}

	public void setStaffID(String staffID)
	{
		this.staffID = staffID;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public int getDesignationID()
	{
		return designationID;
	}

	public void setDesignationID(int designationID)
	{
		this.designationID = designationID;
	}

	public boolean getStatus()
	{
		return status;
	}

	public void setStatus(boolean status)
	{
		this.status = status;
	}

	public boolean isAdministrative()
	{
		return administrative;
	}

	public void setAdministrative(boolean administrative)
	{
		this.administrative = administrative;
	}

	public int getStateID()
	{
		return stateID;
	}

	public void setStateID(int stateID)
	{
		this.stateID = stateID;
	}

	public String getContractorID()
	{
		return contractorID;
	}

	public void setContractorID(String contractorID)
	{
		this.contractorID = contractorID;
	}

	public String getDesignationName()
	{
		return designationName;
	}

	public void setDesignationName(String designationName)
	{
		this.designationName = designationName;
	}

	public String getStateName()
	{
		return stateName;
	}

	public void setStateName(String stateName)
	{
		this.stateName = stateName;
	}

	public String getContractorName()
	{
		return contractorName;
	}

	public void setContractorName(String contractorName)
	{
		this.contractorName = contractorName;
	}

	public ArrayList<Form> getForms()
	{
		return forms;
	}

	public void setForms(ArrayList<Form> forms)
	{
		this.forms = forms;
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

		if (obj instanceof Staff)
		{
			Staff that = (Staff) obj;
			equal = staffID.equals(that.staffID);
		}

		return equal;
	}
}