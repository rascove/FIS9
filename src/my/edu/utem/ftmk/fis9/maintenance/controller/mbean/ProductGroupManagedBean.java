package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;



import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.ProductGroup;

/**
 * @author Nor Azman Mat Ariff
 */
@ViewScoped
@ManagedBean(name = "productGroupMBean")
public class ProductGroupManagedBean extends AbstractManagedBean<ProductGroup>
{
	private static final long serialVersionUID = VERSION;

	public ProductGroupManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getProductGroups("A");
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public ProductGroup getProductGroup()
	{
		return model;
	}

	public void setProductGroup(ProductGroup productGroup)
	{
		this.model = productGroup;
	}

	public ArrayList<ProductGroup> getProductGroups()
	{
		return models;
	}

	public void setProductGroups(ArrayList<ProductGroup> productGroups)
	{
		this.models = productGroups;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new ProductGroup() : (ProductGroup) copy(models, model);
	}

	public void productGroupEntry()
	{
		if(addOperation) model.setStatus("A");
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addProductGroup(model) : facade.updateProductGroup(model), addOperation, facade, "kumpulan keluaran, ID " + model.getProductGroupID(), null, models, model);
			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popup').hide()");
	}
	
	public void productGroupDelete()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			facade.deleteProductGroup(model);
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