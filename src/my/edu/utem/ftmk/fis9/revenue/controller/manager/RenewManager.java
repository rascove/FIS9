package my.edu.utem.ftmk.fis9.revenue.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import my.edu.utem.ftmk.fis9.revenue.model.ForestDevelopmentContractor;
import my.edu.utem.ftmk.fis9.revenue.model.License;
import my.edu.utem.ftmk.fis9.revenue.model.LoggingContractor;
import my.edu.utem.ftmk.fis9.revenue.model.Permit;
import my.edu.utem.ftmk.fis9.revenue.model.Receipt;
import my.edu.utem.ftmk.fis9.revenue.model.Renew;

/**
 * @author Nor Azman Mat Ariff
 */
class RenewManager extends RevenueTableManager
{
	RenewManager(RevenueFacade facade)
	{
		super(facade);
	}

	private int write(Renew renew, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, renew.getType());
		nullable(ps, 2, renew.getForestDevelopmentContractorID());
		nullable(ps, 3, renew.getLoggingContractorID());
		nullable(ps, 4, renew.getLicenseID());
		nullable(ps, 5, renew.getPermitID());
		ps.setLong(6, renew.getReceiptID());
		ps.setDate(7, toSQLDate(renew.getStartDate()));
		ps.setDate(8, toSQLDate(renew.getEndDate()));
		ps.setString(9, renew.getStatus());
		ps.setLong(10, renew.getRenewID());
		
