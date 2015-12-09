package it.polito.applied.asti.clan.service;

import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class UtilAverageTask {
	
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	private LinkedBlockingQueue<Integer> paths;
	
	public UtilAverageTask() throws InterruptedException{
		paths = new LinkedBlockingQueue<Integer>();
	}
	
	@PostConstruct
	private void instantiate(){
		Runnable r = new CalculateAverage();
		taskExecutor.execute(r);
	}
	
	public void queue(Integer i){
		if(!paths.contains(i))
			paths.offer(i);
	}
	
	private class CalculateAverage implements Runnable{
		
		@Override
		public void run() {
			try {
				while(true){
					int idPath = paths.take();
					System.out.println(idPath);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
