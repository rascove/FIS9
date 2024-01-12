package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.TransactionForm;
import my.edu.utem.ftmk.fis9.maintenance.model.TransactionFormMapDepartmentVot;

/**
 * @author Nor Azman Mat Ariff
 */
class TransactionFormMapDepartmentVotManager extends MaintenanceTableManager
{
	TransactionFormMapDepartmentVotManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(TransactionFormMapDepartmentVot transactionFormMapDepartmentVot, PreparedStatement ps) throws SQLException
	{
		ps.setInt(1, transactionFormMapDepartmentVot.getTransactionFormID());
		ps.setInt(2, transactionFormMapDepartmentVot.getDepartmentVotID());
		ps.setString(3, transactionFormMapDepartmentVot.getStatus());

		if (transactionFormMapDepartmentVot.getTransactionFormMapDepartmentVotID() != 0)
			ps.setInt(4, transactionFormMapDepartmentVot.getTransactionFormMapDepartmentVotID());

		return ps.executeUpdate();
	}

	int addTransactionFormMapDepartmentVot(TransactionFormMapDepartmentVot transactionFormMapDepartmentVot) throws SQLException
	{
		int status = 0;
		if(checkExistingCode(transactionFormMapDepartmentVot) == null)
		{
			PreparedStatement ps = facade.prepareStatement("INSERT INTO TransactionFormMapDepartmentVot (TransactionFormID, DepartmentVotID, Status) VALUES (?, ?, ?)");
			status = write(transactionFormMapDepartmentVot, ps);
	
			if (status != 0)
			{
				ResultSet rs = ps.getGeneratedKeys();
	
				if (rs.next())
					transactionFormMapDepartmentVot.setTransactionFormMapDepartmentVotID(rs.getInt(1));
			}
		}

		return status;
	}
	
	TransactionFormMapDepartmentVot checkExistingCode(TransactionFormMapDepartmentVot transactionFormMapDepartmentVot) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT tmd.*, t.Code AS TransactionFormCode, t.Name AS TransactionFormName, d.Code AS DepartmentVotCode, d.Name AS DepartmentVotName FROM TransactionFormMapDepartmentVot tmd, TransactionForm t, DepartmentVot d WHERE tmd.TransactionFormID = t.TransactionFormID AND tmd.DepartmentVotID = d.DepartmentVotID AND t.TransactionFormID = ? AND d.DepartmentVotID = ? AND tmd.Status = 'A'");
		ps.setInt(1, transactionFormMapDepartmentVot.getTransactionFormID());
		ps.setInt(2, transactionFormMapDepartmentVot.getDepartmentVotID());
		
		ResultSet rs = ps.executeQuery();
		
		TransactionFormMapDepartmentVot oldTransactionFormMapDepartmentVot = null;
		
		if (rs.next())
		{
			oldTransactionFormMapDepartmentVot = read(rs);
		}
		
