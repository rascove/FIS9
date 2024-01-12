package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.LicenseType;

/**
 * @author Nor Azman Mat Ariff
 */
class LicenseTypeManager extends MaintenanceTableManager
{
	LicenseTypeManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(LicenseType licenseType, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, licenseType.getCode());
		ps.setString(2, licenseType.getName());
		ps.setString(3, licenseType.getStatus());

		if (licenseType.getLicenseTypeID() != 0)
			ps.setInt(4, licenseType.getLicenseTypeID());

		return ps.executeUpdate();
	}

	int addLicenseType(LicenseType licenseType) throws SQLException
	{
		int status = 0;
		if(checkExistingCode(licenseType) == null)
		{
			PreparedStatement ps = facade.prepareStatement("INSERT INTO LicenseType (Code, Name, Status) VALUES (?, ?, ?)");
			status = write(licenseType, ps);
	
			if (status != 0)
			{
				ResultSet rs = ps.getGeneratedKeys();
	
				if (rs.next())
					licenseType.setLicenseTypeID(rs.getInt(1));
			}
		}
		
		return status;
	}
	
	private LicenseType checkExistingCode(LicenseType licenseType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM LicenseType WHERE Code = ? AND Status = 'A'");
		ps.setString(1, licenseType.getCode());
		
		ResultSet rs = ps.executeQuery();
		
		LicenseType oldLicenseType = null;
		
		if (rs.next())
		{
			oldLicenseType = read(rs);
		}
		
		return oldLicenseType;
	}

	int updateLicenseType(LicenseType licenseType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE LicenseType SET Code = ?, Name = ?, Status = ? WHERE LicenseTypeID = ?");

		return write(licenseType, ps);
	}
	
	int deleteLicenseType(LicenseType licenseType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE LicenseType SET Status = 'I' WHERE LicenseTypeID = ?");
		ps.setInt(1, licenseType.getLicenseTypeID());

		return ps.executeUpdate();
	}

	private LicenseType read(ResultSet rs) throws SQLException
	{
		LicenseType licenseType = new LicenseType();

		licenseType.setLicenseTypeID(rs.getInt("LicenseTypeID"));
		licenseType.setCode(rs.getString("Code"));
		licenseType.setName(rs.getString("Name"));
		licenseType.setStatus(rs.getString("Status"));

		return licenseType;
	}
	
	private ArrayList<LicenseType> getLicenseTypes(ResultSet rs) throws SQLException
	{
		ArrayList<LicenseType> licenseTypes = new ArrayList<>();

		while (rs.next())
		{
			licenseTypes.add(read(rs));
		}
		return licenseTypes;
	}

	LicenseType getLicenseType(int licenseTypeID) throws SQLException
	{
		LicenseType licenseType = null;
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM LicenseType WHERE LicenseTypeID = ?");

		ps.setInt(1, licenseTypeID);

		ResultSet rs = ps.executeQuery();
		
		if (rs.next())
			licenseType = read(rs);
		
		return licenseType;
	}
	
	ArrayList<LicenseType> getLicenseTypes(String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM LicenseType WHERE Status = ? ORDER BY Code");

		ps.setString(1, status);

		return getLicenseTypes(ps.executeQuery());
	}
}