package my.edu.utem.ftmk.fis9.revenue.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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

import my.edu.utem.ftmk.fis9.revenue.controller.manager.RevenueFacade;

/**
 * @author Nor Azman Bin Mat Ariff
 */
public class JurnalGenerator
{
	private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 10,
			Font.BOLD);
	private static Font contentFont = new Font(Font.FontFamily.TIMES_ROMAN, 10,
			Font.NORMAL);

	public static void generate(File file, String[] journal,
			ArrayList<String[]> journalRecords) throws Exception
	{
		Date currentDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Document document = new Document(PageSize.A4, 28.35f, 28.35f, 28.35f,
				28.35f);

		PdfWriter.getInstance(document, new FileOutputStream(file));

		document.open();

		document.addSubject("Jabatan Perhutanan Negeri Sembilan");
		document.addKeywords("Baucar Jurnal");
		document.addAuthor("JPNS");
		document.addCreator("JPNS");
		document.addTitle("BAUCAR JURNAL");

		PdfPTable header = new PdfPTable(new float[] {0.8f, 0.2f});
		PdfPTable header2 = new PdfPTable(
				new float[] {0.3f, 0.2f, 0.2f, 0.15f, 0.15f});
		PdfPTable header3 = new PdfPTable(new float[] {0.2f, 0.1f, 0.7f});
		PdfPTable content = new PdfPTable(new float[] {0.04f, 0.05f, 0.05f,
				0.085f, 0.08f, 0.08f, 0.06f, 0.06f, 0.04f, 0.06f, 0.085f,
				0.085f, 0.05f, 0.085f, 0.09f});
		PdfPTable footer = new PdfPTable(
				new float[] {0.15f, 0.55f, 0.15f, 0.15f});
		PdfPCell title = new PdfPCell(new Phrase(
				"KERAJAAN NEGERI SEMBILAN\nBAUCAR JURNAL", titleFont));
		PdfPCell pageNo = new PdfPCell(new Phrase(
				"(Kew. 306E - Pin. 1/09)\nMuka surat:  1/1", contentFont));
		PdfPCell year = new PdfPCell(
				new Phrase("Tahun Kewangan: " + journal[0], contentFont));
		PdfPCell about = new PdfPCell(new Phrase(journal[8], contentFont));
		PdfPCell remark = new PdfPCell(new Phrase(
				"PINDAHAN / PELARASAN DIMASUK KIRA KE DALAM AKAUN DI BAWAH",
				contentFont));

		PdfPCell no = new PdfPCell(new Phrase("Bil.", contentFont));
		PdfPCell vot = new PdfPCell(new Phrase("Vot", contentFont));
		PdfPCell dept = new PdfPCell(new Phrase("Jab.", contentFont));
		PdfPCell centre = new PdfPCell(new Phrase("PTJ", contentFont));
		PdfPCell program = new PdfPCell(
				new Phrase("Program / Aktiviti", contentFont));
		PdfPCell trust = new PdfPCell(new Phrase("Amanah", contentFont));
		PdfPCell project = new PdfPCell(new Phrase("Projek", contentFont));
		PdfPCell provide = new PdfPCell(new Phrase("Sedia", contentFont));
		PdfPCell cp = new PdfPCell(new Phrase("CP", contentFont));
		PdfPCell object = new PdfPCell(new Phrase("Objek", contentFont));
		PdfPCell dt = new PdfPCell(new Phrase("Amaun (DT)", contentFont));
		PdfPCell kt = new PdfPCell(new Phrase("Amaun (KT)", contentFont));
		PdfPCell paid = new PdfPCell(new Phrase("Membayar", contentFont));
		PdfPCell code = new PdfPCell(
				new Phrase("Kod Penerima / Pembayar", contentFont));
		PdfPCell internal = new PdfPCell(
				new Phrase("Kod Penerima / Pembayar", contentFont));
		PdfPCell rm = new PdfPCell(new Phrase("RM", contentFont));
		PdfPCell dept2 = new PdfPCell(new Phrase("Jab.", contentFont));
		PdfPCell centre2 = new PdfPCell(new Phrase("PTJ", contentFont));

		PdfPCell provider = new PdfPCell(new Phrase(journal[9], contentFont));
		PdfPCell designation = new PdfPCell(new Phrase(journal[10], contentFont));
		PdfPCell date = new PdfPCell(new Phrase(sdf.format(currentDate), contentFont));
		PdfPCell blank = new PdfPCell(new Phrase(" ", contentFont));
		PdfPCell blank3 = new PdfPCell(new Phrase(" ", contentFont));

		title.setBorderWidthRight(0);
		pageNo.setBorderWidthLeft(0);

		year.setColspan(2);
		about.setColspan(2);
		remark.setColspan(3);
		provider.setColspan(3);
		blank3.setColspan(3);

		no.setRowspan(2);
		paid.setColspan(2);
		code.setRowspan(2);
		internal.setColspan(9);

		title.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		year.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		remark.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		no.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		vot.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		dept.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		centre.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		program.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		trust.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		project.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		provide.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cp.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		object.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		dt.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		kt.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		paid.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		code.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		internal.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		rm.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		dept2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		centre2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		no.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		vot.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		dept.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		centre.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		program.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		trust.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		project.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		provide.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cp.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		object.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		dt.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		kt.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		paid.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		code.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		internal.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		rm.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		dept2.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		centre2.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);

		header.setWidthPercentage(100f);
		header2.setWidthPercentage(100f);
		header3.setWidthPercentage(100f);
		content.setWidthPercentage(100f);
		footer.setWidthPercentage(100f);

		header2.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		content.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		header.addCell(title);
		header.addCell(pageNo);
		header.addCell(year);

		header2.addCell(new Phrase("Jenis Urusniaga", contentFont));
		header2.addCell(new Phrase("Kod Pejabat Perakaunan", contentFont));
		header2.addCell(new Phrase("No. Baucar", contentFont));
		header2.addCell(new Phrase("Tarikh Baucar", contentFont));
		header2.addCell(new Phrase("AP 58(a)", contentFont));
		header2.addCell(new Phrase(journal[1], contentFont));
		header2.addCell(new Phrase("", contentFont));
		header2.addCell(new Phrase(journal[2], contentFont));
		header2.addCell(new Phrase(journal[3], contentFont));
		header2.addCell(new Phrase("N", contentFont));

		header3.addCell(new Phrase("Kod Jab. Menyedia", contentFont));
		header3.addCell(new Phrase(journal[4], contentFont));
		header3.addCell(new Phrase(journal[5], contentFont));
		header3.addCell(new Phrase("Kod PTJ Menyedia", contentFont));
		header3.addCell(new Phrase(journal[6], contentFont));
		header3.addCell(new Phrase(journal[7], contentFont));
		header3.addCell(new Phrase("Perihal Jurnal", contentFont));
		header3.addCell(about);
		header3.addCell(remark);

		content.addCell(no);
		content.addCell(vot);
		content.addCell(dept);
		content.addCell(centre);
		content.addCell(program);
		content.addCell(trust);
		content.addCell(project);
		content.addCell(provide);
		content.addCell(cp);
		content.addCell(object);
		content.addCell(dt);
		content.addCell(kt);
		content.addCell(paid);
		content.addCell(code);
		content.addCell(internal);
		content.addCell(rm);
		content.addCell(rm);
		content.addCell(dept2);
		content.addCell(centre2);

		double[] sum = new double[2];
		int size = journalRecords.size();
		DecimalFormat df = new DecimalFormat("#,###,##0.00");

		for (int i = 0; i < size; i++)
		{
			String[] row = journalRecords.get(i);
			double value1 = Double.parseDouble(row[4]),
					value2 = Double.parseDouble(row[5]);
			sum[0] += value1;
			sum[1] += value2;

			PdfPCell dtValue = new PdfPCell(
					new Phrase(df.format(value1), contentFont));
			PdfPCell ktValue = new PdfPCell(
					new Phrase(df.format(value1), contentFont));

			dtValue.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			ktValue.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

			content.addCell(new Phrase(String.valueOf(i + 1), contentFont));
			content.addCell(blank);
			content.addCell(new Phrase(row[0], contentFont));
			content.addCell(new Phrase(row[1], contentFont));
			content.addCell(blank);
			content.addCell(new Phrase(row[2], contentFont));
			content.addCell(blank);
			content.addCell(blank);
			content.addCell(blank);
			content.addCell(new Phrase(row[3], contentFont));
			content.addCell(dtValue);
			content.addCell(ktValue);
			content.addCell(new Phrase(row[6], contentFont));
			content.addCell(new Phrase(row[7], contentFont));
			content.addCell(blank);
		}

		PdfPCell sumControl = new PdfPCell(
				new Phrase("Jumlah Kawalan (RM)", contentFont));
		PdfPCell sumDT = new PdfPCell(
				new Phrase(df.format(sum[0]), contentFont));
		PdfPCell sumKT = new PdfPCell(
				new Phrase(df.format(sum[1]), contentFont));
		PdfPCell sumCount = new PdfPCell(
				new Phrase("Jumlah Bil. Amaun", contentFont));

		sumControl.setColspan(10);
		sumCount.setColspan(2);

		sumControl.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		sumDT.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		sumKT.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

		sumControl.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		sumDT.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		sumKT.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		sumCount.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);

		content.addCell(sumControl);
		content.addCell(sumDT);
		content.addCell(sumKT);
		content.addCell(sumCount);
		content.completeRow();

		footer.addCell(new Phrase("Penyedia", contentFont));
		footer.addCell(provider);
		footer.addCell(new Phrase("Jawatan", contentFont));
		footer.addCell(designation);
		footer.addCell(new Phrase("Tarikh", contentFont));
		footer.addCell(date);
		footer.addCell(new Phrase("Semak", contentFont));
		footer.addCell(blank);
		footer.addCell(new Phrase("Jawatan", contentFont));
		footer.addCell(blank);
		footer.addCell(new Phrase("Tarikh", contentFont));
		footer.addCell(blank);
		footer.addCell(new Phrase("Lulus", contentFont));
		footer.addCell(blank3);
		footer.addCell(new Phrase("Jawatan", contentFont));
		footer.addCell(blank);
		footer.addCell(new Phrase("Tarikh", contentFont));
		footer.addCell(blank);
		footer.addCell(new Phrase("Dibatalkan", contentFont));
		footer.addCell(blank3);
		footer.addCell(new Phrase("Jawatan", contentFont));
		footer.addCell(blank);
		footer.addCell(new Phrase("Tarikh", contentFont));
		footer.addCell(blank);

		document.add(header);
		document.add(header2);
		document.add(header3);
		document.add(content);
		document.add(new Paragraph("\n\n", contentFont));
		document.add(footer);

		document.close();
	}

	public static void main(String[] args)
	{
		try (RevenueFacade rFacade = new RevenueFacade();)
		{
			int journalID = 7;
			String[] journal = rFacade.getJournalHeader(journalID);
			ArrayList<String[]> journalRecords = rFacade
					.getJournalRecords(journalID);

			JurnalGenerator.generate(new File("journal.pdf"), journal,
					journalRecords);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}