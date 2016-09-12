package it.polito.applied.asti.clan.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.polito.applied.asti.clan.exception.BadRequestException;
import it.polito.applied.asti.clan.exception.ConflictException;
import it.polito.applied.asti.clan.pojo.AppAccessInstallSeries;
import it.polito.applied.asti.clan.pojo.AppInfo;
import it.polito.applied.asti.clan.pojo.AppPoiRank;
import it.polito.applied.asti.clan.pojo.CheckTicketInput;
import it.polito.applied.asti.clan.pojo.Comment;
import it.polito.applied.asti.clan.pojo.CommentDTO;
import it.polito.applied.asti.clan.pojo.CommentsPage;
import it.polito.applied.asti.clan.pojo.CommentsRequest;
import it.polito.applied.asti.clan.pojo.DeviceTicketAssociation;
import it.polito.applied.asti.clan.pojo.Log;
import it.polito.applied.asti.clan.pojo.LogDTO;
import it.polito.applied.asti.clan.pojo.LogSeriesInfo;
import it.polito.applied.asti.clan.pojo.LogType;
import it.polito.applied.asti.clan.pojo.PathInfo;
import it.polito.applied.asti.clan.pojo.Poi;
import it.polito.applied.asti.clan.pojo.Ticket;
import it.polito.applied.asti.clan.pojo.TicketTime;
import it.polito.applied.asti.clan.pojo.TotAggregate;
import it.polito.applied.asti.clan.repository.CommentRepository;
import it.polito.applied.asti.clan.repository.DeviceTicketAssociationRepository;
import it.polito.applied.asti.clan.repository.LogRepository;
import it.polito.applied.asti.clan.repository.PathInfoRepository;
import it.polito.applied.asti.clan.repository.PoiRepository;
import it.polito.applied.asti.clan.repository.TicketRepository;

@Service
public class AppServiceImpl implements AppService{
	
	@Value("${status.canceled.id}")
	private String CANCELED;
	@Value("${status.validated.id}")
	private String VALIDATED;
	@Value("${status.released.id}")
	private String RELEASED;
	
	@Value("${version.zip}")
	private int zipVersion;
	
	@Value("${version.app}")
	private int minSupportedVersion;
	
	/*Grandezza pagina commenti da restituire all'app mobile*/
	private final int DIM_PAGE = 10;
	
	
//	@Autowired
//	private VersionZipRepository versionRepo;
	
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
	
	@Autowired
	private DeviceTicketAssociationRepository deviceTicketAssociationRepo;
	
	@Autowired
	private PoiRepository poiRepo;
	
	
	@Override
	public int getVersion() {
		return zipVersion;
	}

