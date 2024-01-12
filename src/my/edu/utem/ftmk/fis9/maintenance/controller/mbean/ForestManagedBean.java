package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.District;
import my.edu.utem.ftmk.fis9.maintenance.model.Forest;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "forestMBean")
public class ForestManagedBean extends AbstractManagedBean<Forest>
{
	private static final long serialVersionUID = VERSION;
	private ArrayList<State> states;
	private ArrayList<SelectItem> stateList;
	private ArrayList<SelectItem> districtList;

	public ForestManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			Staff user = getCurrentUser();
			int stateID = user.getStateID(), designationID = user.getDesignationID();

			if (stateID == 0)
			{
				if (designationID == 0)
				{
					states = facade.getStates();
					models = facade.getForests();
					stateList = new ArrayList<>();
					districtList = new ArrayList<>();

					for (State state : states)
					{
						ArrayList<District> districts = facade.getDistricts(state);
						int size = districts.size();

						state.setDistricts(districts);
						stateList.add(new SelectItem(state.getName(), state.getName()));

						if (size != 0)
						{
							SelectItemGroup group = new SelectItemGroup(state.getName());
							SelectItem[] items = new SelectItem[size];

							for (int i = 0; i < size; i++)
							{
								District district = districts.get(i);
								items[i] = new SelectItem(district.getDistrictID(), district.getName());
							}

							group.setSelectItems(items);
							districtList.add(group);
						}
					}
				}
				else
					models = new ArrayList<>();
			}
			else
			{
				State state = facade.getState(stateID);
				states = new ArrayList<>();
				districtList = new ArrayList<>();
				models = facade.getForests(state);
				ArrayList<District> districts = facade.getDistricts(state);

				state.setDistricts(districts);
				states.add(state);

				for (District district : districts)
					districtList.add(new SelectItem(district.getDistrictID(), district.getName()));
			}

			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public Forest getForest()
	{
		return model;
	}

	public void setForest(Forest forest)
	{
		this.model = forest;
	}

	public ArrayList<State> getStates()
	{
		return states;
	}

	public void setStates(ArrayList<State> states)
	{
		this.states = states;
	}

	public ArrayList<Forest> getForests()
	{
		return models;
	}

	public void setForests(ArrayList<Forest> forests)
	{
		this.models = forests;
	}

	public ArrayList<SelectItem> getDistrictList()
	{
		return districtList;
	}

	public void setDistrictList(ArrayList<SelectItem> districtList)
	{
		this.districtList = districtList;
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
		model = addOperation ? new Forest() : (Forest) copy(models, model);
	}

	public void forestEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			for (State state : states)
			{
				ArrayList<District> districts = state.getDistricts();

				for (District district : districts)
				{
					if (model.getDistrictID() == district.getDistrictID())
					{
						model.setDistrictName(district.getName());
						model.setStateName(state.getName());
					}
				}
			}

			finalizeModelEntry(addOperation ? facade.addForest(model) : facade.updateForest(model), addOperation, facade, "hutan simpan, ID " + model.getForestID(), null, models, model);
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