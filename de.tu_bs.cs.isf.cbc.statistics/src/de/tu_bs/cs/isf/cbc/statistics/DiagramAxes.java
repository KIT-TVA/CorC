package de.tu_bs.cs.isf.cbc.statistics;

public class DiagramAxes {
		private String xAxis;
		private String yAxis;
		private boolean isAverageData;
		
		public DiagramAxes(boolean isProofSteps, boolean isAverageData) {
			xAxis = "diagram <- c(";
			if (!isProofSteps) {
				yAxis = "time <- c(" ;
			} else {
				yAxis = "steps <- c(";
			}
			this.isAverageData = isAverageData;
		}
		
		public void addData(String x, String y) {
			xAxis += "\"" + x + "\", ";
			yAxis += y + ", ";
		}
		
		public String getX() {
			return xAxis;
		}
		
		public String getY() {
			return yAxis;
		}
		
		public boolean isAverageData() {
			return isAverageData;
		}
}
