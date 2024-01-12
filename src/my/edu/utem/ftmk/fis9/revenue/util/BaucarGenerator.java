package my.edu.utem.ftmk.fis9.revenue.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import my.edu.utem.ftmk.fis9.global.util.MalayNumberConverter;

/**
 * @author Nor Azman Bin Mat Ariff
 */
public class BaucarGenerator
{
	private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 10,
			Font.BOLD);
	private static Font contentFont = new Font(Font.FontFamily.TIMES_ROMAN, 10,
			Font.NORMAL);

	public static void generate(File file, String[] baucar,
			ArrayList<String[]> baucarRecords) throws Exception
	{
		Document document = new Document(PageSize.A4, 28.35f, 28.35f, 28.35f,
				28.35f);

		PdfWriter.getInstance(document, new FileOutputStream(file));

		document.open();

		document.addSubject("Jabatan Perhutanan Negeri Sembilan");
		document.addKeywords("Baucar Bayaran");
		document.addAuthor("JPNS");
		document.addCreator("JPNS");
		document.addTitle("BAUCAR BAYARAN");

		Paragraph gap = new Paragraph("\n", contentFont);
		PdfPTable header = new PdfPTable(new float[] {0.8f, 0.2f});
		PdfPTable header2 = new PdfPTable(
				new float[] {0.15f, 0.3f, 0.2f, 0.15f, 0.1f, 0.1f});
		PdfPTable header3 = new PdfPTable(new float[] {0.15f, 0.3f, 0.55f});
		PdfPTable header4 = new PdfPTable(new float[] {0.2f, 0.8f});
		PdfPTable header5 = new PdfPTable(new float[] {0.2f, 0.2f, 0.08f, 0.09f,
				0.08f, 0.09f, 0.08f, 0.18f});
		PdfPTable header6 = new PdfPTable(new float[] {0.2f, 0.15f, 0.65f});
		PdfPTable header7 = new PdfPTable(
				new float[] {0.2f, 0.25f, 0.05f, 0.09f, 0.1f, 0.2f, 0.1f});
		PdfPTable header8 = new PdfPTable(6);

		PdfPTable content = new PdfPTable(new float[] {0.06f, 0.06f, 0.09f,
				0.09f, 0.09f, 0.08f, 0.08f, 0.05f, 0.08f, 0.14f, 0.12f, 0.06f});
		PdfPTable footer = new PdfPTable(
				new float[] {0.15f, 0.4f, 0.15f, 0.15f, 0.15f});
		PdfPCell title = new PdfPCell(new Phrase(
				"KERAJAAN NEGERI NEGERI SEMBILAN\nBAUCAR BAYARAN", titleFont));
		PdfPCell pageNo = new PdfPCell(new Phrase(
				"(Kew. 304E - Pin. 1/09)\nMuka surat:  1/1", contentFont));
		PdfPCell year = new PdfPCell(
				new Phrase("Tahun Kewangan: " + baucar[0], contentFont));

		PdfPCell method = new PdfPCell(new Phrase("CARA BAYARAN", contentFont));
		PdfPCell internal = new PdfPCell(new Phrase(
				"UNTUK KEGUNAAN PEJABAT PERAKAUNAN SAHAJA", contentFont));
		PdfPCell about = new PdfPCell(
				new Phrase("Perihal Bayaran", contentFont));
		PdfPCell aboutContent = new PdfPCell(
				new Phrase(baucar[13], contentFont));
		PdfPCell no = new PdfPCell(new Phrase("No. EFT / Cek", contentFont));
		PdfPCell date = new PdfPCell(new Phrase("Tarikh", contentFont));
		PdfPCell noEFT = new PdfPCell(new Phrase(baucar[14], contentFont));
		PdfPCell dateEFT = new PdfPCell(new Phrase(baucar[15], contentFont));
		PdfPCell noCek = new PdfPCell(new Phrase(" ", contentFont));
		PdfPCell dateCek = new PdfPCell(new Phrase(" ", contentFont));
		PdfPCell noTT = new PdfPCell(new Phrase(" ", contentFont));
		PdfPCell dateTT = new PdfPCell(new Phrase(" ", contentFont));

		PdfPCell remark = new PdfPCell(new Phrase(
				"PERBELANJAAN DIMASUK KIRA KE DALAM AKAUN-AKAUN DI BAWAH",
				contentFont));
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
		PdfPCell amount = new PdfPCell(new Phrase("Amaun (RM)", contentFont));
		PdfPCell code = new PdfPCell(
				new Phrase("Kod Kegunaan Jab.", contentFont));

		PdfPCell blank = new PdfPCell(new Phrase(" ", contentFont));
		PdfPCell blank2 = new PdfPCell(new Phrase(" ", contentFont));
		PdfPCell blank3 = new PdfPCell(new Phrase(" ", contentFont));

		title.setBorderWidthRight(0);
		pageNo.setBorderWidthLeft(0);

		internal.setBorderWidthTop(2);
		internal.setBorderWidthLeft(2);
		internal.setBorderWidthRight(2);
		no.setBorderWidthLeft(2);
		date.setBorderWidthRight(2);
		noEFT.setBorderWidthLeft(2);
		dateEFT.setBorderWidthRight(2);
		noCek.setBorderWidthLeft(2);
		dateCek.setBorderWidthRight(2);
		noTT.setBorderWidthLeft(2);
		noTT.setBorderWidthBottom(2);
		dateTT.setBorderWidthRight(2);
		dateTT.setBorderWidthBottom(2);

		year.setColspan(2);
		blank2.setColspan(3);
		blank3.setColspan(3);
		remark.setColspan(12);
		code.setColspan(2);
		method.setColspan(3);
		internal.setColspan(2);
		about.setRowspan(4);
		aboutContent.setRowspan(4);

		title.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		year.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		remark.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		vot.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		dept.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		centre.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		program.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		trust.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		project.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		provide.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cp.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		object.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		amount.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		code.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		method.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		internal.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		no.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		date.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		noEFT.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		dateEFT.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		noCek.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		dateCek.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		noTT.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		dateTT.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		header.setWidthPercentage(100f);
		header2.setWidthPercentage(100f);
		header3.setWidthPercentage(100f);
		header4.setWidthPercentage(100f);
		header5.setWidthPercentage(100f);
		header6.setWidthPercentage(100f);
		header7.setWidthPercentage(100f);
		header8.setWidthPercentage(100f);
		content.setWidthPercentage(100f);
		footer.setWidthPercentage(100f);

		header2.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		header7.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		header8.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		content.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		header.addCell(title);
		header.addCell(pageNo);
		header.addCell(year);

		header2.addCell(new Phrase("Jenis Urusniaga", contentFont));
		header2.addCell(new Phrase("Kod Pejabat Perakaunan", contentFont));
		header2.addCell(new Phrase("No. Baucar", contentFont));
		header2.addCell(new Phrase("Tarikh Baucar", contentFont));
		header2.addCell(new Phrase("AP 96(a)", contentFont));
		header2.addCell(new Phrase("AP 58(a)", contentFont));
		header2.addCell(new Phrase(baucar[1], contentFont));
		header2.addCell(new Phrase("", contentFont));
		header2.addCell(new Phrase(baucar[2], contentFont));
		header2.addCell(new Phrase(baucar[3], contentFont));
		header2.addCell(new Phrase("N", contentFont));
		header2.addCell(new Phrase("N", contentFont));

		header3.addCell(new Phrase("Kod Jab.", contentFont));
		header3.addCell(new Phrase(baucar[4], contentFont));
		header3.addCell(new Phrase(baucar[5], contentFont));
		header3.addCell(new Phrase("Kod PTJ", contentFont));
		header3.addCell(new Phrase(baucar[6], contentFont));
		header3.addCell(new Phrase(baucar[7], contentFont));
		header3.addCell(new Phrase("Perihal Jurnal", contentFont));

		header4.addCell(new Phrase("Nama Penerima", contentFont));
		header4.addCell(new Phrase(baucar[8], contentFont));
		header4.addCell(new Phrase("No. KP / Daftar Syarikat", contentFont));
		header4.addCell(new Phrase(baucar[9], contentFont));
		header4.addCell(new Phrase("Alamat Penerima", contentFont));
		header4.addCell(blank);

		header5.addCell(new Phrase("E-mel Penerima", contentFont));
		header5.addCell(blank);
		header5.addCell(new Phrase("No. HP", contentFont));
		header5.addCell(blank);
		header5.addCell(new Phrase("No. Faks", contentFont));
		header5.addCell(blank);
		header5.addCell(new Phrase("No. Gaji", contentFont));
		header5.addCell(blank);

		header6.addCell(new Phrase("Kod dan Nama Bank Penerima", contentFont));
		header6.addCell(new Phrase(baucar[10], contentFont));
		header6.addCell(new Phrase(baucar[11], contentFont));

		header7.addCell(new PdfPCell(
				new Phrase("No. Akaun Bank Penerima", contentFont)));
		header7.addCell(new PdfPCell(new Phrase(baucar[12], contentFont)));
		header7.addCell(method);
		header7.addCell(internal);
		header7.addCell(about);
		header7.addCell(aboutContent);
		header7.addCell(blank);
		header7.addCell(new Phrase("Tandakan (X)", contentFont));
		header7.addCell(new Phrase("Berganda (Bil.)", contentFont));
		header7.addCell(no);
		header7.addCell(date);
		header7.addCell(new Phrase("EFT", contentFont));
		header7.addCell(new Phrase("", contentFont));
		header7.addCell(new Phrase("", contentFont));
		header7.addCell(noEFT);
		header7.addCell(dateEFT);
		header7.addCell(new Phrase("Cek", contentFont));
		header7.addCell(blank);
		header7.addCell(blank);
		header7.addCell(noCek);
		header7.addCell(dateCek);
		header7.addCell(new Phrase("TT", contentFont));
		header7.addCell(blank);
		header7.addCell(blank);
		header7.addCell(noTT);
		header7.addCell(dateTT);

		PdfPCell order = new PdfPCell(new Phrase("Pesanan", contentFont));
		PdfPCell invoice = new PdfPCell(new Phrase("Invois Pembekal", contentFont));
		PdfPCell total = new PdfPCell(new Phrase("Jumlah (RM)", contentFont));
		
		order.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		invoice.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		total.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		
		order.setColspan(3);
		invoice.setColspan(3);
		total.setColspan(2);
		
		header8.addCell(order);
		header8.addCell(invoice);
		header8.addCell(new Phrase("Tarikh", contentFont));
		header8.addCell(new Phrase("No. Rujukan", contentFont));
		header8.addCell(new Phrase("Amaun (RM)", contentFont));
		header8.addCell(new Phrase("Tarikh", contentFont));
		header8.addCell(new Phrase("No. Rujukan", contentFont));
		header8.addCell(new Phrase("Amaun (RM)", contentFont));
		header8.addCell(blank);
		header8.addCell(new Phrase("", contentFont));
		header8.addCell(blank);
		header8.addCell(new Phrase(baucar[16], contentFont));
		header8.addCell(new Phrase(baucar[17], contentFont));
		header8.addCell(new Phrase(baucar[18], contentFont));
		header8.addCell(total);
		header8.addCell(blank);
		header8.addCell(total);
		header8.addCell(new Phrase(baucar[18], contentFont));
		
		content.addCell(remark);
		content.addCell(vot);
		content.addCell(dept);
		content.addCell(centre);
		content.addCell(program);
		content.addCell(trust);
		content.addCell(project);
		content.addCell(provide);
		content.addCell(cp);
		content.addCell(object);
		content.addCell(amount);
		content.addCell(code);

		double sum = 0;
		DecimalFormat df = new DecimalFormat("0.00");

		for (String[] row : baucarRecords)
		{
			double value = Double.parseDouble(row[3].replaceAll(",", ""));
			sum += value;

			PdfPCell dtValue = new PdfPCell(
					new Phrase(df.format(value), contentFont));

			dtValue.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

			content.addCell(blank);
			content.addCell(new Phrase(row[0], contentFont));
			content.addCell(new Phrase(row[1], contentFont));
			content.addCell(blank);
			content.addCell(new Phrase(row[2], contentFont));
			content.addCell(blank);
			content.addCell(blank);
			content.addCell(blank);
			content.addCell(blank);
			content.addCell(dtValue);
			content.addCell(blank2);
		}

		PdfPCell sumControl = new PdfPCell(
				new Phrase("Jumlah Bersih (RM)", contentFont));
		PdfPCell sumDT = new PdfPCell(new Phrase(df.format(sum), contentFont));
		PdfPCell sumCount = new PdfPCell(
				new Phrase("Jumlah Bil. Akaun Subsidiari", contentFont));
		PdfPCell sumAmount = new PdfPCell(new Phrase(
				"Amaun: " + MalayNumberConverter.toMalay(sum), contentFont));

		sumControl.setColspan(9);
		sumAmount.setColspan(12);

		sumControl.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		sumDT.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

		sumControl.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		sumDT.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		sumCount.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);

		content.addCell(sumControl);
		content.addCell(sumDT);
		content.addCell(sumCount);
		content.completeRow();
		content.addCell(sumAmount);

		PdfPCell providerNote = new PdfPCell(
				new Phrase("AP 95 dipatuhi", contentFont));
		PdfPCell verifierNote = new PdfPCell(
				new Phrase("AP 95, AP 99, dan AP 102 dipatuhi", contentFont));
		PdfPCell approverNote = new PdfPCell(
				new Phrase("AP 95, AP 99, dan AP 102 dipatuhi", contentFont));
		PdfPCell canceler = new PdfPCell(new Phrase(" ", contentFont));
		PdfPCell designation = new PdfPCell(new Phrase(" ", contentFont));

		providerNote.setColspan(3);
		verifierNote.setColspan(3);
		approverNote.setColspan(3);
		canceler.setColspan(4);
		designation.setColspan(2);

		footer.addCell(new Phrase("Penyedia", contentFont));
		footer.addCell(new Phrase(baucar[19], contentFont));
		footer.addCell(providerNote);
		footer.addCell(new Phrase("Jawatan", contentFont));
		footer.addCell(designation);
		footer.addCell(new Phrase("Tarikh", contentFont));
		footer.addCell(blank);
		footer.addCell(new Phrase("Semak", contentFont));
		footer.addCell(blank);
		footer.addCell(verifierNote);
		footer.addCell(new Phrase("Jawatan", contentFont));
		footer.addCell(designation);
		footer.addCell(new Phrase("Tarikh", contentFont));
		footer.addCell(blank);
		footer.addCell(new Phrase("Lulus", contentFont));
		footer.addCell(blank);
		footer.addCell(approverNote);
		footer.addCell(new Phrase("Jawatan", contentFont));
		footer.addCell(designation);
		footer.addCell(new Phrase("Tarikh", contentFont));
		footer.addCell(blank);
		footer.addCell(new Phrase("Dibatalkan", contentFont));
		footer.addCell(canceler);
		footer.addCell(new Phrase("Jawatan", contentFont));
		footer.addCell(designation);
		footer.addCell(new Phrase("Tarikh", contentFont));
		footer.addCell(blank);

		document.add(header);
		document.add(gap);
		document.add(header2);
		document.add(header3);
		document.add(gap);
		document.add(header4);
		document.add(header5);
		document.add(header6);
		document.add(header7);
		document.add(gap);
		document.add(header8);
		document.add(gap);
		document.add(content);
		document.add(gap);
		document.add(gap);
		document.add(footer);
		document.add(new Paragraph(
				"No. rujukan kelulusan BPKS(8.15) 248-10 SK.6 JLD 35(60)\nDokumen ini dicetak dari pangkalan data dan telah ditandatangi secara digital. Tandatangan secara manual tidak diperlukan.",
				contentFont));

		document.close();
	}

	public static void main(String[] args)
	{
/*		try (RevenueFacade rFacade = new RevenueFacade();)
		{
			int baucarID = 5;
			String[] baucar = rFacade.getVoucherHeader(baucarID);
			ArrayList<String[]> baucarRecords = rFacade
					.getVoucherRecords(baucarID);

			BaucarGenerator.generate(new File("voucher.pdf"), baucar,
					baucarRecords);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}*/
	}
}