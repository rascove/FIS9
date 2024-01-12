package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.DepartmentVot;

/**
 * @author Nor Azman Mat Ariff
 */
class DepartmentVotManager extends MaintenanceTableManager
{
	DepartmentVotManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(DepartmentVot departmentVot, PreparedStatement ps) throws SQLException
	{
		ps.setInt(1, departmentVot.getBursaryVotID());
		ps.setString(2, departmentVot.getCode());
		ps.setString(3, departmentVot.getName());
		ps.setString(4, departmentVot.getStatus());

		if (departmentVot.getDepartmentVotID() != 0)
			ps.setInt(5, departmentVot.getDepartmentVotID());

		return ps.executeUpdate();
	}

	int addDepartmentVot(DepartmentVot departmentVot) throws SQLException
	{
		int status = 0;
		if(!checkExistingCode(departmentVot))
		{
			PreparedStatement ps = facade.prepareStatement("INSERT INTO DepartmentVot (BursaryVotID, Code, Name, Status) VALUES (?, ?, ?, ?)");
			status = write(departmentVot, ps);
	
			if (status != 0)
			{
				ResultSet rs = ps.getGeneratedKeys();
	
				if (rs.next())
					departmentVot.setDepartmentVotID(rs.getInt(1));
			}
		}

		return status;
	}
	
	boolean checkExistingCode(DepartmentVot departmentVot) throws SQLException
	{
		boolean exist = false;
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM DepartmentVot d, BursaryVot b WHERE d.BursaryVotID = b.BursaryVotID AND b.BursaryVotID = ? AND d.DepartmentVotID != ? AND d.Code = ? AND d.Status = 'A'");
		ps.setInt(1, departmentVot.getBursaryVotID());
		ps.setInt(2, departmentVot.getDepartmentVotID());
		ps.setString(3, departmentVot.getCode());
		
		ResultSet rs = ps.executeQuery();
		
		if (rs.next())
		{
			exist = true;
		}
		
		return exist;
	}

	int updateDepartmentVot(DepartmentVot departmentVot) throws SQLException
	{
		int status = 0;
		if(!checkExistingCode(departmentVot))
		{
			PreparedStatement ps = facade.prepareStatement("UPDATE DepartmentVot SET BursaryVotID = ?, Code = ?, Name = ?, Status = ? WHERE DepartmentVotID = ?");
			status = write(departmentVot, ps);
		}

		return status;
	}
	
	int deleteDepartmentVot(DepartmentVot departmentVot) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE DepartmentVot SET Status = 'I' WHERE DepartmentVotID = ?");
		ps.setInt(1, departmentVot.getDepartmentVotID());

		return ps.executeUpdate();
	}

	private DepartmentVot read(ResultSet rs) throws SQLException
	{
		DepartmentVot departmentVot = new DepartmentVot();

		departmentVot.setDepartmentVotID(rs.getInt("DepartmentVotID"));
		departmentVot.setBursaryVotID(rs.getInt("BursaryVotID"));
		departmentVot.setBursaryVotCode(rs.getString("BursaryVotCode"));
		departmentVot.setBursaryVotName(rs.getString("BursaryVotName"));
		departmentVot.setCode(rs.getString("Code"));
		departmentVot.setName(rs.getString("Name"));
		departmentVot.setStatus(rs.getString("Status"));

		return departmentVot;
	}
	
	private ArrayList<DepartmentVot> getDepartmentVots(ResultSet rs) throws SQLException
	{
		ArrayList<DepartmentVot> departmentVots = new ArrayList<>();

		while (rs.next())
		{
			departmentVots.add(read(rs));
		}
			
		return departmentVots;
	}

	DepartmentVot getDepartmentVot(int departmentVotID) throws SQLException
	{
		DepartmentVot departmentVot = null;
		PreparedStatement ps = facade.prepareStatement("SELECT d.*, b.Code AS bursaryVotCode, b.Name AS bursaryVotName "
				+ "FROM DepartmentVot d, BursaryVot b "
				+ "WHERE d.BursaryVotID = b.BursaryVotID AND DepartmentVotID = ?");

		ps.setInt(1, departmentVotID);

		ResultSet rs = ps.executeQuery();
		
		if (rs.next())
			departmentVot = read(rs);
		
		return departmentVot;
	}
	
	ArrayList<DepartmentVot> getDepartmentVots(String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT d.*, b.Code AS bursaryVotCode, b.Name AS bursaryVotName "
				+ "FROM DepartmentVot d, BursaryVot b "
				+ "WHERE d.BursaryVotID = b.BursaryVotID AND d.Status = ? ORDER BY d.DepartmentVotID");

		ps.setString(1, status);

		return getDepartmentVots(ps.executeQuery());
	}
	
	ArrayList<DepartmentVot> getDepartmentVots(String status, int receiptType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT dv.*, bv.Code AS bursaryVotCode, bv.Name AS bursaryVotName " + 
				"FROM DepartmentVot dv, BursaryVot bv, TransactionForm tf, TransactionFormMapDepartmentVot tfmdv " + 
				"WHERE dv.BursaryVotID = bv.BursaryVotID AND tfmdv.DepartmentVotID = dv.DepartmentVotID AND tf.TransactionFormID = tfmdv.TransactionFormID " + 
				"AND dv.Status = ? AND tf.Code = ? ORDER BY dv.Code");

		ps.setString(1, status);
		ps.setInt(2, receiptType);

		return getDepartmentVots(ps.executeQuery());
	}
}