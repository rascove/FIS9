package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.Unit;

/**
 * @author Nor Azman Mat Ariff
 */
class UnitManager extends MaintenanceTableManager
{
	UnitManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(Unit unit, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, unit.getCode());
		ps.setString(2, unit.getName());
		ps.setString(3, unit.getStatus());

		if (unit.getUnitID() != 0)
			ps.setInt(4, unit.getUnitID());

		return ps.executeUpdate();
	}

	int addUnit(Unit unit) throws SQLException
	{
		int status = 0;
		if(checkExistingCode(unit) == null)
		{
			PreparedStatement ps = facade.prepareStatement("INSERT INTO Unit (Code, Name, Status) VALUES (?, ?, ?)");
			status = write(unit, ps);
	
			if (status != 0)
			{
				ResultSet rs = ps.getGeneratedKeys();
	
				if (rs.next())
					unit.setUnitID(rs.getInt(1));
			}
		}
		
		return status;
	}
	
	private Unit checkExistingCode(Unit unit) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM Unit WHERE Code = ? AND Status = 'A'");
		ps.setString(1, unit.getCode());
		
		ResultSet rs = ps.executeQuery();
		
		Unit oldUnit = null;
		
		if (rs.next())
		{
			oldUnit = read(rs);
		}
		
		return oldUnit;
	}

	int updateUnit(Unit unit) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE Unit SET Code = ?, Name = ?, Status = ? WHERE UnitID = ?");

		return write(unit, ps);
	}
	
	int deleteUnit(Unit unit) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE Unit SET Status = 'I' WHERE UnitID = ?");
		ps.setInt(1, unit.getUnitID());

		return ps.executeUpdate();
	}

	private Unit read(ResultSet rs) throws SQLException
	{
		Unit unit = new Unit();

		unit.setUnitID(rs.getInt("UnitID"));
		unit.setCode(rs.getString("Code"));
		unit.setName(rs.getString("Name"));
		unit.setStatus(rs.getString("Status"));

		return unit;
	}
	
	private ArrayList<Unit> getUnits(ResultSet rs) throws SQLException
	{
		ArrayList<Unit> units = new ArrayList<>();

		while (rs.next())
		{
			units.add(read(rs));
		}
		return units;
	}

	Unit getUnit(int unitID) throws SQLException
	{
		Unit unit = null;
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM Unit WHERE UnitID = ?");

		ps.setInt(1, unitID);

		ResultSet rs = ps.executeQuery();
		
		if (rs.next())
			unit = read(rs);
		
		return unit;
	}
	
	ArrayList<Unit> getUnits(String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM Unit WHERE Status = ? ORDER BY Code");

		ps.setString(1, status);

		return getUnits(ps.executeQuery());
	}
	
	ArrayList<Unit> getUnits(int smallProductID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT u.* " + 
				"FROM Unit u, SmallProductRoyaltyRate sprr, SmallProduct sp " + 
				"WHERE sprr.UnitID = u.UnitID AND sprr.SmallProductID = sp.SmallProductID AND sprr.Status = 'A' AND sp.SmallProductID = ?");

		ps.setInt(1, smallProductID);

		return getUnits(ps.executeQuery());
	}
}