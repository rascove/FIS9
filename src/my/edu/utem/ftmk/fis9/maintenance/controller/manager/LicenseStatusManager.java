package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.LicenseStatus;

/**
 * @author Nor Azman Mat Ariff
 */
class LicenseStatusManager extends MaintenanceTableManager
{
	LicenseStatusManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(LicenseStatus licenseStatus, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, licenseStatus.getCode());
		ps.setString(2, licenseStatus.getName());
		ps.setString(3, licenseStatus.getStatus());

		if (licenseStatus.getLicenseStatusID() != 0)
			ps.setInt(4, licenseStatus.getLicenseStatusID());

		return ps.executeUpdate();
	}

	int addLicenseStatus(LicenseStatus licenseStatus) throws SQLException
	{
		int status = 0;
		if(checkExistingCode(licenseStatus) == null)
		{
			PreparedStatement ps = facade.prepareStatement("INSERT INTO LicenseStatus (Code, Name, Status) VALUES (?, ?, ?)");
			status = write(licenseStatus, ps);
	
			if (status != 0)
			{
				ResultSet rs = ps.getGeneratedKeys();
	
				if (rs.next())
					licenseStatus.setLicenseStatusID(rs.getInt(1));
			}
		}
		
		return status;
	}
	
	private LicenseStatus checkExistingCode(LicenseStatus licenseStatus) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM LicenseStatus WHERE Code = ? AND Status = 'A'");
		ps.setString(1, licenseStatus.getCode());
		
		ResultSet rs = ps.executeQuery();
		
		LicenseStatus oldLicenseStatus = null;
		
		if (rs.next())
		{
			oldLicenseStatus = read(rs);
		}
		
		return oldLicenseStatus;
	}

	int updateLicenseStatus(LicenseStatus licenseStatus) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE LicenseStatus SET Code = ?, Name = ?, Status = ? WHERE LicenseStatusID = ?");

		return write(licenseStatus, ps);
	}
	
	int deleteLicenseStatus(LicenseStatus licenseStatus) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE LicenseStatus SET Status = 'I' WHERE LicenseStatusID = ?");
		ps.setInt(1, licenseStatus.getLicenseStatusID());

		return ps.executeUpdate();
	}

	private LicenseStatus read(ResultSet rs) throws SQLException
	{
		LicenseStatus licenseStatus = new LicenseStatus();

		licenseStatus.setLicenseStatusID(rs.getInt("LicenseStatusID"));
		licenseStatus.setCode(rs.getString("Code"));
		licenseStatus.setName(rs.getString("Name"));
		licenseStatus.setStatus(rs.getString("Status"));

		return licenseStatus;
	}
	
	private ArrayList<LicenseStatus> getLicenseStatuss(ResultSet rs) throws SQLException
	{
		ArrayList<LicenseStatus> licenseStatuss = new ArrayList<>();

		while (rs.next())
		{
			licenseStatuss.add(read(rs));
		}
		return licenseStatuss;
	}

	LicenseStatus getLicenseStatus(int licenseStatusID) throws SQLException
	{
		LicenseStatus licenseStatus = null;
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM LicenseStatus WHERE LicenseStatusID = ?");

		ps.setInt(1, licenseStatusID);

		ResultSet rs = ps.executeQuery();
		
		if (rs.next())
			licenseStatus = read(rs);
		
		return licenseStatus;
	}
	
	ArrayList<LicenseStatus> getLicenseStatuss(String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM LicenseStatus WHERE Status = ? ORDER BY Code");

		ps.setString(1, status);

		return getLicenseStatuss(ps.executeQuery());
	}
}