package my.edu.utem.ftmk.fis9.prefelling.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingSurveyCard;
import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingSurveyRecord;

/**
 * @author Satrya Fajri Pratama
 */
class PreFellingSurveyRecordManager extends PreFellingTableManager
{
	PreFellingSurveyRecordManager(PreFellingFacade facade)
	{
		super(facade);
	}

	private void write(PreFellingSurveyRecord preFellingSurveyRecord, PreparedStatement ps) throws SQLException
	{
		nullable(ps, 1, preFellingSurveyRecord.getDiameter());
		nullable(ps, 2, preFellingSurveyRecord.getLogQuantity());
		nullable(ps, 3, preFellingSurveyRecord.getLogQualityID());
		nullable(ps, 4, preFellingSurveyRecord.getFertilityID());
		nullable(ps, 5, preFellingSurveyRecord.getVineDiameter());
		nullable(ps, 6, preFellingSurveyRecord.getVineSpreadthID());
		nullable(ps, 7, preFellingSurveyRecord.getSaplingQuantity());
		nullable(ps, 8, preFellingSurveyRecord.getSeedlingQuantity());
		ps.setInt(9, preFellingSurveyRecord.getSpeciesID());
		ps.setInt(10, preFellingSurveyRecord.getPlotTypeID());
		ps.setLong(11, preFellingSurveyRecord.getPreFellingSurveyRecordID());
	}

	int addPreFellingSurveyRecord(PreFellingSurveyRecord preFellingSurveyRecord, boolean ignoreDuplicate) throws SQLException
	{
		String sql = (ignoreDuplicate ? "INSERT IGNORE" : "INSERT") + " INTO PreFellingSurveyRecord (Diameter, LogQuantity, LogQualityID, FertilityID, VineDiameter, VineSpreadthID, SaplingQuantity, SeedlingQuantity, SpeciesID, PlotTypeID, PreFellingSurveyRecordID, PreFellingSurveyCardID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" + (ignoreDuplicate ? "" : " ON DUPLICATE KEY UPDATE Diameter = ?, LogQuantity = ?, LogQualityID = ?, FertilityID = ?, VineDiameter = ?, VineSpreadthID = ?, SaplingQuantity = ?, SeedlingQuantity = ?, SpeciesID = ?, PlotTypeID = ?");
		PreparedStatement ps = facade.prepareStatement(sql);

		write(preFellingSurveyRecord, ps);
		ps.setLong(12, preFellingSurveyRecord.getPreFellingSurveyCardID());

		if (!ignoreDuplicate)
		{
			nullable(ps, 13, preFellingSurveyRecord.getDiameter());
			nullable(ps, 14, preFellingSurveyRecord.getLogQuantity());
			nullable(ps, 15, preFellingSurveyRecord.getLogQualityID());
			nullable(ps, 16, preFellingSurveyRecord.getFertilityID());
			nullable(ps, 17, preFellingSurveyRecord.getVineDiameter());
			nullable(ps, 18, preFellingSurveyRecord.getVineSpreadthID());
			nullable(ps, 19, preFellingSurveyRecord.getSaplingQuantity());
			nullable(ps, 20, preFellingSurveyRecord.getSeedlingQuantity());
			ps.setInt(21, preFellingSurveyRecord.getSpeciesID());
			ps.setInt(22, preFellingSurveyRecord.getPlotTypeID());
		}

		int status = ps.executeUpdate();
		
		if (status == 0 && !ignoreDuplicate)
			status = 1;
		
		return status;
	}

	int updatePreFellingSurveyRecord(PreFellingSurveyRecord preFellingSurveyRecord) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE PreFellingSurveyRecord SET Diameter = ?, LogQuantity = ?, LogQualityID = ?, FertilityID = ?, VineDiameter = ?, VineSpreadthID = ?, SaplingQuantity = ?, SeedlingQuantity = ?, SpeciesID = ?, PlotTypeID = ? WHERE PreFellingSurveyRecordID = ?");

		write(preFellingSurveyRecord, ps);

