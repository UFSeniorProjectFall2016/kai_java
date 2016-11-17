package kai.system.system;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import kai.system.devices.ExternalDevice;

public class DatabaseConnection {
	private String dbUri;
	private HashMap<String, String> data;
	private static ExternalDevice parser = new ExternalDevice();

	public DatabaseConnection(String dbUri) {
		// Create empty HashMap
		String dbName = "kai_database.txt";
		data = new HashMap<String, String>();

		// Default to current directory if database path not provided
		if (dbUri == null) {
			this.dbUri = System.getProperty("user.dir");
		} else {
			this.dbUri = dbUri;
		}

		// Try reading database assuming that the provided path is correct
		// Search for file name kai_database.txt
		this.dbUri += "/" + dbName;
		this.loadDatabase(this.dbUri);
	}

	private boolean readDatabase(String filename) {
		int l = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = reader.readLine()) != null) {
				if(l++!=0) {
					parser.parse(line);
					if (!parser.errorGenerated()) {
						data.put(parser.getDevId(), line);
					}
				}
			}
			reader.close();
		} catch (Exception e) {
			System.err.format("Exception occurred trying to read '%s'.", filename);
			return false;
		}
		return true;
	}

	private void loadDatabase(String filename) {
		try {
			File file = new File(filename);
			if (file.createNewFile()) {
				Date date = new Date();
				BufferedWriter out = new BufferedWriter(new FileWriter(this.dbUri, false));
				out.write(date.toString());
				out.newLine();
				out.close();
				
				System.out.println("\tFresh Database copy");
				// Load default security
			} else {
				System.out.println("\tReading Database");
				this.readDatabase(filename);
			}
		} catch (IOException e) {
			System.err.println("\tNo database loaded");
		}
	}

	public int numDataAvailable() {
		return this.data.size();
	}
	
	public String getDBUri() {
		return this.dbUri;
	}
	
	public String[] getDBData() {
		if(data.isEmpty()) {
			return null;
		}
		return (String[]) data.values().toArray();
	}
	
	public void saveToDB(String key, String value) {
		data.put(key, value);
	}
	
	public void commitChangeToLocalDB () {
		if(data.isEmpty()) {
			System.err.println("No data to commit to database");
			return;
		}
		
		Date date = new Date();
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(this.dbUri, false));
			out.write(date.toString());
			out.newLine();
			
			for(String s : this.getDBData()) {
				out.write(s);
				out.newLine();
			}
			out.close();
		} catch (IOException e) {
			System.err.println("Error in commmiting to database");
		}
	}
}
