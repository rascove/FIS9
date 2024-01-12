package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.SpeciesType;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "speciesTypeMBean")
public class SpeciesTypeManagedBean extends AbstractManagedBean<SpeciesType>
{
	private static final long serialVersionUID = VERSION;

	public SpeciesTypeManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getSpeciesTypes();
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public SpeciesType getSpeciesType()
	{
		return model;
	}

	public void setSpeciesType(SpeciesType speciesType)
	{
		this.model = speciesType;
	}

	public ArrayList<SpeciesType> getSpeciesTypes()
	{
		return models;
	}

	public void setSpeciesTypes(ArrayList<SpeciesType> speciesTypes)
	{
		this.models = speciesTypes;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new SpeciesType() : (SpeciesType) copy(models, model);
	}

	public void speciesTypeEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addSpeciesType(model) : facade.updateSpeciesType(model), addOperation, facade, "jenis spesis, ID " + model.getSpeciesTypeID(), null, models, model);
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