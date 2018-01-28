package ufo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.rule.DataSource;
import org.kie.api.runtime.rule.RuleUnitExecutor;

import ufo.model.UFOSighting;
import ufo.unit.UFOUnit;

public class Analyser {

	private static String CSV_FILE = "complete.csv";
	private static String CSV_SPLIT = ",";
	private static RuleUnitExecutor EXECUTOR;
	private static BufferedReader br;
	
	public static Integer COUNTER = 0;

	public static void main(String[] args) throws IOException {

		KieServices kieServices = KieServices.Factory.get();
		KieContainer kieContainer = kieServices.newKieClasspathContainer();
		KieBase kieBase = kieContainer.getKieBase("KBase1");

		DataSource<UFOSighting> dataStream = DataSource.create();
		EXECUTOR = RuleUnitExecutor.create().bind(kieBase);
		EXECUTOR.bindVariable("ufo", dataStream);
		Map<String, Integer> histogram = new HashMap<String, Integer>();
		EXECUTOR.bindVariable("histogram", histogram);
		

		new Thread(() -> EXECUTOR.runUntilHalt(UFOUnit.class)).start();

		parseData(CSV_FILE, dataStream);
		
		System.out.println("ufos analysed:"+COUNTER);
		
		for (String key : histogram.keySet()) {

			System.out.println("key:" + key);
			System.out.println("value:" + histogram.get(key));
		}

	}

	public static void parseData(String csv, DataSource<UFOSighting> dataStream) throws IOException {

		URL url = Analyser.class.getClassLoader().getResource(csv);
		br = new BufferedReader(new FileReader(url.getPath()));
		br.readLine().split(CSV_SPLIT); // read the headers

		String line = "";

		while ((line = br.readLine()) != null) {

			// use comma as separator
			String[] sighting = line.split(CSV_SPLIT);

			UFOSighting ufo = new UFOSighting();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");

			String dateParts[] = sighting[0].split("/");

			LocalDateTime dateTime = null;
			try {
				dateTime = LocalDateTime.parse(sighting[0], formatter);
			} catch (Exception e) {

				if (dateParts[1].length() == 1) {

					try {
						formatter = DateTimeFormatter.ofPattern("MM/d/yyyy HH:mm");
						dateTime = LocalDateTime.parse(sighting[0], formatter);
					} catch (Exception e1) {

						continue;

					}
				}

			}

			ufo.setDateTime(dateTime);
			ufo.setCity(sighting[1]);
			ufo.setState(sighting[2]);
			ufo.setCountry(sighting[3]);
			ufo.setShape(sighting[4]);
			if (sighting[5].equals("0.0"))
				ufo.setSeconds(0);
			else {
				try {
					ufo.setSeconds(Integer.valueOf(sighting[5]));
				} catch (Exception e) {

					try {
						String secondsParts[] = sighting[5].split("\\.");
						ufo.setSeconds(Integer.valueOf(secondsParts[0]));
					} catch (Exception e2) {
						continue;
					}
				}
			}
			ufo.setDuration(sighting[6]);
			ufo.setComments(sighting[7]);
			COUNTER++;
			dataStream.insert(ufo);

		}

		EXECUTOR.halt();
	}

}
