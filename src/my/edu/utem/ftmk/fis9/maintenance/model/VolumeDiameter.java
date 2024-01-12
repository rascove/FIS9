package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class VolumeDiameter extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int volumeDiameterID;
	private int diameter;
	private double volume;
	private int volumeGroupID;

	public int getVolumeDiameterID()
	{
		return volumeDiameterID;
	}

	public void setVolumeDiameterID(int volumeDiameterID)
	{
		this.volumeDiameterID = volumeDiameterID;
	}

	public int getDiameter()
	{
		return diameter;
	}

	public void setDiameter(int diameter)
	{
		this.diameter = diameter;
	}

	public double getVolume()
	{
		return volume;
	}

	public void setVolume(double volume)
	{
		this.volume = volume;
	}

	public int getVolumeGroupID()
	{
		return volumeGroupID;
	}

	public void setVolumeGroupID(int volumeGroupID)
	{
		this.volumeGroupID = volumeGroupID;
	}

	@Override
	public String toString()
	{
		return "Diameter " + diameter + ": " + volume + " m\u00B3";
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof VolumeDiameter)
		{
			VolumeDiameter that = (VolumeDiameter) obj;
			equal = volumeDiameterID == that.volumeDiameterID;
		}

		return equal;
	}
}