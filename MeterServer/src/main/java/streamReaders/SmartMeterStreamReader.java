package streamReaders;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.LoggerFactory;

import socketserver.SmartMeterDataEnum;
import socketserver.SmartMeterDataMap;

/**
 * 
 * @author Steerpike
 *
 */
public class SmartMeterStreamReader extends StreamReader {
	private final Integer fWorkerID;
	
	
	//acknowledge
	private static final char ACK = 0x06;
	
	//Repeat request / Negative acknowledge
	private static final char NACK = 0x15;
	
	//end character "!"
	private static final char END = 0x21;
	
	// start character "/"
	private static final char START = 0x2f;
	
	//Carriage return
	private static final char CR = 0x0D;
	
	//linefeed
	private static final char LF = 0x0A;
	
	//frame start character
	private static final char STX = 0x02;
	
	//End Character
	private static final char ETX = 0x03;
	
	
	/**
	 * Creates a new SmartMeterStreamReader instance, that will read in formatted data on aDataInputStream, read in the data, perform nessisary data
	 * validation,  send any messages back to the SmartMeter over aOutputStream, and then put each line of data read in on aSmartMeterData list, which 
	 * will then be queued later in the server to be processed.
	 * @param aDataInputStream
	 * @param aOutputStream
	 * @param aSmartMeterData the data map that the data will
	 */
	public SmartMeterStreamReader(DataInputStream aDataInputStream, DataOutputStream aOutputStream,
			ArrayList<SmartMeterDataMap> aSmartMeterData, Integer aWorkerID) {
		super(aDataInputStream, aOutputStream, aSmartMeterData);
		super.fLogger = LoggerFactory.getLogger(SmartMeterStreamReader.class);
		fWorkerID = aWorkerID;
	}

	@Override
	/**
	 * Parses the incoming data stream in the SmartMeter specification.
	 * @return true if there was no error.
	 */
	public Boolean parse() throws IOException {
		fLogger.debug("LineStreamReader Started on Thread " + fWorkerID);

		
		String lSmartMeterID = readInIdentificationMessage();
		//failed to read in the data.
		if(lSmartMeterID.isEmpty()) {
			return false;
		}
		
		
		sendAcknolwedge();
		
		//now we do the bulk of the reading of the data
		ArrayList<Byte> lBytes = new ArrayList<Byte>();
		Integer lMessageFailCount = 0;
		char lCalcedCheckChar, lSentCheckChar;
		
		
		
		//try receiving the data only 5 times.		
		do {
			if(lMessageFailCount>0) sendNegativeAcknolwedge();
			 lSentCheckChar = readDataMessageIntoByteArray(lBytes);
			 lCalcedCheckChar = calculateBlockCheckChar(lBytes);
			 lMessageFailCount++;
		} while(lSentCheckChar != lCalcedCheckChar && lMessageFailCount < 5);
		
		//we have retried 5 times to get the data, and it has failed. return false from this.
		if(lMessageFailCount >= 5) return false;
		
		
		//now we have the bytes and they are good, so we have to parse them and unpack.
		
		//Dataset part.
		
		StringBuilder sb = new StringBuilder();
		for(Byte b : lBytes) {
			
			//Exclamation mark or no character , so we are done
			if(b == END || b ==0) { 
				break;
			}
			
			if( ((char) b.byteValue() == LF || (char) b.byteValue() == CR) && sb.length() > 0) {
				
				fLogger.debug(String.format("SmartMeterStreamReader %d processes a new line: Meter %s :  %s", fWorkerID), lSmartMeterID,sb.toString());
				

				String[] lValues = sb.toString().split(",");
				SmartMeterDataMap lTempDataMap = new SmartMeterDataMap();
				try {
				
				//add  each corresponding value to the SmartMeterDataEnum
				//the protocol is in the documentation.
					lTempDataMap.setValueAt(SmartMeterDataEnum.METER_ID, lSmartMeterID);
					lTempDataMap.setValueAt(SmartMeterDataEnum.DATE_TIME, lValues[0]);
					lTempDataMap.setValueAt(SmartMeterDataEnum.KILO_WATT_HOURS, lValues[1]);
					lTempDataMap.setValueAt(SmartMeterDataEnum.KILO_WATT_TOTAL, lValues[2]);
					lTempDataMap.setValueAt(SmartMeterDataEnum.KILO_WATT_AMPS_TOTAL, lValues[3]);
					lTempDataMap.setValueAt(SmartMeterDataEnum.KILO_VOLT_AMP_REACTIVE_TOTAL, lValues[4]);
					lTempDataMap.setValueAt(SmartMeterDataEnum.PHASE_1_CURRENT, lValues[5]);
					lTempDataMap.setValueAt(SmartMeterDataEnum.PHASE_2_CURRENT, lValues[6]);
					lTempDataMap.setValueAt(SmartMeterDataEnum.PHASE_3_CURRENT, lValues[7]);
					lTempDataMap.setValueAt(SmartMeterDataEnum.PHASE_1_VOLTAGE, lValues[8]);
					lTempDataMap.setValueAt(SmartMeterDataEnum.PHASE_2_VOLTAGE, lValues[9]);
					lTempDataMap.setValueAt(SmartMeterDataEnum.PHASE_3_VOLTAGE, lValues[10]);
					lTempDataMap.setValueAt(SmartMeterDataEnum.PHASE_3_VOLTAGE, lValues[11]);
				} catch(IndexOutOfBoundsException e){
					fLogger.error(String.format("SmartMeterStreamReader %d processes an incomplete line: Meter %s :  %s", fWorkerID), lSmartMeterID,sb.toString());
					lTempDataMap.clear();
					lTempDataMap = null;
				}

				//add line reading to the list of readings if it was successfully parsed.
				if(lTempDataMap != null) {
					this.fSmartMeterData.add(lTempDataMap);
				}
				//want to reuse the string builder, so this clears it.
				sb.setLength(0);
				  
			} else {
				//else add this byte as a char into the 
				sb.append((char)b.byteValue());
			}
		}
		

		
		super.fOutputStream.writeBytes("Java Server Worker " + fWorkerID + " sent " + fSmartMeterData.size() +" lines of Data!");
		super.fOutputStream.flush();
		super.fOutputStream.close();
		System.out.println("Finished!");


		return true;
		
	}
	
	
	/**
	 * Calculates the local Byte Check Character (BCC) from the received data transmission.
	 * @param lBytes
	 * @return the calculated character from the character check.
	 */
	private char calculateBlockCheckChar(ArrayList<Byte> aBytes) {
	
		byte lResult = 0;
		for(Byte b: aBytes) {
			lResult = (byte) ((lResult + b.byteValue()) & 0xFF);
		}
		
		// TODO Auto-generated method stub
		
		return (char) (((lResult^ 0xFF) + 1) & 0xFF);
	}

