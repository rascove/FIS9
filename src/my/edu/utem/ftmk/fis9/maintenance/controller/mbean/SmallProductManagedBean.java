package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;



import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.ProductGroup;
import my.edu.utem.ftmk.fis9.maintenance.model.SmallProduct;

/**
 * @author Nor Azman Mat Ariff
 */
@ViewScoped
@ManagedBean(name = "smallProductMBean")
public class SmallProductManagedBean extends AbstractManagedBean<SmallProduct>
{
	private static final long serialVersionUID = VERSION;
	private ArrayList<ProductGroup> productGroups;

	public SmallProductManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			productGroups = facade.getProductGroups("A");
			models = facade.getSmallProducts("A");
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public SmallProduct getSmallProduct()
	{
		return model;
	}

	public void setSmallProduct(SmallProduct smallProduct)
	{
		this.model = smallProduct;
	}

	public ArrayList<SmallProduct> getSmallProducts()
	{
		return models;
	}

	public void setSmallProducts(ArrayList<SmallProduct> smallProducts)
	{
		this.models = smallProducts;
	}
	

	public ArrayList<ProductGroup> getProductGroups() 
	{
		return productGroups;
	}

	public void setProductGroups(ArrayList<ProductGroup> productGroups) 
	{
		this.productGroups = productGroups;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new SmallProduct() : (SmallProduct) copy(models, model);
	}

	public void smallProductEntry()
	{
		for (ProductGroup productGroup : productGroups)
			if (productGroup.getProductGroupID() == model.getProductGroupID())
				model.setProductGroupName(productGroup.getName());
		if(addOperation) model.setStatus("A");

		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addSmallProduct(model) : facade.updateSmallProduct(model), addOperation, facade, "keluaran kecil, ID " + model.getSmallProductID(), null, models, model);
			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popup').hide()");
	}
	
	public void smallProductDelete()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			facade.deleteSmallProduct(model);
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