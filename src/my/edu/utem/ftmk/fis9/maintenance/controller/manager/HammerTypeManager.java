package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.HammerType;

/**
 * @author Satrya Fajri Pratama
 */
class HammerTypeManager extends MaintenanceTableManager
{
	HammerTypeManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(HammerType hammerType, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, hammerType.getName());

		if (hammerType.getHammerTypeID() != 0)
			ps.setInt(2, hammerType.getHammerTypeID());

		return ps.executeUpdate();
	}

	int addHammerType(HammerType hammerType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO HammerType (Name) VALUES (?)");
		int status = write(hammerType, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				hammerType.setHammerTypeID(rs.getInt(1));
		}

		return status;
	}

	int updateHammerType(HammerType hammerType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE HammerType SET Name = ? WHERE HammerTypeID = ?");

		return write(hammerType, ps);
	}

	private ArrayList<HammerType> getHammerTypes(ResultSet rs) throws SQLException
	{
		ArrayList<HammerType> hammerTypes = new ArrayList<>();

		while (rs.next())
		{
			HammerType hammerType = new HammerType();

			hammerType.setHammerTypeID(rs.getInt("HammerTypeID"));
			hammerType.setName(rs.getString("Name"));

			hammerTypes.add(hammerType);
		}

		return hammerTypes;
	}

	ArrayList<HammerType> getHammerTypes() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM HammerType ORDER BY Name");

		return getHammerTypes(ps.executeQuery());
	}
}