package ijw.utils.framework;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import ijw.config.framework.Base;

public class TestResult extends Base{

	public static String ProjectName;
	public static int Total_Test_runs;
	public static int Passed;
	public static int Failed;
	public static int Skipped;
	public static String Automation_Effort;
	public static String BrowserName;
	public static String ApplicationURL;
	public static String ResultPath;
	
	public static void updateTable() {

		try {

			System.out.println("Updating ROI table");

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			LocalDateTime now = LocalDateTime.now();
			String date = dtf.format(now);

			FileInputStream input = new FileInputStream(System.getProperty("user.dir") + "/src/test/java/ijw/properties/framework/data.properties");
			prop.load(input);
			ProjectName = prop.getProperty("ProjectName");
			ApplicationURL = prop.getProperty("ApplicationUrl");
			BrowserName = prop.getProperty("browser");
			ResultPath =prop.getProperty("Reportpath");

			StringBuilder msg = new StringBuilder();
		//	 msg.append("<html><style>table, th, td { table { border: 1px solid black; width: 100%;}th, td {border: 1px solid black;text-align: left; padding: 8px;}th { background-color: #4CAF50; color: white;}</style>");

			msg.append("<html><head><style>" + 
			"h1{text-align: center;}" +
			"body{background-color: #FFA500;}"+
			"th{background-color: #000000;}" + 
			"td{background-color: #EDEEF0;}"+
			"table,th,td{ border: 1px solid black; text-align: center;}" +
			"</style></head>"+
			"<body><h1>IJW Test Automation Report</h1>");
			
			msg.append("<table><tr>" +
			"<th><b>Date_of_Execution</b></th>" + 
			"<th><b>Project_Name</b></th>"+
			" <th><b>Application_URL</b></th>"+ 
			"<th><b>Browser_Name</b></th>" +
			"<th><b>Total_Test_runs</b></th>" + 
			"<th><b>Passed</b></th>"+ 
			"<th><b>Failed</b></th>" + 
			"<th><b>Skipped</b></th>" + 
			"<th><b>Automation_Effort</b></th>" + 
			"<th><b>Result_Path</b></th>"+
			" </tr>");
			
			msg.append("<tr>" +
			" <td>" + date + "</td>" +
			" <td>" + ProjectName + "</td>" + 
			" <td>" + ApplicationURL + "</td>" +
			" <td>" + BrowserName + "</td>" + 
			" <td>"+ Total_Test_runs + "</td>" + 
			" <td>" + Passed + "</td>" + 
			" <td>" + Failed + "</td>" +
			" <td>" + Skipped + "</td>" + 
			" <td>"+ Automation_Effort + "</td>" +
			" <td>" + ResultPath + "</td></tr>");
			msg.append("</table></body></html>");
			EmailReport.emailReport(msg.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
