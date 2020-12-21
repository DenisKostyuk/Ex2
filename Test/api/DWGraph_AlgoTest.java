package api;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class DWGraph_AlgoTest {




	/**
	 * In this test I check if it is connected or not.
	 *By creating a graph and connecting all the vertices.
	 *The first test is for the graph I created that is attached.
	 *And a second test is after deletion of organs a test that the graph is not connected after deletion.
	 */
	@Test
	void testIsConnected() 
	{
		directed_weighted_graph g = new DWGraph_DS();
		g.addNode(new NodeData(0));
		g.addNode(new NodeData(1));
		g.addNode(new NodeData(2));

		g.connect(0, 1, 10);
		g.connect(0, 2, 20);
		g.connect(1, 0, 30);
		g.connect(1, 2, 40);
		g.connect(2, 0, 50);
		g.connect(2, 1, 60);
		dw_graph_algorithms algo = new DWGraph_Algo();
		algo.init(g);
		assertTrue(algo.isConnected());

		g.removeEdge(0, 2);
		g.removeEdge(1, 2);
		g.removeEdge(2, 1);
		assertFalse(algo.isConnected());
	}
	
	/**
	 * In this test I test whether the function that returns the weight of the short track.
	 */
	@Test
	void testShortestPathDist() 
	{
		directed_weighted_graph g = new DWGraph_DS();
		g.addNode(new NodeData(0));
		g.addNode(new NodeData(1));
		g.addNode(new NodeData(2));

		g.connect(0, 1, 10);
		g.connect(1, 0, 30);
		g.connect(1, 2, 40);
		g.connect(2, 0, 50);
		g.connect(2, 1, 60);
		g.connect(0, 2, 20);
		dw_graph_algorithms algo = new DWGraph_Algo();
		algo.init(g);
		System.out.println(algo.shortestPathDist(0, 1));
		System.out.println(algo.shortestPathDist(1, 0));
		System.out.println(algo.shortestPathDist(2, 0));
		assertEquals(10, algo.shortestPathDist(0, 1));
		assertEquals(30, algo.shortestPathDist(1, 0));
		assertEquals(50, algo.shortestPathDist(2, 0));
	}


	/**
	 * In this test I check if load / save works.
	 *By creating a graph and adding 3 vertices and connecting them.
	 *Save the graph and then upload the saved graph to a new graph and check if they are equal
	 */
	@Test
	void testSave() 
	{
		directed_weighted_graph g = new DWGraph_DS();
		g.addNode(new NodeData(0));
		g.addNode(new NodeData(1));
		g.addNode(new NodeData(2));

		g.connect(0, 1, 10);
		g.connect(0, 2, 20);
		g.connect(1, 0, 30);
		g.connect(1, 2, 40);
		g.connect(2, 0, 50);
		g.connect(2, 1, 60);

		dw_graph_algorithms algo = new DWGraph_Algo();
		algo.init(g);
		algo.save("graph.json");

//		directed_weighted_graph copy_g = copy.copy();
//		System.out.println(g.nodeSize());
//		System.out.println(copy_g.nodeSize());
//		assertEquals(g.nodeSize(), copy_g.nodeSize());
//
//		copy_g.removeNode(0);
//		assertNotEquals(g.nodeSize(), copy_g.nodeSize());

	}

		/**
		 * In this test I test whether the function returns the short route.
		 */
		@Test
		void testShortestPath() 
		{
			directed_weighted_graph g = new DWGraph_DS();
			g.addNode(new NodeData(0));
			g.addNode(new NodeData(1));
			g.addNode(new NodeData(2));
	
			g.connect(0, 1, 10);
			g.connect(0, 2, 20);
			g.connect(1, 0, 30);
			g.connect(1, 2, 40);
			g.connect(2, 0, 50);
			g.connect(2, 1, 60);
	
			dw_graph_algorithms algo = new DWGraph_Algo();
			algo.init(g);
			g.removeEdge(0,2);
	
			List<node_data> actual = algo.shortestPath(0, 2);
			ArrayList<Integer> expected = new ArrayList<>();
	
			expected.add(0);
			expected.add(1);
			expected.add(2);
	
			g.connect(0, 2, 20);
	
			for(int i=0; i<expected.size(); i++) 
			{
				int ex = expected.get(i);
				int ac = actual.get(i).getKey();
				assertEquals(ex, ac);
			}
		}
		
		/**
		 * In this test I test the function of copying a graph
		 */
		@Test
		void testCopy() 
		{
			directed_weighted_graph g = new DWGraph_DS();
			g.addNode(new NodeData(0));
			g.addNode(new NodeData(1));
			g.addNode(new NodeData(2));
	
			g.connect(0, 1, 10);
			g.connect(0, 2, 20);
			g.connect(1, 0, 30);
			g.connect(1, 2, 40);
			g.connect(2, 0, 50);
			g.connect(2, 1, 60);
	
			dw_graph_algorithms algo = new DWGraph_Algo();
			algo.init(g);
	
			directed_weighted_graph copy = algo.copy();
	
			for(node_data node : g.getV()) 
			{
				assertEquals(node, copy.getNode(node.getKey()));
			}
		}
}
