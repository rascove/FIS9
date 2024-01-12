package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.ChequeType;

/**
 * @author Nor Azman Mat Ariff
 */
class ChequeTypeManager extends MaintenanceTableManager
{
	ChequeTypeManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(ChequeType chequeType, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, chequeType.getCode());
		ps.setString(2, chequeType.getName());
		ps.setString(3, chequeType.getStatus());

		if (chequeType.getChequeTypeID() != 0)
			ps.setInt(4, chequeType.getChequeTypeID());

		return ps.executeUpdate();
	}

	int addChequeType(ChequeType chequeType) throws SQLException
	{
		int status = 0;
		if(checkExistingCode(chequeType) == null)
		{
			PreparedStatement ps = facade.prepareStatement("INSERT INTO ChequeType (Code, Name, Status) VALUES (?, ?, ?)");
			status = write(chequeType, ps);
	
			if (status != 0)
			{
				ResultSet rs = ps.getGeneratedKeys();
	
				if (rs.next())
					chequeType.setChequeTypeID(rs.getInt(1));
			}
		}
		
		return status;
	}
	
	private ChequeType checkExistingCode(ChequeType chequeType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM ChequeType WHERE Code = ? AND Status = 'A'");
		ps.setString(1, chequeType.getCode());
		
		ResultSet rs = ps.executeQuery();
		
		ChequeType oldChequeType = null;
		
		if (rs.next())
		{
			oldChequeType = read(rs);
		}
		
		return oldChequeType;
	}

	int updateChequeType(ChequeType chequeType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE ChequeType SET Code = ?, Name = ?, Status = ? WHERE ChequeTypeID = ?");

		return write(chequeType, ps);
	}
	
	int deleteChequeType(ChequeType chequeType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE ChequeType SET Status = 'I' WHERE ChequeTypeID = ?");
		ps.setInt(1, chequeType.getChequeTypeID());

		return ps.executeUpdate();
	}

	private ChequeType read(ResultSet rs) throws SQLException
	{
		ChequeType chequeType = new ChequeType();

		chequeType.setChequeTypeID(rs.getInt("ChequeTypeID"));
		chequeType.setCode(rs.getString("Code"));
		chequeType.setName(rs.getString("Name"));
		chequeType.setStatus(rs.getString("Status"));

		return chequeType;
	}
	
	private ArrayList<ChequeType> getChequeTypes(ResultSet rs) throws SQLException
	{
		ArrayList<ChequeType> chequeTypes = new ArrayList<>();

		while (rs.next())
		{
			chequeTypes.add(read(rs));
		}
		return chequeTypes;
	}

	ChequeType getChequeType(int chequeTypeID) throws SQLException
	{
		ChequeType chequeType = null;
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM ChequeType WHERE ChequeTypeID = ?");

		ps.setInt(1, chequeTypeID);

		ResultSet rs = ps.executeQuery();
		
		if (rs.next())
			chequeType = read(rs);
		
		return chequeType;
	}
	
	ArrayList<ChequeType> getChequeTypes(String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM ChequeType WHERE Status = ? ORDER BY Code");

		ps.setString(1, status);

		return getChequeTypes(ps.executeQuery());
	}
}