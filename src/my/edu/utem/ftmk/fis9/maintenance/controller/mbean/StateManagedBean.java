package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.Region;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "stateMBean")
public class StateManagedBean extends AbstractManagedBean<State>
{
	private static final long serialVersionUID = VERSION;
	private Region region;
	private ArrayList<Staff> staffs;
	private boolean addRegionOperation;

	public StateManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			Staff user = getCurrentUser();
			int stateID = user.getStateID(), designationID = user.getDesignationID();

			if (stateID == 0)
				models = designationID == 0 ? facade.getStates() : new ArrayList<>();
			else
			{
				State state = facade.getState(stateID);
				models = new ArrayList<>();

				models.add(state);
			}

			sort(models);

			for (State state : models)
				sort(state.getRegions());
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public State getState()
	{
		return model;
	}

	public void setState(State state)
	{
		this.model = state;
	}

	public Region getRegion()
	{
		return region;
	}

	public void setRegion(Region region)
	{
		this.region = region;
	}

	public ArrayList<State> getStates()
	{
		return models;
	}

	public void setStates(ArrayList<State> states)
	{
		this.models = states;
	}

	public ArrayList<Staff> getStaffs()
	{
		return staffs;
	}

	public void setStaffs(ArrayList<Staff> staffs)
	{
		this.staffs = staffs;
	}

	public boolean isAddRegionOperation()
	{
		return addRegionOperation;
	}

	public void setAddRegionOperation(boolean addRegionOperation)
	{
		this.addRegionOperation = addRegionOperation;
	}

	public String getComponent()
	{
		return ":frmManager:table" + (model == null ? "" : ":" + models.indexOf(model) + ":subtable");
	}

	@Override
	public void handleOpen()
	{
		if (addOperation)
		{
			model = new State();
			model.setRegions(new ArrayList<>());
		}
		else
			model = (State) copy(models, model);

		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			staffs = addOperation ? facade.getStaffs() : facade.getStaffs(model);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public void handleOpenRegion()
	{
		if (addRegionOperation)
		{
			region = new Region();
			region.setStateID(model.getStateID());
		}
		else
			region = (Region) copy(model.getRegions(), region);
	}

	public void stateEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			if ("".equals(model.getDeputyDirector1ID()))
			{
				model.setDeputyDirector1ID(null);
				model.setDeputyDirector1Name(null);
			}
			
			if ("".equals(model.getDeputyDirector2ID()))
			{
				model.setDeputyDirector2ID(null);
				model.setDeputyDirector2Name(null);
			}
			
			if ("".equals(model.getSeniorAsstDirector1ID()))
			{
				model.setSeniorAsstDirector1ID(null);
				model.setSeniorAsstDirector1Name(null);
			}
			
			if ("".equals(model.getSeniorAsstDirector2ID()))
			{
				model.setSeniorAsstDirector2ID(null);
				model.setSeniorAsstDirector2Name(null);
			}
			
			if ("".equals(model.getSeniorAsstDirector3ID()))
			{
				model.setSeniorAsstDirector3ID(null);
				model.setSeniorAsstDirector3Name(null);
			}
			
			if ("".equals(model.getSeniorAsstDirector4ID()))
			{
				model.setSeniorAsstDirector4ID(null);
				model.setSeniorAsstDirector4Name(null);
			}
			
			if ("".equals(model.getSeniorAsstDirector5ID()))
			{
				model.setSeniorAsstDirector5ID(null);
				model.setSeniorAsstDirector5Name(null);
			}
			
			if ("".equals(model.getSeniorAsstDirector6ID()))
			{
				model.setSeniorAsstDirector6ID(null);
				model.setSeniorAsstDirector6Name(null);
			}
			
			if ("".equals(model.getAsstDirector1ID()))
			{
				model.setAsstDirector1ID(null);
				model.setAsstDirector1Name(null);
			}
			
			if ("".equals(model.getAsstDirector2ID()))
			{
				model.setAsstDirector2ID(null);
				model.setAsstDirector2Name(null);
			}
			
			if ("".equals(model.getAsstDirector3ID()))
			{
				model.setAsstDirector3ID(null);
				model.setAsstDirector3Name(null);
			}
			
			if ("".equals(model.getAsstDirector4ID()))
			{
				model.setAsstDirector4ID(null);
				model.setAsstDirector4Name(null);
			}
			
			if ("".equals(model.getAsstDirector5ID()))
			{
				model.setAsstDirector5ID(null);
				model.setAsstDirector5Name(null);
			}
			
			if ("".equals(model.getAsstDirector6ID()))
			{
				model.setAsstDirector6ID(null);
				model.setAsstDirector6Name(null);
			}
			
			if (staffs != null)
			{
				for (Staff staff : staffs)
				{
					if (staff.getStaffID().equals(model.getDirectorID()))
						model.setDirectorName(staff.getName());
					
					if (staff.getStaffID().equals(model.getDeputyDirector1ID()))
						model.setDeputyDirector1Name(staff.getName());
					
					if (staff.getStaffID().equals(model.getDeputyDirector2ID()))
						model.setDeputyDirector2Name(staff.getName());
					
					if (staff.getStaffID().equals(model.getSeniorAsstDirector1ID()))
						model.setSeniorAsstDirector1Name(staff.getName());
					
					if (staff.getStaffID().equals(model.getSeniorAsstDirector2ID()))
						model.setSeniorAsstDirector2Name(staff.getName());
					
					if (staff.getStaffID().equals(model.getSeniorAsstDirector3ID()))
						model.setSeniorAsstDirector3Name(staff.getName());
					
					if (staff.getStaffID().equals(model.getSeniorAsstDirector4ID()))
						model.setSeniorAsstDirector4Name(staff.getName());
					
					if (staff.getStaffID().equals(model.getSeniorAsstDirector5ID()))
						model.setSeniorAsstDirector5Name(staff.getName());
					
					if (staff.getStaffID().equals(model.getSeniorAsstDirector6ID()))
						model.setSeniorAsstDirector6Name(staff.getName());
					
					if (staff.getStaffID().equals(model.getAsstDirector1ID()))
						model.setAsstDirector1Name(staff.getName());
					
					if (staff.getStaffID().equals(model.getAsstDirector2ID()))
						model.setAsstDirector2Name(staff.getName());
					
					if (staff.getStaffID().equals(model.getAsstDirector3ID()))
						model.setAsstDirector3Name(staff.getName());
					
					if (staff.getStaffID().equals(model.getAsstDirector4ID()))
						model.setAsstDirector4Name(staff.getName());
					
					if (staff.getStaffID().equals(model.getAsstDirector5ID()))
						model.setAsstDirector5Name(staff.getName());
					
					if (staff.getStaffID().equals(model.getAsstDirector6ID()))
						model.setAsstDirector6Name(staff.getName());
				}
			}
			
			finalizeModelEntry(addOperation ? facade.addState(model) : facade.updateState(model), addOperation, facade, "negeri, ID " + model.getStateID(), null, models, model);
			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popup').hide()");
	}

	public void regionEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addRegionOperation ? facade.addRegion(region) : facade.updateRegion(region), addRegionOperation, facade, "daerah sivil, ID " + region.getRegionID(), null, model.getRegions(), region);
			region = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popupRegion').hide()");
	}
}