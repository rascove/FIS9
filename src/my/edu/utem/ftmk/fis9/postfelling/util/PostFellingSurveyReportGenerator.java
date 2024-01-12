package my.edu.utem.ftmk.fis9.postfelling.util;

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

import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingSurvey;
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingReport;
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingReportElement;

/**
 * @author Satrya Fajri Pratama, Zurina
 */
public class PostFellingSurveyReportGenerator
{
	private static Font size9 = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);
	private static Font bold9 = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);

	public static void generate(File file, PostFellingSurvey survey, ArrayList<Resam> resams,
			ArrayList<Fertility> fertilities) throws Exception
	{
		
		

		DecimalFormat df = new DecimalFormat("0.00");
		
		PostFellingReport surveyReport = survey.getPostFellingReport();
		String[] headers = surveyReport.getHeaders();
		ArrayList<PostFellingReportElement> format1A = surveyReport.getReportFormat1A();
		ArrayList<PostFellingReportElement> format1B = surveyReport.getReportFormat1B();
		ArrayList<PostFellingReportElement> format2A = surveyReport.getReportFormat2A();
		ArrayList<PostFellingReportElement> format2B = surveyReport.getReportFormat2B();
		ArrayList<PostFellingReportElement> format2C = surveyReport.getReportFormat2C();
		ArrayList<PostFellingReportElement> format3A = surveyReport.getReportFormat3A();
		ArrayList<PostFellingReportElement> format3B = surveyReport.getReportFormat3B();
		ArrayList<PostFellingReportElement> format3C = surveyReport.getReportFormat3C();
		ArrayList<PostFellingReportElement> format4A = surveyReport.getReportFormat4A();
		ArrayList<PostFellingReportElement> format4B = surveyReport.getReportFormat4B();
		ArrayList<PostFellingReportElement> format4C = surveyReport.getReportFormat4C();
		ArrayList<PostFellingReportElement> format4D = surveyReport.getReportFormat4D();

		
		/*========= Intepretasi =========*/
		
		PdfPTable tableIntepretasi = new PdfPTable(1);
		tableIntepretasi.setWidthPercentage(90);
		tableIntepretasi.setWidths(new float[]{100f});
		
		
		ArrayList<String> intepretasi = surveyReport.getReportIntepretation();
		String str = "";
		for (int i = 0; i < intepretasi.size();i++)
		{
			//desc += intepretasi.get(i) + "\n";
			PdfPCell cellDesc;
			str = intepretasi.get(i);
			if (str.startsWith("Dari ") || str.startsWith("Langkah")) 
				 cellDesc = new PdfPCell(new Phrase("\n"+str+"\n",bold9));
			else if (str.startsWith("+ "))
				cellDesc = new PdfPCell(new Phrase("                        "+ str,size9));
			else if (str.startsWith("= "))
				cellDesc = new PdfPCell(new Phrase("                                                                                    "+ str,size9));
			else
				cellDesc = new PdfPCell(new Phrase(str,size9));
			
			cellDesc.setBorder(0);
			cellDesc.setPadding(10f);
			cellDesc.setPaddingTop(2);
			cellDesc.setPaddingBottom(2);
			tableIntepretasi.addCell(cellDesc);
			
		}
		
		
		
		
		
		
		/*========= Format 1A =========*/
		
		PdfPTable table1A = new PdfPTable(9);
		table1A.setWidthPercentage(100);
		table1A.setWidths(new float[]
		{
				16f, 4f, 10f, 10f, 10f, 10f, 10f, 10f, 10f
		});

		

		PdfPCell[] header1A = new PdfPCell[]
		{
				new PdfPCell(new Phrase("STATUS", bold9)), 
				new PdfPCell(new Phrase("+15 - 30 cm", bold9)),
				new PdfPCell(new Phrase("+30 - 45 cm", bold9)), 
				new PdfPCell(new Phrase("+45 - 60 cm", bold9)),
				new PdfPCell(new Phrase("+60 - 75 cm", bold9)),
				new PdfPCell(new Phrase("+75 - 90 cm", bold9)),
				new PdfPCell(new Phrase("+90", bold9)), 
				new PdfPCell(new Phrase("Jumlah", bold9))
		};
		header1A[0].setColspan(2);

		for (int i = 0; i < 8; i++)
		{
			header1A[i].setBackgroundColor(BaseColor.LIGHT_GRAY);
			header1A[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
			header1A[i].setHorizontalAlignment(Element.ALIGN_CENTER);
			table1A.addCell(header1A[i]);
		}
		
		for (int i = 0; i < format1A.size(); i++)
		{
			PostFellingReportElement element = format1A.get(i);
			double[] values = element.getValues();

			if (i % 2 == 0)
			{
				PdfPCell cell = new PdfPCell(new Phrase(element.getGroup(), size9));
				cell.setRowspan(2);
				table1A.addCell(cell);
			}

			table1A.addCell(new PdfPCell(new Phrase(element.getCriteria(), size9)));

			for (int j = 0; j < 7; j++)
			{
				PdfPCell cell = new PdfPCell(new Phrase(df.format(values[j]), size9));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table1A.addCell(cell);
			}
		}
		
		/*========= Format 1B =========*/
		
		PdfPTable table1B = new PdfPTable(5);
		table1B.setWidthPercentage(100);
		table1B.setWidths(new float[]
		{
				10f, 4f, 8f, 8f, 8f
		});

		PdfPCell[] header1B = new PdfPCell[]
		{
			new PdfPCell(new Phrase("KESUBURAN", bold9)), 
			new PdfPCell(new Phrase("Pokok Jaras Kecil (+5 - 15 cm)", bold9)),
			new PdfPCell(new Phrase("Jumlah", bold9)),
		};
		header1B[0].setColspan(2);
		header1B[0].setRowspan(2);
		header1B[1].setColspan(2);
		header1B[2].setRowspan(2);

		
		for (int i = 0; i < 3; i++)
		{
			header1B[i].setBackgroundColor(BaseColor.LIGHT_GRAY);
			header1B[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
			header1B[i].setHorizontalAlignment(Element.ALIGN_CENTER);
			table1B.addCell(header1B[i]);
		}
		PdfPCell[] subheader1B = new PdfPCell[]
		{
						new PdfPCell(new Phrase("SPESIS RS", bold9)),
						new PdfPCell(new Phrase("SPESIS BUKAN RS", bold9)),
		};

		for (int i = 0; i < 2; i++)
		{
			subheader1B[i].setBackgroundColor(BaseColor.LIGHT_GRAY);
			subheader1B[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
			subheader1B[i].setHorizontalAlignment(Element.ALIGN_CENTER);
			table1B.addCell(subheader1B[i]);
		}

		for (int i = 0; i < format1B.size(); i++)
		{
			PostFellingReportElement element = format1B.get(i);
			double[] values = element.getValues();

			if (i % 2 == 0)
			{
				PdfPCell cell = new PdfPCell(new Phrase(element.getGroup(), size9));
				cell.setRowspan(2);
				table1B.addCell(cell);
			}

			table1B.addCell(new PdfPCell(new Phrase(element.getCriteria(), size9)));

			for (int j = 0; j < 3; j++)
			{
				PdfPCell cell = new PdfPCell(new Phrase(df.format(values[j]), size9));

				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table1B.addCell(cell);
			}
		}
		
		/*========= Format 2A 2B 2C =========*/
		
		PdfPTable table2A = new PdfPTable(8);
		table2A.setWidthPercentage(100);
		table2A.setWidths(new float[]
		{
				8f, 4f, 4f, 4f, 4f, 4f, 6f, 6f
		});

		PdfPTable table2B = new PdfPTable(8);
		table2B.setWidthPercentage(100);
		table2B.setWidths(new float[]
		{
				8f, 4f, 4f, 4f, 4f, 4f, 6f, 6f
		});
		
		PdfPTable table2C = new PdfPTable(8);
		table2C.setWidthPercentage(100);
		table2C.setWidths(new float[]
		{
				8f, 4f, 4f, 4f, 4f, 4f, 6f, 6f
		});
		
		PdfPCell[] header2ABC = new PdfPCell[]
		{
			new PdfPCell(new Phrase("STATUS", bold9)), 
			new PdfPCell(new Phrase("KELAS KEUNGGULAN", bold9)),
			new PdfPCell(new Phrase("JUMLAH SEMUA", bold9)),
			new PdfPCell(new Phrase("JUMLAH D,C1,S1", bold9)),
		};
		header2ABC[0].setRowspan(2);
		header2ABC[1].setColspan(5);
		header2ABC[2].setRowspan(2);
		header2ABC[3].setRowspan(2);

		PdfPCell[] subheader2ABC = new PdfPCell[]
		{
				new PdfPCell(new Phrase("1 = D", bold9)),
				new PdfPCell(new Phrase("2 = C1", bold9)),
				new PdfPCell(new Phrase("3 = C2", bold9)),
				new PdfPCell(new Phrase("4 = S1", bold9)),
				new PdfPCell(new Phrase("5 = S2", bold9)),
				
		};
		for (int i = 0; i < 4; i++)
		{
			header2ABC[i].setBackgroundColor(BaseColor.LIGHT_GRAY);
			header2ABC[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
			header2ABC[i].setHorizontalAlignment(Element.ALIGN_CENTER);
			table2A.addCell(header2ABC[i]);
			table2B.addCell(header2ABC[i]);
			table2C.addCell(header2ABC[i]);
		}
		
		for (int i = 0; i < 5; i++)
		{
			subheader2ABC[i].setBackgroundColor(BaseColor.LIGHT_GRAY);
			subheader2ABC[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
			subheader2ABC[i].setHorizontalAlignment(Element.ALIGN_CENTER);
			table2A.addCell(subheader2ABC[i]);
			table2B.addCell(subheader2ABC[i]);
			table2C.addCell(subheader2ABC[i]);
		}
		

		for (int i = 0; i < format2A.size(); i++)
		{
			PostFellingReportElement element = format2A.get(i);
			double[] values = element.getValues();
			
			PdfPCell cellElement = new PdfPCell(new Phrase(element.getGroup(), size9));
			table2A.addCell(cellElement);
			
			for (int j = 0; j < 7; j++)
			{
				PdfPCell cell = new PdfPCell(new Phrase(df.format(values[j]), size9));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2A.addCell(cell);
			}
		}
		
		for (int i = 0; i < format2B.size(); i++)
		{
			PostFellingReportElement element = format2B.get(i);
			double[] values = element.getValues();
			
			PdfPCell cellElement = new PdfPCell(new Phrase(element.getGroup(), size9));
			table2B.addCell(cellElement);
			
			for (int j = 0; j < 7; j++)
			{
				PdfPCell cell = new PdfPCell(new Phrase(df.format(values[j]), size9));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2B.addCell(cell);
			}
		}
		
		for (int i = 0; i < format2C.size(); i++)
		{
			PostFellingReportElement element = format2C.get(i);
			double[] values = element.getValues();
			
			PdfPCell cellElement = new PdfPCell(new Phrase(element.getGroup(), size9));
			table2C.addCell(cellElement);
			
			for (int j = 0; j < 7; j++)
			{
				PdfPCell cell = new PdfPCell(new Phrase(df.format(values[j]), size9));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2C.addCell(cell);
			}
		}
		
/*========= Format 3A 3B 3C =========*/
		
		PdfPTable table3A = new PdfPTable(14);
		table3A.setWidthPercentage(100);
		table3A.setWidths(new float[]
		{
				10f, 4f, 4f, 5f, 4f, 4f, 4f, 4f, 4f, 4f, 4f, 4f, 4f, 6f
		});

		PdfPTable table3B = new PdfPTable(14);
		table3B.setWidthPercentage(100);
		table3B.setWidths(new float[]
		{
				10f, 4f, 4f, 5f, 4f, 4f, 4f, 4f, 4f, 4f, 4f, 4f, 4f, 6f
		});
		
		PdfPTable table3C = new PdfPTable(14);
		table3C.setWidthPercentage(100);
		table3C.setWidths(new float[]
		{
				10f, 4f, 4f, 5f, 4f, 4f, 4f, 4f, 4f, 4f, 4f, 4f, 4f, 6f
		});
		
		PdfPCell[] header3ABC = new PdfPCell[]
		{
			new PdfPCell(new Phrase("STATUS", bold9)), 
			new PdfPCell(new Phrase("GANGGUAN OLEH PEPANJAT", bold9))
		};
		header3ABC[0].setRowspan(2);
		header3ABC[1].setColspan(13);
		

		PdfPCell[] subheader3ABC = new PdfPCell[]
		{
				new PdfPCell(new Phrase("1,2,4", bold9)),
				new PdfPCell(new Phrase("0,3,5", bold9)),
				new PdfPCell(new Phrase("JUMLAH", bold9)),
				new PdfPCell(new Phrase("1", bold9)),
				new PdfPCell(new Phrase("2", bold9)),
				new PdfPCell(new Phrase("3", bold9)),
				new PdfPCell(new Phrase("4", bold9)),
				new PdfPCell(new Phrase("5", bold9)),
				new PdfPCell(new Phrase("6", bold9)),
				new PdfPCell(new Phrase("7", bold9)),
				new PdfPCell(new Phrase("8", bold9)),
				new PdfPCell(new Phrase("9", bold9)),
				new PdfPCell(new Phrase("JUMLAH", bold9))
				
		};
		for (int i = 0; i <= 1; i++)
		{
			header3ABC[i].setBackgroundColor(BaseColor.LIGHT_GRAY);
			header3ABC[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
			header3ABC[i].setHorizontalAlignment(Element.ALIGN_CENTER);
			table3A.addCell(header3ABC[i]);
			table3B.addCell(header3ABC[i]);
			table3C.addCell(header3ABC[i]);
		}
		
		for (int i = 0; i < 13; i++)
		{
			subheader3ABC[i].setBackgroundColor(BaseColor.LIGHT_GRAY);
			subheader3ABC[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
			subheader3ABC[i].setHorizontalAlignment(Element.ALIGN_CENTER);
			table3A.addCell(subheader3ABC[i]);
			table3B.addCell(subheader3ABC[i]);
			table3C.addCell(subheader3ABC[i]);
		}
		

		for (int i = 0; i < format3A.size(); i++)
		{
			PostFellingReportElement element = format3A.get(i);
			double[] values = element.getValues();
			
			PdfPCell cellElement = new PdfPCell(new Phrase(element.getGroup(), size9));
			table3A.addCell(cellElement);
			
			for (int j = 0; j < 13; j++)
			{
				PdfPCell cell = new PdfPCell(new Phrase(df.format(values[j]), size9));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table3A.addCell(cell);
			}
		}
		
		for (int i = 0; i < format3B.size(); i++)
		{
			PostFellingReportElement element = format3B.get(i);
			double[] values = element.getValues();
			
			PdfPCell cellElement = new PdfPCell(new Phrase(element.getGroup(), size9));
			table3B.addCell(cellElement);
			
			for (int j = 0; j < 13; j++)
			{
				PdfPCell cell = new PdfPCell(new Phrase(df.format(values[j]), size9));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table3B.addCell(cell);
			}
		}
		
		for (int i = 0; i < format3C.size(); i++)
		{
			PostFellingReportElement element = format3C.get(i);
			double[] values = element.getValues();
			
			PdfPCell cellElement = new PdfPCell(new Phrase(element.getGroup(), size9));
			table3C.addCell(cellElement);
			
			for (int j = 0; j < 13; j++)
			{
				PdfPCell cell = new PdfPCell(new Phrase(df.format(values[j]), size9));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table3C.addCell(cell);
			}
		}
		
		/*========= Format 4A 4B 4C =========*/
		
		PdfPTable table4A = new PdfPTable(13);
		table4A.setWidthPercentage(100);
		table4A.setWidths(new float[]
		{
				8f, 4f, 4f, 4f, 4f, 4f, 4f, 4f, 4f, 4f, 4f, 4f, 6f
		});

		PdfPTable table4B = new PdfPTable(13);
		table4B.setWidthPercentage(100);
		table4B.setWidths(new float[]
		{
				8f, 4f, 4f, 4f, 4f, 4f, 4f, 4f, 4f, 4f, 4f, 4f, 6f
		});
		
		PdfPTable table4C = new PdfPTable(13);
		table4C.setWidthPercentage(100);
		table4C.setWidths(new float[]
		{
				8f, 4f, 4f, 4f, 4f, 4f, 4f, 4f, 4f, 4f, 4f, 4f, 6f
		});
	
		
		PdfPCell[] header4AB = new PdfPCell[]
		{
			new PdfPCell(new Phrase("Bil. Anak Pokok", bold9)), 
			new PdfPCell(new Phrase("0", bold9)),
			new PdfPCell(new Phrase("1", bold9)),
			new PdfPCell(new Phrase("2", bold9)),
			new PdfPCell(new Phrase("3", bold9)),
			new PdfPCell(new Phrase("4", bold9)),
			new PdfPCell(new Phrase("5", bold9)),
			new PdfPCell(new Phrase("6", bold9)),
			new PdfPCell(new Phrase("7", bold9)),
			new PdfPCell(new Phrase("8", bold9)),
			new PdfPCell(new Phrase(">9", bold9)),
			new PdfPCell(new Phrase("JUMLAH", bold9))
		};
		header4AB[0].setColspan(2);
		header4AB[0].setBackgroundColor(BaseColor.LIGHT_GRAY);
		header4AB[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
		header4AB[0].setHorizontalAlignment(Element.ALIGN_RIGHT);
		table4A.addCell(header4AB[0]);
		table4B.addCell(header4AB[0]);
		
		PdfPCell header4C = new PdfPCell(new Phrase("Bil. Rumpun", bold9));
		header4C.setColspan(2);
		header4C.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header4C.setVerticalAlignment(Element.ALIGN_MIDDLE);
		header4C.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table4C.addCell(header4C);
		
		for (int i = 1; i < header4AB.length; i++)
		{
			header4AB[i].setRowspan(2);
			header4AB[i].setBackgroundColor(BaseColor.LIGHT_GRAY);
			header4AB[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
			header4AB[i].setHorizontalAlignment(Element.ALIGN_CENTER);
			table4A.addCell(header4AB[i]);
			table4B.addCell(header4AB[i]);
			table4C.addCell(header4AB[i]);
		}	
		
		PdfPCell subheader4AB = new PdfPCell(new Phrase("Kump. Spesis", bold9));
		subheader4AB.setBackgroundColor(BaseColor.LIGHT_GRAY);
		subheader4AB.setVerticalAlignment(Element.ALIGN_MIDDLE);
		subheader4AB.setHorizontalAlignment(Element.ALIGN_LEFT);
		subheader4AB.setColspan(2);
		table4A.addCell(subheader4AB);
		table4B.addCell(subheader4AB);
		
		PdfPCell subheader4C = new PdfPCell(new Phrase("Batang", bold9));
		subheader4C.setBackgroundColor(BaseColor.LIGHT_GRAY);
		subheader4C.setVerticalAlignment(Element.ALIGN_MIDDLE);
		subheader4C.setHorizontalAlignment(Element.ALIGN_LEFT);
		subheader4C.setColspan(2);
		table4C.addCell(subheader4C);
		

		for (int i = 0; i < format4A.size(); i++)
		{
			PostFellingReportElement element = format4A.get(i);
			double[] values = element.getValues();
			
			if (i % 2 == 0)
			{
				PdfPCell cell = new PdfPCell(new Phrase(element.getGroup(), size9));
				cell.setRowspan(2);
				table4A.addCell(cell);
			}

			table4A.addCell(new PdfPCell(new Phrase(element.getCriteria(), size9)));
			
			for (int j = 0; j < 11; j++)
			{
				PdfPCell cell = new PdfPCell(new Phrase(df.format(values[j]), size9));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table4A.addCell(cell);
			}
		}
		
		for (int i = 0; i < format4B.size(); i++)
		{
			PostFellingReportElement element = format4B.get(i);
			double[] values = element.getValues();
			
			if (i % 2 == 0)
			{
				PdfPCell cell = new PdfPCell(new Phrase(element.getGroup(), size9));
				cell.setRowspan(2);
				table4B.addCell(cell);
			}

			table4B.addCell(new PdfPCell(new Phrase(element.getCriteria(), size9)));
			
			for (int j = 0; j < 11; j++)
			{
				PdfPCell cell = new PdfPCell(new Phrase(df.format(values[j]), size9));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table4B.addCell(cell);
			}
		}
		
		for (int i = 0; i < format4C.size(); i++)
		{
			PostFellingReportElement element = format4C.get(i);
			double[] values = element.getValues();
			
			if (i % 2 == 0)
			{
				PdfPCell cell = new PdfPCell(new Phrase(element.getGroup(), size9));
				cell.setRowspan(2);
				table4C.addCell(cell);
			}

			table4C.addCell(new PdfPCell(new Phrase(element.getCriteria(), size9)));
			
			for (int j = 0; j < 11; j++)
			{
				PdfPCell cell = new PdfPCell(new Phrase(df.format(values[j]), size9));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table4C.addCell(cell);
			}
		}
		
		/*========= Format 4D =========*/
		
		PdfPTable table4D = new PdfPTable(8);
		table4D.setWidthPercentage(100);
		table4D.setWidths(new float[]
		{
				6f, 4f, 4f, 4f, 4f, 4f, 4f, 4f
		});

		PdfPCell[] header4D = new PdfPCell[]
		{
			new PdfPCell(new Phrase("% Keluasan Ditumbuhi", bold9)), 
			new PdfPCell(new Phrase("0 - 20", bold9)),
			new PdfPCell(new Phrase("21 - 40", bold9)),
			new PdfPCell(new Phrase("41 - 60", bold9)),
			new PdfPCell(new Phrase("61 - 80", bold9)),
			new PdfPCell(new Phrase("81 - 100", bold9)),
			new PdfPCell(new Phrase("JUMLAH", bold9))
		};
		
		header4D[0].setColspan(2);
		
		for (int i = 0; i < header4D.length; i++)
		{
			header4D[i].setBackgroundColor(BaseColor.LIGHT_GRAY);
			header4D[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
			header4D[i].setHorizontalAlignment(Element.ALIGN_CENTER);
			table4D.addCell(header4D[i]);
		}	
		
	
		for (int i = 0; i < format4D.size(); i++)
		{
			PostFellingReportElement element = format4D.get(i);
			double[] values = element.getValues();
			
			if (i % 2 == 0)
			{
				PdfPCell cell = new PdfPCell(new Phrase(element.getGroup(), size9));
				cell.setRowspan(2);
				table4D.addCell(cell);
			}

			table4D.addCell(new PdfPCell(new Phrase(element.getCriteria(), size9)));
			
			for (int j = 0; j < values.length; j++)
			{
				PdfPCell cell = new PdfPCell(new Phrase(df.format(values[j]), size9));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table4D.addCell(cell);
			}
		}		
		
		/*========= Compilation =========*/
		
		Paragraph[] titles = new Paragraph[headers.length];
		
		for (int i = 0; i < headers.length; i++)
		{
			titles[i] = new Paragraph("LAPORAN INVENTORI HUTAN SELEPAS TEBANGAN (POST-F) HUTAN SIMPAN "
					+ survey.getForestName().toUpperCase() + " KOMPATMEN " + survey.getComptBlockNo() + " TAHUN "
					+ survey.getYear() + "\n" + headers[i] + "\n\n", bold9);
			titles[i].setAlignment(Paragraph.ALIGN_CENTER);
		}
		Paragraph number_of_plot = new Paragraph("BILANGAN PETAK DINVENTORI/FAKTOR: " + survey.getPostFellingSurveyCards().size() + "\n\n", size9);
		number_of_plot.setAlignment(Paragraph.ALIGN_CENTER);
		
		Paragraph forestname = new Paragraph("INTEPRETASI POST-F BAGI KOMPARTMEN " + survey.getComptBlockNo() +" HUTAN " + survey.getForestName().toUpperCase() + ", " +
											survey.getDistrictName().toUpperCase() + ", " + 
											survey.getStateName().toUpperCase() + "\n\n", bold9);
		forestname.setAlignment(Paragraph.ALIGN_CENTER);
		
		
		
		Document document = new Document(PageSize.A4, 14.175f, 14.175f, 40f, 14.175f);

		PdfWriter.getInstance(document, new FileOutputStream(file));

		document.open();

		document.addTitle("LAPORAN INVENTORI HUTAN SELEPAS TEBANGAN (POST-F)");
		document.addSubject("Jabatan Perhutanan Negeri Sembilan");
		document.addKeywords("Post-F");
		document.addAuthor("JPNS");
		document.addCreator("JPNS");
		
		document.add(forestname);
		document.add(tableIntepretasi);
		
		
		document.newPage();
		document.add(titles[0]);
		document.add(number_of_plot);
		document.add(table1A);
		
		document.newPage();
		document.add(titles[1]);
		document.add(number_of_plot);
		document.add(table1B);
		
		document.newPage();
		document.add(titles[2]);
		document.add(number_of_plot);
		document.add(table2A);
		
		document.newPage();
		document.add(titles[3]);
		document.add(number_of_plot);
		document.add(table2B);
		
		document.newPage();
		document.add(titles[4]);
		document.add(number_of_plot);
		document.add(table2C);
		
		document.newPage();
		document.add(titles[5]);
		document.add(number_of_plot);
		document.add(table3A);
		
		document.newPage();
		document.add(titles[6]);
		document.add(number_of_plot);
		document.add(table3B);
		
		document.newPage();
		document.add(titles[7]);
		document.add(number_of_plot);
		document.add(table3C);
		
		document.newPage();
		document.add(titles[8]);
		document.add(number_of_plot);
		document.add(table4A);
		
		document.newPage();
		document.add(titles[9]);
		document.add(number_of_plot);
		document.add(table4B);
		
		document.newPage();
		document.add(titles[10]);
		document.add(number_of_plot);
		document.add(table4C);
		
		document.newPage();
		document.add(titles[11]);
		//document.add(number_of_plot);
		document.add(table4D);

		document.close();
	}
}