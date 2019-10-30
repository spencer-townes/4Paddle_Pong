import java.awt.Font;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class ScoreCard {
	
	//======================================================= Properties
	
	private final static String DEFAULT_FILENAME = "scores.bin";
	private final static int DEFAULT_LISTSIZE = 10;
	
	private ArrayList<Score> scores = new ArrayList<>();
	private String fileName;
	private int listSize;
	
	//======================================================= Constructors
	
	public ScoreCard() {
		this(DEFAULT_FILENAME, DEFAULT_LISTSIZE);
	}
	
	public ScoreCard(String fileName) {
		this(fileName, DEFAULT_LISTSIZE);
	}
	
	public ScoreCard(int listSize) {
		this(DEFAULT_FILENAME, listSize);
	}
	
	public ScoreCard (String fileName, int listSize) {
		setFileName(fileName);
		setListSize(listSize);
		load();
	}
	
	//======================================================= Methods
	public void addScore(Score score) {
		scores.add(score);
		Collections.sort(scores);
	}
	
	public void addScore(String initials, int score) {
		this.addScore(new Score(initials, score));
	}
	
	public void clearScores() {
		scores.clear();
	}
	
	public void deleteScoreFile() {
		(new File(fileName)).delete();
	}
	public int getScoreCount() {
		return Math.min(listSize, scores.size());
	}
	
	public void save() {
		deleteScoreFile();
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile("scores.bin", "rw");
			for(Score s: scores) s.save(raf);

		} catch (Exception e) { //Don't Crash
			System.out.println("Error: " + e.getMessage());
		} finally { //Checked Last after try
			try {
				raf.close();
			} catch (Exception e) {
			} //Just Double Checking
		}
	}
	
	public void load() {
		if(!(new File(fileName)).exists()) return;
		clearScores();
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile("scores.bin", "r");
			while(raf.getFilePointer() < raf.length())
				scores.add(new Score(raf));
			Collections.sort(scores);

		} catch (Exception e) { //Don't Crash
			System.out.println("Error: " + e.getMessage());
		} finally { //Checked Last after try
			try {
				raf.close();
			} catch (Exception e) {
			} //Just Double Checking
		}
	}
	
	public void resetDefaults() {
		fileName=DEFAULT_FILENAME;
		listSize=DEFAULT_LISTSIZE;
		}
	
	public void displayScoreCard() {
		UIManager.put("OptionPane.messageFont", new Font("Courier New", Font.BOLD, 14));
		UIManager.put("OptionPane.buttonFont", new Font("Courier New", Font.BOLD, 14));
		JOptionPane.showMessageDialog(null, toString(), "-- Top " + listSize + " Scores --", -1);
	}
	
	@Override
	public String toString() {
		String ret = "";
		int i = 0;
		for (Score s: scores) {
			if(++i>listSize) break;
			ret += String.format("%5d) %s\n", i, s);
		}
		return ret;
	}
	
	//======================================================= Getters/Setters
	public String getFileName() {return fileName;}
	public int getListSize() {return listSize;}
	public void setFileName(String fileName) {
		fileName = fileName.trim();
		if(fileName.length() == 0) fileName = DEFAULT_FILENAME;
		if(!fileName.contains(".")) fileName += ".bin";
		if(fileName.endsWith(".")) fileName +="bin";
		if(fileName.startsWith(".")) fileName = "score" + fileName;
		this.fileName = fileName;
	}
	
	public void setListSize(int listSize) {
		this.listSize = Math.max(listSize, DEFAULT_LISTSIZE);
	}
	
	//=======================================================
	// INTERNAL Score Class
	//=======================================================
	
	private class Score implements Comparable<Score>{
		
		//=======================================================
		private String initials;
		private int score;
		
		//=======================================================
		
		public Score(String initials, int score) {
			initials = initials.trim().toUpperCase();
			if(initials.length() < 3)
				initials+="---";
			this.initials = initials.substring(0,3);
			this.score = (score < 0 ? 0: score);
			
		}
		
		public Score(RandomAccessFile raf) throws Exception{
			initials = raf.readUTF();
			score = raf.readInt();
		}
		
		//=======================================================
		public Score clone() {
			return new Score(initials, score);
			
		}
		
		public void save(RandomAccessFile raf) throws Exception{
			raf.writeUTF(initials);
			raf.writeInt(score);
			
		}
		
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof Score)) return false;
			Score s = (Score)obj; return initials.equals(s.initials) && score == s.score;
		}
		
		@Override
		public String toString() {
			return String.format("%-10s %,12d", initials, score);
		}

		@Override
		public int compareTo(Score o) {
			return o.score - score;
		}
		
	}

}
