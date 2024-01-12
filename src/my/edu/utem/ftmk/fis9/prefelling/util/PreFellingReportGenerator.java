package my.edu.utem.ftmk.fis9.prefelling.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import my.edu.utem.ftmk.fis9.maintenance.model.Fertility;
import my.edu.utem.ftmk.fis9.maintenance.model.Resam;
import my.edu.utem.ftmk.fis9.maintenance.model.VineSpreadth;
import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingSurvey;
import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingCuttingOption;
import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingReport;
import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingReportElement;

/**
 * @author Satrya Fajri Pratama
 */
public class PreFellingReportGenerator
{
	private static Font size9 = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);
	private static Font bold9 = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);

	public static void generate(File file, PreFellingSurvey preFellingSurvey, ArrayList<VineSpreadth> vineSpreadths, ArrayList<Resam> resams, ArrayList<Fertility> fertilities) throws Exception
	{
		Document document = new Document(PageSize.A4.rotate(), 14.175f, 14.175f, 14.175f, 14.175f);
		
		PdfWriter.getInstance(document, new FileOutputStream(file));

		document.open();

		document.addTitle("LAPORAN BANCIAN INVENTORI HUTAN SEBELUM TEBANGAN (PRE-F)");
		document.addSubject("Jabatan Perhutanan Negeri Sembilan");
		document.addKeywords("Pre-F");
		document.addAuthor("JPNS");
		document.addCreator("JPNS");

		DecimalFormat df = new DecimalFormat("0.00");
		DecimalFormat dfp = new DecimalFormat("0.00%");
		PreFellingReport preFellingReport = preFellingSurvey.getPreFellingReport();
		String[] headers = preFellingReport.getHeaders();
		ArrayList<PreFellingReportElement> nonCumulativeEstimations = preFellingReport.getNonCumulativeEstimations();
		ArrayList<PreFellingReportElement> cumulativeEstimations = preFellingReport.getCumulativeEstimations();
		ArrayList<PreFellingCuttingOption> preFellingCuttingOptions = preFellingReport.getPreFellingCuttingOptions();
		ArrayList<PreFellingReportElement> vineSpreadthCounts = preFellingReport.getVineSpreadthCounts();
		ArrayList<PreFellingReportElement> logQuantityCounts = preFellingReport.getLogQuantityCounts();
		ArrayList<PreFellingReportElement> logQualityCounts = preFellingReport.getLogQualityCounts();
		ArrayList<PreFellingReportElement> bertamPalmCounts = preFellingReport.getBertamPalmCounts();
		ArrayList<PreFellingReportElement> resamCounts = preFellingReport.getResamCounts();
		ArrayList<PreFellingReportElement> rattanBambooCounts = preFellingReport.getRattanBambooCounts();
		ArrayList<PreFellingReportElement> fertilityCounts = preFellingReport.getFertilityCounts();
		ArrayList<PreFellingReportElement> seedlingCounts = preFellingReport.getSeedlingCounts();
		
		PdfPTable table1 = new PdfPTable(15);
		PdfPTable table2 = new PdfPTable(14);
		PdfPTable table3 = new PdfPTable(13);
		PdfPTable table4 = new PdfPTable(13);
		PdfPTable table5 = new PdfPTable(8);
		PdfPTable table6 = new PdfPTable(9);
		PdfPTable table7 = new PdfPTable(9);
		PdfPTable table8 = new PdfPTable(3);
		PdfPTable table9 = new PdfPTable(7);
		PdfPTable table10 = new PdfPTable(13);
		PdfPTable table11 = new PdfPTable(7);
		PdfPTable table12 = new PdfPTable(3);

		table1.setWidthPercentage(100);
		table2.setWidthPercentage(100);
		table3.setWidthPercentage(100);
		table4.setWidthPercentage(100);
		table5.setWidthPercentage(100);
		table6.setWidthPercentage(100);
		table7.setWidthPercentage(100);
		table8.setWidthPercentage(100);
		table9.setWidthPercentage(100);
		table10.setWidthPercentage(100);
		table11.setWidthPercentage(100);
		table12.setWidthPercentage(100);
		table1.setWidths(new float[] {11f, 11f, 6f, 6f, 6f, 6f, 6f, 6f, 6f, 6f, 6f, 6f, 6f, 6f, 6f});
		table2.setWidths(new float[] {14f, 14f, 6f, 6f, 6f, 6f, 6f, 6f, 6f, 6f, 6f, 6f, 6f, 6f});
		table5.setWidths(new float[] {17f, 17f, 11f, 11f, 11f, 11f, 11f, 11f});
		table6.setWidths(new float[] {14f, 10f, 16f, 10f, 10f, 10f, 10f, 10f, 10f});
		table7.setWidths(new float[] {12f, 12f, 16f, 10f, 10f, 10f, 10f, 10f, 10f});
		table9.setWidths(new float[] {22f, 13f, 13f, 13f, 13f, 13f, 13f});
		table10.setWidths(new float[] {10f, 7.5f, 7.5f, 7.5f, 7.5f, 7.5f, 7.5f, 7.5f, 7.5f, 7.5f, 7.5f, 7.5f, 7.5f});
		table11.setWidths(new float[] {15f, 20f, 13f, 13f, 13f, 13f, 13f});

		PdfPCell[] header1 = new PdfPCell[]
		{
				new PdfPCell(new Phrase("Kriteria", bold9)),
				new PdfPCell(new Phrase("15", bold9)),
				new PdfPCell(new Phrase("30", bold9)),
				new PdfPCell(new Phrase("45", bold9)),
				new PdfPCell(new Phrase("50", bold9)),
				new PdfPCell(new Phrase("55", bold9)),
				new PdfPCell(new Phrase("60", bold9)),
				new PdfPCell(new Phrase("65", bold9)),
				new PdfPCell(new Phrase("70", bold9)),
				new PdfPCell(new Phrase("75", bold9)),
				new PdfPCell(new Phrase("80", bold9)),
				new PdfPCell(new Phrase("85", bold9)),
				new PdfPCell(new Phrase("90+", bold9)),
				new PdfPCell(new Phrase("Jumlah", bold9))
		};
		
		PdfPCell[] header2 = new PdfPCell[]
		{
				new PdfPCell(new Phrase("Kriteria", bold9)),
				new PdfPCell(new Phrase("+15", bold9)),
				new PdfPCell(new Phrase("+30", bold9)),
				new PdfPCell(new Phrase("+45", bold9)),
				new PdfPCell(new Phrase("+50", bold9)),
				new PdfPCell(new Phrase("+55", bold9)),
				new PdfPCell(new Phrase("+60", bold9)),
				new PdfPCell(new Phrase("+65", bold9)),
				new PdfPCell(new Phrase("+70", bold9)),
				new PdfPCell(new Phrase("+75", bold9)),
				new PdfPCell(new Phrase("+80", bold9)),
				new PdfPCell(new Phrase("+85", bold9)),
				new PdfPCell(new Phrase("+90", bold9))
		};
		
		PdfPCell[] header3 = new PdfPCell[]
		{
				new PdfPCell(new Phrase("Opsyen Had Tebangan (cm)", bold9)),
				new PdfPCell(new Phrase("Pengeluaran Isipadu Bersih Sehektar (m\u00B3)", bold9)),
				new PdfPCell(new Phrase("Pokok Tebangan Sehektar (Bilangan)", bold9)),
				new PdfPCell(new Phrase("Bilangan Pokok Bersih Sehektar Dari Kelas Diameter + 30 cm - 45 cm dan Pokok Berdiameter + 45 cm yang Setara Dengannya Dalam Dirian Tinggal (Bilangan)", bold9))
		};
		
		PdfPCell[] header4 = new PdfPCell[]
		{
				new PdfPCell(new Phrase("Opsyen Had Tebangan (cm)", bold9)),
				new PdfPCell(new Phrase("Pengeluaran Isipadu Bersih Sehektar (m\u00B3)", bold9)),
				new PdfPCell(new Phrase("Pokok Tebangan Sehektar (Bilangan)", bold9)),
				new PdfPCell(new Phrase("Bilangan Pokok Bersih Sehektar Dari Kelas Diameter + 30 cm - 45 cm dan Pokok Berdiameter + 45 cm yang Setara Dengannya Dalam Dirian Tinggal (Bilangan)", bold9))
		};
		
		PdfPCell[] header5 = new PdfPCell[]
		{
				new PdfPCell(new Phrase("Kelas Diameter Pokok (cm)", bold9)),
				new PdfPCell(new Phrase("Diameter Pepanjat (cm)", bold9))
		};
		
		PdfPCell[] header6 = new PdfPCell[]
		{
				new PdfPCell(new Phrase("Kumpulan Spesis", bold9)),
				new PdfPCell(new Phrase("Bilangan Balak", bold9)),
				new PdfPCell(new Phrase("Kriteria", bold9)),
				new PdfPCell(new Phrase("+ 30 - 45", bold9)),
				new PdfPCell(new Phrase("+ 45 - 60", bold9)),
				new PdfPCell(new Phrase("+ 60 - 75", bold9)),
				new PdfPCell(new Phrase("+ 75 - 90", bold9)),
				new PdfPCell(new Phrase("+ 90", bold9)),
				new PdfPCell(new Phrase("Jumlah Besar", bold9))
		};
		
		PdfPCell[] header7 = new PdfPCell[]
		{
				new PdfPCell(new Phrase("Kumpulan Spesis", bold9)),
				new PdfPCell(new Phrase("Kualiti Balak", bold9)),
				new PdfPCell(new Phrase("Kriteria", bold9)),
				new PdfPCell(new Phrase("+ 30 - 45", bold9)),
				new PdfPCell(new Phrase("+ 45 - 60", bold9)),
				new PdfPCell(new Phrase("+ 60 - 75", bold9)),
				new PdfPCell(new Phrase("+ 75 - 90", bold9)),
				new PdfPCell(new Phrase("+ 90", bold9)),
				new PdfPCell(new Phrase("Jumlah Besar", bold9))
		};
		
		PdfPCell[] header8 = new PdfPCell[]
		{
				new PdfPCell(new Phrase("Ringkasan", bold9)),
				new PdfPCell(new Phrase("Bertam", bold9)),
				new PdfPCell(new Phrase("Palma", bold9))
		};
		
		PdfPCell[] header10 = new PdfPCell[]
		{
				new PdfPCell(new Phrase("Jenis", bold9)),
				new PdfPCell(new Phrase("Ringkasan Rotan", bold9)),
				new PdfPCell(new Phrase("Ringkasan Buluh", bold9))
		};
		
		PdfPCell[] header12 = new PdfPCell[]
		{
				new PdfPCell(new Phrase("Kelas Anak Benih", bold9)),
				new PdfPCell(new Phrase("Jumlah Bilangan", bold9)),
				new PdfPCell(new Phrase("Bilangan Sehektar", bold9))
		};
		
		header5[0].setRowspan(3);
		header10[0].setRowspan(2);
		
		header1[0].setColspan(2);
		header2[0].setColspan(2);
		header3[0].setColspan(2);
		header3[1].setColspan(3);
		header3[2].setColspan(4);
		header3[3].setColspan(4);
		header4[0].setColspan(2);
		header4[1].setColspan(3);
		header4[2].setColspan(4);
		header4[3].setColspan(4);
		header5[0].setColspan(2);
		header5[1].setColspan(6);
		header10[1].setColspan(8);
		header10[2].setColspan(4);
		
		PdfPCell[] subheader3 = new PdfPCell[]
		{
				new PdfPCell(new Phrase("Diptero-karp", bold9)),
				new PdfPCell(new Phrase("Bukan Diptero-karp", bold9)),
				new PdfPCell(new Phrase("Diptero-karp", bold9)),
				new PdfPCell(new Phrase("Bukan Diptero-karp", bold9)),
				new PdfPCell(new Phrase("Jumlah", bold9)),
				new PdfPCell(new Phrase("Diptero-karp", bold9)),
				new PdfPCell(new Phrase("Bukan Diptero-karp", bold9)),
				new PdfPCell(new Phrase("Jumlah", bold9)),
				new PdfPCell(new Phrase("% Diptero-karp", bold9)),
				new PdfPCell(new Phrase("Diptero-karp", bold9)),
				new PdfPCell(new Phrase("Bukan Diptero-karp", bold9)),
				new PdfPCell(new Phrase("Jumlah", bold9)),
				new PdfPCell(new Phrase("% Diptero-karp", bold9))
		};
		
		PdfPCell[] subheader4 = new PdfPCell[]
		{
				new PdfPCell(new Phrase("Diptero-karp", bold9)),
				new PdfPCell(new Phrase("Bukan Diptero-karp", bold9)),
				new PdfPCell(new Phrase("Diptero-karp", bold9)),
				new PdfPCell(new Phrase("Bukan Diptero-karp", bold9)),
				new PdfPCell(new Phrase("Jumlah", bold9)),
				new PdfPCell(new Phrase("Diptero-karp", bold9)),
				new PdfPCell(new Phrase("Bukan Diptero-karp", bold9)),
				new PdfPCell(new Phrase("Jumlah", bold9)),
				new PdfPCell(new Phrase("% Diptero-karp", bold9)),
				new PdfPCell(new Phrase("Diptero-karp", bold9)),
				new PdfPCell(new Phrase("Bukan Diptero-karp", bold9)),
				new PdfPCell(new Phrase("Jumlah", bold9)),
				new PdfPCell(new Phrase("% Diptero-karp", bold9))
		};
		
		PdfPCell[] subheader5 = new PdfPCell[]
		{
				new PdfPCell(new Phrase("2 cm - 5 cm Diameter", bold9)),
				new PdfPCell(new Phrase("+ 5 cm Diameter", bold9)),
				new PdfPCell(new Phrase("Jumlah Besar", bold9))
		};
		
		PdfPCell[] subheader10 = new PdfPCell[]
		{
				new PdfPCell(new Phrase("Manau/ Manau Tikus", bold9)),
				new PdfPCell(new Phrase("Semambu", bold9)),
				new PdfPCell(new Phrase("Dok", bold9)),
				new PdfPCell(new Phrase("Sega", bold9)),
				new PdfPCell(new Phrase("Dahan", bold9)),
				new PdfPCell(new Phrase("Lain-lain Jenis > 4 cm", bold9)),
				new PdfPCell(new Phrase("Lain-lain Jenis <= 4 cm", bold9)),
				new PdfPCell(new Phrase("Jumlah", bold9)),
				new PdfPCell(new Phrase("Kumpulan Buluh Betong", bold9)),
				new PdfPCell(new Phrase("Lain-lain Jenis > 3 cm", bold9)),
				new PdfPCell(new Phrase("Lain-lain Jenis <= 3 cm", bold9)),
				new PdfPCell(new Phrase("Jumlah", bold9)),
		};
		
		subheader5[0].setColspan(2);
		subheader5[1].setColspan(2);
		subheader5[2].setColspan(2);
		
		for (int i = 0; i < 14; i++)
		{
			header1[i].setBackgroundColor(BaseColor.LIGHT_GRAY);
			header1[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
			header1[i].setHorizontalAlignment(Element.ALIGN_CENTER);
			table1.addCell(header1[i]);
		}
		
		for (int i = 0; i < 13; i++)
		{
			header2[i].setBackgroundColor(BaseColor.LIGHT_GRAY);
			header2[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
			header2[i].setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(header2[i]);
		}
		
		for (int i = 0; i < 4; i++)
		{
			header3[i].setBackgroundColor(BaseColor.LIGHT_GRAY);
			header3[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
			header3[i].setHorizontalAlignment(Element.ALIGN_CENTER);
			table3.addCell(header3[i]);
			
			header4[i].setBackgroundColor(BaseColor.LIGHT_GRAY);
			header4[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
			header4[i].setHorizontalAlignment(Element.ALIGN_CENTER);
			table4.addCell(header4[i]);
		}
		
		for (int i = 0; i < 2; i++)
		{
			header5[i].setBackgroundColor(BaseColor.LIGHT_GRAY);
			header5[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
			header5[i].setHorizontalAlignment(Element.ALIGN_CENTER);
			table5.addCell(header5[i]);
		}
		
		for (int i = 0; i < 9; i++)
		{
			header6[i].setBackgroundColor(BaseColor.LIGHT_GRAY);
			header6[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
			header6[i].setHorizontalAlignment(Element.ALIGN_CENTER);
			table6.addCell(header6[i]);
			
			header7[i].setBackgroundColor(BaseColor.LIGHT_GRAY);
			header7[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
			header7[i].setHorizontalAlignment(Element.ALIGN_CENTER);
			table7.addCell(header7[i]);
		}
		
		for (int i = 0; i < 3; i++)
		{
			header8[i].setBackgroundColor(BaseColor.LIGHT_GRAY);
			header8[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
			header8[i].setHorizontalAlignment(Element.ALIGN_CENTER);
			table8.addCell(header8[i]);
		}
		
		PdfPCell header9 = new PdfPCell(new Phrase("Kelas Peratusan", bold9));
		
		header9.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header9.setVerticalAlignment(Element.ALIGN_MIDDLE);
		header9.setHorizontalAlignment(Element.ALIGN_CENTER);
		table9.addCell(header9);
		
		for (int i = 0; i < 5; i++)
		{
			PdfPCell cell = new PdfPCell(new Phrase(resams.get(i).getName(), bold9));
			
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table9.addCell(cell);
		}
		
		header9 = new PdfPCell(new Phrase("Jumlah", bold9));
		
		header9.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header9.setVerticalAlignment(Element.ALIGN_MIDDLE);
		header9.setHorizontalAlignment(Element.ALIGN_CENTER);
		table9.addCell(header9);
		
		for (int i = 0; i < 3; i++)
		{
			header10[i].setBackgroundColor(BaseColor.LIGHT_GRAY);
			header10[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
			header10[i].setHorizontalAlignment(Element.ALIGN_CENTER);
			table10.addCell(header10[i]);
		}
		
		PdfPCell header11 = new PdfPCell(new Phrase("Kelas Diameter", bold9));
		
		header11.setColspan(2);
		header11.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header11.setVerticalAlignment(Element.ALIGN_MIDDLE);
		header11.setHorizontalAlignment(Element.ALIGN_CENTER);
		table11.addCell(header11);
		
		for (int i = 0; i < 4; i++)
		{
			PdfPCell cell = new PdfPCell(new Phrase(fertilities.get(i).getName(), bold9));
			
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table11.addCell(cell);
		}
		
		header11 = new PdfPCell(new Phrase("Jumlah", bold9));
		
		header11.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header11.setVerticalAlignment(Element.ALIGN_MIDDLE);
		header11.setHorizontalAlignment(Element.ALIGN_CENTER);
		table11.addCell(header11);
		
		for (int i = 0; i < 3; i++)
		{
			header12[i].setBackgroundColor(BaseColor.LIGHT_GRAY);
			header12[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
			header12[i].setHorizontalAlignment(Element.ALIGN_CENTER);
			table12.addCell(header12[i]);
		}
		
		for (int i = 0; i < 13; i++)
		{
			subheader3[i].setBackgroundColor(BaseColor.LIGHT_GRAY);
			subheader3[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
			subheader3[i].setHorizontalAlignment(Element.ALIGN_CENTER);
			table3.addCell(subheader3[i]);
			
			subheader4[i].setBackgroundColor(BaseColor.LIGHT_GRAY);
			subheader4[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
			subheader4[i].setHorizontalAlignment(Element.ALIGN_CENTER);
			table4.addCell(subheader4[i]);
		}
		
		for (int i = 0; i < 3; i++)
		{
			subheader5[i].setBackgroundColor(BaseColor.LIGHT_GRAY);
			subheader5[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
			subheader5[i].setHorizontalAlignment(Element.ALIGN_CENTER);
			table5.addCell(subheader5[i]);
		}
		
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 2; j++)
			{
				PdfPCell cell = new PdfPCell(new Phrase(vineSpreadths.get(j).getName(), bold9));
				
				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table5.addCell(cell);
			}
		}
		
		for (int i = 0; i < 12; i++)
		{
			subheader10[i].setBackgroundColor(BaseColor.LIGHT_GRAY);
			subheader10[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
			subheader10[i].setHorizontalAlignment(Element.ALIGN_CENTER);
			table10.addCell(subheader10[i]);
		}
		
		for (int i = 0; i < nonCumulativeEstimations.size(); i++)
		{
			PreFellingReportElement element = nonCumulativeEstimations.get(i);
			double[] values = element.getValues();
			
			if (i % 5 == 0)
			{
				PdfPCell cell = new PdfPCell(new Phrase(element.getGroup(), size9));
				
				cell.setRowspan(5);
				table1.addCell(cell);
			}
			
			table1.addCell(new PdfPCell(new Phrase(element.getCriteria(), size9)));
			
			for (int j = 0; j < 13; j++)
			{
				PdfPCell cell = new PdfPCell(new Phrase(df.format(values[j]), size9));
				
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table1.addCell(cell);
			}
		}

		for (int i = 0; i < cumulativeEstimations.size(); i++)
		{
			PreFellingReportElement element = cumulativeEstimations.get(i);
			double[] values = element.getValues();
			
			if (i % 5 == 0)
			{
				PdfPCell cell = new PdfPCell(new Phrase(element.getGroup(), size9));
				
				cell.setRowspan(5);
				table2.addCell(cell);
			}
			
			table2.addCell(new PdfPCell(new Phrase(element.getCriteria(), size9)));
			
			for (int j = 0; j < 12; j++)
			{
				PdfPCell cell = new PdfPCell(new Phrase(df.format(values[j]), size9));
				
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell);
			}
		}
		
		for (int i = 0; i < preFellingCuttingOptions.size(); i++)
		{
			PreFellingCuttingOption option = preFellingCuttingOptions.get(i);
			PdfPCell[] cells = new PdfPCell[]
			{
					new PdfPCell(new Phrase(df.format(option.getDipterocarpLimit()), size9)),
					new PdfPCell(new Phrase(df.format(option.getNonDipterocarpLimit()), size9)),
					new PdfPCell(new Phrase(df.format(option.getRelativeDipNetVolume()), size9)),
					new PdfPCell(new Phrase(df.format(option.getRelativeNonDipNetVolume()), size9)),
					new PdfPCell(new Phrase(df.format(option.getRelativeTotalNetVolume()), size9)),
					new PdfPCell(new Phrase(df.format(option.getRelativeDipCount()), size9)),
					new PdfPCell(new Phrase(df.format(option.getRelativeNonDipCount()), size9)),
					new PdfPCell(new Phrase(df.format(option.getRelativeTotalCount()), size9)),
					new PdfPCell(new Phrase(dfp.format(option.getRelativeTotalCountRatio()), size9))
			};
			
			for (PdfPCell cell : cells)
			{
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table3.addCell(cell);
				table4.addCell(cell);
			}
			
			PdfPCell cellDipPartial = new PdfPCell(new Phrase(df.format(option.getRelativeDipNetCountPartial()), size9));
			PdfPCell cellNonDipPartial = new PdfPCell(new Phrase(df.format(option.getRelativeNonDipNetCountPartial()), size9));
			PdfPCell cellTotalPartial = new PdfPCell(new Phrase(df.format(option.getRelativeTotalNetCountPartial()), size9));
			PdfPCell cellTotalPartialRatio = new PdfPCell(new Phrase(dfp.format(option.getRelativeTotalNetCountPartialRatio()), size9));
			
			PdfPCell cellDipAll = new PdfPCell(new Phrase(df.format(option.getRelativeDipNetCountAll()), size9));
			PdfPCell cellNonDipAll = new PdfPCell(new Phrase(df.format(option.getRelativeNonDipNetCountAll()), size9));
			PdfPCell cellTotalAll = new PdfPCell(new Phrase(df.format(option.getRelativeTotalNetCountAll()), size9));
			PdfPCell cellTotalAllRatio = new PdfPCell(new Phrase(dfp.format(option.getRelativeTotalNetCountAllRatio()), size9));
			
			cellDipPartial.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellNonDipPartial.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellTotalPartial.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellTotalPartialRatio.setHorizontalAlignment(Element.ALIGN_CENTER);
			table3.addCell(cellDipPartial);
			table3.addCell(cellNonDipPartial);
			table3.addCell(cellTotalPartial);
			table3.addCell(cellTotalPartialRatio);
			
			cellDipAll.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellNonDipAll.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellTotalAll.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellTotalAllRatio.setHorizontalAlignment(Element.ALIGN_CENTER);
			table4.addCell(cellDipAll);
			table4.addCell(cellNonDipAll);
			table4.addCell(cellTotalAll);
			table4.addCell(cellTotalAllRatio);
		}
		
		for (int i = 0; i < vineSpreadthCounts.size(); i++)
		{
			PreFellingReportElement element = vineSpreadthCounts.get(i);
			double[] values = element.getValues();
			
			if (i % 2 == 0)
			{
				PdfPCell cell = new PdfPCell(new Phrase(element.getGroup(), size9));
				
				cell.setRowspan(2);
				table5.addCell(cell);
			}
			
			table5.addCell(new PdfPCell(new Phrase(element.getCriteria(), size9)));
			
			for (int j = 0; j < 6; j++)
			{
				PdfPCell cell = new PdfPCell(new Phrase(df.format(values[j]), size9));
				
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table5.addCell(cell);
			}
		}
		
		for (int i = 0; i < logQuantityCounts.size(); i++)
		{
			PreFellingReportElement element = logQuantityCounts.get(i);
			double[] values = element.getValues();
			
			if (i % 10 == 0)
			{
				PdfPCell cell = new PdfPCell(new Phrase(element.getGroup(), size9));
				
				cell.setRowspan(10);
				table6.addCell(cell);
			}
			
			if (i % 2 == 0)
			{
				PdfPCell cell = new PdfPCell(new Phrase(element.getSubgroup(), size9));
				
				cell.setRowspan(2);
				table6.addCell(cell);
			}
			
			table6.addCell(new PdfPCell(new Phrase(element.getCriteria(), size9)));
			
			for (int j = 0; j < 6; j++)
			{
				PdfPCell cell = new PdfPCell(new Phrase(df.format(values[j]), size9));
				
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table6.addCell(cell);
			}
		}
		
		for (int i = 0; i < logQualityCounts.size(); i++)
		{
			PreFellingReportElement element = logQualityCounts.get(i);
			double[] values = element.getValues();
			
			if (i % 6 == 0)
			{
				PdfPCell cell = new PdfPCell(new Phrase(element.getGroup(), size9));
				
				cell.setRowspan(6);
				table7.addCell(cell);
			}
			
			if (i % 2 == 0)
			{
				PdfPCell cell = new PdfPCell(new Phrase(element.getSubgroup(), size9));
				
				cell.setRowspan(2);
				table7.addCell(cell);
			}
			
			table7.addCell(new PdfPCell(new Phrase(element.getCriteria(), size9)));
			
			for (int j = 0; j < 6; j++)
			{
				PdfPCell cell = new PdfPCell(new Phrase(df.format(values[j]), size9));
				
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table7.addCell(cell);
			}
		}
		
		for (int i = 0; i < bertamPalmCounts.size(); i++)
		{
			PreFellingReportElement element = bertamPalmCounts.get(i);
			double[] values = element.getValues();
			
			table8.addCell(new PdfPCell(new Phrase(element.getCriteria(), size9)));
			
			for (int j = 0; j < 2; j++)
			{
				PdfPCell cell = new PdfPCell(new Phrase(df.format(values[j]), size9));
				
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table8.addCell(cell);
			}
		}
		
		for (int i = 0; i < resamCounts.size(); i++)
		{
			PreFellingReportElement element = resamCounts.get(i);
			double[] values = element.getValues();
			
			table9.addCell(new PdfPCell(new Phrase(element.getCriteria(), size9)));
			
			for (int j = 0; j < 6; j++)
			{
				PdfPCell cell = new PdfPCell(new Phrase(df.format(values[j]), size9));
				
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table9.addCell(cell);
			}
		}
		
		for (int i = 0; i < rattanBambooCounts.size(); i++)
		{
			PreFellingReportElement element = rattanBambooCounts.get(i);
			double[] values = element.getValues();
			
			table10.addCell(new PdfPCell(new Phrase(element.getCriteria(), size9)));
			
			for (int j = 0; j < 12; j++)
			{
				PdfPCell cell = new PdfPCell(new Phrase(df.format(values[j]), size9));
				
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table10.addCell(cell);
			}
		}
		
		for (int i = 0; i < fertilityCounts.size(); i++)
		{
			PreFellingReportElement element = fertilityCounts.get(i);
			double[] values = element.getValues();
			
			if (i % 2 == 0)
			{
				PdfPCell cell = new PdfPCell(new Phrase(element.getGroup(), size9));
				
				cell.setRowspan(2);
				table11.addCell(cell);
			}
			
			table11.addCell(new PdfPCell(new Phrase(element.getCriteria(), size9)));
			
			for (int j = 0; j < 5; j++)
			{
				PdfPCell cell = new PdfPCell(new Phrase(df.format(values[j]), size9));
				
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table11.addCell(cell);
			}
		}
		
		for (int i = 0; i < seedlingCounts.size(); i++)
		{
			PreFellingReportElement element = seedlingCounts.get(i);
			double[] values = element.getValues();
			
			table12.addCell(new PdfPCell(new Phrase(element.getCriteria(), size9)));
			
			for (int j = 0; j < 2; j++)
			{
				PdfPCell cell = new PdfPCell(new Phrase(df.format(values[j]), size9));
				
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table12.addCell(cell);
			}
		}
		
		Paragraph[] titles = new Paragraph[12];
		
		for (int i = 0; i < 12; i++)
		{
			titles[i] = new Paragraph("LAPORAN INVENTORI HUTAN SEBELUM TEBANGAN (PRE-F) HUTAN SIMPAN " + preFellingSurvey.getForestName().toUpperCase() + " KOMPATMEN " + preFellingSurvey.getComptBlockNo() + " TAHUN " + preFellingSurvey.getYear() + "\nJADUAL " + (i + 1) + ": " + headers[i].toUpperCase() + "\n\n", bold9);
			titles[i].setAlignment(Paragraph.ALIGN_CENTER);
		}
		
		document.add(titles[0]);
		document.add(table1);
		document.newPage();
		
		document.add(titles[1]);
		document.add(table2);
		
		document.setPageSize(PageSize.A4);
		document.newPage();
		
		document.add(titles[2]);
		document.add(table3);
		document.add(new Paragraph("\nNOTA: Peratus KAUM DIPTEROKARP yang berdiameter +30 cm dalam dirian asal = " + dfp.format(preFellingReport.getOriginalStandRatio()), size9));
		document.newPage();
		
		document.add(titles[3]);
		document.add(table4);
		document.add(new Paragraph("\nNOTA: Peratus KAUM DIPTEROKARP yang berdiameter +30 cm dalam dirian asal = " + dfp.format(preFellingReport.getOriginalStandRatio()), size9));
		document.newPage();
		
		document.add(titles[4]);
		document.add(table5);
		document.newPage();
		
		document.add(titles[5]);
		document.add(table6);
		document.newPage();
		
		document.add(titles[6]);
		document.add(table7);
		document.newPage();
		
		document.add(titles[7]);
		document.add(table8);
		document.newPage();
		
		document.add(titles[8]);
		document.add(table9);
		
		document.setPageSize(PageSize.A4.rotate());
		document.newPage();
		
		document.add(titles[9]);
		document.add(table10);
		
		document.setPageSize(PageSize.A4);
		document.newPage();
		
		document.add(titles[10]);
		document.add(table11);
		document.newPage();
		
		document.add(titles[11]);
		document.add(table12);
		
		document.close();
	}
}