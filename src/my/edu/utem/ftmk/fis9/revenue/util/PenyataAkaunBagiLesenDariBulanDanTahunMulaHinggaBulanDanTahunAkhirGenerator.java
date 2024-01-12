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

import my.edu.utem.ftmk.fis9.revenue.controller.manager.RevenueFacade;

/**
 * @author Nor Azman Bin Mat Ariff
 */
public class PenyataAkaunBagiLesenDariBulanDanTahunMulaHinggaBulanDanTahunAkhirGenerator
{
	private static final Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN,
			10, Font.BOLD);
	private static final Font contentFont = new Font(
			Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);

	public static void generate(File file, Date startDate, Date endDate,
			String[] header, ArrayList<String[]> contents, String[] balance,
			String[] cess, ArrayList<String[]> trailers) throws Exception
	{
		Document document = new Document(PageSize.A4.rotate(), 28.35f, 28.35f,
				104.88f, 28.35f);
		File temp = new File(
				(file.getParent() == null ? "" : "/" + file.getParent())
						+ "temp.pdf");

		PdfWriter.getInstance(document, new FileOutputStream(temp));

		document.open();

		document.addSubject("Jabatan Perhutanan Negeri Sembilan");
		document.addKeywords("Penyata Akaun Bagi Pelesen/Pemegang Akaun");
		document.addAuthor("JPNS");
		document.addCreator("JPNS");
		document.addTitle("PENYATA AKAUN BAGI PELESEN/PEMEGANG AKAUN");

		Date date = new Date();
		DecimalFormat df = new DecimalFormat("#,###,##0.00");
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM", new Locale("ms"));

		PdfPCell dateCell = new PdfPCell(new Phrase("TARIKH", titleFont));
		PdfPCell typeCell = new PdfPCell(
				new Phrase("JENIS BAYARAN", titleFont));
		PdfPCell refNoCell = new PdfPCell(new Phrase("NO. RUJUKAN", titleFont));
		PdfPCell creditCell = new PdfPCell(new Phrase("KREDIT", titleFont));
		PdfPCell debitCell = new PdfPCell(new Phrase("DEBIT", titleFont));
		PdfPCell revenueCell = new PdfPCell(
				new Phrase("HASIL TERUS", titleFont));

		dateCell.setBorder(0);
		typeCell.setBorder(0);
		refNoCell.setBorder(0);
		creditCell.setBorder(0);
		debitCell.setBorder(0);
		revenueCell.setBorder(0);

		dateCell.setBorderWidthTop(0.5f);
		typeCell.setBorderWidthTop(0.5f);
		refNoCell.setBorderWidthTop(0.5f);
		creditCell.setBorderWidthTop(0.5f);
		debitCell.setBorderWidthTop(0.5f);
		revenueCell.setBorderWidthTop(0.5f);

		dateCell.setBorderWidthBottom(0.5f);
		typeCell.setBorderWidthBottom(0.5f);
		refNoCell.setBorderWidthBottom(0.5f);
		creditCell.setBorderWidthBottom(0.5f);
		debitCell.setBorderWidthBottom(0.5f);
		revenueCell.setBorderWidthBottom(0.5f);

		dateCell.setBorderWidthLeft(0.5f);
		revenueCell.setBorderWidthRight(0.5f);

		dateCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		typeCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		refNoCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		creditCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		debitCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		revenueCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		PdfPTable aboutInner = new PdfPTable(new float[] {0.2f, 0.05f, 0.75f});
		PdfPTable content = new PdfPTable(
				new float[] {0.1f, 0.35f, 0.1f, 0.15f, 0.15f, 0.15f});
		PdfPTable summary = new PdfPTable(new float[] {0.1f, 0.7f, 0.2f});

		content.setHeaderRows(3);

		aboutInner.setWidthPercentage(100f);
		content.setWidthPercentage(100f);
		summary.setWidthPercentage(100f);

		aboutInner.getDefaultCell().setBorder(0);
		content.getDefaultCell().setBorder(0);
		summary.getDefaultCell().setBorder(0);

		content.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		aboutInner.addCell(new Phrase("NAMA PELESEN", contentFont));
		aboutInner.addCell(new Phrase(":", contentFont));
		aboutInner.addCell(new Phrase(header[0], contentFont));
		aboutInner.addCell(new Phrase("NO. PELESEN", contentFont));
		aboutInner.addCell(new Phrase(":", contentFont));
		aboutInner.addCell(new Phrase(header[1], contentFont));
		aboutInner.addCell(new Phrase("DAERAH HUTAN", contentFont));
		aboutInner.addCell(new Phrase(":", contentFont));
		aboutInner.addCell(new Phrase(header[2], contentFont));
		aboutInner.addCell(new Phrase("BULAN", contentFont));
		aboutInner.addCell(new Phrase(":", contentFont));
		aboutInner.addCell(new Phrase(sdf.format(startDate).toUpperCase()
				+ " HINGGA " + sdf.format(endDate).toUpperCase(), contentFont));
		aboutInner.addCell(new Phrase("TAHUN", contentFont));
		aboutInner.addCell(new Phrase(":", contentFont));
		aboutInner.addCell(new Phrase(
				new SimpleDateFormat("yyyy").format(startDate), contentFont));

		PdfPCell aboutOuter = new PdfPCell(aboutInner);

		aboutOuter.setColspan(7);

		content.addCell(aboutOuter);
		content.addCell(new Phrase(" ", contentFont));
		content.completeRow();
		content.addCell(dateCell);
		content.addCell(typeCell);
		content.addCell(refNoCell);
		content.addCell(creditCell);
		content.addCell(debitCell);
		content.addCell(revenueCell);

		double sumCredit = 0, sumDebit = 0, sumRevenue = 0;

		for (String[] row : contents)
		{
			double credit = row[4].equals("") ? 0
					: df.parse(row[4]).doubleValue();
			double debit = row[5].equals("") ? 0
					: df.parse(row[5]).doubleValue();
			double revenue = row[6].equals("") ? 0
					: df.parse(row[6]).doubleValue();
			sumCredit += credit;
			sumDebit += debit;
			sumRevenue += revenue;
			PdfPCell desc = new PdfPCell(new Phrase(row[1], contentFont));
			PdfPCell value1 = new PdfPCell(
					new Phrase(df.format(credit), contentFont));
			PdfPCell value2 = new PdfPCell(
					new Phrase(df.format(debit), contentFont));
			PdfPCell value3 = new PdfPCell(
					new Phrase(df.format(revenue), contentFont));

			desc.setBorder(0);
			value1.setBorder(0);
			value2.setBorder(0);
			value3.setBorder(0);

			value1.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			value2.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			value3.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

			content.addCell(new Phrase(row[0], contentFont));
			content.addCell(desc);
			content.addCell(new Phrase(row[2], contentFont));
			content.addCell(value1);
			content.addCell(value2);
			content.addCell(value3);
		}

		double balance1 = balance[0].equals("") ? 0
				: df.parse(balance[0]).doubleValue();
		double balance2 = balance[1].equals("") ? 0
				: df.parse(balance[1]).doubleValue();
		sumDebit += balance1 + balance2;
		PdfPCell empty = new PdfPCell(new Phrase(" ", contentFont));
		PdfPCell balance1Desc = new PdfPCell(
				new Phrase("* BAKI AKHIR AMANAH LESEN/PERMIT", contentFont));
		PdfPCell balance2Desc = new PdfPCell(
				new Phrase("* BAKI AKHIR AMANAH PELBAGAI", contentFont));
		PdfPCell balance1Value = new PdfPCell(
				new Phrase(df.format(balance1), contentFont));
		PdfPCell balance2Value = new PdfPCell(
				new Phrase(df.format(balance2), contentFont));

		empty.setBorder(0);

		balance1Desc.setColspan(3);
		balance1Desc.setBorder(0);

		balance2Desc.setColspan(3);
		balance2Desc.setBorder(0);

		balance1Value.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		balance1Value.setBorder(0);

		balance2Value.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		balance2Value.setBorder(0);

		content.addCell(empty);
		content.addCell(balance1Desc);
		content.addCell(balance1Value);
		content.completeRow();

		content.addCell(empty);
		content.addCell(balance2Desc);
		content.addCell(balance2Value);
		content.completeRow();

		PdfPCell total = new PdfPCell(
				new Phrase("JUMLAH    \u00A0", titleFont));
		PdfPCell value1 = new PdfPCell(
				new Phrase(df.format(sumCredit), contentFont));
		PdfPCell value2 = new PdfPCell(
				new Phrase(df.format(sumDebit), contentFont));
		PdfPCell value3 = new PdfPCell(
				new Phrase(df.format(sumRevenue), contentFont));

		total.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		total.setColspan(3);
		total.setBorder(0);

		value1.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		value1.setBorderWidthLeft(0);
		value1.setBorderWidthRight(0);

		value2.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		value2.setBorderWidthLeft(0);
		value2.setBorderWidthRight(0);

		value3.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		value3.setBorderWidthLeft(0);
		value3.setBorderWidthRight(0);

		content.addCell(total);
		content.addCell(value1);
		content.addCell(value2);
		content.addCell(value3);

		PdfPCell desc = new PdfPCell(
				new Phrase("RINGKASAN PENYATA KUTIPAN HASIL", titleFont));
		PdfPCell votCell = new PdfPCell(new Phrase("VOT", titleFont));
		PdfPCell revenueTypeCell = new PdfPCell(
				new Phrase("JENIS HASIL", titleFont));
		PdfPCell amountCell = new PdfPCell(new Phrase("AMAUN (RM)", titleFont));

		desc.setBorder(0);
		desc.setColspan(3);
		desc.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		votCell.setBorder(0);
		revenueTypeCell.setBorder(0);
		amountCell.setBorder(0);

		votCell.setBorderWidthTop(0.5f);
		revenueTypeCell.setBorderWidthTop(0.5f);
		amountCell.setBorderWidthTop(0.5f);

		votCell.setBorderWidthBottom(0.5f);
		revenueTypeCell.setBorderWidthBottom(0.5f);
		amountCell.setBorderWidthBottom(0.5f);

		votCell.setBorderWidthLeft(0.5f);
		amountCell.setBorderWidthRight(0.5f);

		votCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		revenueTypeCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		amountCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		summary.addCell(desc);
		summary.addCell(empty);
		summary.completeRow();
		summary.addCell(votCell);
		summary.addCell(revenueTypeCell);
		summary.addCell(amountCell);

		double sum = cess[2].equals("") ? 0 : df.parse(cess[2]).doubleValue();
		PdfPCell vot = new PdfPCell(new Phrase(cess[0], contentFont));
		PdfPCell value = new PdfPCell(new Phrase(df.format(sum), contentFont));

		vot.setBorder(0);
		vot.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		value.setBorder(0);
		value.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

		summary.addCell(vot);
		summary.addCell(new Phrase(cess[1], contentFont));
		summary.addCell(value);

		for (String[] row : trailers)
		{
			double revenue = row[2].equals("") ? 0
					: df.parse(row[2]).doubleValue();
			sum += revenue;
			vot = new PdfPCell(new Phrase(row[0], contentFont));
			PdfPCell amount = new PdfPCell(
					new Phrase(df.format(revenue), contentFont));

			amount.setBorder(0);
			amount.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

			vot.setBorder(0);
			vot.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

			summary.addCell(vot);
			summary.addCell(new Phrase(row[1], contentFont));
			summary.addCell(amount);
		}

		value1 = new PdfPCell(new Phrase(df.format(sum), contentFont));

		total.setColspan(2);

		value1.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		value1.setBorderWidthLeft(0);
		value1.setBorderWidthRight(0);

		summary.addCell(total);
		summary.addCell(value1);

		document.add(content);
		document.newPage();
		document.add(summary);

		document.close();

		PdfReader reader = new PdfReader(new FileInputStream(temp));
		int count = reader.getNumberOfPages();
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(file));
		PdfPCell genDate = new PdfPCell(new Phrase(
				"TARIKH: " + new SimpleDateFormat("dd/MM/yyyy").format(date),
				titleFont));
		PdfPCell genTime = new PdfPCell(new Phrase(
				"MASA: " + new SimpleDateFormat("hh:mm aa").format(date),
				titleFont));
		PdfPCell title = new PdfPCell(new Phrase(
				"KERAJAAN NEGERI SEMBILAN\nJABATAN PERHUTANAN NEGERI SEMBILAN\nPENYATA AKAUN BAGI PELESEN/PEMEGANG AKAUN",
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
					"LAPORAN: LPRN_012\nMUKASURAT: " + i + "/" + count,
					titleFont));

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
			Date startDate = new Date();
			Date endDate = new Date();
			int licenseID = 1;

			String[] header = rFacade
					.getHeaderPenyataAkaunBagiLesenDariBulanDanTahunMulaHinggaBulanDanTahunAkhir();
			ArrayList<String[]> contents = rFacade
					.getContentPenyataAkaunBagiLesenDariBulanDanTahunMulaHinggaBulanDanTahunAkhir(
							startDate, endDate, licenseID);
			String[] balance = rFacade
					.getBalancePenyataAkaunBagiLesenDariBulanDanTahunMulaHinggaBulanDanTahunAkhir();
			String[] cess = rFacade
					.getCessPenyataAkaunBagiLesenDariBulanDanTahunMulaHinggaBulanDanTahunAkhir();
			ArrayList<String[]> trailers = rFacade
					.getTrailerPenyataAkaunBagiLesenDariBulanDanTahunMulaHinggaBulanDanTahunAkhir(
							startDate, endDate, licenseID);

			PenyataAkaunBagiLesenDariBulanDanTahunMulaHinggaBulanDanTahunAkhirGenerator
					.generate(new File("accountstatement.pdf"), startDate,
							endDate, header, contents, balance, cess, trailers);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}