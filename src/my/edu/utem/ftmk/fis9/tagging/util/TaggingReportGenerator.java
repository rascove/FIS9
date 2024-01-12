package my.edu.utem.ftmk.fis9.tagging.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.DoubleAdder;

import com.itextpdf.text.BaseColor;
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
import my.edu.utem.ftmk.fis9.maintenance.model.MainRevenueRoyaltyRate;
import my.edu.utem.ftmk.fis9.maintenance.model.TreeLimit;
import my.edu.utem.ftmk.fis9.prefelling.model.CuttingLimit;
import my.edu.utem.ftmk.fis9.tagging.model.Tagging;
import my.edu.utem.ftmk.fis9.tagging.model.TaggingForm;
import my.edu.utem.ftmk.fis9.tagging.model.TaggingRecord;

public class TaggingReportGenerator
{
	private final static Font normal10 = new Font(Font.FontFamily.TIMES_ROMAN,
			10, Font.NORMAL);
	private final static Font normal8 = new Font(Font.FontFamily.TIMES_ROMAN, 8,
			Font.NORMAL);
	private final static Font bold10 = new Font(Font.FontFamily.TIMES_ROMAN, 10,
			Font.BOLD);
	private final static Font bold8 = new Font(Font.FontFamily.TIMES_ROMAN, 8,
			Font.BOLD);
	private final static DecimalFormat df = new DecimalFormat("0.00");
	private final static DecimalFormat df4 = new DecimalFormat("0000");

	private int totalTaggedOtherDipTree = 0;
	private int totalTaggedOtherNonDipTree = 0;

	private int totalTualOtherDipTree = 0;
	private int totalTualOtherNonDipTree = 0;

	private double totalTaggedOtherDipTreePerHectre = 0.0f;
	private double totalTaggedOtherNonDipTreePerHectre = 0.0f;

	private double totalVolumeTaggedOtherDipTree = 0.0f;
	private double totalVolumeTaggedOtherNonDipTree = 0.0f;

	private double totalVolumeTaggedOtherDipTreePerHectre = 0.0f;
	private double totalVolumeTaggedOtherNonDipTreePerHectre = 0.0f;

	private int totalCumTree = 0;
	private int totalTaggedTreeC = 0;

	private double totalCumTaggedTreePerHecter = 0.0f;
	private double totalCumTaggedTreeVolume = 0.0f;
	private double totalCumVolumeTaggedTreePerHectre = 0.0f;

	private Tagging tagging;
	private TreeLimit treeLimit;
	private TreeMap<String, MainRevenueRoyaltyRate> map;

	public static void generate(File file, Tagging tagging, TreeLimit treeLimit,
			ArrayList<MainRevenueRoyaltyRate> mainRevenueRoyaltyRates)
			throws Exception
	{
		new TaggingReportGenerator(file, tagging, treeLimit,
				mainRevenueRoyaltyRates);
	}

