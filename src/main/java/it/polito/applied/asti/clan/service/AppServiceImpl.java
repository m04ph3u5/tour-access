package it.polito.applied.asti.clan.service;

import it.polito.applied.asti.clan.exception.BadRequestException;
import it.polito.applied.asti.clan.pojo.Comment;
import it.polito.applied.asti.clan.pojo.CommentDTO;
import it.polito.applied.asti.clan.pojo.CommentsPage;
import it.polito.applied.asti.clan.pojo.CommentsRequest;
import it.polito.applied.asti.clan.pojo.Log;
import it.polito.applied.asti.clan.pojo.LogDTO;
import it.polito.applied.asti.clan.pojo.PathInfo;
import it.polito.applied.asti.clan.pojo.Ticket;
import it.polito.applied.asti.clan.pojo.VersionZip;
import it.polito.applied.asti.clan.repository.CommentRepository;
import it.polito.applied.asti.clan.repository.LogRepository;
import it.polito.applied.asti.clan.repository.PathInfoRepository;
import it.polito.applied.asti.clan.repository.TicketRepository;
import it.polito.applied.asti.clan.repository.VersionZipRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AppServiceImpl implements AppService{
	
	@Value("${status.canceled.id}")
	private String CANCELED;
	@Value("${status.validated.id}")
	private String VALIDATED;
	@Value("${status.released.id}")
	private String RELEASED;
	
	/*Grandezza pagina commenti da restituire all'app mobile*/
	private final int DIM_PAGE = 10;
	
	
	@Autowired
	private VersionZipRepository versionRepo;
	
	@Autowired
	private TicketRepository ticketRepo;
	
	@Autowired
	private CommentRepository commentRepo;
	
	@Autowired
	private PathInfoRepository pathInfoRepo;
	
	@Autowired
	private LogRepository logRepo;
	
	@Autowired
	private UtilAverageTask avgTask;
	
	
	@Override
	public VersionZip getVersion() {
		Sort s = new Sort(new Sort.Order(Sort.Direction.DESC, "id"));
		return versionRepo.findAll(s).get(0);
	}

	@Override
	public boolean checkTicket(String ticket) {
		List<Ticket> tickets = ticketRepo.findByIdTicket(ticket);
		if(tickets!=null && tickets.size()>0){
			if(tickets.get(0).getStatus().equals(CANCELED))
				return false;
			else
				return true;
		}
		else
			return false;
	}

	@Override
	public CommentsPage getComments(CommentsRequest request) throws BadRequestException {
		PathInfo info = pathInfoRepo.findByIdPath(request.getIdPath());
		CommentsPage page = new CommentsPage();

		if(info==null){
			page.setNumComments(0);
			page.setAvgRating(0);
			page.setListOfComments(new ArrayList<CommentDTO>());
			return page;
		}
		page.setNumComments(info.getNumComments());
		float rating = roundAvg(info.getAvgRating());
		page.setAvgRating(rating);
		
		int	numTotPage = page.getNumComments()/DIM_PAGE;
		if((page.getNumComments()%DIM_PAGE)!=0)
			numTotPage++;

		if(request.getPage()>=numTotPage)
			throw new BadRequestException();
		
		if(request.getPage()==numTotPage-1)
			page.setNextPage(-1);
		else
			page.setNextPage(request.getPage()+1);
		
		List<Comment> comments = commentRepo.findByIdPathOrderByRealdateDesc(request.getIdPath());
		List<CommentDTO> listOfComments = new ArrayList<CommentDTO>();
		int i=(request.getPage()*DIM_PAGE);
		int j = 0;
		while(j<DIM_PAGE && i<comments.size()){
			listOfComments.add(new CommentDTO(comments.get(i)));
			i++;
			j++;
		}
		
		page.setListOfComments(listOfComments);
		return page;
	}



	@Override
	public void postComment(List<LogDTO> logComment) throws BadRequestException {
		List<Comment> comments = new ArrayList<Comment>();
		
		for(LogDTO dto : logComment){
			if(dto.getArgs()==null || dto.getArgs().getComment()==null)
				throw new BadRequestException();
			
			Comment c = new Comment(dto.getArgs().getComment(), dto.getTimestamp(), dto.getDevice_id(), dto.getArgs().getId_path(), dto.getArgs().getTicket_contents());
			c = commentRepo.setComment(c);
			comments.add(c);
			avgTask.queue(c.getIdPath());
		}
		postCommentLog(comments, logComment);
	}

	private void postCommentLog(List<Comment> comments, List<LogDTO> logComment) {
		List<Log> logs = new ArrayList<Log>();
		for(int i=0; i<logComment.size(); i++){
			logs.add(new Log(logComment.get(i), comments.get(i).getId()));
		}
		
		logRepo.save(logs);
	}

	@Override
	public void postLog(List<LogDTO> logsDTO) {
		List<Log> logs = new ArrayList<Log>();
		for(int i=0; i<logsDTO.size(); i++){
			logs.add(new Log(logsDTO.get(i)));
		}
		logRepo.save(logs);
	}
	
	private float roundAvg(float avgRating) {
		int intPart = (int)avgRating;
		float floatPart = avgRating - intPart;
		
		if(floatPart<0.25)
			floatPart = 0;
		else if(floatPart>=0.25 && floatPart<0.75)
			floatPart = 0.5f;
		else
			floatPart = 1;
		
		return intPart+floatPart;
	}

}