		return oldTransactionFormMapDepartmentVot;
	}

	int updateTransactionFormMapDepartmentVot(TransactionFormMapDepartmentVot transactionFormMapDepartmentVot) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE TransactionFormMapDepartmentVot SET TransactionFormID = ?, DepartmentVotID = ?, Status = ? WHERE TransactionFormMapDepartmentVotID = ?");

		return write(transactionFormMapDepartmentVot, ps);
	}
	
	int deleteTransactionFormMapDepartmentVot(TransactionFormMapDepartmentVot transactionFormMapDepartmentVot) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE TransactionFormMapDepartmentVot SET Status = 'I' WHERE TransactionFormMapDepartmentVotID = ?");
		ps.setInt(1, transactionFormMapDepartmentVot.getTransactionFormMapDepartmentVotID());

		return ps.executeUpdate();
	}

	private TransactionFormMapDepartmentVot read(ResultSet rs) throws SQLException
	{
		TransactionFormMapDepartmentVot transactionFormMapDepartmentVot = new TransactionFormMapDepartmentVot();

		transactionFormMapDepartmentVot.setTransactionFormMapDepartmentVotID(rs.getInt("TransactionFormMapDepartmentVotID"));
		transactionFormMapDepartmentVot.setTransactionFormID(rs.getInt("TransactionFormID"));
		transactionFormMapDepartmentVot.setTransactionFormCode(rs.getString("TransactionFormCode"));
		transactionFormMapDepartmentVot.setTransactionFormName(rs.getString("TransactionFormName"));
		transactionFormMapDepartmentVot.setDepartmentVotID(rs.getInt("DepartmentVotID"));
		transactionFormMapDepartmentVot.setDepartmentVotCode(rs.getString("DepartmentVotCode"));
		transactionFormMapDepartmentVot.setDepartmentVotName(rs.getString("DepartmentVotName"));
		transactionFormMapDepartmentVot.setBursaryVotID(rs.getInt("BursaryVotID"));
		transactionFormMapDepartmentVot.setBursaryVotCode(rs.getString("BursaryVotCode"));
		transactionFormMapDepartmentVot.setBursaryVotName(rs.getString("BursaryVotName"));		
		transactionFormMapDepartmentVot.setStatus(rs.getString("Status"));

		return transactionFormMapDepartmentVot;
	}
	
	private ArrayList<TransactionFormMapDepartmentVot> getTransactionFormMapDepartmentVots(ResultSet rs) throws SQLException
	{
		ArrayList<TransactionFormMapDepartmentVot> transactionFormMapDepartmentVots = new ArrayList<>();

		while (rs.next())
		{
			transactionFormMapDepartmentVots.add(read(rs));
		}
			
		return transactionFormMapDepartmentVots;
	}

	TransactionFormMapDepartmentVot getTransactionFormMapDepartmentVot(int transactionFormMapDepartmentVotID) throws SQLException
	{
		TransactionFormMapDepartmentVot transactionFormMapDepartmentVot = null;
		PreparedStatement ps = facade.prepareStatement("SELECT tmd.*, t.Code AS TransactionFormCode, t.Name AS TransactionFormName, d.Code AS DepartmentVotCode, d.Name AS DepartmentVotName, b.BursaryVotID, b.Code AS BursaryVotCode, b.Name AS BursaryVotName FROM TransactionFormMapDepartmentVot tmd, TransactionForm t, DepartmentVot d, BursaryVot b WHERE tmd.TransactionFormID = t.TransactionFormID AND tmd.DepartmentVotID = d.DepartmentVotID AND d.BursaryVotID = b.BursaryVotID AND tmd.TransactionFormMapDepartmentVotID = ?");

		ps.setInt(1, transactionFormMapDepartmentVotID);

		ResultSet rs = ps.executeQuery();
		
		if (rs.next())
			transactionFormMapDepartmentVot = read(rs);
		
		return transactionFormMapDepartmentVot;
	}
	
	ArrayList<TransactionFormMapDepartmentVot> getTransactionFormMapDepartmentVots(String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT tmd.*, t.Code AS TransactionFormCode, t.Name AS TransactionFormName, d.Code AS DepartmentVotCode, d.Name AS DepartmentVotName, b.BursaryVotID, b.Code AS BursaryVotCode, b.Name AS BursaryVotName FROM TransactionFormMapDepartmentVot tmd, TransactionForm t, DepartmentVot d, BursaryVot b  WHERE tmd.TransactionFormID = t.TransactionFormID AND tmd.DepartmentVotID = d.DepartmentVotID AND d.BursaryVotID = b.BursaryVotID AND tmd.Status = ? ORDER BY t.Code, d.Code");

		ps.setString(1, status);

		return getTransactionFormMapDepartmentVots(ps.executeQuery());
	}
	
	ArrayList<TransactionFormMapDepartmentVot> getTransactionFormMapDepartmentVots(TransactionForm transactionForm, String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT tmd.*, t.Code AS TransactionFormCode, t.Name AS TransactionFormName, d.Code AS DepartmentVotCode, d.Name AS DepartmentVotName, b.BursaryVotID, b.Code AS BursaryVotCode, b.Name AS BursaryVotName  FROM TransactionFormMapDepartmentVot tmd, TransactionForm t, DepartmentVot d, BursaryVot b  WHERE tmd.TransactionFormID = t.TransactionFormID AND tmd.DepartmentVotID = d.DepartmentVotID AND d.BursaryVotID = b.BursaryVotID AND t.Name = ? AND tmd.Status = ? ORDER BY t.Code, d.Code");

		ps.setString(1, transactionForm.getName());
		ps.setString(2, status);

		return getTransactionFormMapDepartmentVots(ps.executeQuery());
	}
}