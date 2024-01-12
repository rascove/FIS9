package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.Resam;

/**
 * @author Satrya Fajri Pratama
 */
class ResamManager extends MaintenanceTableManager
{
	ResamManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(Resam resam, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, resam.getCode());
		ps.setString(2, resam.getName());

		if (resam.getResamID() != 0)
			ps.setInt(3, resam.getResamID());

		return ps.executeUpdate();
	}

	int addResam(Resam resam) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO Resam (Code, Name) VALUES (?, ?)");
		int status = write(resam, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				resam.setResamID(rs.getInt(1));
		}

		return status;
	}

	int updateResam(Resam resam) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE Resam SET Code = ?, Name = ? WHERE ResamID = ?");

		return write(resam, ps);
	}

	private ArrayList<Resam> getResams(ResultSet rs) throws SQLException
	{
		ArrayList<Resam> resams = new ArrayList<>();

		while (rs.next())
		{
			Resam resam = new Resam();

			resam.setResamID(rs.getInt("ResamID"));
			resam.setCode(rs.getString("Code"));
			resam.setName(rs.getString("Name"));

			resams.add(resam);
		}

		return resams;
	}

	ArrayList<Resam> getResams() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM Resam ORDER BY Code");

		return getResams(ps.executeQuery());
	}
}