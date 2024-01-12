package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.TaggingType;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "taggingTypeMBean")
public class TaggingTypeManagedBean extends AbstractManagedBean<TaggingType>
{
	private static final long serialVersionUID = VERSION;

	public TaggingTypeManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getTaggingTypes();
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public TaggingType getTaggingType()
	{
		return model;
	}

	public void setTaggingType(TaggingType taggingType)
	{
		this.model = taggingType;
	}

	public ArrayList<TaggingType> getTaggingTypes()
	{
		return models;
	}

	public void setTaggingTypes(ArrayList<TaggingType> taggingTypes)
	{
		this.models = taggingTypes;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new TaggingType() : (TaggingType) copy(models, model);
	}

	public void taggingTypeEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addTaggingType(model) : facade.updateTaggingType(model), addOperation, facade, "jenis penandaan, ID " + model.getTaggingTypeID(), null, models, model);
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