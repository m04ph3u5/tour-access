package it.polito.applied.asti.clan.service;

import it.polito.applied.asti.clan.exception.BadRequestException;
import it.polito.applied.asti.clan.pojo.AppAccessInstallSeries;
import it.polito.applied.asti.clan.pojo.AppInfo;
import it.polito.applied.asti.clan.pojo.Comment;
import it.polito.applied.asti.clan.pojo.CommentDTO;
import it.polito.applied.asti.clan.pojo.CommentsPage;
import it.polito.applied.asti.clan.pojo.CommentsRequest;
import it.polito.applied.asti.clan.pojo.Log;
import it.polito.applied.asti.clan.pojo.LogDTO;
import it.polito.applied.asti.clan.pojo.LogSeriesInfo;
import it.polito.applied.asti.clan.pojo.LogType;
import it.polito.applied.asti.clan.pojo.PathInfo;
import it.polito.applied.asti.clan.pojo.Ticket;
import it.polito.applied.asti.clan.pojo.TotAggregate;
import it.polito.applied.asti.clan.pojo.VersionZip;
import it.polito.applied.asti.clan.repository.CommentRepository;
import it.polito.applied.asti.clan.repository.LogRepository;
import it.polito.applied.asti.clan.repository.PathInfoRepository;
import it.polito.applied.asti.clan.repository.TicketRepository;
import it.polito.applied.asti.clan.repository.VersionZipRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
			Log l = new Log(logsDTO.get(i));
			if(l.getLogType().equals(LogType.Install)){
				if(!logRepo.isDeviceInstalled(l.getDeviceId()))
					logs.add(l);
			}else
				logs.add(l);

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

	@Override
	public LogSeriesInfo getLogInfo(Date start, Date end) {
		Map<String, Long> dates = logRepo.getDateSeries(start, end);
		Map<String, Long> devices = logRepo.getDeviceSeries(start, end);
		return new LogSeriesInfo(dates, devices);
	}

	@Override
	public long getAccess(Date date) {
		return logRepo.countNumberAccessFromDate(date);
	}

	@Override
	public long getInstallation(Date date) {
		return logRepo.countNumberInstallationFromDate(date);
	}

	@Override
	public long getDevices(Date date) {
		return logRepo.distinctDevicesFromDate(date);
	}

	@Override
	public Map<Date, AppAccessInstallSeries> getAppInstallAccessSeries(Date start, Date end) {
		TreeMap<Date, AppAccessInstallSeries> map = new TreeMap<Date, AppAccessInstallSeries>();
		List<TotAggregate> access = logRepo.getAccessGrouped(start, end);
		List<TotAggregate> install = logRepo.getInstallGrouped(start, end);
		Calendar c = Calendar.getInstance();
		c.setTime(end);
		c.set(Calendar.HOUR_OF_DAY,23);
		c.set(Calendar.MINUTE,59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		end = c.getTime();

		c.set(Calendar.YEAR, 2015);
		c.set(Calendar.MONTH, 11);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		Date first = c.getTime();
		
		c.setTime(start);

		if(start.before(first)){
			c.set(Calendar.YEAR, 2015);
			c.set(Calendar.MONTH, 11);
			c.set(Calendar.DAY_OF_MONTH, 1);
		}
		
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		start = c.getTime();

		while(start.before(end)){
			map.put(start, new AppAccessInstallSeries());
			c.setTime(start);
			c.set(Calendar.HOUR_OF_DAY,0);
			c.set(Calendar.MINUTE,0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			c.add(Calendar.DAY_OF_MONTH, 1);
			start = c.getTime();
		}
		
		for(TotAggregate a : access){
			Date d = a.getDate();
			c.setTime(d);
			c.set(Calendar.HOUR_OF_DAY,0);
			c.set(Calendar.MINUTE,0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);

			d = c.getTime();
			AppAccessInstallSeries tAS = map.get(d);
			if(tAS!=null){
					tAS.addToTotAccesses(a.getTot());
					map.put(d, tAS);
			}
		}
		
		for(TotAggregate t : install){
			Date d = t.getDate();
			c.setTime(d);
			c.set(Calendar.HOUR_OF_DAY,0);
			c.set(Calendar.MINUTE,0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);

			d = c.getTime();
			AppAccessInstallSeries tAS = map.get(d);
			if(tAS!=null){
				tAS.addToTotInstall(t.getTot());
				map.put(d, tAS);
			}
		}
		return map;
	}

	@Override
	public Map<Date, AppInfo> getAppInfo(Date start, Date end) {
		TreeMap<Date, AppInfo> map = new TreeMap<Date, AppInfo>();
		List<TotAggregate> access = logRepo.getAccessGrouped(start, end);
		List<TotAggregate> install = logRepo.getInstallGrouped(start, end);
		List<TotAggregate> pathStarted = logRepo.getPathStarted(start, end);
		List<TotAggregate> checkedTicket = logRepo.getCheckedTicket(start, end);
		
		Calendar c = Calendar.getInstance();
		c.setTime(end);
		c.set(Calendar.HOUR_OF_DAY,23);
		c.set(Calendar.MINUTE,59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		end = c.getTime();

		c.set(Calendar.YEAR, 2015);
		c.set(Calendar.MONTH, 11);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		Date first = c.getTime();
		
		c.setTime(start);

		if(start.before(first)){
			c.set(Calendar.YEAR, 2015);
			c.set(Calendar.MONTH, 11);
			c.set(Calendar.DAY_OF_MONTH, 1);
		}
		
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		start = c.getTime();

		while(start.before(end)){
			map.put(start, new AppInfo());
			c.setTime(start);
			c.set(Calendar.HOUR_OF_DAY,0);
			c.set(Calendar.MINUTE,0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			c.add(Calendar.DAY_OF_MONTH, 1);
			start = c.getTime();
		}
		
		for(TotAggregate a : access){
			Date d = a.getDate();
			c.setTime(d);
			c.set(Calendar.HOUR_OF_DAY,0);
			c.set(Calendar.MINUTE,0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);

			d = c.getTime();
			AppInfo tAS = map.get(d);
			if(tAS!=null){
					tAS.addToNumAccess(a.getTot());
					map.put(d, tAS);
			}
		}
		
		for(TotAggregate t : install){
			Date d = t.getDate();
			c.setTime(d);
			c.set(Calendar.HOUR_OF_DAY,0);
			c.set(Calendar.MINUTE,0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);

			d = c.getTime();
			AppInfo tAS = map.get(d);
			if(tAS!=null){
				tAS.addToNumInstallation(t.getTot());
				map.put(d, tAS);
			}
		}
		for(TotAggregate p : pathStarted){
			Date d = p.getDate();
			c.setTime(d);
			c.set(Calendar.HOUR_OF_DAY,0);
			c.set(Calendar.MINUTE,0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);

			d = c.getTime();
			AppInfo tAS = map.get(d);
			if(tAS!=null){
					tAS.addToNumPathStarted(p.getTot());
					map.put(d, tAS);
			}
		}
		
		for(TotAggregate ct : checkedTicket){
			Date d = ct.getDate();
			c.setTime(d);
			c.set(Calendar.HOUR_OF_DAY,0);
			c.set(Calendar.MINUTE,0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);

			d = c.getTime();
			AppInfo tAS = map.get(d);
			if(tAS!=null){
				tAS.addToNumCheckedTicket(ct.getTot());
				map.put(d, tAS);
			}
		}
		return map;
	}

}
