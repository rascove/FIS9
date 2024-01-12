package my.edu.utem.ftmk.fis9.revenue.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.revenue.model.Journal;
import my.edu.utem.ftmk.fis9.revenue.model.JournalRecord;

/**
 * @author Nor Azman Mat Ariff
 */
class JournalRecordManager extends RevenueTableManager
{
	JournalRecordManager(RevenueFacade facade)
	{
		super(facade);
	}

	private int write(JournalRecord journalRecord, PreparedStatement ps) throws SQLException
	{
		ps.setLong(1, journalRecord.getJournalRecordID());
		ps.setLong(2, journalRecord.getJournalID());
		ps.setInt(3, journalRecord.getDepartmentVotID());
		ps.setInt(4, journalRecord.getTrustFundID());
		ps.setString(5, journalRecord.getDescription());	
		ps.setBigDecimal(6, journalRecord.getTotal());

		return ps.executeUpdate();
	}

	int addJournalRecord(JournalRecord journalRecord) throws SQLException
	{
		PreparedStatement ps = null;
		ps = facade.prepareStatement("INSERT INTO JournalRecord (JournalRecordID, JournalID, DepartmentVotID, TrustFundID, Description, Total) VALUES (?, ?, ?, ?, ?, ?)");
		int status = write(journalRecord, ps);

		return status;
	}
	
	int updateJournalRecord(JournalRecord journalRecord) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE JournalRecord SET JournalID = ?, DepartmentVotID = ?, TrustFundID = ?, Description = ?, Total = ? WHERE JournalRecordID = ?");
		ps.setLong(1, journalRecord.getJournalRecordID());

		return ps.executeUpdate();
	}

	int deleteJournalRecord(JournalRecord journalRecord) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("DELETE FROM JournalRecord WHERE JournalRecordID = ?");
		ps.setLong(1, journalRecord.getJournalRecordID());

		return ps.executeUpdate();
	}

	private JournalRecord read(ResultSet rs) throws SQLException
	{
		JournalRecord journalRecord = new JournalRecord();

		journalRecord.setJournalRecordID(rs.getLong("JournalRecordID"));
		journalRecord.setJournalID(rs.getLong("JournalID"));
		journalRecord.setDepartmentVotID(rs.getInt("DepartmentVotID"));
		journalRecord.setDepartmentVotCode(rs.getString("DepartmentVotCode"));
		journalRecord.setDepartmentVotName(rs.getString("DepartmentVotName"));
		journalRecord.setBursaryVotID(rs.getInt("BursaryVotID"));
		journalRecord.setBursaryVotCode(rs.getString("BursaryVotCode"));
		journalRecord.setBursaryVotName(rs.getString("BursaryVotName"));		
		journalRecord.setTrustFundID(rs.getInt("TrustFundID"));
		journalRecord.setTrustFundDepartmentVotID(rs.getInt("trustFundDepartmentVotID"));
		journalRecord.setTrustFundDepartmentVotCode(rs.getString("TrustFundDepartmentVotCode"));
		journalRecord.setTrustFundDepartmentVotName(rs.getString("TrustFundDepartmentVotName"));
		journalRecord.setTrustFundBursaryVotID(rs.getInt("trustFundBursaryVotID"));
		journalRecord.setTrustFundBursaryVotCode(rs.getString("TrustFundBursaryVotCode"));
		journalRecord.setTrustFundBursaryVotName(rs.getString("TrustFundBursaryVotName"));
		journalRecord.setDescription(rs.getString("Description"));
		journalRecord.setTotal(rs.getBigDecimal("Total"));
		
		return journalRecord;
	}

	private ArrayList<JournalRecord> getJournalRecords(ResultSet rs) throws SQLException
	{
		ArrayList<JournalRecord> journalRecords = new ArrayList<>();

		while (rs.next())
		{
			JournalRecord journalRecord = read(rs);
			journalRecords.add(journalRecord);
		}
		return journalRecords;
	}

	JournalRecord getJournalRecord(int journalRecordID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT jr.*, dv.Code AS DepartmentVotCode, dv.Name AS DepartmentVotName, bv.BursaryVotID, bv.Code AS BursaryVotCode, bv.Name AS BursaryVotName, tfdv.DepartmentVotID AS TrustFundDepartmentVotID, tfdv.Code AS TrustFundDepartmentVotCode, tfdv.Name AS TrustFundDepartmentVotName, tfbv.BursaryVotID AS TrustFundBursaryVotID, tfbv.Code AS TrustFundBursaryVotCode, tfbv.Name AS TrustFundBursaryVotName " + 
				"FROM JournalRecord jr, Journal j, DepartmentVot dv, BursaryVot bv, TrustFund tf, DepartmentVot tfdv, BursaryVot tfbv " + 
				"WHERE jr.JournalID = j.JournalID AND jr.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND jr.TrustFundID = tf.TrustFundID AND tf.DepartmentVotID = tfdv.DepartmentVotID AND tfdv.BursaryVotID = tfbv.BursaryVotID AND jr.JournalRecordID = ?");

		ps.setLong(1, journalRecordID);

		ResultSet rs = ps.executeQuery();

		return read(rs);
	}
	
	ArrayList<JournalRecord> getJournalRecords(Journal journal) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT jr.*, dv.Code AS DepartmentVotCode, dv.Name AS DepartmentVotName, bv.BursaryVotID, bv.Code AS BursaryVotCode, bv.Name AS BursaryVotName, tfdv.DepartmentVotID AS TrustFundDepartmentVotID, tfdv.Code AS TrustFundDepartmentVotCode, tfdv.Name AS TrustFundDepartmentVotName, tfbv.BursaryVotID AS TrustFundBursaryVotID, tfbv.Code AS TrustFundBursaryVotCode, tfbv.Name AS TrustFundBursaryVotName " + 
				"FROM JournalRecord jr, Journal j, DepartmentVot dv, BursaryVot bv, TrustFund tf, DepartmentVot tfdv, BursaryVot tfbv " + 
				"WHERE jr.JournalID = j.JournalID AND jr.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND jr.TrustFundID = tf.TrustFundID AND tf.DepartmentVotID = tfdv.DepartmentVotID AND tfdv.BursaryVotID = tfbv.BursaryVotID AND j.JournalID = ?");
		ps.setLong(1, journal.getJournalID());

		return getJournalRecords(ps.executeQuery());
	}
}