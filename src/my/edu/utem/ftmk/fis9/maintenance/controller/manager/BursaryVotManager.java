package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.BursaryVot;

/**
 * @author Nor Azman Mat Ariff
 */
class BursaryVotManager extends MaintenanceTableManager
{
	BursaryVotManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(BursaryVot bursaryVot, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, bursaryVot.getCode());
		ps.setString(2, bursaryVot.getName());
		ps.setString(3, bursaryVot.getType());
		ps.setString(4, bursaryVot.getStatus());

		if (bursaryVot.getBursaryVotID() != 0)
			ps.setInt(5, bursaryVot.getBursaryVotID());

		return ps.executeUpdate();
	}

	int addBursaryVot(BursaryVot bursaryVot) throws SQLException
	{
		int status = 0;
		if(!checkExistingCode(bursaryVot))
		{		
			PreparedStatement ps = facade.prepareStatement("INSERT INTO BursaryVot (Code, Name, Type, Status) VALUES (?, ?, ?, ?)");
			status = write(bursaryVot, ps);
	
			if (status != 0)
			{
				ResultSet rs = ps.getGeneratedKeys();
	
				if (rs.next())
					bursaryVot.setBursaryVotID(rs.getInt(1));
			}
		}

		return status;
	}
	
	boolean checkExistingCode(BursaryVot bursaryVot) throws SQLException
	{
		boolean exist = false;
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM BursaryVot WHERE BursaryVotID != ? AND Code = ? AND Status = 'A'");		
		ps.setInt(1, bursaryVot.getBursaryVotID());
		ps.setString(2, bursaryVot.getCode());
		
		ResultSet rs = ps.executeQuery();
		
		if (rs.next())
		{
			exist = true;
		}
		
		return exist;
	}

	int updateBursaryVot(BursaryVot bursaryVot) throws SQLException
	{
		int status = 0;
		if(!checkExistingCode(bursaryVot))
		{
			PreparedStatement ps = facade.prepareStatement("UPDATE BursaryVot SET Code = ?, Name = ?, Type = ?, Status = ? WHERE BursaryVotID = ?");
			status = write(bursaryVot, ps);
		}
		
		return status;
	}
	
	int deleteBursaryVot(BursaryVot bursaryVot) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE BursaryVot SET Status = 'I' WHERE BursaryVotID = ?");
		ps.setInt(1, bursaryVot.getBursaryVotID());

		return ps.executeUpdate();
	}

	private BursaryVot read(ResultSet rs) throws SQLException
	{
		BursaryVot bursaryVot = new BursaryVot();

		bursaryVot.setBursaryVotID(rs.getInt("BursaryVotID"));
		bursaryVot.setCode(rs.getString("Code"));
		bursaryVot.setName(rs.getString("Name"));
		bursaryVot.setType(rs.getString("Type"));
		if(bursaryVot.getType().equalsIgnoreCase("R"))
		{
			bursaryVot.setTypeName("Hasil");
		}
		else
		{
			if(bursaryVot.getType().equalsIgnoreCase("T"))
			{
				bursaryVot.setTypeName("Amanah");
			}
		}
		bursaryVot.setStatus(rs.getString("Status"));

		return bursaryVot;
	}
	
	private ArrayList<BursaryVot> getBursaryVots(ResultSet rs) throws SQLException
	{
		ArrayList<BursaryVot> bursaryVots = new ArrayList<>();

		while (rs.next())
		{
			bursaryVots.add(read(rs));
		}
		return bursaryVots;
	}

	BursaryVot getBursaryVot(int bursaryVotID) throws SQLException
	{
		BursaryVot bursaryVot = null;
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM BursaryVot WHERE BursaryVotID = ?");

		ps.setInt(1, bursaryVotID);

		ResultSet rs = ps.executeQuery();
		
		if (rs.next())
			bursaryVot = read(rs);
		
		return bursaryVot;
	}
	
	ArrayList<BursaryVot> getBursaryVots(String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM BursaryVot WHERE Status = ? ORDER BY Code");

		ps.setString(1, status);

		return getBursaryVots(ps.executeQuery());
	}
	
	
}