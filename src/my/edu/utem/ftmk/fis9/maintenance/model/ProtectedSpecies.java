package my.edu.utem.ftmk.fis9.maintenance.model;

/**
 * @author Satrya Fajri Pratama
 */
public class ProtectedSpecies extends Species
{
	private static final long serialVersionUID = VERSION;
	private int protectedSpeciesID;
	private int protectedTypeID;

	public int getProtectedSpeciesID()
	{
		return protectedSpeciesID;
	}

	public void setProtectedSpeciesID(int protectedSpeciesID)
	{
		this.protectedSpeciesID = protectedSpeciesID;
	}

	public int getProtectedTypeID()
	{
		return protectedTypeID;
	}

	public void setProtectedTypeID(int protectedTypeID)
	{
		this.protectedTypeID = protectedTypeID;
	}
}