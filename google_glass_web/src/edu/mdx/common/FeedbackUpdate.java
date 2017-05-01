package edu.mdx.common;

public class FeedbackUpdate {

	/**
	 * 
	 */

	
	private int[] pace = null;
	private int[] volume = null;
	private int[] bodyLanguage = null;
	private int[] clarity = null;
	private int[] interest = null;
	
	
	public FeedbackUpdate() {
	}
	
	public FeedbackUpdate(int[] pace, int[] volume, int[] bodyLanguage, int[] clarity, int[] interest) {
		super();
		this.pace = pace;
		this.volume = volume;
		this.bodyLanguage = bodyLanguage;
		this.clarity = clarity;
		this.interest = interest;
	}
	
	public void setMetricValues(String metricName, int[] values){
		switch (metricName) {
		case "PACE":
			this.setPace(values);
			break;
		case "VOLUME":
			this.setVolume(values);
			break;
		case "BODY_LANGUAGE":
			this.setBodyLanguage(values);
			break;
		case "CLARITY":
			this.setClarity(values);
			break;
		case "INTEREST":
			this.setInterest(values);
			break;
			

		default:
			break;
		}
	}

	public int[] getPace() {
		return pace;
	}

	public void setPace(int[] pace) {
		this.pace = pace;
	}

	public int[] getVolume() {
		return volume;
	}

	public void setVolume(int[] volume) {
		this.volume = volume;
	}

	public int[] getBodyLanguage() {
		return bodyLanguage;
	}

	public void setBodyLanguage(int[] bodyLanguage) {
		this.bodyLanguage = bodyLanguage;
	}

	public int[] getClarity() {
		return clarity;
	}

	public void setClarity(int[] clarity) {
		this.clarity = clarity;
	}

	public int[] getInterest() {
		return interest;
	}

	public void setInterest(int[] interest) {
		this.interest = interest;
	}
}
