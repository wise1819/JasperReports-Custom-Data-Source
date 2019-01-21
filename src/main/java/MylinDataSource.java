import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public final class MylinDataSource implements JRDataSource {

	private MylinDataSource() {

	}

	@Override
	public boolean next() throws JRException {
		return false;
	}

	@Override
	public Object getFieldValue(JRField jrField) throws JRException {
		return null;
	}

	public static MylinDataSource getDataSource() {
		return new MylinDataSource();
	}

}
