package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.global.util.StringProtector;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.Contractor;
import my.edu.utem.ftmk.fis9.maintenance.model.Designation;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "staffMBean")
public class StaffManagedBean extends AbstractManagedBean<Staff>
{
	private static final long serialVersionUID = VERSION;
	private ArrayList<Designation> designations;
	private ArrayList<State> states;
	private ArrayList<Contractor> contractors;
	private boolean resetPassword;

	public StaffManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			Staff user = getCurrentUser();
			designations = facade.getDesignations();
			states = facade.getStates();
			contractors = facade.getContractors("A");
			int stateID = user.getStateID(), designationID = user.getDesignationID();

			if (stateID == 0)
			{
				if (designationID == 0)
				{
					models = facade.getStaffs();
					State none = new State();

					none.setName("Tiada negeri");
					states.add(0, none);
				}
				else
					models = new ArrayList<>();
			}
			else
			{
				State state = new State();

				state.setStateID(user.getStateID());

				models = facade.getStaffs(state);
			}

			models.remove(user);
			sort(models);
			sort(designations);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public Staff getStaff()
	{
		return model;
	}

	public void setStaff(Staff staff)
	{
		this.model = staff;
	}

	public ArrayList<Staff> getStaffs()
	{
		return models;
	}

	public void setStaffs(ArrayList<Staff> staffs)
	{
		this.models = staffs;
	}

	public ArrayList<Designation> getDesignations()
	{
		return designations;
	}

	public void setDesignations(ArrayList<Designation> designations)
	{
		this.designations = designations;
	}

	public ArrayList<State> getStates()
	{
		return states;
	}

	public void setStates(ArrayList<State> states)
	{
		this.states = states;
	}

	public ArrayList<Contractor> getContractors()
	{
		return contractors;
	}

	public void setContractors(ArrayList<Contractor> contractors)
	{
		this.contractors = contractors;
	}

	public boolean isResetPassword()
	{
		return resetPassword;
	}

	public void setResetPassword(boolean resetPassword)
	{
		this.resetPassword = resetPassword;
	}

	@Override
	public void handleOpen()
	{
		if (addOperation)
		{
			model = new Staff();
			Staff user = getCurrentUser();

			model.setStatus(true);
			model.setStateID(user.getStateID());
		}
		else
			model = (Staff) copy(models, model);
	}

	public void handleOpenConfig()
	{
		model = (Staff) copy(models, model);
	}

	public void staffEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			for (Designation designation : designations)
				if (model.getDesignationID() == designation.getDesignationID())
					model.setDesignationName(designation.getName());

			for (State state : states)
				if (model.getStateID() == state.getStateID())
					model.setStateName(state.getName());

			if (addOperation)
			{
				model.setPassword(StringProtector.encrypt(model.getStaffID(), 1));
				finalizeModelEntry(facade.addStaff(model), addOperation, facade, "pekerja, ID " + model.getStaffID(), ", kerana no. pekerja telah direkodkan sebelumnya", models, model);
			}
			else
			{
				if (resetPassword)
					model.setPassword(StringProtector.encrypt(model.getStaffID(), 1));
				
				finalizeModelEntry(facade.updateStaff(model, resetPassword), addOperation, facade, "pekerja, ID " + model.getStaffID(), null, models, model);
			}

			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popup').hide()");
		execute("PF('popupConfig').hide()");
	}
}