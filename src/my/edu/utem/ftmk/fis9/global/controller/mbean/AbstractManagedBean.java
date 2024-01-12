package my.edu.utem.ftmk.fis9.global.controller.mbean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Collections;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.primefaces.component.datatable.DataTable;

import my.edu.utem.ftmk.fis9.global.DefaultSerializable;
import my.edu.utem.ftmk.fis9.global.controller.manager.AbstractFacade;
import my.edu.utem.ftmk.fis9.global.model.AbstractModel;
import my.edu.utem.ftmk.fis9.global.model.TrailLog;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;

/**
 * @author Satrya Fajri Pratama
 */
public abstract class AbstractManagedBean<Model extends AbstractModel> implements DefaultSerializable
{
	private static final long serialVersionUID = VERSION;
	private int index;
	protected Model model;
	protected ArrayList<Model> models;
	protected boolean addOperation;

	protected final void addMessage(FacesMessage.Severity severity, String target, String message)
	{
		FacesContext.getCurrentInstance().addMessage(target, new FacesMessage(severity, "", message));
	}

	protected final void addMessage(Exception e)
	{
		if (e instanceof SQLIntegrityConstraintViolationException)
		{

		}
		else if (e instanceof InvalidClassException)
			addMessage(FacesMessage.SEVERITY_WARN, null, "Versi data yang dimuat naik tidak sama.");
		else
			addMessage(FacesMessage.SEVERITY_ERROR, null, "Sila maklumkan System Administrator mengenai ralat sistem berikut: " + e.getMessage());
	}

	protected final void execute(String command)
	{
		//RequestContext.getCurrentInstance().execute(command);
		PrimeFaces.current().executeScript(command);
	}

	protected final void update(String... components)
	{
		//RequestContext.getCurrentInstance().update(command);
		PrimeFaces.current().ajax().update(components);
	}

	protected final Staff getCurrentUser()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		UserManagedBean userMBean = context.getApplication().evaluateExpressionGet(context, "#{userMBean}", UserManagedBean.class);

		return userMBean.getUser();
	}

	protected final int log(AbstractFacade facade, String operation) throws SQLException
	{
		TrailLog trailLog = new TrailLog();

		trailLog.setOperation(operation);
		trailLog.setStaffID(getCurrentUser().getStaffID());

		return facade.addTrailLog(trailLog);
	}

	protected final <X extends AbstractModel> String finalizeModelEntry(int status, boolean addOperation, AbstractFacade facade, String operation, ArrayList<X> models, X model) throws SQLException
	{
		String message = null;

		if (status != 0)
		{
			if (addOperation)
			{
				message = model + " berjaya ditambahkan.";

				log(facade, "Tambah " + operation);

				if (models != null)
					models.add(model);
			}
			else
			{
				message = model + " berjaya dikemaskini.";

				log(facade, "Kemaskini " + operation);

				if (models != null)
					models.set(index, model);
			}

			if (models != null)
				sort(models);
		}

		return message;
	}

	protected final <X extends AbstractModel> void finalizeModelEntry(int status, boolean addOperation, AbstractFacade facade, String operation, String message, ArrayList<X> models, X model) throws SQLException
	{
		String value = finalizeModelEntry(status, addOperation, facade, operation, models, model);

		if (value != null)
			addMessage(FacesMessage.SEVERITY_INFO, null, value);
		else
			addMessage(FacesMessage.SEVERITY_WARN, null, model + " tidak dapat " + (addOperation ? "ditambahkan" : "dikemaskini") + (message == null ? "" : message) + ".");
	}

	protected final <X extends AbstractModel> Object copy(ArrayList<X> models, X model)
	{
		Object object = null;

		try
		{
			index = models.indexOf(model);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);

			oos.writeObject(model);
			oos.flush();
			oos.close();
			bos.close();

			byte[] byteData = bos.toByteArray();
			ByteArrayInputStream bais = new ByteArrayInputStream(byteData);
			object = new ObjectInputStream(bais).readObject();
		}
		catch (Exception e)
		{
		}

		return object;
	}

	public final boolean isAddOperation()
	{
		return addOperation;
	}

	public final void setAddOperation(boolean addOperation)
	{
		this.addOperation = addOperation;
	}

	public abstract void handleOpen();

	public final void clearFilter()
	{
		DataTable table = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(":frmManager:table");

		if (table != null)
		{
			table.setValueExpression("sortBy", null);
			table.setValueExpression("filterBy", null);
			table.reset();
		}
	}

	public final void sort(ArrayList<? extends AbstractModel> models)
	{
		Collections.sort(models);
	}

	public final void doNothing()
	{
	}
}