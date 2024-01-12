package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.TransactionForm;

/**
 * @author Nor Azman Mat Ariff
 */
class TransactionFormManager extends MaintenanceTableManager
{
	TransactionFormManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(TransactionForm transactionForm, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, transactionForm.getCode());
		ps.setString(2, transactionForm.getName());
		ps.setString(3, transactionForm.getStatus());

		if (transactionForm.getTransactionFormID() != 0)
			ps.setInt(4, transactionForm.getTransactionFormID());

		return ps.executeUpdate();
	}

	int addTransactionForm(TransactionForm transactionForm) throws SQLException
	{
		int status = 0;
		if(checkExistingCode(transactionForm) == null)
		{
			PreparedStatement ps = facade.prepareStatement("INSERT INTO TransactionForm (Code, Name, Status) VALUES (?, ?, ?)");
			status = write(transactionForm, ps);
	
			if (status != 0)
			{
				ResultSet rs = ps.getGeneratedKeys();
	
				if (rs.next())
					transactionForm.setTransactionFormID(rs.getInt(1));
			}
		}
		
		return status;
	}
	
	TransactionForm checkExistingCode(TransactionForm transactionForm) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM TransactionForm WHERE Code = ? AND Status = 'A'");
		ps.setString(1, transactionForm.getCode());
		
		ResultSet rs = ps.executeQuery();
		
		TransactionForm oldTransactionForm = null;
		
		if (rs.next())
		{
			oldTransactionForm = read(rs);
		}
		
		return oldTransactionForm;
	}

	int updateTransactionForm(TransactionForm transactionForm) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE TransactionForm SET Code = ?, Name = ?, Status = ? WHERE TransactionFormID = ?");

		return write(transactionForm, ps);
	}
	
	int deleteTransactionForm(TransactionForm transactionForm) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE TransactionForm SET Status = 'I' WHERE TransactionFormID = ?");
		ps.setInt(1, transactionForm.getTransactionFormID());

		return ps.executeUpdate();
	}

	private TransactionForm read(ResultSet rs) throws SQLException
	{
		TransactionForm transactionForm = new TransactionForm();
		
		transactionForm.setTransactionFormID(rs.getInt("TransactionFormID"));
		transactionForm.setCode(rs.getString("Code"));
		transactionForm.setName(rs.getString("Name"));
		transactionForm.setStatus(rs.getString("Status"));

		return transactionForm;
	}
	
	private ArrayList<TransactionForm> getTransactionForms(ResultSet rs) throws SQLException
	{
		ArrayList<TransactionForm> transactionForms = new ArrayList<>();

		while (rs.next())
		{
			transactionForms.add(read(rs));
		}
		return transactionForms;
	}

	TransactionForm getTransactionForm(int transactionFormID) throws SQLException
	{
		TransactionForm transactionForm = null;
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM TransactionForm WHERE TransactionFormID = ?");

		ps.setInt(1, transactionFormID);

		ResultSet rs = ps.executeQuery();
		
		if (rs.next())
			transactionForm = read(rs);
		
		return transactionForm;
	}
	
	ArrayList<TransactionForm> getTransactionForms(String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM TransactionForm WHERE Status = ? ORDER BY Code");

		ps.setString(1, status);

		return getTransactionForms(ps.executeQuery());
	}
	
	TransactionForm getTransactionFormByName(TransactionForm transactionForm) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM TransactionForm WHERE Name = ?");

		ps.setString(1, transactionForm.getName());
		
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			transactionForm = read(rs);
		}

		return transactionForm;
	}
}