		return ps.executeUpdate();
	}

	int addRenew(Renew renew) throws SQLException
	{
		int status = 0;
		
		PreparedStatement ps = facade.prepareStatement("INSERT IGNORE INTO Renew (Type, ForestDevelopmentContractorID, LoggingContractorID, LicenseID, PermitID, ReceiptID, StartDate, EndDate, Status, RenewID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"); 
		
		status = write(renew, ps);

		return status;
	}

	int deleteRenew(Renew renew) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE Renew SET Status = 'I' WHERE RenewID = ?");
		ps.setLong(1, renew.getRenewID());

		return ps.executeUpdate();
	}

	private Renew readRenew(ResultSet rs) throws SQLException
	{
		Renew renew = new Renew();

		renew.setRenewID(rs.getLong("RenewID"));
		renew.setType(rs.getString("Type"));
		renew.setReceiptID(rs.getLong("ReceiptID"));
		renew.setStartDate(rs.getDate("StartDate"));
		renew.setEndDate(rs.getDate("EndDate"));
		renew.setStatus(rs.getString("Status"));
		if(renew.getType().equalsIgnoreCase("F"))
		{
			renew = readForestDevelopmentContractor(renew);
		}
		else
		{
			if(renew.getType().equalsIgnoreCase("C"))
			{
				renew = readLoggingContractor(renew);
				
			}
			else
			{
				if(renew.getType().equalsIgnoreCase("L"))
				{
					renew = readLicense(renew);
				}
				else
				{
					if(renew.getType().equalsIgnoreCase("P"))
					{
						renew = readPermit(renew);
					}
				}
			}
		}

		return renew;
	}
	
	private Renew readForestDevelopmentContractor(Renew renew) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT fdc.ForestDevelopmentContractorID " + 
				"FROM Renew r, ForestDevelopmentContractor fdc " + 
				"WHERE r.ForestDevelopmentContractorID = fdc.ForestDevelopmentContractorID AND r.renewID = ?");
		
		ps.setLong(1, renew.getRenewID());
		
		ResultSet rs = ps.executeQuery();

		if (rs.next())
			renew = readForestDevelopmentContractor(renew, rs);
		
		return renew;
	}
	
	private Renew readForestDevelopmentContractor(Renew renew, ResultSet rs) throws SQLException
	{
		renew.setForestDevelopmentContractorID(rs.getLong("ForestDevelopmentContractorID"));

		return renew;
	}
	
	private Renew readLoggingContractor(Renew renew) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT lc.LoggingContractorID " + 
				"FROM Renew r, LoggingContractor lc " + 
				"WHERE r.LoggingContractorID = lc.LoggingContractorID AND r.renewID = ?");
		
		ps.setLong(1, renew.getRenewID());
		
		ResultSet rs = ps.executeQuery();

		if (rs.next())
			renew = readLoggingContractor(renew, rs);
		
		return renew;
	}
	
	private Renew readLoggingContractor(Renew renew, ResultSet rs) throws SQLException
	{
		renew.setLoggingContractorID(rs.getLong("LoggingContractorID"));

		return renew;
	}
	
	private Renew readLicense(Renew renew) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT l.LicenseID " + 
				"FROM Renew r, License l " + 
				"WHERE r.LicenseID = l.LicenseID AND r.renewID = ?");
		
		ps.setLong(1, renew.getRenewID());
		
		ResultSet rs = ps.executeQuery();

		if (rs.next())
			renew = readLicense(renew, rs);
		
		return renew;
	}
	
	private Renew readLicense(Renew renew, ResultSet rs) throws SQLException
	{
		renew.setLicenseID(rs.getLong("LicenseID"));

		return renew;
	}
	
	private Renew readPermit(Renew renew) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT p.PermitID " + 
				"FROM Renew r, Permit p " + 
				"WHERE r.PermitID = p.PermitID AND r.renewID = ?");
		
		ps.setLong(1, renew.getRenewID());
		
		ResultSet rs = ps.executeQuery();

		if (rs.next())
			renew = readPermit(renew, rs);
		
		return renew;
	}
	
	private Renew readPermit(Renew renew, ResultSet rs) throws SQLException
	{
		renew.setPermitID(rs.getLong("PermitID"));

		return renew;
	}

	private ArrayList<Renew> getRenews(ResultSet rs) throws SQLException
	{
		ArrayList<Renew> renews = new ArrayList<>();

		while (rs.next())
		{
			Renew renew = readRenew(rs);
			renews.add(renew);
		}
		return renews;
	}

	Renew getRenew(long renewID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT r.* " + 
				"FROM Renew r, Receipt rc " + 
				"WHERE r.ReceiptID = rc.ReceiptID AND r.RenewID = ?");

		ps.setLong(1, renewID);

		Renew renew = null;
		ResultSet rs = ps.executeQuery();

		if (rs.next())
		{
			renew = readRenew(rs);
		}

		return renew;
	}	
		
	Renew getRenew(Receipt receipt) throws SQLException
	{
		Renew renew = new Renew();
		PreparedStatement ps = facade.prepareStatement("SELECT r.* " + 
				"FROM Renew r, Receipt rc " + 
				"WHERE r.ReceiptID = rc.ReceiptID AND r.ReceiptID = ?");

		ps.setLong(1, receipt.getReceiptID());

		ResultSet rs = ps.executeQuery();

		if (rs.next())
		{
			renew = readRenew(rs);
		}		

		return renew;
	}
	
	ArrayList<Renew> getRenews(ForestDevelopmentContractor forestDevelopmentContractor, String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT r.* " + 
				"FROM Renew r, Receipt rc " + 
				"WHERE r.ReceiptID = rc.ReceiptID AND r.ForestDevelopmentContractorID = ? AND r.Status = ? ORDER BY r.EndDate DESC");

		ps.setLong(1, forestDevelopmentContractor.getForestDevelopmentContractorID());
		ps.setString(2, status);

		return getRenews(ps.executeQuery());
	}
	
	ArrayList<Renew> getRenews(LoggingContractor loggingContractor, String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT r.* " + 
				"FROM Renew r, Receipt rc " + 
				"WHERE r.ReceiptID = rc.ReceiptID AND r.LoggingContractorID = ? AND r.Status = ? ORDER BY r.EndDate DESC");

		ps.setLong(1, loggingContractor.getLoggingContractorID());
		ps.setString(2, status);

		return getRenews(ps.executeQuery());
	}
	
	ArrayList<Renew> getRenews(License license, String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT r.* " + 
				"FROM Renew r, Receipt rc " + 
				"WHERE r.ReceiptID = rc.ReceiptID AND r.LicenseID = ? AND r.Status = ? ORDER BY r.EndDate DESC");

		ps.setLong(1, license.getLicenseID());
		ps.setString(2, status);

		return getRenews(ps.executeQuery());
	}
	
	ArrayList<Renew> getRenews(Permit permit, String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT r.* " + 
				"FROM Renew r, Receipt rc " + 
				"WHERE r.ReceiptID = rc.ReceiptID AND r.PermitID = ? AND r.Status = ? ORDER BY r.EndDate DESC");

		ps.setLong(1, permit.getPermitID());
		ps.setString(2, status);

		return getRenews(ps.executeQuery());
	}
	
	ArrayList<Renew> getRenews(String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT r.* " + 
				"FROM Renew r, Receipt rc " + 
				"WHERE r.ReceiptID = rc.ReceiptID AND r.Status = ?");

		ps.setString(1, status);

		return getRenews(ps.executeQuery());
	}
	
	ArrayList<Renew> getRenews(String status, Date date) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT r.* " + 
				"FROM Renew r, Receipt rc " + 
				"WHERE r.ReceiptID = rc.ReceiptID AND r.Status = ? AND r.EndDate >= ? ORDER BY r.EndDate DESC");

		ps.setString(1, status);
		ps.setDate(2, toSQLDate(date));

		return getRenews(ps.executeQuery());
	}
	
	ArrayList<Renew> getRenews(String type, String status, Date date) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT r.* " + 
				"FROM Renew r, Receipt rc " + 
				"WHERE r.ReceiptID = rc.ReceiptID AND r.Type = ? AND r.Status = ? AND r.EndDate >= ? ORDER BY r.EndDate DESC");

		ps.setString(1, type);
		ps.setString(2, status);
		ps.setDate(3, toSQLDate(date));

		return getRenews(ps.executeQuery());
	}
}