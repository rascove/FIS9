package my.edu.utem.ftmk.fis9.hall.controller.mbean;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.controller.manager.AbstractFacade;
import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.hall.controller.manager.HallFacade;
import my.edu.utem.ftmk.fis9.hall.model.Log;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.District;
import my.edu.utem.ftmk.fis9.maintenance.model.HallOfficer;
import my.edu.utem.ftmk.fis9.maintenance.model.LogSize;
import my.edu.utem.ftmk.fis9.maintenance.model.Species;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.tagging.controller.manager.TaggingFacade;
import my.edu.utem.ftmk.fis9.tagging.model.Tagging;
import my.edu.utem.ftmk.fis9.tagging.model.TaggingForm;
import my.edu.utem.ftmk.fis9.tagging.model.TaggingRecord;

/**
 * @author Nor Azman Mat Ariff
 */
@ViewScoped
@ManagedBean(name = "logManagedBean")
public class LogManagedBean extends AbstractManagedBean<TaggingRecord> {
	private static final long serialVersionUID = VERSION;
	private Tagging tagging;
	private TaggingRecord taggingRecord;
	private Log log;
	private LogSize logSize;
	private District district;
	private TaggingRecord[] traditionalTaggingRecords;
	private ArrayList<Tagging> taggings;
	private ArrayList<TaggingForm> taggingForms;
	private ArrayList<Species> speciesList;
	private ArrayList<Log> logs;
	private ArrayList<LogSize> logSizes;
	private LinkedHashMap<String, ArrayList<TaggingRecord>> map;
	private boolean addTaggingRecordOperation;
	private boolean validateLog;
	private boolean logSizeExist;
	private long selectedTaggingID;
	private BigDecimal minDiameter;
	private BigDecimal maxDiameter;
	private int stateID;
	private int accessLevel;
	private String errorMessage = null;

