package my.edu.utem.ftmk.fis9.postfelling.controller.mbean;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import my.edu.utem.ftmk.fis9.global.controller.manager.AbstractFacade;
import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.global.util.ArrayListConverter;
import my.edu.utem.ftmk.fis9.global.util.EmailSender;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.Contractor;
import my.edu.utem.ftmk.fis9.maintenance.model.Designation;
import my.edu.utem.ftmk.fis9.maintenance.model.District;
import my.edu.utem.ftmk.fis9.maintenance.model.Forest;
import my.edu.utem.ftmk.fis9.maintenance.model.Hall;
import my.edu.utem.ftmk.fis9.maintenance.model.Range;
import my.edu.utem.ftmk.fis9.maintenance.model.RegenerationSpecies;
import my.edu.utem.ftmk.fis9.maintenance.model.Species;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.maintenance.model.Tender;
import my.edu.utem.ftmk.fis9.postfelling.controller.manager.PostFellingFacade;
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingInspectionLine;
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingReport;
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingSurvey;
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingSurveyCard;
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingSurveyRecord;
import my.edu.utem.ftmk.fis9.postfelling.util.PostFellingSurveyLetterGenerator;
import my.edu.utem.ftmk.fis9.prefelling.controller.manager.PreFellingFacade;
import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingSurvey;



/**
 * @author Satrya Fajri Pratama, Zurina
 */
@ViewScoped
@ManagedBean(name = "postFellingSurveyMBean")
public class PostFellingSurveyManagedBean extends AbstractManagedBean<PostFellingSurvey>
{
	private static final long serialVersionUID = VERSION;


	private District district;
	private Range range;
	private ArrayList<PreFellingSurvey> preFellingSurveys;
	private ArrayList<Forest> forests;
	private ArrayList<Tender> tenders;
	private ArrayList<District> districts;
	private ArrayList<Range> ranges;
	private ArrayList<Staff> staffs;
	private ArrayList<Species> speciesList;
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

