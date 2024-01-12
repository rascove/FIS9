package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;



import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.LicenseType;

/**
 * @author Nor Azman Mat Ariff
 */
@ViewScoped
@ManagedBean(name = "licenseTypeMBean")
public class LicenseTypeManagedBean extends AbstractManagedBean<LicenseType>
{
	private static final long serialVersionUID = VERSION;

	public LicenseTypeManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getLicenseTypes("A");
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public LicenseType getLicenseType()
	{
		return model;
	}

	public void setLicenseType(LicenseType licenseType)
	{
		this.model = licenseType;
	}

	public ArrayList<LicenseType> getLicenseTypes()
	{
		return models;
	}

	public void setLicenseTypes(ArrayList<LicenseType> licenseTypes)
	{
		this.models = licenseTypes;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new LicenseType() : (LicenseType) copy(models, model);
	}

	public void licenseTypeEntry()
	{
		if(addOperation) model.setStatus("A");
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addLicenseType(model) : facade.updateLicenseType(model), addOperation, facade, "kumpulan keluaran, ID " + model.getLicenseTypeID(), null, models, model);
			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popup').hide()");
	}
	
	public void licenseTypeDelete()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			facade.deleteLicenseType(model);
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