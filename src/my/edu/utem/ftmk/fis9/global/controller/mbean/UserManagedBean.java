package my.edu.utem.ftmk.fis9.global.controller.mbean;

import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;

import com.ocpsoft.pretty.PrettyContext;
import com.ocpsoft.pretty.faces.config.PrettyConfig;
import com.ocpsoft.pretty.faces.config.mapping.UrlMapping;

import my.edu.utem.ftmk.fis9.global.model.TrailLog;
import my.edu.utem.ftmk.fis9.global.util.StringProtector;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.Form;
import my.edu.utem.ftmk.fis9.maintenance.model.Module;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;

/**
 * @author Satrya Fajri Pratama
 */
@SessionScoped
@ManagedBean(name="userMBean")
public class UserManagedBean extends AbstractManagedBean<Staff>
{
	private static final long serialVersionUID = VERSION;
	private String staffID, password, picture, role;
	private DefaultMenuModel menu;
	private ArrayList<String> authorized;
	private LinkedHashMap<String, String> icons, headers;
	
	public Staff getUser()
	{
		return model;
	}

	public void setUser(Staff user)
	{
		this.model = user;
		
		if (user == null)
		{
			menu = null;
			authorized = null;
			icons = headers = null;
			picture = role = null;
		}
	}

	public String getStaffID()
	{
		return staffID;
	}

	public void setStaffID(String staffID)
	{
		this.staffID = staffID;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public DefaultMenuModel getMenu()
	{
		return menu;
	}

	public ArrayList<String> getAuthorized()
	{
		return authorized;
	}

	public LinkedHashMap<String, String> getIcons()
	{
		return icons;
	}

	public LinkedHashMap<String, String> getHeaders()
	{
		return headers;
	}

	public String getPicture()
	{
		return picture;
	}

	public String getRole()
	{
		return role;
	}

	public String login()
	{
		String page = null;

		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			model = facade.getStaff(staffID, StringProtector.encrypt(password, 1));
			staffID = password = null;

			if (model != null)
			{
				if (model.getStatus())
				{
					page = "pretty:secured";
					menu = new DefaultMenuModel();
					authorized = new ArrayList<>();
					icons = new LinkedHashMap<>();
					headers = new LinkedHashMap<>();
					TrailLog trailLog = new TrailLog();
					ArrayList<Form> temps = model.getForms();
					ArrayList<Module> modules = facade.getModules();
					PrettyConfig config = PrettyContext.getCurrentInstance().getConfig();
					ExternalContext external = FacesContext.getCurrentInstance().getExternalContext();
					File file = new File(external.getRealPath("/") + "files/user/" + model.getStaffID() + ".png");
					String path = external.getRequestContextPath();
					int stateID = model.getStateID(), designationID = model.getDesignationID();
					boolean admin = model.isAdministrative();

					trailLog.setOperation("Log masuk");
					trailLog.setStaffID(model.getStaffID());
					facade.addTrailLog(trailLog);

					menu.addElement(new DefaultMenuItem("Utama", "fa fa-home", path + "/utama"));
					authorized.add("/utama");
					authorized.add("/400");

					if (file.exists())
						picture = "background-image: url('" + path + "/files/user/" + model.getStaffID() + ".png');";
					
					if (designationID == 0 || admin)
						role = "admin";
					else if (designationID >= 1 && designationID <= 15)
						role = "department";
					else if (designationID == 16 || designationID == 17)
						role = "district";
					else if (designationID == 18)
						role = "range";
					else if (designationID == 19 || designationID == 20)
						role = "mobile";
					else if (designationID == 21)
						role = "hall";
					else if (designationID >= 25 && designationID <= 27)
						role = "finance";
					else if (designationID == 28)
						role = "contract";
					
					for (Module module : modules)
					{
						DefaultSubMenu submenu = null;
						ArrayList<Form> forms = module.getForms();
						LinkedHashMap<String, DefaultSubMenu> categories = new LinkedHashMap<>();

						for (Form form : forms)
						{
							if (stateID == 0 && designationID == 0 || admin || temps.contains(form))
							{
								if (submenu == null)
									submenu = new DefaultSubMenu(module.getName(), "fa fa-bars");

								String category = form.getCategory();
								UrlMapping url = config.getMappingById(form.getPath());

								if (category != null)
								{
									DefaultSubMenu subcat = categories.get(category);

									if (subcat == null)
									{
										categories.put(category, subcat = new DefaultSubMenu(category, "fa fa-list-ul"));
										submenu.addElement(subcat);
									}

									if (url != null)
									{
										String viewID = url.getViewId(), name = form.getName(), icon = form.getIcon();
										
										subcat.addElement(new DefaultMenuItem(name, "fa fa-" + icon, path + url.getPattern()));
										authorized.add(url.getPattern());
										icons.put(viewID, icon);
										headers.put(viewID, name);
									}
								}
								else
								{
									if (url != null)
									{
										String viewID = url.getViewId(), name = form.getName(), icon = form.getIcon();
										
										submenu.addElement(new DefaultMenuItem(name, "fa fa-" + icon, path + url.getPattern()));
										authorized.add(url.getPattern());
										icons.put(viewID, icon);
										headers.put(viewID, name);
									}
								}
							}
						}

						if (submenu != null && submenu.getElementsCount() != 0)
							menu.addElement(submenu);
					}
				}
				else
				{
					model = null;
					addMessage(FacesMessage.SEVERITY_WARN, null, "Akaun anda telah dinyahaktifkan. Sila hubungi Pentadbir Sistem untuk bantuan.");
				}
			}
			else
				addMessage(FacesMessage.SEVERITY_ERROR, null, "Emel dan kata laluan tidak sah.");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		return page;
	}

	public String logout()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			TrailLog trailLog = new TrailLog();

			trailLog.setOperation("Log keluar");
			trailLog.setStaffID(model.getStaffID());
			facade.addTrailLog(trailLog);
		}
		catch (SQLException e)
		{
		}

