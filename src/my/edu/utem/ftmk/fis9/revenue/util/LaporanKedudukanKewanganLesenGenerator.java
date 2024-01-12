package my.edu.utem.ftmk.fis9.revenue.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;

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
public class LaporanKedudukanKewanganLesenGenerator
{
	private static final Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN,
			12, Font.BOLD);
	private static final Font underlinedFont = new Font(
			Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD | Font.UNDERLINE);
	private static final Font contentFont = new Font(
			Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

	public static void generate(File file, String[] data) throws Exception
	{
		Document document = new Document(PageSize.A4, 56.69f, 56.69f, 56.69f,
				56.69f);

		PdfWriter.getInstance(document, new FileOutputStream(file));

		document.open();

		document.addSubject("Jabatan Perhutanan Negeri Sembilan");
		document.addKeywords("Laporan Kedudukan Kewangan Lesen");
		document.addAuthor("JPNS");
		document.addCreator("JPNS");
		document.addTitle("LAPORAN KEDUDUKAN KEWANGAN LESEN");

		DecimalFormat df = new DecimalFormat("#,###,##0.00");
		double[] values = new double[7];

		for (int i = 0; i < 7; i++)
		{
			values[i] = df.parse(data[i + 2]).doubleValue();
		}

		Paragraph serial = new Paragraph("JPNS 9", contentFont);
		Paragraph title = new Paragraph(
				"\nPENYATA KEWANGAN\nLAPORAN KEDUDUKAN KEWANGAN LESEN: "
						+ data[0],
				titleFont);
		Paragraph subtitle = new Paragraph("PELESEN: " + data[1],
				underlinedFont);
		PdfPTable content = new PdfPTable(
				new float[] {0.05f, 0.65f, 0.1f, 0.2f});
		PdfPCell a = new PdfPCell(new Phrase("a.", titleFont));
		PdfPCell b = new PdfPCell(new Phrase("\nb.", titleFont));
		PdfPCell[] a_cells = new PdfPCell[] {
				new PdfPCell(new Phrase(df.format(values[0]), contentFont)),
				new PdfPCell(new Phrase(df.format(values[1]), contentFont)),
				new PdfPCell(new Phrase(df.format(values[2]), contentFont)),
				new PdfPCell(new Phrase(df.format(values[3]), contentFont)),
				new PdfPCell(new Phrase(df.format(values[4]), contentFont)),
				new PdfPCell(
						new Phrase("Jumlah Terimaan:    \u00A0", contentFont)),
				new PdfPCell(new Phrase(
						df.format(
								values[0] + values[1] + values[2] + values[3] + values[4]),
						contentFont))};
		PdfPCell[] b_cells = new PdfPCell[] {
				new PdfPCell(new Phrase(df.format(values[5]), contentFont)),
				new PdfPCell(new Phrase(df.format(values[6]), contentFont)),
				new PdfPCell(
						new Phrase("Jumlah Penolakan:    \u00A0", contentFont)),
				new PdfPCell(new Phrase(df.format(values[5] + values[6]),
						contentFont)),
				new PdfPCell(
						new Phrase(
								df.format(values[0] + values[1] + values[2]
										+ values[3] + values[4] - values[5] - values[6]),
								contentFont))};

		serial.setAlignment(Paragraph.ALIGN_RIGHT);
		title.setAlignment(Paragraph.ALIGN_CENTER);
		subtitle.setAlignment(Paragraph.ALIGN_CENTER);

		content.setWidthPercentage(101f);
		content.getDefaultCell().setBorder(0);
		content.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

		a.setBorder(0);
		a.setRowspan(7);

		b.setBorder(0);
		b.setRowspan(7);

		for (PdfPCell a_cell : a_cells)
		{
			a_cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			a_cell.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
			a_cell.setBorder(0);
		}

		a_cells[5].setBorderWidthTop(0.5f);
		a_cells[5].setBorderWidthBottom(0.5f);
		a_cells[5].setUseBorderPadding(true);

		for (PdfPCell b_cell : b_cells)
		{
			b_cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			b_cell.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
			b_cell.setBorder(0);
		}

		b_cells[3].setBorderWidthTop(0.5f);
		b_cells[3].setBorderWidthBottom(0.5f);
		b_cells[3].setUseBorderPadding(true);
		b_cells[4].setBorderWidthTop(0.5f);
		b_cells[4].setBorderWidthBottom(2f);
		b_cells[4].setUseBorderPadding(true);

		content.addCell(a);
		content.addCell(new Phrase("Terimaan:", underlinedFont));
		content.completeRow();
		content.addCell(new Phrase("\nWang Amanah Lesen", contentFont));
		content.addCell(new Phrase("RM", contentFont));
		content.addCell(a_cells[0]);
		content.addCell(new Phrase("\nWang Amanah Jalan", contentFont));
		content.addCell(new Phrase("RM", contentFont));
		content.addCell(a_cells[1]);
		content.addCell(new Phrase("\nWang Amanah Matau", contentFont));
		content.addCell(new Phrase("RM", contentFont));
		content.addCell(a_cells[2]);
		content.addCell(new Phrase("\nWang Amanah Permit Penggunaan", contentFont));
		content.addCell(new Phrase("RM", contentFont));
		content.addCell(a_cells[3]);		
		content.addCell(new Phrase("\nPendahuluan Royalti dan Ses",
				contentFont));
		content.addCell(new Phrase("RM", contentFont));
		content.addCell(a_cells[4]);
		content.addCell(new Phrase(" ", titleFont));
		content.completeRow();
		content.addCell(a_cells[5]);
		content.addCell(new Phrase("RM", contentFont));
		content.addCell(a_cells[6]);
		content.addCell(b);
		content.addCell(new Phrase("\nPenolakan:", underlinedFont));
		content.completeRow();
		content.addCell(new Phrase(
				"\nKutipan Royalti/Ses Pengeluaran Semasa Dari Pas Pemindah",
				contentFont));
		content.addCell(new Phrase("RM", contentFont));
		content.addCell(b_cells[0]);
		content.addCell(new Phrase(
				"\nLaporan Penutup Akhir Royalti/Ses (Jika Ada)", contentFont));
		content.addCell(new Phrase("RM", contentFont));
		content.addCell(b_cells[1]);
		content.addCell(new Phrase(" ", titleFont));
		content.completeRow();
		content.addCell(b_cells[2]);
		content.addCell(new Phrase("RM", contentFont));
		content.addCell(b_cells[3]);
		content.addCell(new Phrase(" ", titleFont));
		content.completeRow();
		content.addCell(new Phrase(
				"Baki Wang Amanah Lesen dan Royalti Yang Ada (a) - (b)",
				contentFont));
		content.addCell(new Phrase("RM", contentFont));
		content.addCell(b_cells[4]);

		document.add(serial);
		document.add(title);
		document.add(subtitle);
		document.add(new Paragraph(
				"\n\nAkaun bagi lesen tersebut di atas mengikut Buku Lejer Hasil adalah seperti berikut:\n ",
				contentFont));
		document.add(content);

		document.close();
	}

	public static void main(String[] args) throws Exception
	{
		String[] data = new String[] {"NT/01/(P)/01/2013",
				"PESINAR MURNI (M) SDN BHD", "50000", "3000", "0", "135000",
				"134375.77", "0"};

		LaporanKedudukanKewanganLesenGenerator
				.generate(new File("licensestatus.pdf"), data);
	}
}