package my.edu.utem.ftmk.fis9.revenue.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import my.edu.utem.ftmk.fis9.global.util.MalayNumberConverter;

/**
 * @author Nor Azman Bin Mat Ariff
 */
public class ResitGenerator
{
	public static void generate(File file, String[] receipt,
			ArrayList<String[]> recordReceipts) throws Exception
	{
		Document document = new Document(PageSize.A4, 28.35f, 28.35f, 28.35f,
				28.35f);

		PdfWriter.getInstance(document, new FileOutputStream(file));

		document.open();

		document.addSubject("Jabatan Perhutanan Negeri Sembilan");
		document.addKeywords("Resit");
		document.addAuthor("JPNS");
		document.addCreator("JPNS");
		document.addTitle("RESIT");

		Font contentFont = new Font(Font.FontFamily.TIMES_ROMAN, 10,
				Font.NORMAL);
		PdfPTable table = new PdfPTable(
				new float[] {0.23f, 0.02f, 0.15f, 0.25f, 0.13f, 0.02f, 0.2f});
		PdfPTable header2 = new PdfPTable(
				new float[] {0.23f, 0.02f, 0.2f, 0.55f});
		PdfPTable header3 = new PdfPTable(new float[] {0.23f, 0.02f, 0.75f});
		PdfPTable content = new PdfPTable(new float[] {0.1f, 0.5f, 0.2f, 0.2f});
		PdfPTable footer = new PdfPTable(new float[] {0.23f, 0.02f, 0.75f});
		PdfPCell title = new PdfPCell(new Phrase("RESIT RASMI", contentFont));
		PdfPCell state = new PdfPCell(
				new Phrase("KERAJAAN NEGERI SEMBILAN", contentFont));
		PdfPCell header2Wrapper = new PdfPCell(header2);
		PdfPCell header3Wrapper = new PdfPCell(header3);
		PdfPCell contentWrapper = new PdfPCell(content);
		PdfPCell footerWrapper = new PdfPCell(footer);
		PdfPCell colon = new PdfPCell(new Phrase(":", contentFont));
		PdfPCell fromHeader = new PdfPCell(
				new Phrase("Diterima Daripada", contentFont));
		PdfPCell from = new PdfPCell(new Phrase(receipt[4], contentFont));
		PdfPCell receiptNoHeader = new PdfPCell(
				new Phrase("No. Resit", contentFont));
		PdfPCell receiptNo = new PdfPCell(new Phrase(receipt[6], contentFont));
		PdfPCell dateHeader = new PdfPCell(new Phrase("Tarikh", contentFont));
		PdfPCell date = new PdfPCell(new Phrase(receipt[7], contentFont));
		PdfPCell timeHeader = new PdfPCell(new Phrase("Masa", contentFont));
		PdfPCell time = new PdfPCell(new Phrase(receipt[8], contentFont));
		PdfPCell icHeader = new PdfPCell(
				new Phrase("No. KP/Daftar Syarikat", contentFont));
		PdfPCell ic = new PdfPCell(new Phrase(receipt[5], contentFont));
		PdfPCell blank = new PdfPCell(new Phrase(" ", contentFont));
		PdfPCell blank2 = new PdfPCell(new Phrase(" ", contentFont));
		PdfPCell blank3 = new PdfPCell(new Phrase("\n\n", contentFont));
		PdfPCell no = new PdfPCell(new Phrase("Bil.", contentFont));
		PdfPCell about = new PdfPCell(
				new Phrase("Perihal Terimaan", contentFont));
		PdfPCell code = new PdfPCell(new Phrase("Kod Terimaan", contentFont));
		PdfPCell amount = new PdfPCell(new Phrase("Amaun (RM)", contentFont));

		table.setWidthPercentage(100f);
		table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		title.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		state.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		no.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		about.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		code.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		amount.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		title.setColspan(7);
		state.setColspan(7);
		header2Wrapper.setColspan(7);
		header3Wrapper.setColspan(7);
		contentWrapper.setColspan(7);
		footerWrapper.setColspan(7);
		blank2.setColspan(2);
		from.setRowspan(2);
		from.setColspan(4);
		ic.setColspan(2);
		blank3.setColspan(3);

		blank.setBorder(0);
		colon.setBorder(0);
		header2.getDefaultCell().setBorder(0);
		header3.getDefaultCell().setBorder(0);
		footer.getDefaultCell().setBorder(0);

		fromHeader.setBorderWidthRight(0);
		fromHeader.setBorderWidthBottom(0);
		blank2.setBorderWidthLeft(0);
		blank2.setBorderWidthRight(0);
		blank2.setBorderWidthBottom(0);
		receiptNoHeader.setBorderWidthRight(0);
		receiptNoHeader.setBorderWidthBottom(0);
		receiptNo.setBorderWidthLeft(0);
		receiptNo.setBorderWidthBottom(0);
		from.setBorderWidthTop(0);
		from.setBorderWidthRight(0);
		from.setBorderWidthBottom(0);
		dateHeader.setBorderWidthTop(0);
		dateHeader.setBorderWidthRight(0);
		dateHeader.setBorderWidthBottom(0);
		date.setBorderWidthTop(0);
		date.setBorderWidthLeft(0);
		date.setBorderWidthBottom(0);
		timeHeader.setBorderWidthTop(0);
		timeHeader.setBorderWidthRight(0);
		timeHeader.setBorderWidthBottom(0);
		time.setBorderWidthTop(0);
		time.setBorderWidthLeft(0);
		time.setBorderWidthBottom(0);
		icHeader.setBorderWidthTop(0);
		icHeader.setBorderWidthRight(0);
		ic.setBorderWidthTop(0);
		ic.setBorderWidthLeft(0);
		ic.setBorderWidthRight(0);
		blank3.setBorderWidthTop(0);

		header2.addCell(new Phrase("JABATAN PENERIMA", contentFont));
		header2.addCell(colon);
		header2.addCell(new Phrase(receipt[0], contentFont));
		header2.addCell(new Phrase(receipt[1], contentFont));
		header2.addCell(new Phrase("PTJ PENERIMA", contentFont));
		header2.addCell(colon);
		header2.addCell(new Phrase(receipt[2], contentFont));
		header2.addCell(new Phrase(receipt[3], contentFont));

		header3.addCell(new Phrase("Bentuk Bayaran", contentFont));
		header3.addCell(colon);
		header3.addCell(new Phrase(receipt[9], contentFont));
		header3.addCell(new Phrase(
				"No. Cek/W. Pos/B. Draft/K. Wang/No. Kad Kredit/MEPS",
				contentFont));
		header3.addCell(colon);
		header3.addCell(new Phrase(receipt[10], contentFont));
		header3.addCell(new Phrase("Bank Pembayar", contentFont));
		header3.addCell(colon);
		header3.addCell(new Phrase(receipt[11], contentFont));
		header3.addCell(new Phrase("Nombor Rujukan", contentFont));
		header3.addCell(colon);
		header3.addCell(blank);

		content.addCell(no);
		content.addCell(about);
		content.addCell(code);
		content.addCell(amount);

		double sum = 0;
		DecimalFormat df = new DecimalFormat("0.00");

		for (int i = 0; i < recordReceipts.size(); i++)
		{
			String[] row = recordReceipts.get(i);
			double value = Double.parseDouble(row[2].replaceAll(",", ""));
			sum += value;

			PdfPCell contentNo = new PdfPCell(
					new Phrase(String.valueOf(i + 1), contentFont));
			PdfPCell contentAbout = new PdfPCell(
					new Phrase(row[0], contentFont));
			PdfPCell contentCode = new PdfPCell(
					new Phrase(row[1], contentFont));
			PdfPCell contentAmount = new PdfPCell(
					new Phrase(df.format(value), contentFont));

			contentNo.setBorderWidthTop(0);
			contentNo.setBorderWidthBottom(0);
			contentAbout.setBorderWidthTop(0);
			contentAbout.setBorderWidthBottom(0);
			contentCode.setBorderWidthTop(0);
			contentCode.setBorderWidthBottom(0);
			contentAmount.setBorderWidthTop(0);
			contentAmount.setBorderWidthBottom(0);

			contentNo.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			contentCode.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			contentAmount.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

			content.addCell(contentNo);
			content.addCell(contentAbout);
			content.addCell(contentCode);
			content.addCell(contentAmount);
		}

		PdfPCell sumControl = new PdfPCell(
				new Phrase("Jumlah (RM)", contentFont));
		PdfPCell total = new PdfPCell(new Phrase(df.format(sum), contentFont));

		sumControl.setColspan(3);

		sumControl.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		total.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

		content.addCell(sumControl);
		content.addCell(total);

		PdfPCell note = new PdfPCell(new Phrase(
				"Cetakan adalah secara berkomputer dan tidak memerlukan tandatangan",
				contentFont));

		note.setBorder(0);
		note.setColspan(3);

		footer.addCell(new Phrase("Ringgit", contentFont));
		footer.addCell(colon);
		footer.addCell(
				new Phrase(MalayNumberConverter.toMalay(sum), contentFont));
		footer.addCell(new Phrase("Operator", contentFont));
		footer.addCell(colon);
		footer.addCell(new Phrase(receipt[12], contentFont));
		footer.addCell(note);

		table.addCell(title);
		table.addCell(state);
		table.addCell(header2Wrapper);
		table.addCell(fromHeader);
		table.addCell(colon);
		table.addCell(blank2);
		table.addCell(receiptNoHeader);
		table.addCell(colon);
		table.addCell(receiptNo);
		table.addCell(from);
		table.addCell(dateHeader);
		table.addCell(colon);
		table.addCell(date);
		table.addCell(timeHeader);
		table.addCell(colon);
		table.addCell(time);
		table.addCell(icHeader);
		table.addCell(colon);
		table.addCell(ic);
		table.addCell(blank3);
		table.addCell(header3Wrapper);
		table.addCell(contentWrapper);
		table.addCell(footerWrapper);

		document.add(table);

		document.close();
	}

	public static void main(String[] args)
	{
		GregorianCalendar gc = new GregorianCalendar(2018, 0, 1);
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM", new Locale("ms"));
		ArrayList<String> months = new ArrayList<>();

		for (int i = 0; i < 12; i++)
		{
			months.add(sdf.format(gc.getTime()));
			gc.add(GregorianCalendar.MONTH, 1);
		}
	}
}