	/**
	 * send an ACK messages through to the SmartMeter
	 * @throws IOException  
	 */
	private void sendAcknolwedge() throws IOException{
		fOutputStream.write(ACK);
		
	}
	
	
	/**
	 * send an NACK messages through to the SmartMeter
	 * @throws IOException  
	 */
	private void sendNegativeAcknolwedge() throws IOException{
		fOutputStream.write(NACK);
		
	}
	
	/**
	 * Attempt to read the Identification Message from the Smart meter.
	 * The Identification message
	 * TODO rebuild this better. probably easier to read all bytes first (look for the LF character) and then parse it.
	 * @return the ID of the smart meter, or null if there was an error.
	 * @throws IOException
	 */
	private String readInIdentificationMessage() throws IOException {
		fLogger.debug("LineStreamReader Started on Thread " + fWorkerID);

		boolean finsihedReadingData = false;
		boolean seenStartCharacter = false;
		Integer lNextByte;
		StringBuilder sb = new StringBuilder();	
		
		
		while(!finsihedReadingData) {
			lNextByte = fInputStream.read();
			//first need to read the ID message, which is: start character, address, end character, carriage return, line feed.			
			
			if(!(lNextByte > 0)) {
				fLogger.error("SmartMeterStreamReader  " + fWorkerID + " has abnormally ended while attempting to read the Identification message" );
				//TODO Potentially throw a new exception here
				return null;
			}
			
			//lets make this easy for me to read
			// this is the char of this current byte
			char lChar =(char) lNextByte.byteValue();
			
			//if these are the endline characters, ignore them.
			if(seenStartCharacter) {
				// if we have not seen the last three characters
				if(!(lChar == END | lChar == CR | lChar == LF))	{
					sb.append(lChar);
					
				}
			} else{
				if(lChar == START) {
					seenStartCharacter = true;
				} else {
					fLogger.error("SmartMeterStreamReader  " + fWorkerID + ". Malformed Indentifcation packet" );
					//TODO Potentially throw a new exception here
					return null;
				}
				
			}		
					
		}
		
		//Id should be in the form of ABCDabcd 1234
		//remove the leading 0's
		while(sb.charAt(0) == '0'){
			sb.deleteCharAt(0);
		}
		
		return sb.toString();
		
	}
	
	
/**
 * Reads bytes from the input stream, and stores them in aArray.
 * This is done as we need to perform the redundancy on the bytes and match it to the received one
 * 
 * @param aArray
 * @return the received Block Check Character (BCC) from the smart meter.
 * @throws IOException 
 */
 private char readDataMessageIntoByteArray(ArrayList<Byte> aArray) throws IOException {
	 aArray.clear();
	 Integer lNextByte = -1;
	 while(   (lNextByte = fInputStream.read()) > 0) {
		 //TODO might gave to remove the STX from this list (from the specification) i need to clarify this.
		//need to add all the bytes to the list, including EXT 
		 aArray.add(lNextByte.byteValue());
		 
		 if(lNextByte.byteValue() == ETX) {
			 //return the next character, which is the block check character.
			 return (char) fInputStream.readByte();
			
		}
		 
	 }	 
	return 0;	 
 }
	
	
}