	public LogManagedBean() {
		try (MaintenanceFacade mFacade = new MaintenanceFacade(); TaggingFacade tFacade = new TaggingFacade();) {
			AbstractFacade.group(mFacade, tFacade);

			Staff user = getCurrentUser();
			String staffID = user.getStaffID();
			int designationID = user.getDesignationID();
			stateID = user.getStateID();
			int endYear = tFacade.getTaggingYearRange()[1];
			Calendar calToday = Calendar.getInstance();
			Date today = resetCalendar(calToday, 0, 0);

			logSizeExist = false;
			logSizes = mFacade.getLogSizes("A");
			System.out.println("endYear=" + endYear);
			if (logSizes.isEmpty()) 
			{
				errorMessage = "Modul Tual Balak tidak boleh digunakan kerana maklumat saiz balak tidak direkodkan.";
			}
			else
			{
				speciesList = mFacade.getSpeciesList();
				sort(speciesList);

				if (stateID == 0) 
				{
					taggings = tFacade.getTaggings(true, 0, endYear);
					if(taggings.isEmpty())
					{
						errorMessage = "Modul Tual Balak tidak boleh digunakan kerana tiada sesi penandaan untuk diproses.";
					}
					else
					{
						accessLevel = 1;
						logSizeExist = true;
					}
					System.out.println("stateID == 0");
				} 
				else 
				{
					System.out.println("stateID != 0");
					State state = mFacade.getState(stateID);
					for (LogSize ls : logSizes) 
					{
						if (ls.getStateID() == state.getStateID()) 
						{
							logSize = ls;
							logSizeExist = true;
						}
					}

					if (logSizeExist == false)
					{
						errorMessage = "Modul Tual Balak tidak boleh digunakan kerana maklumat saiz balak bagi " + state.getName() + " tidak direkodkan.";
					}
					else
					{
						System.out.println("stateID != 0 && logSizeExist = true");
						if (state.getDirectorID().equals(staffID) || (state.getDeputyDirector1ID() != null && state.getDeputyDirector1ID().equals(staffID)) || (state.getDeputyDirector2ID() != null && state.getDeputyDirector2ID().equals(staffID)) || designationID == 6 || designationID == 12) 
						{
							System.out.println("state.getDirectorID()");
							System.out.println("test " + staffID);
							System.out.println(state.getDirectorID().equals(staffID));
							System.out.println(state.getDeputyDirector1ID());
							System.out.println(state.getDeputyDirector1ID().equals(staffID));
							System.out.println(state.getDeputyDirector2ID());
							System.out.println(state.getDeputyDirector2ID().equals(staffID));
							System.out.println(designationID);
							taggings = tFacade.getTaggings(state, true, 0, endYear);
							if(taggings.isEmpty())
							{
								errorMessage = "Modul Tual Balak tidak boleh digunakan kerana tiada sesi penandaan untuk diproses.";
							}
							else
							{
								accessLevel = 1;
							}
						} 
						else 
						{
							System.out.println("!state.getDirectorID()");
							ArrayList<HallOfficer> hallOfficers = mFacade.getHallOfficers(today, user);
							System.out.println(hallOfficers.size());
							if(hallOfficers.isEmpty())
							{
								district = mFacade.getDistrict(user);
								if (district != null)
								{
									System.out.println("district != null");
									if (district.getOfficerID().equals(staffID) || (district.getAsstOfficerID() != null && district.getAsstOfficerID().equals(staffID))) 
									{
										
										taggings = tFacade.getTaggings(district, true, 0, endYear);
										if(taggings.isEmpty())
										{
											
											errorMessage = "Modul Tual Balak tidak boleh digunakan kerana tiada sesi penandaan untuk diproses.";
											System.out.println(taggings + " " + errorMessage);
										}
										else
										{
											accessLevel = 1;
											System.out.println(taggings + " " + errorMessage + " " + accessLevel);

										}
									} 
									else
									{
										accessLevel = 0;
										errorMessage = "Anda tidak dibenarkan menggunakan Modul Tual Balak.";
									}
								} 
								else 
								{
									System.out.println("district = null");
									accessLevel = 0;
									if(mFacade.getHallOfficers(user) != null)
									{
										errorMessage = "Lantikan sebagai Pegawai Balai telah tamat. Anda tidak dibenarkan menggunakan Modul Tual Balak.";
									}
									else
									{
										errorMessage = "Anda tidak dibenarkan menggunakan Modul Tual Balak.";
									}
//									errorMessage = "Anda tidak dibenarkan menggunakan Modul Tual Balak.";
								}
							}
							else
							{
								System.out.println("hallOfficers.size() != 0");
								accessLevel = 2;
								taggings = new ArrayList<Tagging>();
								ArrayList<Integer> districtIDs = new ArrayList<Integer>();
								ArrayList<District> districts = new ArrayList<District>();						
								for(HallOfficer hallOfficer : hallOfficers)
								{
									if(!districtIDs.contains(hallOfficer.getDistrictID()))
									{
										System.out.println("semua hallOfficer.getDistrictID()=" + hallOfficer.getDistrictID());
										System.out.println("hallOfficer.getDistrictID()=" + hallOfficer.getDistrictID());
										districtIDs.add(hallOfficer.getDistrictID());
										District district = new District();
										district.setDistrictID(hallOfficer.getDistrictID());
										districts.add(district);
									}
								}

								for(District district : districts)
								{
									ArrayList<Tagging> tempTaggings = tFacade.getTaggings(district, true, 0, endYear);
									for(Tagging tagging : tempTaggings)
									{
										taggings.add(tagging);
									}
								}	
								districtIDs = null;
								districts = null;
							}
						}
					}
				}
			}
			System.out.println(accessLevel);

			if (taggings != null) {
				sort(taggings);
			} else
				taggings = new ArrayList<>();
		} catch (SQLException e) {
			e.printStackTrace();
			addMessage(e);
		}
	}

	public TaggingRecord getTaggingRecord() {
		return model;
	}

	public void setTaggingRecord(TaggingRecord taggingRecord) {
		this.model = taggingRecord;
	}

