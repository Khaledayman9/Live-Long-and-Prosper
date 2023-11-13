package projectOneAI;

public class LLAPSearch extends GenericSearch {

	public LLAPSearch() {

	}

	public static String solve(String initalState, String strategy, boolean visualize) {
		LLAPProblem problem = new LLAPProblem(initalState);
		GenericSearch xGenericSearch = new GenericSearch();
		xGenericSearch.GeneralSearch(problem, strategy, visualize);
		if (xGenericSearch.isSuccess()) {
			return xGenericSearch.returnString();
		} else {
			return "NOSOLUTION";
		}
	}

}
