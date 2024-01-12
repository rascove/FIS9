package my.edu.utem.ftmk.fis9.postfelling.model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

public class PostFellingInspectionReport extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private PostFellingSurvey postFellingSurvey;
	private String[] headers;
	
	private ArrayList<PostFellingReportElement> reportGarisTiang;
	private ArrayList<PostFellingReportElement> reportBearingGaris;
	private ArrayList<PostFellingReportElement> reportLampiranA;
	private ArrayList<PostFellingReportElement> reportLampiranB;
	private ArrayList<PostFellingReportElement> reportLampiranC;
	private ArrayList<PostFellingReportElement> reportLampiranD;
	private ArrayList<PostFellingReportElement> reportLampiranE;
	private ArrayList<PostFellingReportElement> reportLampiranF;
	private ArrayList<PostFellingReportElement> reportLampiranG;
	private ArrayList<PostFellingReportElement> reportSempadan;
	
	
	
	private double originalStandRatio;
	private static double distanceLineBefore = 50;
	private static double distanceLineAfter = 100;
	DecimalFormat df = new DecimalFormat("0.0");
	String [] bearingGarisResult = new String[5];
	
	public PostFellingInspectionReport(PostFellingSurvey postFellingSurvey)
	{
		this.postFellingSurvey = postFellingSurvey;
		

		headers = new String[] {
				"RINGKASAN MAKLUMAT SEMAKAN POKOK-POKOK YANG DIBANCI DALAM PETAK UTAMA\n INVENTORI HUTAN SELEPAS TEBANGAN (POST-F)",
				"RINGKASAN MAKLUMAT SEMAKAN POKOK-POKOK YANG DIBANCI DALAM PETAK KEDUA\n INVENTORI HUTAN SELEPAS TEBANGAN (POST-F)",
				"RINGKASAN MAKLUMAT SEMAKAN POKOK-POKOK YANG DIBANCI DALAM PETAK KETIGA\n INVENTORI HUTAN SELEPAS TEBANGAN (POST-F)",
				"RINGKASAN MAKLUMAT SEMAKAN ANAK POKOK DAN ANAK BENIH YANG DIBANCI DALAM PETAK KEEMPAT DAN KELIMA\n INVENTORI HUTAN SELEPAS TEBANGAN (POST-F)",
				"RINGKASAN MAKLUMAT SEMAKAN BILANGAN RUMPUN BULUH YANG DIBANCI DALAM PETAK UTAMA\n INVENTORI HUTAN SELEPAS TEBANGAN (POST-F)",
				"RINGKASAN MAKLUMAT SEMAKAN BILANGAN BATANG PALMA, RUMPUN BERTAM DAN TUMBUHAN PEPANJAT\n YANG DIBANCI DALAM PETAK UTAMA\n INVENTORI HUTAN SELEPAS TEBANGAN (POST-F)",
				"RINGKASAN MAKLUMAT SEMAKAN PERATUSAN RESAM, HALIA DAN PISANG YANG DIBANCI DALAM PETAK UTAMA\n INVENTORI HUTAN SELEPAS TEBANGAN (POST-F)"};
	}

	public ArrayList<PostFellingReportElement> getReportGarisTiang()
	{
		if (reportGarisTiang == null)
		{
			reportGarisTiang = new ArrayList<PostFellingReportElement>();
			ArrayList<PostFellingInspectionLine> postFellingInspectionLines = postFellingSurvey.getPostFellingInspectionLines();

			PostFellingReportElement reportElement = new PostFellingReportElement();
			
			String data[];
			int totalErrorBefore = 0, totalErrorAfter = 0, totalStartPoleYes = 0,  totalEndPoleYes = 0;
			for (PostFellingInspectionLine line : postFellingInspectionLines)
			{
				
				data = new String[9];
				data[0] = line.getLineNo();
				data[1] = line.getLineBefore()+"";
				data[2] = df.format((double) (Math.abs(line.getLineBefore() - distanceLineBefore) / distanceLineBefore * 100));
				data[3] = line.getLineAfter()+"";
				data[4] = df.format((double) (Math.abs(line.getLineAfter() - distanceLineAfter) / distanceLineAfter * 100));
				
				if ((line.getLineBefore() - distanceLineBefore) !=0) 
					totalErrorBefore++;
				if ((line.getLineAfter() - distanceLineAfter) != 0) 
					totalErrorAfter++;
				
				if (line.isStartPole())
				{
					data[5] = "/";
					data[6] = "";
					totalStartPoleYes++;
				}
				else
				{
					data[5] = "";
					data[6] = "/";	
				}
				
				if (line.isEndPole())
				{
					data[7] = "/";
					data[8] = "";
					totalEndPoleYes++;
				}
				else
				{
					data[7] = "";
					data[8] = "/";
					
				}
				reportElement.setData(data);
				reportGarisTiang.add(reportElement);
				reportElement = new PostFellingReportElement();
				
			}
			data = new String[9];
			data[0]= "Jumlah";
			data[1]= "-";
			data[2]= "-";
			data[3]= "-";
			data[4]= "-";
			data[5]= totalStartPoleYes+"";
			data[6]= postFellingInspectionLines.size()-totalStartPoleYes+"";
			data[7]= totalEndPoleYes+"";
			data[8]= postFellingInspectionLines.size() - totalEndPoleYes+"";
			reportElement.setData(data);
			reportGarisTiang.add(reportElement);
			reportElement = new PostFellingReportElement();
			
			data = new String[9];
			data[0]= "% Bilangan Kesilapan";
			data[1]= "-";
			data[2]= df.format((double)totalErrorBefore/postFellingInspectionLines.size()*100)+"";
			data[3]= "-";
			data[4]= df.format((double)totalErrorAfter/postFellingInspectionLines.size()*100)+"";
			data[5]= "-";
			data[6]= df.format((double)(postFellingInspectionLines.size() - totalStartPoleYes)/postFellingInspectionLines.size()*100)+"";
			data[7]= "-";
			data[8]= df.format((double)(postFellingInspectionLines.size() - totalEndPoleYes)/postFellingInspectionLines.size()*100)+"";
			reportElement.setData(data);
			reportGarisTiang.add(reportElement);
			
		}

		return reportGarisTiang;
	}
	
	
	public ArrayList<PostFellingReportElement> getReportSempadan()
	{
		if (reportSempadan == null)
		{
			reportSempadan = new ArrayList<PostFellingReportElement>();
			ArrayList<PostFellingInspectionLine> postFellingInspectionLines = postFellingSurvey.getPostFellingInspectionLines();

			PostFellingReportElement reportElement = new PostFellingReportElement();
			
			String data[];
			
			for (PostFellingInspectionLine line : postFellingInspectionLines)
			{
				
				data = new String[9];
				data[0] = line.getLineNo();
				
				
				if (line.isBoundarySignageStart())
				{
					data[1] = "/";
					data[2] = "";
					
				}
				else
				{
					data[1] = "";
					data[2] = "/";
				}
				
				if (line.isBoundaryCleanStart())
				{
					data[3] = "/";
					data[4] = "";
				
				}
				else
				{
					data[3] = "";
					data[4] = "/";	
				}
				
				if (line.isBoundarySignageEnd())
				{
					data[5] = "/";
					data[6] = "";
				}
				else
				{
					data[5] = "";
					data[6] = "/";
				}
				
				if (line.isBoundaryCleanEnd())
				{
					data[7] = "/";
					data[8] = "";
					
				}
				else
				{
					data[7] = "";
					data[8] = "/";
				}
				
				
				reportElement.setData(data);
				reportSempadan.add(reportElement);
				reportElement = new PostFellingReportElement();
				
			}
			
		}

		return reportSempadan;
	}
	
	public ArrayList<PostFellingReportElement> getReportBearingGaris()
	{
		if (reportBearingGaris == null)
		{
			reportBearingGaris = new ArrayList<PostFellingReportElement>();
			ArrayList<PostFellingInspectionLine> postFellingInspectionLines = postFellingSurvey.getPostFellingInspectionLines();

			PostFellingReportElement reportElement = new PostFellingReportElement();
			
			String data[];
			int totalBearing0 = 0, totalPlot0 = 0, totalStakeYes = 0 , totalSteepnessYes = 0 ;
			for (PostFellingInspectionLine line : postFellingInspectionLines)
			{
				
				data = new String[9];
				data[0] = line.getLineNo();
				
				
				if (line.getBearingError()==0)
				{
					data[1] = "/";
					data[2] = "";
					totalBearing0++;
				}
				else
				{
					data[1] = "";
					data[2] = "/";
				}
				
				if (line.getPlotError()==0)
				{
					data[3] = "/";
					data[4] = "";
					totalPlot0++;
				}
				else
				{
					data[3] = "";
					data[4] = "/";	
				}
				
				if (line.isStake())
				{
					data[5] = "/";
					data[6] = "";
					totalStakeYes++;
				}
				else
				{
					data[5] = "";
					data[6] = "/";
				}
				
				if (line.isSteepness())
				{
					data[7] = "/";
					data[8] = "";
					totalSteepnessYes++;
				}
				else
				{
					data[7] = "";
					data[8] = "/";
				}
				
				
				reportElement.setData(data);
				reportBearingGaris.add(reportElement);
				reportElement = new PostFellingReportElement();
				
			}
			data = new String[9];
			data[0]= "Jumlah";
			data[1]= totalBearing0+"";
			data[2]= postFellingInspectionLines.size()-totalBearing0+"";
			data[3]= totalPlot0+"";
			data[4]= postFellingInspectionLines.size()-totalPlot0+"";
			data[5]= totalStakeYes+"";
			data[6]= postFellingInspectionLines.size()-totalStakeYes+"";
			data[7]= totalSteepnessYes+"";
			data[8]= postFellingInspectionLines.size() - totalSteepnessYes+"";
			reportElement.setData(data);
			reportBearingGaris.add(reportElement);
			reportElement = new PostFellingReportElement();
			
			data = new String[9];
			data[0]= "% Bilangan Kesilapan";
			data[1]= "";
			data[2]= df.format((double)(postFellingInspectionLines.size()-totalBearing0)/postFellingInspectionLines.size()*100);
			data[3]= "";
			data[4]= df.format((double)(postFellingInspectionLines.size()-totalPlot0)/postFellingInspectionLines.size()*100);
			data[5]= "";
			data[6]= df.format((double)(postFellingInspectionLines.size() - totalStakeYes)/postFellingInspectionLines.size()*100);
			data[7]= "";
			data[8]= df.format((double)(postFellingInspectionLines.size() - totalSteepnessYes)/postFellingInspectionLines.size()*100);
			
		
			reportElement.setData(data);
			reportBearingGaris.add(reportElement);
			reportElement = new PostFellingReportElement();
			
			
			
			
		}

		return reportBearingGaris;
	}

	public ArrayList<PostFellingReportElement> getReportLampiranA()
	{
		int counter = 0;
		if (reportLampiranA == null)
		{
			reportLampiranA = new ArrayList<PostFellingReportElement>();
			ArrayList<PostFellingSurveyCard> postFellingSurveyCards = postFellingSurvey.getPostFellingSurveyCards();
			PostFellingReportElement reportElement = new PostFellingReportElement();
			int bilPokokPlot1Asal = 0, bilPokokPlot1Semak = 0,bilPokokPlot2Asal = 0, bilPokokPlot2Semak = 0;
			double silap1 = 0, silap2 = 0;
			String data[];
			
			for (PostFellingSurveyCard postFellingSurveyCard : postFellingSurveyCards)
			{	
				if (postFellingSurveyCard.isInspection())
				{
					
					Map<String,Integer> speciesPlot1=new HashMap<>();
					Map<String,Integer> inspectionSpeciesPlot1=new HashMap<>();
					Map<String,Integer> diameterPlot1=new HashMap<>();
					Map<String,Integer> inspectionDiameterPlot1=new HashMap<>();
					
					Map<String,Integer> speciesPlot2=new HashMap<>();
					Map<String,Integer> inspectionSpeciesPlot2=new HashMap<>();
					Map<String,Integer> diameterPlot2=new HashMap<>();
					Map<String,Integer> inspectionDiameterPlot2=new HashMap<>();
					counter++;
					bilPokokPlot1Asal = 0; bilPokokPlot1Semak = 0; bilPokokPlot2Asal = 0; bilPokokPlot2Semak = 0;
					data = new String[16];
					ArrayList<PostFellingSurveyRecord> surveyRecords =  postFellingSurveyCard.getPostFellingSurveyRecords();
					for (PostFellingSurveyRecord surveyRecord : surveyRecords)
					{
						if(surveyRecord.getPlotTypeID() == 1)
						{
							
						
							
							if (speciesPlot1.containsKey(surveyRecord.getSpeciesID()+""))
								speciesPlot1.put(surveyRecord.getSpeciesID()+"", (speciesPlot1.get(surveyRecord.getSpeciesID()+"") + 1));
							else
								speciesPlot1.put(surveyRecord.getSpeciesID()+"", 1);
							
							
							if (inspectionSpeciesPlot1.containsKey(surveyRecord.getInspectionSpeciesID()+""))
								inspectionSpeciesPlot1.put(surveyRecord.getInspectionSpeciesID()+"", inspectionSpeciesPlot1.get(surveyRecord.getInspectionSpeciesID()+"") + 1);
							else
								inspectionSpeciesPlot1.put(surveyRecord.getInspectionSpeciesID()+"", 1);
							
							
							if (diameterPlot1.containsKey(surveyRecord.getDiameter()+""))
								diameterPlot1.put(surveyRecord.getDiameter()+"", diameterPlot1.get(surveyRecord.getDiameter()+"") + 1);
							else
								diameterPlot1.put(surveyRecord.getDiameter()+"", 1);
							
							
							if (inspectionDiameterPlot1.containsKey(surveyRecord.getInspectionDiameter()+""))
								inspectionDiameterPlot1.put(surveyRecord.getInspectionDiameter()+"", inspectionDiameterPlot1.get(surveyRecord.getInspectionDiameter()+"") + 1);
							else
								inspectionDiameterPlot1.put(surveyRecord.getInspectionDiameter()+"", 1);
								
							
							if (surveyRecord.getDiameter() != null && surveyRecord.getDiameter()>0)
								bilPokokPlot1Asal++;
							
							if (surveyRecord.getInspectionDiameter() != null && surveyRecord.getInspectionDiameter() > 0)
								bilPokokPlot1Semak++;		
						}
						
						if(surveyRecord.getPlotTypeID() == 2)
						{
							
							
							if (speciesPlot2.containsKey(surveyRecord.getSpeciesID()+""))
								speciesPlot2.put(surveyRecord.getSpeciesID()+"", speciesPlot2.get(surveyRecord.getSpeciesID()+"") + 1);
							else
								speciesPlot2.put(surveyRecord.getSpeciesID()+"", 1);
							
							
							if (inspectionSpeciesPlot2.containsKey(surveyRecord.getInspectionSpeciesID()+""))
								inspectionSpeciesPlot2.put(surveyRecord.getInspectionSpeciesID()+"", inspectionSpeciesPlot2.get(surveyRecord.getInspectionSpeciesID()+"") + 1);
							else
								inspectionSpeciesPlot2.put(surveyRecord.getInspectionSpeciesID()+"", 1);
							
							
							if (diameterPlot2.containsKey(surveyRecord.getDiameter()+""))
								diameterPlot2.put(surveyRecord.getDiameter()+"", diameterPlot2.get(surveyRecord.getDiameter()+"") + 1);
							else
								diameterPlot2.put(surveyRecord.getDiameter()+"", 1);
							
							
							if (inspectionDiameterPlot2.containsKey(surveyRecord.getInspectionDiameter()+""))
								inspectionDiameterPlot2.put(surveyRecord.getInspectionDiameter()+"", inspectionDiameterPlot2.get(surveyRecord.getInspectionDiameter()+"") + 1);
							else
								inspectionDiameterPlot2.put(surveyRecord.getInspectionDiameter()+"", 1);
							
							if (surveyRecord.getDiameter() != null && surveyRecord.getDiameter()>0)
								bilPokokPlot2Asal++;
							
							if (surveyRecord.getInspectionDiameter() != null && surveyRecord.getInspectionDiameter() > 0)
								bilPokokPlot2Semak++;	
						}
					}
					
					
					silap1 = getErrorRate(bilPokokPlot1Asal,bilPokokPlot1Semak);
					silap2 =  getErrorRate(bilPokokPlot2Asal,bilPokokPlot2Semak);
					
					if (postFellingSurveyCard.getPostFellingSurveyCardID()==1528077113752l) System.out.println("\n\n###");
					Double[] errorRateSpeciesPlot1 = getErrorRate(speciesPlot1,inspectionSpeciesPlot1);
					Double[] errorRateDiameterPlot1 = getErrorRate(diameterPlot1,inspectionDiameterPlot1);
					
					Double[] errorRateSpeciesPlot2 = getErrorRate(speciesPlot2,inspectionSpeciesPlot2);
					Double[] errorRateDiameterPlot2 = getErrorRate(diameterPlot2,inspectionDiameterPlot2);
					
					data[0] = counter+""; 
					data[1] = postFellingSurveyCard.getLineNo()+" ("+ postFellingSurveyCard.getPlotNo()+")";
					
					data[2] = bilPokokPlot1Asal+""; 
					data[3] = bilPokokPlot1Semak+""; 
					data[4] = df.format(silap1)+""; 
					data[5] = errorRateSpeciesPlot1[0].intValue()+"";//bilPokokPlot1Asal+""; 
					data[6] = df.format(errorRateSpeciesPlot1[1]);//df.format(silap1)+"";
					data[7] = errorRateDiameterPlot1[0].intValue()+""; 
					data[8] = df.format(errorRateDiameterPlot1[1]);//df.format(silap1)+"";

					data[9] = bilPokokPlot2Asal+""; 
					data[10] = bilPokokPlot2Semak+""; 
					data[11] = df.format(silap2)+""; 
					data[12] = errorRateSpeciesPlot2[0].intValue()+""; 
					data[13] = df.format(errorRateSpeciesPlot2[1]);
					data[14] = errorRateDiameterPlot2[0].intValue()+"";  
					data[15] =df.format(errorRateDiameterPlot2[1])+"";
					
					reportElement.setData(data);
					reportLampiranA.add(reportElement);
					reportElement = new PostFellingReportElement();
				}		
			}	
		}
		return reportLampiranA;
	}

	public ArrayList<PostFellingReportElement> getReportLampiranB()
	{
		int counter = 0;
		if (reportLampiranB == null)
		{
			reportLampiranB = new ArrayList<PostFellingReportElement>();
			ArrayList<PostFellingSurveyCard> postFellingSurveyCards = postFellingSurvey.getPostFellingSurveyCards();
			PostFellingReportElement reportElement = new PostFellingReportElement();
			int bilPokokPlotAsal = 0, bilPokokPlotSemak = 0;
			double silap = 0;
			String data[];
			
			for (PostFellingSurveyCard postFellingSurveyCard : postFellingSurveyCards)
			{	
				if (postFellingSurveyCard.isInspection())
				{
						
					Map<String,Integer> species=new HashMap<>();
					Map<String,Integer> inspectionSpecies=new HashMap<>();
					Map<String,Integer> diameter=new HashMap<>();
					Map<String,Integer> inspectionDiameter=new HashMap<>();
					
					
					counter++;
						data = new String[9];
						bilPokokPlotAsal = 0; bilPokokPlotSemak = 0;
						ArrayList<PostFellingSurveyRecord> surveyRecords =  postFellingSurveyCard.getPostFellingSurveyRecords();
						for (PostFellingSurveyRecord surveyRecord : surveyRecords)
						{
							if(surveyRecord.getPlotTypeID() == 3)
							{
								
								
								if (species.containsKey(surveyRecord.getSpeciesID()+""))
									species.put(surveyRecord.getSpeciesID()+"", (species.get(surveyRecord.getSpeciesID()+"") + 1));
								else
									species.put(surveyRecord.getSpeciesID()+"", 1);
								
								
								if (inspectionSpecies.containsKey(surveyRecord.getInspectionSpeciesID()+""))
									inspectionSpecies.put(surveyRecord.getInspectionSpeciesID()+"", inspectionSpecies.get(surveyRecord.getInspectionSpeciesID()+"") + 1);
								else
									inspectionSpecies.put(surveyRecord.getInspectionSpeciesID()+"", 1);
								
								
								if (diameter.containsKey(surveyRecord.getDiameter()+""))
									diameter.put(surveyRecord.getDiameter()+"", diameter.get(surveyRecord.getDiameter()+"") + 1);
								else
									diameter.put(surveyRecord.getDiameter()+"", 1);
								
								
								if (inspectionDiameter.containsKey(surveyRecord.getInspectionDiameter()+""))
									inspectionDiameter.put(surveyRecord.getInspectionDiameter()+"", inspectionDiameter.get(surveyRecord.getInspectionDiameter()+"") + 1);
								else
									inspectionDiameter.put(surveyRecord.getInspectionDiameter()+"", 1);
								
								if (surveyRecord.getDiameter()>0)
									bilPokokPlotAsal++;
								
								if (surveyRecord.getInspectionDiameter() > 0)
									bilPokokPlotSemak++;	
							}
							
						}
						silap = getErrorRate(bilPokokPlotAsal,bilPokokPlotSemak);
						Double[] errorRateSpecies = getErrorRate(species,inspectionSpecies);
						Double[] errorRateDiameter = getErrorRate(diameter,inspectionDiameter);
						
						data[0] = counter+""; 
						data[1] = postFellingSurveyCard.getLineNo()+" ("+ postFellingSurveyCard.getPlotNo()+")";
						
						data[2] = bilPokokPlotAsal+""; 
						data[3] = bilPokokPlotSemak+""; 
						data[4] = (int)silap+""; 
						data[5] = errorRateSpecies[0].intValue()+"";//bilPokokPlot1Asal+""; 
						data[6] = df.format(errorRateSpecies[1]);//df.format(silap1)+"";
						data[7] = errorRateDiameter[0].intValue()+""; 
						data[8] = df.format(errorRateDiameter[1]);//df.format(silap1)+"";

						
						reportElement.setData(data);
						reportLampiranB.add(reportElement);
						reportElement = new PostFellingReportElement();
				}		
			}	
		}
		return reportLampiranB;
	}
	
	public ArrayList<PostFellingReportElement> getReportLampiranC()
	{
		int counter = 0;
		if (reportLampiranC == null)
		{
			reportLampiranC = new ArrayList<PostFellingReportElement>();
			ArrayList<PostFellingSurveyCard> postFellingSurveyCards = postFellingSurvey.getPostFellingSurveyCards();
			PostFellingReportElement reportElement = new PostFellingReportElement();
			int bilPokokPlotAsal = 0, bilPokokPlotSemak = 0;
			double silap = 0;
			String data[];
			
			for (PostFellingSurveyCard postFellingSurveyCard : postFellingSurveyCards)
			{	
				if (postFellingSurveyCard.isInspection())
				{
						
					Map<String,Integer> species=new HashMap<>();
					Map<String,Integer> inspectionSpecies=new HashMap<>();
					Map<String,Integer> diameter=new HashMap<>();
					Map<String,Integer> inspectionDiameter=new HashMap<>();
					counter++;
						bilPokokPlotAsal = 0; bilPokokPlotSemak = 0;
						data = new String[9];
						ArrayList<PostFellingSurveyRecord> surveyRecords =  postFellingSurveyCard.getPostFellingSurveyRecords();
						for (PostFellingSurveyRecord surveyRecord : surveyRecords)
						{
							if(surveyRecord.getPlotTypeID() == 5)
							{
								if (species.containsKey(surveyRecord.getSpeciesID()+""))
									species.put(surveyRecord.getSpeciesID()+"", (species.get(surveyRecord.getSpeciesID()+"") + 1));
								else
									species.put(surveyRecord.getSpeciesID()+"", 1);
								
								
								if (inspectionSpecies.containsKey(surveyRecord.getInspectionSpeciesID()+""))
									inspectionSpecies.put(surveyRecord.getInspectionSpeciesID()+"", inspectionSpecies.get(surveyRecord.getInspectionSpeciesID()+"") + 1);
								else
									inspectionSpecies.put(surveyRecord.getInspectionSpeciesID()+"", 1);
								
								
								if (diameter.containsKey(surveyRecord.getDiameter()+""))
									diameter.put(surveyRecord.getDiameter()+"", diameter.get(surveyRecord.getDiameter()+"") + 1);
								else
									diameter.put(surveyRecord.getDiameter()+"", 1);
								
								
								if (inspectionDiameter.containsKey(surveyRecord.getInspectionDiameter()+""))
									inspectionDiameter.put(surveyRecord.getInspectionDiameter()+"", inspectionDiameter.get(surveyRecord.getInspectionDiameter()+"") + 1);
								else
									inspectionDiameter.put(surveyRecord.getInspectionDiameter()+"", 1);
								
								if (surveyRecord.getDiameter() != null && surveyRecord.getDiameter()>0)
									bilPokokPlotAsal++;
								
								if (surveyRecord.getInspectionDiameter() != null && surveyRecord.getInspectionDiameter() > 0)
									bilPokokPlotSemak++;		
							}
							
						}
						silap = getErrorRate(bilPokokPlotAsal,bilPokokPlotSemak);
						Double[] errorRateSpecies = getErrorRate(species,inspectionSpecies);
						Double[] errorRateDiameter = getErrorRate(diameter,inspectionDiameter);
						
						data[0] = counter+""; 
						data[1] = postFellingSurveyCard.getLineNo()+" ("+ postFellingSurveyCard.getPlotNo()+")";
						
						data[2] = bilPokokPlotAsal+""; 
						data[3] = bilPokokPlotSemak+""; 
						data[4] = (int)silap+""; 
						data[5] = errorRateSpecies[0].intValue()+"";//bilPokokPlot1Asal+""; 
						data[6] = df.format(errorRateSpecies[1]);//df.format(silap1)+"";
						data[7] = errorRateDiameter[0].intValue()+""; 
						data[8] = df.format(errorRateDiameter[1]);//df.format(silap1)+"";

						
						reportElement.setData(data);
						reportLampiranC.add(reportElement);
						reportElement = new PostFellingReportElement();
				}		
			}	
		}
		return reportLampiranC;
	}

	public ArrayList<PostFellingReportElement> getReportLampiranD()
	{
		int counter = 0;
		if (reportLampiranD == null)
		{
			reportLampiranD = new ArrayList<PostFellingReportElement>();
			ArrayList<PostFellingSurveyCard> postFellingSurveyCards = postFellingSurvey.getPostFellingSurveyCards();
			PostFellingReportElement reportElement = new PostFellingReportElement();
			int bilAnakPokok = 0, bilAnakPokokSemak = 0,bilBenihAsal = 0, bilBenihSemak = 0;
			double silapAnakPokok = 0, silapBenih = 0;
			String data[];
			
			for (PostFellingSurveyCard postFellingSurveyCard : postFellingSurveyCards)
			{	
				if (postFellingSurveyCard.isInspection())
				{
					
					Map<String,Integer> speciesSapling=new HashMap<>();
					Map<String,Integer> inspectionSpeciesSapling=new HashMap<>();
					Map<String,Integer> speciesSeedling=new HashMap<>();
					Map<String,Integer> inspectionSpeciesSeedling=new HashMap<>();
					
			
					counter++;
						bilAnakPokok = 0; bilAnakPokokSemak = 0; bilBenihAsal = 0; bilBenihSemak = 0;
						data = new String[12];
						ArrayList<PostFellingSurveyRecord> surveyRecords =  postFellingSurveyCard.getPostFellingSurveyRecords();
						for (PostFellingSurveyRecord surveyRecord : surveyRecords)
						{
							
							if(surveyRecord.getPlotTypeID() == 6)
							{
								
								if(surveyRecord.getSaplingQuantity() > 0)
								{
									if (speciesSapling.containsKey(surveyRecord.getSpeciesID()+""))
										speciesSapling.put(surveyRecord.getSpeciesID()+"", (speciesSapling.get(surveyRecord.getSpeciesID()+"") + 1));
									else
										speciesSapling.put(surveyRecord.getSpeciesID()+"", 1);
									
									
									if (inspectionSpeciesSapling.containsKey(surveyRecord.getInspectionSpeciesID()+""))
										inspectionSpeciesSapling.put(surveyRecord.getInspectionSpeciesID()+"", inspectionSpeciesSapling.get(surveyRecord.getInspectionSpeciesID()+"") + 1);
									else
										inspectionSpeciesSapling.put(surveyRecord.getInspectionSpeciesID()+"", 1);
								}
							
								if(surveyRecord.getSeedlingQuantity() > 0)
								{
									if (speciesSeedling.containsKey(surveyRecord.getSpeciesID()+""))
										speciesSeedling.put(surveyRecord.getSpeciesID()+"", (speciesSeedling.get(surveyRecord.getSpeciesID()+"") + 1));
									else
										speciesSeedling.put(surveyRecord.getSpeciesID()+"", 1);
									
									
									if (inspectionSpeciesSeedling.containsKey(surveyRecord.getInspectionSpeciesID()+""))
										inspectionSpeciesSeedling.put(surveyRecord.getInspectionSpeciesID()+"", inspectionSpeciesSeedling.get(surveyRecord.getInspectionSpeciesID()+"") + 1);
									else
										inspectionSpeciesSeedling.put(surveyRecord.getInspectionSpeciesID()+"", 1);
								}
								
								
								    bilAnakPokok+=surveyRecord.getSaplingQuantity();
									bilAnakPokokSemak+=surveyRecord.getInspectionSaplingQuantity();
									bilBenihAsal+=surveyRecord.getSeedlingQuantity();
									bilBenihSemak+=surveyRecord.getInspectionSeedlingQuantity();
								
										
							}
						}
						silapAnakPokok = getErrorRate(bilAnakPokok,bilAnakPokokSemak);
						silapBenih =  getErrorRate(bilBenihAsal,bilBenihSemak);
						Double[] errorRateSpeciesSapling = getErrorRate(speciesSapling,inspectionSpeciesSapling);
						Double[] errorRateSpeciesSeedling = getErrorRate(speciesSeedling,inspectionSpeciesSeedling);
						data[0] = counter+""; 
						data[1] = postFellingSurveyCard.getLineNo()+" ("+ postFellingSurveyCard.getPlotNo()+")";
						
						data[2] = bilAnakPokok+""; 
						data[3] = bilAnakPokokSemak+""; 
						data[4] = (int)silapAnakPokok+""; 
						data[5] = errorRateSpeciesSapling[0].intValue()+"";
						data[6] = df.format(errorRateSpeciesSapling[1]);
						data[7] = bilBenihAsal+""; 
						data[8] = bilBenihSemak+"";

						data[9] = (int)silapBenih+""; 
						data[10] = errorRateSpeciesSeedling[0].intValue()+"";
						data[11] = df.format(errorRateSpeciesSeedling[1]);
						
						
						reportElement.setData(data);
						reportLampiranD.add(reportElement);
						reportElement = new PostFellingReportElement();
				}		
			}	
		}
		return reportLampiranD;
	}
	

	
	public ArrayList<PostFellingReportElement> getReportLampiranE()
	{
		int counter = 0;
		if (reportLampiranE == null)
		{
			reportLampiranE = new ArrayList<PostFellingReportElement>();
			ArrayList<PostFellingSurveyCard> postFellingSurveyCards = postFellingSurvey.getPostFellingSurveyCards();
			PostFellingReportElement reportElement = new PostFellingReportElement();
			double silap = 0;
			String data[];
			
			for (PostFellingSurveyCard postFellingSurveyCard : postFellingSurveyCards)
			{	
				if (postFellingSurveyCard.isInspection())
				{
						counter++;
						data = new String[5];
						
						silap = getErrorRate(postFellingSurveyCard.getBamboo(),postFellingSurveyCard.getInspectionBamboo());
						data[0] = counter+""; 
						data[1] = postFellingSurveyCard.getLineNo()+" ("+ postFellingSurveyCard.getPlotNo()+")";
						
						data[2] = postFellingSurveyCard.getBamboo()+""; 
						data[3] = postFellingSurveyCard.getInspectionBamboo()+""; 
						data[4] = (int)silap+""; 
						reportElement.setData(data);
						reportLampiranE.add(reportElement);
						reportElement = new PostFellingReportElement();
				}		
			}	
		}
		return reportLampiranE;
	}
	
	public ArrayList<PostFellingReportElement> getReportLampiranF()
	{
		int counter = 0;
		if (reportLampiranF == null)
		{
			reportLampiranF = new ArrayList<PostFellingReportElement>();
			ArrayList<PostFellingSurveyCard> postFellingSurveyCards = postFellingSurvey.getPostFellingSurveyCards();
			PostFellingReportElement reportElement = new PostFellingReportElement();
			double silapPalma = 0, silapBertam = 0, silapPepanjat = 0;
			int bilPepanjatAsal = 0,  bilPepanjatSemak = 0; 
			String data[];
			
			for (PostFellingSurveyCard postFellingSurveyCard : postFellingSurveyCards)
			{	
				if (postFellingSurveyCard.isInspection())
				{
						counter++;
						data = new String[11];
						bilPepanjatAsal = 0;  bilPepanjatSemak = 0;
						ArrayList<PostFellingSurveyRecord> surveyRecords =  postFellingSurveyCard.getPostFellingSurveyRecords();
						for (PostFellingSurveyRecord surveyRecord : surveyRecords)
						{
							bilPepanjatAsal+=surveyRecord.getVine();
							bilPepanjatSemak+=surveyRecord.getInspectionVine();
						}
						
						silapPalma = getErrorRate(postFellingSurveyCard.getPalm(),postFellingSurveyCard.getInspectionPalm());
						silapBertam = getErrorRate(postFellingSurveyCard.getBertam(),postFellingSurveyCard.getInspectionBertam());
						silapPepanjat = getErrorRate(bilPepanjatAsal,bilPepanjatSemak);
						
						data[0] = counter+""; 
						data[1] = postFellingSurveyCard.getLineNo()+" ("+ postFellingSurveyCard.getPlotNo()+")";
						
						data[2] = postFellingSurveyCard.getPalm()+""; 
						data[3] = postFellingSurveyCard.getInspectionPalm()+""; 
						data[4] = df.format(silapPalma)+""; 
						data[5] = postFellingSurveyCard.getBertam()+""; 
						data[6] = postFellingSurveyCard.getInspectionBertam()+""; 
						data[7] = df.format(silapBertam)+"";
						data[8] = bilPepanjatAsal+""; 
						data[9] = bilPepanjatSemak+""; 
						data[10] = df.format(silapPepanjat)+"";
						
						
						reportElement.setData(data);
						reportLampiranF.add(reportElement);
						reportElement = new PostFellingReportElement();
				}		
			}	
		}
		return reportLampiranF;
	}
	
	public ArrayList<PostFellingReportElement> getReportLampiranG()
	{
		int counter = 0;
		if (reportLampiranG == null)
		{
			reportLampiranG = new ArrayList<PostFellingReportElement>();
			ArrayList<PostFellingSurveyCard> postFellingSurveyCards = postFellingSurvey.getPostFellingSurveyCards();
			PostFellingReportElement reportElement = new PostFellingReportElement();
			double silapResam = 0, silapHalia = 0, silapPisang = 0;
			String data[];
			
			for (PostFellingSurveyCard postFellingSurveyCard : postFellingSurveyCards)
			{	
				if (postFellingSurveyCard.isInspection())
				{
						counter++;
						data = new String[11];
						
						silapResam = getErrorRate(postFellingSurveyCard.getResamID(),postFellingSurveyCard.getInspectionResamID());
						silapHalia = getErrorRate(postFellingSurveyCard.getGingerID(),postFellingSurveyCard.getInspectionGingerID());
						silapPisang = getErrorRate(postFellingSurveyCard.getBananaID(),postFellingSurveyCard.getInspectionBananaID());
						data[0] = counter+""; 
						data[1] = postFellingSurveyCard.getLineNo()+" ("+ postFellingSurveyCard.getPlotNo()+")";
						 
						data[2] = postFellingSurveyCard.getResamID()+""; 
						data[3] = postFellingSurveyCard.getInspectionResamID()+""; 
						data[4] = (int)silapResam+""; 
						
						data[5] = postFellingSurveyCard.getGingerID()+""; 
						data[6] = postFellingSurveyCard.getInspectionGingerID()+""; 
						data[7] = (int)silapHalia+""; 
						
						data[8] = postFellingSurveyCard.getBananaID()+""; 
						data[9] = postFellingSurveyCard.getInspectionBananaID()+""; 
						data[10] = (int)silapPisang+""; 
						
						reportElement.setData(data);
						reportLampiranG.add(reportElement);
						reportElement = new PostFellingReportElement();
				}		
			}	
		}
		return reportLampiranG;
	}
	public String[] getHeaders()
	{
		return headers;
	}

	public void setHeaders(String[] headers)
	{
		this.headers = headers;
	}

	private static double getErrorRate(int surveyValue, int inspectionValue )
	{
		
		
		if (inspectionValue==0 && surveyValue >0) 
			return 100;
		else if (inspectionValue>0 && surveyValue == 0) 
			return ((double)Math.abs(inspectionValue)/(double)1*100f);
		else if (inspectionValue==0 && surveyValue == 0) 
			return 0;
		else 
			return ((double)Math.abs(inspectionValue-surveyValue)/(double)inspectionValue*100f);
		
	}
	

	private static Double[] getErrorRate(Map<String, Integer> surveyData, Map<String, Integer> inspectionData)
	{
		Double[] rate = new Double[2];
		ArrayList<String> keyList = new ArrayList<String>();
		int i=0,s=0, diff = 0, totalInspection = 0;
		
		Set<String> keys = inspectionData.keySet();
		Iterator<String> iterator = keys.iterator();
		while (iterator.hasNext()) {
		    String key = iterator.next();
		    keyList.add(key);
		    if (!surveyData.containsKey(key)) surveyData.put(key, 0);
		
	    		i = inspectionData.get(key);
		    	s = surveyData.get(key);
		    diff += Math.abs(i - s);
		    totalInspection += i;
		}
		
		
		
		rate[0] = (double) diff;
		if (totalInspection > 0 )
			rate[1] = (double)diff/(double)totalInspection*100f;
		else
			rate[1] = 0.0;
		
		
		return rate;
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

		if (obj instanceof PostFellingInspectionReport)
		{
			PostFellingInspectionReport that = (PostFellingInspectionReport) obj;
			equal = postFellingSurvey.equals(that.postFellingSurvey);
		}

		return equal;
	}
	
	public String[] getBearingGarisResult()
	{
		return bearingGarisResult;
	}

	public void setBearingGarisResult(String[] bearingGarisResult)
	{
		this.bearingGarisResult = bearingGarisResult;
	}
	
	
}