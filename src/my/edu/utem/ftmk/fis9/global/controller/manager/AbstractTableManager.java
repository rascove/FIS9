package my.edu.utem.ftmk.fis9.global.controller.manager;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author Satrya Fajri Pratama
 */
public abstract class AbstractTableManager
{
	protected final boolean toBoolean(int status)
	{
		return status != 0;
	}
	
	protected final int toInt(boolean status)
	{
		return status ? 1 : 0;
	}
	
	protected final java.sql.Date toSQLDate(java.util.Date date)
	{
		return new java.sql.Date(date.getTime());
	}
	
	protected final void nullable(PreparedStatement ps, int index, Integer value) throws SQLException
	{
		if (value == null || value == 0)
			ps.setNull(index, Types.INTEGER);
		else
			ps.setInt(index, value);
	}
	
	protected final void nullable(PreparedStatement ps, int index, Long value) throws SQLException
	{
		if (value == null || value == 0)
			ps.setNull(index, Types.BIGINT);
		else
			ps.setLong(index, value);
	}
	
	protected final void nullable(PreparedStatement ps, int index, Double value) throws SQLException
	{
		if (value == null || value == 0)
			ps.setNull(index, Types.DOUBLE);
		else
			ps.setDouble(index, value);
	}
	
	protected final void nullable(PreparedStatement ps, int index, String value) throws SQLException
	{
		if (value == null || value.trim().isEmpty())
			ps.setNull(index, Types.VARCHAR);
		else
			ps.setString(index, value);
	}
	
	protected final void nullable(PreparedStatement ps, int index, BigDecimal value) throws SQLException
	{
		if (value == null || value == BigDecimal.ZERO)
			ps.setNull(index, Types.DOUBLE);
		else
			ps.setBigDecimal(index, value);
	}
	
	protected final void nullable(PreparedStatement ps, int index, java.util.Date value) throws SQLException
	{
		if (value == null)
			ps.setNull(index, Types.DATE);
		else
			ps.setDate(index, toSQLDate(value));
	}
	
	protected final String standardize(String value)
	{
		if (value != null)
			value = value.toUpperCase().replaceAll("\\s", "").replaceAll("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)", " ");
		
		return value;
	}
}