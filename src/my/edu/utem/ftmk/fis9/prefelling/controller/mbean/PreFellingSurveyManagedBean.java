package my.edu.utem.ftmk.fis9.prefelling.controller.mbean;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.imageio.ImageIO;
import javax.mail.MessagingException;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

import my.edu.utem.ftmk.fis9.global.controller.manager.AbstractFacade;
import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.global.util.ArrayListConverter;
import my.edu.utem.ftmk.fis9.global.util.EmailSender;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.Contractor;
import my.edu.utem.ftmk.fis9.maintenance.model.CuttingOption;
import my.edu.utem.ftmk.fis9.maintenance.model.Designation;
import my.edu.utem.ftmk.fis9.maintenance.model.District;
import my.edu.utem.ftmk.fis9.maintenance.model.Forest;
import my.edu.utem.ftmk.fis9.maintenance.model.Hall;
import my.edu.utem.ftmk.fis9.maintenance.model.Range;
import my.edu.utem.ftmk.fis9.maintenance.model.Species;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.maintenance.model.Tender;
import my.edu.utem.ftmk.fis9.maintenance.model.TreeLimit;
import my.edu.utem.ftmk.fis9.prefelling.controller.manager.PreFellingFacade;
import my.edu.utem.ftmk.fis9.prefelling.model.CuttingLimit;
import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingCuttingOption;
import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingReport;
import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingSurvey;
import my.edu.utem.ftmk.fis9.prefelling.util.ClosingLetterGenerator;
import my.edu.utem.ftmk.fis9.prefelling.util.PreFellingSurveyLetterGenerator;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "preFellingSurveyMBean")
public class PreFellingSurveyManagedBean
		extends AbstractManagedBean<PreFellingSurvey>
{
	private static final long serialVersionUID = VERSION;
	private CuttingLimit cuttingLimit;
	private TreeLimit treeLimit;
	private District district;
	private Range range;
	private ArrayList<Forest> forests;
	private ArrayList<Tender> tenders;
	private ArrayList<District> districts;
	private ArrayList<Range> ranges;
	private ArrayList<Staff> staffs;
	private ArrayList<Species> speciesList;
	private ArrayList<CuttingOption> cuttingOptions;
	private ArrayList<SelectItem> forestList;
	private ArrayList<SelectItem> staffList;
	private ArrayList<SelectItem> yearList;
	private String[] selectedStaffs;
	private String popupName;
	private String downtype;
	private String uptype;
	private int[] yearRange;
	private int selectedYearRange;
	private int accessLevel;

	public PreFellingSurveyManagedBean()
	{
		try (MaintenanceFacade mFacade = new MaintenanceFacade();
				PreFellingFacade pFacade = new PreFellingFacade();)
		{
			AbstractFacade.group(mFacade, pFacade);

			Staff user = getCurrentUser();
			String staffID = user.getStaffID();
			int stateID = user.getStateID(),
					designationID = user.getDesignationID();

			forestList = new ArrayList<>();
			yearList = new ArrayList<>();
			yearRange = pFacade.getPreFellingSurveyYearRange();

			if (yearRange[0] != 0)
			{
				for (int i = yearRange[1]; i >= yearRange[0]; i -= 5)
				{
					if (i == yearRange[0])
						yearList.add(new SelectItem(i, String.valueOf(i)));
					else
						yearList.add(new SelectItem(i,
								Math.max(i - 4, yearRange[0]) + " - " + i));
				}
			}

			if (stateID == 0)
			{
				ArrayList<State> states = mFacade.getStates();
				forests = mFacade.getForests();
				districts = new ArrayList<>();

				for (State state : states)
				{
					ArrayList<District> districts = mFacade.getDistricts(state);

					for (District district : districts)
					{
						SelectItemGroup group = new SelectItemGroup(
								district.getName() + ", " + state.getName());
						ArrayList<SelectItem> items = new ArrayList<>();

						for (Forest forest : forests)
							if (forest.getDistrictID() == district
									.getDistrictID())
								items.add(new SelectItem(forest.getForestID(),
										forest.toString()));

						if (!items.isEmpty())
						{
							group.setSelectItems(
									ArrayListConverter.asSelectItem(items));
							forestList.add(group);
						}
					}

					this.districts.addAll(districts);
				}

				if (designationID == 0)
				{
					tenders = mFacade.getTenders("A");
					downtype = "csoh";
				}
				else
				{
					tenders = mFacade.getTenders("F", "A", user);
					accessLevel = 6;
					uptype = "csoo";
				}
			}
			else
			{
				State state = mFacade.getState(stateID);
				forests = mFacade.getForests(state);

				if (staffID.equals(state.getDirectorID())
						|| staffID.equals(state.getDeputyDirector1ID())
						|| staffID.equals(state.getDeputyDirector2ID())
						|| staffID.equals(state.getSeniorAsstDirector1ID())
						|| staffID.equals(state.getAsstDirector1ID()))
				{
					accessLevel = staffID.equals(state.getDirectorID()) ? 1 : 7;
					downtype = "csoh";
					treeLimit = mFacade.getTreeLimit(state);
					tenders = mFacade.getTenders("F", "A", state);
					districts = mFacade.getDistricts(state);

					for (District district : districts)
					{
						SelectItemGroup group = new SelectItemGroup(
								district.getName());
						ArrayList<SelectItem> items = new ArrayList<>();

						for (Forest forest : forests)
							if (forest.getDistrictID() == district
									.getDistrictID())
								items.add(new SelectItem(forest.getForestID(),
										forest.toString()));

						if (!items.isEmpty())
						{
							group.setSelectItems(
									ArrayListConverter.asSelectItem(items));
							forestList.add(group);
						}
					}
				}
				else
				{
					district = mFacade.getDistrict(user);

					if (district != null)
					{
						ranges = district.getRanges();
						accessLevel = district.getOfficerID().equals(staffID)
								? 2
								: 3;
						uptype = "csoh";
						downtype = "csoo";

						for (Forest forest : forests)
						{
							if (forest.getDistrictID() == district
									.getDistrictID())
							{
								SelectItem item = new SelectItem(
										forest.getForestID(),
										forest.toString());

								if (!forestList.contains(item))
									forestList.add(item);
							}
						}
					}
					else
					{
						range = mFacade.getRange(user);
						staffs = mFacade.getStaffs(state);
						staffList = new ArrayList<>();
						ArrayList<Designation> designations = mFacade
								.getDesignations();

						if (range != null)
						{
							accessLevel = 4;
							uptype = "csoo";
							downtype = "csor";
						}
						else
						{
							accessLevel = 5;
							uptype = "cso[rf]";
							downtype = "csof";

							staffs.remove(user);
						}

						for (Designation designation : designations)
						{
							designationID = designation.getDesignationID();

							if ((designationID == 18 || designationID == 19)
									&& user.getDesignationID() <= designationID)
							{
								ArrayList<SelectItem> items = new ArrayList<>();

								for (Staff staff : staffs)
									if (staff
											.getDesignationID() == designationID)
										items.add(new SelectItem(
												staff.getStaffID(),
												staff.toString()));

								if (!items.isEmpty())
								{
									SelectItemGroup group = new SelectItemGroup(
											designation.getName());

									group.setSelectItems(ArrayListConverter
											.asSelectItem(items));
									staffList.add(group);
								}
							}
						}
					}
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public PreFellingSurvey getPreFellingSurvey()
	{
		return model;
	}

	public void setPreFellingSurvey(PreFellingSurvey preFellingSurvey)
	{
		this.model = preFellingSurvey;
	}

	public CuttingLimit getCuttingLimit()
	{
		return cuttingLimit;
	}

	public void setCuttingLimit(CuttingLimit cuttingLimit)
	{
		this.cuttingLimit = cuttingLimit;
	}

	public ArrayList<PreFellingSurvey> getPreFellingSurveys()
	{
		return models;
	}

	public void setPreFellingSurveys(
			ArrayList<PreFellingSurvey> preFellingSurveys)
	{
		this.models = preFellingSurveys;
	}

	public ArrayList<Forest> getForests()
	{
		return forests;
	}

	public void setForests(ArrayList<Forest> forests)
	{
		this.forests = forests;
	}

	public ArrayList<Tender> getTenders()
	{
		return tenders;
	}

	public void setTenders(ArrayList<Tender> tenders)
	{
		this.tenders = tenders;
	}

	public ArrayList<Range> getRanges()
	{
		return ranges;
	}

	public void setRanges(ArrayList<Range> ranges)
	{
		this.ranges = ranges;
	}

	public ArrayList<Staff> getStaffs()
	{
		return staffs;
	}

	public void setStaffs(ArrayList<Staff> staffs)
	{
		this.staffs = staffs;
	}

	public ArrayList<Species> getSpeciesList()
	{
		return speciesList;
	}

	public void setSpeciesList(ArrayList<Species> speciesList)
	{
		this.speciesList = speciesList;
	}

	public ArrayList<CuttingOption> getCuttingOptions()
	{
		return cuttingOptions;
	}

	public void setCuttingOptions(ArrayList<CuttingOption> cuttingOptions)
	{
		this.cuttingOptions = cuttingOptions;
	}

	public ArrayList<SelectItem> getForestList()
	{
		return forestList;
	}

	public void setForestList(ArrayList<SelectItem> forestList)
	{
		this.forestList = forestList;
	}

	public ArrayList<SelectItem> getStaffList()
	{
		return staffList;
	}

	public void setStaffList(ArrayList<SelectItem> staffList)
	{
		this.staffList = staffList;
	}

	public ArrayList<SelectItem> getYearList()
	{
		return yearList;
	}

	public String[] getSelectedStaffs()
	{
		return selectedStaffs;
	}

	public void setSelectedStaffs(String[] selectedStaffs)
	{
		this.selectedStaffs = selectedStaffs;
	}

	public String getPopupName()
	{
		return popupName;
	}

	public void setPopupName(String popupName)
	{
		this.popupName = popupName;
	}

	public String getDowntype()
	{
		return downtype;
	}

	public String getUptype()
	{
		return uptype;
	}

	public int getSelectedYearRange()
	{
		return selectedYearRange;
	}

	public void setSelectedYearRange(int selectedYearRange)
	{
		this.selectedYearRange = selectedYearRange;
	}

	public int getAccessLevel()
	{
		return accessLevel;
	}

	public void handleYearChange()
	{
		try (MaintenanceFacade mFacade = new MaintenanceFacade();
				PreFellingFacade pFacade = new PreFellingFacade();)
		{
			AbstractFacade.group(mFacade, pFacade);

			Staff user = getCurrentUser();
			String staffID = user.getStaffID();
			int stateID = user.getStateID(),
					designationID = user.getDesignationID(),
					endYear = selectedYearRange,
					startYear = selectedYearRange - 4;

			if (stateID == 0)
			{
				if (designationID == 0)
					models = pFacade.getPreFellingSurveys(startYear, endYear);
				else
					models = pFacade.getPreFellingSurveys(user, startYear,
							endYear);
			}
			else
			{
				State state = mFacade.getState(stateID);

				if (staffID.equals(state.getDirectorID())
						|| staffID.equals(state.getDeputyDirector1ID())
						|| staffID.equals(state.getDeputyDirector2ID())
						|| staffID.equals(state.getSeniorAsstDirector1ID())
						|| staffID.equals(state.getAsstDirector1ID()))
				{
					models = pFacade.getPreFellingSurveys(state, startYear,
							endYear);
					ExternalContext external = FacesContext.getCurrentInstance()
							.getExternalContext();

					for (PreFellingSurvey preFellingSurvey : models)
					{
						String name = "PenetapanHBT_"
								+ preFellingSurvey.getForestName()
										.replaceAll(" ", "")
								+ "_" + preFellingSurvey.getComptBlockNo() + "_"
								+ preFellingSurvey.getYear() + ".pdf";
						File file = new File(external.getRealPath("/")
								+ "files/pre-f/" + name);

						preFellingSurvey.setRecommended(file.exists());
					}
				}
				else
				{
					district = mFacade.getDistrict(user);

					if (district != null)
						models = pFacade.getPreFellingSurveys(district,
								startYear, endYear);
					else
					{
						range = mFacade.getRange(user);

						if (range != null)
							models = pFacade.getPreFellingSurveys(range,
									startYear, endYear);
						else
							models = pFacade.getPreFellingSurveys(user,
									startYear, endYear);

						if (models != null)
						{
							for (Forest forest : forests)
							{
								for (PreFellingSurvey preFellingSurvey : models)
								{
									if (forest.getForestID() == preFellingSurvey
											.getForestID())
									{
										SelectItem item = new SelectItem(
												forest.getForestID(),
												forest.toString());

										if (!forestList.contains(item))
											forestList.add(item);
									}
								}
							}
						}
					}
				}
			}

			if (models != null)
			{
				sort(models);
				ExternalContext external = FacesContext.getCurrentInstance()
						.getExternalContext();

				for (PreFellingSurvey preFellingSurvey : models)
				{
					String path = external.getRealPath("/") + "files/pre-f/",
							name = "_"
									+ preFellingSurvey.getPreFellingSurveyID()
									+ ".png";
					File file1 = new File(path + "pelan" + name);
					File file2 = new File(path + "stok" + name);

					preFellingSurvey
							.setRecorders(mFacade.getStaffs(preFellingSurvey));
					preFellingSurvey.setPlanUploaded(file1.exists());
					preFellingSurvey.setStockUploaded(file2.exists());
				}
			}
			else
				models = new ArrayList<>();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	@Override
	public void handleOpen()
	{
		if (addOperation)
		{
			model = new PreFellingSurvey();
			Staff staff = getCurrentUser();
			GregorianCalendar calendar = new GregorianCalendar();

			model.setPreFellingSurveyID(System.currentTimeMillis());
			model.setYear(calendar.get(GregorianCalendar.YEAR));
			model.setOpen(true);
			model.setCreatorID(staff.getStaffID());
			model.setCreatorName(staff.getName());
		}
		else
		{
			model = (PreFellingSurvey) copy(models, model);

			if (accessLevel < 2)
				prepareClose();
		}
	}

	public void handleOpenBypass()
	{
		model = new PreFellingSurvey();
		Staff staff = getCurrentUser();
		GregorianCalendar calendar = new GregorianCalendar();

		model.setPreFellingSurveyID(System.currentTimeMillis());
		model.setYear(calendar.get(GregorianCalendar.YEAR));
		model.setCreatorID(staff.getStaffID());
		model.setCreatorName(staff.getName());
		model.setPreFellingCuttingOption(new PreFellingCuttingOption());
		model.setCuttingLimits(new ArrayList<>());

		if (cuttingOptions == null)
		{
			try (MaintenanceFacade facade = new MaintenanceFacade())
			{
				cuttingOptions = facade.getCuttingOptions();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
				addMessage(e);
			}
		}
	}

	public void handleForestIDChange()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			State state = new State();

			for (Forest forest : forests)
				if (forest.getForestID() == model.getForestID())
					state.setStateID(forest.getStateID());

			tenders = facade.getTenders("F", "A", state);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public void handleForestIDBypassChange()
	{
		int districtID = 0;

		for (Forest forest : forests)
		{
			if (forest.getForestID() == model.getForestID())
			{
				districtID = forest.getDistrictID();
				break;
			}
		}

		for (District district : districts)
		{
			if (district.getDistrictID() == districtID)
			{
				ranges = district.getRanges();
				break;
			}
		}
	}

	public void handleOpenConfig()
	{
		model = (PreFellingSurvey) copy(models, model);
		ArrayList<Staff> recorders = model.getRecorders();

		if (recorders != null)
		{
			int size = recorders.size();
			selectedStaffs = new String[size];

			for (int i = 0; i < size; i++)
				selectedStaffs[i] = recorders.get(i).getStaffID();
		}
	}

	public void handleAddCuttingLimit()
	{
		if (cuttingLimit != null)
		{
			if (cuttingLimit.getSpeciesID() != 0)
			{
				boolean valid = true;

				for (Species species : speciesList)
				{
					if (species.getSpeciesID() == cuttingLimit.getSpeciesID())
						cuttingLimit.setSpeciesName(species.getName());

					if (treeLimit != null && "1201".equals(species.getCode())
							&& cuttingLimit.getMinDiameter() < treeLimit
									.getChengalLimit())
						valid = false;
				}

				if (valid)
				{
					ArrayList<CuttingLimit> cuttingLimits = model
							.getCuttingLimits();

					if (cuttingLimits.contains(cuttingLimit))
						addMessage(FacesMessage.SEVERITY_WARN, "messages",
								cuttingLimit.getSpeciesName()
										+ " tidak dapat ditambahkan kerana telah direkodkan sebelumnya.");
					else
						cuttingLimits.add(cuttingLimit);
				}
				else
					addMessage(FacesMessage.SEVERITY_WARN, "messages",
							cuttingLimit.getSpeciesName()
									+ " tidak dapat ditambahkan kerana kurang dari had tebangan yang ditetapkan.");
			}
			else
				addMessage(FacesMessage.SEVERITY_WARN, "messages",
						"Sila pilih spesis lain-lain.");
		}

		cuttingLimit = new CuttingLimit();
		cuttingLimit.setPreFellingSurveyID(model.getPreFellingSurveyID());
	}

	public void preFellingSurveyEntry()
	{
		try (MaintenanceFacade mFacade = new MaintenanceFacade();
				PreFellingFacade pFacade = new PreFellingFacade())
		{
			AbstractFacade.group(mFacade, pFacade);

			for (Forest forest : forests)
			{
				if (model.getForestID() == forest.getForestID())
				{
					model.setForestCode(forest.getCode());
					model.setForestName(forest.getName());
					model.setDistrictID(forest.getDistrictID());
					model.setStateID(forest.getStateID());
					model.setDistrictName(forest.getDistrictName());
					model.setStateName(forest.getStateName());
				}
			}

			if (ranges != null)
				for (Range range : ranges)
					if (range.getRangeID() == model.getRangeID())
						model.setRangeName(range.getName());

			if (staffs != null)
				for (Staff staff : staffs)
					if (staff.getStaffID().equals(model.getTeamLeaderID()))
						model.setTeamLeaderName(staff.getName());

			if (model.getTenderNo() != null)
			{
				if (model.getTenderNo().trim().isEmpty())
					model.setTenderNo(null);
				else
				{
					if (tenders != null)
					{
						for (Tender tender : tenders)
						{
							if (tender.getTenderNo()
									.equals(model.getTenderNo()))
							{
								model.setStartDate(tender.getStartDate());
								model.setEndDate(tender.getEndDate());
							}
						}
					}
				}
			}

			if (addOperation)
				finalizeModelEntry(pFacade.addPreFellingSurvey(model, true),
						addOperation, pFacade,
						"bancian, ID " + model.getPreFellingSurveyID(), null,
						models, model);
			else
			{
				boolean valid = true;

				if (selectedStaffs != null)
				{
					ArrayList<Staff> recorders = new ArrayList<>();

					for (String selectedStaff : selectedStaffs)
						for (Staff staff : staffs)
							if (staff.getStaffID().equals(selectedStaff))
								recorders.add(staff);

					model.setRecorders(recorders);
				}

				if (!model.isOpen())
				{
					PreFellingCuttingOption preFellingCuttingOption = model
							.getPreFellingCuttingOption();

					if (preFellingCuttingOption != null)
					{
						model.setCuttingOptionID(
								preFellingCuttingOption.getCuttingOptionID());
						model.setDipterocarpLimit(
								preFellingCuttingOption.getDipterocarpLimit());
						model.setNonDipterocarpLimit(preFellingCuttingOption
								.getNonDipterocarpLimit());
						preFellingCuttingOption.setOriginalStandRatio(model
								.getPreFellingReport().getOriginalStandRatio());
					}
					else
						valid = false;
				}

				if (valid)
					finalizeModelEntry(pFacade.updatePreFellingSurvey(model),
							addOperation, pFacade,
							"bancian, ID " + model.getPreFellingSurveyID(),
							null, models, model);
				else
					addMessage(FacesMessage.SEVERITY_WARN, null,
							"Sila pilih had batas tebangan.");
			}

			if (updateYear(model))
			{
				if (!models.contains(model))
					models.add(model);
			}
			else
				models.remove(model);

			try
			{
				PreFellingCuttingOption preFellingCuttingOption = null;
				FacesContext context = FacesContext.getCurrentInstance();
				ExternalContext external = context.getExternalContext();
				String name = "Bancian_"
						+ model.getForestName().replaceAll(" ", "") + "_"
						+ model.getComptBlockNo() + "_" + model.getYear()
						+ ".pdf";
				int level = 0;

				if (accessLevel == 0 || accessLevel == 1)
				{
					level = 1;
					name = (model.isOpen() ? "Buka" : "Tutup") + name;
					preFellingCuttingOption = model
							.getPreFellingCuttingOption();

					for (District d : districts)
						if (d.getDistrictID() == model.getDistrictID())
							district = d;
				}
				else if (accessLevel == 2 || accessLevel == 3)
				{
					if (model.getTenderNo() == null)
					{
						level = 2;
						name = "Arahan" + name;

						for (Range r : ranges)
							if (r.getRangeID() == model.getRangeID())
								range = r;
					}
				}
				else if (accessLevel == 4)
				{
					if (model.getTenderNo() == null)
					{
						level = 3;
						name = "Lantikan" + name;
					}
				}

				String message = null;
				File file = new File(
						external.getRealPath("/") + "files/pre-f/" + name);

				file.getParentFile().mkdirs();

				if (preFellingCuttingOption != null)
				{
					Staff user = getCurrentUser();
					State state = mFacade.getState(user.getStateID());
					message = "Sesi Inventori Hutan Sebelum Tebangan (Pre-Felling) telah ditutup oleh "
							+ model.getCreatorName()
							+ " untuk:<br/><br/><table border='0'><tr><td>- Hutan simpan</td><td>:</td><td>"
							+ model.getForestName()
							+ "</td></tr><tr><td>- No. kompartmen/sub kompartmen</td><td>:</td><td>"
							+ model.getComptBlockNo() + "</td></tr></table>";

					ClosingLetterGenerator.generate(file, model, state,
							treeLimit, user);
				}
				else
				{
					if (level != 0)
					{
						PreFellingSurveyLetterGenerator.generate(file, model,
								district, range, level);

						if (addOperation)
							message = "Sesi Inventori Hutan Sebelum Tebangan (Pre-Felling) baru telah dibuka oleh "
									+ model.getCreatorName()
									+ " untuk:<br/><br/><table border='0'><tr><td>- Hutan simpan</td><td>:</td><td>"
									+ model.getForestName()
									+ "</td></tr><tr><td>- No. kompartmen/sub kompartmen</td><td>:</td><td>"
									+ model.getComptBlockNo()
									+ "</td></tr><tr><td>- Keluasan</td><td>:</td><td>"
									+ model.getArea()
									+ " hektar</td></tr></table><br/>Sila log masuk ke FIS9 untuk tindakan anda seterusnya.";
					}
				}

				if (message != null)
					new EmailSender().send(true,
							"Inventori Hutan Sebelum Tebangan - "
									+ model.getForestName() + " "
									+ model.getComptBlockNo(),
							message, district.getOfficerID());
			}
			catch (IOException | MessagingException e)
			{
				e.printStackTrace();
				addMessage(FacesMessage.SEVERITY_WARN, null,
						"Sistem tidak berjaya menghantar emel notifikasi.");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				addMessage(e);
			}

			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('" + popupName + "').hide()");
	}

	public void preFellingSurveyEntryBypass()
	{
		try (MaintenanceFacade mFacade = new MaintenanceFacade();
				PreFellingFacade pFacade = new PreFellingFacade())
		{
			AbstractFacade.group(mFacade, pFacade);

			for (Forest forest : forests)
			{
				if (model.getForestID() == forest.getForestID())
				{
					model.setForestCode(forest.getCode());
					model.setForestName(forest.getName());
					model.setDistrictID(forest.getDistrictID());
					model.setStateID(forest.getStateID());
					model.setDistrictName(forest.getDistrictName());
					model.setStateName(forest.getStateName());
				}
			}

			for (Range range : ranges)
				if (range.getRangeID() == model.getRangeID())
					model.setRangeName(range.getName());

			for (CuttingOption cuttingOption : cuttingOptions)
			{
				if (cuttingOption.getCuttingOptionID() == model
						.getCuttingOptionID())
				{
					PreFellingCuttingOption preFellingCuttingOption = model
							.getPreFellingCuttingOption();

					preFellingCuttingOption.setPreFellingSurveyID(
							model.getPreFellingSurveyID());
					preFellingCuttingOption.setStateID(model.getStateID());
					preFellingCuttingOption.setStateName(model.getStateName());
					preFellingCuttingOption.setCuttingOptionID(
							cuttingOption.getCuttingOptionID());
					preFellingCuttingOption.setDipterocarpLimit(
							cuttingOption.getDipterocarpLimit());
					preFellingCuttingOption.setNonDipterocarpLimit(
							cuttingOption.getNonDipterocarpLimit());

					model.setDipterocarpLimit(
							cuttingOption.getDipterocarpLimit());
					model.setNonDipterocarpLimit(
							cuttingOption.getNonDipterocarpLimit());
					break;
				}
			}

			finalizeModelEntry(pFacade.addPreFellingSurvey(model, true), true,
					pFacade, "bancian, ID " + model.getPreFellingSurveyID(),
					null, models, model);

			if (updateYear(model))
			{
				if (!models.contains(model))
					models.add(model);
			}
			else
				models.remove(model);

			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popupBypass').hide()");
	}

	public void prepareClose()
	{
		if (!model.isOpen())
		{
			try (MaintenanceFacade mFacade = new MaintenanceFacade();
					PreFellingFacade pFacade = new PreFellingFacade();)
			{
				AbstractFacade.group(mFacade, pFacade);

				State state = new State();

				handleAddCuttingLimit();
				state.setStateID(model.getStateID());

				speciesList = mFacade.getSpeciesList(state);

				if (model.getPreFellingReport() == null)
				{
					PreFellingReport preFellingReport = new PreFellingReport(
							model, mFacade.getTimberGroups(),
							mFacade.getCuttingOptions(state),
							mFacade.getLogQualities());

					model.setPreFellingSurveyCards(
							pFacade.getPreFellingSurveyCards(model));
					model.setPreFellingReport(preFellingReport);

					ArrayList<Integer> recommendations = pFacade
							.getCuttingOptionRecommendations(model);
					ArrayList<PreFellingCuttingOption> scos = preFellingReport
							.getPreFellingCuttingOptions();
					int size = recommendations.size();

					for (int i = 0; i < size; i++)
					{
						int cuttingOptionID = recommendations.get(i);

						for (PreFellingCuttingOption preFellingCuttingOption : scos)
							if (cuttingOptionID == preFellingCuttingOption
									.getCuttingOptionID())
								preFellingCuttingOption.setRank(i + 1);
					}

					sort(scos);
				}

				if (model.getCuttingLimits() == null)
					model.setCuttingLimits(new ArrayList<>());
			}
			catch (SQLException e)
			{
				e.printStackTrace();
				addMessage(e);
			}
		}
	}

	public StreamedContent download(PreFellingSurvey preFellingSurvey,
			int level)
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		String name = "Bancian_"
				+ preFellingSurvey.getForestName().replaceAll(" ", "") + "_"
				+ preFellingSurvey.getComptBlockNo() + "_"
				+ preFellingSurvey.getYear() + "."
				+ (level != 0 ? "pdf" : downtype), type = null;

		if (level != 0)
		{
			if (level == 1)
				name = (preFellingSurvey.isOpen() ? "Buka" : "Tutup") + name;
			else if (level == 2)
				name = "Arahan" + name;
			else if (level == 3)
				name = "Lantikan" + name;
			else if (level == 4)
				name = name.replaceAll("Bancian", "PenetapanHBT");
		}

		File file = new File(external.getRealPath("/") + "files/pre-f/" + name);
		StreamedContent content = null;

		file.getParentFile().mkdirs();

		try
		{
			if (level != 0)
			{
				if (!file.exists())
				{
					if (accessLevel == 0 || accessLevel == 1)
					{
						for (District d : districts)
							if (d.getDistrictID() == preFellingSurvey
									.getDistrictID())
								district = d;
					}
					else if (accessLevel == 2)
					{
						for (Range r : ranges)
							if (r.getRangeID() == preFellingSurvey.getRangeID())
								range = r;
					}

					if (level != 4)
						PreFellingSurveyLetterGenerator.generate(file,
								preFellingSurvey, district, range, level);
				}

				type = "application/pdf";
			}
			else
			{
				type = "application/octet-stream";
				Staff[] staffs = null;
				District district = null;
				Contractor contractor = null;
				Tender tender = null;

				ObjectOutputStream oos = new ObjectOutputStream(
						new GZIPOutputStream(new FileOutputStream(file)));

				try (MaintenanceFacade facade = new MaintenanceFacade())
				{
					district = facade
							.getDistrict(preFellingSurvey.getDistrictID());
					ArrayList<Range> ranges = district.getRanges();
					ArrayList<Staff> temp = new ArrayList<>();

					temp.add(facade.getStaff(preFellingSurvey.getCreatorID(),
							null));
					temp.add(facade.getStaff(district.getOfficerID(), null));

					if (district.getAsstOfficerID() != null)
						temp.add(facade.getStaff(district.getAsstOfficerID(),
								null));

					if (district.getClerk1ID() != null)
						temp.add(facade.getStaff(district.getClerk1ID(), null));

					if (district.getClerk2ID() != null)
						temp.add(facade.getStaff(district.getClerk2ID(), null));

					if (district.getClerk3ID() != null)
						temp.add(facade.getStaff(district.getClerk3ID(), null));

					for (Range range : ranges)
						temp.add(facade.getStaff(range.getAsstOfficerID(),
								null));

					if (preFellingSurvey.getTeamLeaderID() != null)
						temp.add(facade.getStaff(
								preFellingSurvey.getTeamLeaderID(), null));

					if (preFellingSurvey.getTenderNo() != null)
					{
						tender = facade
								.getTender(preFellingSurvey.getTenderNo());
						contractor = facade
								.getContractor(tender.getContractorID());
					}

					staffs = temp.toArray(new Staff[0]);
				}

				oos.writeInt(21);
				oos.writeObject(contractor);
				oos.writeObject(tender);
				oos.writeObject(staffs);
				oos.writeObject(district);
				oos.writeObject(preFellingSurvey);

				String path = external.getRealPath("/") + "files/pre-f/",
						image = "_" + preFellingSurvey.getPreFellingSurveyID()
								+ ".png";
				File plan = new File(path + "pelan" + image);
				File stock = new File(path + "stok" + image);

				if (plan.exists())
				{
					ByteArrayOutputStream baos = new ByteArrayOutputStream();

					ImageIO.write(ImageIO.read(plan), "png", baos);
					oos.writeObject(baos.toByteArray());
				}
				else
					oos.writeObject(null);

				if (stock.exists())
				{
					ByteArrayOutputStream baos = new ByteArrayOutputStream();

					ImageIO.write(ImageIO.read(stock), "png", baos);
					oos.writeObject(baos.toByteArray());
				}
				else
					oos.writeObject(null);

				oos.close();
			}

			content = DefaultStreamedContent.builder().contentType(type)
					.name(name).stream(() ->
					{
						FileInputStream fis = null;

						try
						{
							fis = new FileInputStream(file);
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}

						return fis;
					}).build();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		return content;
	}

	public void upload(FileUploadEvent event)
	{
		UploadedFile file = event.getFile();

		if (file != null)
		{
			try (ObjectInputStream ois = new ObjectInputStream(
					new GZIPInputStream(file.getInputStream()));
					MaintenanceFacade mFacade = new MaintenanceFacade();
					PreFellingFacade pFacade = new PreFellingFacade();)
			{
				AbstractFacade.group(mFacade, pFacade);

				if (ois.readInt() != 21)
					throw new InvalidClassException("Not Pre-FellingSurvey");

				Staff user = getCurrentUser();
				Contractor contractor = (Contractor) ois.readObject();
				Tender tender = (Tender) ois.readObject();
				Staff[] staffs = (Staff[]) ois.readObject();
				District district = (District) ois.readObject();
				String staffID = user.getStaffID();

				if (contractor != null
						&& mFacade.addContractor(contractor) != 0)
					log(mFacade, "Tambah kontraktor, ID "
							+ contractor.getContractorID());

				if (tender != null && mFacade.addTender(tender) != 0)
					log(mFacade,
							"Tambah sebut harga, ID " + tender.getTenderNo());

				for (Staff staff : staffs)
				{
					if (staff != null)
					{
						if (mFacade.addStaff(staff) != 0)
							log(mFacade, "Tambah pekerja dan akses, ID "
									+ staff.getStaffID());
						else if (mFacade.updateStaff(staff, false) != 0)
							log(mFacade, "Kemaskini pekerja dan akses, ID "
									+ staff.getStaffID());
					}
				}

				if (mFacade.getDistrict(district.getDistrictID()) == null)
				{
					district.setDistrictID(0);

					if (mFacade.addDistrict(district) != 0)
					{
						ArrayList<Range> ranges = district.getRanges();
						ArrayList<Hall> halls = district.getHalls();

						for (Range range : ranges)
						{
							range.setDistrictID(district.getDistrictID());
							mFacade.addRange(range);
						}

						for (Hall hall : halls)
						{
							hall.setDistrictID(district.getDistrictID());
							mFacade.addHall(hall, false);
						}
					}
				}
				else
				{
					if (mFacade.updateDistrict(district) != 0)
					{
						ArrayList<Range> ranges = district.getRanges();
						ArrayList<Hall> halls = district.getHalls();

						for (Range range : ranges)
						{
							if (mFacade.getRange(range.getRangeID()) == null)
							{
								range.setRangeID(0);
								mFacade.addRange(range);
							}
							else
								mFacade.updateRange(range);
						}

						for (Hall hall : halls)
							mFacade.addHall(hall, false);
					}
				}

				PreFellingSurvey preFellingSurvey = (PreFellingSurvey) ois
						.readObject();
				ArrayList<Staff> recorders = preFellingSurvey.getRecorders();

				if (recorders != null)
					for (Staff recorder : recorders)
						if (mFacade.addStaff(recorder) != 0)
							log(mFacade, "Tambah pekerja dan akses, ID "
									+ recorder.getStaffID());

				int status = pFacade.addPreFellingSurvey(preFellingSurvey,
						false);

				if (status != 0)
				{
					log(pFacade, "Tambah bancian, ID "
							+ preFellingSurvey.getPreFellingSurveyID());
					boolean add = updateYear(preFellingSurvey);

					if (models.contains(preFellingSurvey))
					{
						addMessage(FacesMessage.SEVERITY_INFO, null,
								preFellingSurvey + " berjaya dikemaskini.");
						models.set(models.indexOf(preFellingSurvey),
								preFellingSurvey);
					}
					else
					{
						if (accessLevel == 2 || accessLevel == 3)
						{
							if (district.getDistrictID() == preFellingSurvey
									.getDistrictID())
							{
								addMessage(FacesMessage.SEVERITY_INFO, null,
										preFellingSurvey
												+ " berjaya ditambahkan.");

								if (add)
									models.add(preFellingSurvey);
							}
							else
								addMessage(FacesMessage.SEVERITY_INFO, null,
										preFellingSurvey
												+ " berjaya ditambahkan, namun sesi bancian ini tidak termasuk dalam tanggung jawab anda.");
						}
						else if (accessLevel == 4)
						{
							if (range.getRangeID() == preFellingSurvey
									.getRangeID()
									&& preFellingSurvey.getTenderNo() == null)
							{
								addMessage(FacesMessage.SEVERITY_INFO, null,
										preFellingSurvey
												+ " berjaya ditambahkan.");

								if (add)
									models.add(preFellingSurvey);
							}
							else
								addMessage(FacesMessage.SEVERITY_INFO, null,
										preFellingSurvey
												+ " berjaya ditambahkan, namun sesi bancian ini tidak termasuk dalam tanggung jawab anda.");
						}
						else if (accessLevel == 5)
						{
							if (staffID
									.equals(preFellingSurvey.getTeamLeaderID())
									|| recorders != null
											&& recorders.contains(user))
							{
								addMessage(FacesMessage.SEVERITY_INFO, null,
										preFellingSurvey
												+ " berjaya ditambahkan.");

								if (add)
									models.add(preFellingSurvey);
							}
							else
								addMessage(FacesMessage.SEVERITY_INFO, null,
										preFellingSurvey
												+ " berjaya ditambahkan, namun sesi bancian ini tidak termasuk dalam tanggung jawab anda.");
						}
						else if (accessLevel == 6)
						{
							boolean valid = false;

							for (Tender t : tenders)
							{
								if (t.equals(tender))
								{
									valid = true;
									break;
								}
							}

							if (valid)
							{
								addMessage(FacesMessage.SEVERITY_INFO, null,
										preFellingSurvey
												+ " berjaya ditambahkan.");

								if (add)
									models.add(preFellingSurvey);
							}
							else
								addMessage(FacesMessage.SEVERITY_INFO, null,
										preFellingSurvey
												+ " berjaya ditambahkan, namun sesi bancian ini tidak termasuk dalam tanggung jawab anda.");
						}
					}
				}
				else
					addMessage(FacesMessage.SEVERITY_WARN, null,
							preFellingSurvey + " tidak dapat ditambahkan.");

				byte[] bytes1 = (byte[]) ois.readObject();
				byte[] bytes2 = (byte[]) ois.readObject();

				if (bytes1 != null || bytes2 != null)
				{
					ExternalContext external = FacesContext.getCurrentInstance()
							.getExternalContext();
					String path = external.getRealPath("/") + "files/pre-f/",
							name = "_"
									+ preFellingSurvey.getPreFellingSurveyID()
									+ ".png";

					if (bytes1 != null)
					{
						BufferedImage bi = ImageIO
								.read(new ByteArrayInputStream(bytes1));
						File plan = new File(path + "pelan" + name);

						plan.getParentFile().mkdirs();
						ImageIO.write(bi, "png", plan);
						preFellingSurvey.setPlanUploaded(true);
					}

					if (bytes2 != null)
					{
						BufferedImage bi = ImageIO
								.read(new ByteArrayInputStream(bytes2));
						File stock = new File(path + "stok" + name);

						stock.getParentFile().mkdirs();
						ImageIO.write(bi, "png", stock);
						preFellingSurvey.setStockUploaded(true);
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				addMessage(e);
			}
		}
	}

	public void uploadPlan(FileUploadEvent event)
	{
		UploadedFile uf = event.getFile();

		if (uf != null)
		{
			try
			{
				BufferedImage bi = ImageIO.read(uf.getInputStream());
				ExternalContext external = FacesContext.getCurrentInstance()
						.getExternalContext();
				File file = new File(
						external.getRealPath("/") + "files/pre-f/pelan_"
								+ model.getPreFellingSurveyID() + ".png");

				file.getParentFile().mkdirs();
				ImageIO.write(bi, "png", file);
				model.setPlanUploaded(true);
				addMessage(FacesMessage.SEVERITY_INFO, null,
						"Gambar pelan kerja berjaya dimuat naik.");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				addMessage(e);
			}
		}
	}

	public void uploadStock(FileUploadEvent event)
	{
		UploadedFile uf = event.getFile();

		if (uf != null)
		{
			try
			{
				BufferedImage bi = ImageIO.read(uf.getInputStream());
				ExternalContext external = FacesContext.getCurrentInstance()
						.getExternalContext();
				File file = new File(
						external.getRealPath("/") + "files/pre-f/stok_"
								+ model.getPreFellingSurveyID() + ".png");

				file.getParentFile().mkdirs();
				ImageIO.write(bi, "png", file);
				model.setStockUploaded(true);
				addMessage(FacesMessage.SEVERITY_INFO, null,
						"Gambar peta stok berjaya dimuat naik.");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				addMessage(e);
			}
		}
	}

	private boolean updateYear(PreFellingSurvey preFellingSurvey)
	{
		int year = preFellingSurvey.getYear();
		boolean update = false, add = true;

		if (yearRange[0] == 0 || year < yearRange[0])
		{
			yearRange[0] = year;
			update = true;
		}

		if (yearRange[1] == 0 || year > yearRange[1])
		{
			yearRange[1] = year;
			update = true;
		}

		if (models == null)
			models = new ArrayList<>();

		if (update)
		{
			yearList = new ArrayList<>();

			if (yearRange[0] == yearRange[1])
				selectedYearRange = year;

			for (int i = yearRange[1]; i >= yearRange[0]; i -= 5)
			{
				if (selectedYearRange <= i && selectedYearRange >= i - 4)
					selectedYearRange = i;

				if (i == yearRange[0])
					yearList.add(new SelectItem(i, String.valueOf(i)));
				else
					yearList.add(new SelectItem(i,
							Math.max(i - 4, yearRange[0]) + " - " + i));
			}
		}

		if (year > selectedYearRange || year < selectedYearRange - 4)
			add = false;

		return add;
	}
}