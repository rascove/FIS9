package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.SoilType;

/**
 * @author Satrya Fajri Pratama
 */
class SoilTypeManager extends MaintenanceTableManager
{
	SoilTypeManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(SoilType soilType, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, soilType.getCode());
		ps.setString(2, soilType.getName());

		if (soilType.getSoilTypeID() != 0)
			ps.setInt(3, soilType.getSoilTypeID());

		return ps.executeUpdate();
	}

	int addSoilType(SoilType soilType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO SoilType (Code, Name) VALUES (?, ?)");
		int status = write(soilType, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				soilType.setSoilTypeID(rs.getInt(1));
		}

		return status;
	}

	int updateSoilType(SoilType soilType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE SoilType SET Code = ?, Name = ? WHERE SoilTypeID = ?");

		return write(soilType, ps);
	}

	private ArrayList<SoilType> getSoilTypes(ResultSet rs) throws SQLException
	{
		ArrayList<SoilType> soilTypes = new ArrayList<>();

		while (rs.next())
		{
			SoilType soilType = new SoilType();

			soilType.setSoilTypeID(rs.getInt("SoilTypeID"));
			soilType.setCode(rs.getString("Code"));
			soilType.setName(rs.getString("Name"));

			soilTypes.add(soilType);
		}

		return soilTypes;
	}

	ArrayList<SoilType> getSoilTypes() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM SoilType ORDER BY Code");

		return getSoilTypes(ps.executeQuery());
	}
}