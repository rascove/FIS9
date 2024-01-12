package my.edu.utem.ftmk.fis9.maintenance.model;

/**
 * @author Satrya Fajri Pratama
 */
public class VolumeSpecies extends Species
{
	private static final long serialVersionUID = VERSION;
	private int volumeSpeciesID;
	private int volumeGroupID;

	public int getVolumeSpeciesID()
	{
		return volumeSpeciesID;
	}

	public void setVolumeSpeciesID(int volumeSpeciesID)
	{
		this.volumeSpeciesID = volumeSpeciesID;
	}
	
	public int getVolumeGroupID()
	{
		return volumeGroupID;
	}

	public void setVolumeGroupID(int volumeGroupID)
	{
		this.volumeGroupID = volumeGroupID;
	}
}