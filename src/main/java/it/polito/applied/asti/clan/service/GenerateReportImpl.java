/**
 * 
 */
package it.polito.applied.asti.clan.service;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.polito.applied.asti.clan.exception.ServiceUnaivalableException;
import it.polito.applied.asti.clan.pojo.InfoTicketRequest;
import it.polito.applied.asti.clan.pojo.Poi;
import it.polito.applied.asti.clan.pojo.Read;
import it.polito.applied.asti.clan.pojo.SensorLog;
import it.polito.applied.asti.clan.pojo.Ticket;
import it.polito.applied.asti.clan.pojo.TicketRequest;
import it.polito.applied.asti.clan.repository.PoiRepository;
import it.polito.applied.asti.clan.repository.ReadRepository;
import it.polito.applied.asti.clan.repository.SensorRepository;
import it.polito.applied.asti.clan.repository.TicketRepository;
import it.polito.applied.asti.clan.repository.TicketRequestRepository;

/**
 * @author m04ph3u5
 *
 */
@Service
public class GenerateReportImpl implements GenerateReport{

	//Delimiter used in CSV file
	private final String NEW_LINE_SEPARATOR = "\n";

	private final String[] ticketHeader={"IdTicket", "Data_emissione", "Data_primo_utilizzo", "Data_scadenza",  
			"IdGruppo", "Provenienza","Genere", "Età", "Coppia", "Famiglia", "Scolaresca", "Numero_Siti", 
			"Siti"};
	
	private final String[] entranceHeader={"IdTicket", "Sito", "Accettato", "Errore", "Data transito", "Data registrazione"};

	private final String[] environmentHeader={"Sito", "Sonda", "Data", "Temperatura (°C)", "Umidità (%)"};
	
	@Value("${report.maxInstance}")
	private int MAX_INSTANCE;
	
	@Value("${report.userRegion}")
	private String[] REGIONS;

	@Autowired
	private TicketRequestRepository ticketRequestRepo;
	
	@Autowired
	private TicketRepository ticketRepo;
	
	@Autowired
	private PoiRepository poiRepo;
	
	@Autowired
	private ReadRepository readRepo;
	
	@Autowired
	private SensorRepository sensorRepo;
	
	private final String pattern = "dd/MM/yyyy";
	private SimpleDateFormat format;
	private Map<String, String> poiNames;

	private int instance;
	private Object lock;
	
	@PostConstruct
	private void initialize(){
		lock = new Object();
		format = new SimpleDateFormat(pattern);
		List<Poi> poi = poiRepo.findAllPlaceCustom();
		poiNames = new HashMap<String, String>();
		if(poi!=null){
			for(Poi p : poi)
				poiNames.put(p.getIdSite(), p.getName());
		}
	}

	/* (non-Javadoc)
	 * @see it.polito.applied.asti.clan.service.GenerateReport#ticketsReport(java.util.Date, java.util.Date)
	 */
	@Override
	public void ticketsReport(Date start, Date end, HttpServletResponse response) throws ServiceUnaivalableException, IOException {

		if(!increment()){
			response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "Servizio momentaneamente non disponibile");
			return;
		}

