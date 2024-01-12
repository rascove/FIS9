package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.TransactionForm;
import my.edu.utem.ftmk.fis9.maintenance.model.TransactionFormMapTrustFund;

/**
 * @author Nor Azman Mat Ariff
 */
class TransactionFormMapTrustFundManager extends MaintenanceTableManager
{
	TransactionFormMapTrustFundManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(TransactionFormMapTrustFund transactionFormMapTrustFund, PreparedStatement ps) throws SQLException
	{
		ps.setInt(1, transactionFormMapTrustFund.getTransactionFormID());
		ps.setInt(2, transactionFormMapTrustFund.getTrustFundID());
		ps.setString(3, transactionFormMapTrustFund.getStatus());

		if (transactionFormMapTrustFund.getTransactionFormMapTrustFundID() != 0)
			ps.setInt(4, transactionFormMapTrustFund.getTransactionFormMapTrustFundID());

		return ps.executeUpdate();
	}

	int addTransactionFormMapTrustFund(TransactionFormMapTrustFund transactionFormMapTrustFund) throws SQLException
	{
		
		int status = 0;
		if(checkExistingCode(transactionFormMapTrustFund) == null)
		{
			PreparedStatement ps = facade.prepareStatement("INSERT INTO TransactionFormMapTrustFund (TransactionFormID, TrustFundID, Status) VALUES (?, ?, ?)");
			status = write(transactionFormMapTrustFund, ps);

			if (status != 0)
			{
				ResultSet rs = ps.getGeneratedKeys();
	
				if (rs.next())
					transactionFormMapTrustFund.setTransactionFormMapTrustFundID(rs.getInt(1));
			}
		}

		return status;
	}
	
