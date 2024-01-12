package my.edu.utem.ftmk.fis9.revenue.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TreeSet;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import my.edu.utem.ftmk.fis9.revenue.controller.manager.RevenueFacade;

/**
 * @author Nor Azman Bin Mat Ariff
 */
public class LaporanUrusniagaAkaunHasilPadaBulanDanTahunGenerator
{
	private static final Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN,
			10, Font.BOLD);
	private static final Font contentFont = new Font(
			Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);

	public static void generate(File file, Date date, String[] header,
			ArrayList<String[]> contents) throws Exception
	{
		Document document = new Document(PageSize.A4.rotate(), 28.35f, 28.35f,
				127.56f, 28.35f);
		File temp = new File(
				(file.getParent() == null ? "" : "/" + file.getParent())
						+ "temp.pdf");

		PdfWriter.getInstance(document, new FileOutputStream(temp));

		document.open();

		document.addSubject("Jabatan Perhutanan Negeri Sembilan");
		document.addKeywords("Laporan Urusniaga Akaun Hasil");
		document.addAuthor("JPNS");
		document.addCreator("JPNS");
		document.addTitle("LAPORAN URUSNIAGA AKAUN HASIL");

		TreeSet<String> criterias = new TreeSet<>();

		for (String[] row : contents)
		{
			if (!criterias.contains(row[0]))
			{
				criterias.add(row[0]);
			}
		}

		DecimalFormat df = new DecimalFormat("#,###,##0.00");
		PdfPCell receiptDate = new PdfPCell(new Phrase("TARIKH", titleFont));
		PdfPCell type = new PdfPCell(new Phrase("JENIS URUS", titleFont));
		PdfPCell docNo = new PdfPCell(new Phrase("NO. DOKUMEN", titleFont));
		PdfPCell receiptNo = new PdfPCell(new Phrase("NO. RESIT", titleFont));
		PdfPCell month = new PdfPCell(new Phrase("BULAN INI", titleFont));
		PdfPCell balance = new PdfPCell(new Phrase("BAKI", titleFont));
		PdfPCell month_debit = new PdfPCell(new Phrase("DEBIT", titleFont));
		PdfPCell month_credit = new PdfPCell(new Phrase("KREDIT", titleFont));
		PdfPCell year_balance = new PdfPCell(
				new Phrase("TAHUN INI", titleFont));
		PdfPCell past_balance = new PdfPCell(
				new Phrase("TAHUN LEPAS", titleFont));

		receiptDate.setBorder(0);
		type.setBorder(0);
		docNo.setBorder(0);
		receiptNo.setBorder(0);
		month.setBorder(0);
		balance.setBorder(0);
		month_debit.setBorder(0);
		month_credit.setBorder(0);
		year_balance.setBorder(0);
		past_balance.setBorder(0);

		receiptDate.setRowspan(2);
		type.setRowspan(2);
		docNo.setRowspan(2);
		receiptNo.setRowspan(2);
		month.setColspan(2);
		balance.setColspan(2);

		receiptDate.setBorderWidthTop(0.5f);
		type.setBorderWidthTop(0.5f);
		docNo.setBorderWidthTop(0.5f);
		receiptNo.setBorderWidthTop(0.5f);
		month.setBorderWidthTop(0.5f);
		balance.setBorderWidthTop(0.5f);

		receiptDate.setBorderWidthBottom(0.5f);
		type.setBorderWidthBottom(0.5f);
		docNo.setBorderWidthBottom(0.5f);
		receiptNo.setBorderWidthBottom(0.5f);
		month_debit.setBorderWidthBottom(0.5f);
		month_credit.setBorderWidthBottom(0.5f);
		year_balance.setBorderWidthBottom(0.5f);
		past_balance.setBorderWidthBottom(0.5f);

		receiptDate.setBorderWidthLeft(0.5f);
		balance.setBorderWidthRight(0.5f);
		past_balance.setBorderWidthRight(0.5f);

		receiptDate.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		type.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		docNo.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		receiptNo.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		month.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		balance.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		month_debit.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		month_credit.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		year_balance.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		past_balance.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		for (String criteria : criterias)
		{
			String[] current = criteria.split("\t");
			PdfPTable aboutInner = new PdfPTable(
					new float[] {0.2f, 0.05f, 0.1f, 0.65f});
			PdfPTable content = new PdfPTable(new float[] {0.1f, 0.1f, 0.1f,
					0.1f, 0.15f, 0.15f, 0.15f, 0.15f});

			content.setHeaderRows(4);

			aboutInner.setWidthPercentage(100f);
			content.setWidthPercentage(100f);

			aboutInner.getDefaultCell().setBorder(0);
			content.getDefaultCell().setBorder(0);

			content.getDefaultCell()
					.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

			aboutInner.addCell(new Phrase("TAHUN", titleFont));
			aboutInner.addCell(new Phrase(":", titleFont));
			aboutInner.addCell(new Phrase(
					new SimpleDateFormat("yyyy").format(date), contentFont));
			aboutInner.addCell(new Phrase("", titleFont));
			aboutInner.addCell(new Phrase("JAB. PEMBAYAR", titleFont));
			aboutInner.addCell(new Phrase(":", titleFont));
			aboutInner.addCell(new Phrase("0080", contentFont));
			aboutInner.addCell(
					new Phrase("JABATAN PERHUTANAN NEGERI", contentFont));
			aboutInner.addCell(new Phrase("PTJ PEMBAYAR", titleFont));
			aboutInner.addCell(new Phrase(":", titleFont));
			aboutInner.addCell(new Phrase("80000100", contentFont));
			aboutInner.addCell(new Phrase(
					"IBU PEJABAT JABATAN PERHUTANAN NEGERI", contentFont));
			aboutInner.addCell(new Phrase("JAB. DIPERTANGGUNGKAN", titleFont));
			aboutInner.addCell(new Phrase(":", titleFont));
			aboutInner.addCell(new Phrase("0080", contentFont));
			aboutInner.addCell(
					new Phrase("JABATAN PERHUTANAN NEGERI", contentFont));
			aboutInner.addCell(new Phrase("PTJ DIPERTANGGUNGKAN", titleFont));
			aboutInner.addCell(new Phrase(":", titleFont));
			aboutInner.addCell(new Phrase("80000100", contentFont));
			aboutInner.addCell(new Phrase(
					"IBU PEJABAT JABATAN PERHUTANAN NEGERI", contentFont));
			aboutInner.addCell(new Phrase("HASIL", titleFont));
			aboutInner.addCell(new Phrase(":", titleFont));
			aboutInner.addCell(new Phrase(current[0], contentFont));
			aboutInner.addCell(new Phrase(current[1], contentFont));

			PdfPCell aboutOuter = new PdfPCell(aboutInner);

			aboutOuter.setColspan(8);

			content.addCell(aboutOuter);
			content.addCell(new Phrase(" ", contentFont));
			content.completeRow();
			content.addCell(receiptDate);
			content.addCell(type);
			content.addCell(docNo);
			content.addCell(receiptNo);
			content.addCell(month);
			content.addCell(balance);
			content.addCell(month_debit);
			content.addCell(month_credit);
			content.addCell(year_balance);
			content.addCell(past_balance);

			double sumCD = 0, sumCC = 0, sumCY = 0, sumLY = 0;

			for (String[] row : contents)
			{
				if (row[0].equals(criteria))
				{
					double debit = row[5].equals("") ? 0
							: df.parse(row[5]).doubleValue(),
							credit = row[6].equals("") ? 0
									: df.parse(row[6]).doubleValue(),
							year = row[7].equals("") ? 0
									: df.parse(row[7]).doubleValue(),
							past = row[8].equals("") ? 0
									: df.parse(row[8]).doubleValue();
					sumCD += debit;
					sumCC += credit;
					sumCY += year;
					sumLY += past;
					PdfPCell value1 = new PdfPCell(
							new Phrase(df.format(debit), contentFont));
					PdfPCell value2 = new PdfPCell(
							new Phrase(df.format(credit), contentFont));
					PdfPCell value3 = new PdfPCell(
							new Phrase(df.format(year), contentFont));
					PdfPCell value4 = new PdfPCell(
							new Phrase(df.format(past), contentFont));

					value1.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
					value1.setBorder(0);

					value2.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
					value2.setBorder(0);

					value3.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
					value3.setBorder(0);

					value4.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
					value4.setBorder(0);

					content.addCell(new Phrase(row[1], contentFont));
					content.addCell(new Phrase(row[2], contentFont));
					content.addCell(new Phrase(row[3], contentFont));
					content.addCell(new Phrase(row[4], contentFont));
					content.addCell(value1);
					content.addCell(value2);
					content.addCell(value3);
					content.addCell(value4);
				}
			}

			PdfPCell total = new PdfPCell(
					new Phrase("JUMLAH HASIL:    \u00A0", titleFont));
			PdfPCell value1 = new PdfPCell(
					new Phrase(df.format(sumCD), contentFont));
			PdfPCell value2 = new PdfPCell(
					new Phrase(df.format(sumCC), contentFont));
			PdfPCell value3 = new PdfPCell(
					new Phrase(df.format(sumCC - sumCD), contentFont));
			PdfPCell value4 = new PdfPCell(
					new Phrase(df.format(sumCY + sumCC - sumCD), contentFont));
			PdfPCell value5 = new PdfPCell(
					new Phrase(df.format(sumLY), contentFont));

			total.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			total.setColspan(6);
			total.setBorder(0);

			value1.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			value1.setBorderWidthLeft(0);
			value1.setBorderWidthRight(0);

			value2.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			value2.setBorderWidthLeft(0);
			value2.setBorderWidthRight(0);

			value3.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			value3.setBorder(0);

			value4.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			value4.setBorderWidthLeft(0);
			value4.setBorderWidthRight(0);

			value5.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			value5.setBorderWidthLeft(0);
			value5.setBorderWidthRight(0);

			content.addCell(new Phrase(" ", contentFont));
			content.addCell(new Phrase(" ", contentFont));
			content.addCell(new Phrase(" ", contentFont));
			content.addCell(new Phrase(" ", contentFont));
			content.addCell(value1);
			content.addCell(value2);
			content.addCell(value3);
			content.addCell(new Phrase(" ", contentFont));
			content.addCell(new Phrase(" ", contentFont));
			content.completeRow();
			content.addCell(total);
			content.addCell(value4);
			content.addCell(value5);

			document.add(content);
			document.newPage();
		}

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
				"KERAJAAN NEGERI NEGERI SEMBILAN\nPEJABAT AKAUNTAN NEGERI NEGERI SEMBILAN\nSISTEM PERAKAUNAN LEJAR AM\n\nLAPORAN URUSNIAGA AKAUN HASIL\nPADA BULAN "
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
					"MUKASURAT: " + i + "/" + count, titleFont));

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
	public static void main(String[] args)
	{
		try (RevenueFacade rFacade = new RevenueFacade();)
		{
			Date date = new Date();
			int monthInt = 0;
			String year = "2019";
			String[] header = rFacade
					.getHeaderLaporanUrusniagaAkaunHasilPadaBulanDanTahun();
			ArrayList<String[]> vots = rFacade
					.getLaporanUrusniagaPadaBulanDanTahun(
							monthInt,
							Integer.parseInt(
									year),
							false);

			LaporanUrusniagaAkaunHasilPadaBulanDanTahunGenerator.generate(
					new File("revenuetransaction.pdf"), date, header, vots);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}