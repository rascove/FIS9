package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.TimberType;

/**
 * @author Satrya Fajri Pratama
 */
class TimberTypeManager extends MaintenanceTableManager
{
	TimberTypeManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(TimberType timberType, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, timberType.getCode());
		ps.setString(2, timberType.getName());

		if (timberType.getTimberTypeID() != 0)
			ps.setInt(3, timberType.getTimberTypeID());

		return ps.executeUpdate();
	}

	int addTimberType(TimberType timberType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO TimberType (Code, Name) VALUES (?, ?)");
		int status = write(timberType, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				timberType.setTimberTypeID(rs.getInt(1));
		}

		return status;
	}

	int updateTimberType(TimberType timberType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE TimberType SET Code = ?, Name = ? WHERE TimberTypeID = ?");

		return write(timberType, ps);
	}

	private ArrayList<TimberType> getTimberTypes(ResultSet rs) throws SQLException
	{
		ArrayList<TimberType> timberTypes = new ArrayList<>();

		while (rs.next())
		{
			TimberType timberType = new TimberType();

			timberType.setTimberTypeID(rs.getInt("TimberTypeID"));
			timberType.setCode(rs.getString("Code"));
			timberType.setName(rs.getString("Name"));

			timberTypes.add(timberType);
		}

		return timberTypes;
	}

	ArrayList<TimberType> getTimberTypes() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM TimberType ORDER BY Code");

		return getTimberTypes(ps.executeQuery());
	}
}