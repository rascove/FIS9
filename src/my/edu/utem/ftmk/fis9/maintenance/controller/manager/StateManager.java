package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.State;

/**
 * @author Satrya Fajri Pratama
 */
class StateManager extends MaintenanceTableManager
{
	StateManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(State state, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, state.getCode());
		ps.setString(2, state.getName());
		ps.setString(3, state.getMotto());
		ps.setString(4, state.getDirectorID());
		nullable(ps, 5, state.getDeputyDirector1ID());
		nullable(ps, 6, state.getDeputyDirector2ID());
		nullable(ps, 7, state.getSeniorAsstDirector1ID());
		nullable(ps, 8, state.getSeniorAsstDirector2ID());
		nullable(ps, 9, state.getSeniorAsstDirector3ID());
		nullable(ps, 10, state.getSeniorAsstDirector4ID());
		nullable(ps, 11, state.getSeniorAsstDirector5ID());
		nullable(ps, 12, state.getSeniorAsstDirector6ID());
		nullable(ps, 13, state.getAsstDirector1ID());
		nullable(ps, 14, state.getAsstDirector2ID());
		nullable(ps, 15, state.getAsstDirector3ID());
		nullable(ps, 16, state.getAsstDirector4ID());
		nullable(ps, 17, state.getAsstDirector5ID());
		nullable(ps, 18, state.getAsstDirector6ID());

		if (state.getStateID() != 0)
			ps.setInt(19, state.getStateID());

