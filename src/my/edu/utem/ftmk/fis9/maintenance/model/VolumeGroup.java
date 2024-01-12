package my.edu.utem.ftmk.fis9.maintenance.model;

import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class VolumeGroup extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int volumeGroupID;
	private String name;
	private int stateID;
	private String stateName;
	private ArrayList<VolumeSpecies> volumeSpeciesList;
	private ArrayList<VolumeDiameter> volumeDiameterList;

	public int getVolumeGroupID()
	{
		return volumeGroupID;
	}

	public void setVolumeGroupID(int volumeGroupID)
	{
		this.volumeGroupID = volumeGroupID;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getStateID()
	{
		return stateID;
	}

	public void setStateID(int stateID)
	{
		this.stateID = stateID;
	}

	public String getStateName()
	{
		return stateName;
	}

	public void setStateName(String stateName)
	{
		this.stateName = stateName;
	}

	public ArrayList<VolumeSpecies> getVolumeSpeciesList()
	{
		return volumeSpeciesList;
	}

	public void setVolumeSpeciesList(ArrayList<VolumeSpecies> volumeSpeciesList)
	{
		this.volumeSpeciesList = volumeSpeciesList;
	}
	
	public ArrayList<VolumeDiameter> getVolumeDiameterList()
	{
		return volumeDiameterList;
	}

	public void setVolumeDiameterList(ArrayList<VolumeDiameter> volumeDiameterList)
	{
		this.volumeDiameterList = volumeDiameterList;
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

		if (obj instanceof VolumeGroup)
		{
			VolumeGroup that = (VolumeGroup) obj;
			equal = volumeGroupID == that.volumeGroupID;
		}

		return equal;
	}
}