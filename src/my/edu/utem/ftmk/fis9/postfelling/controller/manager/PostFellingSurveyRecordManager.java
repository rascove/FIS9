package my.edu.utem.ftmk.fis9.postfelling.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingSurveyCard;
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingSurveyRecord;

/**
 * @author Satrya Fajri Pratama, Zurina
 */
class PostFellingSurveyRecordManager extends PostFellingTableManager
{
	PostFellingSurveyRecordManager(PostFellingFacade facade)
	{
		super(facade);
	}

	private void write(PostFellingSurveyRecord postFellingSurveyRecord, PreparedStatement ps) throws SQLException
	{
		nullable(ps, 1, postFellingSurveyRecord.getDiameter());
		nullable(ps, 2, postFellingSurveyRecord.getLogQuantity());
		nullable(ps, 3, postFellingSurveyRecord.getLogQualityID());
		nullable(ps, 4, postFellingSurveyRecord.getTreeStatusID());
		nullable(ps, 5, postFellingSurveyRecord.getSilaraID());
		nullable(ps, 6, postFellingSurveyRecord.getDominanceID());
		nullable(ps, 7, postFellingSurveyRecord.getVine());
		nullable(ps, 8, postFellingSurveyRecord.getFertilityID());
		nullable(ps, 9, postFellingSurveyRecord.getSaplingQuantity());
		nullable(ps, 10, postFellingSurveyRecord.getSeedlingQuantity());
		nullable(ps, 11, postFellingSurveyRecord.getSpeciesID());
		ps.setInt(12, postFellingSurveyRecord.getPlotTypeID());
		nullable(ps, 13, postFellingSurveyRecord.getInspectionDiameter());
		nullable(ps, 14, postFellingSurveyRecord.getInspectionLogQuantity());
		nullable(ps, 15, postFellingSurveyRecord.getInspectionLogQualityID());
		nullable(ps, 16, postFellingSurveyRecord.getInspectionTreeStatusID());
		nullable(ps, 17, postFellingSurveyRecord.getInspectionSilaraID());
		nullable(ps, 18, postFellingSurveyRecord.getInspectionDominanceID());
		nullable(ps, 19, postFellingSurveyRecord.getInspectionVine());
		nullable(ps, 20, postFellingSurveyRecord.getInspectionFertilityID());
		nullable(ps, 21, postFellingSurveyRecord.getInspectionSaplingQuantity());
		nullable(ps, 22, postFellingSurveyRecord.getInspectionSeedlingQuantity());
		nullable(ps, 23, postFellingSurveyRecord.getInspectionSpeciesID());
		ps.setInt(24, toInt(postFellingSurveyRecord.isInspection()));
		ps.setLong(25, postFellingSurveyRecord.getPostFellingSurveyRecordID());
	}

