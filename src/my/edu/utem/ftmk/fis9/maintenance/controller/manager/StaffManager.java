package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.Designation;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingSurvey;
import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingSurvey;
import my.edu.utem.ftmk.fis9.tagging.model.Tagging;

/**
 * @author Satrya Fajri Pratama
 */
class StaffManager extends MaintenanceTableManager
{
	StaffManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	int addStaff(Staff staff) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT IGNORE INTO Staff VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

		ps.setString(1, staff.getStaffID());
		ps.setString(2, staff.getName().toUpperCase());
		ps.setString(3, staff.getPassword());
		ps.setInt(4, staff.getDesignationID());
		ps.setInt(5, toInt(staff.getStatus()));
		ps.setInt(6, toInt(staff.isAdministrative()));
		nullable(ps, 7, staff.getStateID());
		ps.setString(8, staff.getContractorID());

		return ps.executeUpdate();
	}

	int updateStaff(Staff staff, boolean passwordChanged) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE Staff SET Name = ?, DesignationID = ?, Status = ?, Administrative = ?, StateID = ?, ContractorID = ?" + (passwordChanged ? ", Password = ? " : "") + " WHERE StaffID = ?");

		ps.setString(1, staff.getName().toUpperCase());
		nullable(ps, 2, staff.getDesignationID());
		ps.setInt(3, toInt(staff.getStatus()));
		ps.setInt(4, toInt(staff.isAdministrative()));
		nullable(ps, 5, staff.getStateID());
		ps.setString(6, staff.getContractorID());

		if (passwordChanged)
		{
			ps.setString(7, staff.getPassword());
			ps.setString(8, staff.getStaffID());
		}
		else
			ps.setString(7, staff.getStaffID());

		return ps.executeUpdate();
	}

	private Staff read(ResultSet rs) throws SQLException
	{
		Designation designation = new Designation();
		Staff staff = new Staff();

		designation.setDesignationID(rs.getInt("DesignationID"));

		staff.setStaffID(rs.getString("StaffID"));
		staff.setName(rs.getString("Name"));
		staff.setPassword(rs.getString("Password"));
		staff.setDesignationID(designation.getDesignationID());
		staff.setStatus(toBoolean(rs.getInt("Status")));
		staff.setAdministrative(toBoolean(rs.getInt("Administrative")));
		staff.setStateID(rs.getInt("StateID"));
		staff.setContractorID(rs.getString("ContractorID"));
		staff.setDesignationName(rs.getString("DesignationName"));
		staff.setStateName(rs.getString("StateName"));
		staff.setContractorName(rs.getString("ContractorName"));
		staff.setForms(facade.getForms(designation));

		return staff;
	}

	Staff getStaff(String staffID, String password) throws SQLException
	{
		Staff staff = null;
		PreparedStatement ps = facade.prepareStatement("SELECT s.*, d.Name AS DesignationName, t.Name AS StateName, c.CompanyName AS ContractorName FROM Staff s LEFT JOIN Designation d ON s.DesignationID = d.DesignationID LEFT JOIN State t ON s.StateID = t.StateID LEFT JOIN Contractor c ON s.ContractorID = c.ContractorID WHERE s.StaffID = ?" + (password == null ? "" : " AND Password = ?"));

		ps.setString(1, staffID);

		if (password != null)
			ps.setString(2, password);

		ResultSet rs = ps.executeQuery();

		if (rs.next())
			staff = read(rs);

		return staff;
	}

	ArrayList<Staff> getStaffs() throws SQLException
	{
		ArrayList<Staff> staffs = new ArrayList<>();
		PreparedStatement ps = facade.prepareStatement("SELECT s.*, d.Name AS DesignationName, t.Name AS StateName, c.CompanyName AS ContractorName FROM Staff s LEFT JOIN Designation d ON s.DesignationID = d.DesignationID LEFT JOIN State t ON s.StateID = t.StateID LEFT JOIN Contractor c ON s.ContractorID = c.ContractorID ORDER BY s.StaffID");
		ResultSet rs = ps.executeQuery();

		while (rs.next())
			staffs.add(read(rs));

		return staffs;
	}

	ArrayList<Staff> getStaffs(Designation designation) throws SQLException
	{
		ArrayList<Staff> staffs = new ArrayList<>();
		PreparedStatement ps = facade.prepareStatement("SELECT s.*, d.Name AS DesignationName, t.Name AS StateName, NULL AS ContractorName FROM Staff s, Designation d, State t WHERE s.DesignationID = ? AND s.DesignationID = d.DesignationID AND s.StateID = t.StateID ORDER BY s.StaffID");

		ps.setInt(1, designation.getDesignationID());

		ResultSet rs = ps.executeQuery();

		while (rs.next())
			staffs.add(read(rs));

		return staffs;
	}

	ArrayList<Staff> getStaffs(State state) throws SQLException
	{
		ArrayList<Staff> staffs = new ArrayList<>();
		PreparedStatement ps = facade.prepareStatement("SELECT s.*, d.Name AS DesignationName, t.Name AS StateName, NULL AS ContractorName FROM Staff s, Designation d, State t WHERE s.StateID = ? AND s.DesignationID = d.DesignationID AND s.StateID = t.StateID ORDER BY s.StaffID");

		ps.setInt(1, state.getStateID());

		ResultSet rs = ps.executeQuery();

		while (rs.next())
			staffs.add(read(rs));

		return staffs;
	}

	ArrayList<Staff> getStaffs(State state, String field, String id) throws SQLException
	{
		ArrayList<Staff> staffs = new ArrayList<>();
		PreparedStatement ps = facade.prepareStatement("SELECT s.*, d.Name AS DesignationName, t.Name AS StateName, NULL AS ContractorName FROM Staff s, Designation d, State t WHERE s.StateID = ? AND s.DesignationID = d.DesignationID AND s." + field + " = ? AND s.StateID = t.StateID ORDER BY s.StaffID");

		ps.setInt(1, state.getStateID());
		ps.setString(2, id);

		ResultSet rs = ps.executeQuery();

		while (rs.next())
			staffs.add(read(rs));

		return staffs;
	}

	ArrayList<Staff> getStaffs(PreFellingSurvey preFellingSurvey) throws SQLException
	{
		ArrayList<Staff> staffs = new ArrayList<>();
		PreparedStatement ps = facade.prepareStatement("SELECT s.*, d.Name AS DesignationName, t.Name AS StateName, c.CompanyName AS ContractorName FROM Staff s LEFT JOIN Designation d ON s.DesignationID = d.DesignationID LEFT JOIN State t ON s.StateID = t.StateID LEFT JOIN Contractor c ON s.ContractorID = c.ContractorID WHERE s.StaffID IN (SELECT RecorderID FROM PreFellingSurveyTeam WHERE PreFellingSurveyID = ?) ORDER BY s.StaffID");

		ps.setLong(1, preFellingSurvey.getPreFellingSurveyID());

		ResultSet rs = ps.executeQuery();

		while (rs.next())
			staffs.add(read(rs));

		return staffs;
	}

	ArrayList<Staff> getStaffs(Tagging tagging) throws SQLException
	{
		ArrayList<Staff> staffs = new ArrayList<>();
		PreparedStatement ps = facade.prepareStatement("SELECT s.*, d.Name AS DesignationName, t.Name AS StateName, c.CompanyName AS ContractorName FROM Staff s LEFT JOIN Designation d ON s.DesignationID = d.DesignationID LEFT JOIN State t ON s.StateID = t.StateID LEFT JOIN Contractor c ON s.ContractorID = c.ContractorID WHERE s.StaffID IN (SELECT RecorderID FROM TaggingTeam WHERE TaggingID = ?) ORDER BY s.StaffID");

		ps.setLong(1, tagging.getTaggingID());

		ResultSet rs = ps.executeQuery();

		while (rs.next())
			staffs.add(read(rs));

		return staffs;
	}

	ArrayList<Staff> getStaffs(PostFellingSurvey postFellingSurvey, boolean survey) throws SQLException
	{
		ArrayList<Staff> staffs = new ArrayList<>();
		PreparedStatement ps = facade.prepareStatement("SELECT s.*, d.Name AS DesignationName, t.Name AS StateName, c.CompanyName AS ContractorName FROM Staff s LEFT JOIN Designation d ON s.DesignationID = d.DesignationID LEFT JOIN State t ON s.StateID = t.StateID LEFT JOIN Contractor c ON s.ContractorID = c.ContractorID WHERE s.StaffID IN (SELECT RecorderID FROM PostFelling" + (survey ? "Survey" : "Inspection") + "Team WHERE PostFellingSurveyID = ?) ORDER BY s.StaffID");

		ps.setLong(1, postFellingSurvey.getPostFellingSurveyID());

		ResultSet rs = ps.executeQuery();

		while (rs.next())
			staffs.add(read(rs));

		return staffs;
	}
}