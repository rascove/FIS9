package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.Silara;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "silaraMBean")
public class SilaraManagedBean extends AbstractManagedBean<Silara>
{
	private static final long serialVersionUID = VERSION;

	public SilaraManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getSilaras();
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public Silara getSilara()
	{
		return model;
	}

	public void setSilara(Silara silara)
	{
		this.model = silara;
	}

	public ArrayList<Silara> getSilaras()
	{
		return models;
	}

	public void setSilaras(ArrayList<Silara> silaras)
	{
		this.models = silaras;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new Silara() : (Silara) copy(models, model);
	}

	public void silaraEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addSilara(model) : facade.updateSilara(model), addOperation, facade, "silara, ID " + model.getSilaraID(), null, models, model);
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