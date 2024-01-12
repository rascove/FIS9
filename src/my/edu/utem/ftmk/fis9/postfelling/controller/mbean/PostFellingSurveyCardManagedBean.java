package my.edu.utem.ftmk.fis9.postfelling.controller.mbean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

import my.edu.utem.ftmk.fis9.global.controller.manager.AbstractFacade;
import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.global.util.ArrayListConverter;
import my.edu.utem.ftmk.fis9.global.util.EmailSender;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.AreaStatus;
import my.edu.utem.ftmk.fis9.maintenance.model.Aspect;
import my.edu.utem.ftmk.fis9.maintenance.model.Banana;
import my.edu.utem.ftmk.fis9.maintenance.model.Contractor;
import my.edu.utem.ftmk.fis9.maintenance.model.Designation;
import my.edu.utem.ftmk.fis9.maintenance.model.District;
import my.edu.utem.ftmk.fis9.maintenance.model.Dominance;
import my.edu.utem.ftmk.fis9.maintenance.model.Elevation;
import my.edu.utem.ftmk.fis9.maintenance.model.Fertility;
import my.edu.utem.ftmk.fis9.maintenance.model.ForestType;
import my.edu.utem.ftmk.fis9.maintenance.model.Ginger;
import my.edu.utem.ftmk.fis9.maintenance.model.LogQuality;
import my.edu.utem.ftmk.fis9.maintenance.model.PlotType;
import my.edu.utem.ftmk.fis9.maintenance.model.RegenerationSpecies;
import my.edu.utem.ftmk.fis9.maintenance.model.Resam;
import my.edu.utem.ftmk.fis9.maintenance.model.Silara;
import my.edu.utem.ftmk.fis9.maintenance.model.SlopeLocation;
import my.edu.utem.ftmk.fis9.maintenance.model.SoilStatus;
import my.edu.utem.ftmk.fis9.maintenance.model.SoilType;
import my.edu.utem.ftmk.fis9.maintenance.model.Species;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.maintenance.model.Tender;
import my.edu.utem.ftmk.fis9.maintenance.model.TreeStatus;
import my.edu.utem.ftmk.fis9.postfelling.controller.manager.PostFellingFacade;
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingInspectionLine;
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingReport;
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingSurvey;
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingSurveyCard;
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingSurveyRecord;
import my.edu.utem.ftmk.fis9.postfelling.util.PostFellingSurveyCardGenerator;
import my.edu.utem.ftmk.fis9.postfelling.util.PostFellingSurveyReportGenerator;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "postFellingSurveyCardMBean")
public class PostFellingSurveyCardManagedBean
		extends AbstractManagedBean<PostFellingSurveyCard>
{
	private static final long serialVersionUID = VERSION;
	private PostFellingSurvey postFellingSurvey;
	private PostFellingSurveyRecord postFellingSurveyRecord;
	private PlotType plotType;
	private PostFellingSurveyCard[] traditionalPostFellingSurveyCards;
	private ArrayList<PostFellingSurvey> postFellingSurveys;
	private ArrayList<ForestType> forestTypes;
	private ArrayList<SoilType> soilTypes;
	private ArrayList<AreaStatus> areaStatuses;
	private ArrayList<SoilStatus> soilStatuses;
	private ArrayList<Aspect> aspects;
	private ArrayList<SlopeLocation> slopeLocations;
	private ArrayList<Elevation> elevations;
	private ArrayList<Resam> resams;
	private ArrayList<Ginger> gingers;
	private ArrayList<Banana> bananas;
	private ArrayList<Species> speciesList;
	private ArrayList<PlotType> plotTypes;
	private ArrayList<LogQuality> logQualities;
	private ArrayList<TreeStatus> treeStatuses;
	private ArrayList<Silara> silaras;
	private ArrayList<Dominance> dominances;
	private ArrayList<Fertility> fertilities;
	private ArrayList<Staff> staffs;
	private ArrayList<SelectItem> lineList;
	private ArrayList<SelectItem> staffList;
	ArrayList<Integer> regenerationSpeciesIDs = new ArrayList<Integer>();
	private LinkedHashMap<String, ArrayList<PostFellingSurveyCard>> map;
	private String[] selectedLines;
	private String workingDates;
	private String recommendation;
	private String percentage;
	private String downtype;
	private String uptype;
	private boolean addPostFellingSurveyRecordOperation;
	private boolean validated;
	private boolean downloaded;
	private long selectedPostFellingSurveyID;
	private int accessLevel;

	public PostFellingSurveyCardManagedBean()
	{

		try (MaintenanceFacade mFacade = new MaintenanceFacade();
				PostFellingFacade pFacade = new PostFellingFacade();)
		{
			AbstractFacade.group(mFacade, pFacade);
			// String designationSivil = "20";
			Staff user = getCurrentUser();

			int stateID = user.getStateID(),
					designationID = user.getDesignationID();
			ArrayList<Designation> designations = mFacade.getDesignations();
			forestTypes = mFacade.getForestTypes();
			soilTypes = mFacade.getSoilTypes();

			areaStatuses = mFacade.getAreaStatuses();
			soilStatuses = mFacade.getSoilStatuses();
			aspects = mFacade.getAspects();
			slopeLocations = mFacade.getSlopeLocations();
			elevations = mFacade.getElevations();
			resams = mFacade.getResams();
			gingers = mFacade.getGingers();
			bananas = mFacade.getBananas();
			speciesList = mFacade.getSpeciesList();
			plotTypes = mFacade.getPlotTypes();
			logQualities = mFacade.getLogQualities();
			treeStatuses = mFacade.getTreeStatuses();
			silaras = mFacade.getSilaras();
			dominances = mFacade.getDominances();
			fertilities = mFacade.getFertilities();

			sort(speciesList);
			sort(plotTypes);

			if (stateID == 0)
			{
				if (designationID == 0)
				{
					postFellingSurveys = pFacade.getPostFellingSurveys();
					uptype = "pfco";
				}
				else
				{
					postFellingSurveys = pFacade.getPostFellingSurveys(user);
					accessLevel = 6;
					downtype = "pfco";
				}
			}
			else
			{
				State state = mFacade.getState(stateID);

				staffs = mFacade.getStaffs(state);// .getStaffs(state,
													// "DesignationID",
													// designationSivil);
				staffList = new ArrayList<>();

				if (user.getDesignationID() < 3 || user.getDesignationID() == 5
						|| user.getDesignationID() == 11)
				{
					postFellingSurveys = pFacade.getPostFellingSurveys(state);
					accessLevel = 1;
					uptype = "pfco";
					downtype = "pfso";

					for (Designation designation : designations)
					{
						designationID = designation.getDesignationID();

						if (user.getDesignationID() <= designationID)
						{

							ArrayList<SelectItem> items = new ArrayList<>();

							for (Staff staff : staffs)
								if (staff.getDesignationID() == designationID)
									items.add(new SelectItem(staff.getStaffID(),
											staff.toString()));

							if (!items.isEmpty())
							{
								SelectItemGroup group = new SelectItemGroup(
										designation.getName());
								group.setSelectItems(
										ArrayListConverter.asSelectItem(items));
								staffList.add(group);
							}
						}
					}

				}
				else
				{
					postFellingSurveys = pFacade.getPostFellingSurveys(user);
					accessLevel = 3;
					downtype = "pfco";
					uptype = "pfso";
				}
			}

			if (postFellingSurveys != null)
			{
				sort(postFellingSurveys);

				for (PostFellingSurvey postFellingSurvey : postFellingSurveys)
					postFellingSurvey.setRecorders(
							mFacade.getStaffs(postFellingSurvey, true));
			}
			else
				postFellingSurveys = new ArrayList<>();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public PostFellingSurveyCard getPostFellingSurveyCard()
	{
		return model;
	}

	public void setPostFellingSurveyCard(
			PostFellingSurveyCard postFellingSurveyCard)
	{
		this.model = postFellingSurveyCard;
	}

	public PostFellingSurvey getPostFellingSurvey()
	{
		return postFellingSurvey;
	}

	public void setPostFellingSurvey(PostFellingSurvey postFellingSurvey)
	{
		this.postFellingSurvey = postFellingSurvey;
	}

	public PostFellingSurveyRecord getPostFellingSurveyRecord()
	{
		return postFellingSurveyRecord;
	}

	public void setPostFellingSurveyRecord(
			PostFellingSurveyRecord postFellingSurveyRecord)
	{
		this.postFellingSurveyRecord = postFellingSurveyRecord;
	}

	public PlotType getPlotType()
	{
		return plotType;
	}

	public void setPlotType(PlotType plotType)
	{
		this.plotType = plotType;
	}

	public PostFellingSurveyCard[] getTraditionalPostFellingSurveyCards()
	{
		return traditionalPostFellingSurveyCards;
	}

	public void setTraditionalPostFellingSurveyCards(
			PostFellingSurveyCard[] traditionalPostFellingSurveyCards)
	{
		this.traditionalPostFellingSurveyCards = traditionalPostFellingSurveyCards;
	}

	public ArrayList<PostFellingSurveyCard> getPostFellingSurveyCards()
	{
		return models;
	}

	public void setPostFellingSurveyCards(
			ArrayList<PostFellingSurveyCard> postFellingSurveyCards)
	{
		this.models = postFellingSurveyCards;
	}

	public ArrayList<PostFellingSurvey> getPostFellingSurveys()
	{
		return postFellingSurveys;
	}

	public void setPostFellingSurveys(
			ArrayList<PostFellingSurvey> postFellingSurveys)
	{
		this.postFellingSurveys = postFellingSurveys;
	}

	public ArrayList<ForestType> getForestTypes()
	{
		return forestTypes;
	}

	public void setForestTypes(ArrayList<ForestType> forestTypes)
	{
		this.forestTypes = forestTypes;
	}

	public ArrayList<SoilType> getSoilTypes()
	{
		return soilTypes;
	}

	public void setSoilTypes(ArrayList<SoilType> soilTypes)
	{
		this.soilTypes = soilTypes;
	}

	public ArrayList<AreaStatus> getAreaStatuses()
	{
		return areaStatuses;
	}

	public void setAreaStatuses(ArrayList<AreaStatus> areaStatuses)
	{
		this.areaStatuses = areaStatuses;
	}

	public ArrayList<SoilStatus> getSoilStatuses()
	{
		return soilStatuses;
	}

	public void setSoilStatuses(ArrayList<SoilStatus> soilStatuses)
	{
		this.soilStatuses = soilStatuses;
	}

	public ArrayList<Aspect> getAspects()
	{
		return aspects;
	}

	public void setAspects(ArrayList<Aspect> aspects)
	{
		this.aspects = aspects;
	}

	public ArrayList<SlopeLocation> getSlopeLocations()
	{
		return slopeLocations;
	}

	public void setSlopeLocations(ArrayList<SlopeLocation> slopeLocations)
	{
		this.slopeLocations = slopeLocations;
	}

	public ArrayList<Elevation> getElevations()
	{
		return elevations;
	}

	public void setElevations(ArrayList<Elevation> elevations)
	{
		this.elevations = elevations;
	}

	public ArrayList<Resam> getResams()
	{
		return resams;
	}

	public void setResams(ArrayList<Resam> resams)
	{
		this.resams = resams;
	}

	public ArrayList<Species> getSpeciesList()
	{
		return speciesList;
	}

	public void setSpeciesList(ArrayList<Species> speciesList)
	{
		this.speciesList = speciesList;
	}

	public ArrayList<PlotType> getPlotTypes()
	{
		return plotTypes;
	}

	public ArrayList<PlotType> getPlotTypesReduced()
	{

		ArrayList<PlotType> plotTypesReduced = new ArrayList<PlotType>();
		for (int i = 0; i < plotTypes.size(); i++)
		{
			if (plotTypes.get(i).getPlotTypeID() == 4)
				continue;
			plotTypesReduced.add(plotTypes.get(i));

		}
		return plotTypesReduced;
	}

	public void setPlotTypes(ArrayList<PlotType> plotTypes)
	{
		this.plotTypes = plotTypes;
	}

	public ArrayList<LogQuality> getLogQualities()
	{
		return logQualities;
	}

	public void setLogQualities(ArrayList<LogQuality> logQualities)
	{
		this.logQualities = logQualities;
	}

	public ArrayList<Ginger> getGingers()
	{
		return gingers;
	}

	public void setGingers(ArrayList<Ginger> gingers)
	{
		this.gingers = gingers;
	}

	public ArrayList<Banana> getBananas()
	{
		return bananas;
	}

	public void setBananas(ArrayList<Banana> bananas)
	{
		this.bananas = bananas;
	}

	public ArrayList<TreeStatus> getTreeStatuses()
	{
		return treeStatuses;
	}

	public void setTreeStatuses(ArrayList<TreeStatus> treeStatuses)
	{
		this.treeStatuses = treeStatuses;
	}

	public ArrayList<Silara> getSilaras()
	{
		return silaras;
	}

	public void setSilaras(ArrayList<Silara> silaras)
	{
		this.silaras = silaras;
	}

	public ArrayList<Dominance> getDominances()
	{
		return dominances;
	}

	public void setDominances(ArrayList<Dominance> dominances)
	{
		this.dominances = dominances;
	}

	public ArrayList<Fertility> getFertilities()
	{
		return fertilities;
	}

	public void setFertilities(ArrayList<Fertility> fertilities)
	{
		this.fertilities = fertilities;
	}

	public ArrayList<SelectItem> getLineList()
	{
		return lineList;
	}

	public void setLineList(ArrayList<SelectItem> lineList)
	{
		this.lineList = lineList;
	}

	public String[] getSelectedLines()
	{
		return selectedLines;
	}

	public void setSelectedLines(String[] selectedLines)
	{
		this.selectedLines = selectedLines;
	}

	public String getWorkingDates()
	{
		return workingDates;
	}

	public void setWorkingDates(String workingDates)
	{
		this.workingDates = workingDates;
	}

	public String getRecommendation()
	{
		return recommendation;
	}

	public void setRecommendation(String recommendation)
	{
		this.recommendation = recommendation;
	}

	public String getPercentage()
	{
		return percentage;
	}

	public void setPercentage(String percentage)
	{
		this.percentage = percentage;
	}

	public String getDowntype()
	{
		return downtype;
	}

	public String getUptype()
	{
		return uptype;
	}

	public boolean isAddPostFellingSurveyRecordOperation()
	{
		return addPostFellingSurveyRecordOperation;
	}

	public void setAddPostFellingSurveyRecordOperation(
			boolean addPostFellingSurveyRecordOperation)
	{
		this.addPostFellingSurveyRecordOperation = addPostFellingSurveyRecordOperation;
	}

	public boolean isValidated()
	{
		return validated;
	}

	public void setValidated(boolean validated)
	{
		this.validated = validated;
	}

	public boolean isDownloaded()
	{
		return downloaded;
	}

	public void setDownloaded(boolean downloaded)
	{
		this.downloaded = downloaded;
	}

	public long getSelectedPostFellingSurveyID()
	{
		return selectedPostFellingSurveyID;
	}

	public void setSelectedPostFellingSurveyID(long selectedPostFellingSurveyID)
	{
		this.selectedPostFellingSurveyID = selectedPostFellingSurveyID;
	}

	public int getAccessLevel()
	{
		return accessLevel;
	}

	public String getComponent()
	{
		return ":frmManager:table" + (model == null ? ""
				: ":" + models.indexOf(model) + ":subtable");
	}

	public ArrayList<SelectItem> getStaffList()
	{
		return staffList;
	}

	public void handlePostFellingSurveyIDChange()
	{
		clearFilter();

		try (MaintenanceFacade mFacade = new MaintenanceFacade();
				PostFellingFacade pFacade = new PostFellingFacade();)
		{
			AbstractFacade.group(mFacade, pFacade);

			postFellingSurvey = null;
			validated = false;
			downloaded = false;
			selectedLines = null;
			recommendation = null;
			percentage = null;
			lineList = null;
			map = null;

			for (PostFellingSurvey s : postFellingSurveys)
				if (s.getPostFellingSurveyID() == selectedPostFellingSurveyID)
					postFellingSurvey = s;

			models = pFacade.getPostFellingSurveyCards(postFellingSurvey);

			Staff user = getCurrentUser();
			// State state = mFacade.getState(postFellingSurvey.getStateID());
			// String staffID = user.getStaffID();

			if (user.getDesignationID() < 3 || user.getDesignationID() == 5
					|| user.getDesignationID() == 11)
			{
				uptype = "pfco";
				if (postFellingSurvey.getTenderNo() == null)
				{

					ArrayList<PostFellingSurveyCard> temp = new ArrayList<>();

					for (PostFellingSurveyCard postFellingSurveyCard : models)
						if (postFellingSurveyCard.getInspectorID() == null)
							temp.add(postFellingSurveyCard);

					// models.removeAll(temp);

				}

			}

			SimpleDateFormat sdf = new SimpleDateFormat("d MMMM yyyy");
			int totalDownloaded = 0, count = models.size();

			sort(models);
			postFellingSurvey.setPostFellingSurveyCards(models);

			ArrayList<PostFellingSurveyCard> tempCards = new ArrayList<PostFellingSurveyCard>();
			for (PostFellingSurveyCard postFellingSurveyCard : models)
			{
				ArrayList<PostFellingSurveyRecord> tempRecords = new ArrayList<PostFellingSurveyRecord>();
				ArrayList<PostFellingSurveyRecord> postFellingSurveyRecords = postFellingSurveyCard
						.getPostFellingSurveyRecords();
				for (PostFellingSurveyRecord postFellingSurveyRecord : postFellingSurveyRecords)
				{
					if (postFellingSurveyRecord.getDiameter() > 0
							|| postFellingSurveyRecord.getSaplingQuantity() > 0
							|| postFellingSurveyRecord
									.getSeedlingQuantity() > 0)
					{
						tempRecords.add(postFellingSurveyRecord);
					}
				}
				postFellingSurveyCard.setPostFellingSurveyRecords(tempRecords);

				tempCards.add(postFellingSurveyCard);

			}

			models = tempCards;

			int totalInspectionCard = 0, totalvalidated = 0;
			for (PostFellingSurveyCard postFellingSurveyCard : models)
			{
				if (postFellingSurveyCard.isInspection())
				{
					totalInspectionCard++;
					if (postFellingSurveyCard.getInspectorID() != null)
						totalvalidated++;
				}

			}

			validated = (totalInspectionCard == totalvalidated
					&& postFellingSurvey.getInspectionOpen() == 2) ? true
							: false;

			if (postFellingSurvey.getStartDate() != null
					&& postFellingSurvey.getEndDate() != null)
				workingDates = sdf.format(postFellingSurvey.getStartDate())
						+ " sehingga "
						+ sdf.format(postFellingSurvey.getEndDate());
			else
				workingDates = "Belum ditentukan";

			// validated = totalValidated == count;

			if (totalDownloaded == count)
			{
				District district = mFacade
						.getDistrict(postFellingSurvey.getDistrictID());
				downloaded = user.getStaffID().equals(district.getOfficerID());
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public void handlePlotTypeIDChange()
	{
		for (PlotType pt : plotTypes)
		{
			if (pt.getPlotTypeID() == postFellingSurveyRecord.getPlotTypeID())
			{
				plotType = pt;
				postFellingSurveyRecord.setPlotTypeName(pt.toString());
			}
		}
	}

	@Override
	public void handleOpen()
	{
		if (addOperation)
		{
			model = new PostFellingSurveyCard();
			Date date = new Date();
			Staff user = getCurrentUser();

			model.setPostFellingSurveyCardID(date.getTime());
			model.setSurveyDate(date);
			model.setRecorderID(user.getStaffID());
			model.setRecorderName(user.getName());
			model.setPostFellingSurveyID(
					postFellingSurvey.getPostFellingSurveyID());
			model.setPostFellingSurveyRecords(new ArrayList<>());
		}
		else
			model = (PostFellingSurveyCard) copy(models, model);
	}

	public void handleOpenPostFellingSurveyRecord()
	{
		if (addPostFellingSurveyRecordOperation)
		{
			postFellingSurveyRecord = new PostFellingSurveyRecord();
			postFellingSurveyRecord
					.setPostFellingSurveyRecordID(System.currentTimeMillis());
			postFellingSurveyRecord.setPostFellingSurveyCardID(
					model.getPostFellingSurveyCardID());
		}
		else
		{
			postFellingSurveyRecord = (PostFellingSurveyRecord) copy(
					model.getPostFellingSurveyRecords(),
					postFellingSurveyRecord);
			handlePlotTypeIDChange();
		}
	}

	public void handleOpenTraditional()
	{
		int size = plotTypes.size();
		long current = System.currentTimeMillis();
		traditionalPostFellingSurveyCards = new PostFellingSurveyCard[size];

		for (int i = 0; i < size; i++)
		{
			PostFellingSurveyCard postFellingSurveyCard = new PostFellingSurveyCard();
			PlotType plotType = plotTypes.get(i);
			ArrayList<PostFellingSurveyRecord> postFellingSurveyRecords = new ArrayList<>();
			postFellingSurveyCard
					.setPostFellingSurveyRecords(postFellingSurveyRecords);

			for (int j = 0; j < 10; j++)
			{
				PostFellingSurveyRecord postFellingSurveyRecord = new PostFellingSurveyRecord();
				postFellingSurveyRecord
						.setPostFellingSurveyRecordID(current + i * 10 + j);
				postFellingSurveyRecord.setPlotTypeID(plotType.getPlotTypeID());
				postFellingSurveyRecord.setPlotTypeName(plotType.toString());

				postFellingSurveyRecords.add(postFellingSurveyRecord);
			}

			traditionalPostFellingSurveyCards[i] = postFellingSurveyCard;
		}
	}

	public void postFellingSurveyCardEntry()
	{
		try (PostFellingFacade facade = new PostFellingFacade())
		{
			for (ForestType forestType : forestTypes)
				if (forestType.getForestTypeID() == model.getForestTypeID())
					model.setForestType(forestType.getCode());

			for (SoilStatus soilStatus : soilStatuses)
				if (soilStatus.getSoilStatusID() == model.getSoilStatusID())
					model.setSoilStatus(soilStatus.getCode());

			for (Aspect aspect : aspects)
				if (aspect.getAspectID() == model.getAspectID())
					model.setAspect(aspect.getCode());

			for (SlopeLocation slopeLocation : slopeLocations)
				if (slopeLocation.getSlopeLocationID() == model
						.getSlopeLocationID())
					model.setSlopeLocation(slopeLocation.getCode());

			for (Elevation elevation : elevations)
				if (elevation.getElevationID() == model.getElevationID())
					model.setElevation(elevation.getCode());

			for (Resam resam : resams)
				if (resam.getResamID() == model.getResamID())
					model.setResam(resam.getCode());

			finalizeModelEntry(
					addOperation ? facade.addPostFellingSurveyCard(model, true)
							: facade.updatePostFellingSurveyCard(model),
					addOperation, facade,
					"kad bancian, ID " + model.getPostFellingSurveyCardID(),
					", kerana no. garisan dan petak telah direkodkan sebelumnya",
					models, model);
			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popup').hide()");
	}

	public void postFellingSurveyRecordEntry()
	{
		try (PostFellingFacade facade = new PostFellingFacade())
		{

			for (Species species : speciesList)
			{
				if (species.getSpeciesID() == postFellingSurveyRecord
						.getSpeciesID())
				{
					postFellingSurveyRecord.setSpeciesCode(species.getCode());
					postFellingSurveyRecord.setSpeciesName(species.getName());
					postFellingSurveyRecord
							.setSpeciesTypeID(species.getSpeciesTypeID());
				}
			}

			for (LogQuality logQuality : logQualities)
			{
				if (logQuality.getLogQualityID() == postFellingSurveyRecord
						.getLogQualityID())
				{
					postFellingSurveyRecord.setLogQuality(logQuality.getCode());
					postFellingSurveyRecord
							.setLogQualityName(logQuality.getName());
				}
			}

			for (Fertility fertility : fertilities)
			{
				if (fertility.getFertilityID() == postFellingSurveyRecord
						.getFertilityID())
				{
					postFellingSurveyRecord.setFertility(fertility.getCode());
					postFellingSurveyRecord
							.setFertilityName(fertility.getName());
				}
			}

			for (TreeStatus treeStatus : treeStatuses)
			{
				if (treeStatus.getTreeStatusID() == postFellingSurveyRecord
						.getTreeStatusID())
				{
					postFellingSurveyRecord.setTreeStatus(treeStatus.getCode());
					postFellingSurveyRecord
							.setTreeStatusName(treeStatus.getName());
				}
			}

			for (Silara silara : silaras)
			{
				if (silara.getSilaraID() == postFellingSurveyRecord
						.getSilaraID())
				{
					postFellingSurveyRecord.setSilara(silara.getCode());
					postFellingSurveyRecord.setSilaraName(silara.getName());
				}
			}

			for (Dominance dominance : dominances)
			{
				if (dominance.getDominanceID() == postFellingSurveyRecord
						.getDominanceID())
				{
					postFellingSurveyRecord.setDominance(dominance.getCode());
					postFellingSurveyRecord
							.setDominanceName(dominance.getName());
				}
			}

			if (addPostFellingSurveyRecordOperation)
			{
				finalizeModelEntry(
						facade.addPostFellingSurveyRecord(
								postFellingSurveyRecord, true),
						addPostFellingSurveyRecordOperation, facade,
						"rekod bancian, ID " + postFellingSurveyRecord
								.getPostFellingSurveyRecordID(),
						", kerana rekod bancian telah direkodkan sebelumnya",
						model.getPostFellingSurveyRecords(),
						postFellingSurveyRecord);

				int plotTypeID = postFellingSurveyRecord.getPlotTypeID();
				String plotTypeName = postFellingSurveyRecord.getPlotTypeName();

				handleOpenPostFellingSurveyRecord();
				postFellingSurveyRecord.setPlotTypeID(plotTypeID);
				postFellingSurveyRecord.setPlotTypeName(plotTypeName);
			}
			else
			{
				finalizeModelEntry(
						facade.updatePostFellingSurveyRecord(
								postFellingSurveyRecord),
						addPostFellingSurveyRecordOperation, facade,
						"rekod bancian, ID " + postFellingSurveyRecord
								.getPostFellingSurveyRecordID(),
						", kerana rekod bancian telah direkodkan sebelumnya",
						model.getPostFellingSurveyRecords(),
						postFellingSurveyRecord);
				postFellingSurveyRecord = null;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		if (!addPostFellingSurveyRecordOperation)
			execute("PF('popupPostFellingSurveyRecord').hide()");
	}

	public void postFellingSurveyRecordDelete()
	{
		try (PostFellingFacade facade = new PostFellingFacade())
		{
			if (facade.deletePostFellingSurveyRecord(
					postFellingSurveyRecord) != 0)
			{
				addMessage(FacesMessage.SEVERITY_INFO, null,
						postFellingSurveyRecord + " berjaya dipadamkan.");
				model.getPostFellingSurveyRecords()
						.remove(postFellingSurveyRecord);
				log(facade, "Padam rekod bancian, ID " + postFellingSurveyRecord
						.getPostFellingSurveyRecordID());
			}
			else
				addMessage(FacesMessage.SEVERITY_WARN, null,
						postFellingSurveyRecord + " tidak dapat dipadamkan.");

			postFellingSurveyRecord = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public void postFellingSurveyCardDelete()
	{

		try (PostFellingFacade facade = new PostFellingFacade())
		{
			model = (PostFellingSurveyCard) copy(models, model);

			if (facade.deletePostFellingSurveyCard(model) != 0)
			{
				addMessage(FacesMessage.SEVERITY_INFO, null,
						model + " berjaya dipadamkan.");
				postFellingSurvey.getPostFellingSurveyCards().remove(model);
				models.remove(model);
				log(facade, "Padam rekod bancian, ID "
						+ model.getPostFellingSurveyCardID());
			}
			else
				addMessage(FacesMessage.SEVERITY_WARN, null,
						model + " tidak dapat dipadamkan.");

			model = null;

		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public void postFellingSurveyRecordEntryTraditional()
	{
		try (PostFellingFacade facade = new PostFellingFacade())
		{
			int count = 0;

			for (PostFellingSurveyCard traditionalPostFellingSurveyCard : traditionalPostFellingSurveyCards)
			{
				ArrayList<PostFellingSurveyRecord> postFellingSurveyRecords = traditionalPostFellingSurveyCard
						.getPostFellingSurveyRecords();

				for (PostFellingSurveyRecord postFellingSurveyRecord : postFellingSurveyRecords)
				{

					if (postFellingSurveyRecord.getSpeciesID() != 0)
					{
						postFellingSurveyRecord.setPostFellingSurveyCardID(
								model.getPostFellingSurveyCardID());

						for (Species species : speciesList)
						{
							if (species
									.getSpeciesID() == postFellingSurveyRecord
											.getSpeciesID())
							{
								postFellingSurveyRecord
										.setSpeciesCode(species.getCode());
								postFellingSurveyRecord
										.setSpeciesName(species.getName());
								postFellingSurveyRecord.setSpeciesTypeID(
										species.getSpeciesTypeID());
							}
						}

						for (LogQuality logQuality : logQualities)
						{
							if (logQuality
									.getLogQualityID() == postFellingSurveyRecord
											.getLogQualityID())
							{
								postFellingSurveyRecord
										.setLogQuality(logQuality.getCode());
								postFellingSurveyRecord.setLogQualityName(
										logQuality.getName());
							}
						}

						for (Fertility fertility : fertilities)
						{
							if (fertility
									.getFertilityID() == postFellingSurveyRecord
											.getFertilityID())
							{
								postFellingSurveyRecord
										.setFertility(fertility.getCode());
								postFellingSurveyRecord
										.setFertilityName(fertility.getName());
							}
						}

						for (TreeStatus treeStatus : treeStatuses)
						{
							if (treeStatus
									.getTreeStatusID() == postFellingSurveyRecord
											.getTreeStatusID())
							{
								postFellingSurveyRecord
										.setTreeStatus(treeStatus.getCode());
								postFellingSurveyRecord.setTreeStatusName(
										treeStatus.getName());
							}
						}

						for (Silara silara : silaras)
						{
							if (silara.getSilaraID() == postFellingSurveyRecord
									.getSilaraID())
							{
								postFellingSurveyRecord
										.setSilara(silara.getCode());
								postFellingSurveyRecord
										.setSilaraName(silara.getName());
							}
						}

						for (Dominance dominance : dominances)
						{
							if (dominance
									.getDominanceID() == postFellingSurveyRecord
											.getDominanceID())
							{
								postFellingSurveyRecord
										.setDominance(dominance.getCode());
								postFellingSurveyRecord
										.setDominanceName(dominance.getName());
							}
						}

						if (facade.addPostFellingSurveyRecord(
								postFellingSurveyRecord, true) != 0)
						{
							count++;

							model.getPostFellingSurveyRecords()
									.add(postFellingSurveyRecord);
							log(facade, "Tambah rekod bancian, ID "
									+ postFellingSurveyRecord
											.getPostFellingSurveyRecordID());
						}
					}
				}
			}

			sort(model.getPostFellingSurveyRecords());
			addMessage(FacesMessage.SEVERITY_INFO, null, "Sebanyak " + count
					+ " rekod bancian berjaya ditambahkan.");

			traditionalPostFellingSurveyCards = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popupTraditional').hide()");
	}

	public void validate()
	{
		try (PostFellingFacade facade = new PostFellingFacade())
		{
			int total = 0, count = 0;
			Staff user = getCurrentUser();

			for (PostFellingSurveyCard postFellingSurveyCard : models)
			{
				if (postFellingSurveyCard.getInspectorID() == null
						&& postFellingSurveyCard.getInspectionDate() != null)
				{
					count++;

					postFellingSurveyCard.setInspectorID(user.getStaffID());
					postFellingSurveyCard.setInspectorName(user.getName());

					if (facade.updatePostFellingSurveyCard(
							postFellingSurveyCard) != 0)
						total++;
				}
			}

			validated = total == count;
			selectedLines = null;
			recommendation = null;
			percentage = null;
			lineList = null;
			map = null;

			addMessage(FacesMessage.SEVERITY_INFO, null,
					(total == 0 ? "Tiada" : total)
							+ " kad bancian berjaya disahkan.");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public void prepareDownload()
	{
		lineList = new ArrayList<>();
		map = new LinkedHashMap<>();

		for (PostFellingSurveyCard postFellingSurveyCard : models)
		{
			if (postFellingSurveyCard.getInspectorID() == null)
			{
				String lineNo = postFellingSurveyCard.getLineNo();
				ArrayList<PostFellingSurveyCard> linePostFellingSurveyCard = map
						.get(lineNo);

				if (linePostFellingSurveyCard == null)
				{
					linePostFellingSurveyCard = new ArrayList<>();
					map.put(lineNo, linePostFellingSurveyCard);
				}

				linePostFellingSurveyCard.add(postFellingSurveyCard);
			}
		}

		ArrayList<String> lines = new ArrayList<>(map.keySet());
		int size = lines.size(), min = (int) Math.ceil(size * 0.1);
		recommendation = "Terdapat " + size
				+ " no. garisan yang belum disahkan, anda dinasihatkan untuk memilih sekurang-kurangnya "
				+ min + " no. garisan untuk disemak.";

		selectLine(null);
		Collections.sort(lines);

		for (String line : lines)
			lineList.add(new SelectItem(line,
					"No. garisan " + line + " (mengandungi "
							+ map.get(line).size()
							+ " no. petak yang belum disemak)"));
	}

	public void prepareInspection()
	{
		lineList = new ArrayList<>();
		map = new LinkedHashMap<>();

		for (PostFellingSurveyCard postFellingSurveyCard : models)
		{
			if (!postFellingSurveyCard.isInspection())
			{
				String lineNo = postFellingSurveyCard.getLineNo();
				ArrayList<PostFellingSurveyCard> linePostFellingSurveyCard = map
						.get(lineNo);

				if (linePostFellingSurveyCard == null)
				{
					linePostFellingSurveyCard = new ArrayList<>();
					map.put(lineNo, linePostFellingSurveyCard);
				}

				linePostFellingSurveyCard.add(postFellingSurveyCard);
			}
		}

		ArrayList<String> lines = new ArrayList<>(map.keySet());
		int size = lines.size(), min = (int) Math.ceil(size * 0.1);
		recommendation = "Terdapat " + size
				+ " no. garisan yang belum disahkan, anda dinasihatkan untuk memilih sekurang-kurangnya "
				+ min + " no. garisan untuk disemak.";

		selectLine(null);
		Collections.sort(lines);

		for (String line : lines)
			lineList.add(new SelectItem(line,
					"No. garisan " + line + " (mengandungi "
							+ map.get(line).size()
							+ " no. petak yang belum disemak)"));
	}

	public void prepareReport()
	{
		try (MaintenanceFacade mFacade = new MaintenanceFacade())
		{
			State state = new State();

			state.setStateID(postFellingSurvey.getStateID());
			ArrayList<RegenerationSpecies> regenerationSpeciesList = mFacade
					.getRegenerationSpeciesList(state);
			if (postFellingSurvey.getPostFellingReport() == null)
				postFellingSurvey.setPostFellingReport(new PostFellingReport(
						postFellingSurvey, regenerationSpeciesList));

		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public void selectLine(AjaxBehaviorEvent event)
	{
		percentage = "Jumlah garisan dipilih: "
				+ (selectedLines == null || selectedLines.length == 0 ? 0
						: Math.round(selectedLines.length * 100d / map.size()))
				+ "%";
	}

	public StreamedContent download()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		String name = "LaporanBancian_"
				+ postFellingSurvey.getForestName().replaceAll(" ", "") + "_"
				+ postFellingSurvey.getComptBlockNo() + "_"
				+ postFellingSurvey.getYear() + ".pdf";
		File file = new File(
				external.getRealPath("/") + "files/postFellingSurvey/" + name);
		StreamedContent content = null;

		file.getParentFile().mkdirs();

		try
		{
			PostFellingSurveyReportGenerator.generate(file, postFellingSurvey,
					resams, fertilities);

			content = DefaultStreamedContent.builder()
					.contentType("application/pdf").name(name).stream(() ->
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

	public StreamedContent download(boolean pdf)
	{
		Staff user = getCurrentUser();
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		String name = "KadBancian_"
				+ postFellingSurvey.getForestName().replaceAll(" ", "") + "_"
				+ postFellingSurvey.getComptBlockNo() + "_"
				+ postFellingSurvey.getYear() + "_" + user.getStaffID() + "."
				+ (pdf ? "pdf" : downtype), type = null;
		File file = new File(
				external.getRealPath("/") + "files/post-f/" + name);
		StreamedContent content = null;

		file.getParentFile().mkdirs();

		try
		{
			if (pdf)
			{
				try (MaintenanceFacade mFacade = new MaintenanceFacade();
						PostFellingFacade pFacade = new PostFellingFacade();)
				{
					State state = new State();
					ArrayList<PostFellingSurveyCard> temp = null;

					AbstractFacade.group(mFacade, pFacade);
					state.setStateID(postFellingSurvey.getStateID());

					ArrayList<RegenerationSpecies> regenerationSpeciesList = mFacade
							.getRegenerationSpeciesList(state);
					LinkedHashMap<String, String> map = new LinkedHashMap<>();
					Tender tender = mFacade
							.getTender(postFellingSurvey.getTenderNo());
					Contractor contractor = null;

					for (RegenerationSpecies regenerationSpecies : regenerationSpeciesList)
					{
						String species = regenerationSpecies.getName()
								.toLowerCase(),
								code = regenerationSpecies.getCode(),
								symbol = "O";

						if (code != null)
						{
							if (code.equals("0106") || code.equals("0103")
									|| code.equals("0105")
									|| code.equals("0107"))
								symbol = "S";
							else if (species.contains("keruing")
									|| code.equals("3901")
									|| code.equals("1001")
									|| code.equals("1002"))
								symbol = "K";
							else if (species.contains("nyatoh")
									|| species.contains("kedondong")
									|| species.contains("resak"))
								symbol = "N";
						}

						map.put(code == null ? "" : code, symbol);
					}

					if (tender != null)
						contractor = mFacade
								.getContractor(tender.getContractorID());

					if (lineList != null)
					{
						temp = postFellingSurvey.getPostFellingSurveyCards();
						ArrayList<PostFellingSurveyCard> selected = new ArrayList<>();

						if (selectedLines != null && selectedLines.length != 0)
						{
							for (String selectedLine : selectedLines)
								selected.addAll(this.map.get(selectedLine));
						}
						else
						{
							ArrayList<String> lines = new ArrayList<>(
									this.map.keySet());

							for (String line : lines)
								selected.addAll(this.map.get(line));
						}

						postFellingSurvey.setPostFellingSurveyCards(selected);
					}

					map.put("0201", "M");
					PostFellingSurveyCardGenerator.generate(file,
							postFellingSurvey, contractor, map);

					if (lineList != null)
					{
						postFellingSurvey.setPostFellingSurveyCards(temp);

						Date now = new Date();
						District district = mFacade
								.getDistrict(postFellingSurvey.getDistrictID());
						downloaded = user.getStaffID()
								.equals(district.getOfficerID());

						for (PostFellingSurveyCard postFellingSurveyCard : temp)
						{
							postFellingSurveyCard.setInspectionDate(now);
							pFacade.updatePostFellingSurveyCard(
									postFellingSurveyCard);
						}
					}
				}

				type = "application/pdf";
			}
			else
			{
				type = "application/octet-stream";

				ObjectOutputStream oos = new ObjectOutputStream(
						new GZIPOutputStream(new FileOutputStream(file)));

				oos.writeInt(42);
				oos.writeObject(postFellingSurvey);

				if (downtype.equals("pfco"))
				{
					oos.writeObject(user);
					oos.writeObject(null);
				}

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
					PostFellingFacade facade = new PostFellingFacade();)
			{
				if (ois.readInt() != 42)
					throw new InvalidClassException(
							"Not PostFellingSurveyCard");

				PostFellingSurvey postFellingSurvey = (PostFellingSurvey) ois
						.readObject();

				if (postFellingSurvey.equals(this.postFellingSurvey))
				{
					String filename = file.getFileName().toLowerCase();
					ArrayList<PostFellingSurveyCard> currentPostFellingSurveyCards = this.postFellingSurvey
							.getPostFellingSurveyCards();
					ArrayList<PostFellingSurveyCard> postFellingSurveyCards = postFellingSurvey
							.getPostFellingSurveyCards();

					if (filename.endsWith("pfco"))
					{
						facade.updatePostFellingSurvey(postFellingSurvey);
						save(facade, currentPostFellingSurveyCards,
								postFellingSurveyCards, false);
						postFellingSurveys.set(
								postFellingSurveys
										.indexOf(this.postFellingSurvey),
								postFellingSurvey);

						this.postFellingSurvey = postFellingSurvey;
						validated = false;
						downloaded = false;
					}
				}
				else
					addMessage(FacesMessage.SEVERITY_WARN, null,
							postFellingSurvey
									+ " tidak dapat ditambahkan kerana maklumat sesi bancian yang dimuat naik tidak sama dengan sesi bancian yang dipilih.");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				addMessage(e);
			}
		}
	}

	private void save(PostFellingFacade facade,
			ArrayList<PostFellingSurveyCard> currentPostFellingSurveyCards,
			ArrayList<PostFellingSurveyCard> postFellingSurveyCards,
			boolean strict) throws SQLException
	{
		int totalPostFellingSurveyCard = 0, totalRecord = 0;

		for (PostFellingSurveyCard postFellingSurveyCard : postFellingSurveyCards)
		{
			if (strict)
				if (postFellingSurveyCard.getInspectorID() == null)
					continue;

			ArrayList<PostFellingSurveyRecord> currentPostFellingSurveyRecords = null;
			ArrayList<PostFellingSurveyRecord> postFellingSurveyRecords = postFellingSurveyCard
					.getPostFellingSurveyRecords();
			int type = 1;

			if (facade.addPostFellingSurveyCard(postFellingSurveyCard,
					false) != 0)
			{
				totalPostFellingSurveyCard++;

				if (!currentPostFellingSurveyCards
						.contains(postFellingSurveyCard))
				{
					PostFellingSurveyCard temp = null;

					for (PostFellingSurveyCard c : currentPostFellingSurveyCards)
						if (postFellingSurveyCard.toString()
								.equals(c.toString()))
							temp = c;

					if (temp != null)
					{
						type = -1;
						currentPostFellingSurveyRecords = temp
								.getPostFellingSurveyRecords();

						log(facade, "Kemaskini kad bancian, ID "
								+ temp.getPostFellingSurveyCardID());
						postFellingSurveyCard.setPostFellingSurveyCardID(
								temp.getPostFellingSurveyCardID());
					}
					else
					{
						currentPostFellingSurveyCards
								.add(postFellingSurveyCard);
						log(facade,
								"Tambah kad bancian, ID "
										+ postFellingSurveyCard
												.getPostFellingSurveyCardID());
					}
				}
				else
				{
					type = 0;
					currentPostFellingSurveyRecords = currentPostFellingSurveyCards
							.get(currentPostFellingSurveyCards
									.indexOf(postFellingSurveyCard))
							.getPostFellingSurveyRecords();

					log(facade,
							"Kemaskini kad bancian, ID " + postFellingSurveyCard
									.getPostFellingSurveyCardID());
				}

				sort(currentPostFellingSurveyCards);
			}

			if (type != 1)
				currentPostFellingSurveyCards.set(currentPostFellingSurveyCards
						.indexOf(postFellingSurveyCard), postFellingSurveyCard);

			for (PostFellingSurveyRecord postFellingSurveyRecord : postFellingSurveyRecords)
			{
				if (type == -1)
					postFellingSurveyRecord.setPostFellingSurveyCardID(
							postFellingSurveyCard.getPostFellingSurveyCardID());

				if (facade.addPostFellingSurveyRecord(postFellingSurveyRecord,
						false) != 0)
				{
					totalRecord++;
					log(facade,
							"Tambah rekod bancian, ID "
									+ postFellingSurveyRecord
											.getPostFellingSurveyRecordID());
				}
			}

			if (currentPostFellingSurveyRecords != null)
			{
				postFellingSurveyRecords
						.addAll(currentPostFellingSurveyRecords);
				sort(postFellingSurveyRecords);
			}
		}

		if (totalPostFellingSurveyCard != 0 || totalRecord != 0)
			addMessage(FacesMessage.SEVERITY_INFO, null,
					totalPostFellingSurveyCard + " kad bancian dan "
							+ totalRecord
							+ " rekod bancian berjaya ditambahkan.");
		else
			addMessage(FacesMessage.SEVERITY_INFO, null,
					"Tiada kad bancian dan rekod bancian berjaya ditambahkan.");
	}

	public void handleCreateInspectionCards()
	{

		String message = null;
		boolean success = true;
		ArrayList<PostFellingSurveyCard> selected = new ArrayList<>();
		try
		{
			PostFellingFacade pfacade = new PostFellingFacade();
			if (postFellingSurvey != null)
			{

				if (selectedLines != null && selectedLines.length != 0)
				{
					for (String selectedLine : selectedLines)
					{
						selected.addAll(this.map.get(selectedLine));
					}
				}
				else
				{
					ArrayList<String> lines = new ArrayList<>(
							this.map.keySet());
					for (String line : lines)
						selected.addAll(this.map.get(line));

					success = false;
				}

				if (postFellingSurvey.getInspectionLeaderID() != null
						|| postFellingSurvey.getInspectionStartDate() != null
						|| postFellingSurvey.getInspectionEndDate() != null)
				{
					postFellingSurvey.setInspectionOpen(1);
					if (pfacade.updatePostFellingSurvey(postFellingSurvey) == 0)
					{
						success = false;

					}

					ArrayList<PostFellingSurveyCard> postFellingSurveyCards = postFellingSurvey
							.getPostFellingSurveyCards();
					for (PostFellingSurveyCard postFellingSurveyCard : postFellingSurveyCards)
					{
						postFellingSurveyCard.setInspectionForestTypeID(
								postFellingSurveyCard.getForestTypeID());
						postFellingSurveyCard.setInspectionSoilStatusID(
								postFellingSurveyCard.getSoilStatusID());
						postFellingSurveyCard.setInspectionAspectID(
								postFellingSurveyCard.getAspectID());
						postFellingSurveyCard.setInspectionSlope(
								postFellingSurveyCard.getSlope());
						postFellingSurveyCard.setInspectionSlopeLocationID(
								postFellingSurveyCard.getSlopeLocationID());
						postFellingSurveyCard.setInspectionElevationID(
								postFellingSurveyCard.getElevationID());
						postFellingSurveyCard.setInspectionBertam(
								postFellingSurveyCard.getBertam());
						postFellingSurveyCard.setInspectionBamboo(
								postFellingSurveyCard.getBamboo());
						postFellingSurveyCard.setInspectionPalm(
								postFellingSurveyCard.getPalm());
						postFellingSurveyCard.setInspectionResamID(
								postFellingSurveyCard.getResamID());
						postFellingSurveyCard.setInspectionGingerID(
								postFellingSurveyCard.getGingerID());
						postFellingSurveyCard.setInspectionBananaID(
								postFellingSurveyCard.getBananaID());
						postFellingSurveyCard.setInspection(false);
						pfacade.updatePostFellingSurveyCardInspection(
								postFellingSurveyCard);
						ArrayList<PostFellingSurveyRecord> postFellingSurveyRecords = postFellingSurveyCard
								.getPostFellingSurveyRecords();
						for (PostFellingSurveyRecord postFellingSurveyRecord : postFellingSurveyRecords)
						{
							postFellingSurveyRecord.setInspectionDiameter(
									postFellingSurveyRecord.getDiameter());
							postFellingSurveyRecord.setInspectionLogQuantity(
									postFellingSurveyRecord.getLogQuantity());
							postFellingSurveyRecord.setInspectionLogQualityID(
									postFellingSurveyRecord.getLogQualityID());
							postFellingSurveyRecord.setInspectionTreeStatusID(
									postFellingSurveyRecord.getTreeStatusID());
							postFellingSurveyRecord.setInspectionSilaraID(
									postFellingSurveyRecord.getSilaraID());
							postFellingSurveyRecord.setInspectionDominanceID(
									postFellingSurveyRecord.getDominanceID());
							postFellingSurveyRecord.setInspectionVine(
									postFellingSurveyRecord.getVine());
							postFellingSurveyRecord.setInspectionFertilityID(
									postFellingSurveyRecord.getFertilityID());
							postFellingSurveyRecord
									.setInspectionSaplingQuantity(
											postFellingSurveyRecord
													.getSaplingQuantity());
							postFellingSurveyRecord
									.setInspectionSeedlingQuantity(
											postFellingSurveyRecord
													.getSeedlingQuantity());
							postFellingSurveyRecord.setInspectionSpeciesID(
									postFellingSurveyRecord.getSpeciesID());
							postFellingSurveyRecord.setInspection(false);
							if (pfacade.updatePostFellingInspectionRecord(
									postFellingSurveyRecord) != 0)
							{
								log(pfacade,
										"Kemaskini rekod bancian untuk sesi semakan, ID "
												+ postFellingSurveyRecord
														.getPostFellingSurveyRecordID());
							}
						}
					}

					for (PostFellingSurveyCard selectedLine : selected)
					{
						PostFellingInspectionLine postFellingInspectionLine = new PostFellingInspectionLine();
						postFellingInspectionLine
								.setLineNo(selectedLine.getLineNo());
						postFellingInspectionLine
								.setPostFellingInspectionLineID(
										System.currentTimeMillis());
						postFellingInspectionLine.setPostFellingSurveyID(
								selectedLine.getPostFellingSurveyID());
						pfacade.addPostFellingInspectionLine(
								postFellingInspectionLine, false);

						selectedLine.setInspection(true);
						selectedLine.setInspectionForestTypeID(
								selectedLine.getForestTypeID());
						selectedLine.setInspectionSoilStatusID(
								selectedLine.getSoilStatusID());
						selectedLine.setInspectionAspectID(
								selectedLine.getAspectID());
						selectedLine
								.setInspectionSlope(selectedLine.getSlope());
						selectedLine.setInspectionSlopeLocationID(
								selectedLine.getSlopeLocationID());
						selectedLine.setInspectionElevationID(
								selectedLine.getElevationID());
						selectedLine
								.setInspectionBertam(selectedLine.getBertam());
						selectedLine
								.setInspectionBamboo(selectedLine.getBamboo());
						selectedLine.setInspectionPalm(selectedLine.getPalm());
						selectedLine.setInspectionResamID(
								selectedLine.getResamID());
						selectedLine.setInspectionGingerID(
								selectedLine.getGingerID());
						selectedLine.setInspectionBananaID(
								selectedLine.getBananaID());
						pfacade.updatePostFellingSurveyCardInspection(
								selectedLine);
						ArrayList<PostFellingSurveyRecord> postFellingSurveyRecords = selectedLine
								.getPostFellingSurveyRecords();
						for (PostFellingSurveyRecord postFellingSurveyRecord : postFellingSurveyRecords)
						{
							postFellingSurveyRecord.setInspectionDiameter(
									postFellingSurveyRecord.getDiameter());
							postFellingSurveyRecord.setInspectionLogQuantity(
									postFellingSurveyRecord.getLogQuantity());
							postFellingSurveyRecord.setInspectionLogQualityID(
									postFellingSurveyRecord.getLogQualityID());
							postFellingSurveyRecord.setInspectionTreeStatusID(
									postFellingSurveyRecord.getTreeStatusID());
							postFellingSurveyRecord.setInspectionSilaraID(
									postFellingSurveyRecord.getSilaraID());
							postFellingSurveyRecord.setInspectionDominanceID(
									postFellingSurveyRecord.getDominanceID());
							postFellingSurveyRecord.setInspectionVine(
									postFellingSurveyRecord.getVine());
							postFellingSurveyRecord.setInspectionFertilityID(
									postFellingSurveyRecord.getFertilityID());
							postFellingSurveyRecord
									.setInspectionSaplingQuantity(
											postFellingSurveyRecord
													.getSaplingQuantity());
							postFellingSurveyRecord
									.setInspectionSeedlingQuantity(
											postFellingSurveyRecord
													.getSeedlingQuantity());
							postFellingSurveyRecord.setInspection(true);
							if (pfacade.updatePostFellingInspectionRecord(
									postFellingSurveyRecord) != 0)
							{
								log(pfacade,
										"Kemaskini rekod bancian untuk sesi semakan, ID "
												+ postFellingSurveyRecord
														.getPostFellingSurveyRecordID());
							}
						}
					}
				}
				else
				{
					success = false;
				}

				// model = (PostFellingSurveyCard) copy(models, model);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		if (!success)
		{
			addMessage(FacesMessage.SEVERITY_INFO, null,
					"Tiada kad bancian untuk semakan berjaya ditambahkan.");

		}
		else
		{

			message = "Sesi Semakan Inventori Hutan Selepas Tebangan (Post-Felling) telah mulakan oleh "
					+ postFellingSurvey.getCreatorName()
					+ " untuk:<br/><br/><table border='0'><tr><td>- Hutan simpan</td><td>:</td><td>"
					+ postFellingSurvey.getForestName()
					+ "</td></tr><tr><td>- No. kompartmen/sub kompartmen</td><td>:</td><td>"
					+ postFellingSurvey.getComptBlockNo()
					+ "</td></tr><tr><td>- Keluasan</td><td>:</td><td>"
					+ postFellingSurvey.getArea()
					+ " hektar</td></tr></table><br/>Sila log masuk ke FIS9 untuk tindakan anda seterusnya.";

			addMessage(FacesMessage.SEVERITY_INFO, null, "Sebanyak "
					+ selected.size()
					+ " kad bancian untuk semakan berjaya dikemaskini.");
		}

		try
		{

			if (message != null)
				new EmailSender().send(true,
						"Inventori Hutan Selepas Tebangan - "
								+ postFellingSurvey.getForestName() + " "
								+ postFellingSurvey.getComptBlockNo(),
						message, postFellingSurvey.getInspectionLeaderID());
		}
		catch (Exception e)
		{
			e.printStackTrace();

		}

		execute("PF('popupInspection').hide()");

	}

}