	public Tagging getTagging() {
		return tagging;
	}

	public void setTagging(Tagging tagging) {
		this.tagging = tagging;
	}

	public TaggingRecord[] getTraditionalTaggingRecords() {
		return traditionalTaggingRecords;
	}

	public void setTraditionalTaggingRecords(TaggingRecord[] traditionalTaggingRecords) {
		this.traditionalTaggingRecords = traditionalTaggingRecords;
	}

	public ArrayList<TaggingRecord> getTaggingRecords() {
		return models;
	}

	public void setTaggingRecords(ArrayList<TaggingRecord> taggingRecords) {
		this.models = taggingRecords;
	}

	public ArrayList<Tagging> getTaggings() {
		return taggings;
	}

	public void setTaggings(ArrayList<Tagging> taggings) {
		this.taggings = taggings;
	}

	public ArrayList<Species> getSpeciesList() {
		return speciesList;
	}

	public void setSpeciesList(ArrayList<Species> speciesList) {
		this.speciesList = speciesList;
	}

	public boolean isAddTaggingRecordOperation() {
		return addTaggingRecordOperation;
	}

	public void setAddTaggingRecordOperation(boolean addTaggingRecordOperation) {
		this.addTaggingRecordOperation = addTaggingRecordOperation;
	}

	public long getSelectedTaggingID() {
		return selectedTaggingID;
	}

	public void setSelectedTaggingID(long selectedTaggingID) {
		this.selectedTaggingID = selectedTaggingID;
	}

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public ArrayList<TaggingForm> getTaggingForms() {
		return taggingForms;
	}

	public void setTaggingForms(ArrayList<TaggingForm> taggingForms) {
		this.taggingForms = taggingForms;
	}

	public LinkedHashMap<String, ArrayList<TaggingRecord>> getMap() {
		return map;
	}

	public void setMap(LinkedHashMap<String, ArrayList<TaggingRecord>> map) {
		this.map = map;
	}

	public boolean isValidateLog() {
		return validateLog;
	}

	public void setValidateLog(boolean validateLog) {
		this.validateLog = validateLog;
	}

	public BigDecimal getMinDiameter() {
		return minDiameter;
	}

	public void setMinDiameter(BigDecimal minDiameter) {
		this.minDiameter = minDiameter;
	}

	public BigDecimal getMaxDiameter() {
		return maxDiameter;
	}

	public void setMaxDiameter(BigDecimal maxDiameter) {
		this.maxDiameter = maxDiameter;
	}

	public int getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public void handleOpen() {
		if (addOperation) {
			log = new Log();

			log.setLogID(System.currentTimeMillis());
			log.setTaggingRecordID(model.getTaggingRecordID());
		} else {
			validateLog = false;
			log = (Log) copy(model.getLogs(), log);

			minDiameter = BigDecimal.valueOf(logSize.getMinSmallSize());
			maxDiameter = new BigDecimal(model.getDiameter() + 5);
			for (Log log1 : model.getLogs()) {
				if (log.getLogNo() > log1.getLogNo() && !log1.getStatus().equalsIgnoreCase("O")) {
					if (maxDiameter.compareTo(log1.getDiameter()) == 1) {
						maxDiameter = log1.getDiameter();
					}
				}
				if (log.getLogNo() < log1.getLogNo() && !log1.getStatus().equalsIgnoreCase("O")) {
					if (minDiameter.compareTo(log1.getDiameter()) == -1) {
						minDiameter = log1.getDiameter();
					}
				}
			}
			if (log.getLogNo() == 1) {
				validateLog = true;
			}
		}
	}

