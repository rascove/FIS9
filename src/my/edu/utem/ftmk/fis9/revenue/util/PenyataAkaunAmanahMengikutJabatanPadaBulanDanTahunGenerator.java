package my.edu.utem.ftmk.fis9.revenue.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
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
public class PenyataAkaunAmanahMengikutJabatanPadaBulanDanTahunGenerator
{
	private static final Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN,
			10, Font.BOLD);
	private static final Font contentFont = new Font(
			Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);

	public static void generate(File file, Date date, String[] header,
			ArrayList<String[]> contents) throws Exception
	{
		Document document = new Document(PageSize.A4.rotate(), 28.35f, 28.35f,
				136.06f, 28.35f);
		File temp = new File(
				(file.getParent() == null ? "" : "/" + file.getParent())
						+ "temp.pdf");

		PdfWriter.getInstance(document, new FileOutputStream(temp));

		document.open();

		document.addSubject("Jabatan Perhutanan Negeri Sembilan");
		document.addKeywords("Penyata Akaun Amanah Mengikut Jabatan/PTJ");
		document.addAuthor("JPNS");
		document.addCreator("JPNS");
		document.addTitle("PENYATA AKAUN AMANAH MENGIKUT JABATAN/PTJ");

		DecimalFormat df = new DecimalFormat("#,###,##0.00");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		GregorianCalendar start = new GregorianCalendar();
		GregorianCalendar end = new GregorianCalendar();

		start.setTime(date);
		end.setTime(date);

		start.set(GregorianCalendar.DATE, 1);
		end.set(GregorianCalendar.DATE, 1);
		end.add(GregorianCalendar.MONTH, 1);
		end.add(GregorianCalendar.DATE, -1);

		PdfPCell code = new PdfPCell(new Phrase("KOD AMANAH", titleFont));
		PdfPCell desc = new PdfPCell(new Phrase("PERIHAL", titleFont));
		PdfPCell initial = new PdfPCell(
				new Phrase("BAKI " + sdf.format(start.getTime())
						+ "\nDEBIT (-)/KREDIT (+)", titleFont));
		PdfPCell current = new PdfPCell(
				new Phrase("BULAN SEMASA\nDEBIT/KREDIT", titleFont));
		PdfPCell update = new PdfPCell(
				new Phrase("KEMASKINI\nDEBIT/KREDIT", titleFont));
		PdfPCell nett = new PdfPCell(new Phrase(
				"BERSIH KEMASKINI\nDEBIT (-)/KREDIT (+)", titleFont));
		PdfPCell balance = new PdfPCell(
				new Phrase("BAKI " + sdf.format(end.getTime()), titleFont));

		code.setBorder(0);
		desc.setBorder(0);
		initial.setBorder(0);
		current.setBorder(0);
		update.setBorder(0);
		nett.setBorder(0);
		balance.setBorder(0);

		code.setBorderWidthTop(0.5f);
		desc.setBorderWidthTop(0.5f);
		initial.setBorderWidthTop(0.5f);
		current.setBorderWidthTop(0.5f);
		update.setBorderWidthTop(0.5f);
		nett.setBorderWidthTop(0.5f);
		balance.setBorderWidthTop(0.5f);

		code.setBorderWidthBottom(0.5f);
		desc.setBorderWidthBottom(0.5f);
		initial.setBorderWidthBottom(0.5f);
		current.setBorderWidthBottom(0.5f);
		update.setBorderWidthBottom(0.5f);
		nett.setBorderWidthBottom(0.5f);
		balance.setBorderWidthBottom(0.5f);

		code.setBorderWidthLeft(0.5f);
		balance.setBorderWidthRight(0.5f);

		code.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		desc.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		initial.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		current.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		update.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		nett.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		balance.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		PdfPTable aboutInner = new PdfPTable(
				new float[] {0.2f, 0.05f, 0.1f, 0.65f});
		PdfPTable content = new PdfPTable(
				new float[] {0.07f, 0.23f, 0.14f, 0.14f, 0.14f, 0.14f, 0.14f});

		content.setHeaderRows(3);

		aboutInner.setWidthPercentage(100f);
		content.setWidthPercentage(100f);

		aboutInner.getDefaultCell().setBorder(0);
		content.getDefaultCell().setBorder(0);

		content.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

		aboutInner.addCell(new Phrase("JAB. DIPERTANGGUNGKAN", titleFont));
		aboutInner.addCell(new Phrase(":", titleFont));
		aboutInner.addCell(new Phrase(header[0], contentFont));
		aboutInner.addCell(new Phrase(header[2], contentFont));
		aboutInner.addCell(new Phrase("PTJ DIPERTANGGUNGKAN", titleFont));
		aboutInner.addCell(new Phrase(":", titleFont));
		aboutInner.addCell(new Phrase(header[1], contentFont));
		aboutInner.addCell(new Phrase(header[3], contentFont));

		PdfPCell aboutOuter = new PdfPCell(aboutInner);

		aboutOuter.setColspan(7);

		content.addCell(aboutOuter);
		content.addCell(new Phrase(" ", contentFont));
		content.completeRow();
		content.addCell(code);
		content.addCell(desc);
		content.addCell(initial);
		content.addCell(current);
		content.addCell(update);
		content.addCell(nett);
		content.addCell(balance);

		double[] sums = new double[7];

		for (String[] row : contents)
		{
			double i = df.parse(row[2]).doubleValue(),
					cd = df.parse(row[3]).doubleValue(),
					cc = df.parse(row[4]).doubleValue(),
					ud = df.parse(row[5]).doubleValue(),
					uc = df.parse(row[6]).doubleValue(),
					n = df.parse(row[7]).doubleValue(),
					b = df.parse(row[8]).doubleValue();
			sums[0] += i;
			sums[1] += cd;
			sums[2] += cc;
			sums[3] += ud;
			sums[4] += uc;
			sums[5] += n;
			sums[6] += b;
			PdfPCell codeCell = new PdfPCell(new Phrase(row[0], contentFont));
			PdfPCell descCell = new PdfPCell(new Phrase(row[1], contentFont));

			descCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
			codeCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

			descCell.setBorder(0);
			codeCell.setBorder(0);

			content.addCell(codeCell);
			content.addCell(descCell);
			content.addCell(new Phrase(df.format(i), contentFont));
			content.addCell(new Phrase(df.format(cd) + "\n" + df.format(cc),
					contentFont));
			content.addCell(new Phrase(df.format(ud) + "\n" + df.format(uc),
					contentFont));
			content.addCell(new Phrase(df.format(n), contentFont));
			content.addCell(new Phrase(df.format(b), contentFont));
		}

		PdfPCell total = new PdfPCell(
				new Phrase("JUMLAH JABATAN/PTJ:    \u00A0", titleFont));
		PdfPCell[] values = new PdfPCell[] {
				new PdfPCell(new Phrase(df.format(sums[0]), contentFont)),
				new PdfPCell(new Phrase(
						df.format(sums[1]) + "\n" + df.format(sums[2]),
						contentFont)),
				new PdfPCell(new Phrase(
						df.format(sums[3]) + "\n" + df.format(sums[4]),
						contentFont)),
				new PdfPCell(new Phrase(df.format(sums[5]), contentFont)),
				new PdfPCell(new Phrase(df.format(sums[6]), contentFont))};

		total.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		total.setColspan(2);
		total.setBorder(0);

		content.addCell(total);

		for (PdfPCell value : values)
		{
			value.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			value.setBorderWidthLeft(0);
			value.setBorderWidthRight(0);

			content.addCell(value);
		}

		document.add(content);
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
		PdfPCell title1 = new PdfPCell(new Phrase(
				"KERAJAAN NEGERI NEGERI SEMBILAN\nPEJABAT PERBENDAHARAAN NEGERI NEGERI SEMBILAN\nSISTEM PERAKAUNAN LEJAR AM",
				titleFont));
		PdfPCell subtitle = new PdfPCell(new Phrase("LAPORAN PENGURUSAN",
				new Font(Font.FontFamily.TIMES_ROMAN, 10,
						Font.BOLD | Font.UNDERLINE)));
		PdfPCell title2 = new PdfPCell(new Phrase(
				"PENYATA AKAUN AMANAH MENGIKUT JABATAN/PUSAT TANGGUNGJAWAB\nPADA BULAN "
						+ new SimpleDateFormat("MMMM yyyy", new Locale("ms"))
								.format(date).toUpperCase(),
				titleFont));

		genDate.setBorder(0);
		genTime.setBorder(0);
		title1.setBorder(0);
		subtitle.setBorder(0);
		title2.setBorder(0);

		title1.setColspan(3);
		subtitle.setColspan(3);
		title2.setColspan(3);

		title1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		title2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		for (int i = 1; i <= count; i++)
		{
			PdfPTable stamp = new PdfPTable(new float[] {0.2f, 0.2f, 0.6f});
			PdfPCell pageNo = new PdfPCell(new Phrase(
					"LAPORAN: L405\nMUKASURAT: " + i + "/" + count, titleFont));

			stamp.setTotalWidth(document.right() - document.left());

			pageNo.setBorder(0);
			pageNo.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

			stamp.addCell(genDate);
			stamp.addCell(genTime);
			stamp.addCell(pageNo);
			stamp.addCell(title1);
			stamp.addCell(subtitle);
			stamp.addCell(title2);

			stamp.writeSelectedRows(0, -1, 28.35f, 566.65f,
					stamper.getOverContent(i));
		}

		stamper.close();
		reader.close();
		temp.delete();
	}

/*	public static void main(String[] args)
	{
		try (RevenueFacade rFacade = new RevenueFacade();)
		{
			Date date = new Date();

			String[] header = rFacade
					.getHeaderPenyataAkaunAmanahMengikutJabatanPadaBulanDanTahun();
			ArrayList<String[]> contents = rFacade
					.getContentPenyataAkaunAmanahMengikutJabatanPadaBulanDanTahun(
							date);

			PenyataAkaunAmanahMengikutJabatanPadaBulanDanTahunGenerator
					.generate(new File("truststatementbydept.pdf"), date,
							header, contents);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}*/
}