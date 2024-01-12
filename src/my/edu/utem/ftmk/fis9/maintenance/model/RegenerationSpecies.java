package my.edu.utem.ftmk.fis9.maintenance.model;

/**
 * @author Satrya Fajri Pratama
 */
public class RegenerationSpecies extends Species
{
	private static final long serialVersionUID = VERSION;
	private int regenerationSpeciesID;
	private int regenerationTypeID;

	public int getRegenerationSpeciesID()
	{
		return regenerationSpeciesID;
	}

	public void setRegenerationSpeciesID(int regenerationSpeciesID)
	{
		this.regenerationSpeciesID = regenerationSpeciesID;
	}

	public int getRegenerationTypeID()
	{
		return regenerationTypeID;
	}

	public void setRegenerationTypeID(int regenerationTypeID)
	{
		this.regenerationTypeID = regenerationTypeID;
	}
}