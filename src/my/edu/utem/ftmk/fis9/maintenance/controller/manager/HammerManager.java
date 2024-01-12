package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.Contractor;
import my.edu.utem.ftmk.fis9.maintenance.model.District;
import my.edu.utem.ftmk.fis9.maintenance.model.Hammer;
import my.edu.utem.ftmk.fis9.maintenance.model.HammerType;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.tagging.model.Tagging;

/**
 * @author Satrya Fajri Pratama
 */
class HammerManager extends MaintenanceTableManager
{
	HammerManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(Hammer hammer, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, hammer.getRegistrationNo());
		nullable(ps, 2, hammer.getHammerTypeID());
		nullable(ps, 3, hammer.getDistrictID());
		ps.setString(4, hammer.getContractorID());
		ps.setString(5, hammer.getHammerNo());

		return ps.executeUpdate();
	}

	int addHammer(Hammer hammer) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT IGNORE INTO Hammer (RegistrationNo, HammerTypeID, DistrictID, ContractorID, HammerNo) VALUES (?, ?, ?, ?, ?)");

		return write(hammer, ps);
	}

	int updateHammer(Hammer hammer) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE Hammer SET RegistrationNo = ?, HammerTypeID = ?, DistrictID = ?, ContractorID = ? WHERE HammerNo = ?");

		return write(hammer, ps);
	}

	private Hammer getHammer(ResultSet rs) throws SQLException
	{
		Hammer hammer = new Hammer();

		hammer.setHammerNo(rs.getString("HammerNo"));
		hammer.setRegistrationNo(rs.getString("RegistrationNo"));
		hammer.setHammerTypeID(rs.getInt("HammerTypeID"));
		hammer.setDistrictID(rs.getInt("DistrictID"));
		hammer.setContractorID(rs.getString("ContractorID"));
		hammer.setHammerTypeName(rs.getString("HammerTypeName"));
		hammer.setStateID(rs.getInt("StateID"));
		hammer.setDistrictName(rs.getString("DistrictName"));
		hammer.setStateName(rs.getString("StateName"));
		hammer.setContractorName(rs.getString("ContractorName"));

		return hammer;
	}

	private ArrayList<Hammer> getHammers(ResultSet rs) throws SQLException
	{
		ArrayList<Hammer> hammers = new ArrayList<>();

		while (rs.next())
			hammers.add(getHammer(rs));

		return hammers;
	}

	Hammer getHammer(String hammerNo) throws SQLException
	{
		Hammer hammer = null;
		PreparedStatement ps = facade.prepareStatement("SELECT h.*, ht.Name AS HammerTypeName, d.StateID, d.Name AS DistrictName, s.Name AS StateName, c.CompanyName AS ContractorName FROM Hammer h LEFT JOIN HammerType ht ON h.HammerTypeID = ht.HammerTypeID LEFT JOIN (District d, State s) ON (h.DistrictID = d.DistrictID AND d.StateID = s.StateID) LEFT JOIN Contractor c ON h.ContractorID = c.ContractorID WHERE h.HammerNo = ?");

		ps.setString(1, hammerNo);
		
		ResultSet rs = ps.executeQuery();
		
		if (rs.next())
			hammer = getHammer(rs);
		
		return hammer;
	}
	
	ArrayList<Hammer> getHammers() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT h.*, ht.Name AS HammerTypeName, d.StateID, d.Name AS DistrictName, s.Name AS StateName, c.CompanyName AS ContractorName FROM Hammer h LEFT JOIN HammerType ht ON h.HammerTypeID = ht.HammerTypeID LEFT JOIN (District d, State s) ON (h.DistrictID = d.DistrictID AND d.StateID = s.StateID) LEFT JOIN Contractor c ON h.ContractorID = c.ContractorID ORDER BY h.HammerNo");

		return getHammers(ps.executeQuery());
	}

	ArrayList<Hammer> getHammers(State state) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT h.*, ht.Name AS HammerTypeName, d.StateID, d.Name AS DistrictName, s.Name AS StateName, NULL AS ContractorName FROM Hammer h, HammerType ht, District d, State s WHERE h.HammerTypeID = ht.HammerTypeID AND h.DistrictID = d.DistrictID AND d.StateID = s.StateID AND s.StateID = ? ORDER BY h.HammerNo");

		ps.setInt(1, state.getStateID());

		return getHammers(ps.executeQuery());
	}

	ArrayList<Hammer> getHammers(District district) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT h.*, ht.Name AS HammerTypeName, d.StateID, d.Name AS DistrictName, s.Name AS StateName, NULL AS ContractorName FROM Hammer h, HammerType ht, District d, State s WHERE h.HammerTypeID = ht.HammerTypeID AND h.DistrictID = ? AND h.DistrictID = d.DistrictID AND d.StateID = s.StateID ORDER BY h.HammerNo");

		ps.setInt(1, district.getDistrictID());

		return getHammers(ps.executeQuery());
	}

	ArrayList<Hammer> getHammers(Contractor contractor) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT h.*, NULL AS HammerTypeName, NULL AS StateID, NULL AS DistrictName, NULL AS StateName, c.CompanyName AS ContractorName FROM Hammer h, Contractor c WHERE h.ContractorID = ? AND h.ContractorID = c.ContractorID ORDER BY h.HammerNo");

		ps.setString(1, contractor.getContractorID());

		return getHammers(ps.executeQuery());
	}

	ArrayList<Hammer> getHammers(Tagging tagging) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT h.*, ht.Name AS HammerTypeName, d.StateID, d.Name AS DistrictName, s.Name AS StateName, c.CompanyName AS ContractorName FROM Hammer h LEFT JOIN HammerType ht ON h.HammerTypeID = ht.HammerTypeID LEFT JOIN (District d, State s) ON (h.DistrictID = d.DistrictID AND d.StateID = s.StateID) LEFT JOIN Contractor c ON h.ContractorID = c.ContractorID WHERE h.HammerNo IN (SELECT HammerNo FROM TaggingHammer WHERE TaggingID = ?) ORDER BY h.HammerNo");

		ps.setLong(1, tagging.getTaggingID());

		return getHammers(ps.executeQuery());
	}

	ArrayList<Hammer> getHammers(State state, HammerType hammerType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT h.*, ht.Name AS HammerTypeName, d.StateID, d.Name AS DistrictName, s.Name AS StateName, NULL AS ContractorName FROM Hammer h, HammerType ht, District d, State s WHERE h.HammerTypeID = ht.HammerTypeID AND h.DistrictID = d.DistrictID AND d.StateID = s.StateID AND s.StateID = ? AND h.HammerTypeID = ? ORDER BY h.HammerNo");

		ps.setInt(1, state.getStateID());
		ps.setInt(2, hammerType.getHammerTypeID());

		return getHammers(ps.executeQuery());
	}

	ArrayList<Hammer> getHammers(HammerType hammerType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT h.*, ht.Name AS HammerTypeName, d.StateID, d.Name AS DistrictName, s.Name AS StateName, NULL AS ContractorName FROM Hammer h, HammerType ht, District d, State s WHERE h.HammerTypeID = ht.HammerTypeID AND h.DistrictID = d.DistrictID AND d.StateID = s.StateID AND h.HammerTypeID = ? ORDER BY h.HammerNo");

		ps.setInt(1, hammerType.getHammerTypeID());

		return getHammers(ps.executeQuery());
	}
}