	public void handleCreateLogs() {
		model.setLogs(new ArrayList<Log>());
		try (HallFacade hFacade = new HallFacade(); TaggingFacade tFacade = new TaggingFacade()) {
			int totalLogs = 0;
			if (model.getCorrectedEstimation() == 0) {
				totalLogs = model.getEstimation();
			} else {
				totalLogs = model.getCorrectedEstimation();
			}

			long logID = System.currentTimeMillis();
			for (int i = 0; i < totalLogs; i++) {
				Log log = new Log();
				log.setLogID(logID + i);
				log.setLogNo(i + 1);
				log.setLogSerialNo(model.getSerialNo() + "/" + (i + 1));
				log.setLength(BigDecimal.ZERO);
				log.setDiameter(BigDecimal.ZERO);
				log.setHoleDiameter(BigDecimal.ZERO);
				log.setTaggingRecordID(model.getTaggingRecordID());
				log.setStatus("O");
				finalizeModelEntry(hFacade.addLog(log, true), addOperation, hFacade, "tual balak, ID " + log.getLogID(),
						", kerana tual balak telah direkodkan sebelumnya", model.getLogs(), log);
			}
			model.setStatus("P");
			if (tFacade.updateTaggingRecordStatus(model) != 0) {
				log(tFacade, "Kemaskini rekod penandaan, ID " + model.getTaggingRecordID());
			}
			addTaggingRecordOperation = false;
		} catch (SQLException e) {
			e.printStackTrace();
			addMessage(e);
		}
		execute("PF('popupConfirmation').hide()");
	}

	public void handleCorrectedEstimation() {
		model = (TaggingRecord) copy(models, model);
	}

	public void correctedEstimationEntry() {
		try (TaggingFacade facade = new TaggingFacade()) {
			finalizeModelEntry(addOperation ? facade.addTaggingRecord(model, true) : facade.updateTaggingRecord(model),
					addOperation, facade, "rekod penandaan, ID " + model.getTaggingRecordID(),
					", kerana no. rekod penandaan telah direkodkan sebelumnya.", models, model);
		} catch (SQLException e) {
			e.printStackTrace();
			addMessage(e);
		}
		execute("PF('popupCorrectedEstimation').hide()");
	}

	public void handleCorrectedSpecies() {
		model = (TaggingRecord) copy(models, model);
		if (model.getCorrectedSpeciesID() == 0) {
			model.setCorrectedSpeciesID(model.getSpeciesID());
		}
	}

