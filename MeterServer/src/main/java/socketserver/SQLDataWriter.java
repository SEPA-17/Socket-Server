package socketserver;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SQLDataWriter {
	ArrayList<SmartMeterDataMap> fSmartMeterData;

	public SQLDataWriter(ArrayList<SmartMeterDataMap> aSmartMeterData) {
		// TODO Auto-generated constructor stub
		this.fSmartMeterData = aSmartMeterData;
	}

	public void run() throws IOException{
		//do the part of connecting to the SQLDatabase.
		//For now, just write to a file.
		
			 FileWriter fw =new FileWriter("output.txt");
			 //there are possible many entries in the datamap
			 //each of these need to be written to file.
			
			 for(SmartMeterDataMap smdm : fSmartMeterData) {
				 StringBuilder sb = new StringBuilder();
				 
				 //for each unique value in the Enum
				for(SmartMeterDataEnum s : SmartMeterDataEnum.values()) {
					if(smdm.getValueAt(s) != null) {
						sb.append(s.toString());
						sb.append(": ");
						sb.append(smdm.getValueAt(s));
						sb.append(",");
					}
					sb.append("\n");
					fw.write(sb.toString());
					sb.setLength(0);
					
				}
			 }
			 
			 fw.close();
			 
			
		
		
		
		
	}

}
