package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.Fertility;

/**
 * @author Satrya Fajri Pratama
 */
class FertilityManager extends MaintenanceTableManager
{
	FertilityManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(Fertility fertility, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, fertility.getCode());
		ps.setString(2, fertility.getName());

		if (fertility.getFertilityID() != 0)
			ps.setInt(3, fertility.getFertilityID());

		return ps.executeUpdate();
	}

	int addFertility(Fertility fertility) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO Fertility (Code, Name) VALUES (?, ?)");
		int status = write(fertility, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				fertility.setFertilityID(rs.getInt(1));
		}

		return status;
	}

	int updateFertility(Fertility fertility) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE Fertility SET Code = ?, Name = ? WHERE FertilityID = ?");

		return write(fertility, ps);
	}

	private ArrayList<Fertility> getFertilities(ResultSet rs) throws SQLException
	{
		ArrayList<Fertility> fertilities = new ArrayList<>();

		while (rs.next())
		{
			Fertility fertility = new Fertility();

			fertility.setFertilityID(rs.getInt("FertilityID"));
			fertility.setCode(rs.getString("Code"));
			fertility.setName(rs.getString("Name"));

			fertilities.add(fertility);
		}

		return fertilities;
	}

	ArrayList<Fertility> getFertilities() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM Fertility ORDER BY Code");

		return getFertilities(ps.executeQuery());
	}
}