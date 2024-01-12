package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.RegenerationSpecies;
import my.edu.utem.ftmk.fis9.maintenance.model.RegenerationType;
import my.edu.utem.ftmk.fis9.maintenance.model.Species;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "regenerationTypeMBean")
public class RegenerationTypeManagedBean extends AbstractManagedBean<RegenerationType>
{
	private static final long serialVersionUID = VERSION;
	private RegenerationSpecies regenerationSpecies;
	private ArrayList<State> states;
	private ArrayList<Species> speciesList;
	private boolean addRegenerationSpeciesOperation;

	public RegenerationTypeManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			speciesList = facade.getSpeciesList();
			Staff user = getCurrentUser();
			int stateID = user.getStateID(), designationID = user.getDesignationID();

			if (stateID == 0)
			{
				if (designationID == 0)
				{
					models = facade.getRegenerationTypes();
					states = facade.getStates();
				}
				else
					models = new ArrayList<>();
			}
			else
			{
				State state = facade.getState(stateID);
				models = facade.getRegenerationTypes(state);
				states = new ArrayList<>();

				states.add(state);
			}

			sort(models);

			for (RegenerationType regenerationType : models)
				sort(regenerationType.getRegenerationSpeciesList());
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public RegenerationType getRegenerationType()
	{
		return model;
	}

	public void setRegenerationType(RegenerationType regenerationType)
	{
		this.model = regenerationType;
	}

	public RegenerationSpecies getRegenerationSpecies()
	{
		return regenerationSpecies;
	}

	public void setRegenerationSpecies(RegenerationSpecies regenerationSpecies)
	{
		this.regenerationSpecies = regenerationSpecies;
	}

	public ArrayList<RegenerationType> getRegenerationTypes()
	{
		return models;
	}

	public void setRegenerationTypes(ArrayList<RegenerationType> regenerationTypes)
	{
		this.models = regenerationTypes;
	}

	public ArrayList<State> getStates()
	{
		return states;
	}

	public void setStates(ArrayList<State> states)
	{
		this.states = states;
	}

	public ArrayList<Species> getSpeciesList()
	{
		return speciesList;
	}

	public void setSpeciesList(ArrayList<Species> speciesList)
	{
		this.speciesList = speciesList;
	}

	public boolean isAddRegenerationSpeciesOperation()
	{
		return addRegenerationSpeciesOperation;
	}

	public void setAddRegenerationSpeciesOperation(boolean addOperationRegenerationSpecies)
	{
		this.addRegenerationSpeciesOperation = addOperationRegenerationSpecies;
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
			model = new RegenerationType();
			Staff user = getCurrentUser();
			int stateID = user.getStateID();

			if (stateID != 0)
				model.setStateID(stateID);
			
			model.setRegenerationSpeciesList(new ArrayList<>());
		}
		else
			model = (RegenerationType) copy(models, model);
	}

	public void handleOpenRegenerationSpecies()
	{
		if (addRegenerationSpeciesOperation)
		{
			regenerationSpecies = new RegenerationSpecies();
			regenerationSpecies.setRegenerationTypeID(model.getRegenerationTypeID());
		}
		else
			regenerationSpecies = (RegenerationSpecies) copy(model.getRegenerationSpeciesList(), regenerationSpecies);
	}

	public void regenerationTypeEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			for (State state : states)
				if (state.getStateID() == model.getStateID())
					model.setStateName(state.getName());

			finalizeModelEntry(addOperation ? facade.addRegenerationType(model) : facade.updateRegenerationType(model), addOperation, facade, "daftar pemulihan, ID " + model.getRegenerationTypeID(), null, models, model);
			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popup').hide()");
	}

	public void regenerationSpeciesEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			for (Species species : speciesList)
			{
				if (species.getSpeciesID() == regenerationSpecies.getSpeciesID())
				{
					regenerationSpecies.setCode(species.getCode());
					regenerationSpecies.setName(species.getName());
					regenerationSpecies.setScientificName(species.getScientificName());
					regenerationSpecies.setSpeciesTypeID(species.getSpeciesTypeID());
					regenerationSpecies.setTimberGroupID(species.getTimberGroupID());
					regenerationSpecies.setTimberTypeID(species.getTimberTypeID());
				}
			}

			finalizeModelEntry(addRegenerationSpeciesOperation ? facade.addRegenerationSpecies(regenerationSpecies) : facade.updateRegenerationSpecies(regenerationSpecies), addRegenerationSpeciesOperation, facade, "spesis pemulihan, ID " + regenerationSpecies.getRegenerationSpeciesID(), null, model.getRegenerationSpeciesList(), regenerationSpecies);
			regenerationSpecies = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popupRegenerationSpecies').hide()");
	}

	public void regenerationSpeciesDelete()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			if (facade.deleteRegenerationSpecies(regenerationSpecies) != 0)
			{
				addMessage(FacesMessage.SEVERITY_INFO, null, regenerationSpecies + " berjaya dipadamkan.");
				model.getRegenerationSpeciesList().remove(regenerationSpecies);
				log(facade, "Padam spesis pemulihan, ID " + regenerationSpecies.getRegenerationSpeciesID());
			}
			else
				addMessage(FacesMessage.SEVERITY_WARN, null, regenerationSpecies + " tidak dapat dipadamkan.");

			regenerationSpecies = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}
}