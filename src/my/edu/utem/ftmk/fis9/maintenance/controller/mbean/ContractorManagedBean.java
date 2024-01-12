package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.Contractor;
import my.edu.utem.ftmk.fis9.maintenance.model.Region;
import my.edu.utem.ftmk.fis9.maintenance.model.State;

/**
 * @author Nor Azman Mat Ariff (original)
 * @author Satrya Fajri Pratama (modified)
 */
@ViewScoped
@ManagedBean(name = "contractorMBean")
public class ContractorManagedBean extends AbstractManagedBean<Contractor>
{
	private static final long serialVersionUID = VERSION;
	private ArrayList<State> states;
	private ArrayList<SelectItem> stateList;

	public ContractorManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getContractors("A");
			states = facade.getStates();
			stateList = new ArrayList<>();

			sort(models);

			for (State state : states)
			{
				ArrayList<Region> regions = state.getRegions();
				int size = regions.size();

				if (size != 0)
				{
					SelectItemGroup group = new SelectItemGroup(state.getName());
					SelectItem[] items = new SelectItem[size];

					for (int i = 0; i < size; i++)
					{
						Region region = regions.get(i);
						items[i] = new SelectItem(region.getRegionID(), region.getName());
					}

					group.setSelectItems(items);
					stateList.add(group);
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public Contractor getContractor()
	{
		return model;
	}

	public void setContractor(Contractor contractor)
	{
		this.model = contractor;
	}

	public ArrayList<Contractor> getContractors()
	{
		return models;
	}

	public void setContractors(ArrayList<Contractor> contractors)
	{
		this.models = contractors;
	}

	public ArrayList<State> getStates()
	{
		return states;
	}

	public void setStates(ArrayList<State> states)
	{
		this.states = states;
	}

	public ArrayList<SelectItem> getStateList()
	{
		return stateList;
	}

	public void setStateList(ArrayList<SelectItem> stateList)
	{
		this.stateList = stateList;
	}

	@Override
	public void handleOpen()
	{
		if (addOperation)
		{
			model = new Contractor();
			model.setStatus("A");
		}
		else
			model = (Contractor) copy(models, model) ;
	}

	public void contractorEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			for (State state : states)
			{
				ArrayList<Region> regions = state.getRegions();

				for (Region region : regions)
					if (model.getRegionID() == region.getRegionID())
						model.setRegionName(region.getName() + ", " + state.getName());
			}

			finalizeModelEntry(addOperation ? facade.addContractor(model) : facade.updateContractor(model), addOperation, facade, "kontraktor, ID " + model.getContractorID(), ", kerana no. pendaftaran syarikat telah direkodkan sebelumnya", models, model);
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