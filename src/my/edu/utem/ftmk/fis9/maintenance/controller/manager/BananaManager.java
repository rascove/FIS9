package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.Banana;

/**
 * @author Zurina
 */
class BananaManager extends MaintenanceTableManager
{
	BananaManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(Banana Banana, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, Banana.getCode());
		ps.setString(2, Banana.getName());

		if (Banana.getBananaID() != 0)
			ps.setInt(3, Banana.getBananaID());

		return ps.executeUpdate();
	}

	int addBanana(Banana Banana) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO Banana (Code, Name) VALUES (?, ?)");
		int status = write(Banana, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				Banana.setBananaID(rs.getInt(1));
		}

		return status;
	}

	int updateBanana(Banana Banana) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE Banana SET Code = ?, Name = ? WHERE BananaID = ?");

		return write(Banana, ps);
	}

	private ArrayList<Banana> getBananas(ResultSet rs) throws SQLException
	{
		ArrayList<Banana> Bananas = new ArrayList<>();

		while (rs.next())
		{
			Banana Banana = new Banana();

			Banana.setBananaID(rs.getInt("BananaID"));
			Banana.setCode(rs.getString("Code"));
			Banana.setName(rs.getString("Name"));

			Bananas.add(Banana);
		}

		return Bananas;
	}

	ArrayList<Banana> getBananas() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM Banana ORDER BY Code");

		return getBananas(ps.executeQuery());
	}
}