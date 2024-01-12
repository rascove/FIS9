package my.edu.utem.ftmk.fis9.revenue.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.revenue.controller.manager.RevenueFacade;

/**
 * @author Nor Azman Bin Mat Ariff
 */
public class LaporanKutipanHarianMengikutOperatorSepertiPadaTarikhGenerator
{
	private static final LinkedHashMap<String, String> map = new LinkedHashMap<>();
	private static final Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN,
			10, Font.BOLD);
	private static final Font contentFont = new Font(
			Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);

	static
	{
		map.put("1", "Tunai (TU)");
		map.put("2", "Cek (CK)");
		map.put("3", "Kiriman Wang (KW)");
		map.put("4", "Wang Pos (WP)");
		map.put("5", "Draf Bank (DB)");
		map.put("6", "Kad Kredit (KK)");
		map.put("7", "Kad Debit (KD)");
		map.put("8", "MEPS Tunai (MT)");
		map.put("9", "Pindahan Telegraf / Makluman Kredit");
	}

	public static void generate(File file, ArrayList<String[]> data, Date date,
			Staff staff) throws Exception
	{
		Document document = new Document(PageSize.A4.rotate(), 28.35f, 28.35f,
				90.71f, 28.35f);
		File temp = new File(
				(file.getParent() == null ? "" : "/" + file.getParent())
						+ "temp.pdf");

		PdfWriter.getInstance(document, new FileOutputStream(temp));

		document.open();

		document.addSubject("Jabatan Perhutanan Negeri Sembilan");
		document.addKeywords("Laporan Kutipan Harian Mengikut Operator");
		document.addAuthor("JPNS");
		document.addCreator("JPNS");
		document.addTitle("LAPORAN KUTIPAN HARIAN MENGIKUT OPERATOR");

		DecimalFormat df = new DecimalFormat("#,###,##0.00");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		PdfPCell no = new PdfPCell(new Phrase("BIL", titleFont));
		PdfPCell receiptNo = new PdfPCell(
				new Phrase("NOMBOR RESIT", titleFont));
		PdfPCell name = new PdfPCell(new Phrase("NAMA PEMBAYAR", titleFont));
		PdfPCell method = new PdfPCell(new Phrase("BENTUK BAYARAN", titleFont));
		PdfPCell time = new PdfPCell(new Phrase("MASA URUSNIAGA", titleFont));
		PdfPCell trust = new PdfPCell(new Phrase("KOD AMANAH", titleFont));
		PdfPCell code = new PdfPCell(new Phrase("KOD OSOL", titleFont));
		PdfPCell money = new PdfPCell(new Phrase("AMAUN (RM)", titleFont));

		no.setBorder(0);
		receiptNo.setBorder(0);
		name.setBorder(0);
		method.setBorder(0);
		time.setBorder(0);
		trust.setBorder(0);
		code.setBorder(0);
		money.setBorder(0);

		no.setBorderWidthTop(0.5f);
		receiptNo.setBorderWidthTop(0.5f);
		name.setBorderWidthTop(0.5f);
		method.setBorderWidthTop(0.5f);
		time.setBorderWidthTop(0.5f);
		trust.setBorderWidthTop(0.5f);
		code.setBorderWidthTop(0.5f);
		money.setBorderWidthTop(0.5f);

		no.setBorderWidthBottom(0.5f);
		receiptNo.setBorderWidthBottom(0.5f);
		name.setBorderWidthBottom(0.5f);
		method.setBorderWidthBottom(0.5f);
		time.setBorderWidthBottom(0.5f);
		trust.setBorderWidthBottom(0.5f);
		code.setBorderWidthBottom(0.5f);
		money.setBorderWidthBottom(0.5f);

		no.setBorderWidthLeft(0.5f);
		money.setBorderWidthRight(0.5f);

		no.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		receiptNo.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		name.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		method.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		time.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		trust.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		code.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		money.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		PdfPTable aboutInner = new PdfPTable(
				new float[] {0.2f, 0.05f, 0.1f, 0.65f});
		PdfPTable content = new PdfPTable(
				new float[] {0.05f, 0.1f, 0.3f, 0.1f, 0.1f, 0.1f, 0.1f, 0.15f});

		content.setHeaderRows(3);

		aboutInner.setWidthPercentage(100f);
		content.setWidthPercentage(100f);

		aboutInner.getDefaultCell().setBorder(0);
		content.getDefaultCell().setBorder(0);

		content.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		aboutInner.addCell(new Phrase("MENERIMA", titleFont));
		aboutInner.addCell(new Phrase("", titleFont));
		aboutInner.addCell(new Phrase("KOD", titleFont));
		aboutInner.addCell(new Phrase("PERIHAL", titleFont));
		aboutInner.addCell(new Phrase("JABATAN", titleFont));
		aboutInner.addCell(new Phrase(":", titleFont));
		aboutInner.addCell(new Phrase("0080", contentFont));
		aboutInner
				.addCell(new Phrase("JABATAN PERHUTANAN NEGERI", contentFont));
		aboutInner.addCell(new Phrase("PTJ/PK", titleFont));
		aboutInner.addCell(new Phrase(":", titleFont));
		aboutInner.addCell(new Phrase("80000100", contentFont));
		aboutInner.addCell(new Phrase("IBU PEJABAT JABATAN PERHUTANAN NEGERI",
				contentFont));
		aboutInner.addCell(new Phrase("OPERATOR", titleFont));
		aboutInner.addCell(new Phrase(":", titleFont));
		aboutInner.addCell(new Phrase(staff.getStaffID(), contentFont));
		aboutInner.addCell(new Phrase(staff.getName(), contentFont));

		PdfPCell aboutOuter = new PdfPCell(aboutInner);

		aboutOuter.setColspan(8);

		content.addCell(aboutOuter);
		content.addCell(new Phrase(" ", contentFont));
		content.completeRow();
		content.addCell(no);
		content.addCell(receiptNo);
		content.addCell(name);
		content.addCell(method);
		content.addCell(time);
		content.addCell(trust);
		content.addCell(code);
		content.addCell(money);

		int index = 0, receivedCount = 0, cancelledCount = 0;
		double sum = 0, receivedSum = 0, cancelledSum = 0;
		LinkedHashMap<String, double[]> counts = new LinkedHashMap<>();

		for (String key : map.keySet())
			counts.put(key, new double[4]);

		for (String[] row : data)
		{
			double amount = df.parse(row[6]).doubleValue(),
					count[] = counts.get(row[2]);
			sum += amount;
			PdfPCell desc = new PdfPCell(new Phrase(row[1], contentFont));
			PdfPCell value = new PdfPCell(
					new Phrase(df.format(amount), contentFont));

			desc.setBorder(0);
			value.setBorder(0);

			value.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

			if (row[6].equals("C"))
			{
				count[2]++;
				cancelledCount++;
				count[3] += amount;
				cancelledSum += amount;
			}
			else
			{
				count[0]++;
				receivedCount++;
				count[1] += amount;
				receivedSum += amount;
			}

			content.addCell(new Phrase(String.valueOf(++index), contentFont));
			content.addCell(new Phrase(row[0], contentFont));
			content.addCell(desc);
			content.addCell(new Phrase(row[2], contentFont));
			content.addCell(new Phrase(row[3], contentFont));
			content.addCell(new Phrase(row[4], contentFont));
			content.addCell(new Phrase(row[5], contentFont));
			content.addCell(value);
		}

		PdfPCell total = new PdfPCell(
				new Phrase("JUMLAH    \u00A0", titleFont));
		PdfPCell value = new PdfPCell(new Phrase(df.format(sum), contentFont));

		total.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		total.setColspan(7);
		total.setBorder(0);

		value.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		value.setBorderWidthLeft(0);
		value.setBorderWidthRight(0);

		content.addCell(total);
		content.addCell(value);

		PdfPTable summary = new PdfPTable(new float[] {0.4f, 0.3f, 0.3f});
		PdfPCell headerDesc = new PdfPCell(
				new Phrase("Ringkasan Kutipan", titleFont));
		PdfPCell headerCount = new PdfPCell(
				new Phrase("Bil. Rekod", titleFont));
		PdfPCell headerAmount = new PdfPCell(
				new Phrase("Amaun (RM)", titleFont));

		headerDesc.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		headerCount.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		headerAmount.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		summary.setWidthPercentage(80f);

		summary.addCell(headerDesc);
		summary.addCell(headerCount);
		summary.addCell(headerAmount);

		index = 0;

		for (String key : map.keySet())
		{
			double[] count = counts.get(key);
			PdfPCell totalCount = new PdfPCell(new Phrase(
					String.valueOf((int) (count[0] + count[2])), titleFont));
			PdfPCell totalSum = new PdfPCell(
					new Phrase(df.format(count[1] + count[3]), titleFont));

			totalCount.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			totalSum.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

			summary.addCell(new Phrase(++index + ". Kutipan " + map.get(key),
					contentFont));
			summary.addCell(totalCount);
			summary.addCell(totalSum);
		}

		PdfPCell totalDesc = new PdfPCell(
				new Phrase("Jumlah Semua", titleFont));
		PdfPCell totalCount = new PdfPCell(new Phrase(
				String.valueOf(receivedCount + cancelledCount), titleFont));
		PdfPCell totalSum = new PdfPCell(
				new Phrase(df.format(receivedSum + cancelledSum), titleFont));
		PdfPCell totalReceivedDesc = new PdfPCell(
				new Phrase("Bilangan Urusniaga Diterima", titleFont));
		PdfPCell totalReceivedCount = new PdfPCell(
				new Phrase(String.valueOf(receivedCount), titleFont));
		PdfPCell totalReceivedSum = new PdfPCell(
				new Phrase(df.format(receivedSum), titleFont));
		PdfPCell totalCancelledDesc = new PdfPCell(
				new Phrase("Bilangan Urusniaga Batal", titleFont));
		PdfPCell totalCancelledCount = new PdfPCell(
				new Phrase(String.valueOf(cancelledCount), titleFont));
		PdfPCell totalCancelledSum = new PdfPCell(
				new Phrase(df.format(cancelledSum), titleFont));

		totalDesc.setBorderWidthBottom(0);
		totalCount.setBorderWidthBottom(0);
		totalSum.setBorderWidthBottom(0);
		totalCancelledDesc.setBorderWidthBottom(0);
		totalCancelledCount.setBorderWidthBottom(0);
		totalCancelledSum.setBorderWidthBottom(0);
		totalCancelledDesc.setBorderWidthTop(0);
		totalCancelledCount.setBorderWidthTop(0);
		totalCancelledSum.setBorderWidthTop(0);
		totalReceivedDesc.setBorderWidthTop(0);
		totalReceivedCount.setBorderWidthTop(0);
		totalReceivedSum.setBorderWidthTop(0);

		totalCount.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		totalReceivedCount.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		totalCancelledCount.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		totalSum.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		totalReceivedSum.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		totalCancelledSum.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

		summary.addCell(totalDesc);
		summary.addCell(totalCount);
		summary.addCell(totalSum);

		summary.addCell(totalCancelledDesc);
		summary.addCell(totalCancelledCount);
		summary.addCell(totalCancelledSum);

		summary.addCell(totalReceivedDesc);
		summary.addCell(totalReceivedCount);
		summary.addCell(totalReceivedSum);

		document.add(content);
		document.newPage();
		document.add(summary);
		document.add(new Paragraph(
				"\n\nDisediakan Oleh:\n\nTandatangan:\n\n\nDisahkan Oleh:\n\nJawatan:\n\nTarikh:",
				titleFont));

		document.close();

		Date gen = new Date();
		PdfReader reader = new PdfReader(new FileInputStream(temp));
		int count = reader.getNumberOfPages();
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(file));
		PdfPCell genDate = new PdfPCell(
				new Phrase("TARIKH: " + sdf.format(gen), titleFont));
		PdfPCell genTime = new PdfPCell(new Phrase(
				"MASA: " + new SimpleDateFormat("hh:mm aa").format(gen),
				titleFont));
		PdfPCell title = new PdfPCell(new Phrase(
				"KERAJAAN NEGERI SEMBILAN\nSISTEM PERAKAUNAN PTJ KEWANGAN\nLAPORAN KUTIPAN HARIAN MENGIKUT OPERATOR SEPERTI PADA "
						+ sdf.format(date),
				titleFont));

		genDate.setBorder(0);
		genTime.setBorder(0);
		title.setBorder(0);

		title.setColspan(3);
		title.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		for (int i = 1; i <= count; i++)
		{
			PdfPTable stamp = new PdfPTable(new float[] {0.2f, 0.2f, 0.6f});
			PdfPCell pageNo = new PdfPCell(
					new Phrase("MUKASURAT: " + i + "/" + count, titleFont));

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
			Staff staff = new Staff();
			Date date = new GregorianCalendar(2018, 6, 12).getTime();

			staff.setStaffID("fis9@utem.edu.my");
			staff.setName("SYSTEM ADMINISTRATOR");

			ArrayList<String[]> data = rFacade
					.getLaporanKutipanHarianMengikutOperatorSepertiPadaTarikh(
							date, staff);

			LaporanKutipanHarianMengikutOperatorSepertiPadaTarikhGenerator
					.generate(new File("incomebyoperator.pdf"), data, date,
							staff);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}