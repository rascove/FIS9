package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.Elevation;

/**
 * @author Satrya Fajri Pratama
 */
class ElevationManager extends MaintenanceTableManager
{
	ElevationManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(Elevation elevation, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, elevation.getCode());
		ps.setString(2, elevation.getName());

		if (elevation.getElevationID() != 0)
			ps.setInt(3, elevation.getElevationID());

		return ps.executeUpdate();
	}

	int addElevation(Elevation elevation) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO Elevation (Code, Name) VALUES (?, ?)");
		int status = write(elevation, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				elevation.setElevationID(rs.getInt(1));
		}

		return status;
	}

	int updateElevation(Elevation elevation) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE Elevation SET Code = ?, Name = ? WHERE ElevationID = ?");

		return write(elevation, ps);
	}

	private ArrayList<Elevation> getElevations(ResultSet rs) throws SQLException
	{
		ArrayList<Elevation> elevations = new ArrayList<>();

		while (rs.next())
		{
			Elevation elevation = new Elevation();

			elevation.setElevationID(rs.getInt("ElevationID"));
			elevation.setCode(rs.getString("Code"));
			elevation.setName(rs.getString("Name"));

			elevations.add(elevation);
		}

		return elevations;
	}

	ArrayList<Elevation> getElevations() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM Elevation ORDER BY Code");

		return getElevations(ps.executeQuery());
	}
}