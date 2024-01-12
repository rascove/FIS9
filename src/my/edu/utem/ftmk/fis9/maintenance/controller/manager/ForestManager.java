package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.District;
import my.edu.utem.ftmk.fis9.maintenance.model.Forest;
import my.edu.utem.ftmk.fis9.maintenance.model.State;

/**
 * @author Satrya Fajri Pratama
 * @author Nor Azman Mat Ariff (getForests(District district))
 */
class ForestManager extends MaintenanceTableManager
{
	ForestManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(Forest forest, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, forest.getCode());
		ps.setString(2, forest.getName());
		ps.setDouble(3, forest.getArea());
		nullable(ps, 4, forest.getVolumeRate());
		nullable(ps, 5, forest.getPowerRate());
		ps.setInt(6, forest.getDistrictID());

		if (forest.getForestID() != 0)
			ps.setInt(7, forest.getForestID());

		return ps.executeUpdate();
	}

	int addForest(Forest forest) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO Forest (Code, Name, Area, VolumeRate, PowerRate, DistrictID) VALUES (?, ?, ?, ?, ?, ?)");
		int status = write(forest, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				forest.setForestID(rs.getInt(1));
		}

		return status;
	}

	int updateForest(Forest forest) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE Forest SET Code = ?, Name = ?, Area = ?, VolumeRate = ?, PowerRate= ?, DistrictID = ? WHERE ForestID = ?");

		return write(forest, ps);
	}

	private Forest read(ResultSet rs) throws SQLException
	{
		Forest forest = new Forest();

		forest.setForestID(rs.getInt("ForestID"));
		forest.setCode(rs.getString("Code"));
		forest.setName(rs.getString("Name"));
		forest.setArea(rs.getDouble("Area"));
		forest.setVolumeRate(rs.getDouble("VolumeRate"));
		forest.setPowerRate(rs.getDouble("PowerRate"));
		forest.setDistrictID(rs.getInt("DistrictID"));
		forest.setStateID(rs.getInt("StateID"));
		forest.setDistrictName(rs.getString("DistrictName"));
		forest.setStateName(rs.getString("StateName"));
		
		return forest;
	}
	
	private ArrayList<Forest> getForests(ResultSet rs) throws SQLException
	{
		ArrayList<Forest> forests = new ArrayList<>();

		while (rs.next())
			forests.add(read(rs));

		return forests;
	}

	Forest getForest(int forestID) throws SQLException
	{
		Forest forest = null;
		PreparedStatement ps = facade.prepareStatement("SELECT f.*, d.StateID, d.Name AS DistrictName, s.Name AS StateName FROM Forest f, District d, State s WHERE f.ForestID = ? AND f.DistrictID = d.DistrictID AND d.StateID = s.StateID ORDER BY f.Name");

		ps.setInt(1, forestID);
		
		ResultSet rs = ps.executeQuery();
		
		if (rs.next())
			forest = read(rs);
		
		return forest;
	}
	
	ArrayList<Forest> getForests() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT f.*, d.StateID, d.Name AS DistrictName, s.Name AS StateName FROM Forest f, District d, State s WHERE f.DistrictID = d.DistrictID AND d.StateID = s.StateID ORDER BY f.Name");

		return getForests(ps.executeQuery());
	}
	
	ArrayList<Forest> getForests(District district) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT f.*, d.StateID, d.Name AS DistrictName, s.Name AS StateName FROM Forest f, District d, State s WHERE f.DistrictID = d.DistrictID AND d.StateID = s.StateID AND f.DistrictID = ? ORDER BY f.Name");

		ps.setInt(1, district.getDistrictID());

		return getForests(ps.executeQuery());
	}

	ArrayList<Forest> getForests(State state) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT f.*, d.StateID, d.Name AS DistrictName, s.Name AS StateName FROM Forest f, District d, State s WHERE f.DistrictID = d.DistrictID AND d.StateID = s.StateID AND s.StateID = ? ORDER BY f.Name");

		ps.setInt(1, state.getStateID());

		return getForests(ps.executeQuery());
	}
}