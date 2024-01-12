package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.Aspect;

/**
 * @author Satrya Fajri Pratama
 */
class AspectManager extends MaintenanceTableManager
{
	AspectManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(Aspect aspect, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, aspect.getCode());
		ps.setString(2, aspect.getName());

		if (aspect.getAspectID() != 0)
			ps.setInt(3, aspect.getAspectID());

		return ps.executeUpdate();
	}

	int addAspect(Aspect aspect) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO Aspect (Code, Name) VALUES (?, ?)");
		int status = write(aspect, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				aspect.setAspectID(rs.getInt(1));
		}

		return status;
	}

	int updateAspect(Aspect aspect) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE Aspect SET Code = ?, Name = ? WHERE AspectID = ?");

		return write(aspect, ps);
	}

	private ArrayList<Aspect> getAspects(ResultSet rs) throws SQLException
	{
		ArrayList<Aspect> aspects = new ArrayList<>();

		while (rs.next())
		{
			Aspect aspect = new Aspect();

			aspect.setAspectID(rs.getInt("AspectID"));
			aspect.setCode(rs.getString("Code"));
			aspect.setName(rs.getString("Name"));

			aspects.add(aspect);
		}

		return aspects;
	}

	ArrayList<Aspect> getAspects() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM Aspect ORDER BY Code");

		return getAspects(ps.executeQuery());
	}
}