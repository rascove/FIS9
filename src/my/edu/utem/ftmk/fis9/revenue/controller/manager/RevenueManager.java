package my.edu.utem.ftmk.fis9.revenue.controller.manager;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import my.edu.utem.ftmk.fis9.revenue.model.Revenue;

/**
 * @author Nor Azman Mat Ariff
 */
class RevenueManager extends RevenueTableManager
{
	RevenueManager(RevenueFacade facade)
	{
		super(facade);
	}

	private int write(Revenue revenue, PreparedStatement ps) throws SQLException
	{
		ps.setInt(1, revenue.getDepartmentVotID());
		ps.setBigDecimal(2, revenue.getValue());
		
		return ps.executeUpdate();
	}

	int addRevenue(Revenue revenue) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO Revenue (DepartmentVotID, Value) VALUES (?, ?) ON DUPLICATE KEY UPDATE value = ROUND(value + ?, 2)");
		
		ps.setInt(1, revenue.getDepartmentVotID());
		ps.setBigDecimal(2, revenue.getValue());
		ps.setBigDecimal(3, revenue.getValue());
		
		return write(revenue, ps);
	}

	int subtractRevenue(Revenue revenue) throws SQLException
	{
		BigDecimal value = getRevenue(revenue);
		value = value.subtract(revenue.getValue()).setScale(2, BigDecimal.ROUND_HALF_UP);
		PreparedStatement ps = facade.prepareStatement("UPDATE Revenue SET Value = ROUND(Value - ?, 2) WHERE DepartmentVotID = ?");
		ps.setBigDecimal(1, value);
		ps.setInt(2, revenue.getDepartmentVotID());

		return ps.executeUpdate();
	}

	private BigDecimal getRevenue(Revenue revenue) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT Value FROM Revenue WHERE DepartmentVotID = ?");

		ps.setInt(1, revenue.getDepartmentVotID());
		
		BigDecimal value = new BigDecimal(0);

		ResultSet rs = ps.executeQuery();

		if (rs.next())
		{
			value = rs.getBigDecimal(1);		
		}

		return value;
	}
}