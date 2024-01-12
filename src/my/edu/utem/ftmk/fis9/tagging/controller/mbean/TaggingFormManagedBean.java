package my.edu.utem.ftmk.fis9.tagging.controller.mbean;

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
import java.util.TreeMap;
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

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import my.edu.utem.ftmk.fis9.global.controller.manager.AbstractFacade;
import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.global.util.EmailSender;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.District;
import my.edu.utem.ftmk.fis9.maintenance.model.LogQuality;
import my.edu.utem.ftmk.fis9.maintenance.model.ProtectedSpecies;
import my.edu.utem.ftmk.fis9.maintenance.model.Range;
import my.edu.utem.ftmk.fis9.maintenance.model.Species;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.maintenance.model.TaggingType;
import my.edu.utem.ftmk.fis9.maintenance.model.TreeLimit;
import my.edu.utem.ftmk.fis9.maintenance.model.TreeType;
import my.edu.utem.ftmk.fis9.prefelling.controller.manager.PreFellingFacade;
import my.edu.utem.ftmk.fis9.prefelling.model.CuttingLimit;
import my.edu.utem.ftmk.fis9.tagging.controller.manager.TaggingFacade;
import my.edu.utem.ftmk.fis9.tagging.model.Tagging;
import my.edu.utem.ftmk.fis9.tagging.model.TaggingForm;
import my.edu.utem.ftmk.fis9.tagging.model.TaggingLimitException;
import my.edu.utem.ftmk.fis9.tagging.model.TaggingRecord;
import my.edu.utem.ftmk.fis9.tagging.util.TaggingFormGenerator;
import my.edu.utem.ftmk.fis9.tagging.util.TaggingReportGenerator;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "taggingFormMBean")
public class TaggingFormManagedBean extends AbstractManagedBean<TaggingForm>
{
	private static final long serialVersionUID = VERSION;
	private Tagging tagging;
	private TaggingRecord taggingRecord;
	private TreeLimit treeLimit;
	private District district;
	private Range range;
	private ArrayList<Tagging> taggings;
	private ArrayList<TaggingType> taggingTypes;
	private ArrayList<Species> speciesList;
	private ArrayList<ProtectedSpecies> protectedSpeciesList;
	private ArrayList<TreeType> treeTypes;
	private ArrayList<LogQuality> logQualities;
	private ArrayList<SelectItem> blockList;
	private TreeMap<String, Double> volumeMap;
	private LinkedHashMap<String, ArrayList<TaggingForm>> map;
	private String[] selectedBlocks;
	private String workingDates;
	private String recommendation;
	private String percentage;
	private String downtype;
	private String uptype;
	private boolean addTaggingRecordOperation;
	private boolean allowedClass;
	private boolean allowedLog;
	private boolean validated;
	private boolean downloaded;
	private double currentVolume;
	private double grossVolumeLimit;
	private double netVolumeLimit;
	private double minDiameter;
	private long selectedTaggingID;
	private int accessLevel;
	private int totalFiles;
	private int counter;

