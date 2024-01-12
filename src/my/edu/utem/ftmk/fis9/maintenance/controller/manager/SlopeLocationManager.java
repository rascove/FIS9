package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.SlopeLocation;

/**
 * @author Satrya Fajri Pratama
 */
class SlopeLocationManager extends MaintenanceTableManager
{
	SlopeLocationManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(SlopeLocation slopeLocation, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, slopeLocation.getCode());
		ps.setString(2, slopeLocation.getName());

		if (slopeLocation.getSlopeLocationID() != 0)
			ps.setInt(3, slopeLocation.getSlopeLocationID());

		return ps.executeUpdate();
	}

	int addSlopeLocation(SlopeLocation slopeLocation) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO SlopeLocation (Code, Name) VALUES (?, ?)");
		int status = write(slopeLocation, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				slopeLocation.setSlopeLocationID(rs.getInt(1));
		}

		return status;
	}

	int updateSlopeLocation(SlopeLocation slopeLocation) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE SlopeLocation SET Code = ?, Name = ? WHERE SlopeLocationID = ?");

		return write(slopeLocation, ps);
	}

	private ArrayList<SlopeLocation> getSlopeLocations(ResultSet rs) throws SQLException
	{
		ArrayList<SlopeLocation> slopeLocations = new ArrayList<>();

		while (rs.next())
		{
			SlopeLocation slopeLocation = new SlopeLocation();

			slopeLocation.setSlopeLocationID(rs.getInt("SlopeLocationID"));
			slopeLocation.setCode(rs.getString("Code"));
			slopeLocation.setName(rs.getString("Name"));

			slopeLocations.add(slopeLocation);
		}

		return slopeLocations;
	}

	ArrayList<SlopeLocation> getSlopeLocations() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM SlopeLocation ORDER BY Code");

		return getSlopeLocations(ps.executeQuery());
	}
}