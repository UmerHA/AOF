package Tests;

//import java.awt.Cursor;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

@SuppressWarnings("serial")
public class JProgressBarTest extends JFrame {

    private JProgressBar progBar;
    private Task task;
	
    public JProgressBarTest () {
    	//init
    	progBar = new JProgressBar();
    	add(progBar);
    	progBar.setValue(0);
    	progBar.setStringPainted(true);
    	
    	setSize(300,120);
    	setVisible(true);
    	
    	//start
        //setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        task = new Task();
        task.addPropertyChangeListener(new pcLis());
        task.execute();
    }
    
    class Task extends SwingWorker<Void, Void> {
    	public Void doInBackground () {
    		for (int i=0;i<500;i++) {
    			try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					//-> just ignore
					//e.printStackTrace();
				}
    		}
    		
    		
    		return null;
    	}
    	
    	public void done () {
    		System.out.println("Done");
    	}
    }
    class pcLis implements PropertyChangeListener {
		public void propertyChange(PropertyChangeEvent evt) {
			if ("progress" == evt.getPropertyName()) {
				int progress = (Integer) evt.getNewValue();
				progBar.setValue(progress);
				System.out.println(task.getProgress());
			}
		}
	}

    public static void main (String[] args) {
    	new JProgressBarTest();
    }
}

