package my.edu.utem.ftmk.fis9.postfelling.model;

import java.text.DecimalFormat;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;
import my.edu.utem.ftmk.fis9.maintenance.model.RegenerationSpecies;

public class PostFellingReport extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private PostFellingSurvey postFellingSurvey;
	private String[] headers;
	private ArrayList<RegenerationSpecies> regenerationSpeciesList;
	private ArrayList<PostFellingReportElement> bertamPalmCounts;
	private ArrayList<PostFellingReportElement> resamCounts;
	private ArrayList<PostFellingReportElement> fertilityCounts;
	private ArrayList<PostFellingReportElement> seedlingCounts;
	private ArrayList<PostFellingReportElement> reportFormat1A;
	private ArrayList<PostFellingReportElement> reportFormat1B;
	private ArrayList<PostFellingReportElement> reportFormat2A;
	private ArrayList<PostFellingReportElement> reportFormat2B;
	private ArrayList<PostFellingReportElement> reportFormat2C;
	private ArrayList<PostFellingReportElement> reportFormat3A;
	private ArrayList<PostFellingReportElement> reportFormat3B;
	private ArrayList<PostFellingReportElement> reportFormat3C;
	private ArrayList<PostFellingReportElement> reportFormat4A;
	private ArrayList<PostFellingReportElement> reportFormat4B;
	private ArrayList<PostFellingReportElement> reportFormat4C;
	private ArrayList<PostFellingReportElement> reportFormat4D;
	private ArrayList<String> reportIntepretation;
	private double originalStandRatio;

	public PostFellingReport(PostFellingSurvey postFellingSurvey, ArrayList<RegenerationSpecies> regenerationSpeciesList)
	{
		this.postFellingSurvey = postFellingSurvey;
		this.regenerationSpeciesList = regenerationSpeciesList;

		headers = new String[] {
				"FORMAT 1A: Taburan Pokok-pokok Melebihi 15 cm Mengikut Status dan Kelas Kebesaran\n(Bil.: Bilangan Pokok/ha; BA: m2/ha)",
				"FORMAT 1B: Taburan Pokok Jaras Kecil (+ 5 - 15 cm Mengikut Kelas Kesuburan dan Kumpulan Jenis)\n(Bil.: Bilangan Pokok/ha; BA: m2/ha)",
				"FORMAT 2A: Taburan Pokok-pokok + 45 cm Mengikut Status dan Kelas Keunggulan\n(Bil.: Bilangan Pokok/ha; BA: m2/ha)",
				"FORMAT 2B: Taburan Pokok-pokok + 30 - 45 cm Mengikut Status dan Kelas Keunggulan\n(Bil.: Bilangan Pokok/ha)",
				"FORMAT 2C: Taburan Pokok-pokok + 15 - 30 cm Mengikut Status dan Kelas Keunggulan\n(Bil.: Bilangan Pokok/ha)",
				"FORMAT 3A: Taburan Pokok-pokok + 45 cm m Mengikut Status dan Gangguan Pepanjat\n(Bil.: Bilangan Pokok/ha)",
				"FORMAT 3B: Taburan Pokok-pokok + 30 - 45 cm Mengikut Status dan Gangguan Pepanjat\n(Bil.: Bilangan Pokok/ha)",
				"FORMAT 3C: Taburan Pokok-pokok + 15 - 30 cm Mengikut Status dan Gangguan Pepanjat\n(Bil.: Bilangan Pokok/ha)",
				"FORMAT 4A: BILANGAN DAN PERATUS PETAK DENGAN ANAK POKOK\n(+ 150 cm tinggi - 5 cm ppd)",
				"FORMAT 4B: BILANGAN DAN PERATUS PETAK DENGAN ANAK BENIH\n(+ 15 cm tinggi - 150 cm)",
				"FORMAT 4C: BILANGAN DAN PERATUS PETAK BERISI BERTAM, BULUH, PALMA",
				"FORMAT 4D: BILANGAN DAN PERATUS PETAK BERISI RESAM, HALIA DAN PISANG",
				"INTERPRETASI POST-F"};
	}

	public ArrayList<PostFellingReportElement> getReportFormat1A()
	{
		if (reportFormat1A == null)
		{
			reportFormat1A = new ArrayList<PostFellingReportElement>();
			ArrayList<PostFellingSurveyCard> postFellingSurveyCards = postFellingSurvey.getPostFellingSurveyCards();

			double ha = 0.0;
			PostFellingReportElement format1A1Bil = new PostFellingReportElement();
			PostFellingReportElement format1A1Ba = new PostFellingReportElement();
			PostFellingReportElement format1A2Bil = new PostFellingReportElement();
			PostFellingReportElement format1A2Ba = new PostFellingReportElement();
			PostFellingReportElement format1A3Bil = new PostFellingReportElement();
			PostFellingReportElement format1A3Ba = new PostFellingReportElement();
			PostFellingReportElement format1AJumlahBil = new PostFellingReportElement();
			PostFellingReportElement format1AJumlahBa = new PostFellingReportElement();

			format1A1Bil.setGroup("STATUS 1");
			format1A1Bil.setCriteria("Bil.");

			format1A1Ba.setGroup("STATUS 1");
			format1A1Ba.setCriteria("BA");

			format1A2Bil.setGroup("STATUS 2");
			format1A2Bil.setCriteria("Bil.");

			format1A2Ba.setGroup("STATUS 2");
			format1A2Ba.setCriteria("BA");

			format1A3Bil.setGroup("STATUS 3");
			format1A3Bil.setCriteria("Bil.");

			format1A3Ba.setGroup("STATUS 3");
			format1A3Ba.setCriteria("BA");

			format1AJumlahBil.setGroup("JUMLAH");
			format1AJumlahBil.setCriteria("Bil.");

			format1AJumlahBa.setGroup("JUMLAH");
			format1AJumlahBa.setCriteria("BA");

			double[] counts1 = new double[7];
			double[] ba1 = new double[7];
			double[] counts2 = new double[7];
			double[] ba2 = new double[7];
			double[] counts3 = new double[7];
			double[] ba3 = new double[7];
			double[] countsJumlah = new double[7];
			double[] baJumlah = new double[7];

			for (PostFellingSurveyCard postFellingSurveyCard : postFellingSurveyCards)
			{
				ArrayList<PostFellingSurveyRecord> postFellingSurveyRecords = postFellingSurveyCard.getPostFellingSurveyRecords();

				for (PostFellingSurveyRecord postFellingSurveyRecord : postFellingSurveyRecords)
				{

					if (postFellingSurveyRecord.getDiameter() > 15 && postFellingSurveyRecord.getDiameter() <= 30)
					{

						if (postFellingSurveyRecord.getTreeStatusID() == 1)
						{
							counts1[0]++;
							ba1[0] += postFellingSurveyRecord.getBasalArea();
						}

						if (postFellingSurveyRecord.getTreeStatusID() == 2)
						{
							counts2[0]++;
							ba2[0] += postFellingSurveyRecord.getBasalArea();
						}

						if (postFellingSurveyRecord.getTreeStatusID() == 3)
						{
							counts3[0]++;
							ba3[0] += postFellingSurveyRecord.getBasalArea();
						}
					}
					else if (postFellingSurveyRecord.getDiameter() > 30 && postFellingSurveyRecord.getDiameter() <= 45)
					{
						if (postFellingSurveyRecord.getTreeStatusID() == 1)
						{
							counts1[1]++;
							ba1[1] += postFellingSurveyRecord.getBasalArea();
						}

						if (postFellingSurveyRecord.getTreeStatusID() == 2)
						{
							counts2[1]++;
							ba2[1] += postFellingSurveyRecord.getBasalArea();
						}

						if (postFellingSurveyRecord.getTreeStatusID() == 3)
						{
							counts3[1]++;
							ba3[1] += postFellingSurveyRecord.getBasalArea();
						}
					}
					else if (postFellingSurveyRecord.getDiameter() > 45 && postFellingSurveyRecord.getDiameter() <= 60)
					{
						if (postFellingSurveyRecord.getTreeStatusID() == 1)
						{
							counts1[2]++;
							ba1[2] += postFellingSurveyRecord.getBasalArea();
						}

						if (postFellingSurveyRecord.getTreeStatusID() == 2)
						{
							counts2[2]++;
							ba2[2] += postFellingSurveyRecord.getBasalArea();
						}

						if (postFellingSurveyRecord.getTreeStatusID() == 3)
						{
							counts3[2]++;
							ba3[2] += postFellingSurveyRecord.getBasalArea();
						}
					}
					else if (postFellingSurveyRecord.getDiameter() > 60 && postFellingSurveyRecord.getDiameter() <= 75)
					{
						if (postFellingSurveyRecord.getTreeStatusID() == 1)
						{
							counts1[3]++;
							ba1[3] += postFellingSurveyRecord.getBasalArea();
						}

						if (postFellingSurveyRecord.getTreeStatusID() == 2)
						{
							counts2[3]++;
							ba2[3] += postFellingSurveyRecord.getBasalArea();
						}

						if (postFellingSurveyRecord.getTreeStatusID() == 3)
						{
							counts3[3]++;
							ba3[3] += postFellingSurveyRecord.getBasalArea();
						}
					}
					else if (postFellingSurveyRecord.getDiameter() > 75 && postFellingSurveyRecord.getDiameter() <= 90)
					{
						if (postFellingSurveyRecord.getTreeStatusID() == 1)
						{
							counts1[4]++;
							ba1[4] += postFellingSurveyRecord.getBasalArea();
						}

						if (postFellingSurveyRecord.getTreeStatusID() == 2)
						{
							counts2[4]++;
							ba2[4] += postFellingSurveyRecord.getBasalArea();
						}

						if (postFellingSurveyRecord.getTreeStatusID() == 3)
						{
							counts3[4]++;
							ba3[4] += postFellingSurveyRecord.getBasalArea();
						}
					}
					else if (postFellingSurveyRecord.getDiameter() > 90)
					{
						if (postFellingSurveyRecord.getTreeStatusID() == 1)
						{
							counts1[5]++;
							ba1[5] += postFellingSurveyRecord.getBasalArea();
						}

						if (postFellingSurveyRecord.getTreeStatusID() == 2)
						{
							counts2[5]++;
							ba2[5] += postFellingSurveyRecord.getBasalArea();
						}

						if (postFellingSurveyRecord.getTreeStatusID() == 3)
						{
							counts3[5]++;
							ba3[5] += postFellingSurveyRecord.getBasalArea();
						}
					}
				}
			}
			
			for (int i = 0; i <= 5; i++)
			{
				ha = postFellingSurveyCards.size() * ((i == 0) ?  0.05: 0.1); 
				counts1[i] = counts1[i] / ha;
				ba1[i] = ba1[i] / ha;
				counts1[6] += counts1[i];
				ba1[6] += ba1[i];

				counts2[i] = counts2[i] / ha;
				ba2[i] = ba2[i] / ha;
				counts2[6] += counts2[i];
				ba2[6] += ba2[i];

				counts3[i] = counts3[i] / ha;
				ba3[i] = ba3[i] / ha;
				counts3[6] += counts3[i];
				ba3[6] += ba3[i];

				countsJumlah[i] = counts1[i] + counts2[i] + counts3[i];
				baJumlah[i] = ba1[i] + ba2[i] + ba3[i];
			}

			countsJumlah[6] = counts1[6] + counts2[6] + counts3[6];
			baJumlah[6] = ba1[6] + ba2[6] + ba3[6];

			format1A1Bil.setValues(counts1);
			format1A1Ba.setValues(ba1);

			format1A2Bil.setValues(counts2);
			format1A2Ba.setValues(ba2);

			format1A3Bil.setValues(counts3);
			format1A3Ba.setValues(ba3);

			format1AJumlahBil.setValues(countsJumlah);
			format1AJumlahBa.setValues(baJumlah);

			reportFormat1A.add(format1A1Bil);
			reportFormat1A.add(format1A1Ba);

			reportFormat1A.add(format1A2Bil);
			reportFormat1A.add(format1A2Ba);

			reportFormat1A.add(format1A3Bil);
			reportFormat1A.add(format1A3Ba);

			reportFormat1A.add(format1AJumlahBil);
			reportFormat1A.add(format1AJumlahBa);
		}

		return reportFormat1A;
	}

	public ArrayList<PostFellingReportElement> getReportFormat1B()
	{

		if (reportFormat1B == null)
		{
			reportFormat1B = new ArrayList<PostFellingReportElement>();
			ArrayList<PostFellingSurveyCard> postFellingSurveyCards = postFellingSurvey.getPostFellingSurveyCards();
			ArrayList<Integer> rsList = new ArrayList<Integer>();

			for (int i = 0; i < regenerationSpeciesList.size(); i++)
			{
				rsList.add(regenerationSpeciesList.get(i).getSpeciesID());
			}

			double ha = postFellingSurveyCards.size() * 0.01;

			PostFellingReportElement format1B1Bil = new PostFellingReportElement();
			PostFellingReportElement format1B1Ba = new PostFellingReportElement();
			PostFellingReportElement format1B2Bil = new PostFellingReportElement();
			PostFellingReportElement format1B2Ba = new PostFellingReportElement();
			PostFellingReportElement format1B3Bil = new PostFellingReportElement();
			PostFellingReportElement format1B3Ba = new PostFellingReportElement();
			PostFellingReportElement format1BJumlahBil = new PostFellingReportElement();
			PostFellingReportElement format1BJumlahBa = new PostFellingReportElement();

			format1B1Bil.setGroup("Kesuburan 1");
			format1B1Bil.setCriteria("Bil.");

			format1B1Ba.setGroup("Kesuburan 1");
			format1B1Ba.setCriteria("BA");

			format1B2Bil.setGroup("Kesuburan 2");
			format1B2Bil.setCriteria("Bil.");

			format1B2Ba.setGroup("Kesuburan 2");
			format1B2Ba.setCriteria("BA");

			format1B3Bil.setGroup("Kesuburan 3");
			format1B3Bil.setCriteria("Bil.");

			format1B3Ba.setGroup("Kesuburan 3");
			format1B3Ba.setCriteria("BA");

			format1BJumlahBil.setGroup("JUMLAH");
			format1BJumlahBil.setCriteria("Bil.");

			format1BJumlahBa.setGroup("JUMLAH");
			format1BJumlahBa.setCriteria("BA");

			double[] counts1 = new double[3];
			double[] ba1 = new double[3];
			double[] counts2 = new double[3];
			double[] ba2 = new double[3];
			double[] counts3 = new double[3];
			double[] ba3 = new double[3];
			double[] countsJumlah = new double[3];
			double[] baJumlah = new double[3];
			
			for (PostFellingSurveyCard postFellingSurveyCard : postFellingSurveyCards)
			{
				
				ArrayList<PostFellingSurveyRecord> postFellingSurveyRecords = postFellingSurveyCard.getPostFellingSurveyRecords();
				
				
				for (PostFellingSurveyRecord postFellingSurveyRecord : postFellingSurveyRecords)
				{	
					
					if (postFellingSurveyRecord.getPlotTypeID() == 5) 
					{
						if (postFellingSurveyRecord.getFertilityID() == 1)
						{
							if (rsList.contains(postFellingSurveyRecord.getSpeciesID()))
							{
								counts1[0]++;
								ba1[0] += postFellingSurveyRecord.getBasalArea();
							}
							else
							{
								counts1[1]++;
								ba1[1] += postFellingSurveyRecord.getBasalArea();
							}
							
						}
						else if (postFellingSurveyRecord.getFertilityID() == 2)
						{
							if (rsList.contains(postFellingSurveyRecord.getSpeciesID()))
							{
								counts2[0]++;
								ba2[0] += postFellingSurveyRecord.getBasalArea();
							}
							else
							{
								counts2[1]++;
								ba2[1] += postFellingSurveyRecord.getBasalArea();
							}
							
						}
						else if (postFellingSurveyRecord.getFertilityID() == 3)
						{
							
							
							if (rsList.contains(postFellingSurveyRecord.getSpeciesID()))
							{
								counts3[0]++;
								ba3[0] += postFellingSurveyRecord.getBasalArea();
								
							}
							else
							{
								counts3[1]++;
								ba3[1] += postFellingSurveyRecord.getBasalArea();
							}
							
							
						}
					}
				}

			}

			
			
			for (int i = 0; i <= 1; i++)
			{
				counts1[i] = counts1[i] / ha;
				ba1[i] = ba1[i] / ha;
				counts1[2] += counts1[i];
				ba1[2] += ba1[i];

				counts2[i] = counts2[i] / ha;
				ba2[i] = ba2[i] / ha;
				counts2[2] += counts2[i];
				ba2[2] += ba2[i];

				counts3[i] = counts3[i] / ha;
				ba3[i] = ba3[i] / ha;
				counts3[2] += counts3[i];
				ba3[2] += ba3[i];

				countsJumlah[i] = counts1[i] + counts2[i] + counts3[i];
				baJumlah[i] = ba1[i] + ba2[i] + ba3[i];
			}

			countsJumlah[2] = counts1[2] + counts2[2] + counts3[2];
			baJumlah[2] = ba1[2] + ba2[2] + ba3[2];

			format1B1Bil.setValues(counts1);
			format1B1Ba.setValues(ba1);

			format1B2Bil.setValues(counts2);
			format1B2Ba.setValues(ba2);

			format1B3Bil.setValues(counts3);
			format1B3Ba.setValues(ba3);

			format1BJumlahBil.setValues(countsJumlah);
			format1BJumlahBa.setValues(baJumlah);

			reportFormat1B.add(format1B1Bil);
			reportFormat1B.add(format1B1Ba);

			reportFormat1B.add(format1B2Bil);
			reportFormat1B.add(format1B2Ba);

			reportFormat1B.add(format1B3Bil);
			reportFormat1B.add(format1B3Ba);

			reportFormat1B.add(format1BJumlahBil);
			reportFormat1B.add(format1BJumlahBa);
		}
		return reportFormat1B;
	}

	public ArrayList<PostFellingReportElement> getReportFormat2A()
	{
		if (reportFormat2A == null)
		{
			reportFormat2A = new ArrayList<PostFellingReportElement>();
			ArrayList<PostFellingSurveyCard> postFellingSurveyCards = postFellingSurvey.getPostFellingSurveyCards();

			double ha = postFellingSurveyCards.size() * 0.1;
			PostFellingReportElement format2A1 = new PostFellingReportElement();
			PostFellingReportElement format2A2 = new PostFellingReportElement();
			PostFellingReportElement format2A3 = new PostFellingReportElement();
			PostFellingReportElement format2AJumlah = new PostFellingReportElement();

			format2A1.setGroup("STATUS 1");
			format2A2.setGroup("STATUS 2");
			format2A3.setGroup("STATUS 3");
			format2AJumlah.setGroup("JUMLAH");

			double[] counts1 = new double[7];
			double[] counts2 = new double[7];
			double[] counts3 = new double[7];
			double[] countsJumlah = new double[7];

			for (PostFellingSurveyCard postFellingSurveyCard : postFellingSurveyCards)
			{
				ArrayList<PostFellingSurveyRecord> postFellingSurveyRecords = postFellingSurveyCard.getPostFellingSurveyRecords();

				for (PostFellingSurveyRecord postFellingSurveyRecord : postFellingSurveyRecords)
				{

					if (postFellingSurveyRecord.getDiameter() > 45)
					{

						if (postFellingSurveyRecord.getTreeStatusID() == 1)
						{
							if (postFellingSurveyRecord.getDominanceID() == 1)
								counts1[0]++;
							if (postFellingSurveyRecord.getDominanceID() == 2)
								counts1[1]++;
							if (postFellingSurveyRecord.getDominanceID() == 3)
								counts1[2]++;
							if (postFellingSurveyRecord.getDominanceID() == 4)
								counts1[3]++;
							if (postFellingSurveyRecord.getDominanceID() == 5)
								counts1[4]++;
							
						}

						if (postFellingSurveyRecord.getTreeStatusID() == 2)
						{
							if (postFellingSurveyRecord.getDominanceID() == 1)
								counts2[0]++;
							if (postFellingSurveyRecord.getDominanceID() == 2)
								counts2[1]++;
							if (postFellingSurveyRecord.getDominanceID() == 3)
								counts2[2]++;
							if (postFellingSurveyRecord.getDominanceID() == 4)
								counts2[3]++;
							if (postFellingSurveyRecord.getDominanceID() == 5)
								counts2[4]++;
							
						}

						if (postFellingSurveyRecord.getTreeStatusID() == 3)
						{
							if (postFellingSurveyRecord.getDominanceID() == 1)
								counts3[0]++;
							if (postFellingSurveyRecord.getDominanceID() == 2)
								counts3[1]++;
							if (postFellingSurveyRecord.getDominanceID() == 3)
								counts3[2]++;
							if (postFellingSurveyRecord.getDominanceID() == 4)
								counts3[3]++;
							if (postFellingSurveyRecord.getDominanceID() == 5)
								counts3[4]++;
							
						}

					}
				}
			}
			
			

			for (int i = 0; i < 5; i++)
			{
				
				counts1[i] /= ha;
				counts2[i] /= ha;
				counts3[i] /= ha;
				counts1[5] += counts1[i];
				counts2[5] += counts2[i];
				counts3[5] += counts3[i];
				countsJumlah[i] = counts1[i] + counts2[i] + counts3[i];
			}

			counts1[6] = counts1[0] + counts1[1] + counts1[3];
			counts2[6] = counts2[0] + counts2[1] + counts2[3];
			counts3[6] = counts3[0] + counts3[1] + counts3[3];
			countsJumlah[5] = counts1[5] + counts2[5] + counts3[5];
			countsJumlah[6] = counts1[6] + counts2[6] + counts3[6];
			
			

			format2A1.setValues(counts1);
			format2A2.setValues(counts2);
			format2A3.setValues(counts3);
			format2AJumlah.setValues(countsJumlah);

			reportFormat2A.add(format2A1);
			reportFormat2A.add(format2A2);
			reportFormat2A.add(format2A3);
			reportFormat2A.add(format2AJumlah);

		}

		return reportFormat2A;
	}
	
	public ArrayList<PostFellingReportElement> getReportFormat2B()
	{
		if (reportFormat2B == null)
		{
			reportFormat2B = new ArrayList<PostFellingReportElement>();
			ArrayList<PostFellingSurveyCard> postFellingSurveyCards = postFellingSurvey.getPostFellingSurveyCards();

			double ha = postFellingSurveyCards.size() * 0.1;
			PostFellingReportElement format2B1 = new PostFellingReportElement();
			PostFellingReportElement format2B2 = new PostFellingReportElement();
			PostFellingReportElement format2B3 = new PostFellingReportElement();
			PostFellingReportElement format2BJumlah = new PostFellingReportElement();

			format2B1.setGroup("STATUS 1");
			format2B2.setGroup("STATUS 2");
			format2B3.setGroup("STATUS 3");
			format2BJumlah.setGroup("JUMLAH");

			double[] counts1 = new double[7];
			double[] counts2 = new double[7];
			double[] counts3 = new double[7];
			double[] countsJumlah = new double[7];

			for (PostFellingSurveyCard postFellingSurveyCard : postFellingSurveyCards)
			{
				ArrayList<PostFellingSurveyRecord> postFellingSurveyRecords = postFellingSurveyCard.getPostFellingSurveyRecords();

				for (PostFellingSurveyRecord postFellingSurveyRecord : postFellingSurveyRecords)
				{

					if (postFellingSurveyRecord.getDiameter() > 30 && postFellingSurveyRecord.getDiameter() <= 45)
					{

						if (postFellingSurveyRecord.getTreeStatusID() == 1)
						{
							if (postFellingSurveyRecord.getDominanceID() == 1)
								counts1[0]++;
							if (postFellingSurveyRecord.getDominanceID() == 2)
								counts1[1]++;
							if (postFellingSurveyRecord.getDominanceID() == 3)
								counts1[2]++;
							if (postFellingSurveyRecord.getDominanceID() == 4)
								counts1[3]++;
							if (postFellingSurveyRecord.getDominanceID() == 5)
								counts1[4]++;
							
						}

						if (postFellingSurveyRecord.getTreeStatusID() == 2)
						{
							if (postFellingSurveyRecord.getDominanceID() == 1)
								counts2[0]++;
							if (postFellingSurveyRecord.getDominanceID() == 2)
								counts2[1]++;
							if (postFellingSurveyRecord.getDominanceID() == 3)
								counts2[2]++;
							if (postFellingSurveyRecord.getDominanceID() == 4)
								counts2[3]++;
							if (postFellingSurveyRecord.getDominanceID() == 5)
								counts2[4]++;
							
						}

						if (postFellingSurveyRecord.getTreeStatusID() == 3)
						{
							if (postFellingSurveyRecord.getDominanceID() == 1)
								counts3[0]++;
							if (postFellingSurveyRecord.getDominanceID() == 2)
								counts3[1]++;
							if (postFellingSurveyRecord.getDominanceID() == 3)
								counts3[2]++;
							if (postFellingSurveyRecord.getDominanceID() == 4)
								counts3[3]++;
							if (postFellingSurveyRecord.getDominanceID() == 5)
								counts3[4]++;
							
						}

					}
				}
			}

			for (int i = 0; i < 5; i++)
			{
				counts1[i] /= ha;
				counts2[i] /= ha;
				counts3[i] /= ha;
				counts1[5] += counts1[i];
				counts2[5] += counts2[i];
				counts3[5] += counts3[i];
				countsJumlah[i] = counts1[i] + counts2[i] + counts3[i];
			}

			counts1[6] = counts1[0] + counts1[1] + counts1[3];
			counts2[6] = counts2[0] + counts2[1] + counts2[3];
			counts3[6] = counts3[0] + counts3[1] + counts3[3];
			countsJumlah[5] = counts1[5] + counts2[5] + counts3[5];
			countsJumlah[6] = counts1[6] + counts2[6] + counts3[6];

			format2B1.setValues(counts1);
			format2B2.setValues(counts2);
			format2B3.setValues(counts3);
			format2BJumlah.setValues(countsJumlah);

			reportFormat2B.add(format2B1);
			reportFormat2B.add(format2B2);
			reportFormat2B.add(format2B3);
			reportFormat2B.add(format2BJumlah);

		}

		return reportFormat2B;
	}
	
	public ArrayList<PostFellingReportElement> getReportFormat2C()
	{
		if (reportFormat2C == null)
		{
			reportFormat2C = new ArrayList<PostFellingReportElement>();
			ArrayList<PostFellingSurveyCard> postFellingSurveyCards = postFellingSurvey.getPostFellingSurveyCards();

			double ha = postFellingSurveyCards.size() * 0.05;
			PostFellingReportElement format2C1 = new PostFellingReportElement();
			PostFellingReportElement format2C2 = new PostFellingReportElement();
			PostFellingReportElement format2C3 = new PostFellingReportElement();
			PostFellingReportElement format2CJumlah = new PostFellingReportElement();

			format2C1.setGroup("STATUS 1");
			format2C2.setGroup("STATUS 2");
			format2C3.setGroup("STATUS 3");
			format2CJumlah.setGroup("JUMLAH");

			double[] counts1 = new double[7];
			double[] counts2 = new double[7];
			double[] counts3 = new double[7];
			double[] countsJumlah = new double[7];

			for (PostFellingSurveyCard postFellingSurveyCard : postFellingSurveyCards)
			{
				ArrayList<PostFellingSurveyRecord> postFellingSurveyRecords = postFellingSurveyCard.getPostFellingSurveyRecords();

				for (PostFellingSurveyRecord postFellingSurveyRecord : postFellingSurveyRecords)
				{

					if (postFellingSurveyRecord.getDiameter() > 15 && postFellingSurveyRecord.getDiameter() <= 30)
					{

						if (postFellingSurveyRecord.getTreeStatusID() == 1)
						{
							if (postFellingSurveyRecord.getDominanceID() == 1)
								counts1[0]++;
							if (postFellingSurveyRecord.getDominanceID() == 2)
								counts1[1]++;
							if (postFellingSurveyRecord.getDominanceID() == 3)
								counts1[2]++;
							if (postFellingSurveyRecord.getDominanceID() == 4)
								counts1[3]++;
							if (postFellingSurveyRecord.getDominanceID() == 5)
								counts1[4]++;
						}

						if (postFellingSurveyRecord.getTreeStatusID() == 2)
						{
							if (postFellingSurveyRecord.getDominanceID() == 1)
								counts2[0]++;
							if (postFellingSurveyRecord.getDominanceID() == 2)
								counts2[1]++;
							if (postFellingSurveyRecord.getDominanceID() == 3)
								counts2[2]++;
							if (postFellingSurveyRecord.getDominanceID() == 4)
								counts2[3]++;
							if (postFellingSurveyRecord.getDominanceID() == 5)
								counts2[4]++;
						}

						if (postFellingSurveyRecord.getTreeStatusID() == 3)
						{
							if (postFellingSurveyRecord.getDominanceID() == 1)
								counts3[0]++;
							if (postFellingSurveyRecord.getDominanceID() == 2)
								counts3[1]++;
							if (postFellingSurveyRecord.getDominanceID() == 3)
								counts3[2]++;
							if (postFellingSurveyRecord.getDominanceID() == 4)
								counts3[3]++;
							if (postFellingSurveyRecord.getDominanceID() == 5)
								counts3[4]++;
						}

					}
				}
			}

			for (int i = 0; i < 5; i++)
			{
				counts1[i] /= ha;
				counts2[i] /= ha;
				counts3[i] /= ha;
				counts1[5] += counts1[i];
				counts2[5] += counts2[i];
				counts3[5] += counts3[i];
				countsJumlah[i] = counts1[i] + counts2[i] + counts3[i];
			}

			counts1[6] = counts1[0] + counts1[1] + counts1[3];
			counts2[6] = counts2[0] + counts2[1] + counts2[3];
			counts3[6] = counts3[0] + counts3[1] + counts3[3];
			countsJumlah[5] = counts1[5] + counts2[5] + counts3[5];
			countsJumlah[6] = counts1[6] + counts2[6] + counts3[6];

			format2C1.setValues(counts1);
			format2C2.setValues(counts2);
			format2C3.setValues(counts3);
			format2CJumlah.setValues(countsJumlah);

			reportFormat2C.add(format2C1);
			reportFormat2C.add(format2C2);
			reportFormat2C.add(format2C3);
			reportFormat2C.add(format2CJumlah);

		}

		return reportFormat2C;
	}
	
	public ArrayList<PostFellingReportElement> getReportFormat3A()
	{
		if (reportFormat3A == null)
		{
			reportFormat3A = new ArrayList<PostFellingReportElement>();
			ArrayList<PostFellingSurveyCard> postFellingSurveyCards = postFellingSurvey.getPostFellingSurveyCards();

			double ha = postFellingSurveyCards.size() * 0.1;
			PostFellingReportElement format3A1 = new PostFellingReportElement();
			PostFellingReportElement format3A2 = new PostFellingReportElement();
			PostFellingReportElement format3A3 = new PostFellingReportElement();
			PostFellingReportElement format3AJumlah = new PostFellingReportElement();

			format3A1.setGroup("STATUS 1");
			format3A2.setGroup("STATUS 2");
			format3A3.setGroup("STATUS 3");
			format3AJumlah.setGroup("JUMLAH");

			double[] counts1 = new double[13];
			double[] counts2 = new double[13];
			double[] counts3 = new double[13];
			double[] countsJumlah = new double[13];

			for (PostFellingSurveyCard postFellingSurveyCard : postFellingSurveyCards)
			{
				ArrayList<PostFellingSurveyRecord> postFellingSurveyRecords = postFellingSurveyCard.getPostFellingSurveyRecords();

				for (PostFellingSurveyRecord postFellingSurveyRecord : postFellingSurveyRecords)
				{

					if (postFellingSurveyRecord.getDiameter() > 45)
					{

						if (postFellingSurveyRecord.getTreeStatusID() == 1)
						{
							if (postFellingSurveyRecord.getVine() == 1)
								counts1[3]++;
							else if (postFellingSurveyRecord.getVine() == 2)
								counts1[4]++;
							else if (postFellingSurveyRecord.getVine() == 3)
								counts1[5]++;
							else if (postFellingSurveyRecord.getVine() == 4)
								counts1[6]++;
							else if (postFellingSurveyRecord.getVine() == 5)
								counts1[7]++;
							else if (postFellingSurveyRecord.getVine() == 6)
								counts1[8]++;
							else if (postFellingSurveyRecord.getVine() == 7)
								counts1[9]++;
							else if (postFellingSurveyRecord.getVine() == 8)
								counts1[10]++;
							else if (postFellingSurveyRecord.getVine() >= 9)
								counts1[11]++;
							else 
								{
									if (postFellingSurveyRecord.getDominanceID() == 1 || postFellingSurveyRecord.getDominanceID() == 2 || postFellingSurveyRecord.getDominanceID() == 4 )
										counts1[0]++;
									else
										counts1[1]++;
								}
							counts1[12]++;
							
						} 
						else if (postFellingSurveyRecord.getTreeStatusID() == 2)
						{
							if (postFellingSurveyRecord.getVine() == 1)
								counts2[3]++;
							else if (postFellingSurveyRecord.getVine() == 2)
								counts2[4]++;
							else if (postFellingSurveyRecord.getVine() == 3)
								counts2[5]++;
							else if (postFellingSurveyRecord.getVine() == 4)
								counts2[6]++;
							else if (postFellingSurveyRecord.getVine() == 5)
								counts2[7]++;
							else if (postFellingSurveyRecord.getVine() == 6)
								counts2[8]++;
							else if (postFellingSurveyRecord.getVine() == 7)
								counts2[9]++;
							else if (postFellingSurveyRecord.getVine() == 8)
								counts2[10]++;
							else if (postFellingSurveyRecord.getVine() >= 9)
								counts2[11]++;		
							else 
							{
								if (postFellingSurveyRecord.getDominanceID() == 1 || postFellingSurveyRecord.getDominanceID() == 2 || postFellingSurveyRecord.getDominanceID() == 4 )
									counts2[0]++;
								else
									counts2[1]++;
							}
							counts2[12]++;
						}
						else if (postFellingSurveyRecord.getTreeStatusID() == 3)
						{
							if (postFellingSurveyRecord.getVine() == 1)
								counts3[3]++;
							else if (postFellingSurveyRecord.getVine() == 2)
								counts3[4]++;
							else if (postFellingSurveyRecord.getVine() == 3)
								counts3[5]++;
							else if (postFellingSurveyRecord.getVine() == 4)
								counts3[6]++;
							else if (postFellingSurveyRecord.getVine() == 5)
								counts3[7]++;
							else if (postFellingSurveyRecord.getVine() == 6)
								counts3[8]++;
							else if (postFellingSurveyRecord.getVine() == 7)
								counts3[9]++;
							else if (postFellingSurveyRecord.getVine() == 8)
								counts3[10]++;
							else if (postFellingSurveyRecord.getVine() >= 9)
								counts3[11]++;
							else 
							{
								if (postFellingSurveyRecord.getDominanceID() == 1 || postFellingSurveyRecord.getDominanceID() == 2 || postFellingSurveyRecord.getDominanceID() == 4 )
									counts3[0]++;
								else
									counts3[1]++;
							}
							counts3[12]++;
						} 
						
					}
				}
			}

			for (int i = 0; i < 13 ; i++)
			{
				
				counts1[i] /= ha;
				counts2[i] /= ha;
				counts3[i] /= ha;
				countsJumlah[i] = counts1[i] + counts2[i] + counts3[i];		
			}
			
			counts1[2] = counts1[0] + counts1[1];
			counts2[2] = counts2[0] + counts2[1];
			counts3[2] = counts3[0] + counts3[1];
			
			countsJumlah[0] = counts1[0] + counts2[0] + counts3[0];
			countsJumlah[1] = counts1[1] + counts2[1] + counts3[1];
			countsJumlah[2] = counts1[2] + counts2[2] + counts3[2];

			format3A1.setValues(counts1);
			format3A2.setValues(counts2);
			format3A3.setValues(counts3);
			format3AJumlah.setValues(countsJumlah);

			reportFormat3A.add(format3A1);
			reportFormat3A.add(format3A2);
			reportFormat3A.add(format3A3);
			reportFormat3A.add(format3AJumlah);

		}

		return reportFormat3A;
	}
	
	public ArrayList<PostFellingReportElement> getReportFormat3B()
	{
		if (reportFormat3B == null)
		{
			reportFormat3B = new ArrayList<PostFellingReportElement>();
			ArrayList<PostFellingSurveyCard> postFellingSurveyCards = postFellingSurvey.getPostFellingSurveyCards();

			double ha = postFellingSurveyCards.size() * 0.1;
			PostFellingReportElement format3B1 = new PostFellingReportElement();
			PostFellingReportElement format3B2 = new PostFellingReportElement();
			PostFellingReportElement format3B3 = new PostFellingReportElement();
			PostFellingReportElement format3BJumlah = new PostFellingReportElement();

			format3B1.setGroup("STATUS 1");
			format3B2.setGroup("STATUS 2");
			format3B3.setGroup("STATUS 3");
			format3BJumlah.setGroup("JUMLAH");

			double[] counts1 = new double[13];
			double[] counts2 = new double[13];
			double[] counts3 = new double[13];
			double[] countsJumlah = new double[13];

			for (PostFellingSurveyCard postFellingSurveyCard : postFellingSurveyCards)
			{
				ArrayList<PostFellingSurveyRecord> postFellingSurveyRecords = postFellingSurveyCard.getPostFellingSurveyRecords();

				for (PostFellingSurveyRecord postFellingSurveyRecord : postFellingSurveyRecords)
				{

					if (postFellingSurveyRecord.getDiameter() <= 45 && postFellingSurveyRecord.getDiameter() > 30)
					{

						if (postFellingSurveyRecord.getTreeStatusID() == 1)
						{
							if (postFellingSurveyRecord.getVine() == 1)
								counts1[3]++;
							else if (postFellingSurveyRecord.getVine() == 2)
								counts1[4]++;
							else if (postFellingSurveyRecord.getVine() == 3)
								counts1[5]++;
							else if (postFellingSurveyRecord.getVine() == 4)
								counts1[6]++;
							else if (postFellingSurveyRecord.getVine() == 5)
								counts1[7]++;
							else if (postFellingSurveyRecord.getVine() == 6)
								counts1[8]++;
							else if (postFellingSurveyRecord.getVine() == 7)
								counts1[9]++;
							else if (postFellingSurveyRecord.getVine() == 8)
								counts1[10]++;
							else if (postFellingSurveyRecord.getVine() >= 9)
								counts1[11]++;
							else 
								{
									if (postFellingSurveyRecord.getDominanceID() == 1 || postFellingSurveyRecord.getDominanceID() == 2 || postFellingSurveyRecord.getDominanceID() == 4 )
										counts1[0]++;
									else
										counts1[1]++;
								}
							counts1[12]++;
							
						} 
						else if (postFellingSurveyRecord.getTreeStatusID() == 2)
						{
							if (postFellingSurveyRecord.getVine() == 1)
								counts2[3]++;
							else if (postFellingSurveyRecord.getVine() == 2)
								counts2[4]++;
							else if (postFellingSurveyRecord.getVine() == 3)
								counts2[5]++;
							else if (postFellingSurveyRecord.getVine() == 4)
								counts2[6]++;
							else if (postFellingSurveyRecord.getVine() == 5)
								counts2[7]++;
							else if (postFellingSurveyRecord.getVine() == 6)
								counts2[8]++;
							else if (postFellingSurveyRecord.getVine() == 7)
								counts2[9]++;
							else if (postFellingSurveyRecord.getVine() == 8)
								counts2[10]++;
							else if (postFellingSurveyRecord.getVine() >= 9)
								counts2[11]++;		
							else 
							{
								if (postFellingSurveyRecord.getDominanceID() == 1 || postFellingSurveyRecord.getDominanceID() == 2 || postFellingSurveyRecord.getDominanceID() == 4 )
									counts2[0]++;
								else
									counts2[1]++;
							}
							counts2[12]++;
						}
						else if (postFellingSurveyRecord.getTreeStatusID() == 3)
						{
							if (postFellingSurveyRecord.getVine() == 1)
								counts3[3]++;
							else if (postFellingSurveyRecord.getVine() == 2)
								counts3[4]++;
							else if (postFellingSurveyRecord.getVine() == 3)
								counts3[5]++;
							else if (postFellingSurveyRecord.getVine() == 4)
								counts3[6]++;
							else if (postFellingSurveyRecord.getVine() == 5)
								counts3[7]++;
							else if (postFellingSurveyRecord.getVine() == 6)
								counts3[8]++;
							else if (postFellingSurveyRecord.getVine() == 7)
								counts3[9]++;
							else if (postFellingSurveyRecord.getVine() == 8)
								counts3[10]++;
							else if (postFellingSurveyRecord.getVine() >= 9)
								counts3[11]++;
							else 
							{
								if (postFellingSurveyRecord.getDominanceID() == 1 || postFellingSurveyRecord.getDominanceID() == 2 || postFellingSurveyRecord.getDominanceID() == 4 )
									counts3[0]++;
								else
									counts3[1]++;
							}
							counts3[12]++;
						} 
						
					}
				}
			}

			for (int i = 0; i < 13 ; i++)
			{
				
				counts1[i] /= ha;
				counts2[i] /= ha;
				counts3[i] /= ha;
				countsJumlah[i] = counts1[i] + counts2[i] + counts3[i];		
			}
			
			counts1[2] = counts1[0] + counts1[1];
			counts2[2] = counts2[0] + counts2[1];
			counts3[2] = counts3[0] + counts3[1];
			
			countsJumlah[0] = counts1[0] + counts2[0] + counts3[0];
			countsJumlah[1] = counts1[1] + counts2[1] + counts3[1];
			countsJumlah[2] = counts1[2] + counts2[2] + counts3[2];

			format3B1.setValues(counts1);
			format3B2.setValues(counts2);
			format3B3.setValues(counts3);
			format3BJumlah.setValues(countsJumlah);

			reportFormat3B.add(format3B1);
			reportFormat3B.add(format3B2);
			reportFormat3B.add(format3B3);
			reportFormat3B.add(format3BJumlah);

		}

		return reportFormat3B;
	}
	
	public ArrayList<PostFellingReportElement> getReportFormat3C()
	{
		if (reportFormat3C == null)
		{
			reportFormat3C = new ArrayList<PostFellingReportElement>();
			ArrayList<PostFellingSurveyCard> postFellingSurveyCards = postFellingSurvey.getPostFellingSurveyCards();

			double ha = postFellingSurveyCards.size() * 0.05;
			PostFellingReportElement format3C1 = new PostFellingReportElement();
			PostFellingReportElement format3C2 = new PostFellingReportElement();
			PostFellingReportElement format3C3 = new PostFellingReportElement();
			PostFellingReportElement format3CJumlah = new PostFellingReportElement();

			format3C1.setGroup("STATUS 1");
			format3C2.setGroup("STATUS 2");
			format3C3.setGroup("STATUS 3");
			format3CJumlah.setGroup("JUMLAH");

			double[] counts1 = new double[13];
			double[] counts2 = new double[13];
			double[] counts3 = new double[13];
			double[] countsJumlah = new double[13];

			for (PostFellingSurveyCard postFellingSurveyCard : postFellingSurveyCards)
			{
				ArrayList<PostFellingSurveyRecord> postFellingSurveyRecords = postFellingSurveyCard.getPostFellingSurveyRecords();

				for (PostFellingSurveyRecord postFellingSurveyRecord : postFellingSurveyRecords)
				{

					if (postFellingSurveyRecord.getDiameter() <= 30 && postFellingSurveyRecord.getDiameter() > 15 )
					{

						if (postFellingSurveyRecord.getTreeStatusID() == 1)
						{
							if (postFellingSurveyRecord.getVine() == 1)
								counts1[3]++;
							else if (postFellingSurveyRecord.getVine() == 2)
								counts1[4]++;
							else if (postFellingSurveyRecord.getVine() == 3)
								counts1[5]++;
							else if (postFellingSurveyRecord.getVine() == 4)
								counts1[6]++;
							else if (postFellingSurveyRecord.getVine() == 5)
								counts1[7]++;
							else if (postFellingSurveyRecord.getVine() == 6)
								counts1[8]++;
							else if (postFellingSurveyRecord.getVine() == 7)
								counts1[9]++;
							else if (postFellingSurveyRecord.getVine() == 8)
								counts1[10]++;
							else if (postFellingSurveyRecord.getVine() >= 9)
								counts1[11]++;
							else 
								{
									if (postFellingSurveyRecord.getDominanceID() == 1 || postFellingSurveyRecord.getDominanceID() == 2 || postFellingSurveyRecord.getDominanceID() == 4 )
										counts1[0]++;
									else
										counts1[1]++;
								}
							counts1[12]++;
							
						} 
						else if (postFellingSurveyRecord.getTreeStatusID() == 2)
						{
							if (postFellingSurveyRecord.getVine() == 1)
								counts2[3]++;
							else if (postFellingSurveyRecord.getVine() == 2)
								counts2[4]++;
							else if (postFellingSurveyRecord.getVine() == 3)
								counts2[5]++;
							else if (postFellingSurveyRecord.getVine() == 4)
								counts2[6]++;
							else if (postFellingSurveyRecord.getVine() == 5)
								counts2[7]++;
							else if (postFellingSurveyRecord.getVine() == 6)
								counts2[8]++;
							else if (postFellingSurveyRecord.getVine() == 7)
								counts2[9]++;
							else if (postFellingSurveyRecord.getVine() == 8)
								counts2[10]++;
							else if (postFellingSurveyRecord.getVine() >= 9)
								counts2[11]++;		
							else 
							{
								if (postFellingSurveyRecord.getDominanceID() == 1 || postFellingSurveyRecord.getDominanceID() == 2 || postFellingSurveyRecord.getDominanceID() == 4 )
									counts2[0]++;
								else
									counts2[1]++;
							}
							counts2[12]++;
						}
						else if (postFellingSurveyRecord.getTreeStatusID() == 3)
						{
							if (postFellingSurveyRecord.getVine() == 1)
								counts3[3]++;
							else if (postFellingSurveyRecord.getVine() == 2)
								counts3[4]++;
							else if (postFellingSurveyRecord.getVine() == 3)
								counts3[5]++;
							else if (postFellingSurveyRecord.getVine() == 4)
								counts3[6]++;
							else if (postFellingSurveyRecord.getVine() == 5)
								counts3[7]++;
							else if (postFellingSurveyRecord.getVine() == 6)
								counts3[8]++;
							else if (postFellingSurveyRecord.getVine() == 7)
								counts3[9]++;
							else if (postFellingSurveyRecord.getVine() == 8)
								counts3[10]++;
							else if (postFellingSurveyRecord.getVine() >= 9)
								counts3[11]++;
							else 
							{
								if (postFellingSurveyRecord.getDominanceID() == 1 || postFellingSurveyRecord.getDominanceID() == 2 || postFellingSurveyRecord.getDominanceID() == 4 )
									counts3[0]++;
								else
									counts3[1]++;
							}
							counts3[12]++;
						} 
						
					}
				}
			}

			for (int i = 0; i < 13 ; i++)
			{
				
				counts1[i] /= ha;
				counts2[i] /= ha;
				counts3[i] /= ha;
				countsJumlah[i] = counts1[i] + counts2[i] + counts3[i];		
			}
			
			counts1[2] = counts1[0] + counts1[1];
			counts2[2] = counts2[0] + counts2[1];
			counts3[2] = counts3[0] + counts3[1];
			
			countsJumlah[0] = counts1[0] + counts2[0] + counts3[0];
			countsJumlah[1] = counts1[1] + counts2[1] + counts3[1];
			countsJumlah[2] = counts1[2] + counts2[2] + counts3[2];

			format3C1.setValues(counts1);
			format3C2.setValues(counts2);
			format3C3.setValues(counts3);
			format3CJumlah.setValues(countsJumlah);

			reportFormat3C.add(format3C1);
			reportFormat3C.add(format3C2);
			reportFormat3C.add(format3C3);
			reportFormat3C.add(format3CJumlah);

		}

		return reportFormat3C;
	}
	
	/*public ArrayList<PostFellingReportElement> getReportFormat4A()
	{
		if (reportFormat4A == null)
		{
			reportFormat4A = new ArrayList<PostFellingReportElement>();
			ArrayList<PostFellingSurveyCard> postFellingSurveyCards = postFellingSurvey.getPostFellingSurveyCards();

			
			PostFellingReportElement format4A1 = new PostFellingReportElement();
			PostFellingReportElement format4A2 = new PostFellingReportElement();
			PostFellingReportElement format4A3 = new PostFellingReportElement();
			PostFellingReportElement format4A4 = new PostFellingReportElement();
			ArrayList<Integer> rsList = new ArrayList<Integer>();

			for (int i = 0; i < regenerationSpeciesList.size(); i++)
			{
				rsList.add(regenerationSpeciesList.get(i).getSpeciesID());
			}

			format4A1.setGroup("SPESIS RS");
			format4A1.setCriteria("Bil");
			
			format4A2.setGroup("SPESIS RS");
			format4A2.setCriteria("%");
			
			format4A3.setGroup("BUKAN SPESIS RS");
			format4A3.setCriteria("Bil");
			
			format4A4.setGroup("BUKAN SPESIS RS");
			format4A4.setCriteria("%");
			

			double[] counts1Bil = new double[11];
			double[] counts1Per = new double[11];
			double[] counts2Bil = new double[11];
			double[] counts2Per = new double[11];
			double totalNotZeroRS = 0.0, totalNotZeroNonRS = 0.0;

			for (PostFellingSurveyCard postFellingSurveyCard : postFellingSurveyCards)
			{
				ArrayList<PostFellingSurveyRecord> postFellingSurveyRecords = postFellingSurveyCard.getPostFellingSurveyRecords();

				for (PostFellingSurveyRecord postFellingSurveyRecord : postFellingSurveyRecords)
				{

					if (postFellingSurveyRecord.getPlotTypeID() == 6)
					{
						
						counts1Bil[10]++;
						counts2Bil[10]++;
						
						if (postFellingSurveyRecord.getSaplingQuantity() > 0 && postFellingSurveyRecord.getSaplingQuantity() < 9)
						{
						
							if (rsList.contains(postFellingSurveyRecord.getSpeciesID())) 
							{
								counts1Bil[postFellingSurveyRecord.getSaplingQuantity()]++;
								totalNotZeroRS++;
							}
							else
							{
								counts2Bil[postFellingSurveyRecord.getSaplingQuantity()]++;
								totalNotZeroNonRS++;
							}
						} 
						else if (postFellingSurveyRecord.getSaplingQuantity() >= 9)
						{
							if (rsList.contains(postFellingSurveyRecord.getSpeciesID())) 
							{
								counts1Bil[9]++;
								totalNotZeroRS++;
							}
							else
							{
								counts2Bil[9]++;
								totalNotZeroNonRS++;
							}
						}
											
					}			
				}
			}
			
			counts1Bil[0] = counts1Bil[10]- totalNotZeroRS;
			counts2Bil[0] = counts2Bil[10]- totalNotZeroNonRS;
			
			for (int i = 0; i < 10; i++)
			{
				if (counts1Bil[10]>0) counts1Per[i] = (counts1Bil[i]/counts1Bil[10])*100;
				if (counts2Bil[10]>0) counts2Per[i] = (counts2Bil[i]/counts2Bil[10])*100;
			}
			
			counts1Per[10]=100;
			counts2Per[10]=100;
			
			format4A1.setValues(counts1Bil);
			format4A2.setValues(counts1Per);
			format4A3.setValues(counts2Bil);
			format4A4.setValues(counts2Per);
			
			
			reportFormat4A.add(format4A1);
			reportFormat4A.add(format4A2);
			reportFormat4A.add(format4A3);
			reportFormat4A.add(format4A4);

		}

		return reportFormat4A;
	}*/
	
	public ArrayList<PostFellingReportElement> getReportFormat4A()
	{
		if (reportFormat4A == null)
		{
			reportFormat4A = new ArrayList<PostFellingReportElement>();
			ArrayList<PostFellingSurveyCard> postFellingSurveyCards = postFellingSurvey.getPostFellingSurveyCards();

			
			PostFellingReportElement format4A1 = new PostFellingReportElement();
			PostFellingReportElement format4A2 = new PostFellingReportElement();
			PostFellingReportElement format4A3 = new PostFellingReportElement();
			PostFellingReportElement format4A4 = new PostFellingReportElement();
			ArrayList<Integer> rsList = new ArrayList<Integer>();

			for (int i = 0; i < regenerationSpeciesList.size(); i++)
			{
				rsList.add(regenerationSpeciesList.get(i).getSpeciesID());
			}

			format4A1.setGroup("SPESIS RS");
			format4A1.setCriteria("Bil");
			
			format4A2.setGroup("SPESIS RS");
			format4A2.setCriteria("%");
			
			format4A3.setGroup("BUKAN SPESIS RS");
			format4A3.setCriteria("Bil");
			
			format4A4.setGroup("BUKAN SPESIS RS");
			format4A4.setCriteria("%");
			

			double[] counts1Bil = new double[11];
			double[] counts1Per = new double[11];
			double[] counts2Bil = new double[11];
			double[] counts2Per = new double[11];

			for (PostFellingSurveyCard postFellingSurveyCard : postFellingSurveyCards)
			{
				ArrayList<PostFellingSurveyRecord> postFellingSurveyRecords = postFellingSurveyCard.getPostFellingSurveyRecords();
				int saplingQuantityRS = 0, saplingQuantityNotRS = 0;
				for (PostFellingSurveyRecord postFellingSurveyRecord : postFellingSurveyRecords)
				{

					if (postFellingSurveyRecord.getPlotTypeID() == 6)
					{
						if (postFellingSurveyRecord.getSaplingQuantity() >= 0 &&  postFellingSurveyRecord.getSaplingQuantity() <= 9)
						{
						
							if (rsList.contains(postFellingSurveyRecord.getSpeciesID())) 
							{
								saplingQuantityRS += postFellingSurveyRecord.getSaplingQuantity();
							}
							else
							{
								saplingQuantityNotRS += postFellingSurveyRecord.getSaplingQuantity();
							}
						} 
						else if (postFellingSurveyRecord.getSaplingQuantity() > 9)
						{
							if (rsList.contains(postFellingSurveyRecord.getSpeciesID())) 
							{
								saplingQuantityRS += 9;
							}
							else
							{
								saplingQuantityNotRS += 9;
							}
						}
											
					}			
				}
				
				
				if (saplingQuantityRS<=9)
					counts1Bil[saplingQuantityRS]++;
				else
					counts1Bil[9]++;
				
				if (saplingQuantityNotRS<=9)
					counts2Bil[saplingQuantityNotRS]++;
				else
					counts2Bil[9]++;
			}
			
			counts1Bil[10] = counts2Bil[10] = postFellingSurveyCards.size();
			for (int i = 0; i < 10; i++)
			{
				
				counts1Per[i] = (counts1Bil[i]/counts1Bil[10])*100;
				counts2Per[i] = (counts2Bil[i]/counts2Bil[10])*100;
			}
			
			counts1Per[10]=100;
			counts2Per[10]=100;
			
			format4A1.setValues(counts1Bil);
			format4A2.setValues(counts1Per);
			format4A3.setValues(counts2Bil);
			format4A4.setValues(counts2Per);
			
			reportFormat4A.add(format4A1);
			reportFormat4A.add(format4A2);
			reportFormat4A.add(format4A3);
			reportFormat4A.add(format4A4);

		}

		return reportFormat4A;
	}
	
	public ArrayList<PostFellingReportElement> getReportFormat4B()
	{
		if (reportFormat4B == null)
		{
			reportFormat4B = new ArrayList<PostFellingReportElement>();
			ArrayList<PostFellingSurveyCard> postFellingSurveyCards = postFellingSurvey.getPostFellingSurveyCards();

			
			PostFellingReportElement format4B1 = new PostFellingReportElement();
			PostFellingReportElement format4B2 = new PostFellingReportElement();
			PostFellingReportElement format4B3 = new PostFellingReportElement();
			PostFellingReportElement format4B4 = new PostFellingReportElement();
			ArrayList<Integer> rsList = new ArrayList<Integer>();

			for (int i = 0; i < regenerationSpeciesList.size(); i++)
			{
				rsList.add(regenerationSpeciesList.get(i).getSpeciesID());
			}

			format4B1.setGroup("SPESIS RS");
			format4B1.setCriteria("Bil");
			
			format4B2.setGroup("SPESIS RS");
			format4B2.setCriteria("%");
			
			format4B3.setGroup("BUKAN SPESIS RS");
			format4B3.setCriteria("Bil");
			
			format4B4.setGroup("BUKAN SPESIS RS");
			format4B4.setCriteria("%");
			

			double[] counts1Bil = new double[11];
			double[] counts1Per = new double[11];
			double[] counts2Bil = new double[11];
			double[] counts2Per = new double[11];

			for (PostFellingSurveyCard postFellingSurveyCard : postFellingSurveyCards)
			{
				ArrayList<PostFellingSurveyRecord> postFellingSurveyRecords = postFellingSurveyCard.getPostFellingSurveyRecords();
				int seedlingQuantityRS = 0, seedlingQuantityNotRS = 0;
				for (PostFellingSurveyRecord postFellingSurveyRecord : postFellingSurveyRecords)
				{

					if (postFellingSurveyRecord.getPlotTypeID() == 6)
					{
						if (postFellingSurveyRecord.getSeedlingQuantity() >= 0 &&  postFellingSurveyRecord.getSeedlingQuantity() <= 9)
						{
						
							if (rsList.contains(postFellingSurveyRecord.getSpeciesID())) 
							{
								seedlingQuantityRS += postFellingSurveyRecord.getSeedlingQuantity();
							}
							else
							{
								seedlingQuantityNotRS += postFellingSurveyRecord.getSeedlingQuantity();
							}
						} 
						else if (postFellingSurveyRecord.getSeedlingQuantity() > 9)
						{
							if (rsList.contains(postFellingSurveyRecord.getSpeciesID())) 
							{
								seedlingQuantityRS += 9;
							}
							else
							{
								seedlingQuantityNotRS += 9;
							}
						}
											
					}			
				}	
				
				if (seedlingQuantityRS<=9)
					counts1Bil[seedlingQuantityRS]++;
				else
					counts1Bil[9]++;
				
				if (seedlingQuantityNotRS<=9)
					counts2Bil[seedlingQuantityNotRS]++;
				else
					counts2Bil[9]++;
			}
			
			counts1Bil[10] = counts2Bil[10] = postFellingSurveyCards.size();
			for (int i = 0; i < 10; i++)
			{
				
				counts1Per[i] = (counts1Bil[i]/counts1Bil[10])*100;
				counts2Per[i] = (counts2Bil[i]/counts2Bil[10])*100;
			}
			
			counts1Per[10]=100;
			counts2Per[10]=100;
			
			format4B1.setValues(counts1Bil);
			format4B2.setValues(counts1Per);
			format4B3.setValues(counts2Bil);
			format4B4.setValues(counts2Per);
			
			reportFormat4B.add(format4B1);
			reportFormat4B.add(format4B2);
			reportFormat4B.add(format4B3);
			reportFormat4B.add(format4B4);

		}

		return reportFormat4B;
	}
	
	public ArrayList<PostFellingReportElement> getReportFormat4C()
	{
		if (reportFormat4C == null)
		{
			reportFormat4C = new ArrayList<PostFellingReportElement>();
			ArrayList<PostFellingSurveyCard> postFellingSurveyCards = postFellingSurvey.getPostFellingSurveyCards();

			
			PostFellingReportElement format4C1 = new PostFellingReportElement();
			PostFellingReportElement format4C2 = new PostFellingReportElement();
			PostFellingReportElement format4C3 = new PostFellingReportElement();
			PostFellingReportElement format4C4 = new PostFellingReportElement();
			PostFellingReportElement format4C5 = new PostFellingReportElement();
			PostFellingReportElement format4C6 = new PostFellingReportElement();
			
			format4C1.setGroup("BERTAM");
			format4C1.setCriteria("Bil");
			
			format4C2.setGroup("BERTAM");
			format4C2.setCriteria("%");
			
			format4C3.setGroup("BULUH");
			format4C3.setCriteria("Bil");
			
			format4C4.setGroup("BULUH");
			format4C4.setCriteria("%");
			
			format4C5.setGroup("PALMA");
			format4C5.setCriteria("Bil");
			
			format4C6.setGroup("PALMA");
			format4C6.setCriteria("%");
			

			double[] counts1Bil = new double[11];
			double[] counts1Per = new double[11];
			double[] counts2Bil = new double[11];
			double[] counts2Per = new double[11];
			double[] counts3Bil = new double[11];
			double[] counts3Per = new double[11];
			

			for (PostFellingSurveyCard postFellingSurveyCard : postFellingSurveyCards)
			{
				
				if (postFellingSurveyCard.getBertam() < 9)
					counts1Bil[postFellingSurveyCard.getBertam()]++;
				else
					counts1Bil[9]++;
				
				if (postFellingSurveyCard.getBamboo() < 9)
					counts2Bil[postFellingSurveyCard.getBamboo()]++;
				else
					counts2Bil[9]++;
				
				if (postFellingSurveyCard.getPalm() < 9)
					counts3Bil[postFellingSurveyCard.getPalm()]++;
				else
					counts3Bil[9]++;		
				
			}
			
			counts1Bil[10] = counts2Bil[10] = counts3Bil[10] = postFellingSurveyCards.size();			
			counts1Per[10] = counts2Per[10] = counts3Per[10] = 100;
			
			for (int i = 0; i < 10; i++)
			{
				if (counts1Bil[10]>0) counts1Per[i] = (counts1Bil[i]/counts1Bil[10])*100;
				if (counts2Bil[10]>0) counts2Per[i] = (counts2Bil[i]/counts2Bil[10])*100;
				if (counts3Bil[10]>0) counts3Per[i] = (counts3Bil[i]/counts3Bil[10])*100;
				
			}
			
			
			format4C1.setValues(counts1Bil);
			format4C2.setValues(counts1Per);
			format4C3.setValues(counts2Bil);
			format4C4.setValues(counts2Per);
			format4C5.setValues(counts3Bil);
			format4C6.setValues(counts3Per);
			
			
			reportFormat4C.add(format4C1);
			reportFormat4C.add(format4C2);
			reportFormat4C.add(format4C3);
			reportFormat4C.add(format4C4);
			reportFormat4C.add(format4C5);
			reportFormat4C.add(format4C6);

		}

		return reportFormat4C;
	}
	
	public ArrayList<PostFellingReportElement> getReportFormat4D()
	{
		if (reportFormat4D == null)
		{
			reportFormat4D = new ArrayList<PostFellingReportElement>();
			ArrayList<PostFellingSurveyCard> postFellingSurveyCards = postFellingSurvey.getPostFellingSurveyCards();

			
			PostFellingReportElement format4D1 = new PostFellingReportElement();
			PostFellingReportElement format4D2 = new PostFellingReportElement();
			PostFellingReportElement format4D3 = new PostFellingReportElement();
			PostFellingReportElement format4D4 = new PostFellingReportElement();
			PostFellingReportElement format4D5 = new PostFellingReportElement();
			PostFellingReportElement format4D6 = new PostFellingReportElement();

			format4D1.setGroup("RESAM");
			format4D1.setCriteria("Bil");
			
			format4D2.setGroup("RESAM");
			format4D2.setCriteria("%");
			
			format4D3.setGroup("HALIA");
			format4D3.setCriteria("Bil");
			
			format4D4.setGroup("HALIA");
			format4D4.setCriteria("%");
			
			format4D5.setGroup("PISANG");
			format4D5.setCriteria("Bil");
			
			format4D6.setGroup("PISANG");
			format4D6.setCriteria("%");
			
			double[] counts1Bil = new double[6];
			double[] counts1Per = new double[6];
			double[] counts2Bil = new double[6];
			double[] counts2Per = new double[6];
			double[] counts3Bil = new double[6];
			double[] counts3Per = new double[6];
			

			for (PostFellingSurveyCard postFellingSurveyCard : postFellingSurveyCards)
			{	
				if (postFellingSurveyCard.getResamID() > 0 &&  postFellingSurveyCard.getGingerID() > 0 && postFellingSurveyCard.getBananaID()>0)
				{
						counts1Bil[postFellingSurveyCard.getResamID()-1]++;
						counts2Bil[postFellingSurveyCard.getGingerID()-1]++;
						counts3Bil[postFellingSurveyCard.getBananaID()-1]++;
				}
							
			}
			
			counts1Bil[5] = counts2Bil[5] = counts3Bil[5] = postFellingSurveyCards.size();			
			counts1Per[5] = counts2Per[5] = counts3Per[5] = 100;
			
			for (int i = 0; i < 6; i++)
			{
				if (counts1Bil[5]>0) counts1Per[i] = (counts1Bil[i]/counts1Bil[5])*100;
				if (counts2Bil[5]>0) counts2Per[i] = (counts2Bil[i]/counts2Bil[5])*100;
				if (counts3Bil[5]>0) counts3Per[i] = (counts3Bil[i]/counts3Bil[5])*100;
				
				//System.out.println("BANANA:" + counts3Bil[i]);
				
				
			}
					
			format4D1.setValues(counts1Bil);
			format4D2.setValues(counts1Per);
			format4D3.setValues(counts2Bil);
			format4D4.setValues(counts2Per);
			format4D5.setValues(counts3Bil);
			format4D6.setValues(counts3Per);
			
			
			reportFormat4D.add(format4D1);
			reportFormat4D.add(format4D2);
			reportFormat4D.add(format4D3);
			reportFormat4D.add(format4D4);
			reportFormat4D.add(format4D5);
			reportFormat4D.add(format4D6);

		}

		return reportFormat4D;
	}
	
	public ArrayList<String> getReportIntepretation()
	{
		
		if (reportIntepretation == null)
		{
			reportIntepretation = new ArrayList<String>();
			DecimalFormat df = new DecimalFormat("0.00");
			
			double format1A1 = 0.0, format1A2 = 0.0, format1A3 = 0.0, format1A1B = 0.0, format2A = 0.0, format2A2B = 0.0, format2B2C = 0.0, format3A1 = 0.0, format3A3B1 = 0.0, format3A2 = 0.0, format3A3B2 = 0.0, format3B3C1 = 0.0, format3B3C2 = 0.0, format1A = 0.0, count1A3 = 0.0, format4A = 0.0, format4B = 0.0, format4C = 0.0, format4D = 0.0, resam, halia, pisang;
			String format1A1Str, format1Str, format2A2BStr, format3A3B1Str,format3A3B2Str, format1A1BStr, format1A3Str;
			ArrayList<PostFellingReportElement> reportFormat1A =  getReportFormat1A();
			ArrayList<PostFellingReportElement> reportFormat1B =  getReportFormat1B();
			ArrayList<PostFellingReportElement> reportFormat2A =  getReportFormat2A();
			ArrayList<PostFellingReportElement> reportFormat2B =  getReportFormat2B();
			ArrayList<PostFellingReportElement> reportFormat2C =  getReportFormat2C();
			ArrayList<PostFellingReportElement> reportFormat3A =  getReportFormat3A();
			ArrayList<PostFellingReportElement> reportFormat3B =  getReportFormat3B();
			ArrayList<PostFellingReportElement> reportFormat3C =  getReportFormat3C();
			ArrayList<PostFellingReportElement> reportFormat4A =  getReportFormat4A();
			ArrayList<PostFellingReportElement> reportFormat4B =  getReportFormat4B();
			ArrayList<PostFellingReportElement> reportFormat4C =  getReportFormat4C();
			ArrayList<PostFellingReportElement> reportFormat4D =  getReportFormat4D();
			
			format1A1 = reportFormat1A.get(0).getValues()[2] + reportFormat1A.get(0).getValues()[3] + reportFormat1A.get(0).getValues()[4] + reportFormat1A.get(0).getValues()[5];
			format1A1Str = df.format(reportFormat1A.get(0).getValues()[2]) + " + " + df.format(reportFormat1A.get(0).getValues()[3]) + " + " +  df.format(reportFormat1A.get(0).getValues()[4]) + " + " +  df.format(reportFormat1A.get(0).getValues()[5]);
			reportIntepretation.add("Dari FORMAT 1A");
			
			reportIntepretation.add("Isi Kandungan +45 cm, Status 1");
			reportIntepretation.add("= "+ format1A1Str);
			reportIntepretation.add("= " +  df.format(format1A1) +" pokok/ha "+ (format1A1 > 25 ? "(bermakna > 25 pokok/ha)": " (bermakna < 25 pokok/ha)"));
			reportIntepretation.add("");
			
			format1A2 = (format1A1*2) + reportFormat1A.get(0).getValues()[1];	
			format1Str = df.format(reportFormat1A.get(0).getValues()[1]) +" + "+ df.format(format1A1)+" (2)";
			reportIntepretation.add("Isi Kandungan +30 - 45 cm, Status 1 dan");
			reportIntepretation.add("+ 45cm, Status 1 yang setara dengannya");
			reportIntepretation.add("= " + format1Str);
			reportIntepretation.add("= " + df.format(format1A2) + " pokok/ha "+ (format1A2 > 32 ? " (bermakna > 32 pokok/ha)": "(bermakna < 32 pokok/ha)"));
			
			reportIntepretation.add("");
			
			format1A3 = reportFormat1A.get(0).getValues()[0] + (reportFormat1A.get(0).getValues()[1] * 3);
			reportIntepretation.add("Isi Kandungan +15 - 30 cm, Status 1 dan");
			reportIntepretation.add("+ 30 - 45 cm, Status 1 yang setara dengannya");
			reportIntepretation.add("= "+ df.format(reportFormat1A.get(0).getValues()[0]) +" + "+ df.format(reportFormat1A.get(0).getValues()[1]) + " (3)");
			reportIntepretation.add("= "+  df.format(format1A3) + (format1A3 > 100 ? " (bermakna > 100 pokok/ha)": "(bermakna < 100 pokok/ha)"));	
	
			
			if (format1A1 >= 25) 
			{
				
				format2A = reportFormat2A.get(0).getValues()[6];	// kelas unggul D C1 S1
				format3A1 = reportFormat3A.get(0).getValues()[2]; //tiada gangguan pepanjat
				format3A2 = reportFormat3A.get(0).getValues()[0]; // tiada gangguan pepanjat dan kelas unggul D C1 S1
				reportIntepretation.add("");
				reportIntepretation.add("Dari FORMAT 2A");
				reportIntepretation.add("Isi Kandungan + 45 cm, Status 1 dan kelas Unggul D, C1, S1 = "+ df.format(format2A) + (format2A > 25 ? " (> 25 pokok/ha)": " (< 25 pokok/ha)"));				
				reportIntepretation.add("");
				reportIntepretation.add("Dari FORMAT 3A");
				reportIntepretation.add("Isi Kandungan + 45 cm, Status 1 dan tiada gangguan pepanjat serta mempunyai kelas unggul D, C1, S1");
				reportIntepretation.add("= "+ df.format(format3A2) + (format3A2 > 25 ? " (> 25 pokok/ha)": " (< 25 pokok/ha)"));
				reportIntepretation.add("");
				reportIntepretation.add("Isi Kandungan + 45 cm, Status 1 dan tiada gangguan pepanjat keseluruhan");
				reportIntepretation.add("= "+ df.format(format3A1) + (format3A1 > 25 ? " (> 25 pokok/ha)": " (< 25 pokok/ha)"));
				
				reportIntepretation.add("Bandingkan JADUAL A (Kategori + 45 cm)");
				reportIntepretation.add("");
				reportIntepretation.add("Bilangan yang setara (Format 1)");
				reportIntepretation.add("= > 25 pokok/ha");
				reportIntepretation.add("");
				reportIntepretation.add("Tiada Gangguan Naungan (Kelas Unggul D, C1, S2) (FORMAT 2)");
				reportIntepretation.add("= "+ (format2A > 25 ? " > 25 pokok/ha": "< 25 pokok/ha)"));
				reportIntepretation.add("");
				
				if (format2A >= 25 && format3A2 >=25)
				{
					reportIntepretation.add("Tiada Gangguan Pepanjat dari kelas unggul D, C1, S1");
					reportIntepretation.add("= > 25 pokok/ha");
					
					reportIntepretation.add("");
					reportIntepretation.add("Kaedah Rawatan: Tiada Rawatan");
					reportIntepretation.add("Status Kompartmen: Terpulih");
				}
				else if  (format2A < 25 && format3A1 >= 25)
				{
					reportIntepretation.add("Tiada Gangguan Pepanjat");
					reportIntepretation.add("= > 25 pokok/ha");
					reportIntepretation.add("");
					reportIntepretation.add("Kaedah Rawatan: Rawatan G");
					reportIntepretation.add("Status Kompartmen: Belum Terpulih");
					
				} else if  (format2A >= 25 && format3A2 < 25)
				{
					
					
					reportIntepretation.add("Tiada Gangguan Pepanjat dari kelas unggul D, C1, S1");
					reportIntepretation.add("= < 25 pokok/ha");
					reportIntepretation.add("");
					reportIntepretation.add("Kaedah Rawatan: Rawatan CL");
					reportIntepretation.add("Status Kompartmen: Belum Terpulih");
					
				} else if  (format2A < 25 && format3A1 < 25)
				{	
				
					reportIntepretation.add("Tiada Gangguan Pepanjat");
					reportIntepretation.add("= < 25 pokok/ha");
					reportIntepretation.add("");
					reportIntepretation.add("Kaedah Rawatan: Rawatan GCL");
					reportIntepretation.add("Status Kompartmen: Belum Terpulih");
					
				}			
			} 
			else //	format1A1 < 25
			{ 
				if (format1A2 >= 32)
				{
					format2A2B = reportFormat2A.get(0).getValues()[6] + reportFormat2B.get(0).getValues()[6]; //status 1 dan kelas unggul D, C1, S1
					format2A2BStr = df.format(reportFormat2A.get(0).getValues()[6])+" + "+df.format(reportFormat2B.get(0).getValues()[6]);
					
					format3A3B1 = reportFormat3A.get(0).getValues()[2] + reportFormat3B.get(0).getValues()[2]; // status 1 dan tiada gangguan pepanjat
					format3A3B1Str = df.format(reportFormat3A.get(0).getValues()[2]) +" + "+ df.format(reportFormat3B.get(0).getValues()[2]);
					
					format3A3B2 = reportFormat3A.get(0).getValues()[0] + reportFormat3B.get(0).getValues()[0]; //  tiada gangguan pepanjat dan kelas unggul D C1 S1
					format3A3B2Str = df.format(reportFormat3A.get(0).getValues()[0]) +" + "+  df.format(reportFormat3B.get(0).getValues()[0]);
					
					reportIntepretation.add("");
					reportIntepretation.add("Dari FORMAT 2B dan 2A");
					reportIntepretation.add("Isi kandungan +30 - 45 cm, Status 1 dan kelas Unggul D, C1, S1  dan");
					reportIntepretation.add("+ 45 cm, Status 1 dan kelas Unggul D, C1, S1 yang setara dengannya");
					reportIntepretation.add("= "+ format2A2BStr +" pokok/ha");
					reportIntepretation.add("= "+  df.format(format2A2B) + (format2A2B > 32 ? " ( > 32 pokok/ha)": " (< 32 pokok/ha)"));
					reportIntepretation.add("");
					reportIntepretation.add("Dari FORMAT 3B dan 3A");
					reportIntepretation.add("Isi kandungan + 30 - 45 cm, Status 1 dan tiada gangguan pepanjat keseluruhan");
					reportIntepretation.add("+ 45 cm, Status 1, Status 1 dan tiada gangguan pepanjat keseluruhan");
					reportIntepretation.add("= "+ format3A3B1Str);
					reportIntepretation.add("= "+  df.format(format3A3B1) + " pokok/ha" +  (format3A3B1 > 32 ? " (> 32 pokok/ha)": " (< 32 pokok/ha)"));
					reportIntepretation.add("");
					
					reportIntepretation.add("Isi kandungan + 30 - 45 cm, Status 1 dan tiada gangguan pepanjat serta mempunyai kelas unggul D, C1, S1 yang setara dengannya");
					reportIntepretation.add("+ 45 cm, Status 1, Status 1 dan tiada gangguan pepanjat  serta mempunyai kelas unggul D, C1, S1 yang setara dengannya");
					reportIntepretation.add("= "+ format3A3B2Str);
					reportIntepretation.add("= "+  df.format(format3A3B2) + " pokok/ha" +  (format3A3B2 > 32 ? " (> 32 pokok/ha)": " (< 32 pokok/ha)"));
					reportIntepretation.add("");
					reportIntepretation.add("Bandingkan JADUAL A (Kategori + 30 - 45 cm)");
					reportIntepretation.add("");
					reportIntepretation.add("Bilangan yang setara (FORMAT 1)");
					reportIntepretation.add("= > 32 pokok/ha");
					reportIntepretation.add("");
					reportIntepretation.add("Tiada Gangguan Naungan (Kelas Unggul D, C1, S2) (FORMAT 2)");
					reportIntepretation.add("= "+ (format2A2B > 32 ? " > 32 pokok/ha": "< 32 pokok/ha)"));
					reportIntepretation.add("");
					
					if (format2A2B >= 32 && format3A3B2 >= 32)
					{
						reportIntepretation.add("Tiada Gangguan Pepanjat dari kelas unggul D, C1, S1");
						reportIntepretation.add("= > 32 pokok/ha");
						reportIntepretation.add("");
						reportIntepretation.add("Kaedah Rawatan: Tiada Rawatan");
						reportIntepretation.add("Status Kompartmen: Terpulih");
					}
					else if  (format2A2B < 32 && format3A3B1 >= 32)
					{
						
						reportIntepretation.add("Tiada Gangguan Pepanjat");
						reportIntepretation.add("= > 32 pokok/ha");
						reportIntepretation.add("");
						reportIntepretation.add("Kaedah Rawatan: Rawatan G");
						reportIntepretation.add("Status Kompartmen: Belum Terpulih");
						
					}
					
					else if  (format2A2B >= 32 && format3A3B2 < 32)
					{
						reportIntepretation.add("Tiada Gangguan Pepanjat dari kelas unggul D, C1, S1");
						reportIntepretation.add("= < 32 pokok/ha");
						reportIntepretation.add("");
						reportIntepretation.add("Kaedah Rawatan: Rawatan CL");
						reportIntepretation.add("Status Kompartmen: Belum Terpulih");
						
					}
					else if  (format2A2B < 32 && format3A3B1 < 32)
					{
						
						reportIntepretation.add("Tiada Gangguan Pepanjat");
						reportIntepretation.add("= < 32 pokok/ha");
						reportIntepretation.add("");
						reportIntepretation.add("Kaedah Rawatan: Rawatan GCL");
						reportIntepretation.add("Status Kompartmen: Belum Terpulih");
						
					}		
						
				}
				else // <32
				{
					if (format1A3 >= 100)
					{
						
						
						format2B2C = reportFormat2B.get(0).getValues()[6] + reportFormat2C.get(0).getValues()[6]; 
						format3B3C1 = reportFormat3B.get(0).getValues()[2] + reportFormat3C.get(0).getValues()[2];
						format3B3C2 = reportFormat3B.get(0).getValues()[0] + reportFormat3C.get(0).getValues()[0];
						
						reportIntepretation.add("");
						reportIntepretation.add("Dari FORMAT 2C dan 2B");
						reportIntepretation.add("Isi kandungan + 15 - 30 cm, Status 1 dan kelas Unggul D, C1, S1 dan");
						reportIntepretation.add("+ 30 - 45 cm, Status 1 dan kelas Unggul D, C1, S1 yang setara dengannya");
						reportIntepretation.add("= "+ df.format(reportFormat2C.get(0).getValues()[6]) + " + "+ df.format(reportFormat2B.get(0).getValues()[6]));
						reportIntepretation.add("= "+  df.format(format2B2C) + (format2B2C > 100 ? " (> 100 pokok/ha)": " (< 100 pokok/ha)"));
						reportIntepretation.add("");
						reportIntepretation.add("Dari FORMAT 3C dan 3B");
						
						reportIntepretation.add("Isi kandungan + 15 - 30 cm, Status 1, tiada gangguan pepanjat  dan");
						reportIntepretation.add("+ 30 - 45 cm, Status 1, tiada gangguan pepanjat yang setara dengannya");
						reportIntepretation.add("= "+ df.format(reportFormat3C.get(0).getValues()[2]) + " + "+ df.format(reportFormat3B.get(0).getValues()[2]));
						reportIntepretation.add("= "+  df.format(format3B3C1) + (format3B3C1 > 100 ? " (> 100 pokok/ha)": " (< 100 pokok/ha)"));
						reportIntepretation.add("");
						
						reportIntepretation.add("Isi kandungan + 15 - 30 cm, Status 1, tiada gangguan pepanjat serta mempunyai kelas unggul D, C1, S1 dan");
						reportIntepretation.add("+ 30 - 45 cm, Status 1, tiada gangguan pepanjat serta mempunyai kelas unggul D, C1, S1 yang setara dengannya");
						reportIntepretation.add("= "+ df.format(reportFormat3C.get(0).getValues()[0]) + " + "+ df.format(reportFormat3B.get(0).getValues()[0]));
						reportIntepretation.add("= "+  df.format(format3B3C2) + (format3B3C2 > 100 ? " (> 100 pokok/ha)": " (< 100 pokok/ha)"));
						reportIntepretation.add("");
						
						reportIntepretation.add("Bandingkan JADUAL A (Kategori + 15 - 30 cm)");
						reportIntepretation.add("");
						reportIntepretation.add("Bilangan yang setara (Format 1)");
						reportIntepretation.add("= > 100 pokok/ha");
						reportIntepretation.add("");
						reportIntepretation.add("Tiada Gangguan Naungan (Kelas Unggul D, C1, S2) (FORMAT 2)");
						reportIntepretation.add("= "+ (format2B2C > 100 ? " > 100 pokok/ha": "< 100 pokok/ha)"));
						reportIntepretation.add("");
						
						if (format2B2C >= 100 && format3B3C2 >= 100)
						{
							reportIntepretation.add("Tiada Gangguan Pepanjat dari kelas unggul D, C1, S1");
							reportIntepretation.add("= > 100 pokok/ha");
							reportIntepretation.add("");
							reportIntepretation.add("Kaedah Rawatan: Tiada Rawatan");
							reportIntepretation.add("Status Kompartmen: Terpulih");
						}
						else if  (format2B2C < 100 && format3B3C1 >= 100)
						{
							
							reportIntepretation.add("Tiada Gangguan Pepanjat");
							reportIntepretation.add("= > 100 pokok/ha");
							reportIntepretation.add("");
							reportIntepretation.add("Kaedah Rawatan: Rawatan G");
							reportIntepretation.add("Status Kompartmen: Belum Terpulih");
						}
						else if  (format2B2C >= 100 && format3B3C2 < 100)
						{
							reportIntepretation.add("Tiada Gangguan Pepanjat dari kelas unggul D, C1, S1");
							reportIntepretation.add("= < 100 pokok/ha");
							reportIntepretation.add("");
							reportIntepretation.add("Kaedah Rawatan: Rawatan CL");
							reportIntepretation.add("Status Kompartmen: Belum Terpulih");
						}
						else if  (format2B2C < 100 && format3B3C1 < 100)
						{
							reportIntepretation.add("Tiada Gangguan Pepanjat");
							reportIntepretation.add("= < 100 pokok/ha");
							reportIntepretation.add("");
							reportIntepretation.add("Kaedah Rawatan: Rawatan GCL");
							reportIntepretation.add("Status Kompartmen: Belum Terpulih");
						}
					}
					else  // < 100
					{
						
						
						reportIntepretation.add("Dari FORMAT 1A dan 1B");
						format1A1B = (reportFormat1A.get(0).getValues()[0]*4) + reportFormat1B.get(0).getValues()[0];
						format1A1BStr = df.format(reportFormat1B.get(0).getValues()[0]) + " + " + df.format(reportFormat1A.get(0).getValues()[0]) + " (4)";			
						
						reportIntepretation.add("Isi Kandungan + 5 - 15 cm, Kesuburan 1 Spesis RS dan");
						reportIntepretation.add("+ 15 - 30 cm, Status 1 yang setara dengannya");
						
						reportIntepretation.add("= "+ format1A1BStr);
						reportIntepretation.add("= "+  format1A1B + (format1A1B > 400 ? " (> 400 pokok/ha)": " (< 400 pokok/ha)"));
						reportIntepretation.add("");
						
						
						if (format1A1B >= 400)
						{						
							
							
							format4C = 100 - Math.min(reportFormat4C.get(1).getValues()[0],reportFormat4C.get(3).getValues()[0]); 
							format1A3 = reportFormat1A.get(4).getValues()[1] + reportFormat1A.get(4).getValues()[2] + reportFormat1A.get(4).getValues()[3] + reportFormat1A.get(4).getValues()[4] + reportFormat1A.get(4).getValues()[5];
							format1A3Str = df.format(reportFormat1A.get(4).getValues()[1]) + " + " + df.format(reportFormat1A.get(4).getValues()[2]) + " + " + df.format(reportFormat1A.get(4).getValues()[3]) + " + " + df.format(reportFormat1A.get(4).getValues()[4]) + " + " + df.format(reportFormat1A.get(4).getValues()[5]);
							
							reportIntepretation.add("");
							reportIntepretation.add("Dari FORMAT 4C");
							
							reportIntepretation.add("Peratus Petak diganggu oleh");
							reportIntepretation.add("Bertam =  100 - "+ reportFormat4C.get(1).getValues()[0] + "% = "+ (100 - reportFormat4C.get(1).getValues()[0]));
							reportIntepretation.add("Buluh = 100 - "+ reportFormat4C.get(3).getValues()[0] +"% = "+ (100 - reportFormat4C.get(3).getValues()[0]));
							//reportIntepretation.add("Palma = 100 - "+ reportFormat4C.get(5).getValues()[0] +"% = "+ (100 - reportFormat4C.get(5).getValues()[0]));
							reportIntepretation.add("");
							
							reportIntepretation.add("");
							reportIntepretation.add("Dari FORMAT 1A");
							reportIntepretation.add("Isi Kandungan + 30 - 45 cm, Status 3 dan");
							reportIntepretation.add("+ 45 cm, Status 3 dan setara dengannya");
							reportIntepretation.add("= "+ format1A3Str);
							reportIntepretation.add("= "+  df.format(format1A3) + (format1A3 > 32 ? " (> 32 pokok/ha)": "(< 32 pokok/ha)"));
							reportIntepretation.add("");
							
							reportIntepretation.add("Bandingkan JADUAL B (Kategori + 5 - 15 cm)");
							reportIntepretation.add("");
							reportIntepretation.add("Kesuburan 1 dan dari Jenis RS (FORMAT 1B)");
							reportIntepretation.add("= > 400 pokok/ha");
							reportIntepretation.add("");
							reportIntepretation.add("Petak diganggu sama ada oleh Betam atau buluh");
							reportIntepretation.add("= " + (format4C > 50 ? " (> 50%": "< 50%"));
							reportIntepretation.add("");
							reportIntepretation.add("Pokok status 3 + 30 - 45 cm dan + 45 cm yang setara");
							reportIntepretation.add("= " + (format1A3 > 32 ? "> 32 pokok/ha" : "< 32 pokok/ha"));
							
							
							if (format4C < 50 && format1A3 < 32)
							{
								
								reportIntepretation.add("Kaedah Rawatan: Tiada Rawatan");
								reportIntepretation.add("Status Kompartmen: Sedang Terpulih");
							}
							else if (format4C < 50 && format1A3 >= 32)
							{
								reportIntepretation.add("Kaedah Rawatan: Rawatan GCL");
								reportIntepretation.add("Status Kompartmen: Belum Terpulih");
							}
							else if (format4C >= 50)
							{
								reportIntepretation.add("Kaedah Rawatan: Rawat Semua Bertam, Buluh Dan Palma + GCL");
								reportIntepretation.add("Status Kompartmen: Belum Terpulih");
							}
							
						}
					else // < 400
						{
							
							format4A = 100 - reportFormat4A.get(1).getValues()[0];
							reportIntepretation.add("Dari FORMAT 4A");
							reportIntepretation.add("Peratus petak berisi dengan anak pokok, Spesis RS = "+ df.format((reportFormat4A.get(0).getValues()[10] - reportFormat4A.get(0).getValues()[0])) + "/" + df.format(reportFormat4A.get(0).getValues()[10]) + " x 100% atau 100% - "+ df.format(reportFormat4A.get(1).getValues()[0])+ "%");
							reportIntepretation.add("= "+ df.format(format4A) + (format4A > 50 ? " (bermakna > 50%)": " (bermakna < 50%)"));
							
							format4B = 100 - reportFormat4B.get(1).getValues()[0];
							if (format4A < 50)
							{
								reportIntepretation.add("");
								reportIntepretation.add("Dari FORMAT 4B");
								reportIntepretation.add("Peratus petak berisi dengan anak pokok, Spesis RS = "+ df.format((reportFormat4B.get(0).getValues()[10] - reportFormat4B.get(0).getValues()[0])) + "/"+ df.format(reportFormat4B.get(0).getValues()[10]) + " x 100% atau 100% - "+ df.format(reportFormat4B.get(1).getValues()[0]) + "%");
								reportIntepretation.add( "= "+ df.format(format4B) + (format4B > 50 ? " (bermakna > 50%)": " (bermakna < 50%)"));
							} 
							else
							{
							
								reportIntepretation.add("");
								format4C = 100 - Math.min(reportFormat4C.get(1).getValues()[0],reportFormat4C.get(3).getValues()[0]); 
								reportIntepretation.add("Dari FORMAT 4C");
								reportIntepretation.add("Peratus Petak diganggu oleh");
								reportIntepretation.add("Bertam =  100 - "+ df.format(reportFormat4C.get(1).getValues()[0]) + "% = "+ df.format((100 - reportFormat4C.get(1).getValues()[0])));
								reportIntepretation.add("Buluh = 100 - "+ df.format(reportFormat4C.get(3).getValues()[0]) +"% = "+ df.format((100 - reportFormat4C.get(3).getValues()[0])));
								reportIntepretation.add("Palma = 100 - "+ df.format(reportFormat4C.get(5).getValues()[0]) +"% = "+ df.format((100 - reportFormat4C.get(5).getValues()[0])));
								reportIntepretation.add("");
								
								resam = reportFormat4D.get(1).getValues()[2] + reportFormat4D.get(3).getValues()[3] + reportFormat4D.get(5).getValues()[4];
								halia = reportFormat4D.get(1).getValues()[2] + reportFormat4D.get(3).getValues()[3] + reportFormat4D.get(5).getValues()[4];
								pisang = reportFormat4D.get(1).getValues()[2] + reportFormat4D.get(3).getValues()[3] + reportFormat4D.get(5).getValues()[4];
								format4D = Math.max(resam, Math.max(halia, pisang));
								reportIntepretation.add("Dari FORMAT 4D");
								reportIntepretation.add("Peratus Petak diganggu (meliputi > 40% kuadrat) oleh");
								reportIntepretation.add("Resam = "+ df.format(resam));
								reportIntepretation.add("Halia = "+ df.format(halia));
								reportIntepretation.add("Pisang = "+ df.format(pisang));
								
								
								count1A3 = reportFormat1A.get(4).getValues()[2] + reportFormat1A.get(4).getValues()[3] + reportFormat1A.get(4).getValues()[4] + reportFormat1A.get(4).getValues()[5];
								format1A = reportFormat1A.get(4).getValues()[1] + count1A3;
								reportIntepretation.add("");
								reportIntepretation.add("Dari FORMAT 1A");
								reportIntepretation.add("Isi kandungan + 30 - 45 cm, Status 3 dan");
								reportIntepretation.add("+ 45 cm, Status 3 yang setara dengannya");
								reportIntepretation.add("= "+ df.format(reportFormat1A.get(4).getValues()[1]) + " + "+ df.format(count1A3));
								reportIntepretation.add("= "+ df.format(format1A) + " pokok/ha");
								reportIntepretation.add("");
							}
							reportIntepretation.add("Dari JADUAL C");
							
							if ((format4A >= 50 || format4B >= 30) && format4C < 10 && format4D < 10 && format1A < 32)
							{
								
								if (format4A >= 50) reportIntepretation.add("Bagi kategori anak pokok: Isi kandungan spesis RS >= 50% ");
								if (format4B >= 30) reportIntepretation.add("Bagi kategori anak benih: Isi kandungan spesis RS >= 30% ");
								reportIntepretation.add("Gangguan samaada Bertam atau Buluh < 10% ");
								reportIntepretation.add("Gangguan samaada Resam, Halia atau Pisang < 10% ");
								reportIntepretation.add("Gangguan pokok-pokok status 3; < 32 pokok/ha");
								reportIntepretation.add("");
								reportIntepretation.add("Rawatan: Tiada Rawatan diperlukan");
								reportIntepretation.add("Status Kompartment: Isi kandungan cukup tanpa gangguan");
							}
							else if (format4A > 50 && format4C < 10 && format4D >= 10 && format1A < 32)
							{
								
								reportIntepretation.add("Bagi kategori anak pokok: Isi kandungan spesis RS > 50% ");
								reportIntepretation.add("Gangguan samaada Bertam atau Buluh < 10% ");
								reportIntepretation.add("Gangguan samaada Resam, Halia atau Pisang   >= 10% ");
								reportIntepretation.add("Gangguan pokok-pokok status 3; < 32 pokok/ha");
								reportIntepretation.add("");
								reportIntepretation.add("Rawatan: Tiada Rawatan");
								reportIntepretation.add("Status Kompartment: Isi kandungan cukup tanpa gangguan");
							}
							else if ((format4A >= 50 || format4B >= 30) && format4C < 10 && format4D < 10 && format1A >= 32)
							{
								if (format4A >= 50) reportIntepretation.add("Bagi kategori anak pokok: Isi kandungan spesis RS >=50% ");
								if (format4B >= 30) reportIntepretation.add("Bagi kategori anak benih: Isi kandungan spesis RS >=30% ");
								reportIntepretation.add("Gangguan samaada Bertam atau Buluh < 10% ");
								reportIntepretation.add("Gangguan samaada Resam, Halia atau Pisang   < 10% ");
								reportIntepretation.add("Gangguan pokok-pokok status 3; >= 32 pokok/ha");
								reportIntepretation.add("");
								reportIntepretation.add("Rawatan: Rawatan GCL");
								reportIntepretation.add("Status Kompartment: Isi kandungan cukup mengalami gangguan");
							}
							else if (format4A >= 50 && format4C < 10 && format4D >= 10 && format1A >= 32)
							{
								reportIntepretation.add("Bagi kategori anak pokok: Isi kandungan spesis RS >= 50% ");							
								reportIntepretation.add("Gangguan samaada Bertam atau Buluh < 10% ");
								reportIntepretation.add("Gangguan samaada Resam, Halia atau Pisang >= 10% ");
								reportIntepretation.add("Gangguan pokok-pokok Status 3 >= 32 pokok/ha");
								reportIntepretation.add("");
								reportIntepretation.add("Rawatan: Rawatan GCL");
								reportIntepretation.add("Status Kompartment: Isi kandungan cukup mengalami gangguan");
							}
							else if (format4A >= 50 && format4C >= 10)
							{
								
								reportIntepretation.add("Bagi kategori anak pokok: Isi kandungan spesis RS >= 50% ");							
								reportIntepretation.add("Gangguan samaada Bertam atau Buluh >= 10% ");
								if (format4D >= 10) 
									reportIntepretation.add("Gangguan samaada Resam, Halia atau Pisang >= 10% ");
								else
									reportIntepretation.add("Gangguan samaada Resam, Halia atau Pisang < 10% ");
								
								if (format1A >= 32)
									reportIntepretation.add("Gangguan pokok-pokok status 3; >= 32 pokok/ha");
								else
									reportIntepretation.add("Gangguan pokok-pokok status 3; < 32 pokok/ha");
								reportIntepretation.add("");
								reportIntepretation.add("Rawatan: Rawat semua Bertam, Buluh, Palma + GCL");
								reportIntepretation.add("Status Kompartment: Isi kandungan cukup mengalami gangguan");
							}
							else if (format4B >= 30 && format4C >= 10 && format4D < 10)
							{
								
								reportIntepretation.add("Bagi kategori anak benih: Isi kandungan spesis RS >= 30% ");	
								reportIntepretation.add("Gangguan samaada Bertam atau Buluh >= 10% ");
								reportIntepretation.add("Gangguan samaada Resam, Halia atau Pisang < 10% ");
								
								if (format1A >= 32)
									reportIntepretation.add("Gangguan pokok-pokok status 3; >= 32 pokok/ha");
								else
									reportIntepretation.add("Gangguan pokok-pokok status 3; < 32 pokok/ha");
								reportIntepretation.add("");
								reportIntepretation.add("Rawatan: Rawat semua Bertam, Buluh, Palma + GCL");
								reportIntepretation.add("Status Kompartment: Isi kandungan cukup mengalami gangguan");
							}
							else if (format4B >= 30 && format4D >= 10)
							{
								
								reportIntepretation.add("Bagi kategori anak benih: Isi kandungan spesis RS >= 30% ");							
								
								if (format4C >= 10)
									reportIntepretation.add("Gangguan samaada Bertam atau Buluh >= 10% ");
								else
									reportIntepretation.add("Gangguan samaada Bertam atau Buluh < 10% ");
								
								if (format4D >= 10) 
									reportIntepretation.add("Gangguan samaada Resam, Halia atau Pisang >= 10% ");
								else
									reportIntepretation.add("Gangguan samaada Resam, Halia atau Pisang < 10% ");
								
								if (format1A >= 32)
									reportIntepretation.add("Gangguan pokok-pokok status 3; >= 32 pokok/ha");
								else
									reportIntepretation.add("Gangguan pokok-pokok status 3; < 32 pokok/ha");
								reportIntepretation.add("");
								reportIntepretation.add("Rawatan: Tanaman Mengaya");
								reportIntepretation.add("Status Kompartment: Isi kandungan cukup mengalami gangguan yang tidak boleh diatasi");
							}
							else if (format4A < 50 && format4B < 30)
							{
								
								reportIntepretation.add("Bagi kategori anak pokok: Isi kandungan spesis RS < 50% ");	
								reportIntepretation.add("Bagi kategori anak benih: Isi kandungan spesis RS < 30% ");
								
								if (format4C >= 10)
									reportIntepretation.add("Gangguan samaada Bertam atau Buluh >= 10% ");
								else
									reportIntepretation.add("Gangguan samaada Bertam atau Buluh < 10% ");
								
								reportIntepretation.add("Gangguan samaada Resam, Halia atau Pisang >= 10% ");
								
								if (format1A >= 32)
									reportIntepretation.add("Gangguan pokok-pokok status 3; >= 32 pokok/ha");
								else
									reportIntepretation.add("Gangguan pokok-pokok status 3; < 32 pokok/ha");
								reportIntepretation.add("");
								reportIntepretation.add("Rawatan: Tanaman Mengaya");
								reportIntepretation.add("Status Kompartment: Isi kandungan miskin");
							}
						
						}
					}										
				}		
			} 
			
			
		}
			
		return reportIntepretation;
		
	}
	
	public String[] getHeaders()
	{
		return headers;
	}

	public ArrayList<PostFellingReportElement> getBertamPalmCounts()
	{
		if (bertamPalmCounts == null)
		{
			bertamPalmCounts = new ArrayList<>();
			ArrayList<PostFellingSurveyCard> postFellingSurveyCards = postFellingSurvey.getPostFellingSurveyCards();

			PostFellingReportElement count = new PostFellingReportElement();
			PostFellingReportElement relativeCount = new PostFellingReportElement();
			double[] countValues = new double[2];
			double[] relativeCountValues = new double[2];

			count.setCriteria("Jumlah rumpun");
			count.setValues(countValues);
			relativeCount.setCriteria("Rumpun sehektar");
			relativeCount.setValues(relativeCountValues);

			for (PostFellingSurveyCard postFellingSurveyCard : postFellingSurveyCards)
			{
				countValues[0] += postFellingSurveyCard.getBertam();
				countValues[1] += postFellingSurveyCard.getPalm();
			}

			for (int i = 0; i < 2; i++)
				relativeCountValues[i] = countValues[i] / (postFellingSurveyCards.size() * 0.1);

			bertamPalmCounts.add(count);
			bertamPalmCounts.add(relativeCount);
		}

		return bertamPalmCounts;
	}

	public ArrayList<PostFellingReportElement> getResamCounts()
	{
		if (resamCounts == null)
		{
			resamCounts = new ArrayList<>();
			ArrayList<PostFellingSurveyCard> postFellingSurveyCards = postFellingSurvey.getPostFellingSurveyCards();

			PostFellingReportElement count = new PostFellingReportElement();
			PostFellingReportElement relativeCount = new PostFellingReportElement();
			double[] countValues = new double[6];
			double[] relativeCountValues = new double[6];

			count.setCriteria("Bilangan petak diinventori");
			count.setValues(countValues);
			relativeCount.setCriteria("Peratus ditumbuhi");
			relativeCount.setValues(relativeCountValues);

			for (PostFellingSurveyCard postFellingSurveyCard : postFellingSurveyCards)
			{
				int resamID = postFellingSurveyCard.getResamID();
				countValues[resamID - 1]++;
				countValues[5]++;
			}

			for (int i = 0; i < 6; i++)
				relativeCountValues[i] = countValues[i] * 100 / postFellingSurveyCards.size();

			resamCounts.add(count);
			resamCounts.add(relativeCount);
		}

		return resamCounts;
	}

	public ArrayList<PostFellingReportElement> getFertilityCounts()
	{
		if (fertilityCounts == null)
		{
			fertilityCounts = new ArrayList<>();
			ArrayList<PostFellingSurveyCard> postFellingSurveyCards = postFellingSurvey.getPostFellingSurveyCards();

			PostFellingReportElement class1Count = new PostFellingReportElement();
			PostFellingReportElement class1RelativeCount = new PostFellingReportElement();
			double[] class1CountValues = new double[5];
			double[] class1RelativeCountValues = new double[5];

			class1Count.setGroup("+ 5 - 15 cm");
			class1Count.setCriteria("Bilangan");
			class1Count.setValues(class1CountValues);
			class1RelativeCount.setGroup("+ 5 - 15 cm");
			class1RelativeCount.setCriteria("Bilangan sehektar");
			class1RelativeCount.setValues(class1RelativeCountValues);

			PostFellingReportElement class2Count = new PostFellingReportElement();
			PostFellingReportElement class2RelativeCount = new PostFellingReportElement();
			double[] class2CountValues = new double[5];
			double[] class2RelativeCountValues = new double[5];

			class2Count.setGroup("+ 15 - 30 cm");
			class2Count.setCriteria("Bilangan");
			class2Count.setValues(class2CountValues);
			class2RelativeCount.setGroup("+ 15 - 30 cm");
			class2RelativeCount.setCriteria("Bilangan sehektar");
			class2RelativeCount.setValues(class2RelativeCountValues);

			PostFellingReportElement totalCount = new PostFellingReportElement();
			PostFellingReportElement totalRelativeCount = new PostFellingReportElement();
			double[] totalCountValues = new double[5];
			double[] totalRelativeCountValues = new double[5];

			totalCount.setGroup("Jumlah besar");
			totalCount.setCriteria("Bilangan");
			totalCount.setValues(totalCountValues);
			totalRelativeCount.setGroup("Jumlah besar");
			totalRelativeCount.setCriteria("Bilangan sehektar");
			totalRelativeCount.setValues(totalRelativeCountValues);

			for (PostFellingSurveyCard postFellingSurveyCard : postFellingSurveyCards)
			{
				ArrayList<PostFellingSurveyRecord> postFellingSurveyRecords = postFellingSurveyCard.getPostFellingSurveyRecords();

				for (PostFellingSurveyRecord postFellingSurveyRecord : postFellingSurveyRecords)
				{
					double diameter = postFellingSurveyRecord.getDiameter();
					int fertilityID = postFellingSurveyRecord.getFertilityID();

					if (diameter >= 5 && diameter < 15)
					{
						class1CountValues[fertilityID - 1]++;
						totalCountValues[fertilityID - 1]++;
						class1CountValues[4]++;
						totalCountValues[4]++;
					}
					else if (diameter >= 15 && diameter < 30)
					{
						class2CountValues[fertilityID - 1]++;
						totalCountValues[fertilityID - 1]++;
						class2CountValues[4]++;
						totalCountValues[4]++;
					}
				}
			}

			for (int i = 0; i < 5; i++)
			{
				class1RelativeCountValues[i] = class1CountValues[i] / (postFellingSurveyCards.size() * 0.01);
				class2RelativeCountValues[i] = class2CountValues[i] / (postFellingSurveyCards.size() * 0.05);
				totalRelativeCountValues[i] = class1RelativeCountValues[i] + class2RelativeCountValues[i];
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

	public ArrayList<PostFellingReportElement> getSeedlingCounts()
	{
		if (seedlingCounts == null)
		{
			seedlingCounts = new ArrayList<>();
			ArrayList<PostFellingSurveyCard> postFellingSurveyCards = postFellingSurvey.getPostFellingSurveyCards();

			PostFellingReportElement count1 = new PostFellingReportElement();
			PostFellingReportElement count2 = new PostFellingReportElement();
			double[] count1Values = new double[6];
			double[] count2Values = new double[6];

			count1.setCriteria("> 1.5 m tinggi - 5 cm diameter");
			count1.setValues(count1Values);
			count2.setCriteria("> 15 cm - 150 cm tinggi");
			count2.setValues(count2Values);

			for (PostFellingSurveyCard postFellingSurveyCard : postFellingSurveyCards)
			{
				ArrayList<PostFellingSurveyRecord> postFellingSurveyRecords = postFellingSurveyCard.getPostFellingSurveyRecords();

				for (PostFellingSurveyRecord postFellingSurveyRecord : postFellingSurveyRecords)
				{
					count1Values[0] += postFellingSurveyRecord.getSaplingQuantity();
					count2Values[0] += postFellingSurveyRecord.getSeedlingQuantity();
				}
			}

			count1Values[1] += count1Values[0] / (postFellingSurveyCards.size() * 0.0025);
			count2Values[1] += count2Values[0] / (postFellingSurveyCards.size() * 0.0004);

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
		return "Laporan " + postFellingSurvey;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof PostFellingReport)
		{
			PostFellingReport that = (PostFellingReport) obj;
			equal = postFellingSurvey.equals(that.postFellingSurvey);
		}

		return equal;
	}
	
	public ArrayList<String> getReportIntepretationForHTML()
	{
		ArrayList<String> report =  getReportIntepretation();
		ArrayList<String> reportIntepretationForHTML = new ArrayList<String>();
		for (int i = 0; i < report.size(); i++)
		{
			String str = report.get(i);
			//if (str.startsWith("Dari ")) str = "<span style=\"text-decoration: underline; font-weight:bold;\">" + str + "</span>";
			if (str.startsWith("Dari ")) 
				str = "<h3 style=\"padding-left:10px;\">" + str + "</h3>";
			else if (str.startsWith("+ "))
				str = "<span style=\"padding-left:102px;\">" + str + "</span>";
			else if (str.startsWith("= "))
				str = "<span style=\"padding-left:330px;\">" + str + "</span>";
			else 
				str = "<span style=\"padding-left:10px;\">" + str + "</span>";
			reportIntepretationForHTML.add(str);
		}
		
		return reportIntepretationForHTML;
		
	}
}