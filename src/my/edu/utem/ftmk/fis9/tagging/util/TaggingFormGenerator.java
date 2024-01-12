package my.edu.utem.ftmk.fis9.tagging.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.TreeSet;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import my.edu.utem.ftmk.fis9.global.util.RomanNumberConverter;
import my.edu.utem.ftmk.fis9.maintenance.model.TreeLimit;
import my.edu.utem.ftmk.fis9.prefelling.model.CuttingLimit;
import my.edu.utem.ftmk.fis9.tagging.model.Tagging;
import my.edu.utem.ftmk.fis9.tagging.model.TaggingForm;
import my.edu.utem.ftmk.fis9.tagging.model.TaggingRecord;

public class TaggingFormGenerator
{
	private final static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private final static Font subtitleFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	private final static Font bolderFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
	private final static Font contentFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
	private final static Font smallerFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
	private final static DecimalFormat df = new DecimalFormat("0.00");
	private final static DecimalFormat df4 = new DecimalFormat("0000");

	public static void generate(File file, Tagging tagging, TreeLimit treeLimit) throws Exception
	{
		Document document = new Document(PageSize.A4, 28.35f, 28.35f, 28.35f, 28.35f);
		ArrayList<TaggingForm> taggingForms = tagging.getTaggingForms();
		ArrayList<CuttingLimit> cuttingLimits = tagging.getCuttingLimits();

		PdfWriter.getInstance(document, new FileOutputStream(file));

		document.open();

		document.addSubject("Jabatan Perhutanan Negeri Sembilan");
		document.addKeywords("Sesi Bancian");
		document.addAuthor("JPNS");
		document.addCreator("JPNS");
		document.addTitle("BORANG PENANDAAN POKOK");

		boolean emptyTebangan = true, emptyIbu = true, emptyPerlindungan = true;

		for (TaggingForm taggingForm : taggingForms)
		{
			if (!emptyTebangan && !emptyIbu && !emptyPerlindungan)
				break;

			int taggingTypeID = taggingForm.getTaggingTypeID();
			ArrayList<TaggingRecord> taggingRecords = taggingForm.getTaggingRecords();

			if (taggingTypeID == 1)
			{
				if (!emptyTebangan && !emptyIbu && !emptyPerlindungan)
					continue;

				for (TaggingRecord taggingRecord : taggingRecords)
				{
					if (!emptyTebangan && !emptyIbu && !emptyPerlindungan)
						break;

					if (emptyTebangan && taggingRecord.getTreeTypeID() == 1)
						emptyTebangan = false;
					else if (emptyIbu && taggingRecord.getTreeTypeID() == 2)
						emptyIbu = false;
					else if (emptyPerlindungan
							&& taggingRecord.getTreeTypeID() == 3)
						emptyPerlindungan = false;
				}
			}
		}

		if (!emptyTebangan)
		{
			Paragraph reportTitle = new Paragraph("BUKU PENANDAAN POKOK DAN KAWALAN PENGELUARAN KAYU", subtitleFont);

			reportTitle.setAlignment(Paragraph.ALIGN_CENTER);
			document.add(reportTitle);

			reportTitle = new Paragraph("(POKOK TEBANGAN)", smallerFont);

			reportTitle.setAlignment(Paragraph.ALIGN_CENTER);
			reportTitle.setSpacingAfter(15);
			document.add(reportTitle);

			Paragraph sectionTitle = new Paragraph("A. MAKLUMAT ASAS", subtitleFont);

			document.add(sectionTitle);
			document.add(new Paragraph("\n", subtitleFont));

			PdfPTable taggingAreaDetails = assembleTaggingGeneralDetails(tagging, 1);

			taggingAreaDetails.setWidthPercentage(100.0f);
			document.add(taggingAreaDetails);
			document.add(new Paragraph("\n", smallerFont));

			PdfPTable cuttingLimitDetails = assembleCuttingLimitDetails(tagging, treeLimit);

			cuttingLimitDetails.setWidthPercentage(100.0f);
			document.add(cuttingLimitDetails);
			document.add(new Paragraph("\n", smallerFont));

			PdfPTable taggingSeries = assembleTaggingSeries(tagging, 1);

			taggingSeries.setWidthPercentage(100.0f);
			document.add(taggingSeries);
			document.add(new Paragraph("\n", smallerFont));

			PdfPTable totalTagDetails = assembleTreeTagSummary(tagging, 1, 1);

			totalTagDetails.setWidthPercentage(100.0f);
			document.add(totalTagDetails);
			document.add(new Paragraph("\n", smallerFont));

			PdfPTable loggerDetails = assembleLoggerDetails();

			loggerDetails.setWidthPercentage(100.0f);
			document.add(loggerDetails);
			document.add(new Paragraph("\n", smallerFont));

			PdfPTable licenseDetails = assembleLicenseDetails();

			licenseDetails.setWidthPercentage(100.0f);
			document.add(licenseDetails);
			document.add(new Paragraph("\n", smallerFont));

			PdfPTable hallDetails = assembleHallDetails(tagging);

			hallDetails.setWidthPercentage(100.0f);
			document.add(hallDetails);
			document.add(new Paragraph("\n", smallerFont));

			document.newPage();
		}

		SimpleDateFormat sdf = new SimpleDateFormat("d MMMM yyyy", new Locale("ms"));
		DecimalFormat df = new DecimalFormat("0.00 m\u00B3");
		HashMap<String, TaggingRecord> motherList = new HashMap<>();
		HashMap<String, TaggingRecord> protectedList = new HashMap<>();
		HashMap<String, String> motherBlock = new HashMap<>();
		HashMap<String, String> protectedBlock = new HashMap<>();
		int blockCount = 0, plotCount = 0;

		for (TaggingForm taggingForm : taggingForms)
		{
			ArrayList<TaggingRecord> taggingRecords = taggingForm.getTaggingRecords();

			if (!taggingRecords.isEmpty())
			{
				if (taggingForms.indexOf(taggingForm) != 0)
					document.newPage();

				PdfPTable summary = new PdfPTable(3);
				int taggingType = taggingForm.getTaggingTypeID();
				boolean l4 = taggingType == 1;

				summary.setWidthPercentage(100);
				summary.getDefaultCell().setBorder(0);
				summary.setWidths(new float[] {35f, 5f, 60f});

				PdfPTable data = new PdfPTable(l4 ? 9 : 7);
				Paragraph title = new Paragraph("BORANG PENANDAAN " + taggingForm.getTaggingTypeName().toUpperCase(), titleFont);
				int count = 1;

				title.setAlignment(Element.ALIGN_CENTER);

				PdfPCell forest = new PdfPCell(new Phrase(tagging.getForestName(), contentFont));
				PdfPCell comptBlockNo = new PdfPCell(new Phrase(tagging.getComptBlockNo(), contentFont));
				PdfPCell area = new PdfPCell(new Phrase(tagging.getArea() + " ha", contentFont));

				forest.setBorder(2);
				comptBlockNo.setBorder(2);
				area.setBorder(2);

				data.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
				data.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
				data.setWidthPercentage(100);
				data.setHeaderRows(1);

				document.add(title);
				document.add(new Phrase("", contentFont));

				if (l4)
					data.addCell(new Phrase("No. Petak Penandaan Pokok\n(" + count++ + ")", contentFont));

				data.addCell(new Phrase("No. Siri Pokok\n(" + count++ + ")", contentFont));
				data.addCell(new Phrase("Kod Spesis\n(" + count++ + ")", contentFont));
				data.addCell(new Phrase("Spesis Pokok\n(" + count++ + ")", contentFont));
				data.addCell(new Phrase("Diameter (cm)\n(" + count++ + ")", contentFont));
				data.addCell(new Phrase("Anggaran Tual Balak\n(" + count++ + ")", contentFont));
				data.addCell(new Phrase("Kualiti Tual Balak Pertama\n(" + count++ + ")", contentFont));
				data.addCell(new Phrase("Catatan\n(" + count++ + ")", contentFont));

				if (l4)
				{
					PdfPCell district = new PdfPCell(new Phrase(tagging.getDistrictName(), contentFont));

					district.setBorder(2);

					data.addCell(new Phrase("Isipadu (m\u00B3)\n(" + count++ + ")", contentFont));

					summary.addCell(new Phrase("Daerah Hutan", contentFont));
					summary.addCell(new Phrase(":", contentFont));
					summary.addCell(district);
				}

				summary.addCell(new Phrase("Hutan Simpan", contentFont));
				summary.addCell(new Phrase(":", contentFont));
				summary.addCell(forest);

				summary.addCell(new Phrase("No. Kompartmen/Subkompartment", contentFont));
				summary.addCell(new Phrase(":", contentFont));
				summary.addCell(comptBlockNo);

				summary.addCell(new Phrase("Luas", contentFont));
				summary.addCell(new Phrase(":", contentFont));
				summary.addCell(area);

				PdfPCell formNo = new PdfPCell(new Phrase(taggingForm.getFormNo(), contentFont));

				formNo.setBorder(2);

				if (taggingType == 1)
				{
					PdfPCell dipterocarpLimit = new PdfPCell(new Phrase(tagging.getDipterocarpLimit() + " cm", contentFont));
					PdfPCell nonDipterocarpLimit = new PdfPCell(new Phrase(tagging.getNonDipterocarpLimit() + " cm", contentFont));
					PdfPCell chengal = new PdfPCell(new Phrase((treeLimit == null ? tagging.getDipterocarpLimit() : treeLimit.getChengalLimit()) + " cm", contentFont));

					dipterocarpLimit.setBorder(2);
					nonDipterocarpLimit.setBorder(2);
					chengal.setBorder(2);

					summary.addCell(new Phrase("Had Batas Tebangan", contentFont));
					summary.addCell(new Phrase(":", contentFont));
					summary.addCell(new Phrase("", contentFont));
					summary.addCell(new Phrase("     a. Dipterokarp", contentFont));
					summary.addCell(new Phrase(":", contentFont));
					summary.addCell(dipterocarpLimit);
					summary.addCell(new Phrase("     b. Bukan Dipterokarp", contentFont));
					summary.addCell(new Phrase(":", contentFont));
					summary.addCell(nonDipterocarpLimit);
					summary.addCell(new Phrase("     c. Chengal", contentFont));
					summary.addCell(new Phrase(":", contentFont));
					summary.addCell(chengal);

					for (int i = 0; i < cuttingLimits.size(); i++)
					{
						CuttingLimit cuttingLimit = cuttingLimits.get(i);
						PdfPCell lain = new PdfPCell(new Phrase(cuttingLimit.getMinDiameter() + " cm", contentFont));

						lain.setBorder(2);
						summary.addCell(new Phrase("     " + ((char) (68 + i)) + ". " + cuttingLimit.getSpeciesName(), contentFont));
						summary.addCell(new Phrase(":", contentFont));
						summary.addCell(lain);
					}

					summary.addCell(new Phrase("No. Blok Penandaan Pokok", contentFont));
					summary.addCell(new Phrase(":", contentFont));
					summary.addCell(formNo);
				}
				else if (taggingType == 2 || taggingType == 3)
				{
					PdfPCell length = new PdfPCell(new Phrase(taggingForm.getLength() + " m", contentFont));
					PdfPCell width = new PdfPCell(new Phrase(taggingForm.getWidth() + " m", contentFont));

					length.setBorder(2);
					width.setBorder(2);

					summary.addCell(new Phrase("No. Jalan", contentFont));
					summary.addCell(new Phrase(":", contentFont));
					summary.addCell(formNo);
					summary.addCell(new Phrase("Panjang Jalan", contentFont));
					summary.addCell(new Phrase(":", contentFont));
					summary.addCell(length);
					summary.addCell(new Phrase("Lebar Hak Lalu Lintang Jalan", contentFont));
					summary.addCell(new Phrase(":", contentFont));
					summary.addCell(width);
				}
				else if (taggingType == 4)
				{
					PdfPCell dimension = new PdfPCell(new Phrase(taggingForm.getLength() + " m x " + taggingForm.getWidth() + " m", contentFont));
					String type = taggingType == 4 ? "Matau" : "Kongsi";

					dimension.setBorder(2);

					summary.addCell(new Phrase("No. " + type, contentFont));
					summary.addCell(new Phrase(":", contentFont));
					summary.addCell(formNo);
					summary.addCell(new Phrase("Ukuran " + type, contentFont));
					summary.addCell(new Phrase(":", contentFont));
					summary.addCell(dimension);
				}

				String hammers = tagging.getHammers().toString();
				PdfPCell date = new PdfPCell(new Phrase(sdf.format(taggingForm.getTaggingDate()), contentFont));
				PdfPCell name = new PdfPCell(new Phrase(tagging.getTeamLeaderName(), contentFont));
				PdfPCell designation = new PdfPCell(new Phrase(tagging.getTeamLeaderDesignation(), contentFont));
				PdfPCell hammer = new PdfPCell(new Phrase(hammers.substring(1, hammers.length() - 1), contentFont));

				date.setBorder(2);
				name.setBorder(2);
				designation.setBorder(2);
				hammer.setBorder(2);

				summary.addCell(new Phrase("Tarikh Penandaan Pokok", contentFont));
				summary.addCell(new Phrase(":", contentFont));
				summary.addCell(date);

				summary.addCell(new Phrase("Ketua Pasukan", contentFont));
				summary.addCell(new Phrase(":", contentFont));
				summary.addCell(new Phrase("", contentFont));

				summary.addCell(new Phrase("     a. Nama", contentFont));
				summary.addCell(new Phrase(":", contentFont));
				summary.addCell(name);

				summary.addCell(new Phrase("     b. Jawatan", contentFont));
				summary.addCell(new Phrase(":", contentFont));
				summary.addCell(designation);

				summary.addCell(new Phrase("No. Tukul Tanda Jabatan", contentFont));
				summary.addCell(new Phrase(":", contentFont));
				summary.addCell(hammer);

				String plotNo = null;
				blockCount++;

				for (TaggingRecord taggingRecord : taggingRecords)
				{
					if (l4)
					{
						String current = taggingRecord.getPlotNo();
						String serialNo = taggingRecord.getSerialNo();
						int treeTypeID = taggingRecord.getTreeTypeID();

						if (!current.equals(plotNo))
						{
							plotCount++;
							plotNo = current;

							data.addCell(new Phrase(current, contentFont));
						}
						else
							data.addCell(new Phrase("", contentFont));

						if (treeTypeID == 2)
						{
							motherList.put(serialNo, taggingRecord);
							motherBlock.put(serialNo, taggingForm.getFormNo());
						}
						else if (treeTypeID == 3)
						{
							protectedList.put(serialNo, taggingRecord);
							protectedBlock.put(serialNo, taggingForm.getFormNo());
						}
					}

					data.addCell(new Phrase(taggingRecord.getSerialNo(), contentFont));
					data.addCell(new Phrase(taggingRecord.getSpeciesCode() == null ? "" : taggingRecord.getSpeciesCode(), contentFont));
					data.addCell(new Phrase(taggingRecord.getSpeciesName(), contentFont));
					data.addCell(new Phrase(taggingRecord.getDiameter() + " cm", contentFont));
					data.addCell(new Phrase(taggingRecord.getLogQualityID() == 0 ? "-" : String.valueOf(taggingRecord.getEstimation()), contentFont));
					data.addCell(new Phrase(taggingRecord.getLogQualityID() == 0 ? "-" : taggingRecord.getLogQualityName(), contentFont));
					data.addCell(new Phrase(taggingRecord.getNote(), contentFont));

					if (l4)
						data.addCell(new Phrase(taggingRecord.getLogQualityID() == 0 ? "-" : df.format(taggingRecord.getVolume()), contentFont));
				}

				document.add(summary);
				document.add(Chunk.NEWLINE);
				document.add(data);
			}
		}

		if (!motherList.isEmpty())
		{
			ArrayList<String> serialNos = new ArrayList<>(motherList.keySet());

			document.newPage();
			Collections.sort(serialNos);

			PdfPTable summary = new PdfPTable(3);

			summary.setWidthPercentage(100);
			summary.getDefaultCell().setBorder(0);
			summary.setWidths(new float[] {35f, 5f, 60f});

			PdfPTable data = new PdfPTable(7);
			Paragraph title = new Paragraph("SENARAI POKOK IBU", titleFont);
			int count = 1;

			title.setAlignment(Element.ALIGN_CENTER);

			document.add(title);
			document.add(new Phrase("", contentFont));

			PdfPCell district = new PdfPCell(new Phrase(tagging.getDistrictName(), contentFont));
			PdfPCell forest = new PdfPCell(new Phrase(tagging.getForestName(), contentFont));
			PdfPCell comptBlockNo = new PdfPCell(new Phrase(tagging.getComptBlockNo(), contentFont));
			PdfPCell area = new PdfPCell(new Phrase(tagging.getArea() + " ha", contentFont));

			PdfPCell dipterocarpLimit = new PdfPCell(new Phrase(tagging.getDipterocarpLimit() + " cm", contentFont));
			PdfPCell nonDipterocarpLimit = new PdfPCell(new Phrase(tagging.getNonDipterocarpLimit() + " cm", contentFont));
			PdfPCell chengal = new PdfPCell(new Phrase((treeLimit == null ? tagging.getDipterocarpLimit() : treeLimit.getChengalLimit()) + " cm", contentFont));

			PdfPCell blockTotal = new PdfPCell(new Phrase(String.valueOf(blockCount), contentFont));
			PdfPCell plotTotal = new PdfPCell(new Phrase(String.valueOf(plotCount), contentFont));

			String hammers = tagging.getHammers().toString();
			PdfPCell date = new PdfPCell(new Phrase(sdf.format(tagging.getStartDate()) + " - " + sdf.format(tagging.getEndDate()), contentFont));
			PdfPCell name = new PdfPCell(new Phrase(tagging.getTeamLeaderName(), contentFont));
			PdfPCell designation = new PdfPCell(new Phrase(tagging.getTeamLeaderDesignation(), contentFont));
			PdfPCell hammer = new PdfPCell(new Phrase(hammers.substring(1, hammers.length() - 1), contentFont));

			district.setBorder(2);
			forest.setBorder(2);
			comptBlockNo.setBorder(2);
			area.setBorder(2);
			dipterocarpLimit.setBorder(2);
			nonDipterocarpLimit.setBorder(2);
			chengal.setBorder(2);
			blockTotal.setBorder(2);
			plotTotal.setBorder(2);
			date.setBorder(2);
			name.setBorder(2);
			designation.setBorder(2);
			hammer.setBorder(2);

			summary.addCell(new Phrase("Daerah Hutan", contentFont));
			summary.addCell(new Phrase(":", contentFont));
			summary.addCell(district);

			summary.addCell(new Phrase("Hutan Simpan", contentFont));
			summary.addCell(new Phrase(":", contentFont));
			summary.addCell(forest);

			summary.addCell(new Phrase("No. Kompartmen/Subkompartment", contentFont));
			summary.addCell(new Phrase(":", contentFont));
			summary.addCell(comptBlockNo);

			summary.addCell(new Phrase("Luas", contentFont));
			summary.addCell(new Phrase(":", contentFont));
			summary.addCell(area);

			summary.addCell(new Phrase("Had Batas Tebangan", contentFont));
			summary.addCell(new Phrase(":", contentFont));
			summary.addCell(new Phrase("", contentFont));

			summary.addCell(new Phrase("     a. Dipterokarp", contentFont));
			summary.addCell(new Phrase(":", contentFont));
			summary.addCell(dipterocarpLimit);

			summary.addCell(new Phrase("     b. Bukan Dipterokarp", contentFont));
			summary.addCell(new Phrase(":", contentFont));
			summary.addCell(nonDipterocarpLimit);

			summary.addCell(new Phrase("     c. Chengal", contentFont));
			summary.addCell(new Phrase(":", contentFont));
			summary.addCell(chengal);

			for (int i = 0; i < cuttingLimits.size(); i++)
			{
				CuttingLimit cuttingLimit = cuttingLimits.get(i);
				PdfPCell lain = new PdfPCell(new Phrase(cuttingLimit.getMinDiameter() + " cm", contentFont));

				lain.setBorder(2);
				summary.addCell(new Phrase("     " + ((char) (68 + i)) + ". " + cuttingLimit.getSpeciesName(), contentFont));
				summary.addCell(new Phrase(":", contentFont));
				summary.addCell(lain);
			}

			summary.addCell(new Phrase("Jumlah Blok Penandaan Pokok", contentFont));
			summary.addCell(new Phrase(":", contentFont));
			summary.addCell(blockTotal);

			summary.addCell(new Phrase("Jumlah Petak Penandaan Pokok", contentFont));
			summary.addCell(new Phrase(":", contentFont));
			summary.addCell(plotTotal);

			summary.addCell(new Phrase("Tarikh Penandaan Pokok", contentFont));
			summary.addCell(new Phrase(":", contentFont));
			summary.addCell(date);

			summary.addCell(new Phrase("Ketua Pasukan", contentFont));
			summary.addCell(new Phrase(":", contentFont));
			summary.addCell(new Phrase("", contentFont));

			summary.addCell(new Phrase("     a. Nama", contentFont));
			summary.addCell(new Phrase(":", contentFont));
			summary.addCell(name);

			summary.addCell(new Phrase("     b. Jawatan", contentFont));
			summary.addCell(new Phrase(":", contentFont));
			summary.addCell(designation);

			summary.addCell(new Phrase("No. Tukul Tanda Jabatan", contentFont));
			summary.addCell(new Phrase(":", contentFont));
			summary.addCell(hammer);

			data.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			data.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			data.setWidthPercentage(100);
			data.setHeaderRows(1);

			data.addCell(new Phrase("Bil\n(" + count++ + ")", contentFont));
			data.addCell(new Phrase("No. Siri Pokok\n(" + count++ + ")", contentFont));
			data.addCell(new Phrase("Spesis Pokok\n(" + count++ + ")", contentFont));
			data.addCell(new Phrase("Diameter (cm)\n(" + count++ + ")", contentFont));
			data.addCell(new Phrase("Blok Penandaan Pokok\n(" + count++ + ")", contentFont));
			data.addCell(new Phrase("Petak Penandaan Pokok\n(" + count++ + ")", contentFont));
			data.addCell(new Phrase("Catatan\n(" + count++ + ")", contentFont));

			for (int i = 0; i < serialNos.size(); i++)
			{
				String serialNo = serialNos.get(i);
				String blockNo = motherBlock.get(serialNo);
				TaggingRecord taggingRecord = motherList.get(serialNo);

				data.addCell(new Phrase(String.valueOf(i + 1), contentFont));
				data.addCell(new Phrase(taggingRecord.getSerialNo(), contentFont));
				data.addCell(new Phrase(taggingRecord.getSpeciesName(), contentFont));
				data.addCell(new Phrase(taggingRecord.getDiameter() + " cm", contentFont));
				data.addCell(new Phrase(blockNo, contentFont));
				data.addCell(new Phrase(taggingRecord.getPlotNo(), contentFont));
				data.addCell(new Phrase(taggingRecord.getNote(), contentFont));
			}

			document.add(summary);
			document.add(Chunk.NEWLINE);
			document.add(data);
		}

		if (!protectedList.isEmpty())
		{
			ArrayList<String> serialNos = new ArrayList<>(protectedList.keySet());

			document.newPage();
			Collections.sort(serialNos);

			PdfPTable summary = new PdfPTable(3);

			summary.setWidthPercentage(100);
			summary.getDefaultCell().setBorder(0);
			summary.setWidths(new float[] {35f, 5f, 60f});

			PdfPTable data = new PdfPTable(7);
			Paragraph title = new Paragraph("SENARAI POKOK PERLINDUNGAN", titleFont);
			int count = 1;

			title.setAlignment(Element.ALIGN_CENTER);

			document.add(title);
			document.add(new Phrase("", contentFont));

			PdfPCell district = new PdfPCell(new Phrase(tagging.getDistrictName(), contentFont));
			PdfPCell forest = new PdfPCell(new Phrase(tagging.getForestName(), contentFont));
			PdfPCell comptBlockNo = new PdfPCell(new Phrase(tagging.getComptBlockNo(), contentFont));
			PdfPCell area = new PdfPCell(new Phrase(tagging.getArea() + " ha", contentFont));

			PdfPCell dipterocarpLimit = new PdfPCell(new Phrase(tagging.getDipterocarpLimit() + " cm", contentFont));
			PdfPCell nonDipterocarpLimit = new PdfPCell(new Phrase(tagging.getNonDipterocarpLimit() + " cm", contentFont));
			PdfPCell chengal = new PdfPCell(new Phrase((treeLimit == null ? tagging.getDipterocarpLimit() : treeLimit.getChengalLimit()) + " cm", contentFont));

			PdfPCell blockTotal = new PdfPCell(new Phrase(String.valueOf(blockCount), contentFont));
			PdfPCell plotTotal = new PdfPCell(new Phrase(String.valueOf(plotCount), contentFont));

			String hammers = tagging.getHammers().toString();
			PdfPCell date = new PdfPCell(new Phrase(sdf.format(tagging.getStartDate()) + " - " + sdf.format(tagging.getEndDate()), contentFont));
			PdfPCell name = new PdfPCell(new Phrase(tagging.getTeamLeaderName(), contentFont));
			PdfPCell designation = new PdfPCell(new Phrase(tagging.getTeamLeaderDesignation(), contentFont));
			PdfPCell hammer = new PdfPCell(new Phrase(hammers.substring(1, hammers.length() - 1), contentFont));

			district.setBorder(2);
			forest.setBorder(2);
			comptBlockNo.setBorder(2);
			area.setBorder(2);
			dipterocarpLimit.setBorder(2);
			nonDipterocarpLimit.setBorder(2);
			chengal.setBorder(2);
			blockTotal.setBorder(2);
			plotTotal.setBorder(2);
			date.setBorder(2);
			name.setBorder(2);
			designation.setBorder(2);
			hammer.setBorder(2);

			summary.addCell(new Phrase("Daerah Hutan", contentFont));
			summary.addCell(new Phrase(":", contentFont));
			summary.addCell(district);

			summary.addCell(new Phrase("Hutan Simpan", contentFont));
			summary.addCell(new Phrase(":", contentFont));
			summary.addCell(forest);

			summary.addCell(new Phrase("No. Kompartmen/Subkompartment", contentFont));
			summary.addCell(new Phrase(":", contentFont));
			summary.addCell(comptBlockNo);

			summary.addCell(new Phrase("Luas", contentFont));
			summary.addCell(new Phrase(":", contentFont));
			summary.addCell(area);

			summary.addCell(new Phrase("Had Batas Tebangan", contentFont));
			summary.addCell(new Phrase(":", contentFont));
			summary.addCell(new Phrase("", contentFont));

			summary.addCell(new Phrase("     a. Dipterokarp", contentFont));
			summary.addCell(new Phrase(":", contentFont));
			summary.addCell(dipterocarpLimit);

			summary.addCell(new Phrase("     b. Bukan Dipterokarp", contentFont));
			summary.addCell(new Phrase(":", contentFont));
			summary.addCell(nonDipterocarpLimit);

			summary.addCell(new Phrase("     c. Chengal", contentFont));
			summary.addCell(new Phrase(":", contentFont));
			summary.addCell(chengal);

			for (int i = 0; i < cuttingLimits.size(); i++)
			{
				CuttingLimit cuttingLimit = cuttingLimits.get(i);
				PdfPCell lain = new PdfPCell(new Phrase(cuttingLimit.getMinDiameter() + " cm", contentFont));

				lain.setBorder(2);
				summary.addCell(new Phrase("     " + ((char) (68 + i)) + ". " + cuttingLimit.getSpeciesName(), contentFont));
				summary.addCell(new Phrase(":", contentFont));
				summary.addCell(lain);
			}

			summary.addCell(new Phrase("Jumlah Blok Penandaan Pokok", contentFont));
			summary.addCell(new Phrase(":", contentFont));
			summary.addCell(blockTotal);

			summary.addCell(new Phrase("Jumlah Petak Penandaan Pokok", contentFont));
			summary.addCell(new Phrase(":", contentFont));
			summary.addCell(plotTotal);

			summary.addCell(new Phrase("Tarikh Penandaan Pokok", contentFont));
			summary.addCell(new Phrase(":", contentFont));
			summary.addCell(date);

			summary.addCell(new Phrase("Ketua Pasukan", contentFont));
			summary.addCell(new Phrase(":", contentFont));
			summary.addCell(new Phrase("", contentFont));

			summary.addCell(new Phrase("     a. Nama", contentFont));
			summary.addCell(new Phrase(":", contentFont));
			summary.addCell(name);

			summary.addCell(new Phrase("     b. Jawatan", contentFont));
			summary.addCell(new Phrase(":", contentFont));
			summary.addCell(designation);

			summary.addCell(new Phrase("No. Tukul Tanda Jabatan", contentFont));
			summary.addCell(new Phrase(":", contentFont));
			summary.addCell(hammer);

			data.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			data.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			data.setWidthPercentage(100);
			data.setHeaderRows(1);

			data.addCell(new Phrase("Bil\n(" + count++ + ")", contentFont));
			data.addCell(new Phrase("No. Siri Pokok\n(" + count++ + ")", contentFont));
			data.addCell(new Phrase("Spesis Pokok\n(" + count++ + ")", contentFont));
			data.addCell(new Phrase("Diameter (cm)\n(" + count++ + ")", contentFont));
			data.addCell(new Phrase("Blok Penandaan Pokok\n(" + count++ + ")", contentFont));
			data.addCell(new Phrase("Petak Penandaan Pokok\n(" + count++ + ")", contentFont));
			data.addCell(new Phrase("Catatan\n(" + count++ + ")", contentFont));

			for (int i = 0; i < serialNos.size(); i++)
			{
				String serialNo = serialNos.get(i);
				String blockNo = protectedBlock.get(serialNo);
				TaggingRecord taggingRecord = protectedList.get(serialNo);

				data.addCell(new Phrase(String.valueOf(i + 1), contentFont));
				data.addCell(new Phrase(taggingRecord.getSerialNo(), contentFont));
				data.addCell(new Phrase(taggingRecord.getSpeciesName(), contentFont));
				data.addCell(new Phrase(taggingRecord.getDiameter() + " cm", contentFont));
				data.addCell(new Phrase(blockNo, contentFont));
				data.addCell(new Phrase(taggingRecord.getPlotNo(), contentFont));
				data.addCell(new Phrase(taggingRecord.getNote(), contentFont));
			}

			document.add(summary);
			document.add(Chunk.NEWLINE);
			document.add(data);
		}

		document.setPageSize(PageSize.A4.rotate());

		LinkedHashMap<String, TaggingForm> l4s = new LinkedHashMap<>();
		LinkedHashMap<String, ArrayList<TaggingRecord>> map = new LinkedHashMap<>();

		for (TaggingForm taggingForm : taggingForms)
		{
			ArrayList<TaggingRecord> taggingRecords = taggingForm.getTaggingRecords();

			if (taggingForm.getTaggingTypeID() == 1)
			{
				for (TaggingRecord taggingRecord : taggingRecords)
				{
					String key = taggingRecord.getTaggingFormID() + "-" + taggingRecord.getPlotNo();
					ArrayList<TaggingRecord> records = map.get(key);

					if (records == null)
					{
						l4s.put(key, taggingForm);
						map.put(key, records = new ArrayList<>());
					}

					records.add(taggingRecord);
				}
			}
		}

		ArrayList<String> keys = new ArrayList<>(map.keySet());
		PdfPTable note = new PdfPTable(5);
		PdfPCell empty = new PdfPCell(new Phrase(" ", smallerFont));
		PdfPCell top = new PdfPCell(new Phrase("\nPETA LOKASI POKOK", smallerFont));
		PdfPCell last = new PdfPCell(new Phrase("21 & 22. Bacaan Jarak Mula dan Tutup Petak", smallerFont));

		note.setWidthPercentage(100);
		note.getDefaultCell().setBorder(0);

		empty.setBorder(0);
		top.setHorizontalAlignment(Element.ALIGN_CENTER);
		top.setBorder(0);
		last.setBorder(0);
		top.setColspan(8);
		last.setRowspan(5);

		note.addCell(new Phrase("1. Nama Hutan Simpan", smallerFont));
		note.addCell(new Phrase("6. Bearing Garisan", smallerFont));
		note.addCell(new Phrase("11. Nombor Kad Tanda Pokok", smallerFont));
		note.addCell(new Phrase("16. Kualiti Tual", smallerFont));
		note.addCell(last);
		note.addCell(new Phrase("2. Daerah Hutan", smallerFont));
		note.addCell(new Phrase("7 & 22. Nombor Petak", smallerFont));
		note.addCell(new Phrase("12. Bilangan Pokok", smallerFont));
		note.addCell(new Phrase("17. Diameter (cm)", smallerFont));
		note.addCell(new Phrase("3. Nombor Kompartmen", smallerFont));
		note.addCell(new Phrase("8. Had Batas Tebangan", smallerFont));
		note.addCell(new Phrase("13. Nombor Siri Tag Pokok", smallerFont));
		note.addCell(new Phrase("18. Isipadu (m\u00B3)", smallerFont));
		note.addCell(new Phrase("4. Nombor Blok", smallerFont));
		note.addCell(new Phrase("9. Ketua Pasukan", smallerFont));
		note.addCell(new Phrase("14. Jenis Pokok", smallerFont));
		note.addCell(new Phrase("19. Bilangan Tual", smallerFont));
		note.addCell(new Phrase("5. Nombor Garisan", smallerFont));
		note.addCell(new Phrase("10. Tarikh Penandaan Petak", smallerFont));
		note.addCell(new Phrase("15. Kod Spesis", smallerFont));
		note.addCell(new Phrase("20. Catatan", smallerFont));

		for (String key : keys)
		{
			document.newPage();

			TaggingForm taggingForm = l4s.get(key);
			ArrayList<TaggingRecord> taggingRecords = map.get(key);
			String plotNo = key.split("-")[1];
			PdfPTable table = new PdfPTable(3);
			PdfPTable summary = new PdfPTable(2);
			PdfPTable plotMap = new PdfPTable(8);
			int count = 1;

			table.setWidthPercentage(100);
			table.getDefaultCell().setBorder(0);
			table.setWidths(new float[] {37f, 3f, 60f});
			plotMap.setWidthPercentage(100);
			plotMap.setWidths(new float[] {24f, 14f, 14f, 14f, 14f, 14f, 3f, 3f});

			PdfPTable data = new PdfPTable(9);
			Paragraph title = new Paragraph("BORANG PENANDAAN POKOK", subtitleFont);

			title.setAlignment(Element.ALIGN_CENTER);

			data.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			data.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			data.setWidthPercentage(100);
			data.setHeaderRows(1);

			document.add(title);
			document.add(new Phrase("", smallerFont));

			summary.addCell(new Phrase("(" + count++ + ") Hutan Simpan: " + tagging.getForestName(), smallerFont));
			summary.addCell(new Phrase("(" + count++ + ") Daerah: " + tagging.getRangeName(), smallerFont));
			summary.addCell(new Phrase("(" + count++ + ") No. Kompt.: " + tagging.getComptBlockNo(), smallerFont));
			summary.addCell(new Phrase("(" + count++ + ") No. Blok: " + taggingForm.getFormNo(), smallerFont));
			summary.addCell(new Phrase("(" + count++ + ") No. Garisan:", smallerFont));
			summary.addCell(new Phrase("(" + count++ + ") Bearing:", smallerFont));
			summary.addCell(new Phrase("(" + count++ + ") No. Petak: " + plotNo, smallerFont));

			PdfPCell limit = new PdfPCell(new Phrase("(" + count++ + ") Had Batas Tebangan:\nD: " + tagging.getDipterocarpLimit() + " cm, BD: " + tagging.getNonDipterocarpLimit() + " cm, CGL: " + (treeLimit == null ? tagging.getDipterocarpLimit() : treeLimit.getChengalLimit()) + " cm", smallerFont));
			PdfPCell locationMap = new PdfPCell();

			limit.setRowspan(2);
			locationMap.setColspan(2);
			locationMap.setBorder(0);
			locationMap.addElement(plotMap);

			PdfPCell detail1 = new PdfPCell(new Phrase("(21) ....... m", smallerFont));
			PdfPCell detail2 = new PdfPCell(new Phrase("(22) No. Petak: " + plotNo + "\n ", smallerFont));
			PdfPCell detail3 = new PdfPCell(new Phrase("(20) ....... m", smallerFont));
			PdfPCell arrowTop = new PdfPCell(new Phrase(" ", smallerFont));
			PdfPCell arrowLeft = new PdfPCell(new Phrase(" ", smallerFont));
			PdfPCell normal = new PdfPCell(new Phrase("50 m", smallerFont));
			PdfPCell rotate = new PdfPCell(new Phrase("50 m", smallerFont));

			detail1.setBorder(0);
			detail2.setBorder(0);
			detail3.setBorder(0);
			normal.setBorder(0);
			rotate.setBorder(0);
			arrowTop.setBorder(8);
			arrowLeft.setBorder(2);

			detail1.setRowspan(5);
			detail2.setColspan(7);
			normal.setRowspan(2);
			rotate.setColspan(2);

			rotate.setRotation(90);
			rotate.setHorizontalAlignment(Element.ALIGN_CENTER);
			normal.setHorizontalAlignment(Element.ALIGN_CENTER);
			rotate.setVerticalAlignment(Element.ALIGN_MIDDLE);
			normal.setVerticalAlignment(Element.ALIGN_MIDDLE);
			detail3.setVerticalAlignment(Element.ALIGN_BOTTOM);

			plotMap.addCell(top);
			plotMap.addCell(detail1);
			plotMap.addCell(detail2);

			for (int k = 0; k < 2; k++)
			{
				for (int i = 0; i < 5; i++)
					plotMap.addCell(new Phrase("\n\n\n", smallerFont));

				plotMap.addCell(arrowTop);
				plotMap.addCell(empty);
			}

			for (int i = 0; i < 5; i++)
				plotMap.addCell(new Phrase("\n\n\n", smallerFont));

			plotMap.addCell(rotate);

			for (int i = 0; i < 5; i++)
				plotMap.addCell(new Phrase("\n\n\n", smallerFont));

			plotMap.addCell(arrowTop);
			plotMap.addCell(empty);
			plotMap.addCell(detail3);

			for (int i = 0; i < 5; i++)
				plotMap.addCell(new Phrase("\n\n\n", smallerFont));

			plotMap.addCell(arrowTop);
			plotMap.addCell(empty);
			plotMap.addCell(empty);
			plotMap.addCell(arrowLeft);
			plotMap.addCell(arrowLeft);
			plotMap.addCell(normal);
			plotMap.addCell(arrowLeft);
			plotMap.addCell(arrowLeft);
			plotMap.addCell(empty);
			plotMap.addCell(empty);

			plotMap.addCell(empty);
			plotMap.addCell(empty);
			plotMap.addCell(empty);
			plotMap.addCell(empty);
			plotMap.addCell(empty);
			plotMap.addCell(empty);
			plotMap.addCell(empty);

			PdfPCell fn = new PdfPCell(new Phrase("Catatan:", smallerFont));
			PdfPCell mother = new PdfPCell(new Phrase("PI - Pokok Ibu", smallerFont));
			PdfPCell protect = new PdfPCell(new Phrase("PL - Pokok Perlindungan", smallerFont));
			PdfPCell log = new PdfPCell(new Phrase("X - Pokok Tebangan", smallerFont));
			PdfPCell nb = new PdfPCell(new Phrase("NB: X - Bil. Pokok - (12)\n ", smallerFont));

			fn.setBorder(0);
			mother.setBorder(0);
			protect.setBorder(0);
			log.setBorder(0);
			nb.setBorder(0);

			fn.setRowspan(2);
			mother.setColspan(3);
			protect.setColspan(3);
			log.setColspan(5);
			nb.setColspan(5);

			plotMap.addCell(fn);
			plotMap.addCell(mother);
			plotMap.addCell(log);
			plotMap.addCell(protect);
			plotMap.addCell(nb);

			summary.addCell(limit);
			summary.addCell(new Phrase("(" + count++ + ") Ketua Pasukan: " + tagging.getTeamLeaderName(), smallerFont));
			summary.addCell(new Phrase("(" + count++ + ") Tarikh: " + sdf.format(taggingForm.getTaggingDate()), smallerFont));
			summary.addCell(new Phrase("(" + count++ + ") No. Kad:", smallerFont));
			summary.addCell(locationMap);

			data.addCell(new Phrase("Bil. Pokok\n(" + count++ + ")", smallerFont));
			data.addCell(new Phrase("No. Siri Pokok\n(" + count++ + ")", smallerFont));
			data.addCell(new Phrase("Jenis Pokok\n(" + count++ + ")", smallerFont));
			data.addCell(new Phrase("Kod Jenis\n(" + count++ + ")", smallerFont));
			data.addCell(new Phrase("Kualiti Pokok\n(" + count++ + ")", smallerFont));
			data.addCell(new Phrase("DPD (cm)\n(" + count++ + ")", smallerFont));
			data.addCell(new Phrase("Isipadu (m\u00B3)\n(" + count++ + ")", smallerFont));
			data.addCell(new Phrase("Bil. Tual\n(" + count++ + ")", smallerFont));
			data.addCell(new Phrase("Catatan\n(" + count++ + ")", smallerFont));

			for (int i = 0; i < taggingRecords.size(); i++)
			{
				TaggingRecord taggingRecord = taggingRecords.get(i);

				data.addCell(new Phrase("" + (i + 1), smallerFont));
				data.addCell(new Phrase(taggingRecord.getSerialNo(), smallerFont));
				data.addCell(new Phrase(taggingRecord.getSpeciesName(), smallerFont));
				data.addCell(new Phrase(taggingRecord.getSpeciesCode() == null ? "" : taggingRecord.getSpeciesCode(), smallerFont));
				data.addCell(new Phrase(taggingRecord.getLogQualityID() == 0 ? "-" : ("" + taggingRecord.getLogQualityID()), smallerFont));
				data.addCell(new Phrase(taggingRecord.getDiameter() + " cm", smallerFont));
				data.addCell(new Phrase(taggingRecord.getLogQualityID() == 0 ? "-" : df.format(taggingRecord.getVolume()), smallerFont));
				data.addCell(new Phrase("" + (taggingRecord.getEstimation() == null || taggingRecord.getEstimation() == 0 ? "-" : taggingRecord.getEstimation()), smallerFont));
				data.addCell(new Phrase(taggingRecord.getNote(), smallerFont));
			}

			PdfPCell blank = new PdfPCell(new Phrase(" ", smallerFont));

			blank.setBorder(0);
			blank.setColspan(9);
			data.addCell(blank);

			table.addCell(summary);
			table.addCell(empty);
			table.addCell(data);

			document.add(table);
			document.add(note);
		}

		document.close();
	}

