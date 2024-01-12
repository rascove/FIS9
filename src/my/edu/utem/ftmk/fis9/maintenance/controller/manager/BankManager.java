package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.Bank;

/**
 * @author Nor Azman Mat Ariff
 */
class BankManager extends MaintenanceTableManager
{
	BankManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(Bank bank, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, bank.getCode());
		ps.setString(2, bank.getName());
		ps.setString(3, bank.getStatus());

		if (bank.getBankID() != 0)
			ps.setInt(4, bank.getBankID());

		return ps.executeUpdate();
	}

	int addBank(Bank bank) throws SQLException
	{
		int status = 0;
		if(checkExistingCode(bank) == null)
		{
			PreparedStatement ps = facade.prepareStatement("INSERT INTO Bank (Code, Name, Status) VALUES (?, ?, ?)");
			status = write(bank, ps);
	
			if (status != 0)
			{
				ResultSet rs = ps.getGeneratedKeys();
	
				if (rs.next())
					bank.setBankID(rs.getInt(1));
			}
		}
		
		return status;
	}
	
	private Bank checkExistingCode(Bank bank) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM Bank WHERE Code = ? AND Status = 'A'");
		ps.setString(1, bank.getCode());
		
		ResultSet rs = ps.executeQuery();
		
		Bank oldBank = null;
		
		if (rs.next())
		{
			oldBank = read(rs);
		}
		
		return oldBank;
	}

	int updateBank(Bank bank) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE Bank SET Code = ?, Name = ?, Status = ? WHERE BankID = ?");

		return write(bank, ps);
	}
	
	int deleteBank(Bank bank) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE Bank SET Status = 'I' WHERE BankID = ?");
		ps.setInt(1, bank.getBankID());

		return ps.executeUpdate();
	}

	private Bank read(ResultSet rs) throws SQLException
	{
		Bank bank = new Bank();

		bank.setBankID(rs.getInt("BankID"));
		bank.setCode(rs.getString("Code"));
		bank.setName(rs.getString("Name"));
		bank.setStatus(rs.getString("Status"));

		return bank;
	}
	
	private ArrayList<Bank> getBanks(ResultSet rs) throws SQLException
	{
		ArrayList<Bank> banks = new ArrayList<>();

		while (rs.next())
		{
			banks.add(read(rs));
		}
		return banks;
	}

	Bank getBank(int bankID) throws SQLException
	{
		Bank bank = null;
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM Bank WHERE BankID = ?");

		ps.setInt(1, bankID);

		ResultSet rs = ps.executeQuery();
		
		if (rs.next())
			bank = read(rs);
		
		return bank;
	}
	
	ArrayList<Bank> getBanks(String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM Bank WHERE Status = ? ORDER BY Code");

		ps.setString(1, status);

		return getBanks(ps.executeQuery());
	}
}