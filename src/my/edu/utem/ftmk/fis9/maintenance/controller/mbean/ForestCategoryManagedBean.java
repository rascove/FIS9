package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;



import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.ForestCategory;

/**
 * @author Nor Azman Mat Ariff
 */
@ViewScoped
@ManagedBean(name = "forestCategoryMBean")
public class ForestCategoryManagedBean extends AbstractManagedBean<ForestCategory>
{
	private static final long serialVersionUID = VERSION;

	public ForestCategoryManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getForestCategorys("A");
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public ForestCategory getForestCategory()
	{
		return model;
	}

	public void setForestCategory(ForestCategory forestCategory)
	{
		this.model = forestCategory;
	}

	public ArrayList<ForestCategory> getForestCategorys()
	{
		return models;
	}

	public void setForestCategorys(ArrayList<ForestCategory> forestCategorys)
	{
		this.models = forestCategorys;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new ForestCategory() : (ForestCategory) copy(models, model);
	}

	public void forestCategoryEntry()
	{
		if(addOperation) model.setStatus("A");
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addForestCategory(model) : facade.updateForestCategory(model), addOperation, facade, "kumpulan keluaran, ID " + model.getForestCategoryID(), null, models, model);
			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popup').hide()");
	}
	
	public void forestCategoryDelete()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			facade.deleteForestCategory(model);
			models.remove(model);
			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popupDelete').hide()");
	}
}