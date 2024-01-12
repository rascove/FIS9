package my.edu.utem.ftmk.fis9.hall.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.hall.model.Log;
import my.edu.utem.ftmk.fis9.maintenance.model.District;
import my.edu.utem.ftmk.fis9.tagging.model.Tagging;

/**
 * @author Nor Azman Mat Ariff
 */
class LogManager extends HallTableManager
{
	LogManager(HallFacade facade)
	{
		super(facade);
	}

	private int write(Log log, PreparedStatement ps) throws SQLException
	{
		ps.setInt(1, log.getLogNo());
		ps.setString(2, log.getLogSerialNo());
		ps.setBigDecimal(3, log.getLength());
		ps.setBigDecimal(4, log.getDiameter());
		ps.setBigDecimal(5, log.getHoleDiameter());
		ps.setLong(6, log.getTaggingRecordID());
		ps.setString(7, log.getStatus());
		ps.setLong(8, log.getLogID());

		return ps.executeUpdate();
	}

	int addLog(Log log, boolean ignoreDuplicate) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement((ignoreDuplicate ? "INSERT IGNORE" : "INSERT") + " INTO Log (LogNo, LogSerialNo, Length, Diameter, HoleDiameter, TaggingRecordID, Status, LogID) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)" + (ignoreDuplicate ? "" : " ON DUPLICATE KEY UPDATE Length = IF(Status = 'O', ?, Length), Diameter = IF(Status = 'O', ?, Diameter), HoleDiameter = IF(Status = 'O', ?, HoleDiameter), Status = IF(Status = 'O', ?, Status)")); 

		if (!ignoreDuplicate)
		{
			ps.setBigDecimal(9, log.getLength());
			ps.setBigDecimal(10, log.getDiameter());
			ps.setBigDecimal(11, log.getHoleDiameter());
			ps.setString(12, log.getStatus());
		}

		int status = write(log, ps);
		
		if (status == 0 && !ignoreDuplicate)
			status = 1;
		
