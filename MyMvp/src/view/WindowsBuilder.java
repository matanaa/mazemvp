package view;

import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


//WindowsBuilder propwin =new WindowsBuilder(properties);
//propwin.buildWindows();
public class WindowsBuilder<T> {
	
	
	XMLEncoder xmlEncoder = null;
	ByteArrayOutputStream baos;
	int len=0;
	
	String buttens[];
	public WindowsBuilder(Serializable obj ) {
		super();
		baos = new ByteArrayOutputStream();
		xmlEncoder = new XMLEncoder(baos);
		xmlEncoder.writeObject(obj);
		xmlEncoder.close();
		
		String xmlstring =getXml();
		len= xmlstring.split("<void property=").length-1;
		buttens =new String[len];
		if (len ==0){
			return;
		}
		xmlstring= xmlstring.substring(xmlstring.indexOf("<void property="),xmlstring.lastIndexOf("</void>"));
		
		for (int i=0;i<len;i++){
			buttens[i]= xmlstring.substring(xmlstring.indexOf("<void property=")+"<void property=\"".length(),xmlstring.indexOf("\">"));
			xmlstring=xmlstring.substring(xmlstring.indexOf("</void>")+"</void>".length());
		}
		
	}
	
	public void buildWindows(){
		Shell shell = new Shell();
		shell.setText("Generate Maze");
		shell.setSize((len+1)*100, 200);

		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);
		
		Label lbl[]= new Label[len];
		Text txt[] =new Text[len];
		
		for (int i=0;i<len;i++){
			lbl[i]=new Label(shell, SWT.NONE);
			lbl[i].setText(buttens[i]);
			txt[i] =new Text(shell, SWT.BORDER);
		}


		Button btnGenerate = new Button(shell, SWT.PUSH);
		btnGenerate.setText("Submit");
		btnGenerate.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
							}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		shell.open();
	}
	private String getXml(){
		return baos.toString();
	}
	
	
}