	TransactionFormMapTrustFund checkExistingCode(TransactionFormMapTrustFund transactionFormMapTrustFund) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT tfmmtfu.*, tfm.Code AS TransactionFormCode, tfm.Name AS TransactionFormName, d.Code AS TrustFundCode, d.Name AS TrustFundName " + 
				"FROM TransactionFormMapTrustFund tfmmtfu, TransactionForm tfm, TrustFund tfu, DepartmentVot d " + 
				"WHERE tfmmtfu.TransactionFormID = tfm.TransactionFormID AND tfmmtfu.TrustFundID = tfu.TrustFundID AND tfu.DepartmentVotID = d.DepartmentVotID AND tfmmtfu.TransactionFormID = ? AND tfmmtfu.TrustFundID = ? AND tfmmtfu.Status = 'A'");
		ps.setInt(1, transactionFormMapTrustFund.getTransactionFormID());
		ps.setInt(2, transactionFormMapTrustFund.getTrustFundID());
		
		ResultSet rs = ps.executeQuery();
		
		TransactionFormMapTrustFund oldTransactionFormMapTrustFund = null;
		
		if (rs.next())
		{
			oldTransactionFormMapTrustFund = read(rs);
		}
		
		return oldTransactionFormMapTrustFund;
	}

	int updateTransactionFormMapTrustFund(TransactionFormMapTrustFund transactionFormMapTrustFund) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE TransactionFormMapTrustFund SET TransactionFormID = ?, TrustFundID = ?, Status = ? WHERE TransactionFormMapTrustFundID = ?");

		return write(transactionFormMapTrustFund, ps);
	}
	
	int deleteTransactionFormMapTrustFund(TransactionFormMapTrustFund transactionFormMapTrustFund) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE TransactionFormMapTrustFund SET Status = 'I' WHERE TransactionFormMapTrustFundID = ?");
		ps.setInt(1, transactionFormMapTrustFund.getTransactionFormMapTrustFundID());

		return ps.executeUpdate();
	}

	private TransactionFormMapTrustFund read(ResultSet rs) throws SQLException
	{
		TransactionFormMapTrustFund transactionFormMapTrustFund = new TransactionFormMapTrustFund();

		transactionFormMapTrustFund.setTransactionFormMapTrustFundID(rs.getInt("TransactionFormMapTrustFundID"));
		transactionFormMapTrustFund.setTransactionFormID(rs.getInt("TransactionFormID"));
		transactionFormMapTrustFund.setTransactionFormCode(rs.getString("TransactionFormCode"));
		transactionFormMapTrustFund.setTransactionFormName(rs.getString("TransactionFormName"));
		transactionFormMapTrustFund.setTrustFundID(rs.getInt("TrustFundID"));
		transactionFormMapTrustFund.setTrustFundCode(rs.getString("TrustFundCode"));
		transactionFormMapTrustFund.setTrustFundName(rs.getString("TrustFundName"));
		transactionFormMapTrustFund.setStatus(rs.getString("Status"));

		return transactionFormMapTrustFund;
	}
	
	private ArrayList<TransactionFormMapTrustFund> getTransactionFormMapTrustFunds(ResultSet rs) throws SQLException
	{
		ArrayList<TransactionFormMapTrustFund> transactionFormMapTrustFunds = new ArrayList<>();

		while (rs.next())
		{
			transactionFormMapTrustFunds.add(read(rs));
		}
			
		return transactionFormMapTrustFunds;
	}

	TransactionFormMapTrustFund getTransactionFormMapTrustFund(int transactionFormMapTrustFundID) throws SQLException
	{
		TransactionFormMapTrustFund transactionFormMapTrustFund = null;
		PreparedStatement ps = facade.prepareStatement("SELECT tfmmtfu.*, tfm.Code AS TransactionFormCode, tfm.Name AS TransactionFormName, d.Code AS TrustFundCode, d.Name AS TrustFundName " + 
				"FROM TransactionFormMapTrustFund tfmmtfu, TransactionForm tfm, TrustFund tfu, DepartmentVot d " + 
				"WHERE tfmmtfu.TransactionFormID = tfm.TransactionFormID AND tfmmtfu.TrustFundID = tfu.TrustFundID AND tfu.DepartmentVotID = d.DepartmentVotID AND tfmmtfu.TransactionFormMapTrustFundID = ?");

		ps.setInt(1, transactionFormMapTrustFundID);

		ResultSet rs = ps.executeQuery();
		
		if (rs.next())
			transactionFormMapTrustFund = read(rs);
		
		return transactionFormMapTrustFund;
	}
	
	ArrayList<TransactionFormMapTrustFund> getTransactionFormMapTrustFunds(String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT tfmmtfu.*, tfm.Code AS TransactionFormCode, tfm.Name AS TransactionFormName, d.Code AS TrustFundCode, d.Name AS TrustFundName " + 
				"FROM TransactionFormMapTrustFund tfmmtfu, TransactionForm tfm, TrustFund tfu, DepartmentVot d " + 
				"WHERE tfmmtfu.TransactionFormID = tfm.TransactionFormID AND tfmmtfu.TrustFundID = tfu.TrustFundID AND tfu.DepartmentVotID = d.DepartmentVotID AND tfmmtfu.Status = ? ORDER BY tfm.Code, d.Code");

		ps.setString(1, status);

		return getTransactionFormMapTrustFunds(ps.executeQuery());
	}
	
	ArrayList<TransactionFormMapTrustFund> getTransactionFormMapTrustFunds(TransactionForm transactionForm, String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT tfmmtfu.*, tfm.Code AS TransactionFormCode, tfm.Name AS TransactionFormName, d.Code AS TrustFundCode, d.Name AS TrustFundName " + 
				"FROM TransactionFormMapTrustFund tfmmtfu, TransactionForm tfm, TrustFund tfu, DepartmentVot d " + 
				"WHERE tfmmtfu.TransactionFormID = tfm.TransactionFormID AND tfmmtfu.TrustFundID = tfu.TrustFundID AND tfu.DepartmentVotID = d.DepartmentVotID AND tfm.Name = ? AND tfmmtfu.Status = ? ORDER BY tfm.Code, d.Code");
		
		ps.setString(1, transactionForm.getName());
		ps.setString(2, status);

		return getTransactionFormMapTrustFunds(ps.executeQuery());
	}
}