	int addPostFellingSurveyRecord(PostFellingSurveyRecord postFellingSurveyRecord, boolean ignoreDuplicate) throws SQLException
	{
		String sql = (ignoreDuplicate ? "INSERT IGNORE" : "INSERT") + " INTO PostFellingSurveyRecord (Diameter, LogQuantity, LogQualityID, TreeStatusID, SilaraID, DominanceID, Vine, FertilityID, SaplingQuantity, SeedlingQuantity, SpeciesID, PlotTypeID, InspectionDiameter, InspectionLogQuantity, InspectionLogQualityID, InspectionTreeStatusID, InspectionSilaraID, InspectionDominanceID, InspectionVine, InspectionFertilityID, InspectionSaplingQuantity, InspectionSeedlingQuantity, InspectionSpeciesID, Inspection, PostFellingSurveyRecordID, PostFellingSurveyCardID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" + (ignoreDuplicate ? "" : " ON DUPLICATE KEY UPDATE Diameter = ?, LogQuantity = ?, LogQualityID = ?, TreeStatusID = ?, SilaraID = ? , DominanceID = ?, Vine = ?, FertilityID = ?, SaplingQuantity = ?, SeedlingQuantity = ?, SpeciesID = ?, PlotTypeID = ?, InspectionDiameter = ?, InspectionLogQuantity = ?, InspectionLogQualityID = ?, InspectionTreeStatusID = ?, InspectionSilaraID = ?, InspectionDominanceID = ?, InspectionVine = ?, InspectionFertilityID = ?, InspectionSaplingQuantity = ?, InspectionSeedlingQuantity = ?, InspectionSpeciesID = ?, Inspection = ?");
		PreparedStatement ps = facade.prepareStatement(sql);

		write(postFellingSurveyRecord, ps);
		ps.setLong(26, postFellingSurveyRecord.getPostFellingSurveyCardID());
		
		if (!ignoreDuplicate)
		{
			nullable(ps, 27, postFellingSurveyRecord.getDiameter());
			nullable(ps, 28, postFellingSurveyRecord.getLogQuantity());
			nullable(ps, 29, postFellingSurveyRecord.getLogQualityID());
			nullable(ps, 30, postFellingSurveyRecord.getTreeStatusID());
			nullable(ps, 31, postFellingSurveyRecord.getSilaraID());
			nullable(ps, 32, postFellingSurveyRecord.getDominanceID());
			nullable(ps, 33, postFellingSurveyRecord.getVine());
			nullable(ps, 34, postFellingSurveyRecord.getFertilityID());
			nullable(ps, 35, postFellingSurveyRecord.getSaplingQuantity());
			nullable(ps, 36, postFellingSurveyRecord.getSeedlingQuantity());
			nullable(ps, 37, postFellingSurveyRecord.getSpeciesID());
			ps.setInt(38, postFellingSurveyRecord.getPlotTypeID());
			nullable(ps, 39, postFellingSurveyRecord.getInspectionDiameter());
			nullable(ps, 40, postFellingSurveyRecord.getInspectionLogQuantity());
			nullable(ps, 41, postFellingSurveyRecord.getInspectionLogQualityID());
			nullable(ps, 42, postFellingSurveyRecord.getInspectionTreeStatusID());
			nullable(ps, 43, postFellingSurveyRecord.getInspectionSilaraID());
			nullable(ps, 44, postFellingSurveyRecord.getInspectionDominanceID());
			nullable(ps, 45, postFellingSurveyRecord.getInspectionVine());
			nullable(ps, 46, postFellingSurveyRecord.getInspectionFertilityID());
			nullable(ps, 47, postFellingSurveyRecord.getInspectionSaplingQuantity());
			nullable(ps, 48, postFellingSurveyRecord.getInspectionSeedlingQuantity());
			nullable(ps, 49, postFellingSurveyRecord.getInspectionSpeciesID());
			ps.setInt(50, toInt(postFellingSurveyRecord.isInspection()));
		}
		
		int status = ps.executeUpdate();
		
		if (status == 0 && !ignoreDuplicate)
			status = 1;
	
		return status;
	}

	
	int updatePostFellingSurveyRecord(PostFellingSurveyRecord postFellingSurveyRecord) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE PostFellingSurveyRecord SET Diameter = ?, LogQuantity = ?, LogQualityID = ?, TreeStatusID = ?, SilaraID = ?, DominanceID = ?, Vine = ?, FertilityID = ?, SaplingQuantity = ?, SeedlingQuantity = ?, SpeciesID = ?, PlotTypeID = ?, InspectionDiameter = ?, InspectionLogQuantity = ?, InspectionLogQualityID = ?, InspectionTreeStatusID = ?, InspectionSilaraID = ?, InspectionDominanceID = ?, InspectionVine = ?, InspectionFertilityID = ?, InspectionSaplingQuantity = ?, InspectionSeedlingQuantity = ?, InspectionSpeciesID = ?, Inspection = ? WHERE PostFellingSurveyRecordID = ?");

