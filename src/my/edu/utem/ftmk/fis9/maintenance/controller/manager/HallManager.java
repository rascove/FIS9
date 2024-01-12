package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.District;
import my.edu.utem.ftmk.fis9.maintenance.model.Hall;
import my.edu.utem.ftmk.fis9.maintenance.model.State;

/**
 * @author Satrya Fajri Pratama
 */
class HallManager extends MaintenanceTableManager
{
	HallManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private void write(Hall hall, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, hall.getName());
		ps.setInt(2, toInt(hall.isStatus()));
		ps.setInt(3, hall.getDistrictID());
		ps.setLong(4, hall.getHallID());
	}

	int addHall(Hall hall, boolean ignoreDuplicate) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement((ignoreDuplicate ? "INSERT IGNORE" : "INSERT") + " INTO Hall (Name, Status, DistrictID, HallID) VALUES (?, ?, ?, ?)" + (ignoreDuplicate ? "" : " ON DUPLICATE KEY UPDATE Name = ?, Status = ?, DistrictID = ?"));

		write(hall, ps);

		if (!ignoreDuplicate)
		{
			ps.setString(5, hall.getName());
			ps.setInt(6, toInt(hall.isStatus()));
			ps.setInt(7, hall.getDistrictID());
		}

		return ps.executeUpdate();
	}

	int updateHall(Hall hall) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE Hall SET Name = ?, Status = ?, DistrictID = ? WHERE HallID = ?");

		write(hall, ps);

		return ps.executeUpdate();
	}

	private Hall read(ResultSet rs) throws SQLException
	{
		Hall hall = new Hall();

		hall.setHallID(rs.getLong("HallID"));
		hall.setName(rs.getString("Name"));
		hall.setStatus(toBoolean(rs.getInt("Status")));
		hall.setDistrictID(rs.getInt("DistrictID"));

		return hall;
	}

	private ArrayList<Hall> getHalls(ResultSet rs) throws SQLException
	{
		ArrayList<Hall> halls = new ArrayList<>();

		while (rs.next())
			halls.add(read(rs));

		return halls;
	}

	ArrayList<Hall> getHalls(District district) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM Hall WHERE DistrictID = ? ORDER BY Name");

		ps.setInt(1, district.getDistrictID());

		return getHalls(ps.executeQuery());
	}

	ArrayList<Hall> getHalls() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM Hall ORDER BY Name");

		return getHalls(ps.executeQuery());
	}

	ArrayList<Hall> getHalls(int status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM Hall WHERE Status = ? ORDER BY Name");

		ps.setInt(1, status);

		return getHalls(ps.executeQuery());
	}

	ArrayList<Hall> getHalls(State state, int status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT h.* FROM Hall h, District d, State s WHERE h.Status = ? AND h.DistrictID = d.DistrictID AND d.StateID = s.StateID AND s.StateID = ? ORDER BY Name");

		ps.setInt(1, status);
		ps.setInt(2, state.getStateID());

		return getHalls(ps.executeQuery());
	}
}