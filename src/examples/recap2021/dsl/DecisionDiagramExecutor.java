package examples.recap2021.dsl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import examples.recap2021.metamodel.DecisionDiagramMetamodel;
import examples.recap2021.metamodel.Goal;
import examples.recap2021.metamodel.Option;
import examples.recap2021.metamodel.Question;
import examples.recap2021.metamodel.Target;

public class DecisionDiagramExecutor {

	private DecisionDiagramMetamodel model;

	public DecisionDiagramExecutor(DecisionDiagramMetamodel mm) {
		this.model = mm;
	}

	public void run() {
	    JFrame frame = new JFrame(model.getName());
		Target currentTarget = model.getInitialTarget();
		while(true) {
			String text = currentTarget.getText();
			if(currentTarget instanceof Goal) {
				JOptionPane.showMessageDialog(frame,text);
				return;
			} else {
				Question q = (Question)currentTarget;
				List<String> opts = new ArrayList<String>();
				Map<String,Target> targets = new HashMap<String,Target>();
				for(Option t: q.getOptions()) {
					opts.add(t.getText());
					targets.put(t.getText(),t.getTarget());
				}
				String[] prim = new String[opts.size()];
				for(int i=0; i<opts.size(); i++) prim[i]=opts.get(i);
				String answer = (String) JOptionPane.showInputDialog(frame, 
				        text,
				        text,
				        JOptionPane.QUESTION_MESSAGE, 
				        null, 
				        prim, 
				        prim[0]);
				currentTarget = targets.get(answer);
			}
		}
	}

}
