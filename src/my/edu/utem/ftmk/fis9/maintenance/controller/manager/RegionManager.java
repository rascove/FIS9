package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.Region;
import my.edu.utem.ftmk.fis9.maintenance.model.State;

/**
 * @author Satrya Fajri Pratama
 */
class RegionManager extends MaintenanceTableManager
{
	RegionManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(Region region, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, region.getCode());
		ps.setString(2, region.getName());
		ps.setInt(3, region.getStateID());

		if (region.getRegionID() != 0)
			ps.setInt(4, region.getRegionID());

		return ps.executeUpdate();
	}

	int addRegion(Region region) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO Region (Code, Name, StateID) VALUES (?, ?, ?)");
		int status = write(region, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				region.setRegionID(rs.getInt(1));
		}

		return status;
	}

	int updateRegion(Region region) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE Region SET Code = ?, Name = ?, StateID = ? WHERE RegionID = ?");

		return write(region, ps);
	}

	private ArrayList<Region> getRegions(ResultSet rs) throws SQLException
	{
		ArrayList<Region> regions = new ArrayList<>();

		while (rs.next())
		{
			Region region = new Region();

			region.setRegionID(rs.getInt("RegionID"));
			region.setCode(rs.getString("Code"));
			region.setName(rs.getString("Name"));
			region.setStateID(rs.getInt("StateID"));

			regions.add(region);
		}

		return regions;
	}

	ArrayList<Region> getRegions(State state) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM Region WHERE StateID = ? ORDER BY Code");

		ps.setInt(1, state.getStateID());

		return getRegions(ps.executeQuery());
	}
}