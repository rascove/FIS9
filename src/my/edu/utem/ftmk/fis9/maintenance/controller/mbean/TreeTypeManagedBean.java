package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.TreeType;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "treeTypeMBean")
public class TreeTypeManagedBean extends AbstractManagedBean<TreeType>
{
	private static final long serialVersionUID = VERSION;

	public TreeTypeManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getTreeTypes();
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public TreeType getTreeType()
	{
		return model;
	}

	public void setTreeType(TreeType treeType)
	{
		this.model = treeType;
	}

	public ArrayList<TreeType> getTreeTypes()
	{
		return models;
	}

	public void setTreeTypes(ArrayList<TreeType> treeTypes)
	{
		this.models = treeTypes;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new TreeType() : (TreeType) copy(models, model);
	}

	public void treeTypeEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addTreeType(model) : facade.updateTreeType(model), addOperation, facade, "jenis pokok, ID " + model.getTreeTypeID(), null, models, model);
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