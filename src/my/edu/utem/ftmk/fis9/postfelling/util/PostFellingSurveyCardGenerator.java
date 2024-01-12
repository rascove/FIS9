package my.edu.utem.ftmk.fis9.postfelling.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PatternColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPatternPainter;
import com.itextpdf.text.pdf.PdfWriter;
import my.edu.utem.ftmk.fis9.maintenance.model.Contractor;
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingSurvey;
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingSurveyCard;
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingSurveyRecord;


/**
 * @author Satrya Fajri Pratama, Zurina
 */
public class PostFellingSurveyCardGenerator
{
	private static Font size9 = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);
	private static Font size7 = new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL);
	private static Font size10bold = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
	private static Font size9bold = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);

	public static void generate(File file, PostFellingSurvey postFellingSurvey, Contractor contractor, LinkedHashMap<String, String> map) throws Exception
	{
			
		Document document = new Document(PageSize.A4.rotate(), 14.175f, 14.175f, 14.175f, 14.175f);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));

		document.open();

		document.addTitle("KAD INVENTORI HUTAN SELEPAS TEBANGAN");
		document.addSubject("Jabatan Perhutanan Negeri Sembilan");
		document.addKeywords("Post-F");
		document.addAuthor("JPNS");
		document.addCreator("JPNS");
		
		ArrayList<PostFellingSurveyCard> postFellingSurveyCards = postFellingSurvey.getPostFellingSurveyCards();
		
		SimpleDateFormat sdf = new SimpleDateFormat("d MMMM yyyy", new Locale("ms"));

		PdfContentByte canvas = writer.getDirectContent();
		PdfPatternPainter line = canvas.createPattern(16, 10);
		
		line.setColorStroke(new BaseColor(0, 128, 0));
		line.setLineWidth(9);
		line.moveTo(4, -1);
		line.lineTo(4, 11);
		line.stroke();
		
		line.setColorStroke(BaseColor.WHITE);
		line.setLineWidth(8);
		line.moveTo(13, -1);
		line.lineTo(13, 11);
		line.stroke();
		document.newPage();
		
		PatternColor pattern = new PatternColor(line);

		for (PostFellingSurveyCard postFellingSurveyCard : postFellingSurveyCards)
		{
			//if (postFellingSurveyCards.indexOf(postFellingSurveyCard) > 1)
				document.newPage();

			ArrayList<PostFellingSurveyRecord> utama1 = new ArrayList<>();
			ArrayList<PostFellingSurveyRecord> utama2 = new ArrayList<>();
			ArrayList<PostFellingSurveyRecord> subpetakA = new ArrayList<>();
			ArrayList<PostFellingSurveyRecord> petak3 = new ArrayList<>();
			ArrayList<PostFellingSurveyRecord> petak4 = new ArrayList<>();
			ArrayList<PostFellingSurveyRecord> postFellingSurveyRecords = postFellingSurveyCard.getPostFellingSurveyRecords();
			int color = 0, count45 = 0, count30 = 0, count15 = 0, count5 = 0, countA = 0, countE = 0, countC = 0, countCT = 0, countPCT = 0,countNCT = 0;
			String type1 = "", type2 = "";
			
		
			

			for (PostFellingSurveyRecord postFellingSurveyRecord : postFellingSurveyRecords)
			{
				String code = postFellingSurveyRecord.getSpeciesCode(), symbol = map.get(code == null ? "" : code);
				int logQuality = postFellingSurveyRecord.getLogQualityID(), fertilityID = postFellingSurveyRecord.getFertilityID(), vineNo = postFellingSurveyRecord.getVine();
				int treeStatus = postFellingSurveyRecord.getTreeStatusID();
				double diameter = (postFellingSurveyRecord.getDiameter()==null ? 0: postFellingSurveyRecord.getDiameter());

				if (treeStatus == 1)
					countCT++;
				else if (treeStatus == 2)
					countPCT++;
				else if (treeStatus == 3)
					countNCT++;
				
				if ((treeStatus == 1 || treeStatus == 2) && vineNo > 0)
					countC++;
												
				
				if (postFellingSurveyRecord.getPlotTypeID() == 1)
				{
					utama1.add(postFellingSurveyRecord);
											
					if (symbol != null)
					{
						if (diameter >= 60)
							color = 8;
						else if (diameter >= 45 && logQuality <= 2)
							count45++;

						if (!type1.contains(symbol))
							type1 += symbol;
					}
				}
				else if (postFellingSurveyRecord.getPlotTypeID() == 2)
				{
					utama2.add(postFellingSurveyRecord);

					if (symbol != null && diameter >= 30 && logQuality <= 2)
					{
						if (diameter == 45)
							count45++;
						else
							count30++;

						if (!type1.contains(symbol))
							type1 += symbol;
					}
				}
				else if (postFellingSurveyRecord.getPlotTypeID() == 3)
				{
					subpetakA.add(postFellingSurveyRecord);
					if (symbol != null && diameter >= 15)
						count15++;
				}
			
				else if (postFellingSurveyRecord.getPlotTypeID() == 5)
				{
					petak3.add(postFellingSurveyRecord);
					

					if (symbol != null && diameter >= 5 && fertilityID == 1)
						count5++;
				}
				else if (postFellingSurveyRecord.getPlotTypeID() == 6)
				{
					petak4.add(postFellingSurveyRecord);

					countA += postFellingSurveyRecord.getSaplingQuantity();
					countE += postFellingSurveyRecord.getSeedlingQuantity();
				}
			}

			if (postFellingSurveyCard.getBertam() >= 5)
				type2 += "BT ";
			
			if (postFellingSurveyCard.getBamboo() >= 5) 
				type2 += "B ";

			if (postFellingSurveyCard.getPalm() >= 9)
				type2 += "P ";
			
			if (postFellingSurveyCard.getResamID() >=2)
				type2 += "R ";

			if (postFellingSurveyCard.getGingerID() >= 2)
				type2 += "H ";
			
			if (postFellingSurveyCard.getBananaID() >= 2)
				type2 += "S ";
			
			if (countC >= 2)
				type2 += "C ";
			
			if (!postFellingSurveyRecords.isEmpty())
			{
				if ((countCT >= countPCT) && (countCT >= countNCT)) 
					type2 +="CT ";
				else if ((countPCT >= countCT) && (countPCT >= countNCT)) 
					type2 += "PCT ";
				else if ((countNCT >= countCT) && (countNCT >= countPCT)) 
					type2 += "NCT ";
			
			
				if (color == 0)
				{	
					if (count45 >= 2 && count30 >= 4)
						color = 7;
					else if (count45 >= 2 && count30 < 4)
						color = 6; 
					else if (count45 < 4 && count30 >= 4)
						color = 5;
					else if (count15 >= 4)
						color = 4; 
					else if (count5 >= 4)
						color = 3;
					else if (countA >= 4)
						color = 2;
					else if (countE >= 4)
						color = 1;
					else
						color = 0;
				}
			}
			
			// 0=white, 1=orange, 2=red, 3=yellow, 4=brown, 5=blue, 6=green_stripe, 7=green, 8=purple 
			
			
			
				
			PdfPTable head = new PdfPTable(3);
			head.setWidthPercentage(98.4f);
			head.setHorizontalAlignment(Element.ALIGN_RIGHT);
			head.setWidths(new float[] {35f, 56f, 9f});
			head.getDefaultCell().setPadding(0);
			PdfPCell emptyCell = new PdfPCell(new Phrase("", size9));
			emptyCell.setBorder(0);
			PdfPCell title = new PdfPCell(new Phrase("KAD INVENTORI HUTAN SELEPAS TEBANGAN", size10bold));
			title.setVerticalAlignment(Element.ALIGN_MIDDLE);
			title.setHorizontalAlignment(Element.ALIGN_CENTER);
			
				
			
			PdfPCell cardNo = new PdfPCell(new Phrase("NO. KAD\n" + postFellingSurveyCard.getLineNo() + "-" + postFellingSurveyCard.getPlotNo() + "\n" + (color > 5 ? type1 : "") + "\n" + type2, size9));
			BaseColor[] colors = new BaseColor[] {BaseColor.WHITE, BaseColor.ORANGE, BaseColor.RED, BaseColor.YELLOW, new BaseColor(165, 42, 42), BaseColor.BLUE, pattern, new BaseColor(0, 128, 0), new BaseColor(128, 0, 128)};
			// 0=white, 1=orange, 2=red, 3=yellow, 4=brown, 5=blue, 6=green_stripe, 7=green, 8=purple 
			cardNo.setBackgroundColor(colors[color]);
			cardNo.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cardNo.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPTable middle = new PdfPTable(5);
			middle.setWidthPercentage(100);
			middle.getDefaultCell().setBorder(0);	
			middle.getDefaultCell().setPaddingBottom(1f);
			middle.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);;	
			middle.setWidths(new float[] {20f, 51.9f, 0.1f, 8f, 20f});
			
			
			PdfPCell[] middleCell = new PdfPCell[] {
					new PdfPCell(new Phrase("KETUA PASUKAN:", size7)),
					new PdfPCell(new Phrase(postFellingSurvey.getTeamLeaderName(), size7)),
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("TARIKH:", size7)),
					new PdfPCell(new Phrase(sdf.format(postFellingSurveyCard.getSurveyDate()).toUpperCase(), size7)),
					new PdfPCell(new Phrase("PEGAWAI REKOD KOD:", size7)),
					new PdfPCell(new Phrase(postFellingSurveyCard.getRecorderName(), size7)),
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("TARIKH:", size7)),
					new PdfPCell(new Phrase(sdf.format(postFellingSurveyCard.getSurveyDate()).toUpperCase(), size7)),
					new PdfPCell(new Phrase("PEGAWAI PEMERIKSA:", size7)),
					new PdfPCell(new Phrase(postFellingSurveyCard.getInspectorName() == null ? "-" : postFellingSurveyCard.getInspectorName(), size7)),
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("TARIKH:", size7)),
					new PdfPCell(new Phrase(postFellingSurveyCard.getInspectionDate() == null ? "-" : sdf.format(postFellingSurveyCard.getInspectionDate()).toUpperCase(), size7))
			};
			
			
			for (int i = 0; i<middleCell.length;i++)
			{		
				if (i<10) 
				{
					middleCell[i].setUseVariableBorders(true);
					middleCell[i].setBorder(Rectangle.BOTTOM);
				}
				else
				{
					middleCell[i].setBorder(0);
				}
				middleCell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				
				middle.addCell(middleCell[i]);
			}
				
			
			
			//head.addCell(emptyCell);
			head.addCell(title);
			head.addCell(middle);
			head.addCell(cardNo);

			PdfPTable maklumatA = new PdfPTable(26);
			PdfPCell[] headAs = new PdfPCell[] {
					new PdfPCell(new Phrase(" TAHUN", size9)),
					new PdfPCell(new Phrase(" NEGERI", size9)),
					new PdfPCell(new Phrase(" H/SIMPAN\n DAERAH", size9)),
					new PdfPCell(new Phrase(" NOMBOR\n KOMPT/\n BLOK", size9)),
					new PdfPCell(new Phrase(" BILANGAN\n GARISAN", size9)),
					new PdfPCell(new Phrase(" BILANGAN\n PETAK", size9)),
					new PdfPCell(new Phrase(" JENIS\n HUTAN", size9)),
					new PdfPCell(new Phrase(" K. TANAH", size9)),
					new PdfPCell(new Phrase(" ASPEK", size9)),
					new PdfPCell(new Phrase(" CERUN", size9)),
					new PdfPCell(new Phrase(" T. CERUN", size9)),
					new PdfPCell(new Phrase(" DONGAKAN", size9)),
					new PdfPCell(new Phrase(" BERTAM", size9)),
					new PdfPCell(new Phrase(" BULUH", size9)),
					new PdfPCell(new Phrase(" PALMA", size9)),
					new PdfPCell(new Phrase(" RESAM", size9)),
					new PdfPCell(new Phrase(" HALIA", size9)),
					new PdfPCell(new Phrase(" PISANG", size9))
			};
			
			PdfPCell[] headA2s = new PdfPCell[26];
					
			for (int i= 0; i<26; i++)
			{
				headA2s[i] = new PdfPCell(new Phrase((i+1)+"", size7));
			}
			

			maklumatA.setSpacingBefore(5f);
			maklumatA.setWidthPercentage(98.4f);
			maklumatA.setHorizontalAlignment(Element.ALIGN_RIGHT);
			maklumatA.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			
			

			for (PdfPCell headA : headAs)
			{
				headA.setRowspan(2);
				headA.setRotation(90);
				headA.setFixedHeight(60f);
				headA.setVerticalAlignment(Element.ALIGN_MIDDLE);
				headA.setHorizontalAlignment(Element.ALIGN_CENTER);
			}
			
			headAs[1].setColspan(2);
			headAs[2].setColspan(2);
			headAs[3].setColspan(3);
			headAs[4].setColspan(2);
			headAs[5].setColspan(2);
			headAs[6].setColspan(2);
			headAs[9].setColspan(2);

			for (PdfPCell headA : headA2s)
			{
				headA.setVerticalAlignment(Element.ALIGN_MIDDLE);
				headA.setHorizontalAlignment(Element.ALIGN_CENTER);
			}
			
			//maklumatA.addCell(emptyCellmaklumatA);
			for (PdfPCell headA : headAs)
				maklumatA.addCell(headA);
			
			
			
			for (PdfPCell headA : headA2s)
				maklumatA.addCell(headA);
			
			PdfPCell[] maklumat = new PdfPCell[] {
					new PdfPCell(new Phrase("" + postFellingSurvey.getYear(), size9)),
					new PdfPCell(new Phrase(postFellingSurvey.getStateCode(), size9)),
					new PdfPCell(new Phrase(postFellingSurvey.getForestCode(), size9)),
					new PdfPCell(new Phrase(postFellingSurvey.getComptBlockNo(), size9)),
					new PdfPCell(new Phrase("" + postFellingSurveyCard.getLineNo(), size9)),
					new PdfPCell(new Phrase("" + postFellingSurveyCard.getPlotNo(), size9)),
					new PdfPCell(new Phrase(postFellingSurveyCard.getForestType(), size9)),
					new PdfPCell(new Phrase(postFellingSurveyCard.getSoilStatus(), size9)),
					new PdfPCell(new Phrase(postFellingSurveyCard.getAspect(), size9)),
					new PdfPCell(new Phrase("" + postFellingSurveyCard.getSlope(), size9)),
					new PdfPCell(new Phrase(postFellingSurveyCard.getSlopeLocation(), size9)),
					new PdfPCell(new Phrase(postFellingSurveyCard.getElevation(), size9)),
					new PdfPCell(new Phrase("" + postFellingSurveyCard.getBertam(), size9)),
					new PdfPCell(new Phrase("" + postFellingSurveyCard.getBamboo(), size9)),
					new PdfPCell(new Phrase("" + postFellingSurveyCard.getPalm(), size9)),
					new PdfPCell(new Phrase(postFellingSurveyCard.getResam(), size9)),
					new PdfPCell(new Phrase(postFellingSurveyCard.getGinger(), size9)),
					new PdfPCell(new Phrase(postFellingSurveyCard.getBanana(), size9))
			};
			
			
			for (PdfPCell headA : maklumat)
			{
				headA.setVerticalAlignment(Element.ALIGN_MIDDLE);
				headA.setHorizontalAlignment(Element.ALIGN_CENTER);
			}
			
			maklumat[1].setColspan(2);
			maklumat[2].setColspan(2);
			maklumat[3].setColspan(3);
			maklumat[4].setColspan(2);
			maklumat[5].setColspan(2);
			maklumat[6].setColspan(2);
			maklumat[9].setColspan(2);
			
			
			for (PdfPCell headA : maklumat)
				maklumatA.addCell(headA);
			
			

			PdfPTable maklumatB = new PdfPTable(46);

			maklumatB.setSpacingBefore(5f);
			maklumatB.setWidthPercentage(100);
			
			float[] cellSize = new float[46];
			for (int i = 0; i <= 45; i++)
			{			
				if (i==5 || i== 21 || i == 36 )
					cellSize[i] = 10f;
				else if ((i>=6 && i<=11) || i>=22 && i<=26 || (i>=37 && i<=41))
					cellSize[i] = 1.7f;
				else if (i == 16 || i == 31 )
					cellSize[i] = 0.3f;
				else
					cellSize[i] = 1.5f;
			}
			
			maklumatB.setWidths(cellSize);

			PdfPCell[] headMain1 = new PdfPCell[] {
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("KOD", size7)),
					new PdfPCell(new Phrase("PETAK UTAMA\n[50m X 20m]\nSEMUA POKOK\n> 45cm", size7)),
					new PdfPCell(new Phrase("DIAMETER", size7))
			};
			
			headMain1[0].setBorder(0);
			headMain1[1].setColspan(4);
			headMain1[3].setColspan(4);
			
			PdfPCell headBalak = new PdfPCell(new Phrase("TUAL BALAK", size7));
			PdfPCell[] subheadBalak = new PdfPCell[] {
					new PdfPCell(new Phrase("BIL.", size7)),
					new PdfPCell(new Phrase("KUALITI", size7)),
			};
			
			
			PdfPCell[] headTree = new PdfPCell[] {
					new PdfPCell(new Phrase("STATUS", size7)),
					new PdfPCell(new Phrase("SILARA", size7)),
					new PdfPCell(new Phrase("UNGGUL", size7)),
					new PdfPCell(new Phrase("PEPANJAT", size7)),
			};
			PdfPCell[] headMain2 = new PdfPCell[] {
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("KOD", size7)),
					new PdfPCell(new Phrase("PETAK UTAMA\n[50m X 20m]\nSEMUA POKOK\n> 30cm - 45cm", size7)),
					new PdfPCell(new Phrase("DIAMETER", size7))
			};
			
			headMain2[0].setBorder(0);
			headMain2[1].setColspan(4);
			headMain2[3].setColspan(3);
			
			PdfPCell[] headMain3 = new PdfPCell[] {
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("KOD", size7)),
					new PdfPCell(new Phrase("PETAK KEDUA\n[25m X 20m]\nSEMUA POKOK\n> 15cm - 30cm", size7)),
					new PdfPCell(new Phrase("DIAMETER", size7))
			};
			headMain3[0].setBorder(0);
			headMain3[1].setColspan(4);
			headMain3[3].setColspan(3);
			
					
			
			PdfPCell[] headBDesc1s = new PdfPCell[46];
			
			headBDesc1s[0]= new PdfPCell(new Phrase("", size7));
			int c = 26;
			for (int i = 1; i <= 45; i++)
			{
				c++;
				if (i==5 || i== 21 || i == 36 ) {
					headBDesc1s[i]= new PdfPCell(new Phrase("JENIS", size7));
					c--;
				}else if (i == 16 || i == 31 )
				{
					headBDesc1s[i]= new PdfPCell(new Phrase("", size7));
					headBDesc1s[i].setBorder(0);
					c--;
				}
				else
					headBDesc1s[i]= new PdfPCell(new Phrase(""+c, size7));
			}
			
			
			for (PdfPCell headB : headMain1)
			{
				headB.setRowspan(2);
				headB.setVerticalAlignment(Element.ALIGN_MIDDLE);
				headB.setHorizontalAlignment(Element.ALIGN_CENTER);
			}

			
			headBalak.setColspan(2);
			headBalak.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headBalak.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			for (PdfPCell subheadB : subheadBalak)
			{
				subheadB.setRotation(90);
				subheadB.setVerticalAlignment(Element.ALIGN_MIDDLE);
				subheadB.setHorizontalAlignment(Element.ALIGN_CENTER);
			}

			for (PdfPCell headB : headTree)
			{
				headB.setRowspan(2);
				headB.setRotation(90);
				headB.setVerticalAlignment(Element.ALIGN_MIDDLE);
				headB.setHorizontalAlignment(Element.ALIGN_CENTER);
			}
			
			for (PdfPCell headB : headMain2)
			{
				headB.setRowspan(2);
				headB.setVerticalAlignment(Element.ALIGN_MIDDLE);
				headB.setHorizontalAlignment(Element.ALIGN_CENTER);
			}

			for (PdfPCell headB : headMain3)
			{
				headB.setRowspan(2);
				headB.setVerticalAlignment(Element.ALIGN_MIDDLE);
				headB.setHorizontalAlignment(Element.ALIGN_CENTER);
			}


			for (PdfPCell headBDesc : headBDesc1s)
			{
				headBDesc.setVerticalAlignment(Element.ALIGN_MIDDLE);
				headBDesc.setHorizontalAlignment(Element.ALIGN_CENTER);
			}

			headBDesc1s[0].setBorder(0);		
			
			
			for (PdfPCell headB : headMain1)
				maklumatB.addCell(headB);

			maklumatB.addCell(headBalak);

			for (PdfPCell headT : headTree)
				maklumatB.addCell(headT);
		
			for (PdfPCell headB : headMain2)
				maklumatB.addCell(headB);

			maklumatB.addCell(headBalak);
			
			for (PdfPCell headT : headTree)
				maklumatB.addCell(headT);

			for (PdfPCell headB : headMain3)
				maklumatB.addCell(headB);

			maklumatB.addCell(headBalak);
			
			for (PdfPCell headT : headTree)
				maklumatB.addCell(headT);
			
			for (PdfPCell subheadB: subheadBalak)
				maklumatB.addCell(subheadB);
			
			for (PdfPCell subheadB: subheadBalak)
				maklumatB.addCell(subheadB);
			
			for (PdfPCell subheadB: subheadBalak)
				maklumatB.addCell(subheadB);
			

			for (PdfPCell headBDesc : headBDesc1s)
				maklumatB.addCell(headBDesc);
			
			
			int sizeU1 = utama1.size(), sizeU2 = utama2.size(), sizeSA = subpetakA.size(), sizeP3 = petak3.size(), sizeP4 = petak4.size(), max1 = Math.max(Math.max(sizeU1, sizeU2), sizeSA), max2 = Math.max(sizeP3, sizeP4);

			
			max1 = (max1 < 4 ? 4 : max1);
			for (int i = 0; i < max1; i++)
			{
				String code = Character.toString ((char) (65+ i));
				PdfPCell number = new PdfPCell(new Phrase(code, size7));
				PdfPCell[] contentB1s = null, contentB2s = null, contentB3s = null;

				number.setVerticalAlignment(Element.ALIGN_MIDDLE);
				number.setHorizontalAlignment(Element.ALIGN_CENTER);

				maklumatB.addCell(number);

				if (i < sizeU1)
				{
					PostFellingSurveyRecord postFellingSurveyRecord = utama1.get(i);
					contentB1s = new PdfPCell[] {
							new PdfPCell(new Phrase(postFellingSurveyRecord.getSpeciesCode() == null ? "" : postFellingSurveyRecord.getSpeciesCode(), size9)),
							new PdfPCell(new Phrase(postFellingSurveyRecord.getSpeciesName(), size7)),
							new PdfPCell(new Phrase("" + postFellingSurveyRecord.getDiameter(), size9)),
							new PdfPCell(new Phrase("" + postFellingSurveyRecord.getLogQuantity(), size9)),
							new PdfPCell(new Phrase(postFellingSurveyRecord.getLogQuality(), size9)),
							new PdfPCell(new Phrase(postFellingSurveyRecord.getTreeStatus(), size9)),
							new PdfPCell(new Phrase(postFellingSurveyRecord.getSilara(), size9)),
							new PdfPCell(new Phrase(postFellingSurveyRecord.getDominance(), size9)),
							new PdfPCell(new Phrase("" + postFellingSurveyRecord.getVine(), size9))
							
					};
					

					for (PdfPCell contentB1 : contentB1s)
					{
						contentB1.setVerticalAlignment(Element.ALIGN_MIDDLE);
						contentB1.setHorizontalAlignment(Element.ALIGN_CENTER);
						
					}
					
				}
				else
				{
					contentB1s = new PdfPCell[] {
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7))
					};
				}

				contentB1s[0].setColspan(4);
				contentB1s[2].setColspan(4);
				
				

				for (PdfPCell contentB1 : contentB1s)
					maklumatB.addCell(contentB1);

				if (i < sizeU2)
				{
					PostFellingSurveyRecord postFellingSurveyRecord = utama2.get(i);
					contentB2s = new PdfPCell[] {
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase(postFellingSurveyRecord.getSpeciesCode() == null ? "" : postFellingSurveyRecord.getSpeciesCode(), size9)),
							new PdfPCell(new Phrase(postFellingSurveyRecord.getSpeciesName(), size7)),
							new PdfPCell(new Phrase("" + postFellingSurveyRecord.getDiameter(), size9)),
							new PdfPCell(new Phrase("" + postFellingSurveyRecord.getLogQuantity(), size9)),
							new PdfPCell(new Phrase(postFellingSurveyRecord.getLogQuality(), size9)),
							new PdfPCell(new Phrase(postFellingSurveyRecord.getTreeStatus(), size9)),
							new PdfPCell(new Phrase(postFellingSurveyRecord.getSilara(), size9)),
							new PdfPCell(new Phrase(postFellingSurveyRecord.getDominance(), size9)),
							new PdfPCell(new Phrase("" + postFellingSurveyRecord.getVine(), size9)),
							
					};

					for (PdfPCell contentB2 : contentB2s)
					{
						contentB2.setVerticalAlignment(Element.ALIGN_MIDDLE);
						contentB2.setHorizontalAlignment(Element.ALIGN_CENTER);
					}
				}
				else
				{
					contentB2s = new PdfPCell[] {
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7))
					};
				}

				contentB2s[0].setBorder(0);
				contentB2s[1].setColspan(4);
				contentB2s[3].setColspan(3);

				for (PdfPCell contentB2 : contentB2s)
					maklumatB.addCell(contentB2);

				if (i < sizeSA)
				{
					PostFellingSurveyRecord postFellingSurveyRecord = subpetakA.get(i);
					contentB3s = new PdfPCell[] {
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase(postFellingSurveyRecord.getSpeciesCode() == null ? "" : postFellingSurveyRecord.getSpeciesCode(), size9)),
							new PdfPCell(new Phrase(postFellingSurveyRecord.getSpeciesName(), size7)),
							new PdfPCell(new Phrase("" + postFellingSurveyRecord.getDiameter(), size9)),
							new PdfPCell(new Phrase("" + postFellingSurveyRecord.getLogQuantity(), size9)),
							new PdfPCell(new Phrase(postFellingSurveyRecord.getLogQuality(), size9)),
							new PdfPCell(new Phrase(postFellingSurveyRecord.getTreeStatus(), size9)),
							new PdfPCell(new Phrase(postFellingSurveyRecord.getSilara(), size9)),
							new PdfPCell(new Phrase(postFellingSurveyRecord.getDominance(), size9)),
							new PdfPCell(new Phrase("" + postFellingSurveyRecord.getVine(), size9)),
							
					};

					for (PdfPCell contentB3 : contentB3s)
					{
						contentB3.setVerticalAlignment(Element.ALIGN_MIDDLE);
						contentB3.setHorizontalAlignment(Element.ALIGN_CENTER);
					}
				}
				else
				{
					contentB3s = new PdfPCell[] {
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7))
					};
				}

				contentB3s[0].setBorder(0);
				contentB3s[1].setColspan(4);
				contentB3s[3].setColspan(3);

				for (PdfPCell contentB3 : contentB3s)
					maklumatB.addCell(contentB3);
			}

		

			PdfPTable boxBottom = new PdfPTable(3);
			boxBottom.setSpacingBefore(5f);
			boxBottom.setHorizontalAlignment(Element.ALIGN_RIGHT);
			boxBottom.setWidthPercentage(100);
			boxBottom.setPaddingTop(0);
			boxBottom.getDefaultCell().setBorder(0);
			boxBottom.setWidths(new float[] {66.1f, 0.4f, 33.5f});
			boxBottom.getDefaultCell().setPadding(0);
			PdfPTable maklumatC = new PdfPTable(20);
			maklumatC.setWidthPercentage(100);
			maklumatC.getDefaultCell().setBorder(0);
			
			cellSize = new float[20];
			for (int i = 0; i <= 19; i++)
			{			
				if (i==10)
					cellSize[i] = 0.4f;
				else if ( i==5 || i == 15)
					cellSize[i] = 28f;
				else
					cellSize[i] = 2.5f;
			}
			maklumatC.setWidths(cellSize);
			
			PdfPTable maklumatPejabat = new PdfPTable(1);
			maklumatPejabat.setWidthPercentage(100);
			maklumatPejabat.setWidths(new float[] {100f});
			maklumatPejabat.getDefaultCell().setPadding(0);
			
			
			PdfPCell[] headMain4 = new PdfPCell[] {
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("KOD", size7)),
					new PdfPCell(new Phrase("PETAK KETIGA\n[10m x 10m]\nSEMUA POKOK\n> 5cm - 15cm", size7)),
					new PdfPCell(new Phrase("DIAMETER", size7)),
					new PdfPCell(new Phrase("KESUBURAN", size7)),
			};
			
			headMain4[0].setBorder(0);
			headMain4[1].setColspan(4);
			headMain4[3].setColspan(3);
			headMain4[4].setRotation(90);
			
			
			PdfPCell[] headMain5 = new PdfPCell[] {
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("KOD", size7)),
					new PdfPCell(new Phrase("PETAK KEEMPAT/LIMA\n[5m x 5m/2m x 2m]\nSEMUA POKOK < 5cm", size7)),
					new PdfPCell(new Phrase("BILANGAN\n>1.5M tinggi\n5cm diameter", size7)),
					new PdfPCell(new Phrase("BILANGAN\n15 cm tinggi - \n1.5 m tinggi", size7))
			};
			
			headMain5[3].setRotation(90);
			headMain5[4].setRotation(90);
			headMain5[0].setBorder(0);
			headMain5[1].setColspan(4);
			headMain5[3].setColspan(2);
			headMain5[4].setColspan(2);
			
			
			PdfPTable colorBox = new PdfPTable(18);
			
			colorBox.setWidthPercentage(100);
			colorBox.getDefaultCell().setBorder(0);
			colorBox.setHorizontalAlignment(Element.ALIGN_CENTER);
			colorBox.setWidths(new float[] {28f,7f,1f,7f,1f,7f,1f,7f,1f,7f,1f,7f,1f,7f,1f,7f,1f,7f});
			
			PdfPCell[] colorCell1 = new PdfPCell[] {
					new PdfPCell(new Phrase("", size9)),
					new PdfPCell(new Phrase("U", size9)),
					new PdfPCell(new Phrase("", size9)),
					new PdfPCell(new Phrase("H", size9)),
					new PdfPCell(new Phrase("", size9)),
					new PdfPCell(new Phrase("HJ", size9)),
					new PdfPCell(new Phrase("", size9)),
					new PdfPCell(new Phrase("B", size9)),
					new PdfPCell(new Phrase("", size9)),
					new PdfPCell(new Phrase("C", size9)),
					new PdfPCell(new Phrase("", size9)),
					new PdfPCell(new Phrase("KU", size9)),
					new PdfPCell(new Phrase("", size9)),
					new PdfPCell(new Phrase("M", size9)),
					new PdfPCell(new Phrase("", size9)),
					new PdfPCell(new Phrase("O", size9)),
					new PdfPCell(new Phrase("", size9)),
					new PdfPCell(new Phrase("KO", size9)),
			};
			
			for (PdfPCell cell : colorCell1) {
				cell.setBorder(0);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				colorBox.addCell(cell);
			}
			
			// 0=white, 1=orange, 2=red, 3=yellow, 4=brown, 5=blue, 6=green_stripe, 7=green, 8=purple 
			
			
			PdfPCell[] colorCell2 = new PdfPCell[] {
					new PdfPCell(new Phrase("KOD WARNA", size7)),
					new PdfPCell(new Phrase("", size9)), //8
					new PdfPCell(new Phrase("", size9)),
					new PdfPCell(new Phrase("", size9)), //7
					new PdfPCell(new Phrase("", size9)),
					new PdfPCell(new Phrase("", size9)), //6
					new PdfPCell(new Phrase("", size9)),
					new PdfPCell(new Phrase("", size9)), //5
					new PdfPCell(new Phrase("", size9)), 
					new PdfPCell(new Phrase("", size9)), //4
					new PdfPCell(new Phrase("", size9)),
					new PdfPCell(new Phrase("", size9)), //3
					new PdfPCell(new Phrase("", size9)),
					new PdfPCell(new Phrase("", size9)), //2
					new PdfPCell(new Phrase("", size9)),
					new PdfPCell(new Phrase("", size9)), //1
					new PdfPCell(new Phrase("", size9)),
					new PdfPCell(new Phrase("", size9))  //0
					
			};
			if (color == 8)
				colorCell2[1] = new PdfPCell(new Phrase("X", size9));
			else if (color == 7)
				colorCell2[3] = new PdfPCell(new Phrase("X", size9));
			else if (color == 6)
				colorCell2[5] = new PdfPCell(new Phrase("X", size9));
			else if (color == 5)
				colorCell2[7] = new PdfPCell(new Phrase("X", size9));
			else if (color == 4)
				colorCell2[9] = new PdfPCell(new Phrase("X", size9));
			else if (color == 3)
				colorCell2[11] = new PdfPCell(new Phrase("X", size9));
			else if (color == 2)
				colorCell2[13] = new PdfPCell(new Phrase("X", size9));
			else if (color == 1)
				colorCell2[15] = new PdfPCell(new Phrase("X", size9));
			else if (color == 0)
				colorCell2[17] = new PdfPCell(new Phrase("X", size9));
			
			
			
			
			for (int i = 0; i < colorCell2.length; i++) {
				if (i%2==1) {
					colorCell2[i].setBorder(1);
					colorCell2[i].setUseVariableBorders(true);
					colorCell2[i].setBorder(Rectangle.LEFT | Rectangle.BOTTOM | Rectangle.RIGHT | Rectangle.TOP);
					colorCell2[i].setHorizontalAlignment(Element.ALIGN_CENTER);
					colorCell2[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				}else
				{
					colorCell2[i].setBorder(0);
					colorCell2[i].setHorizontalAlignment(Element.ALIGN_LEFT);
				}
			}
		
			
			for (PdfPCell cell : colorCell2) {
				colorBox.addCell(cell);
			}
			
			
			PdfPCell[] headPejabat = new PdfPCell[] { 
					new PdfPCell(new Phrase("INVENTORI HUTAN SELEPAS TEBANGAN", size9bold)),
					new PdfPCell(new Phrase("KEGUNAAN DI PEJABAT", size7)),
					new PdfPCell(new Phrase("KETUA PASUKAN: "+(postFellingSurvey.getTeamLeaderName()==null ? "-" : postFellingSurvey.getTeamLeaderName())+"\nTARIKH: "+ sdf.format(postFellingSurveyCard.getSurveyDate()).toUpperCase()+"\nPEGAWAI REKOD KOD: "+postFellingSurveyCard.getRecorderName()+"\nTARIKH: " +sdf.format(postFellingSurveyCard.getSurveyDate()).toUpperCase() , size7)),
					new PdfPCell(colorBox),
					new PdfPCell(new Phrase("PEGAWAI MEMPROSES DATA: "+postFellingSurveyCard.getRecorderName()+"\nPEGAWAI YANG MENGESAH: "+(postFellingSurvey.getCreatorName()==null ? "-" : postFellingSurvey.getCreatorName())+" ", size7))
				};
			
			
			headPejabat[0].setHorizontalAlignment(Element.ALIGN_CENTER);
			headPejabat[0].setHorizontalAlignment(Element.ALIGN_CENTER);
			headPejabat[1].setHorizontalAlignment(Element.ALIGN_CENTER);
			for (PdfPCell headC : headPejabat)
			{
				headC.setPadding(5);
				headC.setExtraParagraphSpace(2);
				headC.setVerticalAlignment(Element.ALIGN_MIDDLE);
			}
			headPejabat[3].setExtraParagraphSpace(5);
			headPejabat[3].setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			
			headPejabat[3].setUseVariableBorders(true);
			headPejabat[3].setBorder(Rectangle.LEFT |  Rectangle.RIGHT );
			headPejabat[4].setUseVariableBorders(true);
			headPejabat[4].setBorder(Rectangle.LEFT |  Rectangle.RIGHT | Rectangle.BOTTOM);
		
			
			PdfPCell[] headBDesc2s = new PdfPCell[20];
			
			c = 66;
			for (int i = 0; i <= 19; i++)
			{
				c++;
				if (i==5 || i== 15 ) {
					headBDesc2s[i]= new PdfPCell(new Phrase("JENIS", size7));
					c--;
				}else if (i==0 || i == 10 )
				{
					headBDesc2s[i]= new PdfPCell(new Phrase("", size7));
					headBDesc2s[i].setBorder(0);
					c--;
				}
				else
					headBDesc2s[i]= new PdfPCell(new Phrase(""+c, size7));
			}
			

			for (PdfPCell headC : headMain4)
			{
				headC.setRowspan(2);
				headC.setVerticalAlignment(Element.ALIGN_MIDDLE);
				headC.setHorizontalAlignment(Element.ALIGN_CENTER);
				
			}
			
			
			
			for (PdfPCell headC : headMain5)
			{
				headC.setRowspan(2);
				headC.setVerticalAlignment(Element.ALIGN_MIDDLE);
				headC.setHorizontalAlignment(Element.ALIGN_CENTER);
			}
			
			
			for (PdfPCell headBDesc : headBDesc2s)
			{
				headBDesc.setVerticalAlignment(Element.ALIGN_MIDDLE);
				headBDesc.setHorizontalAlignment(Element.ALIGN_CENTER);
			}

			headBDesc2s[0].setBorder(0);
		

			for (PdfPCell headB : headMain4)
				maklumatC.addCell(headB);
			
			for (PdfPCell headB : headMain5)
				maklumatC.addCell(headB);

			
			for (PdfPCell headBDesc : headBDesc2s)
				maklumatC.addCell(headBDesc);
		
				
			max2 = (max2 < 10 ? 10 : max2);
			
			for (int i = 0; i < max2; i++)
			{
				String code = Character.toString ((char) (65+ i));
				PdfPCell number = new PdfPCell(new Phrase(code, size7));
				PdfPCell[] contentB4s = null, contentB6s = null;

				number.setVerticalAlignment(Element.ALIGN_MIDDLE);
				number.setHorizontalAlignment(Element.ALIGN_CENTER);

				maklumatC.addCell(number);

				if (i < sizeP3) 
				{
					
					PostFellingSurveyRecord postFellingSurveyRecord = petak3.get(i);
					contentB4s = new PdfPCell[] {
							new PdfPCell(new Phrase(postFellingSurveyRecord.getSpeciesCode() == null ? "" : postFellingSurveyRecord.getSpeciesCode(), size9)),
							new PdfPCell(new Phrase(postFellingSurveyRecord.getSpeciesName(), size7)),
							new PdfPCell(new Phrase("" + postFellingSurveyRecord.getDiameter(), size9)),
							new PdfPCell(new Phrase(postFellingSurveyRecord.getFertility(), size9)),
							
					};
					

					for (PdfPCell contentB4 : contentB4s)
					{
						contentB4.setVerticalAlignment(Element.ALIGN_MIDDLE);
						contentB4.setHorizontalAlignment(Element.ALIGN_CENTER);
					}
				}
				else
				{
					contentB4s = new PdfPCell[] {
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7))
					};
				}

				contentB4s[0].setColspan(4);
				//contentB4s[1].setColspan(2);
				contentB4s[2].setColspan(3);
				//contentB4s[3].setColspan(3);
				
				for (PdfPCell contentB4 : contentB4s)
					maklumatC.addCell(contentB4);

				
				if (i < sizeP4)
				{
					PostFellingSurveyRecord postFellingSurveyRecord = petak4.get(i);
					contentB6s = new PdfPCell[] {
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase(postFellingSurveyRecord.getSpeciesCode() == null ? "" : postFellingSurveyRecord.getSpeciesCode(), size9)),
							new PdfPCell(new Phrase(postFellingSurveyRecord.getSpeciesName(), size7)),
							new PdfPCell(new Phrase("" + postFellingSurveyRecord.getSaplingQuantity(), size9)),
							new PdfPCell(new Phrase("" + postFellingSurveyRecord.getSeedlingQuantity(), size9))
					};

					for (PdfPCell contentB6 : contentB6s)
					{
						contentB6.setVerticalAlignment(Element.ALIGN_MIDDLE);
						contentB6.setHorizontalAlignment(Element.ALIGN_CENTER);
					}
				}
				else
				{
					contentB6s = new PdfPCell[] {
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7))
					};
				}

				contentB6s[0].setBorder(0);
				contentB6s[1].setColspan(4);
				//contentB6s[3].setColspan(3);
				contentB6s[3].setColspan(2);
				contentB6s[4].setColspan(2);

				for (PdfPCell contentB6 : contentB6s)
					maklumatC.addCell(contentB6);
			}
			
			
			
			for (PdfPCell headP : headPejabat)
				maklumatPejabat.addCell(headP);
			
			
			
			//PdfPCell emptyCell = new PdfPCell(new Phrase("", size7));
			//emptyCell.setBorder(0);
			boxBottom.addCell(maklumatC);
			boxBottom.addCell(emptyCell);
			boxBottom.addCell(maklumatPejabat);
			
			document.add(head);
			//document.add(new Paragraph("\n", size7));
			document.add(maklumatA);
			//document.add(new Paragraph("\n", size7));
			document.add(maklumatB);
			//document.add(new Paragraph("\n", size7));
			document.add(boxBottom);
		}

		document.close();
	}
	
	
}