	private TaggingReportGenerator(File file, Tagging tagging,
			TreeLimit treeLimit,
			ArrayList<MainRevenueRoyaltyRate> mainRevenueRoyaltyRates)
			throws Exception
	{
		this.tagging = tagging;
		this.treeLimit = treeLimit;
		this.map = new TreeMap<>();

		for (MainRevenueRoyaltyRate mrrr : mainRevenueRoyaltyRates)
			map.put(mrrr.getSpeciesCode() + " - " + mrrr.getSpeciesName(),
					mrrr);

		Document document = new Document(PageSize.A4, 65f, 65f, 60f, 60f);
		ArrayList<TaggingForm> taggingForms = tagging.getTaggingForms();

		PdfWriter.getInstance(document, new FileOutputStream(file));

		document.open();

		document.addSubject("Jabatan Perhutanan Negeri Sembilan");
		document.addKeywords("Sesi Bancian");
		document.addAuthor("JPNS");
		document.addCreator("JPNS");
		document.addTitle("LAPORAN KERJA-KERJA PENANDAAN POKOK");

		boolean emptyTebangan = true, emptyIbu = true, emptyPerlindungan = true,
				emptyJalan = true, emptyJalan2 = true, emptyMatau = true,
				emptyKongsi = true;

		for (TaggingForm taggingForm : taggingForms)
		{
			if (!emptyTebangan && !emptyIbu && !emptyPerlindungan && !emptyJalan
					&& !emptyJalan2 && !emptyMatau && !emptyKongsi)
				break;

			int taggingTypeID = taggingForm.getTaggingTypeID();
			ArrayList<TaggingRecord> taggingRecords = taggingForm
					.getTaggingRecords();

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
			else if (taggingTypeID == 2)
			{
				if (!emptyJalan)
					continue;

				emptyJalan = taggingRecords.isEmpty();
			}
			else if (taggingTypeID == 3)
			{
				if (!emptyJalan2)
					continue;

				emptyJalan2 = taggingRecords.isEmpty();
			}
			else if (taggingTypeID == 4)
			{
				if (!emptyMatau)
					continue;

				emptyMatau = taggingRecords.isEmpty();
			}
			else if (taggingTypeID == 5)
			{
				if (!emptyKongsi)
					continue;

				emptyKongsi = taggingRecords.isEmpty();
			}
		}

		// if (!emptyTebangan || !emptyIbu || !emptyPerlindungan)
		if (!emptyTebangan)
		{
			Paragraph reportTitle = new Paragraph(
					"BUKU PENANDAAN POKOK DAN KAWALAN PENGELUARAN KAYU",
					bold10);

			reportTitle.setAlignment(Paragraph.ALIGN_CENTER);
			document.add(reportTitle);

			reportTitle = new Paragraph("(POKOK TEBANGAN)", normal8);

			reportTitle.setAlignment(Paragraph.ALIGN_CENTER);
			reportTitle.setSpacingAfter(15);
			document.add(reportTitle);

			Paragraph sectionTitle = new Paragraph("A. MAKLUMAT ASAS", bold10);

			document.add(sectionTitle);
			document.add(new Paragraph("\n", bold10));

			PdfPTable taggingAreaDetails = assembleTaggingGeneralDetails(1);

			taggingAreaDetails.setWidthPercentage(100.0f);
			document.add(taggingAreaDetails);
			document.add(new Paragraph("\n", normal8));

			PdfPTable cuttingLimitDetails = assembleCuttingLimitDetails();

			cuttingLimitDetails.setWidthPercentage(100.0f);
			document.add(cuttingLimitDetails);
			document.add(new Paragraph("\n", normal8));

			PdfPTable taggingSeries = assembleTaggingSeries(1);

			taggingSeries.setWidthPercentage(100.0f);
			document.add(taggingSeries);
			document.add(new Paragraph("\n", normal8));

			PdfPTable totalTagDetails = assembleTreeTagSummary(1, 1);

			totalTagDetails.setWidthPercentage(100.0f);
			document.add(totalTagDetails);
			document.add(new Paragraph("\n", normal8));

			PdfPTable loggerDetails = assembleLoggerDetails();

			loggerDetails.setWidthPercentage(100.0f);
			document.add(loggerDetails);
			document.add(new Paragraph("\n", normal8));

			PdfPTable licenseDetails = assembleLicenseDetails();

			licenseDetails.setWidthPercentage(100.0f);
			document.add(licenseDetails);
			document.add(new Paragraph("\n", normal8));

			PdfPTable hallDetails = assembleHallDetails();

			hallDetails.setWidthPercentage(100.0f);
			document.add(hallDetails);
			document.add(new Paragraph("\n", normal8));

			document.newPage();

			reportTitle = new Paragraph("JABATAN PERHUTANAN NEGERI SEMBILAN",
					bold10);
			reportTitle.setAlignment(Paragraph.ALIGN_CENTER);
			reportTitle.setSpacingAfter(15);
			document.add(reportTitle);

			taggingAreaDetails = assembleTaggingGeneralDetails(2);

			taggingAreaDetails.setWidthPercentage(100.0f);
			taggingAreaDetails.setSpacingAfter(15);
			document.add(taggingAreaDetails);

			sectionTitle = new Paragraph("B. POKOK-POKOK DITANDA ", bold10);

			sectionTitle.setSpacingAfter(5);
			document.add(sectionTitle);

			PdfPTable tableB = assembleLogDetailsByTaggingType(1, 1);
			tableB.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableB.setSpacingAfter(5);
			document.add(tableB);

			sectionTitle = new Paragraph("C. POKOK-POKOK IBU & PERLINDUNGAN",
					bold10);
			sectionTitle.setSpacingAfter(5);
			document.add(sectionTitle);

			PdfPTable tableC = assembleTotalMotherProtectedTrees(1);

			tableC.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableC.setSpacingAfter(5);
			document.add(tableC);

			PdfPTable tableD = assembleGrandTotalTaggedTree();

			tableD.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableD.setSpacingBefore(5);
			tableD.setSpacingAfter(5);
			document.add(tableD);

			if (!emptyTebangan)
			{
				document.setPageSize(PageSize.A4.rotate());
				document.setMargins(20f, 20f, 20f, 20f);
				document.newPage();

				sectionTitle = new Paragraph(
						"BUTIRAN PENGELUARAN KAYU DI BALAI PEMERIKSA HUTAN",
						normal10);

				document.add(sectionTitle);

				String header2TaggedTreeList = tagging.getForestName()
						.toUpperCase() + " KPT " + tagging.getComptBlockNo()
						+ " - POKOK TEBANGAN";
				Paragraph sectionTitleLine2 = new Paragraph(
						header2TaggedTreeList, normal10);

				sectionTitleLine2.setSpacingAfter(5);
				document.add(sectionTitleLine2);

				PdfPTable tableTaggingDetails = assembleTreeTaggingDetails(1,
						1);

				tableTaggingDetails.setWidthPercentage(100.0f);
				document.add(tableTaggingDetails);
			}

			// if (!emptyIbu)
			// {
			// document.setPageSize(PageSize.A4.rotate());
			// document.setMargins(20f, 20f, 20f, 20f);
			// document.newPage();
			//
			// sectionTitle = new Paragraph("BUTIRAN PENGELUARAN KAYU DI BALAI
			// PEMERIKSA HUTAN", normal10);
			//
			// document.add(sectionTitle);
			//
			// String header2TaggedTreeList =
			// tagging.getForestName().toUpperCase() + " KPT " +
			// tagging.getComptBlockNo() + " - POKOK IBU";
			// Paragraph sectionTitleLine2 = new
			// Paragraph(header2TaggedTreeList, normal10);
			//
			// sectionTitleLine2.setSpacingAfter(5);
			// document.add(sectionTitleLine2);
			//
			// PdfPTable tableTaggingDetails = assembleTreeTaggingDetails(1, 2);
			//
			// tableTaggingDetails.setWidthPercentage(100.0f);
			// document.add(tableTaggingDetails);
			// }
			//
			// if (!emptyPerlindungan)
			// {
			// document.setPageSize(PageSize.A4.rotate());
			// document.setMargins(20f, 20f, 20f, 20f);
			// document.newPage();
			//
			// sectionTitle = new Paragraph("BUTIRAN PENGELUARAN KAYU DI BALAI
			// PEMERIKSA HUTAN", normal10);
			//
			// document.add(sectionTitle);
			//
			// String header2TaggedTreeList =
			// tagging.getForestName().toUpperCase() + " KPT " +
			// tagging.getComptBlockNo() + " - POKOK PERLINDUNGAN";
			// Paragraph sectionTitleLine2 = new
			// Paragraph(header2TaggedTreeList, normal10);
			//
			// sectionTitleLine2.setSpacingAfter(5);
			// document.add(sectionTitleLine2);
			//
			// PdfPTable tableTaggingDetails = assembleTreeTaggingDetails(1, 3);
			//
			// tableTaggingDetails.setWidthPercentage(100.0f);
			// document.add(tableTaggingDetails);
			// }

			if (!emptyTebangan)
			{
				PdfPTable tableTaggingSummary = assembleL4TreeTaggingSummary(1,
						true, false);
				sectionTitle = new Paragraph(
						"JADUAL RINGKASAN BILANGAN POKOK DAN ANGGARAN ISIPADU POKOK TEBANGAN BAGI KOMPT. "
								+ tagging.getComptBlockNo().toUpperCase()
								+ ", HS "
								+ tagging.getForestName().toUpperCase()
								+ ", RENJ "
								+ tagging.getRangeName().toUpperCase() + "\n ",
						bold10);
				sectionTitle.setAlignment(Element.ALIGN_CENTER);
				sectionTitle.setSpacingAfter(5);

				if (tableTaggingSummary != null)
				{
					document.setPageSize(PageSize.A4.rotate());
					document.setMargins(20f, 20f, 20f, 20f);
					document.newPage();
					document.add(sectionTitle);

					tableTaggingSummary.setWidthPercentage(100.0f);
					document.add(tableTaggingSummary);
				}

				tableTaggingSummary = assembleL4TreeTaggingSummary(1, false,
						false);

				if (tableTaggingSummary != null)
				{
					document.setPageSize(PageSize.A4.rotate());
					document.setMargins(20f, 20f, 20f, 20f);
					document.newPage();
					document.add(sectionTitle);

					tableTaggingSummary.setWidthPercentage(100.0f);
					document.add(tableTaggingSummary);
				}

				tableTaggingSummary = assembleL4TreeTaggingSummary(1, true,
						true);

				if (tableTaggingSummary != null)
				{
					document.setPageSize(PageSize.A4.rotate());
					document.setMargins(20f, 20f, 20f, 20f);
					document.newPage();
					document.add(sectionTitle);

					tableTaggingSummary.setWidthPercentage(100.0f);
					document.add(tableTaggingSummary);
				}
			}

			if (!emptyIbu)
			{
				PdfPTable tableTaggingSummary = assembleL4TreeTaggingSummary(2,
						true, false);
				sectionTitle = new Paragraph(
						"JADUAL RINGKASAN BILANGAN POKOK IBU BAGI KOMPT. "
								+ tagging.getComptBlockNo().toUpperCase()
								+ ", HS "
								+ tagging.getForestName().toUpperCase()
								+ ", RENJ "
								+ tagging.getRangeName().toUpperCase() + "\n ",
						bold10);
				sectionTitle.setAlignment(Element.ALIGN_CENTER);
				sectionTitle.setSpacingAfter(5);

				document.setPageSize(PageSize.A4.rotate());
				document.setMargins(20f, 20f, 20f, 20f);
				document.newPage();
				document.add(sectionTitle);

				tableTaggingSummary.setWidthPercentage(100.0f);
				document.add(tableTaggingSummary);
			}

			if (!emptyPerlindungan)
			{
				PdfPTable tableTaggingSummary = assembleL4TreeTaggingSummary(3,
						true, false);
				sectionTitle = new Paragraph(
						"JADUAL RINGKASAN BILANGAN POKOK PERLINDUNGAN BAGI KOMPT. "
								+ tagging.getComptBlockNo().toUpperCase()
								+ ", HS "
								+ tagging.getForestName().toUpperCase()
								+ ", RENJ "
								+ tagging.getRangeName().toUpperCase() + "\n ",
						bold10);
				sectionTitle.setAlignment(Element.ALIGN_CENTER);
				sectionTitle.setSpacingAfter(5);

				document.setPageSize(PageSize.A4.rotate());
				document.setMargins(20f, 20f, 20f, 20f);
				document.newPage();
				document.add(sectionTitle);

				tableTaggingSummary.setWidthPercentage(100.0f);
				document.add(tableTaggingSummary);
			}

			document.setPageSize(PageSize.A4);
			document.setMargins(65f, 65f, 60f, 60f);
			document.newPage();
		}

		if (!emptyJalan)
		{
			Paragraph reportTitle = new Paragraph(
					"BUKU PENANDAAN POKOK DAN KAWALAN PENGELUARAN KAYU",
					bold10);
			reportTitle.setAlignment(Paragraph.ALIGN_CENTER);
			document.add(reportTitle);

			reportTitle = new Paragraph("(POKOK JALAN)", normal8);
			reportTitle.setAlignment(Paragraph.ALIGN_CENTER);
			reportTitle.setSpacingAfter(15);
			document.add(reportTitle);

			Paragraph sectionTitle = new Paragraph("A. MAKLUMAT ASAS", bold10);
			document.add(sectionTitle);
			document.add(new Paragraph("\n", bold10));

			PdfPTable taggingAreaDetails = assembleTaggingGeneralDetails(1);

			taggingAreaDetails.setWidthPercentage(100.0f);
			document.add(taggingAreaDetails);
			document.add(new Paragraph("\n", normal8));

			PdfPTable cuttingLimitDetails = assembleCuttingLimitDetails();

			cuttingLimitDetails.setWidthPercentage(100.0f);
			document.add(cuttingLimitDetails);
			document.add(new Paragraph("\n", normal8));

			PdfPTable taggingSeries = assembleTaggingSeries(2);

			taggingSeries.setWidthPercentage(100.0f);
			document.add(taggingSeries);
			document.add(new Paragraph("\n", normal8));

			PdfPTable totalTagDetails = assembleTreeTagSummary(2, 0);

			totalTagDetails.setWidthPercentage(100.0f);
			document.add(totalTagDetails);
			document.add(new Paragraph("\n", normal8));

			PdfPTable loggerDetails = assembleLoggerDetails();

			loggerDetails.setWidthPercentage(100.0f);
			document.add(loggerDetails);
			document.add(new Paragraph("\n", normal8));

			PdfPTable licenseDetails = assembleLicenseDetails();

			licenseDetails.setWidthPercentage(100.0f);
			document.add(licenseDetails);
			document.add(new Paragraph("\n", normal8));

			PdfPTable hallDetails = assembleHallDetails();

			hallDetails.setWidthPercentage(100.0f);
			document.add(hallDetails);
			document.add(new Paragraph("\n", normal8));

			document.setPageSize(PageSize.A4.rotate());
			document.setMargins(20f, 20f, 20f, 20f);
			document.newPage();

			sectionTitle = new Paragraph(
					"BUTIRAN PENGELUARAN KAYU DI BALAI PEMERIKSA HUTAN",
					normal10);

			document.add(sectionTitle);

			String header2TaggedTreeList = tagging.getForestName().toUpperCase()
					+ " KPT " + tagging.getComptBlockNo() + " - POKOK JALAN";
			Paragraph sectionTitleLine2 = new Paragraph(header2TaggedTreeList,
					normal10);

			sectionTitleLine2.setSpacingAfter(5);
			document.add(sectionTitleLine2);

			PdfPTable tableTaggingDetails = assembleTreeTaggingDetails(2, 0);

			tableTaggingDetails.setWidthPercentage(100.0f);
			document.add(tableTaggingDetails);

			document.setPageSize(PageSize.A4.rotate());
			document.setMargins(20f, 20f, 20f, 20f);
			document.newPage();
			sectionTitle = new Paragraph(
					"RINGKASAN BILANGAN DAN TAKSIRAN ISIPADU\nPOKOK JALAN",
					bold10);

			sectionTitle.setAlignment(Element.ALIGN_CENTER);
			sectionTitle.setSpacingAfter(5);
			document.add(sectionTitle);

			PdfPTable tableTaggingSummary = assembleTreeTaggingSummary(2);

			tableTaggingSummary.setWidthPercentage(100.0f);
			document.add(tableTaggingSummary);

			document.setPageSize(PageSize.A4);
			document.setMargins(65f, 65f, 60f, 60f);
			document.newPage();
		}

		if (!emptyJalan2)
		{
			Paragraph reportTitle = new Paragraph(
					"BUKU PENANDAAN POKOK DAN KAWALAN PENGELUARAN KAYU",
					bold10);
			reportTitle.setAlignment(Paragraph.ALIGN_CENTER);
			document.add(reportTitle);

			reportTitle = new Paragraph("(POKOK JALAN SEKUNDER)", normal8);
			reportTitle.setAlignment(Paragraph.ALIGN_CENTER);
			reportTitle.setSpacingAfter(15);
			document.add(reportTitle);

			Paragraph sectionTitle = new Paragraph("A. MAKLUMAT ASAS", bold10);
			document.add(sectionTitle);
			document.add(new Paragraph("\n", bold10));

			PdfPTable taggingAreaDetails = assembleTaggingGeneralDetails(1);

			taggingAreaDetails.setWidthPercentage(100.0f);
			document.add(taggingAreaDetails);
			document.add(new Paragraph("\n", normal8));

			PdfPTable cuttingLimitDetails = assembleCuttingLimitDetails();

			cuttingLimitDetails.setWidthPercentage(100.0f);
			document.add(cuttingLimitDetails);
			document.add(new Paragraph("\n", normal8));

			PdfPTable taggingSeries = assembleTaggingSeries(3);

			taggingSeries.setWidthPercentage(100.0f);
			document.add(taggingSeries);
			document.add(new Paragraph("\n", normal8));

			PdfPTable totalTagDetails = assembleTreeTagSummary(3, 0);

			totalTagDetails.setWidthPercentage(100.0f);
			document.add(totalTagDetails);
			document.add(new Paragraph("\n", normal8));

			PdfPTable loggerDetails = assembleLoggerDetails();

			loggerDetails.setWidthPercentage(100.0f);
			document.add(loggerDetails);
			document.add(new Paragraph("\n", normal8));

			PdfPTable licenseDetails = assembleLicenseDetails();

			licenseDetails.setWidthPercentage(100.0f);
			document.add(licenseDetails);
			document.add(new Paragraph("\n", normal8));

			PdfPTable hallDetails = assembleHallDetails();

			hallDetails.setWidthPercentage(100.0f);
			document.add(hallDetails);
			document.add(new Paragraph("\n", normal8));

			document.setPageSize(PageSize.A4.rotate());
			document.setMargins(20f, 20f, 20f, 20f);
			document.newPage();

			sectionTitle = new Paragraph(
					"BUTIRAN PENGELUARAN KAYU DI BALAI PEMERIKSA HUTAN",
					normal10);

			document.add(sectionTitle);

			String header2TaggedTreeList = tagging.getForestName().toUpperCase()
					+ " KPT " + tagging.getComptBlockNo()
					+ " - POKOK JALAN SEKUNDER";
			Paragraph sectionTitleLine2 = new Paragraph(header2TaggedTreeList,
					normal10);

			sectionTitleLine2.setSpacingAfter(5);
			document.add(sectionTitleLine2);

			PdfPTable tableTaggingDetails = assembleTreeTaggingDetails(3, 0);

			tableTaggingDetails.setWidthPercentage(100.0f);
			document.add(tableTaggingDetails);

			document.setPageSize(PageSize.A4.rotate());
			document.setMargins(20f, 20f, 20f, 20f);
			document.newPage();
			sectionTitle = new Paragraph(
					"RINGKASAN BILANGAN DAN TAKSIRAN ISIPADU\nPOKOK JALAN SEKUNDER",
					bold10);

			sectionTitle.setAlignment(Element.ALIGN_CENTER);
			sectionTitle.setSpacingAfter(5);
			document.add(sectionTitle);

			PdfPTable tableTaggingSummary = assembleTreeTaggingSummary(3);

			tableTaggingSummary.setWidthPercentage(100.0f);
			document.add(tableTaggingSummary);

			document.setPageSize(PageSize.A4);
			document.setMargins(65f, 65f, 60f, 60f);
			document.newPage();
		}

		if (!emptyMatau)
		{
			Paragraph reportTitle = new Paragraph(
					"BUKU PENANDAAN POKOK DAN KAWALAN PENGELUARAN KAYU",
					bold10);
			reportTitle.setAlignment(Paragraph.ALIGN_CENTER);
			document.add(reportTitle);

			reportTitle = new Paragraph("(POKOK MATAU)", normal8);
			reportTitle.setAlignment(Paragraph.ALIGN_CENTER);
			reportTitle.setSpacingAfter(15);
			document.add(reportTitle);

			Paragraph sectionTitle = new Paragraph("A. MAKLUMAT ASAS", bold10);
			document.add(sectionTitle);
			document.add(new Paragraph("\n", bold10));

			PdfPTable taggingAreaDetails = assembleTaggingGeneralDetails(1);

			taggingAreaDetails.setWidthPercentage(100.0f);
			document.add(taggingAreaDetails);
			document.add(new Paragraph("\n", normal8));

			PdfPTable cuttingLimitDetails = assembleCuttingLimitDetails();

			cuttingLimitDetails.setWidthPercentage(100.0f);
			document.add(cuttingLimitDetails);
			document.add(new Paragraph("\n", normal8));

			PdfPTable taggingSeries = assembleTaggingSeries(4);

			taggingSeries.setWidthPercentage(100.0f);
			document.add(taggingSeries);
			document.add(new Paragraph("\n", normal8));

			PdfPTable totalTagDetails = assembleTreeTagSummary(4, 0);

			totalTagDetails.setWidthPercentage(100.0f);
			document.add(totalTagDetails);
			document.add(new Paragraph("\n", normal8));

			PdfPTable loggerDetails = assembleLoggerDetails();

			loggerDetails.setWidthPercentage(100.0f);
			document.add(loggerDetails);
			document.add(new Paragraph("\n", normal8));

			PdfPTable licenseDetails = assembleLicenseDetails();

			licenseDetails.setWidthPercentage(100.0f);
			document.add(licenseDetails);
			document.add(new Paragraph("\n", normal8));

			PdfPTable hallDetails = assembleHallDetails();

			hallDetails.setWidthPercentage(100.0f);
			document.add(hallDetails);
			document.add(new Paragraph("\n", normal8));

			document.setPageSize(PageSize.A4.rotate());
			document.setMargins(20f, 20f, 20f, 20f);
			document.newPage();

			sectionTitle = new Paragraph(
					"BUTIRAN PENGELUARAN KAYU DI BALAI PEMERIKSA HUTAN",
					normal10);

			document.add(sectionTitle);

			String header2TaggedTreeList = tagging.getForestName().toUpperCase()
					+ " KPT " + tagging.getComptBlockNo() + " - POKOK MATAU";
			Paragraph sectionTitleLine2 = new Paragraph(header2TaggedTreeList,
					normal10);

			sectionTitleLine2.setSpacingAfter(5);
			document.add(sectionTitleLine2);

			PdfPTable tableTaggingDetails = assembleTreeTaggingDetails(4, 0);

			tableTaggingDetails.setWidthPercentage(100.0f);
			document.add(tableTaggingDetails);

			document.setPageSize(PageSize.A4.rotate());
			document.setMargins(20f, 20f, 20f, 20f);
			document.newPage();
			sectionTitle = new Paragraph(
					"RINGKASAN BILANGAN DAN TAKSIRAN ISIPADU\nPOKOK JALAN SEKUNDER",
					bold10);

			sectionTitle.setAlignment(Element.ALIGN_CENTER);
			sectionTitle.setSpacingAfter(5);
			document.add(sectionTitle);

			PdfPTable tableTaggingSummary = assembleTreeTaggingSummary(4);

			tableTaggingSummary.setWidthPercentage(100.0f);
			document.add(tableTaggingSummary);

			document.setPageSize(PageSize.A4);
			document.setMargins(65f, 65f, 60f, 60f);
			document.newPage();
		}

		if (!emptyJalan2)
		{
			Paragraph reportTitle = new Paragraph(
					"BUKU PENANDAAN POKOK DAN KAWALAN PENGELUARAN KAYU",
					bold10);
			reportTitle.setAlignment(Paragraph.ALIGN_CENTER);
			document.add(reportTitle);

			reportTitle = new Paragraph("(POKOK KONGSI)", normal8);
			reportTitle.setAlignment(Paragraph.ALIGN_CENTER);
			reportTitle.setSpacingAfter(15);
			document.add(reportTitle);

			Paragraph sectionTitle = new Paragraph("A. MAKLUMAT ASAS", bold10);
			document.add(sectionTitle);
			document.add(new Paragraph("\n", bold10));

			PdfPTable taggingAreaDetails = assembleTaggingGeneralDetails(1);

			taggingAreaDetails.setWidthPercentage(100.0f);
			document.add(taggingAreaDetails);
			document.add(new Paragraph("\n", normal8));

			PdfPTable cuttingLimitDetails = assembleCuttingLimitDetails();

			cuttingLimitDetails.setWidthPercentage(100.0f);
			document.add(cuttingLimitDetails);
			document.add(new Paragraph("\n", normal8));

			PdfPTable taggingSeries = assembleTaggingSeries(5);

			taggingSeries.setWidthPercentage(100.0f);
			document.add(taggingSeries);
			document.add(new Paragraph("\n", normal8));

			PdfPTable totalTagDetails = assembleTreeTagSummary(5, 0);

			totalTagDetails.setWidthPercentage(100.0f);
			document.add(totalTagDetails);
			document.add(new Paragraph("\n", normal8));

			PdfPTable loggerDetails = assembleLoggerDetails();

			loggerDetails.setWidthPercentage(100.0f);
			document.add(loggerDetails);
			document.add(new Paragraph("\n", normal8));

			PdfPTable licenseDetails = assembleLicenseDetails();

			licenseDetails.setWidthPercentage(100.0f);
			document.add(licenseDetails);
			document.add(new Paragraph("\n", normal8));

			PdfPTable hallDetails = assembleHallDetails();

			hallDetails.setWidthPercentage(100.0f);
			document.add(hallDetails);
			document.add(new Paragraph("\n", normal8));

			document.setPageSize(PageSize.A4.rotate());
			document.setMargins(20f, 20f, 20f, 20f);
			document.newPage();

			sectionTitle = new Paragraph(
					"BUTIRAN PENGELUARAN KAYU DI BALAI PEMERIKSA HUTAN",
					normal10);

			document.add(sectionTitle);

			String header2TaggedTreeList = tagging.getForestName().toUpperCase()
					+ " KPT " + tagging.getComptBlockNo() + " - POKOK KONGSI";
			Paragraph sectionTitleLine2 = new Paragraph(header2TaggedTreeList,
					normal10);

			sectionTitleLine2.setSpacingAfter(5);
			document.add(sectionTitleLine2);

			PdfPTable tableTaggingDetails = assembleTreeTaggingDetails(5, 0);

			tableTaggingDetails.setWidthPercentage(100.0f);
			document.add(tableTaggingDetails);

			document.setPageSize(PageSize.A4.rotate());
			document.setMargins(20f, 20f, 20f, 20f);
			document.newPage();
			sectionTitle = new Paragraph(
					"RINGKASAN BILANGAN DAN TAKSIRAN ISIPADU\nPOKOK KONGSI",
					bold10);

			sectionTitle.setAlignment(Element.ALIGN_CENTER);
			sectionTitle.setSpacingAfter(5);
			document.add(sectionTitle);

			PdfPTable tableTaggingSummary = assembleTreeTaggingSummary(5);

			tableTaggingSummary.setWidthPercentage(100.0f);
			document.add(tableTaggingSummary);

			document.setPageSize(PageSize.A4);
			document.setMargins(65f, 65f, 60f, 60f);
			document.newPage();
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

	private PdfPTable assembleTaggingGeneralDetails(int flag) throws Exception
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
			PdfPCell cell = getCell(new Phrase(cellTitle[index], bold10));

			if (flag == 2)
				cell.setBorder(Rectangle.NO_BORDER);

			cell.setVerticalAlignment(Element.ALIGN_TOP);
			taggingAreaDetails.addCell(cell);

			cell = getCell(new Phrase(cellData.get(index), bold10));

			if (flag == 2)
				cell.setBorder(Rectangle.NO_BORDER);

			cell.setVerticalAlignment(Element.ALIGN_TOP);
			taggingAreaDetails.addCell(cell);
		}

		return taggingAreaDetails;
	}