		write(postFellingSurveyRecord, ps);
		return ps.executeUpdate();
	}
	
	int updatePostFellingInspectionRecord(PostFellingSurveyRecord postFellingSurveyRecord) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE PostFellingSurveyRecord SET InspectionDiameter = ?, InspectionLogQuantity = ?, InspectionLogQualityID = ?, InspectionTreeStatusID = ?, InspectionSilaraID = ?, InspectionDominanceID = ?, InspectionVine = ?, InspectionFertilityID = ?, InspectionSaplingQuantity = ?, InspectionSeedlingQuantity = ?, InspectionSpeciesID = ?, Inspection = ? WHERE PostFellingSurveyRecordID = ?");

		nullable(ps, 1, postFellingSurveyRecord.getInspectionDiameter());
		nullable(ps, 2, postFellingSurveyRecord.getInspectionLogQuantity());
		nullable(ps, 3, postFellingSurveyRecord.getInspectionLogQualityID());
		nullable(ps, 4, postFellingSurveyRecord.getInspectionTreeStatusID());
		nullable(ps, 5, postFellingSurveyRecord.getInspectionSilaraID());
		nullable(ps, 6, postFellingSurveyRecord.getInspectionDominanceID());
		nullable(ps, 7, postFellingSurveyRecord.getInspectionVine());
		nullable(ps, 8, postFellingSurveyRecord.getInspectionFertilityID());
		nullable(ps, 9, postFellingSurveyRecord.getInspectionSaplingQuantity());
		nullable(ps, 10, postFellingSurveyRecord.getInspectionSeedlingQuantity());
		nullable(ps, 11, postFellingSurveyRecord.getInspectionSpeciesID());
		ps.setInt(12, toInt(postFellingSurveyRecord.isInspection()));
		ps.setLong(13, postFellingSurveyRecord.getPostFellingSurveyRecordID());

		return ps.executeUpdate();
	}

	int deletePostFellingSurveyRecord(PostFellingSurveyRecord postFellingSurveyRecord) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("DELETE FROM PostFellingSurveyRecord WHERE PostFellingSurveyRecordID = ?");

		ps.setLong(1, postFellingSurveyRecord.getPostFellingSurveyRecordID());

		return ps.executeUpdate();
	}
	
	int resetPostFellingSurveyRecordForInspection(PostFellingSurveyRecord postFellingSurveyRecord) throws SQLException
	{
		
		if (postFellingSurveyRecord.getDiameter()==0 && postFellingSurveyRecord.getSaplingQuantity()==0 && postFellingSurveyRecord.getSeedlingQuantity()==0 )
			return deletePostFellingSurveyRecord(postFellingSurveyRecord);
		else
		{
			PreparedStatement ps = facade.prepareStatement("UPDATE PostFellingSurveyRecord SET InspectionDiameter = ?, InspectionLogQuantity = ?, InspectionLogQualityID = ?, InspectionTreeStatusID = ?, InspectionSilaraID = ?, InspectionDominanceID = ?, InspectionVine = ?, InspectionFertilityID = ?, InspectionSaplingQuantity = ?, InspectionSeedlingQuantity = ?, InspectionSpeciesID = ? WHERE PostFellingSurveyRecordID = ?");
	
			nullable(ps, 1, 0);
			nullable(ps, 2, 0);
			nullable(ps, 3, 0);
			nullable(ps, 4, 0);
			nullable(ps, 5, 0);
			nullable(ps, 6, 0);
			nullable(ps, 7, 0);
			nullable(ps, 8, 0);
			nullable(ps, 9, 0);
			nullable(ps, 10, 0);
			nullable(ps, 11, 0);
			ps.setLong(12, postFellingSurveyRecord.getPostFellingSurveyRecordID());
			
			return ps.executeUpdate();
		}
		
	}

	private ArrayList<PostFellingSurveyRecord> getPostFellingSurveyRecords(ResultSet rs) throws SQLException
	{
		ArrayList<PostFellingSurveyRecord> postFellingSurveyRecords = new ArrayList<>();

		while (rs.next())
		{
			PostFellingSurveyRecord postFellingSurveyRecord = new PostFellingSurveyRecord();
				postFellingSurveyRecord.setPostFellingSurveyRecordID(rs.getLong("PostFellingSurveyRecordID"));
				postFellingSurveyRecord.setDiameter(rs.getDouble("Diameter"));
				postFellingSurveyRecord.setLogQuantity(rs.getInt("LogQuantity"));
				postFellingSurveyRecord.setLogQualityID(rs.getInt("LogQualityID"));
				postFellingSurveyRecord.setLogQuality(rs.getString("LogQuality"));
				postFellingSurveyRecord.setTreeStatusID(rs.getInt("TreeStatusID"));
				postFellingSurveyRecord.setSilaraID(rs.getInt("SilaraID"));
				postFellingSurveyRecord.setDominanceID(rs.getInt("DominanceID"));
				postFellingSurveyRecord.setVine(rs.getInt("Vine"));
				postFellingSurveyRecord.setFertilityID(rs.getInt("FertilityID"));
				postFellingSurveyRecord.setSaplingQuantity(rs.getInt("SaplingQuantity"));
				postFellingSurveyRecord.setSeedlingQuantity(rs.getInt("SeedlingQuantity"));
				postFellingSurveyRecord.setPostFellingSurveyCardID(rs.getLong("PostFellingSurveyCardID"));
				postFellingSurveyRecord.setSpeciesID(rs.getInt("SpeciesID"));
				postFellingSurveyRecord.setSpeciesTypeID(rs.getInt("SpeciesTypeID"));
				postFellingSurveyRecord.setPlotTypeID(rs.getInt("PlotTypeID"));
				postFellingSurveyRecord.setSpeciesCode(rs.getString("SpeciesCode"));
				postFellingSurveyRecord.setSpeciesName(rs.getString("SpeciesName"));
				postFellingSurveyRecord.setPlotTypeName(rs.getString("PlotTypeName"));
				postFellingSurveyRecord.setLogQualityName(rs.getString("LogQualityName"));
				postFellingSurveyRecord.setFertility(rs.getString("Fertility"));
				postFellingSurveyRecord.setFertilityName(rs.getString("FertilityName"));
				postFellingSurveyRecord.setTreeStatus(rs.getString("TreeStatus"));
				postFellingSurveyRecord.setSilara(rs.getString("Silara"));
				postFellingSurveyRecord.setDominance(rs.getString("Dominance"));
				postFellingSurveyRecord.setTreeStatusName(rs.getString("TreeStatusName"));
				postFellingSurveyRecord.setSilaraName(rs.getString("SilaraName"));
				postFellingSurveyRecord.setDominanceName(rs.getString("DominanceName"));
				postFellingSurveyRecord.setInspectionLogQuality(rs.getString("InspectionLogQuality"));
				postFellingSurveyRecord.setInspectionLogQualityName(rs.getString("InspectionLogQualityName"));
				postFellingSurveyRecord.setInspectionFertility(rs.getString("InspectionFertility"));
				postFellingSurveyRecord.setInspectionFertilityName(rs.getString("InspectionFertilityName"));
				postFellingSurveyRecord.setInspectionTreeStatus(rs.getString("InspectionTreeStatus"));
				postFellingSurveyRecord.setInspectionSilara(rs.getString("InspectionSilara"));
				postFellingSurveyRecord.setInspectionDominance(rs.getString("InspectionDominance"));
				postFellingSurveyRecord.setInspectionTreeStatusName(rs.getString("InspectionTreeStatusName"));
				postFellingSurveyRecord.setInspectionSilaraName(rs.getString("InspectionSilaraName"));
				postFellingSurveyRecord.setInspectionDominanceName(rs.getString("InspectionDominanceName"));
				postFellingSurveyRecord.setInspectionDiameter(rs.getDouble("InspectionDiameter"));
				postFellingSurveyRecord.setInspectionLogQuantity(rs.getInt("InspectionLogQuantity"));
				postFellingSurveyRecord.setInspectionLogQualityID(rs.getInt("InspectionLogQualityID"));
				postFellingSurveyRecord.setInspectionTreeStatusID(rs.getInt("InspectionTreeStatusID"));
				postFellingSurveyRecord.setInspectionSilaraID(rs.getInt("InspectionSilaraID"));
				postFellingSurveyRecord.setInspectionDominanceID(rs.getInt("InspectionDominanceID"));
				postFellingSurveyRecord.setInspectionVine(rs.getInt("InspectionVine"));
				postFellingSurveyRecord.setInspectionFertilityID(rs.getInt("InspectionFertilityID"));
				postFellingSurveyRecord.setInspectionSaplingQuantity(rs.getInt("InspectionSaplingQuantity"));
				postFellingSurveyRecord.setInspectionSeedlingQuantity(rs.getInt("InspectionSeedlingQuantity"));
				postFellingSurveyRecord.setInspectionSpeciesID(rs.getInt("InspectionSpeciesID"));
				postFellingSurveyRecord.setInspectionSpeciesCode(rs.getString("InspectionSpeciesCode"));
				postFellingSurveyRecord.setInspectionSpeciesName(rs.getString("InspectionSpeciesName"));
				postFellingSurveyRecord.setInspection(toBoolean(rs.getInt("Inspection")));
				
				
				postFellingSurveyRecords.add(postFellingSurveyRecord);	
		}

		return postFellingSurveyRecords;
	}
	

	ArrayList<PostFellingSurveyRecord> getPostFellingSurveyRecords(PostFellingSurveyCard postFellingSurveyCard) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT a.*, b.Code AS LogQuality, b.Name AS LogQualityName, c.Code AS Fertility, c.Name AS FertilityName, d.Code AS TreeStatus, d.Name AS TreeStatusName, e.Code AS Silara, e.Name AS SilaraName, f.Code AS Dominance, f.Name AS DominanceName, g.Code AS InspectionLogQuality, g.Name AS InspectionLogQualityName, h.Code AS InspectionFertility, h.Name AS InspectionFertilityName, i.Code as InspectionTreeStatus, i.Name as InspectionTreeStatusName, j.Code AS InspectionSilara, j.Name AS InspectionSilaraName, k.Code AS InspectionDominance, k.Name AS InspectionDominanceName, sins.Code AS InspectionSpeciesCode, sins.Name AS InspectionSpeciesName, s.SpeciesTypeID, s.Code AS SpeciesCode, s.Name AS SpeciesName FROM (SELECT sr.*, CONCAT(pt.Name, ' - ', pt.Description) AS PlotTypeName, pt.Description FROM PostFellingSurveyRecord sr, PlotType pt WHERE sr.PostFellingSurveyCardID = ? AND sr.PlotTypeID = pt.PlotTypeID ORDER BY sr.PlotTypeID) AS a LEFT OUTER JOIN (SELECT * FROM Species) AS s ON a.SpeciesID = s.SpeciesID LEFT OUTER JOIN (SELECT * FROM LogQuality) AS b ON a.LogQualityID = b.LogQualityID LEFT OUTER JOIN (SELECT * FROM Fertility) AS c ON a. FertilityID = c.FertilityID LEFT OUTER JOIN (SELECT * FROM TreeStatus) AS d ON a.TreeStatusID = d.TreeStatusID LEFT OUTER JOIN (SELECT * FROM Silara) AS e ON a.SilaraID = e.SilaraID LEFT OUTER JOIN (SELECT * FROM Dominance) AS f ON a.DominanceID = f.DominanceID LEFT OUTER JOIN (SELECT * FROM LogQuality) AS g ON a.InspectionLogQualityID = g.LogQualityID LEFT OUTER JOIN (SELECT * FROM Fertility) AS h ON a. InspectionFertilityID = h.FertilityID LEFT OUTER JOIN (SELECT * FROM TreeStatus) AS i ON a.InspectionTreeStatusID = i.TreeStatusID LEFT OUTER JOIN (SELECT * FROM Silara) AS j ON a.InspectionSilaraID = j.SilaraID LEFT OUTER JOIN (SELECT * FROM Dominance) AS k ON a.InspectionDominanceID = k.DominanceID LEFT OUTER JOIN (SELECT * FROM Species) AS sins ON a.InspectionSpeciesID = sins.SpeciesID");
		
		ps.setLong(1, postFellingSurveyCard.getPostFellingSurveyCardID());
		
		
		return getPostFellingSurveyRecords(ps.executeQuery());
	}
	
	ArrayList<PostFellingSurveyRecord> getPostFellingSurveyRecordsForInspection(PostFellingSurveyCard postFellingSurveyCard) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(" SELECT a.*, b.Code AS LogQuality, b.Name AS LogQualityName, c.Code AS Fertility, c.Name AS FertilityName, d.Code AS TreeStatus, d.Name AS TreeStatusName, e.Code AS Silara, e.Name AS SilaraName, f.Code AS Dominance, f.Name AS DominanceName, g.Code AS InspectionLogQuality, g.Name AS InspectionLogQualityName, h.Code AS InspectionFertility, h.Name AS InspectionFertilityName, i.Code as InspectionTreeStatus, i.Name as InspectionTreeStatusName, j.Code AS InspectionSilara, j.Name AS InspectionSilaraName, k.Code AS InspectionDominance, k.Name AS InspectionDominanceName, sins.Code AS InspectionSpeciesCode, sins.Name AS InspectionSpeciesName, s.SpeciesTypeID, s.Code AS SpeciesCode, s.Name AS SpeciesName FROM (SELECT sr.*, CONCAT(pt.Name, ' - ', pt.Description) AS PlotTypeName, pt.Description FROM PostFellingSurveyRecord sr, PlotType pt WHERE sr.PostFellingSurveyCardID = ? AND sr.PlotTypeID = pt.PlotTypeID ORDER BY sr.PlotTypeID) AS a LEFT OUTER JOIN (SELECT * FROM Species) AS s ON a.SpeciesID = s.SpeciesID LEFT OUTER JOIN (SELECT * FROM LogQuality) AS b ON a.LogQualityID = b.LogQualityID LEFT OUTER JOIN (SELECT * FROM Fertility) AS c ON a. FertilityID = c.FertilityID LEFT OUTER JOIN (SELECT * FROM TreeStatus) AS d ON a.TreeStatusID = d.TreeStatusID LEFT OUTER JOIN (SELECT * FROM Silara) AS e ON a.SilaraID = e.SilaraID LEFT OUTER JOIN (SELECT * FROM Dominance) AS f ON a.DominanceID = f.DominanceID LEFT OUTER JOIN (SELECT * FROM LogQuality) AS g ON a.InspectionLogQualityID = g.LogQualityID LEFT OUTER JOIN (SELECT * FROM Fertility) AS h ON a. InspectionFertilityID = h.FertilityID LEFT OUTER JOIN (SELECT * FROM TreeStatus) AS i ON a.InspectionTreeStatusID = i.TreeStatusID LEFT OUTER JOIN (SELECT * FROM Silara) AS j ON a.InspectionSilaraID = j.SilaraID LEFT OUTER JOIN (SELECT * FROM Dominance) AS k ON a.InspectionDominanceID = k.DominanceID LEFT OUTER JOIN (SELECT * FROM Species) AS sins ON a.InspectionSpeciesID = sins.SpeciesID");
		ps.setLong(1, postFellingSurveyCard.getPostFellingSurveyCardID());
		
		return getPostFellingSurveyRecordsForInspection(ps.executeQuery());
	}
	
	private ArrayList<PostFellingSurveyRecord> getPostFellingSurveyRecordsForInspection(ResultSet rs) throws SQLException
	{
		ArrayList<PostFellingSurveyRecord> postFellingSurveyRecords = new ArrayList<>();

		while (rs.next())
		{
			PostFellingSurveyRecord postFellingSurveyRecord = new PostFellingSurveyRecord();

			postFellingSurveyRecord.setPostFellingSurveyRecordID(rs.getLong("PostFellingSurveyRecordID"));
			postFellingSurveyRecord.setDiameter(rs.getDouble("Diameter"));
			postFellingSurveyRecord.setLogQuantity(rs.getInt("LogQuantity"));
			postFellingSurveyRecord.setLogQualityID(rs.getInt("LogQualityID"));
			postFellingSurveyRecord.setTreeStatusID(rs.getInt("TreeStatusID"));
			postFellingSurveyRecord.setSilaraID(rs.getInt("SilaraID"));
			postFellingSurveyRecord.setDominanceID(rs.getInt("DominanceID"));
			postFellingSurveyRecord.setVine(rs.getInt("Vine"));
			postFellingSurveyRecord.setFertilityID(rs.getInt("FertilityID"));
			postFellingSurveyRecord.setSaplingQuantity(rs.getInt("SaplingQuantity"));
			postFellingSurveyRecord.setSeedlingQuantity(rs.getInt("SeedlingQuantity"));
			postFellingSurveyRecord.setPostFellingSurveyCardID(rs.getLong("PostFellingSurveyCardID"));
			postFellingSurveyRecord.setSpeciesID(rs.getInt("SpeciesID"));
			postFellingSurveyRecord.setSpeciesTypeID(rs.getInt("SpeciesTypeID"));
			postFellingSurveyRecord.setPlotTypeID(rs.getInt("PlotTypeID"));
			postFellingSurveyRecord.setSpeciesCode(rs.getString("SpeciesCode"));
			postFellingSurveyRecord.setSpeciesName(rs.getString("SpeciesName"));
			postFellingSurveyRecord.setPlotTypeName(rs.getString("PlotTypeName"));
			//if (rs.getDouble("InspectionDiameter") > 0 || rs.getInt("InspectionSaplingQuantity") > 0 || rs.getInt("InspectionSeedlingQuantity") > 0) 
			//{
				
				postFellingSurveyRecord.setInspectionLogQuality(rs.getString("InspectionLogQuality"));
				postFellingSurveyRecord.setInspectionLogQualityName(rs.getString("InspectionLogQualityName"));
				postFellingSurveyRecord.setInspectionFertility(rs.getString("InspectionFertility"));
				postFellingSurveyRecord.setInspectionFertilityName(rs.getString("InspectionFertilityName"));
				postFellingSurveyRecord.setInspectionTreeStatus(rs.getString("InspectionTreeStatus"));
				postFellingSurveyRecord.setInspectionSilara(rs.getString("InspectionSilara"));
				postFellingSurveyRecord.setInspectionDominance(rs.getString("InspectionDominance"));
				postFellingSurveyRecord.setInspectionTreeStatusName(rs.getString("InspectionTreeStatusName"));
				postFellingSurveyRecord.setInspectionSilaraName(rs.getString("InspectionSilaraName"));
				postFellingSurveyRecord.setInspectionDominanceName(rs.getString("InspectionDominanceName"));
				postFellingSurveyRecord.setInspectionDiameter(rs.getDouble("InspectionDiameter"));
				postFellingSurveyRecord.setInspectionLogQuantity(rs.getInt("InspectionLogQuantity"));
				postFellingSurveyRecord.setInspectionLogQualityID(rs.getInt("InspectionLogQualityID"));
				postFellingSurveyRecord.setInspectionTreeStatusID(rs.getInt("InspectionTreeStatusID"));
				postFellingSurveyRecord.setInspectionSilaraID(rs.getInt("InspectionSilaraID"));
				postFellingSurveyRecord.setInspectionDominanceID(rs.getInt("InspectionDominanceID"));
				postFellingSurveyRecord.setInspectionVine(rs.getInt("InspectionVine"));
				postFellingSurveyRecord.setInspectionFertilityID(rs.getInt("InspectionFertilityID"));
				postFellingSurveyRecord.setInspectionSaplingQuantity(rs.getInt("InspectionSaplingQuantity"));
				postFellingSurveyRecord.setInspectionSeedlingQuantity(rs.getInt("InspectionSeedlingQuantity"));	
				postFellingSurveyRecord.setInspectionSpeciesID(rs.getInt("InspectionSpeciesID"));
				postFellingSurveyRecord.setInspectionSpeciesCode(rs.getString("InspectionSpeciesCode"));
				postFellingSurveyRecord.setInspectionSpeciesName(rs.getString("InspectionSpeciesName"));
				postFellingSurveyRecord.setInspection(toBoolean(rs.getInt("Inspection")));
				postFellingSurveyRecords.add(postFellingSurveyRecord);
			//}
			
		}

		return postFellingSurveyRecords;
	}
	
	ArrayList<PostFellingSurveyRecord> getPostFellingSurveyRecordsFilterInspection(PostFellingSurveyCard postFellingSurveyCard) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT a.*, b.Code AS LogQuality, b.Name AS LogQualityName, c.Code AS Fertility, c.Name AS FertilityName, d.Code AS TreeStatus, d.Name AS TreeStatusName, e.Code AS Silara, e.Name AS SilaraName, f.Code AS Dominance, f.Name AS DominanceName, g.Code AS InspectionLogQuality, g.Name AS InspectionLogQualityName, h.Code AS InspectionFertility, h.Name AS InspectionFertilityName, i.Code as InspectionTreeStatus, i.Name as InspectionTreeStatusName, j.Code AS InspectionSilara, j.Name AS InspectionSilaraName, k.Code AS InspectionDominance, k.Name AS InspectionDominanceName, sins.Code AS InspectionSpeciesCode, sins.Name AS InspectionSpeciesName, s.SpeciesTypeID, s.Code AS SpeciesCode, s.Name AS SpeciesName FROM (SELECT sr.*, CONCAT(pt.Name, ' - ', pt.Description) AS PlotTypeName, pt.Description FROM PostFellingSurveyRecord sr, PlotType pt WHERE sr.PostFellingSurveyCardID = ? AND sr.PlotTypeID = pt.PlotTypeID ORDER BY sr.PlotTypeID) AS a LEFT OUTER JOIN (SELECT * FROM Species) AS s ON a.SpeciesID = s.SpeciesID LEFT OUTER JOIN (SELECT * FROM LogQuality) AS b ON a.LogQualityID = b.LogQualityID LEFT OUTER JOIN (SELECT * FROM Fertility) AS c ON a. FertilityID = c.FertilityID LEFT OUTER JOIN (SELECT * FROM TreeStatus) AS d ON a.TreeStatusID = d.TreeStatusID LEFT OUTER JOIN (SELECT * FROM Silara) AS e ON a.SilaraID = e.SilaraID LEFT OUTER JOIN (SELECT * FROM Dominance) AS f ON a.DominanceID = f.DominanceID LEFT OUTER JOIN (SELECT * FROM LogQuality) AS g ON a.InspectionLogQualityID = g.LogQualityID LEFT OUTER JOIN (SELECT * FROM Fertility) AS h ON a. InspectionFertilityID = h.FertilityID LEFT OUTER JOIN (SELECT * FROM TreeStatus) AS i ON a.InspectionTreeStatusID = i.TreeStatusID LEFT OUTER JOIN (SELECT * FROM Silara) AS j ON a.InspectionSilaraID = j.SilaraID LEFT OUTER JOIN (SELECT * FROM Dominance) AS k ON a.InspectionDominanceID = k.DominanceID LEFT OUTER JOIN (SELECT * FROM Species) AS sins ON a.InspectionSpeciesID = sins.SpeciesID");
		ps.setLong(1, postFellingSurveyCard.getPostFellingSurveyCardID());
		return getPostFellingSurveyRecordsFilterInspection(ps.executeQuery());
	}
	
	private ArrayList<PostFellingSurveyRecord> getPostFellingSurveyRecordsFilterInspection(ResultSet rs) throws SQLException
	{
		ArrayList<PostFellingSurveyRecord> postFellingSurveyRecords = new ArrayList<>();

		while (rs.next())
		{
			PostFellingSurveyRecord postFellingSurveyRecord = new PostFellingSurveyRecord();
			if (!toBoolean(rs.getInt("Inspection"))) 
			{
				postFellingSurveyRecord.setPostFellingSurveyRecordID(rs.getLong("PostFellingSurveyRecordID"));
				postFellingSurveyRecord.setDiameter(rs.getDouble("Diameter"));
				postFellingSurveyRecord.setLogQuantity(rs.getInt("LogQuantity"));
				postFellingSurveyRecord.setLogQualityID(rs.getInt("LogQualityID"));
				postFellingSurveyRecord.setTreeStatusID(rs.getInt("TreeStatusID"));
				postFellingSurveyRecord.setSilaraID(rs.getInt("SilaraID"));
				postFellingSurveyRecord.setDominanceID(rs.getInt("DominanceID"));
				postFellingSurveyRecord.setVine(rs.getInt("Vine"));
				postFellingSurveyRecord.setFertilityID(rs.getInt("FertilityID"));
				postFellingSurveyRecord.setSaplingQuantity(rs.getInt("SaplingQuantity"));
				postFellingSurveyRecord.setSeedlingQuantity(rs.getInt("SeedlingQuantity"));
				postFellingSurveyRecord.setPostFellingSurveyCardID(rs.getLong("PostFellingSurveyCardID"));
				postFellingSurveyRecord.setSpeciesID(rs.getInt("SpeciesID"));
				postFellingSurveyRecord.setSpeciesTypeID(rs.getInt("SpeciesTypeID"));
				postFellingSurveyRecord.setPlotTypeID(rs.getInt("PlotTypeID"));
				postFellingSurveyRecord.setSpeciesCode(rs.getString("SpeciesCode"));
				postFellingSurveyRecord.setSpeciesName(rs.getString("SpeciesName"));
				postFellingSurveyRecord.setPlotTypeName(rs.getString("PlotTypeName"));
				postFellingSurveyRecord.setLogQualityName(rs.getString("LogQualityName"));
				postFellingSurveyRecord.setFertility(rs.getString("Fertility"));
				postFellingSurveyRecord.setFertilityName(rs.getString("FertilityName"));
				postFellingSurveyRecord.setTreeStatus(rs.getString("TreeStatus"));
				postFellingSurveyRecord.setSilara(rs.getString("Silara"));
				postFellingSurveyRecord.setDominance(rs.getString("Dominance"));
				postFellingSurveyRecord.setTreeStatusName(rs.getString("TreeStatusName"));
				postFellingSurveyRecord.setSilaraName(rs.getString("SilaraName"));
				postFellingSurveyRecord.setDominanceName(rs.getString("DominanceName"));
				postFellingSurveyRecord.setInspectionLogQuality(rs.getString("InspectionLogQuality"));
				postFellingSurveyRecord.setInspectionLogQualityName(rs.getString("InspectionLogQualityName"));
				postFellingSurveyRecord.setInspectionFertility(rs.getString("InspectionFertility"));
				postFellingSurveyRecord.setInspectionFertilityName(rs.getString("InspectionFertilityName"));
				postFellingSurveyRecord.setInspectionTreeStatus(rs.getString("InspectionTreeStatus"));
				postFellingSurveyRecord.setInspectionSilara(rs.getString("InspectionSilara"));
				postFellingSurveyRecord.setInspectionDominance(rs.getString("InspectionDominance"));
				postFellingSurveyRecord.setInspectionTreeStatusName(rs.getString("InspectionTreeStatusName"));
				postFellingSurveyRecord.setInspectionSilaraName(rs.getString("InspectionSilaraName"));
				postFellingSurveyRecord.setInspectionDominanceName(rs.getString("InspectionDominanceName"));
				postFellingSurveyRecord.setInspectionDiameter(rs.getDouble("InspectionDiameter"));
				postFellingSurveyRecord.setInspectionLogQuantity(rs.getInt("InspectionLogQuantity"));
				postFellingSurveyRecord.setInspectionLogQualityID(rs.getInt("InspectionLogQualityID"));
				postFellingSurveyRecord.setInspectionTreeStatusID(rs.getInt("InspectionTreeStatusID"));
				postFellingSurveyRecord.setInspectionSilaraID(rs.getInt("InspectionSilaraID"));
				postFellingSurveyRecord.setInspectionDominanceID(rs.getInt("InspectionDominanceID"));
				postFellingSurveyRecord.setInspectionVine(rs.getInt("InspectionVine"));
				postFellingSurveyRecord.setInspectionFertilityID(rs.getInt("InspectionFertilityID"));
				postFellingSurveyRecord.setInspectionSaplingQuantity(rs.getInt("InspectionSaplingQuantity"));
				postFellingSurveyRecord.setInspectionSeedlingQuantity(rs.getInt("InspectionSeedlingQuantity"));	
				postFellingSurveyRecord.setInspectionSpeciesID(rs.getInt("InspectionSpeciesID"));
				postFellingSurveyRecord.setInspectionSpeciesCode(rs.getString("InspectionSpeciesCode"));
				postFellingSurveyRecord.setInspectionSpeciesName(rs.getString("InspectionSpeciesName"));
				postFellingSurveyRecord.setInspection(toBoolean(rs.getInt("Inspection")));
				
				postFellingSurveyRecords.add(postFellingSurveyRecord);	
			}
		}

		return postFellingSurveyRecords;
	}
	
	
}