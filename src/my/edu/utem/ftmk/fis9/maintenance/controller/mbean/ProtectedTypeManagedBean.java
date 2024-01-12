package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.ProtectedSpecies;
import my.edu.utem.ftmk.fis9.maintenance.model.ProtectedType;
import my.edu.utem.ftmk.fis9.maintenance.model.Species;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "protectedTypeMBean")
public class ProtectedTypeManagedBean extends AbstractManagedBean<ProtectedType>
{
	private static final long serialVersionUID = VERSION;
	private ProtectedSpecies protectedSpecies;
	private ArrayList<State> states;
	private ArrayList<Species> speciesList;
	private boolean addProtectedSpeciesOperation;

	public ProtectedTypeManagedBean()
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
					models = facade.getProtectedTypes();
					states = facade.getStates();
				}
				else
					models = new ArrayList<>();
			}
			else
			{
				State state = facade.getState(stateID);
				models = facade.getProtectedTypes(state);
				states = new ArrayList<>();

				states.add(state);
			}

			sort(models);

			for (ProtectedType protectedType : models)
				sort(protectedType.getProtectedSpeciesList());
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public ProtectedType getProtectedType()
	{
		return model;
	}

	public void setProtectedType(ProtectedType protectedType)
	{
		this.model = protectedType;
	}

	public ProtectedSpecies getProtectedSpecies()
	{
		return protectedSpecies;
	}

	public void setProtectedSpecies(ProtectedSpecies protectedSpecies)
	{
		this.protectedSpecies = protectedSpecies;
	}

	public ArrayList<ProtectedType> getProtectedTypes()
	{
		return models;
	}

	public void setProtectedTypes(ArrayList<ProtectedType> protectedTypes)
	{
		this.models = protectedTypes;
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

	public boolean isAddProtectedSpeciesOperation()
	{
		return addProtectedSpeciesOperation;
	}

	public void setAddProtectedSpeciesOperation(boolean addOperationProtectedSpecies)
	{
		this.addProtectedSpeciesOperation = addOperationProtectedSpecies;
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
			model = new ProtectedType();
			Staff user = getCurrentUser();
			int stateID = user.getStateID();

			if (stateID != 0)
				model.setStateID(stateID);
			
			model.setProtectedSpeciesList(new ArrayList<>());
		}
		else
			model = (ProtectedType) copy(models, model);
	}

	public void handleOpenProtectedSpecies()
	{
		if (addProtectedSpeciesOperation)
		{
			protectedSpecies = new ProtectedSpecies();
			protectedSpecies.setProtectedTypeID(model.getProtectedTypeID());
		}
		else
			protectedSpecies = (ProtectedSpecies) copy(model.getProtectedSpeciesList(), protectedSpecies);
	}

	public void protectedTypeEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			for (State state : states)
				if (state.getStateID() == model.getStateID())
					model.setStateName(state.getName());

			finalizeModelEntry(addOperation ? facade.addProtectedType(model) : facade.updateProtectedType(model), addOperation, facade, "jenis perlindungan, ID " + model.getProtectedTypeID(), null, models, model);
			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popup').hide()");
	}

	public void protectedSpeciesEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			for (Species species : speciesList)
			{
				if (species.getSpeciesID() == protectedSpecies.getSpeciesID())
				{
					protectedSpecies.setCode(species.getCode());
					protectedSpecies.setName(species.getName());
					protectedSpecies.setScientificName(species.getScientificName());
					protectedSpecies.setSpeciesTypeID(species.getSpeciesTypeID());
					protectedSpecies.setTimberGroupID(species.getTimberGroupID());
					protectedSpecies.setTimberTypeID(species.getTimberTypeID());
				}
			}

			finalizeModelEntry(addProtectedSpeciesOperation ? facade.addProtectedSpecies(protectedSpecies) : facade.updateProtectedSpecies(protectedSpecies), addProtectedSpeciesOperation, facade, "spesis perlindungan, ID " + protectedSpecies.getProtectedSpeciesID(), null, model.getProtectedSpeciesList(), protectedSpecies);
			protectedSpecies = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popupProtectedSpecies').hide()");
	}

	public void protectedSpeciesDelete()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			if (facade.deleteProtectedSpecies(protectedSpecies) != 0)
			{
				addMessage(FacesMessage.SEVERITY_INFO, null, protectedSpecies + " berjaya dipadamkan.");
				model.getProtectedSpeciesList().remove(protectedSpecies);
				log(facade, "Padam spesis perlindungan, ID " + protectedSpecies.getProtectedSpeciesID());
			}
			else
				addMessage(FacesMessage.SEVERITY_WARN, null, protectedSpecies + " tidak dapat dipadamkan.");

			protectedSpecies = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}
}