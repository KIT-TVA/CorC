package de.tu_bs.cs.isf.cbc.util.statistics;

public class DiagramAxes {
		private String xAxis;
		private String yAxis;
		private boolean isAverageData;
		private int dataNum;
		
		public DiagramAxes(boolean isProofSteps, boolean isAverageData) {
			xAxis = "diagram <- c(";
			if (!isProofSteps) {
				yAxis = "time <- c(" ;
			} else {
				yAxis = "steps <- c(";
			}
			this.isAverageData = isAverageData;
			this.dataNum = 0;
		}
		
		public void addData(String x, String y) {
			xAxis += "\"" + x + "\", ";
			yAxis += y + ", ";
			dataNum++;
		}
		
		public String getX() {
			return xAxis;
		}
		
		public String getY() {
			return yAxis;
		}
		
		public int getDataNum() {
			return this.dataNum;
		}
		
		public boolean isAverageData() {
			return isAverageData;
		}
}
