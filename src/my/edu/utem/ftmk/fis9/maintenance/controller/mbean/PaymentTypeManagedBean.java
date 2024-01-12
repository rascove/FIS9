package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;



import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.PaymentType;

/**
 * @author Nor Azman Mat Ariff
 */
@ViewScoped
@ManagedBean(name = "paymentTypeMBean")
public class PaymentTypeManagedBean extends AbstractManagedBean<PaymentType>
{
	private static final long serialVersionUID = VERSION;

	public PaymentTypeManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getPaymentTypes("A");
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public PaymentType getPaymentType()
	{
		return model;
	}

	public void setPaymentType(PaymentType paymentType)
	{
		this.model = paymentType;
	}

	public ArrayList<PaymentType> getPaymentTypes()
	{
		return models;
	}

	public void setPaymentTypes(ArrayList<PaymentType> paymentTypes)
	{
		this.models = paymentTypes;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new PaymentType() : (PaymentType) copy(models, model);
	}

	public void paymentTypeEntry()
	{
		if(addOperation) model.setStatus("A");
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addPaymentType(model) : facade.updatePaymentType(model), addOperation, facade, "kumpulan keluaran, ID " + model.getPaymentTypeID(), null, models, model);
			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popup').hide()");
	}
	
	public void paymentTypeDelete()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			facade.deletePaymentType(model);
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