		return status;
	}

	int addLogs(ArrayList<Log> logs) throws SQLException
	{
		int status = 0;
		PreparedStatement ps = facade.prepareStatement("INSERT INTO Log (LogNo, LogSerialNo, Length, Diameter, HoleDiameter, TaggingRecordID, Status, LogID) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE Length = ?, Diameter = ?, HoleDiameter = ?, Status = ?");

		for (Log log : logs)
		{
			ps.setBigDecimal(9, log.getLength());
			ps.setBigDecimal(10, log.getDiameter());
			ps.setBigDecimal(11, log.getHoleDiameter());
			ps.setString(12, log.getStatus());

			status += write(log, ps);
		}

		return status;
	}

	int updateLog(Log log) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE Log SET "
				+ "LogNo = ?, LogSerialNo = ?, Length = ?, Diameter = ?, HoleDiameter = ?, TaggingRecordID = ?, Status = ? "
				+ "WHERE LogID = ?");

		return write(log, ps);
	}

	int updateLogStatus(Log log) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE Log SET Status = ? WHERE LogID = ?");
		ps.setString(1, log.getStatus());
		ps.setLong(2, log.getLogID());

		return ps.executeUpdate();
	}

	int deleteLog(Log log) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE Log SET Length = 0.0, Diameter = 0, HoleDiameter = 0, Status = 'O' WHERE LogID = ?");
		ps.setLong(1, log.getLogID());

		return ps.executeUpdate();
	}

	private Log readLog(ResultSet rs) throws SQLException
	{
		Log log = new Log();

		log.setLogID(rs.getLong("LogID"));
		log.setLogNo(rs.getInt("LogNo"));
		log.setLogSerialNo(rs.getString("LogSerialNo"));
		log.setLength(rs.getBigDecimal("Length"));
		log.setDiameter(rs.getBigDecimal("Diameter"));
		log.setHoleDiameter(rs.getBigDecimal("HoleDiameter"));
		log.setTaggingRecordID(rs.getLong("TaggingRecordID"));
		log.setSpeciesID(rs.getInt("CorrectedSpeciesID"));
		log.setSpeciesCode(rs.getString("CorrectedSpeciesCode"));
		log.setSpeciesName(rs.getString("CorrectedSpeciesName"));
		log.setSummaryTaggingRecord(rs.getString("SerialNo")+"-( "+rs.getString("CorrectedSpeciesCode")+"-"+rs.getString("CorrectedSpeciesName")+" )");
		log.setSpeciesTypeID(rs.getInt("CorrectedSpeciesTypeID"));	
		log.setStatus(rs.getString("Status"));

		return log;
	}

	private ArrayList<Log> getLogs(ResultSet rs) throws SQLException
	{
		ArrayList<Log> logs = new ArrayList<>();

		while (rs.next())
		{
			Log log = readLog(rs);
			logs.add(log);
		}
		return logs;
	}

	Log getLog(long logID) throws SQLException
	{
		Log log = null;
		PreparedStatement ps = facade.prepareStatement("SELECT l.*, s.SpeciesID, s.Code AS SpeciesCode, s.Name AS SpeciesName, s.SpeciesTypeID, cs.SpeciesID AS CorrectedSpeciesID, cs.Code AS CorrectedSpeciesCode, cs.Name AS CorrectedSpeciesName, cs.SpeciesTypeID AS CorrectedSpeciesTypeID, tr.SerialNo " + 
				"FROM Log l LEFT JOIN TaggingRecord tr ON l.TaggingRecordID = tr.TaggingRecordID LEFT JOIN Species s ON tr.SpeciesID = s.SpeciesID LEFT JOIN Species cs ON tr.CorrectedSpeciesID = cs.SpeciesID " + 
				"WHERE l.LogID = ?");

		ps.setLong(1, logID);

		ResultSet rs = ps.executeQuery();

		if (rs.next())
		{
			log = readLog(rs);
		}

		return log;
	}

	ArrayList<Log> getLogs(String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT l.*, s.SpeciesID, s.Code AS SpeciesCode, s.Name AS SpeciesName, s.SpeciesTypeID, cs.SpeciesID AS CorrectedSpeciesID, cs.Code AS CorrectedSpeciesCode, cs.Name AS CorrectedSpeciesName, cs.SpeciesTypeID AS CorrectedSpeciesTypeID, tr.SerialNo " + 
				"FROM Log l LEFT JOIN TaggingRecord tr ON l.TaggingRecordID = tr.TaggingRecordID LEFT JOIN Species s ON tr.SpeciesID = s.SpeciesID LEFT JOIN Species cs ON tr.CorrectedSpeciesID = cs.SpeciesID " + 
				"WHERE l.Status = ? ORDER BY l.LogSerialNo");

		ps.setString(1, status);

		return getLogs(ps.executeQuery());
	}

	ArrayList<Log> getLogs(Tagging tagging) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT l.*, s.SpeciesID, s.Code AS SpeciesCode, s.Name AS SpeciesName, s.SpeciesTypeID, cs.SpeciesID AS CorrectedSpeciesID, cs.Code AS CorrectedSpeciesCode, cs.Name AS CorrectedSpeciesName, cs.SpeciesTypeID AS CorrectedSpeciesTypeID, tr.SerialNo " + 
				"FROM Log l LEFT JOIN TaggingRecord tr ON l.TaggingRecordID = tr.TaggingRecordID LEFT JOIN TaggingForm tf ON tr.TaggingFormID = tf.TaggingFormID LEFT JOIN Species s ON tr.SpeciesID = s.SpeciesID LEFT JOIN Species cs ON tr.CorrectedSpeciesID = cs.SpeciesID " + 
				"WHERE tf.TaggingID = ? ORDER BY l.LogSerialNo");

		ps.setLong(1, tagging.getTaggingID());

		return getLogs(ps.executeQuery());
	}

	ArrayList<Log> getLogs(Tagging tagging, String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT l.*, s.SpeciesID, s.Code AS SpeciesCode, s.Name AS SpeciesName, s.SpeciesTypeID, cs.SpeciesID AS CorrectedSpeciesID, cs.Code AS CorrectedSpeciesCode, cs.Name AS CorrectedSpeciesName, cs.SpeciesTypeID AS CorrectedSpeciesTypeID, tr.SerialNo " + 
				"FROM Log l LEFT JOIN TaggingRecord tr ON l.TaggingRecordID = tr.TaggingRecordID LEFT JOIN TaggingForm tf ON tr.TaggingFormID = tf.TaggingFormID LEFT JOIN Species s ON tr.SpeciesID = s.SpeciesID LEFT JOIN Species cs ON tr.CorrectedSpeciesID = cs.SpeciesID " + 
				"WHERE tf.TaggingID = ? AND l.Status = ? ORDER BY l.LogSerialNo");

		ps.setLong(1, tagging.getTaggingID());
		ps.setString(2, status);

		return getLogs(ps.executeQuery());
	}

	ArrayList<Log> getLogs(District district) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT l.*, s.SpeciesID, s.Code AS SpeciesCode, s.Name AS SpeciesName, s.SpeciesTypeID, cs.SpeciesID AS CorrectedSpeciesID, cs.Code AS CorrectedSpeciesCode, cs.Name AS CorrectedSpeciesName, cs.SpeciesTypeID AS CorrectedSpeciesTypeID, tr.SerialNo " + 
				"FROM Log l LEFT JOIN TaggingRecord tr ON l.TaggingRecordID = tr.TaggingRecordID LEFT JOIN Species s ON tr.SpeciesID = s.SpeciesID LEFT JOIN Species cs ON tr.CorrectedSpeciesID = cs.SpeciesID LEFT JOIN TaggingForm tf ON tr.TaggingFormID = tf.TaggingFormID LEFT JOIN Tagging t ON tf.TaggingID = t.TaggingID LEFT JOIN Survey s ON t.SurveyID = s.SurveyID LEFT JOIN Forest f ON s.ForestID = f.ForestID LEFT JOIN District d ON f.DistrictID = d.DistrictID " + 
				"ORDER BY l.LogSerialNo");

		ps.setInt(1, district.getDistrictID());

		return getLogs(ps.executeQuery());
	}
}