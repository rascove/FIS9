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
import my.edu.utem.ftmk.fis9.maintenance.model.District;
import my.edu.utem.ftmk.fis9.maintenance.model.Hammer;
import my.edu.utem.ftmk.fis9.maintenance.model.HammerType;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "hammerMBean")
public class HammerManagedBean extends AbstractManagedBean<Hammer>
{
	private static final long serialVersionUID = VERSION;
	private Contractor contractor;
	private ArrayList<HammerType> hammerTypes;
	private ArrayList<District> districts;
	private ArrayList<Contractor> contractors;
	private ArrayList<SelectItem> districtList;

	public HammerManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			Staff user = getCurrentUser();
			int stateID = user.getStateID(), designationID = user.getDesignationID();
			hammerTypes = facade.getHammerTypes();

			if (stateID == 0)
			{
				if (designationID == 0)
				{
					districtList = new ArrayList<>();
					districts = new ArrayList<>();
					models = facade.getHammers();
					contractors = facade.getContractors("A");
					ArrayList<State> states = facade.getStates();

					for (State state : states)
					{
						ArrayList<District> districts = facade.getDistricts(state);
						int size = districts.size();

						this.districts.addAll(districts);

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
				{
					contractor = facade.getContractor(user.getContractorID());
					models = contractor == null ? new ArrayList<>() : facade.getHammers(contractor);
				}
			}
			else
			{
				State state = facade.getState(user.getStateID());

				if (state.getDirectorID().equals(user.getStaffID()) || user.isAdministrative())
				{
					districtList = new ArrayList<>();
					models = facade.getHammers(state);
					districts = facade.getDistricts(state);

					for (District district : districts)
						districtList.add(new SelectItem(district.getDistrictID(), district.getName()));
				}
				else
				{
					District district = facade.getDistrict(user);
					models = facade.getHammers(district);
					districts = new ArrayList<>();

					districts.add(district);
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

	public Contractor getContractor()
	{
		return contractor;
	}

	public void setContractor(Contractor contractor)
	{
		this.contractor = contractor;
	}

	public Hammer getHammer()
	{
		return model;
	}

	public void setHammer(Hammer hammer)
	{
		this.model = hammer;
	}

	public ArrayList<Hammer> getHammers()
	{
		return models;
	}

	public void setHammers(ArrayList<Hammer> hammers)
	{
		this.models = hammers;
	}

	public ArrayList<HammerType> getHammerTypes()
	{
		return hammerTypes;
	}

	public void setHammerTypes(ArrayList<HammerType> hammerTypes)
	{
		this.hammerTypes = hammerTypes;
	}

	public ArrayList<District> getDistricts()
	{
		return districts;
	}

	public void setDistricts(ArrayList<District> districts)
	{
		this.districts = districts;
	}

	public ArrayList<Contractor> getContractors()
	{
		return contractors;
	}

	public void setContractors(ArrayList<Contractor> contractors)
	{
		this.contractors = contractors;
	}

	public ArrayList<SelectItem> getDistrictList()
	{
		return districtList;
	}

	public void setDistrictList(ArrayList<SelectItem> districtList)
	{
		this.districtList = districtList;
	}

	@Override
	public void handleOpen()
	{
		if (addOperation)
		{
			model = new Hammer();

			if (districtList == null)
			{
				if (contractor == null)
					model.setDistrictID(districts.get(0).getDistrictID());
				else
					model.setContractorID(contractor.getContractorID());
			}
		}
		else
			model = (Hammer) copy(models, model);
	}

	public void hammerEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			if (model.getHammerTypeID() != 0)
			{
				for (HammerType hammerType : hammerTypes)
					if (hammerType.getHammerTypeID() == model.getHammerTypeID())
						model.setHammerTypeName(hammerType.getName());
				
				for (District district : districts)
				{
					if (district.getDistrictID() == model.getDistrictID())
					{
						model.setDistrictName(district.getName());
						model.setStateName(district.getStateName());
						model.setContractorID(null);
						model.setContractorName(null);
					}
				}
			}
			else
			{
				Contractor c = contractor;
				
				if (contractors != null)
				{
					for (Contractor contractor : contractors)
						if (contractor.getContractorID().equals(model.getContractorID()))
							c = contractor;
				}
				
				model.setContractorName(c.getCompanyName());
				model.setDistrictID(0);
				model.setDistrictName(null);
				model.setStateName(null);
			}

			finalizeModelEntry(addOperation ? facade.addHammer(model) : facade.updateHammer(model), addOperation, facade, "tukul, ID " + model.getHammerNo(), ", kerana no. tukul telah direkodkan sebelumnya", models, model);
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