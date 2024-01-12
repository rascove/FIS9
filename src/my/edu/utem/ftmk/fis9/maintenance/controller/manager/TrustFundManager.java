package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.TrustFund;

/**
 * @author Nor Azman Mat Ariff
 */
class TrustFundManager extends MaintenanceTableManager
{
	TrustFundManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(TrustFund trustFund, PreparedStatement ps) throws SQLException
	{
		ps.setInt(1, trustFund.getDepartmentVotID());
		ps.setString(2, trustFund.getDescription());
		ps.setString(3, trustFund.getStatus());

		if (trustFund.getTrustFundID() != 0)
			ps.setInt(4, trustFund.getTrustFundID());

		return ps.executeUpdate();
	}

	int addTrustFund(TrustFund trustFund) throws SQLException
	{
		int status = 0;
		if(checkExistingDepartmentVot(trustFund) == null)
		{
			PreparedStatement ps = facade.prepareStatement("INSERT INTO TrustFund (DepartmentVotID, Description, Symbol, Status) VALUES (?, ?, ?, ?)");
			status = write(trustFund, ps);
	
			if (status != 0)
			{
				ResultSet rs = ps.getGeneratedKeys();
	
				if (rs.next())
					trustFund.setTrustFundID(rs.getInt(1));
			}
		}
		return status;
	}
	
	TrustFund checkExistingDepartmentVot(TrustFund trustFund) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT t.*, d.Code AS DepartmentVotCode, d.Name AS DepartmentVotName FROM TrustFund t, DepartmentVot d WHERE t.DepartmentVotID = d.DepartmentVotID AND t.DepartmentVotID = ? AND t.Status = 'A'");
		ps.setInt(1, trustFund.getDepartmentVotID());
		
		ResultSet rs = ps.executeQuery();
		
		TrustFund oldTrustFund = null;
		
		if (rs.next())
		{
			oldTrustFund = read(rs);
		}
		
		return oldTrustFund;
	}

	int updateTrustFund(TrustFund trustFund) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE TrustFund SET DepartmentVotID = ? WHERE TrustFundID = ?");
		ps.setInt(1, trustFund.getDepartmentVotID());
		ps.setInt(2, trustFund.getTrustFundID());
		
		return ps.executeUpdate();
	}
	
	int deleteTrustFund(TrustFund trustFund) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE TrustFund SET Status = 'I' WHERE TrustFundID = ?");
		ps.setInt(1, trustFund.getTrustFundID());

		return ps.executeUpdate();
	}

	private TrustFund read(ResultSet rs) throws SQLException
	{
		TrustFund trustFund = new TrustFund();

		trustFund.setTrustFundID(rs.getInt("TrustFundID"));
		trustFund.setDepartmentVotID(rs.getInt("DepartmentVotID"));
		trustFund.setDepartmentVotCode(rs.getString("DepartmentVotCode"));
		trustFund.setDepartmentVotName(rs.getString("DepartmentVotName"));
		trustFund.setBursaryVotID(rs.getInt("BursaryVotID"));
		trustFund.setBursaryVotCode(rs.getString("BursaryVotCode"));
		trustFund.setBursaryVotName(rs.getString("BursaryVotName"));		
		trustFund.setDescription(rs.getString("Description"));		
		trustFund.setSymbol(rs.getString("Symbol"));
		trustFund.setStatus(rs.getString("Status"));

		return trustFund;
	}
	
	private ArrayList<TrustFund> getTrustFunds(ResultSet rs) throws SQLException
	{
		ArrayList<TrustFund> trustFunds = new ArrayList<>();

		while (rs.next())
		{
			trustFunds.add(read(rs));
		}

		return trustFunds;
	}

	TrustFund getTrustFund(int trustFundID) throws SQLException
	{
		TrustFund trustFund = null;
		PreparedStatement ps = facade.prepareStatement("SELECT t.*, d.Code AS DepartmentVotCode, d.Name AS DepartmentVotName, b.BursaryVotID, b.Code AS BursaryVotCode, b.Name AS BursaryVotName FROM TrustFund t, DepartmentVot d, Bursary b WHERE t.DepartmentVotID = d.DepartmentVotID AND d.BursaryID = b.BursaryID AND t.TrustFundID = ?");

		ps.setInt(1, trustFundID);

		ResultSet rs = ps.executeQuery();
		
		if (rs.next())
			trustFund = read(rs);
		
		return trustFund;
	}
	
	ArrayList<TrustFund> getTrustFunds(String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT t.*, d.Code AS DepartmentVotCode, d.Name AS DepartmentVotName, b.BursaryVotID, b.Code AS BursaryVotCode, b.Name AS BursaryVotName FROM TrustFund t, DepartmentVot d, BursaryVot b WHERE t.DepartmentVotID = d.DepartmentVotID AND d.BursaryVotID = b.BursaryVotID AND t.Status = ? ORDER BY d.Code");

		ps.setString(1, status);

		return getTrustFunds(ps.executeQuery());
	}
}