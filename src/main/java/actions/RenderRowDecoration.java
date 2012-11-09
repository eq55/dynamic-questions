package actions;
import org.springframework.webflow.action.FormAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;


public class RenderRowDecoration extends FormAction {
	
	
	private static final String TICK_OR_CROSS = "tickOrCross";
	private static final String POSTCODE = "postcode";

	public Event deriveTickOrCross(RequestContext ctx) throws Exception {
		ctx.getRequestScope().put(POSTCODE, ctx.getRequestParameters().get(POSTCODE));
		if (ctx.getRequestParameters().get(POSTCODE).equalsIgnoreCase("BH1 2NF")) {
			ctx.getRequestScope().put(TICK_OR_CROSS, "questionValid");
		} else {
			ctx.getRequestScope().put(TICK_OR_CROSS, "questionInError");
		}
		return success();
	}
	
}
