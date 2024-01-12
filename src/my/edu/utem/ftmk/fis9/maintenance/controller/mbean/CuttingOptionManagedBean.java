package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.CuttingOption;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "cuttingOptionMBean")
public class CuttingOptionManagedBean extends AbstractManagedBean<CuttingOption>
{
	private static final long serialVersionUID = VERSION;
	private ArrayList<State> states;

	public CuttingOptionManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			Staff user = getCurrentUser();
			int stateID = user.getStateID(), designationID = user.getDesignationID();

			if (stateID == 0)
			{
				if (designationID == 0)
				{
					models = facade.getCuttingOptions();
					states = facade.getStates();
				}
				else
					models = new ArrayList<>();
			}
			else
			{
				State state = facade.getState(stateID);
				models = facade.getCuttingOptions(state);
				states = new ArrayList<>();

				states.add(state);
			}

			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public CuttingOption getCuttingOption()
	{
		return model;
	}

	public void setCuttingOption(CuttingOption cuttingOption)
	{
		this.model = cuttingOption;
	}

	public ArrayList<CuttingOption> getCuttingOptions()
	{
		return models;
	}

	public void setCuttingOptions(ArrayList<CuttingOption> cuttingOptions)
	{
		this.models = cuttingOptions;
	}

	public ArrayList<State> getStates()
	{
		return states;
	}

	public void setStates(ArrayList<State> states)
	{
		this.states = states;
	}

	@Override
	public void handleOpen()
	{
		if (addOperation)
		{
			model = new CuttingOption();
			Staff user = getCurrentUser();
			int stateID = user.getStateID();

			if (stateID != 0)
				model.setStateID(stateID);
		}
		else
			model = (CuttingOption) copy(models, model);
	}

	public void cuttingOptionEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			for (State state : states)
				if (state.getStateID() == model.getStateID())
					model.setStateName(state.getName());

			finalizeModelEntry(addOperation ? facade.addCuttingOption(model) : facade.updateCuttingOption(model), addOperation, facade, "opsyen had tebangan, ID " + model.getCuttingOptionID(), null, models, model);
			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popup').hide()");
	}
}