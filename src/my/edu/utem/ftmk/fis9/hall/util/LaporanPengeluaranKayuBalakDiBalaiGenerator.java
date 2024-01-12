package my.edu.utem.ftmk.fis9.hall.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

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
public class LaporanPengeluaranKayuBalakDiBalaiGenerator
{
	private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 14,
			Font.BOLD | Font.UNDERLINE);
	private static Font strongFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.BOLD);
	private static Font smallerFont = new Font(Font.FontFamily.TIMES_ROMAN, 10,
			Font.BOLD);
	private static Font smallestFont = new Font(Font.FontFamily.TIMES_ROMAN, 6,
			Font.BOLD);
	private static Font contentFont = new Font(Font.FontFamily.TIMES_ROMAN, 10,
			Font.NORMAL);
	private static Font italicFont = new Font(Font.FontFamily.TIMES_ROMAN, 10,
			Font.ITALIC);

	public static void generate(File file, String[] information,
			ArrayList<String[]> data, Date startDate, Date endDate)
			throws Exception
	{
		Document document = new Document(PageSize.A4.rotate(), 28.35f, 28.35f,
				28.35f, 28.35f);

		PdfWriter.getInstance(document, new FileOutputStream(file));

		document.open();

		document.addSubject("Jabatan Perhutanan Negeri Sembilan");
		document.addKeywords("Laporan Pengeluaran Kayu Balak");
		document.addAuthor("JPNS");
		document.addCreator("JPNS");
		document.addTitle("LAPORAN PENGELUARAN KAYU BALAK");

		Paragraph title = new Paragraph(
				"LAPORAN PENGELUARAN KAYU BALAK DI BPJH " + information[0]
						+ "\n\n",
				titleFont);
		PdfPTable header = new PdfPTable(new float[] {0.33f, 0.02f, 0.65f});
		PdfPTable content = new PdfPTable(
				new float[] {0.09f, 0.07f, 0.07f, 0.07f, 0.07f, 0.07f, 0.07f,
						0.07f, 0.07f, 0.07f, 0.07f, 0.07f, 0.07f, 0.07f});
		PdfPTable footer = new PdfPTable(
				new float[] {0.05f, 0.65f, 0.05f, 0.05f, 0.1f, 0.1f});

		title.setAlignment(Paragraph.ALIGN_CENTER);

		header.setWidthPercentage(80f);
		content.setWidthPercentage(100f);
		header.setWidthPercentage(80f);

		header.getDefaultCell().setBorder(0);
		footer.getDefaultCell().setBorder(0);

		double area = Double.parseDouble(information[5]);
		double[] sum = new double[13];
		DecimalFormat df = new DecimalFormat("#,###,##0.00");

		PdfPCell headerLicenseNo = new PdfPCell(
				new Phrase(information[1], strongFont));
		PdfPCell headerDate = new PdfPCell(new Phrase(
				information[2] + " HINGGA " + information[3], strongFont));
		PdfPCell headerComptBlockNo = new PdfPCell(
				new Phrase(information[4], strongFont));
		PdfPCell headerArea = new PdfPCell(
				new Phrase(df.format(area) + " HEKTAR ("
						+ df.format(area * 2.471) + " EKAR)", strongFont));

		headerLicenseNo.setBorderWidth(0);
		headerDate.setBorderWidth(0);
		headerComptBlockNo.setBorderWidth(0);
		headerArea.setBorderWidth(0);

		headerLicenseNo.setBorderWidthBottom(0.5f);
		headerDate.setBorderWidthBottom(0.5f);
		headerComptBlockNo.setBorderWidthBottom(0.5f);
		headerArea.setBorderWidthBottom(0.5f);

		header.addCell(new Phrase("NO. LESEN", strongFont));
		header.addCell(new Phrase(":", strongFont));
		header.addCell(headerLicenseNo);
		header.addCell(new Phrase("TEMPOH KUATKUASA LESEN", strongFont));
		header.addCell(new Phrase(":", strongFont));
		header.addCell(headerDate);
		header.addCell(new Phrase("KAWASAN", strongFont));
		header.addCell(new Phrase(":", strongFont));
		header.addCell(headerComptBlockNo);
		header.addCell(new Phrase("KELUASAN", strongFont));
		header.addCell(new Phrase(":", strongFont));
		header.addCell(headerArea);

		Phrase small = new Phrase("PENGELUARAN KAYU JARAS (", smallerFont);
		Phrase smallBGT2 = new Phrase("JARAS BESAR\n", smallerFont);
		Phrase smallBLT2 = new Phrase("20 CM - < 30 CM\n< 2 M PANJANG",
				smallestFont);
		Phrase smallSGT2 = new Phrase("> 2 M PANJANG", smallestFont);
		Phrase smallSLT2 = new Phrase("< 2 M PANJANG", smallestFont);
		Phrase smallSO = new Phrase("5 CM - < 10 CM\n< 2 M PANJANG",
				smallestFont);

		small.add(new Phrase("PEREPANG DI PANGKAL", italicFont));
		small.add(new Phrase(")", smallerFont));
		smallBGT2.add(
				new Phrase("20 CM - < 30 CM\n> 2 M PANJANG", smallestFont));
		smallBGT2.add(new Phrase("\n(BATANG)", smallerFont));
		smallBLT2.add(new Phrase("\n(BATANG)", smallerFont));
		smallSGT2.add(new Phrase("\n(BATANG)", smallerFont));
		smallSLT2.add(new Phrase("\n(BATANG)", smallerFont));
		smallSO.add(new Phrase("\n(BATANG)", smallerFont));

		PdfPCell headerMonth = new PdfPCell(
				new Phrase("BULAN PENGE-LUARAN", smallerFont));
		PdfPCell headerVolume = new PdfPCell(
				new Phrase("METERPADU KAYU BALAK", smallerFont));
		PdfPCell headerDipterocarp = new PdfPCell(
				new Phrase("KAUM DAMAR", smallerFont));
		PdfPCell headerNonDipterocarp = new PdfPCell(
				new Phrase("KAUM BUKAN DAMAR", smallerFont));
		PdfPCell headerChengal = new PdfPCell(
				new Phrase("CHENGAL", smallerFont));
		PdfPCell headerLogRoyalty = new PdfPCell(
				new Phrase("ROYALTI KAYU BALAK (RM)", smallerFont));
		PdfPCell headerLogCess = new PdfPCell(
				new Phrase("SES KAYU BALAK (RM)", smallerFont));
		PdfPCell headerSmallRoyalty = new PdfPCell(
				new Phrase("ROYALTI KAYU JARAS (RM)", smallerFont));
		PdfPCell headerSmallCess = new PdfPCell(
				new Phrase("SES KAYU JARAS (RM)", smallerFont));
		PdfPCell headerSum = new PdfPCell(
				new Phrase("JUMLAH BESAR ROYALTI DAN SES (RM)", smallerFont));
		PdfPCell headerSmall = new PdfPCell(small);
		PdfPCell headerSmallBGT2 = new PdfPCell(smallBGT2);
		PdfPCell headerSmallS = new PdfPCell(
				new Phrase("JARAS KECIL", smallerFont));
		PdfPCell headerSmallBLT2 = new PdfPCell(smallBLT2);
		PdfPCell headerSmallSS = new PdfPCell(
				new Phrase("10 CM - < 20 CM", smallestFont));
		PdfPCell headerSmallSGT2 = new PdfPCell(smallSGT2);
		PdfPCell headerSmallSLT2 = new PdfPCell(smallSLT2);
		PdfPCell headerSmallSO = new PdfPCell(smallSO);
		PdfPCell contentStart = new PdfPCell(new Phrase(
				"PENGELUARAN BERMULA DARI " + information[6], italicFont));
		PdfPCell empty = new PdfPCell(new Phrase("-", contentFont));

		headerMonth.setRowspan(4);
		headerVolume.setColspan(3);
		headerDipterocarp.setRowspan(3);
		headerNonDipterocarp.setRowspan(3);
		headerChengal.setRowspan(3);
		headerLogRoyalty.setRowspan(4);
		headerLogCess.setRowspan(4);
		headerSmallRoyalty.setRowspan(4);
		headerSmallCess.setRowspan(4);
		headerSum.setRowspan(4);
		headerSmall.setColspan(5);
		headerSmallBGT2.setRowspan(3);
		headerSmallS.setColspan(4);
		headerSmallBLT2.setRowspan(2);
		headerSmallSS.setColspan(2);
		headerSmallSO.setRowspan(2);
		contentStart.setColspan(8);

		headerMonth.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		headerVolume.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		headerDipterocarp.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		headerNonDipterocarp.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		headerChengal.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		headerLogRoyalty.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		headerLogCess.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		headerSmallRoyalty.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		headerSmallCess.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		headerSum.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		headerSmall.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		headerSmallBGT2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		headerSmallS.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		headerSmallBLT2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		headerSmallSS.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		headerSmallSO.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		contentStart.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		empty.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		headerMonth.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		headerVolume.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		headerDipterocarp.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		headerNonDipterocarp.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		headerChengal.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		headerLogRoyalty.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		headerLogCess.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		headerSmallRoyalty.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		headerSmallCess.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		headerSum.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		headerSmall.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		headerSmallBGT2.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		headerSmallS.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		headerSmallBLT2.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		headerSmallSS.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		headerSmallSO.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);

		content.addCell(headerMonth);
		content.addCell(headerVolume);
		content.addCell(headerLogRoyalty);
		content.addCell(headerLogCess);
		content.addCell(headerSmallRoyalty);
		content.addCell(headerSmallCess);
		content.addCell(headerSum);
		content.addCell(headerSmall);
		content.addCell(headerDipterocarp);
		content.addCell(headerNonDipterocarp);
		content.addCell(headerChengal);
		content.addCell(headerSmallBGT2);
		content.addCell(headerSmallS);
		content.addCell(headerSmallBLT2);
		content.addCell(headerSmallSS);
		content.addCell(headerSmallSO);
		content.addCell(headerSmallSGT2);
		content.addCell(headerSmallSLT2);
		content.addCell(contentStart);
		content.completeRow();

		for (String[] row : data)
		{
			content.addCell(new Phrase(row[0], contentFont));

			for (int i = 1; i < 14; i++)
			{
				double value = Double.parseDouble(row[i]);

				if (value == 0)
					content.addCell(empty);
				else
				{
					sum[i - 1] += value;
					PdfPCell cell = new PdfPCell(
							new Phrase(i > 8 ? String.valueOf((int) value)
									: df.format(value), contentFont));

					cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
					content.addCell(cell);
				}
			}
		}

		content.addCell(new Phrase("JUMLAH", smallerFont));

		for (int i = 0; i < 13; i++)
		{
			PdfPCell cell = new PdfPCell(
					new Phrase(i > 7 ? String.valueOf((int) sum[i])
							: df.format(sum[i]), smallerFont));

			cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			content.addCell(cell);
		}

		double payment = Double.parseDouble(information[10]);
		double balance = Double.parseDouble(information[7]);
		PdfPCell footerSummary = new PdfPCell(
				new Phrase("RINGKASANNYA:", smallerFont));
		PdfPCell footerRoyalty = new PdfPCell(
				new Phrase(df.format(sum[3]), smallerFont));
		PdfPCell footerCess = new PdfPCell(
				new Phrase(df.format(sum[4]), smallerFont));
		PdfPCell footerBig = new PdfPCell(
				new Phrase("JUMLAH BESAR", contentFont));
		PdfPCell footerEqual = new PdfPCell(new Phrase("=", smallerFont));
		PdfPCell footerRM = new PdfPCell(new Phrase("RM", smallerFont));
		PdfPCell footerSum = new PdfPCell(
				new Phrase(df.format(sum[3] + sum[4]), smallerFont));
		PdfPCell footerVolume = new PdfPCell(
				new Phrase(df.format(sum[0] + sum[1] + sum[2]), smallerFont));
		PdfPCell footerLong = new PdfPCell(new Phrase(
				String.valueOf((int) (sum[8] + sum[10])), smallerFont));
		PdfPCell footerShort = new PdfPCell(
				new Phrase(String.valueOf((int) (sum[9] + sum[11] + sum[12])),
						smallerFont));
		PdfPCell footerBreak = new PdfPCell(new Phrase(" ", smallerFont));
		PdfPCell footerPayment = new PdfPCell(
				new Phrase(df.format(payment), smallerFont));		
		PdfPCell footerIncome = new PdfPCell(
				new Phrase(df.format(sum[3] + sum[4] + sum[5] + sum[6]), smallerFont));
		PdfPCell footerBalance = new PdfPCell(
				new Phrase(df.format(balance), smallerFont));
		PdfPCell footerCount = new PdfPCell(
				new Phrase(information[9], smallerFont));
		Phrase blank = new Phrase(" ", smallerFont);

		footerRoyalty.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		footerCess.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		footerSum.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		footerPayment.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		footerIncome.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		footerBalance.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

		footerVolume.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		footerLong.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		footerShort.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		footerCount.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		footerRoyalty.setBorder(0);
		footerCess.setBorder(0);

		footerVolume.setBorderWidth(0);
		footerLong.setBorderWidth(0);
		footerShort.setBorderWidth(0);
		footerPayment.setBorderWidth(0);
		footerIncome.setBorderWidth(0);
		footerBalance.setBorderWidth(0);
		footerCount.setBorderWidth(0);

		footerVolume.setBorderWidthBottom(0.5f);
		footerLong.setBorderWidthBottom(0.5f);
		footerShort.setBorderWidthBottom(0.5f);
		footerPayment.setBorderWidthBottom(0.5f);
		footerIncome.setBorderWidthBottom(0.5f);
		footerBalance.setBorderWidthBottom(0.5f);
		footerCount.setBorderWidthBottom(0.5f);

		footerSummary.setColspan(6);
		footerSummary.setBorderWidth(0);
		footerSummary.setBorderWidthBottom(0.5f);

		footerBig.setBorderWidth(0);
		footerBig.setBorderWidthTop(0.5f);
		footerBig.setBorderWidthBottom(0.5f);

		footerEqual.setBorderWidth(0);
		footerEqual.setBorderWidthTop(0.5f);
		footerEqual.setBorderWidthBottom(0.5f);

		footerRM.setBorderWidth(0);
		footerRM.setBorderWidthTop(0.5f);
		footerRM.setBorderWidthBottom(0.5f);

		footerSum.setBorderWidth(0);
		footerSum.setBorderWidthTop(0.5f);
		footerSum.setBorderWidthBottom(0.5f);

		footerBreak.setColspan(6);
		footerBreak.setBorderWidth(0);
		footerBreak.setBorderWidthBottom(0.5f);

		footer.addCell(footerSummary);
		footer.addCell(blank);
		footer.completeRow();
		footer.addCell(new Phrase("1.", contentFont));
		footer.addCell(new Phrase("JUMLAH ROYALTI KAYU BALAK", contentFont));
		footer.addCell(new Phrase("=", smallerFont));
		footer.addCell(new Phrase("RM", smallerFont));
		footer.addCell(footerRoyalty);
		footer.addCell(blank);
		footer.addCell(new Phrase("2.", contentFont));
		footer.addCell(new Phrase("JUMLAH SES KAYU BALAK", contentFont));
		footer.addCell(new Phrase("=", smallerFont));
		footer.addCell(new Phrase("RM", smallerFont));
		footer.addCell(footerCess);
		footer.addCell(blank);
		footer.addCell(blank);
		footer.addCell(footerBig);
		footer.addCell(footerEqual);
		footer.addCell(footerRM);
		footer.addCell(footerSum);
		footer.addCell(blank);
		footer.addCell(blank);
		footer.completeRow();
		footer.addCell(new Phrase("3.", contentFont));
		footer.addCell(new Phrase("JUMLAH METERPADU KAYU BALAK", contentFont));
		footer.addCell(new Phrase("=", smallerFont));
		footer.addCell(blank);
		footer.addCell(footerVolume);
		footer.addCell(new Phrase(" M\u00B3", smallerFont));
		footer.addCell(new Phrase("4.", contentFont));
		footer.addCell(new Phrase(
				"JUMLAH KESELURUHAN BILANGAN KAYU JARAS PANJANG", contentFont));
		footer.addCell(new Phrase("=", smallerFont));
		footer.addCell(blank);
		footer.addCell(footerLong);
		footer.addCell(new Phrase(" BATANG", smallerFont));
		footer.addCell(new Phrase("5.", contentFont));
		footer.addCell(new Phrase(
				"JUMLAH KESELURUHAN BILANGAN KAYU JARAS PENDEK", contentFont));
		footer.addCell(new Phrase("=", smallerFont));
		footer.addCell(blank);
		footer.addCell(footerShort);
		footer.addCell(new Phrase(" BATANG", smallerFont));
		footer.addCell(footerBreak);
		footer.addCell(blank);
		footer.completeRow();
		footer.addCell(new Phrase("A)", contentFont));
		footer.addCell(
				new Phrase("BAYARAN CUKAI DENGAN RESIT (SURAT PEMBERITAHUAN)",
						contentFont));
		footer.addCell(new Phrase("=", smallerFont));
		footer.addCell(new Phrase("RM", smallerFont));
		footer.addCell(footerPayment);
		footer.addCell(blank);
		footer.addCell(new Phrase("B)", contentFont));
		footer.addCell(
				new Phrase("JUMLAH KUTIPAN ROYALTI DAN SES", contentFont));
		footer.addCell(new Phrase("=", smallerFont));
		footer.addCell(new Phrase("RM", smallerFont));
		footer.addCell(footerIncome);
		footer.addCell(blank);
		footer.addCell(new Phrase("C)", contentFont));
		footer.addCell(new Phrase("BAKI CUKAI SEHINGGA " + information[8],
				contentFont));
		footer.addCell(new Phrase("=", smallerFont));
		footer.addCell(new Phrase("RM", smallerFont));
		footer.addCell(footerBalance);
		footer.addCell(blank);
		footer.addCell(new Phrase("D)", contentFont));
		footer.addCell(new Phrase("JUMLAH PAS PEMINDAH", contentFont));
		footer.addCell(new Phrase("=", smallerFont));
		footer.addCell(blank);
		footer.addCell(footerCount);
		footer.addCell(new Phrase(" HELAI", smallerFont));

		document.add(title);
		document.add(header);
		document.add(new Paragraph("\n", titleFont));
		document.add(content);
		document.add(new Paragraph("\n", titleFont));
		document.add(footer);

		document.close();
	}

	public static void main(String[] args)
	{
		try (HallFacade hFacade = new HallFacade();)
		{
			Date startDate = new Date();
			Date endDate = new Date();
			int licenseID = 0;
			String[] information = new String[10];
			ArrayList<String[]> data = hFacade
					.getLaporanPengeluaranKayuBalakDiBalai(startDate, endDate,
							licenseID);

			information[0] = "(SEMENTARA) LENGGENG"; 
			information[1] = "NB/01/12/2017 JEMARI SELEKSI SDN BHD"; 
			information[2] = "15/08/2017"; 
			information[3] = "14/08/2018"; 
			information[4] = "KPT 27, HUTAN SIMPAN LENGGENG, SEREMBAN"; 
			information[5] = "102.03"; 
			information[6] = "25/10/2017"; 
			information[7] = "185000"; 
			information[8] = "25/10/2017"; 
			information[9] = "191"; 

			LaporanPengeluaranKayuBalakDiBalaiGenerator.generate(
					new File("timberouput.pdf"), information, data, startDate,
					endDate);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}