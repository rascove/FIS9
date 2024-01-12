package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;



import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.ChequeType;

/**
 * @author Nor Azman Mat Ariff
 */
@ViewScoped
@ManagedBean(name = "chequeTypeMBean")
public class ChequeTypeManagedBean extends AbstractManagedBean<ChequeType>
{
	private static final long serialVersionUID = VERSION;

	public ChequeTypeManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getChequeTypes("A");
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public ChequeType getChequeType()
	{
		return model;
	}

	public void setChequeType(ChequeType chequeType)
	{
		this.model = chequeType;
	}

	public ArrayList<ChequeType> getChequeTypes()
	{
		return models;
	}

	public void setChequeTypes(ArrayList<ChequeType> chequeTypes)
	{
		this.models = chequeTypes;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new ChequeType() : (ChequeType) copy(models, model);
	}

	public void chequeTypeEntry()
	{
		if(addOperation) model.setStatus("A");
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addChequeType(model) : facade.updateChequeType(model), addOperation, facade, "kumpulan keluaran, ID " + model.getChequeTypeID(), null, models, model);
			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popup').hide()");
	}
	
	public void chequeTypeDelete()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			facade.deleteChequeType(model);
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