	private PdfPTable assembleCuttingLimitDetails() throws Exception
	{
		PdfPTable cuttingLimitDetails = new PdfPTable(4);
		Phrase nos = new Phrase("", normal10);
		Phrase names = new Phrase("", normal10);
		Phrase limits = new Phrase("", normal10);
		int number = 3;

		cuttingLimitDetails.getDefaultCell().setBorder(0);
		cuttingLimitDetails.setWidths(new float[] {10f, 5f, 35f, 50f});

		PdfPCell cell = getCell(new Phrase("HAD TEBANGAN", bold10));

		cell.setColspan(3);
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cuttingLimitDetails.addCell(cell);

		cell = getCell(new Phrase("", bold10));

		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cuttingLimitDetails.addCell(cell);

		nos.add(new Chunk("(i)   \n(ii)   \n(iii)   ", normal10));
		names.add(
				new Chunk("Dipterokarp\nBukan Dipterokarp\nChengal", normal10));
		limits.add(new Chunk("" + tagging.getDipterocarpLimit(), bold10));
		limits.add(new Chunk(" cm ppd", normal10));
		limits.add(new Chunk("\n" + tagging.getNonDipterocarpLimit(), bold10));
		limits.add(new Chunk(" cm ppd", normal10));
		limits.add(new Chunk(
				"\n" + (treeLimit == null ? tagging.getDipterocarpLimit()
						: treeLimit.getChengalLimit()),
				bold10));
		limits.add(new Chunk(" cm ppd", normal10));

		ArrayList<CuttingLimit> cuttingLimits = tagging.getCuttingLimits();

		for (CuttingLimit cuttingLimit : cuttingLimits)
		{
			nos.add(new Chunk(
					"\n(" + RomanNumberConverter.toRoman(++number).toLowerCase()
							+ ")   ",
					normal10));
			names.add(
					new Chunk("\n" + cuttingLimit.getSpeciesName(), normal10));
			limits.add(new Chunk("\n" + cuttingLimit.getMinDiameter(), bold10));
			limits.add(new Chunk(" cm ppd", normal10));
		}

		cell = getCell(nos);

		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorder(Rectangle.BOTTOM | Rectangle.LEFT);
		cuttingLimitDetails.addCell(cell);

		cell = new PdfPCell(new Phrase("", normal10));

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

	private PdfPTable assembleTaggingHammers() throws Exception
	{
		PdfPTable taggingAreaDetails = new PdfPTable(2);
		PdfPCell cell = getCell(new Phrase("NO. TUKUL TANDA JABATAN", bold10));

		taggingAreaDetails.addCell(cell);

		String tukulNo = tagging.getHammers().toString();
		cell = getCell(
				new Phrase(tukulNo.substring(1, tukulNo.length() - 1), bold10));

		taggingAreaDetails.addCell(cell);

		return taggingAreaDetails;
	}

	private PdfPTable assembleTaggingSeries(int taggingTypeID) throws Exception
	{
		PdfPTable taggingAreaDetails = assembleTaggingHammers();
		ArrayList<TaggingForm> taggingForms = tagging.getTaggingForms();

		if (taggingTypeID == 1)
		{
			TreeSet<String> tebangan = new TreeSet<>(), ibu = new TreeSet<>(),
					perlindungan = new TreeSet<>();
			PdfPCell cell = getCell(
					new Phrase("NO. SIRI TAG TANDA POKOK TEBANGAN", bold10));

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

			cell = getCell(new Phrase(summarize(tebangan), normal10));

			cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
			taggingAreaDetails.addCell(cell);

			cell = getCell(new Phrase("NO. SIRI TAG TANDA POKOK IBU", bold10));

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

			cell = getCell(new Phrase(summarize(ibu), normal10));

			cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
			taggingAreaDetails.addCell(cell);

			cell = getCell(new Phrase("NO. SIRI TAG TANDA POKOK PERLINDUNGAN",
					bold10));

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

			cell = getCell(new Phrase(summarize(perlindungan), normal10));

			cell.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT | Rectangle.LEFT);
			taggingAreaDetails.addCell(cell);
		}
		else if (taggingTypeID == 2)
		{
			TreeSet<String> tebangan = new TreeSet<>();
			PdfPCell cell = getCell(
					new Phrase("NO. SIRI TAG TANDA POKOK", bold10));

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

			cell = getCell(new Phrase(summarize(tebangan), normal10));

			taggingAreaDetails.addCell(cell);

			return taggingAreaDetails;
		}

		return taggingAreaDetails;
	}

