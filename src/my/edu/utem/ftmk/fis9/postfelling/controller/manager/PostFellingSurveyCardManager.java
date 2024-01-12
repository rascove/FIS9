package my.edu.utem.ftmk.fis9.postfelling.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingSurveyCard;
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingSurvey;

/**
 * @author Satrya Fajri Pratama, Zurina Saaya
 */
class PostFellingSurveyCardManager extends PostFellingTableManager
{
	PostFellingSurveyCardManager(PostFellingFacade facade)
	{
		super(facade);
	}

	private void write(PostFellingSurveyCard postFellingSurveyCard, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, postFellingSurveyCard.getLineNo());
		ps.setString(2, postFellingSurveyCard.getPlotNo());
		nullable(ps, 3, postFellingSurveyCard.getLatitude());
		nullable(ps, 4, postFellingSurveyCard.getLongitude());
		nullable(ps, 5, postFellingSurveyCard.getForestTypeID());
		nullable(ps, 6, postFellingSurveyCard.getSoilStatusID());
		nullable(ps, 7, postFellingSurveyCard.getAspectID());
		ps.setInt(8, postFellingSurveyCard.getSlope());
		nullable(ps, 9, postFellingSurveyCard.getSlopeLocationID());
		nullable(ps, 10, postFellingSurveyCard.getElevationID());
		ps.setInt(11, postFellingSurveyCard.getBertam());
		ps.setInt(12, postFellingSurveyCard.getBamboo());
		ps.setInt(13, postFellingSurveyCard.getPalm());
		nullable(ps, 14, postFellingSurveyCard.getResamID());
		nullable(ps, 15, postFellingSurveyCard.getGingerID());
		nullable(ps, 16, postFellingSurveyCard.getBananaID());
		ps.setDate(17, toSQLDate(postFellingSurveyCard.getSurveyDate()));
		nullable(ps, 18, postFellingSurveyCard.getInspectionDate());
		ps.setString(19, postFellingSurveyCard.getInspectorID());
		nullable(ps, 20, postFellingSurveyCard.getInspectionForestTypeID());
		nullable(ps, 21,postFellingSurveyCard.getInspectionSoilStatusID());
		nullable(ps, 22, postFellingSurveyCard.getInspectionAspectID());
		nullable(ps, 23, postFellingSurveyCard.getInspectionSlope());
		nullable(ps, 24, postFellingSurveyCard.getInspectionSlopeLocationID());
		nullable(ps, 25, postFellingSurveyCard.getInspectionElevationID());
		nullable(ps, 26, postFellingSurveyCard.getInspectionBertam());
		nullable(ps, 27, postFellingSurveyCard.getInspectionBamboo());
		nullable(ps, 28, postFellingSurveyCard.getInspectionPalm());
		nullable(ps, 29, postFellingSurveyCard.getInspectionResamID());
		nullable(ps, 30, postFellingSurveyCard.getInspectionGingerID());
		nullable(ps, 31, postFellingSurveyCard.getInspectionBananaID());
		nullable(ps, 32, toInt(postFellingSurveyCard.isInspection()));
		ps.setLong(33, postFellingSurveyCard.getPostFellingSurveyCardID());
	}

	int addPostFellingSurveyCard(PostFellingSurveyCard postFellingSurveyCard, boolean ignoreDuplicate) throws SQLException
	{
		String sql = (ignoreDuplicate ? "INSERT IGNORE" : "INSERT") + " INTO PostFellingSurveyCard (LineNo, PlotNo, Latitude, Longitude, ForestTypeID, SoilStatusID, AspectID, Slope, SlopeLocationID, ElevationID, Bertam, Bamboo, Palm, ResamID, GingerID, BananaID, SurveyDate, InspectionDate, InspectorID, InspectionForestTypeID, InspectionSoilStatusID, InspectionAspectID, InspectionSlope, InspectionSlopeLocationID, InspectionElevationID, InspectionBertam, InspectionBamboo, InspectionPalm, InspectionResamID, InspectionGingerID, InspectionBananaID, Inspection, PostFellingSurveyCardID, PostFellingSurveyID, RecorderID) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" + (ignoreDuplicate ? "" : " ON DUPLICATE KEY UPDATE Latitude = ?, Longitude = ?, ForestTypeID = ?, SoilStatusID = ?, AspectID = ?, Slope = ?, SlopeLocationID = ?, ElevationID = ?, Bertam = ?, Bamboo = ?, Palm = ?, ResamID = ?, GingerID = ?, BananaID = ?, SurveyDate = ?, InspectionDate = ?, InspectorID = ?, InspectionForestTypeID = ?, InspectionSoilStatusID = ?, InspectionAspectID = ?, InspectionSlope = ?, InspectionSlopeLocationID = ?, InspectionElevationID = ?, InspectionBertam = ?, InspectionBamboo = ?, InspectionPalm = ?, InspectionResamID = ?, InspectionGingerID = ?, InspectionBananaID = ?, Inspection = ?");
		PreparedStatement ps = facade.prepareStatement(sql);

		write(postFellingSurveyCard, ps);
		ps.setLong(34, postFellingSurveyCard.getPostFellingSurveyID());
		ps.setString(35, postFellingSurveyCard.getRecorderID());
			
		
		if (!ignoreDuplicate)
		{
			nullable(ps, 36, postFellingSurveyCard.getLatitude());
			nullable(ps, 37, postFellingSurveyCard.getLongitude());
			nullable(ps, 38, postFellingSurveyCard.getForestTypeID());
			nullable(ps, 39, postFellingSurveyCard.getSoilStatusID());
			nullable(ps, 40, postFellingSurveyCard.getAspectID());
			ps.setInt(41, postFellingSurveyCard.getSlope());
			nullable(ps, 42, postFellingSurveyCard.getSlopeLocationID());
			nullable(ps, 43, postFellingSurveyCard.getElevationID());
			ps.setInt(44, postFellingSurveyCard.getBertam());
			ps.setInt(45, postFellingSurveyCard.getBamboo());
			ps.setInt(46, postFellingSurveyCard.getPalm());
			nullable(ps, 47, postFellingSurveyCard.getResamID());
			nullable(ps, 48, postFellingSurveyCard.getGingerID());
			nullable(ps, 49, postFellingSurveyCard.getBananaID());
			ps.setDate(50, toSQLDate(postFellingSurveyCard.getSurveyDate()));
			nullable(ps, 51, postFellingSurveyCard.getInspectionDate());
			ps.setString(52, postFellingSurveyCard.getInspectorID());
			nullable(ps, 53, postFellingSurveyCard.getInspectionForestTypeID());
			nullable(ps, 54, postFellingSurveyCard.getInspectionSoilStatusID());
			nullable(ps, 55, postFellingSurveyCard.getInspectionAspectID());
			nullable(ps, 56, postFellingSurveyCard.getInspectionSlope());
			nullable(ps, 57, postFellingSurveyCard.getInspectionSlopeLocationID());
			nullable(ps, 58, postFellingSurveyCard.getInspectionElevationID());
			nullable(ps, 59, postFellingSurveyCard.getInspectionBertam());
			nullable(ps, 60, postFellingSurveyCard.getInspectionBamboo());
			nullable(ps, 61, postFellingSurveyCard.getInspectionPalm());
			nullable(ps, 62, postFellingSurveyCard.getInspectionResamID());
			nullable(ps, 63, postFellingSurveyCard.getInspectionGingerID());
			nullable(ps, 64, postFellingSurveyCard.getInspectionBananaID());
			nullable(ps, 65, toInt(postFellingSurveyCard.isInspection()));
		}

		int status = ps.executeUpdate();
		
		if (status == 0 && !ignoreDuplicate)
			status = 1;
		
		return status;
	}

	int updatePostFellingSurveyCard(PostFellingSurveyCard postFellingSurveyCard) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE PostFellingSurveyCard SET LineNo = ?, PlotNo = ?, Latitude = ?, Longitude = ?, ForestTypeID = ?, SoilStatusID = ?, AspectID = ?, Slope = ?, SlopeLocationID = ?, ElevationID = ?, Bertam = ?, Bamboo = ?, Palm = ?, ResamID = ?, GingerID = ?, BananaID = ?, SurveyDate = ?, InspectionDate = ?, InspectorID = ?, InspectionForestTypeID = ?, InspectionSoilStatusID = ?, InspectionAspectID = ?, InspectionSlope = ?, InspectionSlopeLocationID = ?, InspectionElevationID = ?, InspectionBertam = ?, InspectionBamboo = ?, InspectionPalm = ?, InspectionResamID = ?, InspectionGingerID = ?, InspectionBananaID = ?, Inspection = ? WHERE PostFellingSurveyCardID = ?");

		write(postFellingSurveyCard, ps);
		

		return ps.executeUpdate();
	}
	
	int deletePostFellingSurveyCard(PostFellingSurveyCard postFellingSurveyCard) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("DELETE FROM PostFellingSurveyCard WHERE PostFellingSurveyCardID = ?");

		ps.setLong(1, postFellingSurveyCard.getPostFellingSurveyCardID());
		
		return ps.executeUpdate();
		
	}
	
	
	
	int updatePostFellingSurveyCardInspection(PostFellingSurveyCard postFellingSurveyCard) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE PostFellingSurveyCard SET InspectionForestTypeID = ?, InspectionSoilStatusID = ?, InspectionAspectID = ?, InspectionSlope = ?, InspectionSlopeLocationID = ?, InspectionElevationID = ?, InspectionBertam = ?, InspectionBamboo = ?, InspectionPalm = ?, InspectionResamID = ?, InspectionGingerID = ?, InspectionBananaID = ?, InspectionDate = ?, InspectorID = ?, Inspection = ? WHERE PostFellingSurveyCardID = ?");

		nullable(ps, 1, postFellingSurveyCard.getInspectionForestTypeID());
		nullable(ps, 2, postFellingSurveyCard.getInspectionSoilStatusID());
		nullable(ps, 3, postFellingSurveyCard.getInspectionAspectID());
		ps.setInt(4, postFellingSurveyCard.getInspectionSlope());
		nullable(ps, 5, postFellingSurveyCard.getInspectionSlopeLocationID());
		nullable(ps, 6, postFellingSurveyCard.getInspectionElevationID());
		ps.setInt(7, postFellingSurveyCard.getInspectionBertam());
		ps.setInt(8, postFellingSurveyCard.getInspectionBamboo());
		ps.setInt(9, postFellingSurveyCard.getInspectionPalm());
		nullable(ps, 10, postFellingSurveyCard.getInspectionResamID());
		nullable(ps, 11, postFellingSurveyCard.getInspectionGingerID());
		nullable(ps, 12, postFellingSurveyCard.getInspectionBananaID());
		nullable(ps, 13, postFellingSurveyCard.getInspectionDate());
		ps.setString(14, postFellingSurveyCard.getInspectorID());
		ps.setInt(15, toInt(postFellingSurveyCard.isInspection()));
		ps.setLong(16, postFellingSurveyCard.getPostFellingSurveyCardID());	
		
		
		
		return ps.executeUpdate();
	}

	private PostFellingSurveyCard read(ResultSet rs) throws SQLException
	{
		PostFellingSurveyCard postFellingSurveyCard = new PostFellingSurveyCard();

		postFellingSurveyCard.setPostFellingSurveyCardID(rs.getLong("PostFellingSurveyCardID"));
		postFellingSurveyCard.setLineNo(rs.getString("LineNo"));
		postFellingSurveyCard.setPlotNo(rs.getString("PlotNo"));
		postFellingSurveyCard.setLatitude(rs.getDouble("Latitude"));
		postFellingSurveyCard.setLongitude(rs.getDouble("Longitude"));
		postFellingSurveyCard.setForestTypeID(rs.getInt("ForestTypeID"));
		postFellingSurveyCard.setSoilStatusID(rs.getInt("SoilStatusID"));
		postFellingSurveyCard.setAspectID(rs.getInt("AspectID"));
		postFellingSurveyCard.setSlope(rs.getInt("Slope"));
		postFellingSurveyCard.setSlopeLocationID(rs.getInt("SlopeLocationID"));
		postFellingSurveyCard.setElevationID(rs.getInt("ElevationID"));
		postFellingSurveyCard.setBertam(rs.getInt("Bertam"));
		postFellingSurveyCard.setBamboo(rs.getInt("Bamboo"));
		postFellingSurveyCard.setPalm(rs.getInt("Palm"));
		postFellingSurveyCard.setResamID(rs.getInt("ResamID"));
		postFellingSurveyCard.setGingerID(rs.getInt("GingerID"));
		postFellingSurveyCard.setBananaID(rs.getInt("BananaID"));
		postFellingSurveyCard.setSurveyDate(rs.getDate("SurveyDate"));
		postFellingSurveyCard.setInspectionDate(rs.getDate("InspectionDate"));
		postFellingSurveyCard.setPostFellingSurveyID(rs.getLong("PostFellingSurveyID"));
		postFellingSurveyCard.setRecorderID(rs.getString("RecorderID"));
		postFellingSurveyCard.setInspectorID(rs.getString("InspectorID"));
		postFellingSurveyCard.setForestType(rs.getString("ForestType"));
		postFellingSurveyCard.setSoilStatus(rs.getString("SoilStatus"));
		postFellingSurveyCard.setAspect(rs.getString("Aspect"));
		postFellingSurveyCard.setSlopeLocation(rs.getString("SlopeLocation"));
		postFellingSurveyCard.setElevation(rs.getString("Elevation"));
		postFellingSurveyCard.setResam(rs.getString("Resam"));
		postFellingSurveyCard.setGinger(rs.getString("Ginger"));
		postFellingSurveyCard.setBanana(rs.getString("Banana"));
		postFellingSurveyCard.setRecorderName(rs.getString("RecorderName"));
		postFellingSurveyCard.setInspectorName(rs.getString("InspectorName"));
		postFellingSurveyCard.setPostFellingSurveyRecords(facade.getPostFellingSurveyRecords(postFellingSurveyCard));
		postFellingSurveyCard.setInspectionForestTypeID(rs.getInt("InspectionForestTypeID"));
		postFellingSurveyCard.setInspectionSoilStatusID(rs.getInt("InspectionSoilStatusID"));
		postFellingSurveyCard.setInspectionAspectID(rs.getInt("InspectionAspectID"));
		postFellingSurveyCard.setInspectionSlope(rs.getInt("InspectionSlope"));
		postFellingSurveyCard.setInspectionSlopeLocationID(rs.getInt("InspectionSlopeLocationID"));
		postFellingSurveyCard.setInspectionElevationID(rs.getInt("InspectionElevationID"));
		postFellingSurveyCard.setInspectionBertam(rs.getInt("InspectionBertam"));
		postFellingSurveyCard.setInspectionBamboo(rs.getInt("InspectionBamboo"));
		postFellingSurveyCard.setInspectionPalm(rs.getInt("InspectionPalm"));
		postFellingSurveyCard.setInspectionResamID(rs.getInt("InspectionResamID"));
		postFellingSurveyCard.setInspectionGingerID(rs.getInt("InspectionGingerID"));
		postFellingSurveyCard.setInspectionBananaID(rs.getInt("InspectionBananaID"));
		postFellingSurveyCard.setInspectionForestType(rs.getString("InspectionForestType"));
		postFellingSurveyCard.setInspectionSoilStatus(rs.getString("InspectionSoilStatus"));
		postFellingSurveyCard.setInspectionAspect(rs.getString("InspectionAspect"));
		postFellingSurveyCard.setInspectionSlopeLocation(rs.getString("InspectionSlopeLocation"));
		postFellingSurveyCard.setInspectionElevation(rs.getString("InspectionElevation"));
		postFellingSurveyCard.setInspectionResam(rs.getString("InspectionResam"));
		postFellingSurveyCard.setInspectionGinger(rs.getString("InspectionGinger"));
		postFellingSurveyCard.setInspectionBanana(rs.getString("InspectionBanana"));
		postFellingSurveyCard.setInspection(toBoolean(rs.getInt("Inspection")));
		return postFellingSurveyCard;
	}
	
	private PostFellingSurveyCard readForInspection(ResultSet rs) throws SQLException
	{
		PostFellingSurveyCard postFellingSurveyCard = new PostFellingSurveyCard();

		postFellingSurveyCard.setPostFellingSurveyCardID(rs.getLong("PostFellingSurveyCardID"));
		postFellingSurveyCard.setLineNo(rs.getString("LineNo"));
		postFellingSurveyCard.setPlotNo(rs.getString("PlotNo"));
		postFellingSurveyCard.setLatitude(rs.getDouble("Latitude"));
		postFellingSurveyCard.setLongitude(rs.getDouble("Longitude"));
		postFellingSurveyCard.setForestTypeID(rs.getInt("ForestTypeID"));
		postFellingSurveyCard.setSoilStatusID(rs.getInt("SoilStatusID"));
		postFellingSurveyCard.setAspectID(rs.getInt("AspectID"));
		postFellingSurveyCard.setSlope(rs.getInt("Slope"));
		postFellingSurveyCard.setSlopeLocationID(rs.getInt("SlopeLocationID"));
		postFellingSurveyCard.setElevationID(rs.getInt("ElevationID"));
		postFellingSurveyCard.setBertam(rs.getInt("Bertam"));
		postFellingSurveyCard.setBamboo(rs.getInt("Bamboo"));
		postFellingSurveyCard.setPalm(rs.getInt("Palm"));
		postFellingSurveyCard.setResamID(rs.getInt("ResamID"));
		postFellingSurveyCard.setGingerID(rs.getInt("GingerID"));
		postFellingSurveyCard.setBananaID(rs.getInt("BananaID"));
		postFellingSurveyCard.setSurveyDate(rs.getDate("SurveyDate"));
		postFellingSurveyCard.setInspectionDate(rs.getDate("InspectionDate"));
		postFellingSurveyCard.setPostFellingSurveyID(rs.getLong("PostFellingSurveyID"));
		postFellingSurveyCard.setRecorderID(rs.getString("RecorderID"));
		postFellingSurveyCard.setInspectorID(rs.getString("InspectorID"));
		postFellingSurveyCard.setForestType(rs.getString("ForestType"));
		postFellingSurveyCard.setSoilStatus(rs.getString("SoilStatus"));
		postFellingSurveyCard.setAspect(rs.getString("Aspect"));
		postFellingSurveyCard.setSlopeLocation(rs.getString("SlopeLocation"));
		postFellingSurveyCard.setElevation(rs.getString("Elevation"));
		postFellingSurveyCard.setResam(rs.getString("Resam"));
		postFellingSurveyCard.setGinger(rs.getString("Ginger"));
		postFellingSurveyCard.setBanana(rs.getString("Banana"));
		postFellingSurveyCard.setRecorderName(rs.getString("RecorderName"));
		postFellingSurveyCard.setInspectorName(rs.getString("InspectorName"));
		postFellingSurveyCard.setPostFellingSurveyRecords(facade.getPostFellingSurveyRecordsForInspection(postFellingSurveyCard));
		postFellingSurveyCard.setInspectionForestTypeID(rs.getInt("InspectionForestTypeID"));
		postFellingSurveyCard.setInspectionSoilStatusID(rs.getInt("InspectionSoilStatusID"));
		postFellingSurveyCard.setInspectionAspectID(rs.getInt("InspectionAspectID"));
		postFellingSurveyCard.setInspectionSlope(rs.getInt("InspectionSlope"));
		postFellingSurveyCard.setInspectionSlopeLocationID(rs.getInt("InspectionSlopeLocationID"));
		postFellingSurveyCard.setInspectionElevationID(rs.getInt("InspectionElevationID"));
		postFellingSurveyCard.setInspectionBertam(rs.getInt("InspectionBertam"));
		postFellingSurveyCard.setInspectionBamboo(rs.getInt("InspectionBamboo"));
		postFellingSurveyCard.setInspectionPalm(rs.getInt("InspectionPalm"));
		postFellingSurveyCard.setInspectionResamID(rs.getInt("InspectionResamID"));
		postFellingSurveyCard.setInspectionGingerID(rs.getInt("InspectionGingerID"));
		postFellingSurveyCard.setInspectionBananaID(rs.getInt("InspectionBananaID"));
		postFellingSurveyCard.setInspectionForestType(rs.getString("InspectionForestType"));
		postFellingSurveyCard.setInspectionSoilStatus(rs.getString("InspectionSoilStatus"));
		postFellingSurveyCard.setInspectionAspect(rs.getString("InspectionAspect"));
		postFellingSurveyCard.setInspectionSlopeLocation(rs.getString("InspectionSlopeLocation"));
		postFellingSurveyCard.setInspectionElevation(rs.getString("InspectionElevation"));
		postFellingSurveyCard.setInspectionResam(rs.getString("InspectionResam"));
		postFellingSurveyCard.setInspectionGinger(rs.getString("InspectionGinger"));
		postFellingSurveyCard.setInspectionBanana(rs.getString("InspectionBanana"));
		postFellingSurveyCard.setInspection(toBoolean(rs.getInt("Inspection")));
		return postFellingSurveyCard;
	}

	private ArrayList<PostFellingSurveyCard> getPostFellingSurveyCards(ResultSet rs) throws SQLException
	{
		ArrayList<PostFellingSurveyCard> postFellingSurveyCards = new ArrayList<>();

		while (rs.next())
			postFellingSurveyCards.add(read(rs));

		return postFellingSurveyCards;
	}

	ArrayList<PostFellingSurveyCard> getPostFellingSurveyCards(PostFellingSurvey postFellingSurvey) throws SQLException
	{
		//PreparedStatement ps = facade.prepareStatement("SELECT a.*, b.Name AS InspectorName FROM (SELECT c.*, ft.Code AS ForestType, ss.Code AS SoilStatus, asp.Code AS Aspect, sl.Code AS SlopeLocation, e.Code AS Elevation, r.Code AS Resam, gin.Code AS Ginger, ban.Code AS Banana, s.Name AS RecorderName, ift.Code AS InspectionForestType, iss.Code AS InspectionSoilStatus, iasp.Code AS InspectionAspect, isl.Code AS InspectionSlopeLocation, ie.Code AS InspectionElevation, ir.Code AS InspectionResam, igin.Code AS InspectionGinger, iban.Code AS InspectionBanana FROM PostFellingSurveyCard c, ForestType ft, SoilStatus ss, Aspect asp, SlopeLocation sl, Elevation e, Resam r, Ginger gin, Banana ban, ForestType ift, SoilStatus iss, Aspect iasp, SlopeLocation isl, Elevation ie, Resam ir, Ginger igin, Banana iban, Staff s WHERE c.PostFellingSurveyID =  ? AND c.ForestTypeID = ft.ForestTypeID AND c.SoilStatusID = ss.SoilStatusID AND c.AspectID = asp.AspectID AND c.SlopeLocationID = sl.SlopeLocationID AND c.ElevationID = e.ElevationID AND c.ResamID = r.ResamID AND c.GingerID = gin.GingerID AND c.BananaID = ban.BananaID AND c.RecorderID = s.StaffID AND c.InspectionForestTypeID = ift.ForestTypeID AND c. InspectionSoilStatusID = iss.SoilStatusID AND c. InspectionAspectID = iasp.AspectID AND c. InspectionSlopeLocationID = isl.SlopeLocationID AND c. InspectionElevationID = ie.ElevationID AND c. InspectionResamID = ir.ResamID AND c. InspectionGingerID = igin.GingerID AND c. InspectionBananaID = iban.BananaID ORDER BY c.LineNo, c.PlotNo, c.PostFellingSurveyCardID) AS a LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS b ON a.InspectorID = b.StaffID");
		PreparedStatement ps = facade.prepareStatement("SELECT a.*, ft.Code AS ForestType, ss.Code AS SoilStatus, asp.Code AS Aspect, sl.Code AS SlopeLocation, e.Code AS Elevation, r.Code AS Resam, gin.Code AS Ginger, ban.Code AS Banana, s.Name AS RecorderName, b.Name AS InspectorName, ift.Code AS InspectionForestType, iss.Code AS InspectionSoilStatus, iasp.Code AS InspectionAspect, isl.Code AS InspectionSlopeLocation, ie.Code AS InspectionElevation, ir.Code AS InspectionResam, igin.Code AS InspectionGinger, iban.Code AS InspectionBanana FROM (SELECT c.* FROM PostFellingSurveyCard c WHERE c.PostFellingSurveyID = ? ORDER BY c.LineNo, c.PlotNo, c.PostFellingSurveyCardID) as a LEFT OUTER JOIN (SELECT ForestTypeID , Code FROM ForestType) AS ft ON a.ForestTypeID = ft.ForestTypeID LEFT OUTER JOIN (SELECT SoilStatusID, Code FROM SoilStatus) AS ss ON a.SoilStatusID = ss.SoilStatusID " + 
				"LEFT OUTER JOIN (SELECT AspectID, Code FROM Aspect) AS asp ON a.AspectID = asp.AspectID LEFT OUTER JOIN (SELECT SlopeLocationID, Code FROM SlopeLocation) AS sl ON a. SlopeLocationID = sl.SlopeLocationID LEFT OUTER JOIN (SELECT ElevationID, Code FROM Elevation) AS e ON a.ElevationID = e.ElevationID LEFT OUTER JOIN (SELECT ResamID, Code FROM Resam) AS r ON a.ResamID = r.ResamID LEFT OUTER JOIN (SELECT GingerID, Code FROM Ginger) AS gin ON a.GingerID = gin.GingerID LEFT OUTER JOIN (SELECT BananaID, Code FROM Banana) AS ban ON a.BananaID = ban.BananaID LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS s ON a.RecorderID = s.StaffID LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS b ON a.InspectorID = b.StaffID LEFT OUTER JOIN (SELECT ForestTypeID , Code FROM ForestType) AS ift ON a.InspectionForestTypeID = ift.ForestTypeID LEFT OUTER JOIN (SELECT SoilStatusID, Code FROM SoilStatus) AS iss ON a.InspectionSoilStatusID = iss.SoilStatusID LEFT OUTER JOIN (SELECT AspectID, Code FROM Aspect) AS iasp ON a.InspectionAspectID = iasp.AspectID LEFT OUTER JOIN (SELECT SlopeLocationID, Code FROM SlopeLocation) AS isl ON a.InspectionSlopeLocationID = isl.SlopeLocationID LEFT OUTER JOIN (SELECT ElevationID, Code FROM Elevation) AS ie ON a.InspectionElevationID = ie.ElevationID " + 
				"LEFT OUTER JOIN (SELECT ResamID, Code FROM Resam) AS ir ON a.InspectionResamID = ir.ResamID LEFT OUTER JOIN (SELECT GingerID, Code FROM Ginger) AS igin ON a.InspectionGingerID = igin.GingerID LEFT OUTER JOIN (SELECT BananaID, Code FROM Banana) AS iban ON a.InspectionBananaID = iban.BananaID");
		ps.setLong(1, postFellingSurvey.getPostFellingSurveyID());	
		
		
		return getPostFellingSurveyCards(ps.executeQuery());
	}
	
	ArrayList<PostFellingSurveyCard> getPostFellingInspectionCards(PostFellingSurvey postFellingSurvey) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT a.*, ft.Code AS ForestType, ss.Code AS SoilStatus, asp.Code AS Aspect, sl.Code AS SlopeLocation, e.Code AS Elevation, r.Code AS Resam, gin.Code AS Ginger, ban.Code AS Banana, s.Name AS RecorderName, b.Name AS InspectorName, ift.Code AS InspectionForestType, iss.Code AS InspectionSoilStatus, iasp.Code AS InspectionAspect, isl.Code AS InspectionSlopeLocation, ie.Code AS InspectionElevation, ir.Code AS InspectionResam, igin.Code AS InspectionGinger, iban.Code AS InspectionBanana FROM (SELECT c.* FROM PostFellingSurveyCard c WHERE c.PostFellingSurveyID = ? AND c.Inspection = 1 ORDER BY c.LineNo, c.PlotNo, c.PostFellingSurveyCardID) as a LEFT OUTER JOIN (SELECT ForestTypeID , Code FROM ForestType) AS ft ON a.ForestTypeID = ft.ForestTypeID LEFT OUTER JOIN (SELECT SoilStatusID, Code FROM SoilStatus) AS ss ON a.SoilStatusID = ss.SoilStatusID " + 
				"LEFT OUTER JOIN (SELECT AspectID, Code FROM Aspect) AS asp ON a.AspectID = asp.AspectID LEFT OUTER JOIN (SELECT SlopeLocationID, Code FROM SlopeLocation) AS sl ON a. SlopeLocationID = sl.SlopeLocationID LEFT OUTER JOIN (SELECT ElevationID, Code FROM Elevation) AS e ON a.ElevationID = e.ElevationID LEFT OUTER JOIN (SELECT ResamID, Code FROM Resam) AS r ON a.ResamID = r.ResamID LEFT OUTER JOIN (SELECT GingerID, Code FROM Ginger) AS gin ON a.GingerID = gin.GingerID LEFT OUTER JOIN (SELECT BananaID, Code FROM Banana) AS ban ON a.BananaID = ban.BananaID LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS s ON a.RecorderID = s.StaffID LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS b ON a.InspectorID = b.StaffID LEFT OUTER JOIN (SELECT ForestTypeID , Code FROM ForestType) AS ift ON a.InspectionForestTypeID = ift.ForestTypeID LEFT OUTER JOIN (SELECT SoilStatusID, Code FROM SoilStatus) AS iss ON a.InspectionSoilStatusID = iss.SoilStatusID LEFT OUTER JOIN (SELECT AspectID, Code FROM Aspect) AS iasp ON a.InspectionAspectID = iasp.AspectID LEFT OUTER JOIN (SELECT SlopeLocationID, Code FROM SlopeLocation) AS isl ON a.InspectionSlopeLocationID = isl.SlopeLocationID LEFT OUTER JOIN (SELECT ElevationID, Code FROM Elevation) AS ie ON a.InspectionElevationID = ie.ElevationID \r\n" + 
				"LEFT OUTER JOIN (SELECT ResamID, Code FROM Resam) AS ir ON a.InspectionResamID = ir.ResamID LEFT OUTER JOIN (SELECT GingerID, Code FROM Ginger) AS igin ON a.InspectionGingerID = igin.GingerID LEFT OUTER JOIN (SELECT BananaID, Code FROM Banana) AS iban ON a.InspectionBananaID = iban.BananaID");
		
		ps.setLong(1, postFellingSurvey.getPostFellingSurveyID());
		ArrayList<PostFellingSurveyCard> postFellingSurveyCards = new ArrayList<>();
		ResultSet rs = ps.executeQuery();
		while (rs.next())
			postFellingSurveyCards.add(readForInspection(rs));

		return postFellingSurveyCards;
	}
	
	PostFellingSurveyCard getPostFellingSurveyCard(long postFellingSurveyCardID) throws SQLException
	{
		PostFellingSurveyCard postFellingSurveyCard = new PostFellingSurveyCard();
		//PreparedStatement ps = facade.prepareStatement("SELECT a.*, b.Name AS InspectorName FROM (SELECT c.*, ft.Code AS ForestType, ss.Code AS SoilStatus, asp.Code AS Aspect, sl.Code AS SlopeLocation, e.Code AS Elevation, r.Code AS Resam, gin.Code AS Ginger, ban.Code AS Banana, s.Name AS RecorderName, ift.Code AS InspectionForestType, iss.Code AS InspectionSoilStatus, iasp.Code AS InspectionAspect, isl.Code AS InspectionSlopeLocation, ie.Code AS InspectionElevation, ir.Code AS InspectionResam, igin.Code AS InspectionGinger, iban.Code AS InspectionBanana FROM PostFellingSurveyCard c, ForestType ft, SoilStatus ss, Aspect asp, SlopeLocation sl, Elevation e, Resam r, Ginger gin, Banana ban, ForestType ift, SoilStatus iss, Aspect iasp, SlopeLocation isl, Elevation ie, Resam ir, Ginger igin, Banana iban, Staff s WHERE c.PostFellingSurveyCardID =  ? AND c.ForestTypeID = ft.ForestTypeID AND c.SoilStatusID = ss.SoilStatusID AND c.AspectID = asp.AspectID AND c.SlopeLocationID = sl.SlopeLocationID AND c.ElevationID = e.ElevationID AND c.ResamID = r.ResamID AND c.GingerID = gin.GingerID AND c.BananaID = ban.BananaID AND c.RecorderID = s.StaffID AND c.InspectionForestTypeID = ift.ForestTypeID AND c. InspectionSoilStatusID = iss.SoilStatusID AND c. InspectionAspectID = iasp.AspectID AND c. InspectionSlopeLocationID = isl.SlopeLocationID AND c. InspectionElevationID = ie.ElevationID AND c. InspectionResamID = ir.ResamID AND c. InspectionGingerID = igin.GingerID AND c. InspectionBananaID = iban.BananaID ORDER BY c.LineNo, c.PlotNo, c.PostFellingSurveyCardID) AS a LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS b ON a.InspectorID = b.StaffID");
		PreparedStatement ps = facade.prepareStatement("SELECT a.*, ft.Code AS ForestType, ss.Code AS SoilStatus, asp.Code AS Aspect, sl.Code AS SlopeLocation, e.Code AS Elevation, r.Code AS Resam, gin.Code AS Ginger, ban.Code AS Banana, s.Name AS RecorderName, b.Name AS InspectorName, ift.Code AS InspectionForestType, iss.Code AS InspectionSoilStatus, iasp.Code AS InspectionAspect, isl.Code AS InspectionSlopeLocation, ie.Code AS InspectionElevation, ir.Code AS InspectionResam, igin.Code AS InspectionGinger, iban.Code AS InspectionBanana FROM (SELECT c.* FROM PostFellingSurveyCard c WHERE c.PostFellingSurveyCardID = ? ORDER BY c.LineNo, c.PlotNo, c.PostFellingSurveyCardID) as a LEFT OUTER JOIN (SELECT ForestTypeID , Code FROM ForestType) AS ft ON a.ForestTypeID = ft.ForestTypeID LEFT OUTER JOIN (SELECT SoilStatusID, Code FROM SoilStatus) AS ss ON a.SoilStatusID = ss.SoilStatusID \r\n" + 
				"LEFT OUTER JOIN (SELECT AspectID, Code FROM Aspect) AS asp ON a.AspectID = asp.AspectID LEFT OUTER JOIN (SELECT SlopeLocationID, Code FROM SlopeLocation) AS sl ON a. SlopeLocationID = sl.SlopeLocationID LEFT OUTER JOIN (SELECT ElevationID, Code FROM Elevation) AS e ON a.ElevationID = e.ElevationID LEFT OUTER JOIN (SELECT ResamID, Code FROM Resam) AS r ON a.ResamID = r.ResamID LEFT OUTER JOIN (SELECT GingerID, Code FROM Ginger) AS gin ON a.GingerID = gin.GingerID LEFT OUTER JOIN (SELECT BananaID, Code FROM Banana) AS ban ON a.BananaID = ban.BananaID LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS s ON a.RecorderID = s.StaffID LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS b ON a.InspectorID = b.StaffID LEFT OUTER JOIN (SELECT ForestTypeID , Code FROM ForestType) AS ift ON a.InspectionForestTypeID = ift.ForestTypeID LEFT OUTER JOIN (SELECT SoilStatusID, Code FROM SoilStatus) AS iss ON a.InspectionSoilStatusID = iss.SoilStatusID LEFT OUTER JOIN (SELECT AspectID, Code FROM Aspect) AS iasp ON a.InspectionAspectID = iasp.AspectID LEFT OUTER JOIN (SELECT SlopeLocationID, Code FROM SlopeLocation) AS isl ON a.InspectionSlopeLocationID = isl.SlopeLocationID LEFT OUTER JOIN (SELECT ElevationID, Code FROM Elevation) AS ie ON a.InspectionElevationID = ie.ElevationID \r\n" + 
				"LEFT OUTER JOIN (SELECT ResamID, Code FROM Resam) AS ir ON a.InspectionResamID = ir.ResamID LEFT OUTER JOIN (SELECT GingerID, Code FROM Ginger) AS igin ON a.InspectionGingerID = igin.GingerID LEFT OUTER JOIN (SELECT BananaID, Code FROM Banana) AS iban ON a.InspectionBananaID = iban.BananaID");
		ps.setLong(1, postFellingSurveyCardID);
		ResultSet rs = ps.executeQuery();
		while (rs.next())
			postFellingSurveyCard = read(rs);	
		return postFellingSurveyCard;
	}
	
	
}