package my.edu.utem.ftmk.fis9.global.controller.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import my.edu.utem.ftmk.fis9.global.model.TrailLog;

/**
 * @author Satrya Fajri Pratama
 */
public abstract class AbstractFacade implements AutoCloseable
{
	private static final boolean usePooling = true;
	private static DataSource ds = null;

	private Connection connection;
	private TrailLogManager trailLogManager;
	private ArrayList<AbstractFacade> group;

	static
	{
		try
		{
			if (usePooling)
				ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/fis9");
			else
				Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch (NamingException | ClassNotFoundException e)
		{
		}
	}

	private Connection getConnection() throws SQLException
	{
		if (connection == null || connection.isClosed())
		{
			if (ds == null)
			{
				if (usePooling)
					throw new SQLException("Sambungan ke pangkalan data perlu dikonfigurasi semula.");
				else
				{
					connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbFIS9production1?useAffectedRows=true", "root", "");
					connection.setAutoCommit(false);
				}
			}
			else
				connection = ds.getConnection();

			if (group != null)
				for (AbstractFacade facade : group)
					if (facade != this)
						facade.connection = connection;
		}

		return connection;
	}

	private TrailLogManager getTrailLogManager()
	{
		return trailLogManager == null ? trailLogManager = new TrailLogManager(this) : trailLogManager;
	}

	protected abstract PreparedStatement prepareStatement(String sql) throws SQLException;

	protected final PreparedStatement getPreparedStatement(String sql) throws SQLException
	{
		return getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
	}

	@Override
	public final void close() throws SQLException
	{
		if (connection != null && !connection.isClosed())
		{
			if (!usePooling)
				connection.commit();

			connection.close();
		}
	}

	public final int addTrailLog(TrailLog trailLog) throws SQLException
	{
		return getTrailLogManager().addTrailLog(trailLog);
	}

	public final ArrayList<TrailLog> getTrailLogs(Date logTime) throws SQLException
	{
		return getTrailLogManager().getTrailLogs(logTime);
	}

	public final ArrayList<TrailLog> getTrailLogs(String staffID) throws SQLException
	{
		return getTrailLogManager().getTrailLogs(staffID);
	}

	public final ArrayList<TrailLog> getTrailLogs(String staffID, Date logTime) throws SQLException
	{
		return getTrailLogManager().getTrailLogs(staffID, logTime);
	}

	public static void group(AbstractFacade... facades) throws SQLException
	{
		if (facades.length > 1)
		{
			ArrayList<AbstractFacade> group = new ArrayList<>();

			for (AbstractFacade facade : facades)
			{
				group.add(facade);
				facade.group = group;
			}
		}
	}
}