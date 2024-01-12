package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;



import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.Hall;
import my.edu.utem.ftmk.fis9.maintenance.model.HallOfficer;
import my.edu.utem.ftmk.fis9.maintenance.model.Hammer;
import my.edu.utem.ftmk.fis9.maintenance.model.HammerType;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;

/**
 * @author Nor Azman Mat Ariff
 */
@ViewScoped
@ManagedBean(name = "hallOfficerMBean")
public class HallOfficerManagedBean extends AbstractManagedBean<HallOfficer>
{
	private static final long serialVersionUID = VERSION;
	private ArrayList<Staff> staffs;
	private ArrayList<Staff> deletedStaffs;
	private ArrayList<Hammer> hammers;
	private ArrayList<Hammer> deletedHammers;
	private ArrayList<Hall> halls;
	private Staff user;
	private Date today;

	public HallOfficerManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			user = getCurrentUser();
			
			Calendar calToday = Calendar.getInstance();
			today = resetCalendar(calToday, 0, 0);
			
			HammerType tempHammerType = new HammerType();
			tempHammerType.setHammerTypeID(1);
			halls = facade.getHalls(1);
			
			if (user.getStateID() == 0)
			{
				models = facade.getHallOfficers("A");
				staffs = facade.getStaffs();
				hammers = facade.getHammers(tempHammerType);
				halls = facade.getHalls(1);				
			}
			else
			{
				State state = facade.getState(user.getStateID());
				models = facade.getHallOfficers(state);
				staffs = facade.getStaffs(state);
				hammers = facade.getHammers(state, tempHammerType);
				halls = facade.getHalls(state, 1);
			}
			tempHammerType = null;
			
			sort(models);
			
			Date currentDate = new Date();
			
			deletedStaffs = new ArrayList<Staff>();
			ArrayList<Staff> tempStaffs = new ArrayList<Staff>();
			for(Staff staff : staffs)
			{
				tempStaffs.add(staff);
			}
			
			deletedHammers = new ArrayList<Hammer>();
			ArrayList<Hammer> tempHammers = new ArrayList<Hammer>();
			for(Hammer hammer : hammers)
			{
				tempHammers.add(hammer);
			}			
			
			for(HallOfficer hallOfficer : models)
			{
				if(hallOfficer.getEndDate().compareTo(currentDate) < 0)
				{
					hallOfficer.setStatus("E");
				}

				for(Hammer hammer : hammers)
				{
					if(hammer.getHammerNo().equalsIgnoreCase(hallOfficer.getHammerNo()))
					{
						tempHammers.remove(hammer);
						deletedHammers.add(hammer);
					}
				}
			}
			
			staffs = tempStaffs;
			sort(staffs);
			
			hammers = tempHammers;
			sort(hammers);
			
			tempStaffs = null;
			tempHammers = null;
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public Staff getUser() {
		return user;
	}

	public void setUser(Staff user) {
		this.user = user;
	}

	public HallOfficer getHallOfficer()
	{
		return model;
	}

	public void setHallOfficer(HallOfficer hallOfficer)
	{
		this.model = hallOfficer;
	}

	public ArrayList<HallOfficer> getHallOfficers()
	{
		return models;
	}

	public void setHallOfficers(ArrayList<HallOfficer> hallOfficers)
	{
		this.models = hallOfficers;
	}

	public ArrayList<Staff> getStaffs() {
		return staffs;
	}

	public void setStaffs(ArrayList<Staff> staffs) {
		this.staffs = staffs;
	}

	public ArrayList<Hammer> getHammers() {
		return hammers;
	}

	public void setHammers(ArrayList<Hammer> hammers) {
		this.hammers = hammers;
	}

	public ArrayList<Hall> getHalls() {
		return halls;
	}

	public void setHalls(ArrayList<Hall> halls) {
		this.halls = halls;
	}

	public ArrayList<Staff> getDeletedStaffs() {
		return deletedStaffs;
	}

	public void setDeletedStaffs(ArrayList<Staff> deletedStaffs) {
		this.deletedStaffs = deletedStaffs;
	}

	public ArrayList<Hammer> getDeletedHammers() {
		return deletedHammers;
	}

	public void setDeletedHammers(ArrayList<Hammer> deletedHammers) {
		this.deletedHammers = deletedHammers;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new HallOfficer() : (HallOfficer) copy(models, model);
	}

	public void hallOfficerEntry()
	{
		boolean duplicate = false;
		for(HallOfficer hallOfficer : models)
		{
			if(model.getStaffID().equalsIgnoreCase(hallOfficer.getStaffID()) && hallOfficer.getEndDate().after(today))
			{
				duplicate = true;
			}
		}
		
		for(Staff staff : staffs)
		{
			if(staff.getStaffID().equalsIgnoreCase(model.getStaffID()))
			{
				model.setHallOfficerName(staff.getName());
			}
		}
		for(Hall hall : halls)
		{
			if(hall.getHallID() == model.getHallID())
			{
				model.setHallName(hall.getName());
			}
		}
			
		if(addOperation)
		{
			model.setHallOfficerID(System.currentTimeMillis());
			model.setStatus("A");
		}
		
		if(duplicate == false)
		{
			try (MaintenanceFacade facade = new MaintenanceFacade())
			{
				finalizeModelEntry(addOperation ? facade.addHallOfficer(model) : facade.updateHallOfficer(model), addOperation, facade, "pegawai balai, ID " + model.getHallOfficerID(), null, models, model);
				
				for(Hammer hammer : hammers)
				{
					if(hammer.getHammerNo().equalsIgnoreCase(model.getHammerNo()))
					{
						hammers.remove(hammer);
						deletedHammers.add(hammer);
						break;
					}
				}
				sort(hammers);
				
				model = null;
				
				update("frmManager");
			}
			catch (SQLException e)
			{
				e.printStackTrace();
				addMessage(e);
			}
		}
		else
		{
			addMessage(FacesMessage.SEVERITY_WARN, null, model + " tidak dapat dilantik sebagai pegawai balai kerana masih aktif sebagai pegawai balai di balai yang lain.");
		}

		execute("PF('popup').hide()");
	}
	
	public void hallOfficerDelete()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			if( facade.deleteHallOfficer(model) != 0 )
			{
				addMessage(FacesMessage.SEVERITY_INFO, null, model + " berjaya dipadamkan.");
				log(facade, "Padam pegawai balai, ID " + model.getStaffID());
				models.remove(model);
				
				for(Staff staff : deletedStaffs)
				{
					if(staff.getStaffID().equalsIgnoreCase(model.getStaffID()))
					{
						staffs.add(staff);
						deletedStaffs.remove(staff);
						break;
					}
				}
				
				for(Hammer hammer : deletedHammers)
				{
					if(hammer.getHammerNo().equalsIgnoreCase(model.getHammerNo()))
					{
						hammers.add(hammer);
						deletedHammers.remove(hammer);
						break;
					}
				}

				sort(staffs);
				sort(hammers);
				sort(models);
				update("frmEntry");
			}
			else
			{
				addMessage(FacesMessage.SEVERITY_WARN, null, model + " tidak berjaya dipadamkan.");
			}
			model = null;		
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popupProcess').hide()");
	}
	
	private Date resetCalendar(Calendar cal, int year, int month)
	{
		cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		if (year != 0)
		{
			cal.add(Calendar.YEAR, year);
		}
		if (month != 0)
		{
			cal.add(Calendar.MONTH, month);
		}

		return cal.getTime();
	}
}