		return ps.executeUpdate();
	}

	int addState(State state) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO State (Code, Name, Motto, DirectorID, DeputyDirector1ID, DeputyDirector2ID, SeniorAsstDirector1ID, SeniorAsstDirector2ID, SeniorAsstDirector3ID, SeniorAsstDirector4ID, SeniorAsstDirector5ID, SeniorAsstDirector6ID, AsstDirector1ID, AsstDirector2ID, AsstDirector3ID, AsstDirector4ID, AsstDirector5ID, AsstDirector6ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		int status = write(state, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				state.setStateID(rs.getInt(1));
		}

		return status;
	}

	int updateState(State state) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE State SET Code = ?, Name = ?, Motto = ?, DirectorID = ?, DeputyDirector1ID = ?, DeputyDirector2ID = ?, SeniorAsstDirector1ID = ?, SeniorAsstDirector2ID = ?, SeniorAsstDirector3ID = ?, SeniorAsstDirector4ID = ?, SeniorAsstDirector5ID = ?, SeniorAsstDirector6ID = ?, AsstDirector1ID = ?, AsstDirector2ID = ?, AsstDirector3ID = ?, AsstDirector4ID = ?, AsstDirector5ID = ?, AsstDirector6ID = ? WHERE StateID = ?");

		return write(state, ps);
	}

	private State read(ResultSet rs) throws SQLException
	{
		State state = new State();

		state.setStateID(rs.getInt("StateID"));
		state.setCode(rs.getString("Code"));
		state.setName(rs.getString("Name"));
		state.setMotto(rs.getString("Motto"));
		state.setDirectorID(rs.getString("DirectorID"));
		state.setDeputyDirector1ID(rs.getString("DeputyDirector1ID"));
		state.setDeputyDirector2ID(rs.getString("DeputyDirector2ID"));
		state.setSeniorAsstDirector1ID(rs.getString("SeniorAsstDirector1ID"));
		state.setSeniorAsstDirector2ID(rs.getString("SeniorAsstDirector2ID"));
		state.setSeniorAsstDirector3ID(rs.getString("SeniorAsstDirector3ID"));
		state.setSeniorAsstDirector4ID(rs.getString("SeniorAsstDirector4ID"));
		state.setSeniorAsstDirector5ID(rs.getString("SeniorAsstDirector5ID"));
		state.setSeniorAsstDirector6ID(rs.getString("SeniorAsstDirector6ID"));
		state.setAsstDirector1ID(rs.getString("AsstDirector1ID"));
		state.setAsstDirector2ID(rs.getString("AsstDirector2ID"));
		state.setAsstDirector3ID(rs.getString("AsstDirector3ID"));
		state.setAsstDirector4ID(rs.getString("AsstDirector4ID"));
		state.setAsstDirector5ID(rs.getString("AsstDirector5ID"));
		state.setAsstDirector6ID(rs.getString("AsstDirector6ID"));
		state.setDirectorName(rs.getString("DirectorName"));
		state.setDeputyDirector1Name(rs.getString("DeputyDirector1Name"));
		state.setDeputyDirector2Name(rs.getString("DeputyDirector2Name"));
		state.setSeniorAsstDirector1Name(rs.getString("SeniorAsstDirector1Name"));
		state.setSeniorAsstDirector2Name(rs.getString("SeniorAsstDirector2Name"));
		state.setSeniorAsstDirector3Name(rs.getString("SeniorAsstDirector3Name"));
		state.setSeniorAsstDirector4Name(rs.getString("SeniorAsstDirector4Name"));
		state.setSeniorAsstDirector5Name(rs.getString("SeniorAsstDirector5Name"));
		state.setSeniorAsstDirector6Name(rs.getString("SeniorAsstDirector6Name"));
		state.setAsstDirector1Name(rs.getString("AsstDirector1Name"));
		state.setAsstDirector2Name(rs.getString("AsstDirector2Name"));
		state.setAsstDirector3Name(rs.getString("AsstDirector3Name"));
		state.setAsstDirector4Name(rs.getString("AsstDirector4Name"));
		state.setAsstDirector5Name(rs.getString("AsstDirector5Name"));
		state.setAsstDirector6Name(rs.getString("AsstDirector6Name"));
		state.setRegions(facade.getRegions(state));

		return state;
	}

	State getState(int stateID) throws SQLException
	{
		State state = null;
		PreparedStatement ps = facade.prepareStatement("SELECT s.*, d.Name AS DirectorName, dd1.Name AS DeputyDirector1Name, dd2.Name AS DeputyDirector2Name, sad1.Name AS SeniorAsstDirector1Name, sad2.Name AS SeniorAsstDirector2Name, sad3.Name AS SeniorAsstDirector3Name, sad4.Name AS SeniorAsstDirector4Name, sad5.Name AS SeniorAsstDirector5Name, sad6.Name AS SeniorAsstDirector6Name, ad1.Name AS AsstDirector1Name, ad2.Name AS AsstDirector2Name, ad3.Name AS AsstDirector3Name, ad4.Name AS AsstDirector4Name, ad5.Name AS AsstDirector5Name, ad6.Name AS AsstDirector6Name FROM State s JOIN Staff d LEFT JOIN Staff dd1 ON s.DeputyDirector1ID = dd1.StaffID LEFT JOIN Staff dd2 ON s.DeputyDirector2ID = dd2.StaffID LEFT JOIN Staff sad1 ON s.SeniorAsstDirector1ID = sad1.StaffID LEFT JOIN Staff sad2 ON s.SeniorAsstDirector2ID = sad2.StaffID LEFT JOIN Staff sad3 ON s.SeniorAsstDirector3ID = sad3.StaffID LEFT JOIN Staff sad4 ON s.SeniorAsstDirector4ID = sad4.StaffID LEFT JOIN Staff sad5 ON s.SeniorAsstDirector5ID = sad5.StaffID LEFT JOIN Staff sad6 ON s.SeniorAsstDirector6ID = sad6.StaffID LEFT JOIN Staff ad1 ON s.AsstDirector1ID = ad1.StaffID LEFT JOIN Staff ad2 ON s.AsstDirector2ID = ad2.StaffID LEFT JOIN Staff ad3 ON s.AsstDirector3ID = ad3.StaffID LEFT JOIN Staff ad4 ON s.AsstDirector4ID = ad4.StaffID LEFT JOIN Staff ad5 ON s.AsstDirector5ID = ad5.StaffID LEFT JOIN Staff ad6 ON s.AsstDirector6ID = ad6.StaffID WHERE s.StateID = ? AND s.DirectorID = d.StaffID");

		ps.setInt(1, stateID);

		ResultSet rs = ps.executeQuery();

		if (rs.next())
			state = read(rs);

		return state;
	}

	ArrayList<State> getStates() throws SQLException
	{
		ArrayList<State> states = new ArrayList<>();
		PreparedStatement ps = facade.prepareStatement("SELECT s.*, d.Name AS DirectorName, dd1.Name AS DeputyDirector1Name, dd2.Name AS DeputyDirector2Name, sad1.Name AS SeniorAsstDirector1Name, sad2.Name AS SeniorAsstDirector2Name, sad3.Name AS SeniorAsstDirector3Name, sad4.Name AS SeniorAsstDirector4Name, sad5.Name AS SeniorAsstDirector5Name, sad6.Name AS SeniorAsstDirector6Name, ad1.Name AS AsstDirector1Name, ad2.Name AS AsstDirector2Name, ad3.Name AS AsstDirector3Name, ad4.Name AS AsstDirector4Name, ad5.Name AS AsstDirector5Name, ad6.Name AS AsstDirector6Name FROM State s JOIN Staff d LEFT JOIN Staff dd1 ON s.DeputyDirector1ID = dd1.StaffID LEFT JOIN Staff dd2 ON s.DeputyDirector2ID = dd2.StaffID LEFT JOIN Staff sad1 ON s.SeniorAsstDirector1ID = sad1.StaffID LEFT JOIN Staff sad2 ON s.SeniorAsstDirector2ID = sad2.StaffID LEFT JOIN Staff sad3 ON s.SeniorAsstDirector3ID = sad3.StaffID LEFT JOIN Staff sad4 ON s.SeniorAsstDirector4ID = sad4.StaffID LEFT JOIN Staff sad5 ON s.SeniorAsstDirector5ID = sad5.StaffID LEFT JOIN Staff sad6 ON s.SeniorAsstDirector6ID = sad6.StaffID LEFT JOIN Staff ad1 ON s.AsstDirector1ID = ad1.StaffID LEFT JOIN Staff ad2 ON s.AsstDirector2ID = ad2.StaffID LEFT JOIN Staff ad3 ON s.AsstDirector3ID = ad3.StaffID LEFT JOIN Staff ad4 ON s.AsstDirector4ID = ad4.StaffID LEFT JOIN Staff ad5 ON s.AsstDirector5ID = ad5.StaffID LEFT JOIN Staff ad6 ON s.AsstDirector6ID = ad6.StaffID WHERE s.DirectorID = d.StaffID ORDER BY s.Code");
		ResultSet rs = ps.executeQuery();

		while (rs.next())
			states.add(read(rs));

		return states;
	}
}