package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class PlotType extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int plotTypeID;
	private String name;
	private String description;
	private double length;
	private double width;
	private double minDiameter;
	private double maxDiameter;

	public int getPlotTypeID()
	{
		return plotTypeID;
	}

	public void setPlotTypeID(int plotTypeID)
	{
		this.plotTypeID = plotTypeID;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public double getLength()
	{
		return length;
	}

	public void setLength(double length)
	{
		this.length = length;
	}

	public double getWidth()
	{
		return width;
	}

	public void setWidth(double width)
	{
		this.width = width;
	}

	public double getMinDiameter()
	{
		return minDiameter;
	}

	public void setMinDiameter(double minDiameter)
	{
		this.minDiameter = minDiameter;
	}

	public double getMaxDiameter()
	{
		return maxDiameter;
	}

	public void setMaxDiameter(double maxDiameter)
	{
		this.maxDiameter = maxDiameter;
	}

	@Override
	public String toString()
	{
		return name + " - " + description;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof PlotType)
		{
			PlotType that = (PlotType) obj;
			equal = plotTypeID == that.plotTypeID;
		}

		return equal;
	}
	
	@Override
	public int compareTo(AbstractModel model)
	{
		PlotType that = (PlotType) model;
		int status = 0;
		
		if (plotTypeID < that.plotTypeID)
			status = -1;
		else if (plotTypeID > that.plotTypeID)
			status = 1;
		
		return status;
	}
}