package my.edu.utem.ftmk.fis9.prefelling.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingSurvey;
import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingCuttingOption;

/**
 * @author Satrya Fajri Pratama
 */
class CuttingOptionRecommendationManager extends PreFellingTableManager
{
	CuttingOptionRecommendationManager(PreFellingFacade facade)
	{
		super(facade);
	}

	int addPreFellingCuttingOption(PreFellingCuttingOption preFellingCuttingOption) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO CuttingOptionRecommendation VALUES (?, ?, ?)");

		ps.setInt(1, preFellingCuttingOption.getRank());
		ps.setLong(2, preFellingCuttingOption.getPreFellingSurveyID());
		ps.setInt(3, preFellingCuttingOption.getCuttingOptionID());

		return ps.executeUpdate();
	}

	int deleteCuttingOptionRecommendations(PreFellingSurvey preFellingSurvey) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("DELETE FROM CuttingOptionRecommendation WHERE PreFellingSurveyID = ?");

		ps.setLong(1, preFellingSurvey.getPreFellingSurveyID());

		return ps.executeUpdate();
	}

	ArrayList<Integer> getCuttingOptionRecommendations(PreFellingSurvey preFellingSurvey) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT CuttingOptionID FROM CuttingOptionRecommendation WHERE PreFellingSurveyID = ? ORDER BY Rank");

		ps.setLong(1, preFellingSurvey.getPreFellingSurveyID());

		ArrayList<Integer> recommendations = new ArrayList<>();
		ResultSet rs = ps.executeQuery();

		while (rs.next())
			recommendations.add(rs.getInt(1));

		return recommendations;
	}
}