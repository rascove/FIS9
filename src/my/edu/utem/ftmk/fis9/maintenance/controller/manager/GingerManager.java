package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.Ginger;

/**
 * @author Zurina
 */
class GingerManager extends MaintenanceTableManager
{
	GingerManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(Ginger ginger, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, ginger.getCode());
		ps.setString(2, ginger.getName());

		if (ginger.getGingerID() != 0)
			ps.setInt(3, ginger.getGingerID());

		return ps.executeUpdate();
	}

	int addGinger(Ginger ginger) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO Ginger (Code, Name) VALUES (?, ?)");
		int status = write(ginger, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				ginger.setGingerID(rs.getInt(1));
		}

		return status;
	}

	int updateGinger(Ginger ginger) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE Ginger SET Code = ?, Name = ? WHERE GingerID = ?");

		return write(ginger, ps);
	}

	private ArrayList<Ginger> getGingers(ResultSet rs) throws SQLException
	{
		ArrayList<Ginger> gingers = new ArrayList<>();

		while (rs.next())
		{
			Ginger ginger = new Ginger();

			ginger.setGingerID(rs.getInt("GingerID"));
			ginger.setCode(rs.getString("Code"));
			ginger.setName(rs.getString("Name"));

			gingers.add(ginger);
		}

		return gingers;
	}

	ArrayList<Ginger> getGingers() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM Ginger ORDER BY Code");

		return getGingers(ps.executeQuery());
	}
}