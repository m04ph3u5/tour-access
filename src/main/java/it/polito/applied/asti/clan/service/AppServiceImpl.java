package it.polito.applied.asti.clan.service;

import it.polito.applied.asti.clan.exception.BadRequestException;
import it.polito.applied.asti.clan.exception.NotFoundException;
import it.polito.applied.asti.clan.pojo.Comment;
import it.polito.applied.asti.clan.pojo.CommentBundle;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	
	/*Valore utilizzato per il calcolo della  media di un path.
	 * Corrisponde al quantile della distribuzione normale per ottenere un'affidabilità dell'80%*/
	private final double ZETA_ALFA = 0.845;
	
	/*Numero di possibili voti diversi che arriveranno dall'app mobile
	 * 0.5 - 1 - 1.5 - 2 - 2.5 - 3 - 3.5 - 4 - 4.5 - 5*/
	private final int K = 10;
	private final float MIN = 0.5f;
	private final int MAX = 5;
	private final float STEP = 0.5f;
	
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
	public CommentsPage getComments(CommentsRequest request) throws NotFoundException, BadRequestException {
		PathInfo info = pathInfoRepo.findByIdPath(request.getIdPath());
		if(info==null)
			throw new NotFoundException();
		
		CommentsPage page = new CommentsPage();
		page.setNumComments(info.getNumComments());
		page.setAvgRating(info.getAvgRating());
		
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
		Map<Integer ,PathInfo> paths = new HashMap<Integer, PathInfo>();
		
		for(LogDTO dto : logComment){
			if(dto.getArgs()==null || dto.getArgs().getComment()==null)
				throw new BadRequestException();
			PathInfo info;
			if(paths.containsKey(dto.getArgs().getId_path()))
				info = paths.get(dto.getArgs().getId_path());
			else
				info = pathInfoRepo.findByIdPath(dto.getArgs().getId_path());
			
			Comment c = new Comment(dto.getArgs().getComment(), dto.getTimestamp(), dto.getDevice_id());
			CommentBundle cB = commentRepo.setComment(c);
			if(cB.isNew()){
				c = cB.getComment();
				info = addRateToPathInfo(info, c);
			}else{
				c.setId(cB.getComment().getId());
				info = updateRateToPathInfo(info, cB.getComment(), c);
			}
			paths.put(info.getIdPath() ,info);
		}
		
		Set<Integer> set = paths.keySet();
		for(Integer i : set){
			PathInfo info = paths.get(i);
			info.setAvgRating(calculateAverage(info));
		}
		
		//TODO upsert path info
		
		postCommentLog(comments, logComment);
		
	}
	
	private float calculateAverage(PathInfo info) {
		double a=0, b=0, c=0 ,d=0;
		int numVotes = 0;
		Map<Float, Integer> votes = info.getVotes();
		for(float i = MIN; i<=MAX; i+=STEP){
			if(!votes.containsKey(i))
				votes.put(i, 0);
			numVotes+=votes.get(i);
		}
		Set<Float> set = votes.keySet();
		
		for(Float f : set){
			a+=f*((votes.get(f)+1)/(numVotes+K));
			b+=f*f*((votes.get(f)+1)/(numVotes+K));
		}
		c = a*a;
		d = numVotes+K+1;
		return (float)(a-ZETA_ALFA*Math.sqrt((b-c)/d));
	}

	private PathInfo addRateToPathInfo(PathInfo info, Comment c){
		if(info==null){
			info = new PathInfo();
			info.setIdPath(c.getIdPath());
			Map<Float,Integer> votes = new HashMap<Float, Integer>();
			if(c.getRating()>0)
				votes.put(c.getRating(), 1);
			info.setVotes(votes);
			info.setNumComments(1);
		}else{
			if(c.getRating()>0){
				Map<Float, Integer> votes = info.getVotes();
				int n;
				if(votes.containsKey(c.getRating())){
					n = votes.get(c.getRating());
				}else{
					n = 0;
				}
				votes.put(c.getRating(), n+1);
				info.setVotes(votes);
			}
			info.incrementNumComments();
		}
		return info;
	}
	
	private PathInfo updateRateToPathInfo(PathInfo info, Comment oldComment, Comment newComment) throws BadRequestException{
		if(info==null)
			throw new BadRequestException("INCOERENZA DB -> PATH INFO NULL");
		if(newComment.getRating()>0){
			Map<Float, Integer> votes = info.getVotes();
			int n = votes.get(oldComment.getRating());
			if(n>0)
				votes.put(oldComment.getRating(), n-1);
			n=0;
			if(votes.containsKey(newComment.getRating())){
				n = votes.get(newComment.getRating());
			}
			votes.put(newComment.getRating(), n+1);
			info.setVotes(votes);
		}
		return info;
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

}
