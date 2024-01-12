package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.Contractor;

/**
 * @author Nor Azman Mat Ariff
 * @author Satrya Fajri Pratama
 */
class ContractorManager extends MaintenanceTableManager
{
	ContractorManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int writeContractor(Contractor contractor, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, contractor.getType());
		ps.setString(2, contractor.getName());
		ps.setString(3, contractor.getCompanyName());
		ps.setString(4, contractor.getRegistrationNo());
		ps.setString(5, contractor.getAddress());
		ps.setString(6, contractor.getPostcode());
		ps.setInt(7, contractor.getRegionID());
		ps.setString(8, contractor.getTelNo());
		ps.setString(9, contractor.getFaxNo());
		ps.setString(10, contractor.getStatus());
		ps.setString(11, contractor.getContractorID());

		return ps.executeUpdate();		
	}

	int addContractor(Contractor contractor) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT IGNORE INTO Contractor (Type, Name, CompanyName, RegistrationNo, Address, Postcode, RegionID, TelNo, FaxNo, Status, ContractorID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		return writeContractor(contractor, ps);
	}

	int updateContractor(Contractor contractor) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE Contractor SET Type = ?, Name = ?, CompanyName = ?, RegistrationNo = ?, Address = ?, Postcode = ?, RegionID = ?, TelNo = ?, FaxNo = ?, Status = ? WHERE ContractorID = ?");

		return writeContractor(contractor, ps);
	}

	private Contractor read(ResultSet rs) throws SQLException
	{
		Contractor contractor = new Contractor();

		contractor.setContractorID(rs.getString("ContractorID"));
		contractor.setType(rs.getString("Type"));
		contractor.setName(rs.getString("Name"));
		contractor.setCompanyName(rs.getString("CompanyName"));
		contractor.setRegistrationNo(rs.getString("RegistrationNo"));
		contractor.setAddress(rs.getString("Address"));
		contractor.setPostcode(rs.getString("Postcode"));
		contractor.setRegionID(rs.getInt("RegionID"));
		contractor.setTelNo(rs.getString("TelNo"));
		contractor.setFaxNo(rs.getString("FaxNo"));
		contractor.setStatus(rs.getString("Status"));
		contractor.setRegionName(rs.getString("RegionName") + ", " + rs.getString("StateName"));

		return contractor;
	}
	
	Contractor getContractor(String contractorID) throws SQLException
	{
		Contractor contractor = null;
		PreparedStatement ps = facade.prepareStatement("SELECT c.*, d.Name AS RegionName, s.Name AS StateName FROM Contractor c, Region d, State s WHERE C.RegionID = d.RegionID AND d.StateID = s.StateID AND c.ContractorID = ?");

		ps.setString(1, contractorID);

		ResultSet rs = ps.executeQuery();

		if (rs.next())
			contractor = read(rs);

		return contractor;
	}

	Contractor getContractor(Contractor contractor, String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT c.*, d.Name AS RegionName, s.Name AS StateName FROM Contractor c, Region d, State s WHERE C.RegionID = d.RegionID AND d.StateID = s.StateID AND c.ContractorID = ? and c.Status = ?");

		ps.setString(1, contractor.getContractorID());
		ps.setString(2, status);

		ResultSet rs = ps.executeQuery();

		if (rs.next())
			contractor = read(rs);

		return contractor;
	}

	ArrayList<Contractor> getContractors(String status) throws SQLException
	{
		ArrayList<Contractor> contractors = new ArrayList<>();
		PreparedStatement ps = facade.prepareStatement("SELECT c.*, d.Name AS RegionName, s.Name AS StateName FROM Contractor c, Region d, State s WHERE C.RegionID = d.RegionID AND d.StateID = s.StateID AND c.Status = ? ORDER BY c.ContractorID");

		ps.setString(1,  status);

		ResultSet rs = ps.executeQuery();

		while (rs.next())
			contractors.add(read(rs));

		return contractors;
	}
}