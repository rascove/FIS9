package my.edu.utem.ftmk.fis9.revenue.controller.manager;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.hall.controller.manager.HallFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.District;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.revenue.model.Journal;

/**
 * @author Nor Azman Mat Ariff
 */
class JournalManager extends RevenueTableManager
{
	JournalManager(RevenueFacade facade)
	{
		super(facade);
	}

	HallFacade hFacade = new HallFacade();

	private int write(Journal journal, PreparedStatement ps) throws SQLException
	{
		ps.setLong(1, journal.getJournalID());
		ps.setString(2,
				journal.getJournalNo().replaceAll("\\s", "").toUpperCase());
		ps.setString(3, journal.getName());
		ps.setString(4, journal.getRemarks());
		ps.setDate(5, toSQLDate(journal.getDate()));
		ps.setInt(6, journal.getCategory());
		nullable(ps, 7, journal.getLicenseID());
		nullable(ps, 8, journal.getPermitID());
		ps.setString(9, journal.getRecorderID());
		ps.setTimestamp(10, journal.getRecordTime());
		ps.setBigDecimal(11, journal.getTotal());
		ps.setString(12, journal.getStatus());

		return ps.executeUpdate();
	}

	int addJournal(Journal journal) throws SQLException
	{
		int status = 0;
		PreparedStatement ps = null;
		if (checkExistingJournalNo(journal) == false)
		{
			ps = facade.prepareStatement(
					"INSERT INTO Journal (JournalID, JournalNo, Name, Remarks, Date, Category, LicenseID, PermitID, RecorderID, RecordTime, Total, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			status = write(journal, ps);
		}
		return status;
	}

	private boolean checkExistingJournalNo(Journal journal) throws SQLException
	{
		boolean exist = false;
		PreparedStatement ps = facade.prepareStatement(
				"SELECT * FROM Journal WHERE JournalID != ? AND JournalNo = ?");
		ps.setLong(1, journal.getJournalID());
		ps.setString(2, journal.getJournalNo());

		ResultSet rs = ps.executeQuery();

		if (rs.next())
		{
			exist = true;
		}

		return exist;
	}

	int deleteJournal(Journal journal) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"UPDATE Journal SET Status = 'I' WHERE JournalID = ?");
		ps.setLong(1, journal.getJournalID());

