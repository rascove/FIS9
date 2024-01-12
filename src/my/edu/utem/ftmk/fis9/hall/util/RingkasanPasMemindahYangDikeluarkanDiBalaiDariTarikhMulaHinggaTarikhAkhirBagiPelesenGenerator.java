package my.edu.utem.ftmk.fis9.hall.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import my.edu.utem.ftmk.fis9.hall.controller.manager.HallFacade;

/**
 * @author Nor Azman Bin Mat Ariff
 */
public class RingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenGenerator
{
	private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.BOLD);
	private static Font italicFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.BOLDITALIC);
	private static Font smallerFont = new Font(Font.FontFamily.TIMES_ROMAN, 9,
			Font.BOLD);
	private static Font contentFont = new Font(Font.FontFamily.TIMES_ROMAN, 9,
			Font.NORMAL);

	public static void generate(File file, String[] headerInfo,
			ArrayList<String[]> smallLogs, ArrayList<String[]> bigLogs,
			ArrayList<String[]> bigJaras, ArrayList<String[]> smallJaras,
			ArrayList<String[]> ringkasanPengeluaranTableBigLog, ArrayList<String[]> ringkasanPengeluaranTableSmallLog,
			String[] ringkasanPembayaranTableBigLog, String[] ringkasanPembayaranTableSmallLog, String[][] page2, String[] infoLain, Date startDate,
			Date endDate, long licenseID) throws Exception
	{
		Document document = new Document(PageSize.A4, 28.35f, 28.35f, 28.35f,
				28.35f);

		PdfWriter.getInstance(document, new FileOutputStream(file));

		document.open();

		document.addSubject("Jabatan Perhutanan Negeri Sembilan");
		document.addKeywords(
				"Ringkasan Pas Memindah Yang Dikeluarkan Di Balai");
		document.addAuthor("JPNS");
		document.addCreator("JPNS");
		document.addTitle("RINGKASAN PAS MEMINDAH YANG DIKELUARKAN DI BALAI");

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Paragraph id = new Paragraph("BORANG RPMI",
				new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.NORMAL));
		Paragraph titleBig = new Paragraph(
				"NEGERI SEMBILAN DARUL KHUSUS\nRINGKASAN PAS MEMINDAH YANG DIKELUARKAN\nDI BALAI PEMERIKSA HUTAN "
						+ headerInfo[2] + " DARI " + sdf.format(startDate)
						+ " HINGGA " + sdf.format(endDate) + "\nNO. LESEN: ",
						titleFont);
		Paragraph titleSmall = new Paragraph(
				"RINGKASAN PAS MEMINDAH YANG DIKELUARKAN\nDI BALAI PEMERIKSA HUTAN "
						+ headerInfo[2] + " DARI " + sdf.format(startDate)
						+ " HINGGA " + sdf.format(endDate) + "\nNO. LESEN: ",
						titleFont);
		Paragraph titleBigJ = new Paragraph(
				"RINGKASAN PAS MEMINDAH YANG DIKELUARKAN\nDI BALAI PEMERIKSA HUTAN "
						+ headerInfo[2] + " DARI " + sdf.format(startDate)
						+ " HINGGA " + sdf.format(endDate) + "\nNO. LESEN: ",
						titleFont);
		Paragraph titleSmallJ = new Paragraph("\n2. JENIS PENGELUARAN: ", titleFont);
		PdfPTable contentBig = new PdfPTable(new float[] {0.13f, 0.09f, 0.09f,
				0.09f, 0.09f, 0.02f, 0.13f, 0.09f, 0.09f, 0.09f, 0.09f});
		PdfPTable contentSmall = new PdfPTable(new float[] {0.13f, 0.09f, 0.09f,
				0.09f, 0.09f, 0.02f, 0.13f, 0.09f, 0.09f, 0.09f, 0.09f});
		PdfPTable contentBigJ = new PdfPTable(new float[] {0.13f, 0.09f, 0.09f,
				0.09f, 0.09f, 0.02f, 0.13f, 0.09f, 0.09f, 0.09f, 0.09f});
		PdfPTable contentSmallJ = new PdfPTable(new float[] {0.18f, 0.18f, 0.16f,
				0.16f, 0.16f, 0.16f});
		PdfPTable approval = new PdfPTable(new float[] {0.15f, 0.34f, 0.02f,
				0.15f, 0.34f});

		int p2l = page2.length, p2w = page2[0].length, sheet = 0;
		String finalBalance = page2[p2l - 1][p2w - 1];
		HashSet<Integer> indices = new HashSet<>();

		for (int i = 3; i < page2.length; i++)
		{
			String[] row = page2[i];

			if (row[1] != null)
			{
				sheet++;

				for (int j = 14; j > 5; j--)
				{
					double value = Double.parseDouble(row[p2w - j]);

					if (value != 0)
						indices.add(p2w - j);
				}
			}
		}
		
		titleBig.add(new Phrase(headerInfo[0] + " (" + headerInfo[1] + ")",
				italicFont));
		titleBig.add(
				new Phrase("\nJENIS PENGELUARAN: KAYU BALAK - ", titleFont));
		titleBig.add(new Phrase("BALAK BESAR > 45 CM\n\n", italicFont));

		titleSmall.add(new Phrase(headerInfo[0] + " (" + headerInfo[1] + ")",
				italicFont));
		titleSmall.add(
				new Phrase("\nJENIS PENGELUARAN: KAYU BALAK - ", titleFont));
		titleSmall.add(new Phrase("BALAK KECIL < 45 CM\n\n", italicFont));

		titleBigJ.add(new Phrase(headerInfo[0] + " (" + headerInfo[1] + ")",
				italicFont));
		titleBigJ.add(
				new Phrase("\n1. JENIS PENGELUARAN: ", titleFont));
		titleBigJ.add(
				new Phrase("KAYU JARAS BESAR", italicFont));
		titleBigJ.add(new Phrase("\n(PEREPANG PANGKAL 20 CM - < 30 CM DAN PANJANG > 2 M)\n\n", titleFont));

		titleSmallJ.add(
				new Phrase("KAYU JARAS KECIL\n\n", italicFont));

		Paragraph balance = new Paragraph("BAKI CUKAI SEHINGGA ", contentFont);
		Paragraph count = new Paragraph("BILANGAN PAS MEMINDAH: " + sheet + " HELAI", contentFont);
		
		balance.add(new Phrase(sdf.format(endDate), smallerFont));
		balance.add(new Phrase(": ", contentFont));
		balance.add(new Phrase("RM " + finalBalance, smallerFont));

		id.setAlignment(Paragraph.ALIGN_RIGHT);
		titleBig.setAlignment(Paragraph.ALIGN_CENTER);
		titleSmall.setAlignment(Paragraph.ALIGN_CENTER);
		titleBigJ.setAlignment(Paragraph.ALIGN_CENTER);
		titleSmallJ.setAlignment(Paragraph.ALIGN_CENTER);

		PdfPCell headerType = new PdfPCell(
				new Phrase("JENIS KAYU BALAK", smallerFont));
		PdfPCell headerVolume = new PdfPCell(
				new Phrase("ISIPADU (M\u00B3)", smallerFont));
		PdfPCell headerRate = new PdfPCell(
				new Phrase("KADAR ROYALTI (RM/M\u00B3)", smallerFont));
		PdfPCell headerRoyalty = new PdfPCell(
				new Phrase("JUMLAH ROYALTI (RM)", smallerFont));
		PdfPCell headerCess = new PdfPCell(
				new Phrase("JUMLAH SES (RM)", smallerFont));
		PdfPCell headerTypeJ = new PdfPCell(
				new Phrase("JENIS KAYU JARAS", smallerFont));
		PdfPCell headerAmount = new PdfPCell(
				new Phrase("BIL. BATANG", smallerFont));
		PdfPCell headerSize = new PdfPCell(
				new Phrase("SAIZ PEREPANG DI PANGKAL", smallerFont));
		PdfPCell headerLength = new PdfPCell(
				new Phrase("PANJANG", smallerFont));

		PdfPCell gap = new PdfPCell(new Phrase(" ", contentFont));

		gap.setBorder(0);

		headerType.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		headerVolume.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		headerRate.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		headerRoyalty.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		headerCess.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		headerTypeJ.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		headerAmount.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		headerSize.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		headerLength.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		headerType.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		headerVolume.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		headerRate.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		headerRoyalty.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		headerCess.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		headerTypeJ.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		headerAmount.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		headerSize.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		headerLength.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);

		contentBig.setWidthPercentage(100f);
		contentSmall.setWidthPercentage(100f);
		contentBigJ.setWidthPercentage(100f);
		contentSmallJ.setWidthPercentage(100f);
		approval.setWidthPercentage(100f);

		contentBig.addCell(headerType);
		contentBig.addCell(headerVolume);
		contentBig.addCell(headerRate);
		contentBig.addCell(headerRoyalty);
		contentBig.addCell(headerCess);
		contentBig.addCell(gap);
		contentBig.addCell(headerType);
		contentBig.addCell(headerVolume);
		contentBig.addCell(headerRate);
		contentBig.addCell(headerRoyalty);
		contentBig.addCell(headerCess);

		contentSmall.addCell(headerType);
		contentSmall.addCell(headerVolume);
		contentSmall.addCell(headerRate);
		contentSmall.addCell(headerRoyalty);
		contentSmall.addCell(headerCess);
		contentSmall.addCell(gap);
		contentSmall.addCell(headerType);
		contentSmall.addCell(headerVolume);
		contentSmall.addCell(headerRate);
		contentSmall.addCell(headerRoyalty);
		contentSmall.addCell(headerCess);

		contentBigJ.addCell(headerTypeJ);
		contentBigJ.addCell(headerAmount);
		contentBigJ.addCell(headerRate);
		contentBigJ.addCell(headerRoyalty);
		contentBigJ.addCell(headerCess);
		contentBigJ.addCell(gap);
		contentBigJ.addCell(headerTypeJ);
		contentBigJ.addCell(headerAmount);
		contentBigJ.addCell(headerRate);
		contentBigJ.addCell(headerRoyalty);
		contentBigJ.addCell(headerCess);

		contentSmallJ.addCell(headerSize);
		contentSmallJ.addCell(headerLength);
		contentSmallJ.addCell(headerAmount);
		contentSmallJ.addCell(headerRate);
		contentSmallJ.addCell(headerRoyalty);
		contentSmallJ.addCell(headerCess);

		approval.getDefaultCell().setBorder(0);
		approval.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

		double[] sumBig = new double[4];
		double[] sumSmall = new double[4];
		double[] sumBigJ = new double[4];
		double[] sumSmallJ = new double[4];
		double[][] sumAllBigLog = new double[3][3];
		double[][] sumAllSmallLog = new double[3][3];
		DecimalFormat df = new DecimalFormat("#,###,##0.00");

		PdfPCell headerItem = new PdfPCell(new Phrase("PERKARA", smallerFont));
		PdfPCell headerLog = new PdfPCell(new Phrase("BALAK", smallerFont));
		PdfPCell headerBigJ = new PdfPCell(new Phrase("JARAS B.", smallerFont));
		PdfPCell headerSmallJ = new PdfPCell(
				new Phrase("JARAS K.", smallerFont));
		PdfPCell dash = new PdfPCell(new Phrase("-", smallerFont));

		headerItem.setColspan(2);

		headerItem.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		headerLog.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		headerBigJ.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		headerSmallJ.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		dash.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		int lengthBig = bigLogs.size(), lengthSmall = smallLogs.size(),
				lengthBigJ = bigJaras.size(), halfBig = (lengthBig + 1) / 2,
				halfSmall = (lengthSmall + 1) / 2, halfBigJ = (lengthBigJ + 1) / 2;
		boolean oddBig = lengthBig % 2 == 1, oddSmall = lengthSmall % 2 == 1, oddBigJ = lengthBigJ % 2 == 1;

		for (int i = 0; i < halfBig; i++)
		{
			String[] rowBigLeft = bigLogs.get(i);

			contentBig.addCell(new Phrase(rowBigLeft[0], contentFont));

			for (int j = 0; j < 4; j++)
			{
				double valueBig = rowBigLeft[j + 1] == null ? 0
						: Double.parseDouble(rowBigLeft[j + 1]);
				PdfPCell cellBig = new PdfPCell(
						new Phrase(df.format(valueBig), contentFont));
				sumBig[j] += valueBig;

				cellBig.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				contentBig.addCell(cellBig);
			}

			contentBig.addCell(gap);

			if (i < halfBig - 1 || !oddBig)
			{
				String[] rowBigRight = bigLogs.get(i + halfBig);

				contentBig.addCell(new Phrase(rowBigRight[0], contentFont));

				for (int j = 0; j < 4; j++)
				{
					double valueBig = rowBigRight[j + 1] == null ? 0
							: Double.parseDouble(rowBigRight[j + 1]);
					PdfPCell cellBig = new PdfPCell(
							new Phrase(df.format(valueBig), contentFont));
					sumBig[j] += valueBig;

					cellBig.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
					contentBig.addCell(cellBig);
				}
			}
			else
			{
				PdfPCell sum = new PdfPCell(new Phrase("JUMLAH", smallerFont));

				sum.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				contentBig.addCell(sum);

				for (int j = 0; j < 4; j++)
				{
					PdfPCell cellBig = new PdfPCell(new Phrase(
							j != 1 ? df.format(sumBig[j]) : "", smallerFont));

					cellBig.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
					contentBig.addCell(cellBig);
				}
			}
		}

		for (int i = 0; i < halfSmall; i++)
		{
			String[] rowSmallLeft = smallLogs.get(i);

			contentSmall.addCell(new Phrase(rowSmallLeft[0], contentFont));

			for (int j = 0; j < 4; j++)
			{
				double valueSmall = rowSmallLeft[j + 1] == null ? 0
						: Double.parseDouble(rowSmallLeft[j + 1]);
				PdfPCell cellSmall = new PdfPCell(
						new Phrase(df.format(valueSmall), contentFont));
				sumSmall[j] += valueSmall;

				cellSmall.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				contentSmall.addCell(cellSmall);
			}

			contentSmall.addCell(gap);

			if (i < halfSmall - 1 || !oddSmall)
			{
				String[] rowSmallRight = smallLogs.get(i + halfSmall);

				contentSmall.addCell(new Phrase(rowSmallRight[0], contentFont));

				for (int j = 0; j < 4; j++)
				{
					double valueSmall = rowSmallRight[j + 1] == null ? 0
							: Double.parseDouble(rowSmallRight[j + 1]);
					PdfPCell cellSmall = new PdfPCell(
							new Phrase(df.format(valueSmall), contentFont));
					sumSmall[j] += valueSmall;

					cellSmall.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
					contentSmall.addCell(cellSmall);
				}
			}
			else
			{
				PdfPCell sum = new PdfPCell(new Phrase("JUMLAH", smallerFont));

				sum.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				contentSmall.addCell(sum);

				for (int j = 0; j < 4; j++)
				{
					PdfPCell cellSmall = new PdfPCell(new Phrase(
							j != 1 ? df.format(sumSmall[j]) : "", smallerFont));

					cellSmall.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
					contentSmall.addCell(cellSmall);
				}
			}
		}

		for (int i = 0; i < halfBigJ; i++)
		{
			String[] rowBigLeft = bigJaras.get(i);

			contentBigJ.addCell(new Phrase(rowBigLeft[0], contentFont));

			for (int j = 0; j < 4; j++)
			{
				double valueBig = rowBigLeft[j + 1] == null ? 0
						: Double.parseDouble(rowBigLeft[j + 1]);
				PdfPCell cellBig = new PdfPCell(
						new Phrase(j == 0 ? String.valueOf((int) valueBig) : df.format(valueBig), contentFont));
				sumBigJ[j] += valueBig;

				cellBig.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				contentBigJ.addCell(cellBig);
			}

			contentBigJ.addCell(gap);

			if (i < halfBigJ - 1 || !oddBigJ)
			{
				String[] rowBigRight = bigJaras.get(i + halfBigJ);

				contentBigJ.addCell(new Phrase(rowBigRight[0], contentFont));

				for (int j = 0; j < 4; j++)
				{
					double valueBig = rowBigRight[j + 1] == null ? 0
							: Double.parseDouble(rowBigRight[j + 1]);
					PdfPCell cellBig = new PdfPCell(
							new Phrase(j == 0 ? String.valueOf((int) valueBig) : df.format(valueBig), contentFont));
					sumBigJ[j] += valueBig;

					cellBig.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
					contentBigJ.addCell(cellBig);
				}
			}
			else
			{
				PdfPCell sum = new PdfPCell(new Phrase("JUMLAH", smallerFont));

				sum.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				contentBigJ.addCell(sum);

				for (int j = 0; j < 4; j++)
				{
					PdfPCell cellBig = new PdfPCell(new Phrase(
							j != 1 ? df.format(sumBigJ[j]) : "", smallerFont));

					cellBig.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
					contentBigJ.addCell(cellBig);
				}
			}
		}

		int lengthSmallJ = smallJaras.size(), index = 0;

		contentSmallJ.addCell(new Phrase("20 CM - < 30 CM", contentFont));

		index = setSmallJarasRow(contentSmallJ, "i) < 2 M", "1", index, lengthSmallJ, smallJaras, sumSmallJ, df);
		PdfPCell size10_20 = new PdfPCell(new Phrase("10 CM - < 20 CM", contentFont));
		PdfPCell size5_10 = new PdfPCell(new Phrase("5 CM - < 10 CM", contentFont));

		size10_20.setRowspan(3);
		size5_10.setRowspan(3);

		contentSmallJ.addCell(size10_20);

		index = setSmallJarasRow(contentSmallJ, "i) < 2 M", "2", index, lengthSmallJ, smallJaras, sumSmallJ, df);
		index = setSmallJarasRow(contentSmallJ, "ii) 2 M - < 5 M", "3", index, lengthSmallJ, smallJaras, sumSmallJ, df);
		index = setSmallJarasRow(contentSmallJ, "iii) > 5 M", "4", index, lengthSmallJ, smallJaras, sumSmallJ, df);

		contentSmallJ.addCell(size5_10);

		index = setSmallJarasRow(contentSmallJ, "i) < 2 M", "5", index, lengthSmallJ, smallJaras, sumSmallJ, df);
		index = setSmallJarasRow(contentSmallJ, "ii) 2 M - < 5 M", "6", index, lengthSmallJ, smallJaras, sumSmallJ, df);
		index = setSmallJarasRow(contentSmallJ, "iii) > 5 M", "7", index, lengthSmallJ, smallJaras, sumSmallJ, df);

		contentSmallJ.addCell(new Phrase("20 CM - < 30 CM", contentFont));
		setSmallJarasRow(contentSmallJ, "SEMUA", "8", index, lengthSmallJ, smallJaras, sumSmallJ, df);

		PdfPCell smallJ = new PdfPCell(new Phrase("JUMLAH", smallerFont));

		smallJ.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		smallJ.setColspan(2);

		contentSmallJ.addCell(smallJ);

		for (int j = 0; j < 4; j++)
		{
			PdfPCell cell = new PdfPCell(new Phrase(
					j != 1 ? (j == 0 ? String.valueOf((int) sumSmallJ[0]) : df.format(sumSmallJ[j])) : "", smallerFont));

			cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			contentSmallJ.addCell(cell);
		}

		if (!oddBig)
		{
			for (int i = 0; i < 6; i++)
				contentBig.addCell(gap);

			PdfPCell sum = new PdfPCell(new Phrase("JUMLAH", smallerFont));

			sum.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			contentBig.addCell(sum);

			for (int j = 0; j < 4; j++)
			{
				PdfPCell cellBig = new PdfPCell(new Phrase(
						j != 1 ? df.format(sumBig[j]) : "", smallerFont));

				cellBig.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				contentBig.addCell(cellBig);
			}
		}

		if (!oddSmall)
		{
			for (int i = 0; i < 6; i++)
				contentSmall.addCell(gap);

			PdfPCell sum = new PdfPCell(new Phrase("JUMLAH", smallerFont));

			sum.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			contentSmall.addCell(sum);

			for (int j = 0; j < 4; j++)
			{
				PdfPCell cellSmall = new PdfPCell(new Phrase(
						j != 1 ? df.format(sumSmall[j]) : "", smallerFont));

				cellSmall.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				contentSmall.addCell(cellSmall);
			}
		}

		if (!oddBigJ)
		{
			for (int i = 0; i < 6; i++)
				contentBigJ.addCell(gap);

			PdfPCell sum = new PdfPCell(new Phrase("JUMLAH", smallerFont));

			sum.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			contentBigJ.addCell(sum);

			for (int j = 0; j < 4; j++)
			{
				PdfPCell cellBig = new PdfPCell(new Phrase(
						j != 1 ? df.format(sumBigJ[j]) : "", smallerFont));

				cellBig.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				contentBigJ.addCell(cellBig);
			}
		}

		for (int i = 0; i < 11; i++)
		{
			contentBig.addCell(gap);
			contentSmall.addCell(gap);
		}

		for (int i = 0; i < 2; i++)
		{
			if (i == 1)
			{
				contentBig.addCell(gap);
				contentSmall.addCell(gap);
			}

			PdfPCell header = new PdfPCell(new Phrase(
					"RINGKASAN " + (i == 0 ? "PENGELUARAN" : "PEMBAYARAN"),
					smallerFont));

			header.setBorder(0);
			header.setColspan(5);
			header.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			contentBig.addCell(header);
			contentSmall.addCell(header);
		}

		for (int i = 0; i < 2; i++)
		{
			if (i == 1)
			{
				contentBig.addCell(gap);
				contentSmall.addCell(gap);
			}

			contentBig.addCell(headerItem);
			contentBig.addCell(headerLog);
			contentBig.addCell(headerBigJ);
			contentBig.addCell(headerSmallJ);
			contentSmall.addCell(headerItem);
			contentSmall.addCell(headerLog);
			contentSmall.addCell(headerBigJ);
			contentSmall.addCell(headerSmallJ);
		}

		PdfPCell empty = new PdfPCell(new Phrase("", smallerFont));
		PdfPCell volume = new PdfPCell(new Phrase("1. Isipadu (m\u00B3)", smallerFont));
		PdfPCell royalty = new PdfPCell(new Phrase("2. Kutipan Royalti", smallerFont));
		PdfPCell cess = new PdfPCell(new Phrase("3. Kutipan Ses", smallerFont));
		PdfPCell others = new PdfPCell(new Phrase("4. Lain-lain Bayaran", smallerFont));

		volume.setColspan(2);
		royalty.setColspan(2);
		cess.setColspan(2);
		others.setColspan(2);

		contentBig.addCell(volume);
		contentSmall.addCell(volume);

		for (int j = 0; j < 3; j++)
		{
			PdfPCell cell = new PdfPCell(new Phrase(
					j == 0 ? "M\u00B3" : " BTG.", smallerFont));

			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			contentBig.addCell(cell);
			contentSmall.addCell(cell);
		}

		contentBig.addCell(gap);
		contentSmall.addCell(gap);

		contentBig.addCell(royalty);
		contentSmall.addCell(royalty);

		for (int j = 0; j < 3; j++)
		{
			PdfPCell cell = new PdfPCell(
					new Phrase("(RM)", smallerFont));

			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			contentBig.addCell(cell);
			contentSmall.addCell(cell);
		}

		PdfPCell cell11 = new PdfPCell(new Phrase(" 1.1 Sebelum", contentFont));
		PdfPCell cell21 = new PdfPCell(new Phrase(" 2.1 Sebelum", contentFont));

		cell11.setColspan(2);
		cell21.setColspan(2);

		contentBig.addCell(cell11);
		contentSmall.addCell(cell11);

		for (int j = 0; j < 3; j++)
		{
			if (j == 0)
			{
				double vBigLog = Double.parseDouble(ringkasanPengeluaranTableBigLog.get(0)[1]);
				double vSmallLog = Double.parseDouble(ringkasanPengeluaranTableSmallLog.get(0)[1]);
				PdfPCell valueBigLog = new PdfPCell(new Phrase(df.format(vBigLog), contentFont));
				PdfPCell valueSmallLog = new PdfPCell(new Phrase(df.format(vSmallLog), contentFont));
				sumAllBigLog[0][0] += vBigLog;
				sumAllSmallLog[0][0] += vSmallLog;

				valueBigLog.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				valueSmallLog.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				contentBig.addCell(valueBigLog);
				contentSmall.addCell(valueSmallLog);
			}
			else
			{
				contentBig.addCell(empty);
				contentSmall.addCell(empty);
			}
		}

		contentBig.addCell(gap);
		contentSmall.addCell(gap);

		contentBig.addCell(cell21);
		contentSmall.addCell(cell21);

		for (int j = 0; j < 3; j++)
		{
			if (j == 0)
			{
				double vBigLog = Double.parseDouble(ringkasanPembayaranTableBigLog[0]);
				double vSmallLog = Double.parseDouble(ringkasanPembayaranTableSmallLog[0]);
				PdfPCell valueBigLog = new PdfPCell(new Phrase(df.format(vBigLog), contentFont));
				PdfPCell valueSmallLog = new PdfPCell(new Phrase(df.format(vSmallLog), contentFont));
				sumAllBigLog[1][0] += vBigLog;
				sumAllSmallLog[1][0] += vSmallLog;

				valueBigLog.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				valueSmallLog.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				contentBig.addCell(valueBigLog);
				contentSmall.addCell(valueSmallLog);
			}
			else
			{
				contentBig.addCell(empty);
				contentSmall.addCell(empty);
			}
		}

		PdfPCell cell12 = new PdfPCell(new Phrase(" 1.2 Semasa", contentFont));
		PdfPCell cell22 = new PdfPCell(new Phrase(" 2.2 Semasa", contentFont));

		cell12.setColspan(2);
		cell22.setColspan(2);

		contentBig.addCell(cell12);
		contentSmall.addCell(cell12);

		for (int j = 0; j < 3; j++)
		{
			if (j == 0)
			{
				double vBigLog = Double.parseDouble(ringkasanPengeluaranTableBigLog.get(1)[1]);
				double vSmallLog = Double.parseDouble(ringkasanPengeluaranTableSmallLog.get(1)[1]);
				PdfPCell valueBigLog = new PdfPCell(new Phrase(df.format(vBigLog), contentFont));
				PdfPCell valueSmallLog = new PdfPCell(new Phrase(df.format(vSmallLog), contentFont));
				sumAllBigLog[0][0] += vBigLog;
				sumAllSmallLog[0][0] += vSmallLog;

				valueBigLog.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				valueSmallLog.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				contentBig.addCell(valueBigLog);
				contentSmall.addCell(valueSmallLog);
			}
			else
			{
				contentBig.addCell(empty);
				contentSmall.addCell(empty);
			}
		}

		contentBig.addCell(gap);
		contentSmall.addCell(gap);

		contentBig.addCell(cell22);
		contentSmall.addCell(cell22);

		for (int j = 0; j < 3; j++)
		{
			if (j == 0)
			{
				double vBigLog = Double.parseDouble(ringkasanPembayaranTableBigLog[1]);
				double vSmallLog = Double.parseDouble(ringkasanPembayaranTableSmallLog[1]);
				PdfPCell valueBigLog = new PdfPCell(new Phrase(df.format(vBigLog), contentFont));
				PdfPCell valueSmallLog = new PdfPCell(new Phrase(df.format(vSmallLog), contentFont));
				sumAllBigLog[1][0] += vBigLog;
				sumAllSmallLog[1][0] += vSmallLog;

				valueBigLog.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				valueSmallLog.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				contentBig.addCell(valueBigLog);
				contentSmall.addCell(valueSmallLog);
			}
			else
			{
				contentBig.addCell(empty);
				contentSmall.addCell(empty);
			}
		}

		PdfPCell cell13 = new PdfPCell(new Phrase(" 1.3 Jumlah Terkumpul", contentFont));
		PdfPCell cell23 = new PdfPCell(new Phrase(" 2.3 Jumlah Terkumpul", contentFont));

		cell13.setColspan(2);
		cell23.setColspan(2);

		contentBig.addCell(cell13);
		contentSmall.addCell(cell13);

		for (int j = 0; j < 3; j++)
		{
			if (j == 0)
			{
				PdfPCell valueBigLog = new PdfPCell(new Phrase(df.format(sumAllBigLog[0][0]), contentFont));
				PdfPCell valueSmallLog = new PdfPCell(new Phrase(df.format(sumAllSmallLog[0][0]), contentFont));

				valueBigLog.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				valueSmallLog.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				contentBig.addCell(valueBigLog);
				contentSmall.addCell(valueSmallLog);
			}
			else
			{
				contentBig.addCell(empty);
				contentSmall.addCell(empty);
			}
		}

		contentBig.addCell(gap);
		contentSmall.addCell(gap);

		contentBig.addCell(cell23);
		contentSmall.addCell(cell23);

		for (int j = 0; j < 3; j++)
		{
			if (j == 0)
			{
				PdfPCell valueBigLog = new PdfPCell(new Phrase(df.format(sumAllBigLog[1][0]), contentFont));
				PdfPCell valueSmallLog = new PdfPCell(new Phrase(df.format(sumAllSmallLog[1][0]), contentFont));

				valueBigLog.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				valueSmallLog.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				contentBig.addCell(valueBigLog);
				contentSmall.addCell(valueSmallLog);
			}
			else
			{
				contentBig.addCell(empty);
				contentSmall.addCell(empty);
			}
		}

		for (int j = 0; j < 6; j++)
		{
			contentBig.addCell(gap);
			contentSmall.addCell(gap);
		}

		contentBig.addCell(cess);
		contentSmall.addCell(cess);
		contentBig.completeRow();
		contentSmall.completeRow();

		PdfPCell cell31 = new PdfPCell(new Phrase(" 3.1 Sebelum", contentFont));

		cell31.setColspan(2);

		for (int j = 0; j < 6; j++)
		{
			contentBig.addCell(gap);
			contentSmall.addCell(gap);
		}

		contentBig.addCell(cell31);
		contentSmall.addCell(cell31);

		for (int j = 0; j < 3; j++)
		{
			if (j == 0)
			{
				double vBigLog = Double.parseDouble(ringkasanPembayaranTableBigLog[2]);
				double vSmallLog = Double.parseDouble(ringkasanPembayaranTableSmallLog[2]);
				
				PdfPCell valueBigLog = new PdfPCell(new Phrase(df.format(vBigLog), contentFont));
				PdfPCell valueSmallLog = new PdfPCell(new Phrase(df.format(vSmallLog), contentFont));
				
				sumAllBigLog[2][0] += vBigLog;
				sumAllSmallLog[2][0] += vSmallLog;

				valueBigLog.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				valueSmallLog.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				contentBig.addCell(valueBigLog);
				contentSmall.addCell(valueSmallLog);
			}
			else
			{
				contentBig.addCell(empty);
				contentSmall.addCell(empty);
			}
		}

		PdfPCell cell32 = new PdfPCell(new Phrase(" 3.2 Semasa", contentFont));

		cell32.setColspan(2);

		for (int j = 0; j < 6; j++)
		{
			contentBig.addCell(gap);
			contentSmall.addCell(gap);
		}

		contentBig.addCell(cell32);
		contentSmall.addCell(cell32);

		for (int j = 0; j < 3; j++)
		{
			if (j == 0)
			{
				double vBigLog = Double.parseDouble(ringkasanPembayaranTableBigLog[3]);
				double vSmallLog = Double.parseDouble(ringkasanPembayaranTableSmallLog[3]);
				
				PdfPCell valueBigLog = new PdfPCell(new Phrase(df.format(vBigLog), contentFont));
				PdfPCell valueSmallLog = new PdfPCell(new Phrase(df.format(vSmallLog), contentFont));
				
				sumAllBigLog[2][0] += vBigLog;
				sumAllSmallLog[2][0] += vSmallLog;

				valueBigLog.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				valueSmallLog.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				contentBig.addCell(valueBigLog);
				contentSmall.addCell(valueSmallLog);
			}
			else
			{
				contentBig.addCell(empty);
				contentSmall.addCell(empty);
			}
		}

		PdfPCell cell33 = new PdfPCell(new Phrase(" 3.3 Jumlah Terkumpul", contentFont));

		cell33.setColspan(2);

		for (int j = 0; j < 6; j++)
		{
			contentBig.addCell(gap);
			contentSmall.addCell(gap);
		}

		contentBig.addCell(cell33);
		contentSmall.addCell(cell33);

		for (int j = 0; j < 3; j++)
		{
			if (j == 0)
			{
				PdfPCell valueBigLog = new PdfPCell(new Phrase(df.format(sumAllBigLog[2][0]), contentFont));
				PdfPCell valueSmallLog = new PdfPCell(new Phrase(df.format(sumAllSmallLog[2][0]), contentFont));

				valueBigLog.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				valueSmallLog.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				contentBig.addCell(valueBigLog);
				contentSmall.addCell(valueSmallLog);
			}
			else
			{
				contentBig.addCell(empty);
				contentSmall.addCell(empty);
			}
		}

		for (int j = 0; j < 6; j++)
		{
			contentBig.addCell(gap);
			contentSmall.addCell(gap);
		}

		contentBig.addCell(others);
		contentSmall.addCell(others);
		contentBig.completeRow();
		contentSmall.completeRow();

		String[] contents = new String[] {" 4.1 Sebelum", " 4.2 Semasa", " 4.3 Jumlah Terkumpul"};

		for (String content : contents)
		{
			PdfPCell cell4 = new PdfPCell(new Phrase(content, contentFont));

			cell4.setColspan(2);

			for (int j = 0; j < 6; j++)
			{
				contentBig.addCell(gap);
				contentSmall.addCell(gap);
			}

			contentBig.addCell(cell4);
			contentSmall.addCell(cell4);
			contentBig.completeRow();
			contentSmall.completeRow();
		}

		PdfPCell cellPrepared = new PdfPCell(new Phrase("DISEDIAKAN OLEH:", smallerFont));
		PdfPCell cellVerified = new PdfPCell(new Phrase("DISEMAK OLEH:", smallerFont));

		cellPrepared.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cellPrepared.setColspan(2);
		cellPrepared.setBorder(0);

		cellVerified.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cellVerified.setColspan(2);
		cellVerified.setBorder(0);

		approval.addCell(cellPrepared);
		approval.addCell(gap);
		approval.addCell(cellVerified);

		PdfPCell cellPreparedName = new PdfPCell(new Phrase("", contentFont));
		PdfPCell cellVerifiedName = new PdfPCell(new Phrase("", contentFont));

		cellPreparedName.setBorder(0);
		cellPreparedName.setBorderWidthBottom(0.5f);

		cellVerifiedName.setBorder(0);
		cellVerifiedName.setBorderWidthBottom(0.5f);

		approval.addCell(new Phrase("NAMA :", contentFont));
		approval.addCell(cellPreparedName);
		approval.addCell(gap);
		approval.addCell(new Phrase("NAMA :", contentFont));
		approval.addCell(cellVerifiedName);

		PdfPCell cellPreparedDesignation = new PdfPCell(new Phrase("", contentFont));
		PdfPCell cellVerifiedDesignation = new PdfPCell(new Phrase("", contentFont));

		cellPreparedDesignation.setBorder(0);
		cellPreparedDesignation.setBorderWidthBottom(0.5f);

		cellVerifiedDesignation.setBorder(0);
		cellVerifiedDesignation.setBorderWidthBottom(0.5f);

		approval.addCell(new Phrase("JAWATAN :", contentFont));
		approval.addCell(cellPreparedDesignation);
		approval.addCell(gap);
		approval.addCell(new Phrase("JAWATAN :", contentFont));
		approval.addCell(cellVerifiedDesignation);

		PdfPCell cellPreparedSignature = new PdfPCell(new Phrase("\n\n\n", contentFont));
		PdfPCell cellVerifiedSignature = new PdfPCell(new Phrase("\n\n\n", contentFont));

		cellPreparedSignature.setBorder(0);
		cellPreparedSignature.setBorderWidthBottom(0.5f);

		cellVerifiedSignature.setBorder(0);
		cellVerifiedSignature.setBorderWidthBottom(0.5f);

		approval.addCell(new Phrase("TANDATANGAN :", contentFont));
		approval.addCell(cellPreparedSignature);
		approval.addCell(gap);
		approval.addCell(new Phrase("TANDATANGAN :", contentFont));
		approval.addCell(cellVerifiedSignature);

		PdfPCell cellPreparedDate = new PdfPCell(new Phrase(sdf.format(new Date()), contentFont));
		PdfPCell cellVerifiedDate = new PdfPCell(new Phrase("", contentFont));

		cellPreparedDate.setBorder(0);
		cellPreparedDate.setBorderWidthBottom(0.5f);
		cellPreparedDate.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		cellVerifiedDate.setBorder(0);
		cellVerifiedDate.setBorderWidthBottom(0.5f);

		approval.addCell(new Phrase("TARIKH :", contentFont));
		approval.addCell(cellPreparedDate);
		approval.addCell(gap);
		approval.addCell(new Phrase("TARIKH :", contentFont));
		approval.addCell(cellVerifiedDate);

		Paragraph next = new Paragraph("\n", contentFont);

		document.add(id);
		document.add(titleBig);
		document.add(contentBig);

		document.newPage();

		document.add(id);
		document.add(titleSmall);
		document.add(contentSmall);
		document.add(next);
		document.add(balance);
		document.add(count);

		document.newPage();

		document.add(id);
		document.add(titleBigJ);
		document.add(contentBigJ);
		document.add(titleSmallJ);
		document.add(contentSmallJ);
		document.add(new Paragraph("Catatan: Ses kayu jaras adalah 10% daripada royalti\n\n", contentFont));
		document.add(approval);

		float widths[] = new float[p2w], total = 255.11f;
		widths[0] = widths[p2w - 2] = widths[p2w - 1] = 56.69f;
		widths[1] = 85.04f;

		for (int i = 2; i < p2w - 2; i++)
			if (i < p2w - 14 || i > p2w - 6 || indices.contains(i))
				total += (widths[i] = 42.52f);

		Paragraph title = new Paragraph(
				"NEGERI-NEGERI SEMENANJUNG MALAYSIA\nRINGKASAN PAS MEMINDAH YANG DIKELUARKAN\nDI BALAI PEMERIKSA HUTAN ",
				titleFont);
		Paragraph subtitle = new Paragraph("NO.\n\n", titleFont);
		PdfPTable content = new PdfPTable(widths);
		PdfPTable header = new PdfPTable(new float[] {0.7f, 0.1f, 0.2f});
		PdfPTable footer = new PdfPTable(
				new float[] {0.15f, 0.15f, 0.1f, 0.15f, 0.1f, 0.15f, 0.2f});
		PdfPCell top = new PdfPCell(header);
		PdfPCell serial = new PdfPCell(new Phrase("PNMB R1",
				new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.NORMAL)));
		PdfPCell no = new PdfPCell(
				new Phrase("No. Surat Pemberitahuan:", contentFont));
		PdfPCell headerPremium = new PdfPCell(new Phrase("0.00", contentFont));
		PdfPCell headerBalance = new PdfPCell(new Phrase(infoLain[1], contentFont));
		PdfPCell headerDeposit = new PdfPCell(new Phrase("0.00", contentFont));
		PdfPCell headerDate = new PdfPCell(new Phrase("TARIKH", smallerFont));
		PdfPCell headerNo = new PdfPCell(
				new Phrase("NOMBOR PAS MEMINDAH", smallerFont));
		PdfPCell headerRoyalty2 = new PdfPCell(
				new Phrase("ROYALTI (RM)", smallerFont));
		PdfPCell headerBalance2 = new PdfPCell(
				new Phrase("BAKI (RM)", smallerFont));
		PdfPCell summarySum = new PdfPCell(new Phrase("Jumlah", smallerFont));
		PdfPCell summaryCount = new PdfPCell(
				new Phrase(sheet + " helai", smallerFont));
		PdfPCell statement = new PdfPCell(new Phrase(
				"Butir-butir di atas telah disemak 100% oleh Renjer Hutan " + infoLain[4] + " dan didapati betul.",
				contentFont));
		PdfPCell blank = new PdfPCell(new Phrase(" ", contentFont));

		content.setTotalWidth(total);
		content.setLockedWidth(true);
		footer.setWidthPercentage(100f);
		header.setWidthPercentage(60f);
		header.getDefaultCell().setBorder(0);
		footer.getDefaultCell().setBorder(0);

		top.setColspan(p2w - 4);
		serial.setColspan(2);
		no.setColspan(2);
		statement.setRowspan(3);

		top.setBorder(0);
		serial.setBorder(0);
		headerPremium.setBorder(0);
		headerBalance.setBorder(0);
		headerDeposit.setBorder(0);
		statement.setBorder(0);
		blank.setBorder(0);

		top.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		serial.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
		headerPremium.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		headerBalance.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		headerDeposit.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		headerDate.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		headerNo.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		headerRoyalty2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		headerBalance2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		summarySum.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		summaryCount.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		statement.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);

		headerDate.setRowspan(2);
		headerNo.setRowspan(2);
		headerRoyalty2.setRowspan(2);
		headerBalance2.setRowspan(2);

		headerDate.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		headerNo.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		headerRoyalty2.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		headerBalance2.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);

		header.addCell(new Phrase("Premium pada satu bulan", contentFont));
		header.addCell(new Phrase("RM", contentFont));
		header.addCell(headerPremium);
		header.addCell(new Phrase("Baki daripada bulan dahulu (" + infoLain[0] + ")",
				contentFont));
		header.addCell(new Phrase("RM", contentFont));
		header.addCell(headerBalance);
		header.addCell(new Phrase("Setengah cagaran", contentFont));
		header.addCell(new Phrase("RM", contentFont));
		header.addCell(headerDeposit);

		title.add(new Phrase("" + headerInfo[2], italicFont));
		title.add(new Phrase(" DARI ", titleFont));
		title.add(new Phrase(sdf.format(startDate), italicFont));
		title.add(new Phrase(" HINGGA ", titleFont));
		title.add(new Phrase(sdf.format(endDate), italicFont));

		title.setAlignment(Paragraph.ALIGN_CENTER);
		subtitle.setAlignment(Paragraph.ALIGN_RIGHT);

		content.addCell(serial);
		content.addCell(top);
		content.addCell(no);

		content.addCell(headerDate);
		content.addCell(headerNo);

		for (int i = 0; i < 3; i++)
		{
			String[] row = page2[i];

			if (i == 0)
			{
				String previous = null;
				int span = 1;

				for (int j = 2; j < p2w - 5; j++)
				{
					if (!row[j].equals(previous))
					{
						if (previous != null)
						{
							PdfPCell cell = new PdfPCell(new Phrase(previous, smallerFont));

							cell.setColspan(span);
							cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
							content.addCell(cell);
						}

						previous = row[j];
						span = 1;
					}
					else
						span++;
				}

				PdfPCell cell = new PdfPCell(new Phrase(previous, smallerFont));

				cell.setColspan(span);
				cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);

				content.addCell(cell);

				for (int j = p2w - 5; j < p2w - 2; j++)
				{
					cell = new PdfPCell(new Phrase(page2[i + 1][j], smallerFont));

					cell.setRowspan(2);
					cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
					cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
					content.addCell(cell);
				}

				content.addCell(headerRoyalty2);
				content.addCell(headerBalance2);
			}
			else if (i == 1)
			{
				for (int j = 2; j < p2w - 5; j++)
				{
					String text = row[j];
					text = text.contains("CM ") ? text.replaceAll("CM ", "CM\n") : text.replaceAll(" ", "\n");

					PdfPCell cell = new PdfPCell(new Phrase(text, smallerFont));

					cell.setRotation(90);
					cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
					cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
					content.addCell(cell);
				}
			}
			else
			{
				PdfPCell cell = new PdfPCell(new Phrase(row[0], smallerFont));

				cell.setColspan(p2w - 1);
				cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
				content.addCell(cell);
				
				cell = new PdfPCell(new Phrase(df.format(Double.parseDouble(row[p2w - 1])), smallerFont));

				cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
				content.addCell(cell);
			}
		}

		double[] sum = new double[p2w - 2];

		for (int i = 3; i < page2.length; i++)
		{
			String[] row = page2[i];
			PdfPCell date = new PdfPCell(new Phrase(row[0], contentFont));
			
			date.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			content.addCell(date);
			
			if (row[1] != null)
			{
				PdfPCell passNo = new PdfPCell(new Phrase(row[1], contentFont));
				
				passNo.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				content.addCell(passNo);
				
				for (int j = 2; j < p2w; j++)
				{
					double value = Double.parseDouble(row[j]);
					PdfPCell cell = new PdfPCell(
							new Phrase(df.format(value), contentFont));
					sum[j - 2] += value;

					cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
					content.addCell(cell);
				}
			}
			else
			{
				PdfPCell cell = new PdfPCell(new Phrase(row[2], contentFont));

				cell.setColspan(p2w - 2);
				cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				content.addCell(cell);
				
				cell = new PdfPCell(new Phrase(row[p2w - 1], contentFont));

				cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				content.addCell(cell);
			}
		}

		content.addCell(summarySum);
		content.addCell(summaryCount);

		for (int j = 2; j < p2w - 1; j++)
		{
			PdfPCell cell = new PdfPCell(
					new Phrase(df.format(sum[j - 2]), smallerFont));

			cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			content.addCell(cell);
		}
		
		PdfPCell cell = new PdfPCell(
				new Phrase(df.format(Double.parseDouble(finalBalance)), smallerFont));

		cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		content.addCell(cell);

		footer.addCell(new Phrase("No. Lejar", contentFont));
		footer.addCell(new Phrase("Folio", contentFont));
		footer.addCell(new Phrase("Nama", contentFont));
		footer.addCell(new Phrase(headerInfo[1], smallerFont));
		footer.addCell(new Phrase("No. Lesen / Permit", contentFont));
		footer.addCell(new Phrase(headerInfo[0], smallerFont));
		footer.addCell(statement);
		footer.addCell(new Phrase("Buku Cerakian Muka", contentFont));
		footer.addCell(blank);
		footer.addCell(new Phrase("Kompt", contentFont));
		footer.addCell(new Phrase(headerInfo[3], smallerFont));
		footer.addCell(new Phrase("Hutan", contentFont));
		footer.addCell(new Phrase(infoLain[3], smallerFont));
		footer.addCell(new Phrase("No. Ringkasan", contentFont));
		footer.addCell(blank);
		footer.addCell(new Phrase("Tebangan", contentFont));
		footer.addCell(new Phrase(infoLain[2], smallerFont));
		footer.addCell(blank);
		footer.addCell(new Phrase("", smallerFont));

		Rectangle A4 = PageSize.A4;

		document.setPageSize(new Rectangle(total + 56.69f, A4.getHeight()));
		document.newPage();

		document.add(title);
		document.add(subtitle);
		document.add(content);
		document.add(next);
		document.add(footer);

		document.close();
	}

	private static int setSmallJarasRow(PdfPTable contentSmallJ, String description, String criteria, int index, int lengthSmallJ, ArrayList<String[]> smallJaras, double[] sumSmallJ, DecimalFormat df)
	{
		contentSmallJ.addCell(new Phrase(description, contentFont));

		String[] jaras = null;

		if (lengthSmallJ != 0 && index < lengthSmallJ)
		{
			jaras = smallJaras.get(index);

			if (criteria.equals(jaras[0]))
			{
				index++;

				for (int j = 0; j < 4; j++)
				{
					double value = jaras[j + 1] == null ? 0
							: Double.parseDouble(jaras[j + 1]);
					PdfPCell cell = new PdfPCell(
							new Phrase(j == 0 ? String.valueOf((int) value) : df.format(value), contentFont));
					sumSmallJ[j] += value;

					cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
					contentSmallJ.addCell(cell);
				}
			}
			else
				jaras = null;
		}

		if (jaras == null)
		{
			for (int j = 0; j < 4; j++)
			{
				PdfPCell cell = new PdfPCell(
						new Phrase(j == 0 ? "0" : "0.00", contentFont));

				cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				contentSmallJ.addCell(cell);
			}
		}

		return index;
	}

	public static void main(String[] args)
	{
		long selectedLicenseID = 1544521496416L;
		Date startDate = new GregorianCalendar(2018, 10, 1).getTime(), endDate = new GregorianCalendar(2018, 11, 12).getTime();

		try (HallFacade hFacade = new HallFacade())
		{
			String[] headerInfo = hFacade
					.getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenHeaderInfo(
							selectedLicenseID);
			ArrayList<String[]> smallLogs = hFacade
					.getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenTypeAPage1andPage2(
							startDate, endDate, selectedLicenseID, 0);
			ArrayList<String[]> bigLogs = hFacade
					.getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenTypeAPage1andPage2(
							startDate, endDate, selectedLicenseID, 1);
			ArrayList<String[]> bigJaras = hFacade
					.getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenJarasBesar(
							startDate, endDate, selectedLicenseID);
			ArrayList<String[]> smallJaras = hFacade
					.getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenJarasKecil(
							startDate, endDate, selectedLicenseID);
			ArrayList<String[]> ringkasanPengeluaranTableBigLog = hFacade
					.getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenRingkasanPengeluaranTableBigLog(
							startDate, endDate, selectedLicenseID);
			ArrayList<String[]> ringkasanPengeluaranTableSmallLog = hFacade
					.getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenRingkasanPengeluaranTableSmallLog(
							startDate, endDate, selectedLicenseID);
			String[] ringkasanPembayaranTableBigLog = hFacade
					.getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenRingkasanPembayaranTableBigLog(
							startDate, endDate, selectedLicenseID);
			String[] ringkasanPembayaranTableSmallLog = hFacade
					.getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenRingkasanPembayaranTableSmallLog(
							startDate, endDate, selectedLicenseID);
			String[][] page2 = hFacade
					.getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenDetailTransferPass(
							startDate, endDate, selectedLicenseID);
			String[] infoLain = new String[5];

			generate(new File("ringkasan.pdf"), headerInfo, smallLogs, bigLogs,
					bigJaras, smallJaras, ringkasanPengeluaranTableBigLog, ringkasanPengeluaranTableSmallLog,
					ringkasanPembayaranTableBigLog, ringkasanPembayaranTableSmallLog, page2, infoLain, startDate, 
					endDate, selectedLicenseID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}