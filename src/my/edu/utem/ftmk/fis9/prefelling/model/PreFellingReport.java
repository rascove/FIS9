package my.edu.utem.ftmk.fis9.prefelling.model;

import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;
import my.edu.utem.ftmk.fis9.maintenance.model.CuttingOption;
import my.edu.utem.ftmk.fis9.maintenance.model.LogQuality;
import my.edu.utem.ftmk.fis9.maintenance.model.TimberGroup;

public class PreFellingReport extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private PreFellingSurvey preFellingSurvey;
	private String[] headers;
	private ArrayList<TimberGroup> timberGroups;
	private ArrayList<CuttingOption> cuttingOptions;
	private ArrayList<LogQuality> logQualities;
	private ArrayList<PreFellingReportElement> nonCumulativeEstimations;
	private ArrayList<PreFellingReportElement> cumulativeEstimations;
	private ArrayList<PreFellingCuttingOption> preFellingCuttingOptions;
	private ArrayList<PreFellingReportElement> vineSpreadthCounts;
	private ArrayList<PreFellingReportElement> logQuantityCounts;
	private ArrayList<PreFellingReportElement> logQualityCounts;
	private ArrayList<PreFellingReportElement> bertamPalmCounts;
	private ArrayList<PreFellingReportElement> resamCounts;
	private ArrayList<PreFellingReportElement> rattanBambooCounts;
	private ArrayList<PreFellingReportElement> fertilityCounts;
	private ArrayList<PreFellingReportElement> seedlingCounts;
	private double originalStandRatio;

	public PreFellingReport(PreFellingSurvey preFellingSurvey, ArrayList<TimberGroup> timberGroups, ArrayList<CuttingOption> cuttingOptions, ArrayList<LogQuality> logQualities)
	{
		this.preFellingSurvey = preFellingSurvey;
		this.timberGroups = timberGroups;
		this.cuttingOptions = cuttingOptions;
		this.logQualities = logQualities;

		headers = new String[] {
				"Anggaran Bukan Kumulatif Bilangan Pokok dan Isipadu (Kasar dan Bersih) serta Keluasan Pangkal (Basal Area) Sehektar Mengikut Kelas Diameter dan Kumpulan Spesis",
				"Anggaran Kumulatif Bilangan Pokok dan Isipadu (Kasar dan Bersih) serta Keluasan Pangkal (Basal Area) Sehektar Mengikut Kelas Diameter dan Kumpulan Spesis",
				"Opsyen Had Tebangan Tanpa Mengambilkira Pokok + 15 cm - 30 cm",
				"Opsyen Had Tebangan Dengan Mengambilkira Pokok + 15 cm - 30 cm",
				"Bilangan dan Bilangan Sehektar Pepanjat Mengikut Kelas Diameter dan Kelas Diameter Pokok yang Dilingkari",
				"Kuantiti Balak Mengikut Kelas Diameter dan Kumpulan Spesis (Bilangan Pokok dan Bilangan Pokok Sehektar)",
				"Kualiti Balak Mengikut Kelas Diameter dan Kumpulan Spesis (Bilangan Pokok dan Bilangan Pokok Sehektar)",
				"Ringkasan Bertam dan Palma (Bilangan Rumpun dan Bilangan Rumpun Sehektar)",
				"Ringkasan Resam (Bilangan Petak serta Peratusan Kawasan yang Ditumbuhi Resam)",
				"Ringkasan Rotan dan Buluh (Bilangan dan Bilangan Sehektar)",
				"Ringkasan Kesuburan Pokok (Bilangan dan Bilangan Sehektar)",
				"Ringkasan Anak Benih (Jumlah Bilangan dan Bilangan Sehektar)"
		};
	}

	private void calculateEstimations()
	{
		nonCumulativeEstimations = new ArrayList<>();
		cumulativeEstimations = new ArrayList<>();
		ArrayList<PreFellingSurveyCard> preFellingSurveyCards = preFellingSurvey.getPreFellingSurveyCards();

		PreFellingReportElement nonCumDipCount = new PreFellingReportElement();
		PreFellingReportElement nonCumDipNetCount = new PreFellingReportElement();
		PreFellingReportElement nonCumDipVolume = new PreFellingReportElement();
		PreFellingReportElement nonCumDipNetVolume = new PreFellingReportElement();
		PreFellingReportElement nonCumDipBasalArea = new PreFellingReportElement();
		PreFellingReportElement cumDipCount = new PreFellingReportElement();
		PreFellingReportElement cumDipNetCount = new PreFellingReportElement();
		PreFellingReportElement cumDipVolume = new PreFellingReportElement();
		PreFellingReportElement cumDipNetVolume = new PreFellingReportElement();
		PreFellingReportElement cumDipBasalArea = new PreFellingReportElement();
		double[] nonCumDipCountValues = new double[13];
		double[] nonCumDipNetCountValues = new double[13];
		double[] nonCumDipVolumeValues = new double[13];
		double[] nonCumDipNetVolumeValues = new double[13];
		double[] nonCumDipBasalAreaValues = new double[13];
		double[] cumDipCountValues = new double[12];
		double[] cumDipNetCountValues = new double[12];
		double[] cumDipVolumeValues = new double[12];
		double[] cumDipNetVolumeValues = new double[12];
		double[] cumDipBasalAreaValues = new double[12];

		nonCumDipCount.setGroup("Dipterokarp");
		nonCumDipCount.setCriteria("Bilangan");
		nonCumDipCount.setValues(nonCumDipCountValues);
		nonCumDipNetCount.setGroup("Dipterokarp");
		nonCumDipNetCount.setCriteria("Bilangan bersih");
		nonCumDipNetCount.setValues(nonCumDipNetCountValues);
		nonCumDipVolume.setGroup("Dipterokarp");
		nonCumDipVolume.setCriteria("Isipadu");
		nonCumDipVolume.setValues(nonCumDipVolumeValues);
		nonCumDipNetVolume.setGroup("Dipterokarp");
		nonCumDipNetVolume.setCriteria("Isipadu bersih");
		nonCumDipNetVolume.setValues(nonCumDipNetVolumeValues);
		nonCumDipBasalArea.setGroup("Dipterokarp");
		nonCumDipBasalArea.setCriteria("Keluasan pangkal");
		nonCumDipBasalArea.setValues(nonCumDipBasalAreaValues);
		cumDipCount.setGroup("Dipterokarp");
		cumDipCount.setCriteria("Bilangan");
		cumDipCount.setValues(cumDipCountValues);
		cumDipNetCount.setGroup("Dipterokarp");
		cumDipNetCount.setCriteria("Bilangan bersih");
		cumDipNetCount.setValues(cumDipNetCountValues);
		cumDipVolume.setGroup("Dipterokarp");
		cumDipVolume.setCriteria("Isipadu");
		cumDipVolume.setValues(cumDipVolumeValues);
		cumDipNetVolume.setGroup("Dipterokarp");
		cumDipNetVolume.setCriteria("Isipadu bersih");
		cumDipNetVolume.setValues(cumDipNetVolumeValues);
		cumDipBasalArea.setGroup("Dipterokarp");
		cumDipBasalArea.setCriteria("Keluasan pangkal");
		cumDipBasalArea.setValues(cumDipBasalAreaValues);

		PreFellingReportElement nonCumNonDipCount = new PreFellingReportElement();
		PreFellingReportElement nonCumNonDipNetCount = new PreFellingReportElement();
		PreFellingReportElement nonCumNonDipVolume = new PreFellingReportElement();
		PreFellingReportElement nonCumNonDipNetVolume = new PreFellingReportElement();
		PreFellingReportElement nonCumNonDipBasalArea = new PreFellingReportElement();
		PreFellingReportElement cumNonDipCount = new PreFellingReportElement();
		PreFellingReportElement cumNonDipNetCount = new PreFellingReportElement();
		PreFellingReportElement cumNonDipVolume = new PreFellingReportElement();
		PreFellingReportElement cumNonDipNetVolume = new PreFellingReportElement();
		PreFellingReportElement cumNonDipBasalArea = new PreFellingReportElement();
		double[] nonCumNonDipCountValues = new double[13];
		double[] nonCumNonDipNetCountValues = new double[13];
		double[] nonCumNonDipVolumeValues = new double[13];
		double[] nonCumNonDipNetVolumeValues = new double[13];
		double[] nonCumNonDipBasalAreaValues = new double[13];
		double[] cumNonDipCountValues = new double[12];
		double[] cumNonDipNetCountValues = new double[12];
		double[] cumNonDipVolumeValues = new double[12];
		double[] cumNonDipNetVolumeValues = new double[12];
		double[] cumNonDipBasalAreaValues = new double[12];

		nonCumNonDipCount.setGroup("Bukan dipterokarp");
		nonCumNonDipCount.setCriteria("Bilangan");
		nonCumNonDipCount.setValues(nonCumNonDipCountValues);
		nonCumNonDipNetCount.setGroup("Bukan dipterokarp");
		nonCumNonDipNetCount.setCriteria("Bilangan bersih");
		nonCumNonDipNetCount.setValues(nonCumNonDipNetCountValues);
		nonCumNonDipVolume.setGroup("Bukan dipterokarp");
		nonCumNonDipVolume.setCriteria("Isipadu");
		nonCumNonDipVolume.setValues(nonCumNonDipVolumeValues);
		nonCumNonDipNetVolume.setGroup("Bukan dipterokarp");
		nonCumNonDipNetVolume.setCriteria("Isipadu bersih");
		nonCumNonDipNetVolume.setValues(nonCumNonDipNetVolumeValues);
		nonCumNonDipBasalArea.setGroup("Bukan dipterokarp");
		nonCumNonDipBasalArea.setCriteria("Keluasan pangkal");
		nonCumNonDipBasalArea.setValues(nonCumNonDipBasalAreaValues);
		cumNonDipCount.setGroup("Bukan dipterokarp");
		cumNonDipCount.setCriteria("Bilangan");
		cumNonDipCount.setValues(cumNonDipCountValues);
		cumNonDipNetCount.setGroup("Bukan dipterokarp");
		cumNonDipNetCount.setCriteria("Bilangan bersih");
		cumNonDipNetCount.setValues(cumNonDipNetCountValues);
		cumNonDipVolume.setGroup("Bukan dipterokarp");
		cumNonDipVolume.setCriteria("Isipadu");
		cumNonDipVolume.setValues(cumNonDipVolumeValues);
		cumNonDipNetVolume.setGroup("Bukan dipterokarp");
		cumNonDipNetVolume.setCriteria("Isipadu bersih");
		cumNonDipNetVolume.setValues(cumNonDipNetVolumeValues);
		cumNonDipBasalArea.setGroup("Bukan dipterokarp");
		cumNonDipBasalArea.setCriteria("Keluasan pangkal");
		cumNonDipBasalArea.setValues(cumNonDipBasalAreaValues);

		PreFellingReportElement nonCumTotalCount = new PreFellingReportElement();
		PreFellingReportElement nonCumTotalNetCount = new PreFellingReportElement();
		PreFellingReportElement nonCumTotalVolume = new PreFellingReportElement();
		PreFellingReportElement nonCumTotalNetVolume = new PreFellingReportElement();
		PreFellingReportElement nonCumTotalBasalArea = new PreFellingReportElement();
		PreFellingReportElement cumTotalCount = new PreFellingReportElement();
		PreFellingReportElement cumTotalNetCount = new PreFellingReportElement();
		PreFellingReportElement cumTotalVolume = new PreFellingReportElement();
		PreFellingReportElement cumTotalNetVolume = new PreFellingReportElement();
		PreFellingReportElement cumTotalBasalArea = new PreFellingReportElement();
		double[] nonCumTotalCountValues = new double[13];
		double[] nonCumTotalNetCountValues = new double[13];
		double[] nonCumTotalVolumeValues = new double[13];
		double[] nonCumTotalNetVolumeValues = new double[13];
		double[] nonCumTotalBasalAreaValues = new double[13];
		double[] cumTotalCountValues = new double[12];
		double[] cumTotalNetCountValues = new double[12];
		double[] cumTotalVolumeValues = new double[12];
		double[] cumTotalNetVolumeValues = new double[12];
		double[] cumTotalBasalAreaValues = new double[12];

		nonCumTotalCount.setGroup("Jumlah");
		nonCumTotalCount.setCriteria("Bilangan");
		nonCumTotalCount.setValues(nonCumTotalCountValues);
		nonCumTotalNetCount.setGroup("Jumlah");
		nonCumTotalNetCount.setCriteria("Bilangan bersih");
		nonCumTotalNetCount.setValues(nonCumTotalNetCountValues);
		nonCumTotalVolume.setGroup("Jumlah");
		nonCumTotalVolume.setCriteria("Isipadu");
		nonCumTotalVolume.setValues(nonCumTotalVolumeValues);
		nonCumTotalNetVolume.setGroup("Jumlah");
		nonCumTotalNetVolume.setCriteria("Isipadu bersih");
		nonCumTotalNetVolume.setValues(nonCumTotalNetVolumeValues);
		nonCumTotalBasalArea.setGroup("Jumlah");
		nonCumTotalBasalArea.setCriteria("Keluasan pangkal");
		nonCumTotalBasalArea.setValues(nonCumTotalBasalAreaValues);
		cumTotalCount.setGroup("Jumlah");
		cumTotalCount.setCriteria("Bilangan");
		cumTotalCount.setValues(cumTotalCountValues);
		cumTotalNetCount.setGroup("Jumlah");
		cumTotalNetCount.setCriteria("Bilangan bersih");
		cumTotalNetCount.setValues(cumTotalNetCountValues);
		cumTotalVolume.setGroup("Jumlah");
		cumTotalVolume.setCriteria("Isipadu");
		cumTotalVolume.setValues(cumTotalVolumeValues);
		cumTotalNetVolume.setGroup("Jumlah");
		cumTotalNetVolume.setCriteria("Isipadu bersih");
		cumTotalNetVolume.setValues(cumTotalNetVolumeValues);
		cumTotalBasalArea.setGroup("Jumlah");
		cumTotalBasalArea.setCriteria("Keluasan pangkal");
		cumTotalBasalArea.setValues(cumTotalBasalAreaValues);

		for (TimberGroup timberGroup : timberGroups)
		{
			PreFellingReportElement nonCumCount = new PreFellingReportElement();
			PreFellingReportElement nonCumNetCount = new PreFellingReportElement();
			PreFellingReportElement nonCumVolume = new PreFellingReportElement();
			PreFellingReportElement nonCumNetVolume = new PreFellingReportElement();
			PreFellingReportElement nonCumBasalArea = new PreFellingReportElement();
			PreFellingReportElement cumCount = new PreFellingReportElement();
			PreFellingReportElement cumNetCount = new PreFellingReportElement();
			PreFellingReportElement cumVolume = new PreFellingReportElement();
			PreFellingReportElement cumNetVolume = new PreFellingReportElement();
			PreFellingReportElement cumBasalArea = new PreFellingReportElement();
			double[] nonCumCountValues = new double[13];
			double[] nonCumNetCountValues = new double[13];
			double[] nonCumVolumeValues = new double[13];
			double[] nonCumNetVolumeValues = new double[13];
			double[] nonCumBasalAreaValues = new double[13];
			double[] cumCountValues = new double[12];
			double[] cumNetCountValues = new double[12];
			double[] cumVolumeValues = new double[12];
			double[] cumNetVolumeValues = new double[12];
			double[] cumBasalAreaValues = new double[12];

			nonCumCount.setGroup(timberGroup.getName());
			nonCumCount.setCriteria("Bilangan");
			nonCumCount.setValues(nonCumCountValues);
			nonCumNetCount.setGroup(timberGroup.getName());
			nonCumNetCount.setCriteria("Bilangan bersih");
			nonCumNetCount.setValues(nonCumNetCountValues);
			nonCumVolume.setGroup(timberGroup.getName());
			nonCumVolume.setCriteria("Isipadu");
			nonCumVolume.setValues(nonCumVolumeValues);
			nonCumNetVolume.setGroup(timberGroup.getName());
			nonCumNetVolume.setCriteria("Isipadu bersih");
			nonCumNetVolume.setValues(nonCumNetVolumeValues);
			nonCumBasalArea.setGroup(timberGroup.getName());
			nonCumBasalArea.setCriteria("Keluasan pangkal");
			nonCumBasalArea.setValues(nonCumBasalAreaValues);
			cumCount.setGroup(timberGroup.getName());
			cumCount.setCriteria("Bilangan");
			cumCount.setValues(cumCountValues);
			cumNetCount.setGroup(timberGroup.getName());
			cumNetCount.setCriteria("Bilangan bersih");
			cumNetCount.setValues(cumNetCountValues);
			cumVolume.setGroup(timberGroup.getName());
			cumVolume.setCriteria("Isipadu");
			cumVolume.setValues(cumVolumeValues);
			cumNetVolume.setGroup(timberGroup.getName());
			cumNetVolume.setCriteria("Isipadu bersih");
			cumNetVolume.setValues(cumNetVolumeValues);
			cumBasalArea.setGroup(timberGroup.getName());
			cumBasalArea.setCriteria("Keluasan pangkal");
			cumBasalArea.setValues(cumBasalAreaValues);

			for (PreFellingSurveyCard preFellingSurveyCard : preFellingSurveyCards)
			{
				ArrayList<PreFellingSurveyRecord> preFellingSurveyRecords = preFellingSurveyCard.getPreFellingSurveyRecords();

				for (PreFellingSurveyRecord preFellingSurveyRecord : preFellingSurveyRecords)
				{
					if (preFellingSurveyRecord.getTimberGroupID() == timberGroup.getTimberGroupID())
					{
						int index = -1;
						double diameter = preFellingSurveyRecord.getDiameter(), divisor = preFellingSurveyCards.size();

						if (diameter > 15 && diameter <= 30)
							index = 0;
						else if (diameter > 30 && diameter <= 45)
							index = 1;
						else if (diameter > 45 && diameter <= 50)
							index = 2;
						else if (diameter > 50 && diameter <= 55)
							index = 3;
						else if (diameter > 55 && diameter <= 60)
							index = 4;
						else if (diameter > 60 && diameter <= 65)
							index = 5;
						else if (diameter > 65 && diameter <= 70)
							index = 6;
						else if (diameter > 70 && diameter <= 75)
							index = 7;
						else if (diameter > 75 && diameter <= 80)
							index = 8;
						else if (diameter > 80 && diameter <= 85)
							index = 9;
						else if (diameter > 85 && diameter <= 90)
							index = 10;
						else if (diameter > 90)
							index = 11;

						if (index != -1)
						{
							divisor *= index == 0 ? 0.05 : 0.1;

							nonCumCountValues[index] += 1 / divisor;
							nonCumNetCountValues[index] += preFellingSurveyRecord.getNett() / divisor;
							nonCumVolumeValues[index] += preFellingSurveyRecord.getVolume() / divisor;
							nonCumNetVolumeValues[index] += preFellingSurveyRecord.getNetVolume() / divisor;
							nonCumBasalAreaValues[index] += preFellingSurveyRecord.getBasalArea() / divisor;

							nonCumTotalCountValues[index] += 1 / divisor;
							nonCumTotalNetCountValues[index] += preFellingSurveyRecord.getNett() / divisor;
							nonCumTotalVolumeValues[index] += preFellingSurveyRecord.getVolume() / divisor;
							nonCumTotalNetVolumeValues[index] += preFellingSurveyRecord.getNetVolume() / divisor;
							nonCumTotalBasalAreaValues[index] += preFellingSurveyRecord.getBasalArea() / divisor;

							if (timberGroup.getTimberGroupID() < 5 && timberGroup.getTimberGroupID() != 0)
							{
								nonCumDipCountValues[index] += 1 / divisor;
								nonCumDipNetCountValues[index] += preFellingSurveyRecord.getNett() / divisor;
								nonCumDipVolumeValues[index] += preFellingSurveyRecord.getVolume() / divisor;
								nonCumDipNetVolumeValues[index] += preFellingSurveyRecord.getNetVolume() / divisor;
								nonCumDipBasalAreaValues[index] += preFellingSurveyRecord.getBasalArea() / divisor;
							}
							else
							{
								nonCumNonDipCountValues[index] += 1 / divisor;
								nonCumNonDipNetCountValues[index] += preFellingSurveyRecord.getNett() / divisor;
								nonCumNonDipVolumeValues[index] += preFellingSurveyRecord.getVolume() / divisor;
								nonCumNonDipNetVolumeValues[index] += preFellingSurveyRecord.getNetVolume() / divisor;
								nonCumNonDipBasalAreaValues[index] += preFellingSurveyRecord.getBasalArea() / divisor;
							}

							for (int i = 0; i < index + 1; i++)
							{
								cumCountValues[i] += 1 / divisor;
								cumNetCountValues[i] += preFellingSurveyRecord.getNett() / divisor;
								cumVolumeValues[i] += preFellingSurveyRecord.getVolume() / divisor;
								cumNetVolumeValues[i] += preFellingSurveyRecord.getNetVolume() / divisor;
								cumBasalAreaValues[i] += preFellingSurveyRecord.getBasalArea() / divisor;

								cumTotalCountValues[i] += 1 / divisor;
								cumTotalNetCountValues[i] += preFellingSurveyRecord.getNett() / divisor;
								cumTotalVolumeValues[i] += preFellingSurveyRecord.getVolume() / divisor;
								cumTotalNetVolumeValues[i] += preFellingSurveyRecord.getNetVolume() / divisor;
								cumTotalBasalAreaValues[i] += preFellingSurveyRecord.getBasalArea() / divisor;

								if (timberGroup.getTimberGroupID() < 5 && timberGroup.getTimberGroupID() != 0)
								{
									cumDipCountValues[i] += 1 / divisor;
									cumDipNetCountValues[i] += preFellingSurveyRecord.getNett() / divisor;
									cumDipVolumeValues[i] += preFellingSurveyRecord.getVolume() / divisor;
									cumDipNetVolumeValues[i] += preFellingSurveyRecord.getNetVolume() / divisor;
									cumDipBasalAreaValues[i] += preFellingSurveyRecord.getBasalArea() / divisor;
								}
								else
								{
									cumNonDipCountValues[i] += 1 / divisor;
									cumNonDipNetCountValues[i] += preFellingSurveyRecord.getNett() / divisor;
									cumNonDipVolumeValues[i] += preFellingSurveyRecord.getVolume() / divisor;
									cumNonDipNetVolumeValues[i] += preFellingSurveyRecord.getNetVolume() / divisor;
									cumNonDipBasalAreaValues[i] += preFellingSurveyRecord.getBasalArea() / divisor;
								}
							}
						}
					}
				}
			}

			for (int i = 0; i < 12; i++)
			{
				nonCumCountValues[12] += nonCumCountValues[i];
				nonCumNetCountValues[12] += nonCumNetCountValues[i];
				nonCumVolumeValues[12] += nonCumVolumeValues[i];
				nonCumNetVolumeValues[12] += nonCumNetVolumeValues[i];
				nonCumBasalAreaValues[12] += nonCumBasalAreaValues[i];
			}

			nonCumulativeEstimations.add(nonCumCount);
			nonCumulativeEstimations.add(nonCumNetCount);
			nonCumulativeEstimations.add(nonCumVolume);
			nonCumulativeEstimations.add(nonCumNetVolume);
			nonCumulativeEstimations.add(nonCumBasalArea);
			cumulativeEstimations.add(cumCount);
			cumulativeEstimations.add(cumNetCount);
			cumulativeEstimations.add(cumVolume);
			cumulativeEstimations.add(cumNetVolume);
			cumulativeEstimations.add(cumBasalArea);
		}

		for (int i = 0; i < 12; i++)
		{
			nonCumDipCountValues[12] += nonCumDipCountValues[i];
			nonCumDipNetCountValues[12] += nonCumDipNetCountValues[i];
			nonCumDipVolumeValues[12] += nonCumDipVolumeValues[i];
			nonCumDipNetVolumeValues[12] += nonCumDipNetVolumeValues[i];
			nonCumDipBasalAreaValues[12] += nonCumDipBasalAreaValues[i];

			nonCumNonDipCountValues[12] += nonCumNonDipCountValues[i];
			nonCumNonDipNetCountValues[12] += nonCumNonDipNetCountValues[i];
			nonCumNonDipVolumeValues[12] += nonCumNonDipVolumeValues[i];
			nonCumNonDipNetVolumeValues[12] += nonCumNonDipNetVolumeValues[i];
			nonCumNonDipBasalAreaValues[12] += nonCumNonDipBasalAreaValues[i];

			nonCumTotalCountValues[12] += nonCumTotalCountValues[i];
			nonCumTotalNetCountValues[12] += nonCumTotalNetCountValues[i];
			nonCumTotalVolumeValues[12] += nonCumTotalVolumeValues[i];
			nonCumTotalNetVolumeValues[12] += nonCumTotalNetVolumeValues[i];
			nonCumTotalBasalAreaValues[12] += nonCumTotalBasalAreaValues[i];
		}

		nonCumulativeEstimations.add(nonCumDipCount);
		nonCumulativeEstimations.add(nonCumDipNetCount);
		nonCumulativeEstimations.add(nonCumDipVolume);
		nonCumulativeEstimations.add(nonCumDipNetVolume);
		nonCumulativeEstimations.add(nonCumDipBasalArea);
		cumulativeEstimations.add(cumDipCount);
		cumulativeEstimations.add(cumDipNetCount);
		cumulativeEstimations.add(cumDipVolume);
		cumulativeEstimations.add(cumDipNetVolume);
		cumulativeEstimations.add(cumDipBasalArea);

		nonCumulativeEstimations.add(nonCumNonDipCount);
		nonCumulativeEstimations.add(nonCumNonDipNetCount);
		nonCumulativeEstimations.add(nonCumNonDipVolume);
		nonCumulativeEstimations.add(nonCumNonDipNetVolume);
		nonCumulativeEstimations.add(nonCumNonDipBasalArea);
		cumulativeEstimations.add(cumNonDipCount);
		cumulativeEstimations.add(cumNonDipNetCount);
		cumulativeEstimations.add(cumNonDipVolume);
		cumulativeEstimations.add(cumNonDipNetVolume);
		cumulativeEstimations.add(cumNonDipBasalArea);

		nonCumulativeEstimations.add(nonCumTotalCount);
		nonCumulativeEstimations.add(nonCumTotalNetCount);
		nonCumulativeEstimations.add(nonCumTotalVolume);
		nonCumulativeEstimations.add(nonCumTotalNetVolume);
		nonCumulativeEstimations.add(nonCumTotalBasalArea);
		cumulativeEstimations.add(cumTotalCount);
		cumulativeEstimations.add(cumTotalNetCount);
		cumulativeEstimations.add(cumTotalVolume);
		cumulativeEstimations.add(cumTotalNetVolume);
		cumulativeEstimations.add(cumTotalBasalArea);
	}

	private void calculateLogCounts()
	{
		logQuantityCounts = new ArrayList<>();
		logQualityCounts = new ArrayList<>();
		ArrayList<PreFellingSurveyCard> preFellingSurveyCards = preFellingSurvey.getPreFellingSurveyCards();
		double relativeCount = 1/ (preFellingSurveyCards.size() * 0.1);
		int size = logQualities.size();

		PreFellingReportElement[] logQuantityDipCount = new PreFellingReportElement[5];
		PreFellingReportElement[] logQuantityDipRelativeCount = new PreFellingReportElement[5];
		PreFellingReportElement[] logQuantityNonDipCount = new PreFellingReportElement[5];
		PreFellingReportElement[] logQuantityNonDipRelativeCount = new PreFellingReportElement[5];
		PreFellingReportElement[] logQuantityFullCount = new PreFellingReportElement[5];
		PreFellingReportElement[] logQuantityFullRelativeCount = new PreFellingReportElement[5];
		PreFellingReportElement[] logQuantityPartialCount = new PreFellingReportElement[5];
		PreFellingReportElement[] logQuantityPartialRelativeCount = new PreFellingReportElement[5];
		PreFellingReportElement[] logQuantityOtherCount = new PreFellingReportElement[5];
		PreFellingReportElement[] logQuantityOtherRelativeCount = new PreFellingReportElement[5];
		PreFellingReportElement[] logQuantityTotalCount = new PreFellingReportElement[5];
		PreFellingReportElement[] logQuantityTotalRelativeCount = new PreFellingReportElement[5];
		double[][] logQuantityDipCountValues = new double[5][6];
		double[][] logQuantityDipRelativeCountValues = new double[5][6];
		double[][] logQuantityNonDipCountValues = new double[5][6];
		double[][] logQuantityNonDipRelativeCountValues = new double[5][6];
		double[][] logQuantityFullCountValues = new double[5][6];
		double[][] logQuantityFullRelativeCountValues = new double[5][6];
		double[][] logQuantityPartialCountValues = new double[5][6];
		double[][] logQuantityPartialRelativeCountValues = new double[5][6];
		double[][] logQuantityOtherCountValues = new double[5][6];
		double[][] logQuantityOtherRelativeCountValues = new double[5][6];
		double[][] logQuantityTotalCountValues = new double[5][6];
		double[][] logQuantityTotalRelativeCountValues = new double[5][6];

		for (int i = 0; i < 5; i++)
		{
			logQuantityDipCount[i] = new PreFellingReportElement();
			logQuantityNonDipCount[i] = new PreFellingReportElement();
			logQuantityFullCount[i] = new PreFellingReportElement();
			logQuantityPartialCount[i] = new PreFellingReportElement();
			logQuantityOtherCount[i] = new PreFellingReportElement();
			logQuantityTotalCount[i] = new PreFellingReportElement();
			logQuantityDipRelativeCount[i] = new PreFellingReportElement();
			logQuantityNonDipRelativeCount[i] = new PreFellingReportElement();
			logQuantityFullRelativeCount[i] = new PreFellingReportElement();
			logQuantityPartialRelativeCount[i] = new PreFellingReportElement();
			logQuantityOtherRelativeCount[i] = new PreFellingReportElement();
			logQuantityTotalRelativeCount[i] = new PreFellingReportElement();

			logQuantityDipCount[i].setGroup("Dipterokarp");
			logQuantityDipCount[i].setSubgroup(String.valueOf(i + 1));
			logQuantityDipCount[i].setCriteria("Bilangan");
			logQuantityDipCount[i].setValues(logQuantityDipCountValues[i]);
			logQuantityNonDipCount[i].setGroup("Bukan dipterokarp");
			logQuantityNonDipCount[i].setSubgroup(String.valueOf(i + 1));
			logQuantityNonDipCount[i].setCriteria("Bilangan");
			logQuantityNonDipCount[i].setValues(logQuantityNonDipCountValues[i]);
			logQuantityFullCount[i].setGroup("Kumpulan kayu berpasaran penuh");
			logQuantityFullCount[i].setSubgroup(String.valueOf(i + 1));
			logQuantityFullCount[i].setCriteria("Bilangan");
			logQuantityFullCount[i].setValues(logQuantityFullCountValues[i]);
			logQuantityPartialCount[i].setGroup("Kumpulan kayu separuh berpasaran");
			logQuantityPartialCount[i].setSubgroup(String.valueOf(i + 1));
			logQuantityPartialCount[i].setCriteria("Bilangan");
			logQuantityPartialCount[i].setValues(logQuantityPartialCountValues[i]);
			logQuantityOtherCount[i].setGroup("Pelbagai kumpulan kayu berpasaran");
			logQuantityOtherCount[i].setSubgroup(String.valueOf(i + 1));
			logQuantityOtherCount[i].setCriteria("Bilangan");
			logQuantityOtherCount[i].setValues(logQuantityOtherCountValues[i]);
			logQuantityTotalCount[i].setGroup("Jumlah besar");
			logQuantityTotalCount[i].setSubgroup(String.valueOf(i + 1));
			logQuantityTotalCount[i].setCriteria("Bilangan");
			logQuantityTotalCount[i].setValues(logQuantityTotalCountValues[i]);

			logQuantityDipRelativeCount[i].setGroup("Dipterokarp");
			logQuantityDipRelativeCount[i].setSubgroup(String.valueOf(i + 1));
			logQuantityDipRelativeCount[i].setCriteria("Bilangan sehektar");
			logQuantityDipRelativeCount[i].setValues(logQuantityDipRelativeCountValues[i]);
			logQuantityNonDipRelativeCount[i].setGroup("Bukan dipterokarp");
			logQuantityNonDipRelativeCount[i].setSubgroup(String.valueOf(i + 1));
			logQuantityNonDipRelativeCount[i].setCriteria("Bilangan sehektar");
			logQuantityNonDipRelativeCount[i].setValues(logQuantityNonDipRelativeCountValues[i]);
			logQuantityFullRelativeCount[i].setGroup("Kumpulan kayu berpasaran penuh");
			logQuantityFullRelativeCount[i].setSubgroup(String.valueOf(i + 1));
			logQuantityFullRelativeCount[i].setCriteria("Bilangan sehektar");
			logQuantityFullRelativeCount[i].setValues(logQuantityFullRelativeCountValues[i]);
			logQuantityPartialRelativeCount[i].setGroup("Kumpulan kayu separuh berpasaran");
			logQuantityPartialRelativeCount[i].setSubgroup(String.valueOf(i + 1));
			logQuantityPartialRelativeCount[i].setCriteria("Bilangan sehektar");
			logQuantityPartialRelativeCount[i].setValues(logQuantityPartialRelativeCountValues[i]);
			logQuantityOtherRelativeCount[i].setGroup("Pelbagai kumpulan kayu berpasaran");
			logQuantityOtherRelativeCount[i].setSubgroup(String.valueOf(i + 1));
			logQuantityOtherRelativeCount[i].setCriteria("Bilangan sehektar");
			logQuantityOtherRelativeCount[i].setValues(logQuantityOtherRelativeCountValues[i]);
			logQuantityTotalRelativeCount[i].setGroup("Jumlah besar");
			logQuantityTotalRelativeCount[i].setSubgroup(String.valueOf(i + 1));
			logQuantityTotalRelativeCount[i].setCriteria("Bilangan sehektar");
			logQuantityTotalRelativeCount[i].setValues(logQuantityTotalRelativeCountValues[i]);
		}

		PreFellingReportElement[] logQualityDipCount = new PreFellingReportElement[size];
		PreFellingReportElement[] logQualityDipRelativeCount = new PreFellingReportElement[size];
		PreFellingReportElement[] logQualityNonDipCount = new PreFellingReportElement[size];
		PreFellingReportElement[] logQualityNonDipRelativeCount = new PreFellingReportElement[size];
		PreFellingReportElement[] logQualityFullCount = new PreFellingReportElement[size];
		PreFellingReportElement[] logQualityFullRelativeCount = new PreFellingReportElement[size];
		PreFellingReportElement[] logQualityPartialCount = new PreFellingReportElement[size];
		PreFellingReportElement[] logQualityPartialRelativeCount = new PreFellingReportElement[size];
		PreFellingReportElement[] logQualityOtherCount = new PreFellingReportElement[size];
		PreFellingReportElement[] logQualityOtherRelativeCount = new PreFellingReportElement[size];
		PreFellingReportElement[] logQualityTotalCount = new PreFellingReportElement[size];
		PreFellingReportElement[] logQualityTotalRelativeCount = new PreFellingReportElement[size];
		double[][] logQualityDipCountValues = new double[size][6];
		double[][] logQualityDipRelativeCountValues = new double[size][6];
		double[][] logQualityNonDipCountValues = new double[size][6];
		double[][] logQualityNonDipRelativeCountValues = new double[size][6];
		double[][] logQualityFullCountValues = new double[size][6];
		double[][] logQualityFullRelativeCountValues = new double[size][6];
		double[][] logQualityPartialCountValues = new double[size][6];
		double[][] logQualityPartialRelativeCountValues = new double[size][6];
		double[][] logQualityOtherCountValues = new double[size][6];
		double[][] logQualityOtherRelativeCountValues = new double[size][6];
		double[][] logQualityTotalCountValues = new double[size][6];
		double[][] logQualityTotalRelativeCountValues = new double[size][6];

		for (int i = 0; i < size; i++)
		{
			LogQuality logQuality = logQualities.get(i);
			logQualityDipCount[i] = new PreFellingReportElement();
			logQualityNonDipCount[i] = new PreFellingReportElement();
			logQualityFullCount[i] = new PreFellingReportElement();
			logQualityPartialCount[i] = new PreFellingReportElement();
			logQualityOtherCount[i] = new PreFellingReportElement();
			logQualityTotalCount[i] = new PreFellingReportElement();
			logQualityDipRelativeCount[i] = new PreFellingReportElement();
			logQualityNonDipRelativeCount[i] = new PreFellingReportElement();
			logQualityFullRelativeCount[i] = new PreFellingReportElement();
			logQualityPartialRelativeCount[i] = new PreFellingReportElement();
			logQualityOtherRelativeCount[i] = new PreFellingReportElement();
			logQualityTotalRelativeCount[i] = new PreFellingReportElement();

			logQualityDipCount[i].setGroup("Dipterokarp");
			logQualityDipCount[i].setSubgroup(logQuality.getName());
			logQualityDipCount[i].setCriteria("Bilangan");
			logQualityDipCount[i].setValues(logQualityDipCountValues[i]);
			logQualityNonDipCount[i].setGroup("Bukan dipterokarp");
			logQualityNonDipCount[i].setSubgroup(logQuality.getName());
			logQualityNonDipCount[i].setCriteria("Bilangan");
			logQualityNonDipCount[i].setValues(logQualityNonDipCountValues[i]);
			logQualityFullCount[i].setGroup("Kumpulan kayu berpasaran penuh");
			logQualityFullCount[i].setSubgroup(logQuality.getName());
			logQualityFullCount[i].setCriteria("Bilangan");
			logQualityFullCount[i].setValues(logQualityFullCountValues[i]);
			logQualityPartialCount[i].setGroup("Kumpulan kayu separuh berpasaran");
			logQualityPartialCount[i].setSubgroup(logQuality.getName());
			logQualityPartialCount[i].setCriteria("Bilangan");
			logQualityPartialCount[i].setValues(logQualityPartialCountValues[i]);
			logQualityOtherCount[i].setGroup("Pelbagai kumpulan kayu berpasaran");
			logQualityOtherCount[i].setSubgroup(logQuality.getName());
			logQualityOtherCount[i].setCriteria("Bilangan");
			logQualityOtherCount[i].setValues(logQualityOtherCountValues[i]);
			logQualityTotalCount[i].setGroup("Jumlah besar");
			logQualityTotalCount[i].setSubgroup(logQuality.getName());
			logQualityTotalCount[i].setCriteria("Bilangan");
			logQualityTotalCount[i].setValues(logQualityTotalCountValues[i]);

			logQualityDipRelativeCount[i].setGroup("Dipterokarp");
			logQualityDipRelativeCount[i].setSubgroup(logQuality.getName());
			logQualityDipRelativeCount[i].setCriteria("Bilangan sehektar");
			logQualityDipRelativeCount[i].setValues(logQualityDipRelativeCountValues[i]);
			logQualityNonDipRelativeCount[i].setGroup("Bukan dipterokarp");
			logQualityNonDipRelativeCount[i].setSubgroup(logQuality.getName());
			logQualityNonDipRelativeCount[i].setCriteria("Bilangan sehektar");
			logQualityNonDipRelativeCount[i].setValues(logQualityNonDipRelativeCountValues[i]);
			logQualityFullRelativeCount[i].setGroup("Kumpulan kayu berpasaran penuh");
			logQualityFullRelativeCount[i].setSubgroup(logQuality.getName());
			logQualityFullRelativeCount[i].setCriteria("Bilangan sehektar");
			logQualityFullRelativeCount[i].setValues(logQualityFullRelativeCountValues[i]);
			logQualityPartialRelativeCount[i].setGroup("Kumpulan kayu separuh berpasaran");
			logQualityPartialRelativeCount[i].setSubgroup(logQuality.getName());
			logQualityPartialRelativeCount[i].setCriteria("Bilangan sehektar");
			logQualityPartialRelativeCount[i].setValues(logQualityPartialRelativeCountValues[i]);
			logQualityOtherRelativeCount[i].setGroup("Pelbagai kumpulan kayu berpasaran");
			logQualityOtherRelativeCount[i].setSubgroup(logQuality.getName());
			logQualityOtherRelativeCount[i].setCriteria("Bilangan sehektar");
			logQualityOtherRelativeCount[i].setValues(logQualityOtherRelativeCountValues[i]);
			logQualityTotalRelativeCount[i].setGroup("Jumlah besar");
			logQualityTotalRelativeCount[i].setSubgroup(logQuality.getName());
			logQualityTotalRelativeCount[i].setCriteria("Bilangan sehektar");
			logQualityTotalRelativeCount[i].setValues(logQualityTotalRelativeCountValues[i]);
		}

		for (PreFellingSurveyCard preFellingSurveyCard : preFellingSurveyCards)
		{
			ArrayList<PreFellingSurveyRecord> preFellingSurveyRecords = preFellingSurveyCard.getPreFellingSurveyRecords();

			for (PreFellingSurveyRecord preFellingSurveyRecord : preFellingSurveyRecords)
			{
				if (preFellingSurveyRecord.getPlotTypeID() < 3)
				{
					double diameter = preFellingSurveyRecord.getDiameter();
					int index = 0, logQuantity = preFellingSurveyRecord.getLogQuantity(), logQualityID = preFellingSurveyRecord.getLogQualityID(), speciesTypeID = preFellingSurveyRecord.getSpeciesTypeID(), timberGroupID = preFellingSurveyRecord.getTimberGroupID();

					if (diameter > 45 && diameter <= 60)
						index = 1;
					else if (diameter > 60 && diameter <= 75)
						index = 2;
					else if (diameter > 75 && diameter <= 90)
						index = 3;
					else if (diameter > 90)
						index = 4;

					if (speciesTypeID != 3)
					{
						logQuantityDipCountValues[logQuantity - 1][index]++;
						logQuantityDipRelativeCountValues[logQuantity - 1][index] += relativeCount;
						logQuantityDipCountValues[logQuantity - 1][5]++;
						logQuantityDipRelativeCountValues[logQuantity - 1][5] += relativeCount;

						logQualityDipCountValues[logQualityID - 1][index]++;
						logQualityDipRelativeCountValues[logQualityID - 1][index] += relativeCount;
						logQualityDipCountValues[logQualityID - 1][5]++;
						logQualityDipRelativeCountValues[logQualityID - 1][5] += relativeCount;
					}
					else
					{
						logQuantityNonDipCountValues[logQuantity - 1][index]++;
						logQuantityNonDipRelativeCountValues[logQuantity - 1][index] += relativeCount;
						logQuantityNonDipCountValues[logQuantity - 1][5]++;
						logQuantityNonDipRelativeCountValues[logQuantity - 1][5] += relativeCount;

						logQualityNonDipCountValues[logQualityID - 1][index]++;
						logQualityNonDipRelativeCountValues[logQualityID - 1][index] += relativeCount;
						logQualityNonDipCountValues[logQualityID - 1][5]++;
						logQualityNonDipRelativeCountValues[logQualityID - 1][5] += relativeCount;
					}

					if (timberGroupID >= 1 && timberGroupID <= 7)
					{
						logQuantityFullCountValues[logQuantity - 1][index]++;
						logQuantityFullRelativeCountValues[logQuantity - 1][index] += relativeCount;
						logQuantityFullCountValues[logQuantity - 1][5]++;
						logQuantityFullRelativeCountValues[logQuantity - 1][5] += relativeCount;

						logQualityFullCountValues[logQualityID - 1][index]++;
						logQualityFullRelativeCountValues[logQualityID - 1][index] += relativeCount;
						logQualityFullCountValues[logQualityID - 1][5]++;
						logQualityFullRelativeCountValues[logQualityID - 1][5] += relativeCount;
					}
					else if (timberGroupID == 8)
					{
						logQuantityPartialCountValues[logQuantity - 1][index]++;
						logQuantityPartialRelativeCountValues[logQuantity - 1][index] += relativeCount;
						logQuantityPartialCountValues[logQuantity - 1][5]++;
						logQuantityPartialRelativeCountValues[logQuantity - 1][5] += relativeCount;

						logQualityPartialCountValues[logQualityID - 1][index]++;
						logQualityPartialRelativeCountValues[logQualityID - 1][index] += relativeCount;
						logQualityPartialCountValues[logQualityID - 1][5]++;
						logQualityPartialRelativeCountValues[logQualityID - 1][5] += relativeCount;
					}
					else
					{
						logQuantityOtherCountValues[logQuantity - 1][index]++;
						logQuantityOtherRelativeCountValues[logQuantity - 1][index] += relativeCount;
						logQuantityOtherCountValues[logQuantity - 1][5]++;
						logQuantityOtherRelativeCountValues[logQuantity - 1][5] += relativeCount;

						logQualityOtherCountValues[logQualityID - 1][index]++;
						logQualityOtherRelativeCountValues[logQualityID - 1][index] += relativeCount;
						logQualityOtherCountValues[logQualityID - 1][5]++;
						logQualityOtherRelativeCountValues[logQualityID - 1][5] += relativeCount;
					}

					logQuantityTotalCountValues[logQuantity - 1][index]++;
					logQuantityTotalRelativeCountValues[logQuantity - 1][index] += relativeCount;
					logQuantityTotalCountValues[logQuantity - 1][5]++;
					logQuantityTotalRelativeCountValues[logQuantity - 1][5] += relativeCount;

					logQualityTotalCountValues[logQualityID - 1][index]++;
					logQualityTotalRelativeCountValues[logQualityID - 1][index] += relativeCount;
					logQualityTotalCountValues[logQualityID - 1][5]++;
					logQualityTotalRelativeCountValues[logQualityID - 1][5] += relativeCount;
				}
			}
		}

		for (int i = 0; i < 5; i++)
		{
			logQuantityCounts.add(logQuantityDipCount[i]);
			logQuantityCounts.add(logQuantityDipRelativeCount[i]);
		}

		for (int i = 0; i < size; i++)
		{
			logQualityCounts.add(logQualityDipCount[i]);
			logQualityCounts.add(logQualityDipRelativeCount[i]);
		}

		for (int i = 0; i < 5; i++)
		{
			logQuantityCounts.add(logQuantityNonDipCount[i]);
			logQuantityCounts.add(logQuantityNonDipRelativeCount[i]);
		}

		for (int i = 0; i < size; i++)
		{
			logQualityCounts.add(logQualityNonDipCount[i]);
			logQualityCounts.add(logQualityNonDipRelativeCount[i]);
		}

		for (int i = 0; i < 5; i++)
		{
			logQuantityCounts.add(logQuantityFullCount[i]);
			logQuantityCounts.add(logQuantityFullRelativeCount[i]);
		}

		for (int i = 0; i < size; i++)
		{
			logQualityCounts.add(logQualityFullCount[i]);
			logQualityCounts.add(logQualityFullRelativeCount[i]);
		}

		for (int i = 0; i < 5; i++)
		{
			logQuantityCounts.add(logQuantityPartialCount[i]);
			logQuantityCounts.add(logQuantityPartialRelativeCount[i]);
		}

		for (int i = 0; i < size; i++)
		{
			logQualityCounts.add(logQualityPartialCount[i]);
			logQualityCounts.add(logQualityPartialRelativeCount[i]);
		}

		for (int i = 0; i < 5; i++)
		{
			logQuantityCounts.add(logQuantityOtherCount[i]);
			logQuantityCounts.add(logQuantityOtherRelativeCount[i]);
		}

		for (int i = 0; i < size; i++)
		{
			logQualityCounts.add(logQualityOtherCount[i]);
			logQualityCounts.add(logQualityOtherRelativeCount[i]);
		}

		for (int i = 0; i < 5; i++)
		{
			logQuantityCounts.add(logQuantityTotalCount[i]);
			logQuantityCounts.add(logQuantityTotalRelativeCount[i]);
		}

		for (int i = 0; i < size; i++)
		{
			logQualityCounts.add(logQualityTotalCount[i]);
			logQualityCounts.add(logQualityTotalRelativeCount[i]);
		}
	}

	public String[] getHeaders()
	{
		return headers;
	}

	public ArrayList<PreFellingReportElement> getNonCumulativeEstimations()
	{
		if (nonCumulativeEstimations == null || cumulativeEstimations == null)
			calculateEstimations();

		return nonCumulativeEstimations;
	}

	public ArrayList<PreFellingReportElement> getCumulativeEstimations()
	{
		if (nonCumulativeEstimations == null || cumulativeEstimations == null)
			calculateEstimations();

		return cumulativeEstimations;
	}

	public ArrayList<PreFellingCuttingOption> getPreFellingCuttingOptions()
	{
		if (preFellingCuttingOptions == null)
		{
			preFellingCuttingOptions = new ArrayList<>();
			ArrayList<PreFellingSurveyCard> preFellingSurveyCards = preFellingSurvey.getPreFellingSurveyCards();
			int dipCount = 0, nonDipCount = 0;

			for (CuttingOption cuttingOption : cuttingOptions)
			{
				double dipLimit = cuttingOption.getDipterocarpLimit(), nonDipLimit = cuttingOption.getNonDipterocarpLimit(), relativeDipCount = 0, relativeNonDipCount = 0, relativeDipNetCountAll = 0, relativeDipNetVolume = 0, relativeNonDipNetVolume = 0, relativeDipNetCountPartial = 0, relativeNonDipNetCountAll = 0, relativeNonDipNetCountPartial = 0;

				for (PreFellingSurveyCard preFellingSurveyCard : preFellingSurveyCards)
				{
					ArrayList<PreFellingSurveyRecord> preFellingSurveyRecords = preFellingSurveyCard.getPreFellingSurveyRecords();

					for (PreFellingSurveyRecord preFellingSurveyRecord : preFellingSurveyRecords)
					{
						int speciesTypeID = preFellingSurveyRecord.getSpeciesTypeID();
						double diameter = preFellingSurveyRecord.getDiameter(), divisor = preFellingSurveyCards.size();

						if (diameter <= 30)
							divisor *= 0.05;
						else
						{
							divisor *= 0.1;

							if (originalStandRatio == 0)
							{
								if (speciesTypeID != 3)
									dipCount++;
								else if (speciesTypeID == 3)
									nonDipCount++;
							}
						}

						double relativeNetVolume = preFellingSurveyRecord.getNetVolume() / divisor, relativeNetCount = preFellingSurveyRecord.getNett() / divisor;

						if (speciesTypeID != 3)
						{
							if (diameter >= dipLimit)
							{
								relativeDipCount += 1 / divisor;
								relativeDipNetVolume += relativeNetVolume;
							}
							else if (diameter > 15)
							{
								double factor = diameter < 30 ? 0.33 : (diameter < 45 ? 1 : 2);
								relativeDipNetCountAll += relativeNetCount * factor;

								if (diameter >= 30)
									relativeDipNetCountPartial += relativeNetCount * factor;
							}
						}
						else
						{
							if (diameter >= nonDipLimit)
							{
								relativeNonDipCount += 1 / divisor;
								relativeNonDipNetVolume += relativeNetVolume;
							}
							else if (diameter > 15)
							{
								double factor = diameter < 30 ? 0.33 : (diameter < 45 ? 1 : 2);
								relativeNonDipNetCountAll += relativeNetCount * factor;

								if (diameter >= 30)
									relativeNonDipNetCountPartial += relativeNetCount * factor;
							}
						}
					}
				}

				if (originalStandRatio == 0)
					originalStandRatio = (double) dipCount / (dipCount + nonDipCount);

				if (relativeDipCount != 0 || relativeNonDipCount != 0)
				{
					PreFellingCuttingOption preFellingCuttingOption = new PreFellingCuttingOption();

					preFellingCuttingOption.setPreFellingSurveyID(preFellingSurvey.getPreFellingSurveyID());
					preFellingCuttingOption.setCuttingOptionID(cuttingOption.getCuttingOptionID());
					preFellingCuttingOption.setDipterocarpLimit(dipLimit);
					preFellingCuttingOption.setNonDipterocarpLimit(nonDipLimit);
					preFellingCuttingOption.setRelativeDipCount(relativeDipCount);
					preFellingCuttingOption.setRelativeNonDipCount(relativeNonDipCount);
					preFellingCuttingOption.setRelativeDipNetVolume(relativeDipNetVolume);
					preFellingCuttingOption.setRelativeNonDipNetVolume(relativeNonDipNetVolume);
					preFellingCuttingOption.setRelativeDipNetCountAll(relativeDipNetCountAll);
					preFellingCuttingOption.setRelativeDipNetCountPartial(relativeDipNetCountPartial);
					preFellingCuttingOption.setRelativeNonDipNetCountAll(relativeNonDipNetCountAll);
					preFellingCuttingOption.setRelativeNonDipNetCountPartial(relativeNonDipNetCountPartial);
					preFellingCuttingOption.setRelativeTotalCount(relativeDipCount + relativeNonDipCount);
					preFellingCuttingOption.setRelativeTotalCountRatio(relativeDipCount / (relativeDipCount + relativeNonDipCount));
					preFellingCuttingOption.setRelativeTotalNetVolume(relativeDipNetVolume + relativeNonDipNetVolume);
					preFellingCuttingOption.setRelativeTotalNetCountAll(relativeDipNetCountAll + relativeNonDipNetCountAll);
					preFellingCuttingOption.setRelativeTotalNetCountAllRatio(relativeDipNetCountAll / (relativeDipNetCountAll + relativeNonDipNetCountAll));
					preFellingCuttingOption.setRelativeTotalNetCountPartial(relativeDipNetCountPartial + relativeNonDipNetCountPartial);
					preFellingCuttingOption.setRelativeTotalNetCountPartialRatio(relativeDipNetCountPartial / (relativeDipNetCountPartial + relativeNonDipNetCountPartial));

					preFellingCuttingOptions.add(preFellingCuttingOption);
				}
			}
		}

		return preFellingCuttingOptions;
	}

	public ArrayList<PreFellingReportElement> getVineSpreadthCounts()
	{
		if (vineSpreadthCounts == null)
		{
			vineSpreadthCounts = new ArrayList<>();
			ArrayList<PreFellingSurveyCard> preFellingSurveyCards = preFellingSurvey.getPreFellingSurveyCards();

			PreFellingReportElement class1Count = new PreFellingReportElement();
			PreFellingReportElement class1RelativeCount = new PreFellingReportElement();
			double[] class1CountValues = new double[6];
			double[] class1RelativeCountValues = new double[6];

			class1Count.setGroup("+ 15 - 30");
			class1Count.setCriteria("Jumlah bilangan");
			class1Count.setValues(class1CountValues);
			class1RelativeCount.setGroup("+ 15 - 30");
			class1RelativeCount.setCriteria("Bilangan sehektar");
			class1RelativeCount.setValues(class1RelativeCountValues);

			PreFellingReportElement class2Count = new PreFellingReportElement();
			PreFellingReportElement class2RelativeCount = new PreFellingReportElement();
			double[] class2CountValues = new double[6];
			double[] class2RelativeCountValues = new double[6];

			class2Count.setGroup("+ 30 - 45");
			class2Count.setCriteria("Jumlah bilangan");
			class2Count.setValues(class2CountValues);
			class2RelativeCount.setGroup("+ 30 - 45");
			class2RelativeCount.setCriteria("Bilangan sehektar");
			class2RelativeCount.setValues(class2RelativeCountValues);

			PreFellingReportElement class3Count = new PreFellingReportElement();
			PreFellingReportElement class3RelativeCount = new PreFellingReportElement();
			double[] class3CountValues = new double[33];
			double[] class3RelativeCountValues = new double[33];

			class3Count.setGroup("+ 45");
			class3Count.setCriteria("Jumlah bilangan");
			class3Count.setValues(class3CountValues);
			class3RelativeCount.setGroup("+ 45");
			class3RelativeCount.setCriteria("Bilangan sehektar");
			class3RelativeCount.setValues(class3RelativeCountValues);

			PreFellingReportElement totalCount = new PreFellingReportElement();
			PreFellingReportElement totalRelativeCount = new PreFellingReportElement();
			double[] totalCountValues = new double[6];
			double[] totalRelativeCountValues = new double[6];

			totalCount.setGroup("Jumlah besar");
			totalCount.setCriteria("Jumlah bilangan");
			totalCount.setValues(totalCountValues);
			totalRelativeCount.setGroup("Jumlah besar");
			totalRelativeCount.setCriteria("Bilangan sehektar");
			totalRelativeCount.setValues(totalRelativeCountValues);

			for (PreFellingSurveyCard preFellingSurveyCard : preFellingSurveyCards)
			{
				ArrayList<PreFellingSurveyRecord> preFellingSurveyRecords = preFellingSurveyCard.getPreFellingSurveyRecords();

				for (PreFellingSurveyRecord preFellingSurveyRecord : preFellingSurveyRecords)
				{
					int vineSpreadthID = preFellingSurveyRecord.getVineSpreadthID();

					if (vineSpreadthID < 3)
					{
						double diameter = preFellingSurveyRecord.getDiameter();
						int vineDiameter = preFellingSurveyRecord.getVineDiameter();

						if (diameter > 15 && diameter <= 30)
						{
							if (vineDiameter >= 2 && vineDiameter <= 5)
							{
								class1CountValues[vineSpreadthID == 1 ? 0 : 1]++;
								totalCountValues[vineSpreadthID == 1 ? 0 : 1]++;
							}
							else if (vineDiameter > 5)
							{
								class1CountValues[vineSpreadthID == 1 ? 2 : 3]++;
								totalCountValues[vineSpreadthID == 1 ? 2 : 3]++;
							}

							class1CountValues[vineSpreadthID == 1 ? 4 : 5]++;
							totalCountValues[vineSpreadthID == 1 ? 4 : 5]++;
						}
						else if (diameter > 30 && diameter <= 45)
						{
							if (vineDiameter >= 2 && vineDiameter <= 5)
							{
								class2CountValues[vineSpreadthID == 1 ? 0 : 1]++;
								totalCountValues[vineSpreadthID == 1 ? 0 : 1]++;
							}
							else if (vineDiameter > 5)
							{
								class2CountValues[vineSpreadthID == 1 ? 2 : 3]++;
								totalCountValues[vineSpreadthID == 1 ? 2 : 3]++;
							}

							class2CountValues[vineSpreadthID == 1 ? 4 : 5]++;
							totalCountValues[vineSpreadthID == 1 ? 4 : 5]++;
						}
						else if (diameter > 45)
						{
							if (vineDiameter >= 2 && vineDiameter <= 5)
							{
								class3CountValues[vineSpreadthID == 1 ? 0 : 1]++;
								totalCountValues[vineSpreadthID == 1 ? 0 : 1]++;
							}
							else if (vineDiameter > 5)
							{
								class3CountValues[vineSpreadthID == 1 ? 2 : 3]++;
								totalCountValues[vineSpreadthID == 1 ? 2 : 3]++;
							}

							class3CountValues[vineSpreadthID == 1 ? 4 : 5]++;
							totalCountValues[vineSpreadthID == 1 ? 4 : 5]++;
						}
					}
				}
			}

			for (int i = 0; i < 6; i++)
			{
				class1RelativeCountValues[i] = class1CountValues[i] / (preFellingSurveyCards.size() * 0.05);
				class2RelativeCountValues[i] = class2CountValues[i] / (preFellingSurveyCards.size() * 0.1);
				class3RelativeCountValues[i] = class3CountValues[i] / (preFellingSurveyCards.size() * 0.1);
				totalRelativeCountValues[i] = class1RelativeCountValues[i] + class2RelativeCountValues[i] + class3RelativeCountValues[i];
			}

			vineSpreadthCounts.add(class1Count);
			vineSpreadthCounts.add(class1RelativeCount);
			vineSpreadthCounts.add(class2Count);
			vineSpreadthCounts.add(class2RelativeCount);
			vineSpreadthCounts.add(class3Count);
			vineSpreadthCounts.add(class3RelativeCount);
			vineSpreadthCounts.add(totalCount);
			vineSpreadthCounts.add(totalRelativeCount);
		}

		return vineSpreadthCounts;
	}

	public ArrayList<PreFellingReportElement> getLogQuantityCounts()
	{
		if (logQuantityCounts == null || logQualityCounts == null)
			calculateLogCounts();

		return logQuantityCounts;
	}

	public ArrayList<PreFellingReportElement> getLogQualityCounts()
	{
		if (logQuantityCounts == null || logQualityCounts == null)
			calculateLogCounts();

		return logQualityCounts;
	}

	public ArrayList<PreFellingReportElement> getBertamPalmCounts()
	{
		if (bertamPalmCounts == null)
		{
			bertamPalmCounts = new ArrayList<>();
			ArrayList<PreFellingSurveyCard> preFellingSurveyCards = preFellingSurvey.getPreFellingSurveyCards();

			PreFellingReportElement count = new PreFellingReportElement();
			PreFellingReportElement relativeCount = new PreFellingReportElement();
			double[] countValues = new double[2];
			double[] relativeCountValues = new double[2];

			count.setCriteria("Jumlah rumpun");
			count.setValues(countValues);
			relativeCount.setCriteria("Rumpun sehektar");
			relativeCount.setValues(relativeCountValues);

			for (PreFellingSurveyCard preFellingSurveyCard : preFellingSurveyCards)
			{
				countValues[0] += preFellingSurveyCard.getBertam();
				countValues[1] += preFellingSurveyCard.getPalm();
			}

			for (int i = 0; i < 2; i++)
				relativeCountValues[i] = countValues[i] / (preFellingSurveyCards.size() * 0.1);

			bertamPalmCounts.add(count);
			bertamPalmCounts.add(relativeCount);
		}

		return bertamPalmCounts;
	}

	public ArrayList<PreFellingReportElement> getResamCounts()
	{
		if (resamCounts == null)
		{
			resamCounts = new ArrayList<>();
			ArrayList<PreFellingSurveyCard> preFellingSurveyCards = preFellingSurvey.getPreFellingSurveyCards();

			PreFellingReportElement count = new PreFellingReportElement();
			PreFellingReportElement relativeCount = new PreFellingReportElement();
			double[] countValues = new double[6];
			double[] relativeCountValues = new double[6];

			count.setCriteria("Bilangan petak diinventori");
			count.setValues(countValues);
			relativeCount.setCriteria("Peratus ditumbuhi");
			relativeCount.setValues(relativeCountValues);

			for (PreFellingSurveyCard preFellingSurveyCard : preFellingSurveyCards)
			{
				int resamID = preFellingSurveyCard.getResamID();

				if (resamID != 0)
				{
					countValues[resamID - 1]++;
					countValues[5]++;
				}
			}

			for (int i = 0; i < 6; i++)
				relativeCountValues[i] = countValues[i] * 100 / preFellingSurveyCards.size();

			resamCounts.add(count);
			resamCounts.add(relativeCount);
		}

		return resamCounts;
	}

	public ArrayList<PreFellingReportElement> getRattanBambooCounts()
	{
		if (rattanBambooCounts == null)
		{
			rattanBambooCounts = new ArrayList<>();
			ArrayList<PreFellingSurveyCard> preFellingSurveyCards = preFellingSurvey.getPreFellingSurveyCards();

			PreFellingReportElement count = new PreFellingReportElement();
			PreFellingReportElement relativeCount = new PreFellingReportElement();
			double[] countValues = new double[12];
			double[] relativeCountValues = new double[12];

			count.setCriteria("Bilangan");
			count.setValues(countValues);
			relativeCount.setCriteria("Bilangan sehektar");
			relativeCount.setValues(relativeCountValues);

			for (PreFellingSurveyCard preFellingSurveyCard : preFellingSurveyCards)
			{
				countValues[0] += preFellingSurveyCard.getRattanA();
				countValues[1] += preFellingSurveyCard.getRattanB();
				countValues[2] += preFellingSurveyCard.getRattanC();
				countValues[3] += preFellingSurveyCard.getRattanD();
				countValues[4] += preFellingSurveyCard.getRattanE();
				countValues[5] += preFellingSurveyCard.getRattanF();
				countValues[6] += preFellingSurveyCard.getRattanG();
				countValues[8] += preFellingSurveyCard.getBambooA();
				countValues[9] += preFellingSurveyCard.getBambooB();
				countValues[10] += preFellingSurveyCard.getBambooC();

				countValues[7] += preFellingSurveyCard.getRattanA();
				countValues[7] += preFellingSurveyCard.getRattanB();
				countValues[7] += preFellingSurveyCard.getRattanC();
				countValues[7] += preFellingSurveyCard.getRattanD();
				countValues[7] += preFellingSurveyCard.getRattanE();
				countValues[7] += preFellingSurveyCard.getRattanF();
				countValues[7] += preFellingSurveyCard.getRattanG();
				countValues[11] += preFellingSurveyCard.getBambooA();
				countValues[11] += preFellingSurveyCard.getBambooB();
				countValues[11] += preFellingSurveyCard.getBambooC();
			}

			for (int i = 0; i < 12; i++)
				relativeCountValues[i] = countValues[i] / (preFellingSurveyCards.size() * 0.1);

			rattanBambooCounts.add(count);
			rattanBambooCounts.add(relativeCount);
		}

		return rattanBambooCounts;
	}

	public ArrayList<PreFellingReportElement> getFertilityCounts()
	{
		if (fertilityCounts == null)
		{
			fertilityCounts = new ArrayList<>();
			ArrayList<PreFellingSurveyCard> preFellingSurveyCards = preFellingSurvey.getPreFellingSurveyCards();

			PreFellingReportElement class1Count = new PreFellingReportElement();
			PreFellingReportElement class1RelativeCount = new PreFellingReportElement();
			double[] class1CountValues = new double[5];
			double[] class1RelativeCountValues = new double[5];

			class1Count.setGroup("+ 5 - 15 cm");
			class1Count.setCriteria("Bilangan");
			class1Count.setValues(class1CountValues);
			class1RelativeCount.setGroup("+ 5 - 15 cm");
			class1RelativeCount.setCriteria("Bilangan sehektar");
			class1RelativeCount.setValues(class1RelativeCountValues);

			PreFellingReportElement class2Count = new PreFellingReportElement();
			PreFellingReportElement class2RelativeCount = new PreFellingReportElement();
			double[] class2CountValues = new double[5];
			double[] class2RelativeCountValues = new double[5];

			class2Count.setGroup("+ 15 - 30 cm");
			class2Count.setCriteria("Bilangan");
			class2Count.setValues(class2CountValues);
			class2RelativeCount.setGroup("+ 15 - 30 cm");
			class2RelativeCount.setCriteria("Bilangan sehektar");
			class2RelativeCount.setValues(class2RelativeCountValues);

			PreFellingReportElement totalCount = new PreFellingReportElement();
			PreFellingReportElement totalRelativeCount = new PreFellingReportElement();
			double[] totalCountValues = new double[5];
			double[] totalRelativeCountValues = new double[5];

			totalCount.setGroup("Jumlah besar");
			totalCount.setCriteria("Bilangan");
			totalCount.setValues(totalCountValues);
			totalRelativeCount.setGroup("Jumlah besar");
			totalRelativeCount.setCriteria("Bilangan sehektar");
			totalRelativeCount.setValues(totalRelativeCountValues);

			for (PreFellingSurveyCard preFellingSurveyCard : preFellingSurveyCards)
			{
				ArrayList<PreFellingSurveyRecord> preFellingSurveyRecords = preFellingSurveyCard.getPreFellingSurveyRecords();

				for (PreFellingSurveyRecord preFellingSurveyRecord : preFellingSurveyRecords)
				{
					double diameter = preFellingSurveyRecord.getDiameter();
					int fertilityID = preFellingSurveyRecord.getFertilityID();

					if (fertilityID != 0)
					{
						if (diameter > 5 && diameter <= 15)
						{
							class1CountValues[fertilityID - 1]++;
							totalCountValues[fertilityID - 1]++;
							class1CountValues[4]++;
							totalCountValues[4]++;
						}
						else if (diameter > 15 && diameter <= 30)
						{
							class2CountValues[fertilityID - 1]++;
							totalCountValues[fertilityID - 1]++;
							class2CountValues[4]++;
							totalCountValues[4]++;
						}
					}
				}
			}

			for (int i = 0; i < 5; i++)
			{
				class1RelativeCountValues[i] = class1CountValues[i] / (preFellingSurveyCards.size() * 0.01);
				class2RelativeCountValues[i] = class2CountValues[i] / (preFellingSurveyCards.size() * 0.05);
				totalRelativeCountValues[i] = class1RelativeCountValues[i]  + class2RelativeCountValues[i];
			}

			fertilityCounts.add(class1Count);
			fertilityCounts.add(class1RelativeCount);
			fertilityCounts.add(class2Count);
			fertilityCounts.add(class2RelativeCount);
			fertilityCounts.add(totalCount);
			fertilityCounts.add(totalRelativeCount);
		}

		return fertilityCounts;
	}

	public ArrayList<PreFellingReportElement> getSeedlingCounts()
	{
		if (seedlingCounts == null)
		{
			seedlingCounts = new ArrayList<>();
			ArrayList<PreFellingSurveyCard> preFellingSurveyCards = preFellingSurvey.getPreFellingSurveyCards();

			PreFellingReportElement count1 = new PreFellingReportElement();
			PreFellingReportElement count2 = new PreFellingReportElement();
			double[] count1Values = new double[2];
			double[] count2Values = new double[2];

			count1.setCriteria("> 1.5 m tinggi - 5 cm diameter");
			count1.setValues(count1Values);
			count2.setCriteria("> 15 cm - 150 cm tinggi");
			count2.setValues(count2Values);

			for (PreFellingSurveyCard preFellingSurveyCard : preFellingSurveyCards)
			{
				ArrayList<PreFellingSurveyRecord> preFellingSurveyRecords = preFellingSurveyCard.getPreFellingSurveyRecords();

				for (PreFellingSurveyRecord preFellingSurveyRecord : preFellingSurveyRecords)
				{
					count1Values[0] += preFellingSurveyRecord.getSaplingQuantity();
					count2Values[0] += preFellingSurveyRecord.getSeedlingQuantity();
				}
			}

			count1Values[1] += count1Values[0] / (preFellingSurveyCards.size() * 0.0025);
			count2Values[1] += count2Values[0] / (preFellingSurveyCards.size() * 0.0004);

			seedlingCounts.add(count1);
			seedlingCounts.add(count2);
		}

		return seedlingCounts;
	}

	public double getOriginalStandRatio()
	{
		return originalStandRatio;
	}

	@Override
	public String toString()
	{
		return "Laporan " + preFellingSurvey;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof PreFellingReport)
		{
			PreFellingReport that = (PreFellingReport) obj;
			equal = preFellingSurvey.equals(that.preFellingSurvey);
		}

		return equal;
	}
}