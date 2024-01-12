package my.edu.utem.ftmk.fis9.prefelling.controller.mbean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.mail.MessagingException;

import org.primefaces.event.DragDropEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

import my.edu.utem.ftmk.fis9.global.controller.manager.AbstractFacade;
import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.global.util.EmailSender;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.AreaStatus;
import my.edu.utem.ftmk.fis9.maintenance.model.Aspect;
import my.edu.utem.ftmk.fis9.maintenance.model.Contractor;
import my.edu.utem.ftmk.fis9.maintenance.model.District;
import my.edu.utem.ftmk.fis9.maintenance.model.Elevation;
import my.edu.utem.ftmk.fis9.maintenance.model.Fertility;
import my.edu.utem.ftmk.fis9.maintenance.model.ForestType;
import my.edu.utem.ftmk.fis9.maintenance.model.Geology;
import my.edu.utem.ftmk.fis9.maintenance.model.LogQuality;
import my.edu.utem.ftmk.fis9.maintenance.model.PlotType;
import my.edu.utem.ftmk.fis9.maintenance.model.Range;
import my.edu.utem.ftmk.fis9.maintenance.model.RegenerationSpecies;
import my.edu.utem.ftmk.fis9.maintenance.model.Resam;
import my.edu.utem.ftmk.fis9.maintenance.model.SlopeLocation;
import my.edu.utem.ftmk.fis9.maintenance.model.SoilStatus;
import my.edu.utem.ftmk.fis9.maintenance.model.SoilType;
import my.edu.utem.ftmk.fis9.maintenance.model.Species;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.maintenance.model.Tender;
import my.edu.utem.ftmk.fis9.maintenance.model.TreeLimit;
import my.edu.utem.ftmk.fis9.maintenance.model.VineSpreadth;
import my.edu.utem.ftmk.fis9.prefelling.controller.manager.PreFellingFacade;
import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingCuttingOption;
import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingReport;
import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingSurvey;
import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingSurveyCard;
import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingSurveyRecord;
import my.edu.utem.ftmk.fis9.prefelling.util.PreClosingLetterGenerator;
import my.edu.utem.ftmk.fis9.prefelling.util.PreFellingReportGenerator;
import my.edu.utem.ftmk.fis9.prefelling.util.PreFellingSurveyCardGenerator;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "preFellingSurveyCardMBean")
public class PreFellingSurveyCardManagedBean
		extends AbstractManagedBean<PreFellingSurveyCard>
{
	private static final long serialVersionUID = VERSION;
	private PreFellingSurvey preFellingSurvey;
	private PreFellingSurveyRecord preFellingSurveyRecord;
	private PlotType plotType;
	private District district;
	private Range range;
	private PreFellingSurveyCard[] traditionalPreFellingSurveyCards;
	private ArrayList<PreFellingSurvey> preFellingSurveys;
	private ArrayList<ForestType> forestTypes;
	private ArrayList<SoilType> soilTypes;
	private ArrayList<AreaStatus> areaStatuses;
	private ArrayList<SoilStatus> soilStatuses;
	private ArrayList<Geology> geologies;
	private ArrayList<Aspect> aspects;
	private ArrayList<SlopeLocation> slopeLocations;
	private ArrayList<Elevation> elevations;
	private ArrayList<Resam> resams;
	private ArrayList<Species> speciesList;
	private ArrayList<PlotType> plotTypes;
	private ArrayList<LogQuality> logQualities;
	private ArrayList<Fertility> fertilities;
	private ArrayList<VineSpreadth> vineSpreadths;
	private ArrayList<PreFellingCuttingOption> preFellingCuttingOptions;
	private ArrayList<SelectItem> lineList;
	private ArrayList<Integer> recommendations;
	private LinkedHashMap<String, ArrayList<PreFellingSurveyCard>> map;
	private String[] selectedLines;
	private String workingDates;
	private String recommendation;
	private String percentage;
	private String downtype;
	private String uptype;
	private boolean addPreFellingSurveyRecordOperation;
	private boolean validated;
	private boolean downloaded;
	private long selectedPreFellingSurveyID;
	private int accessLevel;
	private int totalFiles;
	private int counter;

	public PreFellingSurveyCardManagedBean()
	{
		try (MaintenanceFacade mFacade = new MaintenanceFacade();
				PreFellingFacade pFacade = new PreFellingFacade();)
		{
			AbstractFacade.group(mFacade, pFacade);

			Staff user = getCurrentUser();
			String staffID = user.getStaffID();
			int stateID = user.getStateID(),
					designationID = user.getDesignationID(),
					endYear = pFacade.getPreFellingSurveyYearRange()[1];

			forestTypes = mFacade.getForestTypes();
			soilTypes = mFacade.getSoilTypes();
			geologies = mFacade.getGeologies();
			areaStatuses = mFacade.getAreaStatuses();
			soilStatuses = mFacade.getSoilStatuses();
			aspects = mFacade.getAspects();
			slopeLocations = mFacade.getSlopeLocations();
			elevations = mFacade.getElevations();
			resams = mFacade.getResams();
			speciesList = mFacade.getSpeciesList();
			plotTypes = mFacade.getPlotTypes();
			logQualities = mFacade.getLogQualities();
			fertilities = mFacade.getFertilities();
			vineSpreadths = mFacade.getVineSpreadths();

			sort(speciesList);
			sort(plotTypes);

			if (stateID == 0)
			{
				if (designationID == 0)
				{
					preFellingSurveys = pFacade.getPreFellingSurveys(0,
							endYear);
					uptype = "ccoo";
				}
				else
				{
					preFellingSurveys = pFacade.getPreFellingSurveys(user, 0,
							endYear);
					accessLevel = 6;
					downtype = "ccoc";
				}
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
					preFellingSurveys = pFacade.getPreFellingSurveys(state, 0,
							endYear);
					accessLevel = staffID.equals(state.getDirectorID()) ? 1 : 7;
				}
				else
				{
					district = mFacade.getDistrict(user);

					if (district != null)
					{
						preFellingSurveys = pFacade
								.getPreFellingSurveys(district, 0, endYear);
						accessLevel = district.getOfficerID().equals(staffID)
								? 2
								: 3;
						uptype = "ccor";
						downtype = "ccoo";
					}
					else
					{
						range = mFacade.getRange(user);

						if (range != null)
						{
							preFellingSurveys = pFacade
									.getPreFellingSurveys(range, 0, endYear);
							accessLevel = 4;
							uptype = "ccof";
							downtype = "ccor";
						}
						else
						{
							preFellingSurveys = pFacade
									.getPreFellingSurveys(user, 0, endYear);
							accessLevel = 5;
							downtype = "ccof";
						}
					}
				}
			}

			if (preFellingSurveys != null)
			{
				sort(preFellingSurveys);

				for (PreFellingSurvey preFellingSurvey : preFellingSurveys)
					preFellingSurvey
							.setRecorders(mFacade.getStaffs(preFellingSurvey));
			}
			else
				preFellingSurveys = new ArrayList<>();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public PreFellingSurveyCard getPreFellingSurveyCard()
	{
		return model;
	}

	public void setPreFellingSurveyCard(
			PreFellingSurveyCard preFellingSurveyCard)
	{
		this.model = preFellingSurveyCard;
	}

	public PreFellingSurvey getPreFellingSurvey()
	{
		return preFellingSurvey;
	}

	public void setPreFellingSurvey(PreFellingSurvey preFellingSurvey)
	{
		this.preFellingSurvey = preFellingSurvey;
	}

	public PreFellingSurveyRecord getPreFellingSurveyRecord()
	{
		return preFellingSurveyRecord;
	}

	public void setPreFellingSurveyRecord(
			PreFellingSurveyRecord preFellingSurveyRecord)
	{
		this.preFellingSurveyRecord = preFellingSurveyRecord;
	}

	public PlotType getPlotType()
	{
		return plotType;
	}

	public void setPlotType(PlotType plotType)
	{
		this.plotType = plotType;
	}

	public PreFellingSurveyCard[] getTraditionalPreFellingSurveyCards()
	{
		return traditionalPreFellingSurveyCards;
	}

	public void setPreFellingTraditionalSurveyCards(
			PreFellingSurveyCard[] traditionalPreFellingSurveyCards)
	{
		this.traditionalPreFellingSurveyCards = traditionalPreFellingSurveyCards;
	}

	public ArrayList<PreFellingSurveyCard> getPreFellingSurveyCards()
	{
		return models;
	}

	public void setPreFellingSurveyCards(
			ArrayList<PreFellingSurveyCard> preFellingSurveyCards)
	{
		this.models = preFellingSurveyCards;
	}

	public ArrayList<PreFellingSurvey> getPreFellingSurveys()
	{
		return preFellingSurveys;
	}

	public void setPreFellingSurveys(
			ArrayList<PreFellingSurvey> preFellingSurveys)
	{
		this.preFellingSurveys = preFellingSurveys;
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

	public ArrayList<Geology> getGeologies()
	{
		return geologies;
	}

	public void setGeologies(ArrayList<Geology> geologies)
	{
		this.geologies = geologies;
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

	public ArrayList<Fertility> getFertilities()
	{
		return fertilities;
	}

	public void setFertilities(ArrayList<Fertility> fertilities)
	{
		this.fertilities = fertilities;
	}

	public ArrayList<VineSpreadth> getVineSpreadths()
	{
		return vineSpreadths;
	}

	public void setVineSpreadths(ArrayList<VineSpreadth> vineSpreadths)
	{
		this.vineSpreadths = vineSpreadths;
	}

	public ArrayList<PreFellingCuttingOption> getPreFellingCuttingOptions()
	{
		return preFellingCuttingOptions;
	}

	public void setPreFellingCuttingOptions(
			ArrayList<PreFellingCuttingOption> preFellingCuttingOptions)
	{
		this.preFellingCuttingOptions = preFellingCuttingOptions;
	}

	public ArrayList<SelectItem> getLineList()
	{
		return lineList;
	}

	public void setLineList(ArrayList<SelectItem> lineList)
	{
		this.lineList = lineList;
	}

	public ArrayList<Integer> getRecommendations()
	{
		return recommendations;
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

	public boolean isAddPreFellingSurveyRecordOperation()
	{
		return addPreFellingSurveyRecordOperation;
	}

	public void setAddPreFellingSurveyRecordOperation(
			boolean addPreFellingSurveyRecordOperation)
	{
		this.addPreFellingSurveyRecordOperation = addPreFellingSurveyRecordOperation;
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

	public long getSelectedPreFellingSurveyID()
	{
		return selectedPreFellingSurveyID;
	}

	public void setSelectedPreFellingSurveyID(long selectedPreFellingSurveyID)
	{
		this.selectedPreFellingSurveyID = selectedPreFellingSurveyID;
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

	public void handlePreFellingSurveyIDChange()
	{
		clearFilter();

		try (MaintenanceFacade mFacade = new MaintenanceFacade();
				PreFellingFacade pFacade = new PreFellingFacade();)
		{
			AbstractFacade.group(mFacade, pFacade);

			preFellingSurvey = null;
			validated = false;
			downloaded = false;
			selectedLines = null;
			recommendation = null;
			percentage = null;
			lineList = null;
			map = null;

			for (PreFellingSurvey s : preFellingSurveys)
				if (s.getPreFellingSurveyID() == selectedPreFellingSurveyID)
					preFellingSurvey = s;

			models = pFacade.getPreFellingSurveyCards(preFellingSurvey);
			Staff user = getCurrentUser();
			State state = mFacade.getState(preFellingSurvey.getStateID());
			String staffID = user.getStaffID();

			if (staffID.equals(state.getDirectorID())
					|| staffID.equals(state.getDeputyDirector1ID())
					|| staffID.equals(state.getDeputyDirector2ID())
					|| staffID.equals(state.getSeniorAsstDirector1ID())
					|| staffID.equals(state.getAsstDirector1ID()))
			{
				if (preFellingSurvey.getTenderNo() == null)
				{
					uptype = "ccoo";
					ArrayList<PreFellingSurveyCard> temp = new ArrayList<>();

					for (PreFellingSurveyCard preFellingSurveyCard : models)
						if (preFellingSurveyCard.getInspectorID() == null)
							temp.add(preFellingSurveyCard);

					models.removeAll(temp);
				}
				else
					uptype = "ccoc";
			}

			SimpleDateFormat sdf = new SimpleDateFormat("d MMMM yyyy",
					new Locale("ms"));
			int totalValidated = 0, totalDownloaded = 0, count = models.size();

			sort(models);
			preFellingSurvey.setPreFellingSurveyCards(models);

			for (PreFellingSurveyCard preFellingSurveyCard : models)
			{
				sort(preFellingSurveyCard.getPreFellingSurveyRecords());

				if (preFellingSurveyCard.getInspectorID() != null)
					totalValidated++;

				if (preFellingSurveyCard.getInspectionDate() != null)
					totalDownloaded++;
			}

			if (preFellingSurvey.getStartDate() != null
					&& preFellingSurvey.getEndDate() != null)
				workingDates = sdf.format(preFellingSurvey.getStartDate())
						+ " sehingga "
						+ sdf.format(preFellingSurvey.getEndDate());
			else
				workingDates = "Belum ditentukan";

			validated = totalValidated == count;

			if (totalDownloaded == count)
			{
				District district = mFacade
						.getDistrict(preFellingSurvey.getDistrictID());
				downloaded = user.getStaffID().equals(district.getOfficerID());
			}

			if (accessLevel == 7)
				recommendations = pFacade
						.getCuttingOptionRecommendations(preFellingSurvey);
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
			if (pt.getPlotTypeID() == preFellingSurveyRecord.getPlotTypeID())
			{
				plotType = pt;
				preFellingSurveyRecord.setPlotTypeName(pt.toString());
			}
		}
	}

	@Override
	public void handleOpen()
	{
		if (addOperation)
		{
			model = new PreFellingSurveyCard();
			Date date = new Date();
			Staff user = getCurrentUser();

			model.setPreFellingSurveyCardID(date.getTime());
			model.setSurveyDate(date);
			model.setRecorderID(user.getStaffID());
			model.setRecorderName(user.getName());
			model.setPreFellingSurveyID(
					preFellingSurvey.getPreFellingSurveyID());
			model.setPreFellingSurveyRecords(new ArrayList<>());
		}
		else
			model = (PreFellingSurveyCard) copy(models, model);
	}

	public void handleOpenPreFellingSurveyRecord()
	{
		if (addPreFellingSurveyRecordOperation)
		{
			preFellingSurveyRecord = new PreFellingSurveyRecord();

			preFellingSurveyRecord
					.setPreFellingSurveyRecordID(System.currentTimeMillis());
			preFellingSurveyRecord.setPreFellingSurveyCardID(
					model.getPreFellingSurveyCardID());
		}
		else
		{
			preFellingSurveyRecord = (PreFellingSurveyRecord) copy(
					model.getPreFellingSurveyRecords(), preFellingSurveyRecord);
			handlePlotTypeIDChange();
		}
	}

	public void handleOpenTraditional()
	{
		int size = plotTypes.size();
		long current = System.currentTimeMillis();
		traditionalPreFellingSurveyCards = new PreFellingSurveyCard[size];

		for (int i = 0; i < size; i++)
		{
			PreFellingSurveyCard preFellingSurveyCard = new PreFellingSurveyCard();
			PlotType plotType = plotTypes.get(i);
			ArrayList<PreFellingSurveyRecord> preFellingSurveyRecords = new ArrayList<>();

			preFellingSurveyCard
					.setPreFellingSurveyRecords(preFellingSurveyRecords);

			for (int j = 0; j < 10; j++)
			{
				PreFellingSurveyRecord preFellingSurveyRecord = new PreFellingSurveyRecord();

				preFellingSurveyRecord
						.setPreFellingSurveyRecordID(current + i * 10 + j);
				preFellingSurveyRecord.setPlotTypeID(plotType.getPlotTypeID());
				preFellingSurveyRecord.setPlotTypeName(plotType.toString());

				preFellingSurveyRecords.add(preFellingSurveyRecord);
			}

			traditionalPreFellingSurveyCards[i] = preFellingSurveyCard;
		}
	}

	public void preFellingSurveyCardEntry()
	{
		try (PreFellingFacade facade = new PreFellingFacade())
		{
			for (ForestType forestType : forestTypes)
				if (forestType.getForestTypeID() == model.getForestTypeID())
					model.setForestType(forestType.getCode());

			for (SoilType soilType : soilTypes)
				if (soilType.getSoilTypeID() == model.getSoilTypeID())
					model.setSoilType(soilType.getCode());

			/*
			 * for (Geology geology : geologies) if (geology.getGeologyID() ==
			 * preFellingSurveyCard.getGeologyID())
			 * preFellingSurveyCard.setGeology(geology.getCode());
			 */

			for (AreaStatus areaStatus : areaStatuses)
				if (areaStatus.getAreaStatusID() == model.getAreaStatusID())
					model.setAreaStatus(areaStatus.getCode());

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
					addOperation ? facade.addPreFellingSurveyCard(model, true)
							: facade.updatePreFellingSurveyCard(model),
					addOperation, facade,
					"kad bancian, ID " + model.getPreFellingSurveyCardID(),
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

	public void preFellingSurveyRecordEntry()
	{
		try (PreFellingFacade facade = new PreFellingFacade())
		{
			for (Species species : speciesList)
			{
				if (species.getSpeciesID() == preFellingSurveyRecord
						.getSpeciesID())
				{
					preFellingSurveyRecord.setSpeciesCode(species.getCode());
					preFellingSurveyRecord.setSpeciesName(species.getName());
					preFellingSurveyRecord
							.setSpeciesTypeID(species.getSpeciesTypeID());
				}
			}

			for (LogQuality logQuality : logQualities)
			{
				if (logQuality.getLogQualityID() == preFellingSurveyRecord
						.getLogQualityID())
				{
					preFellingSurveyRecord.setLogQuality(logQuality.getCode());
					preFellingSurveyRecord
							.setLogQualityName(logQuality.getName());
				}
			}

			for (Fertility fertility : fertilities)
			{
				if (fertility.getFertilityID() == preFellingSurveyRecord
						.getFertilityID())
				{
					preFellingSurveyRecord.setFertility(fertility.getCode());
					preFellingSurveyRecord
							.setFertilityName(fertility.getName());
				}
			}

			for (VineSpreadth vineSpreadth : vineSpreadths)
			{
				if (vineSpreadth.getVineSpreadthID() == preFellingSurveyRecord
						.getVineSpreadthID())
				{
					preFellingSurveyRecord
							.setVineSpreadth(vineSpreadth.getCode());
					preFellingSurveyRecord
							.setVineSpreadthName(vineSpreadth.getName());

					if (vineSpreadth.getVineSpreadthID() == 3)
						preFellingSurveyRecord.setVineDiameter(null);
				}
			}

			if (addPreFellingSurveyRecordOperation)
			{
				finalizeModelEntry(
						facade.addPreFellingSurveyRecord(preFellingSurveyRecord,
								true),
						addPreFellingSurveyRecordOperation, facade,
						"rekod bancian, ID " + preFellingSurveyRecord
								.getPreFellingSurveyRecordID(),
						", kerana rekod bancian telah direkodkan sebelumnya",
						model.getPreFellingSurveyRecords(),
						preFellingSurveyRecord);

				int plotTypeID = preFellingSurveyRecord.getPlotTypeID();
				String plotTypeName = preFellingSurveyRecord.getPlotTypeName();

				handleOpenPreFellingSurveyRecord();
				preFellingSurveyRecord.setPlotTypeID(plotTypeID);
				preFellingSurveyRecord.setPlotTypeName(plotTypeName);
			}
			else
			{
				finalizeModelEntry(
						facade.updatePreFellingSurveyRecord(
								preFellingSurveyRecord),
						addPreFellingSurveyRecordOperation, facade,
						"rekod bancian, ID " + preFellingSurveyRecord
								.getPreFellingSurveyRecordID(),
						", kerana rekod bancian telah direkodkan sebelumnya",
						model.getPreFellingSurveyRecords(),
						preFellingSurveyRecord);
				preFellingSurveyRecord = null;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		if (!addPreFellingSurveyRecordOperation)
			execute("PF('popupSurveyRecord').hide()");
	}

	public void preFellingSurveyRecordDelete()
	{
		try (PreFellingFacade facade = new PreFellingFacade())
		{
			if (facade
					.deletePreFellingSurveyRecord(preFellingSurveyRecord) != 0)
			{
				addMessage(FacesMessage.SEVERITY_INFO, null,
						preFellingSurveyRecord + " berjaya dipadamkan.");
				model.getPreFellingSurveyRecords()
						.remove(preFellingSurveyRecord);
				log(facade, "Padam rekod bancian, ID "
						+ preFellingSurveyRecord.getPreFellingSurveyRecordID());
			}
			else
				addMessage(FacesMessage.SEVERITY_WARN, null,
						preFellingSurveyRecord + " tidak dapat dipadamkan.");

			preFellingSurveyRecord = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public void preFellingSurveyRecordEntryTraditional()
	{
		try (PreFellingFacade facade = new PreFellingFacade())
		{
			int count = 0;

			for (PreFellingSurveyCard traditionalSurveyCard : traditionalPreFellingSurveyCards)
			{
				ArrayList<PreFellingSurveyRecord> preFellingSurveyRecords = traditionalSurveyCard
						.getPreFellingSurveyRecords();

				for (PreFellingSurveyRecord preFellingSurveyRecord : preFellingSurveyRecords)
				{
					if (preFellingSurveyRecord.getSpeciesID() != 0)
					{
						preFellingSurveyRecord.setPreFellingSurveyCardID(
								model.getPreFellingSurveyCardID());

						for (Species species : speciesList)
						{
							if (species.getSpeciesID() == preFellingSurveyRecord
									.getSpeciesID())
							{
								preFellingSurveyRecord
										.setSpeciesCode(species.getCode());
								preFellingSurveyRecord
										.setSpeciesName(species.getName());
								preFellingSurveyRecord.setSpeciesTypeID(
										species.getSpeciesTypeID());
							}
						}

						for (LogQuality logQuality : logQualities)
						{
							if (logQuality
									.getLogQualityID() == preFellingSurveyRecord
											.getLogQualityID())
							{
								preFellingSurveyRecord
										.setLogQuality(logQuality.getCode());
								preFellingSurveyRecord.setLogQualityName(
										logQuality.getName());
							}
						}

						for (Fertility fertility : fertilities)
						{
							if (fertility
									.getFertilityID() == preFellingSurveyRecord
											.getFertilityID())
							{
								preFellingSurveyRecord
										.setFertility(fertility.getCode());
								preFellingSurveyRecord
										.setFertilityName(fertility.getName());
							}
						}

						for (VineSpreadth vineSpreadth : vineSpreadths)
						{
							if (vineSpreadth
									.getVineSpreadthID() == preFellingSurveyRecord
											.getVineSpreadthID())
							{
								preFellingSurveyRecord.setVineSpreadth(
										vineSpreadth.getCode());
								preFellingSurveyRecord.setVineSpreadthName(
										vineSpreadth.getName());

								if (vineSpreadth.getVineSpreadthID() == 3)
									preFellingSurveyRecord
											.setVineDiameter(null);
							}
						}

						if (facade.addPreFellingSurveyRecord(
								preFellingSurveyRecord, true) != 0)
						{
							count++;

							model.getPreFellingSurveyRecords()
									.add(preFellingSurveyRecord);
							log(facade, "Tambah rekod bancian, ID "
									+ preFellingSurveyRecord
											.getPreFellingSurveyRecordID());
						}
					}
				}
			}

			sort(model.getPreFellingSurveyRecords());
			addMessage(FacesMessage.SEVERITY_INFO, null, "Sebanyak " + count
					+ " rekod bancian berjaya ditambahkan.");

			traditionalPreFellingSurveyCards = null;
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
		try (MaintenanceFacade mFacade = new MaintenanceFacade();
				PreFellingFacade pFacade = new PreFellingFacade();)
		{
			AbstractFacade.group(mFacade, pFacade);

			int total = 0, count = 0;
			Staff user = getCurrentUser();

			for (PreFellingSurveyCard preFellingSurveyCard : models)
			{
				if (preFellingSurveyCard.getInspectorID() == null
						&& preFellingSurveyCard.getInspectionDate() != null)
				{
					count++;

					preFellingSurveyCard.setInspectorID(user.getStaffID());
					preFellingSurveyCard.setInspectorName(user.getName());

					if (pFacade.updatePreFellingSurveyCard(
							preFellingSurveyCard) != 0)
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

			if (total != 0)
			{
				String recipient = null;
				State state = mFacade.getState(user.getStateID());

				if (state.getDeputyDirector1ID() != null)
					recipient = state.getDeputyDirector1ID();
				else if (state.getSeniorAsstDirector1ID() != null)
					recipient = state.getSeniorAsstDirector1ID();
				else if (state.getAsstDirector1ID() != null)
					recipient = state.getAsstDirector1ID();
				else if (state.getDeputyDirector2ID() != null)
					recipient = state.getDeputyDirector2ID();
				else
					recipient = state.getDirectorID();

				new EmailSender().send(true,
						"Kad Bancian Inventori Hutan Sebelum Tebangan - "
								+ preFellingSurvey.getForestName() + " "
								+ preFellingSurvey.getComptBlockNo(),
						"Kad bancian Inventori Hutan Sebelum Tebangan (Pre-Felling) telah disahkan oleh "
								+ user.getName()
								+ " untuk:<br/><br/><table border='0'><tr><td>- Hutan simpan</td><td>:</td><td>"
								+ preFellingSurvey.getForestName()
								+ "</td></tr><tr><td>- No. kompartmen/sub kompartmen</td><td>:</td><td>"
								+ preFellingSurvey.getComptBlockNo()
								+ "</td></tr></table><br/>Sila log masuk ke FIS9 untuk tindakan anda seterusnya.",
						recipient);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
		catch (IOException | MessagingException e)
		{
			e.printStackTrace();
			addMessage(FacesMessage.SEVERITY_WARN, null,
					"Sistem tidak berjaya menghantar emel notifikasi.");
		}
	}

	public void prepareDownload()
	{
		lineList = new ArrayList<>();
		map = new LinkedHashMap<>();

		for (PreFellingSurveyCard preFellingSurveyCard : models)
		{
			if (preFellingSurveyCard.getInspectorID() == null)
			{
				String lineNo = preFellingSurveyCard.getLineNo();
				ArrayList<PreFellingSurveyCard> lineSurveyCard = map
						.get(lineNo);

				if (lineSurveyCard == null)
				{
					lineSurveyCard = new ArrayList<>();
					map.put(lineNo, lineSurveyCard);
				}

				lineSurveyCard.add(preFellingSurveyCard);
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

	public void prepareUpload()
	{
		totalFiles = new Integer(
				FacesContext.getCurrentInstance().getExternalContext()
						.getRequestParameterMap().get("totalFiles"));
	}

	public void prepareReport()
	{
		try (MaintenanceFacade mFacade = new MaintenanceFacade();
				PreFellingFacade pFacade = new PreFellingFacade();)
		{
			AbstractFacade.group(mFacade, pFacade);

			State state = new State();

			state.setStateID(preFellingSurvey.getStateID());

			if (preFellingSurvey.getPreFellingReport() == null)
			{
				PreFellingReport preFellingReport = new PreFellingReport(
						preFellingSurvey, mFacade.getTimberGroups(),
						mFacade.getCuttingOptions(state), logQualities);

				preFellingSurvey.setPreFellingReport(preFellingReport);

				if (accessLevel == 7)
				{
					preFellingCuttingOptions = new ArrayList<>();
					int size = recommendations.size();

					if (size != 0)
					{
						ArrayList<PreFellingCuttingOption> scos = preFellingReport
								.getPreFellingCuttingOptions();

						for (int i = 0; i < size; i++)
						{
							int cuttingOptionID = recommendations.get(i);

							for (PreFellingCuttingOption preFellingCuttingOption : scos)
							{
								if (cuttingOptionID == preFellingCuttingOption
										.getCuttingOptionID())
								{
									preFellingCuttingOption.setRank(i + 1);
									preFellingCuttingOptions
											.add(preFellingCuttingOption);
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

	public void selectLine(AjaxBehaviorEvent event)
	{
		percentage = "Jumlah kad dipilih: "
				+ (selectedLines == null || selectedLines.length == 0 ? 100
						: Math.round(selectedLines.length * 100d / map.size()))
				+ "%";
	}

	public void handleDrop(DragDropEvent<PreFellingCuttingOption> event)
	{
		PreFellingCuttingOption preFellingCuttingOption = event.getData();

		if (!preFellingCuttingOptions.contains(preFellingCuttingOption))
			preFellingCuttingOptions.add(preFellingCuttingOption);
		else
			addMessage(FacesMessage.SEVERITY_INFO, null,
					"Opsyen had tebangan sudah disyorkan sebelumnya.");
	}

	public void recommend()
	{
		if (preFellingCuttingOptions != null)
		{
			try (MaintenanceFacade mFacade = new MaintenanceFacade();
					PreFellingFacade pFacade = new PreFellingFacade();)
			{
				AbstractFacade.group(mFacade, pFacade);
				pFacade.deleteCuttingOptionRecommendations(preFellingSurvey);

				recommendations = new ArrayList<>();

				for (int i = 0; i < preFellingCuttingOptions.size(); i++)
				{
					PreFellingCuttingOption preFellingCuttingOption = preFellingCuttingOptions
							.get(i);

					preFellingCuttingOption.setRank(i + 1);
					pFacade.addPreFellingCuttingOption(preFellingCuttingOption);
					recommendations
							.add(preFellingCuttingOption.getCuttingOptionID());
				}

				preFellingSurvey.setRecommended(true);
				addMessage(FacesMessage.SEVERITY_INFO, null,
						"Opsyen had tebangan berjaya disyorkan.");

				Staff user = getCurrentUser();
				State state = mFacade.getState(user.getStateID());

				new EmailSender().send(
						true,
						"Penetapan Had Batas Tebangan - "
								+ preFellingSurvey
										.getForestName()
								+ " " + preFellingSurvey.getComptBlockNo(),
						"Had batas tebangan Inventori Hutan Sebelum Tebangan (Pre-Felling) telah disyorkan oleh "
								+ user.getName()
								+ " untuk:<br/><br/><table border='0'><tr><td>- Hutan simpan</td><td>:</td><td>"
								+ preFellingSurvey.getForestName()
								+ "</td></tr><tr><td>- No. kompartmen/sub kompartmen</td><td>:</td><td>"
								+ preFellingSurvey.getComptBlockNo()
								+ "</td></tr></table><br/>Sila log masuk ke FIS9 untuk tindakan anda seterusnya.",
						state.getDirectorID());
			}
			catch (SQLException e)
			{
				e.printStackTrace();
				addMessage(e);
			}
			catch (IOException | MessagingException e)
			{
				e.printStackTrace();
				addMessage(FacesMessage.SEVERITY_WARN, null,
						"Sistem tidak berjaya menghantar emel notifikasi.");
			}
		}

		execute("PF('popupReport2').hide()");
	}

	public StreamedContent download()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		String name = "LaporanBancian_"
				+ preFellingSurvey.getForestName().replaceAll(" ", "") + "_"
				+ preFellingSurvey.getComptBlockNo() + "_"
				+ preFellingSurvey.getYear() + ".pdf";
		File file = new File(external.getRealPath("/") + "files/pre-f/" + name);
		StreamedContent content = null;

		file.getParentFile().mkdirs();

		try
		{
			PreFellingReportGenerator.generate(file, preFellingSurvey,
					vineSpreadths, resams, fertilities);
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

	public StreamedContent downloadLetter()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		String name = "PenetapanHBT_"
				+ preFellingSurvey.getForestName().replaceAll(" ", "") + "_"
				+ preFellingSurvey.getComptBlockNo() + "_"
				+ preFellingSurvey.getYear() + ".pdf";
		File file = new File(external.getRealPath("/") + "files/pre-f/" + name);
		StreamedContent content = null;

		file.getParentFile().mkdirs();

		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			State state = facade.getState(preFellingSurvey.getStateID());
			TreeLimit treeLimit = facade.getTreeLimit(state);

			if (preFellingSurvey.getPreFellingReport() == null)
				preFellingSurvey.setPreFellingReport(new PreFellingReport(
						preFellingSurvey, facade.getTimberGroups(),
						facade.getCuttingOptions(state),
						facade.getLogQualities()));

			PreClosingLetterGenerator.generate(file, preFellingSurvey,
					treeLimit, state, getCurrentUser(), recommendations);

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
		String host = null;

		try
		{
			host = InetAddress.getLocalHost().getHostName().toUpperCase();
		}
		catch (Exception e)
		{
			host = user.getStaffID();
		}

		String name = "KadBancian_"
				+ preFellingSurvey.getForestName().replaceAll(" ", "") + "_"
				+ preFellingSurvey.getComptBlockNo() + "_"
				+ preFellingSurvey.getYear() + "_" + host + "."
				+ (pdf ? "pdf" : downtype), type = null;
		File file = new File(external.getRealPath("/") + "files/pre-f/" + name);
		StreamedContent content = null;

		file.getParentFile().mkdirs();

		try
		{
			if (pdf)
			{
				try (MaintenanceFacade mFacade = new MaintenanceFacade();
						PreFellingFacade pFacade = new PreFellingFacade();)
				{
					State state = new State();
					ArrayList<PreFellingSurveyCard> temp = null;

					AbstractFacade.group(mFacade, pFacade);
					state.setStateID(preFellingSurvey.getStateID());

					ArrayList<RegenerationSpecies> regenerationSpeciesList = mFacade
							.getRegenerationSpeciesList(state);
					LinkedHashMap<String, String> map = new LinkedHashMap<>();
					Tender tender = mFacade
							.getTender(preFellingSurvey.getTenderNo());
					Contractor contractor = null;

					for (RegenerationSpecies regenerationSpecies : regenerationSpeciesList)
					{
						String species = regenerationSpecies.getName()
								.toLowerCase(),
								code = regenerationSpecies.getCode(),
								symbol = "C";

						if (code != null)
						{
							if (code.equals("0106") || code.equals("0103")
									|| code.equals("0105")
									|| code.equals("0102"))
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
						temp = preFellingSurvey.getPreFellingSurveyCards();
						ArrayList<PreFellingSurveyCard> selected = new ArrayList<>();

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

						preFellingSurvey.setPreFellingSurveyCards(selected);
					}

					map.put("0201", "M");
					PreFellingSurveyCardGenerator.generate(file,
							preFellingSurvey, contractor, map);

					if (lineList != null)
					{
						preFellingSurvey.setPreFellingSurveyCards(temp);

						Date now = new Date();
						District district = mFacade
								.getDistrict(preFellingSurvey.getDistrictID());
						downloaded = user.getStaffID()
								.equals(district.getOfficerID());

						for (PreFellingSurveyCard preFellingSurveyCard : temp)
						{
							preFellingSurveyCard.setInspectionDate(now);
							pFacade.updatePreFellingSurveyCard(
									preFellingSurveyCard);
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

				oos.writeInt(22);
				oos.writeObject(preFellingSurvey);

				if (downtype.equals("ccof") || downtype.equals("ccoc"))
				{
					oos.writeObject(null);
					oos.writeObject(null);
				}
				else if (downtype.equals("ccor"))
				{
					oos.writeObject(user);
					oos.writeObject(null);
				}
				else if (downtype.equals("ccoo"))
				{
					oos.writeObject(null);
					oos.writeObject(user);
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

	public synchronized void upload(FileUploadEvent event)
	{
		UploadedFile file = event.getFile();

		if (file != null)
		{
			try (ObjectInputStream ois = new ObjectInputStream(
					new GZIPInputStream(file.getInputStream()));
					PreFellingFacade facade = new PreFellingFacade();)
			{
				if (ois.readInt() != 22)
					throw new InvalidClassException("Not PreFellingSurveyCard");

				PreFellingSurvey preFellingSurvey = (PreFellingSurvey) ois
						.readObject();
				Staff adfor = (Staff) ois.readObject();
				Staff dfo = (Staff) ois.readObject();

				if (preFellingSurvey.equals(this.preFellingSurvey))
				{
					String filename = file.getFileName().toLowerCase();
					ArrayList<PreFellingSurveyCard> currentSurveyCards = this.preFellingSurvey
							.getPreFellingSurveyCards();
					ArrayList<PreFellingSurveyCard> preFellingSurveyCards = preFellingSurvey
							.getPreFellingSurveyCards();

					if (filename.endsWith("ccof") || filename.endsWith("ccoc"))
					{
						if (adfor == null && dfo == null)
						{
							facade.updatePreFellingSurvey(preFellingSurvey);
							save(facade, currentSurveyCards,
									preFellingSurveyCards, false);
							preFellingSurvey.setPreFellingSurveyCards(
									currentSurveyCards);
							preFellingSurveys.set(
									preFellingSurveys
											.indexOf(this.preFellingSurvey),
									preFellingSurvey);

							this.preFellingSurvey = preFellingSurvey;
							models = currentSurveyCards;
						}
						else
							addMessage(FacesMessage.SEVERITY_WARN, null,
									preFellingSurvey
											+ " tidak dapat ditambahkan kerana sesi bancian yang dimuat naik tidak sah.");
					}
					else if (filename.endsWith("ccor"))
					{
						if (adfor != null && dfo == null)
						{
							facade.updatePreFellingSurvey(preFellingSurvey);
							save(facade, currentSurveyCards,
									preFellingSurveyCards, false);
							preFellingSurvey.setPreFellingSurveyCards(
									currentSurveyCards);
							preFellingSurveys.set(
									preFellingSurveys
											.indexOf(this.preFellingSurvey),
									preFellingSurvey);

							this.preFellingSurvey = preFellingSurvey;
							models = currentSurveyCards;
							validated = false;
							downloaded = false;
						}
						else
							addMessage(FacesMessage.SEVERITY_WARN, null,
									preFellingSurvey
											+ " tidak dapat ditambahkan kerana sesi bancian yang dimuat naik tidak sah.");
					}
					else if (filename.endsWith("ccoo"))
					{
						if (adfor == null && dfo != null)
						{
							facade.updatePreFellingSurvey(preFellingSurvey);
							save(facade, currentSurveyCards,
									preFellingSurveyCards, true);
							preFellingSurvey.setPreFellingSurveyCards(
									currentSurveyCards);
							preFellingSurveys.set(
									preFellingSurveys
											.indexOf(this.preFellingSurvey),
									preFellingSurvey);

							this.preFellingSurvey = preFellingSurvey;
							models = currentSurveyCards;
						}
						else
							addMessage(FacesMessage.SEVERITY_WARN, null,
									preFellingSurvey
											+ " tidak dapat ditambahkan kerana sesi bancian yang dimuat naik tidak sah.");
					}
				}
				else
					addMessage(FacesMessage.SEVERITY_WARN, null,
							preFellingSurvey
									+ " tidak dapat ditambahkan kerana maklumat sesi bancian yang dimuat naik tidak sama dengan sesi bancian yang dipilih.");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				addMessage(e);
			}
		}

		if (++counter == totalFiles)
		{
			execute("PF('modalloading').hide()");

			counter = 0;
			totalFiles = 0;
		}
	}

	private void save(PreFellingFacade facade,
			ArrayList<PreFellingSurveyCard> currentPreFellingSurveyCards,
			ArrayList<PreFellingSurveyCard> preFellingSurveyCards,
			boolean strict) throws SQLException
	{
		int totalSurveyCard = 0, totalRecord = 0;
		/*
		 * LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>(); // New
		 * mapping map.put(472, 263); map.put(473, 472); map.put(474, 473);
		 * map.put(475, 474); map.put(476, 475); map.put(477, 476); map.put(478,
		 * 477); map.put(479, 478); map.put(480, 479); map.put(481, 480);
		 * map.put(482, 481); map.put(483, 482); map.put(484, 483); map.put(485,
		 * 484); map.put(486, 485); map.put(487, 283); map.put(502, 359);
		 * map.put(505, 486); map.put(506, 502);
		 */

		// Old mapping
		/*
		 * map.put(293, 294); map.put(294, 295); map.put(295, 296); map.put(296,
		 * 297); map.put(297, 298); map.put(298, 299); map.put(299, 300);
		 * map.put(300, 301); map.put(301, 302); map.put(302, 303); map.put(303,
		 * 304); map.put(304, 305); map.put(305, 306); map.put(306, 307);
		 * map.put(307, 308); map.put(308, 309); map.put(309, 310); map.put(310,
		 * 311); map.put(311, 312); map.put(312, 313); map.put(313, 314);
		 * map.put(314, 315); map.put(315, 316); map.put(316, 317); map.put(317,
		 * 318); map.put(318, 319); map.put(319, 320); map.put(320, 321);
		 * map.put(321, 322); map.put(322, 323); map.put(323, 324); map.put(324,
		 * 325); map.put(325, 326); map.put(326, 327); map.put(327, 328);
		 * map.put(328, 329); map.put(329, 330); map.put(330, 331); map.put(331,
		 * 332); map.put(332, 333); map.put(333, 334); map.put(334, 335);
		 * map.put(335, 336); map.put(336, 337); map.put(337, 338); map.put(338,
		 * 339); map.put(339, 340); map.put(340, 341); map.put(341, 342);
		 * map.put(342, 343); map.put(343, 344); map.put(344, 345); map.put(345,
		 * 346); map.put(346, 347); map.put(347, 348); map.put(348, 349);
		 * map.put(349, 350); map.put(350, 351); map.put(351, 352); map.put(352,
		 * 353); map.put(353, 354); map.put(354, 355); map.put(355, 356);
		 * map.put(356, 357); map.put(357, 358); map.put(358, 366); map.put(359,
		 * 367); map.put(360, 368); map.put(361, 369); map.put(362, 370);
		 * map.put(363, 371); map.put(364, 372); map.put(365, 373); map.put(366,
		 * 374); map.put(367, 375); map.put(368, 376); map.put(369, 377);
		 * map.put(370, 378); map.put(371, 379); map.put(372, 380); map.put(373,
		 * 381); map.put(374, 382); map.put(375, 383); map.put(376, 384);
		 * map.put(377, 385); map.put(378, 386); map.put(379, 387); map.put(380,
		 * 388); map.put(381, 389); map.put(382, 390); map.put(383, 391);
		 * map.put(384, 392); map.put(385, 393); map.put(386, 394); map.put(387,
		 * 395); map.put(388, 396); map.put(389, 397); map.put(390, 398);
		 * map.put(391, 399); map.put(392, 400); map.put(393, 401); map.put(394,
		 * 402); map.put(395, 403); map.put(396, 404); map.put(397, 405);
		 * map.put(398, 406); map.put(399, 407); map.put(400, 408); map.put(401,
		 * 409); map.put(402, 410); map.put(403, 411); map.put(404, 412);
		 * map.put(405, 413); map.put(406, 416); map.put(407, 417); map.put(408,
		 * 418); map.put(409, 420); map.put(410, 421); map.put(411, 422);
		 * map.put(412, 423); map.put(413, 283); map.put(414, 484); map.put(415,
		 * 457); map.put(416, 457); map.put(417, 479); map.put(418, 489);
		 * map.put(419, 451); map.put(420, 288); map.put(421, 390); map.put(422,
		 * 481); map.put(423, 481); map.put(424, 491); map.put(425, 485);
		 * map.put(426, 437); map.put(427, 428); map.put(428, 495); map.put(429,
		 * 492); map.put(430, 504); map.put(431, 435); map.put(432, 427);
		 * map.put(433, 483); map.put(434, 447); map.put(435, 490); map.put(436,
		 * 475); map.put(437, 445); map.put(438, 444); map.put(440, 496);
		 * map.put(441, 364); map.put(442, 457); map.put(443, 434); map.put(444,
		 * 503); map.put(445, 460); map.put(446, 500); map.put(447, 359);
		 * map.put(448, 480); map.put(449, 498); map.put(450, 472); map.put(451,
		 * 499); map.put(452, 482); map.put(454, 497); map.put(455, 457);
		 * map.put(456, 360); map.put(457, 443); map.put(458, 460); map.put(459,
		 * 493); map.put(460, 501); map.put(461, 449); map.put(462, 448);
		 * map.put(463, 488); map.put(464, 446); map.put(465, 494); map.put(466,
		 * 441); map.put(467, 293); map.put(468, 436); map.put(469, 459);
		 * map.put(470, 442); map.put(471, 502); map.put(472, 432); map.put(473,
		 * 487); map.put(474, 164); map.put(475, 486);
		 */

		for (PreFellingSurveyCard preFellingSurveyCard : preFellingSurveyCards)
		{
			if (strict && preFellingSurveyCard.getInspectorID() == null)
				continue;

			ArrayList<PreFellingSurveyRecord> currentPreFellingSurveyRecords = null;
			ArrayList<PreFellingSurveyRecord> preFellingSurveyRecords = preFellingSurveyCard
					.getPreFellingSurveyRecords();

			if (facade.addPreFellingSurveyCard(preFellingSurveyCard,
					false) != 0)
			{
				totalSurveyCard++;
				int index = currentPreFellingSurveyCards
						.indexOf(preFellingSurveyCard);

				if (index == -1)
				{
					currentPreFellingSurveyRecords = new ArrayList<>();

					currentPreFellingSurveyCards.add(preFellingSurveyCard);
					log(facade, "Tambah kad bancian, ID "
							+ preFellingSurveyCard.getPreFellingSurveyCardID());
				}
				else
				{
					PreFellingSurveyCard temp = currentPreFellingSurveyCards
							.get(index);
					currentPreFellingSurveyRecords = temp
							.getPreFellingSurveyRecords();

					log(facade, "Kemaskini kad bancian, ID "
							+ preFellingSurveyCard.getPreFellingSurveyCardID());
					preFellingSurveyCard.setPreFellingSurveyCardID(
							temp.getPreFellingSurveyCardID());
					currentPreFellingSurveyCards.set(index,
							preFellingSurveyCard);
				}

				for (PreFellingSurveyRecord preFellingSurveyRecord : preFellingSurveyRecords)
				{
					/*
					 * Integer nID =
					 * map.get(preFellingSurveyRecord.getSpeciesID()); if (nID
					 * != null) preFellingSurveyRecord.setSpeciesID(nID); if
					 * (preFellingSurveyRecord.getPlotTypeID() > 6)
					 * preFellingSurveyRecord.setPlotTypeID(7);
					 */

					preFellingSurveyRecord.setPreFellingSurveyCardID(
							preFellingSurveyCard.getPreFellingSurveyCardID());

					try
					{
						if (facade.addPreFellingSurveyRecord(
								preFellingSurveyRecord, false) != 0)
						{
							totalRecord++;
							index = currentPreFellingSurveyRecords
									.indexOf(preFellingSurveyRecord);

							log(facade, "Tambah rekod bancian, ID "
									+ preFellingSurveyRecord
											.getPreFellingSurveyRecordID());

							if (index == -1)
								currentPreFellingSurveyRecords
										.add(preFellingSurveyRecord);
							else
								currentPreFellingSurveyRecords.set(index,
										preFellingSurveyRecord);
						}
					}
					catch (Exception e)
					{
						System.err.println(preFellingSurveyRecord + "\t"
								+ preFellingSurveyRecord.getSpeciesID());
					}
				}

				sort(currentPreFellingSurveyRecords);
				preFellingSurveyCard.setPreFellingSurveyRecords(
						currentPreFellingSurveyRecords);
			}
		}

		sort(currentPreFellingSurveyCards);

		if (totalSurveyCard != 0 || totalRecord != 0)
			addMessage(FacesMessage.SEVERITY_INFO, null,
					totalSurveyCard + " kad bancian dan " + totalRecord
							+ " rekod bancian berjaya ditambahkan.");
		else
			addMessage(FacesMessage.SEVERITY_INFO, null,
					"Tiada kad bancian dan rekod bancian berjaya ditambahkan.");
	}
}