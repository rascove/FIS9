package my.edu.utem.ftmk.fis9.prefelling.util;

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
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PatternColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPatternPainter;
import com.itextpdf.text.pdf.PdfWriter;

import my.edu.utem.ftmk.fis9.maintenance.model.Contractor;
import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingSurveyCard;
import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingSurvey;
import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingSurveyRecord;

/**
 * @author Satrya Fajri Pratama
 */
public class PreFellingSurveyCardGenerator
{
	private static Font size9 = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);
	private static Font size7 = new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL);

	public static void generate(File file, PreFellingSurvey preFellingSurvey, Contractor contractor, LinkedHashMap<String, String> map) throws Exception
	{
		Document document = new Document(PageSize.A4.rotate(), 14.175f, 14.175f, 14.175f, 14.175f);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));

		document.open();

		document.addTitle("KAD BANCIAN INVENTORI HUTAN SEBELUM TEBANGAN (PRE-F)");
		document.addSubject("Jabatan Perhutanan Negeri Sembilan");
		document.addKeywords("Pre-F");
		document.addAuthor("JPNS");
		document.addCreator("JPNS");

		ArrayList<PreFellingSurveyCard> preFellingSurveyCards = preFellingSurvey.getPreFellingSurveyCards();
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

		PatternColor pattern = new PatternColor(line);

		for (PreFellingSurveyCard preFellingSurveyCard : preFellingSurveyCards)
		{
			if (preFellingSurveyCards.indexOf(preFellingSurveyCard) != 0)
				document.newPage();

			ArrayList<PreFellingSurveyRecord> utama1 = new ArrayList<>();
			ArrayList<PreFellingSurveyRecord> utama2 = new ArrayList<>();
			ArrayList<PreFellingSurveyRecord> subpetakA = new ArrayList<>();
			ArrayList<PreFellingSurveyRecord> subpetakB = new ArrayList<>();
			ArrayList<PreFellingSurveyRecord> petak3 = new ArrayList<>();
			ArrayList<PreFellingSurveyRecord> petak4 = null;
			LinkedHashMap<Integer, PreFellingSurveyRecord> petak4Map = new LinkedHashMap<>();
			ArrayList<PreFellingSurveyRecord> preFellingSurveyRecords = preFellingSurveyCard.getPreFellingSurveyRecords();
			int color = 0, count45P = 0, count30P_45 = 0, count15P_30 = 0, count5P_15 = 0, countA = 0, countE = 0, countC = 0;
			String type1 = "", type2 = "";

			for (PreFellingSurveyRecord preFellingSurveyRecord : preFellingSurveyRecords)
			{
				String code = preFellingSurveyRecord.getSpeciesCode(), symbol = map.get(code == null ? "" : code);
				int logQuality = preFellingSurveyRecord.getLogQualityID(), fertilityID = preFellingSurveyRecord.getFertilityID(), vineDiameter = preFellingSurveyRecord.getVineDiameter() == null ? 0 : preFellingSurveyRecord.getVineDiameter();
				double diameter = preFellingSurveyRecord.getDiameter() == null ? 0 : preFellingSurveyRecord.getDiameter();

				if (preFellingSurveyRecord.getPlotTypeID() == 1)
				{
					utama1.add(preFellingSurveyRecord);

					if (vineDiameter != 0)
						countC++;

					if (diameter >= 60)
						color = 8;
					else if (diameter > 45 && logQuality <= 2)
						count45P++;

					if (symbol == null)
						symbol = "C";

					if (!type1.contains(symbol))
						type1 += symbol + " ";
				}
				else if (preFellingSurveyRecord.getPlotTypeID() == 2)
				{
					utama2.add(preFellingSurveyRecord);

					if (vineDiameter != 0)
						countC++;

					if (symbol != null && logQuality <= 2)
						count30P_45++;

					if (symbol == null)
						symbol = "C";

					if (color != 8 && !type1.contains(symbol))
						type1 += symbol + " ";
				}
				else if (preFellingSurveyRecord.getPlotTypeID() == 3)
				{
					subpetakA.add(preFellingSurveyRecord);

					if (vineDiameter != 0)
						countC++;

					if (symbol != null && fertilityID == 1)
						count15P_30++;
				}
				else if (preFellingSurveyRecord.getPlotTypeID() == 4)
				{
					subpetakB.add(preFellingSurveyRecord);

					if (vineDiameter != 0)
						countC++;

					if (symbol != null && fertilityID == 1)
						count15P_30++;
				}
				else if (preFellingSurveyRecord.getPlotTypeID() == 5)
				{
					petak3.add(preFellingSurveyRecord);

					if (symbol != null && fertilityID == 1)
						count5P_15++;
				}
				else if (preFellingSurveyRecord.getPlotTypeID() > 5)
				{
					int speciesID = preFellingSurveyRecord.getSpeciesID(), sapling = preFellingSurveyRecord.getSaplingQuantity(), seedling = preFellingSurveyRecord.getSeedlingQuantity();
					PreFellingSurveyRecord record = petak4Map.get(speciesID);
					
					if (record == null)
					{
						record = new PreFellingSurveyRecord();
						
						record.setSpeciesID(speciesID);
						record.setSpeciesCode(preFellingSurveyRecord.getSpeciesCode());
						record.setSpeciesName(preFellingSurveyRecord.getSpeciesName());
						record.setSaplingQuantity(0);
						record.setSeedlingQuantity(0);
						
						petak4Map.put(speciesID, record);
					}
					
					record.setSaplingQuantity(record.getSaplingQuantity() + sapling);
					record.setSeedlingQuantity(record.getSeedlingQuantity() + seedling);
					
					if (symbol != null)
					{
						countA += sapling;
						countE += seedling;
					}
				}
			}

			if (preFellingSurveyCard.getRattanA() != null && preFellingSurveyCard.getRattanA() != 0 || preFellingSurveyCard.getRattanB() != null && preFellingSurveyCard.getRattanB() != 0 || preFellingSurveyCard.getRattanC() != null && preFellingSurveyCard.getRattanC() != 0 || preFellingSurveyCard.getRattanD() != null && preFellingSurveyCard.getRattanD() != 0 || preFellingSurveyCard.getRattanE() != null && preFellingSurveyCard.getRattanE() != 0 || preFellingSurveyCard.getRattanF() != null && preFellingSurveyCard.getRattanF() != 0 || preFellingSurveyCard.getRattanG() != null && preFellingSurveyCard.getRattanG() != 0)
				type2 += "R ";

			if (preFellingSurveyCard.getBambooA() != null && preFellingSurveyCard.getBambooA() != 0 || preFellingSurveyCard.getBambooB() != null && preFellingSurveyCard.getBambooB() != 0 || preFellingSurveyCard.getBambooC() != null && preFellingSurveyCard.getBambooC() != 0)
				type2 += "B ";

			if (preFellingSurveyCard.getBertam() != null && preFellingSurveyCard.getBertam() >= 5)
				type2 += "BT ";

			if (preFellingSurveyCard.getPalm() != null && preFellingSurveyCard.getPalm() >= 9)
				type2 += "P ";

			if (countC >= 5)
				type2 += "C ";

			if (preFellingSurveyCard.getResamID() >= 2)
				type2 += "F ";

			if (color == 0)
			{
				if (count45P >= 2 && count30P_45 >= 4)
					color = 7;
				else if (count45P >= 2 && count30P_45 < 4)
					color = 6;
				else if (count45P < 2 && count30P_45 >= 4)
					color = 5;
				else if (count15P_30 >= 4)
					color = 4;
				else if (count5P_15 >= 4)
					color = 3;
				else if (countA >= 4)
					color = 2;
				else if (countE >= 4)
					color = 1;
			}

			PdfPTable box = new PdfPTable(1);
			PdfPCell preFellingSurveyCardNo = new PdfPCell(new Phrase("NO. KAD\n" + preFellingSurveyCard.getLineNo() + "-" + preFellingSurveyCard.getPlotNo() + "\n\n" + (color > 5 ? type1.trim().replace(' ', '-') : "") + "\n" + type2.trim().replace(' ', '-'), size9));
			BaseColor[] colors = new BaseColor[] {BaseColor.WHITE, BaseColor.ORANGE, BaseColor.RED, BaseColor.YELLOW, new BaseColor(150, 75, 0), BaseColor.BLUE, pattern, new BaseColor(0, 128, 0), new BaseColor(128, 0, 128)};

			preFellingSurveyCardNo.setBackgroundColor(colors[color]);
			preFellingSurveyCardNo.setVerticalAlignment(Element.ALIGN_MIDDLE);
			preFellingSurveyCardNo.setHorizontalAlignment(Element.ALIGN_CENTER);
			box.addCell(preFellingSurveyCardNo);

			PdfPCell empty = new PdfPCell(box);
			PdfPCell title = new PdfPCell(new Phrase("KAD BANCIAN\nINVENTORI HUTAN SEBELUM TEBANGAN\n(PRE-F)", size9));

			empty.setBorder(0);
			title.setBorder(0);
			empty.setBackgroundColor(BaseColor.LIGHT_GRAY);
			title.setBackgroundColor(BaseColor.LIGHT_GRAY);
			empty.setPadding(2f);
			title.setPaddingTop(15f);
			title.setPaddingBottom(30f);

			PdfPTable left = new PdfPTable(2);

			left.setWidthPercentage(100);
			left.getDefaultCell().setBorder(0);
			left.setWidths(new float[] {25f, 75f});

			left.addCell(empty);
			left.addCell(title);

			PdfPTable middle = new PdfPTable(5);
			/*PdfPCell kod = new PdfPCell(new Phrase("Tahun/Negeri/Hutan Simpan Daerah/No.Kompt/Blok: " + preFellingSurvey.getStartDate() + " / " + district.getStateName() + " / " + preFellingSurvey.getForestName() + " / No. Kompt/Blok: " + preFellingSurvey.getComptBlockNo(), size9));

			kod.setColspan(5);
			kod.setBorder(0);*/

			middle.setWidthPercentage(100);
			middle.getDefaultCell().setBorder(0);
			middle.setWidths(new float[] {17f, 40f, 0.5f, 10f, 32.5f});

			middle.addCell(new Phrase("Ketua Pasukan:", size9));
			middle.addCell(new Phrase(preFellingSurvey.getTeamLeaderName(), size9));
			middle.addCell("");
			middle.addCell(new Phrase("Tarikh:", size9));
			middle.addCell(new Phrase(sdf.format(preFellingSurveyCard.getSurveyDate()), size9));
			middle.addCell(new Phrase("Pegawai Rekod Kod:", size9));
			middle.addCell(new Phrase(preFellingSurveyCard.getRecorderName(), size9));
			middle.addCell("");
			middle.addCell(new Phrase("Tarikh:", size9));
			middle.addCell(new Phrase(sdf.format(preFellingSurveyCard.getSurveyDate()), size9));
			middle.addCell(new Phrase("Pegawai Pemeriksa:", size9));
			middle.addCell(new Phrase(preFellingSurveyCard.getInspectorName() == null ? "-" : preFellingSurveyCard.getInspectorName(), size9));
			middle.addCell("");
			middle.addCell(new Phrase("Tarikh:", size9));
			middle.addCell(new Phrase(preFellingSurveyCard.getInspectionDate() == null ? "-" : sdf.format(preFellingSurveyCard.getInspectionDate()), size9));
			middle.addCell(new Phrase("Pemegang Lesen:", size9));
			middle.addCell(new Phrase(contractor == null ? "-" : contractor.getName(), size9));
			middle.addCell("");
			middle.addCell(new Phrase("Kontraktor:", size9));
			middle.addCell(new Phrase(contractor == null ? "-" : contractor.getCompanyName(), size9));
			//middle.addCell(kod);

			PdfPTable head = new PdfPTable(3);

			head.setWidthPercentage(100);
			head.getDefaultCell().setBorder(0);
			head.setWidths(new float[] {32f, 1f, 67f});

			head.addCell(left);
			head.addCell("");
			head.addCell(middle);

			PdfPTable maklumatA = new PdfPTable(28);
			PdfPCell[] headAs = new PdfPCell[] {
					new PdfPCell(new Phrase("TAHUN", size9)),
					new PdfPCell(new Phrase("NEGERI", size9)),
					new PdfPCell(new Phrase("H/SIMPAN\nDAERAH", size9)),
					new PdfPCell(new Phrase("NOMBOR\nKOMPT/BLOK", size9)),
					new PdfPCell(new Phrase("NOMBOR\nGARISAN", size9)),
					new PdfPCell(new Phrase("NOMBOR\nPETAK", size9)),
					new PdfPCell(new Phrase("JENIS\nHUTAN", size9)),
					new PdfPCell(new Phrase("JENIS\nTANAH", size9)),
					new PdfPCell(new Phrase("K. TANAH", size9)),
					new PdfPCell(new Phrase("GEOLOGI", size9)),
					new PdfPCell(new Phrase("KEADAAN\nKAWASAN", size9)),
					new PdfPCell(new Phrase("ASPEK", size9)),
					new PdfPCell(new Phrase("CERUN", size9)),
					new PdfPCell(new Phrase("T. CERUN", size9)),
					new PdfPCell(new Phrase("DONGAKAN", size9)),
					new PdfPCell(new Phrase("BERTAM", size9)),
					new PdfPCell(new Phrase("PALMA", size9)),
					new PdfPCell(new Phrase("RESAM %\nDITUMBUHI", size9))
			};
			PdfPCell headAR = new PdfPCell(new Phrase("ROTAN", size9));
			PdfPCell headAB = new PdfPCell(new Phrase("BULUH", size9));
			PdfPCell[] subheadAs = new PdfPCell[] {
					new PdfPCell(new Phrase("A", size9)),
					new PdfPCell(new Phrase("B", size9)),
					new PdfPCell(new Phrase("C", size9)),
					new PdfPCell(new Phrase("D", size9)),
					new PdfPCell(new Phrase("E", size9)),
					new PdfPCell(new Phrase("F", size9)),
					new PdfPCell(new Phrase("G", size9)),
					new PdfPCell(new Phrase("A", size9)),
					new PdfPCell(new Phrase("B", size9)),
					new PdfPCell(new Phrase("C", size9))
			};

			maklumatA.setSpacingBefore(5f);
			maklumatA.setWidthPercentage(100);
			maklumatA.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

			for (PdfPCell headA : headAs)
			{
				//headA.setBorder(0);
				headA.setRowspan(2);
				headA.setRotation(90);
				headA.setFixedHeight(60f);
				headA.setBackgroundColor(BaseColor.LIGHT_GRAY);
				headA.setVerticalAlignment(Element.ALIGN_MIDDLE);
			}

			//headAR.setBorder(0);
			headAR.setColspan(7);
			headAR.setFixedHeight(30f);
			headAR.setBackgroundColor(BaseColor.LIGHT_GRAY);
			headAR.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headAR.setHorizontalAlignment(Element.ALIGN_CENTER);

			//headAB.setBorder(0);
			headAB.setColspan(3);
			headAB.setFixedHeight(30f);
			headAB.setBackgroundColor(BaseColor.LIGHT_GRAY);
			headAB.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headAB.setHorizontalAlignment(Element.ALIGN_CENTER);

			for (PdfPCell subheadA : subheadAs)
			{
				//subheadA.setBorder(0);
				subheadA.setFixedHeight(30f);
				subheadA.setBackgroundColor(BaseColor.LIGHT_GRAY);
				subheadA.setVerticalAlignment(Element.ALIGN_MIDDLE);
				subheadA.setHorizontalAlignment(Element.ALIGN_CENTER);
			}

			for (PdfPCell headA : headAs)
				maklumatA.addCell(headA);

			maklumatA.addCell(headAR);
			maklumatA.addCell(headAB);

			for (PdfPCell subheadA : subheadAs)
				maklumatA.addCell(subheadA);

			maklumatA.addCell(new Phrase("" + preFellingSurvey.getYear(), size9));
			maklumatA.addCell(new Phrase(preFellingSurvey.getStateCode(), size9));
			maklumatA.addCell(new Phrase(preFellingSurvey.getForestCode(), size9));
			maklumatA.addCell(new Phrase(preFellingSurvey.getComptBlockNo(), size9));
			maklumatA.addCell(new Phrase("" + preFellingSurveyCard.getLineNo(), size9));
			maklumatA.addCell(new Phrase("" + preFellingSurveyCard.getPlotNo(), size9));
			maklumatA.addCell(new Phrase(preFellingSurveyCard.getForestType() == null ? "" : preFellingSurveyCard.getForestType(), size9));
			maklumatA.addCell(new Phrase(preFellingSurveyCard.getSoilType() == null ? "" : preFellingSurveyCard.getSoilType(), size9));
			maklumatA.addCell(new Phrase(preFellingSurveyCard.getSoilStatus(), size9));
			maklumatA.addCell(new Phrase(preFellingSurveyCard.getGeology(), size9));
			maklumatA.addCell(new Phrase(preFellingSurveyCard.getAreaStatus(), size9));
			maklumatA.addCell(new Phrase(preFellingSurveyCard.getAspect(), size9));
			maklumatA.addCell(new Phrase(preFellingSurveyCard.getSlope() == null ? "" : preFellingSurveyCard.getSlope().toString(), size9));
			maklumatA.addCell(new Phrase(preFellingSurveyCard.getSlopeLocation(), size9));
			maklumatA.addCell(new Phrase(preFellingSurveyCard.getElevation(), size9));
			maklumatA.addCell(new Phrase(preFellingSurveyCard.getBertam() == null ? "" : preFellingSurveyCard.getBertam().toString(), size9));
			maklumatA.addCell(new Phrase(preFellingSurveyCard.getPalm() == null ? "" : preFellingSurveyCard.getPalm().toString(), size9));
			maklumatA.addCell(new Phrase(preFellingSurveyCard.getResam(), size9));
			maklumatA.addCell(new Phrase(preFellingSurveyCard.getRattanA() == null ? "" : preFellingSurveyCard.getRattanA().toString(), size9));
			maklumatA.addCell(new Phrase(preFellingSurveyCard.getRattanB() == null ? "" : preFellingSurveyCard.getRattanB().toString(), size9));
			maklumatA.addCell(new Phrase(preFellingSurveyCard.getRattanC() == null ? "" : preFellingSurveyCard.getRattanC().toString(), size9));
			maklumatA.addCell(new Phrase(preFellingSurveyCard.getRattanD() == null ? "" : preFellingSurveyCard.getRattanD().toString(), size9));
			maklumatA.addCell(new Phrase(preFellingSurveyCard.getRattanE() == null ? "" : preFellingSurveyCard.getRattanE().toString(), size9));
			maklumatA.addCell(new Phrase(preFellingSurveyCard.getRattanF() == null ? "" : preFellingSurveyCard.getRattanF().toString(), size9));
			maklumatA.addCell(new Phrase(preFellingSurveyCard.getRattanG() == null ? "" : preFellingSurveyCard.getRattanG().toString(), size9));
			maklumatA.addCell(new Phrase(preFellingSurveyCard.getBambooA() == null ? "" : preFellingSurveyCard.getBambooA().toString(), size9));
			maklumatA.addCell(new Phrase(preFellingSurveyCard.getBambooB() == null ? "" : preFellingSurveyCard.getBambooB().toString(), size9));
			maklumatA.addCell(new Phrase(preFellingSurveyCard.getBambooC() == null ? "" : preFellingSurveyCard.getBambooC().toString(), size9));

			PdfPTable maklumatB = new PdfPTable(25);

			maklumatB.setSpacingBefore(5f);
			maklumatB.setWidthPercentage(100);
			maklumatB.setWidths(new float[] {2f, 4f, 13f, 2f, 4f, 2f, 2f, 4f, 2f, 1f, 4f, 13f, 6f, 2f, 2f, 4f, 2f, 1f, 4f, 12f, 6f, 2f, 2f, 2f, 2f});

			PdfPCell[] headB1s = new PdfPCell[] {
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("KOD", size7)),
					new PdfPCell(new Phrase("PETAK UTAMA\n[50m X 20m]\nSEMUA POKOK\n> 45cm", size7)),
					new PdfPCell(new Phrase("DIAMETER", size7))
			};
			PdfPCell headB1B = new PdfPCell(new Phrase("BALAK", size7));
			PdfPCell headB1P = new PdfPCell(new Phrase("PEPANJAT", size7));
			PdfPCell[] subheadB1s = new PdfPCell[] {
					new PdfPCell(new Phrase("BILANGAN", size7)),
					new PdfPCell(new Phrase("KUALITI", size7)),
					new PdfPCell(new Phrase("DIAMETER", size7)),
					new PdfPCell(new Phrase("MELINGKARI", size7))
			};
			PdfPCell[] headB2s = new PdfPCell[] {
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("KOD", size7)),
					new PdfPCell(new Phrase("PETAK UTAMA\n[50m X 20m]\nSEMUA POKOK\n> 30cm - 45cm", size7)),
					new PdfPCell(new Phrase("DIAMETER", size7))
			};
			PdfPCell headB2B = new PdfPCell(new Phrase("BALAK", size7));
			PdfPCell headB2P = new PdfPCell(new Phrase("PEPANJAT", size7));
			PdfPCell[] subheadB2s = new PdfPCell[] {
					new PdfPCell(new Phrase("BILANGAN", size7)),
					new PdfPCell(new Phrase("KUALITI", size7)),
					new PdfPCell(new Phrase("DIAMETER", size7)),
					new PdfPCell(new Phrase("MELINGKARI", size7))
			};
			PdfPCell[] headB3s = new PdfPCell[] {
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("KOD", size7)),
					new PdfPCell(new Phrase("PETAK KEDUA\nSUB-PETAK A\n[25m X 20m]\nSEMUA POKOK\n> 15cm - 30cm", size7)),
					new PdfPCell(new Phrase("DIAMETER", size7)),
					new PdfPCell(new Phrase("KESUBURAN", size7))
			};
			PdfPCell headB3P = new PdfPCell(new Phrase("PEPANJAT", size7));
			PdfPCell[] subheadB3s = new PdfPCell[] {
					new PdfPCell(new Phrase("DIAMETER", size7)),
					new PdfPCell(new Phrase("MELINGKARI", size7))
			};
			PdfPCell[] headBDesc1s = new PdfPCell[] {
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("JENIS", size7)),
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("JENIS", size7)),
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("JENIS", size7)),
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("", size7))
			};

			for (PdfPCell headB : headB1s)
			{
				headB.setRowspan(2);
				headB.setVerticalAlignment(Element.ALIGN_MIDDLE);
				headB.setHorizontalAlignment(Element.ALIGN_CENTER);
			}

			headB1s[0].setBorder(0);
			headB1s[3].setColspan(2);

			headB1B.setColspan(2);
			headB1B.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headB1B.setHorizontalAlignment(Element.ALIGN_CENTER);

			headB1P.setColspan(2);
			headB1P.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headB1P.setHorizontalAlignment(Element.ALIGN_CENTER);

			for (PdfPCell subheadB : subheadB1s)
			{
				subheadB.setRotation(90);
				subheadB.setVerticalAlignment(Element.ALIGN_MIDDLE);
				subheadB.setHorizontalAlignment(Element.ALIGN_CENTER);
			}

			for (PdfPCell headB : headB2s)
			{
				headB.setRowspan(2);
				headB.setVerticalAlignment(Element.ALIGN_MIDDLE);
				headB.setHorizontalAlignment(Element.ALIGN_CENTER);
			}

			headB2s[0].setBorder(0);

			headB2B.setColspan(2);
			headB2B.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headB2B.setHorizontalAlignment(Element.ALIGN_CENTER);

			headB2P.setColspan(2);
			headB2P.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headB2P.setHorizontalAlignment(Element.ALIGN_CENTER);

			for (PdfPCell subheadB : subheadB2s)
			{
				subheadB.setRotation(90);
				subheadB.setVerticalAlignment(Element.ALIGN_MIDDLE);
				subheadB.setHorizontalAlignment(Element.ALIGN_CENTER);
			}

			for (PdfPCell headB : headB3s)
			{
				headB.setRowspan(2);
				headB.setVerticalAlignment(Element.ALIGN_MIDDLE);
				headB.setHorizontalAlignment(Element.ALIGN_CENTER);
			}

			headB3s[0].setBorder(0);
			headB3s[4].setRotation(90);

			headB3P.setColspan(3);
			headB3P.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headB3P.setHorizontalAlignment(Element.ALIGN_CENTER);

			for (PdfPCell subheadB : subheadB3s)
			{
				subheadB.setRotation(90);
				subheadB.setVerticalAlignment(Element.ALIGN_MIDDLE);
				subheadB.setHorizontalAlignment(Element.ALIGN_CENTER);
			}

			for (PdfPCell headBDesc : headBDesc1s)
			{
				headBDesc.setVerticalAlignment(Element.ALIGN_MIDDLE);
				headBDesc.setHorizontalAlignment(Element.ALIGN_CENTER);
			}

			headBDesc1s[0].setBorder(0);
			headBDesc1s[8].setBorder(0);
			headBDesc1s[16].setBorder(0);

			subheadB3s[0].setColspan(2);
			headBDesc1s[3].setColspan(2);
			headBDesc1s[21].setColspan(2);

			for (PdfPCell headB : headB1s)
				maklumatB.addCell(headB);

			maklumatB.addCell(headB1B);
			maklumatB.addCell(headB1P);

			for (PdfPCell headB : headB2s)
				maklumatB.addCell(headB);

			maklumatB.addCell(headB2B);
			maklumatB.addCell(headB2P);

			for (PdfPCell headB : headB3s)
				maklumatB.addCell(headB);

			maklumatB.addCell(headB3P);

			for (PdfPCell subheadB : subheadB1s)
				maklumatB.addCell(subheadB);

			for (PdfPCell subheadB : subheadB2s)
				maklumatB.addCell(subheadB);

			for (PdfPCell subheadB : subheadB3s)
				maklumatB.addCell(subheadB);

			for (PdfPCell headBDesc : headBDesc1s)
				maklumatB.addCell(headBDesc);

			petak4 = new ArrayList<>(petak4Map.values());
			int sizeU1 = utama1.size(), sizeU2 = utama2.size(), sizeSA = subpetakA.size(), sizeSB = subpetakB.size(), sizeP3 = petak3.size(), sizeP4 = petak4.size(), max1 = Math.max(Math.max(sizeU1, sizeU2), sizeSA), max2 = Math.max(Math.max(sizeP3, sizeP4), sizeSB);

			for (int i = 0; i < max1; i++)
			{
				PdfPCell number = new PdfPCell(new Phrase("" + (i + 1), size7));
				PdfPCell[] contentB1s = null, contentB2s = null, contentB3s = null;

				number.setVerticalAlignment(Element.ALIGN_MIDDLE);
				number.setHorizontalAlignment(Element.ALIGN_CENTER);

				maklumatB.addCell(number);

				if (i < sizeU1)
				{
					PreFellingSurveyRecord preFellingSurveyRecord = utama1.get(i);
					contentB1s = new PdfPCell[] {
							new PdfPCell(new Phrase(preFellingSurveyRecord.getSpeciesCode() == null ? "" : preFellingSurveyRecord.getSpeciesCode(), size7)),
							new PdfPCell(new Phrase(preFellingSurveyRecord.getSpeciesName(), size7)),
							new PdfPCell(new Phrase("" + preFellingSurveyRecord.getDiameter(), size7)),
							new PdfPCell(new Phrase("" + preFellingSurveyRecord.getLogQuantity(), size7)),
							new PdfPCell(new Phrase(preFellingSurveyRecord.getLogQuality(), size7)),
							new PdfPCell(new Phrase(preFellingSurveyRecord.getVineSpreadthID() == 3 ? "-" : ("" + preFellingSurveyRecord.getVineDiameter()), size7)),
							new PdfPCell(new Phrase(preFellingSurveyRecord.getVineSpreadth(), size7))
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
							new PdfPCell(new Phrase("", size7))
					};
				}

				contentB1s[2].setColspan(2);

				for (PdfPCell contentB1 : contentB1s)
					maklumatB.addCell(contentB1);

				if (i < sizeU2)
				{
					PreFellingSurveyRecord preFellingSurveyRecord = utama2.get(i);
					contentB2s = new PdfPCell[] {
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase(preFellingSurveyRecord.getSpeciesCode() == null ? "" : preFellingSurveyRecord.getSpeciesCode(), size7)),
							new PdfPCell(new Phrase(preFellingSurveyRecord.getSpeciesName(), size7)),
							new PdfPCell(new Phrase("" + preFellingSurveyRecord.getDiameter(), size7)),
							new PdfPCell(new Phrase("" + preFellingSurveyRecord.getLogQuantity(), size7)),
							new PdfPCell(new Phrase(preFellingSurveyRecord.getLogQuality(), size7)),
							new PdfPCell(new Phrase(preFellingSurveyRecord.getVineSpreadthID() == 3 ? "-" : ("" + preFellingSurveyRecord.getVineDiameter()), size7)),
							new PdfPCell(new Phrase(preFellingSurveyRecord.getVineSpreadth(), size7))
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
							new PdfPCell(new Phrase("", size7))
					};
				}

				contentB2s[0].setBorder(0);

				for (PdfPCell contentB2 : contentB2s)
					maklumatB.addCell(contentB2);

				if (i < sizeSA)
				{
					PreFellingSurveyRecord preFellingSurveyRecord = subpetakA.get(i);
					contentB3s = new PdfPCell[] {
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase(preFellingSurveyRecord.getSpeciesCode() == null ? "" : preFellingSurveyRecord.getSpeciesCode(), size7)),
							new PdfPCell(new Phrase(preFellingSurveyRecord.getSpeciesName(), size7)),
							new PdfPCell(new Phrase("" + preFellingSurveyRecord.getDiameter(), size7)),
							new PdfPCell(new Phrase(preFellingSurveyRecord.getFertility(), size7)),
							new PdfPCell(new Phrase(preFellingSurveyRecord.getVineSpreadthID() == 3 ? "-" : ("" + preFellingSurveyRecord.getVineDiameter()), size7)),
							new PdfPCell(new Phrase(preFellingSurveyRecord.getVineSpreadth(), size7))
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
							new PdfPCell(new Phrase("", size7))
					};
				}

				contentB3s[0].setBorder(0);
				contentB3s[5].setColspan(2);

				for (PdfPCell contentB3 : contentB3s)
					maklumatB.addCell(contentB3);
			}

			for (int i = 0; i < 25; i++)
			{
				PdfPCell gap = new PdfPCell(new Phrase(" ", size7));

				gap.setBorder(0);
				maklumatB.addCell(gap);
			}

			PdfPCell[] headB4s = new PdfPCell[] {
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("KOD", size7)),
					new PdfPCell(new Phrase("PETAK KEDUA SUB-PETAK B\n[25m x 20m]\nHANYA POKOK\n> 15cm - 30cm\nYANG DILINGKARI\nPEPANJAT", size7)),
					new PdfPCell(new Phrase("DIAMETER", size7)),
					new PdfPCell(new Phrase("KESUBURAN", size7)),
			};
			PdfPCell headB4P = new PdfPCell(new Phrase("PEPANJAT", size7));
			PdfPCell[] subheadB4s = new PdfPCell[] {
					new PdfPCell(new Phrase("DIAMETER", size7)),
					new PdfPCell(new Phrase("MELINGKARI", size7))
			};
			PdfPCell[] headB5s = new PdfPCell[] {
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("KOD", size7)),
					new PdfPCell(new Phrase("PETAK KETIGA\n[10m x 10m]\nSEMUA POKOK\n> 5cm - 15cm", size7)),
					new PdfPCell(new Phrase("DIAMETER", size7)),
					new PdfPCell(new Phrase("KESUBURAN", size7)),
			};
			PdfPCell[] headB6s = new PdfPCell[] {
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("KOD", size7)),
					new PdfPCell(new Phrase("PETAK KEEMPAT/LIMA\n[5m x 5m/2m x 2m]\nSEMUA POKOK < 5cm", size7)),
					new PdfPCell(new Phrase("BILANGAN\n+1.5M tinggi\n5cm diameter", size7)),
					new PdfPCell(new Phrase("BILANGAN\n+1.5M tinggi\n1.5cm tinggi", size7))
			};
			PdfPCell[] headBDesc2s = new PdfPCell[] {
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("JENIS", size7)),
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("JENIS", size7)),
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("JENIS", size7)),
					new PdfPCell(new Phrase("", size7)),
					new PdfPCell(new Phrase("", size7))
			};

			for (PdfPCell headB : headB4s)
			{
				headB.setRowspan(2);
				headB.setVerticalAlignment(Element.ALIGN_MIDDLE);
				headB.setHorizontalAlignment(Element.ALIGN_CENTER);
			}

			headB4s[0].setBorder(0);
			headB4s[2].setColspan(2);
			headB4s[3].setColspan(2);
			headB4s[4].setRotation(90);

			headB4P.setColspan(2);
			headB4P.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headB4P.setHorizontalAlignment(Element.ALIGN_CENTER);

			for (PdfPCell subheadB : subheadB4s)
			{
				subheadB.setRotation(90);
				subheadB.setVerticalAlignment(Element.ALIGN_MIDDLE);
				subheadB.setHorizontalAlignment(Element.ALIGN_CENTER);
			}

			for (PdfPCell headB : headB5s)
			{
				headB.setRowspan(2);
				headB.setVerticalAlignment(Element.ALIGN_MIDDLE);
				headB.setHorizontalAlignment(Element.ALIGN_CENTER);
			}

			headB5s[0].setBorder(0);
			headB5s[2].setColspan(3);
			headB5s[3].setColspan(2);
			headB5s[4].setRotation(90);

			for (PdfPCell headB : headB6s)
			{
				headB.setRowspan(2);
				headB.setVerticalAlignment(Element.ALIGN_MIDDLE);
				headB.setHorizontalAlignment(Element.ALIGN_CENTER);
			}

			headB6s[0].setBorder(0);
			headB6s[0].setBorder(0);
			headB6s[2].setColspan(2);
			headB6s[3].setColspan(2);
			headB6s[4].setColspan(2);
			headB6s[3].setRotation(90);
			headB6s[4].setRotation(90);

			for (PdfPCell headBDesc : headBDesc2s)
			{
				headBDesc.setVerticalAlignment(Element.ALIGN_MIDDLE);
				headBDesc.setHorizontalAlignment(Element.ALIGN_CENTER);
			}

			headBDesc2s[0].setBorder(0);
			headBDesc2s[7].setBorder(0);
			headBDesc2s[12].setBorder(0);

			headBDesc2s[2].setColspan(2);
			headBDesc2s[3].setColspan(2);
			headBDesc2s[9].setColspan(3);
			headBDesc2s[10].setColspan(2);
			headBDesc2s[14].setColspan(2);
			headBDesc2s[15].setColspan(2);
			headBDesc2s[16].setColspan(2);

			for (PdfPCell headB : headB4s)
				maklumatB.addCell(headB);

			maklumatB.addCell(headB4P);

			for (PdfPCell headB : headB5s)
				maklumatB.addCell(headB);

			for (PdfPCell headB : headB6s)
				maklumatB.addCell(headB);

			for (PdfPCell subheadB : subheadB4s)
				maklumatB.addCell(subheadB);

			for (PdfPCell headBDesc : headBDesc2s)
				maklumatB.addCell(headBDesc);

			for (int i = 0; i < max2; i++)
			{
				PdfPCell number = new PdfPCell(new Phrase("" + (i + 1), size7));
				PdfPCell[] contentB4s = null, contentB5s = null, contentB6s = null;

				number.setVerticalAlignment(Element.ALIGN_MIDDLE);
				number.setHorizontalAlignment(Element.ALIGN_CENTER);

				maklumatB.addCell(number);

				if (i < sizeSB)
				{
					PreFellingSurveyRecord preFellingSurveyRecord = subpetakB.get(i);
					contentB4s = new PdfPCell[] {
							new PdfPCell(new Phrase(preFellingSurveyRecord.getSpeciesCode() == null ? "" : preFellingSurveyRecord.getSpeciesCode(), size7)),
							new PdfPCell(new Phrase(preFellingSurveyRecord.getSpeciesName(), size7)),
							new PdfPCell(new Phrase("" + preFellingSurveyRecord.getDiameter(), size7)),
							new PdfPCell(new Phrase(preFellingSurveyRecord.getFertility(), size7)),
							new PdfPCell(new Phrase(preFellingSurveyRecord.getVineSpreadthID() == 3 ? "-" : ("" + preFellingSurveyRecord.getVineDiameter()), size7)),
							new PdfPCell(new Phrase(preFellingSurveyRecord.getVineSpreadth(), size7))
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
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7))
					};
				}

				contentB4s[1].setColspan(2);
				contentB4s[2].setColspan(2);

				for (PdfPCell contentB4 : contentB4s)
					maklumatB.addCell(contentB4);

				if (i < sizeP3)
				{
					PreFellingSurveyRecord preFellingSurveyRecord = petak3.get(i);
					contentB5s = new PdfPCell[] {
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase(preFellingSurveyRecord.getSpeciesCode() == null ? "" : preFellingSurveyRecord.getSpeciesCode(), size7)),
							new PdfPCell(new Phrase(preFellingSurveyRecord.getSpeciesName(), size7)),
							new PdfPCell(new Phrase("" + preFellingSurveyRecord.getDiameter(), size7)),
							new PdfPCell(new Phrase(preFellingSurveyRecord.getFertility(), size7)),
					};

					for (PdfPCell contentB5 : contentB5s)
					{
						contentB5.setVerticalAlignment(Element.ALIGN_MIDDLE);
						contentB5.setHorizontalAlignment(Element.ALIGN_CENTER);
					}
				}
				else
				{
					contentB5s = new PdfPCell[] {
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase("", size7))
					};
				}

				contentB5s[0].setBorder(0);
				contentB5s[2].setColspan(3);
				contentB5s[3].setColspan(2);

				for (PdfPCell contentB5 : contentB5s)
					maklumatB.addCell(contentB5);

				if (i < sizeP4)
				{
					PreFellingSurveyRecord preFellingSurveyRecord = petak4.get(i);
					contentB6s = new PdfPCell[] {
							new PdfPCell(new Phrase("", size7)),
							new PdfPCell(new Phrase(preFellingSurveyRecord.getSpeciesCode() == null ? "" : preFellingSurveyRecord.getSpeciesCode(), size7)),
							new PdfPCell(new Phrase(preFellingSurveyRecord.getSpeciesName(), size7)),
							new PdfPCell(new Phrase((preFellingSurveyRecord.getSaplingQuantity() == 0 ? "-" : preFellingSurveyRecord.getSaplingQuantity()) + "", size7)),
							new PdfPCell(new Phrase((preFellingSurveyRecord.getSeedlingQuantity() == 0 ? "-" : preFellingSurveyRecord.getSeedlingQuantity()) + "", size7))
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
				contentB6s[2].setColspan(2);
				contentB6s[3].setColspan(2);
				contentB6s[4].setColspan(2);

				for (PdfPCell contentB6 : contentB6s)
					maklumatB.addCell(contentB6);
			}

			document.add(head);
			document.add(new Paragraph("\n[A] MAKLUMAT AM SERTA ROTAN DAN BULUH", size9));
			document.add(maklumatA);
			document.add(new Paragraph("\n[B] MAKLUMAT POKOK DAN PEPANJAT", size9));
			document.add(maklumatB);
		}

		document.close();
	}
}