		return ps.executeUpdate();
	}

	int updateStatusJournal(Journal journal) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"UPDATE Journal SET Status = ? WHERE JournalID = ?");
		ps.setString(1, journal.getStatus());
		ps.setLong(2, journal.getJournalID());

		return ps.executeUpdate();
	}

	private Journal read(ResultSet rs) throws SQLException
	{
		Journal journal = new Journal();

		journal.setJournalID(rs.getLong("JournalID"));
		journal.setJournalNo(rs.getString("JournalNo"));
		journal.setName(rs.getString("Name"));
		journal.setRemarks(rs.getString("Remarks"));
		journal.setDate(rs.getDate("Date"));
		journal.setCategory(rs.getInt("Category"));
		journal.setLicenseID(rs.getLong("LicenseID"));
		journal.setPermitID(rs.getLong("PermitID"));
		journal.setRecorderID(rs.getString("RecorderID"));
		journal.setRecorderName(rs.getString("RecorderName"));
		journal.setRecordTime(rs.getTimestamp("RecordTime"));
		journal.setTotal(rs.getBigDecimal("Total"));
		journal.setStatus(rs.getString("Status"));

		journal.setJournalRecords(facade.getJournalRecords(journal));
		
		if (journal.getCategory() == 0)
		{
			readLicense(journal);
			journal.setTransferPasses(hFacade.getTransferPasses(journal));
		}
		else
		{
			readPermit(journal);
		}

		return journal;
	}

	private Journal readLicense(Journal journal) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT l.licenseNo, lc.CompanyName AS LicenseeCompanyName, l.WoodWorkFund, l.LicenseFund, lt.LicenseTypeID "
						+ "FROM License l, LoggingContractor lc, LicenseType lt "
						+ "WHERE l.LicenseeID = lc.LoggingContractorID AND l.LicenseTypeID = lt.LicenseTypeID AND l.LicenseID = ?");

		ps.setLong(1, journal.getLicenseID());

		ResultSet rs = ps.executeQuery();

		while (rs.next())
		{
			journal.setLicenseNo(rs.getString("licenseNo"));
			journal.setLicenseeCompanyName(rs.getString("LicenseeCompanyName"));
			journal.setWoodWorkFund(rs.getBigDecimal("WoodWorkFund"));
			journal.setLicenseFund(rs.getBigDecimal("LicenseFund"));
			journal.setLicenseTypeID(rs.getInt("licenseTypeID"));
		}

		return journal;
	}

	private Journal readPermit(Journal journal) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT p.PermitNo, lc.CompanyName AS LicenseeCompanyName, p.PermitFund, pt.PermitTypeID "
						+ "FROM Permit p, License l, LoggingContractor lc, PermitType pt "
						+ "WHERE p.LicenseID = l.LicenseID AND l.LicenseeID = lc.LoggingContractorID AND p.PermitTypeID = pt.PermitTypeID AND p.PermitID = ?");

		ps.setLong(1, journal.getPermitID());

		ResultSet rs = ps.executeQuery();

		while (rs.next())
		{
			journal.setPermitNo(rs.getString("PermitNo"));
			journal.setLicenseeCompanyName(rs.getString("LicenseeCompanyName"));
			journal.setPermitFund(rs.getBigDecimal("PermitFund"));
			journal.setPermitTypeID(rs.getInt("PermitTypeID"));
		}

		return journal;
	}

	private ArrayList<Journal> getJournals(ResultSet rs) throws SQLException
	{
		ArrayList<Journal> journals = new ArrayList<>();

		while (rs.next())
		{
			Journal journal = read(rs);
			journals.add(journal);
		}
		return journals;
	}

	Journal getJournal(long journalID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT j.*, l.LicenseNo, lc.companyName, lc.RegistrationSerialNo AS LicenseeNo, lc.Address, lc.TelNo, d.DistrictID, d.Name AS DistrictName, stt.StateID, stt.Name AS StateName, s.Name AS RecorderName "
						+ "FROM Journal j, License l, LoggingContractor lc, Staff s, Forest f, District d, State stt "
						+ "WHERE j.LicenseID = l.LicenseID AND l.LicenseeID = lc.LoggingContractorID AND j.RecorderID = s.StaffID AND l.ForestID = f.ForestID AND f.DistrictID = d.DistrictID AND d.StateID = stt.StateID  "
						+ "AND j.JournalID = ?");

		ps.setLong(1, journalID);

		ResultSet rs = ps.executeQuery();

		return read(rs);
	}

	ArrayList<Journal> getJournals(String status) throws SQLException
	{
		PreparedStatement ps = facade
				.prepareStatement("SELECT j.*, s.Name AS RecorderName "
						+ "FROM Journal j, Staff s "
						+ "WHERE j.RecorderID = s.StaffID AND j.Status = ?");
		ps.setString(1, status);

		return getJournals(ps.executeQuery());
	}

	ArrayList<Journal> getJournals(Staff staff) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT j.*, s.Name AS RecorderName "
						+ "FROM Journal j, Staff s "
						+ "WHERE j.RecorderID = s.StaffID "
						+ "AND j.RecorderID = ?");
		ps.setString(1, staff.getStaffID());

		return getJournals(ps.executeQuery());
	}

	ArrayList<Journal> getJournals(District district) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT j.*, l.LicenseNo, lc.companyName, lc.RegistrationSerialNo AS LicenseeNo, lc.Address, lc.TelNo, d.DistrictID, d.Name AS DistrictName, stt.StateID, stt.Name AS StateName, s.Name AS RecorderName "
						+ "FROM Journal j, License l, LoggingContractor lc, Staff s, Forest f, District d, State stt "
						+ "WHERE j.LicenseID = l.LicenseID AND l.LicenseeID = lc.LoggingContractorID AND j.RecorderID = s.StaffID AND l.ForestID = f.ForestID AND f.DistrictID = d.DistrictID AND d.StateID = stt.StateID "
						+ "AND d.DistrictID = ?");
		ps.setInt(1, district.getDistrictID());

		return getJournals(ps.executeQuery());
	}

	ArrayList<Journal> getJournals(State state) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT j.*, s.Name AS RecorderName "
						+ "FROM Journal j, Staff s, State stt "
						+ "WHERE j.RecorderID = s.StaffID AND s.StateID = stt.StateID "
						+ "AND stt.StateID = ?");
		
		ps.setInt(1, state.getStateID());

		return getJournals(ps.executeQuery());
	}

	String[] getJournalHeader(long journalID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT YEAR(j.Date) AS Tahun, \"160\" AS JenisUrusniaga, j.JournalNo, j.Date AS Tarikh, \"0080\" AS Jab, \"JABATAN PERHUTANAN NEGERI\" AS JabDetail, \"80000100\" AS PTJ, \"IBU PEJABAT JABATAN PERHUTANAN NEGERI\" AS PTJDetail, j.Remarks AS PerihalJurnal, s.Name AS Penyedia, d.Name AS Jawatan "
						+ "FROM journal j, Staff s, Designation d "
						+ "WHERE j.RecorderID = s.StaffID AND s.DesignationID = d.DesignationID AND j.JournalID = ?");

		ps.setLong(1, journalID);

		ResultSet rs = ps.executeQuery();

		String[] journal = new String[11];

		while (rs.next())
		{
			journal[0] = rs.getString("Tahun");
			journal[1] = rs.getString("JenisUrusniaga");
			journal[2] = rs.getString("JournalNo");
			journal[3] = new SimpleDateFormat("dd/MM/yyyy")
					.format(rs.getDate("Tarikh"));
			journal[4] = rs.getString("Jab");
			journal[5] = rs.getString("JabDetail");
			journal[6] = rs.getString("PTJ");
			journal[7] = rs.getString("PTJDetail");
			journal[8] = rs.getString("PerihalJurnal");
			journal[9] = rs.getString("Penyedia");
			journal[10] = rs.getString("Jawatan");
		}

		return journal;
	}

	ArrayList<String[]> getJournalRecords(long journalID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT \"0800\" AS Jab, \"80000100\" AS PTJ, bvtf.Code AS KodAmanah, bv.Code AS KodObjek, jr.Total AS AmountDebit, jr.Total AS AmountKredit, \"0800\" AS JabBayar, \"80000100\" AS PTJBayar "
						+ "FROM Journal j, JournalRecord jr, TrustFund tf, DepartmentVot dvtf, BursaryVot bvtf, DepartmentVot dv, BursaryVot bv "
						+ "WHERE jr.JournalID = j.JournalID AND jr.TrustFundID = tf.TrustFundID AND tf.DepartmentVotID = dvtf.DepartmentVotID AND dvtf.BursaryVotID = bvtf.BursaryVotID AND jr.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND j.JournalID = ?");

		ps.setLong(1, journalID);

		ResultSet rs = ps.executeQuery();

		ArrayList<String[]> journalRecords = new ArrayList<String[]>();

		while (rs.next())
		{
			String[] journalRecord = new String[8];
			journalRecord[0] = rs.getString("Jab");
			journalRecord[1] = rs.getString("PTJ");
			journalRecord[2] = rs.getString("KodAmanah");
			journalRecord[3] = rs.getString("KodObjek");
			journalRecord[4] = rs.getBigDecimal("AmountDebit")
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			journalRecord[5] = rs.getBigDecimal("AmountKredit")
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			journalRecord[6] = rs.getString("JabBayar");
			journalRecord[7] = rs.getString("PTJBayar");
			journalRecords.add(journalRecord);
		}

		return journalRecords;
	}
}