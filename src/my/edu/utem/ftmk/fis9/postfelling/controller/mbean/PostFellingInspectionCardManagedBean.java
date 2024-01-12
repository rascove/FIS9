package my.edu.utem.ftmk.fis9.postfelling.controller.mbean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
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
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.AreaStatus;
import my.edu.utem.ftmk.fis9.maintenance.model.Aspect;
import my.edu.utem.ftmk.fis9.maintenance.model.Banana;
import my.edu.utem.ftmk.fis9.maintenance.model.Contractor;
import my.edu.utem.ftmk.fis9.maintenance.model.Designation;
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
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingInspectionReport;
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingReport;
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingSurvey;
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingSurveyCard;
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingSurveyRecord;
import my.edu.utem.ftmk.fis9.postfelling.util.PostFellingInspectionReportGenerator;
import my.edu.utem.ftmk.fis9.postfelling.util.PostFellingInspectionSurveyCardGenerator;
import my.edu.utem.ftmk.fis9.postfelling.util.PostFellingSurveyReportGenerator;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "postFellingInspectionCardMBean")
public class PostFellingInspectionCardManagedBean
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
	private String[] selectedStaffs;
	private ArrayList<Staff> inspectionStaffs;
	private boolean inspectionFinish;

	public PostFellingInspectionCardManagedBean()
	{

		try (MaintenanceFacade mFacade = new MaintenanceFacade();
				PostFellingFacade pFacade = new PostFellingFacade();)
		{
			AbstractFacade.group(mFacade, pFacade);
			// String designationSivil = "20";
			Staff user = getCurrentUser();
			ArrayList<Designation> designations = mFacade.getDesignations();
			int stateID = user.getStateID(),
					designationID = user.getDesignationID();

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
					postFellingSurveys = pFacade.getPostFellingInspections();
					uptype = "pfco";
				}
				else
				{
					postFellingSurveys = pFacade
							.getPostFellingInspections(user);
					accessLevel = 6;
					downtype = "pfco";
				}
			}
			else
			{
				State state = mFacade.getState(stateID);

				staffs = mFacade.getStaffs(state);// , "DesignationID",
													// designationSivil);
				staffList = new ArrayList<>();

				/*
				 * ArrayList<SelectItem> items = new ArrayList<>(); for (Staff
				 * staff : staffs) { if
				 * (user.getStaffID().equals(staff.getStaffID())) continue;
				 * items.add(new SelectItem(staff.getStaffID(),
				 * staff.toString())); } if (!items.isEmpty()) { SelectItemGroup
				 * group = new
				 * SelectItemGroup("Pengawas Hutan (Silvikultur dan Perlindungan)"
				 * );
				 * group.setSelectItems(ArrayListConverter.asSelectItem(items));
				 * staffList.add(group); }
				 */

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

				if (user.getDesignationID() < 3 || user.getDesignationID() == 5
						|| user.getDesignationID() == 11)
				{
					postFellingSurveys = pFacade
							.getPostFellingInspections(state);

					accessLevel = 1;
					uptype = "pfco";
					downtype = "pfco";
				}
				else
				{
					postFellingSurveys = pFacade
							.getPostFellingInspections(user);
					accessLevel = 3;
					downtype = "pfco";
					uptype = "pfco";
				}
			}

			if (postFellingSurveys != null)
			{
				sort(postFellingSurveys);

				for (PostFellingSurvey postFellingSurvey : postFellingSurveys)
					postFellingSurvey.setInspectionRecorders(
							mFacade.getStaffs(postFellingSurvey, false));

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

	public ArrayList<Staff> getInspectionStaffs()
	{
		return inspectionStaffs;
	}

	public void setInspectionStaffs(ArrayList<Staff> inspectionStaffs)
	{
		this.inspectionStaffs = inspectionStaffs;
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
			inspectionFinish = false;

			for (PostFellingSurvey s : postFellingSurveys)
				if (s.getPostFellingSurveyID() == selectedPostFellingSurveyID)
					postFellingSurvey = s;

			ArrayList<PostFellingInspectionLine> postFellingInspectionLines = pFacade
					.getPostFellingInspectionLines(postFellingSurvey);
			postFellingSurvey
					.setPostFellingInspectionLines(postFellingInspectionLines);
			// models =
			// pFacade.getPostFellingInspectionCards(postFellingSurvey);

			Staff user = getCurrentUser();

			if (user.getDesignationID() < 3 || user.getDesignationID() == 5
					|| user.getDesignationID() == 11)
			{
				uptype = "pfco";
				downtype = "pfco";
				// models =
				// pFacade.getPostFellingSurveyCards(postFellingSurvey);
			}
			else
			{
				uptype = "pfco";
				downtype = "pfco";
				// models =
				// pFacade.getPostFellingInspectionCards(postFellingSurvey);

			}

			ArrayList<PostFellingSurveyCard> filteredCards = new ArrayList<PostFellingSurveyCard>();
			for (PostFellingSurveyCard postFellingSurveyCard : pFacade
					.getPostFellingInspectionCards(postFellingSurvey))
			{

				ArrayList<PostFellingSurveyRecord> filteredRecords = new ArrayList<PostFellingSurveyRecord>();
				for (PostFellingSurveyRecord record : postFellingSurveyCard
						.getPostFellingSurveyRecords())
				{
					if ((record.getPlotTypeID() < 6
							&& record.getInspectionDiameter() > 0))
						filteredRecords.add(record);
					else if (record.getPlotTypeID() == 6 && (record
							.getInspectionSaplingQuantity() > 0
							|| record.getInspectionSeedlingQuantity() > 0))
						filteredRecords.add(record);
				}
				postFellingSurveyCard
						.setPostFellingSurveyRecords(filteredRecords);
				filteredCards.add(postFellingSurveyCard);

			}

			models = filteredCards;

			sort(models);
			postFellingSurvey.setPostFellingSurveyCards(models);

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

	public void handleOpenInspectionDetails()
	{

	}

	public void handleResetInspectionInfo()
	{
		model = (PostFellingSurveyCard) copy(models, model);
		model.setInspectionForestTypeID(0);
		model.setInspectionSoilStatusID(0);
		model.setInspectionAspectID(0);
		model.setInspectionSlope(0);
		model.setInspectionSlopeLocationID(0);
		model.setInspectionElevationID(0);
		model.setInspectionBertam(0);
		model.setInspectionBamboo(0);
		model.setInspectionPalm(0);
		model.setInspectionResamID(0);
		model.setInspectionGingerID(0);
		model.setInspectionBananaID(0);
		handlePostFellingSurveyIDChange();
	}

	public void handleResetInspectionRecord()
	{

		model = (PostFellingSurveyCard) copy(models, model);
		try (PostFellingFacade facade = new PostFellingFacade())
		{
			ArrayList<PostFellingSurveyRecord> postFellingSurveyRecords = model
					.getPostFellingSurveyRecords();
			for (PostFellingSurveyRecord postFellingSurveyRecord : postFellingSurveyRecords)
			{
				facade.resetPostFellingSurveyRecordForInspection(
						postFellingSurveyRecord);
			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
		model.setPostFellingSurveyRecords(null);

		handlePostFellingSurveyIDChange();
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

	public void handleOpenConfig()
	{
		//// model = (PostFellingSurvey) copy(models, model);
		ArrayList<Staff> recorders = postFellingSurvey.getInspectionRecorders();

		if (recorders != null)
		{
			int size = recorders.size();
			selectedStaffs = new String[size];

			for (int i = 0; i < size; i++)
			{
				selectedStaffs[i] = recorders.get(i).getStaffID();
			}

		}
	}

	public void handleUpdateInspectionDetails()
	{
		try (MaintenanceFacade mFacade = new MaintenanceFacade();
				PostFellingFacade pFacade = new PostFellingFacade())
		{
			AbstractFacade.group(mFacade, pFacade);

			if (inspectionFinish)
				postFellingSurvey.setInspectionOpen(2);

			pFacade.updatePostFellingSurvey(postFellingSurvey);

			ArrayList<PostFellingInspectionLine> postFellingInspectionLines = postFellingSurvey
					.getPostFellingInspectionLines();
			for (PostFellingInspectionLine postFellingInspectionLine : postFellingInspectionLines)
			{
				pFacade.updatePostFellingInspectionLine(
						postFellingInspectionLine);
			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popupInspectionDetails').hide()");
	}

	public void handleUpdateCommentPPPN()
	{
		try (PostFellingFacade pFacade = new PostFellingFacade())
		{
			pFacade.updatePostFellingSurvey(postFellingSurvey);
			addMessage(FacesMessage.SEVERITY_INFO, null,
					"Ulasan berjaya dikemaskini.");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popupInspectionReport').show()");

	}

	public void handleValidateInspectionCard()
	{

		Staff user = getCurrentUser();
		Date date = new Date();

		try (PostFellingFacade pfacade = new PostFellingFacade())
		{
			model.setInspectorID(user.getStaffID());
			if (model.getInspectionDate() == null)
				model.setInspectionDate(date);

			finalizeModelEntry(
					addOperation ? pfacade.addPostFellingSurveyCard(model, true)
							: pfacade.updatePostFellingSurveyCardInspection(
									model),
					addOperation, pfacade,
					"kad bancian, ID " + model.getPostFellingSurveyCardID(),
					", kerana no. garisan dan petak telah direkodkan sebelumnya",
					models, model);
			model = null;
			handlePostFellingSurveyIDChange();

		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public void postFellingSurveyUpdate()
	{
		try (MaintenanceFacade mFacade = new MaintenanceFacade();
				PostFellingFacade pFacade = new PostFellingFacade())
		{
			AbstractFacade.group(mFacade, pFacade);

			if (selectedStaffs != null)
			{
				ArrayList<Staff> recorders = new ArrayList<>();

				for (String selectedStaff : selectedStaffs)
					for (Staff staff : staffs)
						if (staff.getStaffID().equals(selectedStaff))
							recorders.add(staff);

				postFellingSurvey.setInspectionRecorders(recorders);
			}

			pFacade.updatePostFellingSurvey(postFellingSurvey);

		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popupStaff').hide()");
	}

	public void postFellingInspectionCardEntry()
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
							: facade.updatePostFellingSurveyCardInspection(
									model),
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

			postFellingSurveyRecord.setInspection(true);
			for (Species species : speciesList)
			{
				if (species.getSpeciesID() == postFellingSurveyRecord
						.getInspectionSpeciesID())
				{
					postFellingSurveyRecord
							.setInspectionSpeciesCode(species.getCode());
					postFellingSurveyRecord
							.setInspectionSpeciesName(species.getName());
					// postFellingSurveyRecord.setSpeciesTypeID(species.getSpeciesTypeID());
				}
			}

			for (LogQuality logQuality : logQualities)
			{
				if (logQuality.getLogQualityID() == postFellingSurveyRecord
						.getInspectionLogQualityID())
				{
					postFellingSurveyRecord
							.setInspectionLogQuality(logQuality.getCode());
					postFellingSurveyRecord
							.setInspectionLogQualityName(logQuality.getName());
				}
			}

			for (Fertility fertility : fertilities)
			{
				if (fertility.getFertilityID() == postFellingSurveyRecord
						.getInspectionFertilityID())
				{
					postFellingSurveyRecord
							.setInspectionFertility(fertility.getCode());
					postFellingSurveyRecord
							.setInspectionFertilityName(fertility.getName());
				}
			}

			for (TreeStatus treeStatus : treeStatuses)
			{
				if (treeStatus.getTreeStatusID() == postFellingSurveyRecord
						.getInspectionTreeStatusID())
				{
					postFellingSurveyRecord
							.setInspectionTreeStatus(treeStatus.getCode());
					postFellingSurveyRecord
							.setInspectionTreeStatusName(treeStatus.getName());
				}
			}

			for (Silara silara : silaras)
			{
				if (silara.getSilaraID() == postFellingSurveyRecord
						.getInspectionSilaraID())
				{
					postFellingSurveyRecord
							.setInspectionSilara(silara.getCode());
					postFellingSurveyRecord
							.setInspectionSilaraName(silara.getName());
				}
			}

			for (Dominance dominance : dominances)
			{
				if (dominance.getDominanceID() == postFellingSurveyRecord
						.getInspectionDominanceID())
				{
					postFellingSurveyRecord
							.setInspectionDominance(dominance.getCode());
					postFellingSurveyRecord
							.setInspectionDominanceName(dominance.getName());
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

	public void postFellingSurveyRecordResetForInspection()
	{

		try (PostFellingFacade facade = new PostFellingFacade())
		{
			facade.resetPostFellingSurveyRecordForInspection(
					postFellingSurveyRecord);
			model.getPostFellingSurveyRecords().remove(postFellingSurveyRecord);
			handlePlotTypeIDChange();
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

					if (postFellingSurveyRecord.getInspectionSpeciesID() != 0)
					{
						postFellingSurveyRecord.setPostFellingSurveyCardID(
								model.getPostFellingSurveyCardID());

						for (Species species : speciesList)
						{
							if (species
									.getSpeciesID() == postFellingSurveyRecord
											.getInspectionSpeciesID())
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
											.getInspectionLogQualityID())
							{
								postFellingSurveyRecord.setInspectionLogQuality(
										logQuality.getCode());
								postFellingSurveyRecord
										.setInspectionLogQualityName(
												logQuality.getName());
							}
						}

						for (Fertility fertility : fertilities)
						{
							if (fertility
									.getFertilityID() == postFellingSurveyRecord
											.getInspectionFertilityID())
							{
								postFellingSurveyRecord.setInspectionFertility(
										fertility.getCode());
								postFellingSurveyRecord
										.setInspectionFertilityName(
												fertility.getName());
							}
						}

						for (TreeStatus treeStatus : treeStatuses)
						{
							if (treeStatus
									.getTreeStatusID() == postFellingSurveyRecord
											.getInspectionTreeStatusID())
							{
								postFellingSurveyRecord.setInspectionTreeStatus(
										treeStatus.getCode());
								postFellingSurveyRecord
										.setInspectionTreeStatusName(
												treeStatus.getName());
							}
						}

						for (Silara silara : silaras)
						{
							if (silara.getSilaraID() == postFellingSurveyRecord
									.getInspectionSilaraID())
							{
								postFellingSurveyRecord
										.setInspectionSilara(silara.getCode());
								postFellingSurveyRecord.setInspectionSilaraName(
										silara.getName());
							}
						}

						for (Dominance dominance : dominances)
						{
							if (dominance
									.getDominanceID() == postFellingSurveyRecord
											.getInspectionDominanceID())
							{
								postFellingSurveyRecord.setInspectionDominance(
										dominance.getCode());
								postFellingSurveyRecord
										.setInspectionDominanceName(
												dominance.getName());
							}
						}

						postFellingSurveyRecord.setInspection(true);

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

	public void prepareReport()
	{
		try (PostFellingFacade facade = new PostFellingFacade();
				MaintenanceFacade mFacade = new MaintenanceFacade())
		{
			State state = new State();
			state.setStateID(postFellingSurvey.getStateID());
			PostFellingSurvey reportPostFellingSurvey = postFellingSurvey;
			reportPostFellingSurvey.setPostFellingSurveyCards(
					facade.getPostFellingSurveyCards(postFellingSurvey));

			ArrayList<RegenerationSpecies> regenerationSpeciesList = mFacade
					.getRegenerationSpeciesList(state);
			if (postFellingSurvey.getPostFellingReport() == null)
				postFellingSurvey.setPostFellingReport(new PostFellingReport(
						reportPostFellingSurvey, regenerationSpeciesList));

			if (postFellingSurvey.getCommentPPPN() == null
					|| postFellingSurvey.getCommentPPPN().length() < 1)
			{
				addMessage(FacesMessage.SEVERITY_WARN, null,
						"Sila isi Ulasan PPPN untuk muat turun laporan bancian");

			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public void prepareInspectionReport()
	{
		try (MaintenanceFacade mFacade = new MaintenanceFacade();
				PostFellingFacade pFacade = new PostFellingFacade();)
		{

			State state = new State();
			PostFellingSurvey postFellingSurveyForInspectionReport = postFellingSurvey;
			postFellingSurveyForInspectionReport.setPostFellingSurveyCards(
					pFacade.getPostFellingSurveyCards(postFellingSurvey));
			state.setStateID(postFellingSurvey.getStateID());
			postFellingSurvey.setPostFellingInspectionReport(
					new PostFellingInspectionReport(
							postFellingSurveyForInspectionReport));

			Staff teamLeader = null;
			ArrayList<Staff> staffs = mFacade.getStaffs();

			for (Staff staff : staffs)
			{
				if (staff.getStaffID()
						.equals(postFellingSurvey.getInspectionLeaderID()))
				{
					teamLeader = staff;
					break;
				}
			}
			teamLeader.setStaffID(postFellingSurvey.getInspectionLeaderID());
			teamLeader.setName(postFellingSurvey.getInspectionLeaderName());
			inspectionStaffs = mFacade.getStaffs(postFellingSurvey, false);
			if (teamLeader != null)
				inspectionStaffs.add(0, teamLeader);
			if (postFellingSurvey.getCommentPPPN() == null
					|| postFellingSurvey.getCommentPPPN().length() < 1)
			{
				addMessage(FacesMessage.SEVERITY_WARN, null,
						"Sila isi Ulasan PPPN untuk sebelum muat turun laporan semakan");

			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public void selectLine(AjaxBehaviorEvent event)
	{
		percentage = "Jumlah kad dipilih: "
				+ (selectedLines == null || selectedLines.length == 0 ? 100
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

	public StreamedContent downloadInspectionReport()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		String name = "LaporanSemakanBancian_"
				+ postFellingSurvey.getForestName().replaceAll(" ", "") + "_"
				+ postFellingSurvey.getComptBlockNo() + "_"
				+ postFellingSurvey.getYear() + ".pdf";
		File file = new File(
				external.getRealPath("/") + "files/postFellingSurvey/" + name);
		StreamedContent content = null;

		file.getParentFile().mkdirs();

		try
		{
			postFellingSurvey.setPostFellingInspectionReport(
					new PostFellingInspectionReport(postFellingSurvey));
			PostFellingInspectionReportGenerator.generate(file,
					postFellingSurvey, inspectionStaffs);

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

		try (PostFellingFacade pFacade = new PostFellingFacade();)
		{
			postFellingSurvey.setPostFellingSurveyCards(
					pFacade.getPostFellingSurveyCards(postFellingSurvey));
			postFellingSurvey.setPostFellingInspectionLines(
					pFacade.getPostFellingInspectionLines(postFellingSurvey));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addMessage(e);
		}
		try
		{
			if (pdf)
			{
				try (MaintenanceFacade mFacade = new MaintenanceFacade();
						PostFellingFacade pFacade = new PostFellingFacade();)
				{
					State state = new State();

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

					map.put("0201", "M");
					PostFellingInspectionSurveyCardGenerator.generate(file,
							postFellingSurvey, contractor, map, false);
				}

				type = "application/pdf";
			}
			else
			{
				type = "application/octet-stream";
				ObjectOutputStream oos = new ObjectOutputStream(
						new GZIPOutputStream(new FileOutputStream(file)));

				oos.writeInt(43);
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

	public StreamedContent downloadSurveyCard(boolean all)
	{
		Staff user = getCurrentUser();
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		String name = "", type = null;
		if (all)
			name = "KadBancian_"
					+ postFellingSurvey.getForestName().replaceAll(" ", "")
					+ "_" + postFellingSurvey.getComptBlockNo() + "_"
					+ postFellingSurvey.getYear() + "_" + user.getStaffID()
					+ "(semua).pdf";
		else
			name = "KadBancian_"
					+ postFellingSurvey.getForestName().replaceAll(" ", "")
					+ "_" + postFellingSurvey.getComptBlockNo() + "_"
					+ postFellingSurvey.getYear() + "_" + user.getStaffID()
					+ "(semakan).pdf";

		File file = new File(
				external.getRealPath("/") + "files/post-f/" + name);
		StreamedContent content = null;

		file.getParentFile().mkdirs();

		try (PostFellingFacade pFacade = new PostFellingFacade();)
		{
			postFellingSurvey.setPostFellingSurveyCards(
					pFacade.getPostFellingSurveyCards(postFellingSurvey));
			postFellingSurvey.setPostFellingInspectionLines(
					pFacade.getPostFellingInspectionLines(postFellingSurvey));

		}
		catch (Exception e)
		{
			e.printStackTrace();
			addMessage(e);
		}
		try
		{

			try (MaintenanceFacade mFacade = new MaintenanceFacade();
					PostFellingFacade pFacade = new PostFellingFacade();)
			{
				State state = new State();

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
							code = regenerationSpecies.getCode(), symbol = "O";

					if (code != null)
					{
						if (code.equals("0106") || code.equals("0103")
								|| code.equals("0105") || code.equals("0107"))
							symbol = "S";
						else if (species.contains("keruing")
								|| code.equals("3901") || code.equals("1001")
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

				map.put("0201", "M");
				PostFellingInspectionSurveyCardGenerator.generate(file,
						postFellingSurvey, contractor, map, all);
			}

			type = "application/pdf";

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
				if (ois.readInt() != 43)
					throw new InvalidClassException(
							"Not PostFellingInspectionCard");

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
						for (PostFellingInspectionLine line : postFellingSurvey
								.getPostFellingInspectionLines())
						{
							facade.updatePostFellingInspectionLine(line);
						}
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

				postFellingSurveyRecord.setInspection(true);
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

	public boolean isInspectionFinish()
	{
		return inspectionFinish;
	}

	public void setInspectionFinish(boolean inspectionFinish)
	{
		this.inspectionFinish = inspectionFinish;
	}

}