package my.edu.utem.ftmk.fis9.revenue.controller.manager;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import my.edu.utem.ftmk.fis9.revenue.model.Transaction;

/**
 * @author Nor Azman Mat Ariff
 */
class TransactionManager extends RevenueTableManager
{
	TransactionManager(RevenueFacade facade)
	{
		super(facade);
	}

	private int write(Transaction transaction, PreparedStatement ps) throws SQLException
	{
		ps.setLong(1, transaction.getTransactionID());
		ps.setString(2, transaction.getTransactionType());
		ps.setLong(3, transaction.getTransactionFormID());
		ps.setInt(4, transaction.getDepartmentVotID());
		ps.setBigDecimal(5, transaction.getValue());
		ps.setTimestamp(6, transaction.getRecordTime());			
		
		return ps.executeUpdate();
	}

	int addTransaction(Transaction transaction) throws SQLException
	{
		int status = 0;
		PreparedStatement ps = facade.prepareStatement("INSERT INTO Transaction (TransactionID, TransactionType, TransactionFormID, DepartmentVotID, Value, RecordTime) VALUES (?, ?, ?, ?, ?, ?)");
		status = write(transaction, ps);

		return status;
	}
}