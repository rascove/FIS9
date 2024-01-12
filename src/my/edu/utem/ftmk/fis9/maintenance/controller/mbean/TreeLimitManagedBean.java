package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.maintenance.model.TreeLimit;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "treeLimitMBean")
public class TreeLimitManagedBean extends AbstractManagedBean<TreeLimit>
{
	private static final long serialVersionUID = VERSION;
	private ArrayList<State> states;
	private boolean hasTreeLimit;

	public TreeLimitManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			Staff user = getCurrentUser();
			int stateID = user.getStateID(), designationID = user.getDesignationID();

			if (stateID == 0)
			{
				if (designationID == 0)
				{
					models = facade.getTreeLimits();
					states = facade.getStates();
				}
				else
					models = new ArrayList<>();
			}
			else
			{
				State state = facade.getState(stateID);
				TreeLimit treeLimit = facade.getTreeLimit(state);

				models = new ArrayList<>();
				states = new ArrayList<>();

				states.add(state);

				if (treeLimit != null)
				{
					models.add(treeLimit);
					hasTreeLimit = true;
				}
			}

			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public TreeLimit getTreeLimit()
	{
		return model;
	}

	public void setTreeLimit(TreeLimit treeLimit)
	{
		this.model = treeLimit;
	}

	public ArrayList<State> getStates()
	{
		return states;
	}

	public void setStates(ArrayList<State> states)
	{
		this.states = states;
	}

	public ArrayList<TreeLimit> getTreeLimits()
	{
		return models;
	}

	public void setTreeLimits(ArrayList<TreeLimit> treeLimits)
	{
		this.models = treeLimits;
	}

	public boolean isHasTreeLimit()
	{
		return hasTreeLimit;
	}

	public void setHasTreeLimit(boolean hasTreeLimit)
	{
		this.hasTreeLimit = hasTreeLimit;
	}

	@Override
	public void handleOpen()
	{
		if (addOperation)
		{
			model = new TreeLimit();
			Staff user = getCurrentUser();
			int stateID = user.getStateID();

			if (stateID != 0)
				model.setStateID(stateID);
		}
		else
			model = (TreeLimit) copy(models, model);
	}

	public void treeLimitEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			for (State state : states)
				if (model.getStateID() == state.getStateID())
					model.setStateName(state.getName());

			finalizeModelEntry(addOperation ? facade.addTreeLimit(model) : facade.updateTreeLimit(model), addOperation, facade, "had pokok, ID " + model.getTreeLimitID(), ", kerana had pokok untuk negeri " + model.getStateName() + " telah direkodkan sebelumnya", models, model);
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