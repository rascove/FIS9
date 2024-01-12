package my.edu.utem.ftmk.fis9.hall.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import my.edu.utem.ftmk.fis9.hall.controller.manager.HallFacade;

/**
 * @author Nor Azman Bin Mat Ariff
 */
public class ShuttleReportGenerator
{
	private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.BOLD);
	private static Font contentFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.NORMAL);

	public static void generate(File file,
			ArrayList<String[]> bigSizeLogShuttleReport,
			ArrayList<String[]> smallSizeLogShuttleReport, String month, String year)
			throws Exception
	{
		Document document = new Document(PageSize.A3.rotate(), 28.35f, 28.35f,
				28.35f, 28.35f);

		PdfWriter.getInstance(document, new FileOutputStream(file));

		document.open();

		document.addSubject("Jabatan Perhutanan Negeri Sembilan");
		document.addKeywords("Penyata Keluaran Hasil");
		document.addAuthor("JPNS");
		document.addCreator("JPNS");
		document.addTitle(
				"PENYATA KELUARAN HASIL HUTAN DAN CUKAI, ROYALTI DAN PREMIUM");

		Paragraph titleBig = new Paragraph(
				"BORANG 1(a) - PENYATA KELUARAN HASIL HUTAN DAN CUKAI, ROYALTI DAN PREMIUM\n(untuk kayu balak besar GPD: >= 45 cm)\nBulan "
						+ month + " " + year + "\n\n",
				titleFont);
		Paragraph titleSmall = new Paragraph(
				"BORANG 1(b) - PENYATA KELUARAN HASIL HUTAN DAN CUKAI, ROYALTI DAN PREMIUM\n(untuk kayu balak kecil GPD: < 45 cm)\nBulan "
						+ month + " " + year  + "\n\n",
				titleFont);
		float[] widths = new float[] {10, 6, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
				5, 7, 7};
		PdfPTable tableBig = new PdfPTable(widths);
		PdfPTable tableSmall = new PdfPTable(widths);

		titleBig.setAlignment(Paragraph.ALIGN_CENTER);
		titleSmall.setAlignment(Paragraph.ALIGN_CENTER);

		tableBig.setHeaderRows(2);
		tableBig.setWidthPercentage(101f);

		tableSmall.setHeaderRows(2);
		tableSmall.setWidthPercentage(101f);

		PdfPCell titleName = new PdfPCell(new Phrase("Spesis", titleFont));
		PdfPCell titleType = new PdfPCell(new Phrase("Kump. Kayu", titleFont));
		PdfPCell titleCode = new PdfPCell(new Phrase("Kod Kayu", titleFont));
		PdfPCell titleRoyalty = new PdfPCell(
				new Phrase("Kadar Royalti\nse-0.01 m\u00B3", titleFont));
		PdfPCell titleRF = new PdfPCell(new Phrase("Hutan Simpan", titleFont));
		PdfPCell titleSF = new PdfPCell(new Phrase("Hutan Negeri", titleFont));
		PdfPCell titleCR = new PdfPCell(new Phrase("Laporan Tutup", titleFont));
		PdfPCell titleOL = new PdfPCell(new Phrase("Tanah Milik", titleFont));
		PdfPCell titleSum = new PdfPCell(
				new Phrase("Jumlah Keluaran", titleFont));
		PdfPCell titleVolume = new PdfPCell(
				new Phrase("Meter Padu", titleFont));
		PdfPCell titleQuote = new PdfPCell(
				new Phrase("Kutipan Royalti", titleFont));
		PdfPCell titleLog = new PdfPCell(new Phrase("KAYU BALAK", titleFont));
		PdfPCell empty = new PdfPCell(new Phrase("", contentFont));
		PdfPCell dash = new PdfPCell(new Phrase("-", contentFont));
		PdfPCell dash2 = new PdfPCell(new Phrase("-", contentFont));

		titleName.setRowspan(2);
		titleType.setRowspan(2);
		titleCode.setRowspan(2);
		titleRoyalty.setRowspan(2);
		titleRF.setColspan(2);
		titleSF.setColspan(2);
		titleCR.setColspan(2);
		titleOL.setColspan(2);
		titleSum.setColspan(2);
		empty.setBackgroundColor(BaseColor.LIGHT_GRAY);
		dash2.setBackgroundColor(BaseColor.LIGHT_GRAY);

		titleName.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		titleType.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		titleCode.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		titleRoyalty.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		titleRF.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		titleSF.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		titleCR.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		titleOL.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		titleSum.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		titleVolume.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		titleQuote.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		dash.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		dash2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		tableBig.addCell(titleName);
		tableBig.addCell(titleType);
		tableBig.addCell(titleCode);
		tableBig.addCell(titleRoyalty);
		tableBig.addCell(titleRF);
		tableBig.addCell(titleSF);
		tableBig.addCell(titleCR);
		tableBig.addCell(titleSum);
		tableBig.addCell(titleRoyalty);
		tableBig.addCell(titleOL);
		tableBig.addCell(titleSum);
		tableBig.addCell(titleVolume);
		tableBig.addCell(titleQuote);
		tableBig.addCell(titleVolume);
		tableBig.addCell(titleQuote);
		tableBig.addCell(titleVolume);
		tableBig.addCell(titleQuote);
		tableBig.addCell(titleVolume);
		tableBig.addCell(titleQuote);
		tableBig.addCell(titleVolume);
		tableBig.addCell(titleQuote);
		tableBig.addCell(titleVolume);
		tableBig.addCell(titleQuote);

		tableSmall.addCell(titleName);
		tableSmall.addCell(titleType);
		tableSmall.addCell(titleCode);
		tableSmall.addCell(titleRoyalty);
		tableSmall.addCell(titleRF);
		tableSmall.addCell(titleSF);
		tableSmall.addCell(titleCR);
		tableSmall.addCell(titleSum);
		tableSmall.addCell(titleRoyalty);
		tableSmall.addCell(titleOL);
		tableSmall.addCell(titleSum);
		tableSmall.addCell(titleVolume);
		tableSmall.addCell(titleQuote);
		tableSmall.addCell(titleVolume);
		tableSmall.addCell(titleQuote);
		tableSmall.addCell(titleVolume);
		tableSmall.addCell(titleQuote);
		tableSmall.addCell(titleVolume);
		tableSmall.addCell(titleQuote);
		tableSmall.addCell(titleVolume);
		tableSmall.addCell(titleQuote);
		tableSmall.addCell(titleVolume);
		tableSmall.addCell(titleQuote);

		for (int i = 0; i < 17; i++)
		{
			PdfPCell index = new PdfPCell(new Phrase(
					"(" + (i < 9 ? "0" : "") + (i + 1) + ")", contentFont));

			index.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

			tableBig.addCell(index);
			tableSmall.addCell(index);
		}

		tableBig.addCell(titleLog);
		tableBig.completeRow();

		tableSmall.addCell(titleLog);
		tableSmall.completeRow();

		DecimalFormat df = new DecimalFormat("0.00");

		if (!bigSizeLogShuttleReport.isEmpty())
		{
			String previousType = bigSizeLogShuttleReport.get(0)[1];
			double[] sumType = new double[12], sumAll = new double[12];

			for (String[] shuttleReport : bigSizeLogShuttleReport)
			{
				if (!shuttleReport[1].equals(previousType))
				{
					PdfPCell cell = new PdfPCell(
							new Phrase("Jumlah " + previousType, titleFont));

					cell.setBackgroundColor(BaseColor.LIGHT_GRAY);

					tableBig.addCell(cell);
					tableBig.addCell(empty);
					tableBig.addCell(empty);
					tableBig.addCell(empty);

					for (int i = 1; i < 12; i++)
					{
						if (i == 5)
						{
							tableBig.addCell(dash2);
							tableBig.addCell(dash2);
						}

						if (i == 7)
							tableBig.addCell(empty);
						else
						{
							PdfPCell value = new PdfPCell(new Phrase(
									df.format(sumType[i]), titleFont));

							value.setBackgroundColor(BaseColor.LIGHT_GRAY);
							value.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

							tableBig.addCell(value);
						}
					}

					previousType = shuttleReport[1];
					sumType = new double[12];
				}

				PdfPCell species = new PdfPCell(
						new Phrase(shuttleReport[0], contentFont));
				PdfPCell type = new PdfPCell(
						new Phrase(shuttleReport[1], contentFont));
				PdfPCell code = new PdfPCell(
						new Phrase(shuttleReport[2], contentFont));

				type.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				code.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

				tableBig.addCell(species);
				tableBig.addCell(type);
				tableBig.addCell(code);

				for (int i = 3; i < 15; i++)
				{
					double value = Double.parseDouble(shuttleReport[i]);
					PdfPCell cell = new PdfPCell(
							new Phrase(shuttleReport[i], contentFont));

					sumAll[i - 3] += value;
					sumType[i - 3] += value;

					cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
					tableBig.addCell(cell);

					if (i == 7)
					{
						tableBig.addCell(dash);
						tableBig.addCell(dash);
					}
				}
			}

			PdfPCell cell = new PdfPCell(
					new Phrase("Jumlah " + previousType, titleFont));

			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);

			tableBig.addCell(cell);
			tableBig.addCell(empty);
			tableBig.addCell(empty);
			tableBig.addCell(empty);

			for (int i = 1; i < 12; i++)
			{
				if (i == 5)
				{
					tableBig.addCell(dash2);
					tableBig.addCell(dash2);
				}

				if (i == 7)
					tableBig.addCell(empty);
				else
				{
					PdfPCell value = new PdfPCell(
							new Phrase(df.format(sumType[i]), titleFont));

					value.setBackgroundColor(BaseColor.LIGHT_GRAY);
					value.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

					tableBig.addCell(value);
				}
			}

			cell = new PdfPCell(new Phrase("Jumlah Besar", titleFont));

			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);

			tableBig.addCell(cell);
			tableBig.addCell(empty);
			tableBig.addCell(empty);
			tableBig.addCell(empty);

			for (int i = 1; i < 12; i++)
			{
				if (i == 5)
				{
					tableBig.addCell(dash2);
					tableBig.addCell(dash2);
				}

				if (i == 7)
					tableBig.addCell(empty);
				else
				{
					PdfPCell value = new PdfPCell(
							new Phrase(df.format(sumAll[i]), titleFont));

					value.setBackgroundColor(BaseColor.LIGHT_GRAY);
					value.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

					tableBig.addCell(value);
				}
			}
		}

		if (!smallSizeLogShuttleReport.isEmpty())
		{
			String previousType = smallSizeLogShuttleReport.get(0)[1];
			double[] sumType = new double[12], sumAll = new double[12];

			for (String[] shuttleReport : smallSizeLogShuttleReport)
			{
				if (!shuttleReport[1].equals(previousType))
				{
					PdfPCell cell = new PdfPCell(
							new Phrase("Jumlah " + previousType, titleFont));

					cell.setBackgroundColor(BaseColor.LIGHT_GRAY);

					tableSmall.addCell(cell);
					tableSmall.addCell(empty);
					tableSmall.addCell(empty);
					tableSmall.addCell(empty);

					for (int i = 1; i < 12; i++)
					{
						if (i == 5)
						{
							tableSmall.addCell(dash2);
							tableSmall.addCell(dash2);
						}

						if (i == 7)
							tableSmall.addCell(empty);
						else
						{
							PdfPCell value = new PdfPCell(new Phrase(
									df.format(sumType[i]), titleFont));

							value.setBackgroundColor(BaseColor.LIGHT_GRAY);
							value.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

							tableSmall.addCell(value);
						}
					}

					previousType = shuttleReport[1];
					sumType = new double[12];
				}

				PdfPCell species = new PdfPCell(
						new Phrase(shuttleReport[0], contentFont));
				PdfPCell type = new PdfPCell(
						new Phrase(shuttleReport[1], contentFont));
				PdfPCell code = new PdfPCell(
						new Phrase(shuttleReport[2], contentFont));

				type.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				code.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

				tableSmall.addCell(species);
				tableSmall.addCell(type);
				tableSmall.addCell(code);

				for (int i = 3; i < 15; i++)
				{
					double value = Double.parseDouble(shuttleReport[i]);
					PdfPCell cell = new PdfPCell(
							new Phrase(shuttleReport[i], contentFont));

					sumAll[i - 3] += value;
					sumType[i - 3] += value;

					cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
					tableSmall.addCell(cell);

					if (i == 7)
					{
						tableSmall.addCell(dash);
						tableSmall.addCell(dash);
					}
				}
			}

			PdfPCell cell = new PdfPCell(
					new Phrase("Jumlah " + previousType, titleFont));

			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);

			tableSmall.addCell(cell);
			tableSmall.addCell(empty);
			tableSmall.addCell(empty);
			tableSmall.addCell(empty);

			for (int i = 1; i < 12; i++)
			{
				if (i == 5)
				{
					tableSmall.addCell(dash2);
					tableSmall.addCell(dash2);
				}

				if (i == 7)
					tableSmall.addCell(empty);
				else
				{
					PdfPCell value = new PdfPCell(
							new Phrase(df.format(sumType[i]), titleFont));

					value.setBackgroundColor(BaseColor.LIGHT_GRAY);
					value.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

					tableSmall.addCell(value);
				}
			}

			cell = new PdfPCell(new Phrase("Jumlah Besar", titleFont));

			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);

			tableSmall.addCell(cell);
			tableSmall.addCell(empty);
			tableSmall.addCell(empty);
			tableSmall.addCell(empty);

			for (int i = 1; i < 12; i++)
			{
				if (i == 5)
				{
					tableSmall.addCell(dash2);
					tableSmall.addCell(dash2);
				}

				if (i == 7)
					tableSmall.addCell(empty);
				else
				{
					PdfPCell value = new PdfPCell(
							new Phrase(df.format(sumAll[i]), titleFont));

					value.setBackgroundColor(BaseColor.LIGHT_GRAY);
					value.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

					tableSmall.addCell(value);
				}
			}
		}

		document.add(titleBig);
		document.add(tableBig);

		document.newPage();

		document.add(titleSmall);
		document.add(tableSmall);

		document.close();
	}

	public static void main(String[] args)
	{
		try (HallFacade hFacade = new HallFacade();)
		{
			int month = 12;
			int year = 2019;
			
			ArrayList<String[]> bigSizeLogShuttleReport = hFacade
					.getBigSizeLogShuttleReport(month, year);
			ArrayList<String[]> smallSizeLogShuttleReport = hFacade
					.getSmallSizeLogShuttleReport(month, year);

			ShuttleReportGenerator.generate(new File("shuttlereport.pdf"),
					bigSizeLogShuttleReport, smallSizeLogShuttleReport, String.valueOf(month), String.valueOf(year));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}