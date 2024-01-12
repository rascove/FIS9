package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.Dominance;

/**
 * @author Zurina
 */
class DominanceManager extends MaintenanceTableManager
{
	DominanceManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(Dominance dominance, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, dominance.getCode());
		ps.setString(2, dominance.getName());

		if (dominance.getDominanceID() != 0)
			ps.setInt(3, dominance.getDominanceID());

		return ps.executeUpdate();
	}

	int addDominance(Dominance dominance) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO Dominance (Code, Name) VALUES (?, ?)");
		int status = write(dominance, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				dominance.setDominanceID(rs.getInt(1));
		}

		return status;
	}

	int updateDominance(Dominance dominance) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE Dominance SET Code = ?, Name = ? WHERE DominanceID = ?");

		return write(dominance, ps);
	}

	private ArrayList<Dominance> getDominances(ResultSet rs) throws SQLException
	{
		ArrayList<Dominance> dominances = new ArrayList<>();

		while (rs.next())
		{
			Dominance dominance = new Dominance();

			dominance.setDominanceID(rs.getInt("DominanceID"));
			dominance.setCode(rs.getString("Code"));
			dominance.setName(rs.getString("Name"));

			dominances.add(dominance);
		}

		return dominances;
	}

	ArrayList<Dominance> getDominances() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM Dominance ORDER BY Code");

		return getDominances(ps.executeQuery());
	}
}