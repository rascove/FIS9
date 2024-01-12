package my.edu.utem.ftmk.fis9.hall.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author Nor Azman Bin Mat Ariff
 */
public class TransferPassGenerator
{
	private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 14,
			Font.BOLD);
	private static Font contentFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.NORMAL);
	private static Font underlinedFont = new Font(Font.FontFamily.TIMES_ROMAN,
			12, Font.UNDERLINE);
	private static Font smallerFont = new Font(Font.FontFamily.TIMES_ROMAN, 10,
			Font.NORMAL);
	private static Font smallestFont = new Font(Font.FontFamily.TIMES_ROMAN, 6,
			Font.NORMAL);

	public static void generate(File file, String[] transferPass,
			ArrayList<String[]> transferPassRecords, int code) throws Exception
	{
		Document document = new Document(PageSize.A4, 28.35f, 28.35f, 28.35f,
				28.35f);

		PdfWriter.getInstance(document, new FileOutputStream(file));

		document.open();

		document.addSubject("Jabatan Perhutanan Negeri Sembilan");
		document.addKeywords("Pas Pemindahan");
		document.addAuthor("JPNS");
		document.addCreator("JPNS");
		document.addTitle("PAS BAGI MEMINDAHKAN HASIL HUTAN");

		for (int i = 0; i < 3; i++)
		{
			if (i != 0)
			{
				document.newPage();
				document.add(new Paragraph(i == 1 ? "PENDUA" : "PENIGA",
						new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
			}
			else
				document.add(new Paragraph("ASAL",
						new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));

			Paragraph serial1 = new Paragraph("(Hutan 08/86 - Pin. 2/94)",
					smallestFont);
			Paragraph title = new Paragraph(
					"KERAJAAN NEGERI SEMBILAN\nAKTA PERHUTANAN NEGARA 1984",
					contentFont);
			Paragraph subtitle = new Paragraph("Borang 8", smallerFont);
			Paragraph name = new Paragraph("PAS BAGI MEMINDAHKAN HASIL HUTAN",
					titleFont);
			Paragraph no = new Paragraph(
					"Seksyen 70 (2)\nNo. Pas Pemindahan: " + transferPass[0],
					smallerFont);
			Paragraph content = new Paragraph(
					"Pas ini membenarkan Encik/Syarikat* ", contentFont);
			Paragraph serial2 = new Paragraph("TJD000327 - PNMB., Tr.",
					smallestFont);

			content.add(new Chunk(transferPass[1], underlinedFont));
			content.add(new Chunk(" No. K.P.P.N. ", contentFont));
			content.add(new Chunk(transferPass[2], underlinedFont));
			content.add(new Chunk(" yang beralamat di ", contentFont));
			content.add(new Chunk(transferPass[3], underlinedFont));
			content.add(new Chunk(
					" memindah melalui Balai Pemeriksa Jabatan Hutan di ",
					contentFont));
			content.add(new Chunk(transferPass[4], underlinedFont));
			content.add(new Chunk(
					" hasil hutan yang diperihalkan di bawah ini yang telah diambil di bawah Lesen/Lesen Kecil/Permit Penggunaan* No. ",
					contentFont));
			content.add(new Chunk(transferPass[5], underlinedFont));
			content.add(new Chunk(" dan dipunyai oleh ", contentFont));
			content.add(new Chunk(transferPass[6], underlinedFont));
			content.add(new Chunk(".", contentFont));

			serial1.setAlignment(Paragraph.ALIGN_RIGHT);
			title.setAlignment(Paragraph.ALIGN_CENTER);
			subtitle.setAlignment(Paragraph.ALIGN_CENTER);
			name.setAlignment(Paragraph.ALIGN_CENTER);
			no.setAlignment(Paragraph.ALIGN_CENTER);
			content.setAlignment(Paragraph.ALIGN_JUSTIFIED);
			serial2.setAlignment(Paragraph.ALIGN_RIGHT);

			document.add(serial1);
			document.add(title);
			document.add(subtitle);
			document.add(name);
			document.add(no);
			document.add(content);
			document.add(serial2);
			document.add(new Paragraph("\n", contentFont));

			PdfPTable table = new PdfPTable(new float[] {0.25f, 0.08f, 0.08f,
					0.08f, 0.08f, 0.08f, 0.08f, 0.08f, 0.08f, 0.11f});
			PdfPCell serial = new PdfPCell(
					new Phrase("No. Pas Pemindahan: " + transferPass[0] + "\n", contentFont));
			PdfPCell type = new PdfPCell(
					new Phrase("Jenis Hasil", contentFont));
			PdfPCell quantity = new PdfPCell(
					new Phrase("Bilangan atau\nKuantiti", contentFont));
			PdfPCell length = new PdfPCell(
					new Phrase("Panjang (Meter)", contentFont));
			PdfPCell diameter = new PdfPCell(
					new Phrase("Garispusat Min.\n(sentimeter)", contentFont));
			PdfPCell cubic = new PdfPCell(
					new Phrase("Meter Padu", contentFont));
			PdfPCell volume = new PdfPCell(new Phrase("Isipadu", contentFont));
			PdfPCell cutting = new PdfPCell(
					new Phrase("Potongan Bagi Rongga dll.", contentFont));
			PdfPCell netVol = new PdfPCell(
					new Phrase("Isipadu Bersih", contentFont));
			PdfPCell holeDia = new PdfPCell(
					new Phrase("Garis-pusat Min.", contentFont));
			PdfPCell holeVol = new PdfPCell(new Phrase("Isipadu", contentFont));
			PdfPCell rate = new PdfPCell(
					new Phrase("Kadar Royalti/m\u00B3", contentFont));
			PdfPCell royalty = new PdfPCell(new Phrase("Royalti", contentFont));
			PdfPCell empty = new PdfPCell(new Phrase("", contentFont));
			PdfPCell one = new PdfPCell(new Phrase("1", contentFont));

			table.setHeaderRows(4);
			table.setWidthPercentage(100f);
			table.setSkipFirstHeader(true);

			quantity.setRotation(90);
			length.setRotation(90);
			diameter.setRotation(90);
			rate.setRotation(90);

			serial.setColspan(10);
			serial.setBorder(0);
			
			type.setRowspan(3);
			quantity.setRowspan(3);
			length.setRowspan(3);
			diameter.setRowspan(3);
			cubic.setColspan(4);
			rate.setRowspan(3);
			royalty.setRowspan(3);
			volume.setRowspan(2);
			cutting.setColspan(2);
			netVol.setRowspan(2);

			type.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			type.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			quantity.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			length.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			diameter.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			cubic.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			rate.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			royalty.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			royalty.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			volume.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			volume.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			cutting.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cutting.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			netVol.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			netVol.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			holeDia.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			holeDia.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			holeVol.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			holeVol.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			one.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

			table.addCell(serial);
			
			for (int j = 0; j < 2; j++)
			{
				table.addCell(type);
				table.addCell(quantity);
				table.addCell(length);
				table.addCell(diameter);
				table.addCell(cubic);
				table.addCell(rate);
				table.addCell(royalty);
				table.addCell(volume);
				table.addCell(cutting);
				table.addCell(netVol);
				table.addCell(holeDia);
				table.addCell(holeVol);
			}

			double[] sum = new double[4];
			DecimalFormat df = new DecimalFormat("0.00");
			DecimalFormat dfDiameter = new DecimalFormat("0");
			DecimalFormat dfLength = new DecimalFormat("0.0");
			double cessRate = 0;

			for (String[] transferPassRecordString : transferPassRecords)
			{
				table.addCell(new PdfPCell(
						new Phrase(transferPassRecordString[0], contentFont)));

				if (code != 1)
				{
					double valueLength = Double
							.parseDouble(transferPassRecordString[1]);
					double valueDiameter = Double
							.parseDouble(transferPassRecordString[2]);
					double valueVolume = Double
							.parseDouble(transferPassRecordString[3]);
					double valueHoleDiameter = Double
							.parseDouble(transferPassRecordString[4]);
					double valueHoleVolume = Double
							.parseDouble(transferPassRecordString[5]);
					double valueNetVolume = Double
							.parseDouble(transferPassRecordString[6]);
					cessRate = Double.parseDouble(transferPassRecordString[10]);

					sum[0]++;
					sum[1] += valueNetVolume;

					PdfPCell contentLength = new PdfPCell(new Phrase(
							dfLength.format(valueLength), contentFont));
					PdfPCell contentDiameter = new PdfPCell(new Phrase(
							dfDiameter.format(valueDiameter), contentFont));
					PdfPCell contentVolume = new PdfPCell(
							new Phrase(df.format(valueVolume), contentFont));
					PdfPCell contentHoleDiameter = new PdfPCell(new Phrase(
							df.format(valueHoleDiameter), contentFont));
					PdfPCell contentHoleVolume = new PdfPCell(new Phrase(
							df.format(valueHoleVolume), contentFont));
					PdfPCell contentNetVolume = new PdfPCell(
							new Phrase(df.format(valueNetVolume), contentFont));

					contentLength.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
					contentDiameter
							.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
					contentVolume.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
					contentHoleDiameter
							.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
					contentHoleVolume
							.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
					contentNetVolume
							.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

					table.addCell(one);
					table.addCell(contentLength);
					table.addCell(contentDiameter);
					table.addCell(contentVolume);
					table.addCell(contentHoleDiameter);
					table.addCell(contentHoleVolume);
					table.addCell(contentNetVolume);
				}
				else
				{
					double valueQuantity = Double
							.parseDouble(transferPassRecordString[1]);
					sum[0] += valueQuantity;
					PdfPCell contentQuantity = new PdfPCell(
							new Phrase(df.format(valueQuantity), contentFont));

					contentQuantity
							.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

					table.addCell(contentQuantity);
					table.addCell(new PdfPCell(new Phrase(
							transferPassRecordString[2], contentFont)));
					table.addCell(empty);
					table.addCell(empty);
					table.addCell(empty);
					table.addCell(empty);
					table.addCell(empty);
				}

				double valueRate = Double
						.parseDouble(transferPassRecordString[7]);
				double valueRoyalty = Double
						.parseDouble(transferPassRecordString[8]);
				double valueCess = Double
						.parseDouble(transferPassRecordString[9]);

				PdfPCell contentRate = new PdfPCell(
						new Phrase(df.format(valueRate), contentFont));
				PdfPCell contentRoyalty = new PdfPCell(
						new Phrase(df.format(valueRoyalty), contentFont));

				sum[2] += valueRoyalty;
				sum[3] += valueCess;

				contentRate.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				contentRoyalty.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

				table.addCell(contentRate);
				table.addCell(contentRoyalty);
			}

			PdfPCell sumQuantity = new PdfPCell(
					new Phrase(String.valueOf((int) sum[0]), contentFont));
			PdfPCell sumNetVolume = new PdfPCell(new Phrase(
					sum[1] == 0 ? "" : df.format(sum[1]), contentFont));
			PdfPCell sumRoyalty = new PdfPCell(
					new Phrase(df.format(sum[2]), contentFont));
			/*
			 * PdfPCell cessValue = new PdfPCell(new Phrase( sum[1] == 0 ? "" :
			 * "RM " + df.format(sum[3] / sum[1]) + " x", contentFont));
			 */
			PdfPCell cessValue = new PdfPCell(new Phrase(
					sum[1] == 0 ? "" : "RM " + df.format(cessRate) + " \u00D7",
					contentFont));
			PdfPCell cess = new PdfPCell(
					new Phrase(df.format(sum[3]), contentFont));
			PdfPCell big = new PdfPCell(
					new Phrase("Jumlah Besar", contentFont));
			PdfPCell total = new PdfPCell(
					new Phrase(df.format(sum[2] + sum[3]), contentFont));

			sumQuantity.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			sumNetVolume.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			sumRoyalty.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			cessValue.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			cess.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			total.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

			cessValue.setColspan(6);
			big.setColspan(7);

			table.addCell(new PdfPCell(new Phrase("Jumlah", contentFont)));
			table.addCell(sumQuantity);
			table.addCell(empty);
			table.addCell(empty);
			table.addCell(empty);
			table.addCell(empty);
			table.addCell(empty);
			table.addCell(sumNetVolume);
			table.addCell(empty);
			table.addCell(sumRoyalty);

			table.addCell(new PdfPCell(new Phrase("SES:", contentFont)));
			table.addCell(cessValue);
			table.addCell(sumNetVolume);
			table.addCell(empty);
			table.addCell(cess);

			table.addCell(big);
			table.addCell(empty);
			table.addCell(empty);
			table.addCell(total);

			PdfPTable footer = new PdfPTable(new float[] {0.6f, 0.4f});
			PdfPCell officer = new PdfPCell(
					new Phrase("\n\nPEGAWAI HUTAN BERKUASA\n\n\n", contentFont));
			PdfPCell line = new PdfPCell(new Phrase(
					"\n*Potong yang tidak berkenaan.\nPas ini hendaklah disimpan oleh pemandu kenderaan atau orang yang bertanggungjawab atas hasil hutan itu.",
					smallerFont));

			officer.setBorder(0);
			officer.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

			line.setColspan(2);
			line.setBorderWidthLeft(0);
			line.setBorderWidthRight(0);
			line.setBorderWidthBottom(0);
			line.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

			document.add(footer);

			footer.setWidthPercentage(101f);
			footer.getDefaultCell().setBorder(0);

			footer.addCell(new Phrase(
					"No. Pendaftaran Alat Pengangkutan: " + transferPass[7],
					contentFont));
			footer.addCell(
					new Phrase("Waktu: " + transferPass[10], contentFont));
			footer.addCell(
					new Phrase("Destinasi: " + transferPass[8], contentFont));
			footer.addCell(
					new Phrase("Tarikh: " + transferPass[11], contentFont));
			footer.addCell(
					new Phrase("Had Muatan: " + transferPass[9], contentFont));
			footer.completeRow();
			footer.completeRow();
			footer.completeRow();
			footer.addCell(new Phrase("", contentFont));
			footer.addCell(officer);
			footer.addCell(new Phrase(
					"No. Tukul Besi Hasil: " + transferPass[12] + "\n\n",
					contentFont));
			footer.completeRow();
			footer.addCell(line);

			document.add(table);
			document.add(new Paragraph("\n", contentFont));
			document.add(footer);
		}

		document.close();
	}
	/*
	 * public static void main(String[] args) { try (HallFacade hFacade = new
	 * HallFacade();) { TransferPass model = new TransferPass(); int code = 0;
	 * model.setTransferPassID(1531366163360L); model.setCode(code); String[]
	 * transferPassString = hFacade
	 * .getTransferPassString(model.getTransferPassID()); ArrayList<String[]>
	 * transferPassRecordStrings = hFacade .getTransferPassRecordString(model);
	 * System.out.println(transferPassRecordStrings.size());
	 * TransferPassGenerator.generate(new File("transferpass.pdf"),
	 * transferPassString, transferPassRecordStrings, code); } catch (Exception
	 * e) { e.printStackTrace(); } }
	 */
}