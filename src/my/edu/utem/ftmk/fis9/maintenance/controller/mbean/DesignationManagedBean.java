package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.Designation;
import my.edu.utem.ftmk.fis9.maintenance.model.Form;
import my.edu.utem.ftmk.fis9.maintenance.model.Module;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "designationMBean")
public class DesignationManagedBean extends AbstractManagedBean<Designation>
{
	private static final long serialVersionUID = VERSION;
	private ArrayList<SelectItem> formList;
	private LinkedHashMap<Integer, Form> formMap;
	private Integer[] selectedForms;

	public DesignationManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			ArrayList<Module> modules = facade.getModules();
			models = facade.getDesignations();
			formList = new ArrayList<>();
			formMap = new LinkedHashMap<>();

			for (Module module : modules)
			{
				ArrayList<Form> forms = module.getForms();
				int size = forms.size();

				if (size != 0)
				{
					SelectItem[] elements = new SelectItem[size];
					SelectItemGroup group = new SelectItemGroup(module.getName());

					for (int i = 0; i < size; i++)
					{
						Form form = forms.get(i);
						String name = form.getName();
						elements[i] = new SelectItem(form.getFormID(), name);

						formMap.put(form.getFormID(), form);
					}

					group.setSelectItems(elements);
					formList.add(group);
				}
			}

			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public Designation getDesignation()
	{
		return model;
	}

	public void setDesignation(Designation designation)
	{
		this.model = designation;
	}

	public ArrayList<Designation> getDesignations()
	{
		return models;
	}

	public void setDesignations(ArrayList<Designation> designations)
	{
		this.models = designations;
	}

	public ArrayList<SelectItem> getFormList()
	{
		return formList;
	}

	public void setFormList(ArrayList<SelectItem> formList)
	{
		this.formList = formList;
	}

	public Integer[] getSelectedForms()
	{
		return selectedForms;
	}

	public void setSelectedForms(Integer[] selectedForms)
	{
		this.selectedForms = selectedForms;
	}

	@Override
	public void handleOpen()
	{
		if (addOperation)
		{
			model = new Designation();
			model.setForms(new ArrayList<>());
		}
		else
			model = (Designation) copy(models, model);
	}

	public void handleOpenConfig()
	{
		model = (Designation) copy(models, model);
		ArrayList<Form> forms = model.getForms();
		int size = forms.size();
		selectedForms = new Integer[size];
		
		for (int i = 0; i < size; i++)
			selectedForms[i] = forms.get(i).getFormID();
	}

	public void designationEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			if (addOperation)
				finalizeModelEntry(facade.addDesignation(model), addOperation, facade, "jawatan, ID " + model.getDesignationID(), null, models, model);
			else
			{
				if (selectedForms != null)
				{
					ArrayList<Form> forms = new ArrayList<>();

					for (Integer selectedForm : selectedForms)
						forms.add(formMap.get(selectedForm));

					model.setForms(forms);
				}

				finalizeModelEntry(facade.updateDesignation(model), addOperation, facade, "jawatan, ID " + model.getDesignationID(), null, models, model);
			}

			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popup').hide()");
		execute("PF('popupConfig').hide()");
	}
}