	private static String summarize(TreeSet<String> set)
	{
		String output = "-";

		if (!set.isEmpty())
		{
			TreeSet<String> prefixes = new TreeSet<>();
			LinkedHashSet<String> summary1 = new LinkedHashSet<>();
			LinkedHashSet<String> summary2 = new LinkedHashSet<>();
			LinkedHashSet<String> summary = new LinkedHashSet<>();
			String regex = "(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)";

			for (String value : set)
			{
				String[] values = value.split(regex);
				int length = values.length;

				if (length >= 2)
				{
					String prefix = "";

					for (int i = 0; i < length - 1; i++)
						prefix += values[i];

					prefixes.add(prefix);
				}
			}

			for (String prefix : prefixes)
			{
				ArrayList<String> values = new ArrayList<>();
				TreeSet<Integer> list = new TreeSet<>();

				for (String value : set)
				{
					if (value.startsWith(prefix))
					{
						value = value.trim();

						values.add(value);
						list.add(new Integer(value.replaceAll(prefix, "")));
					}
				}

				if (!list.isEmpty())
				{
					int first = list.first(), last = list.last(), prev = -1,
							start = first;

					for (int i = first; i <= last; i++)
					{
						if (prev != -1 && prev != i - 1)
						{
							summary1.add(start == prev ? prefix + start
									: "DARI " + prefix + start + " HINGGA "
									+ prefix + prev);

							if (list.contains(i))
								start = i;
						}

						if (list.contains(i))
							prev = i;
					}

					summary1.add(start == prev ? prefix + df4.format(start)
					: "DARI " + prefix + df4.format(start) + " HINGGA "
					+ prefix + df4.format(prev));
					set.removeAll(values);
				}
			}

			TreeSet<Integer> list = new TreeSet<>();

			for (String value : set)
				list.add(new Integer(value));

			if (list.size() != 0)
			{
				int first = list.first(), last = list.last(), prev = -1,
						start = first;

				for (int i = first; i <= last; i++)
				{
					if (prev != -1 && prev != i - 1)
					{
						summary2.add(start == prev ? "" + start
								: "DARI " + start + " HINGGA " + prev);

						if (list.contains(i))
							start = i;
					}

					if (list.contains(i))
						prev = i;
				}

				summary2.add(start == prev ? df4.format(start)
						: "DARI " + df4.format(start) + " HINGGA "
						+ df4.format(prev));
			}

			summary.addAll(summary2);
			summary.addAll(summary1);

			output = summary.toString().replaceAll("\\[", "").replaceAll("\\]",
					"");
		}

		return output;
	}

