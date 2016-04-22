package it.polito.applied.asti.clan.pojo;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;

public class TicketRequest {
	
	@Id
	private String id;
	private String operatorId;
	private Set<String> ticketNumbers;
	private List<String> placesId;
	private Date requestDate;
	private boolean isGroup;
	private InfoTicketRequest info;
	private String tipology;
	private boolean acceptedFromAcl=false;
	private boolean someTicketIsCanceled=false;
	
	public TicketRequest(){
		
	}
	
	public TicketRequest(TicketRequestDTO dto, String operatorId){
		this.operatorId = operatorId;
		this.ticketNumbers = dto.getTicketsNumber();
		this.placesId = dto.getPlacesId();
		this.requestDate = new Date();
		this.info = dto.getInfo();
		this.tipology = dto.getTipology();
		if(dto.getTipology().equals("")){
			
		}else if(dto.getTipology().equals("")){
			
		}else if(dto.getTipology().equals("")){
			
		}else if(dto.getTipology().equals("")){
			
		}
		
		if(this.ticketNumbers!=null && this.ticketNumbers.size()==1){
			this.isGroup=false;
			this.info.setWithChildren(null);
			this.info.setWithElderly(null);
		}
		else if(this.ticketNumbers!=null && this.ticketNumbers.size()>1){
			this.isGroup=true;
			this.info.setAge(null);
			this.info.setGender(null);
		}		
	}
	
	
	public boolean isSomeTicketIsCanceled() {
		return someTicketIsCanceled;
	}

	public void setSomeTicketIsCanceled(boolean someTicketIsCanceled) {
		this.someTicketIsCanceled = someTicketIsCanceled;
	}

	public boolean getAcceptedFromAcl() {
		return  acceptedFromAcl;
	}
	public void  setAcceptedFromAcl(boolean acceptedFromAcl) {
		  this.acceptedFromAcl= acceptedFromAcl;
	}
	
	public String getTipology() {
		return tipology;
	}

	public void setRole(String t) {
		this.tipology = t;
	}

	public Set<String> getTicketNumbers() {
		return ticketNumbers;
	}
	public void setTicketNumbers(Set<String> ticketNumbers) {
		this.ticketNumbers = ticketNumbers;
	}
	public List<String> getPlacesId() {
		return placesId;
	}
	public void setPlacesId(List<String> placesId) {
		this.placesId = placesId;
	}
	public InfoTicketRequest getInfo() {
		return info;
	}
	public void setInfo(InfoTicketRequest info) {
		this.info = info;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public String getId() {
		return id;
	}
	public boolean isGroup() {
		return isGroup;
	}
	public void setGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	
	
	
}
