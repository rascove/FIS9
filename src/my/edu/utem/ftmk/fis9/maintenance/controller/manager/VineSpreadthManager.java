package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.VineSpreadth;

/**
 * @author Satrya Fajri Pratama
 */
class VineSpreadthManager extends MaintenanceTableManager
{
	VineSpreadthManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(VineSpreadth vineSpreadth, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, vineSpreadth.getCode());
		ps.setString(2, vineSpreadth.getName());

		if (vineSpreadth.getVineSpreadthID() != 0)
			ps.setInt(3, vineSpreadth.getVineSpreadthID());

		return ps.executeUpdate();
	}

	int addVineSpreadth(VineSpreadth vineSpreadth) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO VineSpreadth (Code, Name) VALUES (?, ?)");
		int status = write(vineSpreadth, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				vineSpreadth.setVineSpreadthID(rs.getInt(1));
		}

		return status;
	}

	int updateVineSpreadth(VineSpreadth vineSpreadth) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE VineSpreadth SET Code = ?, Name = ? WHERE VineSpreadthID = ?");

		return write(vineSpreadth, ps);
	}

	private ArrayList<VineSpreadth> getVineSpreadths(ResultSet rs) throws SQLException
	{
		ArrayList<VineSpreadth> vineSpreadths = new ArrayList<>();

		while (rs.next())
		{
			VineSpreadth vineSpreadth = new VineSpreadth();

			vineSpreadth.setVineSpreadthID(rs.getInt("VineSpreadthID"));
			vineSpreadth.setCode(rs.getString("Code"));
			vineSpreadth.setName(rs.getString("Name"));

			vineSpreadths.add(vineSpreadth);
		}

		return vineSpreadths;
	}

	ArrayList<VineSpreadth> getVineSpreadths() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM VineSpreadth ORDER BY Code");

		return getVineSpreadths(ps.executeQuery());
	}
}