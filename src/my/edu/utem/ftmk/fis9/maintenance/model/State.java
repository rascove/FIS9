package my.edu.utem.ftmk.fis9.maintenance.model;

import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class State extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int stateID;
	private String code;
	private String name;
	private String motto;
	private String directorID;
	private String deputyDirector1ID;
	private String deputyDirector2ID;
	private String seniorAsstDirector1ID;
	private String seniorAsstDirector2ID;
	private String seniorAsstDirector3ID;
	private String seniorAsstDirector4ID;
	private String seniorAsstDirector5ID;
	private String seniorAsstDirector6ID;
	private String asstDirector1ID;
	private String asstDirector2ID;
	private String asstDirector3ID;
	private String asstDirector4ID;
	private String asstDirector5ID;
	private String asstDirector6ID;
	private String directorName;
	private String deputyDirector1Name;
	private String deputyDirector2Name;
	private String seniorAsstDirector1Name;
	private String seniorAsstDirector2Name;
	private String seniorAsstDirector3Name;
	private String seniorAsstDirector4Name;
	private String seniorAsstDirector5Name;
	private String seniorAsstDirector6Name;
	private String asstDirector1Name;
	private String asstDirector2Name;
	private String asstDirector3Name;
	private String asstDirector4Name;
	private String asstDirector5Name;
	private String asstDirector6Name;
	private ArrayList<Region> regions;
	private ArrayList<District> districts;

	public int getStateID()
	{
		return stateID;
	}

	public void setStateID(int stateID)
	{
		this.stateID = stateID;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getMotto()
	{
		return motto;
	}

	public void setMotto(String motto)
	{
		this.motto = motto;
	}

	public String getDirectorID()
	{
		return directorID;
	}

	public void setDirectorID(String directorID)
	{
		this.directorID = directorID;
	}

	public String getDeputyDirector1ID()
	{
		return deputyDirector1ID;
	}

	public void setDeputyDirector1ID(String deputyDirector1ID)
	{
		this.deputyDirector1ID = deputyDirector1ID;
	}

	public String getDeputyDirector2ID()
	{
		return deputyDirector2ID;
	}

	public void setDeputyDirector2ID(String deputyDirector2ID)
	{
		this.deputyDirector2ID = deputyDirector2ID;
	}

	public String getSeniorAsstDirector1ID()
	{
		return seniorAsstDirector1ID;
	}

	public void setSeniorAsstDirector1ID(String seniorAsstDirector1ID)
	{
		this.seniorAsstDirector1ID = seniorAsstDirector1ID;
	}

	public String getSeniorAsstDirector2ID()
	{
		return seniorAsstDirector2ID;
	}

	public void setSeniorAsstDirector2ID(String seniorAsstDirector2ID)
	{
		this.seniorAsstDirector2ID = seniorAsstDirector2ID;
	}

	public String getSeniorAsstDirector3ID()
	{
		return seniorAsstDirector3ID;
	}

	public void setSeniorAsstDirector3ID(String seniorAsstDirector3ID)
	{
		this.seniorAsstDirector3ID = seniorAsstDirector3ID;
	}

	public String getSeniorAsstDirector4ID()
	{
		return seniorAsstDirector4ID;
	}

	public void setSeniorAsstDirector4ID(String seniorAsstDirector4ID)
	{
		this.seniorAsstDirector4ID = seniorAsstDirector4ID;
	}

	public String getSeniorAsstDirector5ID()
	{
		return seniorAsstDirector5ID;
	}

	public void setSeniorAsstDirector5ID(String seniorAsstDirector5ID)
	{
		this.seniorAsstDirector5ID = seniorAsstDirector5ID;
	}

	public String getSeniorAsstDirector6ID()
	{
		return seniorAsstDirector6ID;
	}

	public void setSeniorAsstDirector6ID(String seniorAsstDirector6ID)
	{
		this.seniorAsstDirector6ID = seniorAsstDirector6ID;
	}

	public String getAsstDirector1ID()
	{
		return asstDirector1ID;
	}

	public void setAsstDirector1ID(String asstDirector1ID)
	{
		this.asstDirector1ID = asstDirector1ID;
	}

	public String getAsstDirector2ID()
	{
		return asstDirector2ID;
	}

	public void setAsstDirector2ID(String asstDirector2ID)
	{
		this.asstDirector2ID = asstDirector2ID;
	}

	public String getAsstDirector3ID()
	{
		return asstDirector3ID;
	}

	public void setAsstDirector3ID(String asstDirector3ID)
	{
		this.asstDirector3ID = asstDirector3ID;
	}

	public String getAsstDirector4ID()
	{
		return asstDirector4ID;
	}

	public void setAsstDirector4ID(String asstDirector4ID)
	{
		this.asstDirector4ID = asstDirector4ID;
	}

	public String getAsstDirector5ID()
	{
		return asstDirector5ID;
	}

	public void setAsstDirector5ID(String asstDirector5ID)
	{
		this.asstDirector5ID = asstDirector5ID;
	}

	public String getAsstDirector6ID()
	{
		return asstDirector6ID;
	}

	public void setAsstDirector6ID(String asstDirector6ID)
	{
		this.asstDirector6ID = asstDirector6ID;
	}

	public String getDirectorName()
	{
		return directorName;
	}

	public void setDirectorName(String directorName)
	{
		this.directorName = directorName;
	}

	public String getDeputyDirector1Name()
	{
		return deputyDirector1Name;
	}

	public void setDeputyDirector1Name(String deputyDirector1Name)
	{
		this.deputyDirector1Name = deputyDirector1Name;
	}

	public String getDeputyDirector2Name()
	{
		return deputyDirector2Name;
	}

	public void setDeputyDirector2Name(String deputyDirector2Name)
	{
		this.deputyDirector2Name = deputyDirector2Name;
	}

	public String getSeniorAsstDirector1Name()
	{
		return seniorAsstDirector1Name;
	}

	public void setSeniorAsstDirector1Name(String seniorAsstDirector1Name)
	{
		this.seniorAsstDirector1Name = seniorAsstDirector1Name;
	}

	public String getSeniorAsstDirector2Name()
	{
		return seniorAsstDirector2Name;
	}

	public void setSeniorAsstDirector2Name(String seniorAsstDirector2Name)
	{
		this.seniorAsstDirector2Name = seniorAsstDirector2Name;
	}

	public String getSeniorAsstDirector3Name()
	{
		return seniorAsstDirector3Name;
	}

	public void setSeniorAsstDirector3Name(String seniorAsstDirector3Name)
	{
		this.seniorAsstDirector3Name = seniorAsstDirector3Name;
	}

	public String getSeniorAsstDirector4Name()
	{
		return seniorAsstDirector4Name;
	}

	public void setSeniorAsstDirector4Name(String seniorAsstDirector4Name)
	{
		this.seniorAsstDirector4Name = seniorAsstDirector4Name;
	}

	public String getSeniorAsstDirector5Name()
	{
		return seniorAsstDirector5Name;
	}

	public void setSeniorAsstDirector5Name(String seniorAsstDirector5Name)
	{
		this.seniorAsstDirector5Name = seniorAsstDirector5Name;
	}

	public String getSeniorAsstDirector6Name()
	{
		return seniorAsstDirector6Name;
	}

	public void setSeniorAsstDirector6Name(String seniorAsstDirector6Name)
	{
		this.seniorAsstDirector6Name = seniorAsstDirector6Name;
	}

	public String getAsstDirector1Name()
	{
		return asstDirector1Name;
	}

	public void setAsstDirector1Name(String asstDirector1Name)
	{
		this.asstDirector1Name = asstDirector1Name;
	}

	public String getAsstDirector2Name()
	{
		return asstDirector2Name;
	}

	public void setAsstDirector2Name(String asstDirector2Name)
	{
		this.asstDirector2Name = asstDirector2Name;
	}

	public String getAsstDirector3Name()
	{
		return asstDirector3Name;
	}

	public void setAsstDirector3Name(String asstDirector3Name)
	{
		this.asstDirector3Name = asstDirector3Name;
	}

	public String getAsstDirector4Name()
	{
		return asstDirector4Name;
	}

	public void setAsstDirector4Name(String asstDirector4Name)
	{
		this.asstDirector4Name = asstDirector4Name;
	}

	public String getAsstDirector5Name()
	{
		return asstDirector5Name;
	}

	public void setAsstDirector5Name(String asstDirector5Name)
	{
		this.asstDirector5Name = asstDirector5Name;
	}

	public String getAsstDirector6Name()
	{
		return asstDirector6Name;
	}

	public void setAsstDirector6Name(String asstDirector6Name)
	{
		this.asstDirector6Name = asstDirector6Name;
	}

	public ArrayList<Region> getRegions()
	{
		return regions;
	}

	public void setRegions(ArrayList<Region> regions)
	{
		this.regions = regions;
	}

	public ArrayList<District> getDistricts()
	{
		return districts;
	}

	public void setDistricts(ArrayList<District> districts)
	{
		this.districts = districts;
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

		if (obj instanceof State)
		{
			State that = (State) obj;
			equal = stateID == that.stateID;
		}

		return equal;
	}
}