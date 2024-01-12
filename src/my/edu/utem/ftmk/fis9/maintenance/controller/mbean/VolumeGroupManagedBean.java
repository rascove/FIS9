package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.Species;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.maintenance.model.VolumeDiameter;
import my.edu.utem.ftmk.fis9.maintenance.model.VolumeGroup;
import my.edu.utem.ftmk.fis9.maintenance.model.VolumeSpecies;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "volumeGroupMBean")
public class VolumeGroupManagedBean extends AbstractManagedBean<VolumeGroup>
{
	private static final long serialVersionUID = VERSION;
	private VolumeSpecies volumeSpecies;
	private VolumeDiameter volumeDiameter;
	private ArrayList<State> states;
	private ArrayList<Species> speciesList;
	private boolean addVolumeSpeciesOperation;
	private boolean addVolumeDiameterOperation;

	public VolumeGroupManagedBean()
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
					models = facade.getVolumeGroups();
					states = facade.getStates();
				}
				else
					models = new ArrayList<>();
			}
			else
			{
				State state = facade.getState(stateID);
				models = facade.getVolumeGroups(state);
				states = new ArrayList<>();

				states.add(state);
			}

			sort(models);

			for (VolumeGroup volumeGroup : models)
				sort(volumeGroup.getVolumeSpeciesList());
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public VolumeGroup getVolumeGroup()
	{
		return model;
	}

	public void setVolumeGroup(VolumeGroup volumeGroup)
	{
		this.model = volumeGroup;
	}

	public VolumeSpecies getVolumeSpecies()
	{
		return volumeSpecies;
	}

	public void setVolumeSpecies(VolumeSpecies volumeSpecies)
	{
		this.volumeSpecies = volumeSpecies;
	}

	public VolumeDiameter getVolumeDiameter()
	{
		return volumeDiameter;
	}

	public void setVolumeDiameter(VolumeDiameter volumeDiameter)
	{
		this.volumeDiameter = volumeDiameter;
	}

	public ArrayList<VolumeGroup> getVolumeGroups()
	{
		return models;
	}

	public void setVolumeGroups(ArrayList<VolumeGroup> volumeGroups)
	{
		this.models = volumeGroups;
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

	public boolean isAddVolumeSpeciesOperation()
	{
		return addVolumeSpeciesOperation;
	}

	public void setAddVolumeSpeciesOperation(boolean addOperationVolumeSpecies)
	{
		this.addVolumeSpeciesOperation = addOperationVolumeSpecies;
	}
	
	public boolean isAddVolumeDiameterOperation()
	{
		return addVolumeDiameterOperation;
	}

	public void setAddVolumeDiameterOperation(boolean addVolumeDiameterOperation)
	{
		this.addVolumeDiameterOperation = addVolumeDiameterOperation;
	}

	public String getComponent()
	{
		return ":frmManager:table" + (model == null ? "" : ":" + models.indexOf(model) + ":subtable");
	}
	
	public String getComponent2()
	{
		return ":frmManager:table" + (model == null ? "" : ":" + models.indexOf(model) + ":subtable2");
	}

	@Override
	public void handleOpen()
	{
		if (addOperation)
		{
			model = new VolumeGroup();
			Staff user = getCurrentUser();
			int stateID = user.getStateID();

			if (stateID != 0)
				model.setStateID(stateID);
			
			model.setVolumeSpeciesList(new ArrayList<>());
			model.setVolumeDiameterList(new ArrayList<>());
		}
		else
			model = (VolumeGroup) copy(models, model);
	}

	public void handleOpenVolumeSpecies()
	{
		if (addVolumeSpeciesOperation)
		{
			volumeSpecies = new VolumeSpecies();
			volumeSpecies.setVolumeGroupID(model.getVolumeGroupID());
		}
		else
			volumeSpecies = (VolumeSpecies) copy(model.getVolumeSpeciesList(), volumeSpecies);
	}
	
	public void handleOpenVolumeDiameter()
	{
		if (addVolumeDiameterOperation)
		{
			volumeDiameter = new VolumeDiameter();
			volumeDiameter.setVolumeGroupID(model.getVolumeGroupID());
		}
		else
			volumeDiameter = (VolumeDiameter) copy(model.getVolumeDiameterList(), volumeDiameter);
	}

	public void volumeGroupEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			for (State state : states)
				if (state.getStateID() == model.getStateID())
					model.setStateName(state.getName());

			finalizeModelEntry(addOperation ? facade.addVolumeGroup(model) : facade.updateVolumeGroup(model), addOperation, facade, "jenis perlindungan, ID " + model.getVolumeGroupID(), null, models, model);
			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popup').hide()");
	}

	public void volumeSpeciesEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			for (Species species : speciesList)
			{
				if (species.getSpeciesID() == volumeSpecies.getSpeciesID())
				{
					volumeSpecies.setCode(species.getCode());
					volumeSpecies.setName(species.getName());
					volumeSpecies.setScientificName(species.getScientificName());
					volumeSpecies.setSpeciesTypeID(species.getSpeciesTypeID());
					volumeSpecies.setTimberGroupID(species.getTimberGroupID());
					volumeSpecies.setTimberTypeID(species.getTimberTypeID());
				}
			}

			finalizeModelEntry(addVolumeSpeciesOperation ? facade.addVolumeSpecies(volumeSpecies) : facade.updateVolumeSpecies(volumeSpecies), addVolumeSpeciesOperation, facade, "spesis perlindungan, ID " + volumeSpecies.getVolumeSpeciesID(), null, model.getVolumeSpeciesList(), volumeSpecies);
			volumeSpecies = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popupVolumeSpecies').hide()");
	}

	public void volumeSpeciesDelete()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			if (facade.deleteVolumeSpecies(volumeSpecies) != 0)
			{
				addMessage(FacesMessage.SEVERITY_INFO, null, volumeSpecies + " berjaya dipadamkan.");
				model.getVolumeSpeciesList().remove(volumeSpecies);
				log(facade, "Padam spesis perlindungan, ID " + volumeSpecies.getVolumeSpeciesID());
			}
			else
				addMessage(FacesMessage.SEVERITY_WARN, null, volumeSpecies + " tidak dapat dipadamkan.");

			volumeSpecies = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}
	
	public void volumeDiameterEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addVolumeDiameterOperation ? facade.addVolumeDiameter(volumeDiameter) : facade.updateVolumeDiameter(volumeDiameter), addVolumeDiameterOperation, facade, "sifir isipadu, ID " + volumeDiameter.getVolumeDiameterID(), null, model.getVolumeDiameterList(), volumeDiameter);
			volumeDiameter = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popupVolumeDiameter').hide()");
	}

	public void volumeDiameterDelete()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			if (facade.deleteVolumeDiameter(volumeDiameter) != 0)
			{
				addMessage(FacesMessage.SEVERITY_INFO, null, volumeDiameter + " berjaya dipadamkan.");
				model.getVolumeDiameterList().remove(volumeDiameter);
				log(facade, "Padam sifir isipadu, ID " + volumeDiameter.getVolumeDiameterID());
			}
			else
				addMessage(FacesMessage.SEVERITY_WARN, null, volumeDiameter + " tidak dapat dipadamkan.");

			volumeDiameter = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}
}