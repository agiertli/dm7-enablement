package ufo.unit;

import java.util.Map;

import org.kie.api.runtime.rule.DataSource;
import org.kie.api.runtime.rule.RuleUnit;

import ufo.model.UFOSighting;

public class UFOUnit implements RuleUnit {

	private DataSource<UFOSighting> ufo;

	private Map<String, Integer> histogram;
	
	public UFOUnit() {}
	public UFOUnit(DataSource<UFOSighting> ufos) {

		this.ufo = ufos;
	}

	// A DataSource of UFOs for this RuleUnit.
	public DataSource<UFOSighting> getUfo() {
		return ufo;
	}

	@Override
	public void onStart() {
		System.out.println(getName() + " started.");
	}

	@Override
	public void onEnd() {
		System.out.println(getName() + " ended.");
	}

	private String getName() {
		return "UFOUnit";
	}

	public Map<String, Integer> getHistogram() {
		return histogram;
	}

	public void setHistogram(Map<String, Integer> histogram) {
		this.histogram = histogram;
	}

	public void setUfo(DataSource<UFOSighting> ufo) {
		this.ufo = ufo;
	}

}
