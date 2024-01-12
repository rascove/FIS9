package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.District;
import my.edu.utem.ftmk.fis9.maintenance.model.Hall;
import my.edu.utem.ftmk.fis9.maintenance.model.Range;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "districtMBean")
public class DistrictManagedBean extends AbstractManagedBean<District>
{
	private static final long serialVersionUID = VERSION;
	private Range range;
	private Hall hall;
	private ArrayList<State> states;
	private ArrayList<Staff> staffs;
	private boolean addRangeOperation;
	private boolean addHallOperation;
	private int selectedStateID;
	private int accessLevel;

	public DistrictManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			Staff user = getCurrentUser();
			int stateID = user.getStateID(), designationID = user.getDesignationID();

			if (stateID == 0)
			{
				if (designationID == 0)
				{
					models = facade.getDistricts();
					states = facade.getStates();
				}
				else
					models = new ArrayList<>();
			}
			else
			{
				State state = facade.getState(stateID);

				if (designationID == 1 || user.isAdministrative())
				{
					models = facade.getDistricts(state);
					accessLevel = 1;
				}
				else
				{
					District district = facade.getDistrict(user);
					models = new ArrayList<>();
					accessLevel = 2;

					models.add(district);
				}

				states = new ArrayList<>();
				selectedStateID = stateID;

				states.add(state);
			}

			sort(models);

			for (District district : models)
			{
				sort(district.getRanges());
				sort(district.getHalls());
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public District getDistrict()
	{
		return model;
	}

	public void setDistrict(District district)
	{
		this.model = district;
	}

	public Range getRange()
	{
		return range;
	}

	public void setRange(Range range)
	{
		this.range = range;
	}

	public Hall getHall()
	{
		return hall;
	}

	public void setHall(Hall hall)
	{
		this.hall = hall;
	}

	public ArrayList<District> getDistricts()
	{
		return models;
	}

	public void setDistricts(ArrayList<District> districts)
	{
		this.models = districts;
	}

	public ArrayList<State> getStates()
	{
		return states;
	}

	public void setStates(ArrayList<State> states)
	{
		this.states = states;
	}

	public ArrayList<Staff> getStaffs()
	{
		return staffs;
	}

	public void setStaffs(ArrayList<Staff> staffs)
	{
		this.staffs = staffs;
	}

	public boolean isAddRangeOperation()
	{
		return addRangeOperation;
	}

	public void setAddRangeOperation(boolean addRangeOperation)
	{
		this.addRangeOperation = addRangeOperation;
	}

	public boolean isAddHallOperation()
	{
		return addHallOperation;
	}

	public void setAddHallOperation(boolean addHallOperation)
	{
		this.addHallOperation = addHallOperation;
	}

	public int getSelectedStateID()
	{
		return selectedStateID;
	}

	public void setSelectedStateID(int selectedStateID)
	{
		this.selectedStateID = selectedStateID;
	}

	public int getAccessLevel()
	{
		return accessLevel;
	}

	public String getComponent()
	{
		return ":frmManager:table" + (model == null ? "" : ":" + models.indexOf(model) + ":subtable");
	}

	public String getComponent2()
	{
		return ":frmManager:table" + (model == null ? "" : ":" + models.indexOf(model) + ":subtable2");
	}

	public void loadStaffs()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			State state = new State();
			state.setStateID(model.getStateID());
			staffs = facade.getStaffs(state);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public void handleStateIDChange()
	{
		model.setStateID(selectedStateID);
		loadStaffs();
	}

	@Override
	public void handleOpen()
	{
		if (addOperation)
		{
			model = new District();

			model.setRanges(new ArrayList<>());
			model.setHalls(new ArrayList<>());

			if (getCurrentUser().getStateID() == 0)
				selectedStateID = 0;
			else
				model.setStateID(selectedStateID);
		}
		else
			model = (District) copy(models, model);

		loadStaffs();
	}

	public void handleOpenRange()
	{
		if (addRangeOperation)
		{
			range = new Range();
			range.setDistrictID(model.getDistrictID());
		}
		else
			range = (Range) copy(model.getRanges(), range);

		loadStaffs();
	}

	public void handleOpenHall()
	{
		if (addHallOperation)
		{
			hall = new Hall();

			hall.setHallID(System.currentTimeMillis());
			hall.setDistrictID(model.getDistrictID());
		}
		else
			hall = (Hall) copy(model.getHalls(), hall);
	}

	public void districtEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			if (staffs != null)
			{
				for (Staff staff : staffs)
				{
					if (staff.getStaffID().equals(model.getOfficerID()))
						model.setOfficerName(staff.getName());

					if (staff.getStaffID().equals(model.getAsstOfficerID()))
						model.setAsstOfficerName(staff.getName());

					if (staff.getStaffID().equals(model.getClerk1ID()))
						model.setClerk1Name(staff.getName());

					if (staff.getStaffID().equals(model.getClerk2ID()))
						model.setClerk2Name(staff.getName());

					if (staff.getStaffID().equals(model.getClerk3ID()))
						model.setClerk3Name(staff.getName());
				}
			}

			for (State state : states)
			{
				if (state.getStateID() == model.getStateID())
				{
					model.setStateCode(state.getCode());
					model.setStateName(state.getName());
				}
			}

			finalizeModelEntry(addOperation ? facade.addDistrict(model) : facade.updateDistrict(model), addOperation, facade, "daerah hutan, ID " + model.getDistrictID(), null, models, model);
			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popup').hide()");
	}

	public void rangeEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			if (staffs != null)
				for (Staff staff : staffs)
					if (staff.getStaffID().equals(range.getAsstOfficerID()))
						range.setAsstOfficerName(staff.getName());

			finalizeModelEntry(addRangeOperation ? facade.addRange(range) : facade.updateRange(range), addRangeOperation, facade, "renj hutan, ID " + range.getRangeID(), null, model.getRanges(), range);
			range = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popupRange').hide()");
	}

	public void hallEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addHallOperation ? facade.addHall(hall, true) : facade.updateHall(hall), addHallOperation, facade, "balai pemeriksa hutan, ID " + hall.getHallID(), null, model.getHalls(), hall);
			hall = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popupHall').hide()");
	}
}