	private PdfPTable assembleTreeTagSummary(int taggingTypeID, int treeTypeID)
			throws Exception
	{
		PdfPTable totalTagDetails = new PdfPTable(6);

		totalTagDetails.setWidths(
				new float[] {5f, 28.32f, 16.67f, 16.67f, 16.67f, 16.67f});

		PdfPCell cell = getCell(new Phrase("JUMLAH POKOK DITANDA:", bold10));

		cell.setColspan(6);
		totalTagDetails.addCell(cell);

		String header3[] = {"Kumpulan Spesis Pokok", "Bil. Pokok",
				"Bil. Pokok/ha", "Isipadu Balak (m\u00B3)",
				"Isipadu Balak (m\u00B3)/ha"};

		for (int index = 0; index < header3.length; index++)
		{
			cell = getCell(new Phrase(header3[index], normal10));

			if (index == 0)
				cell.setColspan(2);

			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			totalTagDetails.addCell(cell);
		}

		cell = getCell(new Phrase("(i)", normal10));

		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
		totalTagDetails.addCell(cell);

		cell = getCell(new Phrase("Dipterokarp", normal10));

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

		cell = getCell(new Phrase(out1, bold10));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalTagDetails.addCell(cell);
		cell = getCell(new Phrase(out2, bold10));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalTagDetails.addCell(cell);
		cell = getCell(new Phrase(out3, bold10));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalTagDetails.addCell(cell);
		cell = getCell(new Phrase(out4, bold10));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalTagDetails.addCell(cell);

		cell = getCell(new Phrase("(ii)", normal10));

		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
		totalTagDetails.addCell(cell);

		cell = getCell(new Phrase("Bukan Dipterokarp", normal10));

		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
		totalTagDetails.addCell(cell);

		out1 = totalTreeNonDip == 0 ? "-" : Integer.toString(totalTreeNonDip);
		out2 = totalHectreNonDip == 0 ? "-" : df.format(totalHectreNonDip);
		out3 = totalVolumeNonDip == 0 ? "-" : df.format(totalVolumeNonDip);
		out4 = volumeHectreNonDip == 0 ? "-" : df.format(volumeHectreNonDip);
		int number = 4;

		cell = getCell(new Phrase(out1, bold10));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalTagDetails.addCell(cell);
		cell = getCell(new Phrase(out2, bold10));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalTagDetails.addCell(cell);
		cell = getCell(new Phrase(out3, bold10));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalTagDetails.addCell(cell);
		cell = getCell(new Phrase(out4, bold10));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalTagDetails.addCell(cell);

		cell = getCell(new Phrase("(iii)", normal10));

		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
		totalTagDetails.addCell(cell);

		cell = getCell(new Phrase("Chengal", normal10));

		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
		totalTagDetails.addCell(cell);

		out1 = totalTreeChengal == 0 ? "-" : Integer.toString(totalTreeChengal);
		out2 = totalHectreChengal == 0 ? "-" : df.format(totalHectreChengal);
		out3 = totalVolumeChengal == 0 ? "-" : df.format(totalVolumeChengal);
		out4 = volumeHectreChengal == 0 ? "-" : df.format(volumeHectreChengal);

		cell = getCell(new Phrase(out1, bold10));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalTagDetails.addCell(cell);
		cell = getCell(new Phrase(out2, bold10));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalTagDetails.addCell(cell);
		cell = getCell(new Phrase(out3, bold10));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalTagDetails.addCell(cell);
		cell = getCell(new Phrase(out4, bold10));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalTagDetails.addCell(cell);

		for (CuttingLimit cuttingLimit : cuttingLimits)
		{
			int totalTaggedTree = 0, totalTaggedLog = 0;
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
							totalTaggedLog += taggingRecord.getEstimation();
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
				totalTualOtherDipTree += totalTaggedLog;
				totalTaggedOtherDipTreePerHectre += totalTaggedTreePerHectre;
				totalVolumeTaggedOtherDipTree += totalVolumeTaggedTree;
				totalVolumeTaggedOtherDipTreePerHectre += totalVolumeTaggedTreePerHectre;
			}
			else
			{
				totalTaggedOtherNonDipTree += totalTaggedTree;
				totalTualOtherNonDipTree += totalTaggedLog;
				totalTaggedOtherNonDipTreePerHectre += totalTaggedTreePerHectre;
				totalVolumeTaggedOtherNonDipTree += totalVolumeTaggedTree;
				totalVolumeTaggedOtherNonDipTreePerHectre += totalVolumeTaggedTreePerHectre;
			}

			cell = getCell(new Phrase(
					"(" + RomanNumberConverter.toRoman(number++).toLowerCase()
							+ ")",
					normal10));

			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			totalTagDetails.addCell(cell);

			cell = getCell(new Phrase(cuttingLimit.getSpeciesName(), normal10));

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

			cell = getCell(new Phrase(out1, bold10));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			totalTagDetails.addCell(cell);
			cell = getCell(new Phrase(out2, bold10));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			totalTagDetails.addCell(cell);
			cell = getCell(new Phrase(out3, bold10));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			totalTagDetails.addCell(cell);
			cell = getCell(new Phrase(out4, bold10));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			totalTagDetails.addCell(cell);
		}

		cell = getCell(new Phrase("Jumlah", bold10));

		cell.setColspan(2);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalTagDetails.addCell(cell);

		totalCumTree = totalTreeDip + totalTreeNonDip + totalTreeChengal
				+ totalTaggedOtherDipTree + totalTaggedOtherNonDipTree;
		cell = getCell(new Phrase(Integer.toString(totalCumTree), bold10));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalTagDetails.addCell(cell);

		totalCumTaggedTreePerHecter = totalHectreDip + totalHectreNonDip
				+ totalHectreChengal + totalTaggedOtherDipTreePerHectre
				+ totalTaggedOtherNonDipTreePerHectre;
		cell = getCell(
				new Phrase(df.format(totalCumTaggedTreePerHecter), bold10));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalTagDetails.addCell(cell);

		totalCumTaggedTreeVolume = totalVolumeDip + totalVolumeNonDip
				+ totalVolumeChengal + totalVolumeTaggedOtherDipTree
				+ totalVolumeTaggedOtherNonDipTree;
		cell = getCell(new Phrase(df.format(totalCumTaggedTreeVolume), bold10));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalTagDetails.addCell(cell);

		totalCumVolumeTaggedTreePerHectre = volumeHectreDip + volumeHectreNonDip
				+ volumeHectreChengal + totalVolumeTaggedOtherDipTreePerHectre
				+ totalVolumeTaggedOtherNonDipTreePerHectre;
		cell = getCell(new Phrase(df.format(totalCumVolumeTaggedTreePerHectre),
				bold10));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalTagDetails.addCell(cell);

		cell = getCell(new Phrase(" ", bold10));

		cell.setColspan(6);
		totalTagDetails.addCell(cell);

		cell = getCell(new Phrase("LESEN PENGUSAHASILAN HUTAN", bold10));

		cell.setColspan(3);
		totalTagDetails.addCell(cell);

		cell = getCell(new Phrase("", bold10));

		cell.setColspan(3);
		totalTagDetails.addCell(cell);