		setUser(null);
		
		return "pretty:login";
	}

	public void updateProfile()
	{
		boolean change = false;
		String temp = null;

		if (password != null && !password.isEmpty())
		{
			temp = StringProtector.encrypt(password, 1);
			change = !model.getPassword().equals(temp);
			password = null;

			if (change)
				model.setPassword(temp);
		}

		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			int status = facade.updateStaff(model, change);

			if (status != 0)
			{
				addMessage(FacesMessage.SEVERITY_INFO, null, "Profil berjaya dikemaskini.");

				TrailLog trailLog = new TrailLog();

				trailLog.setOperation("Kemaskini profil");
				trailLog.setStaffID(getCurrentUser().getStaffID());
				facade.addTrailLog(trailLog);
			}
			else
				addMessage(FacesMessage.SEVERITY_WARN, null, "Profil tidak dapat dikemaskini.");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}
	
	public void upload(FileUploadEvent event)
	{
		UploadedFile uf = event.getFile();

		if (uf != null)
		{
			try
			{
				BufferedImage bi = ImageIO.read(uf.getInputstream());
				ExternalContext external = FacesContext.getCurrentInstance().getExternalContext();
				File file = new File(external.getRealPath("/") + "files/user/" + model.getStaffID() + ".png");
				int dimension = Math.min(bi.getWidth(), bi.getHeight());
				
				file.getParentFile().mkdirs();
				ImageIO.write(bi.getSubimage(0, 0, dimension, dimension), "png", file);
				addMessage(FacesMessage.SEVERITY_INFO, null, "Gambar profil berjaya dikemaskini.");
				
				picture = "background-image: url('" + external.getRequestContextPath() + "/files/user/" + model.getStaffID() + ".png?v=" + System.currentTimeMillis() + "');";
			}
			catch (Exception e)
			{
				e.printStackTrace();
				addMessage(e);
			}
		}
	}

	@Override
	public void handleOpen()
	{
	}
}