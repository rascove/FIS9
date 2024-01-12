package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.TreeStatus;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "treeStatusMBean")
public class TreeStatusManagedBean extends AbstractManagedBean<TreeStatus>
{
	private static final long serialVersionUID = VERSION;

	public TreeStatusManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getTreeStatuses();
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public TreeStatus getTreeStatus()
	{
		return model;
	}

	public void setTreeStatus(TreeStatus treeStatus)
	{
		this.model = treeStatus;
	}

	public ArrayList<TreeStatus> getTreeStatuses()
	{
		return models;
	}

	public void setTreeStatuses(ArrayList<TreeStatus> treeStatuss)
	{
		this.models = treeStatuss;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new TreeStatus() : (TreeStatus) copy(models, model);
	}

	public void treeStatusEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addTreeStatus(model) : facade.updateTreeStatus(model), addOperation, facade, "keadaan pokok, ID " + model.getTreeStatusID(), null, models, model);
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