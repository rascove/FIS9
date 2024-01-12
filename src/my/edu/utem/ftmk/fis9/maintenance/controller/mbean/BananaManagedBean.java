package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.Banana;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "bananaMBean")
public class BananaManagedBean extends AbstractManagedBean<Banana>
{
	private static final long serialVersionUID = VERSION;

	public BananaManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getBananas();
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public Banana getBanana()
	{
		return model;
	}

	public void setBanana(Banana banana)
	{
		this.model = banana;
	}

	public ArrayList<Banana> getBananas()
	{
		return models;
	}

	public void setBananas(ArrayList<Banana> bananas)
	{
		this.models = bananas;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new Banana() : (Banana) copy(models, model);
	}

	public void bananaEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addBanana(model) : facade.updateBanana(model), addOperation, facade, "pisang, ID " + model.getBananaID(), null, models, model);
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