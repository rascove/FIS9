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
public class PenyataAkaunHasilMengikutJabatanPadaBulanDanTahunGenerator
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
		document.addKeywords("Penyata Akaun Hasil Mengikut Jabatan/PTJ");
		document.addAuthor("JPNS");
		document.addCreator("JPNS");
		document.addTitle("PENYATA AKAUN HASIL MENGIKUT JABATAN/PTJ");

		DecimalFormat df = new DecimalFormat("#,###,##0.00");
		PdfPCell code = new PdfPCell(new Phrase("KOD HASIL", titleFont));
		PdfPCell desc = new PdfPCell(new Phrase("PERIHAL", titleFont));
		PdfPCell balance = new PdfPCell(new Phrase("BELANJAWAN", titleFont));
		PdfPCell initial = new PdfPCell(new Phrase("ASAL", titleFont));
		PdfPCell verified = new PdfPCell(new Phrase("DISEMAK", titleFont));
		PdfPCell current = new PdfPCell(
				new Phrase("BULAN SEMASA\nDEBIT/KREDIT", titleFont));
		PdfPCell update = new PdfPCell(
				new Phrase("KEMASKINI\nDEBIT/KREDIT", titleFont));
		PdfPCell nett = new PdfPCell(new Phrase("BERSIH", titleFont));
		PdfPCell variant = new PdfPCell(new Phrase("VARIAN RM", titleFont));
		PdfPCell percentage = new PdfPCell(new Phrase("%", titleFont));

		code.setBorder(0);
		desc.setBorder(0);
		balance.setBorder(0);
		initial.setBorder(0);
		verified.setBorder(0);
		current.setBorder(0);
		update.setBorder(0);
		nett.setBorder(0);
		variant.setBorder(0);
		percentage.setBorder(0);

		code.setRowspan(2);
		desc.setRowspan(2);
		balance.setColspan(2);
		current.setRowspan(2);
		update.setRowspan(2);
		nett.setRowspan(2);
		variant.setRowspan(2);
		percentage.setRowspan(2);

		code.setBorderWidthTop(0.5f);
		desc.setBorderWidthTop(0.5f);
		balance.setBorderWidthTop(0.5f);
		current.setBorderWidthTop(0.5f);
		update.setBorderWidthTop(0.5f);
		nett.setBorderWidthTop(0.5f);
		variant.setBorderWidthTop(0.5f);
		percentage.setBorderWidthTop(0.5f);

		code.setBorderWidthBottom(0.5f);
		desc.setBorderWidthBottom(0.5f);
		initial.setBorderWidthBottom(0.5f);
		verified.setBorderWidthBottom(0.5f);
		current.setBorderWidthBottom(0.5f);
		update.setBorderWidthBottom(0.5f);
		nett.setBorderWidthBottom(0.5f);
		variant.setBorderWidthBottom(0.5f);
		percentage.setBorderWidthBottom(0.5f);

		code.setBorderWidthLeft(0.5f);
		percentage.setBorderWidthRight(0.5f);

		code.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		desc.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		balance.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		initial.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		verified.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		current.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		update.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		nett.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		variant.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		percentage.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		PdfPTable aboutInner = new PdfPTable(
				new float[] {0.2f, 0.05f, 0.1f, 0.65f});
		PdfPTable content = new PdfPTable(new float[] {0.07f, 0.2f, 0.11f,
				0.11f, 0.11f, 0.11f, 0.11f, 0.11f, 0.07f});

		content.setHeaderRows(4);

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

		aboutOuter.setColspan(9);

		content.addCell(aboutOuter);
		content.addCell(new Phrase(" ", contentFont));
		content.completeRow();
		content.addCell(code);
		content.addCell(desc);
		content.addCell(balance);
		content.addCell(current);
		content.addCell(update);
		content.addCell(nett);
		content.addCell(variant);
		content.addCell(percentage);
		content.addCell(initial);
		content.addCell(verified);

		double[] sums = new double[9];

		for (String[] row : contents)
		{
			double i = df.parse(row[2]).doubleValue(),
					v = df.parse(row[3]).doubleValue(),
					cd = df.parse(row[4]).doubleValue(),
					ud = df.parse(row[5]).doubleValue(),
					cc = df.parse(row[6]).doubleValue(),
					uc = df.parse(row[7]).doubleValue(),
					n = df.parse(row[8]).doubleValue(),
					d = df.parse(row[9]).doubleValue(),
					p = df.parse(row[10]).doubleValue();
			sums[0] += i;
			sums[1] += v;
			sums[2] += cd;
			sums[3] += ud;
			sums[4] += cc;
			sums[5] += uc;
			sums[6] += n;
			sums[7] += d;
			sums[8] += p;
			PdfPCell codeCell = new PdfPCell(new Phrase(row[0], contentFont));
			PdfPCell descCell = new PdfPCell(new Phrase(row[1], contentFont));
			PdfPCell percentageCell = new PdfPCell(
					new Phrase(df.format(p), contentFont));

			descCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
			codeCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			percentageCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

			descCell.setBorder(0);
			codeCell.setBorder(0);
			percentageCell.setBorder(0);

			content.addCell(codeCell);
			content.addCell(descCell);
			content.addCell(new Phrase(df.format(i), contentFont));
			content.addCell(new Phrase(df.format(v), contentFont));
			content.addCell(new Phrase(df.format(cd) + "\n" + df.format(cc),
					contentFont));
			content.addCell(new Phrase(df.format(ud) + "\n" + df.format(uc),
					contentFont));
			content.addCell(new Phrase(df.format(n), contentFont));
			content.addCell(new Phrase(df.format(d), contentFont));
			content.addCell(percentageCell);
		}

		PdfPCell total = new PdfPCell(
				new Phrase("JUMLAH:    \u00A0", titleFont));
		PdfPCell[] values = new PdfPCell[] {
				new PdfPCell(new Phrase(df.format(sums[0]), contentFont)),
				new PdfPCell(new Phrase(df.format(sums[1]), contentFont)),
				new PdfPCell(new Phrase(
						df.format(sums[2]) + "\n" + df.format(sums[4]),
						contentFont)),
				new PdfPCell(new Phrase(
						df.format(sums[3]) + "\n" + df.format(sums[5]),
						contentFont)),
				new PdfPCell(new Phrase(df.format(sums[6]), contentFont)),
				new PdfPCell(new Phrase(df.format(sums[7]), contentFont)),
				new PdfPCell(new Phrase(df.format(sums[8]), contentFont))};

		total.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		total.setColspan(2);
		total.setBorder(0);

		content.addCell(total);

		for (int i = 0; i < 7; i++)
		{
			PdfPCell value = values[i];

			value.setHorizontalAlignment(
					i == 6 ? PdfPCell.ALIGN_CENTER : PdfPCell.ALIGN_RIGHT);
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
		PdfPCell genDate = new PdfPCell(new Phrase(
				"TARIKH: " + new SimpleDateFormat("dd/MM/yyyy").format(gen),
				titleFont));
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
				"PENYATA AKAUN HASIL MENGIKUT JABATAN/PUSAT TANGGUNGJAWAB\nPADA BULAN "
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
					"LAPORAN: L303\nMUKASURAT: " + i + "/" + count, titleFont));

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
					.getHeaderPenyataAkaunHasilMengikutJabatanPadaBulanDanTahun();
			ArrayList<String[]> contents = rFacade
					.getContentPenyataAkaunHasilMengikutJabatanPadaBulanDanTahun(
							date);

			PenyataAkaunHasilMengikutJabatanPadaBulanDanTahunGenerator.generate(
					new File("revenuestatementbydept.pdf"), date, header,
					contents);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}*/
}