	public PostFellingSurveyManagedBean()
	{
		try (MaintenanceFacade mFacade = new MaintenanceFacade(); PreFellingFacade prefFacade = new PreFellingFacade(); PostFellingFacade pFacade = new PostFellingFacade();)
		{
			AbstractFacade.group(mFacade, prefFacade, pFacade);

			Staff user = getCurrentUser();
			String staffID = user.getStaffID();
			int stateID = user.getStateID(), designationID = user.getDesignationID();
			forestList = new ArrayList<>();

			yearList = new ArrayList<>();
			yearList = new ArrayList<>();
			yearRange = pFacade.getPostFellingSurveyYearRange();

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

			ArrayList<Designation> designations = mFacade.getDesignations();

			if (stateID == 0)
			{
				ArrayList<PreFellingSurvey> tempPreFellingSurveys = new ArrayList<>();
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
					//models = pFacade.getPostFellingSurveys();
					tenders = mFacade.getTenders("A");
					preFellingSurveys = prefFacade.getPreFellingSurveys(false);
					downtype = "pfso";

					for (PreFellingSurvey preFellingSurvey : preFellingSurveys)
						if (!preFellingSurvey.isOpen() && !preFellingSurvey.isPostFellingCreated())
							tempPreFellingSurveys.add(preFellingSurvey);

					preFellingSurveys = tempPreFellingSurveys;
				}
				else
				{
					//models = pFacade.getPostFellingSurveys();
					tenders = mFacade.getTenders("A", user);
					accessLevel = 6;
					uptype = "pfso";
				}


			}
			else
			{
				State state = mFacade.getState(stateID);
				forests = mFacade.getForests(state);
				districts = mFacade.getDistricts(state);
				district = mFacade.getDistrict(user);

				if (staffID.equals(state.getDirectorID()) || staffID.equals(state.getDeputyDirector1ID()) || staffID.equals(state.getDeputyDirector2ID()) || staffID.equals(state.getSeniorAsstDirector1ID()) || staffID.equals(state.getAsstDirector1ID()) || designationID == 11 || designationID == 5 || (district != null && district.getOfficerID().equals(staffID)))				
				{
					if (district != null) {
						//models = pFacade.getPostFellingSurveys(district);
						preFellingSurveys = prefFacade.getPreFellingSurveys(district, 0, new GregorianCalendar().get(GregorianCalendar.YEAR));
						ranges = district.getRanges();
						staffs = mFacade.getStaffs(state);
					}
					else
					{

						//models = pFacade.getPostFellingSurveys(state);
						preFellingSurveys = prefFacade.getPreFellingSurveys(state,false);
					}
					downtype = "pfso";
					uptype = "pfco";
					accessLevel = staffID.equals(state.getDirectorID()) ? 1 : 2;
					tenders = mFacade.getTenders("A", state);

					//districts = mFacade.getDistricts(state);

					ArrayList<PreFellingSurvey> tempSurveys = new ArrayList<>();
					staffs = mFacade.getStaffs(state);//, "DesignationID", designationSivil);
					staffList = new ArrayList<>();

					for (PreFellingSurvey survey : preFellingSurveys)
					{

						if (!survey.isOpen() && !survey.isPostFellingCreated())
							tempSurveys.add(survey);
					}

					preFellingSurveys = tempSurveys;

					/*for (District district : districts)
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
					}*/





					for (Designation designation : designations)
					{
						designationID = designation.getDesignationID();

						if (user.getDesignationID() <= designationID)
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
				else
				{

					if (user.getContractorID() == null)
					{
						staffs = mFacade.getStaffs(state);//, "DesignationID", designationSivil);
						accessLevel = 3;
					}
					else
					{
						staffs = mFacade.getStaffs(state, "ContractorID", user.getContractorID());
						accessLevel = 4;
					}

					//models = pFacade.getPostFellingSurveys(user);
					staffs.remove(user);


					staffList = new ArrayList<>();
					//ArrayList<Designation> designations = mFacade.getDesignations();

					uptype = "pfso";
					downtype = "pfco";


					for (Designation designation : designations)
					{
						designationID = designation.getDesignationID();

						if (user.getDesignationID() <= designationID)
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

					if (models != null)
					{
						for (Forest forest : forests)
						{
							for (PostFellingSurvey postFellingSurvey : models)
							{
								if (forest.getForestID() == postFellingSurvey.getForestID())
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

			if (tenders != null)
			{
				ArrayList<Tender> temp = new ArrayList<>();
				for (Tender tender : tenders)
					if (tender.getWorkType().equals("P") && tender.getStateID() == stateID)
						temp.add(tender);

				tenders = temp;
			}



			/*if (models != null)
			{
				sort(models);
				ExternalContext external = FacesContext.getCurrentInstance().getExternalContext();


				for (PostFellingSurvey postFellingSurvey : models)
				{

					File file = new File(external.getRealPath("/") + "files/post-f/" + postFellingSurvey.getPostFellingSurveyID() + ".png");
					postFellingSurvey.setRecorders(mFacade.getStaffs(postFellingSurvey,true));
					postFellingSurvey.setPlanUploaded(file.exists());
				}
			}
			else
				models = new ArrayList<>();*/




		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

	}

	public void handlePreFellingSurveyIDChange()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			State state = new State();

			for (PreFellingSurvey survey : preFellingSurveys)
				if (survey.getPreFellingSurveyID() == model.getPreFellingSurveyID())
					state.setStateID(survey.getStateID());

			ArrayList<Tender> temp = new ArrayList<>();
			tenders = facade.getTenders("A", state);

			for (Tender tender : tenders)
				if (tender.getWorkType().equals("P"))
					temp.add(tender);

			tenders = temp;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public PostFellingSurvey getPostFellingSurvey()
	{
		return model;
	}

	public void setPostFellingSurvey(PostFellingSurvey postFellingSurvey)
	{
		this.model = postFellingSurvey;
	}

	public ArrayList<PostFellingSurvey> getPostFellingSurveys()
	{
		return models;
	}

	public void setPostFellingSurvey(ArrayList<PostFellingSurvey> postFellingSurveys)
	{
		this.models = postFellingSurveys;
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

	public int getAccessLevel()
	{
		return accessLevel;
	}

	@Override
	public void handleOpen() 
	{
		if (addOperation)
		{
			model = new PostFellingSurvey();
			Staff staff = getCurrentUser();
			GregorianCalendar calendar = new GregorianCalendar();
			model.setPostFellingSurveyID(System.currentTimeMillis());
			model.setYear(calendar.get(GregorianCalendar.YEAR));
			model.setOpen(true);
			model.setCreatorID(staff.getStaffID());
			model.setCreatorName(staff.getName());

		}
		else
		{
			model = (PostFellingSurvey) copy(models, model);

			if (!model.isOpen()) {
				if (accessLevel < 2)
					prepareClose();	
			}


		}
	}

	public void handleCloseInspection()
	{

		try 
		{
			PostFellingFacade pFacade = new PostFellingFacade();
			//model.setOpen(false);
			model.setInspectionOpen(2);
			finalizeModelEntry(pFacade.updatePostFellingSurvey(model), addOperation, pFacade, "bancian, ID " + model.getPostFellingSurveyID(), null, models, model);

		}
		catch (Exception e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public void handleOpenInspection()
	{
		try 
		{
			PostFellingFacade pFacade = new PostFellingFacade();
			model = (PostFellingSurvey) copy(models, model);
			//model.setOpen(false);
			model.setInspectionOpen(0);
			finalizeModelEntry(pFacade.updatePostFellingSurvey(model), addOperation, pFacade, "bancian, ID " + model.getPostFellingSurveyID(), null, models, model);

		}
		catch (Exception e)
		{
			e.printStackTrace();
			addMessage(e);
		}

	}

	public void handleCloseSurvey()
	{
		String message = null;

		try 
		{
			PostFellingFacade pFacade = new PostFellingFacade();
			model = (PostFellingSurvey) copy(models, model);
			ArrayList<PostFellingSurveyCard> cards = pFacade.getPostFellingSurveyCards(model);
			String cardMsg = "";
			boolean error = false;
			for (PostFellingSurveyCard card: cards)
			{
				if (card.getAspectID() == 0 || card.getBananaID() == 0 || card.getForestTypeID() == 0 || card.getGingerID() == 0 || card.getElevationID() == 0 || card.getResamID() == 0 || card.getSlopeLocationID() == 0  || card.getSoilStatusID() == 0) {
					cardMsg += "" + card.getLineNo() + " " + card.getPlotNo() + ", ";
					error = true;
				}
			}

			if (error) {
				addMessage(FacesMessage.SEVERITY_ERROR, null, "Proses TUTUP bancian TIDAK BERJAYA. Sila lengkapkan maklumat hutan pada kad bancian " + cardMsg);
			}else
			{
				model.setOpen(false);
				finalizeModelEntry(pFacade.updatePostFellingSurvey(model), addOperation, pFacade, "bancian, ID " + model.getPostFellingSurveyID(), null, models, model);
				message = "Sesi Inventori Hutan Selepas Tebangan (Post-Felling) telah diselesaikan oleh " + model.getCreatorName() + " untuk:<br/><br/><table border='0'><tr><td>- Hutan simpan</td><td>:</td><td>" + model.getForestName() + "</td></tr><tr><td>- No. kompartmen/sub kompartmen</td><td>:</td><td>" + model.getComptBlockNo() + "</td></tr><tr><td>- Keluasan</td><td>:</td><td>" + model.getArea() + " hektar</td></tr></table><br/>Sila log masuk ke FIS9 untuk tindakan anda seterusnya.";
			}	
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addMessage(e);
		}


		try
		{

			if (message != null)
				new EmailSender().send(true, "Inventori Hutan Selepas Tebangan - " + model.getForestName() + " " + model.getComptBlockNo(), message, model.getCreatorID());
		}
		catch (Exception e)
		{
			e.printStackTrace();

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

			ArrayList<Tender> temp = new ArrayList<>();
			tenders = facade.getTenders("A", state);

			for (Tender tender : tenders)
				if (tender.getWorkType().equals("F"))
					temp.add(tender);

			tenders = temp;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public void handleOpenConfig()
	{
		model = (PostFellingSurvey) copy(models, model);
		ArrayList<Staff> recorders = model.getRecorders();

		if (recorders != null)
		{
			int size = recorders.size();
			selectedStaffs = new String[size];

			for (int i = 0; i < size; i++)
				selectedStaffs[i] = recorders.get(i).getStaffID();
		}
	}



	public void postFellingSurveyEntry()
	{
		try (MaintenanceFacade mFacade = new MaintenanceFacade(); PostFellingFacade pFacade = new PostFellingFacade())
		{
			AbstractFacade.group(mFacade, pFacade);
			String message = null;

			if (preFellingSurveys != null)
			{
				for (PreFellingSurvey s : preFellingSurveys)
				{
					if (model.getPreFellingSurveyID() == s.getPreFellingSurveyID())
					{

						model.setForestID(s.getForestID());
						model.setRangeID(s.getForestID());
						model.setDistrictID(s.getDistrictID());
						model.setStateID(s.getStateID());
						model.setForestCode(s.getForestCode());
						model.setForestName(s.getForestName());
						model.setComptBlockNo(s.getComptBlockNo());
						model.setArea(s.getArea());
						model.setDistrictCode(s.getDistrictCode());
						model.setDistrictName(s.getDistrictName());
						model.setStateCode(s.getStateCode());
						model.setStateName(s.getStateName());
						model.setRangeName(s.getRangeName());
					}
				}
			}



			if (staffs != null) 
			{
				if (model.getTeamLeaderID().isEmpty())
					model.setTeamLeaderID(null);
				else
				{	
					for (Staff staff : staffs)
						if (staff.getStaffID().equals(model.getTeamLeaderID()))
							model.setTeamLeaderName(staff.getName());
				}
			}

			if (model.getTenderNo() != null)
			{
				if (model.getTenderNo().trim().isEmpty())
				{
					model.setTenderNo(null);

				}
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

			if (model.getTeamLeaderID() == null && model.getTenderNo() == null)
			{
				model.setStartDate(null);
				model.setEndDate(null);
			}

			if(model.getStartDate() == null || model.getEndDate() == null)
			{
				model.setStartDate(null);
				model.setEndDate(null);
			}

			if (model.getTenderNo() != null)
				model.setTeamLeaderID(null);			

			if (model.getTeamLeaderID() != null)
				model.setTenderNo(null);		


			if (addOperation)
			{
				finalizeModelEntry(pFacade.addPostFellingSurvey(model, true), addOperation, pFacade, "bancian, ID " + model.getPostFellingSurveyID(), null, models, model);
				message = "Sesi Inventori Hutan Selepas Tebangan (Post-Felling) baru telah dibuka oleh " + model.getCreatorName() + " untuk:<br/><br/><table border='0'><tr><td>- Hutan simpan</td><td>:</td><td>" + model.getForestName() + "</td></tr><tr><td>- No. kompartmen/sub kompartmen</td><td>:</td><td>" + model.getComptBlockNo() + "</td></tr><tr><td>- Keluasan</td><td>:</td><td>" + model.getArea() + " hektar</td></tr></table><br/>Sila log masuk ke FIS9 untuk tindakan anda seterusnya.";
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
				String cardMsg = "";
				if (!model.isOpen()) {
					ArrayList<PostFellingSurveyCard> cards; cards = pFacade.getPostFellingSurveyCards(model);
					boolean error = false;
					for (PostFellingSurveyCard card: cards)
					{
						if (card.getAspectID() == 0 || card.getBananaID() == 0 || card.getForestTypeID() == 0 || card.getGingerID() == 0 || card.getElevationID() == 0 || card.getResamID() == 0 || card.getSlopeLocationID() == 0  || card.getSoilStatusID() == 0) {
							cardMsg += "" + card.getLineNo() + " " + card.getPlotNo() + ", ";
							error = true;
						}
					}

					if (error) {
						addMessage(FacesMessage.SEVERITY_ERROR, null, "Proses TUTUP bancian TIDAK BERJAYA. Sila lengkapkan maklumat hutan pada kad bancian " + cardMsg);
						model.setOpen(true);;
					}
				}

				finalizeModelEntry(pFacade.updatePostFellingSurvey(model), addOperation, pFacade, "bancian, ID " + model.getPostFellingSurveyID(), null, models, model);
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
				String name = "Bancian_" + model.getForestName().replaceAll(" ", "") + "_" + model.getComptBlockNo() + "_" + model.getYear() + ".pdf";
				int level = 0;

				for (District d : districts)
					if (d.getDistrictID() == model.getDistrictID())
						district = d;

				//for (Range r : ranges)
				//	if (r.getRangeID() == model.getRangeID())
				//		range = r;

				if (accessLevel == 0 || accessLevel == 1 || accessLevel == 2)
				{
					level = 1;
					name = (model.isOpen() ? "Buka" : "Tutup") + name;	
				}
				else if (accessLevel == 3 || accessLevel == 4)
				{
					if (model.getTenderNo() == null)
					{
						level = 2;
						name = "Arahan" + name;
					}
				}
				else if (accessLevel == 3)
				{
					if (model.getTenderNo() == null)
					{
						level = 3;
						name = "Lantikan" + name;
					}
				}

				File file = new File(external.getRealPath("/") + "files/post-f/" + name);

				file.getParentFile().mkdirs();
				if (level != 0)
					PostFellingSurveyLetterGenerator.generate(file, model, district, range, level);


				if (message != null &&  model.getTeamLeaderID()!= null)
					new EmailSender().send(true, "Inventori Hutan Selepas Tebangan - " + model.getForestName() + " " + model.getComptBlockNo(), message, model.getTeamLeaderID());
				if (message != null && model.getTenderNo()!= null)
					new EmailSender().send(true, "Inventori Hutan Selepas Tebangan - " + model.getForestName() + " " + model.getComptBlockNo(), message,  mFacade.getTender(model.getTenderNo()).getContractorID());


			}
			catch (Exception e)
			{
				e.printStackTrace();
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

	public void prepareClose()
	{
		if (!model.isOpen())
		{
			try (MaintenanceFacade mFacade = new MaintenanceFacade(); PostFellingFacade pFacade = new PostFellingFacade();)
			{
				AbstractFacade.group(mFacade, pFacade);

				State state = new State();

				state.setStateID(model.getStateID());
				
				ArrayList<RegenerationSpecies> regenerationSpeciesList = mFacade.getRegenerationSpeciesList(state);
				speciesList = mFacade.getSpeciesList(state);

				if (model.getPostFellingReport() == null)
				{
					model.setPostFellingSurveyCards(pFacade.getPostFellingSurveyCards(model));
					model.setPostFellingReport(new PostFellingReport(model,regenerationSpeciesList));
				}
			}
			catch (SQLException e)
			{
				e.printStackTrace();
				addMessage(e);
			}
		}
	}

	public StreamedContent download(PostFellingSurvey postFellingSurvey, int level)
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		String name = "BancianPostF_" + postFellingSurvey.getForestName().replaceAll(" ", "") + "_" + postFellingSurvey.getComptBlockNo() + "_" + postFellingSurvey.getYear() + "." + (level != 0 ? "pdf" : downtype), type = null;
		try (PostFellingFacade pFacade = new PostFellingFacade();)
		{
			postFellingSurvey.setPostFellingSurveyCards(pFacade.getPostFellingSurveyCards(postFellingSurvey));
			postFellingSurvey.setPostFellingInspectionLines(pFacade.getPostFellingInspectionLines(postFellingSurvey));

		}
		catch (Exception e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		if (level != 0)
		{
			if (level == 1 || level == 2 )
				name = (postFellingSurvey.isOpen() ? "Buka" : "Tutup") + name;

			else if (level == 3)
				name = "Lantikan" + name;
		}

		File file = new File(external.getRealPath("/") + "files/post-f/" + name);
		StreamedContent content = null;
		file.getParentFile().mkdirs();

		try
		{
			if (level != 0)
			{
				if (!file.exists())
				{

					for (District d : districts)
						if (d.getDistrictID() == postFellingSurvey.getDistrictID())
							district = d;

					PostFellingSurveyLetterGenerator.generate(file, postFellingSurvey, district, range, level);
				}

				type = "application/pdf";
			}
			else
			{
				type = "application/octet-stream";
				Staff[] staffs = null;
				District district = null;
				Contractor contractorPreF = null;
				Contractor contractorPostF = null;
				Tender tenderPreF = null;
				Tender tenderPostF = null;
				PreFellingSurvey preFellingSurvey = null;

				ObjectOutputStream oos = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(file)));

				try (MaintenanceFacade mFacade = new MaintenanceFacade(); PreFellingFacade pFacade = new PreFellingFacade();)
				{
					AbstractFacade.group(mFacade, pFacade);

					district = mFacade.getDistrict(postFellingSurvey.getDistrictID());
					ArrayList<Range> ranges = district.getRanges();
					ArrayList<Staff> temp = new ArrayList<>();

					temp.add(mFacade.getStaff(postFellingSurvey.getCreatorID(), null));
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

					if (postFellingSurvey.getTeamLeaderID() != null)
						temp.add(mFacade.getStaff(postFellingSurvey.getTeamLeaderID(), null));

					if (postFellingSurvey.getTenderNo() != null)
					{
						tenderPostF = mFacade.getTender(postFellingSurvey.getTenderNo());
						contractorPostF = mFacade.getContractor(tenderPostF.getContractorID());
					}

					staffs = temp.toArray(new Staff[0]);
					preFellingSurvey = pFacade.getPreFellingSurvey(postFellingSurvey.getPreFellingSurveyID());

					if (preFellingSurvey.getTenderNo() != null)
					{
						tenderPreF = mFacade.getTender(preFellingSurvey.getTenderNo());
						contractorPreF = mFacade.getContractor(tenderPreF.getContractorID());
					}
				}

				oos.writeInt(41);
				oos.writeObject(contractorPreF);
				oos.writeObject(contractorPostF);
				oos.writeObject(tenderPreF);
				oos.writeObject(tenderPostF);
				oos.writeObject(staffs);
				oos.writeObject(district);
				oos.writeObject(preFellingSurvey);
				oos.writeObject(postFellingSurvey);

				String path = external.getRealPath("/") + "files/post-f/", image = "_" + postFellingSurvey.getPostFellingSurveyID() + ".png";
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

	private void save(PostFellingFacade facade, ArrayList<PostFellingSurveyCard> currentPostFellingSurveyCards, ArrayList<PostFellingSurveyCard> postFellingSurveyCards, boolean strict) throws SQLException
	{
		int totalPostFellingSurveyCard = 0, totalRecord = 0;

		for (PostFellingSurveyCard postFellingSurveyCard : postFellingSurveyCards)
		{
			if (strict)
				if (postFellingSurveyCard.getInspectorID() == null)
					continue;

			ArrayList<PostFellingSurveyRecord> currentPostFellingSurveyRecords = null;
			ArrayList<PostFellingSurveyRecord> postFellingSurveyRecords = postFellingSurveyCard.getPostFellingSurveyRecords();
			int type = 1;

			if (facade.addPostFellingSurveyCard(postFellingSurveyCard, false) != 0)
			{
				totalPostFellingSurveyCard++;

				if (!currentPostFellingSurveyCards.contains(postFellingSurveyCard))
				{
					PostFellingSurveyCard temp = null;

					for (PostFellingSurveyCard c : currentPostFellingSurveyCards)
						if (postFellingSurveyCard.toString().equals(c.toString()))
							temp = c;

					if (temp != null)
					{
						type = -1;
						currentPostFellingSurveyRecords = temp.getPostFellingSurveyRecords();

						log(facade, "Kemaskini kad bancian, ID " + temp.getPostFellingSurveyCardID());
						postFellingSurveyCard.setPostFellingSurveyCardID(temp.getPostFellingSurveyCardID());
					}
					else
					{
						currentPostFellingSurveyCards.add(postFellingSurveyCard);
						log(facade, "Tambah kad bancian, ID " + postFellingSurveyCard.getPostFellingSurveyCardID());
					}
				}
				else
				{
					type = 0;
					currentPostFellingSurveyRecords = currentPostFellingSurveyCards.get(currentPostFellingSurveyCards.indexOf(postFellingSurveyCard)).getPostFellingSurveyRecords();

					log(facade, "Kemaskini kad bancian, ID " + postFellingSurveyCard.getPostFellingSurveyCardID());
				}

				sort(currentPostFellingSurveyCards);
			}

			if (type != 1)
				currentPostFellingSurveyCards.set(currentPostFellingSurveyCards.indexOf(postFellingSurveyCard), postFellingSurveyCard);

			for (PostFellingSurveyRecord postFellingSurveyRecord : postFellingSurveyRecords)
			{
				if (type == -1)
					postFellingSurveyRecord.setPostFellingSurveyCardID(postFellingSurveyCard.getPostFellingSurveyCardID());

				if (facade.addPostFellingSurveyRecord(postFellingSurveyRecord, false) != 0)
				{
					totalRecord++;
					log(facade, "Tambah rekod bancian, ID " + postFellingSurveyRecord.getPostFellingSurveyRecordID());
				}
			}

			if (currentPostFellingSurveyRecords != null)
			{
				postFellingSurveyRecords.addAll(currentPostFellingSurveyRecords);
				sort(postFellingSurveyRecords);
			}
		}

		if (totalPostFellingSurveyCard != 0 || totalRecord != 0)
			addMessage(FacesMessage.SEVERITY_INFO, null,
					totalPostFellingSurveyCard + " kad bancian dan " + totalRecord + " rekod bancian berjaya ditambahkan.");
		else
			addMessage(FacesMessage.SEVERITY_INFO, null,
					"Tiada kad bancian dan rekod bancian berjaya ditambahkan.");
	}

	public void handleYearChange()
	{
		try (MaintenanceFacade mFacade = new MaintenanceFacade(); PostFellingFacade pFacade = new PostFellingFacade();)
		{
			AbstractFacade.group(mFacade, pFacade);

			Staff user = getCurrentUser();
			String staffID = user.getStaffID();
			int stateID = user.getStateID(), designationID = user.getDesignationID(), endYear = selectedYearRange, startYear = selectedYearRange - 4;

			if (stateID == 0)
			{
				if (designationID == 0)
					models = pFacade.getPostFellingSurveys(startYear, endYear);
				else
					models = pFacade.getPostFellingSurveys(user, startYear, endYear);
			}
			else
			{
				State state = mFacade.getState(stateID);

				if (staffID.equals(state.getDirectorID()) || staffID.equals(state.getDeputyDirector1ID()) || staffID.equals(state.getDeputyDirector2ID()) || staffID.equals(state.getSeniorAsstDirector1ID()) || staffID.equals(state.getAsstDirector1ID()) || designationID == 11 || designationID == 5 || (district != null && district.getOfficerID().equals(staffID)))									
				{
					models = pFacade.getPostFellingSurveys(state, startYear, endYear);
				}
				else
				{

					models = pFacade.getPostFellingSurveys(user, startYear, endYear);
					district = mFacade.getDistrict(user);
					range = mFacade.getRange(user);
					if (models != null)
					{
						for (Forest forest : forests)
						{
							for (PostFellingSurvey postFellingSurvey : models)
							{
								if (forest.getForestID() == postFellingSurvey.getForestID())
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

			if (models != null)
			{
				sort(models);
				ExternalContext external = FacesContext.getCurrentInstance().getExternalContext();

				for (PostFellingSurvey postFellingSurvey : models)
				{
					//String path = external.getRealPath("/") + "files/post-f/", name = "_" + postFellingSurvey.getPostFellingSurveyID() + ".png";
					File file = new File(external.getRealPath("/") + "files/post-f/" + postFellingSurvey.getPostFellingSurveyID() + ".png");

					//File file = new File(path + "pelan" + name);

					postFellingSurvey.setRecorders(mFacade.getStaffs(postFellingSurvey,true));
					postFellingSurvey.setPlanUploaded(file.exists());

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

	public void upload(FileUploadEvent event)
	{
		UploadedFile file = event.getFile();

		if (file != null)
		{
			try (ObjectInputStream ois = new ObjectInputStream(new GZIPInputStream(file.getInputstream())); MaintenanceFacade mFacade = new MaintenanceFacade(); PreFellingFacade peFacade = new PreFellingFacade(); PostFellingFacade pFacade = new PostFellingFacade();)
			{
				AbstractFacade.group(mFacade, pFacade);

				if (ois.readInt() != 41)
					throw new InvalidClassException("Not PostFellingSurvey");

				Contractor contractorPreF = (Contractor) ois.readObject();
				Contractor contractorPostF = (Contractor) ois.readObject();
				Tender tenderPreF = (Tender) ois.readObject();
				Tender tenderPostF = (Tender) ois.readObject();
				Staff[] staffs = (Staff[]) ois.readObject();
				District district = (District) ois.readObject();

				if (contractorPreF != null && mFacade.addContractor(contractorPreF) != 0)
					log(mFacade, "Tambah kontraktor, ID " + contractorPreF.getContractorID());

				if (contractorPostF != null && mFacade.addContractor(contractorPostF) != 0)
					log(mFacade, "Tambah kontraktor, ID " + contractorPostF.getContractorID());

				if (tenderPreF != null && mFacade.addTender(tenderPreF) != 0)
					log(mFacade, "Tambah sebut harga, ID " + tenderPreF.getTenderNo());

				if (tenderPostF != null && mFacade.addTender(tenderPostF) != 0)
					log(mFacade, "Tambah sebut harga, ID " + tenderPostF.getTenderNo());

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
				PostFellingSurvey postFellingSurvey = (PostFellingSurvey) ois.readObject();
				ArrayList<PostFellingSurveyCard> postFellingSurveyCards = postFellingSurvey.getPostFellingSurveyCards();
				ArrayList<PostFellingSurveyCard> currentPostFellingSurveyCards = new ArrayList<PostFellingSurveyCard>();
				ArrayList<Staff> recorders = postFellingSurvey.getRecorders();

				if (recorders != null)
					for (Staff recorder : recorders)
						if (mFacade.addStaff(recorder) != 0)
							log(mFacade, "Tambah pekerja dan akses, ID " + recorder.getStaffID());

				peFacade.addPreFellingSurvey(preFellingSurvey, false);

				if (pFacade.addPostFellingSurvey(postFellingSurvey, false) != 0)
				{
					log(pFacade, "Tambah bancian, ID " + postFellingSurvey.getPostFellingSurveyID());
					boolean add = updateYear(postFellingSurvey);
					
					if (postFellingSurvey.getPostFellingSurveyCards().size() > 0)
						save(pFacade, currentPostFellingSurveyCards, postFellingSurveyCards, false);

					ArrayList<PostFellingInspectionLine> postFellingInspectionLines = postFellingSurvey.getPostFellingInspectionLines();
					
					if (postFellingInspectionLines!=null)
						for (PostFellingInspectionLine line : postFellingInspectionLines)
							pFacade.addPostFellingInspectionLine(line, true);
					
					if (models.contains(postFellingSurvey))
					{
						addMessage(FacesMessage.SEVERITY_INFO, null, postFellingSurvey + " berjaya dikemaskini.");
						models.set(models.indexOf(postFellingSurvey), postFellingSurvey);
					}
					else
					{
						addMessage(FacesMessage.SEVERITY_INFO, null, postFellingSurvey + " berjaya ditambahkan.");
						
						if (add)
							models.add(postFellingSurvey);
					}

				}
				else
					addMessage(FacesMessage.SEVERITY_WARN, null, postFellingSurvey + " tidak dapat ditambahkan.");

				byte[] bytes1 = (byte[]) ois.readObject();
				byte[] bytes2 = (byte[]) ois.readObject();

				if (bytes1 != null || bytes2 != null)
				{
					ExternalContext external = FacesContext.getCurrentInstance().getExternalContext();
					String path = external.getRealPath("/") + "files/post-f/", name = "_" + postFellingSurvey.getPostFellingSurveyID() + ".png";

					if (bytes1 != null)
					{
						BufferedImage bi = ImageIO.read(new ByteArrayInputStream(bytes1));
						File plan = new File(path + "pelan" + name);

						plan.getParentFile().mkdirs();
						ImageIO.write(bi, "png", plan);
						postFellingSurvey.setPlanUploaded(true);
					}

					if (bytes2 != null)
					{
						BufferedImage bi = ImageIO.read(new ByteArrayInputStream(bytes2));
						File stock = new File(path + "stok" + name);

						stock.getParentFile().mkdirs();
						ImageIO.write(bi, "png", stock);
						postFellingSurvey.setStockUploaded(true);
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
				File file = new File(external.getRealPath("/") + "files/post-f/" + model.getPostFellingSurveyID() + ".png");
				System.out.println("uploadPlan-FILE:"+file.getPath());
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

	public ArrayList<SelectItem> getYearList()
	{
		return yearList;
	}
	public int getSelectedYearRange()
	{
		return selectedYearRange;
	}

	public void setSelectedYearRange(int selectedYearRange)
	{
		this.selectedYearRange = selectedYearRange;
	}

	private boolean updateYear(PostFellingSurvey postFellingSurvey)
	{
		int year = postFellingSurvey.getYear();
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