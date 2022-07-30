package Core.Helper.Report;

public class ReportHandle {
	public boolean isTrue;
	public String message = "";
	public Exception exceptionThrown = new Exception();
	
	public ReportHandle() {
		
	}
	
	public ReportHandle(boolean isTrue, String message, Exception exceptionThrown) {
		this.isTrue = isTrue;
		this.message = message;
		this.exceptionThrown = exceptionThrown;
	}

	public void updateStatus(boolean isTrue, String message, Exception exceptionThrown) {
		this.isTrue = isTrue;
		this.message = message;
		this.exceptionThrown = exceptionThrown;
	}
}
