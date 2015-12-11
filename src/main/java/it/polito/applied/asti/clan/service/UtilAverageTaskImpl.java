package it.polito.applied.asti.clan.service;

import it.polito.applied.asti.clan.pojo.Comment;
import it.polito.applied.asti.clan.repository.CommentRepository;
import it.polito.applied.asti.clan.repository.PathInfoRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilAverageTaskImpl implements UtilAverageTask{
	
	/*Valore utilizzato per il calcolo della  media di un path.
	 * Corrisponde al quantile della distribuzione normale per ottenere un'affidabilità dell'80%*/
	private final double ZETA_ALFA = 0.845;
	
	/*Numero di possibili voti diversi che arriveranno dall'app mobile
	 * 0.5 - 1 - 1.5 - 2 - 2.5 - 3 - 3.5 - 4 - 4.5 - 5*/
	private final int K = 10;
	private final float MIN = 0.5f;
	private final int MAX = 5;
	private final float STEP = 0.5f;
	private Thread t;
	
	@Autowired
	private CommentRepository commentRepo;
	
	@Autowired 
	private PathInfoRepository pathInfoRepo;
	
	private LinkedBlockingQueue<Integer> paths;
	
	public UtilAverageTaskImpl(){
		paths = new LinkedBlockingQueue<Integer>();
	}
	
	@PostConstruct
	private void instantiate(){
		t = new CalculateAverage();
		t.start();
	}
	
	@PreDestroy
	private void poison(){
		paths.offer(-1);
		t.interrupt();
	}

	
	@Override
	public void queue(Integer i){
		if(!paths.contains(i))
			paths.offer(i);
	}
	
	private class CalculateAverage extends Thread{
		
		@Override
		public void run() {
			Map<Float, Float> votes = new HashMap<Float, Float>();
			float n=0;
			double a=0, b=0, c=0 ,d=0;
			/*Per il numero dei voti non mi affido alla size della lista di commenti in quanto
			 * potrebbe (attualmente non accade per come l'app ci manda i dati) in futuro verificarsi
			 * che arrivi un commento senza voto (ovvero rating=0)*/
			int numVotes = 0;
			float average=0;
			try {
				while(!Thread.currentThread().isInterrupted()){
					int idPath = paths.take();
					if(idPath==-1)
						break;
					List<Comment> comments = commentRepo.findByIdPath(idPath);
					if(comments==null || comments.size()==0)
						continue;
					for(Comment comment : comments){
						n=0;
						if(comment.getRating()>0){
							if(votes.containsKey(comment.getRating()))
								n=votes.get(comment.getRating());
							votes.put(comment.getRating(), n+1);
							numVotes++;
						}
					}
					
					for(float i=MIN; i<=MAX; i+=STEP){
						if(!votes.containsKey(i))
							votes.put(i, 0f);
					}
					
					Set<Float> set = votes.keySet();
					
					for(Float f : set){
						System.out.println(f+" "+votes.get(f)+" "+numVotes);
						a+=(double)(f)*((votes.get(f)+1)/(numVotes+K));
						b+=(double) (f*f)*((votes.get(f)+1)/(numVotes+K));
						System.out.println("AB: "+a+" "+b);
					}
					c = a*a;
					d = numVotes+K+1;
					average = (float)(a-ZETA_ALFA*Math.sqrt((b-c)/d));
					System.out.println("A: "+a);
					System.out.println("B: "+b);
					System.out.println("C: "+c);
					System.out.println("D: "+d);
					pathInfoRepo.updateAverage(idPath, average, comments.size());
					votes.clear();
					a=0;
					b=0;
					c=0;
					d=0;
					average=0;
					numVotes=0;
				}
			} catch (InterruptedException e) {
				System.out.println("INTERRUPTED EXCEPTION IN");
				Thread.currentThread().interrupt();
			}
		}
		
	}
}