	private static PdfPCell getCell(Phrase content) throws Exception
	{
		PdfPCell cell = new PdfPCell(content);
		BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN,
				BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);

		float padding = cell.getPaddingTop();
		float fontSize = content.getFont().getSize();
		float capHeight = bf.getFontDescriptor(BaseFont.CAPHEIGHT, fontSize);

		cell.setPadding(padding);
		cell.setPaddingBottom(2 * padding);
		cell.setPaddingTop(capHeight - fontSize + 1.5f * padding);

		return cell;
	}

	private static PdfPTable assembleTaggingGeneralDetails(Tagging tagging, int flag) throws Exception
	{
		PdfPTable taggingAreaDetails = new PdfPTable(2);
		String cellTitle[] = {"DAERAH HUTAN", "RENJ HUTAN", "HUTAN SIMPAN",
		"NO. KOMPARTMEN/SUBKOMPARTMEN"};
		ArrayList<String> cellData = new ArrayList<>();

		cellData.add(tagging.getDistrictName().toUpperCase());
		cellData.add(tagging.getRangeName().toUpperCase());
		cellData.add(tagging.getForestName().toUpperCase());
		cellData.add(tagging.getComptBlockNo().toUpperCase());

		for (int index = 0; index < cellTitle.length; index++)
		{
			PdfPCell cell = getCell(new Phrase(cellTitle[index], bolderFont));

			if (flag == 2)
				cell.setBorder(Rectangle.NO_BORDER);

			cell.setVerticalAlignment(Element.ALIGN_TOP);
			taggingAreaDetails.addCell(cell);

			cell = getCell(new Phrase(cellData.get(index), bolderFont));

			if (flag == 2)
				cell.setBorder(Rectangle.NO_BORDER);

			cell.setVerticalAlignment(Element.ALIGN_TOP);
			taggingAreaDetails.addCell(cell);
		}

		return taggingAreaDetails;
	}

	private static PdfPTable assembleCuttingLimitDetails(Tagging tagging, TreeLimit treeLimit) throws Exception
	{
		PdfPTable cuttingLimitDetails = new PdfPTable(4);
		Phrase nos = new Phrase("", smallerFont);
		Phrase names = new Phrase("", smallerFont);
		Phrase limits = new Phrase("", smallerFont);
		int number = 3;

		cuttingLimitDetails.getDefaultCell().setBorder(0);
		cuttingLimitDetails.setWidths(new float[] {10f, 5f, 35f, 50f});

		PdfPCell cell = getCell(new Phrase("HAD TEBANGAN", bolderFont));

		cell.setColspan(3);
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cuttingLimitDetails.addCell(cell);

		cell = getCell(new Phrase("", bolderFont));

		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cuttingLimitDetails.addCell(cell);

		nos.add(new Chunk("(i)   \n(ii)   \n(iii)   ", smallerFont));
		names.add(
				new Chunk("Dipterokarp\nBukan Dipterokarp\nChengal", smallerFont));
		limits.add(new Chunk("" + tagging.getDipterocarpLimit(), bolderFont));
		limits.add(new Chunk(" cm ppd", smallerFont));
		limits.add(new Chunk("\n" + tagging.getNonDipterocarpLimit(), bolderFont));
		limits.add(new Chunk(" cm ppd", smallerFont));
		limits.add(new Chunk(
				"\n" + (treeLimit == null ? tagging.getDipterocarpLimit()
						: treeLimit.getChengalLimit()),
				bolderFont));
		limits.add(new Chunk(" cm ppd", smallerFont));

		ArrayList<CuttingLimit> cuttingLimits = tagging.getCuttingLimits();

		for (CuttingLimit cuttingLimit : cuttingLimits)
		{
			nos.add(new Chunk(
					"\n(" + RomanNumberConverter.toRoman(++number).toLowerCase()
					+ ")   ",
					smallerFont));
			names.add(
					new Chunk("\n" + cuttingLimit.getSpeciesName(), smallerFont));
			limits.add(new Chunk("\n" + cuttingLimit.getMinDiameter(), bolderFont));
			limits.add(new Chunk(" cm ppd", smallerFont));
		}

		cell = getCell(nos);

		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorder(Rectangle.BOTTOM | Rectangle.LEFT);
		cuttingLimitDetails.addCell(cell);

		cell = new PdfPCell(new Phrase("", smallerFont));

		cell.setBorder(Rectangle.BOTTOM);
		cuttingLimitDetails.addCell(cell);

		cell = getCell(names);

		cell.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT);
		cuttingLimitDetails.addCell(cell);

		cell = getCell(limits);

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT | Rectangle.LEFT);
		cuttingLimitDetails.addCell(cell);

		return cuttingLimitDetails;
	}

	private static PdfPTable assembleTaggingHammers(Tagging tagging) throws Exception
	{
		PdfPTable taggingAreaDetails = new PdfPTable(2);
		PdfPCell cell = getCell(new Phrase("NO. TUKUL TANDA JABATAN", bolderFont));

		taggingAreaDetails.addCell(cell);

		String tukulNo = tagging.getHammers().toString();
		cell = getCell(
				new Phrase(tukulNo.substring(1, tukulNo.length() - 1), bolderFont));

		taggingAreaDetails.addCell(cell);

		return taggingAreaDetails;
	}

	private static PdfPTable assembleTaggingSeries(Tagging tagging, int taggingTypeID) throws Exception
	{
		PdfPTable taggingAreaDetails = assembleTaggingHammers(tagging);
		ArrayList<TaggingForm> taggingForms = tagging.getTaggingForms();

		if (taggingTypeID == 1)
		{
			TreeSet<String> tebangan = new TreeSet<>(), ibu = new TreeSet<>(),
					perlindungan = new TreeSet<>();
			PdfPCell cell = getCell(
					new Phrase("NO. SIRI TAG TANDA POKOK TEBANGAN", bolderFont));

			cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
			taggingAreaDetails.addCell(cell);

			for (TaggingForm taggingForm : taggingForms)
			{
				if (taggingForm.getTaggingTypeID() == taggingTypeID)
				{
					ArrayList<TaggingRecord> taggingRecords = taggingForm
							.getTaggingRecords();

					for (TaggingRecord taggingRecord : taggingRecords)
						if (taggingRecord.getTreeTypeID() == 1)
							tebangan.add(taggingRecord.getSerialNo());
				}
			}

			cell = getCell(new Phrase(summarize(tebangan), smallerFont));

			cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
			taggingAreaDetails.addCell(cell);

			cell = getCell(new Phrase("NO. SIRI TAG TANDA POKOK IBU", bolderFont));

			cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
			taggingAreaDetails.addCell(cell);

			for (TaggingForm taggingForm : taggingForms)
			{
				if (taggingForm.getTaggingTypeID() == taggingTypeID)
				{
					ArrayList<TaggingRecord> taggingRecords = taggingForm
							.getTaggingRecords();

					for (TaggingRecord taggingRecord : taggingRecords)
						if (taggingRecord.getTreeTypeID() == 2)
							ibu.add(taggingRecord.getSerialNo());
				}
			}

			cell = getCell(new Phrase(summarize(ibu), smallerFont));

			cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
			taggingAreaDetails.addCell(cell);

			cell = getCell(new Phrase("NO. SIRI TAG TANDA POKOK PERLINDUNGAN",
					bolderFont));

			cell.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT | Rectangle.LEFT);
			taggingAreaDetails.addCell(cell);

			for (TaggingForm taggingForm : taggingForms)
			{
				if (taggingForm.getTaggingTypeID() == taggingTypeID)
				{
					ArrayList<TaggingRecord> taggingRecords = taggingForm
							.getTaggingRecords();

					for (TaggingRecord taggingRecord : taggingRecords)
						if (taggingRecord.getTreeTypeID() == 3)
							perlindungan.add(taggingRecord.getSerialNo());
				}
			}

			cell = getCell(new Phrase(summarize(perlindungan), smallerFont));

			cell.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT | Rectangle.LEFT);
			taggingAreaDetails.addCell(cell);
		}
		else if (taggingTypeID == 2)
		{
			TreeSet<String> tebangan = new TreeSet<>();
			PdfPCell cell = getCell(
					new Phrase("NO. SIRI TAG TANDA POKOK", bolderFont));

			taggingAreaDetails.addCell(cell);

			for (TaggingForm taggingForm : taggingForms)
			{
				if (taggingForm.getTaggingTypeID() == taggingTypeID)
				{
					ArrayList<TaggingRecord> taggingRecords = taggingForm
							.getTaggingRecords();

					for (TaggingRecord taggingRecord : taggingRecords)
						tebangan.add(taggingRecord.getSerialNo());
				}
			}

			cell = getCell(new Phrase(summarize(tebangan), smallerFont));

			taggingAreaDetails.addCell(cell);

			return taggingAreaDetails;
		}

		return taggingAreaDetails;
	}

	private static PdfPTable assembleTreeTagSummary(Tagging tagging, int taggingTypeID, int treeTypeID)
			throws Exception
	{
		PdfPTable totalTagDetails = new PdfPTable(6);

		totalTagDetails.setWidths(
				new float[] {5f, 28.32f, 16.67f, 16.67f, 16.67f, 16.67f});

		PdfPCell cell = getCell(new Phrase("JUMLAH POKOK DITANDA:", bolderFont));

		cell.setColspan(6);
		totalTagDetails.addCell(cell);

		String header3[] = {"Kumpulan Spesis Pokok", "Bil. Pokok",
				"Bil. Pokok/ha", "Isipadu Balak (m\u00B3)",
		"Isipadu Balak (m\u00B3)/ha"};

		for (int index = 0; index < header3.length; index++)
		{
			cell = getCell(new Phrase(header3[index], smallerFont));

			if (index == 0)
				cell.setColspan(2);

			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			totalTagDetails.addCell(cell);
		}

		cell = getCell(new Phrase("(i)", smallerFont));

		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
		totalTagDetails.addCell(cell);

		cell = getCell(new Phrase("Dipterokarp", smallerFont));

		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
		totalTagDetails.addCell(cell);

		int totalTreeDip = 0, totalTreeNonDip = 0, totalTreeChengal = 0;
		double totalVolumeDip = 0, totalVolumeNonDip = 0,
				totalVolumeChengal = 0;
		ArrayList<TaggingForm> taggingForms = tagging.getTaggingForms();
		ArrayList<CuttingLimit> cuttingLimits = tagging.getCuttingLimits();
		ArrayList<Integer> otherSpeciesIDs = new ArrayList<>();

		for (CuttingLimit cuttingLimit : cuttingLimits)
			otherSpeciesIDs.add(cuttingLimit.getSpeciesID());

		for (TaggingForm taggingForm : taggingForms)
		{
			if (taggingForm.getTaggingTypeID() == taggingTypeID)
			{
				ArrayList<TaggingRecord> taggingRecords = taggingForm
						.getTaggingRecords();

				for (TaggingRecord taggingRecord : taggingRecords)
				{
					if (!otherSpeciesIDs.contains(taggingRecord.getSpeciesID())
							&& taggingRecord.getTreeTypeID() == treeTypeID)
					{
						double volume = taggingRecord.getVolume();

						if (taggingRecord.getSpeciesTypeID() == 1
								|| taggingRecord.getSpeciesTypeID() == 2)
						{
							if (!"1201".equals(taggingRecord.getSpeciesCode()))
							{
								totalTreeDip++;
								totalVolumeDip += volume;
							}
							else
							{
								totalTreeChengal++;
								totalVolumeChengal += volume;
							}
						}
						else
						{
							totalTreeNonDip++;
							totalVolumeNonDip += volume;
						}
					}
				}
			}
		}

		double totalHectreDip = totalTreeDip / tagging.getArea(),
				volumeHectreDip = totalVolumeDip / tagging.getArea(),
				totalHectreNonDip = totalTreeNonDip / tagging.getArea(),
				volumeHectreNonDip = totalVolumeNonDip / tagging.getArea(),
				totalHectreChengal = totalTreeChengal / tagging.getArea(),
				volumeHectreChengal = totalVolumeChengal / tagging.getArea();
		String out1 = totalTreeDip == 0 ? "-" : Integer.toString(totalTreeDip),
				out2 = totalHectreDip == 0 ? "-" : df.format(totalHectreDip),
						out3 = totalVolumeDip == 0 ? "-" : df.format(totalVolumeDip),
								out4 = volumeHectreDip == 0 ? "-" : df.format(volumeHectreDip);

		cell = getCell(new Phrase(out1, bolderFont));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalTagDetails.addCell(cell);
		cell = getCell(new Phrase(out2, bolderFont));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalTagDetails.addCell(cell);
		cell = getCell(new Phrase(out3, bolderFont));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalTagDetails.addCell(cell);
		cell = getCell(new Phrase(out4, bolderFont));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalTagDetails.addCell(cell);

		cell = getCell(new Phrase("(ii)", smallerFont));

		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
		totalTagDetails.addCell(cell);

		cell = getCell(new Phrase("Bukan Dipterokarp", smallerFont));

		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
		totalTagDetails.addCell(cell);

		out1 = totalTreeNonDip == 0 ? "-" : Integer.toString(totalTreeNonDip);
		out2 = totalHectreNonDip == 0 ? "-" : df.format(totalHectreNonDip);
		out3 = totalVolumeNonDip == 0 ? "-" : df.format(totalVolumeNonDip);
		out4 = volumeHectreNonDip == 0 ? "-" : df.format(volumeHectreNonDip);
		int number = 4;

		cell = getCell(new Phrase(out1, bolderFont));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalTagDetails.addCell(cell);
		cell = getCell(new Phrase(out2, bolderFont));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalTagDetails.addCell(cell);
		cell = getCell(new Phrase(out3, bolderFont));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalTagDetails.addCell(cell);
		cell = getCell(new Phrase(out4, bolderFont));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalTagDetails.addCell(cell);

		cell = getCell(new Phrase("(iii)", smallerFont));

		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
		totalTagDetails.addCell(cell);

		cell = getCell(new Phrase("Chengal", smallerFont));

		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
		totalTagDetails.addCell(cell);

		out1 = totalTreeChengal == 0 ? "-" : Integer.toString(totalTreeChengal);
		out2 = totalHectreChengal == 0 ? "-" : df.format(totalHectreChengal);
		out3 = totalVolumeChengal == 0 ? "-" : df.format(totalVolumeChengal);
		out4 = volumeHectreChengal == 0 ? "-" : df.format(volumeHectreChengal);

		cell = getCell(new Phrase(out1, bolderFont));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalTagDetails.addCell(cell);
		cell = getCell(new Phrase(out2, bolderFont));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalTagDetails.addCell(cell);
		cell = getCell(new Phrase(out3, bolderFont));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalTagDetails.addCell(cell);
		cell = getCell(new Phrase(out4, bolderFont));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalTagDetails.addCell(cell);

		int totalTaggedOtherDipTree = 0;
		int totalTaggedOtherNonDipTree = 0;

		double totalTaggedOtherDipTreePerHectre = 0.0f;
		double totalTaggedOtherNonDipTreePerHectre = 0.0f;

		double totalVolumeTaggedOtherDipTree = 0.0f;
		double totalVolumeTaggedOtherNonDipTree = 0.0f;

		double totalVolumeTaggedOtherDipTreePerHectre = 0.0f;
		double totalVolumeTaggedOtherNonDipTreePerHectre = 0.0f;

		int totalCumTree = 0;

		double totalCumTaggedTreePerHecter = 0.0f;
		double totalCumTaggedTreeVolume = 0.0f;
		double totalCumVolumeTaggedTreePerHectre = 0.0f;

		for (CuttingLimit cuttingLimit : cuttingLimits)
		{
			int totalTaggedTree = 0;
			double totalTaggedTreePerHectre = 0, totalVolumeTaggedTree = 0,
					totalVolumeTaggedTreePerHectre = 0;

			for (TaggingForm taggingForm : taggingForms)
			{
				if (taggingForm.getTaggingTypeID() == taggingTypeID)
				{
					ArrayList<TaggingRecord> taggingRecords = taggingForm
							.getTaggingRecords();

					for (TaggingRecord taggingRecord : taggingRecords)
					{
						if (otherSpeciesIDs
								.contains(taggingRecord.getSpeciesID())
								&& taggingRecord.getSpeciesID() == cuttingLimit
								.getSpeciesID()
								&& taggingRecord.getTreeTypeID() == treeTypeID)
						{
							double volume = taggingRecord.getVolume();

							totalTaggedTree++;
							totalVolumeTaggedTree += volume;
						}
					}
				}
			}

			totalTaggedTreePerHectre += totalTaggedTree / tagging.getArea();
			totalVolumeTaggedTreePerHectre = totalVolumeTaggedTree
					/ tagging.getArea();

			if (cuttingLimit.getSpeciesTypeID() == 1
					|| cuttingLimit.getSpeciesTypeID() == 2)
			{
				totalTaggedOtherDipTree += totalTaggedTree;
				totalTaggedOtherDipTreePerHectre += totalTaggedTreePerHectre;
				totalVolumeTaggedOtherDipTree += totalVolumeTaggedTree;
				totalVolumeTaggedOtherDipTreePerHectre += totalVolumeTaggedTreePerHectre;
			}
			else
			{
				totalTaggedOtherNonDipTree += totalTaggedTree;
				totalTaggedOtherNonDipTreePerHectre += totalTaggedTreePerHectre;
				totalVolumeTaggedOtherNonDipTree += totalVolumeTaggedTree;
				totalVolumeTaggedOtherNonDipTreePerHectre += totalVolumeTaggedTreePerHectre;
			}

			cell = getCell(new Phrase(
					"(" + RomanNumberConverter.toRoman(number++).toLowerCase()
					+ ")",
					smallerFont));

			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			totalTagDetails.addCell(cell);

			cell = getCell(new Phrase(cuttingLimit.getSpeciesName(), smallerFont));

			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			totalTagDetails.addCell(cell);

			out1 = totalTaggedTree == 0 ? "-"
					: Integer.toString(totalTaggedTree);
			out2 = totalTaggedTreePerHectre == 0 ? "-"
					: df.format(totalTaggedTreePerHectre);
			out3 = totalVolumeTaggedTree == 0 ? "-"
					: df.format(totalVolumeTaggedTree);
			out4 = totalVolumeTaggedTreePerHectre == 0 ? "-"
					: df.format(totalVolumeTaggedTreePerHectre);

			cell = getCell(new Phrase(out1, bolderFont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			totalTagDetails.addCell(cell);
			cell = getCell(new Phrase(out2, bolderFont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			totalTagDetails.addCell(cell);
			cell = getCell(new Phrase(out3, bolderFont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			totalTagDetails.addCell(cell);
			cell = getCell(new Phrase(out4, bolderFont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			totalTagDetails.addCell(cell);
		}

		cell = getCell(new Phrase("Jumlah", bolderFont));

		cell.setColspan(2);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalTagDetails.addCell(cell);

		totalCumTree = totalTreeDip + totalTreeNonDip + totalTreeChengal
				+ totalTaggedOtherDipTree + totalTaggedOtherNonDipTree;
		cell = getCell(new Phrase(Integer.toString(totalCumTree), bolderFont));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalTagDetails.addCell(cell);

		totalCumTaggedTreePerHecter = totalHectreDip + totalHectreNonDip
				+ totalHectreChengal + totalTaggedOtherDipTreePerHectre
				+ totalTaggedOtherNonDipTreePerHectre;
		cell = getCell(
				new Phrase(df.format(totalCumTaggedTreePerHecter), bolderFont));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalTagDetails.addCell(cell);

		totalCumTaggedTreeVolume = totalVolumeDip + totalVolumeNonDip
				+ totalVolumeChengal + totalVolumeTaggedOtherDipTree
				+ totalVolumeTaggedOtherNonDipTree;
		cell = getCell(new Phrase(df.format(totalCumTaggedTreeVolume), bolderFont));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalTagDetails.addCell(cell);

		totalCumVolumeTaggedTreePerHectre = volumeHectreDip + volumeHectreNonDip
				+ volumeHectreChengal + totalVolumeTaggedOtherDipTreePerHectre
				+ totalVolumeTaggedOtherNonDipTreePerHectre;
		cell = getCell(new Phrase(df.format(totalCumVolumeTaggedTreePerHectre),
				bolderFont));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalTagDetails.addCell(cell);

		cell = getCell(new Phrase(" ", bolderFont));

		cell.setColspan(6);
		totalTagDetails.addCell(cell);

		cell = getCell(new Phrase("LESEN PENGUSAHASILAN HUTAN", bolderFont));

		cell.setColspan(3);
		totalTagDetails.addCell(cell);

		cell = getCell(new Phrase("", bolderFont));

		cell.setColspan(3);
		totalTagDetails.addCell(cell);

		cell = getCell(new Phrase("(i)", smallerFont));
		cell.setBorder(Rectangle.TOP | Rectangle.LEFT);
		totalTagDetails.addCell(cell);
		cell = getCell(new Phrase("No. Lesen", smallerFont));
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT);
		cell.setColspan(2);
		totalTagDetails.addCell(cell);
		cell = getCell(new Phrase("(HS)", smallerFont));
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT);
		cell.setColspan(3);
		totalTagDetails.addCell(cell);

		cell = getCell(new Phrase("(ii)", smallerFont));
		cell.setBorder(Rectangle.BOTTOM | Rectangle.LEFT);
		totalTagDetails.addCell(cell);
		cell = getCell(new Phrase("Luas (ha)", smallerFont));
		cell.setColspan(2);
		cell.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT);
		totalTagDetails.addCell(cell);
		cell = getCell(new Phrase(tagging.getArea() + "", bolderFont));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.BOTTOM | Rectangle.LEFT);
		totalTagDetails.addCell(cell);
		cell = getCell(new Phrase("ha", bolderFont));
		cell.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT);
		cell.setColspan(2);
		totalTagDetails.addCell(cell);

		return totalTagDetails;
	}

	private static PdfPTable assembleLoggerDetails() throws Exception
	{
		PdfPTable loggerTable = new PdfPTable(2);
		PdfPCell cell = getCell(
				new Phrase("NAMA DAN ALAMAT PEMEGANG LESEN", bolderFont));

		loggerTable.addCell(cell);

		Phrase addressLicenseHolder = new Phrase("-", smallerFont);
		cell = getCell(addressLicenseHolder);

		loggerTable.addCell(cell);

		cell = getCell(
				new Phrase("NAMA DAN ALAMAT KONTRAKTOR PEMBALAKAN", bolderFont));
		loggerTable.addCell(cell);

		Phrase addressContractor = new Phrase("-", smallerFont);
		cell = getCell(addressContractor);

		loggerTable.addCell(cell);

		return loggerTable;
	}

	private static PdfPTable assembleLicenseDetails() throws Exception
	{
		PdfPTable licenseDetails = new PdfPTable(3);

		licenseDetails.setWidths(new float[] {50f, 25f, 25f});

		PdfPCell cell = getCell(new Phrase("TEMPOH KUAT KUASA LESEN", bolderFont));
		licenseDetails.addCell(cell);
		cell = getCell(new Phrase("Dari", smallerFont));
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
		licenseDetails.addCell(cell);
		cell = getCell(new Phrase("hingga", smallerFont));
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
		licenseDetails.addCell(cell);

		cell = getCell(
				new Phrase("PEMBAHARUAN TEMPOH KUAT KUASA LESEN", bolderFont));
		cell.setRowspan(2);
		licenseDetails.addCell(cell);
		cell = getCell(new Phrase("Dari", smallerFont));
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
		licenseDetails.addCell(cell);
		cell = getCell(new Phrase("hingga", smallerFont));
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
		licenseDetails.addCell(cell);
		cell = getCell(new Phrase("Dari", smallerFont));
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
		licenseDetails.addCell(cell);
		cell = getCell(new Phrase("hingga", smallerFont));
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
		licenseDetails.addCell(cell);

		return licenseDetails;
	}

	private static PdfPTable assembleHallDetails(Tagging tagging) throws Exception
	{
		PdfPTable tableB = new PdfPTable(2);
		PdfPCell cell = getCell(new Phrase("BALAI PEMERIKSA HUTAN", bolderFont));

		tableB.addCell(cell);

		cell = getCell(new Phrase("BPH " + tagging.getHallName().toUpperCase(),
				bolderFont));
		tableB.addCell(cell);

		return tableB;
	}
}