package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.maintenance.model.Tender;

/**
 * @author Nor Azman Mat Ariff
 * @author Satrya Fajri Pratama
 */
class TenderManager extends MaintenanceTableManager
{
	TenderManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(Tender tender, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, tender.getContractorID());
		ps.setString(2, tender.getWorkType());
		ps.setDate(3, toSQLDate(tender.getAppointDate()));
		ps.setDate(4, toSQLDate(tender.getStartDate()));
		ps.setDate(5, toSQLDate(tender.getEndDate()));
		ps.setString(6, tender.getVoucher());
		ps.setString(7, tender.getStatus());
		ps.setInt(8, tender.getStateID());
		ps.setString(9, tender.getTenderNo());

		return ps.executeUpdate();		
	}

	int addTender(Tender tender) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT IGNORE INTO Tender (ContractorID, WorkType, AppointDate, StartDate, EndDate, Voucher, Status, StateID, TenderNo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

		return write(tender, ps);
	}

	int updateTender(Tender tender) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE Tender SET ContractorID = ?, WorkType = ?, AppointDate = ?, StartDate = ?, EndDate = ?, Voucher = ?, Status = ?, StateID = ? WHERE TenderNo = ?");

		return write(tender, ps);
	}

	private Tender read(ResultSet rs) throws SQLException
	{
		Tender tender = new Tender();

		tender.setTenderNo(rs.getString("TenderNo"));
		tender.setContractorID(rs.getString("ContractorID"));	
		tender.setWorkType(rs.getString("WorkType"));
		tender.setAppointDate(rs.getDate("AppointDate"));
		tender.setStartDate(rs.getDate("StartDate"));
		tender.setEndDate(rs.getDate("EndDate"));
		tender.setVoucher(rs.getString("Voucher"));
		tender.setStatus(rs.getString("Status"));
		tender.setStateID(rs.getInt("StateID"));
		tender.setContractorName(rs.getString("ContractorName"));

		return tender;
	}

	Tender getTender(String tenderNo) throws SQLException
	{
		Tender tender = null;
		PreparedStatement ps = facade.prepareStatement("SELECT t.*, c.CompanyName AS ContractorName FROM Tender t, Contractor c WHERE t.ContractorID = c.ContractorID AND t.TenderNo = ?");

		ps.setString(1, tenderNo);

		ResultSet rs = ps.executeQuery();

		if (rs.next())
			tender = read(rs);

		return tender;
	}

	Tender getTender(String tenderNo, String status) throws SQLException
	{
		Tender tender = null;
		PreparedStatement ps = facade.prepareStatement("SELECT t.*, c.CompanyName AS ContractorName FROM Tender t, Contractor c WHERE t.ContractorID = c.ContractorID AND t.TenderNo = ? and t.Status = ?");

		ps.setString(1, tenderNo);
		ps.setString(2, status);

		ResultSet rs = ps.executeQuery();

		if (rs.next())
			tender = read(rs);

		return tender;
	}

	ArrayList<Tender> getTenders(String status) throws SQLException
	{
		ArrayList<Tender> tenders = new ArrayList<>();
		PreparedStatement ps = facade.prepareStatement("SELECT t.*, c.CompanyName AS ContractorName FROM Tender t, Contractor c WHERE t.ContractorID = c.ContractorID AND t.Status = ? ORDER BY t.TenderNo");

		ps.setString(1, status);

		ResultSet rs = ps.executeQuery();

		while (rs.next())
			tenders.add(read(rs));

		return tenders;
	}

	ArrayList<Tender> getTenders(String status, State state) throws SQLException
	{
		ArrayList<Tender> tenders = new ArrayList<>();
		PreparedStatement ps = facade.prepareStatement("SELECT t.*, c.CompanyName AS ContractorName FROM Tender t, Contractor c WHERE t.ContractorID = c.ContractorID AND t.Status = ? AND t.StateID = ? ORDER BY t.TenderNo");

		ps.setString(1, status);
		ps.setInt(2, state.getStateID());

		ResultSet rs = ps.executeQuery();

		while (rs.next())
			tenders.add(read(rs));

		return tenders;
	}

	ArrayList<Tender> getTenders(String status, Staff staff) throws SQLException
	{
		ArrayList<Tender> tenders = new ArrayList<>();
		PreparedStatement ps = facade.prepareStatement("SELECT t.*, c.CompanyName AS ContractorName FROM Tender t, Contractor c WHERE t.ContractorID = c.ContractorID AND t.Status = ? AND c.ContractorID = (SELECT ContractorID FROM Staff WHERE StaffID = ?) ORDER BY t.TenderNo");

		ps.setString(1, status);
		ps.setString(2, staff.getStaffID());

		ResultSet rs = ps.executeQuery();

		while (rs.next())
			tenders.add(read(rs));

		return tenders;
	}
	ArrayList<Tender> getTenders(String workType, String status, State state) throws SQLException
	{
		ArrayList<Tender> tenders = new ArrayList<>();
		PreparedStatement ps = facade.prepareStatement("SELECT t.*, c.CompanyName AS ContractorName FROM Tender t, Contractor c WHERE t.WorkType = ? AND (t.WorkType = 'F' AND t.TenderNo NOT IN (SELECT TenderNo FROM PreFellingSurvey WHERE TenderNo = t.TenderNo) OR t.WorkType = 'T' AND t.TenderNo NOT IN (SELECT TenderNo FROM Tagging WHERE TenderNo = t.TenderNo) OR t.WorkType = 'P' AND t.TenderNo NOT IN (SELECT TenderNo FROM PostFellingSurvey WHERE TenderNo = t.TenderNo)) AND t.ContractorID = c.ContractorID AND t.Status = ? AND t.StateID = ? ORDER BY t.TenderNo");

		ps.setString(1, workType);
		ps.setString(2, status);
		ps.setInt(3, state.getStateID());

		ResultSet rs = ps.executeQuery();

		while (rs.next())
			tenders.add(read(rs));

		return tenders;
	}

	ArrayList<Tender> getTenders(String workType, String status, Staff staff) throws SQLException
	{
		ArrayList<Tender> tenders = new ArrayList<>();
		PreparedStatement ps = facade.prepareStatement("SELECT t.*, c.CompanyName AS ContractorName FROM Tender t, Contractor c WHERE t.WorkType = ? AND (t.WorkType = 'F' AND t.TenderNo NOT IN (SELECT TenderNo FROM PreFellingSurvey WHERE TenderNo = t.TenderNo) OR t.WorkType = 'T' AND t.TenderNo NOT IN (SELECT TenderNo FROM Tagging WHERE TenderNo = t.TenderNo) OR t.WorkType = 'P' AND t.TenderNo NOT IN (SELECT TenderNo FROM PostFellingSurvey WHERE TenderNo = t.TenderNo)) AND t.ContractorID = c.ContractorID AND t.Status = ? AND c.ContractorID = (SELECT ContractorID FROM Staff WHERE StaffID = ?) ORDER BY t.TenderNo");

		ps.setString(1, workType);
		ps.setString(2, status);
		ps.setString(3, staff.getStaffID());

		ResultSet rs = ps.executeQuery();

		while (rs.next())
			tenders.add(read(rs));

		return tenders;
	}
}