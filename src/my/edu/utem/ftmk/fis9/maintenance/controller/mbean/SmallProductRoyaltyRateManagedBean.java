package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;



import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.SmallProduct;
import my.edu.utem.ftmk.fis9.maintenance.model.SmallProductRoyaltyRate;
import my.edu.utem.ftmk.fis9.maintenance.model.Unit;

/**
 * @author Nor Azman Mat Ariff
 */
@ViewScoped
@ManagedBean(name = "smallProductRoyaltyRateMBean")
public class SmallProductRoyaltyRateManagedBean extends AbstractManagedBean<SmallProductRoyaltyRate>
{
	private static final long serialVersionUID = VERSION;
	private ArrayList<SmallProduct> smallProducts;
	private ArrayList<Unit> units;

	public SmallProductRoyaltyRateManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			smallProducts = facade.getSmallProducts("A");
			units = facade.getUnits("A");
			models = facade.getSmallProductRoyaltyRates("A");
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public SmallProductRoyaltyRate getSmallProductRoyaltyRate()
	{
		return model;
	}

	public void setSmallProductRoyaltyRate(SmallProductRoyaltyRate smallProductRoyaltyRate)
	{
		this.model = smallProductRoyaltyRate;
	}

	public ArrayList<SmallProductRoyaltyRate> getSmallProductRoyaltyRates()
	{
		return models;
	}

	public void setSmallProductRoyaltyRates(ArrayList<SmallProductRoyaltyRate> smallProductRoyaltyRates)
	{
		this.models = smallProductRoyaltyRates;
	}

	public ArrayList<SmallProduct> getSmallProducts() {
		return smallProducts;
	}

	public void setSmallProducts(ArrayList<SmallProduct> smallProducts) {
		this.smallProducts = smallProducts;
	}

	public ArrayList<Unit> getUnits() {
		return units;
	}

	public void setUnits(ArrayList<Unit> units) {
		this.units = units;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new SmallProductRoyaltyRate() : (SmallProductRoyaltyRate) copy(models, model);
	}

	public void smallProductRoyaltyRateEntry()
	{
		if(addOperation)
		{
			model.setSmallProductRoyaltyRateID(System.currentTimeMillis());
			model.setStatus("A");
		}
		for (SmallProduct smallProduct : smallProducts)
		{
			if (smallProduct.getSmallProductID() == model.getSmallProductID())
			{
				model.setSmallProductCode(smallProduct.getCode());
				model.setSmallProductName(smallProduct.getName());
			}
		}
		for (Unit unit : units)
		{
			if (unit.getUnitID() == model.getUnitID())
			{
				model.setUnitCode(unit.getCode());
				model.setUnitName(unit.getName());
			}
		}	
		
	    for (ListIterator<SmallProductRoyaltyRate> itr = models.listIterator(); itr.hasNext();) 
	    {
	    	SmallProductRoyaltyRate smallProductRoyaltyRate = itr.next();
	    	if((smallProductRoyaltyRate.getSmallProductID() == model.getSmallProductID()) && (smallProductRoyaltyRate.getUnitID() == model.getUnitID()))
	    	{
				itr.remove();
	    	}
	    }
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addSmallProductRoyaltyRate(model) : 0, addOperation, facade, "kadar royalti keluaran kecil, ID " + model.getSmallProductRoyaltyRateID(), null, models, model);
			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popup').hide()");
	}
	
	public void smallProductRoyaltyRateDelete()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			facade.deleteSmallProductRoyaltyRate(model);
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