package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.Species;
import my.edu.utem.ftmk.fis9.maintenance.model.SpeciesType;
import my.edu.utem.ftmk.fis9.maintenance.model.TimberGroup;
import my.edu.utem.ftmk.fis9.maintenance.model.TimberType;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "speciesMBean")
public class SpeciesManagedBean extends AbstractManagedBean<Species>
{
	private static final long serialVersionUID = VERSION;
	private ArrayList<SpeciesType> speciesTypes;
	private ArrayList<TimberGroup> timberGroups;
	private ArrayList<TimberType> timberTypes;

	public SpeciesManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getSpeciesList();
			speciesTypes = facade.getSpeciesTypes();
			timberGroups = facade.getTimberGroups();
			timberTypes = facade.getTimberTypes();

			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public Species getSpecies()
	{
		return model;
	}

	public void setSpecies(Species species)
	{
		this.model = species;
	}

	public ArrayList<Species> getSpeciesList()
	{
		return models;
	}

	public void setSpeciesList(ArrayList<Species> speciesList)
	{
		this.models = speciesList;
	}

	public ArrayList<SpeciesType> getSpeciesTypes()
	{
		return speciesTypes;
	}

	public void setSpeciesTypes(ArrayList<SpeciesType> speciesTypes)
	{
		this.speciesTypes = speciesTypes;
	}

	public ArrayList<TimberGroup> getTimberGroups()
	{
		return timberGroups;
	}

	public void setTimberGroups(ArrayList<TimberGroup> timberGroups)
	{
		this.timberGroups = timberGroups;
	}

	public ArrayList<TimberType> getTimberTypes()
	{
		return timberTypes;
	}

	public void setTimberTypes(ArrayList<TimberType> timberTypes)
	{
		this.timberTypes = timberTypes;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new Species() : (Species) copy(models, model);
	}

	public void speciesEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			if (model.getCode().trim().isEmpty())
				model.setCode(null);
			
			if (model.getSymbol().trim().isEmpty())
				model.setSymbol(null);

			for (SpeciesType speciesType : speciesTypes)
				if (speciesType.getSpeciesTypeID() == model.getSpeciesTypeID())
					model.setSpeciesTypeName(speciesType.getName());

			for (TimberGroup timberGroup : timberGroups)
				if (timberGroup.getTimberGroupID() == model.getTimberGroupID())
					model.setTimberGroupName(timberGroup.getName());

			for (TimberType timberType : timberTypes)
				if (timberType.getTimberTypeID() == model.getTimberTypeID())
					model.setTimberTypeName(timberType.getName());

			finalizeModelEntry(addOperation ? facade.addSpecies(model) : facade.updateSpecies(model), addOperation, facade, "spesis, ID " + model.getSpeciesID(), null, models, model);
			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popup').hide()");
	}
	
	public StreamedContent download()
	{
		String name = "Spesis.cdo";
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		File file = new File(external.getRealPath("/") + "files/maintenance/" + name);
		StreamedContent content = null;

		file.getParentFile().mkdirs();

		try (ObjectOutputStream oos = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(file))))
		{
			oos.writeInt(11);
			oos.writeObject(models.toArray(new Species[0]));
			content = new DefaultStreamedContent(new FileInputStream(file), "application/octet-stream", name);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		return content;
	}
	
	public void upload(FileUploadEvent event)
	{
		UploadedFile file = event.getFile();

		if (file != null)
		{
			try (ObjectInputStream ois = new ObjectInputStream(new GZIPInputStream(file.getInputstream())); MaintenanceFacade facade = new MaintenanceFacade();)
			{
				if (ois.readInt() != 11)
					throw new InvalidClassException("Not species");
				
				Species[] speciesList = (Species[]) ois.readObject();
				int add = 0, update = 0;
				
				for (Species species : speciesList)
				{
					boolean addOperation = !models.contains(species);
					int status = addOperation ? facade.addSpecies(species) : facade.updateSpecies(species);
					
					finalizeModelEntry(status, addOperation, facade, "spesis, ID " + species.getSpeciesID(), models, species);
					
					if (status != 0)
					{
						if (addOperation)
							add++;
						else
							update++;
					}
				}
				
				addMessage(FacesMessage.SEVERITY_INFO, null, add + " spesis berjaya ditambahkan dan " + update + " spesis berjaya dikemaskini.");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				addMessage(e);
			}
		}
	}
}