		response.setContentType("text/csv");
		response.setHeader( "Content-Disposition", "filename=ticketsReport-" + format.format(new Date()) +".csv" );
		CSVPrinter csvFilePrinter = null;
		OutputStreamWriter outputwriter = null;
		//Create the CSVFormat object with "\n" as a record delimiter
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);

		List<TicketRequest> requests = ticketRequestRepo.find(start, end);
		
		try {
			
			OutputStream stream =  response.getOutputStream();
			OutputStream bos = new BufferedOutputStream(stream); 
			outputwriter = new OutputStreamWriter(bos);
			csvFilePrinter = new CSVPrinter(outputwriter, csvFileFormat);
			
			if(requests==null || requests.isEmpty()){
				csvFilePrinter.printRecord("Non sono stati emessi biglietti per il periodo selezionato ("+format.format(start)+"-"+format.format(end)+").");
				return;
			}

			//Write csv header row
			csvFilePrinter.printRecord((Object[])ticketHeader);
			
			for(TicketRequest r : requests){
				List<Ticket> tickets = ticketRepo.findInList(r.getTicketNumbers(),start, end);
				if(tickets==null || tickets.isEmpty())
					continue;
				for(Ticket t : tickets){
					List<Object> ticketRecord = new ArrayList<Object>();
					//idTicket
					ticketRecord.add(t.getIdTicket());
					//dataEmissione
					ticketRecord.add(t.getEmissionDate());
					//data prima validazione
					ticketRecord.add(t.getStartDate());
					//data scadenza
					ticketRecord.add(t.getEndDate());
					//Singolo o idGruppo
					if(r.isGroup())
						ticketRecord.add(r.getId());
					else
						ticketRecord.add("Singolo");
					//Informazioni statisctiche.
					InfoTicketRequest info = r.getInfo();
					if(info==null){
						//Provenienza
						ticketRecord.add("-");
						//Genere
						ticketRecord.add("-");
						//Età
						ticketRecord.add("-");
						//Coppia
						ticketRecord.add("-");
						//Famiglia
						ticketRecord.add("-");
						//Scolaresca
						ticketRecord.add("-");

					}else{
						//Provenienza
						if(info.getRegion()<0 || info.getRegion()>REGIONS.length)
							ticketRecord.add("N.D.");
						else
							ticketRecord.add(REGIONS[info.getRegion()]);
						//Genere
						if(info.getGender()!=null)
							ticketRecord.add(info.getGender());
						else
							ticketRecord.add("-");
						//Età
						if(info.getAge()!=null)
							ticketRecord.add(info.getAge());
						else
							ticketRecord.add("-");
						//Coppia
						ticketRecord.add(info.getCouple());
						//Famiglia
						ticketRecord.add(info.getFamily());
						//Scolaresca
						ticketRecord.add(info.getSchoolGroup());
					}
					//NUMERO SITI
					ticketRecord.add(r.getPlacesId().size());
					
					//Lista siti
					StringBuilder sb = new StringBuilder();
					for(int i=0; i<r.getPlacesId().size(); i++){
						if(poiNames.containsKey(r.getPlacesId().get(i)))
							sb.append(poiNames.get(r.getPlacesId().get(i)));
						if(i<r.getPlacesId().size()-1)
							sb.append(" - ");
					}
					ticketRecord.add(sb.toString());
							
					csvFilePrinter.printRecord(ticketRecord);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IOException: "+e.getMessage());
		} finally{
			decrement();
			try {
				outputwriter.flush();
				outputwriter.close();
				csvFilePrinter.close();
			} catch (IOException e) {
				System.out.println("Closing Exception: "+e.getMessage());
			}

		}
	}

	/* (non-Javadoc)
	 * @see it.polito.applied.asti.clan.service.GenerateReport#entrancesReport(java.util.Date, java.util.Date, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void entrancesReport(Date start, Date end, HttpServletResponse response)
			throws ServiceUnaivalableException, IOException {
		
		if(!increment()){
			response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "Servizio momentaneamente non disponibile");
			return;
		}		
		
		response.setContentType("text/csv");
		response.setHeader( "Content-Disposition", "filename=entrancesReport-" + format.format(new Date()) +".csv" );
		CSVPrinter csvFilePrinter = null;
		OutputStreamWriter outputwriter = null;
		//Create the CSVFormat object with "\n" as a record delimiter
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
		
		List<Read> reads = readRepo.find(start, end);
		try {
			
			OutputStream stream =  response.getOutputStream();
			OutputStream bos = new BufferedOutputStream(stream); 
			outputwriter = new OutputStreamWriter(bos);
			csvFilePrinter = new CSVPrinter(outputwriter, csvFileFormat);
			
			if(reads==null || reads.isEmpty()){
				csvFilePrinter.printRecord("Non ci sono stati ingressi durante il periodo selezionato ("+format.format(start)+"-"+format.format(end)+").");
				return;
			}
			
			//Write csv header row
			csvFilePrinter.printRecord((Object[])entranceHeader);
			
			for(Read r : reads){
				List<Object> entranceRecord = new ArrayList<Object>();
				
				//ID TICKET
				entranceRecord.add(r.getIdTicket());
				
				//SITO
				if(poiNames.containsKey(r.getIdSite()))
					entranceRecord.add(poiNames.get(r.getIdSite()));
				else
					entranceRecord.add("-");
				//ACCETTATO
				entranceRecord.add(r.getIsAccepted());
				//MESSAGGIO DI ERRORE
				entranceRecord.add(r.getDesError());
				//DATA TRANSITO
				entranceRecord.add(r.getDtaTransit());
				//DATA DI REGISTRAZIONE SUL SERVER
				entranceRecord.add(r.getDateOnServer());
				csvFilePrinter.printRecord(entranceRecord);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IOException: "+e.getMessage());
		} finally{
			decrement();
			try {
				outputwriter.flush();
				outputwriter.close();
				csvFilePrinter.close();
			} catch (IOException e) {
				System.out.println("Closing Exception: "+e.getMessage());
			}

		}

	}
	
	/* (non-Javadoc)
	 * @see it.polito.applied.asti.clan.service.GenerateReport#environmentReport(java.util.Date, java.util.Date, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void environmentReport(Date start, Date end, HttpServletResponse response)
			throws ServiceUnaivalableException, IOException {
		
		if(!increment()){
			response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "Servizio momentaneamente non disponibile");
			return;
		}	
		
		response.setContentType("text/csv");
		response.setHeader( "Content-Disposition", "filename=environmentReport-" + format.format(new Date()) +".csv" );
		CSVPrinter csvFilePrinter = null;
		OutputStreamWriter outputwriter = null;
		//Create the CSVFormat object with "\n" as a record delimiter
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
		
		List<SensorLog> logs = sensorRepo.find(start, end);
		
		try {
			
			OutputStream stream =  response.getOutputStream();
			OutputStream bos = new BufferedOutputStream(stream); 
			outputwriter = new OutputStreamWriter(bos);
			csvFilePrinter = new CSVPrinter(outputwriter, csvFileFormat);
			
			if(logs==null || logs.isEmpty()){
				csvFilePrinter.printRecord("Non ci sono dati rilevati dalle sonde ambientali per il periodo selezionato ("+format.format(start)+"-"+format.format(end)+").");
				return;
			}
			
			//Write csv header row
			csvFilePrinter.printRecord((Object[])environmentHeader);
			
			for(SensorLog s : logs){
				List<Object> environmentRecord = new ArrayList<Object>();
				
				//SITO
				if(poiNames.containsKey(s.getIdSite()))
					environmentRecord.add(poiNames.get(s.getIdSite()));
				else
					environmentRecord.add("-");
				//SONDA
				environmentRecord.add(s.getIdSonda());
				//DATA
				environmentRecord.add(s.getTimestamp());
				//TEMPERATURA
				environmentRecord.add(s.getValTemp());
				//UMIDITÀ
				environmentRecord.add(s.getValHum());
				
				csvFilePrinter.printRecord(environmentRecord);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IOException: "+e.getMessage());
		} finally{
			decrement();
			try {
				outputwriter.flush();
				outputwriter.close();
				csvFilePrinter.close();
			} catch (IOException e) {
				System.out.println("Closing Exception: "+e.getMessage());
			}

		}
	}

	private boolean increment(){
		synchronized (lock) {
			if(instance<MAX_INSTANCE){
				instance++;
				return true;
			}else
				return false;
		}
	}

	private void decrement(){
		synchronized (lock) {
			if(instance>0)
				instance--;
		}
	}

}
