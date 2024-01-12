package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;



import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.LicenseStatus;

/**
 * @author Nor Azman Mat Ariff
 */
@ViewScoped
@ManagedBean(name = "licenseStatusMBean")
public class LicenseStatusManagedBean extends AbstractManagedBean<LicenseStatus>
{
	private static final long serialVersionUID = VERSION;

	public LicenseStatusManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getLicenseStatuss("A");
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public LicenseStatus getLicenseStatus()
	{
		return model;
	}

	public void setLicenseStatus(LicenseStatus licenseStatus)
	{
		this.model = licenseStatus;
	}

	public ArrayList<LicenseStatus> getLicenseStatuss()
	{
		return models;
	}

	public void setLicenseStatuss(ArrayList<LicenseStatus> licenseStatuss)
	{
		this.models = licenseStatuss;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new LicenseStatus() : (LicenseStatus) copy(models, model);
	}

	public void licenseStatusEntry()
	{
		if(addOperation) model.setStatus("A");
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addLicenseStatus(model) : facade.updateLicenseStatus(model), addOperation, facade, "kumpulan keluaran, ID " + model.getLicenseStatusID(), null, models, model);
			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popup').hide()");
	}
	
	public void licenseStatusDelete()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			facade.deleteLicenseStatus(model);
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