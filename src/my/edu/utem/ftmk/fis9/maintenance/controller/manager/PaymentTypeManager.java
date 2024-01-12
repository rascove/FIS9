package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.PaymentType;

/**
 * @author Nor Azman Mat Ariff
 */
class PaymentTypeManager extends MaintenanceTableManager
{
	PaymentTypeManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(PaymentType paymentType, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, paymentType.getCode());
		ps.setString(2, paymentType.getName());
		ps.setString(3, paymentType.getStatus());

		if (paymentType.getPaymentTypeID() != 0)
			ps.setInt(4, paymentType.getPaymentTypeID());

		return ps.executeUpdate();
	}

	int addPaymentType(PaymentType paymentType) throws SQLException
	{
		int status = 0;
		if(checkExistingCode(paymentType) == null)
		{
			PreparedStatement ps = facade.prepareStatement("INSERT INTO PaymentType (Code, Name, Status) VALUES (?, ?, ?)");
			status = write(paymentType, ps);
	
			if (status != 0)
			{
				ResultSet rs = ps.getGeneratedKeys();
	
				if (rs.next())
					paymentType.setPaymentTypeID(rs.getInt(1));
			}
		}
		
		return status;
	}
	
	private PaymentType checkExistingCode(PaymentType paymentType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM PaymentType WHERE Code = ? AND Status = 'A'");
		ps.setString(1, paymentType.getCode());
		
		ResultSet rs = ps.executeQuery();
		
		PaymentType oldPaymentType = null;
		
		if (rs.next())
		{
			oldPaymentType = read(rs);
		}
		
		return oldPaymentType;
	}

	int updatePaymentType(PaymentType paymentType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE PaymentType SET Code = ?, Name = ?, Status = ? WHERE PaymentTypeID = ?");

		return write(paymentType, ps);
	}
	
	int deletePaymentType(PaymentType paymentType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE PaymentType SET Status = 'I' WHERE PaymentTypeID = ?");
		ps.setInt(1, paymentType.getPaymentTypeID());

		return ps.executeUpdate();
	}

	private PaymentType read(ResultSet rs) throws SQLException
	{
		PaymentType paymentType = new PaymentType();

		paymentType.setPaymentTypeID(rs.getInt("PaymentTypeID"));
		paymentType.setCode(rs.getString("Code"));
		paymentType.setName(rs.getString("Name"));
		paymentType.setStatus(rs.getString("Status"));

		return paymentType;
	}
	
	private ArrayList<PaymentType> getPaymentTypes(ResultSet rs) throws SQLException
	{
		ArrayList<PaymentType> paymentTypes = new ArrayList<>();

		while (rs.next())
		{
			paymentTypes.add(read(rs));
		}
		return paymentTypes;
	}

	PaymentType getPaymentType(int paymentTypeID) throws SQLException
	{
		PaymentType paymentType = null;
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM PaymentType WHERE PaymentTypeID = ?");

		ps.setInt(1, paymentTypeID);

		ResultSet rs = ps.executeQuery();
		
		if (rs.next())
			paymentType = read(rs);
		
		return paymentType;
	}
	
	ArrayList<PaymentType> getPaymentTypes(String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM PaymentType WHERE Status = ? ORDER BY Code");

		ps.setString(1, status);

		return getPaymentTypes(ps.executeQuery());
	}
}