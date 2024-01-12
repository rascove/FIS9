package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.Geology;

/**
 * @author Satrya Fajri Pratama
 */
class GeologyManager extends MaintenanceTableManager
{
	GeologyManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(Geology geology, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, geology.getCode());
		ps.setString(2, geology.getName());

		if (geology.getGeologyID() != 0)
			ps.setInt(3, geology.getGeologyID());

		return ps.executeUpdate();
	}

	int addGeology(Geology geology) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO Geology (Code, Name) VALUES (?, ?)");
		int status = write(geology, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				geology.setGeologyID(rs.getInt(1));
		}

		return status;
	}

	int updateGeology(Geology geology) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE Geology SET Code = ?, Name = ? WHERE GeologyID = ?");

		return write(geology, ps);
	}

	private ArrayList<Geology> getGeologies(ResultSet rs) throws SQLException
	{
		ArrayList<Geology> geologies = new ArrayList<>();

		while (rs.next())
		{
			Geology geology = new Geology();

			geology.setGeologyID(rs.getInt("GeologyID"));
			geology.setCode(rs.getString("Code"));
			geology.setName(rs.getString("Name"));

			geologies.add(geology);
		}

		return geologies;
	}

	ArrayList<Geology> getGeologies() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM Geology ORDER BY Code");

		return getGeologies(ps.executeQuery());
	}
}