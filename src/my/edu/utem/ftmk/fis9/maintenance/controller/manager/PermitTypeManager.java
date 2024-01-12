package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.PermitType;

/**
 * @author Nor Azman Mat Ariff
 */
class PermitTypeManager extends MaintenanceTableManager
{
	PermitTypeManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(PermitType permitType, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, permitType.getCode());
		ps.setString(2, permitType.getName());
		ps.setString(3, permitType.getStatus());

		if (permitType.getPermitTypeID() != 0)
			ps.setInt(4, permitType.getPermitTypeID());

		return ps.executeUpdate();
	}

	int addPermitType(PermitType permitType) throws SQLException
	{
		int status = 0;
		if(checkExistingCode(permitType) == null)
		{
			PreparedStatement ps = facade.prepareStatement("INSERT INTO PermitType (Code, Name, Status) VALUES (?, ?, ?)");
			status = write(permitType, ps);
	
			if (status != 0)
			{
				ResultSet rs = ps.getGeneratedKeys();
	
				if (rs.next())
					permitType.setPermitTypeID(rs.getInt(1));
			}
		}
		
		return status;
	}
	
	private PermitType checkExistingCode(PermitType permitType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM PermitType WHERE Code = ? AND Status = 'A'");
		ps.setString(1, permitType.getCode());
		
		ResultSet rs = ps.executeQuery();
		
		PermitType oldPermitType = null;
		
		if (rs.next())
		{
			oldPermitType = read(rs);
		}
		
		return oldPermitType;
	}

	int updatePermitType(PermitType permitType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE PermitType SET Code = ?, Name = ?, Status = ? WHERE PermitTypeID = ?");

		return write(permitType, ps);
	}
	
	int deletePermitType(PermitType permitType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE PermitType SET Status = 'I' WHERE PermitTypeID = ?");
		ps.setInt(1, permitType.getPermitTypeID());

		return ps.executeUpdate();
	}

	private PermitType read(ResultSet rs) throws SQLException
	{
		PermitType permitType = new PermitType();

		permitType.setPermitTypeID(rs.getInt("PermitTypeID"));
		permitType.setCode(rs.getString("Code"));
		permitType.setName(rs.getString("Name"));
		permitType.setStatus(rs.getString("Status"));

		return permitType;
	}
	
	private ArrayList<PermitType> getPermitTypes(ResultSet rs) throws SQLException
	{
		ArrayList<PermitType> permitTypes = new ArrayList<>();

		while (rs.next())
		{
			permitTypes.add(read(rs));
		}
		return permitTypes;
	}

	PermitType getPermitType(int permitTypeID) throws SQLException
	{
		PermitType permitType = null;
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM PermitType WHERE PermitTypeID = ?");

		ps.setInt(1, permitTypeID);

		ResultSet rs = ps.executeQuery();
		
		if (rs.next())
			permitType = read(rs);
		
		return permitType;
	}
	
	ArrayList<PermitType> getPermitTypes(String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM PermitType WHERE Status = ? ORDER BY Code");

		ps.setString(1, status);

		return getPermitTypes(ps.executeQuery());
	}
}