package interceptors;

import org.springframework.webflow.definition.StateDefinition;
import org.springframework.webflow.execution.EnterStateVetoException;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.FlowExecutionListenerAdapter;
import org.springframework.webflow.execution.RequestContext;
import org.springframework.webflow.execution.View;

public class DynamicQuestionFlowAdaptor extends FlowExecutionListenerAdapter {
	
	@Override
	public void eventSignaled(RequestContext context, Event event) {
		// TODO Auto-generated method stub
		super.eventSignaled(context, event);
		System.out.println("***eventSignaled:" + event.toString());
	}
	
	@Override
	public void stateEntering(RequestContext context, StateDefinition state)
			throws EnterStateVetoException {
		// TODO Auto-generated method stub
		super.stateEntering(context, state);
		System.out.println("***stateEntering:" + state.toString());
	}
	
	@Override
	public void viewRendering(RequestContext context, View view,
			StateDefinition viewState) {
		// TODO Auto-generated method stub
		super.viewRendering(context, view, viewState);
		System.out.println("***viewRendering:" + view.toString());
	}

}