	public void correctedSpeciesEntry() {
		try (TaggingFacade facade = new TaggingFacade()) {
			finalizeModelEntry(addOperation ? facade.addTaggingRecord(model, true) : facade.updateTaggingRecord(model),
					addOperation, facade, "rekod penandaan, ID " + model.getTaggingRecordID(),
					", kerana no. rekod penandaan telah direkodkan sebelumnya.", models, model);
			for (Species species : speciesList) {
				if (model.getCorrectedSpeciesID() == species.getSpeciesID()) {
					model.setCorrectedSpeciesCode(species.getCode());
					model.setCorrectedSpeciesName(species.getName());
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			addMessage(e);
		}
		execute("PF('popupCorrectedSpecies').hide()");
	}

	public void handleTaggingIDChange() {
		clearFilter();

		try (MaintenanceFacade mFacade = new MaintenanceFacade();
				HallFacade hFacade = new HallFacade();
				TaggingFacade tFacade = new TaggingFacade();) {
			AbstractFacade.group(mFacade, hFacade, tFacade);

			tagging = null;
			map = null;

			for (Tagging t : taggings)
				if (t.getTaggingID() == selectedTaggingID)
					tagging = t;

			if (stateID == 0) {
				logSizeExist = false;
				for (LogSize ls : logSizes) {
					if (ls.getStateID() == tagging.getStateID()) {
						logSize = ls;
						logSizeExist = true;
					}
				}
			}
			
			

			if (!logSizeExist) {
				addMessage(FacesMessage.SEVERITY_WARN, null,
						"Modul Tual Balak tidak boleh digunakan kerana maklumat saiz balak bagi " + tagging.getStateName() + " tidak direkodkan.");
			} else {
				taggingForms = tFacade.getTaggingForms(tagging);

				models = tFacade.getTaggingRecords(tagging);

				ArrayList<TaggingRecord> tempTaggingRecords = new ArrayList<TaggingRecord>();
				for (TaggingRecord taggingRecord : models) {
					if (taggingRecord.getTreeTypeID() == 1) {
						tempTaggingRecords.add(taggingRecord);
					}
				}

				sort(tempTaggingRecords);
				models = tempTaggingRecords;
				tempTaggingRecords = null;

				logs = hFacade.getLogs(tagging);

				boolean completedTaggingRecord = true;
				for (TaggingRecord tr : models) {
					completedTaggingRecord = true;
					ArrayList<Log> temp = new ArrayList<>();

					for (Log l : logs) {
						if (tr.getTaggingRecordID() == l.getTaggingRecordID()) {
							temp.add(l);
							if (!l.getStatus().equalsIgnoreCase("P")) {
								completedTaggingRecord = false;
							}
						}
					}
					sort(temp);
					tr.setLogs(temp);
					if (tr.getStatus().equalsIgnoreCase("P") && completedTaggingRecord == true) {
						tr.setStatus("C");
						tFacade.updateTaggingRecordStatus(tr);
					}
				}
				sort(models);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			addMessage(e);
		}
	}

	public void handleOpenTraditional() {
		int size = taggingRecord.getEstimation();
		long current = System.currentTimeMillis();

		ArrayList<Log> logs = new ArrayList<Log>();

		for (int i = 0; i < size; i++) {
			Log log = new Log();

			log.setLogID(current + i * size);

			logs.add(log);
		}
		taggingRecord.setLogs(logs);
	}

	public void logEntry() {
		try (HallFacade facade = new HallFacade()) {
			if (addOperation) {
				finalizeModelEntry(facade.addLog(log, true), addOperation, facade,
						"rekod penandaan, ID " + model.getTaggingRecordID(),
						", kerana tual balak telah direkodkan sebelumnya", model.getLogs(), log);
			} else {
				log.setStatus("C");
				finalizeModelEntry(facade.updateLog(log), addOperation, facade, "tual balak, ID " + log.getLogID(),
						", kerana tual balak telah direkodkan sebelumnya", model.getLogs(), log);
				log = null;
				execute("PF('popup').hide()");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			addMessage(e);
		}
	}

	public void logDelete() {
		try (HallFacade hFacade = new HallFacade()) {
			if (hFacade.deleteLog(log) != 0) {
				addMessage(FacesMessage.SEVERITY_INFO, null, log + " berjaya dipadamkan.");
				log.setLength(BigDecimal.ZERO);
				log.setDiameter(BigDecimal.ZERO);
				log.setHoleDiameter(BigDecimal.ZERO);
				log.setStatus("O");
				log(hFacade, "Padam tual balak, ID " + log.getLogID());
			} else
				addMessage(FacesMessage.SEVERITY_WARN, null, log + " tidak dapat dipadamkan.");

			log = null;
		} catch (SQLException e) {
			e.printStackTrace();
			addMessage(e);
		}
		execute("PF('popupProcess').hide()");
	}

	public void logEntryTraditional() {
		try (HallFacade facade = new HallFacade()) {
			int count = 0;

			for (TaggingRecord traditionalTaggingRecord : traditionalTaggingRecords) {
				ArrayList<Log> logs = traditionalTaggingRecord.getLogs();

				for (Log log : logs) {
					if (facade.addLog(log, true) != 0) {
						count++;

						model.getLogs().add(log);
						log(facade, "Tambah tual balak, ID " + log.getLogID());
					}
				}
			}

			sort(model.getLogs());
			addMessage(FacesMessage.SEVERITY_INFO, null, "Sebanyak " + count + " tual balak berjaya ditambahkan.");

			traditionalTaggingRecords = null;
		} catch (SQLException e) {
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popupTraditional').hide()");
	}

	private Date resetCalendar(Calendar cal, int year, int month) {
		cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		if (year != 0) {
			cal.add(Calendar.YEAR, year);
		}
		if (month != 0) {
			cal.add(Calendar.MONTH, month);
		}

		return cal.getTime();
	}

}