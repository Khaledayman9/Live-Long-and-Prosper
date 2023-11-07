package projectOneAI;
import java.util.*;

public class GenericSearch {
//	public static Node generalSearch(LLAPProblem problem, Queue<Node> nodeQueue) {
//		// Initialize the node queue with the initial state.
//		nodeQueue.add(new Node(problem.getInitialState(), null, "", 0, 0));
//		while (!nodeQueue.isEmpty()) {
//			// Remove the front node from the queue.
//			Node node = nodeQueue.poll();
//			if (problem.goalTest(node.getStateString())) {
//				return node; // Return the solution node.
//			}s
//			nodeQueue = problem.expand(node, nodeQueue);
//		}
//
//		return null; // No solution found, return failure.
//	}
//
//	public static Node BFS(LLAPProblem problem) {
//		Queue<Node> nodeQueue = new LinkedList<>();
//		return generalSearch(problem, nodeQueue);
//	}
//
//	public static Node UCS(LLAPProblem problem) {
//		// Use a priority queue with path cost as the priority.
//		Queue<Node> nodeQueue = new PriorityQueue<>(new NodeComparator());
//		return generalSearch(problem, nodeQueue);
//	}
//
//	public static Node DFS(LLAPProblem problem) {
//		// Use a stack (Last In, First Out) for depth-first search.
//		Queue<Node> nodeQueue = new LinkedList<>();
//		return generalSearch(problem, nodeQueue);
//	}
//
//	public static Node IDS(LLAPProblem problem) {
//		for (int depth = 0;; depth++) {
//			Node result = DepthLimitedSearch(problem, depth);
//			if (result != null) {
//				return result;
//			}
//		}
//	}
//
//	private static Node DepthLimitedSearch(LLAPProblem problem, int depthLimit) {
//		Queue<Node> nodeQueue = new LinkedList<>();
//		return generalSearch(problem, nodeQueue, depthLimit);
//	}
}
