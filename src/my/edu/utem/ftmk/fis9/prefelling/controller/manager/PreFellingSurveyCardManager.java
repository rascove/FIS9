package my.edu.utem.ftmk.fis9.prefelling.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingSurveyCard;
import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingSurvey;

/**
 * @author Satrya Fajri Pratama
 */
class PreFellingSurveyCardManager extends PreFellingTableManager
{
	PreFellingSurveyCardManager(PreFellingFacade facade)
	{
		super(facade);
	}

	private void write(PreFellingSurveyCard preFellingSurveyCard, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, standardize(preFellingSurveyCard.getLineNo()));
		ps.setString(2, standardize(preFellingSurveyCard.getPlotNo()));
		ps.setString(3, standardize(preFellingSurveyCard.getSubBaseNo()));
		nullable(ps, 4, preFellingSurveyCard.getLatitude());
		nullable(ps, 5, preFellingSurveyCard.getLongitude());
		nullable(ps, 6, preFellingSurveyCard.getForestTypeID());
		nullable(ps, 7, preFellingSurveyCard.getSoilTypeID());
		ps.setString(8, preFellingSurveyCard.getGeology());
		nullable(ps, 9, preFellingSurveyCard.getAreaStatusID());
		nullable(ps, 10, preFellingSurveyCard.getSoilStatusID());
		nullable(ps, 11, preFellingSurveyCard.getAspectID());
		nullable(ps, 12, preFellingSurveyCard.getSlope());
		nullable(ps, 13, preFellingSurveyCard.getSlopeLocationID());
		nullable(ps, 14, preFellingSurveyCard.getElevationID());
		nullable(ps, 15, preFellingSurveyCard.getBertam());
		nullable(ps, 16, preFellingSurveyCard.getPalm());
		nullable(ps, 17, preFellingSurveyCard.getResamID());
		nullable(ps, 18, preFellingSurveyCard.getRattanA());
		nullable(ps, 19, preFellingSurveyCard.getRattanB());
		nullable(ps, 20, preFellingSurveyCard.getRattanC());
		nullable(ps, 21, preFellingSurveyCard.getRattanD());
		nullable(ps, 22, preFellingSurveyCard.getRattanE());
		nullable(ps, 23, preFellingSurveyCard.getRattanF());
		nullable(ps, 24, preFellingSurveyCard.getRattanG());
		nullable(ps, 25, preFellingSurveyCard.getBambooA());
		nullable(ps, 26, preFellingSurveyCard.getBambooB());
		nullable(ps, 27, preFellingSurveyCard.getBambooC());
		ps.setDate(28, toSQLDate(preFellingSurveyCard.getSurveyDate()));
		nullable(ps, 29, preFellingSurveyCard.getInspectionDate());
		ps.setString(30, preFellingSurveyCard.getInspectorID());
		ps.setLong(31, preFellingSurveyCard.getPreFellingSurveyCardID());
	}

	int addPreFellingSurveyCard(PreFellingSurveyCard preFellingSurveyCard, boolean ignoreDuplicate) throws SQLException
	{
		String sql = (ignoreDuplicate ? "INSERT IGNORE" : "INSERT") + " INTO PreFellingSurveyCard (LineNo, PlotNo, SubBaseNo, Latitude, Longitude, ForestTypeID, SoilTypeID, Geology, AreaStatusID, SoilStatusID, AspectID, Slope, SlopeLocationID, ElevationID, Bertam, Palm, ResamID, RattanA, RattanB, RattanC, RattanD, RattanE, RattanF, RattanG, BambooA, BambooB, BambooC, SurveyDate, InspectionDate, InspectorID, PreFellingSurveyCardID, PreFellingSurveyID, RecorderID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" + (ignoreDuplicate ? "" : " ON DUPLICATE KEY UPDATE Latitude = ?, Longitude = ?, ForestTypeID = ?, SoilTypeID = ?, Geology = ?, AreaStatusID = ?, SoilStatusID = ?, AspectID = ?, Slope = ?, SlopeLocationID = ?, ElevationID = ?, Bertam = ?, Palm = ?, ResamID = ?, RattanA = ?, RattanB = ?, RattanC = ?, RattanD = ?, RattanE = ?, RattanF = ?, RattanG = ?, BambooA = ?, BambooB = ?, BambooC = ?, SurveyDate = ?, InspectionDate = ?, InspectorID = ?");
		PreparedStatement ps = facade.prepareStatement(sql);

		write(preFellingSurveyCard, ps);
		ps.setLong(32, preFellingSurveyCard.getPreFellingSurveyID());
		ps.setString(33, preFellingSurveyCard.getRecorderID());

		if (!ignoreDuplicate)
		{
			nullable(ps, 34, preFellingSurveyCard.getLatitude());
			nullable(ps, 35, preFellingSurveyCard.getLongitude());
			nullable(ps, 36, preFellingSurveyCard.getForestTypeID());
			nullable(ps, 37, preFellingSurveyCard.getSoilTypeID());
			ps.setString(38, preFellingSurveyCard.getGeology());
			ps.setInt(39, preFellingSurveyCard.getAreaStatusID());
			ps.setInt(40, preFellingSurveyCard.getSoilStatusID());
			ps.setInt(41, preFellingSurveyCard.getAspectID());
			ps.setInt(42, preFellingSurveyCard.getSlope());
			ps.setInt(43, preFellingSurveyCard.getSlopeLocationID());
			ps.setInt(44, preFellingSurveyCard.getElevationID());
			ps.setInt(45, preFellingSurveyCard.getBertam());
			ps.setInt(46, preFellingSurveyCard.getPalm());
			ps.setInt(47, preFellingSurveyCard.getResamID());
			ps.setInt(48, preFellingSurveyCard.getRattanA());
			ps.setInt(49, preFellingSurveyCard.getRattanB());
			ps.setInt(50, preFellingSurveyCard.getRattanC());
			ps.setInt(51, preFellingSurveyCard.getRattanD());
			ps.setInt(52, preFellingSurveyCard.getRattanE());
			ps.setInt(53, preFellingSurveyCard.getRattanF());
			ps.setInt(54, preFellingSurveyCard.getRattanG());
			ps.setInt(55, preFellingSurveyCard.getBambooA());
			ps.setInt(56, preFellingSurveyCard.getBambooB());
			ps.setInt(57, preFellingSurveyCard.getBambooC());
			ps.setDate(58, toSQLDate(preFellingSurveyCard.getSurveyDate()));
			nullable(ps, 59, preFellingSurveyCard.getInspectionDate());
			ps.setString(60, preFellingSurveyCard.getInspectorID());
		}

		int status = ps.executeUpdate();
		
		if (status == 0 && !ignoreDuplicate)
			status = 1;
		
		return status;
	}

	int updatePreFellingSurveyCard(PreFellingSurveyCard preFellingSurveyCard) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE PreFellingSurveyCard SET LineNo = ?, PlotNo = ?, SubBaseNo = ?, Latitude = ?, Longitude = ?, ForestTypeID = ?, SoilTypeID = ?, Geology = ?, AreaStatusID = ?, SoilStatusID = ?, AspectID = ?, Slope = ?, SlopeLocationID = ?, ElevationID = ?, Bertam = ?, Palm = ?, ResamID = ?, RattanA = ?, RattanB = ?, RattanC = ?, RattanD = ?, RattanE = ?, RattanF = ?, RattanG = ?, BambooA = ?, BambooB = ?, BambooC = ?, SurveyDate = ?, InspectionDate = ?, InspectorID = ? WHERE PreFellingSurveyCardID = ?");

		write(preFellingSurveyCard, ps);

		return ps.executeUpdate();
	}

	private PreFellingSurveyCard read(ResultSet rs) throws SQLException
	{
		PreFellingSurveyCard preFellingSurveyCard = new PreFellingSurveyCard();

		preFellingSurveyCard.setPreFellingSurveyCardID(rs.getLong("PreFellingSurveyCardID"));
		preFellingSurveyCard.setLineNo(rs.getString("LineNo"));
		preFellingSurveyCard.setPlotNo(rs.getString("PlotNo"));
		preFellingSurveyCard.setSubBaseNo(rs.getString("SubBaseNo"));
		preFellingSurveyCard.setLatitude(rs.getDouble("Latitude"));
		preFellingSurveyCard.setLongitude(rs.getDouble("Longitude"));
		preFellingSurveyCard.setForestTypeID(rs.getInt("ForestTypeID"));
		preFellingSurveyCard.setSoilTypeID(rs.getInt("SoilTypeID"));
		preFellingSurveyCard.setGeology(rs.getString("Geology"));
		preFellingSurveyCard.setAreaStatusID(rs.getInt("AreaStatusID"));
		preFellingSurveyCard.setSoilStatusID(rs.getInt("SoilStatusID"));
		preFellingSurveyCard.setAspectID(rs.getInt("AspectID"));
		preFellingSurveyCard.setSlope(rs.getInt("Slope"));
		preFellingSurveyCard.setSlopeLocationID(rs.getInt("SlopeLocationID"));
		preFellingSurveyCard.setElevationID(rs.getInt("ElevationID"));
		preFellingSurveyCard.setBertam(rs.getInt("Bertam"));
		preFellingSurveyCard.setPalm(rs.getInt("Palm"));
		preFellingSurveyCard.setResamID(rs.getInt("ResamID"));
		preFellingSurveyCard.setRattanA(rs.getInt("RattanA"));
		preFellingSurveyCard.setRattanB(rs.getInt("RattanB"));
		preFellingSurveyCard.setRattanC(rs.getInt("RattanC"));
		preFellingSurveyCard.setRattanD(rs.getInt("RattanD"));
		preFellingSurveyCard.setRattanE(rs.getInt("RattanE"));
		preFellingSurveyCard.setRattanF(rs.getInt("RattanF"));
		preFellingSurveyCard.setRattanG(rs.getInt("RattanG"));
		preFellingSurveyCard.setBambooA(rs.getInt("BambooA"));
		preFellingSurveyCard.setBambooB(rs.getInt("BambooB"));
		preFellingSurveyCard.setBambooC(rs.getInt("BambooC"));
		preFellingSurveyCard.setSurveyDate(rs.getDate("SurveyDate"));
		preFellingSurveyCard.setInspectionDate(rs.getDate("InspectionDate"));
		preFellingSurveyCard.setPreFellingSurveyID(rs.getLong("PreFellingSurveyID"));
		preFellingSurveyCard.setRecorderID(rs.getString("RecorderID"));
		preFellingSurveyCard.setInspectorID(rs.getString("InspectorID"));
		preFellingSurveyCard.setForestType(rs.getString("ForestType"));
		preFellingSurveyCard.setSoilType(rs.getString("SoilType"));
		preFellingSurveyCard.setAreaStatus(rs.getString("AreaStatus"));
		preFellingSurveyCard.setSoilStatus(rs.getString("SoilStatus"));
		preFellingSurveyCard.setAspect(rs.getString("Aspect"));
		preFellingSurveyCard.setSlopeLocation(rs.getString("SlopeLocation"));
		preFellingSurveyCard.setElevation(rs.getString("Elevation"));
		preFellingSurveyCard.setResam(rs.getString("Resam"));
		preFellingSurveyCard.setRecorderName(rs.getString("RecorderName"));
		preFellingSurveyCard.setInspectorName(rs.getString("InspectorName"));
		preFellingSurveyCard.setPreFellingSurveyRecords(facade.getPreFellingSurveyRecords(preFellingSurveyCard));

		return preFellingSurveyCard;
	}

	private ArrayList<PreFellingSurveyCard> getPreFellingSurveyCards(ResultSet rs) throws SQLException
	{
		ArrayList<PreFellingSurveyCard> preFellingSurveyCards = new ArrayList<>();

		while (rs.next())
			preFellingSurveyCards.add(read(rs));

		return preFellingSurveyCards;
	}

	ArrayList<PreFellingSurveyCard> getPreFellingSurveyCards(PreFellingSurvey preFellingSurvey) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT c.*, ft.Code AS ForestType, st.Code AS SoilType, ast.Code AS AreaStatus, ss.Code AS SoilStatus, a.Code AS Aspect, sl.Code AS SlopeLocation, e.Code AS Elevation, r.Code AS Resam, s.Name AS RecorderName, s1.Name AS InspectorName FROM PreFellingSurveyCard c JOIN Staff s LEFT JOIN ForestType ft ON c.ForestTypeID = ft.ForestTypeID LEFT JOIN SoilType st ON c.SoilTypeID = st.SoilTypeID LEFT JOIN AreaStatus ast ON c.AreaStatusID = ast.AreaStatusID LEFT JOIN SoilStatus ss ON c.SoilStatusID = ss.SoilStatusID LEFT JOIN Aspect a ON c.AspectID = a.AspectID LEFT JOIN SlopeLocation sl ON c.SlopeLocationID = sl.SlopeLocationID LEFT JOIN Elevation e ON c.ElevationID = e.ElevationID LEFT JOIN Resam r ON c.ResamID = r.ResamID LEFT JOIN Staff s1 ON c.InspectorID = s1.StaffID WHERE c.PreFellingSurveyID = ? AND c.RecorderID = s.StaffID ORDER BY c.LineNo, c.PlotNo, c.PreFellingSurveyCardID");

		ps.setLong(1, preFellingSurvey.getPreFellingSurveyID());

		return getPreFellingSurveyCards(ps.executeQuery());
	}
}