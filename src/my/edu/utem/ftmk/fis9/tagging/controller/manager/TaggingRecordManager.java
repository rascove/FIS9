package my.edu.utem.ftmk.fis9.tagging.controller.manager;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.tagging.model.Tagging;
import my.edu.utem.ftmk.fis9.tagging.model.TaggingForm;
import my.edu.utem.ftmk.fis9.tagging.model.TaggingRecord;

/**
 * @author Satrya Fajri Pratama
 */
class TaggingRecordManager extends TaggingTableManager
{
	TaggingRecordManager(TaggingFacade facade)
	{
		super(facade);
	}

	private void write(TaggingRecord taggingRecord, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, standardize(taggingRecord.getPlotNo()));
		ps.setString(2, standardize(taggingRecord.getSerialNo()));
		ps.setInt(3, taggingRecord.getSpeciesID());
		nullable(ps, 4, taggingRecord.getCorrectedSpeciesID());
		nullable(ps, 5, taggingRecord.getTreeTypeID());
		ps.setDouble(6, taggingRecord.getDiameter());
		nullable(ps, 7, taggingRecord.getEstimation());
		nullable(ps, 8, taggingRecord.getCorrectedEstimation());
		nullable(ps, 9, taggingRecord.getLogQualityID());
		ps.setString(10, taggingRecord.getNote());
		nullable(ps, 11, taggingRecord.getLatitude());
		nullable(ps, 12, taggingRecord.getLongitude());
		ps.setLong(13, taggingRecord.getTaggingRecordID());
	}

	int addTaggingRecord(TaggingRecord taggingRecord, boolean ignoreDuplicate) throws SQLException
	{
		String sql = (ignoreDuplicate ? "INSERT IGNORE" : "INSERT") + " INTO TaggingRecord (PlotNo, SerialNo, SpeciesID, CorrectedSpeciesID, TreeTypeID, Diameter, Estimation, CorrectedEstimation, LogQualityID, Note, Latitude, Longitude, TaggingRecordID, TaggingFormID, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'O')" + (ignoreDuplicate ? "" : " ON DUPLICATE KEY UPDATE PlotNo = ?, SerialNo = ?, SpeciesID = ?, CorrectedSpeciesID = ?, TreeTypeID = ?, Diameter = ?, Estimation = ?, CorrectedEstimation = ?, LogQualityID = ?, Note = ?, Latitude = ?, Longitude = ?");
		PreparedStatement ps = facade.prepareStatement(sql);

		write(taggingRecord, ps);
		ps.setLong(14, taggingRecord.getTaggingFormID());

		if (!ignoreDuplicate)
		{
			ps.setString(15, standardize(taggingRecord.getPlotNo()));
			ps.setString(16, standardize(taggingRecord.getSerialNo()));
			ps.setInt(17, taggingRecord.getSpeciesID());
			nullable(ps, 18, taggingRecord.getCorrectedSpeciesID());
			nullable(ps, 19, taggingRecord.getTreeTypeID());
			ps.setDouble(20, taggingRecord.getDiameter());
			nullable(ps, 21, taggingRecord.getEstimation());
			nullable(ps, 22, taggingRecord.getCorrectedEstimation());
			nullable(ps, 23, taggingRecord.getLogQualityID());
			ps.setString(24, taggingRecord.getNote());
			nullable(ps, 25, taggingRecord.getLatitude());
			nullable(ps, 26, taggingRecord.getLongitude());
		}

		int status = ps.executeUpdate();

		if (status == 0 && !ignoreDuplicate)
			status = 1;

		return status;
	}

	int updateTaggingRecord(TaggingRecord taggingRecord) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE TaggingRecord SET PlotNo = ?, SerialNo = ?, SpeciesID = ?, CorrectedSpeciesID = ?, TreeTypeID = ?, Diameter = ?, Estimation = ?, CorrectedEstimation = ?, LogQualityID = ?, Note = ?, Latitude = ?, Longitude = ? WHERE TaggingRecordID = ?");

		write(taggingRecord, ps);

		return ps.executeUpdate();
	}

	int updateTaggingRecordStatus(TaggingRecord taggingRecord) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE TaggingRecord SET Status = ? WHERE TaggingRecordID = ?");
		ps.setString(1, taggingRecord.getStatus());
		ps.setLong(2, taggingRecord.getTaggingRecordID());

		return ps.executeUpdate();
	}

	int deleteTaggingRecord(TaggingRecord taggingRecord) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("DELETE FROM TaggingRecord WHERE TaggingRecordID = ?");

		ps.setLong(1, taggingRecord.getTaggingRecordID());

		return ps.executeUpdate();
	}

	private TaggingRecord read(ResultSet rs) throws SQLException
	{
		TaggingRecord taggingRecord = new TaggingRecord();

		taggingRecord.setTaggingRecordID(rs.getLong("TaggingRecordID"));
		taggingRecord.setPlotNo(rs.getString("PlotNo"));
		taggingRecord.setSerialNo(rs.getString("SerialNo"));
		taggingRecord.setSpeciesID(rs.getInt("SpeciesID"));
		taggingRecord.setCorrectedSpeciesID(rs.getInt("CorrectedSpeciesID"));
		taggingRecord.setCorrectedSpeciesCode(rs.getString("CorrectedSpeciesCode"));
		taggingRecord.setCorrectedSpeciesName(rs.getString("CorrectedSpeciesName"));
		taggingRecord.setTreeTypeID(rs.getInt("TreeTypeID"));
		taggingRecord.setDiameter(rs.getDouble("Diameter"));
		taggingRecord.setEstimation(rs.getInt("Estimation"));
		taggingRecord.setCorrectedEstimation(rs.getInt("CorrectedEstimation"));
		taggingRecord.setLogQualityID(rs.getInt("LogQualityID"));
		taggingRecord.setNote(rs.getString("Note"));
		taggingRecord.setLatitude(rs.getDouble("Latitude"));
		taggingRecord.setLongitude(rs.getDouble("Longitude"));
		taggingRecord.setTaggingFormID(rs.getLong("TaggingFormID"));
		taggingRecord.setSpeciesTypeID(rs.getInt("SpeciesTypeID"));
		taggingRecord.setSpeciesCode(rs.getString("SpeciesCode"));
		taggingRecord.setSpeciesName(rs.getString("SpeciesName"));
		taggingRecord.setTreeTypeName(rs.getString("TreeTypeName"));
		taggingRecord.setLogQuality(rs.getString("LogQuality"));
		taggingRecord.setLogQualityName(rs.getString("LogQualityName"));
		taggingRecord.setStatus(rs.getString("Status"));

		return taggingRecord;
	}

	private ArrayList<TaggingRecord> getTaggingRecords(ResultSet rs) throws SQLException
	{
		ArrayList<TaggingRecord> taggingRecords = new ArrayList<>();

		while (rs.next())
			taggingRecords.add(read(rs));

		return taggingRecords;
	}

	TaggingRecord getTaggingRecord(String serialNo) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT tr.*, s.SpeciesTypeID, s.Code AS SpeciesCode, s.Name AS SpeciesName, tt.Name AS TreeTypeName, lg.Code AS LogQuality, lg.Name AS LogQualityName FROM TaggingRecord tr JOIN Species s LEFT JOIN TreeType tt ON tr.TreeTypeID = tt.TreeTypeID LEFT JOIN LogQuality lg ON tr.LogQualityID = lg.LogQualityID WHERE tr.SerialNo = ? AND tr.SpeciesID = s.SpeciesID ORDER BY tr.PlotNo");

		ps.setString(1, standardize(serialNo));

		TaggingRecord taggingRecord = null;
		ResultSet rs = ps.executeQuery();

		if (rs.next())
			taggingRecord = read(rs);

		return taggingRecord;
	}

	ArrayList<TaggingRecord> getTaggingRecords(Tagging tagging) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT tr.*, s.SpeciesTypeID, s.Code AS SpeciesCode, s.Name AS SpeciesName, cs.Code AS CorrectedSpeciesCode, cs.Name AS CorrectedSpeciesName, tt.Name AS TreeTypeName, lg.Code AS LogQuality, lg.Name AS LogQualityName FROM TaggingRecord tr JOIN Species s LEFT JOIN Species cs ON tr.CorrectedSpeciesID = cs.SpeciesID LEFT JOIN TreeType tt ON tr.TreeTypeID = tt.TreeTypeID LEFT JOIN LogQuality lg ON tr.LogQualityID = lg.LogQualityID WHERE tr.TaggingFormID IN (SELECT TaggingFormID FROM TaggingForm WHERE TaggingID = ?) AND tr.SpeciesID = s.SpeciesID ORDER BY tr.PlotNo, tr.SerialNo");

		ps.setLong(1, tagging.getTaggingID());

		return getTaggingRecords(ps.executeQuery());
	}

	ArrayList<TaggingRecord> getTaggingRecords(TaggingForm taggingForm) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT tr.*, s.SpeciesTypeID, s.Code AS SpeciesCode, s.Name AS SpeciesName, cs.Code AS CorrectedSpeciesCode, cs.Name AS CorrectedSpeciesName, tt.Name AS TreeTypeName, lg.Code AS LogQuality, lg.Name AS LogQualityName FROM TaggingRecord tr JOIN Species s LEFT JOIN Species cs ON tr.CorrectedSpeciesID = cs.SpeciesID LEFT JOIN TreeType tt ON tr.TreeTypeID = tt.TreeTypeID LEFT JOIN LogQuality lg ON tr.LogQualityID = lg.LogQualityID WHERE tr.TaggingFormID = ? AND tr.SpeciesID = s.SpeciesID ORDER BY tr.PlotNo, tr.SerialNo");

		ps.setLong(1, taggingForm.getTaggingFormID());

		return getTaggingRecords(ps.executeQuery());
	}

	String[] getHeaderBukuKawalanPengeluaran(long taggingID, long licenseID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT t.*, ps.ComptBlockNo, ps.Area, f.Name AS ForestName, d.Name AS DistrictName, stt.Name AS StateName, l.LicenseNo, lc.CompanyName " + 
				"FROM TransferPass tp, Tagging t, Forest f, Hall h, PrefellingSurvey ps, District d, State stt, License l, LoggingContractor lc " + 
				"WHERE t.HallID = h.HallID AND t.PrefellingSurveyID = ps.PrefellingSurveyID AND ps.ForestID = f.ForestID AND f.DistrictID = d.DistrictID AND d.StateID = stt.StateID AND tp.TaggingID = t.TaggingID AND tp.LicenseID = l.LicenseID AND t.TaggingID = ? AND l.LicenseID = ? AND l.LicenseeID = lc.LoggingContractorID");

		ps.setLong(1, taggingID);
		ps.setLong(2, licenseID);

		ResultSet rs = ps.executeQuery();

		String[] log = new String[5];
		while (rs.next())
		{
			log[0] = rs.getString("ComptBlockNo");
			log[1] = rs.getString("ForestName");
			log[2] = rs.getString("DistrictName");
			log[3] = rs.getString("LicenseNo")+" - "+rs.getString("CompanyName");
			log[4] = rs.getString("Area");
		}
		return log;
	}	

	ArrayList<String[]> getCurrentStatusTaggingRecords(long taggingID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT s.Name AS SpeciesName, tr.SerialNo, tr.Diameter, 0 AS IsipaduPokok, tr.CorrectedEstimation, lq.Code AS KualitiBalakPertama " + 
				"FROM TaggingRecord tr LEFT JOIN TaggingForm tf ON tr.TaggingFormID = tf.TaggingFormID LEFT JOIN Tagging t ON tf.TaggingID = t.TaggingID LEFT JOIN Species s ON tr.CorrectedSpeciesID = s.SpeciesID LEFT JOIN LogQuality lq ON tr.LogQualityID = lq.LogQualityID " + 
				"WHERE t.TaggingID = ? GROUP BY tr.SerialNo ORDER BY tr.SerialNo");

		ps.setLong(1, taggingID);

		ResultSet rs = ps.executeQuery();

		ArrayList<String[]> logs = new ArrayList<String[]>();
		while (rs.next())
		{
			String[] log = new String[23];
			log[0] = rs.getString("SpeciesName");
			log[1] = rs.getString("SerialNo");
			log[2] = rs.getString("Diameter");
			log[3] = rs.getString("IsipaduPokok");
			log[4] = String.valueOf(rs.getInt("CorrectedEstimation"));
			log[5] = String.valueOf(rs.getInt("KualitiBalakPertama"));

			for(int i = 0; i < 15; i++)
			{
				log[i + 6] = " ";
			}

			for(int i = Integer.parseInt(log[4]); i < 5; i++)
			{
				log[i*3 + 6] = "X";
				log[i*3 + 7] = "X";
				log[i*3 + 8] = "X";
			}

			log[21] = "0";
			log[22] = "0";

			logs.add(log);
		}

		ps = facade.prepareStatement("SELECT s.Name AS SpeciesName, tr.SerialNo, tr.Diameter, 0 AS IsipaduPokok, tr.estimation, lq.Code AS KualitiBalakPertama, IF(lg.LogNo = 1, lg.Length, 'X') AS PanjangTual1, IF(lg.LogNo = 1, lg.Diameter, 'X') AS DiameterTual1, IF(lg.LogNo = 1, ROUND((PI()*(lg.Diameter/2/100)*(lg.Diameter/2/100)*lg.Length)-(PI()*(lg.HoleDiameter/2/100)*(lg.HoleDiameter/2/100)*lg.Length), 2), 'X') AS IsipaduTual1, "
				+ "IF(lg.LogNo = 2, lg.Length, 'X') AS PanjangTual2, IF(lg.LogNo = 2, lg.Diameter, 'X') AS DiameterTual2, IF(lg.LogNo = 2, ROUND((PI()*(lg.Diameter/2/100)*(lg.Diameter/2/100)*lg.Length)-(PI()*(lg.HoleDiameter/2/100)*(lg.HoleDiameter/2/100)*lg.Length), 2), 'X') AS IsipaduTual2, "
				+ "IF(lg.LogNo = 3, lg.Length, 'X') AS PanjangTual3, IF(lg.LogNo = 3, lg.Diameter, 'X') AS DiameterTual3, IF(lg.LogNo = 3, ROUND((PI()*(lg.Diameter/2/100)*(lg.Diameter/2/100)*lg.Length)-(PI()*(lg.HoleDiameter/2/100)*(lg.HoleDiameter/2/100)*lg.Length), 2), 'X') AS IsipaduTual3, "
				+ "IF(lg.LogNo = 4, lg.Length, 'X') AS PanjangTual4, IF(lg.LogNo = 4, lg.Diameter, 'X') AS DiameterTual4, IF(lg.LogNo = 4, ROUND((PI()*(lg.Diameter/2/100)*(lg.Diameter/2/100)*lg.Length)-(PI()*(lg.HoleDiameter/2/100)*(lg.HoleDiameter/2/100)*lg.Length), 2), 'X') AS IsipaduTual4, "
				+ "IF(lg.LogNo = 5, lg.Length, 'X') AS PanjangTual5, IF(lg.LogNo = 5, lg.Diameter, 'X') AS DiameterTual5, IF(lg.LogNo = 5, ROUND((PI()*(lg.Diameter/2/100)*(lg.Diameter/2/100)*lg.Length)-(PI()*(lg.HoleDiameter/2/100)*(lg.HoleDiameter/2/100)*lg.Length), 2), 'X') AS IsipaduTual5 " + 
				"FROM Tagging t LEFT JOIN TaggingForm tf ON tf.TaggingID = t.TaggingID LEFT JOIN TaggingRecord tr ON tr.TaggingFormID = tf.TaggingFormID LEFT JOIN Species s ON tr.CorrectedSpeciesID = s.SpeciesID LEFT JOIN LogQuality lq ON tr.LogQualityID = lq.LogQualityID LEFT JOIN Log lg ON lg.TaggingRecordID = tr.TaggingRecordID " + 
				"WHERE lg.Status = 'P' AND t.TaggingID = ? ORDER BY tr.SerialNo");

		ps.setLong(1, taggingID);

		rs = ps.executeQuery();
		BigDecimal jumlahIsipadu = BigDecimal.ZERO;
		int jumlahBilTual = 0;

		while (rs.next())
		{
			for(String[] log : logs)
			{
				if(log[1].equalsIgnoreCase(rs.getString("SerialNo")))
				{
					if(!rs.getString("PanjangTual1").equalsIgnoreCase("X"))
					{
						log[6] = rs.getString("PanjangTual1");
						log[7] = rs.getString("DiameterTual1");
						log[8] = String.format("%,.2f", rs.getBigDecimal("IsipaduTual1").setScale(2, BigDecimal.ROUND_HALF_UP));
					}
					if(!rs.getString("PanjangTual2").equalsIgnoreCase("X"))
					{
						log[9] = rs.getString("PanjangTual2");
						log[10] = rs.getString("DiameterTual2");
						log[11] = String.format("%,.2f", rs.getBigDecimal("IsipaduTual2").setScale(2, BigDecimal.ROUND_HALF_UP));
					}
					if(!rs.getString("PanjangTual3").equalsIgnoreCase("X"))
					{
						log[12] = rs.getString("PanjangTual3");
						log[13] = rs.getString("DiameterTual3");
						log[14] = String.format("%,.2f", rs.getBigDecimal("IsipaduTual3").setScale(2, BigDecimal.ROUND_HALF_UP));
					}
					if(!rs.getString("PanjangTual4").equalsIgnoreCase("X"))
					{
						log[15] = rs.getString("PanjangTual4");
						log[16] = rs.getString("DiameterTual4");
						log[17] = String.format("%,.2f", rs.getBigDecimal("IsipaduTual4").setScale(2, BigDecimal.ROUND_HALF_UP));
					}			
					if(!rs.getString("PanjangTual5").equalsIgnoreCase("X"))
					{
						log[18] = rs.getString("PanjangTual5");
						log[19] = rs.getString("DiameterTual5");
						log[20] = String.format("%,.2f", rs.getBigDecimal("IsipaduTual5").setScale(2, BigDecimal.ROUND_HALF_UP));
					}		

					jumlahIsipadu = BigDecimal.ZERO;
					jumlahBilTual = 0;
					for(int i = 0; i < 5; i++)
					{					
						if(!(log[i*3+6].equalsIgnoreCase("X") || log[i*3+6].equalsIgnoreCase(" ")))
						{
							jumlahBilTual++;
							jumlahIsipadu = jumlahIsipadu.add(new BigDecimal(log[i*3+8]));
						}
					}
					log[21] = String.valueOf(jumlahBilTual);
					log[22] = String.format("%,.2f", jumlahIsipadu.setScale(2, BigDecimal.ROUND_HALF_UP));
				}
			}
		}

		return logs;
	}
}