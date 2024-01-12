package my.edu.utem.ftmk.fis9.revenue.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author Nor Azman Bin Mat Ariff
 */
public class LaporanUrusniagaAkaunAmanahPadaBulanDanTahunGenerator
{
	private static final Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN,
			10, Font.BOLD);
	private static final Font contentFont = new Font(
			Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);

	public static void generate(File file, ArrayList<String[]> vots, Date date)
			throws Exception
	{
		Document document = new Document(PageSize.A4.rotate(), 28.35f, 28.35f,
				127.56f, 28.35f);
		File temp = new File(
				(file.getParent() == null ? "" : "/" + file.getParent())
						+ "temp.pdf");

		PdfWriter.getInstance(document, new FileOutputStream(temp));

		document.open();

		document.addSubject("Jabatan Perhutanan Negeri Sembilan");
		document.addKeywords("Laporan Urusniaga Akaun Amanah");
		document.addAuthor("JPNS");
		document.addCreator("JPNS");
		document.addTitle("LAPORAN URUSNIAGA AKAUN AMANAH");

		DecimalFormat df = new DecimalFormat("#,###,##0.00");
		PdfPCell no = new PdfPCell(new Phrase("TARIKH", titleFont));
		PdfPCell receiptNo = new PdfPCell(
				new Phrase("JENIS URUSNIAGA", titleFont));
		PdfPCell name = new PdfPCell(new Phrase("NO. DOKUMEN", titleFont));
		PdfPCell time = new PdfPCell(new Phrase("NO. RESIT", titleFont));
		PdfPCell trust = new PdfPCell(new Phrase("DEBIT", titleFont));
		PdfPCell money = new PdfPCell(
				new Phrase("KREDIT\nDEBIT (-)/KREDIT (+)", titleFont));

		no.setBorder(0);
		receiptNo.setBorder(0);
		name.setBorder(0);
		time.setBorder(0);
		trust.setBorder(0);
		money.setBorder(0);

		no.setBorderWidthTop(0.5f);
		receiptNo.setBorderWidthTop(0.5f);
		name.setBorderWidthTop(0.5f);
		time.setBorderWidthTop(0.5f);
		trust.setBorderWidthTop(0.5f);
		money.setBorderWidthTop(0.5f);

		no.setBorderWidthBottom(0.5f);
		receiptNo.setBorderWidthBottom(0.5f);
		name.setBorderWidthBottom(0.5f);
		time.setBorderWidthBottom(0.5f);
		trust.setBorderWidthBottom(0.5f);
		money.setBorderWidthBottom(0.5f);

		no.setBorderWidthLeft(0.5f);
		money.setBorderWidthRight(0.5f);

		no.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		receiptNo.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		name.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		time.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		trust.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		money.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		String current = null;
		PdfPTable content_01 = null;
		double sumD_01 = 0, sumC_01 = 0;

		for (String[] row : vots)
		{
			if (!row[0].equals(current))
			{
				if (current != null)
				{
					PdfPCell total_01 = new PdfPCell(
							new Phrase("JUMLAH AMANAH:    \u00A0", titleFont));
					PdfPCell value1_01 = new PdfPCell(
							new Phrase(df.format(sumD_01), contentFont));
					PdfPCell value2_01 = new PdfPCell(
							new Phrase(df.format(sumC_01), contentFont));

					total_01.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
					total_01.setColspan(4);
					total_01.setBorder(0);

					value1_01.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
					value1_01.setBorderWidthLeft(0);
					value1_01.setBorderWidthRight(0);

					value2_01.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
					value2_01.setBorderWidthLeft(0);
					value2_01.setBorderWidthRight(0);

					content_01.addCell(total_01);
					content_01.addCell(value1_01);
					content_01.addCell(value2_01);

					document.add(content_01);
					document.newPage();
				}

				current = row[0];
				String[] split = current.split("\t");

				PdfPTable aboutInner_01 = new PdfPTable(
						new float[] {0.2f, 0.05f, 0.1f, 0.65f});
				content_01 = new PdfPTable(
						new float[] {0.15f, 0.15f, 0.15f, 0.15f, 0.2f, 0.2f});

				content_01.setHeaderRows(3);

				aboutInner_01.setWidthPercentage(100f);
				content_01.setWidthPercentage(100f);

				aboutInner_01.getDefaultCell().setBorder(0);
				content_01.getDefaultCell().setBorder(0);

				content_01.getDefaultCell()
						.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

				aboutInner_01.addCell(new Phrase("TAHUN", titleFont));
				aboutInner_01.addCell(new Phrase(":", titleFont));
				aboutInner_01.addCell(
						new Phrase(new SimpleDateFormat("yyyy").format(date),
								contentFont));
				aboutInner_01.addCell(new Phrase("", titleFont));
				aboutInner_01.addCell(new Phrase("JAB. PEMBAYAR", titleFont));
				aboutInner_01.addCell(new Phrase(":", titleFont));
				aboutInner_01.addCell(new Phrase("0080", contentFont));
				aboutInner_01.addCell(
						new Phrase("JABATAN PERHUTANAN NEGERI", contentFont));
				aboutInner_01.addCell(new Phrase("PTJ PEMBAYAR", titleFont));
				aboutInner_01.addCell(new Phrase(":", titleFont));
				aboutInner_01.addCell(new Phrase("80000100", contentFont));
				aboutInner_01.addCell(new Phrase(
						"IBU PEJABAT JABATAN PERHUTANAN NEGERI", contentFont));
				aboutInner_01.addCell(
						new Phrase("JAB. DIPERTANGGUNGKAN", titleFont));
				aboutInner_01.addCell(new Phrase(":", titleFont));
				aboutInner_01.addCell(new Phrase("0080", contentFont));
				aboutInner_01.addCell(
						new Phrase("JABATAN PERHUTANAN NEGERI", contentFont));
				aboutInner_01
						.addCell(new Phrase("PTJ DIPERTANGGUNGKAN", titleFont));
				aboutInner_01.addCell(new Phrase(":", titleFont));
				aboutInner_01.addCell(new Phrase("80000100", contentFont));
				aboutInner_01.addCell(new Phrase(
						"IBU PEJABAT JABATAN PERHUTANAN NEGERI", contentFont));
				aboutInner_01.addCell(new Phrase("AMANAH", titleFont));
				aboutInner_01.addCell(new Phrase(":", titleFont));
				aboutInner_01.addCell(new Phrase(split[0], contentFont));
				aboutInner_01.addCell(new Phrase(split[1], contentFont));

				PdfPCell aboutOuter_01 = new PdfPCell(aboutInner_01);

				aboutOuter_01.setColspan(6);

				content_01.addCell(aboutOuter_01);
				content_01.addCell(new Phrase(" ", contentFont));
				content_01.completeRow();
				content_01.addCell(no);
				content_01.addCell(receiptNo);
				content_01.addCell(name);
				content_01.addCell(time);
				content_01.addCell(trust);
				content_01.addCell(money);
			}

			double debit = df.parse(row[5]).doubleValue(),
					credit = df.parse(row[6]).doubleValue();
			sumD_01 += debit;
			sumC_01 += credit;
			PdfPCell value1 = new PdfPCell(
					new Phrase(df.format(debit), contentFont));
			PdfPCell value2 = new PdfPCell(
					new Phrase(df.format(credit), contentFont));

			value1.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			value1.setBorder(0);

			value2.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			value2.setBorder(0);

			content_01.addCell(new Phrase(row[1], contentFont));
			content_01.addCell(new Phrase(row[2], contentFont));
			content_01.addCell(new Phrase(row[3], contentFont));
			content_01.addCell(new Phrase(row[4], contentFont));
			content_01.addCell(value1);
			content_01.addCell(value2);
		}

		PdfPCell total_01 = new PdfPCell(
				new Phrase("JUMLAH AMANAH:    \u00A0", titleFont));
		PdfPCell value1_01 = new PdfPCell(
				new Phrase(df.format(sumD_01), contentFont));
		PdfPCell value2_01 = new PdfPCell(
				new Phrase(df.format(sumC_01), contentFont));

		total_01.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		total_01.setColspan(4);
		total_01.setBorder(0);

		value1_01.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		value1_01.setBorderWidthLeft(0);
		value1_01.setBorderWidthRight(0);

		value2_01.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		value2_01.setBorderWidthLeft(0);
		value2_01.setBorderWidthRight(0);

		content_01.addCell(total_01);
		content_01.addCell(value1_01);
		content_01.addCell(value2_01);
		
		document.add(content_01);
		document.close();

		Date gen = new Date();
		PdfReader reader = new PdfReader(new FileInputStream(temp));
		int count = reader.getNumberOfPages();
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(file));
		PdfPCell genDate = new PdfPCell(new Phrase(
				"TARIKH: " + new SimpleDateFormat("dd/MM/yyyy").format(gen),
				titleFont));
		PdfPCell genTime = new PdfPCell(new Phrase(
				"MASA: " + new SimpleDateFormat("hh:mm aa").format(gen),
				titleFont));
		PdfPCell title = new PdfPCell(new Phrase(
				"KERAJAAN NEGERI NEGERI SEMBILAN\nPEJABAT PERBENDAHARAAN NEGERI NEGERI SEMBILAN\nSISTEM PERAKAUNAN LEJAR AM\n\nLAPORAN URUSNIAGA AKAUN AMANAH\nPADA BULAN "
						+ new SimpleDateFormat("MMMM yyyy", new Locale("ms"))
								.format(date).toUpperCase(),
				titleFont));

		genDate.setBorder(0);
		genTime.setBorder(0);
		title.setBorder(0);

		title.setColspan(3);
		title.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		for (int i = 1; i <= count; i++)
		{
			PdfPTable stamp = new PdfPTable(new float[] {0.2f, 0.2f, 0.6f});
			PdfPCell pageNo = new PdfPCell(new Phrase(
					"LAPORAN: L400\nMUKASURAT: " + i + "/" + count, titleFont));

			stamp.setTotalWidth(document.right() - document.left());

			pageNo.setBorder(0);
			pageNo.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

			stamp.addCell(genDate);
			stamp.addCell(genTime);
			stamp.addCell(pageNo);
			stamp.addCell(title);

			stamp.writeSelectedRows(0, -1, 28.35f, 566.65f,
					stamper.getOverContent(i));
		}

		stamper.close();
		reader.close();
		temp.delete();
	}

	/*
	 * public static void main(String[] args) { try (RevenueFacade rFacade = new
	 * RevenueFacade()) { Date date = new GregorianCalendar(2018, 6,
	 * 12).getTime(); ArrayList<String[]> vots79501 = rFacade
	 * .getLaporanUrusniagaAkaunAmanahPadaBulanDanTahunBagiVot79501( date);
	 * ArrayList<String[]> vots79503 = rFacade
	 * .getLaporanUrusniagaAkaunAmanahPadaBulanDanTahunBagiVot79503( date);
	 * LaporanUrusniagaAkaunAmanahPadaBulanDanTahunGenerator.generate( new
	 * File("trusttransaction.pdf"), vots79501, vots79503, date); } catch
	 * (Exception e) { e.printStackTrace(); } }
	 */
}