	public TaggingFormManagedBean()
	{
		try (MaintenanceFacade mFacade = new MaintenanceFacade(); TaggingFacade tFacade = new TaggingFacade();)
		{
			AbstractFacade.group(mFacade, tFacade);

			Staff user = getCurrentUser();
			String staffID = user.getStaffID();
			int stateID = user.getStateID(), designationID = user.getDesignationID(), endYear = tFacade.getTaggingYearRange()[1];

			taggingTypes = mFacade.getTaggingTypes();
			speciesList = mFacade.getSpeciesList();
			treeTypes = mFacade.getTreeTypes();
			logQualities = mFacade.getLogQualities();

			sort(speciesList);

			if (stateID == 0)
			{
				volumeMap = mFacade.getVolumeMap();

				if (designationID == 0)
				{
					taggings = tFacade.getTaggings(false, 0, endYear);
					uptype = "cfoo";
				}
				else
				{
					taggings = tFacade.getTaggings(user, 0, endYear);
					accessLevel = 6;
					downtype = "cfoc";
				}
			}
			else
			{
				State state = mFacade.getState(stateID);

				treeLimit = mFacade.getTreeLimit(state);
				protectedSpeciesList = mFacade.getProtectedSpeciesList(state);
				volumeMap = mFacade.getVolumeMap(state);

				if (state.getDirectorID().equals(staffID))
				{
					taggings = tFacade.getTaggings(state, false, 0, endYear);
					accessLevel = 1;
				}
				else
				{
					district = mFacade.getDistrict(user);

					if (district != null)
					{
						taggings = tFacade.getTaggings(district, false, 0, endYear);
						accessLevel = district.getOfficerID().equals(staffID) ? 2 : 3;
						uptype = "cfor";
						downtype = "cfoo";
					}
					else
					{
						range = mFacade.getRange(user);

						if (range != null)
						{
							taggings = tFacade.getTaggings(range, 0, endYear);
							accessLevel = 4;
							uptype = "cfof";
							downtype = "cfor";
						}
						else
						{
							taggings = tFacade.getTaggings(user, 0, endYear);
							accessLevel = 5;
							downtype = "cfof";
						}
					}
				}
			}

			if (taggings != null)
			{
				sort(taggings);

				for (Tagging tagging : taggings)
				{
					tagging.setRecorders(mFacade.getStaffs(tagging));
					tagging.setHammers(mFacade.getHammers(tagging));
					tagging.setTaggingLimitExceptions(tFacade.getTaggingLimitExceptions(tagging));
				}
			}
			else
				taggings = new ArrayList<>();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public TaggingForm getTaggingForm()
	{
		return model;
	}

	public void setTaggingForm(TaggingForm taggingForm)
	{
		this.model = taggingForm;
	}

	public Tagging getTagging()
	{
		return tagging;
	}

	public void setTagging(Tagging tagging)
	{
		this.tagging = tagging;
	}

	public TaggingRecord getTaggingRecord()
	{
		return taggingRecord;
	}

	public void setTaggingRecord(TaggingRecord taggingRecord)
	{
		this.taggingRecord = taggingRecord;
	}

	public TreeLimit getTreeLimit()
	{
		return treeLimit;
	}

	public void setTreeLimit(TreeLimit treeLimit)
	{
		this.treeLimit = treeLimit;
	}

	public ArrayList<TaggingForm> getTaggingForms()
	{
		return models;
	}

	public void setTaggingForms(ArrayList<TaggingForm> taggingForms)
	{
		this.models = taggingForms;
	}

	public ArrayList<Tagging> getTaggings()
	{
		return taggings;
	}

	public void setTaggings(ArrayList<Tagging> taggings)
	{
		this.taggings = taggings;
	}

	public ArrayList<TaggingType> getTaggingTypes()
	{
		return taggingTypes;
	}

	public void setTaggingTypes(ArrayList<TaggingType> taggingTypes)
	{
		this.taggingTypes = taggingTypes;
	}

	public ArrayList<Species> getSpeciesList()
	{
		return speciesList;
	}

	public void setSpeciesList(ArrayList<Species> speciesList)
	{
		this.speciesList = speciesList;
	}

	public ArrayList<ProtectedSpecies> getProtectedSpeciesList()
	{
		return protectedSpeciesList;
	}

	public void setProtectedSpeciesList(ArrayList<ProtectedSpecies> protectedSpeciesList)
	{
		this.protectedSpeciesList = protectedSpeciesList;
	}

	public ArrayList<TreeType> getTreeTypes()
	{
		return treeTypes;
	}

	public void setTreeTypes(ArrayList<TreeType> treeTypes)
	{
		this.treeTypes = treeTypes;
	}

	public ArrayList<LogQuality> getLogQualities()
	{
		return logQualities;
	}

	public void setLogQualities(ArrayList<LogQuality> logQualities)
	{
		this.logQualities = logQualities;
	}

	public ArrayList<SelectItem> getBlockList()
	{
		return blockList;
	}

	public void setBlockList(ArrayList<SelectItem> blockList)
	{
		this.blockList = blockList;
	}

	public String[] getSelectedBlocks()
	{
		return selectedBlocks;
	}

	public void setSelectedBlocks(String[] selectedBlocks)
	{
		this.selectedBlocks = selectedBlocks;
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

	public boolean isAddTaggingRecordOperation()
	{
		return addTaggingRecordOperation;
	}

	public void setAddTaggingRecordOperation(boolean addTaggingRecordOperation)
	{
		this.addTaggingRecordOperation = addTaggingRecordOperation;
	}

	public boolean isAllowedClass()
	{
		return allowedClass;
	}

	public void setAllowedClass(boolean allowedClass)
	{
		this.allowedClass = allowedClass;
	}

	public boolean isAllowedLog()
	{
		return allowedLog;
	}

	public void setAllowedLog(boolean allowedLog)
	{
		this.allowedLog = allowedLog;
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

	public double getCurrentVolume()
	{
		return currentVolume;
	}

	public void setCurrentVolume(double currentVolume)
	{
		this.currentVolume = currentVolume;
	}

	public double getGrossVolumeLimit()
	{
		return grossVolumeLimit;
	}

	public void setGrossVolumeLimit(double grossVolumeLimit)
	{
		this.grossVolumeLimit = grossVolumeLimit;
	}

	public double getNetVolumeLimit()
	{
		return netVolumeLimit;
	}

	public void setNetVolumeLimit(double netVolumeLimit)
	{
		this.netVolumeLimit = netVolumeLimit;
	}

	public double getMinDiameter()
	{
		return minDiameter;
	}

	public void setMinDiameter(double minDiameter)
	{
		this.minDiameter = minDiameter;
	}

	public long getSelectedTaggingID()
	{
		return selectedTaggingID;
	}

	public void setSelectedTaggingID(long selectedTaggingID)
	{
		this.selectedTaggingID = selectedTaggingID;
	}

	public int getAccessLevel()
	{
		return accessLevel;
	}

	public String getComponent()
	{
		return ":frmManager:table" + (model == null ? "" : ":" + models.indexOf(model) + ":subtable");
	}

	public void handleTaggingIDChange()
	{
		clearFilter();

		try (MaintenanceFacade mFacade = new MaintenanceFacade(); PreFellingFacade pFacade = new PreFellingFacade(); TaggingFacade tFacade = new TaggingFacade();)
		{
			AbstractFacade.group(mFacade, pFacade, tFacade);

			tagging = null;
			validated = false;
			downloaded = false;
			selectedBlocks = null;
			recommendation = null;
			percentage = null;
			blockList = null;
			map = null;

			for (Tagging t : taggings)
				if (t.getTaggingID() == selectedTaggingID)
					tagging = t;

			models = tFacade.getTaggingForms(tagging);
			Staff user = getCurrentUser();
			State state = mFacade.getState(tagging.getStateID());
			String staffID = user.getStaffID();

			if (accessLevel % 6 == 0)
			{
				protectedSpeciesList = mFacade.getProtectedSpeciesList(state);
				treeLimit = mFacade.getTreeLimit(state);
			}

			if (staffID.equals(state.getDirectorID()))
			{
				if (tagging.getTenderNo() == null)
				{
					uptype = "cfoo";
					ArrayList<TaggingForm> temp = new ArrayList<>();

					for (TaggingForm taggingForm : models)
						if (taggingForm.getInspectorID() == null)
							temp.add(taggingForm);

					models.removeAll(temp);
				}
				else
					uptype = "cfoc";
			}

			SimpleDateFormat sdf = new SimpleDateFormat("d MMMM yyyy", new Locale("ms"));
			int totalValidated = 0, totalDownloaded = 0, count = models.size();

			currentVolume = 0;
			grossVolumeLimit = tagging.getGrossVolumeLimit() * tagging.getArea();
			netVolumeLimit = tagging.getNetVolumeLimit() * tagging.getArea();

			sort(models);
			tagging.setTaggingForms(models);
			tagging.setCuttingLimits(pFacade.getCuttingLimits(tagging));

			for (TaggingForm taggingForm : models)
			{
				ArrayList<TaggingRecord> taggingRecords = taggingForm.getTaggingRecords();
				sort(taggingRecords);

				if (taggingForm.getInspectorID() != null)
					totalValidated++;

				if (taggingForm.getInspectionDate() != null)
					totalDownloaded++;

				for (TaggingRecord taggingRecord : taggingRecords)
				{
					int diameter = (int) Math.round(taggingRecord.getDiameter());
					String speciesID = taggingRecord.getSpeciesID() + "-";
					Double volume = null;

					while (volume == null)
					{
						volume = volumeMap.get(speciesID + diameter--);

						if (volume != null)
							taggingRecord.setVolume(volume);
					}
				}

				if (taggingForm.getTaggingTypeID() == 1)
					for (TaggingRecord taggingRecord : taggingRecords)
						if (taggingRecord.getTreeTypeID() == 1)
							currentVolume += taggingRecord.getVolume();
			}

			if (tagging.getStartDate() != null && tagging.getEndDate() != null)
				workingDates = sdf.format(tagging.getStartDate()) + " sehingga " + sdf.format(tagging.getEndDate());
			else
				workingDates = "Belum ditentukan";

			validated = totalValidated == count;

			if (totalDownloaded == count)
			{
				District district = mFacade.getDistrict(tagging.getDistrictID());
				downloaded = user.getStaffID().equals(district.getOfficerID());
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public void handleSpeciesIDChange()
	{
		boolean contains = false;

		for (ProtectedSpecies protectedSpecies : protectedSpeciesList)
			if (protectedSpecies.getSpeciesID() == taggingRecord.getSpeciesID())
				contains = true;

		allowedLog = !contains;

		if (model.getTaggingTypeID() == 1)
		{
			if (allowedLog)
			{
				allowedClass = true;
				allowedLog = false;

				taggingRecord.setTreeTypeID(0);
			}
			else
			{
				allowedClass = false;

				taggingRecord.setTreeTypeID(3);
				setMinDiameter();
			}
		}
		else
		{
			allowedClass = true;
			setMinDiameter();
		}
	}

	public void handleTreeTypeIDChange()
	{
		int treeTypeID = taggingRecord.getTreeTypeID();

		if (treeTypeID == 1)
		{
			allowedLog = true;
			setMinDiameter();
		}
		else if (treeTypeID == 2)
		{
			allowedLog = false;
			minDiameter = treeLimit.getMotherLimit();
		}
		else if (treeTypeID == 3)
		{
			allowedLog = false;
			setMinDiameter();
		}
	}

	@Override
	public void handleOpen()
	{
		if (addOperation)
		{
			model = new TaggingForm();
			Date date = new Date();
			Staff user = getCurrentUser();

			model.setTaggingFormID(date.getTime());
			model.setTaggingDate(date);
			model.setRecorderID(user.getStaffID());
			model.setRecorderName(user.getName());
			model.setTaggingID(tagging.getTaggingID());
			model.setTaggingRecords(new ArrayList<>());
		}
		else
			model = (TaggingForm) copy(models, model);
	}

	public void handleOpenTaggingRecord()
	{
		if (addTaggingRecordOperation)
		{
			taggingRecord = new TaggingRecord();

			taggingRecord.setTaggingRecordID(System.currentTimeMillis());
			taggingRecord.setTaggingFormID(model.getTaggingFormID());
		}
		else
		{
			ArrayList<TaggingRecord> taggingRecords = model.getTaggingRecords();
			taggingRecord = (TaggingRecord) copy(taggingRecords, taggingRecord);
			boolean contains = false;

			for (ProtectedSpecies protectedSpecies : protectedSpeciesList)
				if (protectedSpecies.getSpeciesID() == taggingRecord.getSpeciesID())
					contains = true;

			allowedLog = !contains;
			allowedClass = !contains;

			if (allowedLog && model.getTaggingTypeID() == 1 && taggingRecord.getTreeTypeID() != 1)
				allowedLog = false;
		}
	}

	public void taggingFormEntry()
	{
		try (TaggingFacade facade = new TaggingFacade())
		{
			for (TaggingType taggingType : taggingTypes)
				if (model.getTaggingTypeID() == taggingType.getTaggingTypeID())
					model.setTaggingTypeName(taggingType.getName());

			finalizeModelEntry(addOperation ? facade.addTaggingForm(model, true) : facade.updateTaggingForm(model), addOperation, facade, "borang penandaan, ID " + model.getTaggingFormID(), ", kerana no. " + (model.getTaggingTypeID() == 1 ? "blok penandaan" : model.getTaggingTypeName().toLowerCase()) + " telah direkodkan sebelumnya.", models, model);
			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popup').hide()");
	}

	public void taggingRecordEntry()
	{
		boolean valid = true;

		try (TaggingFacade facade = new TaggingFacade())
		{
			currentVolume = 0;
			int diameter = (int) Math.round(taggingRecord.getDiameter()), limit = 0;
			String speciesID = taggingRecord.getSpeciesID() + "-";
			Double volume = null;

			while (volume == null)
			{
				volume = volumeMap.get(speciesID + diameter--);

				if (volume != null)
					taggingRecord.setVolume(volume);
			}

			for (TaggingForm taggingForm : models)
			{
				if (taggingForm.getTaggingTypeID() == 1)
				{
					ArrayList<TaggingRecord> taggingRecords = taggingForm.getTaggingRecords();

					for (TaggingRecord taggingRecord : taggingRecords)
						if (taggingRecord.getTreeTypeID() == 1 && !taggingRecord.equals(this.taggingRecord))
							currentVolume += taggingRecord.getVolume();
				}
			}

			for (Species species : speciesList)
			{
				if (species.getSpeciesID() == taggingRecord.getSpeciesID())
				{
					taggingRecord.setSpeciesCode(species.getCode());
					taggingRecord.setSpeciesName(species.getName());
					taggingRecord.setSpeciesTypeID(species.getSpeciesTypeID());
				}
			}

			for (TreeType treeType : treeTypes)
				if (treeType.getTreeTypeID() == taggingRecord.getTreeTypeID())
					taggingRecord.setTreeTypeName(treeType.getName());

			for (LogQuality logQuality : logQualities)
			{
				if (logQuality.getLogQualityID() == taggingRecord.getLogQualityID())
				{
					taggingRecord.setLogQuality(logQuality.getCode());
					taggingRecord.setLogQualityName(logQuality.getName());
				}
			}

			if (model.getTaggingTypeID() == 1 && taggingRecord.getTreeTypeID() == 1)
			{
				int size = 0;
				ArrayList<TaggingRecord> taggingRecords = model.getTaggingRecords();
				ArrayList<TaggingLimitException> taggingLimitExceptions = tagging.getTaggingLimitExceptions();
				limit = treeLimit.getMaximumPerPlot();

				for (TaggingLimitException taggingLimitException : taggingLimitExceptions)
					if (taggingLimitException.getBlockNo().equals(model.getFormNo()) && taggingLimitException.getPlotNo().equals(taggingRecord.getPlotNo()))
						limit = taggingLimitException.getQuantity();

				for (TaggingRecord taggingRecord : taggingRecords)
					if (taggingRecord.getTreeTypeID() == 1 && this.taggingRecord.getPlotNo().equals(taggingRecord.getPlotNo()) && !taggingRecord.equals(this.taggingRecord))
						size++;

				if (size + 1 > limit && limit != 0 || currentVolume + taggingRecord.getVolume() > grossVolumeLimit)
					valid = false;
			}

			if (valid)
			{
				String value = finalizeModelEntry(addTaggingRecordOperation ? facade.addTaggingRecord(taggingRecord, true) : facade.updateTaggingRecord(taggingRecord), addTaggingRecordOperation, facade, "rekod penandaan, ID " + taggingRecord.getTaggingRecordID(), model.getTaggingRecords(), taggingRecord);

				if (value != null)
				{
					addMessage(FacesMessage.SEVERITY_INFO, null, value);
					currentVolume += taggingRecord.getVolume();
				}
				else
				{
					String message = null;
					TaggingRecord suspect = facade.getTaggingRecord(taggingRecord.getSerialNo());
					
					if (suspect != null)
					{
						TaggingForm tf = facade.getTaggingForm(suspect.getTaggingFormID());
						Tagging t = facade.getTagging(tf.getTaggingID());
						message = ", kerana rekod penandaan telah direkodkan sebelumnya di sesi penandaan " + t.toString() + " " + tf.toString().toLowerCase() + " no. petak " + suspect.getPlotNo();
					}
					
					addMessage(FacesMessage.SEVERITY_WARN, null, taggingRecord + " tidak dapat " + (addTaggingRecordOperation ? "ditambahkan" : "dikemaskini") + (message == null ? "" : message) + ".");
				}
				
				if (addTaggingRecordOperation)
					handleOpenTaggingRecord();
				else
					taggingRecord = null;
			}
			else
				addMessage(FacesMessage.SEVERITY_WARN, null, taggingRecord + " tidak dapat direkodkan kerana " + (limit == 0 ? "" : "jumlah pokok tebangan di petak " + taggingRecord.getPlotNo() + " telah melebihi had " + limit + " pokok/petak atau ") + "jumlah isipadu telah melebihi had " + grossVolumeLimit + " m\u00B3.");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		if (valid && !addTaggingRecordOperation)
			execute("PF('popupTaggingRecord').hide()");
	}

	public void taggingRecordDelete()
	{
		try (TaggingFacade facade = new TaggingFacade())
		{
			if (facade.deleteTaggingRecord(taggingRecord) != 0)
			{
				addMessage(FacesMessage.SEVERITY_INFO, null, taggingRecord + " berjaya dipadamkan.");
				model.getTaggingRecords().remove(taggingRecord);
				log(facade, "Padam rekod penandaan, ID " + taggingRecord.getTaggingRecordID());

				currentVolume = 0;

				for (TaggingForm taggingForm : models)
				{
					if (taggingForm.getTaggingTypeID() == 1)
					{
						ArrayList<TaggingRecord> taggingRecords = taggingForm.getTaggingRecords();

						for (TaggingRecord taggingRecord : taggingRecords)
							if (taggingRecord.getTreeTypeID() == 1)
								currentVolume += taggingRecord.getVolume();
					}
				}
			}
			else
				addMessage(FacesMessage.SEVERITY_WARN, null, taggingRecord + " tidak dapat dipadamkan.");

			taggingRecord = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public void validate()
	{
		try (MaintenanceFacade mFacade = new MaintenanceFacade(); TaggingFacade tFacade = new TaggingFacade();)
		{
			AbstractFacade.group(mFacade, tFacade);

			int total = 0, count = 0;
			Staff user = getCurrentUser();

			for (TaggingForm taggingForm : models)
			{
				if (taggingForm.getInspectorID() == null && taggingForm.getInspectionDate() != null)
				{
					count++;

					taggingForm.setInspectorID(user.getStaffID());
					taggingForm.setInspectorName(user.getName());

					if (tFacade.updateTaggingForm(taggingForm) != 0)
						total++;
				}
			}

			validated = total == count;
			selectedBlocks = null;
			recommendation = null;
			percentage = null;
			blockList = null;
			map = null;

			addMessage(FacesMessage.SEVERITY_INFO, null, (total == 0 ? "Tiada" : total) + " borang penandaan berjaya disahkan.");

			if (total != 0)
				new EmailSender().send(true, "Borang Penandaan - " + tagging.getForestName() + " " + tagging.getComptBlockNo(), "Borang penandaan telah disahkan oleh " + user.getName() + " untuk:<br/><br/><table border='0'><tr><td>- Hutan simpan</td><td>:</td><td>" + tagging.getForestName() + "</td></tr><tr><td>- No. kompartmen/sub kompartmen</td><td>:</td><td>" + tagging.getComptBlockNo() + "</td></tr></table><br/>Sila log masuk ke FIS9 untuk tindakan anda seterusnya.", mFacade.getState(user.getStateID()).getDirectorID());
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
		catch (IOException | MessagingException e)
		{
			e.printStackTrace();
			addMessage(FacesMessage.SEVERITY_WARN, null, "Sistem tidak berjaya menghantar emel notifikasi.");
		}
	}

	public void prepareDownload()
	{
		blockList = new ArrayList<>();
		map = new LinkedHashMap<>();

		for (TaggingForm taggingForm : models)
		{
			if (taggingForm.getInspectorID() == null && taggingForm.getTaggingTypeID() == 1)
			{
				String blockNo = taggingForm.getFormNo();
				ArrayList<TaggingForm> blockForm = map.get(blockNo);

				if (blockForm == null)
				{
					blockForm = new ArrayList<>();
					map.put(blockNo, blockForm);
				}

				blockForm.add(taggingForm);
			}
		}

		ArrayList<String> lines = new ArrayList<>(map.keySet());
		int size = lines.size(), min = (int) Math.ceil(size * 0.025);
		recommendation = "Terdapat " + size + " no. blok yang belum disahkan, anda dinasihatkan untuk memilih sekurang-kurangnya " + min + " no. blok untuk disemak.";

		selectBlock(null);
		Collections.sort(lines);

		for (String line : lines)
			blockList.add(new SelectItem(line, "No. blok " + line + " (mengandungi " + map.get(line).size() + " no. petak yang belum disemak)"));
	}

	public void prepareUpload()
	{
		totalFiles = new Integer(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("totalFiles"));
	}

	public void selectBlock(AjaxBehaviorEvent event)
	{
		percentage = "Jumlah borang dipilih: " + (selectedBlocks == null || selectedBlocks.length == 0 ? 100 : Math.round(selectedBlocks.length * 100d / map.size())) + "%";
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

		String name = "BorangPenandaan_" + tagging.getForestName().replaceAll(" ", "") + "_" + tagging.getComptBlockNo() + "_" + tagging.getYear() + "_" + host + "." + (pdf ? "pdf" : downtype), type = null;
		File file = new File(external.getRealPath("/") + "files/tagging/" + name);
		StreamedContent content = null;

		file.getParentFile().mkdirs();

		try
		{
			if (pdf)
			{
				try (MaintenanceFacade mFacade = new MaintenanceFacade(); TaggingFacade tFacade = new TaggingFacade();)
				{
					State state = new State();
					ArrayList<TaggingForm> temp = null;

					AbstractFacade.group(mFacade, tFacade);
					state.setStateID(tagging.getStateID());

					if (blockList != null)
					{
						temp = tagging.getTaggingForms();
						ArrayList<TaggingForm> selected = new ArrayList<>();

						if (selectedBlocks != null && selectedBlocks.length != 0)
						{
							for (String selectedLine : selectedBlocks)
								selected.addAll(this.map.get(selectedLine));
						}
						else
						{
							ArrayList<String> lines = new ArrayList<>(this.map.keySet());

							for (String line : lines)
								selected.addAll(this.map.get(line));
						}

						for (TaggingForm taggingForm : temp)
							if (taggingForm.getTaggingTypeID() != 1 && taggingForm.getInspectorID() == null)
								selected.add(taggingForm);

						tagging.setTaggingForms(selected);
					}

					TaggingFormGenerator.generate(file, tagging, treeLimit);

					if (blockList != null)
					{
						tagging.setTaggingForms(temp);

						Date now = new Date();
						District district = mFacade.getDistrict(tagging.getDistrictID());
						downloaded = user.getStaffID().equals(district.getOfficerID());

						for (TaggingForm taggingForm : temp)
						{
							taggingForm.setInspectionDate(now);
							tFacade.updateTaggingForm(taggingForm);
						}
					}
				}

				type = "application/pdf";
			}
			else
			{
				type = "application/octet-stream";
				ObjectOutputStream oos = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(file)));

				oos.writeInt(32);
				oos.writeObject(tagging);

				if (downtype.equals("cfof") || downtype.equals("cfoc"))
				{
					oos.writeObject(null);
					oos.writeObject(null);
				}
				else if (downtype.equals("cfor"))
				{
					oos.writeObject(user);
					oos.writeObject(null);
				}
				else if (downtype.equals("cfoo"))
				{
					oos.writeObject(null);
					oos.writeObject(user);
				}

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

	public StreamedContent download()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		String name = "LaporanPenandaan_" + tagging.getForestName().replaceAll(" ", "") + "_" + tagging.getComptBlockNo() + "_" + tagging.getYear() + ".pdf";
		File file = new File(external.getRealPath("/") + "files/tagging/" + name);
		StreamedContent content = null;

		file.getParentFile().mkdirs();

		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			State state = new State();

			state.setStateID(tagging.getStateID());
			TaggingReportGenerator.generate(file, tagging, treeLimit, facade.getMainRevenueRoyaltyRates(state, "A"));

			content = new DefaultStreamedContent(new FileInputStream(file), "application/pdf", name);
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
			try (ObjectInputStream ois = new ObjectInputStream(new GZIPInputStream(file.getInputstream())); TaggingFacade facade = new TaggingFacade();)
			{
				if (ois.readInt() != 32)
					throw new InvalidClassException("Not TaggingForm");

				Tagging tagging = (Tagging) ois.readObject();
				Staff adfor = (Staff) ois.readObject();
				Staff dfo = (Staff) ois.readObject();

				if (tagging.equals(this.tagging))
				{
					String filename = file.getFileName().toLowerCase();
					ArrayList<TaggingForm> currentTaggingForms = this.tagging.getTaggingForms();
					ArrayList<TaggingForm> taggingForms = tagging.getTaggingForms();
					currentVolume = 0;

					if (filename.endsWith("cfof") || filename.endsWith("cfoc"))
					{
						if (adfor == null && dfo == null)
						{
							facade.updateTagging(tagging);
							save(facade, currentTaggingForms, taggingForms, false);
							tagging.setTaggingForms(currentTaggingForms);
							taggings.set(taggings.indexOf(this.tagging), tagging);

							this.tagging = tagging;
							models = currentTaggingForms;
						}
						else
							addMessage(FacesMessage.SEVERITY_WARN, null,
									tagging + " tidak dapat ditambahkan kerana sesi penandaan yang dimuat naik tidak sah.");
					}
					else if (filename.endsWith("cfor"))
					{
						if (adfor != null && dfo == null)
						{
							facade.updateTagging(tagging);
							save(facade, currentTaggingForms, taggingForms, false);
							tagging.setTaggingForms(currentTaggingForms);
							taggings.set(taggings.indexOf(this.tagging), tagging);

							this.tagging = tagging;
							models = currentTaggingForms;
							validated = false;
							downloaded = false;
						}
						else
							addMessage(FacesMessage.SEVERITY_WARN, null,
									tagging + " tidak dapat ditambahkan kerana sesi penandaan yang dimuat naik tidak sah.");
					}
					else if (filename.endsWith("cfoo"))
					{
						if (adfor == null && dfo != null)
						{
							facade.updateTagging(tagging);
							save(facade, currentTaggingForms, taggingForms, true);
							tagging.setTaggingForms(currentTaggingForms);
							taggings.set(taggings.indexOf(this.tagging), tagging);

							this.tagging = tagging;
							models = currentTaggingForms;
						}
						else
							addMessage(FacesMessage.SEVERITY_WARN, null,
									tagging + " tidak dapat ditambahkan kerana sesi penandaan yang dimuat naik tidak sah.");
					}

					for (TaggingForm taggingForm : models)
					{
						if (taggingForm.getTaggingTypeID() == 1)
						{
							ArrayList<TaggingRecord> taggingRecords = taggingForm.getTaggingRecords();

							for (TaggingRecord taggingRecord : taggingRecords)
								if (taggingRecord.getTreeTypeID() == 1)
									currentVolume += taggingRecord.getVolume();
						}
					}
				}
				else
					addMessage(FacesMessage.SEVERITY_WARN, null,
							tagging + " tidak dapat ditambahkan kerana maklumat sesi penandaan yang dimuat naik tidak sama dengan sesi penandaan yang dipilih.");
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

	private void setMinDiameter()
	{
		ArrayList<CuttingLimit> cuttingLimits = tagging.getCuttingLimits();
		Species species = null;

		for (Species t : speciesList)
			if (t.getSpeciesID() == taggingRecord.getSpeciesID())
				species = t;

		if (species.getSpeciesTypeID() == 1 || species.getSpeciesTypeID() == 2)
			minDiameter = tagging.getDipterocarpLimit();
		else
		{
			if ("1201".equals(species.getCode()))
				minDiameter = treeLimit.getChengalLimit();
			else
			{
				minDiameter = -1;

				for (CuttingLimit cuttingLimit : cuttingLimits)
					if (cuttingLimit.getSpeciesID() == species.getSpeciesID())
						minDiameter = cuttingLimit.getMinDiameter();

				if (minDiameter == -1)
					minDiameter = tagging.getNonDipterocarpLimit();
			}
		}
	}

	private void save(TaggingFacade facade, ArrayList<TaggingForm> currentTaggingForms, ArrayList<TaggingForm> taggingForms, boolean strict) throws SQLException
	{
		int totalTaggingForm = 0, totalRecord = 0;
		/*LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>();

		// New mapping
		map.put(472, 263);
		map.put(473, 472);
		map.put(474, 473);
		map.put(475, 474);
		map.put(476, 475);
		map.put(477, 476);
		map.put(478, 477);
		map.put(479, 478);
		map.put(480, 479);
		map.put(481, 480);
		map.put(482, 481);
		map.put(483, 482);
		map.put(484, 483);
		map.put(485, 484);
		map.put(486, 485);
		map.put(487, 283);
		map.put(502, 359);
		map.put(505, 486);
		map.put(506, 502);*/

		for (TaggingForm taggingForm : taggingForms)
		{
			if (strict && taggingForm.getInspectorID() == null)
				continue;

			ArrayList<TaggingRecord> currentTaggingRecords = null;
			ArrayList<TaggingRecord> taggingRecords = taggingForm.getTaggingRecords();

			if (facade.addTaggingForm(taggingForm, false) != 0)
			{
				totalTaggingForm++;
				int index = currentTaggingForms.indexOf(taggingForm);

				if (index == -1)
				{
					currentTaggingRecords = new ArrayList<>();

					currentTaggingForms.add(taggingForm);
					log(facade, "Tambah borang penandaan, ID " + taggingForm.getTaggingFormID());
				}
				else
				{
					TaggingForm temp = currentTaggingForms.get(index);
					currentTaggingRecords = temp.getTaggingRecords();

					log(facade, "Kemaskini borang penandaan, ID " + taggingForm.getTaggingFormID());
					taggingForm.setTaggingFormID(temp.getTaggingFormID());
					currentTaggingForms.set(index, taggingForm);
				}

				for (TaggingRecord taggingRecord : taggingRecords)
				{
					/*Integer nID = map.get(taggingRecord.getSpeciesID());

					if (nID != null)
						taggingRecord.setSpeciesID(nID);*/

					taggingRecord.setTaggingFormID(taggingForm.getTaggingFormID());

					if (facade.addTaggingRecord(taggingRecord, false) != 0)
					{
						totalRecord++;
						index = currentTaggingRecords.indexOf(taggingRecord);

						log(facade, "Tambah rekod penandaan, ID " + taggingRecord.getTaggingRecordID());

						if (index == -1)
							currentTaggingRecords.add(taggingRecord);
						else
							currentTaggingRecords.set(index, taggingRecord);
					}
				}

				sort(currentTaggingRecords);
				taggingForm.setTaggingRecords(currentTaggingRecords);
			}
		}

		sort(currentTaggingForms);

		if (totalTaggingForm != 0 || totalRecord != 0)
			addMessage(FacesMessage.SEVERITY_INFO, null,
					totalTaggingForm + " borang penandaan dan " + totalRecord + " rekod penandaan berjaya ditambahkan.");
		else
			addMessage(FacesMessage.SEVERITY_INFO, null,
					"Tiada borang penandaan dan rekod penandaan berjaya ditambahkan.");
	}
}