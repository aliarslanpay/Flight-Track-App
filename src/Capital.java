import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Capital {

	private int id;
	private String name;
	
	
	public int getLastId() throws FileNotFoundException {
		Scanner scanner =new Scanner(new FileInputStream("capital.txt"));
		String[] lastLine = null;
		int i=0;
		while (scanner.hasNextLine()) {
			lastLine = scanner.nextLine().split("-");
		    i++;
		}
		scanner.close();
		if(i==0){
			return 0;
		}
		else{
			return Integer.parseInt(lastLine[0]);
		}
	}
	
	public boolean addCapital() throws IOException {
		FileWriter writer = new FileWriter("capital.txt",true);
		writer.write((getLastId()+1)+"-"+getName()+"\n");
		writer.close();
		
		return true;
	}
	
	public boolean updateCapital() throws IOException {
		FileWriter writer = new FileWriter("capital.txt",true);
		writer.write((getId())+"-"+getName()+"\n");
		writer.close();
		
		return true;
	}
	
	public List<Capital> getList() throws FileNotFoundException {
		Scanner scanner =new Scanner(new FileInputStream("capital.txt"));
		List<Capital>list=new ArrayList<Capital>();
		String[] item=null;
		while(scanner.hasNextLine()){
			item=scanner.nextLine().split("-");
			Capital capital=new Capital();
			capital.setId(Integer.parseInt(item[0]));
			capital.setName(item[1]);
			list.add(capital);
		}
		scanner.close();
		
		return list;
	}
	
	public void allRecordDelete() throws IOException {
		FileWriter writer = new FileWriter("capital.txt");
		writer.write("");
		writer.close();
	}
	
	public Capital findByName(String name) throws FileNotFoundException {
		Scanner scanner =new Scanner(new FileInputStream("capital.txt"));
		String[] currentLine = null;
		while (scanner.hasNextLine()) {
			currentLine = scanner.nextLine().split("-");
			
			if(currentLine[1].equals(name)){
				Capital capital=new Capital();
				capital.setId(Integer.parseInt(currentLine[0]));
				capital.setName(currentLine[1]);
				scanner.close();
				
				return capital;
			}
		}
		scanner.close();
		
		return null;
	}

	public Capital findById(int id) throws FileNotFoundException {
		Scanner scanner=new Scanner(new FileInputStream("capital.txt"));
		String[] currentLine = null;
		while (scanner.hasNextLine()) {
			currentLine = scanner.nextLine().split("-");
			if(currentLine[0].equals(Integer.toString(id))){
				Capital capital=new Capital();
				capital.setId(Integer.parseInt(currentLine[0]));
				capital.setName(currentLine[1]);
				scanner.close();
				
				return capital;
			}
		}
		scanner.close();
		
		return null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
