package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.CuttingOption;
import my.edu.utem.ftmk.fis9.maintenance.model.State;

/**
 * @author Satrya Fajri Pratama
 */
class CuttingOptionManager extends MaintenanceTableManager
{
	CuttingOptionManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(CuttingOption cuttingOption, PreparedStatement ps) throws SQLException
	{
		ps.setDouble(1, cuttingOption.getDipterocarpLimit());
		ps.setDouble(2, cuttingOption.getNonDipterocarpLimit());
		ps.setInt(3, cuttingOption.getStateID());
		
		if (cuttingOption.getCuttingOptionID() != 0)
			ps.setInt(4, cuttingOption.getCuttingOptionID());

		return ps.executeUpdate();
	}

	int addCuttingOption(CuttingOption cuttingOption) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO CuttingOption (DipterocarpLimit, NonDipterocarpLimit, StateID) VALUES (?, ?, ?)");
		int status = write(cuttingOption, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				cuttingOption.setCuttingOptionID(rs.getInt(1));
		}

		return status;
	}

	int updateCuttingOption(CuttingOption cuttingOption) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE CuttingOption SET DipterocarpLimit = ?, NonDipterocarpLimit = ?, StateID = ? WHERE CuttingOptionID = ?");

		return write(cuttingOption, ps);
	}

	private ArrayList<CuttingOption> getCuttingOptions(ResultSet rs) throws SQLException
	{
		ArrayList<CuttingOption> cuttingOptions = new ArrayList<>();

		while (rs.next())
		{
			CuttingOption cuttingOption = new CuttingOption();

			cuttingOption.setCuttingOptionID(rs.getInt("CuttingOptionID"));
			cuttingOption.setDipterocarpLimit(rs.getDouble("DipterocarpLimit"));
			cuttingOption.setNonDipterocarpLimit(rs.getDouble("NonDipterocarpLimit"));
			cuttingOption.setStateID(rs.getInt("StateID"));
			cuttingOption.setStateName(rs.getString("StateName"));

			cuttingOptions.add(cuttingOption);
		}

		return cuttingOptions;
	}

	ArrayList<CuttingOption> getCuttingOptions() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT co.*, s.Name AS StateName FROM CuttingOption co LEFT JOIN State s ON co.StateID = s.StateID ORDER BY co.DipterocarpLimit DESC, co.NonDipterocarpLimit DESC");

		return getCuttingOptions(ps.executeQuery());
	}
	
	ArrayList<CuttingOption> getCuttingOptions(State state) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT co.*, s.Name AS StateName FROM CuttingOption co LEFT JOIN State s ON co.StateID = s.StateID WHERE co.StateID = ? OR co.StateID IS NULL ORDER BY co.DipterocarpLimit DESC, co.NonDipterocarpLimit DESC");

		ps.setInt(1, state.getStateID());
		
		return getCuttingOptions(ps.executeQuery());
	}
}