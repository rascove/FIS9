package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.PlotType;

/**
 * @author Satrya Fajri Pratama
 */
class PlotTypeManager extends MaintenanceTableManager
{
	PlotTypeManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(PlotType plotType, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, plotType.getName());
		ps.setString(2, plotType.getDescription());
		ps.setDouble(3, plotType.getLength());
		ps.setDouble(4, plotType.getWidth());
		nullable(ps, 5, plotType.getMinDiameter());
		nullable(ps, 6, plotType.getMaxDiameter());
		
		if (plotType.getPlotTypeID() != 0)
			ps.setInt(7, plotType.getPlotTypeID());

		return ps.executeUpdate();
	}

	int addPlotType(PlotType plotType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO PlotType (Name, Description, Length, Width, MinDiameter, MaxDiameter) VALUES (?, ?, ?, ?, ?, ?)");
		int status = write(plotType, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				plotType.setPlotTypeID(rs.getInt(1));
		}

		return status;
	}

	int updatePlotType(PlotType plotType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE PlotType SET Name = ?, Description = ?, Length = ?, Width = ?, MinDiameter = ?, MaxDiameter = ? WHERE PlotTypeID = ?");

		return write(plotType, ps);
	}

	private ArrayList<PlotType> getPlotTypes(ResultSet rs) throws SQLException
	{
		ArrayList<PlotType> plotTypes = new ArrayList<>();

		while (rs.next())
		{
			PlotType plotType = new PlotType();

			plotType.setPlotTypeID(rs.getInt("PlotTypeID"));
			plotType.setName(rs.getString("Name"));
			plotType.setDescription(rs.getString("Description"));
			plotType.setLength(rs.getDouble("Length"));
			plotType.setWidth(rs.getDouble("Width"));
			plotType.setMinDiameter(rs.getDouble("MinDiameter"));
			plotType.setMaxDiameter(rs.getDouble("MaxDiameter"));
			
			plotTypes.add(plotType);
		}

		return plotTypes;
	}

	ArrayList<PlotType> getPlotTypes() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM PlotType ORDER BY PlotTypeID");

		return getPlotTypes(ps.executeQuery());
	}
}