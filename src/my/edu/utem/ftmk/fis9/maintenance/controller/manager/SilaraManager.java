package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.Silara;

/**
 * @author Zurina
 */
class SilaraManager extends MaintenanceTableManager
{
	SilaraManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(Silara silara, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, silara.getCode());
		ps.setString(2, silara.getName());

		if (silara.getSilaraID() != 0)
			ps.setInt(3, silara.getSilaraID());

		return ps.executeUpdate();
	}

	int addSilara(Silara silara) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO Silara (Code, Name) VALUES (?, ?)");
		int status = write(silara, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				silara.setSilaraID(rs.getInt(1));
		}

		return status;
	}

	int updateSilara(Silara silara) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE Silara SET Code = ?, Name = ? WHERE SilaraID = ?");

		return write(silara, ps);
	}

	private ArrayList<Silara> getSilaras(ResultSet rs) throws SQLException
	{
		ArrayList<Silara> silaras = new ArrayList<>();

		while (rs.next())
		{
			Silara silara = new Silara();

			silara.setSilaraID(rs.getInt("SilaraID"));
			silara.setCode(rs.getString("Code"));
			silara.setName(rs.getString("Name"));

			silaras.add(silara);
		}

		return silaras;
	}

	ArrayList<Silara> getSilaras() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM Silara ORDER BY Code");

		return getSilaras(ps.executeQuery());
	}
}