		return ps.executeUpdate();
	}

	int deletePreFellingSurveyRecord(PreFellingSurveyRecord preFellingSurveyRecord) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("DELETE FROM PreFellingSurveyRecord WHERE PreFellingSurveyRecordID = ?");

		ps.setLong(1, preFellingSurveyRecord.getPreFellingSurveyRecordID());

		return ps.executeUpdate();
	}

	private ArrayList<PreFellingSurveyRecord> getPreFellingSurveyRecords(ResultSet rs) throws SQLException
	{
		ArrayList<PreFellingSurveyRecord> preFellingSurveyRecords = new ArrayList<>();

		while (rs.next())
		{
			PreFellingSurveyRecord preFellingSurveyRecord = new PreFellingSurveyRecord();

			preFellingSurveyRecord.setPreFellingSurveyRecordID(rs.getLong("PreFellingSurveyRecordID"));
			preFellingSurveyRecord.setDiameter(rs.getDouble("Diameter"));
			preFellingSurveyRecord.setLogQuantity(rs.getInt("LogQuantity"));
			preFellingSurveyRecord.setLogQualityID(rs.getInt("LogQualityID"));
			preFellingSurveyRecord.setFertilityID(rs.getInt("FertilityID"));
			preFellingSurveyRecord.setVineDiameter(rs.getInt("VineDiameter"));
			preFellingSurveyRecord.setVineSpreadthID(rs.getInt("VineSpreadthID"));
			preFellingSurveyRecord.setSaplingQuantity(rs.getInt("SaplingQuantity"));
			preFellingSurveyRecord.setSeedlingQuantity(rs.getInt("SeedlingQuantity"));
			preFellingSurveyRecord.setPreFellingSurveyCardID(rs.getLong("PreFellingSurveyCardID"));
			preFellingSurveyRecord.setSpeciesID(rs.getInt("SpeciesID"));
			preFellingSurveyRecord.setSpeciesTypeID(rs.getInt("SpeciesTypeID"));
			preFellingSurveyRecord.setTimberGroupID(rs.getInt("TimberGroupID"));
			preFellingSurveyRecord.setPlotTypeID(rs.getInt("PlotTypeID"));
			preFellingSurveyRecord.setLogQuality(rs.getString("LogQuality"));
			preFellingSurveyRecord.setLogQualityName(rs.getString("LogQualityName"));
			preFellingSurveyRecord.setFertility(rs.getString("Fertility"));
			preFellingSurveyRecord.setFertilityName(rs.getString("FertilityName"));
			preFellingSurveyRecord.setVineSpreadth(rs.getString("VineSpreadth"));
			preFellingSurveyRecord.setVineSpreadthName(rs.getString("VineSpreadthName"));
			preFellingSurveyRecord.setSpeciesCode(rs.getString("SpeciesCode"));
			preFellingSurveyRecord.setSpeciesName(rs.getString("SpeciesName"));
			preFellingSurveyRecord.setPlotTypeName(rs.getString("PlotTypeName"));

			preFellingSurveyRecords.add(preFellingSurveyRecord);
		}

		return preFellingSurveyRecords;
	}

	ArrayList<PreFellingSurveyRecord> getPreFellingSurveyRecords(PreFellingSurveyCard preFellingSurveyCard) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT sr.*, s.SpeciesTypeID, s.TimberGroupID, s.Code AS SpeciesCode, s.Name AS SpeciesName, CONCAT(pt.Name, ' - ', pt.Description) AS PlotTypeName, pt.Description, lg.Code AS LogQuality, lg.Name AS LogQualityName, f.Code AS Fertility, f.Name AS FertilityName, vs.Code AS VineSpreadth, vs.Name AS VineSpreadthName FROM PreFellingSurveyRecord sr JOIN Species s JOIN PlotType pt LEFT JOIN LogQuality lg ON sr.LogQualityID = lg.LogQualityID LEFT JOIN Fertility f ON sr.FertilityID = f.FertilityID LEFT JOIN VineSpreadth vs ON sr.VineSpreadthID = vs.VineSpreadthID WHERE sr.PreFellingSurveyCardID = ? AND sr.SpeciesID = s.SpeciesID AND sr.PlotTypeID = pt.PlotTypeID ORDER BY sr.PlotTypeID, s.Code");

		ps.setLong(1, preFellingSurveyCard.getPreFellingSurveyCardID());

		return getPreFellingSurveyRecords(ps.executeQuery());
	}
}