package my.edu.utem.ftmk.fis9.tagging.controller.mbean;

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
import org.primefaces.model.UploadedFile;

import my.edu.utem.ftmk.fis9.global.controller.manager.AbstractFacade;
import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.global.util.ArrayListConverter;
import my.edu.utem.ftmk.fis9.global.util.EmailSender;
import my.edu.utem.ftmk.fis9.global.util.StringProtector;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.Contractor;
import my.edu.utem.ftmk.fis9.maintenance.model.Designation;
import my.edu.utem.ftmk.fis9.maintenance.model.District;
import my.edu.utem.ftmk.fis9.maintenance.model.Forest;
import my.edu.utem.ftmk.fis9.maintenance.model.Hall;
import my.edu.utem.ftmk.fis9.maintenance.model.Hammer;
import my.edu.utem.ftmk.fis9.maintenance.model.Range;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.maintenance.model.Tender;
import my.edu.utem.ftmk.fis9.prefelling.controller.manager.PreFellingFacade;
import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingSurvey;
import my.edu.utem.ftmk.fis9.tagging.controller.manager.TaggingFacade;
import my.edu.utem.ftmk.fis9.tagging.model.Tagging;
import my.edu.utem.ftmk.fis9.tagging.model.TaggingLimitException;
import my.edu.utem.ftmk.fis9.tagging.util.TaggingLetterGenerator;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "taggingMBean")
public class TaggingManagedBean extends AbstractManagedBean<Tagging>
{
	private static final long serialVersionUID = VERSION;
	private District district;
	private Range range;
	private TaggingLimitException taggingLimitException;
	private ArrayList<PreFellingSurvey> preFellingSurveys;
	private ArrayList<Forest> forests;
	private ArrayList<Tender> tenders;
	private ArrayList<District> districts;
	private ArrayList<Range> ranges;
	private ArrayList<Hall> halls;
	private ArrayList<Staff> staffs;
	private ArrayList<Hammer> hammers;
	private ArrayList<SelectItem> forestList;
	private ArrayList<SelectItem> staffList;
	private ArrayList<SelectItem> hammerList;
	private ArrayList<SelectItem> yearList;
	private String[] selectedStaffs;
	private String[] selectedHammers;
	private String popupName;
	private String downtype;
	private String uptype;
	private String code;
	private int[] yearRange;
	private int selectedYearRange;
	private int accessLevel;