		cell = getCell(new Phrase("(i)", normal10));
		cell.setBorder(Rectangle.TOP | Rectangle.LEFT);
		totalTagDetails.addCell(cell);
		cell = getCell(new Phrase("No. Lesen", normal10));
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT);
		cell.setColspan(2);
		totalTagDetails.addCell(cell);
		cell = getCell(new Phrase("(HS)", normal10));
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT);
		cell.setColspan(3);
		totalTagDetails.addCell(cell);

		cell = getCell(new Phrase("(ii)", normal10));
		cell.setBorder(Rectangle.BOTTOM | Rectangle.LEFT);
		totalTagDetails.addCell(cell);
		cell = getCell(new Phrase("Luas (ha)", normal10));
		cell.setColspan(2);
		cell.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT);
		totalTagDetails.addCell(cell);
		cell = getCell(new Phrase(tagging.getArea() + "", bold10));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.BOTTOM | Rectangle.LEFT);
		totalTagDetails.addCell(cell);
		cell = getCell(new Phrase("ha", bold10));
		cell.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT);
		cell.setColspan(2);
		totalTagDetails.addCell(cell);

		return totalTagDetails;
	}

	private PdfPTable assembleLoggerDetails() throws Exception
	{
		PdfPTable loggerTable = new PdfPTable(2);
		PdfPCell cell = getCell(
				new Phrase("NAMA DAN ALAMAT PEMEGANG LESEN", bold10));

		loggerTable.addCell(cell);

		Phrase addressLicenseHolder = new Phrase("-", normal10);
		cell = getCell(addressLicenseHolder);

		loggerTable.addCell(cell);

		cell = getCell(
				new Phrase("NAMA DAN ALAMAT KONTRAKTOR PEMBALAKAN", bold10));
		loggerTable.addCell(cell);

		Phrase addressContractor = new Phrase("-", normal10);
		cell = getCell(addressContractor);

		loggerTable.addCell(cell);

		return loggerTable;
	}

	private PdfPTable assembleLicenseDetails() throws Exception
	{
		PdfPTable licenseDetails = new PdfPTable(3);

		licenseDetails.setWidths(new float[] {50f, 25f, 25f});

		PdfPCell cell = getCell(new Phrase("TEMPOH KUAT KUASA LESEN", bold10));
		licenseDetails.addCell(cell);
		cell = getCell(new Phrase("Dari", normal10));
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
		licenseDetails.addCell(cell);
		cell = getCell(new Phrase("hingga", normal10));
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
		licenseDetails.addCell(cell);

		cell = getCell(
				new Phrase("PEMBAHARUAN TEMPOH KUAT KUASA LESEN", bold10));
		cell.setRowspan(2);
		licenseDetails.addCell(cell);
		cell = getCell(new Phrase("Dari", normal10));
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
		licenseDetails.addCell(cell);
		cell = getCell(new Phrase("hingga", normal10));
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
		licenseDetails.addCell(cell);
		cell = getCell(new Phrase("Dari", normal10));
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
		licenseDetails.addCell(cell);
		cell = getCell(new Phrase("hingga", normal10));
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
		licenseDetails.addCell(cell);

		return licenseDetails;
	}

	private PdfPTable assembleHallDetails() throws Exception
	{
		PdfPTable tableB = new PdfPTable(2);
		PdfPCell cell = getCell(new Phrase("BALAI PEMERIKSA HUTAN", bold10));

		tableB.addCell(cell);

		cell = getCell(new Phrase("BPH " + tagging.getHallName().toUpperCase(),
				bold10));
		tableB.addCell(cell);

		return tableB;
	}

	private PdfPTable assembleLogDetailsByTaggingType(int taggingTypeID,
			int treeTypeID) throws Exception
	{

		PdfPTable tableB = new PdfPTable(4);
		PdfPCell cell = null;

		tableB.setTotalWidth(new float[] {200.0f, 100.0f, 100.0f, 100.0f});
		tableB.setLockedWidth(true);
		String tableHeaders[] = {"", "Batang", "Bilangan Tual", "Isipadu"};

		for (String header : tableHeaders)
		{
			cell = new PdfPCell(new Phrase(header, bold10));

			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(Rectangle.NO_BORDER);
			tableB.addCell(cell);
		}

		cell = new PdfPCell(new Phrase("1. Dipterokarp", normal10));
		cell.setBorder(Rectangle.NO_BORDER);
		tableB.addCell(cell);

		int totalTreeDip = 0, totalTreeNonDip = 0, totalTreeChengal = 0,
				totalLogDip = 0, totalLogNonDip = 0, totalLogChengal = 0;
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
								totalLogDip += taggingRecord.getEstimation();
								totalVolumeDip += volume;
							}
							else
							{
								totalTreeChengal++;
								totalLogChengal += taggingRecord
										.getEstimation();
								totalVolumeChengal += volume;
							}
						}
						else
						{
							totalTreeNonDip++;
							totalLogNonDip += taggingRecord.getEstimation();
							totalVolumeNonDip += volume;
						}
					}
				}
			}
		}

		double volumeHectreDip = totalVolumeDip / tagging.getArea(),
				volumeHectreNonDip = totalVolumeNonDip / tagging.getArea(),
				volumeHectreChengal = totalVolumeChengal / tagging.getArea();

		cell = new PdfPCell(new Phrase(
				(totalTreeDip + totalTaggedOtherDipTree) + " pokok", normal10));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorder(Rectangle.NO_BORDER);
		tableB.addCell(cell);

		int totalTualDip = totalLogDip + totalTualOtherDipTree;
		cell = new PdfPCell(
				new Phrase(Integer.toString(totalTualDip), normal10));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorder(Rectangle.NO_BORDER);
		tableB.addCell(cell);

		double totalVolumeDipAll = volumeHectreDip
				+ totalVolumeTaggedOtherDipTreePerHectre;
		cell = new PdfPCell(new Phrase(
				df.format(totalVolumeDipAll) + " m\u00B3/ha", normal10));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorder(Rectangle.NO_BORDER);
		tableB.addCell(cell);

		// table data non dipterokarp
		cell = new PdfPCell(new Phrase("2. Bukan Dipterokarp", normal10));
		cell.setBorder(Rectangle.NO_BORDER);
		tableB.addCell(cell);

		cell = new PdfPCell(new Phrase(
				(totalTreeNonDip + totalTaggedOtherNonDipTree) + " pokok",
				normal10));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tableB.addCell(cell);

		int totalTualNonDip = totalLogNonDip + totalTualOtherNonDipTree;
		cell = new PdfPCell(
				new Phrase(Integer.toString(totalTualNonDip), normal10));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tableB.addCell(cell);

		double totalVolumeNonDipAll = volumeHectreNonDip
				+ totalVolumeTaggedOtherNonDipTreePerHectre;
		cell = new PdfPCell(new Phrase(
				df.format(totalVolumeNonDipAll) + " m\u00B3/ha", normal10));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorder(Rectangle.NO_BORDER);
		tableB.addCell(cell);

		// chengal
		cell = new PdfPCell(new Phrase("3. Chengal", normal10));
		cell.setBorder(Rectangle.NO_BORDER);
		tableB.addCell(cell);

		cell = new PdfPCell(
				new Phrase((totalTreeChengal) + " pokok", normal10));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tableB.addCell(cell);

		cell = new PdfPCell(
				new Phrase(Integer.toString(totalLogChengal), normal10));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tableB.addCell(cell);

		cell = new PdfPCell(new Phrase(
				df.format(volumeHectreChengal) + " m\u00B3/ha", normal10));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorder(Rectangle.NO_BORDER);
		tableB.addCell(cell);

		cell = new PdfPCell(
				new Phrase("Jumlah Dipterokarp, Bukan Dipterokarp, dan Chengal",
						normal10));
		cell.setBorder(Rectangle.NO_BORDER);
		tableB.addCell(cell);

		cell = new PdfPCell(new Phrase(
				Integer.toString(totalCumTree) + " pokok", normal10));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorder(Rectangle.NO_BORDER);
		tableB.addCell(cell);

		int totalCumTual = totalTualDip + totalTualNonDip;
		cell = new PdfPCell(
				new Phrase(Integer.toString(totalCumTual), normal10));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorder(Rectangle.NO_BORDER);
		tableB.addCell(cell);

		cell = new PdfPCell(new Phrase(
				df.format(totalCumVolumeTaggedTreePerHectre) + " m\u00B3/ha",
				normal10));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorder(Rectangle.NO_BORDER);
		tableB.addCell(cell);

		return tableB;
	}

	private PdfPTable assembleTotalMotherProtectedTrees(int taggingTypeID)
			throws Exception
	{
		PdfPTable tableC = new PdfPTable(4);

		tableC.setTotalWidth(new float[] {200.0f, 100.0f, 100.0f, 100.0f});
		tableC.setLockedWidth(true);

		PdfPCell cell = new PdfPCell(new Phrase("1. Ibu", normal10));

		cell.setBorder(Rectangle.NO_BORDER);
		tableC.addCell(cell);

		int totalMotherTree = 0, totalProtectedTree = 0;
		ArrayList<TaggingForm> taggingForms = tagging.getTaggingForms();

		for (TaggingForm taggingForm : taggingForms)
		{
			if (taggingForm.getTaggingTypeID() == taggingTypeID)
			{
				ArrayList<TaggingRecord> taggingRecords = taggingForm
						.getTaggingRecords();

				for (TaggingRecord taggingRecord : taggingRecords)
				{
					if (taggingRecord.getTreeTypeID() == 2)
						totalMotherTree++;
					else if (taggingRecord.getTreeTypeID() == 3)
						totalProtectedTree++;
				}
			}
		}

		cell = new PdfPCell(new Phrase(totalMotherTree + " pokok", normal10));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorder(Rectangle.NO_BORDER);
		tableC.addCell(cell);
		cell = new PdfPCell(new Phrase(" ", normal10));
		cell.setBorder(Rectangle.NO_BORDER);
		tableC.addCell(cell);
		cell = new PdfPCell(new Phrase(" ", normal10));
		cell.setBorder(Rectangle.NO_BORDER);
		tableC.addCell(cell);

		cell = new PdfPCell(new Phrase("2. Perlindungan", normal10));
		cell.setBorder(Rectangle.NO_BORDER);
		tableC.addCell(cell);

		cell = new PdfPCell(
				new Phrase(totalProtectedTree + " pokok", normal10));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorder(Rectangle.NO_BORDER);
		tableC.addCell(cell);
		cell = new PdfPCell(new Phrase(" ", normal10));
		cell.setBorder(Rectangle.NO_BORDER);
		tableC.addCell(cell);
		cell = new PdfPCell(new Phrase(" ", normal10));
		cell.setBorder(Rectangle.NO_BORDER);
		tableC.addCell(cell);

		cell = new PdfPCell(
				new Phrase("Jumlah Ibu dan Perlindungan", normal10));
		cell.setBorderColorRight(BaseColor.WHITE);
		cell.setBorder(Rectangle.NO_BORDER);
		tableC.addCell(cell);

		totalTaggedTreeC = totalMotherTree + totalProtectedTree;
		cell = new PdfPCell(new Phrase(totalTaggedTreeC + " pokok", normal10));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorderColorLeft(BaseColor.WHITE);
		cell.setBorder(Rectangle.NO_BORDER);
		tableC.addCell(cell);
		cell = new PdfPCell(new Phrase(" ", normal10));
		cell.setBorder(Rectangle.NO_BORDER);
		tableC.addCell(cell);
		cell = new PdfPCell(new Phrase(" ", normal10));
		cell.setBorder(Rectangle.NO_BORDER);
		tableC.addCell(cell);

		return tableC;
	}

	private PdfPTable assembleGrandTotalTaggedTree() throws Exception
	{
		PdfPTable tableD = new PdfPTable(4);

		tableD.setTotalWidth(new float[] {200.0f, 100.0f, 100.0f, 100.0f});
		tableD.setLockedWidth(true);

		PdfPCell cell = new PdfPCell(
				new Phrase("D. JUMLAH POKOK DI TANDA", bold10));

		cell.setBorder(Rectangle.NO_BORDER);
		tableD.addCell(cell);

		int totalTaggedTreeD = totalCumTree + totalTaggedTreeC;
		cell = new PdfPCell(new Phrase(totalTaggedTreeD + " pokok", normal10));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorder(Rectangle.NO_BORDER);
		tableD.addCell(cell);
		cell = new PdfPCell(new Phrase(""));
		cell.setBorder(Rectangle.NO_BORDER);
		tableD.addCell(cell);
		cell = new PdfPCell(new Phrase(""));
		cell.setBorder(Rectangle.NO_BORDER);
		tableD.addCell(cell);

		return tableD;
	}

	private PdfPTable assembleTreeTaggingDetails(int taggingTypeID,
			int treeTypeID) throws Exception
	{
		String tableHeaders[] = {"Bil", "Spesis Pokok", "No Siri", "Dia.",
				"Isipadu Pokok", "Ang. Bil. Tual", "Kualiti Balak Pertama",
				"Kawalan Pengeluaran Kayu", "Jum. Bil. Tual Balak",
				"Jumlah Isipadu", "Catatan"};
		PdfPTable tableDetails = new PdfPTable(25);
		PdfPCell cell = null;

		tableDetails.setTotalWidth(new float[] {30.0f, 100.0f, 50.0f, 30.0f,
				40.0f, 30.0f, 40.0f, 25.0f, 25.0f, 25.0f, 25.0f, 25.0f, 25.0f,
				25.0f, 25.0f, 25.0f, 25.0f, 25.0f, 25.0f, 25.0f, 25.0f, 25.0f,
				30.0f, 40.0f, 40.0f});
		tableDetails.setLockedWidth(true);
		tableDetails.setHeaderRows(4);

		for (int index = 0; index < tableHeaders.length; index++)
		{
			cell = getCell(new Phrase(tableHeaders[index], bold8));

			if (index != 7)
			{
				cell.setRowspan(3);
				cell.disableBorderSide(Rectangle.BOTTOM);
			}
			else
				cell.setColspan(15);

			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableDetails.addCell(cell);
		}

		for (int tualCtr = 1; tualCtr < 6; tualCtr++)
		{
			cell = getCell(new Phrase("Tual Balak " + tualCtr, bold8));

			cell.setColspan(3);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableDetails.addCell(cell);
		}

		String tualHeaderDetails[] = {"Pan.", "Dia.", "Isip."};

		for (int index = 0; index < 5; index++)
		{
			for (int index1 = 0; index1 < tualHeaderDetails.length; index1++)
			{
				cell = getCell(new Phrase(tualHeaderDetails[index1], bold8));

				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.disableBorderSide(Rectangle.BOTTOM);
				tableDetails.addCell(cell);
			}

		}

		String units[] = {"", "", "", "(cm)", "(m\u00B3)", "", "", "(m)",
				"(cm)", "(m\u00B3)", "(m)", "(cm)", "(m\u00B3)", "(m)", "(cm)",
				"(m\u00B3)", "(m)", "(cm)", "(m\u00B3)", "(m)", "(cm)",
				"(m\u00B3)", "", "(m\u00B3)", ""};

		for (int index = 0; index < units.length; index++)
		{
			cell = getCell(new Phrase(units[index], bold8));

			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.disableBorderSide(Rectangle.TOP);
			cell.disableBorderSide(Rectangle.BOTTOM);
			tableDetails.addCell(cell);
		}

		for (int index = 1; index < 26; index++)
		{
			cell = getCell(new Phrase("(" + index + ")", bold8));
			cell.disableBorderSide(Rectangle.TOP);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableDetails.addCell(cell);
		}

		int number = 0;
		ArrayList<TaggingRecord> sorted = new ArrayList<>();
		ArrayList<TaggingForm> taggingForms = tagging.getTaggingForms();

		for (TaggingForm taggingForm : taggingForms)
		{
			if (taggingForm.getTaggingTypeID() == taggingTypeID)
			{
				ArrayList<TaggingRecord> taggingRecords = taggingForm
						.getTaggingRecords();

				for (TaggingRecord taggingRecord : taggingRecords)
					if (taggingRecord.getTreeTypeID() == treeTypeID)
						sorted.add(taggingRecord);
			}
		}

		Collections.sort(sorted);

		for (TaggingRecord taggingRecord : sorted)
		{
			double volume = taggingRecord.getVolume();

			cell = getCell(new Phrase(Integer.toString(++number), normal8));
			tableDetails.addCell(cell);

			cell = getCell(new Phrase(taggingRecord.getSpeciesCode() + " - "
					+ taggingRecord.getSpeciesName(), normal8));
			tableDetails.addCell(cell);

			cell = getCell(new Phrase(taggingRecord.getSerialNo(), normal8));
			tableDetails.addCell(cell);

			cell = getCell(new Phrase(
					Long.toString(Math.round(taggingRecord.getDiameter())),
					normal8));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableDetails.addCell(cell);

			cell = getCell(new Phrase(df.format(volume), normal8));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableDetails.addCell(cell);

			int estLog = taggingRecord.getEstimation();
			cell = getCell(new Phrase(Integer.toString(estLog), normal8));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableDetails.addCell(cell);

			cell = getCell(
					new Phrase("" + taggingRecord.getLogQualityID(), normal8));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableDetails.addCell(cell);

			int min = 0, max = 5;

			switch (estLog)
			{
				case 1:
					min = 2;
					break;
				case 2:
					min = 3;
					break;
				case 3:
					min = 4;
					break;
				case 4:
					min = max;
					break;
				case 5:
					max = min;
					break;
			}

			String contentX = "X";

			for (int col = 1; col <= 5; col++)
			{
				for (int subCol = 1; subCol < 4; subCol++)
				{
					if ((min <= col) && (max == 5))
						contentX = "X";
					else
						contentX = " ";

					cell = getCell(new Phrase(contentX, normal8));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tableDetails.addCell(cell);

				}
			}

			cell = getCell(new Phrase("", normal8));
			tableDetails.addCell(cell);

			cell = getCell(new Phrase("", normal8));
			tableDetails.addCell(cell);

			cell = getCell(new Phrase(taggingRecord.getNote(), normal8));
			tableDetails.addCell(cell);
		}

		return tableDetails;

	}

	private PdfPTable assembleL4TreeTaggingSummary(int type, boolean dipt,
			boolean chengal) throws Exception
	{
		int count = 0, min = 0, length = 0;
		ArrayList<TaggingForm> taggingForms = tagging.getTaggingForms();
		ArrayList<TaggingRecord> taggingRecords = new ArrayList<>();
		HashMap<String, AtomicInteger[]> countDips = new HashMap<>();
		HashMap<String, AtomicInteger[]> countNonDips = new HashMap<>();
		HashMap<String, DoubleAdder[]> volumeDips = new HashMap<>();
		HashMap<String, DoubleAdder[]> volumeNonDips = new HashMap<>();
		PdfPTable tableSummary = null;
		PdfPCell cell = null;

		if (type == 1)
		{
			min = (int) (dipt
					? (chengal ? treeLimit.getChengalLimit()
							: tagging.getDipterocarpLimit())
					: tagging.getNonDipterocarpLimit());
			count = (135 - min) / 5;
			length = count + 5;

			for (TaggingForm taggingForm : taggingForms)
			{
				if (taggingForm.getTaggingTypeID() == 1)
				{
					ArrayList<TaggingRecord> currentTaggingRecords = taggingForm
							.getTaggingRecords();

					for (TaggingRecord taggingRecord : currentTaggingRecords)
					{
						if (taggingRecord.getTreeTypeID() == 1)
						{
							if (dipt && (taggingRecord.getSpeciesTypeID() == 1
									|| taggingRecord.getSpeciesTypeID() == 2))
							{
								if (chengal && "1201"
										.equals(taggingRecord.getSpeciesCode()))
									taggingRecords.add(taggingRecord);
								else if (!chengal && !"1201"
										.equals(taggingRecord.getSpeciesCode()))
									taggingRecords.add(taggingRecord);
							}
							else if (!dipt && (taggingRecord
									.getSpeciesTypeID() != 1
									&& taggingRecord.getSpeciesTypeID() != 2))
								taggingRecords.add(taggingRecord);
						}
					}
				}
			}

			float width = 66f / count, widths[] = new float[length];
			tableSummary = new PdfPTable(length);
			widths[0] = 9f;
			widths[1] = 7f;
			widths[length - 3] = 6f;
			widths[length - 2] = 6f;
			widths[length - 1] = 6f;

			for (int i = 0; i < count; i++)
				widths[i + 2] = width;

			tableSummary.setWidths(widths);
			tableSummary.setHeaderRows(2);

			for (TaggingRecord taggingRecord : taggingRecords)
			{
				String name = taggingRecord.getSpeciesCode() + " - "
						+ taggingRecord.getSpeciesName();
				int index = (int) (taggingRecord.getDiameter() - min) / 5;

				if (index >= count)
					index = count - 1;

				if (index < 0)
					index = 0;

				if (taggingRecord.getSpeciesTypeID() != 1
						&& taggingRecord.getSpeciesTypeID() != 2)
				{
					AtomicInteger[] valueNonDips = countNonDips.get(name);
					DoubleAdder[] volumeNonDip = volumeNonDips.get(name);

					if (valueNonDips == null)
					{
						valueNonDips = new AtomicInteger[count];
						volumeNonDip = new DoubleAdder[count];

						for (int i = 0; i < count; i++)
						{
							valueNonDips[i] = new AtomicInteger();
							volumeNonDip[i] = new DoubleAdder();
						}

						countNonDips.put(name, valueNonDips);
						volumeNonDips.put(name, volumeNonDip);
					}

					valueNonDips[index].incrementAndGet();
					volumeNonDip[index].add(taggingRecord.getVolume());
				}
				else
				{
					AtomicInteger[] valueDips = countDips.get(name);
					DoubleAdder[] volumeDip = volumeDips.get(name);

					if (valueDips == null)
					{
						valueDips = new AtomicInteger[count];
						volumeDip = new DoubleAdder[count];

						for (int i = 0; i < count; i++)
						{
							valueDips[i] = new AtomicInteger();
							volumeDip[i] = new DoubleAdder();
						}

						countDips.put(name, valueDips);
						volumeDips.put(name, volumeDip);
					}

					valueDips[index].incrementAndGet();
					volumeDip[index].add(taggingRecord.getVolume());
				}
			}

			ArrayList<String> keyDips = new ArrayList<>(countDips.keySet());
			ArrayList<String> keyNonDips = new ArrayList<>(
					countNonDips.keySet());
			int[] smallSumDips = new int[count],
					smallSumNonDips = new int[count], bigSums = new int[count];
			double[] smallVolumeSumDips = new double[count],
					smallVolumeSumNonDips = new double[count],
					bigVolumeSums = new double[count];
			double totalRoyalty = 0, totalCess = 0;

			Collections.sort(keyDips);
			Collections.sort(keyNonDips);

			for (String key : keyDips)
			{
				AtomicInteger[] values = countDips.get(key);
				DoubleAdder[] volume = volumeDips.get(key);

				for (int i = 0; i < count; i++)
				{
					int value = values[i].get();
					double vol = volume[i].doubleValue();
					smallSumDips[i] += values[i].get();
					smallVolumeSumDips[i] += vol;
					bigSums[i] += value;
					bigVolumeSums[i] += vol;
				}
			}

			for (String key : keyNonDips)
			{
				AtomicInteger[] values = countNonDips.get(key);
				DoubleAdder[] volume = volumeNonDips.get(key);

				for (int i = 0; i < count; i++)
				{
					int value = values[i].get();
					double vol = volume[i].doubleValue();
					smallSumNonDips[i] += values[i].get();
					smallVolumeSumNonDips[i] += vol;
					bigSums[i] += value;
					bigVolumeSums[i] += vol;
				}
			}

			if (dipt)
			{
				if (keyDips.size() != 0)
				{
					getSummaryHeaders(tableSummary,
							"Selang Kelas Diameter Pokok Tebangan ("
									+ (chengal ? "Chengal" : "Dipterokarp")
									+ ") Yang Di Tanda Tag (cm)",
							min, count, true, true);

					for (String key : keyDips)
					{
						int sum = 0;
						double total = 0;
						AtomicInteger[] values = countDips.get(key);
						DoubleAdder[] volume = volumeDips.get(key);
						MainRevenueRoyaltyRate mrrr = map.get(key);

						cell = getCell(new Phrase(key, normal8));
						cell.setRowspan(2);
						tableSummary.addCell(cell);

						cell = getCell(new Phrase("Bil. Pokok", normal8));
						tableSummary.addCell(cell);

						for (int i = 0; i < count; i++)
						{
							int value = values[i].get();
							sum += value;

							String out = value == 0 ? "" : "" + value;
							cell = getCell(new Phrase(out, normal8));

							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							tableSummary.addCell(cell);
						}

						cell = getCell(new Phrase("" + sum, normal8));

						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						tableSummary.addCell(cell);

						tableSummary.addCell(getCell(new Phrase("", normal8)));
						tableSummary.addCell(getCell(new Phrase("", normal8)));

						cell = getCell(new Phrase("Isipadu Pokok", normal8));
						tableSummary.addCell(cell);

						for (int i = 0; i < count; i++)
						{
							double value = volume[i].doubleValue();
							total += value;

							String out = value == 0 ? "" : df.format(value);
							cell = getCell(new Phrase(out, normal8));

							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							tableSummary.addCell(cell);
						}

						cell = getCell(new Phrase(df.format(total), normal8));

						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						tableSummary.addCell(cell);

						if (mrrr != null)
						{
							double royalty = total * mrrr
									.getBigSizeRoyaltyRate().doubleValue(),
									cess = total
											* mrrr.getCessRate().doubleValue();
							totalRoyalty += royalty;
							totalCess += cess;

							cell = getCell(
									new Phrase(df.format(royalty), normal8));

							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							tableSummary.addCell(cell);

							cell = getCell(
									new Phrase(df.format(cess), normal8));

							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							tableSummary.addCell(cell);
						}
						else
						{
							cell = getCell(new Phrase("-", normal8));

							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							tableSummary.addCell(cell);

							cell = getCell(new Phrase("-", normal8));

							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							tableSummary.addCell(cell);
						}
					}

					int sum = 0;
					double total = 0;
					cell = getCell(new Phrase(
							"Jumlah Bilangan Pokok "
									+ (chengal ? "Chengal" : "Dipterokarp"),
							bold8));
					cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
					cell.setColspan(2);
					tableSummary.addCell(cell);

					for (int i = 0; i < count; i++)
					{
						int value = smallSumDips[i];
						sum += value;

						String out = value == 0 ? "" : "" + value;
						cell = getCell(new Phrase(out, bold8));

						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
						tableSummary.addCell(cell);
					}

					cell = getCell(new Phrase("" + sum, bold8));

					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
					tableSummary.addCell(cell);

					cell = getCell(new Phrase("", bold8));

					cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
					tableSummary.addCell(cell);

					cell = getCell(new Phrase("", bold8));

					cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
					tableSummary.addCell(cell);

					cell = getCell(new Phrase(
							"Jumlah Anggaran Isipadu Pokok 80%", bold8));
					cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
					cell.setColspan(2);
					tableSummary.addCell(cell);

					for (int i = 0; i < count; i++)
					{
						double value = smallVolumeSumDips[i];
						total += value;

						String out = value == 0 ? "" : df.format(value);
						cell = getCell(new Phrase(out, bold8));

						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
						tableSummary.addCell(cell);
					}

					cell = getCell(new Phrase(df.format(total), bold8));

					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
					tableSummary.addCell(cell);

					cell = getCell(new Phrase(df.format(totalRoyalty), bold8));

					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
					tableSummary.addCell(cell);

					cell = getCell(new Phrase(df.format(totalCess), bold8));

					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
					tableSummary.addCell(cell);
				}
				else
					tableSummary = null;
			}
			else
			{
				if (keyNonDips.size() != 0)
				{
					getSummaryHeaders(tableSummary,
							"Selang Kelas Diameter Pokok Tebangan (Bukan Dipterokarp) Yang Di Tanda Tag (cm)",
							min, count, true, true);

					for (String key : keyNonDips)
					{
						int sum = 0;
						double total = 0;
						AtomicInteger[] values = countNonDips.get(key);
						DoubleAdder[] volume = volumeNonDips.get(key);
						MainRevenueRoyaltyRate mrrr = map.get(key);

						cell = getCell(new Phrase(key, normal8));
						cell.setRowspan(2);
						tableSummary.addCell(cell);

						cell = getCell(new Phrase("Bil. Pokok", normal8));
						tableSummary.addCell(cell);

						for (int i = 0; i < count; i++)
						{
							int value = values[i].get();
							sum += value;

							String out = value == 0 ? "" : "" + value;
							cell = getCell(new Phrase(out, normal8));

							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							tableSummary.addCell(cell);
						}

						cell = getCell(new Phrase("" + sum, normal8));

						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						tableSummary.addCell(cell);

						tableSummary.addCell(getCell(new Phrase("", normal8)));
						tableSummary.addCell(getCell(new Phrase("", normal8)));

						cell = getCell(new Phrase("Isipadu Pokok", normal8));
						tableSummary.addCell(cell);

						for (int i = 0; i < count; i++)
						{
							double value = volume[i].doubleValue();
							total += value;

							String out = value == 0 ? "" : df.format(value);
							cell = getCell(new Phrase(out, normal8));

							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							tableSummary.addCell(cell);
						}

						cell = getCell(new Phrase(df.format(total), normal8));

						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						tableSummary.addCell(cell);

						if (mrrr != null)
						{
							double royalty = total * mrrr
									.getBigSizeRoyaltyRate().doubleValue(),
									cess = total
											* mrrr.getCessRate().doubleValue();
							totalRoyalty += royalty;
							totalCess += cess;

							cell = getCell(
									new Phrase(df.format(royalty), normal8));

							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							tableSummary.addCell(cell);

							cell = getCell(
									new Phrase(df.format(cess), normal8));

							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							tableSummary.addCell(cell);
						}
						else
						{
							cell = getCell(new Phrase("-", normal8));

							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							tableSummary.addCell(cell);

							cell = getCell(new Phrase("-", normal8));

							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							tableSummary.addCell(cell);
						}
					}

					int sum = 0;
					double total = 0;
					cell = getCell(new Phrase(
							"Jumlah Bilangan Pokok Bukan Dipterokarp", bold8));
					cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
					cell.setColspan(2);
					tableSummary.addCell(cell);

					for (int i = 0; i < count; i++)
					{
						int value = smallSumNonDips[i];
						sum += value;

						String out = value == 0 ? "" : "" + value;
						cell = getCell(new Phrase(out, bold8));

						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
						tableSummary.addCell(cell);
					}

					cell = getCell(new Phrase("" + sum, bold8));

					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
					tableSummary.addCell(cell);

					cell = getCell(new Phrase("", bold8));

					cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
					tableSummary.addCell(cell);

					cell = getCell(new Phrase("", bold8));

					cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
					tableSummary.addCell(cell);

					cell = getCell(new Phrase(
							"Jumlah Anggaran Isipadu Pokok 80%", bold8));
					cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
					cell.setColspan(2);
					tableSummary.addCell(cell);

					for (int i = 0; i < count; i++)
					{
						double value = smallVolumeSumNonDips[i];
						total += value;

						String out = value == 0 ? "" : df.format(value);
						cell = getCell(new Phrase(out, bold8));

						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
						tableSummary.addCell(cell);
					}

					cell = getCell(new Phrase(df.format(total), bold8));

					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
					tableSummary.addCell(cell);

					cell = getCell(new Phrase(df.format(totalRoyalty), bold8));

					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
					tableSummary.addCell(cell);

					cell = getCell(new Phrase(df.format(totalRoyalty), bold8));

					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
					tableSummary.addCell(cell);
				}
				else
					tableSummary = null;
			}
		}
		else if (type == 2)
		{
			min = 30;
			count = 7;
			length = count + 2;

			for (TaggingForm taggingForm : taggingForms)
			{
				if (taggingForm.getTaggingTypeID() == 1)
				{
					ArrayList<TaggingRecord> currentTaggingRecords = taggingForm
							.getTaggingRecords();

					for (TaggingRecord taggingRecord : currentTaggingRecords)
					{
						if (taggingRecord.getTreeTypeID() == 2)
							taggingRecords.add(taggingRecord);
					}
				}
			}

			float width = 75f / count, widths[] = new float[length];
			tableSummary = new PdfPTable(length);
			widths[0] = 15f;
			widths[length - 1] = 10f;

			for (int i = 0; i < count; i++)
				widths[i + 1] = width;

			tableSummary.setWidths(widths);
			tableSummary.setHeaderRows(2);

			for (TaggingRecord taggingRecord : taggingRecords)
			{
				String name = taggingRecord.getSpeciesCode() + " - "
						+ taggingRecord.getSpeciesName();
				int index = (int) (taggingRecord.getDiameter() - min) / 5;

				if (index >= count)
					index = count - 1;

				if (index < 0)
					index = 0;

				if (taggingRecord.getSpeciesTypeID() != 1
						&& taggingRecord.getSpeciesTypeID() != 2)
				{
					AtomicInteger[] valueNonDips = countNonDips.get(name);

					if (valueNonDips == null)
					{
						valueNonDips = new AtomicInteger[count];

						for (int i = 0; i < count; i++)
							valueNonDips[i] = new AtomicInteger();

						countNonDips.put(name, valueNonDips);
					}

					valueNonDips[index].incrementAndGet();
				}
				else
				{
					AtomicInteger[] valueDips = countDips.get(name);

					if (valueDips == null)
					{
						valueDips = new AtomicInteger[count];

						for (int i = 0; i < count; i++)
							valueDips[i] = new AtomicInteger();

						countDips.put(name, valueDips);
					}

					valueDips[index].incrementAndGet();
				}
			}

			ArrayList<String> keyDips = new ArrayList<>(countDips.keySet());
			ArrayList<String> keyNonDips = new ArrayList<>(
					countNonDips.keySet());
			int[] smallSumDips = new int[count],
					smallSumNonDips = new int[count], bigSums = new int[count];

			Collections.sort(keyDips);
			Collections.sort(keyNonDips);

			for (String key : keyDips)
			{
				AtomicInteger[] values = countDips.get(key);

				for (int i = 0; i < count; i++)
				{
					int value = values[i].get();
					smallSumDips[i] += values[i].get();
					bigSums[i] += value;
				}
			}

			for (String key : keyNonDips)
			{
				AtomicInteger[] values = countNonDips.get(key);

				for (int i = 0; i < count; i++)
				{
					int value = values[i].get();
					smallSumNonDips[i] += values[i].get();
					bigSums[i] += value;
				}
			}

			getSummaryHeaders(tableSummary,
					"Selang Kelas Diameter Pokok Ibu Yang Di Tanda Tag (cm)",
					min, count, false, false);

			if (keyDips.size() != 0)
			{
				for (String key : keyDips)
				{
					int sum = 0;
					AtomicInteger[] values = countDips.get(key);

					cell = getCell(new Phrase(key, normal8));
					tableSummary.addCell(cell);

					for (int i = 0; i < count; i++)
					{
						int value = values[i].get();
						sum += value;

						String out = value == 0 ? "" : "" + value;
						cell = getCell(new Phrase(out, normal8));

						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						tableSummary.addCell(cell);
					}

					cell = getCell(new Phrase("" + sum, normal8));

					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					tableSummary.addCell(cell);
				}

				int sum = 0;
				cell = getCell(
						new Phrase("Jumlah Pokok Ibu Dipterokarp", bold8));
				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				tableSummary.addCell(cell);

				for (int i = 0; i < count; i++)
				{
					int value = smallSumDips[i];
					sum += value;

					String out = value == 0 ? "" : "" + value;
					cell = getCell(new Phrase(out, bold8));

					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
					tableSummary.addCell(cell);
				}

				cell = getCell(new Phrase("" + sum, bold8));

				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				tableSummary.addCell(cell);
			}

			if (keyNonDips.size() != 0)
			{
				for (String key : keyNonDips)
				{
					int sum = 0;
					AtomicInteger[] values = countNonDips.get(key);

					cell = getCell(new Phrase(key, normal8));
					tableSummary.addCell(cell);

					for (int i = 0; i < count; i++)
					{
						int value = values[i].get();
						sum += value;

						String out = value == 0 ? "" : "" + value;
						cell = getCell(new Phrase(out, normal8));

						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						tableSummary.addCell(cell);
					}

					cell = getCell(new Phrase("" + sum, normal8));

					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					tableSummary.addCell(cell);
				}

				int sum = 0;
				cell = getCell(new Phrase("Jumlah Pokok Ibu Bukan Dipterokarp",
						bold8));
				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				tableSummary.addCell(cell);

				for (int i = 0; i < count; i++)
				{
					int value = smallSumNonDips[i];
					sum += value;

					String out = value == 0 ? "" : "" + value;
					cell = getCell(new Phrase(out, bold8));

					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
					tableSummary.addCell(cell);
				}

				cell = getCell(new Phrase("" + sum, bold8));

				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				tableSummary.addCell(cell);
			}

			int sum = 0;
			cell = getCell(new Phrase("Jumlah Keseluruhan Pokok Ibu", bold8));
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			tableSummary.addCell(cell);

			for (int i = 0; i < count; i++)
			{
				int value = bigSums[i];
				sum += value;

				String out = value == 0 ? "" : "" + value;
				cell = getCell(new Phrase(out, bold8));

				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				tableSummary.addCell(cell);
			}

			cell = getCell(new Phrase("" + sum, bold8));

			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			tableSummary.addCell(cell);
		}
		else if (type == 3)
		{
			min = 30;
			count = 21;
			length = count + 2;

			for (TaggingForm taggingForm : taggingForms)
			{
				if (taggingForm.getTaggingTypeID() == 1)
				{
					ArrayList<TaggingRecord> currentTaggingRecords = taggingForm
							.getTaggingRecords();

					for (TaggingRecord taggingRecord : currentTaggingRecords)
					{
						if (taggingRecord.getTreeTypeID() == 3)
							taggingRecords.add(taggingRecord);
					}
				}
			}

			float width = 82f / count, widths[] = new float[length];
			tableSummary = new PdfPTable(length);
			widths[0] = 12f;
			widths[length - 1] = 6f;

			for (int i = 0; i < count; i++)
				widths[i + 1] = width;

			tableSummary.setWidths(widths);
			tableSummary.setHeaderRows(2);

			for (TaggingRecord taggingRecord : taggingRecords)
			{
				String name = taggingRecord.getSpeciesCode() + " - "
						+ taggingRecord.getSpeciesName();
				int index = (int) (taggingRecord.getDiameter() - min) / 5;

				if (index >= count)
					index = count - 1;

				if (index < 0)
					index = 0;

				if (taggingRecord.getSpeciesTypeID() != 1
						&& taggingRecord.getSpeciesTypeID() != 2)
				{
					AtomicInteger[] valueNonDips = countNonDips.get(name);

					if (valueNonDips == null)
					{
						valueNonDips = new AtomicInteger[count];

						for (int i = 0; i < count; i++)
							valueNonDips[i] = new AtomicInteger();

						countNonDips.put(name, valueNonDips);
					}

					valueNonDips[index].incrementAndGet();
				}
				else
				{
					AtomicInteger[] valueDips = countDips.get(name);

					if (valueDips == null)
					{
						valueDips = new AtomicInteger[count];

						for (int i = 0; i < count; i++)
							valueDips[i] = new AtomicInteger();

						countDips.put(name, valueDips);
					}

					valueDips[index].incrementAndGet();
				}
			}

			ArrayList<String> keyDips = new ArrayList<>(countDips.keySet());
			ArrayList<String> keyNonDips = new ArrayList<>(
					countNonDips.keySet());
			int[] smallSumDips = new int[count],
					smallSumNonDips = new int[count], bigSums = new int[count];

			Collections.sort(keyDips);
			Collections.sort(keyNonDips);

			for (String key : keyDips)
			{
				AtomicInteger[] values = countDips.get(key);

				for (int i = 0; i < count; i++)
				{
					int value = values[i].get();
					smallSumDips[i] += values[i].get();
					bigSums[i] += value;
				}
			}

			for (String key : keyNonDips)
			{
				AtomicInteger[] values = countNonDips.get(key);

				for (int i = 0; i < count; i++)
				{
					int value = values[i].get();
					smallSumNonDips[i] += values[i].get();
					bigSums[i] += value;
				}
			}

			getSummaryHeaders(tableSummary,
					"Selang Kelas Diameter Bagi Pokok Perlindungan (cm)", min,
					count, false, true);

			if (keyDips.size() != 0)
			{
				for (String key : keyDips)
				{
					int sum = 0;
					AtomicInteger[] values = countDips.get(key);

					cell = getCell(new Phrase(key, normal8));
					tableSummary.addCell(cell);

					for (int i = 0; i < count; i++)
					{
						int value = values[i].get();
						sum += value;

						String out = value == 0 ? "" : "" + value;
						cell = getCell(new Phrase(out, normal8));

						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						tableSummary.addCell(cell);
					}

					cell = getCell(new Phrase("" + sum, normal8));

					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					tableSummary.addCell(cell);
				}

				int sum = 0;
				cell = getCell(new Phrase("Jumlah Kaum Dipterokarp", bold8));
				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				tableSummary.addCell(cell);

				for (int i = 0; i < count; i++)
				{
					int value = smallSumDips[i];
					sum += value;

					String out = value == 0 ? "" : "" + value;
					cell = getCell(new Phrase(out, bold8));

					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
					tableSummary.addCell(cell);
				}

				cell = getCell(new Phrase("" + sum, bold8));

				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				tableSummary.addCell(cell);
			}

			if (keyNonDips.size() != 0)
			{
				for (String key : keyNonDips)
				{
					int sum = 0;
					AtomicInteger[] values = countNonDips.get(key);

					cell = getCell(new Phrase(key, normal8));
					tableSummary.addCell(cell);

					for (int i = 0; i < count; i++)
					{
						int value = values[i].get();
						sum += value;

						String out = value == 0 ? "" : "" + value;
						cell = getCell(new Phrase(out, normal8));

						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						tableSummary.addCell(cell);
					}

					cell = getCell(new Phrase("" + sum, normal8));

					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					tableSummary.addCell(cell);
				}

				int sum = 0;
				cell = getCell(
						new Phrase("Jumlah Kaum Bukan Dipterokarp", bold8));
				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				tableSummary.addCell(cell);

				for (int i = 0; i < count; i++)
				{
					int value = smallSumNonDips[i];
					sum += value;

					String out = value == 0 ? "" : "" + value;
					cell = getCell(new Phrase(out, bold8));

					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
					tableSummary.addCell(cell);
				}

				cell = getCell(new Phrase("" + sum, bold8));

				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				tableSummary.addCell(cell);
			}

			int sum = 0;
			cell = getCell(
					new Phrase("Jumlah Keseluruhan Pokok Perlindungan", bold8));
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			tableSummary.addCell(cell);

			for (int i = 0; i < count; i++)
			{
				int value = bigSums[i];
				sum += value;

				String out = value == 0 ? "" : "" + value;
				cell = getCell(new Phrase(out, bold8));

				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				tableSummary.addCell(cell);
			}

			cell = getCell(new Phrase("" + sum, bold8));

			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			tableSummary.addCell(cell);
		}

		return tableSummary;
	}

	private PdfPTable assembleTreeTaggingSummary(int taggingTypeID)
			throws Exception
	{
		ArrayList<TaggingRecord> taggingRecords = new ArrayList<>();
		ArrayList<TaggingForm> taggingForms = tagging.getTaggingForms();

		for (TaggingForm taggingForm : taggingForms)
			if (taggingForm.getTaggingTypeID() == taggingTypeID)
				taggingRecords.addAll(taggingForm.getTaggingRecords());

		return assembleTreeTaggingSummary(taggingRecords);
	}

	private PdfPTable assembleTreeTaggingSummary(
			ArrayList<TaggingRecord> taggingRecords) throws Exception
	{
		int count = 12, min = 30, length = count + 3;
		float width = 75f / (count + 1), widths[] = new float[length];
		HashMap<String, AtomicInteger[]> countDips = new HashMap<>();
		HashMap<String, AtomicInteger[]> countNonDips = new HashMap<>();
		HashMap<String, DoubleAdder[]> volumeDips = new HashMap<>();
		HashMap<String, DoubleAdder[]> volumeNonDips = new HashMap<>();
		PdfPTable tableSummary = new PdfPTable(length);

		widths[0] = 15f;
		widths[1] = 10f;

		for (int i = 0; i <= count; i++)
			widths[i + 2] = width;

		tableSummary.setWidths(widths);
		tableSummary.setHeaderRows(1);
		getSummaryHeaders(tableSummary, min, count);

		for (TaggingRecord taggingRecord : taggingRecords)
		{
			String name = taggingRecord.getSpeciesCode() + " - "
					+ taggingRecord.getSpeciesName();
			int index = (int) (taggingRecord.getDiameter() - min) / 5;

			if (index >= count)
				index = count - 1;

			if (index < 0)
				index = 0;

			if (taggingRecord.getSpeciesTypeID() != 1
					&& taggingRecord.getSpeciesTypeID() != 2)
			{
				AtomicInteger[] valueNonDips = countNonDips.get(name);
				DoubleAdder[] volumeNonDip = volumeNonDips.get(name);

				if (valueNonDips == null)
				{
					valueNonDips = new AtomicInteger[count];
					volumeNonDip = new DoubleAdder[count];

					for (int i = 0; i < count; i++)
					{
						valueNonDips[i] = new AtomicInteger();
						volumeNonDip[i] = new DoubleAdder();
					}

					countNonDips.put(name, valueNonDips);
					volumeNonDips.put(name, volumeNonDip);
				}

				valueNonDips[index].incrementAndGet();
				volumeNonDip[index].add(taggingRecord.getVolume());
			}
			else
			{
				AtomicInteger[] valueDips = countDips.get(name);
				DoubleAdder[] volumeDip = volumeDips.get(name);

				if (valueDips == null)
				{
					valueDips = new AtomicInteger[count];
					volumeDip = new DoubleAdder[count];

					for (int i = 0; i < count; i++)
					{
						valueDips[i] = new AtomicInteger();
						volumeDip[i] = new DoubleAdder();
					}

					countDips.put(name, valueDips);
					volumeDips.put(name, volumeDip);
				}

				valueDips[index].incrementAndGet();
				volumeDip[index].add(taggingRecord.getVolume());
			}
		}

		PdfPCell cell = null;
		Vector<String> keyDips = new Vector<>(countDips.keySet());
		Vector<String> keyNonDips = new Vector<>(countNonDips.keySet());
		int[] smallSumDips = new int[count], smallSumNonDips = new int[count],
				bigSums = new int[count];
		double[] smallVolumeSumDips = new double[count],
				smallVolumeSumNonDips = new double[count],
				bigVolumeSums = new double[count];

		Collections.sort(keyDips);
		Collections.sort(keyNonDips);

		if (keyDips.size() != 0)
		{
			for (String key : keyDips)
			{
				int sum = 0;
				double total = 0;
				AtomicInteger[] values = countDips.get(key);
				DoubleAdder[] volume = volumeDips.get(key);

				cell = getCell(new Phrase(key, normal8));
				cell.setRowspan(2);
				tableSummary.addCell(cell);

				cell = getCell(new Phrase("Bil. Pokok", normal8));
				tableSummary.addCell(cell);

				for (int i = 0; i < count; i++)
				{
					int value = values[i].get();
					smallSumDips[i] += value;
					sum += value;

					String out = value == 0 ? "" : "" + value;
					cell = getCell(new Phrase(out, normal8));

					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					tableSummary.addCell(cell);
				}

				cell = getCell(new Phrase("" + sum, normal8));

				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tableSummary.addCell(cell);

				cell = getCell(new Phrase("Isipadu Pokok", normal8));
				tableSummary.addCell(cell);

				for (int i = 0; i < count; i++)
				{
					double value = volume[i].doubleValue();
					smallVolumeSumDips[i] += value;
					total += value;

					String out = value == 0 ? "" : df.format(value);
					cell = getCell(new Phrase(out, normal8));

					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					tableSummary.addCell(cell);
				}

				cell = getCell(new Phrase(df.format(total), normal8));

				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tableSummary.addCell(cell);
			}

			int sum = 0;
			double total = 0;
			cell = getCell(new Phrase("JUMLAH KECIL", bold8));
			cell.setRowspan(2);
			tableSummary.addCell(cell);

			cell = getCell(new Phrase("Bil. Pokok", bold8));
			tableSummary.addCell(cell);

			for (int i = 0; i < count; i++)
			{
				int value = smallSumDips[i];
				bigSums[i] += value;
				sum += value;

				String out = value == 0 ? "" : "" + value;
				cell = getCell(new Phrase(out, bold8));

				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tableSummary.addCell(cell);
			}

			cell = getCell(new Phrase("" + sum, bold8));

			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableSummary.addCell(cell);

			cell = getCell(new Phrase("Isipadu Pokok", bold8));
			tableSummary.addCell(cell);

			for (int i = 0; i < count; i++)
			{
				double value = smallVolumeSumDips[i];
				bigVolumeSums[i] += value;
				total += value;

				String out = value == 0 ? "" : df.format(value);
				cell = getCell(new Phrase(out, bold8));

				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tableSummary.addCell(cell);
			}

			cell = getCell(new Phrase(df.format(total), bold8));

			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableSummary.addCell(cell);
		}

		if (keyNonDips.size() != 0)
		{
			for (String key : keyNonDips)
			{
				int sum = 0;
				double total = 0;
				AtomicInteger[] values = countNonDips.get(key);
				DoubleAdder[] volume = volumeNonDips.get(key);

				cell = getCell(new Phrase(key, normal8));
				cell.setRowspan(2);
				tableSummary.addCell(cell);

				cell = getCell(new Phrase("Bil. Pokok", normal8));
				tableSummary.addCell(cell);

				for (int i = 0; i < count; i++)
				{
					int value = values[i].get();
					smallSumNonDips[i] += value;
					sum += value;

					String out = value == 0 ? "" : "" + value;
					cell = getCell(new Phrase(out, normal8));

					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					tableSummary.addCell(cell);
				}

				cell = getCell(new Phrase("" + sum, normal8));

				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tableSummary.addCell(cell);

				cell = getCell(new Phrase("Isipadu Pokok", normal8));
				tableSummary.addCell(cell);

				for (int i = 0; i < count; i++)
				{
					double value = volume[i].doubleValue();
					smallVolumeSumNonDips[i] += value;
					total += value;

					String out = value == 0 ? "" : df.format(value);
					cell = getCell(new Phrase(out, normal8));

					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					tableSummary.addCell(cell);
				}

				cell = getCell(new Phrase(df.format(total), normal8));

				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tableSummary.addCell(cell);
			}

			int sum = 0;
			double total = 0;
			cell = getCell(new Phrase("JUMLAH KECIL", bold8));
			cell.setRowspan(2);
			tableSummary.addCell(cell);

			cell = getCell(new Phrase("Bil. Pokok", bold8));
			tableSummary.addCell(cell);

			for (int i = 0; i < count; i++)
			{
				int value = smallSumNonDips[i];
				bigSums[i] += value;
				sum += value;

				String out = value == 0 ? "" : "" + value;
				cell = getCell(new Phrase(out, bold8));

				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tableSummary.addCell(cell);
			}

			cell = getCell(new Phrase("" + sum, bold8));

			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableSummary.addCell(cell);

			cell = getCell(new Phrase("Isipadu Pokok", bold8));
			tableSummary.addCell(cell);

			for (int i = 0; i < count; i++)
			{
				double value = smallVolumeSumNonDips[i];
				bigVolumeSums[i] += value;
				total += value;

				String out = value == 0 ? "" : df.format(value);
				cell = getCell(new Phrase(out, bold8));

				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tableSummary.addCell(cell);
			}

			cell = getCell(new Phrase(df.format(total), bold8));

			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableSummary.addCell(cell);
		}

		int sum = 0;
		double total = 0;
		cell = getCell(new Phrase("JUMLAH BESAR", bold8));
		cell.setRowspan(2);
		tableSummary.addCell(cell);

		cell = getCell(new Phrase("Bil. Pokok", bold8));
		tableSummary.addCell(cell);

		for (int i = 0; i < count; i++)
		{
			int value = bigSums[i];
			sum += value;

			String out = value == 0 ? "" : "" + value;
			cell = getCell(new Phrase(out, bold8));

			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableSummary.addCell(cell);
		}

		cell = getCell(new Phrase("" + sum, bold8));

		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tableSummary.addCell(cell);

		cell = getCell(new Phrase("Isipadu Pokok", bold8));
		tableSummary.addCell(cell);

		for (int i = 0; i < count; i++)
		{
			double value = bigVolumeSums[i];
			total += value;

			String out = value == 0 ? "" : df.format(value);
			cell = getCell(new Phrase(out, bold8));

			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableSummary.addCell(cell);
		}

		cell = getCell(new Phrase(df.format(total), bold8));

		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tableSummary.addCell(cell);

		return tableSummary;
	}

	private void getSummaryHeaders(PdfPTable tableSummary, String title,
			int min, int count, boolean detail, boolean limit) throws Exception
	{
		PdfPCell cell = getCell(new Phrase("Spesis", bold8));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		cell.setRowspan(2);
		tableSummary.addCell(cell);

		if (detail)
		{
			cell = getCell(new Phrase("Butiran", bold8));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell.setRowspan(2);
			tableSummary.addCell(cell);
		}

		cell = getCell(new Phrase(title, bold8));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		cell.setColspan(count);
		tableSummary.addCell(cell);

		cell = getCell(new Phrase("Jumlah Keseluruhan", bold8));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		cell.setRowspan(2);
		tableSummary.addCell(cell);

		if (detail)
		{
			cell = getCell(new Phrase("Anggaran (RM)", bold8));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell.setColspan(2);
			tableSummary.addCell(cell);
		}

		for (int i = 0; i < count; i++)
		{
			String header = (limit && i == count - 1) ? ("> " + min)
					: (min + "-" + (min + 4));
			cell = getCell(new Phrase(header, bold8));
			min += 5;

			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			tableSummary.addCell(cell);
		}

		if (detail)
		{
			cell = getCell(new Phrase("Royalti", bold8));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			tableSummary.addCell(cell);

			cell = getCell(new Phrase("Ses", bold8));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			tableSummary.addCell(cell);
		}
	}

	private void getSummaryHeaders(PdfPTable tableSummary, int min, int count)
			throws Exception
	{
		PdfPCell cell = getCell(new Phrase("SPESIS", bold8));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableSummary.addCell(cell);

		cell = getCell(new Phrase("BUTIRAN", bold8));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableSummary.addCell(cell);

		for (int i = 0; i < count; i++)
		{
			String header = min + "-" + (min + 4);
			cell = getCell(new Phrase(header, bold8));
			min += 5;

			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableSummary.addCell(cell);
		}

		cell = getCell(new Phrase("JUMLAH", bold8));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableSummary.addCell(cell);
	}
}