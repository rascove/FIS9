package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.MainRevenueRoyaltyRate;
import my.edu.utem.ftmk.fis9.maintenance.model.State;

/**
 * @author Nor Azman Mat Ariff
 */
class MainRevenueRoyaltyRateManager extends MaintenanceTableManager
{
	MainRevenueRoyaltyRateManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(MainRevenueRoyaltyRate mainRevenueRoyaltyRate, PreparedStatement ps) throws SQLException
	{
		ps.setInt(1, mainRevenueRoyaltyRate.getStateID());
		ps.setInt(2, mainRevenueRoyaltyRate.getSpeciesID());
		ps.setBigDecimal(3, mainRevenueRoyaltyRate.getBigSizeRoyaltyRate());
		ps.setBigDecimal(4, mainRevenueRoyaltyRate.getSmallSizeRoyaltyRate());
		ps.setBigDecimal(5, mainRevenueRoyaltyRate.getCessRate());
		ps.setBigDecimal(6, mainRevenueRoyaltyRate.getJarasRoyaltyRate());
		ps.setBigDecimal(7, mainRevenueRoyaltyRate.getJarasCessRate());
		ps.setLong(8, mainRevenueRoyaltyRate.getMainRevenueRoyaltyRateID());
		
		return ps.executeUpdate();
	}

	int addMainRevenueRoyaltyRate(MainRevenueRoyaltyRate mainRevenueRoyaltyRate) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO MainRevenueRoyaltyRate (StateID, SpeciesID, BigSizeRoyaltyRate, SmallSizeRoyaltyRate, CessRate, JarasRoyaltyRate, JarasCessRate, StartDate, EndDate, Status, MainRevenueRoyaltyRateID) VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_DATE, NULL, 'A', ?) ON DUPLICATE KEY UPDATE EndDate = CURRENT_DATE, Status = NULL");
		int status = write(mainRevenueRoyaltyRate, ps);
		
		if (status == 2)
			status = ps.executeUpdate();
		
		return status;
	}
	
	int deleteMainRevenueRoyaltyRate(MainRevenueRoyaltyRate mainRevenueRoyaltyRate) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE MainRevenueRoyaltyRate SET EndDate = CURRENT_DATE, Status = NULL WHERE MainRevenueRoyaltyRateID = ?");
		ps.setLong(1, mainRevenueRoyaltyRate.getMainRevenueRoyaltyRateID());

		return ps.executeUpdate();
	}

	private MainRevenueRoyaltyRate read(ResultSet rs) throws SQLException
	{
		MainRevenueRoyaltyRate mainRevenueRoyaltyRate = new MainRevenueRoyaltyRate();

		mainRevenueRoyaltyRate.setMainRevenueRoyaltyRateID(rs.getLong("MainRevenueRoyaltyRateID"));
		mainRevenueRoyaltyRate.setStateID(rs.getInt("StateID"));
		mainRevenueRoyaltyRate.setStateCode(rs.getString("StateCode"));
		mainRevenueRoyaltyRate.setStateName(rs.getString("StateName"));
		mainRevenueRoyaltyRate.setSpeciesID(rs.getInt("SpeciesID"));
		mainRevenueRoyaltyRate.setSpeciesCode(rs.getString("SpeciesCode"));
		mainRevenueRoyaltyRate.setSpeciesName(rs.getString("SpeciesName"));
		mainRevenueRoyaltyRate.setBigSizeRoyaltyRate(rs.getBigDecimal("BigSizeRoyaltyRate"));
		mainRevenueRoyaltyRate.setSmallSizeRoyaltyRate(rs.getBigDecimal("SmallSizeRoyaltyRate"));
		mainRevenueRoyaltyRate.setCessRate(rs.getBigDecimal("CessRate"));
		mainRevenueRoyaltyRate.setJarasRoyaltyRate(rs.getBigDecimal("JarasRoyaltyRate"));
		mainRevenueRoyaltyRate.setJarasCessRate(rs.getBigDecimal("JarasCessRate"));
		mainRevenueRoyaltyRate.setStartDate(rs.getDate("StartDate"));
		mainRevenueRoyaltyRate.setEndDate(rs.getDate("EndDate"));
		mainRevenueRoyaltyRate.setStatus(rs.getString("Status"));
		
		if (mainRevenueRoyaltyRate.getStatus() == null)
			mainRevenueRoyaltyRate.setStatus("I");

		return mainRevenueRoyaltyRate;
	}
	
	private ArrayList<MainRevenueRoyaltyRate> getMainRevenueRoyaltyRates(ResultSet rs) throws SQLException
	{
		ArrayList<MainRevenueRoyaltyRate> mainRevenueRoyaltyRates = new ArrayList<>();

		while (rs.next())
		{
			mainRevenueRoyaltyRates.add(read(rs));
		}
		return mainRevenueRoyaltyRates;
	}

	MainRevenueRoyaltyRate getMainRevenueRoyaltyRate(long mainRevenueRoyaltyRateID) throws SQLException
	{
		MainRevenueRoyaltyRate mainRevenueRoyaltyRate = null;
		PreparedStatement ps = facade.prepareStatement("SELECT mrrr.*, sp.Code AS SpeciesCode, sp.Name AS SpeciesName, st.Code AS StateCode, st.Name AS StateName FROM MainRevenueRoyaltyRate mrrr, Species sp, State st WHERE mrrr.SpeciesID = sp.SpeciesID AND mrrr.StateID = st.StateID AND mrrr.MainRevenueRoyaltyRateID = ?");

		ps.setLong(1, mainRevenueRoyaltyRateID);

		ResultSet rs = ps.executeQuery();
		
		if (rs.next())
			mainRevenueRoyaltyRate = read(rs);
		
		return mainRevenueRoyaltyRate;
	}
	
	ArrayList<MainRevenueRoyaltyRate> getMainRevenueRoyaltyRates(String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT mrrr.*, sp.Code AS SpeciesCode, sp.Name AS SpeciesName, st.Code AS StateCode, st.Name AS StateName FROM MainRevenueRoyaltyRate mrrr, Species sp, State st WHERE mrrr.SpeciesID = sp.SpeciesID AND mrrr.StateID = st.StateID AND mrrr.Status = ? ORDER BY SpeciesCode");

		ps.setString(1, status);

		return getMainRevenueRoyaltyRates(ps.executeQuery());
	}
	
	ArrayList<MainRevenueRoyaltyRate> getMainRevenueRoyaltyRates(State state, String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT mrrr.*, sp.Code AS SpeciesCode, sp.Name AS SpeciesName, st.Code AS StateCode, st.Name AS StateName FROM MainRevenueRoyaltyRate mrrr, Species sp, State st WHERE mrrr.SpeciesID = sp.SpeciesID AND mrrr.StateID = ? AND mrrr.StateID = st.StateID AND mrrr.Status = ? ORDER BY SpeciesCode");

		ps.setInt(1, state.getStateID());
		ps.setString(2, status);

		return getMainRevenueRoyaltyRates(ps.executeQuery());
	}
}