	public TaggingManagedBean()
	{
		try (MaintenanceFacade mFacade = new MaintenanceFacade(); PreFellingFacade pFacade = new PreFellingFacade(); TaggingFacade tFacade = new TaggingFacade();)
		{
			AbstractFacade.group(mFacade, pFacade, tFacade);

			Staff user = getCurrentUser();
			String staffID = user.getStaffID();
			int stateID = user.getStateID(), designationID = user.getDesignationID();

			forestList = new ArrayList<>();
			yearList = new ArrayList<>();
			yearRange = tFacade.getTaggingYearRange();

			if (yearRange[0] != 0)
			{
				for (int i = yearRange[1]; i >= yearRange[0]; i -= 5)
				{
					if (i == yearRange[0])
						yearList.add(new SelectItem(i, String.valueOf(i)));
					else
						yearList.add(new SelectItem(i, Math.max(i - 4, yearRange[0]) + " - " + i));
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
						SelectItemGroup group = new SelectItemGroup(district.getName() + ", " + state.getName());
						ArrayList<SelectItem> items = new ArrayList<>();

						for (Forest forest : forests)
							if (forest.getDistrictID() == district.getDistrictID())
								items.add(new SelectItem(forest.getForestID(), forest.toString()));

						if (!items.isEmpty())
						{
							group.setSelectItems(ArrayListConverter.asSelectItem(items));
							forestList.add(group);
						}
					}

					this.districts.addAll(districts);
				}

				if (designationID == 0)
				{
					preFellingSurveys = pFacade.getPreFellingSurveys(true);
					downtype = "ctoh";
				}
				else
				{
					Contractor contractor = mFacade.getContractor(user.getContractorID());
					tenders = mFacade.getTenders("T", "A", user);
					hammers = mFacade.getHammers(contractor);
					hammerList = new ArrayList<>();
					accessLevel = 6;
					uptype = "ctoo";
				}
			}
			else
			{
				State state = mFacade.getState(stateID);
				forests = mFacade.getForests(state);

				if (state.getDirectorID().equals(staffID) || staffID.equals(state.getDeputyDirector1ID()) || staffID.equals(state.getDeputyDirector2ID()) || staffID.equals(state.getSeniorAsstDirector1ID()) || staffID.equals(state.getAsstDirector1ID()))
				{
					accessLevel = 1;
					downtype = "ctoh";
					preFellingSurveys = pFacade.getPreFellingSurveys(state, true);
					tenders = mFacade.getTenders("T", "A", state);
					districts = mFacade.getDistricts(state);

					for (District district : districts)
					{
						SelectItemGroup group = new SelectItemGroup(district.getName());
						ArrayList<SelectItem> items = new ArrayList<>();

						for (Forest forest : forests)
							if (forest.getDistrictID() == district.getDistrictID())
								items.add(new SelectItem(forest.getForestID(), forest.toString()));

						if (!items.isEmpty())
						{
							group.setSelectItems(ArrayListConverter.asSelectItem(items));
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
						halls = new ArrayList<>();
						accessLevel = district.getOfficerID().equals(staffID) ? 2 : 3;
						uptype = "ctoh";
						downtype = "ctoo";
						ArrayList<Hall> temp = district.getHalls();

						for (Hall hall : temp)
							if (hall.isStatus())
								halls.add(hall);

						for (Forest forest : forests)
						{
							if (forest.getDistrictID() == district.getDistrictID())
							{
								SelectItem item = new SelectItem(forest.getForestID(), forest.toString());

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
						ArrayList<Designation> designations = mFacade.getDesignations();

						if (range != null)
						{
							District district = new District();

							district.setDistrictID(range.getDistrictID());

							hammers = mFacade.getHammers(district);
							hammerList = new ArrayList<>();
							ArrayList<Hammer> tempHammers = new ArrayList<>();
							accessLevel = 4;
							uptype = "ctoo";
							downtype = "ctor";

							for (Hammer hammer : hammers)
							{
								if (hammer.getHammerTypeID() == 2)
								{
									hammerList.add(new SelectItem(hammer.getHammerNo(), hammer.toString()));
									tempHammers.add(hammer);
								}
							}

							hammers = tempHammers;
						}
						else
						{
							accessLevel = 5;
							uptype = "cto[rf]";
							downtype = "ctof";

							staffs.remove(user);
						}

						for (Designation designation : designations)
						{
							designationID = designation.getDesignationID();

							if ((designationID == 18 || designationID == 19) && user.getDesignationID() <= designationID)
							{
								ArrayList<SelectItem> items = new ArrayList<>();

								for (Staff staff : staffs)
									if (staff.getDesignationID() == designationID)
										items.add(new SelectItem(staff.getStaffID(), staff.toString()));

								if (!items.isEmpty())
								{
									SelectItemGroup group = new SelectItemGroup(designation.getName());

									group.setSelectItems(ArrayListConverter.asSelectItem(items));
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

	public Tagging getTagging()
	{
		return model;
	}

	public void setTagging(Tagging tagging)
	{
		this.model = tagging;
	}

	public TaggingLimitException getTaggingLimitException()
	{
		return taggingLimitException;
	}

	public void setTaggingLimitException(TaggingLimitException taggingLimitException)
	{
		this.taggingLimitException = taggingLimitException;
	}

	public ArrayList<Tagging> getTaggings()
	{
		return models;
	}

	public void setTaggings(ArrayList<Tagging> taggings)
	{
		this.models = taggings;
	}

	public ArrayList<PreFellingSurvey> getPreFellingSurveys()
	{
		return preFellingSurveys;
	}

	public void setPreFellingSurveys(ArrayList<PreFellingSurvey> preFellingSurveys)
	{
		this.preFellingSurveys = preFellingSurveys;
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

	public ArrayList<Hall> getHalls()
	{
		return halls;
	}

	public void setHalls(ArrayList<Hall> halls)
	{
		this.halls = halls;
	}

	public ArrayList<Staff> getHammers()
	{
		return staffs;
	}

	public void setHammers(ArrayList<Staff> staffs)
	{
		this.staffs = staffs;
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

	public ArrayList<SelectItem> getHammerList()
	{
		return hammerList;
	}

	public void setHammerList(ArrayList<SelectItem> hammerList)
	{
		this.hammerList = hammerList;
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

	public String[] getSelectedHammers()
	{
		return selectedHammers;
	}

	public void setSelectedHammers(String[] selectedHammers)
	{
		this.selectedHammers = selectedHammers;
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

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
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
		try (MaintenanceFacade mFacade = new MaintenanceFacade(); PreFellingFacade pFacade = new PreFellingFacade(); TaggingFacade tFacade = new TaggingFacade();)
		{
			AbstractFacade.group(mFacade, pFacade, tFacade);

			Staff user = getCurrentUser();
			String staffID = user.getStaffID();
			int stateID = user.getStateID(), designationID = user.getDesignationID(), endYear = selectedYearRange, startYear = selectedYearRange - 4;

			if (stateID == 0)
			{
				if (designationID == 0)
					models = tFacade.getTaggings(false, startYear, endYear);
				else
					models = tFacade.getTaggings(user, startYear, endYear);
			}
			else
			{
				State state = mFacade.getState(stateID);
				forests = mFacade.getForests(state);

				if (state.getDirectorID().equals(staffID) || staffID.equals(state.getDeputyDirector1ID()) || staffID.equals(state.getDeputyDirector2ID()) || staffID.equals(state.getSeniorAsstDirector1ID()) || staffID.equals(state.getAsstDirector1ID()))
					models = tFacade.getTaggings(state, false, startYear, endYear);
				else
				{
					district = mFacade.getDistrict(user);

					if (district != null)
						models = tFacade.getTaggings(district, false, startYear, endYear);
					else
					{
						range = mFacade.getRange(user);

						if (range != null)
							models = tFacade.getTaggings(range, startYear, endYear);
						else
							models = tFacade.getTaggings(user, startYear, endYear);

						if (models != null)
						{
							for (Forest forest : forests)
							{
								for (Tagging tagging : models)
								{
									if (forest.getForestID() == tagging.getForestID())
									{
										SelectItem item = new SelectItem(forest.getForestID(), forest.toString());

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
				ExternalContext external = FacesContext.getCurrentInstance().getExternalContext();

				for (Tagging tagging : models)
				{
					String path = external.getRealPath("/") + "files/tagging/", name = "_" + tagging.getTaggingID() + ".png";
					File file1 = new File(path + "pelan" + name);
					File file2 = new File(path + "stok" + name);

					tagging.setPlanUploaded(file1.exists());
					tagging.setStockUploaded(file2.exists());
					tagging.setRecorders(mFacade.getStaffs(tagging));
					tagging.setHammers(mFacade.getHammers(tagging));
					tagging.setTaggingLimitExceptions(tFacade.getTaggingLimitExceptions(tagging));
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
			model = new Tagging();
			Staff staff = getCurrentUser();
			GregorianCalendar calendar = new GregorianCalendar();

			model.setTaggingID(System.currentTimeMillis());
			model.setYear(calendar.get(GregorianCalendar.YEAR));
			model.setOpen(true);
			model.setCreatorID(staff.getStaffID());
			model.setCreatorName(staff.getName());
			model.setTaggingLimitExceptions(new ArrayList<>());
		}
		else
			model = (Tagging) copy(models, model);
	}

	public void handlePreFellingSurveyIDChange()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			State state = new State();

			for (PreFellingSurvey preFellingSurvey : preFellingSurveys)
				if (preFellingSurvey.getPreFellingSurveyID() == model.getPreFellingSurveyID())
					state.setStateID(preFellingSurvey.getStateID());

			tenders = facade.getTenders("T", "A", state);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public void handleOpenConfig1()
	{
		model = (Tagging) copy(models, model);
		selectedHammers = ArrayListConverter.asString(model.getHammers());
	}

	public void handleOpenConfig2()
	{
		model = (Tagging) copy(models, model);
		ArrayList<Staff> recorders = model.getRecorders();

		if (recorders != null)
		{
			int size = recorders.size();
			selectedStaffs = new String[size];

			for (int i = 0; i < size; i++)
				selectedStaffs[i] = recorders.get(i).getStaffID();
		}
	}

	public void handleOpenTaggingLimitException()
	{
		taggingLimitException = new TaggingLimitException();
		taggingLimitException.setTaggingID(model.getTaggingID());
	}

	public void handleOpenCode()
	{
		code = generate();
	}

	public void handleAddTaggingLimitException()
	{
		ArrayList<TaggingLimitException> taggingLimitExceptions = model.getTaggingLimitExceptions();

		if (taggingLimitExceptions.contains(taggingLimitException))
			addMessage(FacesMessage.SEVERITY_WARN, "messagesLimit", taggingLimitException + " tidak dapat ditambahkan kerana telah direkodkan sebelumnya.");
		else
		{
			taggingLimitExceptions.add(taggingLimitException);

			taggingLimitException = new TaggingLimitException();
			taggingLimitException.setTaggingID(model.getTaggingID());
		}
	}

	public void taggingEntry()
	{
		try (MaintenanceFacade mFacade = new MaintenanceFacade(); TaggingFacade tFacade = new TaggingFacade())
		{
			AbstractFacade.group(mFacade, tFacade);
			PreFellingSurvey preFellingSurvey = null;

			if (preFellingSurveys != null)
			{
				for (PreFellingSurvey s : preFellingSurveys)
				{
					if (model.getPreFellingSurveyID() == s.getPreFellingSurveyID())
					{
						preFellingSurvey = s;

						model.setForestID(s.getForestID());
						model.setRangeID(s.getRangeID());
						model.setDistrictID(s.getDistrictID());
						model.setStateID(s.getStateID());
						model.setRangeName(s.getRangeName());
						model.setForestCode(s.getForestCode());
						model.setForestName(s.getForestName());
						model.setComptBlockNo(s.getComptBlockNo());
						model.setArea(s.getArea());
						model.setYear(s.getYear());
						model.setDistrictCode(s.getDistrictCode());
						model.setDistrictName(s.getDistrictName());
						model.setStateCode(s.getStateCode());
						model.setStateName(s.getStateName());
					}
				}
			}

			if (staffs != null)
				for (Staff staff : staffs)
					if (staff.getStaffID().equals(model.getTeamLeaderID()))
						model.setTeamLeaderName(staff.getName());
			
			if (halls != null)
				for (Hall hall : halls)
					if (hall.getHallID() == model.getHallID())
						model.setHallName(hall.getName());

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
							if (tender.getTenderNo().equals(model.getTenderNo()))
							{
								model.setStartDate(tender.getStartDate());
								model.setEndDate(tender.getEndDate());
							}
						}
					}
				}
			}

			if (addOperation)
			{
				int status = tFacade.addTagging(model, true);
				finalizeModelEntry(status, addOperation, tFacade, "penandaan, ID " + model.getTaggingID(), null, models, model);

				if (status != 0 && preFellingSurvey != null)
					preFellingSurveys.remove(preFellingSurvey);
			}
			else
			{
				if (selectedStaffs != null)
				{
					ArrayList<Staff> recorders = new ArrayList<>();

					for (String selectedStaff : selectedStaffs)
						for (Staff staff : staffs)
							if (staff.getStaffID().equals(selectedStaff))
								recorders.add(staff);

					model.setRecorders(recorders);
				}

				if (selectedHammers != null)
				{
					ArrayList<Hammer> taggingHammers = new ArrayList<>();

					for (String selectedHammer : selectedHammers)
						for (Hammer hammer : hammers)
							if (hammer.getHammerNo().equals(selectedHammer))
								taggingHammers.add(hammer);

					model.setHammers(taggingHammers);
				}

				finalizeModelEntry(tFacade.updateTagging(model), addOperation, tFacade, "penandaan, ID " + model.getTaggingID(), null, models, model);
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
				FacesContext context = FacesContext.getCurrentInstance();
				ExternalContext external = context.getExternalContext();
				String name = "Penandaan_" + model.getForestName().replaceAll(" ", "") + "_" + model.getComptBlockNo() + "_" + model.getYear() + ".pdf";
				int level = 0;

				if (accessLevel == 0 || accessLevel == 1)
				{
					level = 1;
					name = (model.isOpen() ? "Buka" : "Tutup") + name;

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

				if (level != 0)
				{
					File file = new File(external.getRealPath("/") + "files/tagging/" + name);

					file.getParentFile().mkdirs();
					TaggingLetterGenerator.generate(file, model, district, range, level);

					if (level == 1)
					{
						String message = null;

						if (addOperation)
							message = "Sesi penandaan baru telah dibuka oleh " + model.getCreatorName() + " untuk:<br/><br/><table border='0'><tr><td>- Hutan simpan</td><td>:</td><td>" + model.getForestName() + "</td></tr><tr><td>- No. kompartmen/sub kompartmen</td><td>:</td><td>" + model.getComptBlockNo() + "</td></tr></table><br/>Sila log masuk ke FIS9 untuk tindakan anda seterusnya.";
						else if (!addOperation && !model.isOpen())
							message = "Sesi penandaan telah ditutup oleh " + model.getCreatorName() + " untuk:<br/><br/><table border='0'><tr><td>- Hutan simpan</td><td>:</td><td>" + model.getForestName() + "</td></tr><tr><td>- No. kompartmen/sub kompartmen</td><td>:</td><td>" + model.getComptBlockNo() + "</td></tr></table><br/>";

						if (message != null)
							new EmailSender().send(true, "Sesi Penandaan - " + model.getForestName() + " " + model.getComptBlockNo(), message, district.getOfficerID());
					}
				}
			}
			catch (IOException | MessagingException e)
			{
				e.printStackTrace();
				addMessage(FacesMessage.SEVERITY_WARN, null, "Sistem tidak berjaya menghantar emel notifikasi.");
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

	public void taggingLimitExceptionEntry()
	{
		try (TaggingFacade tFacade = new TaggingFacade())
		{
			finalizeModelEntry(tFacade.updateTagging(model), false, tFacade, "penandaan, ID " + model.getTaggingID(), null, models, model);

			if (!model.getTaggingLimitExceptions().isEmpty())
			{
				for (District d : districts)
					if (d.getDistrictID() == model.getDistrictID())
						district = d;

				try
				{
					new EmailSender().send(true, "Pengecualian Had Pokok Tebangan - " + model.getForestName() + " " + model.getComptBlockNo(), "<p>Sila maklumkan kod pengesahan pengecualian had pokok tebangan berikut kepada Penolong Pegawai Hutan Daerah Renj " + model.getRangeName() + " atau ketua pasukan penandaan:</p><p>" + generate() + "</p>", district.getOfficerID());
				}
				catch (IOException | MessagingException e)
				{
					e.printStackTrace();
					addMessage(FacesMessage.SEVERITY_WARN, null, "Sistem tidak berjaya menghantar emel notifikasi.");
				}
			}

			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popupLimit').hide()");
	}

	public void taggingCodeEntry()
	{
		try
		{
			String[] temp = StringProtector.decrypt(code, 1).split(";");
			long taggingID = Long.parseLong(temp[0]);

			if (taggingID == model.getTaggingID())
			{
				ArrayList<TaggingLimitException> taggingLimitExceptions = new ArrayList<>();
				
				for (int i = 1; i < temp.length; i++)
				{
					String[] value = temp[i].split(":");
					TaggingLimitException taggingLimitException = new TaggingLimitException();
	
					taggingLimitException.setBlockNo(value[0]);
					taggingLimitException.setPlotNo(value[1]);
					taggingLimitException.setQuantity(Integer.parseInt(value[2]));
					taggingLimitException.setTaggingID(taggingID);
	
					taggingLimitExceptions.add(taggingLimitException);
				}
	
				try (TaggingFacade tFacade = new TaggingFacade())
				{
					finalizeModelEntry(tFacade.updateTagging(model), false, tFacade, "penandaan, ID " + model.getTaggingID(), null, models, model);
					model.setTaggingLimitExceptions(taggingLimitExceptions);
					
					model = null;
				}
				catch (SQLException e)
				{
					e.printStackTrace();
					addMessage(e);
				}
			}
			else
				addMessage(FacesMessage.SEVERITY_WARN, null, "Kod pengesahan pengecualian had pokok tebangan tidak sah untuk sesi penandaan ini.");
		}
		catch (Exception e)
		{
			addMessage(FacesMessage.SEVERITY_WARN, null, "Kod pengesahan pengecualian had pokok tebangan tidak sah.");
		}

		code = null;
		execute("PF('popupCode').hide()");
	}

	public StreamedContent download(Tagging tagging, int level)
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		String name = "Penandaan_" + tagging.getForestName().replaceAll(" ", "") + "_" + tagging.getComptBlockNo() + "_" + tagging.getYear() + "." + (level != 0 ? "pdf" : downtype), type = null;

		if (level != 0)
		{
			if (level == 1)
				name = (tagging.isOpen() ? "Buka" : "Tutup") + name;
			else if (level == 2)
				name = "Arahan" + name;
			else if (level == 3)
				name = "Lantikan" + name;
		}

		File file = new File(external.getRealPath("/") + "files/tagging/" + name);
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
							if (d.getDistrictID() == tagging.getDistrictID())
								district = d;
					}
					else if (accessLevel == 2)
					{
						for (Range r : ranges)
							if (r.getRangeID() == tagging.getRangeID())
								range = r;
					}

					TaggingLetterGenerator.generate(file, tagging, district, range, level);
				}

				type = "application/pdf";
			}
			else
			{
				type = "application/octet-stream";
				Staff[] staffs = null;
				District district = null;
				Contractor contractorPreF = null;
				Contractor contractorTagging = null;
				Tender tenderPreF = null;
				Tender tenderTagging = null;
				PreFellingSurvey preFellingSurvey = null;

				ObjectOutputStream oos = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(file)));

				try (MaintenanceFacade mFacade = new MaintenanceFacade(); PreFellingFacade pFacade = new PreFellingFacade();)
				{
					AbstractFacade.group(mFacade, pFacade);

					district = mFacade.getDistrict(tagging.getDistrictID());
					ArrayList<Range> ranges = district.getRanges();
					ArrayList<Staff> temp = new ArrayList<>();

					temp.add(mFacade.getStaff(tagging.getCreatorID(), null));
					temp.add(mFacade.getStaff(district.getOfficerID(), null));

					if (district.getAsstOfficerID() != null)
						temp.add(mFacade.getStaff(district.getAsstOfficerID(), null));

					if (district.getClerk1ID() != null)
						temp.add(mFacade.getStaff(district.getClerk1ID(), null));
					
					if (district.getClerk2ID() != null)
						temp.add(mFacade.getStaff(district.getClerk2ID(), null));
					
					if (district.getClerk3ID() != null)
						temp.add(mFacade.getStaff(district.getClerk3ID(), null));
					
					for (Range range : ranges)
						temp.add(mFacade.getStaff(range.getAsstOfficerID(), null));

					if (tagging.getTeamLeaderID() != null)
						temp.add(mFacade.getStaff(tagging.getTeamLeaderID(), null));

					if (tagging.getTenderNo() != null)
					{
						tenderTagging = mFacade.getTender(tagging.getTenderNo());
						contractorTagging = mFacade.getContractor(tenderTagging.getContractorID());
					}

					staffs = temp.toArray(new Staff[0]);
					preFellingSurvey = pFacade.getPreFellingSurvey(tagging.getPreFellingSurveyID());
					
					if (preFellingSurvey.getTenderNo() != null)
					{
						tenderPreF = mFacade.getTender(preFellingSurvey.getTenderNo());
						contractorPreF = mFacade.getContractor(tenderPreF.getContractorID());
					}
				}

				oos.writeInt(31);
				oos.writeObject(contractorPreF);
				oos.writeObject(contractorTagging);
				oos.writeObject(tenderPreF);
				oos.writeObject(tenderTagging);
				oos.writeObject(staffs);
				oos.writeObject(district);
				oos.writeObject(preFellingSurvey);
				oos.writeObject(tagging);

				String path = external.getRealPath("/") + "files/tagging/", image = "_" + tagging.getTaggingID() + ".png";
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

			content = new DefaultStreamedContent(new FileInputStream(file), type, name);
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
			try (ObjectInputStream ois = new ObjectInputStream(new GZIPInputStream(file.getInputstream())); MaintenanceFacade mFacade = new MaintenanceFacade(); PreFellingFacade pFacade = new PreFellingFacade(); TaggingFacade tFacade = new TaggingFacade();)
			{
				AbstractFacade.group(mFacade, pFacade, tFacade);

				if (ois.readInt() != 31)
					throw new InvalidClassException("Not Tagging");
				
				Staff user = getCurrentUser();
				Contractor contractorPreF = (Contractor) ois.readObject();
				Contractor contractorTagging = (Contractor) ois.readObject();
				Tender tenderPreF = (Tender) ois.readObject();
				Tender tenderTagging = (Tender) ois.readObject();
				Staff[] staffs = (Staff[]) ois.readObject();
				District district = (District) ois.readObject();
				String staffID = user.getStaffID();

				if (contractorPreF != null && mFacade.addContractor(contractorPreF) != 0)
					log(mFacade, "Tambah kontraktor, ID " + contractorPreF.getContractorID());

				if (contractorTagging != null && mFacade.addContractor(contractorTagging) != 0)
					log(mFacade, "Tambah kontraktor, ID " + contractorTagging.getContractorID());
				
				if (tenderPreF != null && mFacade.addTender(tenderPreF) != 0)
					log(mFacade, "Tambah sebut harga, ID " + tenderPreF.getTenderNo());

				if (tenderTagging != null && mFacade.addTender(tenderTagging) != 0)
					log(mFacade, "Tambah sebut harga, ID " + tenderTagging.getTenderNo());
				
				for (Staff staff : staffs)
				{
					if (staff != null)
					{
						if (mFacade.addStaff(staff) != 0)
							log(mFacade, "Tambah pekerja dan akses, ID " + staff.getStaffID());
						else if (mFacade.updateStaff(staff, false) != 0)
							log(mFacade, "Kemaskini pekerja dan akses, ID " + staff.getStaffID());
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

				PreFellingSurvey preFellingSurvey = (PreFellingSurvey) ois.readObject();
				Tagging tagging = (Tagging) ois.readObject();
				ArrayList<Staff> recorders = tagging.getRecorders();
				ArrayList<Hammer> hammers = tagging.getHammers();

				if (recorders != null)
					for (Staff recorder : recorders)
						if (mFacade.addStaff(recorder) != 0)
							log(mFacade, "Tambah pekerja dan akses, ID " + recorder.getStaffID());

				if (hammers != null)
					for (Hammer hammer : hammers)
						if (mFacade.addHammer(hammer) != 0)
							log(mFacade, "Tambah tukul, ID " + hammer.getHammerNo());

				pFacade.addPreFellingSurvey(preFellingSurvey, false);

				if (tFacade.addTagging(tagging, false) != 0)
				{
					log(tFacade, "Tambah penandaan, ID " + tagging.getTaggingID());
					boolean add = updateYear(tagging);
					
					if (models.contains(tagging))
					{
						addMessage(FacesMessage.SEVERITY_INFO, null, tagging + " berjaya dikemaskini.");
						models.set(models.indexOf(tagging), tagging);
					}
					else
					{
						if (accessLevel == 2 || accessLevel == 3)
						{
							if (district.getDistrictID() == tagging.getDistrictID())
							{
								addMessage(FacesMessage.SEVERITY_INFO, null, tagging + " berjaya ditambahkan.");
								
								if (add)
									models.add(tagging);
							}
							else
								addMessage(FacesMessage.SEVERITY_INFO, null, tagging + " berjaya ditambahkan, namun sesi penandaan ini tidak termasuk dalam tanggung jawab anda.");
						}
						else if (accessLevel == 4)
						{
							if (range.getRangeID() == tagging.getRangeID() && tagging.getTenderNo() == null)
							{
								addMessage(FacesMessage.SEVERITY_INFO, null, tagging + " berjaya ditambahkan.");
								
								if (add)
									models.add(tagging);
							}
							else
								addMessage(FacesMessage.SEVERITY_INFO, null, tagging + " berjaya ditambahkan, namun sesi penandaan ini tidak termasuk dalam tanggung jawab anda.");
						}
						else if (accessLevel == 5)
						{
							if (staffID.equals(tagging.getTeamLeaderID()) || recorders != null && recorders.contains(user))
							{
								addMessage(FacesMessage.SEVERITY_INFO, null, tagging + " berjaya ditambahkan.");
								
								if (add)
									models.add(tagging);
							}
							else
								addMessage(FacesMessage.SEVERITY_INFO, null, tagging + " berjaya ditambahkan, namun sesi penandaan ini tidak termasuk dalam tanggung jawab anda.");
						}
						else if (accessLevel == 6)
						{
							boolean valid = false;

							for (Tender t : tenders)
							{
								if (t.equals(tenderTagging))
								{
									valid = true;
									break;
								}
							}

							if (valid)
							{
								addMessage(FacesMessage.SEVERITY_INFO, null, tagging + " berjaya ditambahkan.");
								
								if (add)
									models.add(tagging);
							}
							else
								addMessage(FacesMessage.SEVERITY_INFO, null, tagging + " berjaya ditambahkan, namun sesi penandaan ini tidak termasuk dalam tanggung jawab anda.");
						}
					}
				}
				else
					addMessage(FacesMessage.SEVERITY_WARN, null, tagging + " tidak dapat ditambahkan.");

				byte[] bytes1 = (byte[]) ois.readObject();
				byte[] bytes2 = (byte[]) ois.readObject();

				if (bytes1 != null || bytes2 != null)
				{
					ExternalContext external = FacesContext.getCurrentInstance().getExternalContext();
					String path = external.getRealPath("/") + "files/tagging/", name = "_" + tagging.getTaggingID() + ".png";

					if (bytes1 != null)
					{
						BufferedImage bi = ImageIO.read(new ByteArrayInputStream(bytes1));
						File plan = new File(path + "pelan" + name);

						plan.getParentFile().mkdirs();
						ImageIO.write(bi, "png", plan);
						tagging.setPlanUploaded(true);
					}

					if (bytes2 != null)
					{
						BufferedImage bi = ImageIO.read(new ByteArrayInputStream(bytes2));
						File stock = new File(path + "stok" + name);

						stock.getParentFile().mkdirs();
						ImageIO.write(bi, "png", stock);
						tagging.setStockUploaded(true);
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
				BufferedImage bi = ImageIO.read(uf.getInputstream());
				ExternalContext external = FacesContext.getCurrentInstance().getExternalContext();
				File file = new File(external.getRealPath("/") + "files/tagging/pelan_" + model.getTaggingID() + ".png");

				file.getParentFile().mkdirs();
				ImageIO.write(bi, "png", file);
				model.setPlanUploaded(true);
				addMessage(FacesMessage.SEVERITY_INFO, null, "Gambar pelan kerja berjaya dimuat naik.");
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
				BufferedImage bi = ImageIO.read(uf.getInputstream());
				ExternalContext external = FacesContext.getCurrentInstance().getExternalContext();
				File file = new File(external.getRealPath("/") + "files/tagging/stok_" + model.getTaggingID() + ".png");

				file.getParentFile().mkdirs();
				ImageIO.write(bi, "png", file);
				model.setStockUploaded(true);
				addMessage(FacesMessage.SEVERITY_INFO, null, "Gambar peta stok berjaya dimuat naik.");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				addMessage(e);
			}
		}
	}

	private String generate()
	{
		String key = "";
		ArrayList<TaggingLimitException> taggingLimitExceptions = model.getTaggingLimitExceptions();

		if (!taggingLimitExceptions.isEmpty())
		{
			key += model.getTaggingID();

			for (TaggingLimitException taggingLimitException : taggingLimitExceptions)
				key += ";" + taggingLimitException.getBlockNo() + ":" + taggingLimitException.getPlotNo() + ":" + taggingLimitException.getQuantity();

			key = StringProtector.encrypt(key, 1);
		}

		return key;
	}
	
	private boolean updateYear(Tagging tagging)
	{
		int year = tagging.getYear();
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
					yearList.add(new SelectItem(i, Math.max(i - 4, yearRange[0]) + " - " + i));
			}
		}
		
		if (year > selectedYearRange || year < selectedYearRange - 4)
			add = false;
		
		return add;
	}
}