	@Override
	public boolean checkTicket(CheckTicketInput c) {
		DeviceTicketAssociation d = deviceTicketAssociationRepo.findByDeviceId(c.getDeviceId());
		if(d!=null)
			d.addTicketTime(new TicketTime(c.getTicket(), new Date()));
		else{
			d = new DeviceTicketAssociation();
			d.setDeviceId(c.getDeviceId());
			d.addTicketTime(new TicketTime(c.getTicket(), new Date()));
		}
		deviceTicketAssociationRepo.save(d);
		List<Ticket> tickets = ticketRepo.findByIdTicket(c.getTicket());
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
	public void postComment(List<LogDTO> logComment, int appVersion) throws BadRequestException {
		List<Comment> comments = new ArrayList<Comment>();
		
		for(LogDTO dto : logComment){
			if(dto.getArgs()==null || dto.getArgs().getComment()==null)
				throw new BadRequestException();
			
			Comment c = new Comment(dto.getArgs().getComment(), dto.getTimestamp(), dto.getDevice_id(), dto.getArgs().getId_path(), dto.getArgs().getTicket_contents());
			c = commentRepo.setComment(c);
			comments.add(c);
			avgTask.queue(c.getIdPath());
		}
		postCommentLog(comments, logComment, appVersion);
	}

	private void postCommentLog(List<Comment> comments, List<LogDTO> logComment, int appVersion) {
		List<Log> logs = new ArrayList<Log>();
		for(int i=0; i<logComment.size(); i++){
			logs.add(new Log(logComment.get(i), comments.get(i).getId(), appVersion));
		}
		
		logRepo.save(logs);
	}

	@Override
	public void postLog(List<LogDTO> logsDTO, int appVersion) {
		List<Log> logs = new ArrayList<Log>();
		for(int i=0; i<logsDTO.size(); i++){
			Log l = new Log(logsDTO.get(i), appVersion);
			if(l.getLogType()!=null && l.getLogType().equals(LogType.Install)){
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
	public long getViewedPoi(Date date) {
		return logRepo.countViewedPoiFromDate(date);
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
		
		Calendar c = Calendar.getInstance();
		c.setTime(end);
		c.set(Calendar.HOUR_OF_DAY,23);
		c.set(Calendar.MINUTE,59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		end = c.getTime();

		c.set(Calendar.YEAR, 2016);
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		Date first = c.getTime();
		
		c.setTime(start);

		if(start.before(first)){
			c.set(Calendar.YEAR, 2016);
			c.set(Calendar.MONTH, 0);
			c.set(Calendar.DAY_OF_MONTH, 1);
		}
		
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		Date current = c.getTime();
		List<TotAggregate> devices = logRepo.getDevicesGrouped(current, end);
		List<TotAggregate> install = logRepo.getInstallGrouped(current, end);
		
		
		for(TotAggregate tt : install){
			c.set(tt.getYear(), tt.getMonth()-1, tt.getDay());
			Date d = c.getTime();
			while(current.before(d)){
				map.put(current, new AppAccessInstallSeries());
				c.setTime(current);
				c.add(Calendar.DAY_OF_MONTH, 1);
				current = c.getTime();
			}
			AppAccessInstallSeries aa = new AppAccessInstallSeries();
			aa.setTotInstall(tt.getTot());
			map.put(current, aa);
			c.setTime(current);
			c.add(Calendar.DAY_OF_MONTH, 1);
			current = c.getTime();
		}
		
		for(TotAggregate tt : devices){
			c.set(tt.getYear(), tt.getMonth()-1, tt.getDay());
			Date d = c.getTime();
			AppAccessInstallSeries aa = map.get(d);
			if(aa!=null){
				aa.setTotDevice(tt.getTot());
			}
		}	
		while(current.before(end)){
			map.put(current, new AppAccessInstallSeries());
			c.setTime(current);
			c.add(Calendar.DAY_OF_MONTH, 1);
			current = c.getTime();
		}
		return map;
	}

	@Override
	public Map<Date, AppInfo> getAppInfo(Date start, Date end) {
		TreeMap<Date, AppInfo> map = new TreeMap<Date, AppInfo>();
		Calendar c = Calendar.getInstance();
		c.setTime(end);
		c.set(Calendar.HOUR_OF_DAY,23);
		c.set(Calendar.MINUTE,59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		end = c.getTime();

		c.set(Calendar.YEAR, 2016);
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		Date first = c.getTime();
		
		c.setTime(start);

		if(start.before(first)){
			c.set(Calendar.YEAR, 2016);
			c.set(Calendar.MONTH, 0);
			c.set(Calendar.DAY_OF_MONTH, 1);
		}
		
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		Date current = c.getTime();
		
		List<TotAggregate> access = logRepo.getAccessGrouped(current, end);
		List<TotAggregate> install = logRepo.getInstallGrouped(current, end);
		List<TotAggregate> pathStarted = logRepo.getPathStarted(current, end);
		List<TotAggregate> checkedTicket = logRepo.getCheckedTicket(current, end);
		
		for(TotAggregate tt : access){
			c.set(tt.getYear(), tt.getMonth()-1, tt.getDay());
			Date d = c.getTime();
		
			while(current.before(d)){
				map.put(current, new AppInfo());
				c.setTime(current);
				c.add(Calendar.DAY_OF_MONTH, 1);
				current = c.getTime();
			}
			AppInfo aa = new AppInfo();
			aa.setNumAccess(tt.getTot());
			map.put(current, aa);
			c.setTime(current);
			c.add(Calendar.DAY_OF_MONTH, 1);
			current = c.getTime();
		}
		for(TotAggregate tt : install){
			c.set(tt.getYear(), tt.getMonth()-1, tt.getDay());
			Date d = c.getTime();
			AppInfo aa = map.get(d);
			if(aa!=null){
				aa.setNumInstallation(tt.getTot());
			}
		}
		for(TotAggregate tt : pathStarted){
			c.set(tt.getYear(), tt.getMonth()-1, tt.getDay());
			Date d = c.getTime();
			AppInfo aa = map.get(d);
			if(aa!=null){
				aa.setNumPathStarted(tt.getTot());
			}
		}
		for(TotAggregate tt : checkedTicket){
			c.set(tt.getYear(), tt.getMonth()-1, tt.getDay());
			Date d = c.getTime();
			AppInfo aa = map.get(d);
			if(aa!=null){
				aa.setNumCheckedTicket(tt.getTot());
			}
		}
		
		return map;
	}

	@Override
	public void checkAppVersion(int appVersion) throws ConflictException {
		if(appVersion<minSupportedVersion)
			throw new ConflictException("La tua versione dell'app ("+appVersion+") è obsoleta. Per favore, scarica la nuova versione");
	}

	@Override
	public List<AppPoiRank> getAppPoiRank(Date start, Date end) {
		List<Poi> poiList = poiRepo.findAll();
		Map<String, String> namePoi = new HashMap<String, String>();
		for(Poi p : poiList){
			namePoi.put(p.getIdSite(), p.getName());
		}
	
		Calendar c = Calendar.getInstance();
		c.setTime(end);
		c.set(Calendar.HOUR_OF_DAY,23);
		c.set(Calendar.MINUTE,59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		end = c.getTime();
		c.set(Calendar.YEAR, 2016);
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		Date first = c.getTime();
		
		c.setTime(start);

		if(start.before(first)){
			c.set(Calendar.YEAR, 2016);
			c.set(Calendar.MONTH, 0);
			c.set(Calendar.DAY_OF_MONTH, 1);
		}
		
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		Date current = c.getTime();
		
		List<AppPoiRank> l = logRepo.getOpenPoiRank(current, end);
		for(AppPoiRank p : l){
			p.setName(namePoi.get(p.getIdPoi()));
		}
		return l;
	}
	
//	private String transformName(String s){
//		if(s==null)
//			return null;
//		String string = s.replace('_', ' ');
//		return string;
//	}

}
